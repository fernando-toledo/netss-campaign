package com.netss.campaign.component.util;


import com.netss.campaign.domain.Campaign;

import java.time.LocalDate;

public class CampaignsBuildHelper {

    public static Campaign campaignBlackFriday() {
        Campaign c = new Campaign();
        c.setId(1L);
        c.setName("BlackFriday");
        c.setTeamId("timao");
        c.setCampaignStart(LocalDate.of(2018,11,24));
        c.setCampaignEnd(LocalDate.of(2018,11,30));
        return c;
    }

    public static Campaign campaignDayTwo(){
        Campaign c = new Campaign();
        c.setId(1L);
        c.setName("DayTwo");
        c.setTeamId("timao");
        c.setCampaignStart(LocalDate.of(2018,01,01));
        c.setCampaignEnd(LocalDate.of(2018,01,02));
        return c;
    }

    public static Campaign campaignAlsoDayTwo(){
        Campaign c = new Campaign();
        c.setId(1L);
        c.setName("AlsoDayTwo");
        c.setTeamId("timao");
        c.setCampaignStart(LocalDate.of(2018,01,01));
        c.setCampaignEnd(LocalDate.of(2018,01,02));
        return c;
    }

    public static Campaign campaignDayThree(){
        Campaign c = new Campaign();
        c.setId(1L);
        c.setName("DayThree");
        c.setTeamId("timao");
        c.setCampaignStart(LocalDate.of(2018,01,01));
        c.setCampaignEnd(LocalDate.of(2018,01,03));
        return c;
    }

    public static Campaign campaignDayFour(){
        Campaign c = new Campaign();
        c.setId(1L);
        c.setName("DayFour");
        c.setTeamId("timao");
        c.setCampaignStart(LocalDate.of(2018,01,01));
        c.setCampaignEnd(LocalDate.of(2018,01,04));
        return c;
    }

    public static Campaign campaignDayFive(){
        Campaign c = new Campaign();
        c.setId(1L);
        c.setName("DayFive");
        c.setTeamId("timao");
        c.setCampaignStart(LocalDate.of(2018,01,01));
        c.setCampaignEnd(LocalDate.of(2018,01,05));
        return c;
    }

    public static Campaign campaignBlackFridayUpdate(Long id) {
        Campaign c = new Campaign();
        c.setId(id);
        c.setName("BlackFriday2");
        c.setTeamId("timao2");
        c.setCampaignStart(LocalDate.of(2018,12,24));
        c.setCampaignEnd(LocalDate.of(2018,12,30));
        return c;
    }

    public static Campaign campaignCompanyBirthday() {
        Campaign c = new Campaign();
        c.setId(2L);
        c.setName("Campaign Birthday");
        c.setTeamId("spfc");
        c.setCampaignStart(LocalDate.of(2018,02,01));
        c.setCampaignEnd(LocalDate.of(2018,02,10));
        return c;
    }

    public static Campaign campaignChristmas() {
        Campaign c = new Campaign();
        c.setId(3L);
        c.setName("Campaign Christmas");
        c.setTeamId("real");
        c.setCampaignStart(LocalDate.of(2018,12,25));
        c.setCampaignEnd(LocalDate.of(2018,12,27));
        return c;
    }

    public static Campaign campaignThisWeek() {
        Campaign c = new Campaign();
        c.setId(4L);
        c.setName("Campaign This Week");
        c.setTeamId("barca");
        c.setCampaignStart(LocalDate.now());
        c.setCampaignEnd(LocalDate.now().plusWeeks(1));
        return c;
    }

    public static Campaign buildCampaign(String name, LocalDate start, LocalDate end) {
        Campaign c = new Campaign();
        c.setId(5L);
        c.setName(name);
        c.setCampaignStart(start);
        c.setCampaignEnd(end);
        return c;
    }
}
