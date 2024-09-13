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
import static com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type.TYPE_ENUM;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author ASUS
 */
public class PstTaxCigarette extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_CIGARETTE = "cigarette_tax_records";

    public static final int FLD_CIGARETTE_TAX_ID = 0;
    public static final int FLD_PENJUAL = 1;
    public static final int FLD_JUMLAH_BATANG = 2;
    public static final int FLD_TAX_TYPE_ID = 3;
    public static final int FLD_JUMLAH_PAJAK = 4;
    public static final int FLD_TANGGAL_PENJUALAN = 5;
    public static final int FLD_TANGGAL_JATUH_TEMPO = 6;
    public static final int FLD_STATUS_PEMBAYARAN = 7;
    public static final int FLD_TANGGAL_PEMBAYARAN = 8;

    public static final String[] fieldNames = {
        "cigarette_tax_id",
        "penjual",
        "jumlah_batang",
        "tax_type_id",
        "jumlah_pajak",
        "tanggal_penjualan",
        "tanggal_jatuh_tempo",
        "status_pembayaran",
        "tanggal_pembayaran"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_DATE
    };

    public PstTaxCigarette() {
    }

    public PstTaxCigarette(int i) throws DBException {
        super(new PstTaxCigarette());
    }

    public PstTaxCigarette(String sOid) throws DBException {
        super(new PstTaxCigarette(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    public PstTaxCigarette(long lOid) throws DBException {
        super(new PstTaxCigarette(0));
        String sOid = String.valueOf(lOid);
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    @Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    @Override
    public String getTableName() {
        return TBL_TAX_CIGARETTE;
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
        return new PstTaxCigarette().getClass().getName();
    }

    @Override
    public long fetchExc(Entity ent) throws Exception {
        TaxCigarette taxCigarette = fetchExc(ent.getOID());
        ent = (Entity) taxCigarette;
        return taxCigarette.getOID();
    }

    public static TaxCigarette fetchExc(long oid) throws DBException {
        try {
            TaxCigarette taxCigarette = new TaxCigarette();
            PstTaxCigarette pstTaxCigarette = new PstTaxCigarette(oid);
            taxCigarette.setOID(oid);
            taxCigarette.setPenjual(pstTaxCigarette.getString(FLD_PENJUAL));
            taxCigarette.setJumlahBatang(pstTaxCigarette.getInt(FLD_JUMLAH_BATANG));

            long taxTypeId = pstTaxCigarette.getlong(FLD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();
            taxType.setOID(taxTypeId);
            taxCigarette.setTaxType(taxType);

            taxCigarette.setJumlahPajak(pstTaxCigarette.getdouble(FLD_JUMLAH_PAJAK));
            taxCigarette.setTanggalPenjualan(pstTaxCigarette.getDate(FLD_TANGGAL_PENJUALAN));
            String statusPembayaranStr = pstTaxCigarette.getString(FLD_STATUS_PEMBAYARAN);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxCigarette.setStatusPembayaran(statusPembayaran);
            taxCigarette.setTanggalJatuhTempo(pstTaxCigarette.getDate(FLD_TANGGAL_JATUH_TEMPO));
            taxCigarette.setTanggalPembayaran(pstTaxCigarette.getDate(FLD_TANGGAL_PEMBAYARAN));

            return taxCigarette;
        } catch (Exception e) {
            throw new DBException(new PstTaxCigarette(0), DBException.UNKNOWN);
        }
    }

    @Override
    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxCigarette) ent);
    }

    public long insertExc(TaxCigarette taxCigarette) throws DBException {
        try {
            PstTaxCigarette pstTaxCigarette = new PstTaxCigarette(0);
            pstTaxCigarette.setString(FLD_PENJUAL, taxCigarette.getPenjual());
            pstTaxCigarette.setInt(FLD_JUMLAH_BATANG, taxCigarette.getJumlahBatang());
            pstTaxCigarette.setLong(FLD_TAX_TYPE_ID, taxCigarette.getTaxType().getOID());
            pstTaxCigarette.setDouble(FLD_JUMLAH_PAJAK, taxCigarette.getJumlahPajak());
            pstTaxCigarette.setDate(FLD_TANGGAL_PENJUALAN, taxCigarette.getTanggalPenjualan());
            pstTaxCigarette.setDate(FLD_TANGGAL_JATUH_TEMPO, taxCigarette.getTanggalJatuhTempo());
            pstTaxCigarette.setString(FLD_STATUS_PEMBAYARAN, taxCigarette.getStatusPembayaran().getStatus());
            pstTaxCigarette.setDate(FLD_TANGGAL_PEMBAYARAN, taxCigarette.getTanggalPembayaran());

            pstTaxCigarette.insert();
            taxCigarette.setOID(pstTaxCigarette.getlong(FLD_CIGARETTE_TAX_ID));
            return taxCigarette.getOID();
        } catch (Exception e) {
            throw new DBException(new PstTaxCigarette(0), DBException.UNKNOWN);
        }
    }

    //Metode Update
    @Override
    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxCigarette) ent);
    }

    public long updateExc(TaxCigarette taxCigarette) throws DBException {
        try {
            if (taxCigarette.getOID() == 0) {
                throw new DBException(this, DBException.RECORD_NOT_FOUND);
            }

            PstTaxCigarette pstTaxCigarette = new PstTaxCigarette(taxCigarette.getOID());
            pstTaxCigarette.setString(FLD_PENJUAL, taxCigarette.getPenjual());
            pstTaxCigarette.setInt(FLD_JUMLAH_BATANG, taxCigarette.getJumlahBatang());
            pstTaxCigarette.setLong(FLD_TAX_TYPE_ID, taxCigarette.getTaxType().getOID());
            pstTaxCigarette.setDouble(FLD_JUMLAH_PAJAK, taxCigarette.getJumlahPajak());
            pstTaxCigarette.setDate(FLD_TANGGAL_PENJUALAN, taxCigarette.getTanggalPenjualan());
            pstTaxCigarette.setDate(FLD_TANGGAL_JATUH_TEMPO, taxCigarette.getTanggalJatuhTempo());
            pstTaxCigarette.setString(FLD_STATUS_PEMBAYARAN, taxCigarette.getStatusPembayaran().getStatus());

            pstTaxCigarette.setDate(FLD_TANGGAL_PEMBAYARAN, taxCigarette.getTanggalPembayaran());

            pstTaxCigarette.update();
            return taxCigarette.getOID();
        } catch (Exception e) {
            throw new DBException(new PstTaxCigarette(0), DBException.UNKNOWN);
        }
    }

    //Metode Delete
    @Override
    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public long deleteExc(long oid) throws DBException {
        try {
            PstTaxCigarette pstTaxCigarette = new PstTaxCigarette(oid);
            pstTaxCigarette.delete();
            return oid;
        } catch (Exception e) {
            throw new DBException(new PstTaxCigarette(0), DBException.UNKNOWN);
        }
    }

    public static Vector listAll(int limitStart, int recordToGet, String whereClause, String order) {
        return list(limitStart, recordToGet, whereClause, order, null);
    }

    public static Vector<TaxCigarette> list(int limitStart, int recordToGet, String whereClause, String order, String join) {
        Vector<TaxCigarette> lists = new Vector<>();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_CIGARETTE;

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
                TaxCigarette taxCigarette = new TaxCigarette();
                resultToObject(rs, taxCigarette);
                lists.add(taxCigarette);
            }

            rs.close(); // Ensure the ResultSet is closed after processing
            return lists;

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            DBResultSet.close(dbrs); // Ensure the DBResultSet is closed in finally block
        }
        return new Vector<>();
    }

    public static void resultToObject(ResultSet rs, TaxCigarette taxCigarette) {
        try {
            taxCigarette.setCigaretteTaxId(rs.getLong(PstTaxCigarette.fieldNames[PstTaxCigarette.FLD_CIGARETTE_TAX_ID]));
            taxCigarette.setPenjual(rs.getString(PstTaxCigarette.fieldNames[PstTaxCigarette.FLD_PENJUAL]));
            taxCigarette.setJumlahBatang(rs.getInt(PstTaxCigarette.fieldNames[PstTaxCigarette.FLD_JUMLAH_BATANG]));

            // Assuming that TaxType ID is stored as a long in the database
            long taxTypeId = rs.getLong(PstTaxCigarette.fieldNames[FLD_TAX_TYPE_ID]);
            TaxType taxType = PstTaxType.fetchExc(taxTypeId); // Ambil data TaxType
            taxCigarette.setTaxType(taxType);

            taxCigarette.setJumlahPajak(rs.getDouble(PstTaxCigarette.fieldNames[PstTaxCigarette.FLD_JUMLAH_PAJAK]));
            taxCigarette.setTanggalPenjualan(rs.getDate(PstTaxCigarette.fieldNames[PstTaxCigarette.FLD_TANGGAL_PENJUALAN]));
            taxCigarette.setTanggalJatuhTempo(rs.getDate(PstTaxCigarette.fieldNames[PstTaxCigarette.FLD_TANGGAL_JATUH_TEMPO]));
            String statusPembayaranStr = rs.getString(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_PAY_STATUS]);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxCigarette.setStatusPembayaran(statusPembayaran);

            taxCigarette.setTanggalPembayaran(rs.getDate(PstTaxCigarette.fieldNames[PstTaxCigarette.FLD_TANGGAL_PEMBAYARAN]));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    public static boolean checkCigarette(String no, int type) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "";

            if (type == 1) {
                sql = "SELECT * FROM " + TBL_TAX_CIGARETTE + " WHERE "
                        + PstTaxCigarette.fieldNames[PstTaxCigarette.FLD_PENJUAL] + " = '" + no + "'";
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