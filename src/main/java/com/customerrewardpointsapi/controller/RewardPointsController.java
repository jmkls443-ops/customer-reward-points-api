package com.customerrewardpointsapi.controller;

import com.customerrewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.customerrewardpointsapi.service.RewardPointsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardPointsController {

    private final RewardPointsService rewardPointsService;

    public RewardPointsController(RewardPointsService rewardPointsService) {
        this.rewardPointsService = rewardPointsService;
    }

    // GET /rewards?startDate=2026-04-01&endDate=2026-06-30
    @GetMapping
    public List<CustomerRewardPointsSummaryDTO> getRewards(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return rewardPointsService.calculateRewardsForPeriod(startDate, endDate);
    }
}
