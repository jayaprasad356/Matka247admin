package com.example.bigbillionadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            }

            case R.id.deposit_req: {

               // Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, Deposit_requestActivity.class);
                startActivity(i);
                break;
            }
            case R.id.withdrawal: {

                //Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, WithdrawalActivity.class);
                startActivity(i);
                break;
            }
            case R.id.declare_result: {

               // Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, Result_viewActivity.class);
                startActivity(i);
                break;
            }
            case R.id.show_biddings: {

             //   Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

               Intent i = new Intent(MainActivity.this, Show_biddinsActivity.class);
               startActivity(i);
                break;
            }
            case R.id.sharing_point: {

             //   Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, Sharing_pointsActivity.class);
                startActivity(i);
                break;
            }
            case R.id.winners: {

               // Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, WinnersActivity.class);
                startActivity(i);
                break;
            }
            case R.id.users: {

               // Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, UsersActivity.class);
                startActivity(i);
                break;
            }
            case R.id.settings: {

              //  Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                break;
            }


        }

        return false;
    }
}