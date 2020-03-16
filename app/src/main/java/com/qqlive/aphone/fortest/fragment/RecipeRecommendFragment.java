package com.qqlive.aphone.fortest.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qqlive.aphone.fortest.ITabClickListener;
import com.qqlive.aphone.fortest.Kitchen.GlideImageLoader;
import com.qqlive.aphone.fortest.Kitchen.RecipeRecAdapter;
import com.qqlive.aphone.fortest.R;
import com.youth.banner.Banner;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class RecipeRecommendFragment extends BaseFragment implements RecipeRecAdapter.ListItemClickListener {

    private final static String TAG = RecipeRecommendFragment.class.getSimpleName() + "RecipeRecommendFragment" ;
    private Banner banner;
    private RecipeRecAdapter mRecipeAdapter;
    View view;
    private RecyclerView mRecipeRecycleView;
    private int NUM_LIST_ITEMS = 50;

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.fragment_recciperec, container, false);

        initView();
        return view;
    }


    private void initView() {

        Log.i(TAG, "initView: ");

        mRecipeRecycleView = (RecyclerView)view.findViewById(R.id.recycleview_recipe_recommend);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mRecipeRecycleView.setLayoutManager(layoutManager);
        mRecipeRecycleView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeRecAdapter(this,getActivity(),2);
        mRecipeRecycleView.setAdapter(mRecipeAdapter);



//        banner = view.findViewById(R.id.banne1r);
//        //本地图片数据（资源文件）
//        List<Integer> list=new ArrayList<>();
//        list.add(R.drawable.banner_1);
//        list.add(R.drawable.banner_2);
//        list.add(R.drawable.banner_3);
//
//
//
//        banner.setImages(list)
//                .setImageLoader(new GlideImageLoader())
//                .start();
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
