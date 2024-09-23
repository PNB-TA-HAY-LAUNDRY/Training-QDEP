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
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.entity.Entity;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author andin
 */
public class PstTaxWater extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_WATER = "water_usage_records";

    public static final int FLD_WATER_USAGE_ID = 0;
    public static final int FLD_PENGGUNA = 1;
    public static final int FLD_LOKASI = 2;
    public static final int FLD_VOLUME_AIR_M3 = 3;
    public static final int FLD_TAX_TYPE_ID = 4;
    public static final int FLD_JUMLAH_PAJAK = 5;
    public static final int FLD_PERIODE_PAJAK = 6;
    public static final int FLD_STATUS_PEMBAYARAN = 7;
    public static final int FLD_TANGGAL_JATUH_TEMPO = 8;
    public static final int FLD_TANGGAL_PEMBAYARAN = 9;

    public static final String[] fieldNames = {
        "water_usage_id",
        "pengguna",
        "lokasi",
        "volume_air_m3",
        "tax_type_id",
        "jumlah_pajak",
        "periode_pajak",
        "status_pembayaran",
        "tanggal_jatuh_tempo",
        "tanggal_pembayaran"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_STRING,// status_pembayaran
        TYPE_DATE,
        TYPE_DATE
    };

    public PstTaxWater() {
    }

    public PstTaxWater(int i) throws DBException {
        super(new PstTaxWater());
    }

    public PstTaxWater(String sOid) throws DBException {
        super(new PstTaxWater(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    public PstTaxWater(long lOid) throws DBException {
        super(new PstTaxWater(0));
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
        return TBL_TAX_WATER;
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
        return new PstTaxWater().getClass().getName();
    }

    @Override
    public long fetchExc(Entity ent) throws Exception {
        TaxWater taxWater = fetchExc(ent.getOID());
        ent = (Entity) taxWater;
        return taxWater.getOID();
    }

    public static TaxWater fetchExc(long oid) throws DBException {
        try {
            TaxWater taxWater = new TaxWater();
            PstTaxWater pstTaxWater = new PstTaxWater(oid);
            taxWater.setOID(oid);
            taxWater.setPengguna(pstTaxWater.getString(FLD_PENGGUNA));
            taxWater.setLokasi(pstTaxWater.getString(FLD_LOKASI));
            taxWater.setVolumeAirM3(pstTaxWater.getdouble(FLD_VOLUME_AIR_M3));

            long taxTypeId = pstTaxWater.getlong(FLD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();
            taxType.setOID(taxTypeId);
            taxWater.setTaxType(taxType);

            taxWater.setJumlahPajak(pstTaxWater.getdouble(FLD_JUMLAH_PAJAK));
            taxWater.setPeriodePajak(pstTaxWater.getDate(FLD_PERIODE_PAJAK));
            taxWater.setTanggalJatuhTempo(pstTaxWater.getDate(FLD_TANGGAL_JATUH_TEMPO));
            String statusPembayaranStr = pstTaxWater.getString(FLD_STATUS_PEMBAYARAN);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxWater.setStatusPembayaran(statusPembayaran);
            taxWater.setTanggalPembayaran(pstTaxWater.getDate(FLD_TANGGAL_PEMBAYARAN));

            return taxWater;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxWater(0), DBException.UNKNOWN);
        }
    }

        public static void insertExc(TaxWater taxWater) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBHandler.getConnection(); // Mendapatkan koneksi dari DBHandler
            String sql = "INSERT INTO water_usage_records (pengguna, lokasi, volume_air_m3, tax_type_id, jumlah_pajak, periode_pajak, tanggal_jatuh_tempo, status_pembayaran, tanggal_pembayaran) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, taxWater.getPengguna());
            pstmt.setString(2, taxWater.getLokasi());
            pstmt.setDouble(3, taxWater.getVolumeAirM3());
            pstmt.setLong(4, taxWater.getTaxType().getTaxTypeId());
            pstmt.setDouble(5, taxWater.getJumlahPajak());
            pstmt.setDate(6, (Date) taxWater.getPeriodePajak());
            pstmt.setDate(7, (Date) taxWater.getTanggalJatuhTempo());
            pstmt.setString(8, taxWater.getStatusPembayaran().toString());
            pstmt.setDate(9, (Date) taxWater.getTanggalPembayaran());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(new PstTaxWater(), DBException.UNKNOWN);
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
        return updateExc((TaxWater) ent);
    }

    public long updateExc(TaxWater taxWater) throws DBException {
        try {
            if (taxWater.getOID() != 0) {
                PstTaxWater pstTaxWater = new PstTaxWater(taxWater.getOID());
                pstTaxWater.setString(FLD_PENGGUNA, taxWater.getPengguna());
                pstTaxWater.setString(FLD_LOKASI, taxWater.getLokasi());
                pstTaxWater.setDouble(FLD_VOLUME_AIR_M3, taxWater.getVolumeAirM3());
                pstTaxWater.setLong(FLD_TAX_TYPE_ID, taxWater.getTaxType().getOID());
                pstTaxWater.setDouble(FLD_JUMLAH_PAJAK, taxWater.getJumlahPajak());
                pstTaxWater.setDate(FLD_PERIODE_PAJAK, taxWater.getPeriodePajak());
                pstTaxWater.setDate(FLD_TANGGAL_JATUH_TEMPO, taxWater.getTanggalJatuhTempo());
                pstTaxWater.setString(FLD_STATUS_PEMBAYARAN, taxWater.getStatusPembayaran().getStatus());
                pstTaxWater.setDate(FLD_TANGGAL_PEMBAYARAN, taxWater.getTanggalPembayaran());

                pstTaxWater.update(); // Perbarui record di database
                return taxWater.getOID(); // Kembalikan OID record yang diperbarui
            }
        } catch (Exception e) {
            throw new DBException(new PstTaxWater(0), DBException.UNKNOWN);
        }
        return 0;
    }


    //Metode Delete
    public static long deleteExc(long waterUsageId) throws DBException {
        String query = "DELETE FROM " + TBL_TAX_WATER + " WHERE " + fieldNames[FLD_WATER_USAGE_ID] + " = ?";
        System.out.println("Executing query: " + query + " with ID: " + waterUsageId);

        try (Connection conn = DBHandler.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setLong(1, waterUsageId);
            int result = pst.executeUpdate();
            System.out.println("Result of deletion: " + result);

            if (result > 0) {
                return waterUsageId; // Penghapusan berhasil, mengembalikan ID yang dihapus
            } else {
                return 0; // Tidak ada data yang dihapus
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw new DBException(e); // Menangani kesalahan SQL 
        }
    }

    
    public static Vector listAll(int limitStart, int recordToGet, String whereClause, String order) {
        return list(limitStart, recordToGet, whereClause, order, null);
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order, String join) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_WATER;

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
                TaxWater taxwater = new TaxWater();
                resultToObject(rs, taxwater);
                lists.add(taxwater);
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
    

    private static void resultToObject(ResultSet rs, TaxWater taxWater) {
        try {
            taxWater.setOID(rs.getLong(PstTaxWater.fieldNames[FLD_WATER_USAGE_ID]));
            taxWater.setPengguna(rs.getString(PstTaxWater.fieldNames[FLD_PENGGUNA]));
            taxWater.setLokasi(rs.getString(PstTaxWater.fieldNames[FLD_LOKASI]));
            taxWater.setVolumeAirM3(rs.getDouble(PstTaxWater.fieldNames[FLD_VOLUME_AIR_M3]));

            // Tambahkan ini untuk mendapatkan nama jenis pajak
            long taxTypeId = rs.getLong(PstTaxWater.fieldNames[FLD_TAX_TYPE_ID]);
            TaxType taxType = PstTaxType.fetchExc(taxTypeId); // Ambil data TaxType
            taxWater.setTaxType(taxType); // Simpan objek TaxType ke dalam taxWater

            taxWater.setJumlahPajak(rs.getDouble(PstTaxWater.fieldNames[FLD_JUMLAH_PAJAK]));
            taxWater.setPeriodePajak(rs.getDate(PstTaxWater.fieldNames[FLD_PERIODE_PAJAK]));
            taxWater.setTanggalJatuhTempo(rs.getDate(PstTaxWater.fieldNames[FLD_TANGGAL_JATUH_TEMPO]));
            String statusPembayaranStr = rs.getString(PstTaxWater.fieldNames[PstTaxWater.FLD_STATUS_PEMBAYARAN]);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxWater.setStatusPembayaran(statusPembayaran);
            taxWater.setTanggalPembayaran(rs.getDate(PstTaxWater.fieldNames[FLD_TANGGAL_PEMBAYARAN]));

        } catch (Exception e) {
            System.out.println("Err : " + e.toString());
        }
    }

    public static boolean checkTaxWater(String pengguna, int type) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "";

            if (type == 1) {
                sql = "SELECT * FROM " + TBL_TAX_WATER + " WHERE "
                        + PstTaxWater.fieldNames[PstTaxWater.FLD_PENGGUNA] + " = '" + pengguna + "'";
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