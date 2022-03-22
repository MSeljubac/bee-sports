package io.beesports.utils;

import io.beesports.domain.consts.CalendarConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class CalendarUtils {

    private final CalendarConsts calendarConsts;

    @Autowired
    public CalendarUtils(CalendarConsts calendarConsts) {
        this.calendarConsts = calendarConsts;
    }

    public CalendarConsts getCalendarConsts() {
        return calendarConsts;
    }

    /**
     * Returns a {@link Calendar} variable set an amount (x) of days back or forward.
     *
     * @param calendar A calendar variable that needs setting time to <b>x</b> days back or forward.
     * @param x        A number of days to add/reduce from the calendar variable.
     * @return A {@link Calendar} variable set <b>x</b> days back or forward.
     */
    public Calendar addDays(Calendar calendar, int x) {
        calendar.add(Calendar.DAY_OF_YEAR, x);
        return calendar;
    }

    /**
     * Sets the time of a {@link Calendar} variable to the end of the day (23:59:59.999).
     *
     * @param calendar A calendar variable that needs setting time to the end of day.
     */
    private void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }


    /**
     * Sets the time of a {@link Calendar} variable to the start of the day (00:00:00.000).
     *
     * @param calendar A calendar variable that needs setting time to the start of day.
     */
    private void setTimeToStartOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


    public String getDateString(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

}
