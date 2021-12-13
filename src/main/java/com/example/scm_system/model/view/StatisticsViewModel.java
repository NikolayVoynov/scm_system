package com.example.scm_system.model.view;

public class StatisticsViewModel {

    private final int authorizedRequests;
    private final int anonymousRequests;

    public StatisticsViewModel(int authorizedRequests, int anonymousRequests) {
        this.authorizedRequests = authorizedRequests;
        this.anonymousRequests = anonymousRequests;
    }

    public int getAuthorizedRequests() {
        return authorizedRequests;
    }

    public int getAnonymousRequests() {
        return anonymousRequests;
    }

    public int getTotalRequests() {
        return authorizedRequests + anonymousRequests;
    }
}
