package com.senior.project.genealogy.view.fragment.familyTree.DialogProfile;

import com.senior.project.genealogy.response.People;
import java.util.List;

public interface DialogProfileFragmentPresenter {
    void getRelative(int peopleId, String token);
    void getRelativeSuccess(List<People> peopleList);
    void getRelativeFalse();
}
