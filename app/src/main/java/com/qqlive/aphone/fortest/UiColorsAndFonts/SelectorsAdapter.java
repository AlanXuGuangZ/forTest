package com.qqlive.aphone.fortest.UiColorsAndFonts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qqlive.aphone.fortest.R;


public class SelectorsAdapter extends RecyclerView.Adapter<SelectorsAdapter.SelectorViewHolder>{
    private Context mContext;
    private SelectorClickListener mListener;

    public SelectorsAdapter(Context context,SelectorClickListener listener) {
        mContext = context;
        mListener = listener;

    }
    interface SelectorClickListener {
        void onClickSelector(int position);
    }

    @Override
    public SelectorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.selector_list_item,parent,false);
        return new SelectorViewHolder(view);


    }

    @Override
    public void onBindViewHolder(SelectorViewHolder holder, int position) {

        holder.firstNameTextView.setText("11");
        holder.lastNameTextView.setText("11");


    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class SelectorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView firstNameTextView;
         TextView lastNameTextView;

        SelectorViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = (TextView)itemView.findViewById(R.id.tv_firstName);
            lastNameTextView = (TextView)itemView.findViewById(R.id.tv_lastName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClickSelector(getAdapterPosition());
        }
    }
}
