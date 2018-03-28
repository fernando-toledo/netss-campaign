package com.netss.campaign.service;

import com.netss.campaign.domain.Campaign;
import com.netss.campaign.domain.CampaignPeriodRule;
import com.netss.campaign.domain.UpdatedCampaignObserver;
import com.netss.campaign.integration.amqp.CampaignMessageProducer;
import com.netss.campaign.repository.CampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CampaignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);

    private CampaignRepository campaignRepository;
    private CampaignMessageProducer campaignMessageProducer;

    public CampaignService(CampaignRepository campaignRepository, CampaignMessageProducer campaignMessageProducer) {
        this.campaignRepository = campaignRepository;
        this.campaignMessageProducer = campaignMessageProducer;
    }

    public Campaign updateCampaign(Campaign campaign) {

        LOGGER.debug("update", campaign);

        Campaign oldCampaign = campaignRepository.getOne(campaign.getId());
        oldCampaign.setName(campaign.getName());
        oldCampaign.setCampaignStart(campaign.getCampaignStart());
        oldCampaign.setCampaignEnd(campaign.getCampaignEnd());
        oldCampaign.setTeamId(campaign.getTeamId());
        oldCampaign = campaignRepository.save(oldCampaign);

        campaignMessageProducer.sendCampaignUpdateMessage(Arrays.asList(oldCampaign));

        return oldCampaign;
    }

    public Campaign saveCampaignWithoutRule(Campaign campaign) {

        LOGGER.debug("save", campaign);

        Campaign newCampaign = campaignRepository.save(campaign);
        campaignMessageProducer.sendCampaignUpdateMessage(Arrays.asList(campaign));
        return newCampaign;
    }

    public void deleteCampaignById(Long campaignId) {

        LOGGER.debug("delete", campaignId);

        campaignRepository.deleteById(campaignId);
        Campaign deletedCampaign = new Campaign();
        deletedCampaign.setId(campaignId);
        campaignMessageProducer.sendCampaignUpdateMessage(Arrays.asList(deletedCampaign));
    }

    public Campaign saveCampaign(Campaign campaign){

        LOGGER.debug("create campaign with period rule", campaign);

        List<Campaign> campaignsInPeriod = campaignRepository.getCampaignInPeriod(campaign.getCampaignStart(), campaign.getCampaignEnd());

        UpdatedCampaignObserver observer = new UpdatedCampaignObserver();
        campaignsInPeriod.forEach(c -> c.addObserver(observer));
        campaignsInPeriod.add(campaign);
        new CampaignPeriodRule().incrementEndDate(campaignsInPeriod, campaign, observer);

        List<Campaign> updatedCampaigns = observer.getUpdatedCampaigns();

        Campaign newCampaign = campaignRepository.save(campaign);
        campaignRepository.saveAll(updatedCampaigns);

        return newCampaign;
    }
}
