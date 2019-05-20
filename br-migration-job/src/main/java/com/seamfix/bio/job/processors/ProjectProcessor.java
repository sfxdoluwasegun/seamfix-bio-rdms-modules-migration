package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.Organisation;
import com.seamfix.bio.job.jpa.dao.OrganisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.sf.bioregistra.entity.Project;
import java.util.Date;

public class ProjectProcessor implements ItemProcessor<Project, com.seamfix.bio.entities.Project> {

    private final OrganisationRepository organisationRepository;

    public ProjectProcessor(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(ProjectProcessor.class);

    @Override
    public com.seamfix.bio.entities.Project process(Project proj) throws Exception {
        log.info("BioRegistra User Projects migration job is in progress!");
        com.seamfix.bio.entities.Project converted = new com.seamfix.bio.entities.Project();
        converted.setPid(proj.getpId());
        if (proj.getOrgId() != null && !proj.getOrgId().trim().isEmpty()) {
            converted.setOrgId(proj.getOrgId());
            Organisation org = organisationRepository.findByOrgId(proj.getOrgId());
            if (org != null) {
                converted.setNewOrgId(org);
            }

        }

        if (proj.getGeoFenceSetting() != null) {
            converted.setGeoFenceEnabled(proj.getGeoFenceSetting().getEnabled());
        }

        converted.setAllowOcr(proj.getAllowOcr());
        converted.setPpId(proj.getPpId() == null || proj.getPpId().trim().isEmpty() ? "" : proj.getPpId());
        converted.setAllowMap(proj.getAllowMap() == null ? Boolean.FALSE : proj.getAllowMap());
        converted.setAllowPrintIdCard(proj.getAllowPrintIdCard() == null ? Boolean.FALSE : proj.getAllowPrintIdCard());
        converted.setBvnCount(proj.getBvnCount());
        converted.setBvnFields(proj.getBvnFields() == null || proj.getBvnFields().isEmpty() ? "" : String.join(",", proj.getBvnFields()));
        converted.setBvnMode(proj.getBvnMode() == null || proj.getBvnMode().trim().isEmpty() ? "" : proj.getBvnMode());
        converted.setCaptureEstimate(proj.getCaptureEstimate() == null ? new Long(0) : proj.getCaptureEstimate());
        converted.setCategory(proj.getCategory() == null || proj.getCategory().trim().isEmpty() ? "" : proj.getCategory());
        converted.setCost(proj.getCost());
        if (proj.getCreated() != null) {
            converted.setCreateDate(new Date(proj.getCreated()));
        }
        converted.setCreatedBy(proj.getCreatorEmail() == null || proj.getCreatorEmail().trim().isEmpty() ? "" : proj.getCreatorEmail());
        converted.setCreatorEmail(proj.getCreatorEmail() == null || proj.getCreatorEmail().trim().isEmpty() ? "" : proj.getCreatorEmail());
        converted.setCycle(proj.getCycle() == null || proj.getCycle().trim().isEmpty() ? "" : proj.getCycle());
        converted.setDescription(proj.getDescription() == null || proj.getDescription().trim().isEmpty() ? "" : proj.getDescription());
        converted.setEnableBvn(proj.isEnableBvn() == null ? Boolean.FALSE : proj.isEnableBvn());
        converted.setEnableLoc(proj.getEnableLoc() == null ? Boolean.FALSE : proj.getEnableLoc());
        converted.setEnablePreviewCapture(proj.getEnablePreviewCapture() == null ? Boolean.FALSE : proj.getEnablePreviewCapture());
        if (proj.getEndDate() != null) {
            converted.setEndDate(new Date(proj.getEndDate()));
        }
        converted.setFingerPrintSyncCount(proj.getFingerPrintSyncCount());
        converted.setFirstSyncCountStatus(proj.isFirstSyncCountStatus() == null ? Boolean.FALSE : proj.isFirstSyncCountStatus());
        converted.setHost(proj.getHost() == null || proj.getHost().trim().isEmpty() ? "" : proj.getHost());
        converted.setLocSetting(proj.getLocSetting() == null || proj.getLocSetting().trim().isEmpty() ? "" : proj.getLocSetting());
        converted.setLogo(proj.getLogo() == null || proj.getLogo().trim().isEmpty() ? "" : proj.getLogo());
        converted.setName(proj.getName() == null || proj.getName().trim().isEmpty() ? "" : proj.getName());
        converted.setOfflineVal(proj.isOfflineVal() == null ? Boolean.FALSE : proj.isOfflineVal());
        converted.setPort(proj.getPort());
        converted.setQParams(proj.getQParams() == null || proj.getQParams().isEmpty() ? "" : String.join(",", proj.getQParams()));
        converted.setRemoteDataWareHouse(proj.getRemoteDataWareHouse() == null ? Boolean.FALSE : proj.getRemoteDataWareHouse());
        converted.setSVersion(proj.getsVersion() == null ? new Double(0) : proj.getsVersion());
        converted.setSkipBvnVal(proj.isSkipBvnVal() == null ? Boolean.FALSE : proj.isSkipBvnVal());
        if (proj.getStartDate() != null) {
            converted.setStartDate(new Date(proj.getStartDate()));
        }
        converted.setSyncCount(proj.getSyncCount());
        converted.setTextSyncCount(proj.getTextSyncCount());
        converted.setType(proj.getType() == null || proj.getType().trim().isEmpty() ? "" : proj.getType());
        converted.setUseCases(proj.getUseCases() == null || proj.getUseCases().isEmpty() ? "" : String.join(",", proj.getUseCases()));
        converted.setUseId(proj.isUseId() == null ? Boolean.FALSE : proj.isUseId());
        converted.setValRequired(proj.isValRequired() == null ? Boolean.FALSE : proj.isValRequired());
        converted.setWebEnrollment(proj.isWebEnrollment() == null ? Boolean.FALSE : proj.isWebEnrollment());
        converted.setVersion(proj.getVersion() == null ? new Double(0) : proj.getVersion());

        return converted;
    }

}
