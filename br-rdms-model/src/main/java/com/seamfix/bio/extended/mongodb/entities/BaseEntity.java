package com.seamfix.bio.extended.mongodb.entities;

import java.io.Serializable;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;



@Data
public class BaseEntity implements Serializable {

    @Id
    private ObjectId id;

    private Long created = System.currentTimeMillis();

    private boolean active = true;

    private boolean deleted;

    private Long lastModified;

}
