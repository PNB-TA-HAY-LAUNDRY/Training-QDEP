package com.dimata.pajak.ctrl;

import com.dimata.pajak.entity.JenisPajak;
import javax.servlet.http.HttpServletRequest;

public class FrmJenisPajak {

    public static final String FRM_NAMA = "nama";
    public static final String FRM_DESKRIPSI = "deskripsi";
    public static final String FRM_DAERAH_ID = "daerah_id";  // Tambahan daerah_id

    public static JenisPajak requestEntity(HttpServletRequest request) {
        JenisPajak jenisPajak = new JenisPajak();
        jenisPajak.setNama(request.getParameter(FRM_NAMA));
        jenisPajak.setDeskripsi(request.getParameter(FRM_DESKRIPSI));
        jenisPajak.setDaerahId(Long.parseLong(request.getParameter(FRM_DAERAH_ID)));  // Mengambil daerah_id dari request
        return jenisPajak;
    }
}
