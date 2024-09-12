/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PstTaxOwnership;
import com.dimata.entity.pajak.TaxOwnership;
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
 * @author ihsan
 */
public class CtrlTaxOwnership extends Control implements I_Language{
    
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
    private TaxOwnership taxOwnership;
    private TaxOwnership prevTaxOwnership;
    private PstTaxOwnership pstTaxOwnership;
    private FrmTaxOwnership frmTaxOwnership;
    int language = LANGUAGE_FOREIGN;
    
    public CtrlTaxOwnership(HttpServletRequest request) {
        this.request = request;
        msgString = "";
        taxOwnership = new TaxOwnership();
        try {
            pstTaxOwnership = new PstTaxOwnership(0);
        } catch (Exception e) {
            ; // Handle potential exception during initialization
        }
        // Initialize form handler with request and entity
        frmTaxOwnership = new FrmTaxOwnership(request, taxOwnership);
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
    
    public TaxOwnership getTaxOwnership() {
        return taxOwnership;
    }
    
    // Getter for the form handler
    public FrmTaxOwnership getForm() {
        return frmTaxOwnership;
    }
    
    // Getter for the message string
    public String getMessage() {
        return msgString;
    }

    // Getter for the start index
    public int getStart() {
        return start;
    }
    
    public int action (int cmd, long oidTax){
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd){
            case Command.ADD:
                // tidak ada codingan cuman pakai link ke Form 
                break;
          
                
                
            // function untuk pemanggilan data ke tabel 
            case Command.LIST:
                try {
                    Vector <TaxOwnership> taxOwnerships = PstTaxOwnership.listAll(0, 0, "", "");
                    
                    request.setAttribute("taxOwnerships", taxOwnerships);
                } catch (Exception e){
                    System.out.println("Exception " + e);
                } break;
             
                
                
                
            // function untuk menyimpan data dari form ke dalam tabel    
            case Command.SAVE:
                    String taxOldPlat = "";
                    if (oidTax != 0){
                        try {
                            taxOwnership = PstTaxOwnership.fetchExc(oidTax);
                            prevTaxOwnership = PstTaxOwnership.fetchExc(oidTax);
                            taxOldPlat = taxOwnership.getNoPlat();
                        } catch (Exception exc){
                        
                        }
                    }
                    
                    frmTaxOwnership.requestEntityObject(taxOwnership);
                    if (frmTaxOwnership.errorSize() > 0){
                        msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                        return RSLT_FORM_INCOMPLETE;
                    }
                    
                    if (taxOwnership.getOID() == 0){
                        try {
                            boolean checkedPlat = pstTaxOwnership.checkTaxOwnership(taxOwnership.getNoPlat(),1 );
                            
                            if (checkedPlat == false){
                                long oid = pstTaxOwnership.insertExc(this.taxOwnership);
                            } else {
                                msgString = getSystemMessage(I_DBExceptionInfo.MULTIPLE_ID);
                                return getControlMsgId(I_DBExceptionInfo.MULTIPLE_ID);
                            }
                        } catch (DBException dbexception){
                            System.out.println("Go to DBException");
                            System.out.println("dbexception");
                            excCode = dbexception.getErrorCode();
                            msgString = getSystemMessage(excCode);
                            System.out.println(msgString);
                            return getControlMsgId(excCode);
                        } catch  (Exception exception){
                            System.out.println("Go to Exception");
                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                        }
                    } else {
                        try {
                            long oid = pstTaxOwnership.updateExc(this.taxOwnership);
                        } catch (DBException dbexception){
                            excCode = dbexception.getErrorCode();
                            msgString = getSystemMessage(excCode);
                            return getControlMsgId(excCode);
                        } catch (Exception exception){
                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                        }
                    } break;
                
                    
                    
            // function untuk menghapus data dari tabel            
            case Command.DELETE:
                if (oidTax != 0){
                    try {
                        long oid = pstTaxOwnership.deleteExc(taxOwnership);
                    } catch (DBException dbexception){
                        excCode = dbexception.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exception){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                } break;
                    
                
                
                
            // function untuk mengedit data 
            case Command.EDIT:
                if (oidTax != 0) {
                    try {
                        // Fetch the asset list for editing
                        taxOwnership = PstTaxOwnership.fetchExc(oidTax);

                        // Set assetList to request attribute
                        request.setAttribute("taxOwnerships", taxOwnership);

                        if (frmTaxOwnership.errorSize() > 0) {
                            // Incomplete form message
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE;
                        }
                    } catch (DBException dbexception) {
                        // Handle database exception
                        excCode = dbexception.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exception) {
                        // Handle general exceptions
                        System.out.println(exception);
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                } break;
            default:    
        }
        return excCode;
    }
}
