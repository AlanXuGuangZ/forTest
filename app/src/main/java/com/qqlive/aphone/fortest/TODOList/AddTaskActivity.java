package com.qqlive.aphone.fortest.TODOList;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.mydb.Contract;
import com.qqlive.aphone.fortest.mydb.TaskDataBaseHelper;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = AddTaskActivity.class.getSimpleName();

    private Toast mToast;

    private EditText mTaskDescriptionEditText;

    private SQLiteDatabase db;

    private Button mAddButton;

    private int mPriority = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        TaskDataBaseHelper dbHelper = new TaskDataBaseHelper(this);
        db = dbHelper.getWritableDatabase();

        mTaskDescriptionEditText = (EditText)findViewById(R.id.editTextTaskDescription);
        findViewById(R.id.btn_add_to_tasks).setOnClickListener(this);
        findViewById(R.id.radButton1).setOnClickListener(this);
        findViewById(R.id.radButton2).setOnClickListener(this);
        findViewById(R.id.radButton3).setOnClickListener(this);

    }

    public void addTaskDescription(){


        ContentValues contentValues = new ContentValues();
        if (mTaskDescriptionEditText.getText().toString().isEmpty() || mPriority == 0){
            if (mToast != null ){
                mToast.cancel();
            }
            mToast = Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT);
            mToast.show();
        }else {
            contentValues.put(Contract.TaskContentEntry.COLUMN_DESCRIPTION,mTaskDescriptionEditText.getText().toString());
            contentValues.put(Contract.TaskContentEntry.COLUMN_PRORITY,mPriority);

            Uri uri = getContentResolver().insert(Contract.TaskContentEntry.CONTENT_URI,contentValues);

            if(uri != null) {
                Toast.makeText(getBaseContext(),uri.toString(),Toast.LENGTH_LONG).show();
            }
            finish();
//            try {
//                db.endTransaction();
//                db.insert(Contract.TaskContentEntry.TABLE_NAME,null,contentValues);
//                db.setTransactionSuccessful();
//            }catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                if (mToast != null) {
//                    mToast.cancel();
//                }
//                mToast = Toast.makeText(this,"添加成功"+mPriority,Toast.LENGTH_SHORT);
//                mToast.show();
//                mTaskDescriptionEditText.setText(null);
//
//            }
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radButton1:
                mPriority = 1 ;
                break;
            case R.id.radButton2:
                mPriority = 2;
                break;
            case R.id.radButton3:
                mPriority = 3;
                break;
            case R.id.btn_add_to_tasks:
                addTaskDescription();
                break;
            default:
                mPriority = 0;

        }
    }
}
