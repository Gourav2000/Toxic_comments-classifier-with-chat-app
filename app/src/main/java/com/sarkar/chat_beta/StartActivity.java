package com.sarkar.chat_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    Button button;
    EditText editText;
    SharedPreferences sharedPreferences;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        editText=findViewById(R.id.name);
        button=findViewById(R.id.btn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString())){
                    Toast.makeText(StartActivity.this,"Please write your name then proceed",Toast.LENGTH_SHORT).show();
                }
                else {
                    sh=getSharedPreferences("com.sarkar.chat_beta",MODE_PRIVATE);
                    SharedPreferences.Editor edit=sh.edit();
                    edit.putString("name",editText.getText().toString());
                    edit.commit();
                    Intent intent=new Intent(StartActivity.this,MainActivity.class);
                    intent.putExtra("name",editText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences=getSharedPreferences("com.sarkar.chat_beta",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","");
        if(!name.equals("")){
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }
    }
}
