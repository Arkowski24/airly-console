import java.time.LocalDateTime;
import java.util.Date;

public class MeasurementsWithInterval {
    public final Date fromDateTime;
    public final Date tillDateTime;

    public MeasurementsWithInterval(Date fromDateTime, Date tillDateTime) {
        this.fromDateTime = fromDateTime;
        this.tillDateTime = tillDateTime;
    }
}
