package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;

import com.senior.project.genealogy.response.Event;
import java.util.List;

public interface DetailEventBranchView {
    void showProgressDialog();
    void closeProgressDialog();
    void showListCreatedEvent(List<Event> eventList);
    void addEventToList(Event event);
}
