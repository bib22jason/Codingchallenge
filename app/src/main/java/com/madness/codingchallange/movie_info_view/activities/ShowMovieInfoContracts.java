package com.madness.codingchallange.movie_info_view.activities;

/**
 *  This class list the contracts with the presenter and the view for {@link ShowMovieInfoActivity}
 */
public interface ShowMovieInfoContracts {

    interface ShowMovieInfoPresenter{
        void init(ShowMovieInfoContracts.ShowMovieInfoView view);

        void setData();
    }

    interface ShowMovieInfoView{
        void setData();
    }
}
