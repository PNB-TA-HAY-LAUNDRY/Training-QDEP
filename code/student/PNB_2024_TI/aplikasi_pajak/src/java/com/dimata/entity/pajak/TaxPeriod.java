package com.dimata.entity.pajak;

import com.dimata.qdep.entity.Entity;

public class TaxPeriod extends Entity {

    private long id;
    private int year;
    private int month;
    private long taxTypeId;
    private long regionalTaxId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public long getRegionalTaxId() {
        return regionalTaxId;
    }

    public void setRegionalTaxId(long regionalTaxId) {
        this.regionalTaxId = regionalTaxId;
    }
}
