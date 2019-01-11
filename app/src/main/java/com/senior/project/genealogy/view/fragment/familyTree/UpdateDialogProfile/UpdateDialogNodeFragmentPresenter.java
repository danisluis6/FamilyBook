package com.senior.project.genealogy.view.fragment.familyTree.UpdateDialogProfile;

import com.senior.project.genealogy.response.People;

public interface UpdateDialogNodeFragmentPresenter {
    void updatePeople(People people, String token);
    void updatePeopleSuccess(People people);
    void updatePeopleFalse();
}
