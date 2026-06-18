package com.customerrewardpointsapi.service;

import com.customerrewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.customerrewardpointsapi.entity.RewardPointsTransaction;
import com.customerrewardpointsapi.repository.RewardPointsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class RewardPointsService {

    private final RewardPointsRepository rewardPointsRepository;

    // Constructor injection
    public RewardPointsService(RewardPointsRepository rewardPointsRepository) {
        this.rewardPointsRepository = rewardPointsRepository;
    }
    public List<CustomerRewardPointsSummaryDTO> calculateRewardsForPeriod(LocalDate startDate, LocalDate endDate) {
        List<RewardPointsTransaction> transactions =
                rewardPointsRepository.findByTransactionDateBetween(startDate, endDate);

        Map<String, CustomerRewardPointsSummaryDTO> summaries = new HashMap<>();

        for (RewardPointsTransaction tx : transactions) {
            BigDecimal points = calculateRewardPoints(tx.getAmount());

            String monthKey = tx.getTransactionDate().getMonth().toString();

            summaries.computeIfAbsent(tx.getCustomerId(), id ->
                    new CustomerRewardPointsSummaryDTO(tx.getCustomerId(), tx.getCustomerName(),
                            new HashMap<>(), BigDecimal.ZERO));

            CustomerRewardPointsSummaryDTO summary = summaries.get(tx.getCustomerId());

            summary.getMonthlyRewards().merge(monthKey, points, BigDecimal::add);
            summary.setTotalRewards(summary.getTotalRewards().add(points));
        }

        return new ArrayList<>(summaries.values());
    }
    public BigDecimal calculateRewardPoints(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(50)) <= 0) {
            return BigDecimal.ZERO;
        } else if (amount.compareTo(BigDecimal.valueOf(100)) <= 0) {
            // 1 point per dollar above 50
            return amount.subtract(BigDecimal.valueOf(50));
        } else {
            // 2 points per dollar above 100, plus 50 points for the 50–100 range
            return amount.subtract(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(50));
        }
    }

}
