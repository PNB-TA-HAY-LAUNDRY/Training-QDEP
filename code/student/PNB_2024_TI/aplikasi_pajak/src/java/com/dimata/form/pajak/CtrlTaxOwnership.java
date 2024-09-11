/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PstTaxOwnership;
import com.dimata.entity.pajak.TaxOwnership;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.lang.I_Language;
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
}
