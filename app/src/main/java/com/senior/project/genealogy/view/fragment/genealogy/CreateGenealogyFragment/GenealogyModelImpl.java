package com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.GenealogyResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.GenealogyApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenealogyModelImpl implements GenealogyModel {
    private CreateGenealogyFragmentPresenter mCreateGenealogyFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public GenealogyModelImpl(CreateGenealogyFragmentPresenter createGenealogyFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mCreateGenealogyFragmentPresenter = createGenealogyFragmentPresenter;
    }


    @Override
    public void createGenealogy(Genealogy genealogy, String token) {
        Call<GenealogyResponse> call = mApplicationApi.getClient().create(GenealogyApi.class).createGenealogy(genealogy, token);
        call.enqueue(new Callback<GenealogyResponse>() {
            @Override
            public void onResponse(Call<GenealogyResponse> call, Response<GenealogyResponse> response) {
                GenealogyResponse genealogyResponse = response.body();
                int code = Integer.parseInt(genealogyResponse.getError().getCode());
                if (code == Constants.HTTPCodeResponse.SUCCESS)
                    mCreateGenealogyFragmentPresenter.createGenealogySuccess(genealogyResponse.getGenealogyList());
                else
                    mCreateGenealogyFragmentPresenter.createGenealogyFalse();
            }

            @Override
            public void onFailure(Call<GenealogyResponse> call, Throwable t) {
                mCreateGenealogyFragmentPresenter.createGenealogyFalse();
            }
        });
    }
}
