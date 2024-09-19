/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.entity.pajak.TaxOwnerships;
import com.dimata.entity.pajak.TaxType;
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
public class FrmTaxOwnerships extends FRMHandler implements I_FRMInterface, I_FRMType {

    private TaxOwnerships taxOwnerships;

    public static final String FRM_TAX_OWNERSHIP = "FRM_TAX_OWNERSHIP";

    public static final int FRM_FIELD_NO_PLAT = 0;
    public static final int FRM_FIELD_OLD_NAME = 1;
    public static final int FRM_FIELD_NEW_NAME = 2;
    public static final int FRM_FIELD_NEW_ADDRESS = 3;
    public static final int FRM_FIELD_TAX_TYPE = 4;
    public static final int FRM_FIELD_TOTAL_TAX = 5;
    public static final int FRM_FIELD_PROCESS_DATE = 6;
    public static final int FRM_FIELD_PAY_STATUS = 7;
    public static final int FRM_FIELD_DUE_DATE = 8;
    public static final int FRM_FIELD_PAY_DATE = 9;

    public static String[] fieldNames
            = {
                "FRM_FIELD_NO_PLAT",
                "FRM_FIELD_OLD_NAME",
                "FRM_FIELD_NEW_NAME",
                "FRM_FIELD_NEW_ADDRESS",
                "FRM_FIELD_TAX_TYPE",
                "FRM_FIELD_TOTAL_TAX",
                "FRM_FIELD_PROCESS_DATE",
                "FRM_FIELD_PAY_STATUS",
                "FRM_FIELD_DUE_DATE",
                "FRM_FIELD_PAY_DATE"
            };

    public static int[] fieldTypes
            = {
                TYPE_STRING + ENTRY_REQUIRED,// no plat
                TYPE_STRING + ENTRY_REQUIRED,// nama_pemilik_lama
                TYPE_STRING + ENTRY_REQUIRED,// nama_pemilik_baru
                TYPE_STRING + ENTRY_REQUIRED,// alamat_baru
                TYPE_STRING + ENTRY_REQUIRED, // tax_type_id
                TYPE_FLOAT + ENTRY_REQUIRED,// jumlah_pajak
                TYPE_DATE + ENTRY_REQUIRED,// tanggal_proses
                TYPE_STRING + ENTRY_REQUIRED,// status_pembayaran
                TYPE_DATE + ENTRY_REQUIRED,// tanggal_jatuh_tempo
                TYPE_DATE// tanggal pembayaran
            };

    public FrmTaxOwnerships() {
    }

    public FrmTaxOwnerships(TaxOwnerships taxOwnerships) {
        this.taxOwnerships = taxOwnerships;
    }

    public FrmTaxOwnerships(HttpServletRequest request, TaxOwnerships taxOwnerships) {
        super(new FrmTaxOwnerships(taxOwnerships), request);
        this.taxOwnerships = taxOwnerships;
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getFormName() {
        return FRM_TAX_OWNERSHIP;
    }

    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public TaxOwnerships getEntityObject() {
        return taxOwnerships;
    }

    public void requestEntityObject(TaxOwnerships taxOwnerships) {
        try {
            this.requestParam();
            taxOwnerships.setNoPlat(getString(FRM_FIELD_NO_PLAT));
            taxOwnerships.setNamaPemilikLama(getString(FRM_FIELD_OLD_NAME));
            taxOwnerships.setNamaPemilikBaru(getString(FRM_FIELD_NEW_NAME));
            taxOwnerships.setAlamatBaru(getString(FRM_FIELD_NEW_ADDRESS));
            taxOwnerships.setJenisPajak(getString(FRM_FIELD_TAX_TYPE));

            

            taxOwnerships.setJumlahPajak(getDouble(FRM_FIELD_TOTAL_TAX));
            taxOwnerships.setTanggalProses(getDate(FRM_FIELD_PROCESS_DATE));
            taxOwnerships.setStatusPembayaran(PaymentStatus.valueOf(getString(FRM_FIELD_PAY_STATUS).toUpperCase()));
            taxOwnerships.setTanggalJatuhTempo(getDate(FRM_FIELD_DUE_DATE));
            taxOwnerships.setTanggalPembayaran(getDate(FRM_FIELD_PAY_DATE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject: " + e.toString());
        }
    }

}