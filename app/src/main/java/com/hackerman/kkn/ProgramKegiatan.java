package com.hackerman.kkn;

/**
 * Created by hackerman on 19/06/17.
 */

public class ProgramKegiatan {

    public static final String CATEGORY_TEMA = "tema";
    public static final String CATEGORY_NON_TEMA = "non tema";
    public static final String CATEGORY_BANTU_TEMA = "bantu tema";
    public static final String CATEGORY_NON_PROGRAM = "non program";

    private String id;
    private long date;
    private String category;
    private String title;
    private String kode;
    private String uraian;
    private long startTime;
    private long endTime;
    private String peserta;
    private long durasi;

    public ProgramKegiatan() {
    }

    public ProgramKegiatan(String id, long date, String category, String title, String kode, String uraian, long startTime, long endTime, String peserta, long durasi) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.title = title;
        this.kode = kode;
        this.uraian = uraian;
        this.startTime = startTime;
        this.endTime = endTime;
        this.peserta = peserta;
        this.durasi = durasi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getPeserta() {
        return peserta;
    }

    public void setPeserta(String peserta) {
        this.peserta = peserta;
    }

    public long getDurasi() {
        return durasi;
    }

    public void setDurasi(long durasi) {
        this.durasi = durasi;
    }
}
