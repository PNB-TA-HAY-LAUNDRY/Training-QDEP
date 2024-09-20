package com.dimata.entity.pajak;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 * Entity class for vehicle tax records.
 */
public class TaxVehicle extends Entity {
    private long vehicleTaxId;
    private String noPlat;
    private String namaPemilik;
    private String alamat;
    private String merk;
    private String model;
    private String tahunPembuatan;
    private TaxType taxType;
    private double jumlahPajak;
    private String periodePajak;
    private PaymentStatus statusPembayaran;
    private Date tanggalJatuhTempo;
    private Date tanggalPembayaran;

        public TaxVehicle() {
        this.statusPembayaran = PaymentStatus.BELUM_DIBAYAR;
    }

    public TaxVehicle(String noPlat, String namaPemilik, String alamat,
            String merk, String model, String tahunPembuatan, TaxType taxType,
            Double jumlahPajak, String periodePajak, PaymentStatus statusPembayaran,
            Date tanggalJatuhTempo, Date tanggalPembayaran) {
        
        this.noPlat = noPlat;
        this.namaPemilik = namaPemilik;
        this.alamat = alamat;
        this.merk = merk;
        this.model = model;
        this.tahunPembuatan = tahunPembuatan;
        this.taxType = taxType;
        this.jumlahPajak = jumlahPajak;
        this.periodePajak = periodePajak;
        this.statusPembayaran = statusPembayaran != null ? statusPembayaran : PaymentStatus.BELUM_DIBAYAR;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalPembayaran = tanggalPembayaran;
    }

    // Getters and Setters

    public long getVehicleTaxId() {
        return vehicleTaxId;
    }

    public void setVehicleTaxId(long vehicleTaxId) {
        this.vehicleTaxId = vehicleTaxId;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTahunPembuatan() {
        return tahunPembuatan;
    }

    public void setTahunPembuatan(String tahunPembuatan) {
        this.tahunPembuatan = tahunPembuatan;
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

    public void setJumlahPajak(Double jumlahPajak) {
        this.jumlahPajak = jumlahPajak;
    }

    public String getPeriodePajak() {
        return periodePajak;
    }

    public void setPeriodePajak(String periodePajak) {
        this.periodePajak = periodePajak;
    }

    public PaymentStatus getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(PaymentStatus statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
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