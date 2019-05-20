/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.job.service;

import com.seamfix.bio.job.jpa.dao.ProjectRepository;
import com.seamfix.bio.job.jpa.dao.UserRepository;
import com.seamfix.bio.entities.*;
import com.seamfix.bio.job.jpa.dao.CapturedDataRepository;
import com.seamfix.bio.job.jpa.dao.DeviceRepository;
import com.seamfix.bio.job.mongodb.dao.BioUserRepository;
import com.sf.bioregistra.entity.BioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Service
public class TimeSelectorService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CapturedDataRepository capturedDataRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    BioUserRepository mongoDbUserRepository;

    public Long getProjectLastTime() {
        Long time = new Long(0);
        Project proj = projectRepository.findTopByOrderByCreateDateDesc();
        if (proj != null && proj.getCreateDate() != null) {
            time = proj.getCreateDate().getTime();
        }
        return time;

    }

    public Long getUserLastTime() {
        Long time = new Long(0);
        AppUser user = userRepository.findTopByOrderByCreateDateDesc();
        if (user != null && user.getCreateDate() != null) {
            time = user.getCreateDate().getTime();
        }
        return time;

    }

    public Long getMongodbUserLastTime() {
        Long time = new Long(0);
        BioUser user = mongoDbUserRepository.findTopByOrderByCreatedDesc();
        if (user != null && user.getCreated() != null) {
            time = user.getCreated();
        }
        return time;

    }

    public Long getCapturedDataLastTime() {
        Long time = new Long(0);
        CapturedData data = capturedDataRepository.findTopByOrderByCreateDateDesc();
        if (data != null && data.getCreateDate() != null) {
            time = data.getCreateDate().getTime();
        }
        return time;

    }

    public Long getDeviceLastTime() {
        Long time = new Long(0);
        Device device = deviceRepository.findTopByOrderByCreateDateDesc();
        if (device != null && device.getCreateDate() != null) {
            time = device.getCreateDate().getTime();
        }
        return time;

    }

}
