package com.example.bingerapp;

public class Movies {
    private String movieImageUrl;
    private String movieTitle;
    private String movieDescription;
    private double movieRating;
    private String movieProductionYear;

    public Movies(String movieImageUrl, String movieTitle, String movieDescription, double movieRating, String movieProductionYear) {
        this.movieImageUrl = movieImageUrl;
        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
        this.movieRating = movieRating;
        this.movieProductionYear = movieProductionYear;
    }

    public String getMovieImageUrl() {
        return movieImageUrl;
    }

    public void setMovieImageUrl(String movieImageUrl) {
        this.movieImageUrl = movieImageUrl;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieProductionYear() {
        return movieProductionYear;
    }

    public void setMovieProductionYear(String movieProductionYear) {
        this.movieProductionYear = movieProductionYear;
    }
}
