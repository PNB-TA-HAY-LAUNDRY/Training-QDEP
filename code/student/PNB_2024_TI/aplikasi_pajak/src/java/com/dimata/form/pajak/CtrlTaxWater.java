/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PstTaxWater;
import com.dimata.entity.pajak.TaxWater;
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
 * @author [Your Name]
 */
public class CtrlTaxWater extends Control implements I_Language {

    private HttpServletRequest request; // HTTP request object

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_DELETE_RESTRICT = 4;

    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Kode sudah ada", "Data tidak lengkap", "Data tidak bisa dihapus, masih digunakan modul lain ..."},
        {"Success", "Cannot process", "Code already exists", "Data incomplete", "Cannot delete, data still used by another module"}
    };

    private int start;
    private String msgString;
    private TaxWater taxWater;
    private TaxWater prevTaxWater;
    private PstTaxWater pstTaxWater;
//    private FrmTaxWater frmTaxWater;
    int language = LANGUAGE_FOREIGN;

    public CtrlTaxWater(HttpServletRequest request) {
        this.request = request;
        msgString = "";
        taxWater = new TaxWater();
        try {
            pstTaxWater = new PstTaxWater(0);
        } catch (Exception e) {
            e.printStackTrace(); // Handle potential exception during initialization
        }
        // Initialize form handler with request and entity
//        frmTaxWater = new FrmTaxWater(request, taxWater);
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

    public TaxWater getTaxWater() {
        return taxWater;
    }

    // Getter for the form handler
//    public FrmTaxWater getForm() {
//        return frmTaxWater;
//    }

    // Getter for the message string
    public String getMessage() {
        return msgString;
    }

    // Getter for the start index
    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidTaxWater) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                // No coding, just link to Form
                break;

            // Function for fetching data from the table
            case Command.LIST:
                try {
                    Vector<TaxWater> taxWaters = PstTaxWater.listAll(0, 0, "", "");
                    request.setAttribute("taxWaters", taxWaters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            // Function for saving data from form to table
//            case Command.SAVE:
//                String taxOldCode = "";
//                if (oidTaxWater != 0) {
//                    try {
//                        taxWater = PstTaxWater.fetchExc(oidTaxWater);
//                        prevTaxWater = PstTaxWater.fetchExc(oidTaxWater);
//                        taxOldCode = taxWater.getPengguna();
//                    } catch (Exception exc) {
//                        exc.printStackTrace();
//                    }
//                }
//
//                frmTaxWater.requestEntityObject(taxWater);
//                if (frmTaxWater.errorSize() > 0) {
//                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
//                    return RSLT_FORM_INCOMPLETE;
//                }

//                if (taxWater.getOID() == 0) {
//                    try {
//                        boolean checkedCode = pstTaxWater.checkTaxWater(taxWater.getPengguna(), 1);
//                        if (!checkedCode) {
//                            long oid = pstTaxWater.insertExc(this.taxWater);
//                        } else {
//                            msgString = getSystemMessage(I_DBExceptionInfo.MULTIPLE_ID);
//                            return getControlMsgId(I_DBExceptionInfo.MULTIPLE_ID);
//                        }
//                    } catch (DBException dbexception) {
//                        excCode = dbexception.getErrorCode();
//                        msgString = getSystemMessage(excCode);
//                        return getControlMsgId(excCode);
//                    } catch (Exception exception) {
//                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                    }
//                } else {
//                    try {
//                        long oid = pstTaxWater.updateExc(this.taxWater);
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

            // Function for deleting data from the table
            case Command.DELETE:
                if (oidTaxWater != 0) {
                    try {
                        long oid = pstTaxWater.deleteExc(taxWater);
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

            // Function for editing data
//            case Command.EDIT:
//                if (oidTaxWater != 0) {
//                    try {
//                        taxWater = PstTaxWater.fetchExc(oidTaxWater);
//                        request.setAttribute("taxWaters", taxWater);
//                        if (frmTaxWater.errorSize() > 0) {
//                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
//                            return RSLT_FORM_INCOMPLETE;
//                        }
//                    } catch (DBException dbexception) {
//                        excCode = dbexception.getErrorCode();
//                        msgString = getSystemMessage(excCode);
//                        return getControlMsgId(excCode);
//                     } catch (Exception exception) {
//                        // Handle general exceptions
//                        System.out.println(exception);
//                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                    }
//                } break;
            default:    
        }
        return excCode;
    }
}