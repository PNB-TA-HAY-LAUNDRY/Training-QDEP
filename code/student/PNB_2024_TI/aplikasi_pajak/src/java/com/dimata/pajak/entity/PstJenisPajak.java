package com.dimata.pajak.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.lang.I_Language;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PstJenisPajak extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc {

    public static final String TBL_JENIS_PAJAK = "jenispajak";

    public static final int FLD_ID = 0;
    public static final int FLD_NAMA = 1;
    public static final int FLD_DESKRIPSI = 2;
    public static final int FLD_DAERAH_ID = 3;  // Kolom daerah_id

    public static final String[] fieldNames = {
        "ID",
        "NAMA",
        "DESKRIPSI",
        "DAERAH_ID"  // kolom daerah_id
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG  // Tipe kolom daerah_id
    };

    public PstJenisPajak() {
    }

    public PstJenisPajak(long lOid) throws DBException {
        super(new PstJenisPajak());
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
        return TBL_JENIS_PAJAK;
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
        return new PstJenisPajak().getClass().getName();
    }

    @Override
    public long fetchExc(Entity ent) throws Exception {
        JenisPajak jenisPajak = fetchExc(ent.getOID());
        ent = jenisPajak;
        return jenisPajak.getOId();
    }

    @Override
    public long insertExc(Entity ent) throws Exception {
        return insertExc((JenisPajak) ent);
    }

    @Override
    public long updateExc(Entity ent) throws Exception {
        return updateExc((JenisPajak) ent);
    }

    @Override
    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static JenisPajak fetchExc(long oid) throws DBException {
        try {
            JenisPajak jenisPajak = new JenisPajak();
            PstJenisPajak pstJenisPajak = new PstJenisPajak(oid);
            jenisPajak.setId(pstJenisPajak.getLong(FLD_ID));
            jenisPajak.setNama(pstJenisPajak.getString(FLD_NAMA));
            jenisPajak.setDeskripsi(pstJenisPajak.getString(FLD_DESKRIPSI));
            jenisPajak.setDaerahId(pstJenisPajak.getLong(FLD_DAERAH_ID));
            return jenisPajak;
        } catch (Exception e) {
            throw new DBException(new PstJenisPajak(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(JenisPajak jenisPajak) throws DBException {
        String sql = "INSERT INTO " + TBL_JENIS_PAJAK + " (NAMA, DESKRIPSI, DAERAH_ID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = DBHandler.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, jenisPajak.getNama());
            pstmt.setString(2, jenisPajak.getDeskripsi());
            pstmt.setLong(3, jenisPajak.getDaerahId());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    jenisPajak.setId(generatedKeys.getLong(1));
                } else {
                    throw new DBException(new PstJenisPajak(), DBException.UNKNOWN);
                }
            }
        } catch (SQLException e) {
            throw new DBException(new PstJenisPajak(), DBException.UNKNOWN);
        }
        return jenisPajak.getOId();
    }

    public static long updateExc(JenisPajak jenisPajak) throws DBException {
        try {
            if (jenisPajak.getOId() == 0) {
                throw new DBException(new PstJenisPajak(), DBException.RECORD_NOT_FOUND);
            }
            PstJenisPajak pstJenisPajak = new PstJenisPajak(jenisPajak.getOId());
            pstJenisPajak.setString(FLD_NAMA, jenisPajak.getNama());
            pstJenisPajak.setString(FLD_DESKRIPSI, jenisPajak.getDeskripsi());
            pstJenisPajak.setLong(FLD_DAERAH_ID, jenisPajak.getDaerahId());
            pstJenisPajak.update();
        } catch (Exception e) {
            throw new DBException(new PstJenisPajak(), DBException.UNKNOWN);
        }
        return jenisPajak.getOId();
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstJenisPajak pstJenisPajak = new PstJenisPajak(oid);
            pstJenisPajak.delete();
        } catch (Exception e) {
            throw new DBException(new PstJenisPajak(), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector<JenisPajak> listAll() throws DBException {
        Vector<JenisPajak> list = new Vector<>();
        try {
            DBResultSet dbrs = null;
            try {
                String sql = "SELECT * FROM " + TBL_JENIS_PAJAK;
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) {
                    JenisPajak jenisPajak = new JenisPajak();
                    jenisPajak.setId(rs.getLong(fieldNames[FLD_ID]));
                    jenisPajak.setNama(rs.getString(fieldNames[FLD_NAMA]));
                    jenisPajak.setDeskripsi(rs.getString(fieldNames[FLD_DESKRIPSI]));
                    jenisPajak.setDaerahId(rs.getLong(fieldNames[FLD_DAERAH_ID]));
                    list.add(jenisPajak);
                }
            } finally {
                if (dbrs != null) {
                    dbrs.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
