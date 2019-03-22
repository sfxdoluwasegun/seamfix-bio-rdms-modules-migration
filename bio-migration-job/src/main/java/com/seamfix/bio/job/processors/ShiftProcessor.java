package com.seamfix.bio.job.processors;

import com.sf.biocloud.entity.Shift;
import org.springframework.batch.item.ItemProcessor;

public class ShiftProcessor implements ItemProcessor<Shift, com.seamfix.bio.entities.Shift> {

    @Override
    public com.seamfix.bio.entities.Shift process(Shift shift) throws Exception {
        return null;
    }
    
}
