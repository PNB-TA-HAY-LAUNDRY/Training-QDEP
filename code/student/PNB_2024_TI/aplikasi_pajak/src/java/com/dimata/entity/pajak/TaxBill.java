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
public class TaxBill extends Entity {

    private long id;
    private BigDecimal amount;
    private long taxTypeId;
    private long regionalTaxId;
    private long taxPeriodId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public long getTaxPeriodId() {
        return taxPeriodId;
    }

    public void setTaxPeriodId(long taxPeriodId) {
        this.taxPeriodId = taxPeriodId;
    }
}
