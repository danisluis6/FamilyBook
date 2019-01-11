package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;


import com.senior.project.genealogy.response.Event;

import java.util.List;

public class DetailEventBranchPresenterImpl implements DetailEventBranchPresenter {

    private DetailEventBranchView mDetailEventBranchView;
    private DetailEventBranchModel mDetailEventBranchModel;

    public DetailEventBranchPresenterImpl(DetailEventBranchView detailEventBranchView) {
        mDetailEventBranchView = detailEventBranchView;
        mDetailEventBranchModel = new DetailEventBranchModelImpl(this);
    }


    @Override
    public void pushEvent(Event event, String token) {
        if (mDetailEventBranchView != null) {
            mDetailEventBranchView.showProgressDialog();
            mDetailEventBranchModel.pushEvent(event, token);
        }
    }

    @Override
    public void pushEventSuccess(Event event) {
        if (mDetailEventBranchView != null) {
            mDetailEventBranchView.closeProgressDialog();
            mDetailEventBranchView.addEventToList(event);
        }
    }

    @Override
    public void pushEventFalse() {
        mDetailEventBranchView.closeProgressDialog();
    }

    @Override
    public void getCreatedEvent(Integer branchId, String token) {
        if (mDetailEventBranchView != null) {
            mDetailEventBranchView.showProgressDialog();
            mDetailEventBranchModel.getCreatedEvent(branchId, token);
        }
    }

    @Override
    public void getCreatedEventSuccess(List<Event> eventList) {
        if (mDetailEventBranchView != null) {
            mDetailEventBranchView.closeProgressDialog();
            mDetailEventBranchView.showListCreatedEvent(eventList);
        }
    }

    @Override
    public void getCreatedEventFalse() {
        mDetailEventBranchView.closeProgressDialog();
    }

}
