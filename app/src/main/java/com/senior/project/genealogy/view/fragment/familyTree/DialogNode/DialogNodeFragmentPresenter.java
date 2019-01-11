package com.senior.project.genealogy.view.fragment.familyTree.DialogNode;

import com.senior.project.genealogy.response.People;
import java.util.List;

public interface DialogNodeFragmentPresenter {
    void createPeople(People people, String token);
    void createPeopleSuccess(List<People> peopleList);
    void createPeopleFalse();
}
