package com.customerrewardpointsapi.controller;

import com.customerrewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.customerrewardpointsapi.service.RewardPointsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardPointsController.class)
class RewardPointsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardPointsService rewardPointsService;

    @Test
    void testGetRewards_returnsSummary() throws Exception {
        Map<String, BigDecimal> monthlyRewards = new HashMap<>();
        monthlyRewards.put("APRIL", BigDecimal.valueOf(91.5));
        monthlyRewards.put("MAY", BigDecimal.valueOf(25.9));

        CustomerRewardPointsSummaryDTO summary =
                new CustomerRewardPointsSummaryDTO("CUST001", "Alice", monthlyRewards, BigDecimal.valueOf(117.4));

        when(rewardPointsService.calculateRewardsForPeriod(LocalDate.of(2026, 4, 1), LocalDate.of(2026, 6, 30)))
                .thenReturn(Collections.singletonList(summary));

        mockMvc.perform(get("/rewards")
                        .param("startDate", "2026-04-01")
                        .param("endDate", "2026-06-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("CUST001"))
                .andExpect(jsonPath("$[0].customerName").value("Alice"))
                .andExpect(jsonPath("$[0].monthlyRewards.APRIL").value(91.5))
                .andExpect(jsonPath("$[0].totalRewards").value(117.4));
    }
}
