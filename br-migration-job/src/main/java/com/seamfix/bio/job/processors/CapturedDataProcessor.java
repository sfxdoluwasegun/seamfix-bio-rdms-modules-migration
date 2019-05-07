package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.CapturedData;
import com.seamfix.bio.entities.CapturedDataDemoGraphics;
import com.seamfix.bio.extended.mongodb.entities.DataUnit;
import com.seamfix.bio.job.jpa.dao.CapturedDataDemoGraphicsRepository;
import com.seamfix.bio.job.jpa.dao.CapturedDataRepository;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import java.util.Date;

public class CapturedDataProcessor implements ItemProcessor<com.seamfix.bio.extended.mongodb.entities.CapturedData, CapturedData> {
    
    private final CapturedDataRepository capturedDataRepository;
    private final CapturedDataDemoGraphicsRepository capturedDataDemoGraphicsRepository;
    
    public CapturedDataProcessor(CapturedDataRepository capturedDataRepository, CapturedDataDemoGraphicsRepository capturedDataDemoGraphicsRepository) {
        this.capturedDataRepository = capturedDataRepository;
        this.capturedDataDemoGraphicsRepository = capturedDataDemoGraphicsRepository;
    }
    
    private static final Logger log = LoggerFactory.getLogger(CapturedDataProcessor.class);
    
    @Override
    public CapturedData process(com.seamfix.bio.extended.mongodb.entities.CapturedData capturedData) throws Exception {
        log.info("BioRegistra Captured Data migration job is in progress!");
        CapturedData converted = new CapturedData();
        converted.setAppVersion(capturedData.getAppVersion() == null ? new Double("0") : capturedData.getAppVersion());
        converted.setCId(capturedData.getCId() == null ? "" : capturedData.getCId());
        converted.setCaptureAddr(capturedData.getCaptureAddr() == null ? "" : capturedData.getCaptureAddr());
        converted.setCaptured(capturedData.getCaptured() == null ? new Long("0") : capturedData.getCaptured());
        converted.setCost(capturedData.getCost());
        if (capturedData.getCreated() != null) {
            converted.setCreateDate(new Date(capturedData.getCreated()));
        }
        converted.setCreatedBy(capturedData.getCreatedBy() == null ? "" : capturedData.getCreatedBy());
        converted.setCustomId(capturedData.getCustomId() == null ? "" : capturedData.getCustomId());
        converted.setEuniqueId(capturedData.getEuniqueId() == null ? "" : capturedData.getEuniqueId());
        converted.setFlag(capturedData.getFlag() == null ? "" : capturedData.getFlag());
        converted.setGeoFenceId(capturedData.getGeoFenceId() == null ? "" : capturedData.getGeoFenceId());
        converted.setGeofenceStatus(capturedData.getGeofenceStatus() == null ? "" : capturedData.getGeofenceStatus());
        if (capturedData.getLastModified() != null) {
            converted.setLastModified(new Date(capturedData.getLastModified()));
        }
        converted.setLatitude(capturedData.getLatitude() == null ? "" : capturedData.getLatitude());
        converted.setLocation(capturedData.getLocation() == null ? "" : capturedData.getLocation());
        converted.setLongitude(capturedData.getLongitude() == null ? "" : capturedData.getLongitude());
        converted.setMachineId(capturedData.getMachineId() == null ? "" : capturedData.getMachineId());
        converted.setMachineSpecs(capturedData.getMachineSpecs() == null ? "" : capturedData.getMachineSpecs());
        if (capturedData.getModified() != null) {
            converted.setModified(new Date(capturedData.getModified()));
        }
        
        converted.setPId(capturedData.getPId() == null ? "" : capturedData.getPId());
        converted.setPpId(capturedData.getPpId() == null ? "" : capturedData.getPpId());
        converted.setPreId(capturedData.getPreId() == null ? "" : capturedData.getPreId());
        converted.setPrevId(capturedData.getPrevId() == null ? "" : capturedData.getPrevId());
        converted.setPuniqueId(capturedData.getPuniqueId() == null ? "" : capturedData.getPuniqueId());
        converted.setPushRef(capturedData.getPushRef() == null ? "" : capturedData.getPushRef());
        converted.setPushStatus(capturedData.isPushStatus());
        converted.setReason(capturedData.getReason() == null ? "" : capturedData.getReason());
        converted.setSField(capturedData.getSField() == null ? "" : capturedData.getSField());
        converted.setTag(capturedData.getTag() == null ? "" : capturedData.getTag());
        converted.setUniqueId(capturedData.getUniqueId() == null ? "" : capturedData.getUniqueId());
        converted.setUseCase(capturedData.getUseCase() == null ? "" : capturedData.getUseCase());
        converted.setVerified(capturedData.getVerified());
        converted = capturedDataRepository.save(converted);
        
        ArrayList<DataUnit> demoGraphicsData = capturedData.getText();
        if (demoGraphicsData != null && !demoGraphicsData.isEmpty()) {
            for (DataUnit data : demoGraphicsData) {
                
                CapturedDataDemoGraphics demo = new CapturedDataDemoGraphics();
                demo.setTextId(data.getId() == null ? "" : data.getId());
                demo.setUniqueId(converted.getUniqueId());
                demo.setCapturedDataId(converted);
                if (capturedData.getCreated() != null) {
                    demo.setCreateDate(new Date(capturedData.getCreated()));
                }
                demo.setCreatedBy(capturedData.getCreatedBy() == null ? "" : capturedData.getCreatedBy());
                demo.setDeleted(converted.isDeleted());
                demo.setDeviceName(data.getDeviceName() == null ? "" : data.getDeviceName());
                demo.setDeviceRes(data.getDeviceRes() == null ? "" : data.getDeviceRes());
                demo.setExt(data.getExt() == null ? "" : data.getExt());
                demo.setLabel(data.getLabel() == null ? "" : data.getLabel());
                if (capturedData.getLastModified() != null) {
                    demo.setLastModified(new Date(capturedData.getLastModified()));
                }
                demo.setNewValue(data.isNewValue());
                demo.setSkipReason(data.getSkipReason() == null ? "" : data.getSkipReason());
                demo.setThreshold(data.getThreshold() == null ? "" : data.getThreshold());
                demo.setType(data.getType() == null ? "" : data.getType());
                demo.setType(data.getValue() == null ? "" : data.getValue());
                capturedDataDemoGraphicsRepository.save(demo);
                
            }
            
        }
        
        return converted;
    }
    
}
