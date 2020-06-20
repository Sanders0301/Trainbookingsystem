/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ui.base.ListTableModel;



public class CustomerPanelTest {
    
    public CustomerPanelTest() {
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
     * Test of getTableModel method, of class CustomerPanel.
     */
    @Test
    public void testGetTableModel() {
        System.out.println("getTableModel");
        CustomerPanel instance = new CustomerPanel();
        ListTableModel expResult = null;
        ListTableModel result = instance.getTableModel();
        assertEquals(expResult, result);
       
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColumnWeight method, of class CustomerPanel.
     */
    @Test
    public void testGetColumnWeight() {
        System.out.println("getColumnWeight");
        int columnCount = 0;
        CustomerPanel instance = new CustomerPanel();
        float[] expResult = null;
        float[] result = instance.getColumnWeight(columnCount);
        assertArrayEquals(expResult, result);
 
        fail("The test case is a prototype.");
    }

    /**
     * Test of onSearch method, of class CustomerPanel.
     */
    @Test
    public void testOnSearch() {
        System.out.println("onSearch");
        String key = "";
        CustomerPanel instance = new CustomerPanel();
        instance.onSearch(key);
 
        fail("The test case is a prototype.");
    }

    /**
     * Test of onInsert method, of class CustomerPanel.
     */
    @Test
    public void testOnInsert() {
        System.out.println("onInsert");
        CustomerPanel instance = new CustomerPanel();
        instance.onInsert();
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of onDelete method, of class CustomerPanel.
     */
    @Test
    public void testOnDelete() {
        System.out.println("onDelete");
        int[] selectedRows = null;
        CustomerPanel instance = new CustomerPanel();
        instance.onDelete(selectedRows);

        fail("The test case is a prototype.");
    }

    /**
     * Test of onUpdate method, of class CustomerPanel.
     */
    @Test
    public void testOnUpdate() {
        System.out.println("onUpdate");
        int selectedRow = 0;
        CustomerPanel instance = new CustomerPanel();
        instance.onUpdate(selectedRow);
 
        fail("The test case is a prototype.");
    }
    
}
