package com.zerogate.fraud.repository;

import com.zerogate.fraud.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, String> {
}
