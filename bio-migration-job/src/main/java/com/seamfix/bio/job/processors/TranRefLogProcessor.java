package com.seamfix.bio.job.processors;

import com.seamfix.bio.extended.mongodb.entities.TransactionRefLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.seamfix.bio.jpa.dao.TransactionRefLogRepository;

public class TranRefLogProcessor implements ItemProcessor<TransactionRefLog, com.seamfix.bio.entities.TransactionRefLog> {

    private static final Logger log = LoggerFactory.getLogger(TranRefLogProcessor.class);

    private final TransactionRefLogRepository transactionRefLogRepository;

    public TranRefLogProcessor(TransactionRefLogRepository transactionRefLogRepository) {
        this.transactionRefLogRepository = transactionRefLogRepository;
    }

    @Override
    public com.seamfix.bio.entities.TransactionRefLog process(TransactionRefLog tranLog) throws Exception {
         log.info("IClocker Transaction Reference Log migration job is in progress!");
        com.seamfix.bio.entities.TransactionRefLog converted = new com.seamfix.bio.entities.TransactionRefLog();
        converted.setAmount(tranLog.getAmount());
        converted.setCipher(tranLog.getCipher() == null || tranLog.getCipher().trim().isEmpty() ? "" : tranLog.getCipher());
        converted.setCurrency(tranLog.getCurrency() == null || tranLog.getCurrency().trim().isEmpty() ? "" : tranLog.getCurrency());
        converted.setOldId(tranLog.getId().toHexString());
        converted.setOrgId(tranLog.getOrgId() == null || tranLog.getOrgId().trim().isEmpty() ? "" : tranLog.getOrgId());
        if (tranLog.getStatus() != null) {
            converted.setStatus(tranLog.getStatus());
        }
        converted.setTransactionRef(tranLog.getTransactionRef() == null || tranLog.getTransactionRef().trim().isEmpty() ? "" : tranLog.getTransactionRef());
        converted.setTxMode(tranLog.getTxMode() == null || tranLog.getTxMode().trim().isEmpty() ? "" : tranLog.getTxMode());
        transactionRefLogRepository.save(converted);
        return converted;
    }

}
