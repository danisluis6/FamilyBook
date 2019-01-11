package com.senior.project.genealogy.view.fragment.branch.ShowBranchFragment;

import android.support.v7.widget.RecyclerView;
import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.GenealogyResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.BranchApi;
import com.senior.project.genealogy.service.GenealogyApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchModelImpl implements BranchModel {
    private BranchFragmentPresenter mBranchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public BranchModelImpl(BranchFragmentPresenter branchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mBranchFragmentPresenter = branchFragmentPresenter;
    }

    @Override
    public void getGenealogiesByUsername(String token) {
        Call<GenealogyResponse> call = mApplicationApi.getClient().create(GenealogyApi.class).getGenealogiesByUserName(token);
        call.enqueue(new Callback<GenealogyResponse>() {
            @Override
            public void onResponse(Call<GenealogyResponse> call, Response<GenealogyResponse> response) {
                GenealogyResponse genealogyResponse = response.body();
                int code = Integer.parseInt(genealogyResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mBranchFragmentPresenter.getGenealogiesByUsernameSuccess(genealogyResponse.getGenealogyList());
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mBranchFragmentPresenter.getGenealogiesByUsernameSuccess(genealogyResponse.getGenealogyList());
                        //mBranchFragmentPresenter.showToast(String.valueOf(genealogyResponse.getError().getDescription()));
                        break;
                    default:
                        mBranchFragmentPresenter.getGenealogiesByUsernameFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<GenealogyResponse> call, Throwable t) {
                mBranchFragmentPresenter.getGenealogiesByUsernameFalse();
            }
        });
    }

//    @Override
//    public void getBranchesByGenealogyId(String token, int genealogyId) {
//        Call<BranchResponse> call = mApplicationApi.getClient().create(BranchApi.class).getBranchesByGenealogyId(genealogyId, token);
//        call.enqueue(new Callback<BranchResponse>() {
//            @Override
//            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
//                BranchResponse branchResponse = response.body();
//                mBranchFragmentPresenter.getBranchesByGenealogyIdSuccess(branchResponse.getBranchList());
//            }
//
//            @Override
//            public void onFailure(Call<BranchResponse> call, Throwable t) {
//                mBranchFragmentPresenter.getBranchesByGenealogyIdFalse();
//            }
//        });
//    }

    @Override
    public void deleteBranch(int branchId, String token, final RecyclerView.ViewHolder viewHolder) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(BranchApi.class).deleteBranch(branchId, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mBranchFragmentPresenter.deleteBranchSuccess(viewHolder);
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mBranchFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    case Constants.HTTPCodeResponse.UNAUTHORIZED:
                        mBranchFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    default:
                        mBranchFragmentPresenter.deleteBranchFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mBranchFragmentPresenter.deleteBranchFalse();
            }
        });
    }
}
