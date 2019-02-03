package com.example.bingerapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AiringTodayAdapter extends RecyclerView.Adapter<AiringTodayAdapter.ViewHolder> {
   private List<Movies> moviesList;
   private Context mContext;
   private boolean isLikeBtnClicked = false;

    public AiringTodayAdapter(List<Movies> moviesList, Context mContext) {
        this.moviesList = moviesList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_tv_show,viewGroup,false);

        return new AiringTodayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

      Movies movies = moviesList.get(i);
      String videoImageUrl = movies.getMovieImageUrl();

      if(videoImageUrl != null){
//          Glide.with(mContext)
//                  .load(videoImageUrl)
//                  .into(viewHolder.movieImage);

          Picasso.with(mContext)
                  .load(videoImageUrl)
                  .placeholder(R.drawable.placeholder_img)
                  .error(R.drawable.error_img)
                  .into(viewHolder.movieImage);
      }


      viewHolder.videoTitle.setText(movies.getMovieTitle());

      viewHolder.videoDescription.setText(movies.getMovieDescription());

      viewHolder.videoProductionYear.setText(movies.getMovieProductionYear().substring(0,4));

      String movieRating = String.valueOf(movies.getMovieRating());

      viewHolder.videoRating.setText(movieRating);


      viewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(isLikeBtnClicked){
                  isLikeBtnClicked = false;
                  viewHolder.likeBtn.setImageResource(R.drawable.ic_favorite_holo);
              }
              else{
                  isLikeBtnClicked = true;
                  viewHolder.likeBtn.setImageResource(R.drawable.ic_favorite);
              }
          }
      });








    }

    public void updateNewsListItems(List<Movies> movies) {
        final NewsUnderCategoryDiffCallback diffCallback = new NewsUnderCategoryDiffCallback(this.moviesList, movies);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

//        this.newsDetailsMerges.clear();
        this.moviesList.addAll(movies);
        diffResult.dispatchUpdatesTo(this);
//        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView movieImage;
        private final TextView videoTitle;
        private final TextView videoDescription;
        private final TextView videoRating;
        private final TextView videoProductionYear;
        private final ImageView likeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.videoPoster);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoDescription = itemView.findViewById(R.id.videoDescription);
            videoRating = itemView.findViewById(R.id.videoRating);
            videoProductionYear = itemView.findViewById(R.id.productionYear);
            likeBtn = itemView.findViewById(R.id.likeButton);

        }
    }
}
