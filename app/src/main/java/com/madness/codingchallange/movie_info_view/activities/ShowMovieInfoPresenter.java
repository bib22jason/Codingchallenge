package com.madness.codingchallange.movie_info_view.activities;

/**
 * Presenter of the Activity {@link ShowMovieInfoActivity}
 */
public class ShowMovieInfoPresenter implements ShowMovieInfoContracts.ShowMovieInfoPresenter {

    private ShowMovieInfoContracts.ShowMovieInfoView view;

    @Override
    public void init(ShowMovieInfoContracts.ShowMovieInfoView view) {
        this.view = view;
    }

    @Override
    public void setData() {
        view.setData();
    }
}
