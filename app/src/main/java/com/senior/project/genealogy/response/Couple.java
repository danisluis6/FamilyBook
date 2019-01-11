package com.senior.project.genealogy.response;

public class Couple {
    private People mainPeople;
    private People partnerPeople;

    public Couple(People mainPeople) {
        this.mainPeople = mainPeople;
    }

    public People getMainPeople() {
        return mainPeople;
    }

    public void setMainPeople(People mainPeople) {
        this.mainPeople = mainPeople;
    }

    public People getPartnerPeople() {
        return partnerPeople;
    }

    public void setPartnerPeople(People partnerPeople) {
        this.partnerPeople = partnerPeople;
    }
}
