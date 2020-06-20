package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
    
    //private static final String DRIVER = "org.apache.derby.iapi.jdbc.AutoloadedDriver";
    private static DBManager sInstance;

    public static synchronized DBManager getInstance() {
        if (sInstance == null) {
            sInstance = new DBManager();
            // auto connect
            try {
            	
                sInstance.connect("train", "123456");
		System.out.println("------------------------------------");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sInstance;
    }

    private Connection mConnection;
   // private MySQLDAO mDAO; 
    private DBDAO mDAO;
    

    private DBManager() {
    }

    
    public boolean connect(String user, String pass) throws SQLException {
        try {
            //Class.forName(DRIVER);
            this.mConnection = DriverManager.getConnection("jdbc:derby:db_train;create=true", user, pass);
            this.mDAO = new DBDAO();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
	    return false;
        }
    }

    
    public boolean connectSilently(String user, String pass) {
        try {
            return connect(user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean isClosed() {
        try {
            return mConnection == null || mConnection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    
    public StatementBuilder execute(String sql) {
        return new StatementBuilder().execute(sql);
    }

    
    public StatementBuilder prepare(String sql, Object ... params) {
        return new StatementBuilder().prepare(sql, params);
    }

    
    public DBDAO dao() {
        return mDAO;
    }

    private enum StatementState {
        Ready, Batching, Preparing, Commited
    }

    
    @SuppressWarnings("WeakerAccess")
    public class StatementBuilder {
        Statement stmt;
        StatementState state;
        List<String> sqlList;
        int prepareParamIndex;

        public StatementBuilder() {
            this.state = StatementState.Ready;
            this.sqlList = new ArrayList<>(3);
        }

        
        public StatementBuilder execute(String sql) {
            if (state == StatementState.Ready) {
                state = StatementState.Batching;
                try {
                    stmt = mConnection.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            sqlList.add(sql);
            return this;
        }

        
        public StatementBuilder prepare(String sql, Object... params) {
            try {
                state = StatementState.Preparing;
                stmt = mConnection.prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                put(params);
                return this;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        
        public StatementBuilder put(Object... params) {
            if (params != null && params.length > 0) {
                try {
                    PreparedStatement stmt = (PreparedStatement) this.stmt;
                    for (Object obj : params) {
                        prepareParamIndex++;
                        stmt.setObject(prepareParamIndex, obj);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            return this;
        }

        
        public ResultSet query() {
            try {
                ResultSet res = null;
                if (state == StatementState.Batching && sqlList.size() >= 1) {
                    res = stmt.executeQuery(sqlList.get(0));
                }
                else if(state == StatementState.Preparing){
                    res = ((PreparedStatement) stmt).executeQuery();
                }
                //close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

       
        public int[] update() {
            try {
                int[] res = null;
                if (state == StatementState.Batching) {
                    for (String sql : sqlList) {
//                        stmt.addBatch(sql);
                        stmt.executeUpdate(sql);
                    }
//                    res = stmt.executeBatch();
                }
                else if(state == StatementState.Preparing){
                    boolean isQuery = ((PreparedStatement) stmt).execute();
                    if(!isQuery){
                        res = new int[] { stmt.getUpdateCount() };
                    }
                }
                close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        
        public void close() {
            try {
                if (stmt != null && !stmt.isClosed()) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
