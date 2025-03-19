import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MaxwellContainerCC1Test {
    private MaxwellContainer container;

    @Before
    public void setUp() {
        container = new MaxwellContainer(100, 200);
        container.makeInvisible();
    }

    @Test
    public void testAddDemon() {
        container.addDemon(12);
        assertArrayEquals(new int[]{12}, container.demons());
        container.makeInvisible();
    }

    @Test
    public void testDelDemon() {
        container.addDemon(10);
        container.delDemon(10);
        assertArrayEquals(new int[]{}, container.demons());
        container.makeInvisible();
    }

    @Test
    public void testAddParticle() {
        container.addParticle("blue", false, 20, 40, 5, 5);
        assertEquals(1, container.particles().length);
        container.makeInvisible();
    }

    @Test
    public void testDelParticle() {
        container.addParticle("blue", false, 20, 40, 5, 5);
        container.delParticle("blue");
        assertEquals(0, container.particles().length);
        container.makeInvisible();
    }

    @Test
    public void testAddHole() {
        container.addHole(50, 50, 2);
        assertEquals(1, container.holes().length);
        container.makeInvisible();
    }

    @Test
    public void testStartSimulation() {
        container.addParticle("red", true, 50, 50, 10, 0);
        container.start(5);
        int[][] particles = container.particles();
        assertEquals(100, particles[0][0]);
        container.makeInvisible();
    }

    @Test
    public void testIsGoal() {
        container.addParticle("red", true, 150, 50, -10, 0);
        assertFalse(container.isGoal());
        container.start(10);
        assertTrue(container.isGoal());
        container.makeInvisible();
    }

    @Test
    public void testFinish() {
        container.addDemon(10);
        container.addParticle("red", true, 50, 50, 5, 5);
        container.addHole(20, 20, 3);
        container.finish();
        assertFalse(container.ok());
        container.makeInvisible();
    }
    
    @After
    public void tearDown() {
        container = null;
    }
}

