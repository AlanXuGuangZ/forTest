package com.qqlive.aphone.fortest.WaitList;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.mydb.Contract;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder>{

    private Context mContext;
    private Cursor mCursor;
    private WaitlistClickListener mWaitlistClickListener;
    private static final String TAG = GuestListAdapter.class.getSimpleName();


    public GuestListAdapter(Context context, Cursor cursor,WaitlistClickListener listener ) {
        Log.i(TAG, "GuestListAdapter:");
        this.mContext = context ;
        mCursor = cursor;
        mWaitlistClickListener = listener;

    }



    public interface WaitlistClickListener {
        void onWaitlistClick(int clickWaitlist);
    }
    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item,parent,false);
        return  new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(Contract.WaitlistEntry.COLUMN_GUEST_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(Contract.WaitlistEntry._ID));
        int size = mCursor.getInt(mCursor.getColumnIndex(Contract.WaitlistEntry.COLUMN_PARTY_SIZE));
        holder.nameTextView.setText(name);
        holder.partySizeTextView.setText(String.valueOf(size));
        holder.itemView.setTag(id);//保存起来，要划掉的guestID


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }




    class GuestViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nameTextView;
        TextView partySizeTextView;
        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView)itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView)itemView.findViewById(R.id.party_size_text_view);
            itemView.setOnClickListener(this);
        }
        public void onClick(View view){
            Log.i(TAG, "onClick: ");
            mWaitlistClickListener.onWaitlistClick(getAdapterPosition());

        }
    }
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }else {

        }
    }
}
