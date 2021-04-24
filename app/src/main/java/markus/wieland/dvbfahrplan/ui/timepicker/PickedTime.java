package markus.wieland.dvbfahrplan.ui.timepicker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PickedTime {

    private final LocalDateTime localDateTime;
    private final boolean isArrival;

    public PickedTime(LocalDateTime localDateTime, boolean isArrival) {
        this.localDateTime = localDateTime;
        this.isArrival = isArrival;
    }

    public PickedTime() {
        this.localDateTime = LocalDateTime.now();
        this.isArrival = false;
    }

    public int getYear() {
        return localDateTime.getYear();
    }

    public int getMonth() {
        return localDateTime.getMonth().getValue() - 1;
    }

    public int getDayOfMonth() {
        return localDateTime.getDayOfMonth();
    }

    public int getMinute() {
        return localDateTime.getMinute();
    }

    public int getHour() {
        return localDateTime.getHour();
    }

    public String getLocalDateTimeAsString() {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME) + "Z";
    }

    public boolean isArrival() {
        return isArrival;
    }
}
