package com.dimata.pajak.ctrl;

import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import com.dimata.pajak.entity.JenisPajak;

import javax.servlet.http.HttpServletRequest;

public class FrmJenisPajak extends FRMHandler implements I_FRMInterface, I_FRMType {
    private JenisPajak jenisPajak;

    public static final String FRM_NAME_JENIS_PAJAK = "FRM_NAME_JENIS_PAJAK";

    public static final int FRM_FIELD_JENIS_PAJAK_ID = 0;
    public static final int FRM_FIELD_NAMA = 1;
    public static final int FRM_FIELD_DESKRIPSI = 2;
    public static final int FRM_FIELD_DAERAH_ID = 3; // Field untuk daerah_id

    public static String[] fieldNames = {
        "FRM_FIELD_JENIS_PAJAK_ID",
        "FRM_FIELD_NAMA",
        "FRM_FIELD_DESKRIPSI",
        "FRM_FIELD_DAERAH_ID" // Nama field id daerah
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING,
        TYPE_LONG // Tipe field baru
    };

    public FrmJenisPajak() {
    }

    public FrmJenisPajak(JenisPajak jenisPajak) {
        this.jenisPajak = jenisPajak;
    }

    public FrmJenisPajak(HttpServletRequest request, JenisPajak jenisPajak) {
        super(new FrmJenisPajak(jenisPajak), request);
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
            jenisPajak.setDaerahId(getLong(FRM_FIELD_DAERAH_ID)); // Mengatur daerah_id
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
