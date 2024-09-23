/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.entity.pajak.TaxFuelSales;
import com.dimata.entity.pajak.TaxType;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Merta
 */
public class FrmTaxFuelSales extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private TaxFuelSales taxFuelSales;
    
    public static final String FRM_TAX_FUEL_SALES = "FRM_TAX_FUEL_SALES";
    
    public static final int FRM_FIELD_SELLER = 0;
    public static final int FRM_FIELD_FUEL_AMOUNT = 1;
    public static final int FRM_FIELD_TAX_TYPE_ID = 2;
    public static final int FRM_FIELD_TOTAL_TAX = 3;
    public static final int FRM_FIELD_SALE_DATE = 4;
    public static final int FRM_FIELD_PAY_STATUS = 5;
    public static final int FRM_FIELD_DUE_DATE = 6;
    public static final int FRM_FIELD_PAY_DATE = 7;
    
    public static String[] fieldNames = 
            {
                "FRM_FIELD_SELLER",
                "FRM_FIELD_FUEL_AMOUNT",
                "FRM_FIELD_TAX_TYPE_ID",
                "FRM_FIELD_TOTAL_TAX",
                "FRM_FIELD_SALE_DATE",
                "FRM_FIELD_PAY_STATUS",
                "FRM_FIELD_DUE_DATE",
                "FRM_FIELD_PAY_DATE"
            };
    
    public static int[] fieldTypes =
            {
                TYPE_STRING + ENTRY_REQUIRED,// penjual
                TYPE_FLOAT + ENTRY_REQUIRED,// jumlah liter
                TYPE_LONG + ENTRY_REQUIRED, // tax_type_id
                TYPE_FLOAT + ENTRY_REQUIRED,// jumlah_pajak
                TYPE_DATE + ENTRY_REQUIRED,// tanggal_penjualan
                TYPE_STRING + ENTRY_REQUIRED,// status_pembayaran
                TYPE_DATE + ENTRY_REQUIRED,// tanggal_jatuh_tempo
                TYPE_DATE// tanggal pembayaran
            };
    
    public FrmTaxFuelSales() {
    }
    
    public FrmTaxFuelSales(TaxFuelSales taxFuelSales) {
        this.taxFuelSales = taxFuelSales;
    }
    
    public FrmTaxFuelSales(HttpServletRequest request, TaxFuelSales taxFuelSales) {
        super(new FrmTaxFuelSales(taxFuelSales), request);
        this.taxFuelSales = taxFuelSales;
    }
    
    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getFormName() {
        return FRM_TAX_FUEL_SALES;
    }

    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public TaxFuelSales getEntityObject() {
        return taxFuelSales;
    }
    
    public void requestEntityObject(TaxFuelSales taxFuelSales) {
        try {
            this.requestParam();
            taxFuelSales.setPenjual(getString(FRM_FIELD_SELLER));
            taxFuelSales.setJumlahLiter(getDouble(FRM_FIELD_FUEL_AMOUNT));
           
            long taxTypeId = getLong(FRM_FIELD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();  
            taxType.setTaxTypeId(taxTypeId);
            taxFuelSales.setTaxType(taxType); 
            
            taxFuelSales.setJumlahPajak(getDouble(FRM_FIELD_TOTAL_TAX));
            taxFuelSales.setTanggalPenjualan(getDate(FRM_FIELD_SALE_DATE));
            
            String statusPembayaran = getString(FRM_FIELD_PAY_STATUS);
            taxFuelSales.setStatusPembayaran(PaymentStatus.valueOf(statusPembayaran.toUpperCase()));
            
            taxFuelSales.setTanggalJatuhTempo(getDate(FRM_FIELD_DUE_DATE));
            taxFuelSales.setTanggalPembayaran(getDate(FRM_FIELD_PAY_DATE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    private BigDecimal Double(int FRM_FIELD_TOTAL_TAX) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
