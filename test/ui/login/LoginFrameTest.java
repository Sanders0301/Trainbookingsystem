/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LoginFrameTest {
    
    public LoginFrameTest() {
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
     * Test of width method, of class LoginFrame.
     */
    @Test
    public void testWidth() {
        System.out.println("width");
        LoginFrame instance = new LoginFrame();
        int expResult = 0;
        int result = instance.width();
        assertEquals(expResult, result);
     
        fail("The test case is a prototype.");
    }

    /**
     * Test of height method, of class LoginFrame.
     */
    @Test
    public void testHeight() {
        System.out.println("height");
        LoginFrame instance = new LoginFrame();
        int expResult = 0;
        int result = instance.height();
        assertEquals(expResult, result);
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class LoginFrame.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        LoginFrame.main(args);
    
        fail("The test case is a prototype.");
    }
    
}
