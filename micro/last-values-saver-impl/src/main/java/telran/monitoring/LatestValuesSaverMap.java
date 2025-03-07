package telran.monitoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import telran.monitoring.api.LatestValuesSaver;
import telran.monitoring.api.SensorData;

public class LatestValuesSaverMap implements LatestValuesSaver {
    private Map<Long, SensorData> latestValues = new HashMap<>();

    @Override
    public void addValue(SensorData sensorData) {
        latestValues.put(sensorData.patientId(), sensorData);
    }

    @Override
    public List<SensorData> getAllValues(long patientId) {
        return latestValues.values().stream()
                .filter(data -> data.patientId() == patientId)
                .collect(Collectors.toList());
    }

    @Override
    public SensorData getLastValue(long patientId) {
        return latestValues.get(patientId);
    }

    @Override
    public void clearValues(long patientId) {
        latestValues.remove(patientId);
    }

    @Override
    public void clearAndAddValue(long patientId, SensorData sensorData) {
        clearValues(patientId);
        addValue(sensorData);
    }
}
