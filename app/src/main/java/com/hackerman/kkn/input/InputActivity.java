package com.hackerman.kkn.input;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackerman.kkn.ProgramKegiatan;
import com.hackerman.kkn.R;
import com.hackerman.kkn.main.Jam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InputActivity extends AppCompatActivity {

    private EditText inputJudul;
    private EditText inputUraian;
    private EditText inputPeserta;
    private EditText inputKode;
    private Button inputTanggal;
    private Spinner inputKategori;
    private Button inputEndTime;
    private Button inputStartTime;
    private String judul;
    private String uraian;
    private String peserta;
    private String kategori;
    private String kode;
    public long tanggal;
    public long endTime;
    public long startTime;
    private DatabaseReference programRef;
    private DatabaseReference jamRef;
    private ProgramKegiatan program;
    private long durasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputJudul = (EditText)findViewById(R.id.input_judul);
        inputUraian = (EditText)findViewById(R.id.input_uraian);
        inputPeserta = (EditText)findViewById(R.id.input_peserta);
        inputKode = (EditText)findViewById(R.id.input_kode);
        inputTanggal = (Button)findViewById(R.id.input_tanggal);
        inputKategori = (Spinner)findViewById(R.id.input_kategori);
        inputStartTime = (Button)findViewById(R.id.input_start_time);
        inputEndTime = (Button)findViewById(R.id.input_end_time);

        initData();
        String userId = "0";
        programRef = FirebaseDatabase.getInstance().getReference("program").child(userId);
        jamRef = FirebaseDatabase.getInstance().getReference("jam").child("0");
        handleButtonClick();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                String id = programRef.push().getKey();
                durasi = endTime - startTime;
                program = new ProgramKegiatan(id, tanggal, kategori,
                        judul, kode, uraian, startTime, endTime, peserta, durasi);
                programRef.child(id).setValue(program).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            jamRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Jam jam = dataSnapshot.getValue(Jam.class);
                                    switch (program.getCategory()) {
                                        case ProgramKegiatan.CATEGORY_TEMA :
                                            jam.setTema(jam.getTema() + durasi);
                                            break;
                                        case ProgramKegiatan.CATEGORY_NON_TEMA :
                                            jam.setNonTema(jam.getNonTema() + durasi);
                                            break;
                                        case ProgramKegiatan.CATEGORY_BANTU_TEMA :
                                            jam.setBantuTema(jam.getBantuTema() + durasi);
                                            break;
                                        case ProgramKegiatan.CATEGORY_NON_PROGRAM :
                                            jam.setNonProgram(jam.getNonProgram() + durasi);
                                            break;
                                    }
                                    jamRef.setValue(jam);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            onBackPressed();
                        }
                    }
                });
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initData() {
        judul = "";
        uraian = "";
        peserta = "";
        kode = "";
        kategori = ProgramKegiatan.CATEGORY_TEMA;

        tanggal = new Date().getTime();
        setDateButton(tanggal);

        startTime = tanggal;
        endTime = tanggal;
    }

    public void setDateButton(long time) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM", Locale.getDefault());
        inputTanggal.setText(format.format(time));
    }

    public void setStartTimeButton(long time) {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.getDefault());
        inputStartTime.setText(format.format(time));
    }

    public void setEndTimeButton(long time) {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.getDefault());
        inputEndTime.setText(format.format(time));
    }

    private void getData() {
        if (!inputJudul.getText().toString().isEmpty()) {
            judul = inputJudul.getText().toString();
        }
        if (!inputUraian.getText().toString().isEmpty()) {
            uraian = inputUraian.getText().toString();
        }
        if (!inputPeserta.getText().toString().isEmpty()) {
            peserta = inputPeserta.getText().toString();
        }
        if (!inputKode.getText().toString().isEmpty()) {
            kode = inputKode.getText().toString();
        }

        int spinnerPos = inputKategori.getSelectedItemPosition();
        switch (spinnerPos) {
            case 0 :
                kategori = ProgramKegiatan.CATEGORY_TEMA;
                break;
            case 1 :
                kategori = ProgramKegiatan.CATEGORY_NON_TEMA;
                break;
            case 2 :
                kategori = ProgramKegiatan.CATEGORY_BANTU_TEMA;
                break;
            case 3 :
                kategori = ProgramKegiatan.CATEGORY_NON_PROGRAM;
        }
    }

    private void handleButtonClick() {
        inputTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new DatePicker();
                fragment.show(getSupportFragmentManager(), "");
            }
        });

        inputStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new StartTimePicker();
                fragment.show(getSupportFragmentManager(), "");
            }
        });

        inputEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new EndTimePicker();
                fragment.show(getSupportFragmentManager(), "");
            }
        });
    }
}
