package com.qqlive.aphone.fortest.boardingpass;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.boardingpass.utilities.FakeDataUtils;
import com.qqlive.aphone.fortest.databinding.ActivityBoardPassMainBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BoardPassMainActivity extends AppCompatActivity {
    private static final String TAG = BoardPassMainActivity.class.getSimpleName()+"UI";
    ActivityBoardPassMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_board_pass_main);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_board_pass_main);
        BoardingPassInfo fakeBoardingInfo = FakeDataUtils.generateFakeBoardingPassInfo();
        displayBoardingPassInfo(fakeBoardingInfo);

    }

    private void displayBoardingPassInfo(BoardingPassInfo info) {

        mainBinding.textViewPassengerName.setText(info.passengerName);
        mainBinding.textViewOriginAirport.setText(info.originCode);
        mainBinding.textViewFlightCode.setText(info.flightCode);
        mainBinding.textViewDestinationAirport.setText(info.destCode);

        mainBinding.textViewSeat.setText(info.seatNumber);
        mainBinding.textViewGate.setText(info.departureGate);
        mainBinding.textViewTerminal.setText(info.departureTerminal);

        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault());
        String boardingTime = format.format(info.boardingTime);
        String departureTime = format.format(info.departureTime);
        String arrivalTime = format.format(info.arrivalTime);

        Log.i(TAG, "displayBoardingPassInfo: ************"+boardingTime+"*********info.boardingTime:"+info.boardingTime);

        mainBinding.textViewBoardingTime.setText(boardingTime);
        mainBinding.textViewDepartureTime.setText(departureTime);
        mainBinding.textViewArrivalTime.setText(arrivalTime);

        long totalMinutesUntilBoarding = info.getMinutesUntilBoarding();
        long hoursUntilBoarding = TimeUnit.MINUTES.toHours(totalMinutesUntilBoarding);
        long minutesLessHoursUntilBoarding =
                totalMinutesUntilBoarding - TimeUnit.HOURS.toMinutes(hoursUntilBoarding);
        String hourAndMinutesUntiBoarding = getString(R.string.countDownFormat,
                hoursUntilBoarding,
                minutesLessHoursUntilBoarding);

        mainBinding.textViewBoardingInCountdown.setText(hourAndMinutesUntiBoarding);




    }
}
