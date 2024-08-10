package telran.time;

//import java.util.concurrent.TimeUnit;

public class TimePoint implements Comparable<TimePoint>{
    private float amount;
    private TimeUnit timeUnit;
    
    public TimePoint(float amount, telran.time.TimeUnit hour) {
        this.amount = amount;
        this.timeUnit = hour;
    }
    
    @Override
    public int compareTo(TimePoint arg0) {
        //Auto-generated method stub
        return Float.compare(this.convert(TimeUnit.SECOND).getAmount(), arg0.convert(TimeUnit.SECOND).getAmount());
    }

    public float getAmount(){
        return amount;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public boolean equals(Object obj) {
        //
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TimePoint timePoint = (TimePoint) obj;
        return Float.compare(timePoint.convert(this.timeUnit).getAmount(), this.amount) == 0;
    }
    public TimePoint convert(TimeUnit timeUnit2) {
        
        //returns new TimePoint object equaled to the "this" object but
        //with a gibven timeUnit
        float amountInSeconds = this.amount * this.timeUnit.getValueOfSeconds();
        float targetAmount = amountInSeconds / timeUnit2.getValueOfSeconds();
        return new TimePoint(targetAmount, timeUnit2);
    }
    public TimePoint with(TimePointAdjuster adjuster) {
        return adjuster.adjust(this);
    }

}