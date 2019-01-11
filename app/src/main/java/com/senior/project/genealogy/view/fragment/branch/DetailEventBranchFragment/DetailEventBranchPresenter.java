package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;


import com.senior.project.genealogy.response.Event;

import java.util.List;

public interface DetailEventBranchPresenter {
    void pushEvent(Event event, String token);
    void pushEventSuccess(Event event);
    void pushEventFalse();
    void getCreatedEvent(Integer branchId, String token);
    void getCreatedEventSuccess(List<Event> eventList);
    void getCreatedEventFalse();
}
