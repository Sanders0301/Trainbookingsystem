package bean;

import java.io.Serializable;
import java.util.Date;
// customer information
public class Customer implements Serializable{
	private static final long serialVersionUID = 7883012890434315219L;
	private String name;
    private int sex;
    private String id_num;
    private String tel;
    private int customer_type;
    private Date reg_time;

    public static final int SEX_FEMALE = 0;
    public static final int SEX_MALE = 1;

    public static final int TYPE_STUDENT = 2;
    public static final int TYPE_NORMAL = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public String getSexString(){
        return sex == SEX_FEMALE ? "Female" : "Male";
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdNum() {
        return id_num;
    }

    public void setIdNum(String id_num) {
        this.id_num = id_num;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getCustomerType() {
        return customer_type;
    }

    public String getCustomerTypeString() {
        return customer_type == TYPE_STUDENT ? "Student" : "Adult";
    }

    public void setCustomerType(int customer_type) {
        this.customer_type = customer_type;
    }

	public Date getReg_time() {
		return reg_time;
	}

	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
    
}
