/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.entity.pajak;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class TaxCigarette extends Entity {
    private long cigaretteTaxId;
    private String penjual;
    private int jumlahBatang;
    private TaxType taxType;
    private double jumlahPajak;
    private Date tanggalPenjualan;
    private Date tanggalJatuhTempo;
    private Date tanggalPembayaran;
    private PaymentStatus status;

   

    // Constructor tanpa parameter
    public TaxCigarette() {
         this.status = PaymentStatus.BELUM_DIBAYAR;
    }

    // Constructor dengan parameter
    public TaxCigarette
        (String penjual, 
                int jumlahBatang, TaxType taxType,
                        double jumlahPajak, Date tanggalPenjualan,
                              PaymentStatus statusPembayaran, Date tanggalJatuhTempo, 
                              Date tanggalPembayaran) {
        this.penjual = penjual;
        this.jumlahBatang = jumlahBatang;
        this.taxType = taxType;
        this.jumlahPajak = jumlahPajak;
        this.tanggalPenjualan = tanggalPenjualan;
        this.status = statusPembayaran != null ? statusPembayaran : PaymentStatus.BELUM_DIBAYAR;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalPembayaran = tanggalPembayaran;
    }

    // Getters dan Setters

    public long getCigaretteTaxId() {
        return cigaretteTaxId;
    }

    public void setCigaretteTaxId(long cigaretteTaxId) {
        this.cigaretteTaxId = cigaretteTaxId;
    }

    public String getPenjual() {
        return penjual;
    }

    public void setPenjual(String penjual) {
        this.penjual = penjual;
    }

    public int getJumlahBatang() {
        return jumlahBatang;
    }

    public void setJumlahBatang(int jumlahBatang) {
        this.jumlahBatang = jumlahBatang;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
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

    public void setStatusPembayaran(PaymentStatus statusPembayaran) {
        this.status = statusPembayaran;
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
}