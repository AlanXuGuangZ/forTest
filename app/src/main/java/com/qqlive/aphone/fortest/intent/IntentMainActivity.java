package com.qqlive.aphone.fortest.intent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;

import java.net.URL;
import java.util.List;

public class IntentMainActivity extends AppCompatActivity {

    public static LocationManager locationManager;
    public Location location;
    public Double latitude,longitude;
    private final static String TAG = IntentMainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_main);

    }
    public void onClickOpenWebpageButton(View view) {
        openWebPage("https://www.udacity.com");
    }

    public void onClickOpenAddressButton(View view){
        String addressString = "1600 Amphitheatre Parkway, CA";

        Log.i(TAG, "onClickOpenAddressButton: "+longitude+"and"+latitude);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo")
                .path("0,0")
                .query(addressString);

        Uri addressUri = builder.build();

        showMap(addressUri);
    }

    public void onClickShareTextButton(View view) {
        String textThatYouWantTOShare =
                "sharing the coolest thing i've learned so far.you should"+
                        "check out Udacity and Google's Android Nanodegree!";
        shareText(textThatYouWantTOShare);

    }

    public void createYourOwn(View view) {

    }

    public void openWebPage(String uri) {
        Uri webUri = Uri.parse(uri);
        Intent webIntent = new Intent(Intent.ACTION_VIEW,webUri);
        PackageManager packageManager = getPackageManager();
//        List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent,0);
//        if (activities.size() > 0){
//            startActivity(webIntent);
//        }else {
//            Toast.makeText(this, "没有可用的应用", Toast.LENGTH_SHORT).show();
//        }
        if (webIntent.resolveActivity(packageManager)!=null){
            startActivity(webIntent);
        }else {
            Toast.makeText(this, "没有可用的应用", Toast.LENGTH_SHORT).show();
        }

    }

    public void showMap(Uri geoLocation) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        mapIntent.setData(geoLocation);
        if (mapIntent.resolveActivity(getPackageManager())!=null){
            startActivity(mapIntent);
        }
    }

    public void shareText (String textToShare) {
        String mimeType = "text/plain";
        String title = "Learning how to share";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }

}
