package telran.interview;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ConnectionPool {

//Connection pool comprises of some number of connections
//It canot contains more than the predefined number of connection
//You should define some Connection Pool policy so that if number of connections 
//is going to exceed the limit a connection should be removed from the connection
//Policy should take in consideration the order of adding connections in pool
// and using connection

private final int maxSize;
private LinkedHashMap<String, Connection> connectionsMap;
public ConnectionPool(int size) {
    this.maxSize = size;
    connectionsMap = new LinkedHashMap<>(size * 2, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Connection> eldestEntry){
            return size() > maxSize;
        }
    };
}
public void addConnection(Connection connection) {
    String id = connection.connectionId();
    if(connectionsMap.containsKey(id)) {
        throw new IllegalStateException();
    }
    connectionsMap.put(id, connection);
}
public Connection getConnection(String connectionId) {
    if (!connectionsMap.containsKey(connectionId)) {
        throw new NoSuchElementException();
    }

    return connectionsMap.get(connectionId);
}
}