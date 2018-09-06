package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.EmployeeAttendanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendanceLog, Long> {

    public EmployeeAttendanceLog findTopByOrderByCreateDateDesc();
}
