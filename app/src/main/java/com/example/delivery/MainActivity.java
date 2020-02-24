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
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.delivery.Config.IpConfig;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MenuItem menuItem1;
    public static final String mypreference = "mypref";

    String check;

    Menu menu;

    List<JSONObject> list = new ArrayList<JSONObject>();

    Spinner filter;
    JSONArray jsonArray;
    JSONArray jsonArray2;
    JSONObject jsonObject;
    OrderAdapter orderAdapter;
    ListView listView;
    RequestParams params;
    AsyncHttpClient client;
    IpConfig ipConfig = new IpConfig();
    String MYURL = ipConfig.myURI +"restdelivery";
    Context context;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("login", null) == null)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {

            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            listView = findViewById(R.id.main_listview);
            filter = findViewById(R.id.filter_dropdown);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            navigationView.setItemIconTintList(null);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.foreground));

            }

            menu = navigationView.getMenu();
            menuItem1 = menu.findItem(R.id.nav_login);
            if (sharedPreferences.getString("login", null) ==null)
            {
                ///Do Nothing
            }
            else {
                menuItem1.setTitle("Logout");
            }



            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();



            context = getApplicationContext();

            params = new RequestParams();
            params.put("action", "getorders");

            client = new AsyncHttpClient();

            String del = sharedPreferences.getString("delId", null);

            try {
                JSONObject jsonObject = new JSONObject(del);

                int delId = Integer.parseInt(jsonObject.getString("delId"));
                params.put("delId", delId);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            client.get(MYURL, params, new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                    super.onSuccess(statusCode, headers, response);

                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                            R.array.status, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    filter.setAdapter(adapter);

                    try {
                        jsonArray = response;
                        orderAdapter = new OrderAdapter(context, R.layout.order_row_layout);

                        orderAdapter.remove();

                        listView.setAdapter(orderAdapter);

                        int count = 0;
                        String name, date, orderId;



                        while (count<jsonArray.length())
                        {

                            JSONObject JO = jsonArray.getJSONObject(count);
                            name = JO.getString("name");
                            orderId = JO.getString("orderId");
                            date = JO.getString("status");

                            OrderModel orderModel = new OrderModel(name, orderId, date);

                            orderAdapter.add(orderModel);

                            count++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0){
                                try {
                                    orderAdapter = new OrderAdapter(context, R.layout.order_row_layout);

                                    orderAdapter.remove();

                                    listView.setAdapter(orderAdapter);

                                    int count = 0;
                                    String name, date, orderId;


                                    list.clear();
                                    while (count<jsonArray.length())
                                    {

                                        JSONObject JO = jsonArray.getJSONObject(count);
                                        name = JO.getString("name");
                                        orderId = JO.getString("orderId");
                                        date = JO.getString("status");

                                        OrderModel orderModel = new OrderModel(name, orderId, date);
                                        list.add(JO);
                                        orderAdapter.add(orderModel);

                                        count++;
                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (position == 1){
                                try {
                                    orderAdapter = new OrderAdapter(context, R.layout.order_row_layout);

                                    orderAdapter.remove();

                                    listView.setAdapter(orderAdapter);

                                    int count = 0;
                                    String name, date, orderId;

                                    list.clear();

                                    while (count<jsonArray.length())
                                    {

                                        JSONObject JO = jsonArray.getJSONObject(count);
                                        name = JO.getString("name");
                                        orderId = JO.getString("orderId");
                                        date = JO.getString("status");

                                        if (date.equalsIgnoreCase("allocated")){
                                            OrderModel orderModel = new OrderModel(name, orderId, date);
                                            list.add(JO);
                                            orderAdapter.add(orderModel);
                                        }
                                        count++;
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (position == 2){
                                try {
                                    orderAdapter = new OrderAdapter(context, R.layout.order_row_layout);

                                    orderAdapter.remove();

                                    listView.setAdapter(orderAdapter);

                                    int count = 0;
                                    String name, date, orderId;

                                    list.clear();

                                    while (count<jsonArray.length())
                                    {

                                        JSONObject JO = jsonArray.getJSONObject(count);
                                        name = JO.getString("name");
                                        orderId = JO.getString("orderId");
                                        date = JO.getString("status");

                                        if (date.equalsIgnoreCase("Processing")){
                                            OrderModel orderModel = new OrderModel(name, orderId, date);
                                            list.add(JO);
                                            orderAdapter.add(orderModel);
                                        }
                                        count++;
                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if (position == 3){
                                try {
                                    orderAdapter = new OrderAdapter(context, R.layout.order_row_layout);

                                    orderAdapter.remove();

                                    listView.setAdapter(orderAdapter);

                                    int count = 0;
                                    String name, date, orderId;

                                    list.clear();

                                    while (count<jsonArray.length())
                                    {

                                        JSONObject JO = jsonArray.getJSONObject(count);
                                        name = JO.getString("name");
                                        orderId = JO.getString("orderId");
                                        date = JO.getString("status");

                                        if (date.equalsIgnoreCase("Cancelled")){
                                            OrderModel orderModel = new OrderModel(name, orderId, date);
                                            list.add(JO);
                                            orderAdapter.add(orderModel);
                                        }
                                        count++;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else if (position == 4){
                                try {
                                    orderAdapter = new OrderAdapter(context, R.layout.order_row_layout);

                                    orderAdapter.remove();

                                    listView.setAdapter(orderAdapter);

                                    int count = 0;
                                    String name, date, orderId;

                                    list.clear();

                                    while (count<jsonArray.length())
                                    {

                                        JSONObject JO = jsonArray.getJSONObject(count);
                                        name = JO.getString("name");
                                        orderId = JO.getString("orderId");
                                        date = JO.getString("status");

                                        if (date.equalsIgnoreCase("Completed")){
                                            OrderModel orderModel = new OrderModel(name, orderId, date);
                                            list.add(JO);
                                            orderAdapter.add(orderModel);
                                        }
                                        count++;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Toast.makeText(MainActivity.this, "Failed to get Orders", Toast.LENGTH_SHORT).show();
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ViewOrderActivity.class);
                    intent.putExtra("position", position);
                    String orderDetails = list.get(position).toString();
                    intent.putExtra("order", orderDetails);

                    startActivity(intent);
                }
            });
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Intent intent1;
                    if (menuItem.getTitle().equals("All Orders")) {
                        intent1 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent1);
                    }
                    else if (menuItem.getTitle().equals("Logout")) {
                        intent1 = new Intent(MainActivity.this, LoginActivity.class);
                        editor.clear();
                        editor.commit();
                        startActivity(intent1);
                    }

                    return true;
                }
            });
        }









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
