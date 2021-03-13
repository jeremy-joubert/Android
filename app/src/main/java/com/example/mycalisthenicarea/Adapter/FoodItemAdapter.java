package com.example.mycalisthenicarea.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycalisthenicarea.InfoFoodActivity;
import com.example.mycalisthenicarea.Model.FoodItem;
import com.example.mycalisthenicarea.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    private List<FoodItem> foodItemList;
    private LayoutInflater layoutInflater;
    private int layoutId;
    private Context context;

    public FoodItemAdapter(List<FoodItem> foodItemList, Context context, int layoutId) {
        this.foodItemList = foodItemList;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId=layoutId;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem currentItem=foodItemList.get(position);
        holder.getTextViewTitle().setText(currentItem.getNom());
        holder.getTextViewMarque().setText(currentItem.getMarque());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        ImageLoader.getInstance().displayImage(currentItem.getImageURL(), holder.getImageView());
        if (currentItem.getCodeBar().equals("0")){//vérifier si c est un produit ajouter ou non
            addEventLister(holder.getView(), String.valueOf(position));
        }else{
            addEventLister(holder.getView(),currentItem.getCodeBar());
        }
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewTitle;
        private final TextView textViewMarque;
        private final ImageView imageView;
        private final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.item_food_titre);
            textViewMarque=itemView.findViewById(R.id.item_food_marque);
            view = itemView;
            imageView = itemView.findViewById(R.id.item_food_icon);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewMarque() {
            return textViewMarque;
        }

        public View getView() {
            return view;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    private void addEventLister(View view, String code){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, InfoFoodActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(InfoFoodActivity.CODE_PARAMETER, code);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
