package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;


import com.senior.project.genealogy.response.Event;

public interface DetailEventBranchModel {
    void pushEvent(Event event, String token);
    void getCreatedEvent(Integer branchId, String token);
}
