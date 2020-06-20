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



public class PassbyTest {
    
    public PassbyTest() {
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
     * Test of getTrain method, of class Passby.
     */
    @Test
    public void testGetTrain() {
        System.out.println("getTrain");
        Passby instance = new Passby();
        Train expResult = null;
        Train result = instance.getTrain();
        assertEquals(expResult, result);
     
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTrain method, of class Passby.
     */
    @Test
    public void testSetTrain() {
        System.out.println("setTrain");
        Train train = null;
        Passby instance = new Passby();
        instance.setTrain(train);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStationOrder method, of class Passby.
     */
    @Test
    public void testGetStationOrder() {
        System.out.println("getStationOrder");
        Passby instance = new Passby();
        int expResult = 0;
        int result = instance.getStationOrder();
        assertEquals(expResult, result);
      
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStationOrder method, of class Passby.
     */
    @Test
    public void testSetStationOrder() {
        System.out.println("setStationOrder");
        int station_order = 0;
        Passby instance = new Passby();
        instance.setStationOrder(station_order);
    
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDepartStation method, of class Passby.
     */
    @Test
    public void testGetDepartStation() {
        System.out.println("getDepartStation");
        Passby instance = new Passby();
        Station expResult = null;
        Station result = instance.getDepartStation();
        assertEquals(expResult, result);
     
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDepartStation method, of class Passby.
     */
    @Test
    public void testSetDepartStation() {
        System.out.println("setDepartStation");
        Station depart_station = null;
        Passby instance = new Passby();
        instance.setDepartStation(depart_station);
      
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArriveStation method, of class Passby.
     */
    @Test
    public void testGetArriveStation() {
        System.out.println("getArriveStation");
        Passby instance = new Passby();
        Station expResult = null;
        Station result = instance.getArriveStation();
        assertEquals(expResult, result);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of setArriveStation method, of class Passby.
     */
    @Test
    public void testSetArriveStation() {
        System.out.println("setArriveStation");
        Station arrive_station = null;
        Passby instance = new Passby();
        instance.setArriveStation(arrive_station);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistance method, of class Passby.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        Passby instance = new Passby();
        float expResult = 0.0F;
        float result = instance.getDistance();
        assertEquals(expResult, result, 0.0);
     
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDistance method, of class Passby.
     */
    @Test
    public void testSetDistance() {
        System.out.println("setDistance");
        float distance = 0.0F;
        Passby instance = new Passby();
        instance.setDistance(distance);
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStayTime method, of class Passby.
     */
    @Test
    public void testGetStayTime() {
        System.out.println("getStayTime");
        Passby instance = new Passby();
        int expResult = 0;
        int result = instance.getStayTime();
        assertEquals(expResult, result);
  
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStayTime method, of class Passby.
     */
    @Test
    public void testSetStayTime() {
        System.out.println("setStayTime");
        int stay_time = 0;
        Passby instance = new Passby();
        instance.setStayTime(stay_time);
   
        fail("The test case is a prototype.");
    }
    
}
