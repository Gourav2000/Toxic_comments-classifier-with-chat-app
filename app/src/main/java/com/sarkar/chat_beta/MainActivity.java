package com.sarkar.chat_beta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ImageView send_btn;
    EditText editText;
    String name;
    DatabaseReference databaseReference;
    ArrayList<String> idList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.lv);
        send_btn=findViewById(R.id.send_btn);
        editText=findViewById(R.id.et);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        name= extras.getString("name");

        final ArrayList<String> arrayList=new ArrayList();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(!snapshot.child("corrected").getValue(String.class).equals("/empty/")){
                        arrayList.add("("+snapshot.child("name").getValue(String.class)+"):"+snapshot.child("corrected").getValue(String.class));
                        idList.add(snapshot.child("id").getValue(String.class));
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
                        listView.setAdapter(arrayAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(!snapshot.child("corrected").exists()){
                        Log.i("null-object",snapshot.getKey());
                    }
                    else {
                        if(!idList.contains(snapshot.child("id").getValue(String.class))){
                            if(!snapshot.child("corrected").getValue(String.class).equals("/empty/")){
                                idList.add(snapshot.child("id").getValue(String.class));
                                arrayList.add("("+snapshot.child("name").getValue(String.class)+"):"+snapshot.child("corrected").getValue(String.class));
                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
                                listView.setAdapter(arrayAdapter);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().equals("")){
                    String string=editText.getText().toString();
                    editText.setText("");
                    final String[] id = {idgenerator()};
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("id",id[0]);
                    hashMap.put("message",string);
                    hashMap.put("corrected","/empty/");
                    hashMap.put("name",name);
                    databaseReference=FirebaseDatabase.getInstance().getReference();
                    /*databaseReference.child("id").setValue(id[0]);
                    databaseReference.child("message").setValue(string);
                    databaseReference.child("corrected").setValue("/empty/");*/
                    databaseReference.push().setValue(hashMap);
                    databaseReference=FirebaseDatabase.getInstance().getReference();
                    /*databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if(snapshot.child("id").getValue(String.class).equals(id[0])){
                                    if(!snapshot.child("corrected").getValue(String.class).equals("/empty/")){
                                        arrayList.add(snapshot.child("corrected").getValue(String.class)+"2");
                                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
                                        listView.setAdapter(arrayAdapter);
                                        idList.add(id[0]);
                                        id[0] ="";
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this,"Something went wrong try again later",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
            }
        });


    }
    public String idgenerator() {
        String ALPHA_NUMERIC_STRING = "qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int count=20;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            SharedPreferences sharedPreferences=getSharedPreferences("com.sarkar.chat_beta",MODE_PRIVATE);
            SharedPreferences.Editor edit=sharedPreferences.edit();
            edit.putString("name","");
            edit.commit();
            Intent intent= new Intent(MainActivity.this,StartActivity.class);
            startActivity(intent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return  true;
    }
}
