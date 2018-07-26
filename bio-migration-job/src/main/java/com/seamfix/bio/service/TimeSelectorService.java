/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.service;

import com.seamfix.bio.entities.AppUser;
import com.seamfix.bio.entities.Employee;
import com.seamfix.bio.entities.EmployeeAttendanceLog;
import com.seamfix.bio.entities.Location;
import com.seamfix.bio.entities.Project;
import com.seamfix.bio.entities.TransactionRefLog;
import com.seamfix.bio.entities.UserInvitation;
import com.seamfix.bio.jpa.dao.EmployeeAttendanceRepository;
import com.seamfix.bio.jpa.dao.EmployeeRepository;
import com.seamfix.bio.jpa.dao.LocationRepository;
import com.seamfix.bio.jpa.dao.ProjectRepository;
import com.seamfix.bio.jpa.dao.TransactionRefLogRepository;
import com.seamfix.bio.jpa.dao.UserInvitationRepository;
import com.seamfix.bio.jpa.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Service
public class TimeSelectorService {

    @Value("${cron.expression}")
    private String cronExpression;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInvitationRepository userInvitationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeAttendanceRepository attLogRepository;

    @Autowired
    TransactionRefLogRepository tranRefRepository;

    public String getCronTabExpression() {
        String exp = cronExpression;
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            return "0 0/10 * * * ?";
        }
        return exp;
    }

    public Long getProjectLastTime() {
        Long time = new Long(0);
        Project proj = projectRepository.findTopByOrderByCreateDateDesc();
        if (proj != null && proj.getCreateDate() != null) {
            time = proj.getCreateDate().getTime();
        }
        return time;

    }

    public Long getLocationLastTime() {
        Long time = new Long(0);
        Location loc = locationRepository.findTopByOrderByCreateDateDesc();
        if (loc != null && loc.getCreateDate() != null) {
            time = loc.getCreateDate().getTime();
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

    public Long getUserInviteLastTime() {
        Long time = new Long(0);
        UserInvitation invite = userInvitationRepository.findTopByOrderByCreateDateDesc();
        if (invite != null && invite.getCreateDate() != null) {
            time = invite.getCreateDate().getTime();
        }
        return time;

    }

    public Long getEmployeeLastTime() {
        Long time = new Long(0);
        Employee employee = employeeRepository.findTopByOrderByCreateDateDesc();
        if (employee != null && employee.getCreateDate() != null) {
            time = employee.getCreateDate().getTime();
        }
        return time;

    }

    public Long getEmployeeAttLogLastTime() {
        Long time = new Long(0);
        EmployeeAttendanceLog attLog = attLogRepository.findTopByOrderByCreateDateDesc();
        if (attLog != null && attLog.getCreateDate() != null) {
            time = attLog.getCreateDate().getTime();
        }
        return time;

    }

    public Long getTranRefLogLastTime() {
        Long time = new Long(0);
        TransactionRefLog tranLog = tranRefRepository.findTopByOrderByCreateDateDesc();
        if (tranLog != null && tranLog.getCreateDate() != null) {
            time = tranLog.getCreateDate().getTime();
        }
        return time;

    }

}
