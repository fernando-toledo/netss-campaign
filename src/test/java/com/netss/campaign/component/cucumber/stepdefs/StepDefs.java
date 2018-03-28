package com.netss.campaign.component.cucumber.stepdefs;

import com.netss.campaign.NetssCampaignApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = NetssCampaignApplication.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
