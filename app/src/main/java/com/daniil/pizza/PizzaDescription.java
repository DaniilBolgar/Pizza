package com.daniil.pizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PizzaDescription extends AppCompatActivity {

    private ImageView pizzaImage;
    private TextView pizzaName;
    private TextView pizzaDescription;
    private TextView price;
    private TextView size;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_description);
        Bundle bundle = getIntent().getExtras();
        String pizzaId = bundle.get("pizzalist").toString();
        Log.d("test", pizzaId);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        pizzaImage = findViewById(R.id.pizzaImage);
        pizzaName = findViewById(R.id.pizzaName);
        price = findViewById(R.id.price);
        size = findViewById(R.id.size);
        pizzaDescription = findViewById(R.id.pizzaDescription);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("pizzalist").child(pizzaId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PizzaModel pModel = dataSnapshot.getValue(PizzaModel.class);
                Glide.with(getApplicationContext()).load(pModel.getImageUrl()).centerCrop().into(pizzaImage);
                pizzaName.setText(pModel.getName());
                pizzaDescription.setText(pModel.getDescription());
                price.setText("Price: " + pModel.getPrice().toString());
                size.setText(pModel.getSize());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}