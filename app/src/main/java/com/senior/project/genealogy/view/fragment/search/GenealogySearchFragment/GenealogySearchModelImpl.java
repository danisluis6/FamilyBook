package com.senior.project.genealogy.view.fragment.search.GenealogySearchFragment;

import com.senior.project.genealogy.response.GenealogyResponse;
import com.senior.project.genealogy.response.Search;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.SearchApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenealogySearchModelImpl implements GenealogySearchModel {
    private GenealogySearchFragmentPresenter mGenealogySearchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public GenealogySearchModelImpl(GenealogySearchFragmentPresenter genealogySearchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mGenealogySearchFragmentPresenter = genealogySearchFragmentPresenter;
    }

    @Override
    public void searchGenealogyByName(Search search, String token) {
        Call<GenealogyResponse> call = mApplicationApi.getClient().create(SearchApi.class).searchGenealogyByName(search, token);
        call.enqueue(new Callback<GenealogyResponse>() {
            @Override
            public void onResponse(Call<GenealogyResponse> call, Response<GenealogyResponse> response) {
                GenealogyResponse genealogyResponse = response.body();
                int code = Integer.parseInt(genealogyResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mGenealogySearchFragmentPresenter.searchGenealogyByNameSuccess(genealogyResponse.getGenealogyList());
                        break;
                    default:
                        mGenealogySearchFragmentPresenter.searchGenealogyByNameFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<GenealogyResponse> call, Throwable t) {
                mGenealogySearchFragmentPresenter.searchGenealogyByNameFalse();
            }
        });
    }

}
