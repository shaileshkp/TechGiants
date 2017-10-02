package com.example.examinationsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examinationsystem.R;
import com.example.examinationsystem.constants.User;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    TextView nav_user_name,nav_user_phone;
    ImageView nav_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        nav_user_name = (TextView) header.findViewById(R.id.nav_user_name);
        nav_user_phone = (TextView) header.findViewById(R.id.nav_user_phone);
        nav_imageView = (ImageView) header.findViewById(R.id.nav_imageView);

        nav_user_name.setText(User.userName);
        nav_user_phone.setText(User.userId);
        Picasso.with(getApplicationContext()).load(User.imageUrl).into(nav_imageView);

        PostsFragment postsFragment = new PostsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.nav_main_fragment_container,
                postsFragment,
                postsFragment.getTag()
        ).commit();

//        MenuItem menuItem = (MenuItem)navigationView.findViewById(R.id.action_profile);
//        Picasso.with(getApplicationContext()).load(User.imageUrl).into((ImageView) menuItem);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            PostsFragment postsFragment = new PostsFragment();
            toolbar.setTitle("Home");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.nav_main_fragment_container,
                    postsFragment,
                    postsFragment.getTag()
            ).commit();
        }
        else if(id == R.id.nav_exams) {
            Intent intent = new Intent(MainActivity.this, Exams.class);
            startActivity(intent);
        }
        else if(id== R.id.nav_rating) {
            Rating rating= new Rating();
            toolbar.setTitle("Rating");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.nav_main_fragment_container,
                    rating,
                    rating.getTag()
            ).commit();
        } else if(id== R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}