/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.entity.pajak.TaxOwnership;
import com.dimata.entity.pajak.TaxType;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ihsan
 */
public class FrmTaxOwnership extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private TaxOwnership taxOwnership;
    
    public static final String FRM_TAX_OWNERSHIP = "FRM_TAX_OWNERSHIP";
    
    public static final int FRM_FIELD_NO_PLAT = 0;
    public static final int FRM_FIELD_OLD_NAME = 1;
    public static final int FRM_FIELD_NEW_NAME = 2;
    public static final int FRM_FIELD_NEW_ADDRESS = 3;
    public static final int FRM_FIELD_TAX_TYPE_ID = 4;
    public static final int FRM_FIELD_TOTAL_TAX = 5;
    public static final int FRM_FIELD_PROCESS_DATE = 6;
    public static final int FRM_FIELD_PAY_STATUS = 7;
    public static final int FRM_FIELD_DUE_DATE = 8;
    public static final int FRM_FIELD_PAY_DATE = 9;
    
    public static String[] fieldNames = 
            {
                "FRM_FIELD_NO_PLAT",
                "FRM_FIELD_OLD_NAME",
                "FRM_FIELD_NEW_NAME",
                "FRM_FIELD_NEW_ADDRESS",
                "FRM_FIELD_TAX_TYPE_ID",
                "FRM_FIELD_TOTAL_TAX",
                "FRM_FIELD_PROCESS_DATE",
                "FRM_FIELD_PAY_STATUS",
                "FRM_FIELD_DUE_DATE",
                "FRM_FIELD_PAY_DATE"
            };
    
    public static int[] fieldTypes =
            {
                TYPE_STRING + ENTRY_REQUIRED,// no plat
                TYPE_STRING + ENTRY_REQUIRED,// nama_pemilik_lama
                TYPE_STRING + ENTRY_REQUIRED,// nama_pemilik_baru
                TYPE_STRING + ENTRY_REQUIRED,// alamat_baru
                TYPE_LONG + ENTRY_REQUIRED, // tax_type_id
                TYPE_FLOAT + ENTRY_REQUIRED,// jumlah_pajak
                TYPE_DATE + ENTRY_REQUIRED,// tanggal_proses
                TYPE_STRING + ENTRY_REQUIRED,// status_pembayaran
                TYPE_DATE + ENTRY_REQUIRED,// tanggal_jatuh_tempo
                TYPE_DATE// tanggal pembayaran
            };
    
    public FrmTaxOwnership(){
    }
    
    public FrmTaxOwnership(TaxOwnership taxOwnership){
        this.taxOwnership = taxOwnership;
    }
    
    public FrmTaxOwnership (HttpServletRequest request, TaxOwnership taxOwnership){
        super(new FrmTaxOwnership(taxOwnership), request);
        this.taxOwnership = taxOwnership;
    }
    
    @Override
    public int getFieldSize() {
       return fieldNames.length;
    }

    // Method to return the form's name
    @Override
    public String getFormName() {
        return FRM_TAX_OWNERSHIP;
    }

    // Method to return the names of the fields in the form
    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    // Method to return the types of the fields in the form
    @Override
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    // Method to return the current AssetType object
    public TaxOwnership getEntityObject() {
        return taxOwnership;
    }
    
    public void requestEntityObject(TaxOwnership taxOwnership){
        try {
            this.requestParam();
            taxOwnership.setNoPlat(getString(FRM_FIELD_NO_PLAT));
            taxOwnership.setNamaPemilikLama(getString(FRM_FIELD_OLD_NAME));
            taxOwnership.setNamaPemilikBaru(getString(FRM_FIELD_NEW_NAME));
            taxOwnership.setAlamatBaru(getString(FRM_FIELD_NEW_ADDRESS));
           
            long taxTypeId = getLong(FRM_FIELD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();  
            taxType.setTaxTypeId(taxTypeId);
            taxOwnership.setTaxType(taxType); 
            
            taxOwnership.setJumlahPajak(getDouble(FRM_FIELD_TOTAL_TAX));
            taxOwnership.setTanggalProses(getDate(FRM_FIELD_PROCESS_DATE));
            
            String statusPembayaran = getString(FRM_FIELD_PAY_STATUS);
            taxOwnership.setStatusPembayaran(PaymentStatus.valueOf(statusPembayaran.toUpperCase()));
            
            taxOwnership.setTanggalJatuhTempo(getDate(FRM_FIELD_DUE_DATE));
            taxOwnership.setTanggalPembayaran(getDate(FRM_FIELD_PAY_DATE));
        } catch (Exception e){
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
