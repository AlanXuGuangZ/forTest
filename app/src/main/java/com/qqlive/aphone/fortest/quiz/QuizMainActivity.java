package com.qqlive.aphone.fortest.quiz;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.mydb.Contract;

public class QuizMainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = QuizMainActivity.class.getSimpleName();
    private static final String URI_CALL_BACK = "wordContent";
    private static final String DEFINITION_CALL_BACK = "definitionContent";

    private Toast mToast;

    private Cursor mData;

    private int mCurrentState;

    private Button mButton;

    private TextView mWordTextView;

    private TextView mDefinitionTextView;

    private final int STATE_HIDDEN = 0;

    private final int STATE_SHOW = 1;

    private int mDefCol,mWordCOL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        mButton = (Button)findViewById(R.id.btn_next);
        mButton.setOnClickListener(this);
        mWordTextView = (TextView)findViewById(R.id.tv_view_word);
        mDefinitionTextView = (TextView)findViewById(R.id.tv_view_definition);
        /**
         * 保存数据
         */

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(URI_CALL_BACK) && savedInstanceState.containsKey(DEFINITION_CALL_BACK)) {
                mDefinitionTextView.setText(savedInstanceState.getString(URI_CALL_BACK));
                mWordTextView.setText(savedInstanceState.getString(DEFINITION_CALL_BACK));
            }
        }
        new WordFetchTask().execute();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (mData != null) {
            mData.close();
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedState){
        super.onSaveInstanceState(savedState);
        String wordDisplayTextViewContents = mWordTextView.getText().toString();
        savedState.putString(URI_CALL_BACK,wordDisplayTextViewContents);
        String definitionDisplayTextViewContents = mDefinitionTextView.getText().toString();
        savedState.putString(DEFINITION_CALL_BACK,definitionDisplayTextViewContents);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                switch (mCurrentState) {
                    case STATE_HIDDEN:
                        showDefinition();
                        break;
                    case STATE_SHOW:
                        nextWord();
                        break;
                }
        }
    }

    private void nextWord() {

        if (mData != null) {
            if (!mData.moveToNext()) {
                mData.moveToFirst();
            }
            if (mToast != null){
                mToast.cancel();
            }
            mToast = Toast.makeText(QuizMainActivity.this,"next word"+mData.getPosition(),Toast.LENGTH_SHORT);
            mToast.show();
            mDefinitionTextView.setVisibility(View.INVISIBLE);
            mButton.setText(getString(R.string.show_definition));
            mWordTextView.setText(mData.getString(mWordCOL));

            mCurrentState = STATE_HIDDEN;
        }else {
            Toast.makeText(QuizMainActivity.this,"没有可获得的数据",Toast.LENGTH_SHORT).show();
        }


    }

    private void showDefinition() {
        if (mData != null) {
            if (mToast != null){
                mToast.cancel();
            }
            mToast =Toast.makeText(QuizMainActivity.this,"show definition"+mData.getPosition(),Toast.LENGTH_SHORT);
            mToast.show();
            mButton.setText(getString(R.string.next_word));
            mDefinitionTextView.setText(mData.getString(mDefCol));
            mCurrentState = STATE_SHOW;
            mDefinitionTextView.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(QuizMainActivity.this,"没有可获得的数据",Toast.LENGTH_SHORT).show();
        }



    }
    public class WordFetchTask extends AsyncTask<Void,Void,Cursor> {



        @Override
        protected Cursor doInBackground(Void... params) {
            ContentResolver resolver = getContentResolver();
            try {
                Cursor cursor = resolver.query(Contract.DroidTermsExampleContract.CONTENT_URI,
                        null,null,null,null);
                return cursor;
            }catch (Exception e) {
                mToast = Toast.makeText(QuizMainActivity.this,"没有权限",Toast.LENGTH_SHORT);
                mToast.show();
                return null;
            }
        }
        @Override
        protected  void  onPostExecute (Cursor cursor) {
            super.onPostExecute(cursor);
            mData = cursor;

            try {
                mWordCOL = mData.getColumnIndex(Contract.DroidTermsExampleContract.COLUMN_WORD);
                mDefCol = mData.getColumnIndex(Contract.DroidTermsExampleContract.COLUMN_DEFINITION);
                Log.i(TAG, "onPostExecute: "+mWordCOL+"----------"+mDefCol);
                if (mData.moveToPosition(1)) {
                    Log.i(TAG, "onPostExecute: *******************"+mData.getPosition());
                    mWordTextView.setText(mData.getString(mWordCOL));
                }
            }catch (Exception e) {
                Toast.makeText(QuizMainActivity.this,"没有可获得的数据",Toast.LENGTH_SHORT).show();
            }

        }

    }

}
