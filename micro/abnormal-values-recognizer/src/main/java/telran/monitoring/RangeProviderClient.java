package telran.monitoring;

import org.w3c.dom.ranges.Range;

public interface RangeProviderClient {
Range getRange(long patientId);
}