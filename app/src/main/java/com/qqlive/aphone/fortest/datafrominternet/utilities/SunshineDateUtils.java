package com.qqlive.aphone.fortest.datafrominternet.utilities;

import android.content.Context;
import android.text.format.DateUtils;

import com.qqlive.aphone.fortest.R;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public final class SunshineDateUtils {

    public final static long SECOND_IN_MILLIS = 1000;
    public final static long MINUTE_IN_MILLIS = SECOND_IN_MILLIS*60;
    public final static long HOUR_IN_MILLIS = MINUTE_IN_MILLIS*60;
    public final static long DAY_IN_MILLIS = HOUR_IN_MILLIS*24;


    public static long normalizeDate(long date){
        long retValNew = date / DAY_IN_MILLIS * DAY_IN_MILLIS;
        return retValNew;
    }

    public static long getLocalDateFromUTC(long utcDate){
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffset = timeZone.getOffset(utcDate);
        return utcDate - gmtOffset;

    }

    public static long getUTCDateFromLocal(long localDate){
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOFFset = timeZone.getOffset(localDate);
        return localDate + gmtOFFset;
    }

    public static long getDayNumber(long date){
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffset = timeZone.getOffset(date);
        return (date+gmtOffset) / DAY_IN_MILLIS;
    }

    public static String getReadableDateString(Context context,long timeInMillis) {
        int flags = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NO_YEAR
                | DateUtils.FORMAT_SHOW_WEEKDAY;
        return DateUtils.formatDateTime(context,timeInMillis,flags);
    }

    public static String getDayName(Context context,long dateInMillis) {
        long dayNumber = getDayNumber(dateInMillis);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());
        if (dayNumber == currentDayNumber){
            return context.getString(R.string.today);
        }else if (dayNumber ==currentDayNumber+1){
            return context.getString(R.string.tomorrow);
        }else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            return dateFormat.format(dateInMillis);
        }
    }

    public static String getFriendlyDateString(Context context,long dateInMillis,boolean showFullDate) {
        long localDate = getLocalDateFromUTC(dateInMillis);
        long dayNumber = getDayNumber(localDate);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());

        if (dayNumber ==currentDayNumber || showFullDate){
            String dayName = getDayName(context,localDate);
            String readableDate = getReadableDateString(context,localDate);
            if (dayNumber-currentDayNumber<2){
                String localizedDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(localizedDayName,dayName);
            }else {
                return readableDate;
            }
        }else if (dayNumber < currentDayNumber+7){
            return getDayName(context,localDate);
        }else {
            int flags = DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_NO_YEAR
                    | DateUtils.FORMAT_ABBREV_ALL
                    | DateUtils.FORMAT_SHOW_WEEKDAY;
            return DateUtils.formatDateTime(context,localDate,flags);
        }
    }
    public static boolean isDateNormalized(long millisSinceEpoch) {
        boolean isDateNormalized = false;
        if (millisSinceEpoch % DAY_IN_MILLIS == 0 ) {
            isDateNormalized = true;
        }
        return isDateNormalized;

    }


    public static long getNormalizedUtcDateForToday() {
        /*
         * this number represents the number of milliseconds that elapsed since january
         * 1st,1970 at midnight in the GMT time zone
         */
        long utcNowMillis = System.currentTimeMillis();
        /*
         *this timezone represents the device's current time zone,it provides us with a means
         * of acquiring the offset for local time from a utc time stamp
         */
        TimeZone currentTimeZone = TimeZone.getDefault();

        long gmtOffsetMillis = currentTimeZone.getOffset(utcNowMillis);

        long timeSinceEpochLocationTimeMillis = utcNowMillis + gmtOffsetMillis;

        long daySinceEpochLocal = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocationTimeMillis);

        long normalizedUtcMidnightMillis = TimeUnit.DAYS.toMillis(daySinceEpochLocal);

        return normalizedUtcMidnightMillis;




    }
}
