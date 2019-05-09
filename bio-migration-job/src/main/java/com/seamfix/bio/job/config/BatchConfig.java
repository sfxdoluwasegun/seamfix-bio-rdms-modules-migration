package com.seamfix.bio.job.config;

import com.sf.biocloud.entity.ProspectiveUsers;
import org.slf4j.Logger;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import com.sf.biocloud.entity.*;
import com.seamfix.bio.entities.*;
import com.seamfix.bio.job.events.*;
import com.seamfix.bio.job.jpa.dao.*;
import com.sf.biocloud.entity.Location;
import com.seamfix.bio.job.processors.*;
import com.sf.bioregistra.entity.Country;
import com.sf.bioregistra.entity.OrgType;
import com.sf.bioregistra.entity.BioUser;
import com.sf.bioregistra.entity.Project;
import org.springframework.batch.core.Job;
import com.seamfix.bio.proxy.Organization;
import org.springframework.batch.core.Step;
import org.springframework.data.domain.Sort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort.Direction;
import com.seamfix.bio.job.service.TimeSelectorService;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.core.RepositoryMetadata;
import com.seamfix.bio.extended.mongodb.entities.TransactionRefLog;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import com.seamfix.bio.job.mongodb.dao.IclockerUserExtMongoRepository;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.data.repository.core.support.DefaultCrudMethods;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Configuration
public class BatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    public MongoTemplate mongoTemplate;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    public OrgTypeRepository orgTypeRepository;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    IclockerSubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    UserPhotoRepository userPhotoRepository;

    @Autowired
    UserInvitationRepository userInvitationRepository;

    @Autowired
    public OrganisationRepository organisationRepository;

    @Autowired
    IclockerUserRoleRepository iclockerUserRoleRepository;

    @Autowired
    TransactionRefLogRepository transactionRefLogRepository;

    @Autowired
    EmployeeAttendanceRepository employeeAttendanceRepository;

    @Autowired
    BioRegistraUserRoleRepository bioRegistraUserRoleRepository;

    @Autowired
    CustomerSubscriptionRepository customerSubscriptionRepository;

    @Autowired
    IclockerUserExtMongoRepository mongodbIclockerUserExtRepository;

    @Autowired
    com.seamfix.bio.job.jpa.dao.IclockerUserExtRepository iclockerUserExtRepository;

    @Autowired
    CustomerSubscriptionPaymentHistoryRespository customerSubscriptionPaymentHistoryRespository;

    @Autowired
    TimeSelectorService selector;

    @Autowired
    ProspectiveUsersRepository prospectiveUsersRepository;

    @Autowired
    ReEnrolmentRepository reEnrolmentRepository;

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
        return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new MigrationListener())
                .flow(orgTypeStep())
                .next(orgStep()).next(projectStep())/**/
                .next(countryStateStep()).next(locationStep()).next(userStep())
                .next(reEnrolmentLogStep())
                .next(userPhotoStep()).next(iclockerUserExtStep()).next(iclockerUserRoleStep()).next(bioregistraUserRoleStep()).next(userInvitationStep()).next(employeeStep()).next(attendanceLogStep()).next(tranRefLogStep()).next(prospectiveUserStep()).next(subscriptionStep()).next(subscriptionPaymentHistoryStep()).next(subscriptionPlanStep())
                .end().build();

    }

    @Bean
    @Qualifier(value = "orgTypeStep")
    public Step orgTypeStep() {
        return stepBuilderFactory.get("orgTypeStep").<OrgType, com.seamfix.bio.entities.OrgType>chunk(10)
                .reader(orgTypeReader()).processor(new OrgTypeProcessor()).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).writer(orgTypeWriter()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope 
    public MongoItemReader<OrgType> orgTypeReader() {
        logger.info("Org Type Reader");
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
        return stepBuilderFactory.get("orgStep").<com.seamfix.bio.proxy.Organization, Organisation>chunk(10)
                .reader(organizationReader()).processor(new OrgProcessor(orgTypeRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).writer(orgWriter()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope 
    public MongoItemReader<com.seamfix.bio.proxy.Organization> organizationReader() {
        logger.info("Organization Reader");
        MongoItemReader<com.seamfix.bio.proxy.Organization> reader = new MongoItemReader<>();
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
    @StepScope 
    public MongoItemReader<Project> projectReader() {
        logger.info("Project Reader");
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
    @StepScope 
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
    @StepScope 
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
    @StepScope 
    public MongoItemReader<BioUser> bioUserReader() {
        logger.info("BioUser Reader");
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
    @StepScope 
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
        return stepBuilderFactory.get("iclockerUserExtStep").<BioCloudUserExt, IclockerUserExt>chunk(1)
                .reader(bioUserIclockerUserExtReader()).processor( new IclockerUserExtProcessor(userRepository, mongodbIclockerUserExtRepository, iclockerUserExtRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope 
    public MongoItemReader<BioCloudUserExt> bioUserIclockerUserExtReader() {
        MongoItemReader<BioCloudUserExt> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("biocloud_users");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(BioCloudUserExt.class);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    @Qualifier(value = "iclockerUserRoleStep")
    public Step iclockerUserRoleStep() {
        return stepBuilderFactory.get("iclockerUserRoleStep").<BioUser, IclockerUserRole>chunk(1)
                .reader(bioUserIclockerUserRoleReader()).processor(new IclockerUserRoleProcessor(userRepository, iclockerUserRoleRepository, locationRepository, organisationRepository, mongodbIclockerUserExtRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope 
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
    @StepScope 
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
    @StepScope 
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
    @StepScope 
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
    @StepScope 
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
//        reader.setQuery("{lastModified: {'$gte': +" + selector.getEmployeeAttLogLastTime() + "}}");
        reader.setQuery(new Query(where("lastModified").gte(selector.getEmployeeAttLogLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "tranRefLogStep")
    public Step tranRefLogStep() {
        return stepBuilderFactory.get("tranRefLogStep").<TransactionRefLog, com.seamfix.bio.entities.TransactionRefLog>chunk(5)
                .reader(tranRefLogReader()).processor(new TranRefLogProcessor(transactionRefLogRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope 
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

    @Bean
    @Qualifier(value = "subscriptionStep")
    public Step subscriptionStep() {
        TaskletStep step = stepBuilderFactory.get("subscriptionStep").<Subscription, CustomerSubscription>chunk(5)
                .reader(subscriptionReader()).processor(new CustomerSubscriptionProcessor(customerSubscriptionRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
        step.setAllowStartIfComplete(true);
        return step;
    }

    @Bean
    @StepScope 
    public MongoItemReader<Subscription> subscriptionReader() {
        MongoItemReader<Subscription> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("subscription");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(Subscription.class);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    @Qualifier(value = "subscriptionPaymentHistoryStep")
    public Step subscriptionPaymentHistoryStep() {
        TaskletStep step = stepBuilderFactory.get("subscriptionPaymentHistoryStep").<SubscriptionPaymentHistory, CustomerSubscriptionPaymentHistory>chunk(5)
                .reader(subscriptionPaymentReader()).processor(new CustomerSubscriptionPaymentHistoryProcessor(customerSubscriptionPaymentHistoryRespository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
        step.setAllowStartIfComplete(true);
        return step;
    }

    @Bean
    @StepScope 
    public MongoItemReader<SubscriptionPaymentHistory> subscriptionPaymentReader() {
        MongoItemReader<SubscriptionPaymentHistory> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("subscription_payment_history");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(SubscriptionPaymentHistory.class);
        reader.setQuery(new Query(where("created")
                .gt(selector.getSubscriptionPaymentHistoryLastTime())
                .and("reference").exists(true)));
        return reader;
    }

    @Bean
    @Qualifier(value = "subscriptionPlanStep")
    public Step subscriptionPlanStep() {
        return stepBuilderFactory.get("subscriptionPlanStep")
                .allowStartIfComplete(true)
                .<SubscriptionPlan, IclockerSubscriptionPlan>chunk(5)
                .reader(subscriptionPlanReader())
                .processor(new IclockerSubscriptionPlanProcessor(subscriptionPlanRepository))
                .faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant()
                .skipPolicy(dataIntegrityViolationExceptionSkipper())
                .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<SubscriptionPlan> subscriptionPlanReader() {
        MongoItemReader<SubscriptionPlan> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("subscription_plan");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(SubscriptionPlan.class);
        reader.setQuery(new Query(where("discount").ne(null)));
        return reader;
    }

    @Bean
    @Qualifier(value = "prospectiveUserStep")
    public Step prospectiveUserStep() {
        return stepBuilderFactory.get("prospectiveUserStep").<ProspectiveUsers, com.seamfix.bio.entities.ProspectiveUsers>chunk(5)
                .reader(prospectiveUserReader()).processor(new ProspectiveUsersProcessor(prospectiveUsersRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope
    public MongoItemReader<ProspectiveUsers> prospectiveUserReader() {
        MongoItemReader<ProspectiveUsers> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("prospective_users");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(ProspectiveUsers.class);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    @Qualifier(value = "reEnrolmentLogStep")
    public Step reEnrolmentLogStep() {
        return stepBuilderFactory.get("reEnrolmentLogStep").<ReEnrollmentLog, ReEnrolmentLog>chunk(5)
                .reader(reEnrolmentLogReader()).processor(new ReEnrolmentLogProcessor(reEnrolmentRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope
    public MongoItemReader<ReEnrollmentLog> reEnrolmentLogReader() {
        MongoItemReader<ReEnrollmentLog> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("reenrollment-log");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(ReEnrollmentLog.class);
        reader.setQuery(new Query(where("lastModified").gt(selector.getReEnrolmentLogLastModifiedTime())));
        return reader;
    }

}
