package com.hackerman.kkn.main;

/**
 * Created by hackerman on 21/06/17.
 */

public class Jam {

    private long tema;
    private long nonTema;
    private long bantuTema;
    private long nonProgram;

    public Jam() {
    }

    public Jam(long tema, long nonTema, long bantuTema, long nonProgram) {
        this.tema = tema;
        this.nonTema = nonTema;
        this.bantuTema = bantuTema;
        this.nonProgram = nonProgram;
    }

    public long getTema() {
        return tema;
    }

    public void setTema(long tema) {
        this.tema = tema;
    }

    public long getNonTema() {
        return nonTema;
    }

    public void setNonTema(long nonTema) {
        this.nonTema = nonTema;
    }

    public long getBantuTema() {
        return bantuTema;
    }

    public void setBantuTema(long bantuTema) {
        this.bantuTema = bantuTema;
    }

    public long getNonProgram() {
        return nonProgram;
    }

    public void setNonProgram(long nonProgram) {
        this.nonProgram = nonProgram;
    }
}
