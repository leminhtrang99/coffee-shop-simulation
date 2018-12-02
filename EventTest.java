

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class EventTest.
 *
 * Trang Le
 * Professor Xia
 * CS 150-02: Data Structures & Algorithms
 * v1.0 Sep 25, 2018
 */

public class EventTest
{
    /**
     * Default constructor for test class EventTest
     */
    public EventTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    @Test 
    public void compareEqualEventTest() {
        Event e1 = new Event(1, 21600,"arrival");
        Event e2 = new Event(2, 21600,"arrival");
        assertEquals(e1.compareTo(e2), 0);
    }
    
    @Test 
    public void compareEarlierEventTest() {
        Event e1 = new Event(1, 15000,"arrival");
        Event e2 = new Event(2, 21600,"arrival");
        assertEquals(e1.compareTo(e2), -1);
    }
    
    @Test 
    public void compareLaterEventTest() {
        Event e1 = new Event(1, 64139,"arrival");
        Event e2 = new Event(2, 21600,"arrival");
        assertEquals(e1.compareTo(e2), 1);
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}