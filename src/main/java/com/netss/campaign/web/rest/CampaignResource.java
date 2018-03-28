package com.netss.campaign.web.rest;


import com.netss.campaign.domain.Campaign;
import com.netss.campaign.repository.CampaignRepository;
import com.netss.campaign.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/campaigns", produces = APPLICATION_JSON_VALUE)
public class CampaignResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignResource.class);

    private final CampaignRepository campaignRepository;
    private final CampaignService campaignService;

    public CampaignResource(CampaignRepository campaignRepository, CampaignService campaignService) {
        this.campaignRepository = campaignRepository;
        this.campaignService = campaignService;
    }

    @GetMapping("/{campaignId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Campaign> get(@PathVariable(value="campaignId") Long campaignId) {
        return campaignRepository
            .findById(campaignId)
            .map(c -> ResponseEntity.ok(c))
            .orElse(ResponseEntity.noContent().build()) ;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Campaign> getAll(@RequestParam(value="campaignId") List<Long> campaignsId) {

        if(CollectionUtils.isEmpty(campaignsId))
            return campaignRepository.findAll();

        return campaignRepository.findAllById(campaignsId);
    }

    @GetMapping("/team/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Campaign> getByTeamId(@PathVariable(value="teamId") String teamId) {
        return campaignRepository.findAll();
    }

    @DeleteMapping("/{campaignId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value="campaignId") Long campaignId) {
        campaignService.deleteCampaignById(campaignId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Campaign update(@RequestBody @Valid Campaign campaign) {
        return campaignService.updateCampaign(campaign) ;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Campaign create(@RequestBody @Valid Campaign campaign, CampaignResource campaignResource) {
        return campaignService.saveCampaign(campaign);
    }

}
