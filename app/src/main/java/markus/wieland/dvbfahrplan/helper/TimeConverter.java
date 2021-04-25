package markus.wieland.dvbfahrplan.helper;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeConverter {

    private static final String REGEX_PATTERN_STRING = "\\/Date\\((\\d*)(\\+|\\-)(\\d{2})(\\d{2})\\)\\/";
    private static final Pattern REGEX_PATTERN = Pattern.compile(REGEX_PATTERN_STRING);

    private TimeConverter() {
    }

    public static boolean isSameDay(LocalDateTime localDateTime, LocalDateTime localDateTime2){
        return localDateTime.getYear() == localDateTime2.getYear()
                && localDateTime.getMonth().getValue() == localDateTime2.getMonth().getValue()
                && localDateTime.getDayOfMonth() == localDateTime2.getDayOfMonth();
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

    public static boolean isSameYear(LocalDateTime localDateTime, LocalDateTime localDateTime2) {
        return localDateTime.getYear() == localDateTime2.getYear();
    }

    public static String getStringOfLocalDateWithDates(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatterWithDate = DateTimeFormatter.ofPattern("dd.MM., HH:mm");
        DateTimeFormatter formatterWithDateYear = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

        if (isSameDay(LocalDateTime.now(), localDateTime)) return localDateTime.format(formatter);
        if (isSameYear(LocalDateTime.now(), localDateTime)) return localDateTime.format(formatterWithDate);
        return localDateTime.format(formatterWithDateYear);

    }

}
