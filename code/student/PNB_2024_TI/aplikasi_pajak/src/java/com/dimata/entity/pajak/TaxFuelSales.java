/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.entity.pajak;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author ihsan
 */
public class TaxFuelSales extends Entity{
    private int fuelTaxId;
    private String penjual;
    private double jumlahLiter;
    private TaxType taxType;
    private double jumlahPajak;
    private Date tanggalPenjualan;
    private PaymentStatus status; // 'Belum Dibayar' atau 'Dibayar'
    private Date tanggalJatuhTempo;
    private Date tanggalPembayaran;

    // Constructor
    public TaxFuelSales() {
        this.status = PaymentStatus.BELUM_DIBAYAR;
    }

    public TaxFuelSales(int fuelTaxId, String penjual, double jumlahLiter, int taxTypeId, double jumlahPajak,
                        Date tanggalPenjualan, PaymentStatus statusPembayaran, Date tanggalJatuhTempo, Date tanggalPembayaran, TaxType taxType) {
        
        this.fuelTaxId = fuelTaxId;
        this.penjual = penjual;
        this.jumlahLiter = jumlahLiter;
        this.jumlahPajak = jumlahPajak;
        this.tanggalPenjualan = tanggalPenjualan;
        this.status = statusPembayaran != null ? statusPembayaran : PaymentStatus.BELUM_DIBAYAR;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalPembayaran = tanggalPembayaran;
        this.taxType = taxType;
    }

    // Getters and Setters
    public int getFuelTaxId() {
        return fuelTaxId;
    }

    public void setFuelTaxId(int fuelTaxId) {
        this.fuelTaxId = fuelTaxId;
    }

    public String getPenjual() {
        return penjual;
    }

    public void setPenjual(String penjual) {
        this.penjual = penjual;
    }

    public double getJumlahLiter() {
        return jumlahLiter;
    }

    public void setJumlahLiter(double jumlahLiter) {
        this.jumlahLiter = jumlahLiter;
    }
    
    public double getJumlahPajak() {
        return jumlahPajak;
    }

    public void setJumlahPajak(double jumlahPajak) {
        this.jumlahPajak = jumlahPajak;
    }

    public Date getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggalPenjualan(Date tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

     public PaymentStatus getStatusPembayaran() {
        return status;
    }

    public void setStatusPembayaran(PaymentStatus status) {
        this.status = status;
    }

    public Date getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    public void setTanggalJatuhTempo(Date tanggalJatuhTempo) {
        this.tanggalJatuhTempo = tanggalJatuhTempo;
    }

    public Date getTanggalPembayaran() {
        return tanggalPembayaran;
    }

    public void setTanggalPembayaran(Date tanggalPembayaran) {
        this.tanggalPembayaran = tanggalPembayaran;
    }

     public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    void setTaxTypeId(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
