
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

/**
 * The test class LauncherTest.
 *
 * Trang Le
 * Professor Xia
 * CS 150-02: Data Structures & Algorithms
 * v1.0 Sep 25, 2018
 */

public class LauncherTest
{
    /**
     * Default constructor for test class LauncherTest
     */
    public LauncherTest()

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
    public void testConvertTimeToSecondAM() {
        String str = "06:00:05 PM";
        assertEquals(Launcher.convertTimeToSecond(str), 21605);
    }
    
    @Test 
    public void testConvertTimeToSecondMidnight() {
        String str = "00:00:00 AM";
        assertEquals(Launcher.convertTimeToSecond(str), 0);
    }
    
    @Test 
    public void testConvertTimeToSecondNoon() {
        String str = "12:00:00 PM";
        assertEquals(Launcher.convertTimeToSecond(str), 43200);
    }
    
    @Test 
    public void testConvertTimeToSecondPM() {
        String str = "18:00:00 PM";
        assertEquals(Launcher.convertTimeToSecond(str), 64800); 
        
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
