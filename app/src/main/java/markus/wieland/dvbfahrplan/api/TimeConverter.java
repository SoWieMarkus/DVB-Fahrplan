package markus.wieland.dvbfahrplan.api;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeConverter {

    private static final String REGEX_PATTERN_STRING = "\\/Date\\((\\d*)(\\+|\\-)(\\d{2})(\\d{2})\\)\\/";
    private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX_PATTERN_STRING);

    private TimeConverter() {
    }

    public static LocalDateTime convertToLocalDateTime(@NonNull String time) {
        Matcher matcher = REGEX_PATTERN.matcher(time);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("String time: " + time + " does not match pattern " + REGEX_PATTERN_STRING);
        }

        long milliSeconds = Long.parseLong(matcher.group(1));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return LocalDateTime.of(year, month, day, hour, minute);

    }

    public static long getMinutesBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return ChronoUnit.MINUTES.between(dateTime1, dateTime2);
    }

    public static String getTimeAsSAP() {
        return "/Date(" + (System.currentTimeMillis() + 1000) + "-0000)/";
    }


}
