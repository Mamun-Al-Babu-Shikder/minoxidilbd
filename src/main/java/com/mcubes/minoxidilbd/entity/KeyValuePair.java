package com.mcubes.minoxidilbd.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Entity
public class KeyValuePair {
    @Id
    private String accessKey;
    private String value;
    
    public KeyValuePair(){}

    public KeyValuePair(String accessKey, String value) {
        this.accessKey = accessKey;
        this.value = value;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
