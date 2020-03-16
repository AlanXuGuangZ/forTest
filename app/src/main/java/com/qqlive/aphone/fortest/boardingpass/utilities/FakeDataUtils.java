package com.qqlive.aphone.fortest.boardingpass.utilities;

import android.util.Log;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.boardingpass.BoardingPassInfo;

import java.text.ParseException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class FakeDataUtils {
    private static final String TAG = FakeDataUtils.class.getSimpleName()+"UI";

    public static BoardingPassInfo generateFakeBoardingPassInfo() {
        BoardingPassInfo bpi = new BoardingPassInfo();

        bpi.passengerName = "许永彪";
        bpi.flightCode = "UD 777";
        bpi.originCode = "深圳";
        bpi.destCode = "长沙";

        long now = System.currentTimeMillis();

        // Anything from 0 minutes up to (but not including) 30 minutes
        long randomMinutesUntilBoarding = (long) (Math.random() * 30);
        // Standard 40 minute boarding time
        long totalBoardingMinutes = 40;
        // Anything from 1 hours up to (but not including) 6 hours
        long randomFlightLengthHours = (long) (Math.random() * 5 + 1);

        long boardingMillis = now + minutesToMillis(randomMinutesUntilBoarding);
        long departure = boardingMillis + minutesToMillis(totalBoardingMinutes);
        long arrival = departure + hoursToMillis(randomFlightLengthHours);
        bpi.boardingTime = new Timestamp(boardingMillis);
        bpi.departureTime = new Timestamp(departure);
        bpi.arrivalTime = new Timestamp(arrival);
        bpi.departureTerminal = "3A";
        bpi.departureGate = "33C";
        bpi.seatNumber = "1A";
        bpi.barCodeImageResource = R.drawable.ic_plane;

        Log.i(TAG, "generateFakeBoardingPassInfo: *************boardingTime"+bpi.boardingTime+"boardingMillis:"+boardingMillis);

        return bpi;


    }

    private static long hoursToMillis(long randomFlightLengthHours) {
        return TimeUnit.HOURS.toMillis(randomFlightLengthHours);

    }

    private static long minutesToMillis(long randomMinutesUntilBoarding) {
        return TimeUnit.MINUTES.toMillis(randomMinutesUntilBoarding);
    }


}
