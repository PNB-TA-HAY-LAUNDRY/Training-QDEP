/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PstTaxCigarette;
import com.dimata.entity.pajak.TaxCigarette;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ASUS
 */
public class CtrlTaxCigarette extends Control implements I_Language {
     private HttpServletRequest request; // HTTP request object

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_DELETE_RESTRICT = 4;
    
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Data sudah ada", "Data tidak lengkap", "Data tidak bisa dihapus, masih dipakai modul lain ..."},
        {"Success", "Cannot process", "Data already exists", "Data incomplete", "Cannot delete, data still used by another module"}
    };
    
    private int start;
    private String msgString;
    private TaxCigarette taxCigarette;
    private TaxCigarette prevTaxCigarette;
    private PstTaxCigarette pstTaxCigarette;
//    private FrmTaxCigarette frmTaxCigarette;
    private int language = LANGUAGE_FOREIGN;
    
    public CtrlTaxCigarette(HttpServletRequest request) {
        this.request = request;
        msgString = "";
        taxCigarette = new TaxCigarette();
        try {
            pstTaxCigarette = new PstTaxCigarette(0);
        } catch (Exception e) {
            ; // Handle potential exception during initialization
        }
        // Initialize form handler with request and entity
//        frmTaxCigarette = new FrmTaxCigarette(request, taxCigarette);
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
    
    public TaxCigarette getTaxCigarette() {
        return taxCigarette;
    }
    
    // Getter for the form handler
//    public FrmTaxCigarette getForm() {
//        return frmTaxCigarette;
//    }
    
    // Getter for the message string
    public String getMessage() {
        return msgString;
    }

    // Getter for the start index
    public int getStart() {
        return start;
    }
    
    public int action(int cmd, long oidTaxCigarette) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                // tidak ada codingan cuman pakai link ke Form 
                break;

            // Function untuk pemanggilan data ke tabel 
            case Command.LIST:
                try {
                    Vector<TaxCigarette> taxCigarettes = PstTaxCigarette.listAll(0, 0, "", "");
                    request.setAttribute("taxCigarettes", taxCigarettes);
                } catch (Exception e) {
                    System.out.println("Exception " + e);
                } 
                break;

            // Function untuk menyimpan data dari form ke dalam tabel    
//            case Command.SAVE:
//                String oldCigaretteName = "";
//                if (oidTaxCigarette != 0) {
//                    try {
//                        taxCigarette = PstTaxCigarette.fetchExc(oidTaxCigarette);
//                        prevTaxCigarette = PstTaxCigarette.fetchExc(oidTaxCigarette);
//                        oldCigaretteName = taxCigarette.getPenjual();
//                    } catch (Exception exc) {
//                        // Handle potential exception
//                    }
//                }
//                
//                frmTaxCigarette.requestEntityObject(taxCigarette);
//                if (frmTaxCigarette.errorSize() > 0) {
//                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
//                    return RSLT_FORM_INCOMPLETE;
//                }
//                
//                if (taxCigarette.getOID() == 0) {
//                    try {
//                        boolean checkedCigarette = pstTaxCigarette.checkCigarette(taxCigarette.getPenjual(), 1);
//                        
//                        if (!checkedCigarette) {
//                            long oid = pstTaxCigarette.insertExc(this.taxCigarette);
//                        } else {
//                            msgString = getSystemMessage(I_DBExceptionInfo.MULTIPLE_ID);
//                            return getControlMsgId(I_DBExceptionInfo.MULTIPLE_ID);
//                        }
//                    } catch (DBException dbexception) {
//                        System.out.println("Go to DBException");
//                        excCode = dbexception.getErrorCode();
//                        msgString = getSystemMessage(excCode);
//                        return getControlMsgId(excCode);
//                    } catch (Exception exception) {
//                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                    }
//                } else {
//                    try {
//                        long oid = pstTaxCigarette.updateExc(this.taxCigarette);
//                    } catch (DBException dbexception) {
//                        excCode = dbexception.getErrorCode();
//                        msgString = getSystemMessage(excCode);
//                        return getControlMsgId(excCode);
//                    } catch (Exception exception) {
//                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                    }
//                } 
//                break;

            // Function untuk menghapus data dari tabel            
            case Command.DELETE:
                if (oidTaxCigarette != 0) {
                    try {
                        long oid = pstTaxCigarette.deleteExc(taxCigarette);
                    } catch (DBException dbexception) {
                        excCode = dbexception.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exception) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                } 
                break;

            // Function untuk mengedit data 
//            case Command.EDIT:
//                if (oidTaxCigarette != 0) {
//                    try {
//                        // Fetch the cigarette data for editing
//                        taxCigarette = PstTaxCigarette.fetchExc(oidTaxCigarette);
//
//                        // Set cigarette data to request attribute
//                        request.setAttribute("taxCigarette", taxCigarette);
//
//                        if (frmTaxCigarette.errorSize() > 0) {
//                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
//                            return RSLT_FORM_INCOMPLETE;
//                        }
//                    } catch (DBException dbexception) {
//                        excCode = dbexception.getErrorCode();
//                        msgString = getSystemMessage(excCode);
//                        return getControlMsgId(excCode);
//                    } catch (Exception exception) {
//                        System.out.println(exception);
//                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                    }
//                } 
//                break;
            default:    
        }
        return excCode;
    }
}