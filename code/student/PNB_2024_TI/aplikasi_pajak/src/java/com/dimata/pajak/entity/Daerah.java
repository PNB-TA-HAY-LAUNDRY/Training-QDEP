package com.dimata.pajak.entity;

import com.dimata.qdep.entity.Entity;

public class Daerah extends Entity {

    private long id;
    private String nama;
    private String kodeDaerah;
    private String deskripsi;

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

    public String getKodeDaerah() {
        return kodeDaerah;
    }

    public void setKodeDaerah(String kodeDaerah) {
        this.kodeDaerah = kodeDaerah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
