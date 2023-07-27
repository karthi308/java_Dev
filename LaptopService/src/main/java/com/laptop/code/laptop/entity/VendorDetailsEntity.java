package com.laptop.code.laptop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="vendor_details")
public class VendorDetailsEntity implements Serializable {
    @Id
    @Column(name="stock_id")
    int stock_id;

    @Column(name="stock_name")
    String stockName;

    @Column(name="price")
    String price;

    @Column(name="vendor_name")
    String vendorName;

    @Column(name="warnty_frm_date")
    String wrntyFrmDate;

    @Column(name="wrnty_to_date")
    String wrntyToDate;

    @Column(name="location")
    String location;

}
