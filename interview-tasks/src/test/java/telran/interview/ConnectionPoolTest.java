package telran.interview;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionPoolTest {
    private static final int N_CONNECTIONS = 3;
    ConnectionPool connectionPool;
    Connection[] connections = {
            new Connection("Connection1"),
            new Connection("Connection2"),
            new Connection("Connection3"),
            new Connection("Connection4"),
    };

    @BeforeEach
    void setUp() {
        connectionPool = new ConnectionPool(N_CONNECTIONS);
        for (int i = 1; i <= N_CONNECTIONS; i++) {
            connectionPool.addConnection(connections[i - 1]);
        }
    }

    @Test
    void addAndDeletionTest() {
        connectionPool.addConnection(connections[connections.length - 1]);

        assertThrowsExactly(IllegalStateException.class,
                () -> connectionPool.addConnection(connections[1]));
        assertThrowsExactly(IllegalStateException.class,
                () -> connectionPool.addConnection(connections[2]));
        assertThrowsExactly(IllegalStateException.class,
                () -> connectionPool.addConnection(connections[3]));
        connectionPool.addConnection(connections[0]);
    }
    @Test
    void aceesOrderTest() {
        assertEquals(connections[0],connectionPool.getConnection("Connection1")) ;
        connectionPool.addConnection(connections[connections.length - 1]);
        connectionPool.getConnection("Connection1");
        connectionPool.getConnection("Connection3");
        connectionPool.getConnection("Connection4");
        assertThrowsExactly(NoSuchElementException.class,
        () -> connectionPool.getConnection("Connection2"));
    }

}