package com.senior.project.genealogy.view.fragment.search.BranchSearchFragment;

import com.senior.project.genealogy.response.BranchResponse;
import com.senior.project.genealogy.response.Search;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.SearchApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchSearchModelImpl implements BranchSearchModel {
    private BranchSearchFragmentPresenter mBranchSearchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public BranchSearchModelImpl(BranchSearchFragmentPresenter branchSearchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mBranchSearchFragmentPresenter = branchSearchFragmentPresenter;
    }

    @Override
    public void searchBranchByName(Search search, String token) {
        Call<BranchResponse> call = mApplicationApi.getClient().create(SearchApi.class).searchBranchByName(search, token);
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                BranchResponse branchResponse = response.body();
                int code = Integer.parseInt(branchResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mBranchSearchFragmentPresenter.searchBranchByNameSuccess(branchResponse.getBranchList());
                        break;
                    default:
                        mBranchSearchFragmentPresenter.searchBranchByNameFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                mBranchSearchFragmentPresenter.searchBranchByNameFalse();
            }
        });
    }

}
