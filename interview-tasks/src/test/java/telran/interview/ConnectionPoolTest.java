package telran.interview;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

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
    
    // Adding a third connection should remove the eldest (conn1)
    pool.addConnection(conn3);
    assertThrows(NoSuchElementException.class, () -> pool.getConnection("1")); // conn1 should be removed
    assertEquals(conn2, pool.getConnection("2"));
    assertEquals(conn3, pool.getConnection("3"));
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