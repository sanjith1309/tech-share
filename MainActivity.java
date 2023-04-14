package com.sanjith.techshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databasereference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tech-share-5f5e9-default-rtdb.firebaseio.com/");
    private TextView topicview,dataview;
    EditText content;
    Button post,refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topicview=findViewById(R.id.textView2);
        post=findViewById(R.id.button);
        content=findViewById(R.id.editTextTextPersonName);
        dataview=findViewById(R.id.textView3);
        refresh=findViewById(R.id.button2);
        getdata();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databasereference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String qwerty=snapshot.getValue(String.class);
                        dataview.setText(qwerty);
                        Toast.makeText(MainActivity.this, "REFRESH", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content1=content.getText().toString().trim();
                if(content1.isEmpty()){
                    content.setError("type something");
                    content.requestFocus();
                    return;
                }
                else if(content1.length()<20){
                    content.setError("length of the data must  be greater than 20");
                    content.requestFocus();
                    return;}
                else{
                    databasereference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String data=snapshot.getValue(String.class);
                            String data1=" "+data+content1+" \n-------------------\n\n";
                            dataview.setText(data1);
                            content.setText("");
                            databasereference.child("data").setValue(data1);
                            Toast.makeText(MainActivity.this, "posted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private void getdata() {
        databasereference.child("topic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String datatopic= snapshot.getValue(String.class);
                topicview.setText(datatopic);
                Toast.makeText(MainActivity.this, "topic:"+datatopic, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databasereference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String abc=snapshot.getValue(String.class);
                dataview.setText(abc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
