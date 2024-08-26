/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.pajakdaerah.form;

import com.dimata.pajakdaerah.entity.JenisPajak;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import static com.dimata.qdep.form.I_FRMType.ENTRY_REQUIRED;
import static com.dimata.qdep.form.I_FRMType.TYPE_LONG;
import static com.dimata.qdep.form.I_FRMType.TYPE_STRING;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ihsan
 */
public class FrmPajak extends FRMHandler implements I_FRMInterface, I_FRMType{
    private JenisPajak jenisPajak;

    public static final String FRM_NAME_JENIS_PAJAK = "FRM_NAME_JENIS_PAJAK";

    public static final int FRM_FIELD_JENIS_PAJAK_ID = 0;
    public static final int FRM_FIELD_NAMA = 1;
    public static final int FRM_FIELD_DESKRIPSI = 2;

    public static String[] fieldNames = {
        "FRM_FIELD_JENIS_PAJAK_ID",
        "FRM_FIELD_NAMA",
        "FRM_FIELD_DESKRIPSI"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING
    };

    public FrmPajak() {
    }

    public FrmPajak(JenisPajak jenisPajak) {
        this.jenisPajak = jenisPajak;
    }

    public FrmPajak(HttpServletRequest request, JenisPajak jenisPajak) {
        super(new FrmPajak(jenisPajak), request);
        this.jenisPajak = jenisPajak;
    }

    public String getFormName() {
        return FRM_NAME_JENIS_PAJAK;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public JenisPajak getEntityObject() {
        return jenisPajak;
    }

    public void requestEntityObject(JenisPajak jenisPajak) {
        try {
            this.requestParam();
            jenisPajak.setId(getLong(FRM_FIELD_JENIS_PAJAK_ID));
            jenisPajak.setNama(getString(FRM_FIELD_NAMA));
            jenisPajak.setDeskripsi(getString(FRM_FIELD_DESKRIPSI));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
