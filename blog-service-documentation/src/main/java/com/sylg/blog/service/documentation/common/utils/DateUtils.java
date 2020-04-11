package com.sylg.blog.service.documentation.common.utils;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Date utilities.
 *
 * @author 忆地球往事
 * @date 2020-04-11 11:28
 */
public class DateUtils {

    private DateUtils() {
    }

    /**
     * Gets current date.
     *
     * @return current date
     */
    @NonNull
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Converts from date into a calendar instance.
     *
     * @param date date instance must not be null
     * @return calendar instance
     */
    @NonNull
    public static Calendar convertTo(@NonNull Date date) {
        Assert.notNull(date, "Date must not be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Adds date.
     *
     * @param date     current date must not be null
     * @param time     time must not be less than 1
     * @param timeUnit time unit must not be null
     * @return added date
     */
    public static LocalDateTime add(@NonNull LocalDateTime date, long time, @NonNull TimeUnit timeUnit) {
        Assert.notNull(date, "Date must not be null");
        Assert.isTrue(time >= 0, "Addition time must not be less than 1");
        Assert.notNull(timeUnit, "Time unit must not be null");

        LocalDateTime result;

        int timeIntValue;

        if (time > Integer.MAX_VALUE) {
            timeIntValue = Integer.MAX_VALUE;
        } else {
            timeIntValue = Long.valueOf(time).intValue();
        }

        // Calc the expiry time
        switch (timeUnit) {
            case DAYS:
                result = date.plusDays(timeIntValue);
                break;
            case HOURS:
                result = date.plusHours(timeIntValue);
                break;
            case MINUTES:
                result = date.plusMinutes(timeIntValue);
                break;
            case SECONDS:
                result = date.plusSeconds(timeIntValue);
                break;
            case MILLISECONDS:
                result = date.plus(timeIntValue, ChronoUnit.MILLIS);
                break;
            default:
                result = date;
        }
        return result;
    }
}
