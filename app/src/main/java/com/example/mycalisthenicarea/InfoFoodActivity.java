package com.example.mycalisthenicarea;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycalisthenicarea.Model.FoodItem;
import com.example.mycalisthenicarea.bdd.ListDeProduits;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoFoodActivity extends AppCompatActivity {

    public static final String CODE_PARAMETER="code";
    private static final String TAG =InfoFoodActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_food);

        initBottomMenu();

        String code=getIntent().getExtras().getString(CODE_PARAMETER);
        try {
            Integer.parseInt(code);
            afficherProduitAjouter(code);
        }catch (NumberFormatException e){
            creerUneStringRequest(code);
        }
    }

    private void initBottomMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intentHome = new Intent(InfoFoodActivity.this, MainActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.nav_search:
                        Intent intentSearch = new Intent(InfoFoodActivity.this, SecondActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_settings:
                        break;
                }
                return false;
            }
        });
    }

    private void creerUneStringRequest(String code){
        RequestQueue queue = Volley.newRequestQueue(this);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String url="https://fr.openfoodfacts.org/api/v0/product/"+code+".json";
            Log.d(TAG, "URL is " + url);
            StringRequest stringRequest=new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseJSON = new JSONObject(response);
                                JSONObject foodJSON=new JSONObject(responseJSON.getString("product"));
                                FoodItem item = new FoodItem(code,foodJSON.getString("product_name_fr"), foodJSON.getString("brands"), foodJSON.getString("quantity"), foodJSON.getString("image_url"));
                                afficherProduit(item);
                            } catch (JSONException e) {
                                Log.e(TAG, "Error while parsing foods result", e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Erreur de requête", Toast.LENGTH_LONG);
                            toast.show();
                            Log.e(TAG, "Erreur de requete");
                        }
                    }
            );
            queue.add(stringRequest);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Non connecté à internet", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }

    private void afficherProduitAjouter(String code){
        int position=Integer.parseInt(code);
        FoodItem item = ListDeProduits.getInstance().get(position);
        afficherProduit(item);

    }

    private void afficherProduit(FoodItem item){
        TextView nomView=findViewById(R.id.nom_food_view);
        TextView marqueView=findViewById(R.id.marque_food_view);
        TextView poidView=findViewById(R.id.poid_food_view);
        ImageView imageView=findViewById(R.id.food_imageView);
        nomView.setText(item.getNom());
        marqueView.setText(item.getMarque());
        poidView.setText(item.getPoids());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(InfoFoodActivity.this));
        ImageLoader.getInstance().displayImage(item.getImageURL(), imageView);
    }
}
