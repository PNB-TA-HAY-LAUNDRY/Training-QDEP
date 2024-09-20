package com.dimata.form.pajak;

import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.entity.pajak.PstTaxFuelSales;
import com.dimata.entity.pajak.TaxFuelSales;
import com.dimata.entity.pajak.TaxType;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import java.util.Vector;

public class CtrlTaxFuelSales extends Control implements I_Language {

    private HttpServletRequest request;

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_DELETE_RESTRICT = 4;

    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Data sudah ada", "Data tidak lengkap", "Data tidak bisa dihapus, masih dipakai modul lain ..."},
        {"Success", "Cannot process", "Data already exists", "Data incomplete", "Cannot delete, data is still used by another module"}
    };

    private int start;
    private String msgString;
    private TaxFuelSales taxFuelSales;
    private PstTaxFuelSales pstTaxFuelSales;
    private FrmTaxFuelSales frmTaxFuelSales;
    int language = LANGUAGE_FOREIGN;

    public CtrlTaxFuelSales(HttpServletRequest request) {
        this.request = request;
        msgString = "";
        taxFuelSales = new TaxFuelSales();
        try {
            pstTaxFuelSales = new PstTaxFuelSales(0);
        } catch (Exception e) {
            ; // Handle potential exception during initialization
        }
        frmTaxFuelSales = new FrmTaxFuelSales(request, taxFuelSales);
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

    public TaxFuelSales getTaxFuelSales() {
        return taxFuelSales;
    }

    public FrmTaxFuelSales getForm() {
        return frmTaxFuelSales;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidTax) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;

        switch (cmd) {
            case Command.ADD:
                break;

            case Command.LIST:
                try {
                    Vector<TaxFuelSales> taxFuelSalesList = PstTaxFuelSales.listAll(0, 0, "", "");
                    request.setAttribute("taxFuelSalesList", taxFuelSalesList);
                } catch (Exception e) {
                    System.out.println("Exception " + e);
                }
                break;

            case Command.SAVE:
                break;

            case Command.DELETE:
                if (oidTax != 0) {
                    try {
                        long oid = pstTaxFuelSales.deleteExc(oidTax);
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

            case Command.EDIT:
                if (oidTax != 0) {
                    try {
                        taxFuelSales = PstTaxFuelSales.fetchExc(oidTax);
                        request.setAttribute("taxFuelSales", taxFuelSales);

                        if (frmTaxFuelSales.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE;
                        }
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

            default:
                ;
        }

        return excCode;
    }
    
    public int actionDelete(long fuelTaxId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;

        if (fuelTaxId != 0) {
            try {

                // Kemudian, hapus data utama
                long oid = PstTaxFuelSales.deleteExc(fuelTaxId);
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