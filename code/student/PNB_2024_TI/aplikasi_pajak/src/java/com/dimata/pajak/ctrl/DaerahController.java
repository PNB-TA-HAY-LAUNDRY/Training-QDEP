package com.dimata.pajak.ctrl;

import com.dimata.pajak.entity.Daerah;
import com.dimata.pajak.entity.PstDaerah;
import com.dimata.qdep.db.DBException;

import javax.servlet.http.HttpServletRequest;
import java.util.Vector;

public class DaerahController {

    public static Daerah getDaerahById(long id) throws DBException {
        return PstDaerah.fetchExc(id);
    }

    public static long saveDaerah(HttpServletRequest request) throws DBException {
        Daerah daerah = new Daerah();
        daerah.setNama(request.getParameter("nama"));
        daerah.setKodeDaerah(request.getParameter("kodeDaerah"));
        daerah.setDeskripsi(request.getParameter("deskripsi"));

        String oid = request.getParameter("oid");
        if (oid != null && !oid.isEmpty()) {
            long daerahId = Long.parseLong(oid);
            if (daerahId != 0) {
                daerah.setId(daerahId);
                return PstDaerah.updateExc(daerah);
            }
        }
        return PstDaerah.insertExc(daerah);
    }

    public static long deleteDaerah(HttpServletRequest request) throws DBException {
        String oid = request.getParameter("oid");
        if (oid != null && !oid.isEmpty()) {
            long daerahId = Long.parseLong(oid);
            return PstDaerah.deleteExc(daerahId);
        }
        return 0;
    }

    public static Vector<Daerah> listDaerah(int limitStart, int recordToGet, String whereClause, String order) {
        Vector<Daerah> daerahs = new Vector<>();
        try {
            daerahs = PstDaerah.list(limitStart, recordToGet, whereClause, order);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return daerahs;
    }

    public static Daerah loadDaerah(HttpServletRequest request) throws DBException {
        String oid = request.getParameter("oid");
        if (oid != null && !oid.isEmpty()) {
            long daerahId = Long.parseLong(oid);
            return getDaerahById(daerahId);
        }
        return new Daerah();
    }
    
    
        public Vector<Daerah> getAllDaerah() {
        Vector<Daerah> daerahs = new Vector<>();
        try {
            daerahs = PstDaerah.list(0, Integer.MAX_VALUE, null, null);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return daerahs;
    }
}
