package com.dimata.pajak.entity;

import com.dimata.qdep.entity.Entity;

public class JenisPajak extends Entity {

    private long id;
    private String nama;
    private String deskripsi;
    private long daerahId;  // Menyimpan ID daerah yang berelasi

    public long getOId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public long getDaerahId() {
        return daerahId;
    }

    public void setDaerahId(long daerahId) {
        this.daerahId = daerahId;
    }
}
