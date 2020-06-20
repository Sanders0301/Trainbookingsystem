/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ManagerTest {
    
    public ManagerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getManagerId method, of class Manager.
     */
    @Test
    public void testGetManagerId() {
        System.out.println("getManagerId");
        Manager instance = new Manager();
        int expResult = 0;
        int result = instance.getManagerId();
        assertEquals(expResult, result);
    
        fail("The test case is a prototype.");
    }

    /**
     * Test of setManagerId method, of class Manager.
     */
    @Test
    public void testSetManagerId() {
        System.out.println("setManagerId");
        int manager_id = 0;
        Manager instance = new Manager();
        instance.setManagerId(manager_id);
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPointId method, of class Manager.
     */
    @Test
    public void testGetPointId() {
        System.out.println("getPointId");
        Manager instance = new Manager();
        int expResult = 0;
        int result = instance.getPointId();
        assertEquals(expResult, result);
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPointId method, of class Manager.
     */
    @Test
    public void testSetPointId() {
        System.out.println("setPointId");
        int point_id = 0;
        Manager instance = new Manager();
        instance.setPointId(point_id);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsername method, of class Manager.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        Manager instance = new Manager();
        String expResult = "";
        String result = instance.getUsername();
        assertEquals(expResult, result);
 
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUsername method, of class Manager.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "";
        Manager instance = new Manager();
        instance.setUsername(username);
 
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class Manager.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Manager instance = new Manager();
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
 
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPassword method, of class Manager.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        Manager instance = new Manager();
        instance.setPassword(password);
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Manager.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Manager instance = new Manager();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
    
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Manager.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Manager instance = new Manager();
        instance.setName(name);
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSex method, of class Manager.
     */
    @Test
    public void testGetSex() {
        System.out.println("getSex");
        Manager instance = new Manager();
        int expResult = 0;
        int result = instance.getSex();
        assertEquals(expResult, result);
      
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSexString method, of class Manager.
     */
    @Test
    public void testGetSexString() {
        System.out.println("getSexString");
        Manager instance = new Manager();
        String expResult = "";
        String result = instance.getSexString();
        assertEquals(expResult, result);
    
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSex method, of class Manager.
     */
    @Test
    public void testSetSex() {
        System.out.println("setSex");
        int sex = 0;
        Manager instance = new Manager();
        instance.setSex(sex);
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of getManagerType method, of class Manager.
     */
    @Test
    public void testGetManagerType() {
        System.out.println("getManagerType");
        Manager instance = new Manager();
        int expResult = 0;
        int result = instance.getManagerType();
        assertEquals(expResult, result);
    
        fail("The test case is a prototype.");
    }

    /**
     * Test of getManagerTypeString method, of class Manager.
     */
    @Test
    public void testGetManagerTypeString() {
        System.out.println("getManagerTypeString");
        Manager instance = new Manager();
        String expResult = "";
        String result = instance.getManagerTypeString();
        assertEquals(expResult, result);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of setManagerType method, of class Manager.
     */
    @Test
    public void testSetManagerType() {
        System.out.println("setManagerType");
        int manager_type = 0;
        Manager instance = new Manager();
        instance.setManagerType(manager_type);

        fail("The test case is a prototype.");
    }
    
}
