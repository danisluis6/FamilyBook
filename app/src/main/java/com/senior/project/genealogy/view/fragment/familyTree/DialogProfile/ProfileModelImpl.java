package com.senior.project.genealogy.view.fragment.familyTree.DialogProfile;

import com.senior.project.genealogy.response.PeopleResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.FamilyRelationApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileModelImpl implements ProfileModel {
    private DialogProfileFragmentPresenter mDialogProfileFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public ProfileModelImpl(DialogProfileFragmentPresenter dialogProfileFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDialogProfileFragmentPresenter = dialogProfileFragmentPresenter;
    }

    @Override
    public void getRelative(int peopleId, String token) {
        Call<PeopleResponse> call = mApplicationApi.getClient().create(FamilyRelationApi.class).getFamilyRelation(peopleId, token);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                PeopleResponse peopleResponse = response.body();
                int code = Integer.parseInt(peopleResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mDialogProfileFragmentPresenter.getRelativeSuccess(peopleResponse.getPeopleList());
                        break;
                    default:
                        mDialogProfileFragmentPresenter.getRelativeFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                mDialogProfileFragmentPresenter.getRelativeFalse();
            }
        });
    }
}
