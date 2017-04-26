package com.aishindus.factory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MyAccount extends AppCompatActivity {
    Toolbar mToolbar;
    SessionManager session;
    EditText name,password,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        session = new SessionManager(getApplicationContext());
        name = (EditText) findViewById(R.id.print_name);
        mobile = (EditText) findViewById(R.id.print_mobile);
        password = (EditText) findViewById(R.id.print_password);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_my_account);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        disableEditText(name);
        disableEditText(mobile);
        disableEditText(password);

        HashMap<String, String> user = session.getUserDetails();
        name.setText(user.get(session.KEY_NAME));
        mobile.setText(user.get(session.KEY_MOB));
        password.setText(user.get(session.KEY_PASS));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_account_action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(MyAccount.this,"hello",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }
}
