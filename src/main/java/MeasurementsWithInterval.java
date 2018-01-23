import java.time.LocalDateTime;
import java.util.Date;

public class MeasurementsWithInterval {
    public final Date fromDateTime;
    public final Date tillDateTime;
    public final Measurements measurements;

    public MeasurementsWithInterval(Date fromDateTime, Date tillDateTime, Measurements measurements) {
        this.fromDateTime = fromDateTime;
        this.tillDateTime = tillDateTime;
        this.measurements = measurements;
    }
}
