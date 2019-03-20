package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.Shift;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    Shift findByShiftId(String shiftId);

    Shift findByNameAndCreatedBy(String name, String createdBy);

}
