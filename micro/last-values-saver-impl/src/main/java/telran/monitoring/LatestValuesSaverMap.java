package telran.monitoring;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import telran.monitoring.api.SensorData;
import telran.monitoring.logging.Logger;

public class LatestValuesSaverMap extends AbstractDataSaverLogger {
    private final HashMap<Long, List<SensorData>> history = new HashMap<>();

    protected LatestValuesSaverMap(Logger logger) {
        super(logger);
    }

    @Override
    public void addValue(SensorData sensorData) {
        history.computeIfAbsent(sensorData.patientId(), k -> new LinkedList<>()).add(sensorData);
        logger.log("info", "Added sensor data: " + sensorData);
    }

    @Override
    public List<SensorData> getAllValues(long patientId) {
        List<SensorData> values = history.getOrDefault(patientId, Collections.emptyList());
        logger.log("info", "Retrieved all values for patient " + patientId + ": " + values);
        return values;
    }

    @Override
    public SensorData getLastValue(long patientId) {
        List<SensorData> patientHistory = history.getOrDefault(patientId, List.of());
        SensorData res = null;
        if (!patientHistory.isEmpty()) {
            res = patientHistory.get(patientHistory.size() - 1);
        }
        logger.log("info", "Retrieved last value for patient " + patientId + ": " + res);
        return res;
    }

    @Override
    public void clearValues(long patientId) {
        List<SensorData> patientHistory = history.get(patientId);
        if (patientHistory != null) {
            patientHistory.clear();
            logger.log("info", "Cleared all values for patient " + patientId);
        } else {
            logger.log("warning", "No history found for patient " + patientId + " to clear");
        }
    }

    @Override
    public void clearAndAddValue(long patientId, SensorData sensorData) {
        List<SensorData> patientHistory = history.computeIfAbsent(patientId, k -> new LinkedList<>());
        patientHistory.clear();
        patientHistory.add(sensorData);
        logger.log("info", "Cleared and added sensor data for patient " + patientId + ": " + sensorData);
    }
}
