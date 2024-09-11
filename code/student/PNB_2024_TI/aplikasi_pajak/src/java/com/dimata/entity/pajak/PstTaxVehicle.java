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
 * Persistent class for vehicle tax records.
 */
public class PstTaxVehicle extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_TAX_VEHICLE = "vehicle_tax_records";

    public static final int FLD_VEHICLE_TAX_ID = 0;
    public static final int FLD_NO_PLAT = 1;
    public static final int FLD_NAMA_PEMILIK = 2;
    public static final int FLD_ALAMAT = 3;
    public static final int FLD_MERK = 4;
    public static final int FLD_MODEL = 5;
    public static final int FLD_TAHUN_PEMBUATAN = 6;
    public static final int FLD_TAX_TYPE_ID = 7;
    public static final int FLD_JUMLAH_PAJAK = 8;
    public static final int FLD_PERIODE_PAJAK = 9;
    public static final int FLD_STATUS_PEMBAYARAN = 10;
    public static final int FLD_TANGGAL_JATUH_TEMPO = 11;
    public static final int FLD_TANGGAL_PEMBAYARAN = 12;
    
    public static final String[] fieldNames = {
        "vehicle_tax_id",
        "no_plat",
        "nama_pemilik",
        "alamat",
        "merk",
        "model",
        "tahun_pembuatan",
        "tax_type_id",
        "jumlah_pajak",
        "periode_pajak",
        "status_pembayaran",
        "tanggal_jatuh_tempo",
        "tanggal_pembayaran"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, // 0
        TYPE_STRING, // no plat
        TYPE_STRING, // nama_pemilik
        TYPE_STRING, // alamat
        TYPE_STRING, // merk
        TYPE_STRING, // model
        TYPE_INT, // tahun_pembuatan
        TYPE_LONG, // tax_type_id
        TYPE_FLOAT, // jumlah_pajak
        TYPE_DATE, // periode_pajak
        TYPE_STRING, // status_pembayaran
        TYPE_DATE, // tanggal_jatuh_tempo
        TYPE_DATE // tanggal_pembayaran
    };

    public PstTaxVehicle() {
    }
    
    public PstTaxVehicle(int i) throws DBException {
        super(new PstTaxVehicle());
    }

    public PstTaxVehicle(String sOid) throws DBException {
        super(new PstTaxVehicle(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
    }

    public PstTaxVehicle(long lOid) throws DBException {
        super(new PstTaxVehicle(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
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
        return TBL_TAX_VEHICLE;
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
        return new PstTaxVehicle().getClass().getName();
    }

    @Override
    public long insertExc(Entity ent) throws Exception {
        return insertExc((TaxVehicle) ent);
    }

    public long insertExc(TaxVehicle taxVehicle) throws DBException {
        try {
            PstTaxVehicle pstTaxVehicle = new PstTaxVehicle(0);
            pstTaxVehicle.setString(FLD_NO_PLAT, taxVehicle.getNoPlat());
            pstTaxVehicle.setString(FLD_NAMA_PEMILIK, taxVehicle.getNamaPemilik());
            pstTaxVehicle.setString(FLD_ALAMAT, taxVehicle.getAlamat());
            pstTaxVehicle.setString(FLD_MERK, taxVehicle.getMerk());
            pstTaxVehicle.setString(FLD_MODEL, taxVehicle.getModel());
            pstTaxVehicle.setInt(FLD_TAHUN_PEMBUATAN, taxVehicle.getTahunPembuatan());
            pstTaxVehicle.setLong(FLD_TAX_TYPE_ID, taxVehicle.getTaxType().getOID());
            pstTaxVehicle.setDouble(FLD_JUMLAH_PAJAK, taxVehicle.getJumlahPajak());
            pstTaxVehicle.setDate(FLD_PERIODE_PAJAK, taxVehicle.getPeriodePajak());
            pstTaxVehicle.setString(FLD_STATUS_PEMBAYARAN, taxVehicle.getStatusPembayaran().name()); // Enum to String
            pstTaxVehicle.setDate(FLD_TANGGAL_JATUH_TEMPO, taxVehicle.getTanggalJatuhTempo());
            pstTaxVehicle.setDate(FLD_TANGGAL_PEMBAYARAN, taxVehicle.getTanggalPembayaran());
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxVehicle(0), DBException.UNKNOWN);
        }
        return taxVehicle.getOID();
    }

//    @Override
//    public long updateExc(Entity ent) throws Exception {
//        return updateExc((TaxVehicle) ent);
//    }
//
//    public long updateExc(TaxVehicle taxVehicle) throws DBException {
//        try {
//            if (taxVehicle.getOID() != 0) {
//                PstTaxVehicle pstTaxVehicle = new PstTaxVehicle(taxVehicle.getOID());
//                pstTaxVehicle.setString(FLD_NO_PLAT, taxVehicle.getNoPlat());
//                pstTaxVehicle.setString(FLD_NAMA_PEMILIK, taxVehicle.getNamaPemilik());
//                pstTaxVehicle.setString(FLD_ALAMAT, taxVehicle.getAlamat());
//                pstTaxVehicle.setString(FLD_MERK, taxVehicle.getMerk());
//                pstTaxVehicle.setString(FLD_MODEL, taxVehicle.getModel());
//                pstTaxVehicle.setInt(FLD_TAHUN_PEMBUATAN, taxVehicle.getTahunPembuatan());
//                pstTaxVehicle.setLong(FLD_TAX_TYPE_ID, taxVehicle.getTaxType().getOID());
//                pstTaxVehicle.setDouble(FLD_JUMLAH_PAJAK, taxVehicle.getJumlahPajak());
//                pstTaxVehicle.setDate(FLD_PERIODE_PAJAK, taxVehicle.getPeriodePajak());
//                pstTaxVehicle.setString(FLD_STATUS_PEMBAYARAN, taxVehicle.getStatusPembayaran().name()); // Enum to String
//                pstTaxVehicle.setDate(FLD_TANGGAL_JATUH_TEMPO, taxVehicle.getTanggalJatuhTempo());
//                pstTaxVehicle.setDate(FLD_TANGGAL_PEMBAYARAN, taxVehicle.getTanggalPembayaran());
//                pstTaxVehicle.update();
//                return taxVehicle.getOID();
//            }
//        } catch (DBException dbe) {
//            throw dbe;
//        } catch (Exception e) {
//            throw new DBException(new PstTaxVehicle(0), DBException.UNKNOWN);
//        }
//        return 0;
//    }
//
//    @Override
//    public long deleteExc(Entity ent) throws Exception {
//        if (ent == null) {
//            throw new DBException(this, DBException.RECORD_NOT_FOUND);
//        }
//        return deleteExc(ent.getOID());
//    }

//    public long deleteExc(long oid) throws DBException {
//        try {
//            PstTaxVehicle pstTaxVehicle = new PstTaxVehicle(oid);
//            pstTaxVehicle.delete();
//        } catch (DBException dbe) {
//            throw dbe;
//        } catch (Exception e) {
//            throw new DBException(new PstTaxVehicle(0), DBException.UNKNOWN);
//        }
//        return oid;
//    }

    @Override
    public long fetchExc(Entity ent) throws Exception {
        TaxVehicle taxVehicle = fetchExc(ent.getOID());
        ent = (Entity) taxVehicle;
        return taxVehicle.getOID();
    }

    public static TaxVehicle fetchExc(long oid) throws DBException {
        try {
            TaxVehicle taxVehicle = new TaxVehicle();
            PstTaxVehicle pstTaxVehicle = new PstTaxVehicle(oid);
            taxVehicle.setOID(oid);
            taxVehicle.setVehicleTaxId(pstTaxVehicle.getlong(FLD_VEHICLE_TAX_ID));
            taxVehicle.setNoPlat(pstTaxVehicle.getString(FLD_NO_PLAT));
            taxVehicle.setNamaPemilik(pstTaxVehicle.getString(FLD_NAMA_PEMILIK));
            taxVehicle.setAlamat(pstTaxVehicle.getString(FLD_ALAMAT));
            taxVehicle.setMerk(pstTaxVehicle.getString(FLD_MERK));
            taxVehicle.setModel(pstTaxVehicle.getString(FLD_MODEL));
            taxVehicle.setTahunPembuatan(pstTaxVehicle.getInt(FLD_TAHUN_PEMBUATAN));
            long taxTypeId = pstTaxVehicle.getlong(FLD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();
            taxType.setOID(taxTypeId);
            taxVehicle.setTaxType(taxType);
            taxVehicle.setJumlahPajak(pstTaxVehicle.getdouble(FLD_JUMLAH_PAJAK));
            taxVehicle.setPeriodePajak(pstTaxVehicle.getDate(FLD_PERIODE_PAJAK));
            String statusPembayaranStr = pstTaxVehicle.getString(FLD_STATUS_PEMBAYARAN);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase()); 
            taxVehicle.setStatusPembayaran(statusPembayaran);
            taxVehicle.setTanggalJatuhTempo(pstTaxVehicle.getDate(FLD_TANGGAL_JATUH_TEMPO));
            taxVehicle.setTanggalPembayaran(pstTaxVehicle.getDate(FLD_TANGGAL_PEMBAYARAN));
            return taxVehicle;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxVehicle(0), DBException.UNKNOWN);
        }
    }
    
    public static Vector listAll(int limitStart, int recordToGet, String whereClause, String order) {
    return list(limitStart, recordToGet, whereClause, order, null);
}

public static Vector list(int limitStart, int recordToGet, String whereClause, String order, String join) {
    Vector lists = new Vector();
    DBResultSet dbrs = null;
    try {
        String sql = "SELECT * FROM " + TBL_TAX_VEHICLE;

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
            TaxVehicle taxVehicle = new TaxVehicle();
            resultToObject(rs, taxVehicle);
            lists.add(taxVehicle);
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

public static void resultToObject(ResultSet rs, TaxVehicle TaxVehicle) {
    try {
        TaxVehicle.setVehicleTaxId(rs.getLong(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_VEHICLE_TAX_ID]));
        TaxVehicle.setNoPlat(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_NO_PLAT]));
        TaxVehicle.setNamaPemilik(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_NAMA_PEMILIK]));
        TaxVehicle.setAlamat(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_ALAMAT]));
        TaxVehicle.setMerk(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_MERK]));
        TaxVehicle.setModel(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_MODEL]));
        TaxVehicle.setTahunPembuatan(rs.getInt(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_TAHUN_PEMBUATAN]));

        long taxTypeId = rs.getLong(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_TAX_TYPE_ID]);
        TaxType taxType = PstTaxType.fetchExc(taxTypeId); // Mengambil object TaxType berdasarkan ID
        TaxVehicle.setTaxType(taxType); 

        TaxVehicle.setJumlahPajak(rs.getDouble(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_JUMLAH_PAJAK]));
        TaxVehicle.setPeriodePajak(rs.getDate(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_PERIODE_PAJAK]));

        String statusPembayaranStr = rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_STATUS_PEMBAYARAN]);
        PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase()); // Mengubah string menjadi enum PaymentStatus
        TaxVehicle.setStatusPembayaran(statusPembayaran);

        TaxVehicle.setTanggalJatuhTempo(rs.getDate(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_TANGGAL_JATUH_TEMPO]));
        TaxVehicle.setTanggalPembayaran(rs.getDate(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_TANGGAL_PEMBAYARAN]));
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    @Override
    public long updateExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long deleteExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
