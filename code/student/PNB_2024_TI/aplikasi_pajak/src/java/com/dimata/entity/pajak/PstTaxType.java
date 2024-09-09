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

    // Default constructor
    public PstTaxType(){
    }
    
    // Constructor to instantiate with an integer (could be used for querying)
    public PstTaxType(int i) throws DBException {
        super(new PstTaxType());
    }

    // Constructor that initializes the entity with a string OID (Object ID)
    public PstTaxType(String sOid) throws DBException {
        super(new PstTaxType(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    // Constructor that initializes the entity with a long OID
    public PstTaxType(long lOid) throws DBException {
        super(new PstTaxType(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
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
            throw new DBException(new PstTaxType(taxType.getOID()), DBException.UNKNOWN);
        }
        return taxType.getOID();
    }

    // Update an AssetList entity in the database
    @Override
    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxType) ent);
    }
    
    public long updateExc(TaxType taxType) throws DBException {
        try {
            if (taxType.getOID() != 0) {
                PstTaxType pstTaxType = new PstTaxType(taxType.getOID());
                pstTaxType.setString(FLD_NAME_TAX, taxType.getNamaPajak());
                pstTaxType.setString(FLD_DESCRIPTION, taxType.getDeskripsi());
                pstTaxType.update();
                return taxType.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);
        }
        return 0;
    }

     // Deletes an AssetList entity from the database
    @Override
    public long deleteExc(Entity ent) throws Exception {
       if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }
    
    public long deleteExc(long oid) throws DBException {
        try {
            PstTaxType pstTaxType = new PstTaxType(oid);
            pstTaxType.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);
        }
        return oid;
    }

    // Fetches an AssetList entity from the database using its OID
    @Override
    public long fetchExc(Entity ent) throws Exception {
        TaxType taxType = fetchExc(ent.getOID());
        ent = (Entity) taxType;
        return taxType.getOID();
    }
    
    public static TaxType fetchExc(long oid) throws DBException {
        try {
            TaxType taxType = new TaxType();
            PstTaxType pstTaxType = new PstTaxType(oid);
            taxType.setOID(oid);
            taxType.setTaxTypeId(pstTaxType.getlong(FLD_TAX_TYPE_ID));
            taxType.setNamaPajak(pstTaxType.getString(FLD_NAME_TAX));
            taxType.setDeskripsi(pstTaxType.getString(FLD_DESCRIPTION));
            return taxType;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);
        }
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
            TaxType taxType = new TaxType();
            resultToObject(rs, taxType);
            lists.add(taxType);
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

    // Checks if an asset condition exists in the database based on name and type
    public static boolean checkAsset(String name, int type) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "";

            if (type == 1) {// check asset name
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
}