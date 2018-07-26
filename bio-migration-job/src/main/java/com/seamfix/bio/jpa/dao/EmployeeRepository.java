package com.seamfix.bio.jpa.dao;

import com.seamfix.bio.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Employee findTopByOrderByCreateDateDesc();

}
