package com.dimata.entity.pajak;


import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.entity.Entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.util.Vector;

/**
 * Persistent class for vehicle tax records.
 */
public class PstTaxVehicle extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TAX_VEHICLE = "vehicle_tax_record";

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
        TYPE_STRING, // tahun_pembuatan
        TYPE_LONG, // tax_type_id
        TYPE_FLOAT, // jumlah_pajak
        TYPE_STRING,// periode_pajak
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
    
     public static void insertExc(TaxVehicle taxVehicle) throws DBException, SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBHandler.getConnection(); // Mendapatkan koneksi dari DBHandler
            String sql = "INSERT INTO vehicle_tax_record (no_plat, nama_pemilik, alamat, merk, model, tahun_pembuatan, tax_type_id, jumlah_pajak, periode_pajak, status_pembayaran, tanggal_jatuh_tempo, tanggal_pembayaran) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, taxVehicle.getNoPlat());
            pstmt.setString(2, taxVehicle.getNamaPemilik());
            pstmt.setString(3, taxVehicle.getAlamat());
            pstmt.setString(4, taxVehicle.getMerk());
            pstmt.setString(5, taxVehicle.getModel());
            pstmt.setString(6, taxVehicle.getTahunPembuatan());
            pstmt.setLong(7, taxVehicle.getTaxType().getTaxTypeId());
            pstmt.setDouble(8, taxVehicle.getJumlahPajak());
            pstmt.setString(9, taxVehicle.getPeriodePajak());
            pstmt.setString(10, taxVehicle.getStatusPembayaran().toString());
            pstmt.setDate(11, (Date) taxVehicle.getTanggalJatuhTempo());
            pstmt.setDate(12, (Date) taxVehicle.getTanggalPembayaran());
            pstmt.executeUpdate();
        } catch (SQLException e) {
    e.printStackTrace(); // Menampilkan error di console
    System.out.println("Error during insertion: " + e.getMessage()); // Menampilkan pesan error
    throw e; // Lempar exception agar bisa diketahui jenis kesalahannya
} finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close(); // Hanya jika Anda membuka koneksi di sini
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
            taxVehicle.setTahunPembuatan(pstTaxVehicle.getString(FLD_TAHUN_PEMBUATAN));

            // Mengambil TaxType berdasarkan taxTypeId
            long taxTypeId = pstTaxVehicle.getlong(FLD_TAX_TYPE_ID);
            TaxType taxType = new TaxType();
            taxType.setOID(taxTypeId);
            taxVehicle.setTaxType(taxType);

            taxVehicle.setJumlahPajak(pstTaxVehicle.getdouble(FLD_JUMLAH_PAJAK));
            taxVehicle.setPeriodePajak(pstTaxVehicle.getString(FLD_PERIODE_PAJAK));
            // Mengatur status pembayaran
            String statusPembayaranStr = pstTaxVehicle.getString(FLD_STATUS_PEMBAYARAN);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
            taxVehicle.setStatusPembayaran(statusPembayaran);
            
            
            // Tetap menampilkan tanggal jatuh tempo, bahkan jika status pembayaran belum dibayar
            taxVehicle.setTanggalJatuhTempo(pstTaxVehicle.getDate(FLD_TANGGAL_JATUH_TEMPO));

            // Tanggal pembayaran bisa null jika belum dibayar
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

    public static Vector<TaxVehicle> list(int limitStart, int recordToGet, String whereClause, String order, String join) {
        Vector<TaxVehicle> lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_TAX_VEHICLE;

            if (join != null && !join.isEmpty()) {
                sql += " JOIN " + join;
            }

            if (whereClause != null && !whereClause.isEmpty()) {
                sql += " WHERE " + whereClause;
            }

            if (order != null && !order.isEmpty()) {
                sql += " ORDER BY " + order;
            }

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart >= 0 && recordToGet > 0) {
                        sql += " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                default:
                    break;
            }

            System.out.println("SQL Query: " + sql); // Debugging line

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                TaxVehicle taxVehicle = new TaxVehicle();
                resultToObject(rs, taxVehicle);
                lists.add(taxVehicle);
            }

            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
public static long deleteExc(long vehicleTaxId) throws DBException {
        String query = "DELETE FROM " + TBL_TAX_VEHICLE + " WHERE " + fieldNames[FLD_VEHICLE_TAX_ID] + " = ?";
        System.out.println("Executing query: " + query + " with ID: " + vehicleTaxId);

        try (Connection conn = DBHandler.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setLong(1, vehicleTaxId);
            int result = pst.executeUpdate();
            System.out.println("Result of deletion: " + result);

            if (result > 0) {
                return vehicleTaxId; // Penghapusan berhasil, mengembalikan ID yang dihapus
            } else {
                return 0; // Tidak ada data yang dihapus
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw new DBException(e); // Menangani kesalahan SQL
        }
    }



    public static void resultToObject(ResultSet rs, TaxVehicle TaxVehicle) {
        try {
            TaxVehicle.setVehicleTaxId(rs.getLong(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_VEHICLE_TAX_ID]));
            TaxVehicle.setNoPlat(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_NO_PLAT]));
            TaxVehicle.setNamaPemilik(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_NAMA_PEMILIK]));
            TaxVehicle.setAlamat(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_ALAMAT]));
            TaxVehicle.setMerk(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_MERK]));
            TaxVehicle.setModel(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_MODEL]));
            TaxVehicle.setTahunPembuatan(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_TAHUN_PEMBUATAN]));

            long taxTypeId = rs.getLong(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_TAX_TYPE_ID]);
            TaxType taxType = PstTaxType.fetchExc(taxTypeId); // Mengambil object TaxType berdasarkan ID
            TaxVehicle.setTaxType(taxType);

            TaxVehicle.setJumlahPajak(rs.getDouble(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_JUMLAH_PAJAK]));
            TaxVehicle.setPeriodePajak(rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_PERIODE_PAJAK]));
            TaxVehicle.setTanggalJatuhTempo(rs.getDate(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_TANGGAL_JATUH_TEMPO]));
            String statusPembayaranStr = rs.getString(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_STATUS_PEMBAYARAN]);
            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase()); // Mengubah string menjadi enum PaymentStatus
            TaxVehicle.setStatusPembayaran(statusPembayaran);
            TaxVehicle.setTanggalPembayaran(rs.getDate(PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_TANGGAL_PEMBAYARAN]));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static boolean checkTaxVehicle(String no, int type) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "";

            if (type == 1) {
                sql = "SELECT * FROM " + TBL_TAX_VEHICLE + " WHERE "
                        + PstTaxVehicle.fieldNames[PstTaxVehicle.FLD_NO_PLAT] + " = '" + no + "'";
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

    @Override
    public long updateExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long deleteExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long insertExc(Entity ent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}