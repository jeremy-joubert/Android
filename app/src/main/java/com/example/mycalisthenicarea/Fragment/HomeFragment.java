package com.example.mycalisthenicarea.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mycalisthenicarea.MainActivity;
import com.example.mycalisthenicarea.R;
import com.example.mycalisthenicarea.SecondActivity;

import java.util.zip.Inflater;

public class HomeFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home, container, false);
        CardView cardViewFood=view.findViewById(R.id.cardView_food);
        cardViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SecondActivity.INPUT_PARAMETER, "au cas ou");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        intOuvertureFragmentListener();
        return view;
    }

    private void intOuvertureFragmentListener(){
        CardView cardViewForum=view.findViewById(R.id.cardView_forum);
        CardView cardViewGoals=view.findViewById(R.id.cardView_goals);
        CardView cardViewNewsPaper=view.findViewById(R.id.cardView_newsPaper);
        CardView cardViewSettings=view.findViewById(R.id.cardView_settings);
        CardView cardViewTraining=view.findViewById(R.id.cardView_training);
        addEventListener(cardViewForum, "Forum");
        addEventListener(cardViewGoals, "Goals");
        addEventListener(cardViewNewsPaper, "NewsPaper");
        addEventListener(cardViewSettings, "Settings");
        addEventListener(cardViewTraining, "Training");
    }

    private void addEventListener(CardView cardView, String message){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("message", message);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Fragment fragment=new PageFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
