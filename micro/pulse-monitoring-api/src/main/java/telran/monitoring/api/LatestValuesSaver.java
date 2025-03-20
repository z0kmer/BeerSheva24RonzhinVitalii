package telran.monitoring.api;

import java.util.List;

public interface LatestValuesSaver {
   void addValue(SensorData sensorData);
   List<SensorData> getAllValues(long patientId);
   SensorData getLastValue(long patientId);
   void clearValues(long patientId);
   void clearAndAddValue(long patientId, SensorData sensorData);

   // Фабричный метод для создания реализации LatestValuesSaver
   static LatestValuesSaver getLatestValuesSaver(String latestValuesSaverClassName) {
       try {
           Class<?> clazz = Class.forName(latestValuesSaverClassName);
           return (LatestValuesSaver) clazz.getDeclaredConstructor().newInstance();
       } catch (Exception e) {
           throw new RuntimeException("Failed to create LatestValuesSaver instance: " + e.getMessage(), e);
       }
   }
}
