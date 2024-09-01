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
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Vector;

/**
 * Kelas ini menangani operasional basis data untuk tabel Pembayaran Pajak
 */
public class PstTaxPayment extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_PAYMENT = "PembayaranPajak";

    public static final int FLD_TAX_PAYMENT_ID = 0;
    public static final int FLD_TOTAL_PAYMENT = 1;
    public static final int FLD_PAYMENT_DATE = 2;
    public static final int FLD_TAX_BILL_ID = 3;

    public static final String[] fieldNames = {
        "id_pembayaran_pajak",
        "total_pembayaran",
        "tanggal_pembayaran",
        "id_tagihan_pajak"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_LONG
    };
  

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getTableName() {
        return TBL_TAX_PAYMENT;
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
        return new PstTaxPayment().getClass().getName();
    }

    public long insertExc(TaxPayment taxPayment) throws DBException {
        try {
            PstTaxPayment pstTaxPayment = new PstTaxPayment();
            pstTaxPayment.setDecimal(FLD_TOTAL_PAYMENT, taxPayment.getTotalPayment());
            pstTaxPayment.setDate(FLD_PAYMENT_DATE, taxPayment.getPaymentDate());
            pstTaxPayment.setLong(FLD_TAX_BILL_ID, taxPayment.getTaxBillId());
            pstTaxPayment.insert();
            taxPayment.setOID(pstTaxPayment.getLong(FLD_TAX_PAYMENT_ID));
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return taxPayment.getOID();
    }

    public long updateExc(TaxPayment taxPayment) throws DBException {
        try {
            if (taxPayment.getOID() != 0) {
                PstTaxPayment pstTaxPayment = new PstTaxPayment();
                pstTaxPayment.setDecimal(FLD_TOTAL_PAYMENT, taxPayment.getTotalPayment());
                pstTaxPayment.setDate(FLD_PAYMENT_DATE, taxPayment.getPaymentDate());
                pstTaxPayment.setLong(FLD_TAX_BILL_ID, taxPayment.getTaxBillId());
                pstTaxPayment.update();
                return taxPayment.getOID();
            }
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(long oid) throws DBException {
        try {
            PstTaxPayment pstTaxPayment = new PstTaxPayment();
            pstTaxPayment.delete();
        } catch (Exception e) {
            throw new DBException(this, DBException.UNKNOWN);
        }
        return oid;
    }

    public static TaxPayment fetchExc(long oid) throws DBException {
        try {
            TaxPayment taxPayment = new TaxPayment();
            PstTaxPayment pstTaxPayment = new PstTaxPayment();
            taxPayment.setOID(oid);
            taxPayment.setTotalPayment(pstTaxPayment.getDecimal(FLD_TOTAL_PAYMENT));
            taxPayment.setPaymentDate((Date) pstTaxPayment.getDate(FLD_PAYMENT_DATE));
            taxPayment.setTaxBillId(pstTaxPayment.getLong(FLD_TAX_BILL_ID));
            return taxPayment;
        } catch (Exception e) {
            throw new DBException(new PstTaxPayment(), DBException.UNKNOWN);
        }
    }

    public static Vector<TaxPayment> list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector<TaxPayment> lists = new Vector<>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_PAYMENT;
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
                TaxPayment taxPayment = new TaxPayment();
                resultToObject(rs, taxPayment);
                lists.add(taxPayment);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    public static void resultToObject(ResultSet rs, TaxPayment taxPayment) {
        try {
            taxPayment.setOID(rs.getLong(fieldNames[FLD_TAX_PAYMENT_ID]));
            taxPayment.setTotalPayment(rs.getBigDecimal(fieldNames[FLD_TOTAL_PAYMENT]));
            taxPayment.setPaymentDate(rs.getDate(fieldNames[FLD_PAYMENT_DATE]));
            taxPayment.setTaxBillId(rs.getLong(fieldNames[FLD_TAX_BILL_ID]));
        } catch (Exception e) {
            System.out.println("error : " + e);
        }
    }

    public static boolean checkOID(long taxPaymentId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_PAYMENT + " WHERE " + fieldNames[FLD_TAX_PAYMENT_ID] + " = " + taxPaymentId;
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
        TaxPayment taxPayment = fetchExc(ent.getOID());
        ent = (Entity) taxPayment;
        return taxPayment.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxPayment) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxPayment) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    private void setDecimal(int FLD_TOTAL_PAYMENT, BigDecimal totalPayment) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private BigDecimal getDecimal(int FLD_TOTAL_PAYMENT) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
