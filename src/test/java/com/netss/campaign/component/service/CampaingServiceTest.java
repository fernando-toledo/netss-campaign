package com.netss.campaign.component.service;

import com.netss.campaign.component.util.CampaignsBuildHelper;
import com.netss.campaign.domain.Campaign;
import com.netss.campaign.integration.amqp.CampaignMessageProducer;
import com.netss.campaign.repository.CampaignRepository;
import com.netss.campaign.service.CampaignService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CampaingServiceTest {


    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignService campaignService;

    //=========================
    //INTEGRATION MOCKED BEANS:
    //=========================
    // These integration beans are mocked to facilitate integration issues.

    @MockBean
    private CampaignMessageProducer producer;

    @Test
    public void shouldUpdateCampaignEndDataWhenNewCampaignIsSaved(){

        campaignService.saveCampaign(CampaignsBuildHelper.campaignDayThree());
        campaignService.saveCampaign(CampaignsBuildHelper.campaignDayTwo());
        campaignService.saveCampaign(CampaignsBuildHelper.campaignDayThree());

        List<Campaign> campaigns = campaignRepository.findAll();

        assertEquals(3, campaigns.size());

    }
}
