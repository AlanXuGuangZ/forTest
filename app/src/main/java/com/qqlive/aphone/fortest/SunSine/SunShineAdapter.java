package com.qqlive.aphone.fortest.SunSine;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.datafrominternet.utilities.SunshineDateUtils;
import com.qqlive.aphone.fortest.datafrominternet.utilities.SunshineWeatherUtils;
import com.qqlive.aphone.fortest.mydb.Contract;

import org.w3c.dom.Text;


public class SunShineAdapter extends RecyclerView.Adapter<SunShineAdapter.ForcastAdapterViewHolder>{
    private Cursor mWeatherDataCursor;
    private boolean mUseTodayLayout;
    private Context mContext ;
    private String TAG = SunShineAdapter.class.getSimpleName()+"Sunshine";
    private forcastClickListener mforcastClickListener;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    public SunShineAdapter(forcastClickListener listener,Context context){


        mforcastClickListener = listener;
        mContext = context;
        mUseTodayLayout = mContext.getResources().getBoolean(R.bool.use_today_layout);

    }
    public interface forcastClickListener{
        void  onForcastClick(long clickedWeather);
    }

    public class ForcastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mDayTextView;
        public final TextView mWeatherDescriptionTextView;
        public final TextView mWeatherMaxTempTextView;
        public final TextView mWeatherMinTempTextView;
        public final ImageView mWeatherIdImageView;

        public ForcastAdapterViewHolder(View view) {
            super(view);
            mDayTextView = (TextView)view.findViewById(R.id.data);
            mWeatherDescriptionTextView = (TextView)view.findViewById(R.id.tv_weather_description);
            mWeatherMaxTempTextView = (TextView)view.findViewById(R.id.tv_maxTemp);
            mWeatherMinTempTextView = (TextView)view.findViewById(R.id.tv_minTemp);
            mWeatherIdImageView = (ImageView)view.findViewById(R.id.weather_icon);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mWeatherDataCursor.moveToPosition(adapterPosition);
            long weatherForDay = mWeatherDataCursor.getLong(mWeatherDataCursor.getColumnIndex(Contract.WeatherDataEntry.COLUMN_DATE));
            mforcastClickListener.onForcastClick(weatherForDay);
        }
    }
    @Override
    public ForcastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem;
        switch (viewType) {
            case VIEW_TYPE_TODAY:
                layoutIdForListItem = R.layout.forcast_list_item_today;
                break;
            case VIEW_TYPE_FUTURE_DAY:
                layoutIdForListItem = R.layout.forcast_list_item;
                break;
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);


            }
        Log.i(TAG, "onCreateViewHolder: viewType:"+viewType);
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
//        int backgroundColorForViewHolder = ToyColorUtils.getViewHolderBackgroundColorFromInstance(context,viewHolderCount);
//        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);
        return  new ForcastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForcastAdapterViewHolder holder, int position) {
        mWeatherDataCursor.moveToPosition(position);
        //获得时间
        long dateMillis = mWeatherDataCursor.getLong(mWeatherDataCursor.getColumnIndex(Contract.WeatherDataEntry.COLUMN_DATE));
        String dateString = SunshineDateUtils.getFriendlyDateString(mContext,dateMillis,false);
        //获得天气图片
        int weatherId = mWeatherDataCursor.getInt(mWeatherDataCursor.getColumnIndex(Contract.WeatherDataEntry.COLUMN_WEATHER_ID));
        int weatherImageId;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_FUTURE_DAY:
                weatherImageId = SunshineWeatherUtils.getSmallArtResourceIdForWeatherCondition(weatherId);
                break;
            case VIEW_TYPE_TODAY:
                weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
        Log.i(TAG, "onBindViewHolder: viewType"+viewType+"imageID:"+weatherImageId);

        //获得天气描述
        String description = SunshineWeatherUtils.getStringForWeatherCondition(mContext,weatherId);
        //获得天气温度范围
        double highInCelsius = mWeatherDataCursor.getDouble(mWeatherDataCursor.getColumnIndex(Contract.WeatherDataEntry.COLUMN_MAX_TEMP));
        double lowInCelsius = mWeatherDataCursor.getDouble(mWeatherDataCursor.getColumnIndex(Contract.WeatherDataEntry.COLUMN_MIN_TEMP));
        long roundHigh = Math.round(highInCelsius);
        long roundLow = Math.round(lowInCelsius);
        String minTemp = SunshineWeatherUtils.formatTemperature(mContext,roundLow);
        String maxTemp = SunshineWeatherUtils.formatTemperature(mContext,roundHigh);
        //设置数据
        holder.mWeatherIdImageView.setImageResource(weatherImageId);
        holder.mDayTextView.setText(dateString);
        holder.mWeatherMinTempTextView.setText(minTemp);
        holder.mWeatherMaxTempTextView.setText(maxTemp);
        holder.mWeatherDescriptionTextView.setText(description);

    }


    @Override
    public int getItemCount() {
        if (null == mWeatherDataCursor) return 0;
        Log.i(TAG, "getItemCount: "+mWeatherDataCursor.getCount());
        return mWeatherDataCursor.getCount();

    }
    @Override
    public int getItemViewType(int position) {
        if (mUseTodayLayout && position ==0) {
            return VIEW_TYPE_TODAY;
        }else {
            return VIEW_TYPE_FUTURE_DAY;
        }
    }

    public void setmWeatherData(Cursor weatherData) {
        mWeatherDataCursor = weatherData;
        notifyDataSetChanged();
    }
}
