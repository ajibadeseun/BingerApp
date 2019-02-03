package com.example.bingerapp;

import android.support.v7.util.DiffUtil;



import java.util.List;

public class NewsUnderCategoryDiffCallback extends DiffUtil.Callback {
    private List<Movies> mOldNewsList;
    private List<Movies> mNewNewsList;

    public NewsUnderCategoryDiffCallback(List<Movies> mOldNewsList, List<Movies> mNewNewsList) {
        this.mOldNewsList = mOldNewsList;
        this.mNewNewsList = mNewNewsList;
    }

    @Override
    public int getOldListSize() {
        return mOldNewsList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewNewsList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldNewsList.get(oldItemPosition).getMovieTitle() .equals (mNewNewsList.get(newItemPosition).getMovieTitle()) ;

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Movies oldNewsList = mOldNewsList.get(oldItemPosition);

        Movies newNewsList = mNewNewsList.get(newItemPosition);

        return oldNewsList.getMovieTitle().equals(newNewsList.getMovieTitle());
    }

//    @Nullable
//    @Override
//    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//        // Implement method if you're going to use ItemAnimator
//        return super.getChangePayload(oldItemPosition, newItemPosition);
//    }
}
