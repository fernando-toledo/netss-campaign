package com.netss.campaign.repository;

import com.netss.campaign.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the Campaign entity.
 */
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("select c from Campaign c where c.campaignEnd >= :start and c.campaignStart <= :end")
    List<Campaign> getCampaignInPeriod(
        @Param("start") LocalDate campaignStart,
        @Param("end") LocalDate campaignEnd);
}
