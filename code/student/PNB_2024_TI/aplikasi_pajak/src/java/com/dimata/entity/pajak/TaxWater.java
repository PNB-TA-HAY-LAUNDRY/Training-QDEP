/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.entity.pajak;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author andin
 */
public class TaxWater extends Entity {
    private long waterUsageId;
    private String pengguna;
    private String lokasi;
    private double volumeAirM3;
    private TaxType taxType;
    private double jumlahPajak;
    private Date periodePajak;
    private Date tanggalJatuhTempo;
    private Date tanggalPembayaran;
    private PaymentStatus status;


    // Constructor tanpa parameter
    public TaxWater() {
        this.status = PaymentStatus.BELUM_DIBAYAR;
    }

    // Constructor dengan parameter
    public TaxWater(String pengguna, String lokasi, double volumeAirM3, TaxType taxType,
            double jumlahPajak, Date periodePajak, PaymentStatus statusPembayaran,
            Date tanggalJatuhTempo, Date tanggalPembayaran) {

        this.pengguna = pengguna;
        this.lokasi = lokasi;
        this.volumeAirM3 = volumeAirM3;
        this.taxType = taxType;
        this.jumlahPajak = jumlahPajak;
        this.periodePajak = periodePajak;
        this.status = statusPembayaran != null ? statusPembayaran : PaymentStatus.BELUM_DIBAYAR;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalPembayaran = tanggalPembayaran;
    }

    
    public long getWaterUsageId() {
        return waterUsageId;
    }

    public void setWaterUsageId(long waterUsageId) {
        this.waterUsageId = waterUsageId;
    }
    
    // Getters dan Setters
    public String getPengguna() {
        return pengguna;
    }

    public void setPengguna(String pengguna) {
        this.pengguna = pengguna;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public double getVolumeAirM3() {
        return volumeAirM3;
    }

    public void setVolumeAirM3(double volumeAirM3) {
        this.volumeAirM3 = volumeAirM3;
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

    public Date getPeriodePajak() {
        return periodePajak;
    }

    public void setPeriodePajak(Date periodePajak) {
        this.periodePajak = periodePajak;
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