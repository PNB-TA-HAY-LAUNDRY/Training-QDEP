/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.form.pajak;

import com.dimata.entity.pajak.PaymentStatus;
import com.dimata.entity.pajak.PstTaxWater;
import com.dimata.entity.pajak.TaxWater;
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
 * @author [andin
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
    private FrmTaxWater frmTaxWater;
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
        frmTaxWater = new FrmTaxWater(request, taxWater);
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
    public FrmTaxWater getForm() {
        return frmTaxWater;
    }

    // Getter for the message string
    public String getMessage() {
        return msgString;
    }

    // Getter for the start index
    public int getStart() {
        return start;
    }

    public int action(int cmd, long TaxWaterUsageId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                // No coding, just link to Form
                break;

            // function untuk pemanggilan data ke tabel 
            case Command.LIST:
                try {
                    Vector<TaxWater> taxWaters = PstTaxWater.listAll(0, 0, "", "");

                    request.setAttribute("taxWaters", taxWaters);
                } catch (Exception e) {
                    System.out.println("Exception " + e);
                }
                break;

            // Function for fetching data from the table
            case Command.SAVE:
                // Membuat objek TaxWater untuk menampung data yang diambil dari form
                TaxWater taxWater = new TaxWater();

                // Mengambil data dari form dan menyetelnya ke objek TaxWater
                taxWater.setPengguna(request.getParameter("pengguna"));
                taxWater.setLokasi(request.getParameter("lokasi"));
//                taxWater.setVolumeAirM3(request.getParameter("volume_air_m3"));
                taxWater.setVolumeAirM3(Double.parseDouble(request.getParameter("volume_air_m3")));

                // Mengambil ID jenis pajak (tax_type_id) dari form, lalu di-convert ke Long
                long taxTypeId = Long.parseLong(request.getParameter("tax_type_id"));
                TaxType taxType = new TaxType(); // Asumsikan Anda punya class TaxType
                taxType.setTaxTypeId(taxTypeId);
                taxWater.setTaxType(taxType);
                // Mendapatkan jumlah pajak dan tanggal dari form
                taxWater.setJumlahPajak(Double.parseDouble(request.getParameter("jumlah_pajak")));

                // Mengonversi tanggal dari string (dari form) ke objek Date
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    taxWater.setPeriodePajak(dateFormat.parse(request.getParameter("periode_pajak")));
                    taxWater.setTanggalJatuhTempo(dateFormat.parse(request.getParameter("tanggal_jatuh_tempo")));
                    // Mengambil status pembayaran dari form
                String statusPembayaran = request.getParameter("status_pembayaran");
                taxWater.setStatusPembayaran(PaymentStatus.valueOf(statusPembayaran.toUpperCase()));
                    taxWater.setTanggalPembayaran(dateFormat.parse(request.getParameter("tanggal_pembayaran")));
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle error jika format tanggal tidak valid
                }

                

                try {
                    // Memanggil method insertExc untuk menyimpan data ke database
                    PstTaxWater.insertExc(taxWater);

                    // Menambahkan pesan sukses setelah data disimpan
                    request.setAttribute("message", "Data berhasil disimpan.");
                } catch (DBException e) {
                    e.printStackTrace();
                    // Menambahkan pesan error jika terjadi kesalahan
                    request.setAttribute("message", "Terjadi kesalahan saat menyimpan data.");
                }
                break;

            // Function for deleting data from the table
            //           case Command.DELETE:
            //             if (TaxWaterUsageId != 0) {
                
            //               try {
            //                 pstTaxWater.deleteExc(TaxWaterUsageId);
            //           } catch (DBException dbexception) {
            //             excCode = dbexception.getErrorCode();
            //           msgString = getSystemMessage(excCode);
            //         return getControlMsgId(excCode);
            //   } catch (Exception exception) {
            //     msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
            //   return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            //}
            //}
            //break;
            // Function for editing data
            case Command.EDIT:
                if (TaxWaterUsageId != 0) {
                    try {
                        // Fetch the asset list for editing
                        PstTaxWater.fetchExc(TaxWaterUsageId);

                        // Set assetList to request attribute
//                        request.setAttribute("taxwaters", taxwater);
                        if (frmTaxWater.errorSize() > 0) {
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

    
    public int actionDelete(long waterUsageId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;

        if (waterUsageId != 0) {
            try {
//                // Pertama, hapus relasi terkait jika ada
//                deleteRelatedData(transferTaxId);

                // Kemudian, hapus data utama
                long oid = PstTaxWater.deleteExc(waterUsageId);
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