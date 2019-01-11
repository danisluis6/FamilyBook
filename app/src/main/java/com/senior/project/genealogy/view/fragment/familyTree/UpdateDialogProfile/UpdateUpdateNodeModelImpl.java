package com.senior.project.genealogy.view.fragment.familyTree.UpdateDialogProfile;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.FamilyTreeApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUpdateNodeModelImpl implements UpdateNodeModel {
    private UpdateDialogNodeFragmentPresenter mUpdateDialogNodeFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public UpdateUpdateNodeModelImpl(UpdateDialogNodeFragmentPresenter updateDialogNodeFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mUpdateDialogNodeFragmentPresenter = updateDialogNodeFragmentPresenter;
    }

    @Override
    public void updatePeople(final People people, String token) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(FamilyTreeApi.class).updatePeople(people, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mUpdateDialogNodeFragmentPresenter.updatePeopleSuccess(people);
                        break;
                    default:
                        mUpdateDialogNodeFragmentPresenter.updatePeopleFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mUpdateDialogNodeFragmentPresenter.updatePeopleFalse();
            }
        });
    }
}
