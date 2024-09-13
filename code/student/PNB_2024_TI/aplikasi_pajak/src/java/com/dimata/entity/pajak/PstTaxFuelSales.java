package com.dimata.entity.pajak;


import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;

import java.sql.ResultSet;
import java.util.Vector;
import java.math.BigDecimal;
import java.util.Date;

public class PstTaxFuelSales extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_FUEL_SALES = "fuel_sales_tax_records";

    public static final int FLD_FUEL_TAX_ID = 0;
    public static final int FLD_PENJUAL = 1;
    public static final int FLD_JUMLAH_LITER = 2;
    public static final int FLD_TAX_TYPE_ID = 3;
    public static final int FLD_JUMLAH_PAJAK = 4;
    public static final int FLD_TANGGAL_PENJUALAN = 5;
    public static final int FLD_STATUS_PEMBAYARAN = 6;
    public static final int FLD_TANGGAL_JATUH_TEMPO = 7;
    public static final int FLD_TANGGAL_PEMBAYARAN = 8;

    public static final String[] fieldNames = {
        "fuel_tax_id",
        "penjual",
        "jumlah_liter",
        "tax_type_id",
        "jumlah_pajak",
        "tanggal_penjualan",
        "status_pembayaran",
        "tanggal_jatuh_tempo",
        "tanggal_pembayaran"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE
    };

    public PstTaxFuelSales() {
    }

    public PstTaxFuelSales(int i) throws DBException {
        super(new PstTaxFuelSales());
    }

    public PstTaxFuelSales(String sOid) throws DBException {
        super(new PstTaxFuelSales(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    public PstTaxFuelSales(long lOid) throws DBException {
        super(new PstTaxFuelSales(0));
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
        return TBL_TAX_FUEL_SALES;
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
        return new PstTaxFuelSales().getClass().getName();
    }

    @Override
    public long fetchExc(Entity ent) throws Exception {
        TaxFuelSales taxFuelSales = fetchExc(ent.getOID());
        ent = (Entity) taxFuelSales;
        return taxFuelSales.getOID();
    }

    public static TaxFuelSales fetchExc(long oid) throws DBException {
        try {
            TaxFuelSales taxFuelSales = new TaxFuelSales();
            PstTaxFuelSales pstTaxFuelSales = new PstTaxFuelSales(oid);
            taxFuelSales.setOID(oid);
            taxFuelSales.setFuelTaxId(pstTaxFuelSales.getInt(FLD_FUEL_TAX_ID));
            taxFuelSales.setPenjual(pstTaxFuelSales.getString(FLD_PENJUAL));
            taxFuelSales.setJumlahLiter(BigDecimal.valueOf(pstTaxFuelSales.getDouble(FLD_JUMLAH_LITER)));
            taxFuelSales.setTaxTypeId(pstTaxFuelSales.getInt(FLD_TAX_TYPE_ID));
            taxFuelSales.setJumlahPajak(BigDecimal.valueOf(pstTaxFuelSales.getDouble(FLD_JUMLAH_PAJAK)));
            taxFuelSales.setTanggalPenjualan(pstTaxFuelSales.getDate(FLD_TANGGAL_PENJUALAN));
           taxFuelSales.setTanggalJatuhTempo(pstTaxFuelSales.getDate(FLD_TANGGAL_JATUH_TEMPO));
             String statusPembayaranStr = pstTaxFuelSales.getString(FLD_STATUS_PEMBAYARAN);
        PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase()); 
        taxFuelSales.setStatusPembayaran(statusPembayaran);
        
            
            taxFuelSales.setTanggalPembayaran(pstTaxFuelSales.getDate(FLD_TANGGAL_PEMBAYARAN));
            return taxFuelSales;
        } catch (Exception e) {
            throw new DBException(new PstTaxFuelSales(0), DBException.UNKNOWN);
        }       
    }

    @Override
    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxFuelSales) ent);
    }

    public long insertExc(TaxFuelSales taxFuelSales) throws DBException {
        try {
            PstTaxFuelSales pstTaxFuelSales = new PstTaxFuelSales(0);
            pstTaxFuelSales.setString(FLD_PENJUAL, taxFuelSales.getPenjual());
            pstTaxFuelSales.setDouble(FLD_JUMLAH_LITER, taxFuelSales.getJumlahLiter().doubleValue());
            pstTaxFuelSales.setInt(FLD_TAX_TYPE_ID, taxFuelSales.getTaxTypeId());
            pstTaxFuelSales.setDouble(FLD_JUMLAH_PAJAK, taxFuelSales.getJumlahPajak().doubleValue());
            pstTaxFuelSales.setDate(FLD_TANGGAL_PENJUALAN, taxFuelSales.getTanggalPenjualan());
             pstTaxFuelSales.setDate(FLD_TANGGAL_JATUH_TEMPO, taxFuelSales.getTanggalJatuhTempo());
            pstTaxFuelSales.setString(FLD_STATUS_PEMBAYARAN, taxFuelSales.getStatusPembayaran().getStatus()); 
           
            pstTaxFuelSales.setDate(FLD_TANGGAL_PEMBAYARAN, taxFuelSales.getTanggalPembayaran());
            pstTaxFuelSales.insert();
            return taxFuelSales.getOID();
        } catch (Exception e) {
            throw new DBException(new PstTaxFuelSales(0), DBException.UNKNOWN);
        }
    }

    @Override
    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxFuelSales) ent);
    }

    public long updateExc(TaxFuelSales taxFuelSales) throws DBException {
        try {
            if (taxFuelSales.getOID() != 0) {
                PstTaxFuelSales pstTaxFuelSales = new PstTaxFuelSales(taxFuelSales.getOID());
                pstTaxFuelSales.setString(FLD_PENJUAL, taxFuelSales.getPenjual());
                pstTaxFuelSales.setDouble(FLD_JUMLAH_LITER, taxFuelSales.getJumlahLiter().doubleValue());
                pstTaxFuelSales.setInt(FLD_TAX_TYPE_ID, taxFuelSales.getTaxTypeId());
                pstTaxFuelSales.setDouble(FLD_JUMLAH_PAJAK, taxFuelSales.getJumlahPajak().doubleValue());
                pstTaxFuelSales.setDate(FLD_TANGGAL_PENJUALAN, taxFuelSales.getTanggalPenjualan());
                pstTaxFuelSales.setDate(FLD_TANGGAL_JATUH_TEMPO, taxFuelSales.getTanggalJatuhTempo());
               pstTaxFuelSales.setString(FLD_STATUS_PEMBAYARAN, taxFuelSales.getStatusPembayaran().getStatus()); 
                
                pstTaxFuelSales.setDate(FLD_TANGGAL_PEMBAYARAN, taxFuelSales.getTanggalPembayaran());
                pstTaxFuelSales.update();
                return taxFuelSales.getOID();
            }
        } catch (Exception e) {
            throw new DBException(new PstTaxFuelSales(0), DBException.UNKNOWN);
        }
        return 0;
    }

    @Override
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }

    public long deleteExc(long oid) throws DBException {
        try {
            PstTaxFuelSales pstTaxFuelSales = new PstTaxFuelSales(oid);
            pstTaxFuelSales.delete();
            return oid;
        } catch (Exception e) {
            throw new DBException(new PstTaxFuelSales(0), DBException.UNKNOWN);
        }
    }

    public static Vector listAll(int limitStart, int recordToGet, String whereClause, String order) {
    return list(limitStart, recordToGet, whereClause, order, null);
}
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order, String join) {
    Vector lists = new Vector();
    DBResultSet dbrs = null;
    try {
        String sql = "SELECT * FROM " + TBL_TAX_FUEL_SALES;

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
            TaxFuelSales TaxFuelSales = new TaxFuelSales();
            resultToObject(rs, TaxFuelSales);
            lists.add(TaxFuelSales);
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
    public static void resultToObject(ResultSet rs, TaxFuelSales taxFuelSales) {
    try {
        taxFuelSales.setOID(rs.getLong(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_FUEL_TAX_ID]));
        taxFuelSales.setPenjual(rs.getString(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_PENJUAL]));
        taxFuelSales.setJumlahLiter(rs.getBigDecimal(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_JUMLAH_LITER]));
        
        long taxTypeId = rs.getLong(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_TAX_TYPE_ID]);
        TaxType taxType = PstTaxType.fetchExc(taxTypeId);
        taxFuelSales.setTaxType(taxType);
        
        taxFuelSales.setJumlahPajak(rs.getBigDecimal(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_JUMLAH_PAJAK]));
        taxFuelSales.setTanggalPenjualan(rs.getDate(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_TANGGAL_PENJUALAN]));
        taxFuelSales.setTanggalJatuhTempo(rs.getDate(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_TANGGAL_JATUH_TEMPO]));
        String statusPembayaranStr = rs.getString(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_STATUS_PEMBAYARAN]);
        PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
        taxFuelSales.setStatusPembayaran(statusPembayaran);
        
        
        taxFuelSales.setTanggalPembayaran(rs.getDate(PstTaxFuelSales.fieldNames[PstTaxFuelSales.FLD_TANGGAL_PEMBAYARAN]));
    } catch (Exception e) {
        System.out.println("Error in resultToObject: " + e);
    }
}

    private long getDouble(int FLD_JUMLAH_LITER) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}