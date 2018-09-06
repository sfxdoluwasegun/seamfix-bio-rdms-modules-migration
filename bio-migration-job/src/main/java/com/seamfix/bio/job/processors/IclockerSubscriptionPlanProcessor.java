package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.IclockerSubscriptionPlan;
import com.seamfix.bio.job.jpa.dao.IclockerSubscriptionPlanRepository;
import com.sf.biocloud.entity.SubscriptionPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import static com.seamfix.bio.job.util.StringUtil.isNullOrEmpty;

public class IclockerSubscriptionPlanProcessor implements ItemProcessor<SubscriptionPlan, IclockerSubscriptionPlan> {
    private static final Logger logger = LoggerFactory.getLogger(IclockerSubscriptionPlanProcessor.class);

    private IclockerSubscriptionPlanRepository repository;

    public IclockerSubscriptionPlanProcessor(IclockerSubscriptionPlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public IclockerSubscriptionPlan process(SubscriptionPlan subscriptionPlan) throws Exception {
        logger.info("Iclocker Subscription Plan Migration in Progress...");
        IclockerSubscriptionPlan converted = new IclockerSubscriptionPlan();
        IclockerSubscriptionPlan existingPlan = repository.findByPlanId(subscriptionPlan.getPlanId());
        if (existingPlan != null) {
            setNewFields(existingPlan, subscriptionPlan);
            converted = existingPlan;
        } else {
            setNewFields(converted, subscriptionPlan);
        }
        repository.save(converted);
        return converted;
    }

    public void setNewFields(IclockerSubscriptionPlan converted, SubscriptionPlan subscriptionPlan) {
        converted.setName(isNullOrEmpty(subscriptionPlan.getName()) ? "" : subscriptionPlan.getName());
        converted.setDescription(isNullOrEmpty(subscriptionPlan.getDescription()) ? "" : subscriptionPlan.getDescription());
        converted.setDiscount(subscriptionPlan.getDiscount());
        converted.setPricePerMonth(subscriptionPlan.getPricePerMonth());
        converted.setPricePerAnnum(subscriptionPlan.getPricePerAnnum());
        converted.setMaxAttendeeThreshold(subscriptionPlan.getMaxAttendeeThreshold());
        converted.setPlanId(isNullOrEmpty(subscriptionPlan.getPlanId()) ? "" : subscriptionPlan.getPlanId());
    }


}
