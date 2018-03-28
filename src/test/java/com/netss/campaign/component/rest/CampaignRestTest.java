package com.netss.campaign.component.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.netss.campaign.component.util.CampaignsBuildHelper;
import com.netss.campaign.domain.Campaign;
import com.netss.campaign.integration.amqp.CampaignMessageProducer;
import com.netss.campaign.repository.CampaignRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class CampaignRestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CampaignRepository campaignRepository;

    //=========================
    //INTEGRATION MOCKED BEANS:
    //=========================
    // These integration beans are mocked to facilitate integration issues.

    @MockBean
    private CampaignMessageProducer producer;


    @Test
    public void shouldGetAllCampaign() throws Exception {

        campaignRepository.deleteAll();

        mvc.perform(MockMvcRequestBuilders.post("/campaigns")
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFriday()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.post("/campaigns")
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFriday()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.post("/campaigns")
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFriday()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated());

        List<Campaign> campaigns = campaignRepository.findAll();

        assertEquals(3, campaigns.size());
    }

    @Test
    public void shouldCreateUniqueCampaign() throws Exception {

        campaignRepository.deleteAll();

        mvc.perform(MockMvcRequestBuilders.post("/campaigns")
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFriday()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated());

        List<Campaign> campaigns = campaignRepository.findAll();

        assertEquals(1, campaigns.size());
        assertEquals("BlackFriday", campaigns.get(0).getName());
    }

    @Test
    public void shouldDeleteUniqueCampaign() throws Exception {

        campaignRepository.deleteAll();

        MvcResult createResult = mvc.perform(MockMvcRequestBuilders.post("/campaigns")
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFriday()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        Campaign created = mapper.readValue(createResult.getResponse().getContentAsString() , Campaign.class);

        List<Campaign> campaignsBeforeDeletion = campaignRepository.findAll();
        assertEquals(1, campaignsBeforeDeletion.size());

        mvc.perform(MockMvcRequestBuilders.delete(String.format("/campaigns/%s", created.getId()))
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFriday()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        List<Campaign> campaignsAfterDeletion = campaignRepository.findAll();
        assertEquals(0, campaignsAfterDeletion.size());

    }

    @Test
    public void shouldGetUniqueCampaign() throws Exception {

        campaignRepository.deleteAll();

        MvcResult createResult = mvc.perform(MockMvcRequestBuilders.post("/campaigns")
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFriday()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        Campaign created = mapper.readValue(createResult.getResponse().getContentAsString() , Campaign.class);

        List<Campaign> campaignsBeforeDeletion = campaignRepository.findAll();
        assertEquals(1, campaignsBeforeDeletion.size());

        MvcResult getResult = mvc.perform(MockMvcRequestBuilders.get(String.format("/campaigns/%s", created.getId()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        Campaign getCampaign = mapper.readValue(getResult.getResponse().getContentAsString() , Campaign.class);

        List<Campaign> campaignsAfterDeletion = campaignRepository.findAll();
        assertEquals(1, campaignsAfterDeletion.size());
        assertEquals(getCampaign, created);

    }

    @Test
    public void shouldUpdateUniqueCampaign() throws Exception {

        campaignRepository.deleteAll();

        MvcResult createResult = mvc.perform(MockMvcRequestBuilders.post("/campaigns")
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFriday()))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        Campaign created = mapper.readValue(createResult.getResponse().getContentAsString() , Campaign.class);

        List<Campaign> campaignsBeforeUpdate = campaignRepository.findAll();
        assertEquals(1, campaignsBeforeUpdate.size());

        MvcResult updateResult = mvc.perform(MockMvcRequestBuilders.put("/campaigns")
            .content(mapper.writeValueAsString(CampaignsBuildHelper.campaignBlackFridayUpdate(created.getId())))
            .contentType(APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        Campaign updated = mapper.readValue(updateResult.getResponse().getContentAsString() , Campaign.class);

        List<Campaign> campaignsAfterUpdate = campaignRepository.findAll();

        Assert.assertEquals(1, campaignsAfterUpdate.size());
        Assert.assertEquals(updated, CampaignsBuildHelper.campaignBlackFridayUpdate(created.getId()));
    }


}
