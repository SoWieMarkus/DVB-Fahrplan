package markus.wieland.dvbfahrplan.ui.timepicker;

import android.content.Context;
import android.text.format.Time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.helper.TimeConverter;

public class PickedTime {

    private final LocalDateTime localDateTime;
    private final boolean isArrival;
    private final boolean custom;

    public PickedTime(LocalDateTime localDateTime, boolean isArrival) {
        this.localDateTime = localDateTime;
        this.isArrival = isArrival;
        this.custom = true;
    }

    public PickedTime() {
        this.localDateTime = LocalDateTime.now();
        this.isArrival = false;
        this.custom = false;
    }

    public int getYear() {
        return localDateTime.getYear();
    }

    public int getMonth() {
        return localDateTime.getMonth().getValue();
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
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Europe/Berlin"));
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    public boolean isArrival() {
        return isArrival;
    }

    private String getLocalDateTimeAsStringToDisplay() {
        if (TimeConverter.isSameDay(LocalDateTime.now(), localDateTime))
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
    }

    public String toString(Context context) {
        String string = (isArrival ? context.getString(R.string.route_string_arrival) : context.getString(R.string.route_string_departure)) + " ";
        string += custom ? getLocalDateTimeAsStringToDisplay() : context.getString(R.string.now);
        return string;
    }

   public String getDateAsString(Context context){
        if (TimeConverter.isSameDay(localDateTime, LocalDateTime.now())) return context.getString(R.string.time_picker_today);
        if (TimeConverter.isSameYear(localDateTime, LocalDateTime.now())) return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM."));
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
   }
}
