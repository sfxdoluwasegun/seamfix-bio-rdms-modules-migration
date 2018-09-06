/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.job.service;

import com.seamfix.bio.job.jpa.dao.CustomerSubscriptionPaymentHistoryRespository;
import com.seamfix.bio.job.jpa.dao.UserInvitationRepository;
import com.seamfix.bio.job.jpa.dao.ProjectRepository;
import com.seamfix.bio.job.jpa.dao.LocationRepository;
import com.seamfix.bio.job.jpa.dao.CustomerSubscriptionRepository;
import com.seamfix.bio.job.jpa.dao.UserRepository;
import com.seamfix.bio.job.jpa.dao.EmployeeAttendanceRepository;
import com.seamfix.bio.job.jpa.dao.EmployeeRepository;
import com.seamfix.bio.job.jpa.dao.TransactionRefLogRepository;
import com.seamfix.bio.entities.*;
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

    @Autowired
    private CustomerSubscriptionRepository customerSubscriptionRepository;

    @Autowired
    private CustomerSubscriptionPaymentHistoryRespository customerSubscriptionPaymentHistoryRespository;

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

    public Long getSubscriptionLastTime() {
        Long time = new Long(0);
        CustomerSubscription customerSubscription = customerSubscriptionRepository.findTopByOrderByCreateDateDesc();
        if (customerSubscription != null && customerSubscription.getCreateDate() != null) {
            time = customerSubscription.getCreateDate().getTime();
        }
        return time;
    }

    public Long getSubscriptionPaymentHistoryLastTime() {
        Long time = new Long(0);
        CustomerSubscriptionPaymentHistory paymentHistory = customerSubscriptionPaymentHistoryRespository.findTopByOrderByCreateDateDesc();
        if (paymentHistory != null && paymentHistory.getCreateDate() != null) {
            time = paymentHistory.getCreateDate().getTime();
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
