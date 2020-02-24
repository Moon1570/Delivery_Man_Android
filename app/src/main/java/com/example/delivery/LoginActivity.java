package com.example.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivery.Config.IpConfig;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MenuItem menuItem1;
    Menu menu;
    public static final String mypreference = "mypref";
    public static final String login = "logout";
    public static String delId = "null";
    String check;

    TextView phoneTxt, passTxt;
    Button loginBtn;
    RequestParams params;
    AsyncHttpClient client;
    IpConfig ipConfig = new IpConfig();;
    String MYURL = ipConfig.myURI + "restdelivery";
    JSONArray jsonArray;
    String phone, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if (sharedPreferences.getString("login", null) == null) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.foreground));

            }


            phoneTxt = findViewById(R.id.login_phone);
            passTxt = findViewById(R.id.login_password);
            loginBtn = findViewById(R.id.btn_login);

            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            navigationView.setItemIconTintList(null);


            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();


            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    phone = phoneTxt.getText().toString();
                    pass = passTxt.getText().toString();

                    params = new RequestParams();
                    params.put("phone", phone);
                    params.put("pass", pass);
                    params.put("action", "deliLogin");

                    client = new AsyncHttpClient();

                    client.post(MYURL, params, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);


                            try {
                                Toast.makeText(LoginActivity.this, "Welcome " + response.getJSONObject(0).getString("delName"), Toast.LENGTH_SHORT).show();

                                delId = response.getJSONObject(0).toString();
                                editor.putString("login", "login");
                                editor.putString("delId", delId);
                                editor.commit();


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent1;
                if (menuItem.getTitle().equals("All Orders")) {
                    intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                }
                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
