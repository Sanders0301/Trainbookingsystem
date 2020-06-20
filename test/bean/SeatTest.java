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


public class SeatTest {
    
    public SeatTest() {
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
     * Test of getSeatId method, of class Seat.
     */
    @Test
    public void testGetSeatId() {
        System.out.println("getSeatId");
        Seat instance = new Seat();
        int expResult = 0;
        int result = instance.getSeatId();
        assertEquals(expResult, result);
 
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSeatId method, of class Seat.
     */
    @Test
    public void testSetSeatId() {
        System.out.println("setSeatId");
        int seat_id = 0;
        Seat instance = new Seat();
        instance.setSeatId(seat_id);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTrain method, of class Seat.
     */
    @Test
    public void testGetTrain() {
        System.out.println("getTrain");
        Seat instance = new Seat();
        Train expResult = null;
        Train result = instance.getTrain();
        assertEquals(expResult, result);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTrain method, of class Seat.
     */
    @Test
    public void testSetTrain() {
        System.out.println("setTrain");
        Train train = null;
        Seat instance = new Seat();
        instance.setTrain(train);
     
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCarriageNum method, of class Seat.
     */
    @Test
    public void testGetCarriageNum() {
        System.out.println("getCarriageNum");
        Seat instance = new Seat();
        int expResult = 0;
        int result = instance.getCarriageNum();
        assertEquals(expResult, result);
     
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCarriageNum method, of class Seat.
     */
    @Test
    public void testSetCarriageNum() {
        System.out.println("setCarriageNum");
        int carriage_num = 0;
        Seat instance = new Seat();
        instance.setCarriageNum(carriage_num);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeatNum method, of class Seat.
     */
    @Test
    public void testGetSeatNum() {
        System.out.println("getSeatNum");
        Seat instance = new Seat();
        int expResult = 0;
        int result = instance.getSeatNum();
        assertEquals(expResult, result);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSeatNum method, of class Seat.
     */
    @Test
    public void testSetSeatNum() {
        System.out.println("setSeatNum");
        int seat_num = 0;
        Seat instance = new Seat();
        instance.setSeatNum(seat_num);
   
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeatType method, of class Seat.
     */
    @Test
    public void testGetSeatType() {
        System.out.println("getSeatType");
        Seat instance = new Seat();
        String expResult = "";
        String result = instance.getSeatType();
        assertEquals(expResult, result);
    
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSeatType method, of class Seat.
     */
    @Test
    public void testSetSeatType() {
        System.out.println("setSeatType");
        String seat_type = "";
        Seat instance = new Seat();
        instance.setSeatType(seat_type);
   
        fail("The test case is a prototype.");
    }
    
}
