 package com.dimata.entity.pajak;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.*;
import java.util.*;

public class PstTaxType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_TYPE = "tax_types";

    public static final int FLD_TAX_TYPE_ID = 0;
    public static final int FLD_NAME_TAX = 1;
    public static final int FLD_DESCRIPTION = 2;
    public static final int FLD_TARIF = 3;

    public static final String[] fieldNames = {
        "tax_type_id",
        "nama_pajak",
        "deskripsi",
        "tarif"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_FLOAT
    };

    public PstTaxType() {
    }

    public PstTaxType(int i) throws DBException {
        super(new PstTaxType());
    }

    public PstTaxType(String sTaxTypeId) throws DBException {
        super(new PstTaxType(0));
        if (!locate(sTaxTypeId)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    public PstTaxType(long lTaxTypeId) throws DBException {
        super(new PstTaxType(0));
        String sTaxTypeId = String.valueOf(lTaxTypeId);
        if (!locate(sTaxTypeId)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getTableName() {
        return TBL_TAX_TYPE;
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
        return new PstTaxType().getClass().getName();
    }

    @Override
    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxType) ent);
    }

    public long insertExc(TaxType taxType) throws DBException {
        try {
            PstTaxType pstTaxType = new PstTaxType(0);
            pstTaxType.setString(FLD_NAME_TAX, taxType.getNamaPajak());
            pstTaxType.setString(FLD_DESCRIPTION, taxType.getDeskripsi());
            pstTaxType.setDouble(FLD_TARIF, taxType.getTarif().doubleValue());
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(taxType.getTaxTypeId()), DBException.UNKNOWN);
        }
        return taxType.getTaxTypeId();
    }

    @Override
    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxType) ent);
    }

    public long updateExc(TaxType taxType) throws DBException {
        try {
            if (taxType.getTaxTypeId() != 0) {
                PstTaxType pstTaxType = new PstTaxType(taxType.getTaxTypeId());
                pstTaxType.setString(FLD_NAME_TAX, taxType.getNamaPajak());
                pstTaxType.setString(FLD_DESCRIPTION, taxType.getDeskripsi());
                pstTaxType.update();
                return taxType.getTaxTypeId();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(TaxType ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getTaxTypeId());
    }

    public long deleteExc(long taxTypeId) throws DBException {
        try {
            PstTaxType pstTaxType = new PstTaxType(taxTypeId);
            pstTaxType.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);
        }
        return taxTypeId;
    }

    public long fetchExc(TaxType ent) throws Exception {
        TaxType taxType = fetchExc(ent.getTaxTypeId());
        ent = (TaxType) taxType;
        return taxType.getTaxTypeId();
    }

    public static TaxType fetchExc(long taxTypeId) throws DBException {
        try {
            TaxType taxType = new TaxType();
            PstTaxType pstTaxType = new PstTaxType(taxTypeId);
            taxType.setTaxTypeId(taxTypeId);
            taxType.setNamaPajak(pstTaxType.getString(FLD_NAME_TAX));
            taxType.setDeskripsi(pstTaxType.getString(FLD_DESCRIPTION));
            return taxType;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);
        }
    }

    public static void resultToObject(ResultSet rs, TaxType taxType) {
        try {
            taxType.setTaxTypeId(rs.getLong(PstTaxType.fieldNames[PstTaxType.FLD_TAX_TYPE_ID]));
            taxType.setNamaPajak(rs.getString(PstTaxType.fieldNames[PstTaxType.FLD_NAME_TAX]));
            taxType.setDeskripsi(rs.getString(PstTaxType.fieldNames[PstTaxType.FLD_DESCRIPTION]));
            taxType.setTarif(rs.getBigDecimal(PstTaxType.fieldNames[PstTaxType.FLD_TARIF]));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static boolean checkTaxType(String name, int type) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "";

            if (type == 1) {
                sql = "SELECT * FROM " + TBL_TAX_TYPE + " WHERE "
                        + PstTaxType.fieldNames[PstTaxType.FLD_NAME_TAX] + " = '" + name + "'";
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

    public static Vector listAll(int limitStart, int recordToGet, String whereClause, String order) {
        return list(limitStart, recordToGet, whereClause, order, null);
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order, String join) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_TYPE;

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
                TaxType taxType = new TaxType();
                resultToObject(rs, taxType);
                lists.add(taxType);
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

    public static TaxType fetchByName(String namaPajak) throws DBException {
        TaxType taxType = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Mendapatkan koneksi dari DBHandler atau DriverManager
            conn = DBHandler.getConnection(); // Sesuaikan dengan cara Anda mendapatkan koneksi

            // Membuat query SQL untuk mencari berdasarkan nama_pajak
            String sql = "SELECT * FROM " + TBL_TAX_TYPE + " WHERE " + fieldNames[FLD_NAME_TAX] + " = ?";

            // Menyiapkan PreparedStatement
            pstmt = conn.prepareStatement(sql);

            // Set parameter untuk nama pajak
            pstmt.setString(1, namaPajak);

            // Eksekusi query
            rs = pstmt.executeQuery();

            // Jika ditemukan, isi objek TaxType dengan data dari result set
            if (rs.next()) {
                taxType = new TaxType();
                resultToObject(rs, taxType);
            }

        } catch (SQLException e) {
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);
        } finally {
            // Pastikan resource ditutup
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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

        return taxType; // Mengembalikan null jika tidak ditemukan
    }

    @Override
    public long fetchExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long deleteExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
