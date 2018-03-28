package com.netss.campaign.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Observer;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

public class CampaignPeriodRule {

    //TODO user=fh message='avoid recursive method, change logic later. if the list scales'
    public void incrementEndDate(List<Campaign> campaigns, Campaign campaign, Observer observer) {

        campaigns.stream()
            .filter(c -> !c.equals(campaign))
            .sorted(comparing(Campaign::getCampaignEnd))
            .forEach(c -> {
                LocalDate endDatePlusOneDay = c.getCampaignEnd().plusDays(1);
                c.setCampaignEnd(endDatePlusOneDay);
                avoidConflictEndDate(campaigns, c);
            });

    }

    private void avoidConflictEndDate(List<Campaign> campaigns, Campaign campaign) {
        boolean conflict = campaigns.stream().anyMatch(shouldIncrement(campaign));
        if (conflict) {
            LocalDate endDatePlusOneDay = campaign.getCampaignEnd().plusDays(1);
            campaign.setCampaignEnd(endDatePlusOneDay);
            avoidConflictEndDate(campaigns, campaign);
        }
    }

    private Predicate<Campaign> shouldIncrement(Campaign campaign) {
        return c -> !c.equals(campaign) && c.isSameCampaignEndDate(campaign.getCampaignEnd());
    }

}
