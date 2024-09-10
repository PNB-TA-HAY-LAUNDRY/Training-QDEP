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
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.entity.Entity;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author ihsan
 */
public class PstTaxOwnership extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_TAX_OWNER = "vehicle_ownership_transfer_tax_records";

    public static final int FLD_TRANSFER_TAX_ID = 0;
    public static final int FLD_NO_PLAT = 1;
    public static final int FLD_OLD_NAME = 2;
    public static final int FLD_NEW_NAME = 3;
    public static final int FLD_NEW_ADDRESS = 4;
    public static final int FLD_TAX_TYPE_ID = 5;
    public static final int FLD_TOTAL_TAX = 6;
    public static final int FLD_PROCESS_DATE = 7;
    public static final int FLD_PAY_STATUS = 8;
    public static final int FLD_DUE_DATE = 9;
    public static final int FLD_PAY_DATE = 10;
    
    
    public static final String[] fieldNames = {
        "transfer_tax_id",
        "no_plat",
        "nama_pemilik_lama",
        "nama_pemilik_baru",
        "alamat_baru",
        "tax_type_id",
        "jumlah_pajak",
        "tanggal_proses",
        "status_pembayaran",
        "tanggal_jatuh_tempo",
        "tanggal_pembayaran",
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, // 0
        TYPE_STRING,// no plat
        TYPE_STRING,// nama_pemilik_lama
        TYPE_STRING,// nama_pemilik_baru
        TYPE_STRING,// alamat_baru
        TYPE_LONG,// tax_type_id
        TYPE_FLOAT,// jumlah_pajak
        TYPE_DATE,// tanggal_proses
        TYPE_STRING,// status_pembayaran
        TYPE_DATE,// tanggal_jatuh_tempo
        TYPE_DATE,// tanggal pembayaran
    };

    public PstTaxOwnership(){
    }
    
    // Constructor to instantiate with an integer (could be used for querying)
    public PstTaxOwnership(int i) throws DBException {
        super(new PstTaxOwnership());
    }

    // Constructor that initializes the entity with a string OID (Object ID)
    public PstTaxOwnership(String sOid) throws DBException {
        super(new PstTaxOwnership(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    // Constructor that initializes the entity with a long OID
    public PstTaxOwnership(long lOid) throws DBException {
        super(new PstTaxOwnership(0));
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
        return TBL_TAX_OWNER;
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
        return new PstTaxOwnership().getClass().getName();
    }
    
    @Override
    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxOwnership) ent);
    }

    public long insertExc(TaxOwnership taxOwnership) throws DBException {
        try {
            PstTaxOwnership pstTaxOwnership = new PstTaxOwnership(0);
            pstTaxOwnership.setString(FLD_NO_PLAT, taxOwnership.getNoPlat());
            pstTaxOwnership.setString(FLD_OLD_NAME,taxOwnership.getNamaPemilikLama());
            pstTaxOwnership.setString(FLD_NEW_NAME, taxOwnership.getNamaPemilikBaru());
            pstTaxOwnership.setString(FLD_NEW_ADDRESS, taxOwnership.getAlamatBaru());
            pstTaxOwnership.setLong(FLD_TAX_TYPE_ID, taxOwnership.getTaxType().getOID());
            pstTaxOwnership.setDouble(FLD_TOTAL_TAX, taxOwnership.getJumlahPajak());
            pstTaxOwnership.setDate(FLD_PROCESS_DATE, taxOwnership.getTanggalProses());
            pstTaxOwnership.setInt(FLD_PAY_STATUS, taxOwnership.getStatusPembayaran().ordinal()); // Enum to int
            pstTaxOwnership.setDate(FLD_DUE_DATE, taxOwnership.getTanggalJatuhTempo());
            pstTaxOwnership.setDate(FLD_PAY_DATE, taxOwnership.getTanggalPembayaran());
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(taxOwnership.getOID()), DBException.UNKNOWN);
        }
        return taxOwnership.getOID();
    }
    
    // Update an AssetList entity in the database
    @Override
    public long updateExc(Entity ent) throws Exception {
        return updateExc((TaxOwnership) ent);
    }
    
    public long updateExc(TaxOwnership taxOwnership) throws DBException {
        try {
            if (taxOwnership.getOID() != 0) {
                PstTaxOwnership pstTaxOwnership = new PstTaxOwnership(taxOwnership.getOID()); 
            pstTaxOwnership.setString(FLD_NO_PLAT, taxOwnership.getNoPlat());
            pstTaxOwnership.setString(FLD_OLD_NAME,taxOwnership.getNamaPemilikLama());
            pstTaxOwnership.setString(FLD_NEW_NAME, taxOwnership.getNamaPemilikBaru());
            pstTaxOwnership.setString(FLD_NEW_ADDRESS, taxOwnership.getAlamatBaru());
            pstTaxOwnership.setLong(FLD_TAX_TYPE_ID, taxOwnership.getTaxType().getOID());
            pstTaxOwnership.setDouble(FLD_TOTAL_TAX, taxOwnership.getJumlahPajak());
            pstTaxOwnership.setDate(FLD_PROCESS_DATE, taxOwnership.getTanggalProses());
            pstTaxOwnership.setInt(FLD_PAY_STATUS, taxOwnership.getStatusPembayaran().ordinal()); // Enum to int
            pstTaxOwnership.setDate(FLD_DUE_DATE, taxOwnership.getTanggalJatuhTempo());
            pstTaxOwnership.setDate(FLD_PAY_DATE, taxOwnership.getTanggalPembayaran());
            pstTaxOwnership.update();
                return taxOwnership.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxOwnership(0), DBException.UNKNOWN);
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
            PstTaxOwnership pstTaxOwnership = new PstTaxOwnership(oid);
            pstTaxOwnership.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxOwnership(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    // Fetches an AssetList entity from the database using its OID
    @Override
    public long fetchExc(Entity ent) throws Exception {
        TaxOwnership taxOwnership = fetchExc(ent.getOID());
        ent = (Entity) taxOwnership;
        return taxOwnership.getOID();
    }
    
    public static TaxOwnership fetchExc(long oid) throws DBException {
        try {
            TaxOwnership taxOwnership = new TaxOwnership();
            PstTaxOwnership pstTaxOwnership = new PstTaxOwnership(oid);
            taxOwnership.setOID(oid);
            taxOwnership.setTransferTaxId(pstTaxOwnership.getlong(FLD_TRANSFER_TAX_ID));
            taxOwnership.setNoPlat(pstTaxOwnership.getString(FLD_NO_PLAT));
            taxOwnership.setNamaPemilikLama(pstTaxOwnership.getString(FLD_OLD_NAME));
            taxOwnership.setNamaPemilikBaru(pstTaxOwnership.getString(FLD_NEW_NAME));
            taxOwnership.setAlamatBaru(pstTaxOwnership.getString(FLD_NEW_ADDRESS));
            
            // Assuming the TaxType ID is being set and retrieved using a separate method
            long taxTypeId = pstTaxOwnership.getlong(FLD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();
            taxType.setOID(taxTypeId);
            taxOwnership.setTaxType(taxType);
            
            taxOwnership.setJumlahPajak(pstTaxOwnership.getdouble(FLD_TOTAL_TAX));
            taxOwnership.setTanggalProses(pstTaxOwnership.getDate(FLD_PROCESS_DATE));
            
            // Retrieving and setting enum value from integer
            int paymentStatusOrdinal = pstTaxOwnership.getInt(FLD_PAY_STATUS);
            taxOwnership.setStatusPembayaran(TaxOwnership.StatusPembayaran.values()[paymentStatusOrdinal]);
            
            taxOwnership.setTanggalJatuhTempo(pstTaxOwnership.getDate(FLD_DUE_DATE));
            taxOwnership.setTanggalPembayaran(pstTaxOwnership.getDate(FLD_PAY_DATE));
            return taxOwnership;
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
        String sql = "SELECT * FROM " + TBL_TAX_OWNER;

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
            TaxOwnership taxOwnership = new TaxOwnership();
            resultToObject(rs, taxOwnership);
            lists.add(taxOwnership);
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
    
   public static void resultToObject(ResultSet rs, TaxOwnership taxOwnership) {
    try {
        taxOwnership.setTransferTaxId(rs.getLong(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_TRANSFER_TAX_ID]));
        taxOwnership.setNoPlat(rs.getString(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_NO_PLAT]));
        taxOwnership.setNamaPemilikLama(rs.getString(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_OLD_NAME]));
        taxOwnership.setNamaPemilikBaru(rs.getString(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_NEW_NAME]));
        taxOwnership.setAlamatBaru(rs.getString(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_NEW_ADDRESS]));
        
        long taxTypeId = rs.getLong(PstTaxOwnership.fieldNames[FLD_TAX_TYPE_ID]);
        TaxType taxType = PstTaxType.fetchExc(taxTypeId); // Ambil data TaxType
        taxOwnership.setTaxType(taxType); // Simpan objek TaxType ke dalam taxWater
        
        taxOwnership.setJumlahPajak(rs.getDouble(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_TOTAL_TAX]));
        taxOwnership.setTanggalProses(rs.getDate(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_PROCESS_DATE]));
        
        // Retrieving and setting enum value from integer in the database
        int paymentStatusOrdinal = rs.getInt(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_PAY_STATUS]);
        if (paymentStatusOrdinal >= 0 && paymentStatusOrdinal < TaxOwnership.StatusPembayaran.values().length) {
            taxOwnership.setStatusPembayaran(TaxOwnership.StatusPembayaran.values()[paymentStatusOrdinal]);
        }
        
        taxOwnership.setTanggalJatuhTempo(rs.getDate(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_DUE_DATE]));
        taxOwnership.setTanggalPembayaran(rs.getDate(PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_PAY_DATE]));
    } catch (Exception e) {
        System.out.println("Error: " + e);
    }
}

   // Checks if an asset condition exists in the database based on name and type
    public static boolean checkTaxOwnership(String no, int type) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "";

            if (type == 1) {// check asset name
                sql = "SELECT * FROM " + TBL_TAX_OWNER + " WHERE "
                        + PstTaxOwnership.fieldNames[PstTaxOwnership.FLD_NO_PLAT] + " = '" + no + "'";
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