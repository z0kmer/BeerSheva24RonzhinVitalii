package telran.interview;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ConnectionPoolTest {
//should contain the tests reflecting your understanding what should be done
    @Test
    void addConnectionTest() {
        ConnectionPool pool = new ConnectionPool(2);
        Connection conn1 = new Connection("1");
        Connection conn2 = new Connection("2");
        Connection conn3 = new Connection("3");
    
        pool.addConnection(conn1);
        pool.addConnection(conn2);
        
        assertThrows(IllegalStateException.class, () -> pool.addConnection(conn1));
        assertThrows(IllegalStateException.class, () -> pool.addConnection(conn3));
    }

    @Test
    void getConnectionTest() {
        ConnectionPool pool = new ConnectionPool(2);
        Connection conn1 = new Connection("1");
        Connection conn2 = new Connection("2");

        pool.addConnection(conn1);
        pool.addConnection(conn2);

        assertEquals(conn1, pool.getConnection("1"));
        assertEquals(conn2, pool.getConnection("2"));
        assertThrows(NoSuchElementException.class, () -> pool.getConnection("3"));
    }
}