package com.senior.project.genealogy.view.fragment.branch.UpdateBranchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.BranchApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBranchModelImpl implements UpdateBranchModel {
    private UpdateBranchFragmentPresenter mUpdateBranchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public UpdateBranchModelImpl(UpdateBranchFragmentPresenter updateBranchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mUpdateBranchFragmentPresenter = updateBranchFragmentPresenter;
    }

    @Override
    public void updateBranch(final Branch branch, String token) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(BranchApi.class).updateBranch(branch, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mUpdateBranchFragmentPresenter.updateBranchSuccess(branch);
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mUpdateBranchFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    case Constants.HTTPCodeResponse.UNAUTHORIZED:
                        mUpdateBranchFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    default:
                        mUpdateBranchFragmentPresenter.updateBranchFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mUpdateBranchFragmentPresenter.updateBranchFalse();
            }
        });
    }
}
