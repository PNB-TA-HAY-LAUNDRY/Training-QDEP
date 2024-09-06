/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.entity.pajak;
import com.dimata.qdep.entity.*;
import java.math.BigDecimal;
import java.sql.Date;
/**
 *
 * @author ihsan
 */
public class TaxPayment extends Entity {

    private long id;
    private BigDecimal totalPayment;
    private Date paymentDate;
    private long taxBillId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public long getTaxBillId() {
        return taxBillId;
    }

    public void setTaxBillId(long taxBillId) {
        this.taxBillId = taxBillId;
    }
}
