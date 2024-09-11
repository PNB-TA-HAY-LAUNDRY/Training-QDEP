/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.entity.pajak;

/**
 *
 * @author ihsan
 */
public enum PaymentStatus {
    BELUM_DIBAYAR("Belum Dibayar"),
    DIBAYAR("Dibayar");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    @Override
    public String toString() {
        return status;
   }
}
