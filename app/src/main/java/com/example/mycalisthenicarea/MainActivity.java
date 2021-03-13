package com.example.mycalisthenicarea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycalisthenicarea.Fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import static com.google.android.material.bottomnavigation.BottomNavigationView.*;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SECOND_ACTIVITY=1;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomMenu();
        initFragmentHome();

        /*ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(configuration);

        EditText editTextLogin= (EditText) findViewById(R.id.editTextTextPersonName);
        EditText editTextPassWord=(EditText) findViewById(R.id.editTextTextPassword);
        Button button=(Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SecondActivity.INPUT_PARAMETER, editTextLogin.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button buttonScan=findViewById(R.id.button_scan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator=new IntentIntegrator(MainActivity.this);
                integrator.setCaptureActivity(CaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.initiateScan();
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this, InfoFoodActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(InfoFoodActivity.CODE_PARAMETER, result.getContents());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        /*super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==SecondActivity.REQUEST_OK){
                Toast toast=Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG);
                toast.show();
            }else{
                Toast toast=Toast.makeText(getApplicationContext(), "KO", Toast.LENGTH_LONG);
                toast.show();
            }
        }*/
    }

    private void initBottomMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        recreate();
                        break;
                    case R.id.nav_search:
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_settings:
                        break;
                }
                return false;
            }
        });
    }

    private void initFragmentHome(){
        //injecter le fragment dans container_fragment
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment fragment=new HomeFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}