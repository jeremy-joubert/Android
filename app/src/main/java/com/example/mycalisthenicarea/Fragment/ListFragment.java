package com.example.mycalisthenicarea.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycalisthenicarea.Adapter.FoodItemAdapter;
import com.example.mycalisthenicarea.Model.FoodItem;
import com.example.mycalisthenicarea.R;
import com.example.mycalisthenicarea.SecondActivity;
import com.example.mycalisthenicarea.bdd.ListDeProduits;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private String url;

    private static final String TAG = ListFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_list, container, false);

        url=this.getArguments().getString("urlEnvoye");

        creerUneStringRequest(view);

        return view;
    }

    private void creerUneStringRequest(View view){
        List<FoodItem> foodItemList=new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            Log.d(TAG, "URL is " + url);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject responseJSON=new JSONObject(response);
                                JSONArray listFood=new JSONArray(responseJSON.getString("products"));
                                JSONObject foodItem;
                                for(int i=0; i<listFood.length();i++){
                                    foodItem=listFood.getJSONObject(i);
                                    try {
                                        foodItemList.add(new FoodItem(foodItem.getString("code"),foodItem.getString("product_name_fr"), foodItem.getString("brands"), foodItem.getString("quantity"), foodItem.getString("image_small_url")));

                                    }catch (Exception e){}
                                }
                                RecyclerView listViewFood=view.findViewById(R.id.list_horizontal_food_view);
                                listViewFood.setAdapter(new FoodItemAdapter(foodItemList, view.getContext(), R.layout.adapter_horizontal_item_food));
                            }catch (JSONException e){
                                Log.e(TAG, "Error while parsing foods result", e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            RecyclerView listViewFood=view.findViewById(R.id.list_horizontal_food_view);
                            listViewFood.setAdapter(new FoodItemAdapter(ListDeProduits.getInstance(), view.getContext(), R.layout.adapter_horizontal_item_food));
                        }
                    }
            );
            queue.add(stringRequest);
        }else {
            Toast toast = Toast.makeText(view.getContext(), "Non connecté à internet", Toast.LENGTH_LONG);
            toast.show();
            getActivity().finish();
        }
    }
}
