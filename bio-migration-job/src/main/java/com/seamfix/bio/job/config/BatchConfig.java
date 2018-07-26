package com.seamfix.bio.job.config;

import com.seamfix.bio.entities.BioRegistraUserRole;
import com.seamfix.bio.entities.CountryObj;
import com.seamfix.bio.entities.Employee;
import com.seamfix.bio.entities.EmployeeAttendanceLog;
import com.seamfix.bio.entities.IclockerUserExt;
import com.seamfix.bio.entities.IclockerUserRole;
import com.seamfix.bio.entities.Organisation;
import com.seamfix.bio.entities.UserInvitation;
import com.seamfix.bio.job.events.ContraintViolationExceptionProcessorSkipper;
import com.seamfix.bio.job.events.DataIntegrityViolationExceptionSkipper;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import com.seamfix.bio.job.events.Listener;
import com.seamfix.bio.job.events.NullPointerExceptionProcessorSkipper;
import com.seamfix.bio.job.processors.CountryProcessor;
import com.seamfix.bio.job.processors.OrgProcessor;
import com.seamfix.bio.job.processors.OrgTypeProcessor;
import com.seamfix.bio.job.processors.ProjectProcessor;
import com.seamfix.bio.jpa.dao.CountryRepository;
import com.seamfix.bio.jpa.dao.OrgTypeRepository;
import com.seamfix.bio.jpa.dao.OrganisationRepository;
import com.seamfix.bio.jpa.dao.ProjectRepository;
import com.seamfix.bio.jpa.dao.StateRepository;
import com.sf.bioregistra.entity.Country;
import com.sf.bioregistra.entity.OrgType;
import com.seamfix.bio.extended.mongodb.entities.Organization;
import com.seamfix.bio.extended.mongodb.entities.TransactionRefLog;
import com.seamfix.bio.job.processors.BioRegistraUserRoleProcessor;
import com.seamfix.bio.job.processors.BioUserProcessor;
import com.seamfix.bio.job.processors.EmployeeAttendanceLogProcessor;
import com.seamfix.bio.job.processors.EmployeeProcessor;
import com.seamfix.bio.job.processors.IclockerUserExtProcessor;
import com.seamfix.bio.job.processors.IclockerUserRoleProcessor;
import com.seamfix.bio.job.processors.LocationProcessor;
import com.seamfix.bio.job.processors.TranRefLogProcessor;
import com.seamfix.bio.job.processors.UserInviteProcessor;
import com.seamfix.bio.job.processors.UserPhotoProcessor;
import com.seamfix.bio.jpa.dao.BioRegistraUserRoleRepository;
import com.seamfix.bio.jpa.dao.EmployeeAttendanceRepository;
import com.seamfix.bio.jpa.dao.EmployeeRepository;
import com.seamfix.bio.jpa.dao.IclockerUserRoleRepository;
import com.seamfix.bio.jpa.dao.LocationRepository;
import com.seamfix.bio.jpa.dao.TransactionRefLogRepository;
import com.seamfix.bio.jpa.dao.UserInvitationRepository;
import com.seamfix.bio.jpa.dao.UserPhotoRepository;
import com.seamfix.bio.jpa.dao.UserRepository;
import com.sf.biocloud.entity.Location;
import com.sf.bioregistra.entity.BioUser;
import com.sf.bioregistra.entity.Project;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.HashMap;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultCrudMethods;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import com.seamfix.bio.mongodb.dao.IclockerUserExtMongoRepository;
import com.seamfix.bio.service.TimeSelectorService;
import com.sf.biocloud.entity.AttendanceLog;
import com.sf.biocloud.entity.Attendee;
import com.sf.biocloud.entity.BioCloudUserInvite;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public MongoTemplate mongoTemplate;

    @Autowired
    public OrgTypeRepository orgTypeRepository;

    @Autowired
    public OrganisationRepository organisationRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPhotoRepository userPhotoRepository;

    @Autowired
    IclockerUserExtMongoRepository mongodbIclockerUserExtRepository;

    @Autowired
    com.seamfix.bio.jpa.dao.IclockerUserExtRepository iclockerUserExtRepository;
    @Autowired
    IclockerUserRoleRepository iclockerUserRoleRepository;
    @Autowired
    BioRegistraUserRoleRepository bioRegistraUserRoleRepository;

    @Autowired
    UserInvitationRepository userInvitationRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeAttendanceRepository employeeAttendanceRepository;

    @Autowired
    TransactionRefLogRepository transactionRefLogRepository;

    @Autowired
    TimeSelectorService selector;

    @Bean
    public SkipPolicy nullPointerExceptionProcessorSkipper() {
        return new NullPointerExceptionProcessorSkipper();
    }

    @Bean
    public SkipPolicy contraintViolationExceptionProcessorSkipper() {
        return new ContraintViolationExceptionProcessorSkipper();
    }

    @Bean
    public SkipPolicy dataIntegrityViolationExceptionSkipper() {
        return new DataIntegrityViolationExceptionSkipper();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new Listener())
                .flow(orgTypeStep()).next(orgStep()).next(projectStep())./**/
                next(countryStateStep()).next(locationStep()).next(userStep()).
                next(userPhotoStep()).next(iclockerUserExtStep()).next(iclockerUserRoleStep()).next(bioregistraUserRoleStep()).next(userInvitationStep()).next(employeeStep()).next(attendanceLogStep()).next(tranRefLogStep()).end().build();

    }

    @Bean
    @Qualifier(value = "orgTypeStep")
    public Step orgTypeStep() {
        return stepBuilderFactory.get("orgTypeStep").<OrgType, com.seamfix.bio.entities.OrgType>chunk(10)
                .reader(orgTypeReader()).processor(new OrgTypeProcessor()).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).writer(orgTypeWriter()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<OrgType> orgTypeReader() {
        MongoItemReader<OrgType> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("org_type");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(OrgType.class);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    public RepositoryItemWriter<com.seamfix.bio.entities.OrgType> orgTypeWriter() {
        RepositoryMetadata u = new DefaultRepositoryMetadata(OrgTypeRepository.class);
        DefaultCrudMethods defaultCrudMethods = new DefaultCrudMethods(u);
        RepositoryItemWriter<com.seamfix.bio.entities.OrgType> writer = new RepositoryItemWriter<>();
        writer.setRepository(orgTypeRepository);
        writer.setMethodName(defaultCrudMethods.getSaveMethod().get().getName());
        return writer;
    }

    @Bean
    @Qualifier(value = "orgStep")
    public Step orgStep() {
        return stepBuilderFactory.get("orgStep").<Organization, Organisation>chunk(10)
                .reader(organizationReader()).processor(new OrgProcessor(orgTypeRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).writer(orgWriter()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<Organization> organizationReader() {
        MongoItemReader<Organization> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("organization");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(Organization.class);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    public RepositoryItemWriter<Organisation> orgWriter() {
        RepositoryMetadata u = new DefaultRepositoryMetadata(OrganisationRepository.class);
        DefaultCrudMethods defaultCrudMethods = new DefaultCrudMethods(u);
        RepositoryItemWriter<Organisation> writer = new RepositoryItemWriter<>();
        writer.setRepository(organisationRepository);
        writer.setMethodName(defaultCrudMethods.getSaveMethod().get().getName());
        return writer;
    }

    @Bean
    @Qualifier(value = "projectStep")
    public Step projectStep() {
        return stepBuilderFactory.get("projectStep").<Project, com.seamfix.bio.entities.Project>chunk(10)
                .reader(projectReader()).processor(new ProjectProcessor(organisationRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).writer(projectWriter()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<Project> projectReader() {
        MongoItemReader<Project> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("projects");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(Project.class);
        reader.setQuery(new Query(where("created").gt(selector.getProjectLastTime())));
        return reader;
    }

    @Bean
    public RepositoryItemWriter<com.seamfix.bio.entities.Project> projectWriter() {
        RepositoryMetadata u = new DefaultRepositoryMetadata(ProjectRepository.class);
        DefaultCrudMethods defaultCrudMethods = new DefaultCrudMethods(u);
        RepositoryItemWriter<com.seamfix.bio.entities.Project> writer = new RepositoryItemWriter<>();
        writer.setRepository(projectRepository);
        writer.setMethodName(defaultCrudMethods.getSaveMethod().get().getName());
        return writer;
    }

    @Bean
    @Qualifier(value = "countryStateStep")
    public Step countryStateStep() {
        return stepBuilderFactory.get("countryStateStep").<Country, CountryObj>chunk(1)
                .reader(countryStateReader()).processor(new CountryProcessor(countryRepository, stateRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<Country> countryStateReader() {
        MongoItemReader<Country> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("countries");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(Country.class);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    @Qualifier(value = "locationStep")
    public Step locationStep() {
        return stepBuilderFactory.get("locationStep").<Location, com.seamfix.bio.entities.Location>chunk(5)
                .reader(locationReader()).processor(new LocationProcessor(organisationRepository, countryRepository, stateRepository, locationRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<Location> locationReader() {
        MongoItemReader<Location> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("locations");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(Location.class);
        reader.setQuery(new Query(where("created").gt(selector.getLocationLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "userStep")
    public Step userStep() {
        return stepBuilderFactory.get("userStep").<BioUser, com.seamfix.bio.entities.AppUser>chunk(1)
                .reader(bioUserReader()).processor(new BioUserProcessor(userRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<BioUser> bioUserReader() {
        MongoItemReader<BioUser> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("users");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(BioUser.class);
        reader.setQuery(new Query(where("created").gt(selector.getUserLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "userPhotoStep")
    public Step userPhotoStep() {
        return stepBuilderFactory.get("userPhotoStep").<BioUser, com.seamfix.bio.entities.UserPhoto>chunk(1)
                .reader(bioUserPhotoReader()).processor(new UserPhotoProcessor(userRepository, userPhotoRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<BioUser> bioUserPhotoReader() {
        MongoItemReader<BioUser> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("users");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(BioUser.class);
        reader.setQuery(new Query(where("created").gt(selector.getUserLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "iclockerUserExtStep")
    public Step iclockerUserExtStep() {
        return stepBuilderFactory.get("iclockerUserExtStep").<BioUser, IclockerUserExt>chunk(1)
                .reader(bioUserIclockerUserExtReader()).processor(new IclockerUserExtProcessor(userRepository, mongodbIclockerUserExtRepository, iclockerUserExtRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<BioUser> bioUserIclockerUserExtReader() {
        MongoItemReader<BioUser> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("users");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(BioUser.class);
        reader.setQuery(new Query(where("created").gt(selector.getUserLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "iclockerUserRoleStep")
    public Step iclockerUserRoleStep() {
        return stepBuilderFactory.get("iclockerUserRoleStep").<BioUser, IclockerUserRole>chunk(1)
                .reader(bioUserIclockerUserRoleReader()).processor(new IclockerUserRoleProcessor(userRepository, iclockerUserRoleRepository, locationRepository, organisationRepository, mongodbIclockerUserExtRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<BioUser> bioUserIclockerUserRoleReader() {
        MongoItemReader<BioUser> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("users");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(BioUser.class);
        reader.setQuery(new Query(where("created").gt(selector.getUserLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "bioregistraUserRoleStep")
    public Step bioregistraUserRoleStep() {
        return stepBuilderFactory.get("bioregistraUserRoleStep").<BioUser, BioRegistraUserRole>chunk(1)
                .reader(bioregistraUseRoleReader()).processor(new BioRegistraUserRoleProcessor(userRepository, bioRegistraUserRoleRepository, projectRepository, organisationRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<BioUser> bioregistraUseRoleReader() {
        MongoItemReader<BioUser> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("users");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(BioUser.class);
        reader.setQuery(new Query(where("created").gt(selector.getUserLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "userInvitationStep")
    public Step userInvitationStep() {
        return stepBuilderFactory.get("userInvitationStep").<BioCloudUserInvite, UserInvitation>chunk(1)
                .reader(userInvitationReader()).processor(new UserInviteProcessor(locationRepository, organisationRepository, userInvitationRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<BioCloudUserInvite> userInvitationReader() {
        MongoItemReader<BioCloudUserInvite> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("user_invitations");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(BioCloudUserInvite.class);
        reader.setQuery(new Query(where("created").gt(selector.getUserInviteLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "employeeStep")
    public Step employeeStep() {
        return stepBuilderFactory.get("employeeStep").<Attendee, Employee>chunk(1)
                .reader(employeeReader()).processor(new EmployeeProcessor(locationRepository, organisationRepository, employeeRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<Attendee> employeeReader() {
        MongoItemReader<Attendee> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("attendee");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(Attendee.class);
        reader.setQuery(new Query(where("created").gt(selector.getEmployeeLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "attendanceLogStep")
    public Step attendanceLogStep() {
        return stepBuilderFactory.get("attendanceLogStep").<AttendanceLog, EmployeeAttendanceLog>chunk(5)
                .reader(attendanceLogReader()).processor(new EmployeeAttendanceLogProcessor(locationRepository, organisationRepository, employeeAttendanceRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<AttendanceLog> attendanceLogReader() {
        MongoItemReader<AttendanceLog> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("attendance_log");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(AttendanceLog.class);
        reader.setQuery(new Query(where("created").gt(selector.getEmployeeAttLogLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "tranRefLogStep")
    public Step tranRefLogStep() {
        return stepBuilderFactory.get("tranRefLogStep").<TransactionRefLog, com.seamfix.bio.entities.TransactionRefLog>chunk(5)
                .reader(tranRefLogReader()).processor(new TranRefLogProcessor(transactionRefLogRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    public MongoItemReader<TransactionRefLog> tranRefLogReader() {
        MongoItemReader<TransactionRefLog> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("transaction_ref_log");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(TransactionRefLog.class);
        reader.setQuery(new Query(where("created").gt(selector.getTranRefLogLastTime())));
        return reader;
    }

}
