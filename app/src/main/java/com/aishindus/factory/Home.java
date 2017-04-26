package com.aishindus.factory;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GridView gridView;
    String m_Text = "Hello";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private SessionManager session;

    static final String[] TITLE = new String[]{"Daily Report", "Search", "Search By Date", "About Us!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session = new SessionManager(getApplicationContext());

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(Home.this, TITLE));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final Intent i = new Intent(Home.this, Report.class);
                i.putExtra("position", position);

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Home.this);
                final View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);

                if (position == 0)
                    startActivity(i);
                else if (position == 1) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setView(mView);
                    final EditText input = (EditText) mView.findViewById(R.id.userInputDialog);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Boolean wantToCloseDialog = true;
                            m_Text = input.getText().toString();
                            if (m_Text.matches("")) {
                                Log.e("hi", "hello");
                                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    input.getBackground().mutate().setColorFilter(getResources().getColor(R.color.error_col, Home.this.getTheme()), PorterDuff.Mode.SRC_ATOP);
                                } else
                                    input.getBackground().mutate().setColorFilter(getResources().getColor(R.color.error_col), PorterDuff.Mode.SRC_ATOP);
                                input.startAnimation(shake);
                                wantToCloseDialog = false;
                            } else {
                                i.putExtra("element", m_Text);
                                startActivity(i);
                            }
                            if (wantToCloseDialog)
                                dialog.dismiss();
                        }
                    });

                } else if (position == 2) {
                    final Calendar myCalendar = Calendar.getInstance();
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            i.putExtra("element", sdf.format(myCalendar.getTime()));
                            startActivity(i);
                        }
                    };
                    new DatePickerDialog(Home.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userName = (TextView) headerView.findViewById(R.id.user_name);
        userName.setText("Hello " + session.getUserDetails().get(session.KEY_NAME));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            Intent i = new Intent(Home.this, MyAccount.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            session.logoutUser();
            finish();
        }
        return false;
    }
}
