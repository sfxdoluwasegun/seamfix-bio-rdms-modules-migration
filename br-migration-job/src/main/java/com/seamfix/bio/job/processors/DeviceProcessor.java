package com.seamfix.bio.job.processors;

import com.seamfix.bio.entities.Device;
import com.seamfix.bio.job.jpa.dao.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import java.util.Date;

public class DeviceProcessor implements ItemProcessor<com.sf.bioregistra.entity.Device, Device> {

    private final DeviceRepository deviceRepository;

    public DeviceProcessor(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(DeviceProcessor.class);

    @Override
    public Device process(com.sf.bioregistra.entity.Device device) throws Exception {
        log.info("BioRegistra Device migration job is in progress!");
        Device converted = new Device();

        if (device.getCreated() != null) {
            converted.setCreateDate(new Date(device.getCreated()));
        }

        if (device.getLastModified() != null) {
            converted.setLastModified(new Date(device.getLastModified()));
        }
        converted.setLga(device.getLga() == null ? "" : device.getLga());
        if (device.getLastModified() != null) {
            converted.setModified(new Date(device.getLastModified()));
        }
        converted.setState(device.getState() == null ? "" : device.getState());
        converted.setTag(device.getTag() == null ? "" : device.getTag());
        converted.setType(device.getType() == null ? "" : device.getType());
        converted.setUniqueId(device.getUniqueId() == null ? "" : device.getUniqueId());
        deviceRepository.save(converted);

        return converted;
    }

}
