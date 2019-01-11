package com.senior.project.genealogy.view.fragment.familyTree.DialogNode;

import com.senior.project.genealogy.response.PeopleResponse;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.FamilyTreeApi;
import com.senior.project.genealogy.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodeModelImpl implements NodeModel {
    private DialogNodeFragmentPresenter mDialogNodeFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public NodeModelImpl(DialogNodeFragmentPresenter dialogNodeFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDialogNodeFragmentPresenter = dialogNodeFragmentPresenter;
    }

    @Override
    public void createPeople(People people, String token) {
        Call<PeopleResponse> call = mApplicationApi.getClient().create(FamilyTreeApi.class).createPeople(people, token);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                PeopleResponse peopleResponse = response.body();
                int code = Integer.parseInt(peopleResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mDialogNodeFragmentPresenter.createPeopleSuccess(peopleResponse.getPeopleList());
                        break;
                    default:
                        mDialogNodeFragmentPresenter.createPeopleFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                mDialogNodeFragmentPresenter.createPeopleFalse();
            }
        });
    }
}
