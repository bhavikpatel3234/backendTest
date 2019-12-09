package io.golo.backendtest.repository;

import io.golo.backendtest.entity.MonitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorLogRepository extends JpaRepository<MonitorLog, Long> {
}
