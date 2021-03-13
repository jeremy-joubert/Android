package com.example.mycalisthenicarea;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycalisthenicarea.Fragment.AddProduitFragment;
import com.example.mycalisthenicarea.Fragment.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SecondActivity extends AppCompatActivity {

    public static final String INPUT_PARAMETER="input";

    private static final String TAG = SecondActivity.class.getSimpleName();

    public static final int REQUEST_OK=0;
    public static final int REQUEST_KO=1;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initBottomMenu();
        intFragmentSearch("urlDeDepart");
        initTopMenu();
        initSearchBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(SecondActivity.this, InfoFoodActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(InfoFoodActivity.CODE_PARAMETER, result.getContents());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
       // setResult(REQUEST_KO);
       // finish();
    }

    private void faireUneFragmentTransaction(FragmentTransaction fragmentTransaction, Fragment fragment){
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initBottomMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_search:
                        recreate();
                        break;
                    case R.id.nav_settings:
                        break;
                }
                return false;
            }
        });

    }

    private void initTopMenu(){
        //bottomNavigationView
        TabLayout tabLayout=findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Fragment fragment=null;
                switch (tab.getPosition()){
                    case 0:
                        fragment=new ListFragment();
                        faireUneFragmentTransaction(fragmentTransaction,fragment);
                        break;
                    case 1:
                        IntentIntegrator integrator=new IntentIntegrator(SecondActivity.this);
                        integrator.setCaptureActivity(CaptureActivity.class);
                        integrator.setOrientationLocked(false);
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                        integrator.setPrompt("Scan a barcode");
                        integrator.initiateScan();
                        break;
                    case 2:
                        fragment=new AddProduitFragment();
                        faireUneFragmentTransaction(fragmentTransaction,fragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void intFragmentSearch(String url){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("urlEnvoye", url);
        Fragment fragment=new ListFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initSearchBar(){
        EditText searchBar=findViewById(R.id.menu_search_bar);
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String url=getUrlBonFormat(searchBar.getText().toString());
                    Log.d(TAG, "URL is " + url);
                    fermerClavier();
                    intFragmentSearch(url);
                    return true;
                }
                return false;
            }
        });
    }

    private String getUrlBonFormat(String param){
        String url;
        url=param.trim().replaceAll(" +", "-");
        return "https://fr.openfoodfacts.org/categorie/"+url+".json";
    }

    private void fermerClavier(){
       // final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromInputMethod(getWindowToken(), 0);

    }

}
