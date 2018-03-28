package com.netss.campaign.integration.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netss.campaign.domain.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.netss.campaign.config.RabbitMqConfig.UPDATE_CAMPAIGN_MESSAGE_EXCHANGE;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class CampaignMessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignMessageProducer.class);

    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public CampaignMessageProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendCampaignUpdateMessage(List<Campaign> campaigns) {
        if (!isEmpty(campaigns)) {

            List<Long> ids = campaigns.stream().map(Campaign::getId).collect(toList());

            LOGGER.info("Sending updates : {}", ids);

            try {

                String campaign = objectMapper.writeValueAsString(campaigns);
                rabbitTemplate.convertAndSend(UPDATE_CAMPAIGN_MESSAGE_EXCHANGE, "*", campaign);

                LOGGER.info("Message has been sent");

            } catch (java.io.IOException e) {
                LOGGER.error("Unexpected error ", e);
            }

        }
    }
}
