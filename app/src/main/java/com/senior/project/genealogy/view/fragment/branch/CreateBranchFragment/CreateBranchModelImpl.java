package com.senior.project.genealogy.view.fragment.branch.CreateBranchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.BranchResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.BranchApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBranchModelImpl implements CreateBranchModel {
    private CreateBranchFragmentPresenter mCreateBranchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public CreateBranchModelImpl(CreateBranchFragmentPresenter createBranchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mCreateBranchFragmentPresenter = createBranchFragmentPresenter;
    }


    @Override
    public void createBranch(Branch branch, String token) {
        Call<BranchResponse> call = mApplicationApi.getClient().create(BranchApi.class).createBranch(branch, token);
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                BranchResponse branchResponse = response.body();
                int code = Integer.parseInt(branchResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mCreateBranchFragmentPresenter.createBranchSuccess(branchResponse.getBranchList());
                        break;
                    case Constants.HTTPCodeResponse.UNAUTHORIZED:
                        mCreateBranchFragmentPresenter.showToast(String.valueOf(branchResponse.getError().getDescription()));
                        break;
                    default:
                        mCreateBranchFragmentPresenter.createBranchFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                mCreateBranchFragmentPresenter.createBranchFalse();
            }
        });
    }
}
