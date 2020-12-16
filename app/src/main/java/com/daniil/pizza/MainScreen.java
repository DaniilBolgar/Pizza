package com.daniil.pizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private PizzAdapter pizzAdapter;
    private TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {                                        //find 3 images for pizzas
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        recyclerView = findViewById(R.id.recyclerView);
        welcomeMsg = findViewById(R.id.welcomeMsg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        firebaseDatabase = FirebaseDatabase.getInstance();
        setDate();
        databaseReference = firebaseDatabase.getReference("pizzalist");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<PizzaModel> list;
                list = new ArrayList<PizzaModel>();
                if(dataSnapshot.exists()){
                    for(DataSnapshot nSnapshot:dataSnapshot.getChildren()){
                        PizzaModel pizzaModel = nSnapshot.getValue(PizzaModel.class);
                        list.add(pizzaModel);
                    }
                    Log.d("List",list.get(0).getName());
                    pizzAdapter = new PizzAdapter(list, getApplicationContext());
                    recyclerView.setAdapter(pizzAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay >= 0 && timeOfDay < 12){

            welcomeMsg.setText("Good Morning! Welcome to Pizzapp!");
            Toast.makeText(getApplicationContext(), "test",Toast.LENGTH_SHORT).show();

        }else if(timeOfDay >= 12 && timeOfDay < 16){

            welcomeMsg.setText("Good Afternoon! Welcome to Pizzapp!");
            Toast.makeText(getApplicationContext(), "test",Toast.LENGTH_SHORT).show();

        }else if(timeOfDay >= 16 && timeOfDay < 21){

            welcomeMsg.setText("Evening! Welcome to Pizzapp!");
            Toast.makeText(getApplicationContext(), "test",Toast.LENGTH_SHORT).show();

        }else if(timeOfDay >= 21 && timeOfDay < 24)
            welcomeMsg.setText("Good Evening! Welcome to Pizzapp!");
            Toast.makeText(getApplicationContext(), "test",Toast.LENGTH_SHORT).show();
    }
}