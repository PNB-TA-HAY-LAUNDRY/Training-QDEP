/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PstTaxVehicle;
import com.dimata.entity.pajak.TaxVehicle;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

public class CtrlTaxVehicle extends Control implements I_Language {

    private HttpServletRequest request; // HTTP request object

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_DELETE_RESTRICT = 4;

    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Kategori sudah ada", "Data tidak lengkap", "Kategori tidak bisa dihapus, masih dipakai modul lain ..."},
        {"Succes", "Can not process", "Category code already exist", "Data incomplete", "Cannot delete, category still used by another module"}
    };

    private int start;
    private String msgString;
    private TaxVehicle taxVehicle;
    private TaxVehicle prevTaxvehicle;
    private PstTaxVehicle pstTaxVehicle;
    private FrmTaxVehicle frmTaxVehicle;
    int language = LANGUAGE_FOREIGN;

    public CtrlTaxVehicle(HttpServletRequest request) {
        this.request = request;
        msgString = "";
        taxVehicle = new TaxVehicle();
        try {
            pstTaxVehicle = new PstTaxVehicle(0);
        } catch (Exception e) {
            ; // Handle potential exception during initialization
        }
        // Initialize form handler with request and entity
        frmTaxVehicle = new FrmTaxVehicle(request, taxVehicle);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return resultText[language][RSLT_EST_CODE_EXIST];
            case I_DBExceptionInfo.DEL_RESTRICTED:
                return resultText[language][RSLT_DELETE_RESTRICT];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            case I_DBExceptionInfo.DEL_RESTRICTED:
                return RSLT_DELETE_RESTRICT;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public TaxVehicle getTaxVehicle() {
        return taxVehicle;
    }

    // Getter for the form handler
    public FrmTaxVehicle getForm() {
        return frmTaxVehicle;
    }

    // Getter for the message string
    public String getMessage() {
        return msgString;
    }

    // Getter for the start index
    public int getStart() {
        return start;
    }

public int action(int cmd, long oidTax) {
    msgString = "";
    int excCode = I_DBExceptionInfo.NO_EXCEPTION;
    int rsCode = RSLT_OK;
    switch (cmd) {
        case Command.ADD:
            // tidak ada codingan cuman pakai link ke Form 
            break;

        case Command.LIST:
            try {
                Vector<TaxVehicle> taxVehicles = PstTaxVehicle.listAll(0, 0, "", "");
                
                request.setAttribute("taxVehicles", taxVehicles);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            break;
            
        case Command.SEARCH:

            break;

        case Command.SAVE:
            // Save logic
            break;

        case Command.DELETE:
            // Delete logic
            break;

        case Command.EDIT:
            // Edit logic
            break;
        default:
    }
    return excCode;
}

public int actionDelete(long vehicleTaxId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;

        if (vehicleTaxId != 0) {
            try {
//                // Pertama, hapus relasi terkait jika ada
//                deleteRelatedData(transferTaxId);

                // Kemudian, hapus data utama
                long oid = PstTaxVehicle.deleteExc(vehicleTaxId);
                if (oid != 0) {
                    msgString = "Data berhasil dihapus.";
                } else {
                    msgString = "Gagal menghapus data. ID tidak ditemukan.";
                    return RSLT_UNKNOWN_ERROR;
                }
            } catch (DBException dbexc) {
                excCode = dbexc.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        } else {
            msgString = "ID tidak valid untuk penghapusan.";
            return RSLT_FORM_INCOMPLETE;
        }

        return excCode;
    }

}