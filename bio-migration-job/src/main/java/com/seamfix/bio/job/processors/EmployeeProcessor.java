package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.Employee;
import com.seamfix.bio.entities.Location;
import com.seamfix.bio.entities.Organisation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.jpa.dao.EmployeeRepository;
import com.seamfix.bio.jpa.dao.LocationRepository;
import com.seamfix.bio.jpa.dao.OrganisationRepository;
import com.sf.biocloud.entity.Attendee;
import java.util.Date;

public class EmployeeProcessor implements ItemProcessor<Attendee, Employee> {

    private static final Logger log = LoggerFactory.getLogger(EmployeeProcessor.class);

    private final LocationRepository locationRepository;
    private final OrganisationRepository organisationRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeProcessor(LocationRepository locationRepository, OrganisationRepository organisationRepository, EmployeeRepository employeeRepository) {
        this.locationRepository = locationRepository;
        this.organisationRepository = organisationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee process(Attendee attendee) throws Exception {
        log.info("Employee migration job is in progress!");
        Employee converted = new Employee();
        converted.setActive(attendee.isActive());
        converted.setApprovalStatus(attendee.getApprovalStatus());
        converted.setAttendeeStatus(attendee.getAttendeeStatus());
        if (attendee.getCreated() != null) {
            converted.setCreateDate(new Date(attendee.getCreated()));
        }
        converted.setEmail(attendee.getEmail() == null || attendee.getEmail().trim().isEmpty() ? "" : attendee.getEmail());
        converted.setFirstName(attendee.getFirstName() == null || attendee.getFirstName().trim().isEmpty() ? "" : attendee.getFirstName());
        converted.setGender(attendee.getGender() == null || attendee.getGender().trim().isEmpty() ? "" : attendee.getGender());
        converted.setLastName(attendee.getLastName() == null || attendee.getLastName().trim().isEmpty() ? "" : attendee.getLastName());
        converted.setLocId(attendee.getLocId() == null || attendee.getLocId().trim().isEmpty() ? "" : attendee.getLocId());
        converted.setOrgId(attendee.getOrgId() == null || attendee.getOrgId().trim().isEmpty() ? "" : attendee.getOrgId());
        converted.setPhoneNumber(attendee.getPhoneNumber() == null || attendee.getPhoneNumber().trim().isEmpty() ? "" : attendee.getPhoneNumber());
        long newOrgId = 0;
        long newLocId = 0;
        Organisation org = organisationRepository.findByOrgId(attendee.getOrgId());
        if (org != null) {
            newOrgId = org.getId();
        }
        Location loc = locationRepository.findByLocId(attendee.getLocId());
        if (loc != null) {
            newLocId = loc.getId();
        }
        converted.setNewLocId(newLocId);
        converted.setNewOrgId(newOrgId);
        employeeRepository.save(converted);
        return converted;
    }

}
