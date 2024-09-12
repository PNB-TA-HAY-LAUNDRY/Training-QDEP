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

/**
 *
 * @author ihsan
 */
public class CtrlTaxVehicle extends Control implements I_Language{
    
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
                    Vector <TaxVehicle> taxVehicles = PstTaxVehicle.listAll(0, 0, "", "");
                    
                    request.setAttribute("taxvehicles", taxVehicles);
                } catch (Exception e){
                    System.out.println("Exception " + e);
                } break;
             
                
                
                
            // function untuk menyimpan data dari form ke dalam tabel    
            case Command.SAVE:
                    String taxOldPlat = "";
                    if (oidTax != 0){
                        try {
                            taxVehicle = PstTaxVehicle.fetchExc(oidTax);
                            prevTaxvehicle = PstTaxVehicle.fetchExc(oidTax);
                            taxOldPlat = taxVehicle.getNoPlat();
                        } catch (Exception exc){
                        
                        }
                    }
                    
                    frmTaxVehicle.requestEntityObject(taxVehicle);
                    if (frmTaxVehicle.errorSize() > 0){
                        msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                        return RSLT_FORM_INCOMPLETE;
                    }
                    
                    if (taxVehicle.getOID() == 0){
                        try {
                            boolean checkedPlat = pstTaxVehicle.checkTaxVehicle(taxVehicle.getNoPlat(),1 );
                            
                            if (checkedPlat == false){
                                long oid = pstTaxVehicle.insertExc(this.taxVehicle);
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
                            long oid = pstTaxVehicle.updateExc(this.taxVehicle);
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
                        long oid = pstTaxVehicle.deleteExc(taxVehicle);
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
                        taxVehicle = PstTaxVehicle.fetchExc(oidTax);

                        // Set assetList to request attribute
                        request.setAttribute("taxvehicles", taxVehicle);

                        if (frmTaxVehicle.errorSize() > 0) {
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
