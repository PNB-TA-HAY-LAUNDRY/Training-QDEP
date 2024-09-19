/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.entity.pajak.PstTaxOwnership;
import com.dimata.entity.pajak.PstTaxOwnerships;
import com.dimata.entity.pajak.TaxOwnership;
import com.dimata.entity.pajak.TaxOwnerships;
import com.dimata.entity.pajak.TaxType;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import static com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ihsan
 */
public class CtrlTaxOwnerships extends Control implements I_Language {

    private HttpServletRequest request; // HTTP request object

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_DELETE_RESTRICT = 4;
    public static final int DELETE = 1; // Sesuaikan dengan nilai yang digunakan

    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Kategori sudah ada", "Data tidak lengkap", "Kategori tidak bisa dihapus, masih dipakai modul lain ..."},
        {"Succes", "Can not process", "Category code already exist", "Data incomplete", "Cannot delete, category still used by another module"}
    };

    private int start;
    private String msgString;
    private TaxOwnerships taxOwnerships;
    private TaxOwnerships prevTaxOwnerships;
    private PstTaxOwnerships pstTaxOwnerships;
    private FrmTaxOwnerships frmTaxOwnerships;
    int language = LANGUAGE_FOREIGN;

    public CtrlTaxOwnerships(HttpServletRequest request) {
        this.request = request;
        msgString = "";
        taxOwnerships = new TaxOwnerships();
        try {
            pstTaxOwnerships = new PstTaxOwnerships(0);
        } catch (Exception e) {
            ; // Handle potential exception during initialization
        }
        // Initialize form handler with request and entity
        frmTaxOwnerships = new FrmTaxOwnerships(request, taxOwnerships);
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

    public TaxOwnerships getTaxOwnerships() {
        return taxOwnerships;
    }

    // Getter for the form handler
    public FrmTaxOwnerships getForm() {
        return frmTaxOwnerships;
    }

    // Getter for the message string
    public String getMessage() {
        return msgString;
    }

    // Getter for the start index
    public int getStart() {
        return start;
    }

    public int action(int cmd, long transferTaxId) {
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
                    Vector<TaxOwnerships> taxOwnerships = PstTaxOwnerships.listAll(0, 0, "", "");

                    request.setAttribute("taxOwnerships", taxOwnerships);
                } catch (Exception e) {
                    System.out.println("Exception " + e);
                }
                break;

            // case untuk Command.SAVE
            case Command.SAVE:
                // Membuat objek TaxOwnership untuk menampung data yang diambil dari form
                TaxOwnerships taxOwnerships = new TaxOwnerships();

                // Mengambil data dari form dan menyetelnya ke objek taxOwnership
                taxOwnerships.setNoPlat(request.getParameter("no_plat"));
                taxOwnerships.setNamaPemilikLama(request.getParameter("nama_pemilik_lama"));
                taxOwnerships.setNamaPemilikBaru(request.getParameter("nama_pemilik_baru"));
                taxOwnerships.setAlamatBaru(request.getParameter("alamat_baru"));
                taxOwnerships.setJenisPajak(request.getParameter("jenisPajak"));

                

                // Mendapatkan jumlah pajak dan tanggal dari form
                taxOwnerships.setJumlahPajak(Double.parseDouble(request.getParameter("jumlah_pajak")));

                // Mengonversi tanggal dari string (dari form) ke objek Date
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    taxOwnerships.setTanggalProses(dateFormat.parse(request.getParameter("tanggal_proses")));
                    taxOwnerships.setTanggalJatuhTempo(dateFormat.parse(request.getParameter("tanggal_jatuh_tempo")));
                    taxOwnerships.setTanggalPembayaran(dateFormat.parse(request.getParameter("tanggal_pembayaran")));
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle error jika format tanggal tidak valid
                }

                // Mengambil status pembayaran dari form
                String statusPembayaran = request.getParameter("status_pembayaran");
                taxOwnerships.setStatusPembayaran(PaymentStatus.valueOf(statusPembayaran.toUpperCase()));

                try {
                    // Memanggil method insertExc untuk menyimpan data ke database
                    PstTaxOwnerships.insertExc(taxOwnerships);

                    // Menambahkan pesan sukses setelah data disimpan
                    request.setAttribute("message", "Data berhasil disimpan.");
                } catch (DBException e) {
                    e.printStackTrace();
                    // Menambahkan pesan error jika terjadi kesalahan
                    request.setAttribute("message", "Terjadi kesalahan saat menyimpan data.");
                }
                break;

            // function untuk menghapus data dari tabel            
//            case Command.DELETE:
//                if (transferTaxId != 0) {
//                    try {
//                        long oid = PstTaxOwnership.deleteExc(transferTaxId);
//                        msgString = "Data berhasil dihapus.";
//                    } catch (DBException dbexc) {
//                        excCode = dbexc.getErrorCode();
//                        msgString = getSystemMessage(excCode);
//                        return getControlMsgId(excCode);
//                    } catch (Exception exc) {
//                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                    }
//                } else {
//                    msgString = "ID tidak valid untuk penghapusan.";
//                }
//                break;
            // function untuk mengedit data 
            case Command.EDIT:
                if (transferTaxId != 0) {
                    try {
                        // Fetch the asset list for editing
                        PstTaxOwnerships.fetchExc(transferTaxId);

                        // Set assetList to request attribute
//                        request.setAttribute("taxOwnerships", taxOwnership);
                        if (frmTaxOwnerships.errorSize() > 0) {
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
    

//     public int actionDelete(long transferTaxId) {
//        msgString = "";
//        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
//
//        if (transferTaxId != 0) {
//            try {
////                // Pertama, hapus relasi terkait jika ada
////                deleteRelatedData(transferTaxId);
//
//                // Kemudian, hapus data utama
//                long oid = PstTaxOwnerships.deleteExc(transferTaxId);
//                if (oid != 0) {
//                    msgString = "Data berhasil dihapus.";
//                } else {
//                    msgString = "Gagal menghapus data. ID tidak ditemukan.";
//                    return RSLT_UNKNOWN_ERROR;
//                }
//            } catch (DBException dbexc) {
//                excCode = dbexc.getErrorCode();
//                msgString = getSystemMessage(excCode);
//                return getControlMsgId(excCode);
//            } catch (Exception exc) {
//                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//            }
//        } else {
//            msgString = "ID tidak valid untuk penghapusan.";
//            return RSLT_FORM_INCOMPLETE;
//        }
//
//        return excCode;
//    }

//    private void deleteRelatedData(long transferTaxId) throws DBException {
//        System.out.println("Deleting related data for ID: " + transferTaxId);
//        PstTaxOwnership.deleteByTaxTypeId(transferTaxId);
//    }

}
