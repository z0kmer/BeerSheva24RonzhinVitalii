package telran.time;

import java.util.Arrays;

public class FutureProximityAdjuster implements TimePointAdjuster{
    TimePoint [] timePoints;

    public FutureProximityAdjuster(TimePoint [] timePoints) {

        //copy a given array
        //sort copy and assign to the field timePoints
        //using Java standard Arrays class
        //repeated time points are possible
        this.timePoints = Arrays.copyOf(timePoints, timePoints.length);
        Arrays.sort(this.timePoints);
    }
    @Override
    public TimePoint adjust(TimePoint timePoint) {
        //Auto-generated method stub
        //returns a time point only in future (greater than a given time point) from the field timePoints
        //nearest to a given timePoint
        for (TimePoint tp : timePoints) {
            if (tp.compareTo(timePoint) > 0) {
                return tp;
            }
        }
        return null;
    }
}