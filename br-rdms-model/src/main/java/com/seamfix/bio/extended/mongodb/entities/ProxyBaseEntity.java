package com.seamfix.bio.extended.mongodb.entities;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Id;



@Data
public class ProxyBaseEntity implements Serializable {

    @Id
    private String id;

    private Long created = System.currentTimeMillis();

    private boolean active = true;

    private boolean deleted;

    private Long lastModified;

}
