package com.bezbednost.team11.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.Timestamp;

public class Certificate {

    @Id
    private BigInteger serialNumber;

    @Column
    private String commonName;

    @Column
    private Timestamp startDate;

    @Column
    private Timestamp endDate;

    @Column
    private String certKeyStorePath;


}
