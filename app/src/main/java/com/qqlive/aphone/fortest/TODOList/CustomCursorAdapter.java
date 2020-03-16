package com.qqlive.aphone.fortest.TODOList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.mydb.Contract;


public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.CustomViewHolder>{

    private Context mContext;

    private Cursor mCursor;


    public CustomCursorAdapter(Context context){
        this.mContext = context;


    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.task_layout,parent,false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        int id =mCursor.getInt(mCursor.getColumnIndex(Contract.TaskContentEntry._ID));
        String description = mCursor.getString(mCursor.getColumnIndex(Contract.TaskContentEntry.COLUMN_DESCRIPTION));
        int priority = mCursor.getInt(mCursor.getColumnIndex(Contract.TaskContentEntry.COLUMN_PRORITY));


        holder.itemView.setTag(id);
        holder.mDescriptionTextView.setText(description);

        String priorityString = "" + priority; //converts int to String
        holder.mPriorityTextView.setText(priorityString);

        //set color based on the priority
        GradientDrawable priorityCircle = (GradientDrawable)holder.mPriorityTextView.getBackground();

        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

    private int getPriorityColor(int priority) {
        int priorityColor = 0;
        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext,R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext,R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext,R.color.materialYellow);
                break;
            default:break;
        }
        return priorityColor;
    }


    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }

        return mCursor.getCount();
    }
    //更新cursor，如果数据发生了改变

    public Cursor swapCursor(Cursor cursor) {
        if (mCursor == cursor) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = cursor;
        if (cursor  != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView mDescriptionTextView ;
        TextView mPriorityTextView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            mDescriptionTextView = (TextView)itemView.findViewById(R.id.tv_taskDescription);
            mPriorityTextView = (TextView)itemView.findViewById(R.id.tv_taskPriority);
        }

    }


}
