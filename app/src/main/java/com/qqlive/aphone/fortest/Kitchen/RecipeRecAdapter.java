package com.qqlive.aphone.fortest.Kitchen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.circleImageView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecAdapter extends RecyclerView.Adapter<RecipeRecAdapter.KitChenHomeViewHolder> {

    private static final String TAG = RecipeRecAdapter.class.getSimpleName();

    //private static int viewHolderCount;
    private static int position = 0;
    private Context mContext;
    private final ListItemClickListener mOnClickListener;
    private boolean mKitchenBannerAndEntrance;
    private static final int VIEW_TYPE_ENTRANCE = 0;
    private static final int VIEW_TYPE_RECIPE_LIST = 1;
    private int mNumberItems;


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RecipeRecAdapter(ListItemClickListener listener , Context context,int number) {
        mContext = context;
      //  viewHolderCount = 0;
        mNumberItems = number;
        mOnClickListener =listener;
        mKitchenBannerAndEntrance = mContext.getResources().getBoolean(R.bool.use_today_layout);

    }

    @Override
    public KitChenHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdByRecomListItem;
        int g = getItemViewType(viewType);
        if (viewType == VIEW_TYPE_ENTRANCE) {
            layoutIdByRecomListItem = R.layout.recipe_recom_list_entrance;
        }else {
            layoutIdByRecomListItem = R.layout.recipe_recom_list;
        }
//        switch (viewType){
//
//            case VIEW_TYPE_ENTRANCE:
//
//                break;
//            case VIEW_TYPE_RECIPE_LIST:
//                layoutIdByRecomListItem = R.layout.recipe_recom_list;
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
//        }


        Log.i(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdByRecomListItem,parent,shouldAttachToParentImmediately);
        return new KitChenHomeViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (mKitchenBannerAndEntrance && position ==0) {
            return VIEW_TYPE_ENTRANCE;
        }else {
            return VIEW_TYPE_RECIPE_LIST;
        }
    }

    class KitChenHomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final Banner mFirstBanner;
        public final ImageView mRecipePicture;
        public final TextView mRecipeName;
        public final TextView mRecipeIntroduce;
        public final ImageView mRecipeRe;
        public final ImageView mMenuRe;
        public final ImageView mIngredientProcess;
        public final ImageView mPersonalTailor;
        public final circleImageView mRecipeChef;

        public KitChenHomeViewHolder(View itemView) {
            super(itemView);

            mFirstBanner = (Banner)itemView.findViewById(R.id.banner);
            mRecipePicture = (ImageView)itemView.findViewById(R.id.iv_picture_recipe_list);
            mRecipeName = (TextView)itemView.findViewById(R.id.tv_name_recipe_list);
            mRecipeIntroduce = (TextView)itemView.findViewById(R.id.tv_introduce_recipe_list);
            mRecipeChef = (circleImageView)itemView.findViewById(R.id.civ_chef_recipe_list);
            mRecipeRe = (ImageView)itemView.findViewById(R.id.recipe_icon);
            mMenuRe = (ImageView)itemView.findViewById(R.id.menu_icon);
            mIngredientProcess = (ImageView)itemView.findViewById(R.id.ingredient_process_icon);
            mPersonalTailor = (ImageView)itemView.findViewById(R.id.personal_tailor_icon);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v){
            position = getAdapterPosition();
            mOnClickListener.onListItemClick(getAdapterPosition());

        }
    }
    @Override
    public void onBindViewHolder(KitChenHomeViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_ENTRANCE:
                List<Integer> list=new ArrayList<>();
                list.add(R.drawable.banner_1);
                list.add(R.drawable.banner_2);
                list.add(R.drawable.banner_3);
                holder.mFirstBanner.setImages(list)
                        .setImageLoader(new GlideImageLoader())
                        .start();

            case VIEW_TYPE_RECIPE_LIST:
                 holder.mRecipePicture.setImageResource(R.drawable.huoguo_1);
                 holder.mRecipeChef.setImageResource(R.drawable.huoguo_1);
                 holder.mRecipeName.setText("火锅");
                 holder.mRecipeIntroduce.setText("hello");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }



    }

}
