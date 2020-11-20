package com.daniil.pizza;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PizzAdapter extends RecyclerView.Adapter<PizzAdapter.CustomViewHolder> {

    private List<PizzaModel> pizzaList;

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizzacard, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        PizzaModel pModel = pizzaList.get(position);
        holder.txtPizzaName.setText(pModel.getName());
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPizzaName;
        private ImageView imgPizzaImage;
         public CustomViewHolder(View itemView) {
                super(itemView);
                txtPizzaName = itemView.findViewById(R.id.pizzaname);
                imgPizzaImage = itemView.findViewById(R.id.imageView);
        }
    }

    public PizzAdapter(List<PizzaModel> list) {
        pizzaList = list;
    }
}
