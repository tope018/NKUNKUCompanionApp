package com.example.tope0_000.nkucompanionapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ImageView curvedArrowImageView;
    private TextView mainText;
    public boolean darkTheme = false;
    private Button blackboardButton;
    private Button mynkuButton;
    private Button calendarButton;
    private Button directoryButton;

    // define the SharedPreferences object and editor
    private SharedPreferences savedValues;
    private SharedPreferences.Editor editor;

    //define instance variables that should be saved
    private boolean theme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //Get theme saved value
        darkTheme = savedValues.getBoolean("theme", false);

        //Set Theme
        if(darkTheme) {
            this.setTheme(R.style.AppTheme_dark);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        curvedArrowImageView = (ImageView) findViewById(R.id.curved_arrow_image_view);
        mainText = (TextView) findViewById(R.id.mainText);
        setSupportActionBar(toolbar);
        blackboardButton = (Button) findViewById(R.id.blackboardButton);
        mynkuButton = (Button) findViewById(R.id.mynkuButton);
        calendarButton = (Button) findViewById(R.id.calendarButton);
        directoryButton = (Button) findViewById(R.id.directoryButton);

        blackboardButton.setOnClickListener(this);
        mynkuButton.setOnClickListener(this);
        calendarButton.setOnClickListener(this);
        directoryButton.setOnClickListener(this);

        if(darkTheme) {
            blackboardButton.setBackgroundResource(R.drawable.button_dark);
            mynkuButton.setBackgroundResource(R.drawable.button_dark);
            calendarButton.setBackgroundResource(R.drawable.button_dark);
            directoryButton.setBackgroundResource(R.drawable.button_dark);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        curvedArrowImageView.setImageResource(R.drawable.curved_arrow);
        curvedArrowImageView.setRotationX(180);
        curvedArrowImageView.setRotationY(180);
        curvedArrowImageView.setScaleY(0.8f);
        curvedArrowImageView.setScaleX(0.9f);
        if(darkTheme)
            curvedArrowImageView.setImageResource(R.drawable.curved_arrow_yellow);

        mainText.setMovementMethod(LinkMovementMethod.getInstance());
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
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        editor = savedValues.edit();

        if (id == R.id.nav_home) {
            // Handle the home action
        } else if (id == R.id.nav_calculator) {
            startActivity(new Intent(this, GpaCalculator.class));
            return true;
        } else if (id == R.id.nav_map) {
            startActivity(new Intent(this, MapsActivity.class));
            return true;
        } else if (id == R.id.nav_schedule) {
            startActivity(new Intent(this, ScheduleActivity.class));
            return true;

        } else if (id == R.id.nav_dark_theme) {
            darkTheme = true;
            editor.putBoolean("theme", darkTheme);
            editor.commit();
            recreate();
            return true;

        } else if (id == R.id.nav_light_theme) {
            darkTheme = false;
            editor.putBoolean("theme", darkTheme);
            editor.commit();
            recreate();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPause() {
        editor = savedValues.edit();
        editor.putBoolean("theme", darkTheme);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        darkTheme = savedValues.getBoolean("theme", false);
    }

    @Override
    public void onClick(View v) {
        Uri uri;
        Intent intent;
        switch(v.getId()) {
            case R.id.blackboardButton:
                uri = Uri.parse("https://learnonline.nku.edu/webapps/portal/execute/tabs/tabAction?tab_tab_group_id=_16_1"); // missing 'http://' will cause crashed
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.mynkuButton:
                uri = Uri.parse("https://mynku.nku.edu"); // missing 'http://' will cause crashed
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.calendarButton:
                uri = Uri.parse("http://www.nku.edu/calendars.html"); // missing 'http://' will cause crashed
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.directoryButton:
                uri = Uri.parse("http://directory.nku.edu/"); // missing 'http://' will cause crashed
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }
}
