package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.PeopleResponse;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.FamilyTreeApi;
import com.senior.project.genealogy.service.MemberApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapModelImpl implements MapModel {
    private MapFragmentPresenter mMapFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public MapModelImpl(MapFragmentPresenter mapFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mMapFragmentPresenter = mapFragmentPresenter;
    }

    @Override
    public void getFamilyTreeByBranchId(int branchId, String token) {
        Call<PeopleResponse> call = mApplicationApi.getClient().create(FamilyTreeApi.class).getPeopleByBranchId(branchId, token);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                PeopleResponse peopleResponse = response.body();
                int code = Integer.parseInt(peopleResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mMapFragmentPresenter.getFamilyTreeByBranchIdSuccess(peopleResponse.getPeopleList());
                        break;
                    default:
                        mMapFragmentPresenter.getFamilyTreeByBranchIdFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                mMapFragmentPresenter.getFamilyTreeByBranchIdFalse();
            }
        });
    }

    @Override
    public void deletePeople(final int peopleId, String token) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(FamilyTreeApi.class).deletePeople(peopleId, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mMapFragmentPresenter.deletePeopleSuccess(peopleId);
                        break;
                    default:
                        mMapFragmentPresenter.deletePeopleFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mMapFragmentPresenter.deletePeopleFalse();
            }
        });
    }

    @Override
    public void joinBranch(UserBranchPermission userBranchPermission, String token) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(MemberApi.class).joinBranch(userBranchPermission, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mMapFragmentPresenter.joinBranchSuccess();
                        break;
                    default:
                        mMapFragmentPresenter.joinBranchFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mMapFragmentPresenter.joinBranchFalse();
            }
        });
    }

    @Override
    public void outBranch(UserBranchPermission userBranchPermission, String token) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(MemberApi.class).outBranch(userBranchPermission, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mMapFragmentPresenter.outBranchSuccess();
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mMapFragmentPresenter.outBranchFalse();
                        break;
                    default:
                        mMapFragmentPresenter.outBranchFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mMapFragmentPresenter.outBranchFalse();
            }
        });
    }
}
