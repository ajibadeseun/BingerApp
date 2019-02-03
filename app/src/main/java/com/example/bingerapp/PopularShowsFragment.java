package com.example.bingerapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class PopularShowsFragment extends Fragment {

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .build();
    private AiringTodayAdapter airingTodayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_shows, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.popularTvShows);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        getAiringTodayMovies(Constants.TMDB_BASE_URL, Constants.POPULAR_SHOWS_PATH,
                Constants.API_KEY, Constants.TMDB_ACCESS_TOKEN,1, new GetAiringTodayCallback() {
                    @Override
                    public void onCallback(List<Movies> moviesList, int statusCode) {
                        if (statusCode == 1) {
                            if (moviesList != null) {
                                airingTodayAdapter = new AiringTodayAdapter(moviesList,getActivity());
                                recyclerView.setAdapter(airingTodayAdapter);

                            }
                        }
                    }
                });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                Toast.makeText(getActivity(),"Page: "+page,Toast.LENGTH_SHORT).show();

                int pageCount = page + 1;

                getAiringTodayMovies(Constants.TMDB_BASE_URL, Constants.POPULAR_SHOWS_PATH,
                        Constants.API_KEY, Constants.TMDB_ACCESS_TOKEN,pageCount, new GetAiringTodayCallback() {
                            @Override
                            public void onCallback(List<Movies> moviesList, int statusCode) {
                                if (statusCode == 1) {
                                    if (moviesList != null) {
                                        airingTodayAdapter.updateNewsListItems(moviesList);

                                    }
                                }
                            }
                        });

            }
        });


        return view;
    }


    public interface GetAiringTodayCallback {
        void onCallback(List<Movies> moviesList, int statusCode);
    }

    public void getAiringTodayMovies(String baseUrl, String airingTodayPath, String apiKey,
                                     String token,int pageNum,
                                     final GetAiringTodayCallback getAiringTodayCallback) {

        String url = baseUrl.concat(airingTodayPath).replace("{API_KEY}", apiKey)
                .concat("&page=").concat(String.valueOf(pageNum));

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .addHeader("authorization", "Bearer " + token)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        getAiringTodayCallback.onCallback(null, 0);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResponse = response.body().string();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (jsonResponse != null) {
                            getAiringTodayCallback.onCallback(retrieveMovieDataFromJson(jsonResponse), 1);

                        }

                    }
                });

            }
        });


    }

    private List<Movies> retrieveMovieDataFromJson(String jsonResponse) {
        List<Movies> moviesList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray resultsArray = jsonObject.optJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject object = resultsArray.getJSONObject(i);
                String movieTitle = object.optString("original_name");
                String movieProductionDate = object.optString("first_air_date");
                String posterPath = object.optString("poster_path");
                String movieImageUrl = Constants.IMAGE_URL.concat(posterPath);
                String movieDescription = object.optString("overview");
                double movieRating = object.optDouble("vote_average");

                moviesList.add(new Movies(movieImageUrl, movieTitle, movieDescription, movieRating, movieProductionDate));

            }
            return moviesList;
        } catch (JSONException e) {
            return null;
        }
    }
}
