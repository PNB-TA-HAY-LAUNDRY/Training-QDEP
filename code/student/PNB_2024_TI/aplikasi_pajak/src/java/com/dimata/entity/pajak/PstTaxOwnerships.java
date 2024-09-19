/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.entity.pajak;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import static com.dimata.qdep.db.I_DBType.TYPE_DATE;
import static com.dimata.qdep.db.I_DBType.TYPE_FLOAT;
import static com.dimata.qdep.db.I_DBType.TYPE_ID;
import static com.dimata.qdep.db.I_DBType.TYPE_LONG;
import static com.dimata.qdep.db.I_DBType.TYPE_PK;
import static com.dimata.qdep.db.I_DBType.TYPE_STRING;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author ihsan
 */
public class PstTaxOwnerships extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
   public static final String TBL_TAX_OWNER = "vehicle_ownership_transfer_tax_record";

    public static final int FLD_TRANSFER_TAX_ID = 0;
    public static final int FLD_NO_PLAT = 1;
    public static final int FLD_OLD_NAME = 2;
    public static final int FLD_NEW_NAME = 3;
    public static final int FLD_NEW_ADDRESS = 4;
    public static final int FLD_TAX_TYPE = 5;
    public static final int FLD_TOTAL_TAX = 6;
    public static final int FLD_PROCESS_DATE = 7;
    public static final int FLD_PAY_STATUS = 8;
    public static final int FLD_DUE_DATE = 9;
    public static final int FLD_PAY_DATE = 10;

    public static final String[] fieldNames = {
        "transfer_tax_id",
        "no_plat",
        "nama_pemilik_lama",
        "nama_pemilik_baru",
        "alamat_baru",
        "jenis_pajak",
        "jumlah_pajak",
        "tanggal_proses",
        "status_pembayaran",
        "tanggal_jatuh_tempo",
        "tanggal_pembayaran",};

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, // 0
        TYPE_STRING,// no plat
        TYPE_STRING,// nama_pemilik_lama
        TYPE_STRING,// nama_pemilik_baru
        TYPE_STRING,// alamat_baru
        TYPE_STRING,// tax_type_id
        TYPE_FLOAT,// jumlah_pajak
        TYPE_DATE,// tanggal_proses
        TYPE_STRING,// status_pembayaran
        TYPE_DATE,// tanggal_jatuh_tempo
        TYPE_DATE,// tanggal pembayaran
    };

    public PstTaxOwnerships() {
    }

    public PstTaxOwnerships(int i) throws DBException {
        super(new PstTaxOwnerships());
    }

    public PstTaxOwnerships(String sOid) throws DBException {
        super(new PstTaxOwnerships(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    public PstTaxOwnerships(long lOid) throws DBException {
        super(new PstTaxOwnerships(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getTableName() {
        return TBL_TAX_OWNER;
    }

    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public int[] getFieldTypes() {
        return fieldTypes;
    }

    @Override
    public String getPersistentName() {
        return new PstTaxOwnerships().getClass().getName();
    }

    public static void insertExc(TaxOwnerships taxOwnerships) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBHandler.getConnection(); // Mendapatkan koneksi dari DBHandler
            String sql = "INSERT INTO vehicle_ownership_transfer_tax_record (no_plat, nama_pemilik_lama, nama_pemilik_baru, alamat_baru, jenis_pajak, jumlah_pajak, tanggal_proses, tanggal_jatuh_tempo, status_pembayaran, tanggal_pembayaran) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, taxOwnerships.getNoPlat());
            pstmt.setString(2, taxOwnerships.getNamaPemilikLama());
            pstmt.setString(3, taxOwnerships.getNamaPemilikBaru());
            pstmt.setString(4, taxOwnerships.getAlamatBaru());
            pstmt.setString(5, taxOwnerships.getJenisPajak());
            pstmt.setDouble(6, taxOwnerships.getJumlahPajak());
            pstmt.setDate(7, (Date) taxOwnerships.getTanggalProses());
            pstmt.setDate(8, (Date) taxOwnerships.getTanggalJatuhTempo());
            pstmt.setString(9, taxOwnerships.getStatusPembayaran().toString());
            pstmt.setDate(10, (Date) taxOwnerships.getTanggalPembayaran());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(new PstTaxOwnerships(), DBException.UNKNOWN);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close(); // Hanya jika Anda membuka koneksi di sini
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxOwnerships) ent);
    }

    public long updateExc(TaxOwnerships taxOwnerships) throws DBException {
        try {
            PstTaxOwnerships pstTaxOwnerships = new PstTaxOwnerships(taxOwnerships.getOID());
            pstTaxOwnerships.setString(FLD_NO_PLAT, taxOwnerships.getNoPlat());
            pstTaxOwnerships.setString(FLD_OLD_NAME, taxOwnerships.getNamaPemilikLama());
            pstTaxOwnerships.setString(FLD_NEW_NAME, taxOwnerships.getNamaPemilikBaru());
            pstTaxOwnerships.setString(FLD_NEW_ADDRESS, taxOwnerships.getAlamatBaru());
            pstTaxOwnerships.setString(FLD_TAX_TYPE, taxOwnerships.getJenisPajak());
            pstTaxOwnerships.setDouble(FLD_TOTAL_TAX, taxOwnerships.getJumlahPajak());
            pstTaxOwnerships.setString(FLD_PAY_STATUS, taxOwnerships.getStatusPembayaran().getStatus());
            pstTaxOwnerships.setDate(FLD_PROCESS_DATE, taxOwnerships.getTanggalProses());
            pstTaxOwnerships.setDate(FLD_DUE_DATE, taxOwnerships.getTanggalJatuhTempo());
            pstTaxOwnerships.setDate(FLD_PAY_DATE, taxOwnerships.getTanggalPembayaran());
        } catch (DBException e) {
            throw e;
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return 0;
    }

    public static int deleteById(long id) throws DBException {
        Connection connection = null;
        Statement statement = null;
        int result = -1;
        try {
            connection = DBHandler.getConnection();
            statement = connection.createStatement();
            String sql = "DELETE FROM " + TBL_TAX_OWNER + " WHERE " + fieldNames[FLD_TRANSFER_TAX_ID] + " = " + id;
            result = statement.executeUpdate(sql);
            if (result == 0) {
                throw new DBException(new PstTaxOwnerships(), DBException.RECORD_NOT_FOUND);
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace(System.err);
            throw new DBException(new PstTaxType(), sqlexception);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }
   
//     public static long deleteExc(long transferTaxId) throws DBException {
//        String query = "DELETE FROM " + TBL_TAX_OWNER + " WHERE " + FLD_TRANSFER_TAX_ID + " = ?";
//        System.out.println("Executing query: " + query + " with ID: " + transferTaxId);
//
//        try (Connection conn = DBHandler.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
//            pst.setLong(1, transferTaxId);
//            int result = pst.executeUpdate();
//            System.out.println("Result of deletion: " + result);
//
//            if (result > 0) {
//                return transferTaxId;
//            } else {
//                return 0; // Tidak ada data yang dihapus
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL Error: " + e.getMessage());
//            throw new DBException(e);
//        }
//    }
     
//     public static void deleteByTaxTypeId(long transferTaxId) throws DBException {
//        String query = "DELETE FROM vehicle_ownership_transfer_tax_records WHERE tax_type_id = ?";
//        System.out.println("Executing related data deletion query: " + query + " with ID: " + transferTaxId);
//
//        try (Connection conn = DBHandler.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
//            pst.setLong(1, transferTaxId);
//            int result = pst.executeUpdate();
//            System.out.println("Result of related data deletion: " + result);
//        } catch (SQLException e) {
//            System.err.println("SQL Error: " + e.getMessage());
//            throw new DBException(e);
//        }
//    }
//
    
    
    @Override
    public long fetchExc(Entity ent) throws Exception {
        TaxOwnerships taxOwnerships = fetchExc(ent.getOID());
        ent = (Entity) taxOwnerships;
        return taxOwnerships.getOID();
    }

    public static TaxOwnerships fetchExc(long oid) throws DBException {
        try {
            TaxOwnerships taxOwnerships = new TaxOwnerships();
            PstTaxOwnerships pstTaxOwnerships = new PstTaxOwnerships(oid);
            taxOwnerships.setOID(oid);
            taxOwnerships.setTransferTaxId(pstTaxOwnerships.getlong(FLD_TRANSFER_TAX_ID));
            taxOwnerships.setNoPlat(pstTaxOwnerships.getString(FLD_NO_PLAT));
            taxOwnerships.setNamaPemilikLama(pstTaxOwnerships.getString(FLD_OLD_NAME));
            taxOwnerships.setNamaPemilikBaru(pstTaxOwnerships.getString(FLD_NEW_NAME));
            taxOwnerships.setAlamatBaru(pstTaxOwnerships.getString(FLD_NEW_ADDRESS));
            taxOwnerships.setJenisPajak(pstTaxOwnerships.getString(FLD_TAX_TYPE));

            

            taxOwnerships.setJumlahPajak(pstTaxOwnerships.getdouble(FLD_TOTAL_TAX));
            taxOwnerships.setTanggalProses(pstTaxOwnerships.getDate(FLD_PROCESS_DATE));
            taxOwnerships.setTanggalJatuhTempo(pstTaxOwnerships.getDate(FLD_DUE_DATE));

            String statusPembayaranStr = pstTaxOwnerships.getString(FLD_PAY_STATUS);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxOwnerships.setStatusPembayaran(statusPembayaran);

            taxOwnerships.setTanggalPembayaran(pstTaxOwnerships.getDate(FLD_PAY_DATE));
            return taxOwnerships;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxOwnerships(0), DBException.UNKNOWN);
        }
    }

    public static Vector listAll(int limitStart, int recordToGet, String whereClause, String order) {
        return list(limitStart, recordToGet, whereClause, order, null);
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order, String join) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_OWNER;

            if (join != null && !join.isEmpty()) {
                sql += " JOIN " + join;
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql += " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql += " ORDER BY " + order;
            }

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart >= 0 && recordToGet > 0) {
                        sql += " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;

                case DBHandler.DBSVR_SYBASE:
                case DBHandler.DBSVR_ORACLE:
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    break;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                TaxOwnerships taxOwnerships = new TaxOwnerships();
                resultToObject(rs, taxOwnerships);
                lists.add(taxOwnerships);
            }

            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static void resultToObject(ResultSet rs, TaxOwnerships taxOwnerships) {
        try {
            taxOwnerships.setTransferTaxId(rs.getLong(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_TRANSFER_TAX_ID]));
            taxOwnerships.setNoPlat(rs.getString(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_NO_PLAT]));
            taxOwnerships.setNamaPemilikLama(rs.getString(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_OLD_NAME]));
            taxOwnerships.setNamaPemilikBaru(rs.getString(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_NEW_NAME]));
            taxOwnerships.setAlamatBaru(rs.getString(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_NEW_ADDRESS]));
            taxOwnerships.setJenisPajak(rs.getString(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_TAX_TYPE]));

            

            taxOwnerships.setJumlahPajak(rs.getDouble(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_TOTAL_TAX]));
            taxOwnerships.setTanggalProses(rs.getDate(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_PROCESS_DATE]));

            taxOwnerships.setTanggalJatuhTempo(rs.getDate(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_DUE_DATE]));

            String statusPembayaranStr = rs.getString(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_PAY_STATUS]);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxOwnerships.setStatusPembayaran(statusPembayaran);

            taxOwnerships.setTanggalPembayaran(rs.getDate(PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_PAY_DATE]));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static boolean checkTaxOwnerships(String no, int type) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "";

            if (type == 1) {
                sql = "SELECT * FROM " + TBL_TAX_OWNER + " WHERE "
                        + PstTaxOwnerships.fieldNames[PstTaxOwnerships.FLD_NO_PLAT] + " = '" + no + "'";
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    @Override
    public long insertExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long deleteExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
