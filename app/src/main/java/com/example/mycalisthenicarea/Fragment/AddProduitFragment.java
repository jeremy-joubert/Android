package com.example.mycalisthenicarea.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mycalisthenicarea.Model.FoodItem;
import com.example.mycalisthenicarea.R;
import com.example.mycalisthenicarea.bdd.ListDeProduits;

public class AddProduitFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.fragment_add_produit, container, false);
        initSubmitButton();
        return view;
    }

    private void initSubmitButton(){
        Button button=view.findViewById(R.id.submit_button_form);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fermerClavier();
                creerNouveauProduit();
                revenirSurListFragment();
            }
        });
    }

    private void creerNouveauProduit(){
        EditText editTextTitre=view.findViewById(R.id.form_titre);
        String title=editTextTitre.getText().toString();
        EditText editTextMarque=view.findViewById(R.id.form_marque);
        String marque=editTextMarque.getText().toString();
        EditText editTextPoids=view.findViewById(R.id.form_poids);
        String poids=editTextPoids.getText().toString();
        FoodItem foodItem=new FoodItem("0", title, marque, poids, "0");
        ListDeProduits.getInstance().add(foodItem);
    }

    private void revenirSurListFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("urlEnvoye", "url");
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment fragment=new ListFragment();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void fermerClavier(){
        /*final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(view.getWindowToken(), 0);*/

    }
}
