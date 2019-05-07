package com.seamfix.bio.job.jpa.dao;

import com.seamfix.bio.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    public Device findTopByOrderByCreateDateDesc();

}
