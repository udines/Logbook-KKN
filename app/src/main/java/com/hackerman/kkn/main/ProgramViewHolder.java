package com.hackerman.kkn.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hackerman.kkn.ProgramKegiatan;
import com.hackerman.kkn.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by hackerman on 19/06/17.
 */

public class ProgramViewHolder extends RecyclerView.ViewHolder {

    private final TextView tanggal;
    private final TextView jam;
    private final TextView kategori;
    private final TextView kode;
    private final TextView judul;
    private final TextView uraian;
    private final TextView peserta;
    private ProgramKegiatan program;

    public ProgramViewHolder(View itemView) {
        super(itemView);
        tanggal = (TextView)itemView.findViewById(R.id.item_program_tanggal);
        jam = (TextView)itemView.findViewById(R.id.item_program_jam);
        kategori = (TextView)itemView.findViewById(R.id.item_program_kategori);
        kode = (TextView)itemView.findViewById(R.id.item_program_kode_sektor);
        judul = (TextView)itemView.findViewById(R.id.item_program_judul);
        uraian = (TextView)itemView.findViewById(R.id.item_program_uraian);
        peserta = (TextView)itemView.findViewById(R.id.item_program_peserta);
    }

    public void setView(ProgramKegiatan p) {
        this.program = p;
        kategori.setText(p.getCategory());
        kode.setText(p.getKode());
        judul.setText(p.getTitle());
        uraian.setText(p.getUraian());
        peserta.setText(p.getPeserta());
        tanggal.setText(longToDate(p.getDate()));
        String timeRange = longToTime(p.getStartTime()) + " - " + longToTime(p.getEndTime());
        jam.setText(timeRange);
    }

    private String longToDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM", Locale.getDefault());
        return format.format(time);
    }

    private String longToTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return format.format(time);
    }
}
