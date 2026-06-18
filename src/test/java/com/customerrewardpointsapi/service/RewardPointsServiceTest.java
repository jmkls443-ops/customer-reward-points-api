package com.customerrewardpointsapi.service;

import com.customerrewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.customerrewardpointsapi.entity.RewardPointsTransaction;
import com.customerrewardpointsapi.repository.RewardPointsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class RewardPointsServiceTest {

    @Mock
    private RewardPointsRepository rewardPointsRepository;

    @InjectMocks
    private RewardPointsService rewardPointsService;

    public RewardPointsServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateRewardsForPeriod_multipleTransactions() {
        RewardPointsTransaction tx1 = new RewardPointsTransaction();
        tx1.setCustomerId("CUST001");
        tx1.setCustomerName("Alice");
        tx1.setAmount(BigDecimal.valueOf(120.75));
        tx1.setTransactionDate(LocalDate.of(2026, 4, 10));

        RewardPointsTransaction tx2 = new RewardPointsTransaction();
        tx2.setCustomerId("CUST001");
        tx2.setCustomerName("Alice");
        tx2.setAmount(BigDecimal.valueOf(75.90));
        tx2.setTransactionDate(LocalDate.of(2026, 5, 5));

        when(rewardPointsRepository.findByTransactionDateBetween(
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 6, 30)))
                .thenReturn(Arrays.asList(tx1, tx2));

        List<CustomerRewardPointsSummaryDTO> summaries =
                rewardPointsService.calculateRewardsForPeriod(LocalDate.of(2026, 4, 1), LocalDate.of(2026, 6, 30));

        assertThat(summaries).hasSize(1);
        CustomerRewardPointsSummaryDTO summary = summaries.get(0);

        assertThat(summary.getCustomerId()).isEqualTo("CUST001");
        assertThat(summary.getTotalRewards()).isEqualByComparingTo(BigDecimal.valueOf(117.4));
        assertThat(summary.getMonthlyRewards().get("APRIL")).isEqualByComparingTo(BigDecimal.valueOf(91.5));
        assertThat(summary.getMonthlyRewards().get("MAY")).isEqualByComparingTo(BigDecimal.valueOf(25.9));
    }
}
