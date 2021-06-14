package com.dummydevice.dummy_device.model;


public class Authority {
    Long id;

    String name;

    public Authority() {
        super();
    }

    public Authority(String name) {
        super();
        this.name = name;
    }

    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
