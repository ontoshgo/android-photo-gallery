package com.photogallery.app.presentation.ui.view.activity.photosWall;

import com.photogallery.app.domain.interactor.BaseSubscriber;
import com.photogallery.app.domain.interactor.GetWallPhotosUseCase;
import com.photogallery.app.domain.interactor.impl.GetWallPhotosUseCaseImpl;
import com.photogallery.app.data.model.Wall;
import com.photogallery.app.presentation.util.ErrorHandler;

public class WallPhotosPresenterImpl implements WallPhotosPresenter {

    private Wall wall;
    private int currentPage;
    private boolean loading;
    private static int NO_PAGE = 0;
    private static int FIRST_PAGE = 1;
    private static int DIFFERENCE_NEXT_PAGE = 1;

    private WallView view;
    private GetWallPhotosUseCase useCase = new GetWallPhotosUseCaseImpl();


    @Override
    public void setView(WallView view) {
        this.view = view;
    }

    @Override
    public void loadFirstPage() {
        view.showLoading();
        setLoading(true);
        useCase.execute(new WallPhotosSubscriber(), FIRST_PAGE);
    }

    @Override
    public void loadNextPage() {
        this.loading = true;
        int nextPage = currentPage + DIFFERENCE_NEXT_PAGE;
        useCase.execute(new WallPhotosSubscriber(), nextPage);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return wall.getTotalPages();
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    @Override
    public void onStop() {
        useCase.unSubscribe();
    }

    private class WallPhotosSubscriber extends BaseSubscriber<Wall> {
        @Override
        public void onCompleted() {
            view.hideLoading();
            setLoading(false);
        }

        @Override
        public void onError(Throwable e) {
            setLoading(false);
            view.hideLoading();
            view.showMessage(ErrorHandler.handleError());
            if (currentPage == NO_PAGE) {
                view.showReload();
            }
        }

        @Override
        public void onNext(Wall wall) {
            WallPhotosPresenterImpl.this.wall = wall;
            currentPage = wall.getCurrentPage();
            view.updateWallPhotos(wall);
        }
    }
}
