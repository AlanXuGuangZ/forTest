package com.qqlive.aphone.fortest;


import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qqlive.aphone.fortest.SunSine.SunShineMainActivity;
import com.qqlive.aphone.fortest.WaitList.WaitListMainActivity;
import com.qqlive.aphone.fortest.fragment.BaseFragment;
import com.qqlive.aphone.fortest.fragment.RecipeRecommendFragment;
import com.qqlive.aphone.fortest.fragment.videoFragment;
import com.qqlive.aphone.fortest.mydb.MyDataBaseHelper;
import com.qqlive.aphone.fortest.visualizerpreferences.VisualizerMainActivity;


import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity implements TabLayout.OnTabClickListener {
    private final static String TAG = "MyActivity";
    private TabLayout mTabLayout;
    private tabView mTabview;
    private TabLayout mTopTabLayout;
    private ArrayList<TabItem> tabs;
    private ArrayList<TabItem> top_tabs;
    private ViewPager mViewPager;
    private ActionBar actionBar;
    BaseFragment fragment;
    private MyDataBaseHelper dbHelper;
    private static int newVersion = 1;
    private int isUserLoginFlag;
    private int isUserRegFlag;
    private String phone;
    private String password;
    private EditText phoneText;
    private EditText passwordText;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

//        findViewById(R.id.onClickOpenContentProviderButton).setOnClickListener(this);
//        findViewById(R.id.onClickGithubButton).setOnClickListener(this);
//        findViewById(R.id.onClickOpenToyButton).setOnClickListener(this);
//        findViewById(R.id.createYourOwn).setOnClickListener(this);


        initView();
        initData();
     //   initToyData();
        dbHelper = new MyDataBaseHelper(getApplicationContext());
        // getFragmentManager().findFragmentById(R.id.music_fragment).getView().findViewById(R.id.button_register).setOnClickListener(this);
        // getFragmentManager().findFragmentById(R.id.music_fragment).getView().findViewById(R.id.button_sign).setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    /*
    private void initToyData() {
        mToysListTextView = (TextView) findViewById(R.id.tv_toys_name);
        String[] toyNames = ToyBoy.getToyNames();
        try{
            for (String toyName : toyNames) {
                mToysListTextView.append(toyName + "\n\n\n");
            }
        }catch (Exception e){
            e.getStackTrace();
        }

    }
    */


    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.main);
        }
    }


    private void initData() {
        tabs = new ArrayList<>();
        top_tabs = new ArrayList<>();
        top_tabs.add(new TabItem(R.string.chef));
        top_tabs.add(new TabItem(R.string.recipe));
        top_tabs.add(new TabItem(R.string.process));
        top_tabs.add(new TabItem(R.string.customized));
        tabs.add(new TabItem(R.drawable.selector_tab_main, R.string.main, RecipeRecommendFragment.class));
        tabs.add(new TabItem(R.drawable.selector_tab_order, R.string.order, videoFragment.class));
//        tabs.add(new TabItem(R.drawable.selector_tab_moments, R.string.net, NetFragment.class));
//        tabs.add(new TabItem(R.drawable.selector_tab_profile, R.string.animation, AnimationFragment.class));
        mTabLayout.setCurrentTab(0);
        mTabLayout.initData(tabs, this);
        Log.i(TAG, "initData: " + mTabLayout.getChildAt(0));
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("ddfdf",String.valueOf(position));

                mTabLayout.setCurrentTab(position);
                actionBar.setTitle(tabs.get(position).lableResId);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /*
    public void sendMessage(View view){
        Intent intent = new Intent(this,DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        if("".equals(editText.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "sorry,不能为空", Toast.LENGTH_SHORT).show();

            Log.d(TAG, "sendMessage: ");
            return;
        }
        message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
    */
/*
    public void clickMusic(View view){
        Intent intent = new Intent(this,MusicActivity.class);
        startActivity(intent);

 }
 */
    @Override
    public void onTabClick(TabItem tabItem) {
        actionBar.setTitle(tabItem.lableResId);
        mViewPager.setCurrentItem(tabs.indexOf(tabItem));
        //Log.i(TAG, "onTabClick: " + tabs.indexOf(tabItem));
        /*
        for (int i = 0;i<mTabview.getChildCount();i++) {
            ImageView IV = (ImageView)mTabview.getChildAt(i);
            if (IV.getId()) {
                Log.i(TAG, "onTabClick: onclick" );
                mViewPager.setCurrentItem(i);
            }
        }
        */



    }


    public class FragAdapter extends FragmentPagerAdapter {


        public FragAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            try {
                return tabs.get(arg0).tagFragmentClz.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return tabs.size();
        }

    }

    /**
     * 使用安卓最简单的存储技术：SharedPreferences
     * 1,使用Activity类的getSharedPreferences方法获取到SharedPreferences对象，用来指定文件名和访问权限
     * 2,获取SharedPreferences.Editor对象，并使用对象的PutXXX方法用来保存key-value
     * 3.通过SharedPreferences.Editor对象的commit方法提交key-balue
     * by Marceil on 2017/2/21
     */
    public void Btn_save(View view) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("text", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
//            EditText editText = (EditText) findViewById(R.id.edit_message);
//            String text = editText.getText().toString();
//            editor.putString("editText", text);
            editor.commit();
            Toast.makeText(MyActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(MyActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
        }

    }

    public void Btn_echo(View view) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("text", MODE_PRIVATE);
            String text = sharedPreferences.getString("editText", "");
        //    EditText editText = (EditText) findViewById(R.id.edit_message);
         //   editText.setText(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册&登录模块
     */
    public void Btn_register(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //判断是否已经存在
        String sql = "select * from users";
        Cursor cursor = db.rawQuery(sql, null);
       // phoneText = (EditText) findViewById(R.id.user_phone);
       // passwordText = (EditText) findViewById(R.id.user_psw);

        if (phoneText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
            Toast.makeText(MyActivity.this, "输入有误，小心拿小圈圈捶你胸口哦:phone:" + phone + "password:" + password, Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                //建表的时候，第一列为id
                phone = cursor.getString(1);
                password = cursor.getString(2);
                isUserRegFlag = 0; //用户没有被注册
                if (phoneText.getText().toString().equals(phone)) {
                    Toast.makeText(MyActivity.this, "好倒霉，已经被抢先一步，换个号码试试", Toast.LENGTH_SHORT).show();
                    isUserRegFlag = 1;//用户已经注册
                    break;
                }

            }
        }


        if (isUserRegFlag != 1) {
            String sqlInsert = "insert into users(phone,password) values ('" + phoneText.getText().toString() + "','" + passwordText.getText().toString() + "')";
            Log.d(TAG, "用户输入的帐号和密码：" + phoneText.getText().toString() + "," + passwordText.getText().toString());
            db.execSQL(sqlInsert);
            Toast.makeText(MyActivity.this, "恭喜，注册成功", Toast.LENGTH_SHORT).show();
            isUserRegFlag = 0;

        }

    }

    public void Btn_sign(View view) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from users";
        Cursor cursor = db.rawQuery(sql, null);
       // phoneText = (EditText) findViewById(R.id.user_phone);
       // passwordText = (EditText) findViewById(R.id.user_psw);
        isUserLoginFlag = 0; //验证用户信息失败
        if (phoneText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
            Toast.makeText(MyActivity.this, "输入有误，小心拿小圈圈捶你胸口哦:phone:" + phone + "password:" + password, Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                phone = cursor.getString(1);
                password = cursor.getString(2);
                if (phoneText.getText().toString().equals(phone) && passwordText.getText().toString().equals(password)) {
                    Toast.makeText(MyActivity.this, "恭喜，登录成功", Toast.LENGTH_SHORT).show();
                    isUserLoginFlag = 1;
                    break;
                }
            }
        }
        if (isUserLoginFlag != 1) {
            Toast.makeText(MyActivity.this, "密码输入有误，小心拿小圈圈捶你胸口哦", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();

    }





    /*
    public void Btn_register(View view){
        EditText editText = (EditText)findViewById(R.id.user_phone);
        EditText editText2 = (EditText)findViewById(R.id.user_psw);
        String phone = editText.getText().toString();
        String password = editText2.getText().toString();

        if (phone.equals("")||password.equals("")){
            Toast.makeText(MyActivity.this,"输入有误，小心拿小圈圈捶你胸口哦:phone:"+phone+"password:"+password,Toast.LENGTH_SHORT).show();
        }else if(DataIsOwn(phone)){
            Toast.makeText(MyActivity.this,"好倒霉，已经被抢先一步，换个号码试试",Toast.LENGTH_SHORT).show();
        }else {
            insertData(phone,password);
            Log.d(TAG, "要加入的数据，phone:"+phone+"password:"+password);
            Toast.makeText(MyActivity.this,"恭喜，注册成功",Toast.LENGTH_SHORT).show();
        }
    }
    public void Btn_sign(View view){
        EditText editText = (EditText)findViewById(R.id.user_phone);
        EditText editText2 = (EditText)findViewById(R.id.user_psw);
        String phone = editText.getText().toString();
        String password = editText2.getText().toString();

        if (password.equals("")||phone.equals("")){
            Toast.makeText(MyActivity.this,"输入有误，小心拿小圈圈捶你胸口哦:phone"+phone+"password:"+password,Toast.LENGTH_SHORT).show();
        }else if (DataIsOwn(phone)){

            if (password != null &&password.equals(realPassword)){
                Toast.makeText(MyActivity.this,"恭喜，登录成功",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MyActivity.this,"密码输入有误，小心拿小圈圈捶你胸口哦",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 数据库SQLite技术
     * by Marceil on 2017/2/22
     */
    //创建数据库
    //插入数据
    /*
    public void insertData(String phone,String password){
        ArrayList<User> users = new ArrayList<User>();
        User user = new User(phone,password);
        users.add(user);
        mdbm.add(users);
    }

    //查询号码数据是否存在
    public boolean DataIsOwn(String string){
        List<User> users = mdbm.query();
        for (User user :users){
            if (string != null&&string.equals(user.phone)){
                realPassword = user.password;
                Log.i("DataIsOwn", "realPassword: " +user.password);
                isown = true ;
            }
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor =db.query("user",null,null,null,null,null,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            if (string != null ||string.equals(cursor.getString(cursor.getColumnIndex("phone"))));{
                position = cursor.getPosition();
                return true;
            }
        }

        return isown;
    }
    */

    /**
     * Intent
     * created on 2017/2/27 by Marceil
     */
    public void Btn_Github2(View view) {
        Uri location = Uri.parse("http://www.hao123.com");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        if (activities.size() > 0) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(MyActivity.this, "没有可用的应用", Toast.LENGTH_SHORT).show();
        }
    }

    public void Btn_intent(View view){
        Intent toyIntent = new Intent(MyActivity.this,WaitListMainActivity.class);
        startActivity(toyIntent);

    }
    public void Btn_sunshine(View view){
        Intent sunIntent = new Intent(MyActivity.this, SunShineMainActivity.class);
        startActivity(sunIntent);
    }
    public void Btn_Github(View view){
        Intent GHIntent = new Intent(MyActivity.this,VisualizerMainActivity.class);
        startActivity(GHIntent);
    }

}
