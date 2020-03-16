package com.qqlive.aphone.fortest.mydb;

import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.widget.EditText;

import com.qqlive.aphone.fortest.MyActivity;
import com.qqlive.aphone.fortest.R;

/**
 * Created by Marceil on 2017/2/22.
 */

public class MySharePreferencesManager extends MyActivity{


    /**
     *
     */
    public void saveSharePreferencesPerInfo(){
        try {
            //保存手机号码
            SharedPreferences sharedPreferences = getSharedPreferences("text",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
//            EditText editText_phone = (EditText)findViewById(R.id.user_phone);
      //      String phone = editText_phone.getText().toString();
       //     editor.putString("phone",phone);
            //保存密码
 //           EditText editText_psw = (EditText)findViewById(R.id.user_psw);
      //      String psw = editText_psw.getText().toString();
        //    editor.putString("password",psw);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     *
     *
     */
    public void echoSharePreferencesPerInf(){
        try {
            //写入手机号码
            SharedPreferences sharedPreferences = getSharedPreferences("text",MODE_PRIVATE);
            String phoneNum = sharedPreferences.getString("phone","");
         //   EditText editText = (EditText)findViewById(R.id.user_phone);
            //editText.setText(phoneNum);
        }catch (Exception e){
            e.getStackTrace();
        }

    }
}
