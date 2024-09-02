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
public class PstRegionalTax extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_REGIONAL_TAX = "pajakdaerah";

    public static final int FLD_REGIONAL_TAX_ID = 0;
    public static final int FLD_NAME = 1;
    public static final int FLD_CODE = 2;
    public static final int FLD_DESCRIPTION = 3;

    public static final String[] fieldNames = {
        "id_pajak_daerah",
        "nama_daerah",
        "kode_daerah",
        "deskripsi"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstRegionalTax() {
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getTableName() {
        return TBL_REGIONAL_TAX;
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
        return new PstRegionalTax().getClass().getName();
    }

    public long insertExc(RegionalTax regionalTax) throws DBException {
        try {
            PstRegionalTax pstRegionalTax = new PstRegionalTax();
            pstRegionalTax.setString(FLD_NAME, regionalTax.getName());
            pstRegionalTax.setString(FLD_CODE, regionalTax.getCode());
            pstRegionalTax.setString(FLD_DESCRIPTION, regionalTax.getDescription());
            pstRegionalTax.insert();
            regionalTax.setOID(pstRegionalTax.getlong(FLD_REGIONAL_TAX_ID));
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return regionalTax.getOID();
    }

    public long updateExc(RegionalTax regionalTax) throws DBException {
        try {
            if (regionalTax.getOID() != 0) {
                PstRegionalTax pstRegionalTax = new PstRegionalTax();
                pstRegionalTax.setString(FLD_NAME, regionalTax.getName());
                pstRegionalTax.setString(FLD_CODE, regionalTax.getCode());
                pstRegionalTax.setString(FLD_DESCRIPTION, regionalTax.getDescription());
                pstRegionalTax.update();
                return regionalTax.getOID();
            }
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(long oid) throws DBException {
        try {
            PstRegionalTax pstRegionalTax = new PstRegionalTax();
            pstRegionalTax.delete();
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return oid;
    }

    public static RegionalTax fetchExc(long oid) throws DBException {
        try {
            RegionalTax regionalTax = new RegionalTax();
            PstRegionalTax pstRegionalTax = new PstRegionalTax();
            regionalTax.setOID(oid);
            regionalTax.setName(pstRegionalTax.getString(FLD_NAME));
            regionalTax.setCode(pstRegionalTax.getString(FLD_CODE));
            regionalTax.setDescription(pstRegionalTax.getString(FLD_DESCRIPTION));
            return regionalTax;
        } catch (Exception e) {
            throw new DBException(new PstRegionalTax(), DBException.UNKNOWN);
        }
    }

    public static Vector<RegionalTax> list(int limitStart, int recordToGet, String whereClause, String order) {
    Vector<RegionalTax> lists = new Vector<>();
    DBResultSet dbrs = null;
    try {
        String sql = "SELECT * FROM " + TBL_REGIONAL_TAX;
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
            RegionalTax regionalTax = new RegionalTax();
            resultToObject(rs, regionalTax);
            lists.add(regionalTax);
        }
    } catch (Exception e) {
        System.out.println(e);
    } finally {
        DBResultSet.close(dbrs);
    }
    return lists;
}


    public static void resultToObject(ResultSet rs, RegionalTax regionalTax) {
    try {
        regionalTax.setId(rs.getLong(fieldNames[FLD_REGIONAL_TAX_ID]));
        regionalTax.setName(rs.getString(fieldNames[FLD_NAME]));
        regionalTax.setCode(rs.getString(fieldNames[FLD_CODE]));
        regionalTax.setDescription(rs.getString(fieldNames[FLD_DESCRIPTION]));
    } catch (Exception e) {
        System.out.println("error : " + e);
    }
}
    
    public static int deleteById(long id) throws DBException, SQLException {
        Connection connection = null;
        Statement statement = null;
        int result = -1;
        try {
            connection = DBHandler.getConnection();
            statement = connection.createStatement();
            String sql = "DELETE FROM " + TBL_REGIONAL_TAX + " WHERE " + fieldNames[FLD_REGIONAL_TAX_ID] + " = " + id;
            result = statement.executeUpdate(sql);
            if (result == 0) {
                throw new DBException(new PstRegionalTax(), DBException.RECORD_NOT_FOUND);
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace(System.err);
            throw new DBException(new PstRegionalTax(), sqlexception);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }


    public static boolean checkOID(long regionalTaxId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_REGIONAL_TAX + " WHERE " + fieldNames[FLD_REGIONAL_TAX_ID] + " = " + regionalTaxId;
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
        RegionalTax regionaltax = fetchExc(ent.getOID());
        ent = (Entity) regionaltax;
        return regionaltax.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((RegionalTax) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((RegionalTax) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
}
