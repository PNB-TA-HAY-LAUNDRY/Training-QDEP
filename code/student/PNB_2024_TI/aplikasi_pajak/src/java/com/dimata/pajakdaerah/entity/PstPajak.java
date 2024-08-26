/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dimata.pajakdaerah.entity;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import static com.dimata.qdep.db.I_DBType.TYPE_ID;
import static com.dimata.qdep.db.I_DBType.TYPE_LONG;
import static com.dimata.qdep.db.I_DBType.TYPE_PK;
import static com.dimata.qdep.db.I_DBType.TYPE_STRING;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author ihsan
 */
public class PstPajak extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    public static final String TBL_JENIS_PAJAK = "jenispajak";

    public static final int FLD_ID = 0;
    public static final int FLD_NAMA = 1;
    public static final int FLD_DESKRIPSI = 2;

    public static final String[] fieldNames = {
        "ID",
        "NAMA",
        "DESKRIPSI"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstPajak() {
    }

    public PstPajak(long lOid) throws DBException {
        super(new PstPajak());
        if (!locate(lOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_JENIS_PAJAK;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPajak().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        JenisPajak jenisPajak = fetchExc(ent.getOID());
        ent = jenisPajak;
        return jenisPajak.getOId();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((JenisPajak) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((JenisPajak) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static JenisPajak fetchExc(long oid) throws DBException {
        try {
            JenisPajak jenisPajak = new JenisPajak();
            PstPajak pstPajak = new PstPajak(oid);
            jenisPajak.setId(pstPajak.getLong(FLD_ID));
            jenisPajak.setNama(pstPajak.getString(FLD_NAMA));
            jenisPajak.setDeskripsi(pstPajak.getString(FLD_DESKRIPSI));

            return jenisPajak;
        } catch (Exception e) {
            throw new DBException(new PstPajak(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(JenisPajak jenisPajak) throws DBException {
        try {
            PstPajak pstPajak = new PstPajak();
            pstPajak.setString(FLD_NAMA, jenisPajak.getNama());
            pstPajak.setString(FLD_DESKRIPSI, jenisPajak.getDeskripsi());
            pstPajak.insert();
            jenisPajak.setId(pstPajak.getLong(FLD_ID));
        } catch (Exception e) {
            throw new DBException(new PstPajak(), DBException.UNKNOWN);
        }
        return jenisPajak.getOId();
    }

    public static long updateExc(JenisPajak jenisPajak) throws DBException {
        try {
            if (jenisPajak.getOId() != 0) {
                PstPajak pstPajak = new PstPajak(jenisPajak.getOId());
                pstPajak.setString(FLD_NAMA, jenisPajak.getNama());
                pstPajak.setString(FLD_DESKRIPSI, jenisPajak.getDeskripsi());
                pstPajak.update();
                return jenisPajak.getOId();
            }
        } catch (Exception e) {
            throw new DBException(new PstPajak(), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPajak pstPajak = new PstPajak(oid);
            pstPajak.delete();
        } catch (Exception e) {
            throw new DBException(new PstPajak(), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector<JenisPajak> list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector<JenisPajak> lists = new Vector<>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_JENIS_PAJAK;
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
                JenisPajak jenisPajak = new JenisPajak();
                resultToObject(rs, jenisPajak);
                lists.add(jenisPajak);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    public static void resultToObject(ResultSet rs, JenisPajak jenisPajak) {
        try {
            jenisPajak.setId(rs.getLong(fieldNames[FLD_ID]));
            jenisPajak.setNama(rs.getString(fieldNames[FLD_NAMA]));
            jenisPajak.setDeskripsi(rs.getString(fieldNames[FLD_DESKRIPSI]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkOID(long jenisPajakId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_JENIS_PAJAK + " WHERE " + fieldNames[FLD_ID] + " = " + jenisPajakId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            result = rs.next();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }
}
