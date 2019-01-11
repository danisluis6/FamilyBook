package com.senior.project.genealogy.view.fragment.search.GenealogySearchFragment;

import com.senior.project.genealogy.response.Search;

public interface GenealogySearchModel {
    void searchGenealogyByName(Search search, String token);
}
