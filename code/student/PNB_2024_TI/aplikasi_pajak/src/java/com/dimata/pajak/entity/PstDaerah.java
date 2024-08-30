package com.dimata.pajak.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PstDaerah extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc {

    public static final String TBL_DAERAH = "daerah";

    public static final int FLD_ID = 0;
    public static final int FLD_NAMA = 1;
    public static final int FLD_KODE_DAERAH = 2;
    public static final int FLD_DESKRIPSI = 3;

    public static final String[] fieldNames = {
        "ID",
        "NAMA",
        "KODE_DAERAH",
        "DESKRIPSI"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstDaerah() {
        super();
    }

    public PstDaerah(long lOid) throws DBException {
        super(new PstDaerah());
        if (!locate(lOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getTableName() {
        return TBL_DAERAH;
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
        return new PstDaerah().getClass().getName();
    }

    @Override
    public long fetchExc(Entity ent) throws Exception {
        Daerah daerah = fetchExc(ent.getOID());
        ent = daerah;
        return daerah.getOId();
    }

    @Override
    public long insertExc(Entity ent) throws Exception {
        return insertExc((Daerah) ent);
    }

    @Override
    public long updateExc(Entity ent) throws Exception {
        return updateExc((Daerah) ent);
    }

    @Override
    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Daerah fetchExc(long oid) throws DBException {
        try {
            Daerah daerah = new Daerah();
            PstDaerah pstDaerah = new PstDaerah(oid);
            daerah.setId(pstDaerah.getLong(FLD_ID));
            daerah.setNama(pstDaerah.getString(FLD_NAMA));
            daerah.setKodeDaerah(pstDaerah.getString(FLD_KODE_DAERAH));
            daerah.setDeskripsi(pstDaerah.getString(FLD_DESKRIPSI));
            return daerah;
        } catch (Exception e) {
            throw new DBException(new PstDaerah(), DBException.UNKNOWN, e);
        }
    }

    public static long insertExc(Daerah daerah) throws DBException {
        try {
            PstDaerah pstDaerah = new PstDaerah();
            pstDaerah.setString(FLD_NAMA, daerah.getNama());
            pstDaerah.setString(FLD_KODE_DAERAH, daerah.getKodeDaerah());
            pstDaerah.setString(FLD_DESKRIPSI, daerah.getDeskripsi());
            pstDaerah.insert();
            daerah.setId(pstDaerah.getLong(FLD_ID));
            return daerah.getOId();
        } catch (Exception e) {
            throw new DBException(new PstDaerah(), DBException.UNKNOWN, e);
        }
    }

    public static long updateExc(Daerah daerah) throws DBException {
        try {
            if (daerah.getOId() != 0) {
                PstDaerah pstDaerah = new PstDaerah(daerah.getOId());
                pstDaerah.setString(FLD_NAMA, daerah.getNama());
                pstDaerah.setString(FLD_KODE_DAERAH, daerah.getKodeDaerah());
                pstDaerah.setString(FLD_DESKRIPSI, daerah.getDeskripsi());
                pstDaerah.update();
                return daerah.getOId();
            }
            return 0;
        } catch (Exception e) {
            throw new DBException(new PstDaerah(), DBException.UNKNOWN, e);
        }
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstDaerah pstDaerah = new PstDaerah(oid);
            pstDaerah.delete();
            return oid;
        } catch (Exception e) {
            throw new DBException(new PstDaerah(), DBException.UNKNOWN, e);
        }
    }

    public static Vector<Daerah> list(int limitStart, int recordToGet, String whereClause, String order) throws DBException {
        Vector<Daerah> lists = new Vector<>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_DAERAH;
            if (whereClause != null && whereClause.length() > 0) {
                sql += " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql += " ORDER BY " + order;
            }

            if (limitStart >= 0 && recordToGet > 0) {
                sql += " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Daerah daerah = new Daerah();
                resultToObject(rs, daerah);
                lists.add(daerah);
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    public static void resultToObject(ResultSet rs, Daerah daerah) {
        try {
            daerah.setId(rs.getLong(fieldNames[FLD_ID]));
            daerah.setNama(rs.getString(fieldNames[FLD_NAMA]));
            daerah.setKodeDaerah(rs.getString(fieldNames[FLD_KODE_DAERAH]));
            daerah.setDeskripsi(rs.getString(fieldNames[FLD_DESKRIPSI]));
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    

    public static boolean checkOID(long daerahId) throws DBException {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_DAERAH + " WHERE " + fieldNames[FLD_ID] + " = " + daerahId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }
}
