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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author ihsan
 */
public class PstTaxPeriod extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_PERIOD = "periodepajak";

    public static final int FLD_TAX_PERIOD_ID = 0;
    public static final int FLD_YEAR = 1;
    public static final int FLD_MONTH = 2;
    public static final int FLD_TAX_TYPE_ID = 3;
    public static final int FLD_REGIONAL_TAX_ID = 4;

    public static final String[] fieldNames = {
        "id_periode_pajak",
        "tahun",
        "bulan",
        "id_jenis_pajak",
        "id_pajak_daerah"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstTaxPeriod() {
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getTableName() {
        return TBL_TAX_PERIOD;
    }

    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstTaxPeriod().getClass().getName();
    }

    public long insertExc(TaxPeriod taxPeriod) throws DBException {
        try {
            PstTaxPeriod pstTaxPeriod = new PstTaxPeriod();
            pstTaxPeriod.setInt(FLD_YEAR, taxPeriod.getYear());
            pstTaxPeriod.setInt(FLD_MONTH, taxPeriod.getMonth());
            pstTaxPeriod.setLong(FLD_TAX_TYPE_ID, taxPeriod.getTaxTypeId());
            pstTaxPeriod.setLong(FLD_REGIONAL_TAX_ID, taxPeriod.getRegionalTaxId());
            pstTaxPeriod.insert();
            taxPeriod.setOID(pstTaxPeriod.getlong(FLD_TAX_PERIOD_ID));
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return taxPeriod.getOID();
    }

    public long updateExc(TaxPeriod taxPeriod) throws DBException {
        try {
            if (taxPeriod.getOID() != 0) {
                PstTaxPeriod pstTaxPeriod = new PstTaxPeriod();
                pstTaxPeriod.setInt(FLD_YEAR, taxPeriod.getYear());
                pstTaxPeriod.setInt(FLD_MONTH, taxPeriod.getMonth());
                pstTaxPeriod.setLong(FLD_TAX_TYPE_ID, taxPeriod.getTaxTypeId());
                pstTaxPeriod.setLong(FLD_REGIONAL_TAX_ID, taxPeriod.getRegionalTaxId());
                pstTaxPeriod.update();
                return taxPeriod.getOID();
            }
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(long oid) throws DBException {
        try {
            PstTaxPeriod pstTaxPeriod = new PstTaxPeriod();
            pstTaxPeriod.delete();
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return oid;
    }

    public static TaxPeriod fetchExc(long oid) throws DBException {
        try {
            TaxPeriod taxPeriod = new TaxPeriod();
            PstTaxPeriod pstTaxPeriod = new PstTaxPeriod();
            taxPeriod.setOID(oid);
            taxPeriod.setYear(pstTaxPeriod.getInt(FLD_YEAR));
            taxPeriod.setMonth(pstTaxPeriod.getInt(FLD_MONTH));
            taxPeriod.setTaxTypeId(pstTaxPeriod.getLong(FLD_TAX_TYPE_ID));
            taxPeriod.setRegionalTaxId(pstTaxPeriod.getLong(FLD_REGIONAL_TAX_ID));
            return taxPeriod;
        } catch (Exception e) {
            throw new DBException(new PstTaxPeriod(), DBException.UNKNOWN);
        }
    }

    public static Vector<TaxPeriod> list(int limitStart, int recordToGet, String whereClause, String order) {
    Vector<TaxPeriod> lists = new Vector<>();
    DBResultSet dbrs = null;
    try {
        String sql = "SELECT * FROM " + TBL_TAX_PERIOD;
        if (whereClause != null && !whereClause.isEmpty()) {
            sql += " WHERE " + whereClause;
        }
        if (order != null && !order.isEmpty()) {
            sql += " ORDER BY " + order;
        }
        sql += " LIMIT " + limitStart + "," + recordToGet;
        System.out.println("SQL Query: " + sql); // Debug: Print the SQL query
        dbrs = DBHandler.execQueryResult(sql);
        ResultSet rs = dbrs.getResultSet();
        while (rs.next()) {
            TaxPeriod taxPeriod = new TaxPeriod();
            resultToObject(rs, taxPeriod);
            lists.add(taxPeriod);
        }
    } catch (Exception e) {
        e.printStackTrace(); // Debug: Print stack trace for exception
    } finally {
        DBResultSet.close(dbrs);
    }
    return lists;
}


    public static void resultToObject(ResultSet rs, TaxPeriod taxPeriod) {
    try {
        taxPeriod.setOID(rs.getLong(fieldNames[FLD_TAX_PERIOD_ID]));
        taxPeriod.setYear(rs.getInt(fieldNames[FLD_YEAR]));
        taxPeriod.setMonth(rs.getInt(fieldNames[FLD_MONTH]));
        taxPeriod.setTaxTypeId(rs.getLong(fieldNames[FLD_TAX_TYPE_ID]));
        taxPeriod.setRegionalTaxId(rs.getLong(fieldNames[FLD_REGIONAL_TAX_ID]));
    } catch (Exception e) {
        e.printStackTrace(); // Print exception stack trace
    }
}
    
    public static int deleteById(long id) throws DBException, SQLException {
        Connection connection = null;
        Statement statement = null;
        int result = -1;
        try {
            connection = DBHandler.getConnection();
            statement = connection.createStatement();
            String sql = "DELETE FROM " + TBL_TAX_PERIOD + " WHERE " + fieldNames[FLD_TAX_PERIOD_ID] + " = " + id;
            result = statement.executeUpdate(sql);
            if (result == 0) {
                throw new DBException(new PstTaxPeriod(), DBException.RECORD_NOT_FOUND);
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace(System.err);
            throw new DBException(new PstTaxPeriod(), sqlexception);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }


    public static boolean checkOID(long taxPeriodId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_PERIOD + " WHERE " + fieldNames[FLD_TAX_PERIOD_ID] + " = " + taxPeriodId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            result = rs.next();
        } catch (Exception e) {
            System.out.println("err : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    public long fetchExc(Entity ent) throws Exception {
        TaxPeriod taxperiod = fetchExc(ent.getOID());
        ent = (Entity) taxperiod;
        return taxperiod.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxPeriod) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxPeriod) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
}