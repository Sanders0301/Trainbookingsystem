/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CustomerTest {
    
    public CustomerTest() {
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
     * Test of getName method, of class Customer.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Customer instance = new Customer();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
      
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Customer.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "testName";
        Customer instance = new Customer();
        instance.setName(name);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSex method, of class Customer.
     */
    @Test
    public void testGetSex() {
        System.out.println("getSex");
        Customer instance = new Customer();
        int expResult = 1;
        int result = instance.getSex();
        assertEquals(expResult, result);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSexString method, of class Customer.
     */
    @Test
    public void testGetSexString() {
        System.out.println("getSexString");
        Customer instance = new Customer();
        String expResult = "1";
        String result = instance.getSexString();
        assertEquals(expResult, result);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSex method, of class Customer.
     */
    @Test
    public void testSetSex() {
        System.out.println("setSex");
        int sex = 1;
        Customer instance = new Customer();
        instance.setSex(sex);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIdNum method, of class Customer.
     */
    @Test
    public void testGetIdNum() {
        System.out.println("getIdNum");
        Customer instance = new Customer();
        String expResult = "testNum";
        String result = instance.getIdNum();
        assertEquals(expResult, result);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIdNum method, of class Customer.
     */
    @Test
    public void testSetIdNum() {
        System.out.println("setIdNum");
        String id_num = "testNum";
        Customer instance = new Customer();
        instance.setIdNum(id_num);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTel method, of class Customer.
     */
    @Test
    public void testGetTel() {
        System.out.println("getTel");
        Customer instance = new Customer();
        String expResult = "123456";
        String result = instance.getTel();
        assertEquals(expResult, result);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTel method, of class Customer.
     */
    @Test
    public void testSetTel() {
        System.out.println("setTel");
        String tel = "123456";
        Customer instance = new Customer();
        instance.setTel(tel);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomerType method, of class Customer.
     */
    @Test
    public void testGetCustomerType() {
        System.out.println("getCustomerType");
        Customer instance = new Customer();
        int expResult = 2;
        int result = instance.getCustomerType();
        assertEquals(expResult, result);
       
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomerTypeString method, of class Customer.
     */
    @Test
    public void testGetCustomerTypeString() {
        System.out.println("getCustomerTypeString");
        Customer instance = new Customer();
        String expResult = "2";
        String result = instance.getCustomerTypeString();
        assertEquals(expResult, result);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCustomerType method, of class Customer.
     */
    @Test
    public void testSetCustomerType() {
        System.out.println("setCustomerType");
        int customer_type = 2;
        Customer instance = new Customer();
        instance.setCustomerType(customer_type);
       
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReg_time method, of class Customer.
     */
    @Test
    public void testGetReg_time() {
        System.out.println("getReg_time");
        Customer instance = new Customer();
        String dt = "20-06-2020";
        Date expResult = null;
        try {
            expResult = new SimpleDateFormat("dd-MM-yyyy").parse(dt);
        } catch (ParseException ex) {
            Logger.getLogger(CustomerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date result = instance.getReg_time();
        assertEquals(expResult, result);
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReg_time method, of class Customer.
     */
    @Test
    public void testSetReg_time() {
        System.out.println("setReg_time");
        String dt = "20-06-2020";
        Date reg_time = null;  
        try {
            reg_time = new SimpleDateFormat("dd-MM-yyyy").parse(dt);
        } catch (ParseException ex) {
            Logger.getLogger(CustomerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Customer instance = new Customer();
        instance.setReg_time(reg_time);
        
        fail("The test case is a prototype.");
    }
    
}
