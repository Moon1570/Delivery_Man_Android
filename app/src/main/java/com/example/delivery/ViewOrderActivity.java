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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivery.Config.IpConfig;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewOrderActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MenuItem menuItem1;
    public static final String mypreference = "mypref";

    String check, order;

    Menu menu;

    TextView idTxt, cocTxt, buyerNameTxt, exDateTxt, quantityTxt, divTxt, disTxt, UpaTxt, UniTxt, VillageTxt, StreetTxt, orderDateTxt, productTxt, phoneTxt;
    Button markAsCompleteBtn, markAsConfirmBtn, cancelBtn;
    String oid;

    JSONArray jsonArray;
    JSONArray jsonArray2;
    JSONObject jsonObject;
    OrderAdapter orderAdapter;
    ListView listView;
    RequestParams params;
    AsyncHttpClient client;
    IpConfig ipConfig = new IpConfig();
    String MYURL = ipConfig.myURI + "restdelivery";
    Context context;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        idTxt = findViewById(R.id.order_id);
        cocTxt = findViewById(R.id.care_of_contact);
        buyerNameTxt = findViewById(R.id.buyer_name);
        exDateTxt = findViewById(R.id.expected_date);
        quantityTxt = findViewById(R.id.quantity);
        divTxt = findViewById(R.id.division);
        disTxt = findViewById(R.id.district);
        UpaTxt = findViewById(R.id.upazilla);
        UniTxt = findViewById(R.id.union);
        VillageTxt = findViewById(R.id.village);
        StreetTxt = findViewById(R.id.street);
        orderDateTxt = findViewById(R.id.ordering_date);
        productTxt = findViewById(R.id.product);
        phoneTxt = findViewById(R.id.phone);

        markAsCompleteBtn = findViewById(R.id.btn_mark_as_complete);
        markAsConfirmBtn = findViewById(R.id.btn_mark_as_confirm);
        cancelBtn = findViewById(R.id.btn_cancel);



        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("login", null) == null)
        {
            Intent intent = new Intent(ViewOrderActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {

            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);



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


            final Intent intent = getIntent();
            order = intent.getExtras().getString("order");

            try {
                JSONObject jsonObject = new JSONObject(order);

                oid = jsonObject.getString("orderId");

                idTxt.setText("Order Id : "+jsonObject.getString("orderId"));
                cocTxt.setText("Receiver Name : "+jsonObject.getString("name"));
                buyerNameTxt.setText("Buyer Name : "+jsonObject.getString("customerName"));
                exDateTxt.setText("Expected Date : "+jsonObject.getString("date"));
                quantityTxt.setText("Quantity : "+jsonObject.getString("quantity"));
                divTxt.setText("Division : "+jsonObject.getString("division"));
                disTxt.setText("District : "+jsonObject.getString("district"));
                UpaTxt.setText("Upazilla : "+jsonObject.getString("upazilla"));
                UniTxt.setText("Union : "+jsonObject.getString("union"));
                VillageTxt.setText("Village : "+jsonObject.getString("orderVillage"));
                StreetTxt.setText("Street : "+jsonObject.getString("orderSteet"));
                orderDateTxt.setText("Ordering Date : "+jsonObject.getString("orderingDate"));
                phoneTxt.setText("Phone : "+jsonObject.getString("orderPhone"));
                productTxt.setText("Product : "+jsonObject.getString("productName"));

                String status = jsonObject.getString("status");
                if (status.equalsIgnoreCase("Completed")){
                    markAsCompleteBtn.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "This order is already completed", Toast.LENGTH_SHORT).show();
                }



                markAsCompleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        markAsConfirmBtn.setVisibility(View.VISIBLE);
                        markAsCompleteBtn.setVisibility(View.INVISIBLE);
                        cancelBtn.setVisibility(View.VISIBLE);
                    }
                });

                markAsConfirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        params = new RequestParams();
                        params.put("orderId", oid);
                        params.put("action", "markAsComplete");

                        client = new AsyncHttpClient();

                        client.get(MYURL, params, new JsonHttpResponseHandler()
                        {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                super.onSuccess(statusCode, headers, response);
                                Toast.makeText(ViewOrderActivity.this, "Moved TO completed list", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(ViewOrderActivity.this, MainActivity.class);
                                startActivity(intent1);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                Toast.makeText(ViewOrderActivity.this, "Problem with Movig the Order", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        markAsConfirmBtn.setVisibility(View.INVISIBLE);
                        markAsCompleteBtn.setVisibility(View.VISIBLE);
                        cancelBtn.setVisibility(View.INVISIBLE);
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }




            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Intent intent1;
                    if (menuItem.getTitle().equals("New Orders")) {
                        intent1 = new Intent(ViewOrderActivity.this, MainActivity.class);
                        startActivity(intent1);
                    }
                    else if (menuItem.getTitle().equals("Logout")) {
                        intent1 = new Intent(ViewOrderActivity.this, LoginActivity.class);
                        editor.clear();
                        editor.commit();
                        startActivity(intent1);
                    }
                    return true;
                }
            });

        }

    }

    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
