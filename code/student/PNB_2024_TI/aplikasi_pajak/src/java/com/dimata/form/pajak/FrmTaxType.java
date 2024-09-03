package com.dimata.form.pajak;

import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import com.dimata.entity.pajak.TaxType;

import javax.servlet.http.HttpServletRequest;

public class FrmTaxType extends FRMHandler implements I_FRMInterface, I_FRMType {
    private TaxType taxType;

    public static final String FRM_NAME_TAX_TYPE = "FRM_NAME_TAX_TYPE";

    public static final int FRM_FIELD_ID = 0;
    public static final int FRM_FIELD_NAME = 1;
    public static final int FRM_FIELD_DESCRIPTION = 2;

    public static String[] fieldNames = {
        "FRM_FIELD_ID",
        "FRM_FIELD_NAME",
        "FRM_FIELD_DESCRIPTION"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING
    };

    public FrmTaxType() {
    }

    public FrmTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    public FrmTaxType(HttpServletRequest request, TaxType taxType) {
        super(new FrmTaxType(taxType), request);
        this.taxType = taxType;
    }

    @Override
    public String getFormName() {
        return FRM_NAME_TAX_TYPE;
    }

    @Override
    public int[] getFieldTypes() {
        return fieldTypes;
    }

    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    public TaxType getEntityObject() {
        return taxType;
    }

    public void requestEntityObject(TaxType taxType) {
        try {
            this.requestParam();
            taxType.setId(getLong(FRM_FIELD_ID));
            taxType.setName(getString(FRM_FIELD_NAME));
            taxType.setDescription(getString(FRM_FIELD_DESCRIPTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
