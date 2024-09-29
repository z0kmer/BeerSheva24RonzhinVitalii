package telran.interview;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ConnectionPool {
    private final int size;
    private final LinkedHashMap<String, Connection> connections;
//TODO work out data structure
//Connection pool comprises of some number of connections
//It canot contains more than the predefined number of connection
//You should define some Connection Pool policy so that if number of connections 
//is going to exceed the limit a connection should be removed from the connection
//Policy should take in consideration the order of adding connections in pool
// and using connection

    public ConnectionPool(int size) {
        this.size = size;
        this.connections = new LinkedHashMap<>(size, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Connection> eldest) {
                return size() > ConnectionPool.this.size;
            }
        };
    }

    public void addConnection(Connection connection) {
        // if the connection already exists in the pool the IllegalStateException should be thrown
        if (connections.containsKey(connection.connectionId())) {
            throw new IllegalStateException("Connection already exists");
        }
        connections.put(connection.connectionId(), connection);
    }
    public Connection getConnection(String connectionId) {
        //If connection with a given ID doesn't exist the NoSuchElementException should be thrown
        Connection connection = connections.get(connectionId);
        if (connection == null) {
            throw new NoSuchElementException("Connection not found");
        }
        return connection;
    }
}