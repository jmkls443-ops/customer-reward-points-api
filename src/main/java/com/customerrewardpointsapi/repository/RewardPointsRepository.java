package com.customerrewardpointsapi.repository;

import com.customerrewardpointsapi.entity.RewardPointsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RewardPointsRepository extends JpaRepository<RewardPointsTransaction, Long> {
    List<RewardPointsTransaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}
