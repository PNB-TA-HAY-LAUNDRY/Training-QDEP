/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.entity.pajak.TaxType;
import com.dimata.entity.pajak.TaxVehicle;
import static com.dimata.qdep.db.I_DBType.TYPE_INT;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import static com.dimata.qdep.form.I_FRMType.ENTRY_REQUIRED;
import static com.dimata.qdep.form.I_FRMType.TYPE_DATE;
import static com.dimata.qdep.form.I_FRMType.TYPE_FLOAT;
import static com.dimata.qdep.form.I_FRMType.TYPE_LONG;
import static com.dimata.qdep.form.I_FRMType.TYPE_STRING;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ihsan
 */
public class FrmTaxVehicle extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private TaxVehicle taxVehicle;
    
    public static final String FRM_TAX_VEHICLE = "FRM_TAX_VEHICLE";
    
    public static final int FRM_FIELD_NO_PLAT = 0;
    public static final int FRM_FIELD_NAMA_PEMILIK = 1;
    public static final int FRM_FIELD_ALAMAT = 2;
    public static final int FRM_FIELD_MERK = 3;
    public static final int FRM_FIELD_MODEL = 4;
    public static final int FRM_FIELD_TAHUN_PEMBUATAN = 5;
    public static final int FRM_FIELD_TAX_TYPE_ID = 6;
    public static final int FRM_FIELD_JUMLAH_PAJAK = 7;
    public static final int FRM_FIELD_PERIODE_PAJAK = 8;
    public static final int FRM_FIELD_TANGGAL_JATUH_TEMPO = 9;
    public static final int FRM_FIELD_STATUS_PEMBAYARAN = 10;
    public static final int FRM_FIELD_TANGGAL_PEMBAYARAN = 11;
    
    public static String[] fieldNames = 
            {
                "FRM_FIELD_NO_PLAT",
                "FRM_FIELD_NAMA_PEMILIK",
                "FRM_FIELD_ALAMAT",
                "FRM_FIELD_MERK",
                "FRM_FIELD_MODEL",
                "FRM_FIELD_TAHUN_PEMBUATAN",
                "FRM_FIELD_TAX_TYPE_ID",
                "FRM_FIELD_JUMLAH_PAJAK",
                "FRM_FIELD_PERIODE_PAJAK",
                "FRM_FIELD_TANGGAL_JATUH_TEMPO",
                "FRM_FIELD_STATUS_PEMBAYARAN",
                "FRM_FIELD_TANGGAL_PEMBAYARAN"
            };
    
    public static int[] fieldTypes =
            {
                TYPE_STRING + ENTRY_REQUIRED,// no plat
                TYPE_STRING + ENTRY_REQUIRED,// nama_pemilik
                TYPE_STRING + ENTRY_REQUIRED,// Alamat
                TYPE_STRING + ENTRY_REQUIRED,// Merk
                TYPE_STRING + ENTRY_REQUIRED,// Model
                TYPE_INT + ENTRY_REQUIRED, // Tahun Pembuatan
                TYPE_LONG + ENTRY_REQUIRED, // tax_type_id
                TYPE_FLOAT + ENTRY_REQUIRED,// jumlah_pajak
                TYPE_STRING + ENTRY_REQUIRED,// Periode Pajak
                TYPE_DATE + ENTRY_REQUIRED,// tanggal Jatuh Tempo
                TYPE_STRING + ENTRY_REQUIRED,// status_pembayaran
                TYPE_DATE// tanggal pembayaran
            };
    
    public FrmTaxVehicle(){
    }
    
    public FrmTaxVehicle(TaxVehicle taxVehicle){
        this.taxVehicle = taxVehicle;
    }
    
    public FrmTaxVehicle (HttpServletRequest request, TaxVehicle taxVehicle){
        super(new FrmTaxVehicle(taxVehicle), request);
        this.taxVehicle = taxVehicle;
    }
    
    @Override
    public int getFieldSize() {
       return fieldNames.length;
    }

    @Override
    public String getFormName() {
        return FRM_TAX_VEHICLE;
    }

    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public TaxVehicle getEntityObject() {
        return taxVehicle;
    }
    
    public void requestEntityObject(TaxVehicle taxVehicle){
        try {
            this.requestParam();
            taxVehicle.setNoPlat(getString(FRM_FIELD_NO_PLAT));
            taxVehicle.setNamaPemilik(getString(FRM_FIELD_NAMA_PEMILIK));
            taxVehicle.setAlamat(getString(FRM_FIELD_ALAMAT));
            taxVehicle.setMerk(getString(FRM_FIELD_MERK));
            taxVehicle.setModel(getString(FRM_FIELD_MODEL));
            taxVehicle.setTahunPembuatan(getInt(FRM_FIELD_TAHUN_PEMBUATAN));
           
            long taxTypeId = getLong(FRM_FIELD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();  
            taxType.setTaxTypeId(taxTypeId);
            taxVehicle.setTaxType(taxType); 
            
            taxVehicle.setJumlahPajak(getDouble(FRM_FIELD_JUMLAH_PAJAK));
            taxVehicle.setPeriodePajak(getString(FRM_FIELD_PERIODE_PAJAK));
            
            String statusPembayaran = getString(FRM_FIELD_STATUS_PEMBAYARAN);
            taxVehicle.setStatusPembayaran(PaymentStatus.valueOf(statusPembayaran.toUpperCase()));
            
            taxVehicle.setTanggalJatuhTempo(getDate(FRM_FIELD_TANGGAL_JATUH_TEMPO));
            taxVehicle.setTanggalPembayaran(getDate(FRM_FIELD_TANGGAL_PEMBAYARAN));
        } catch (Exception e){
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
