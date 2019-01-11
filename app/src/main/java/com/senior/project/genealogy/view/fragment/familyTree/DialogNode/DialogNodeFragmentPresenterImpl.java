package com.senior.project.genealogy.view.fragment.familyTree.DialogNode;

import com.senior.project.genealogy.response.People;
import java.util.List;

public class DialogNodeFragmentPresenterImpl implements DialogNodeFragmentPresenter {

    private NodeModel mNodeModel;
    private DialogNodeFragmentView mDialogNodeFragmentView;

    public DialogNodeFragmentPresenterImpl(DialogNodeFragmentView dialogNodeFragmentView) {
        mDialogNodeFragmentView = dialogNodeFragmentView;
        mNodeModel = new NodeModelImpl(this);
    }

    @Override
    public void createPeople(People people, String token) {
        mDialogNodeFragmentView.showProgressDialog();
        mNodeModel.createPeople(people, token);
    }

    @Override
    public void createPeopleSuccess(List<People> peopleList) {
        mDialogNodeFragmentView.closeProgressDialog();
        mDialogNodeFragmentView.closeDialogFragment(peopleList);
    }

    @Override
    public void createPeopleFalse() {
        mDialogNodeFragmentView.closeProgressDialog();
//        mDialogNodeFragmentView.showMap(peopleList);
    }

}
