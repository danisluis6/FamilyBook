package com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.GenealogyApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateGenealogyModelImpl implements UpdateGenealogyModel {
    private UpdateGenealogyFragmentPresenter mUpdateGenealogyFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public UpdateGenealogyModelImpl(UpdateGenealogyFragmentPresenter updateGenealogyFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mUpdateGenealogyFragmentPresenter = updateGenealogyFragmentPresenter;
    }

    @Override
    public void updateGenealogy(final Genealogy genealogy, String token) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(GenealogyApi.class).updateGenealogy(genealogy, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mUpdateGenealogyFragmentPresenter.updateGenealogySuccess(genealogy);
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mUpdateGenealogyFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    case Constants.HTTPCodeResponse.UNAUTHORIZED:
                        mUpdateGenealogyFragmentPresenter.showToast(String.valueOf(codeResponse.getError().getDescription()));
                        break;
                    default:
                        mUpdateGenealogyFragmentPresenter.updateGenealogyFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mUpdateGenealogyFragmentPresenter.updateGenealogyFalse();
            }
        });
    }
}
