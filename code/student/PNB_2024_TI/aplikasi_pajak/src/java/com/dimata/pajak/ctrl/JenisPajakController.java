package com.dimata.pajak.ctrl;

import com.dimata.pajak.entity.PstJenisPajak;
import com.dimata.pajak.entity.JenisPajak;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.util.lang.I_Language;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

public class JenisPajakController extends Control implements I_Language {

    public enum ResultStatus {
        OK,
        UNKNOWN_ERROR,
        DELETE_RESTRICT,
        VALIDATION_ERROR,
        NOT_FOUND
    }

    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Pajak tidak bisa dihapus, masih dipakai modul lain ...", "Data tidak valid", "Data tidak ditemukan"},
        {"Success", "Cannot process", "Tax cannot be deleted, still used by another module ...", "Invalid data", "Data not found"}
    };

    private String msgString;
    
    public JenisPajakController() {
        msgString = "";
    }

    private String getSystemMessage(ResultStatus status) {
        switch (status) {
            case DELETE_RESTRICT:
                return resultText[0][2];
            case VALIDATION_ERROR:
                return resultText[0][3];
            case NOT_FOUND:
                return resultText[0][4];
            default:
                return resultText[0][1];
        }
    }

    private int getControlMsgId(ResultStatus status) {
        switch (status) {
            case DELETE_RESTRICT:
                return 2;
            case VALIDATION_ERROR:
                return 3;
            case NOT_FOUND:
                return 4;
            default:
                return 1;
        }
    }

    public String getMessage() {
        return msgString;
    }

    private boolean validateInput(HttpServletRequest request) {
        String nama = request.getParameter("nama");
        String deskripsi = request.getParameter("deskripsi");
        String daerahIdStr = request.getParameter("daerah_id");

        if (nama == null || nama.trim().isEmpty()) {
            msgString = "Nama tidak boleh kosong.";
            return false;
        }
        if (deskripsi == null || deskripsi.trim().isEmpty()) {
            msgString = "Deskripsi tidak boleh kosong.";
            return false;
        }
        if (daerahIdStr == null || daerahIdStr.trim().isEmpty()) {
            msgString = "Daerah ID tidak boleh kosong.";
            return false;
        }

        try {
            Long.parseLong(daerahIdStr);
        } catch (NumberFormatException e) {
            msgString = "Daerah ID harus berupa angka.";
            return false;
        }

        return true;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        msgString = "";
        ResultStatus status = ResultStatus.OK;

        int cmd = Integer.parseInt(request.getParameter("cmd"));
        long oidJenisPajak = Long.parseLong(request.getParameter("oidJenisPajak"));

        try {
            if (cmd == 1) { 
                if (oidJenisPajak != 0) {
                    long oid = PstJenisPajak.deleteExc(oidJenisPajak);
                    msgString = "Berhasil menghapus jenis pajak.";
                } else {
                    status = ResultStatus.NOT_FOUND;
                    msgString = getSystemMessage(status);
                }
            } else if (cmd == 2 || cmd == 3) { // CREATE or UPDATE command
                if (validateInput(request)) {
                    JenisPajak jenisPajak = new JenisPajak();
                    jenisPajak.setNama(request.getParameter("nama"));
                    jenisPajak.setDeskripsi(request.getParameter("deskripsi"));
                    
                    // Mendapatkan daerah_id dari request dan mengaturnya ke objek JenisPajak
                    long daerahId = Long.parseLong(request.getParameter("daerah_id"));
                    jenisPajak.setDaerahId(daerahId);

                    if (cmd == 2) { // CREATE command
                        PstJenisPajak.insertExc(jenisPajak);
                        msgString = "Berhasil menambahkan jenis pajak.";
                    } else { // UPDATE command
                        jenisPajak.setId(oidJenisPajak);
                        PstJenisPajak.updateExc(jenisPajak);
                        msgString = "Berhasil memperbarui jenis pajak.";
                    }
                } else {
                    status = ResultStatus.VALIDATION_ERROR;
                    msgString = getSystemMessage(status);
                }
            } else {
                status = ResultStatus.UNKNOWN_ERROR;
                msgString = "Perintah tidak valid";
            }
        } catch (DBException dbexc) {
            status = ResultStatus.DELETE_RESTRICT;
            msgString = getSystemMessage(status);
        } catch (Exception exc) {
            status = ResultStatus.UNKNOWN_ERROR;
            msgString = getSystemMessage(status);
        }

        request.setAttribute("message", msgString);
        response.sendRedirect("matjenispajak.jsp");
    }

    public Vector<JenisPajak> getAllJenisPajak() {
        Vector<JenisPajak> jenisPajakList = new Vector<>();
        try {
            jenisPajakList = PstJenisPajak.list(0, 0, null, "ID");
        } catch (Exception e) {
            msgString = "Terjadi kesalahan saat mengambil data.";
        }
        return jenisPajakList;
    }
}
