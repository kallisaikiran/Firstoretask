package com.firestoresample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class FireStoreHomeActivity extends AppCompatActivity {

    Button btnMyprofile;
    TextView tvFirestoreWC;

    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firestore_dashboard);

        getSupportActionBar().setTitle("Dashboard");
        navigationView();

        tvFirestoreWC = (TextView) findViewById(R.id.tvFirestoreWC);
        tvFirestoreWC.setText(getIntent().getStringExtra("message"));
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {
                Fragment fragment = null;
                if (view == findViewById(R.id.btnMyProfile)) {
                    fragment = new FirestoreProfileFragment();
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.commit();
            }
        };

        Button btnMyProfile = (Button) findViewById(R.id.btnMyProfile);
        btnMyProfile.setOnClickListener(listener);


    }

    private void navigationView() {
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.dashboard:
                        Intent intent = new Intent(getApplicationContext(), FireStoreHomeActivity.class);
                        intent.putExtra("message",getIntent().getStringExtra("message"));
                        startActivity(intent);
                        break;


                    case R.id.logout:
                        Intent logout = new Intent(getApplicationContext(), FirestoreMainActivity.class);
                        startActivity(logout);
                        finish();
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {

            diolougebox();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void diolougebox() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(FireStoreHomeActivity.this);
        builder1.setMessage("Do you want to close the Application.");  //message we want to show the end user
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); //cancle the alert dialog box
                        finish();//finish the process
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}