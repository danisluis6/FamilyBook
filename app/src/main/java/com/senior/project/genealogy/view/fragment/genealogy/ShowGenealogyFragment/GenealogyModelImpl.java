package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import android.support.v7.widget.RecyclerView;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.GenealogyResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.GenealogyApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenealogyModelImpl implements GenealogyModel {
    private GenealogyFragmentPresenter mGenealogyFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public GenealogyModelImpl(GenealogyFragmentPresenter genealogyFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mGenealogyFragmentPresenter = genealogyFragmentPresenter;
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
                        mGenealogyFragmentPresenter.getGenealogiesByUsernameSuccess(genealogyResponse.getGenealogyList());
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mGenealogyFragmentPresenter.getGenealogiesByUsernameSuccess(genealogyResponse.getGenealogyList());
                        //mGenealogyFragmentPresenter.showToast(String.valueOf(genealogyResponse.getError().getDescription()));
                        break;
                    default:
                        mGenealogyFragmentPresenter.getGenealogiesByUsernameFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<GenealogyResponse> call, Throwable t) {
                mGenealogyFragmentPresenter.getGenealogiesByUsernameFalse();
            }
        });
    }

    @Override
    public void deleteGenealogy(int genealogyId, String token, final RecyclerView.ViewHolder viewHolder) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(GenealogyApi.class).deleteGenealogy(genealogyId, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mGenealogyFragmentPresenter.deleteGenealogySuccess(viewHolder);
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mGenealogyFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    case Constants.HTTPCodeResponse.UNAUTHORIZED:
                        mGenealogyFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    default:
                        mGenealogyFragmentPresenter.deleteGenealogyFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mGenealogyFragmentPresenter.deleteGenealogyFalse();
            }
        });
    }
}
