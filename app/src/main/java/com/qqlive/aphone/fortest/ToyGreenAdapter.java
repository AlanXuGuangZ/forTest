package com.qqlive.aphone.fortest;

import android.content.Context;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**缓存对将来适配器中被修改的视图的引用
 */

public class ToyGreenAdapter extends RecyclerView.Adapter<ToyGreenAdapter.NumberViewHolder>{

    private final static String TAG = ToyGreenAdapter.class.getSimpleName();

    private static int viewHolderCount;
    private static int position = 0;
    private int mNumberItems;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    public static int getMposition(){
        return position;
    }

    /**
     * constructor for ToyGreenAdapter that accepts a number of items to display and the
     * specification
     * @param numberOfItems number if items to display in list
     */
    public ToyGreenAdapter(int numberOfItems,ListItemClickListener listener){
        mNumberItems = numberOfItems;
        viewHolderCount = 0;
        mOnClickListener =listener;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item_toy;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,viewGroup,shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolder.viewHolderIndex.setText("ViewHolder index:"+viewHolderCount);

        int backgroundColorForViewHolder = ToyColorUtils.getViewHolderBackgroundColorFromInstance(context,viewHolderCount);
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        viewHolderCount++;
        Log.i(TAG, "onCreateViewHolder: number of viewHolders created:"+viewHolderCount);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: #"+position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView listItemNumberView;

        TextView viewHolderIndex;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listItemNumberView = (TextView)itemView.findViewById(R.id.tv_item_number_toy);
            viewHolderIndex = (TextView)itemView.findViewById(R.id.tv_view_view_holder_instance_toy);

            itemView.setOnClickListener(this);

        }
        void bind(int listIndex){
            listItemNumberView.setText(String.valueOf(listIndex));
        }
        @Override
        public void onClick(View v){
            position = getAdapterPosition();
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }

}
