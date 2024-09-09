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
public class TaxOwnership extends Entity{
    private long transferTaxId;
    private String noPlat;
    private String namaPemilikLama;
    private String namaPemilikBaru;
    private String alamatBaru;
    private TaxType taxType;
    private double jumlahPajak;
    private Date tanggalProses;
    private StatusPembayaran statusPembayaran;
    private Date tanggalJatuhTempo;
    private Date tanggalPembayaran;

    // Enum untuk status pembayaran
    public enum StatusPembayaran {
        Belum_Dibayar,
        Dibayar
    }

    // Constructor tanpa parameter
    public TaxOwnership() {
    }

    // Constructor dengan parameter
    public TaxOwnership(String noPlat, String namaPemilikLama, String namaPemilikBaru,
            String alamatBaru, TaxType taxType, double jumlahPajak,
            Date tanggalProses, StatusPembayaran statusPembayaran,
            Date tanggalJatuhTempo, Date tanggalPembayaran) {
        
        this.noPlat = noPlat;
        this.namaPemilikLama = namaPemilikLama;
        this.namaPemilikBaru = namaPemilikBaru;
        this.alamatBaru = alamatBaru;
        this.taxType = taxType;
        this.jumlahPajak = jumlahPajak;
        this.tanggalProses = tanggalProses;
        this.statusPembayaran = statusPembayaran;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalPembayaran = tanggalPembayaran;
    }

    // Getters dan Setters

    public long getTransferTaxId() {
        return transferTaxId;
    }

    public void setTransferTaxId(long transferTaxId) {
        this.transferTaxId = transferTaxId;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getNamaPemilikLama() {
        return namaPemilikLama;
    }

    public void setNamaPemilikLama(String namaPemilikLama) {
        this.namaPemilikLama = namaPemilikLama;
    }

    public String getNamaPemilikBaru() {
        return namaPemilikBaru;
    }

    public void setNamaPemilikBaru(String namaPemilikBaru) {
        this.namaPemilikBaru = namaPemilikBaru;
    }

    public String getAlamatBaru() {
        return alamatBaru;
    }

    public void setAlamatBaru(String alamatBaru) {
        this.alamatBaru = alamatBaru;
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

    public Date getTanggalProses() {
        return tanggalProses;
    }

    public void setTanggalProses(Date tanggalProses) {
        this.tanggalProses = tanggalProses;
    }

    public StatusPembayaran getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(StatusPembayaran statusPembayaran) {
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
