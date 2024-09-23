/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.TaxWater;
import com.dimata.entity.pajak.TaxType;
import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author andin
 */
public class FrmTaxWater extends FRMHandler implements I_FRMInterface, I_FRMType {

    private TaxWater taxWater;

    public static final String FRM_TAX_WATER = "FRM_TAX_WATER";

    public static final int FRM_FIELD_PENGGUNA = 0;
    public static final int FRM_FIELD_LOKASI = 1;
    public static final int FRM_FIELD_VOLUME_AIR_M3 = 2;
    public static final int FRM_FIELD_TAX_TYPE_ID = 3;
    public static final int FRM_FIELD_JUMLAH_PAJAK = 4;
    public static final int FRM_FIELD_PERIODE_PAJAK = 5;
    public static final int FRM_FIELD_STATUS_PEMBAYARAN = 6;
    public static final int FRM_FIELD_TANGGAL_JATUH_TEMPO = 7;
    public static final int FRM_FIELD_TANGGAL_PEMBAYARAN = 8;

    public static String[] fieldNames = {
        "FRM_FIELD_PENGGUNA",
        "FRM_FIELD_LOKASI",
        "FRM_FIELD_VOLUME_AIR_M3",
        "FRM_FIELD_TAX_TYPE_ID",
        "FRM_FIELD_JUMLAH_PAJAK",
        "FRM_FIELD_PERIODE_PAJAK",
        "FRM_FIELD_STATUS_PEMBAYARAN",
        "FRM_FIELD_TANGGAL_JATUH_TEMPO",
        "FRM_FIELD_TANGGAL_PEMBAYARAN"
    };

    public static int[] fieldTypes = {
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_FLOAT + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_FLOAT + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_DATE
    };

    public FrmTaxWater() {}

    public FrmTaxWater(TaxWater taxWater) {
        this.taxWater = taxWater;
    }

    public FrmTaxWater(HttpServletRequest request, TaxWater taxWater) {
        super(new FrmTaxWater(taxWater), request);
        this.taxWater = taxWater;
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getFormName() {
        return FRM_TAX_WATER;
    }

    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public TaxWater getEntityObject() {
        return taxWater;
    }

    public void requestEntityObject(TaxWater taxWater) {
        try {
            this.requestParam();
            taxWater.setPengguna(getString(FRM_FIELD_PENGGUNA));
            taxWater.setLokasi(getString(FRM_FIELD_LOKASI));
            taxWater.setVolumeAirM3(getDouble(FRM_FIELD_VOLUME_AIR_M3));

            long taxTypeId = getLong(FRM_FIELD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();
            taxType.setTaxTypeId(taxTypeId);
            taxWater.setTaxType(taxType);

            taxWater.setJumlahPajak(getDouble(FRM_FIELD_JUMLAH_PAJAK));
            taxWater.setPeriodePajak(getDate(FRM_FIELD_PERIODE_PAJAK));
            taxWater.setTanggalJatuhTempo(getDate(FRM_FIELD_TANGGAL_JATUH_TEMPO));
            
            String statusPembayaranStr = getString(FRM_FIELD_STATUS_PEMBAYARAN);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxWater.setStatusPembayaran(statusPembayaran);
            
            taxWater.setTanggalPembayaran(getDate(FRM_FIELD_TANGGAL_PEMBAYARAN));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}