/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.entity.pajak;

/**
 *
 * @author ihsan
 */
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

    public static final String TBL_TAX_TYPE = "jenispajak";

    public static final int FLD_TAX_TYPE_ID = 0;
    public static final int FLD_NAME = 1;
    public static final int FLD_DESCRIPTION = 2;

    public static final String[] fieldNames = {
        "id_jenis_pajak",
        "nama_pajak",
        "deskripsi"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstTaxType() {
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
    
    public String getPersistentName() {
        return new PstTaxType().getClass().getName();
    }

    public long insertExc(TaxType taxType) throws DBException {
        try {
            PstTaxType pstTaxType = new PstTaxType();
            pstTaxType.setString(FLD_NAME, taxType.getName());
            pstTaxType.setString(FLD_DESCRIPTION, taxType.getDescription());
            pstTaxType.insert();
            taxType.setOID(pstTaxType.getlong(FLD_TAX_TYPE_ID));
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return taxType.getOID();
    }

    public long updateExc(TaxType taxType) throws DBException {
        try {
            if (taxType.getOID() != 0) {
                PstTaxType pstTaxType = new PstTaxType();
                pstTaxType.setString(FLD_NAME, taxType.getName());
                pstTaxType.setString(FLD_DESCRIPTION, taxType.getDescription());
                pstTaxType.update();
                return taxType.getOID();
            }
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return 0;
    }


    public long deleteExc(long oid) throws DBException {
        try {
            PstTaxType pstTaxType = new PstTaxType();
            pstTaxType.delete();
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return oid;
    }

    public static TaxType fetchExc(long oid) throws DBException {
        try {
            TaxType taxType = new TaxType();
            PstTaxType pstTaxType = new PstTaxType();
            taxType.setOID(oid);
            taxType.setName(pstTaxType.getString(FLD_NAME));
            taxType.setDescription(pstTaxType.getString(FLD_DESCRIPTION));
            return taxType;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(), DBException.UNKNOWN);
        }
    }

    public static Vector<TaxType> list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector<TaxType> lists = new Vector<>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_TYPE;
            if (whereClause != null && !whereClause.isEmpty()) {
                sql += " WHERE " + whereClause;
            }
            if (order != null && !order.isEmpty()) {
                sql += " ORDER BY " + order;
            }
            sql += " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                TaxType taxType = new TaxType();
                resultToObject(rs, taxType);
                lists.add(taxType);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    public static void resultToObject(ResultSet rs, TaxType taxType) {
        try {
            taxType.setOID(rs.getLong(fieldNames[FLD_TAX_TYPE_ID]));
            taxType.setName(rs.getString(fieldNames[FLD_NAME]));
            taxType.setDescription(rs.getString(fieldNames[FLD_DESCRIPTION]));
        } catch (Exception e) {
            System.out.println("error : " + e);
        }
    }

    public static boolean checkOID(long taxTypeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_TYPE + " WHERE " + fieldNames[FLD_TAX_TYPE_ID] + " = " + taxTypeId;
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
        TaxType taxtype = fetchExc(ent.getOID());
        ent = (Entity) taxtype;
        return taxtype.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxType) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxType) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
}
