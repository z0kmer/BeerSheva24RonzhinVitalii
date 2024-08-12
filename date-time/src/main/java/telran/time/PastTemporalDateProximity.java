package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class PastTemporalDateProximity implements TemporalAdjuster{
//some encapsulation
//array of temporals supprting Day, Month, Year (Dates)

private Temporal[] temporals;

public PastTemporalDateProximity(Temporal[] temporals) {
    this.temporals = temporals;
}

    @Override
    public Temporal adjustInto(Temporal temporal) {
        //Auto-generated method stub
        //return the temporal for the encapsulated array that is a nearest in past
        return temporals == null || temporals.length == 0 ? null : findClosest(temporal);
    }

    private Temporal findClosest(Temporal temporal) {
        Arrays.sort(temporals, (t1, t2) -> Long.compare(
            t1.getLong(ChronoField.EPOCH_DAY), 
            t2.getLong(ChronoField.EPOCH_DAY)
        ));

        Temporal closest = null;
        int i = 0;
        while (i < temporals.length) {
            Temporal t = temporals[i];
            closest = t.getLong(ChronoField.EPOCH_DAY) < temporal.getLong(ChronoField.EPOCH_DAY) ? t : closest;
            i++;
        }

        return closest != null && temporal.getClass().equals(closest.getClass()) 
            ? closest 
            : temporal;
    }
}