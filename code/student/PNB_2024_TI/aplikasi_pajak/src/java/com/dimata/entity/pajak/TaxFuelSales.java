package com.dimata.entity.pajak;
import com.dimata.qdep.entity.Entity;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Merta
 */
public class TaxFuelSales extends Entity{
    
    private int fuelTaxId;
    private String penjual;
    private BigDecimal jumlahLiter;
    private int taxTypeId;
    private BigDecimal jumlahPajak;
    private Date tanggalPenjualan;
    private PaymentStatus status; // 'Belum Dibayar' atau 'Dibayar'
    private Date tanggalJatuhTempo;
    private Date tanggalPembayaran;
    private TaxType taxType;

    // Constructor
    public TaxFuelSales() {
        this.status = PaymentStatus.BELUM_DIBAYAR;
    }

    public TaxFuelSales(int fuelTaxId, String penjual, BigDecimal jumlahLiter, int taxTypeId, BigDecimal jumlahPajak,
                        Date tanggalPenjualan, PaymentStatus statusPembayaran, Date tanggalJatuhTempo, Date tanggalPembayaran, TaxType taxType) {
        this.fuelTaxId = fuelTaxId;
        this.penjual = penjual;
        this.jumlahLiter = jumlahLiter;
        this.taxTypeId = taxTypeId;
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

    public BigDecimal getJumlahLiter() {
        return jumlahLiter;
    }

    public void setJumlahLiter(BigDecimal jumlahLiter) {
        this.jumlahLiter = jumlahLiter;
    }

    public int getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(int taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public BigDecimal getJumlahPajak() {
        return jumlahPajak;
    }

    public void setJumlahPajak(BigDecimal jumlahPajak) {
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
}