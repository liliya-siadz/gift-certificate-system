package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GiftCertificateEntityModel {
    private long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private Date createDate;
    private Date lastUpdateDate;
}
