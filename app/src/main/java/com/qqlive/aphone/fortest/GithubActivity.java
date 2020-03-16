package com.qqlive.aphone.fortest;

import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.qqlive.aphone.fortest.datafrominternet.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class GithubActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    EditText mSearchBoxEdiText;
    TextView mUrlDisplayTextView;
    TextView mSearchResultTextView;
    TextView mErrorMessageTextView;
    ProgressBar mLoadingIndictorProgressBar;
    private final static String TAG = "GithubActivity";

    private final static String URI_CALL_BACK = "uliCallBacks";
    private final static String Search_Result_CALL_BACK = "searchResult";

    private final static int GITHUB_SEARCH_LOADER = 11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        mSearchBoxEdiText = (EditText)findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView)findViewById(R.id.tv_url_display);
        mSearchResultTextView = (TextView)findViewById(R.id.tv_github_search_result_json);
        mErrorMessageTextView = (TextView)findViewById(R.id.tv_error_message_display);
        mLoadingIndictorProgressBar = (ProgressBar)findViewById(R.id.pd_loading_indicate);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(URI_CALL_BACK) && savedInstanceState.containsKey(Search_Result_CALL_BACK)) {
                mUrlDisplayTextView.setText(savedInstanceState.getString(URI_CALL_BACK));
                mSearchResultTextView.setText(savedInstanceState.getString(Search_Result_CALL_BACK));
            }
        }
        getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER,null,this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState){
        super.onSaveInstanceState(savedState);
        String urlDisplayTextViewContents = mUrlDisplayTextView.getText().toString();
        savedState.putString(URI_CALL_BACK,urlDisplayTextViewContents);
        String searchResultDisplayTextViewContents = mSearchResultTextView.getText().toString();
        savedState.putString(Search_Result_CALL_BACK,searchResultDisplayTextViewContents);
    }

    public void makeGithubSearchQuery(){
        Log.i(TAG, "makeGithubSearchQuery: ");
        String githubQuery = mSearchBoxEdiText.getText().toString();
        if (TextUtils.isEmpty(githubQuery)) {
            mUrlDisplayTextView.setText("No query entered, nothing to search for.");
            return;
        }
        URL githubSearchUrl = NetworkUtils.buildGithubUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());
        Bundle queryBundle = new Bundle();
        queryBundle.putString(URI_CALL_BACK,githubSearchUrl.toString());
//        String githubSearchResults = null;
//        new GithubQueryTask().execute(githubSearchUrl);
//        try {
//            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearchUrl);
//            mSearchResultTextView.setText(githubSearchResults);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(GITHUB_SEARCH_LOADER,queryBundle,this);
        }else {
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER,queryBundle,this);
        }
    }

    public void showJsonDataView() {
        Log.i(TAG, "showJsonDataView: ");
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mSearchResultTextView.setVisibility(View.VISIBLE);
    }
    public void showErrorMessageView() {
        Log.i(TAG, "showErrorMessageView: ");
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mSearchResultTextView.setVisibility(View.INVISIBLE);
    }

    /**
     * 异步加载器，请求网络数据：
     * 消除僵尸Activity
     * 防止后台线程出现重复
     * 将处理结果告诉当前有效的Activity
     * @param id
     * @param args
     * @return
     */

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            String mGithubJson;
            @Override
            public void onStartLoading(){
                Log.i(TAG, "onStartLoading: ");
                if (args == null) {
                    return;
                }
                if (mGithubJson != null) {
                    Log.i(TAG, "onStartLoading: mGithubJson"+mGithubJson);
                    deliverResult(mGithubJson);
                }else {
                    mLoadingIndictorProgressBar.setVisibility(View.VISIBLE);
                    Log.i(TAG, "onStartLoading: mGithubJson:null");
                    forceLoad();
                }

            }
            @Override
            public String loadInBackground() {
                Log.i(TAG, "loadInBackground: ");
                String searchQueryUrlStr = args.getString(URI_CALL_BACK);
                if (searchQueryUrlStr == null || TextUtils.isEmpty(searchQueryUrlStr)) {
                    return null ;
                }
                try {
                    URL githunUrl = new URL(searchQueryUrlStr);
                    Log.i(TAG, "loadInBackground: getResponseFromHttpUrl"+githunUrl);
                    return  NetworkUtils.getResponseFromHttpUrl(githunUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            public void deliverResult(String githubJson){
                mGithubJson = githubJson;
                super.deliverResult(githubJson);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.i(TAG, "onLoadFinished: ");
        mLoadingIndictorProgressBar.setVisibility(View.INVISIBLE);
        if (data != null && !data.equals("")){
            Log.i(TAG, "onLoadFinished:data= "+data);
            showJsonDataView();
            mSearchResultTextView.setText(data);
        }else {
            showErrorMessageView();
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.i(TAG, "onLoaderReset: ");

    }

    /**
     * 内部类，异步实现网络请求
     */
//    public class GithubQueryTask extends AsyncTask<URL,Void,String>{
//
//
//        @Override
//        protected String doInBackground(URL... urls) {
//            URL searchUrl = urls[0];
//            String githubSearchResults = null;
//            Log.i(TAG, "doInBackground: AsyncTask内部类，处理网络线程");
//            try {
//                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return  githubSearchResults;
//        }
//        @Override
//        protected void onPreExecute(){
//            Log.i(TAG, "onPreExecute: ");
//            super.onPreExecute();
//            mLoadingIndictorProgressBar.setVisibility(View.VISIBLE);
//        }
//        @Override
//        protected void onPostExecute(String githubSearchResults) {
//            mLoadingIndictorProgressBar.setVisibility(View.INVISIBLE);
//            if (githubSearchResults != null && !githubSearchResults.equals("")){
//                Log.i(TAG, "onPostExecute: ");
//                showJsonDataView();
//                mSearchResultTextView.setText(githubSearchResults);
//            }else {
//                showErrorMessageView();
//            }
//
//
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu: 关联菜单Menu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Log.i(TAG, "onOptionsItemSelected: ");
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_search){
            Toast.makeText(GithubActivity.this,"Search click",Toast.LENGTH_SHORT).show();
            makeGithubSearchQuery();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
