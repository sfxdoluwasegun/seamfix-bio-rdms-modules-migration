/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.bio.job.events;

import java.util.List;
import org.springframework.batch.item.ItemWriter;

/**
 *
 * @author uonuoha
 */
public class NoOpItemWriter implements ItemWriter {

    @Override
    public void write(List list) throws Exception {

    }

}
