package com.senior.project.genealogy.view.fragment.familyTree.ShowFamilyTreeFragment;

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

public class FamilyTreeModelImpl implements FamilyTreeModel {
    private FamilyTreeFragmentPresenter mFamilyTreeFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public FamilyTreeModelImpl(FamilyTreeFragmentPresenter familyTreeFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mFamilyTreeFragmentPresenter = familyTreeFragmentPresenter;
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
                        mFamilyTreeFragmentPresenter.getGenealogiesByUsernameSuccess(genealogyResponse.getGenealogyList());
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mFamilyTreeFragmentPresenter.getGenealogiesByUsernameSuccess(genealogyResponse.getGenealogyList());
//                        mFamilyTreeFragmentPresenter.showToast(String.valueOf(genealogyResponse.getError().getDescription()));
                        break;
                    default:
                        mFamilyTreeFragmentPresenter.getGenealogiesByUsernameFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<GenealogyResponse> call, Throwable t) {
                mFamilyTreeFragmentPresenter.getGenealogiesByUsernameFalse();
            }
        });
    }

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
                        mFamilyTreeFragmentPresenter.deleteBranchSuccess(viewHolder);
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mFamilyTreeFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    case Constants.HTTPCodeResponse.UNAUTHORIZED:
                        mFamilyTreeFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    default:
                        mFamilyTreeFragmentPresenter.deleteBranchFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mFamilyTreeFragmentPresenter.deleteBranchFalse();
            }
        });
    }
}
