package com.dimata.pajak.ctrl;

import com.dimata.pajak.entity.Daerah;

public class FrmDaerah {

    public static final int FRM_FIELD_DAERAH_ID = 0;
    public static final int FRM_FIELD_NAMA = 1;
    public static final int FRM_FIELD_KODE_DAERAH = 2;
    public static final int FRM_FIELD_DESKRIPSI = 3;

    public static final String[] fieldNames = {
        "id",
        "nama",
        "kode_daerah",
        "deskripsi"
    };

    private Daerah daerah;

    public FrmDaerah() {
        daerah = new Daerah();
    }

    public FrmDaerah(Daerah daerah) {
        this.daerah = daerah;
    }

    public void setFieldValue(int fieldIndex, String value) {
        switch (fieldIndex) {
            case FRM_FIELD_DAERAH_ID:
                daerah.setId(Long.parseLong(value));
                break;
            case FRM_FIELD_NAMA:
                daerah.setNama(value);
                break;
            case FRM_FIELD_KODE_DAERAH:
                daerah.setKodeDaerah(value);
                break;
            case FRM_FIELD_DESKRIPSI:
                daerah.setDeskripsi(value);
                break;
        }
    }

    public String getFieldValue(int fieldIndex) {
        switch (fieldIndex) {
            case FRM_FIELD_DAERAH_ID:
                return String.valueOf(daerah.getOId());
            case FRM_FIELD_NAMA:
                return daerah.getNama();
            case FRM_FIELD_KODE_DAERAH:
                return daerah.getKodeDaerah();
            case FRM_FIELD_DESKRIPSI:
                return daerah.getDeskripsi();
            default:
                return null;
        }
    }

    public Daerah getDaerah() {
        return daerah;
    }

    public void setDaerah(Daerah daerah) {
        this.daerah = daerah;
    }
}
