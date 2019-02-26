package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.EmployeeAttendanceLog;
import com.sf.biocloud.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendanceLog, Long> {

    public EmployeeAttendanceLog findTopByOrderByCreateDateDesc();

    public EmployeeAttendanceLog findTopByOrderByLastModified();

    EmployeeAttendanceLog findByJobId(String jobId);

    @Modifying
    @Query("UPDATE EmployeeAttendanceLog e SET e.status = :status, e.match =:match  WHERE e.jobId = :jobId")
    int updateAttendanceStatus(@Param("match") boolean match, @Param("status") String status, @Param("jobId") String jobId);
}
