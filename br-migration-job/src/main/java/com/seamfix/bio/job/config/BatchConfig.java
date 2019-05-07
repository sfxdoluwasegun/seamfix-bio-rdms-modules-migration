package com.seamfix.bio.job.config;

import org.slf4j.Logger;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import com.seamfix.bio.entities.*;
import com.seamfix.bio.job.events.*;
import com.seamfix.bio.job.jpa.dao.*;
import com.seamfix.bio.job.processors.*;
import com.sf.bioregistra.entity.Country;
import com.sf.bioregistra.entity.BioUser;
import com.sf.bioregistra.entity.Project;
import org.springframework.batch.core.Job;
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
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.data.repository.core.support.DefaultCrudMethods;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import com.sf.bioregistra.entity.subscription.SubscriptionType;

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
    BioRegistraUserRoleRepository bioRegistraUserRoleRepository;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    public OrganisationRepository organisationRepository;

    @Autowired
    public SubscriptionTypeRepository subscriptionTypeRepository;

    @Autowired
    CapturedDataRepository capturedDataRepository;

    @Autowired
    CapturedDataDemoGraphicsRepository capturedDataDemoGraphicsRepository;

    @Autowired
    DeviceRepository deviceRepository;

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
        return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new MigrationListener())
                .flow(subscriptionTypeStep()).next(orgStep())
                .next(projectStep())
                .next(countryStateStep())
                .next(userStep())
                .next(bioregistraUserRoleStep())
                .next(capturedDataStep())
                .next(deviceStep()).end().build();

    }

    @Bean
    @Qualifier(value = "subscriptionTypeStep")
    public Step subscriptionTypeStep() {
        return stepBuilderFactory.get("subscriptionTypeStep").<SubscriptionType, com.seamfix.bio.entities.SubscriptionType>chunk(10)
                .reader(subscriptionTypeReader()).processor(new SubscriptionTypeProcessor()).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).writer(subscriptionTypeWriter()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope
    public MongoItemReader<SubscriptionType> subscriptionTypeReader() {
        logger.info("SubscriptionType Reader");
        MongoItemReader<SubscriptionType> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("subscription_type");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(SubscriptionType.class);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    public RepositoryItemWriter<com.seamfix.bio.entities.SubscriptionType> subscriptionTypeWriter() {
        RepositoryMetadata u = new DefaultRepositoryMetadata(SubscriptionTypeRepository.class);
        DefaultCrudMethods defaultCrudMethods = new DefaultCrudMethods(u);
        RepositoryItemWriter<com.seamfix.bio.entities.SubscriptionType> writer = new RepositoryItemWriter<>();
        writer.setRepository(subscriptionTypeRepository);
        writer.setMethodName(defaultCrudMethods.getSaveMethod().get().getName());
        return writer;
    }

    @Bean
    @Qualifier(value = "orgStep")
    public Step orgStep() {
        return stepBuilderFactory.get("orgStep").<com.seamfix.bio.extended.mongodb.entities.Organization, Organisation>chunk(10)
                .reader(organizationReader()).processor(new OrgProcessor(subscriptionTypeRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).writer(orgWriter()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).build();
    }

    @Bean
    @StepScope
    public MongoItemReader<com.seamfix.bio.extended.mongodb.entities.Organization> organizationReader() {
        logger.info("Organization Reader");
        MongoItemReader<com.seamfix.bio.extended.mongodb.entities.Organization> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("organization");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(com.seamfix.bio.extended.mongodb.entities.Organization.class);
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
                .reader(countryStateReader()).processor(new CountryProcessor(countryRepository, stateRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).writer(new NoOpItemWriter()).build();
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
    @Qualifier(value = "userStep")
    public Step userStep() {
        return stepBuilderFactory.get("userStep").<BioUser, com.seamfix.bio.entities.AppUser>chunk(1)
                .reader(bioUserReader()).processor(new BioUserProcessor(userRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).writer(new NoOpItemWriter()).build();
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
    @Qualifier(value = "bioregistraUserRoleStep")
    public Step bioregistraUserRoleStep() {
        return stepBuilderFactory.get("bioregistraUserRoleStep").<BioUser, BioRegistraUserRole>chunk(1)
                .reader(bioregistraUseRoleReader()).processor(new BioRegistraUserRoleProcessor(userRepository, bioRegistraUserRoleRepository, projectRepository, organisationRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).writer(new NoOpItemWriter()).build();
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
        reader.setQuery(new Query(where("created").gt(selector.getMongodbUserLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "capturedDataStep")
    public Step capturedDataStep() {
        return stepBuilderFactory.get("capturedDataStep").<com.seamfix.bio.extended.mongodb.entities.CapturedData, CapturedData>chunk(5)
                .reader(capturedDataReader()).processor(new CapturedDataProcessor(capturedDataRepository, capturedDataDemoGraphicsRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).writer(new NoOpItemWriter()).build();
    }

    @Bean
    @StepScope
    public MongoItemReader<com.seamfix.bio.extended.mongodb.entities.CapturedData> capturedDataReader() {
        MongoItemReader<com.seamfix.bio.extended.mongodb.entities.CapturedData> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("captured_data");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(com.seamfix.bio.extended.mongodb.entities.CapturedData.class);
        reader.setQuery(new Query(where("created").gt(selector.getCapturedDataLastTime())));
        return reader;
    }

    @Bean
    @Qualifier(value = "deviceStep")
    public Step deviceStep() {
        return stepBuilderFactory.get("deviceStep").<com.sf.bioregistra.entity.Device, Device>chunk(2)
                .reader(deviceReader()).processor(new DeviceProcessor(deviceRepository)).faultTolerant().skipPolicy(nullPointerExceptionProcessorSkipper()).faultTolerant().skipPolicy(dataIntegrityViolationExceptionSkipper()).writer(new NoOpItemWriter()).build();
    }

    @Bean
    @StepScope
    public MongoItemReader<com.sf.bioregistra.entity.Device> deviceReader() {
        MongoItemReader<com.sf.bioregistra.entity.Device> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection("devices");
        reader.setSort(new HashMap<String, Sort.Direction>() {
            {
                put("_id", Direction.DESC);
            }
        });
        reader.setTargetType(com.sf.bioregistra.entity.Device.class);
        reader.setQuery(new Query(where("created").gt(selector.getDeviceLastTime())));
        return reader;
    }

}
