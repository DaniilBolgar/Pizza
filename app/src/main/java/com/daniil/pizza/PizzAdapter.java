package com.daniil.pizza;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PizzAdapter extends RecyclerView.Adapter<PizzAdapter.CustomViewHolder> {

    private List<PizzaModel> pizzaList;
    private Context context;

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizzacard, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        PizzaModel pModel = pizzaList.get(position);
        holder.txtPizzaName.setText(pModel.getName());
        Glide.with(context).load(pModel.getImageUrl()).apply(new RequestOptions().override(250, 250)).into(holder.imgPizzaImage);
        holder.imgPizzaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PizzaDescription.class);
                intent.putExtra("pizzalist",position + 1);
                view.getContext().startActivity(intent);
            }
        });
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

    public PizzAdapter(List<PizzaModel> list, Context applicationContext) {
        pizzaList = list;
        context = applicationContext;
    }

}
