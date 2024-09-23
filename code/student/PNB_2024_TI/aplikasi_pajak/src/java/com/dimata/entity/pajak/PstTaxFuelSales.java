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
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;

import java.sql.ResultSet;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;

public class PstTaxFuelSales extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_FUEL_SALES = "fuel_sales_tax_records";

    public static final int FLD_FUEL_TAX_ID = 0;
    public static final int FLD_PENJUAL = 1;
    public static final int FLD_JUMLAH_LITER = 2;
    public static final int FLD_TAX_TYPE_ID = 3;
    public static final int FLD_JUMLAH_PAJAK = 4;
    public static final int FLD_TANGGAL_PENJUALAN = 5;
    public static final int FLD_STATUS_PEMBAYARAN = 6;
    public static final int FLD_TANGGAL_JATUH_TEMPO = 7;
    public static final int FLD_TANGGAL_PEMBAYARAN = 8;

    public static final String[] fieldNames = {
        "fuel_tax_id",
        "penjual",
        "jumlah_liter",
        "tax_type_id",
        "jumlah_pajak",
        "tanggal_penjualan",
        "status_pembayaran",
        "tanggal_jatuh_tempo",
        "tanggal_pembayaran"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE
    };

    public PstTaxFuelSales() {
    }

    public PstTaxFuelSales(int i) throws DBException {
        super(new PstTaxFuelSales());
    }

    public PstTaxFuelSales(String sOid) throws DBException {
        super(new PstTaxFuelSales(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    public PstTaxFuelSales(long lOid) throws DBException {
        super(new PstTaxFuelSales(0));
        String sOid = String.valueOf(lOid);
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
        return TBL_TAX_FUEL_SALES;
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
        return new PstTaxFuelSales().getClass().getName();
    }

    @Override
    public long fetchExc(Entity ent) throws Exception {
        TaxFuelSales taxFuelSales = fetchExc(ent.getOID());
        ent = (Entity) taxFuelSales;
        return taxFuelSales.getOID();
    }

    public static TaxFuelSales fetchExc(long oid) throws DBException {
        try {
            TaxFuelSales taxFuelSales = new TaxFuelSales();
            PstTaxFuelSales pstTaxFuelSales = new PstTaxFuelSales(oid);
            taxFuelSales.setOID(oid);
            taxFuelSales.setFuelTaxId(pstTaxFuelSales.getInt(FLD_FUEL_TAX_ID));
            taxFuelSales.setPenjual(pstTaxFuelSales.getString(FLD_PENJUAL));
            taxFuelSales.setJumlahLiter(Double.valueOf(pstTaxFuelSales.getDouble(FLD_JUMLAH_LITER)));
            taxFuelSales.setTaxTypeId(pstTaxFuelSales.getInt(FLD_TAX_TYPE_ID));
            taxFuelSales.setJumlahPajak(Double.valueOf(pstTaxFuelSales.getDouble(FLD_JUMLAH_PAJAK)));
            taxFuelSales.setTanggalPenjualan(pstTaxFuelSales.getDate(FLD_TANGGAL_PENJUALAN));
            String statusPembayaranStr = pstTaxFuelSales.getString(FLD_STATUS_PEMBAYARAN);
            taxFuelSales.setTanggalJatuhTempo(pstTaxFuelSales.getDate(FLD_TANGGAL_JATUH_TEMPO));

            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxFuelSales.setStatusPembayaran(statusPembayaran);

            taxFuelSales.setTanggalPembayaran(pstTaxFuelSales.getDate(FLD_TANGGAL_PEMBAYARAN));
            return taxFuelSales;
        } catch (Exception e) {
            throw new DBException(new PstTaxFuelSales(0), DBException.UNKNOWN);
        }
    }

    public static void insertExc(TaxFuelSales taxFuelSales) throws DBException, SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBHandler.getConnection(); // Mendapatkan koneksi dari DBHandler
            String sql = "INSERT INTO fuel_sales_tax_records (penjual, jumlah_liter, tax_type_id, jumlah_pajak, tanggal_penjualan,  status_pembayaran, tanggal_jatuh_tempo, tanggal_pembayaran) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, taxFuelSales.getPenjual());
            pstmt.setDouble(2, taxFuelSales.getJumlahLiter());
            pstmt.setLong(3, taxFuelSales.getTaxType().getTaxTypeId());
            pstmt.setDouble(4, taxFuelSales.getJumlahPajak());
            pstmt.setDate(5, (Date) taxFuelSales.getTanggalPenjualan());
            pstmt.setString(6, taxFuelSales.getStatusPembayaran().getStatus());
            pstmt.setDate(7, (Date) taxFuelSales.getTanggalJatuhTempo());
            pstmt.setDate(8, (Date) taxFuelSales.getTanggalPembayaran());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Menampilkan error di console
            System.out.println("Error during insertion: " + e.getMessage()); // Menampilkan pesan error
            throw e; // Lempar exception agar bisa diketahui jenis kesalahannya
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
        return updateExc((TaxFuelSales) ent);
    }

    public long updateExc(TaxFuelSales taxFuelSales) throws DBException {
        try {
            if (taxFuelSales.getOID() != 0) {
                PstTaxFuelSales pstTaxFuelSales = new PstTaxFuelSales(taxFuelSales.getOID());
                pstTaxFuelSales.setString(FLD_PENJUAL, taxFuelSales.getPenjual());
                pstTaxFuelSales.setDouble(FLD_JUMLAH_LITER, taxFuelSales.getJumlahLiter());
                pstTaxFuelSales.setLong(FLD_TAX_TYPE_ID, taxFuelSales.getTaxType().getOID());
                pstTaxFuelSales.setDouble(FLD_JUMLAH_PAJAK, taxFuelSales.getJumlahPajak());
                pstTaxFuelSales.setDate(FLD_TANGGAL_PENJUALAN, taxFuelSales.getTanggalPenjualan());
                pstTaxFuelSales.setDate(FLD_TANGGAL_JATUH_TEMPO, taxFuelSales.getTanggalJatuhTempo());
                pstTaxFuelSales.setString(FLD_STATUS_PEMBAYARAN, taxFuelSales.getStatusPembayaran().getStatus());
                pstTaxFuelSales.setDate(FLD_TANGGAL_PEMBAYARAN, taxFuelSales.getTanggalPembayaran());
                pstTaxFuelSales.update();
                return taxFuelSales.getOID();
            }
        } catch (Exception e) {
            throw new DBException(new PstTaxFuelSales(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static Vector listAll(int limitStart, int recordToGet, String whereClause, String order) {
        return list(limitStart, recordToGet, whereClause, order, null);
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order, String join) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_FUEL_SALES;

            if (join != null && !join.isEmpty()) {
                sql += " JOIN " + join;
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql += " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql += " ORDER BY " + order;
            }

            // Handle limit and offset based on the DB server type
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
                    // Implement if needed for other DBs
                    break;

                default:
                    break;
            }

            // Execute the query
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            // Extract data from the result set
            while (rs.next()) {
                TaxFuelSales TaxFuelSales = new TaxFuelSales();
                resultToObject(rs, TaxFuelSales);
                lists.add(TaxFuelSales);
            }

            rs.close(); // Ensure the ResultSet is closed after processing
            return lists;

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            DBResultSet.close(dbrs); // Ensure the DBResultSet is closed in finally block
        }
        return new Vector();
    }

    public static void resultToObject(ResultSet rs, TaxFuelSales taxFuelSales) {
        try {
            taxFuelSales.setOID(rs.getLong(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_FUEL_TAX_ID]));
            taxFuelSales.setPenjual(rs.getString(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_PENJUAL]));
            taxFuelSales.setJumlahLiter(rs.getDouble(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_JUMLAH_LITER]));

            long taxTypeId = rs.getLong(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_TAX_TYPE_ID]);
            TaxType taxType = PstTaxType.fetchExc(taxTypeId);
            taxFuelSales.setTaxType(taxType);

            taxFuelSales.setJumlahPajak(rs.getDouble(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_JUMLAH_PAJAK]));
            taxFuelSales.setTanggalPenjualan(rs.getDate(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_TANGGAL_PENJUALAN]));
            taxFuelSales.setTanggalJatuhTempo(rs.getDate(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_TANGGAL_JATUH_TEMPO]));
            String statusPembayaranStr = rs.getString(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_STATUS_PEMBAYARAN]);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxFuelSales.setStatusPembayaran(statusPembayaran);

            taxFuelSales.setTanggalPembayaran(rs.getDate(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_TANGGAL_PEMBAYARAN]));
        } catch (Exception e) {
            System.out.println("Error in resultToObject: " + e);
        }
    }

    public static boolean checkTaxFuelSales(String no, int type) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "";

            if (type == 1) {
                sql = "SELECT * FROM " + TBL_TAX_FUEL_SALES + " WHERE "
                        + PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_PENJUAL] + " = '" + no + "'";
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
    
    public static long deleteExc(long fuelTaxId) throws DBException {
        String query = "DELETE FROM " + TBL_TAX_FUEL_SALES + " WHERE " + fieldNames[FLD_FUEL_TAX_ID] + " = ?";
        System.out.println("Executing query: " + query + " with ID: " + fuelTaxId);

        try (Connection conn = DBHandler.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setLong(1, fuelTaxId);
            int result = pst.executeUpdate();
            System.out.println("Result of deletion: " + result);

            if (result > 0) {
                return fuelTaxId; // Penghapusan berhasil, mengembalikan ID yang dihapus
            } else {
                return 0; // Tidak ada data yang dihapus
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw new DBException(e); // Menangani kesalahan SQL
        }
    }

    private long getDouble(int FLD_JUMLAH_LITER) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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