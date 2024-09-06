package com.dimata.entity.pajak;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Kelas ini menangani operasional basis data untuk tabel Tagihan Pajak
 */
public class PstTaxBill extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_BILL = "TagihanPajak";

    public static final int FLD_TAX_BILL_ID = 0;
    public static final int FLD_AMOUNT = 1;
    public static final int FLD_TAX_TYPE_ID = 2;
    public static final int FLD_REGIONAL_TAX_ID = 3;
    public static final int FLD_TAX_PERIOD_ID = 4;

    public static final String[] fieldNames = {
        "id_tagihan_pajak",
        "jumlah_tagihan",
        "id_jenis_pajak",
        "id_pajak_daerah",
        "id_periode_pajak"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getTableName() {
        return TBL_TAX_BILL;
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
        return new PstTaxBill().getClass().getName();
    }

    public long insertExc(TaxBill taxBill) throws DBException {
        try {
            PstTaxBill pstTaxBill = new PstTaxBill();
            pstTaxBill.setDecimal(FLD_AMOUNT, taxBill.getAmount());
            pstTaxBill.setLong(FLD_TAX_TYPE_ID, taxBill.getTaxTypeId());
            pstTaxBill.setLong(FLD_REGIONAL_TAX_ID, taxBill.getRegionalTaxId());
            pstTaxBill.setLong(FLD_TAX_PERIOD_ID, taxBill.getTaxPeriodId());
            pstTaxBill.insert();
            taxBill.setOID(pstTaxBill.getLong(FLD_TAX_BILL_ID));
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return taxBill.getOID();
    }

    public long updateExc(TaxBill taxBill) throws DBException {
        try {
            if (taxBill.getOID() != 0) {
                PstTaxBill pstTaxBill = new PstTaxBill();
                pstTaxBill.setDecimal(FLD_AMOUNT, taxBill.getAmount());
                pstTaxBill.setLong(FLD_TAX_TYPE_ID, taxBill.getTaxTypeId());
                pstTaxBill.setLong(FLD_REGIONAL_TAX_ID, taxBill.getRegionalTaxId());
                pstTaxBill.setLong(FLD_TAX_PERIOD_ID, taxBill.getTaxPeriodId());
                pstTaxBill.update();
                return taxBill.getOID();
            }
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(long oid) throws DBException {
        try {
            // Hapus data terkait di tabel PembayaranPajak sebelum menghapus dari TagihanPajak
            deletePaymentsByTaxBillId(oid);
            
            PstTaxBill pstTaxBill = new PstTaxBill();
            pstTaxBill.setLong(FLD_TAX_BILL_ID, oid);
            pstTaxBill.delete();
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return oid;
    }
    
    private void deletePaymentsByTaxBillId(long id) throws DBException, SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBHandler.getConnection();
            statement = connection.createStatement();
            String sql = "DELETE FROM PembayaranPajak WHERE id_tagihan_pajak = " + id;
            statement.executeUpdate(sql);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    public static TaxBill fetchExc(long oid) throws DBException {
        try {
            TaxBill taxBill = new TaxBill();
            PstTaxBill pstTaxBill = new PstTaxBill();
            taxBill.setOID(oid);
            taxBill.setAmount(pstTaxBill.getDecimal(FLD_AMOUNT));
            taxBill.setTaxTypeId(pstTaxBill.getLong(FLD_TAX_TYPE_ID));
            taxBill.setRegionalTaxId(pstTaxBill.getLong(FLD_REGIONAL_TAX_ID));
            taxBill.setTaxPeriodId(pstTaxBill.getLong(FLD_TAX_PERIOD_ID));
            return taxBill;
        } catch (Exception e) {
            throw new DBException(new PstTaxBill(), DBException.UNKNOWN);
        }
    }

    public static Vector<TaxBill> list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector<TaxBill> lists = new Vector<>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_BILL;
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
                TaxBill taxBill = new TaxBill();
                resultToObject(rs, taxBill);
                lists.add(taxBill);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    public static void resultToObject(ResultSet rs, TaxBill taxBill) {
        try {
            taxBill.setOID(rs.getLong(fieldNames[FLD_TAX_BILL_ID]));
            taxBill.setAmount(rs.getBigDecimal(fieldNames[FLD_AMOUNT]));
            taxBill.setTaxTypeId(rs.getLong(fieldNames[FLD_TAX_TYPE_ID]));
            taxBill.setRegionalTaxId(rs.getLong(fieldNames[FLD_REGIONAL_TAX_ID]));
            taxBill.setTaxPeriodId(rs.getLong(fieldNames[FLD_TAX_PERIOD_ID]));
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
            String sql = "DELETE FROM " + TBL_TAX_BILL + " WHERE " + fieldNames[FLD_TAX_BILL_ID] + " = " + id;
            result = statement.executeUpdate(sql);
            if (result == 0) {
                throw new DBException(new PstTaxBill(), DBException.RECORD_NOT_FOUND);
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace(System.err);
            throw new DBException(new PstTaxBill(), sqlexception);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }


    public static boolean checkOID(long taxBillId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_BILL + " WHERE " + fieldNames[FLD_TAX_BILL_ID] + " = " + taxBillId;
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
        TaxBill taxbill = fetchExc(ent.getOID());
        ent = (Entity) taxbill;
        return taxbill.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxBill) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxBill) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    private void setDecimal(int FLD_AMOUNT, BigDecimal amount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private BigDecimal getDecimal(int FLD_AMOUNT) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
