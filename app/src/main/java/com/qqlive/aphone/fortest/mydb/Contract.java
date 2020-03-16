package com.qqlive.aphone.fortest.mydb;

import android.bluetooth.BluetoothA2dp;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.qqlive.aphone.fortest.datafrominternet.utilities.SunshineDateUtils;

import java.security.PublicKey;


public class Contract {


    public static final class WaitlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "waitlist";
        public static final String COLUMN_GUEST_NAME = "guestName";
        public static final String COLUMN_PARTY_SIZE = "partySize";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }







    public static final class WeatherDataEntry implements BaseColumns {

        //create uri
        public static final String WEATHER_AUTHORITY = "com.qqlive.aphone.fortest";
        public static final Uri WEATHER_BASE_CONTENT_URI = Uri.parse("content://"+WEATHER_AUTHORITY);
        public static final String WEATHER_PATH = "WeatherData";
        public static final Uri CONTENT_URI =
                WEATHER_BASE_CONTENT_URI.buildUpon().appendPath(WEATHER_PATH).build();




        public static final String TABLE_NAME = "WeatherData";

        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_WEATHER_ID = "weather_id";
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";

        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_HUMIDITY = "humidity";

        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_WIND_SPEED = "wind";

        public static final String COLUMN_DEGREES = "degrees";
        private static final String TAG = Contract.class.getSimpleName();


        public static String getSqlSelectForTodayOnwards() {
            long normalizedUtcNow = SunshineDateUtils.normalizeDate(System.currentTimeMillis());
            return WeatherDataEntry.COLUMN_DATE + ">=" + normalizedUtcNow;
        }

        public static Uri buildWeatherUriWithDate(long date) {
            Uri dateUri = CONTENT_URI.buildUpon().appendPath(Long.toString(date))
                    .build();
            return dateUri;
        }

       // public static final String COLUMN_WEATHER_DATA = "weatherData";
       // public static final String COLUMN_TIMESTAMP = "timestamp";
    }
    public static final class DroidTermsExampleContract implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.example.udacity.droidtermsexample";
        /**
         * This is the {@link Uri} on which all other DroidTermsExample Uris are built.
         */
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        /**
         * The path for terms
         */
        public static final String PATH_TERMS = "terms";

        /**
         * This is the {@link Uri} used to get a full list of terms and definitions.
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TERMS).build();


        /**
         * This is a String type that denotes a Uri references a list or directory.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TERMS;

        /**
         * This is a String type that denotes a Uri references a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TERMS;


        // Declaring all these as constants makes code a lot more readable.
        // It also looks a more like SQL.

        /**
         * This is the version of the database for {@link android.database.sqlite.SQLiteOpenHelper}.
         */
        public static final int DATABASE_VERSION = 1;

        /**
         * This is the name of the SQL table for terms.
         */
        public static final String TERMS_TABLE = "term_entries";
        /**
         * This is the name of the SQL database for terms.
         */
        public static final String DATABASE_NAME = "terms";

        /**
         * This is the column name in the SQLiteDatabase for the word.
         */
        public static final String COLUMN_WORD = "word";
        /**
         * This is the column name in the SQLiteDatabase for the definition.
         */
        public static final String COLUMN_DEFINITION = "definition";

        /**
         * This is an array containing all the column headers in the terms table.
         */
        public static final String[] COLUMNS =
                {_ID, COLUMN_WORD, COLUMN_DEFINITION};

        /**
         * This is the index of the ID in the terms table
         */
        public static final int COLUMN_INDEX_ID = 0;
        /**
         * This is the index of the word in the terms table
         */
        public static final int COLUMN_INDEX_WORD = 1;
        /**
         * This is the index of the definition in the terms table
         */
        public static final int COLUMN_INDEX_DEFINITION = 2;

        /**
         * This method creates a {@link Uri} for a single term, referenced by id.
         * @param id The id of the term.
         * @return The Uri with the appended id.
         */
        public static Uri buildTermUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }
    public static final class TaskContentEntry implements BaseColumns {

        public static final String AUTHORITY = "com.qqlive.aphone.fortest_task";

        // The base content URI = "content://" + <authority>
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

        // Define the possible paths for accessing data in this contract
        // This is the path for the "tasks" directory
        public static final String PATH_TASKS = "tasks";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();




        /*

         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    description     |    priority   |
         - - - - - - - - - - - - - - - - - - - - - -
        |  1   |  Complete lesson   |       1       |
         - - - - - - - - - - - - - - - - - - - - - -
        |  2   |    Go shopping     |       3       |
         - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   Learn guitar     |       2       |
         - - - - - - - - - - - - - - - - - - - - - -
         */
        public static final String TABLE_NAME = "tasks";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_PRORITY = "priority";

    }
}
