package com.rsweapon.todo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerOptions;
public class MainActivity extends AppCompatActivity {
private  ModelAdpter adpter;
private RecyclerView recyclerView;
private ProgressDialog dialog;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyc);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       getSupportActionBar().setTitle("TODO");
       recyclerView.setLayoutManager(new LinearLayoutManager(this));


            dialog = new ProgressDialog(this);
            dialog.setTitle("ToDO Store  ");
            dialog.setMessage("Please Wait ........");
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            FirebaseRecyclerOptions<Model> options =
                    new FirebaseRecyclerOptions.Builder<Model>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("todo"), Model.class)
                            .build();
            adpter=new ModelAdpter(options);
            recyclerView.setAdapter(adpter);
            adpter.notifyDataSetChanged();
            dialog.dismiss();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           OpenDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void OpenDialog() {

        Dialog dialog=new Dialog();
        dialog.show(getSupportFragmentManager(),"todo");
    }

    @Override
    protected void onStart() {
        super.onStart();
        adpter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adpter.stopListening();
    }


}