package com.netss.campaign.component.cucumber.stepdefs;

import com.netss.campaign.web.rest.CampaignResource;
import cucumber.api.Format;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CampaignStepDefs extends StepDefs {

    @Autowired
    private CampaignResource campaignResource;

    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(campaignResource).build();
    }


    @When("^When I create a '(.*)' campaign, between the dates (.+) and (.+)$")
    public void i_search_user_admin(String campaign, @Format("yyyy-MM-dd") LocalDate start, @Format("yyyy-MM-dd") LocalDate end) throws Throwable {
        actions = restUserMockMvc.perform(get("/campaigns/" + campaign)
            .accept(MediaType.APPLICATION_JSON));
    }

    @And("^I search for campaign '(.*)'$")
    public void i_create_a_campaign(String campaign) throws Throwable {
        actions = restUserMockMvc.perform(get("/campaigns/" + campaign)
            .accept(MediaType.APPLICATION_JSON));
    }

    @Then("^the campaign is found$")
    public void the_user_is_found() throws Throwable {
        actions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Then("^Its name is '(.*)'$")
    public void his_last_name_is(String campaignId) throws Throwable {
        actions.andExpect(jsonPath("$.campaign").value(campaignId));
    }

}
