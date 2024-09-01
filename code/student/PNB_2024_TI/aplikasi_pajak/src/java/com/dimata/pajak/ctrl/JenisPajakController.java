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
            msgString = "Daerah ID tidak valid.";
            return false;
        }

        return true;
    }

    private int getCommand(HttpServletRequest request) {
        int cmd = 0;
        String command = request.getParameter("command");
        if (command != null) {
            try {
                cmd = Integer.parseInt(command);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing command: " + e.getMessage());
            }
        }
        return cmd;
    }

    private long getOId(HttpServletRequest request) {
        long oid = 0;
        String oidStr = request.getParameter("oid");
        if (oidStr != null) {
            try {
                oid = Long.parseLong(oidStr);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing OID: " + e.getMessage());
            }
        }
        return oid;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    msgString = "";
    
    // Menggunakan parameter dari request untuk menentukan perintah (cmd)
    String action = request.getParameter("cmd"); // asumsikan 'cmd' adalah parameter dari request
    
    JenisPajak jenisPajak = new JenisPajak();
    
    if (!"DELETE".equalsIgnoreCase(action)) { // Periksa apakah tindakan bukan DELETE
        if (!validateInput(request)) {
            return;
        }
        jenisPajak.setNama(request.getParameter("nama"));
        jenisPajak.setDeskripsi(request.getParameter("deskripsi"));
        jenisPajak.setDaerahId(Long.parseLong(request.getParameter("daerah_id")));
    }

    // Gunakan switch-case untuk menentukan aksi berdasarkan action string
    switch (action != null ? action.toUpperCase() : "") {
        case "CREATE":
            try {
                PstJenisPajak.insertExc(jenisPajak);
                msgString = resultText[0][0]; // Berhasil
            } catch (DBException e) {
                msgString = getSystemMessage(ResultStatus.UNKNOWN_ERROR);
            }
            break;

        case "UPDATE":
            try {
                PstJenisPajak.updateExc(jenisPajak);
                msgString = resultText[0][0]; // Berhasil
            } catch (DBException e) {
                msgString = getSystemMessage(ResultStatus.NOT_FOUND);
            }
            break;

        case "DELETE":
            long oid = getOId(request);
            if (oid != 0) {
                try {
                    PstJenisPajak.deleteExc(oid);
                    msgString = resultText[0][0]; // Berhasil
                } catch (DBException e) {
                    msgString = getSystemMessage(ResultStatus.DELETE_RESTRICT);
                }
            }
            break;

        default:
            msgString = resultText[0][1]; // Tidak dapat diproses
            break;
    }
}


    public Vector<JenisPajak> getAllJenisPajak() {
        try {
            return PstJenisPajak.listAll();
        } catch (DBException e) {
            return new Vector<>();
        }
    }
}
