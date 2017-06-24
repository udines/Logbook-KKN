package com.hackerman.kkn.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hackerman.kkn.ProgramKegiatan;
import com.hackerman.kkn.R;
import com.hackerman.kkn.input.InputActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference programRef;
    private Query allQuery, todayQuery, weekQuery, temaQuery, nonTemaQuery, bantuTemaQuery, nonProgramQuery;
    private RecyclerView recyclerView;
    private ValueEventListener eventListener;
    private ValueEventListener jamListener;
    private int queryIndex = 0;
    private DatabaseReference jamRef;
    private TextView textTema;
    private TextView textNonTema;
    private TextView textBantuTema;
    private TextView textNonProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.main_recyclerview);
        programRef = FirebaseDatabase.getInstance().getReference("program").child("0");
        jamRef = FirebaseDatabase.getInstance().getReference("jam").child("0");
        allQuery = programRef;
        temaQuery = programRef.orderByChild("category").equalTo(ProgramKegiatan.CATEGORY_TEMA);
        nonTemaQuery = programRef.orderByChild("category").equalTo(ProgramKegiatan.CATEGORY_NON_TEMA);
        bantuTemaQuery = programRef.orderByChild("category").equalTo(ProgramKegiatan.CATEGORY_BANTU_TEMA);
        nonProgramQuery = programRef.orderByChild("category").equalTo(ProgramKegiatan.CATEGORY_NON_PROGRAM);
        textTema = (TextView)findViewById(R.id.main_jam_tema);
        textNonTema = (TextView)findViewById(R.id.main_jam_non_tema);
        textBantuTema = (TextView)findViewById(R.id.main_jam_bantu_tema);
        textNonProgram = (TextView)findViewById(R.id.main_jam_non_program);

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ProgramKegiatan> programList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    programList.add(dataSnapshot1.getValue(ProgramKegiatan.class));
                }
                Collections.reverse(programList);
                ProgramAdapter adapter = new ProgramAdapter(programList, getApplicationContext(), getLayoutInflater());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        jamListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Jam jam = dataSnapshot.getValue(Jam.class);
                textTema.setText(String.valueOf("Tema : " + jam.getTema() / 3600000 + " jam"));
                textNonTema.setText(String.valueOf("Non tema : " + jam.getNonTema() / 3600000 + " jam"));
                textBantuTema.setText(String.valueOf("Bantu tema : " + jam.getBantuTema() / 3600000 + " jam"));
                textNonProgram.setText(String.valueOf("Non program : " + jam.getNonProgram() / 3600000 + " jam"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputIntent = new Intent(getApplicationContext(), InputActivity.class);
                startActivity(inputIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_semua) {
            allQuery.removeEventListener(eventListener);
            queryIndex = 0;
            onStart();
            return true;
        } else if (id == R.id.action_tema) {
            temaQuery.removeEventListener(eventListener);
            queryIndex = 1;
            onStart();
            return true;
        } else if (id == R.id.action_non_tema) {
            nonTemaQuery.removeEventListener(eventListener);
            queryIndex = 2;
            onStart();
            return true;
        } else if (id == R.id.action_bantu_tema) {
            bantuTemaQuery.removeEventListener(eventListener);
            queryIndex = 3;
            onStart();
            return true;
        } else if (id == R.id.action_non_program) {
            nonProgramQuery.removeEventListener(eventListener);
            queryIndex = 4;
            onStart();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ProgramAdapter extends RecyclerView.Adapter<ProgramViewHolder> {

        private ArrayList<ProgramKegiatan> programList;
        private Context context;
        private LayoutInflater inflater;

        public ProgramAdapter(ArrayList<ProgramKegiatan> programList, Context context, LayoutInflater inflater) {
            this.programList = programList;
            this.context = context;
            this.inflater = inflater;
        }

        @Override
        public ProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.item_program, parent, false);
            return new ProgramViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ProgramViewHolder holder, int position) {
            holder.setView(programList.get(position));
        }

        @Override
        public int getItemCount() {
            return programList.size();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (queryIndex) {
            case 0 :
                allQuery.addValueEventListener(eventListener);
                break;
            case 1 :
                temaQuery.addValueEventListener(eventListener);
                break;
            case 2 : nonTemaQuery.addValueEventListener(eventListener);
                break;
            case 3 : bantuTemaQuery.addValueEventListener(eventListener);
                break;
            case 4 : nonProgramQuery.addValueEventListener(eventListener);
                break;
        }
        jamRef.addValueEventListener(jamListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        switch (queryIndex) {
            case 0 :
                allQuery.removeEventListener(eventListener);
                break;
            case 1 :
                temaQuery.removeEventListener(eventListener);
                break;
            case 2 : nonTemaQuery.removeEventListener(eventListener);
                break;
            case 3 : bantuTemaQuery.removeEventListener(eventListener);
                break;
            case 4 : nonProgramQuery.removeEventListener(eventListener);
                break;
        }
        jamRef.removeEventListener(jamListener);
    }
}
