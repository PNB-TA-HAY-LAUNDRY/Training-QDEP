/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.entity.pajak;

import com.dimata.qdep.entity.*;
import java.math.BigDecimal;
/**
 *
 * @author ihsan
 */


public class TaxType extends Entity {

    private long taxTypeId = 0;
    private String namaPajak = "";
    private String deskripsi = "";
    private BigDecimal tarif;

    public TaxType() {
    }

    public TaxType(String namaPajak, String deskripsi, BigDecimal tarif) {
        this.namaPajak = namaPajak;
        this.deskripsi = deskripsi;
        this.tarif = tarif;
    }

    // Getters and Setters
    public long getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public String getNamaPajak() {
        return namaPajak;
    }

    public void setNamaPajak(String namaPajak) {
        this.namaPajak = namaPajak;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }
}
