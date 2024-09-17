/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.entity.pajak.PstTaxOwnership;
import com.dimata.entity.pajak.TaxOwnership;
import com.dimata.entity.pajak.TaxType;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ihsan
 */
public class CtrlTaxOwnership extends Control implements I_Language {

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

    public int action(int cmd, long oidTax) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                // tidak ada codingan cuman pakai link ke Form 
                break;

            // function untuk pemanggilan data ke tabel 
            case Command.LIST:
                try {
                    Vector<TaxOwnership> taxOwnerships = PstTaxOwnership.listAll(0, 0, "", "");

                    request.setAttribute("taxOwnerships", taxOwnerships);
                } catch (Exception e) {
                    System.out.println("Exception " + e);
                }
                break;

            // case untuk Command.SAVE
            case Command.SAVE:
                // Membuat objek TaxOwnership untuk menampung data yang diambil dari form
                TaxOwnership taxOwnership = new TaxOwnership();

                // Mengambil data dari form dan menyetelnya ke objek taxOwnership
                taxOwnership.setNoPlat(request.getParameter("no_plat"));
                taxOwnership.setNamaPemilikLama(request.getParameter("nama_pemilik_lama"));
                taxOwnership.setNamaPemilikBaru(request.getParameter("nama_pemilik_baru"));
                taxOwnership.setAlamatBaru(request.getParameter("alamat_baru"));

                // Mengambil ID jenis pajak (tax_type_id) dari form, lalu di-convert ke Long
                long taxTypeId = Long.parseLong(request.getParameter("tax_type_id"));
                TaxType taxType = new TaxType(); // Asumsikan Anda punya class TaxType
                taxType.setTaxTypeId(taxTypeId);
                taxOwnership.setTaxType(taxType);

                // Mendapatkan jumlah pajak dan tanggal dari form
                taxOwnership.setJumlahPajak(Double.parseDouble(request.getParameter("jumlah_pajak")));

                // Mengonversi tanggal dari string (dari form) ke objek Date
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    taxOwnership.setTanggalProses(dateFormat.parse(request.getParameter("tanggal_proses")));
                    taxOwnership.setTanggalJatuhTempo(dateFormat.parse(request.getParameter("tanggal_jatuh_tempo")));
                    taxOwnership.setTanggalPembayaran(dateFormat.parse(request.getParameter("tanggal_pembayaran")));
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle error jika format tanggal tidak valid
                }

                // Mengambil status pembayaran dari form
                String statusPembayaran = request.getParameter("status_pembayaran");
                taxOwnership.setStatusPembayaran(PaymentStatus.valueOf(statusPembayaran.toUpperCase()));

                try {
                    // Memanggil method insertExc untuk menyimpan data ke database
                    PstTaxOwnership.insertExc(taxOwnership);

                    // Menambahkan pesan sukses setelah data disimpan
                    request.setAttribute("message", "Data berhasil disimpan.");
                } catch (DBException e) {
                    e.printStackTrace();
                    // Menambahkan pesan error jika terjadi kesalahan
                    request.setAttribute("message", "Terjadi kesalahan saat menyimpan data.");
                }
                break;

            // function untuk menghapus data dari tabel            
            case Command.DELETE:
                if (oidTax != 0) {
                    try {
                        long oid = pstTaxOwnership.deleteExc(oidTax);
                        if (oid != 0) {
                            msgString = "Data berhasil dihapus.";
                        } else {
                            msgString = "Data gagal dihapus.";
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
                }
                break;
            default:
        }
        return excCode;
    }
}
