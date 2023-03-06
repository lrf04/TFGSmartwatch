package com.example.tfgsmartwatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tfgsmartwatch.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private EditText editTextEmail,editTextPassword,editTextId;
    private Switch switchRecordar;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();
        prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        /*setCredentialsIfExist();*/

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();
                String id=editTextId.getText().toString();
                if(login(email,password)){
                    goToMain();
                    saveOnPreferences(email,password,id);
                }
            }
        });

    }
    private void bindUI(){
        editTextId=(EditText)findViewById(R.id.editTextId);
        editTextEmail =(EditText) findViewById(R.id.editTextTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);

        buttonLogin=(Button) findViewById(R.id.buttonLogin);
    }

    private boolean login(String name,String password) {
        if (!isValidEmail(name)) {
            Toast.makeText(this, "Email no válido", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPassword(password)) {
            Toast.makeText(this, "Contraseña no válida", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean isValidEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        return password.length()>=4;
    }

    private void saveOnPreferences(String name,String password,String id){

            //Hay que crear este porque el otro que creamos era solo de lectura
            SharedPreferences.Editor editor=prefs.edit();
            editor.putString("name",name);
            editor.putString("password",password);
            editor.putString("id",id);
            editor.apply();

    }

    /*private void setCredentialsIfExist(){
        String name= util.getUserNamePrefs(prefs);
        String password=util.getUserPasswordPrefs(prefs);
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)){
            editTextEmail.setText(name);
            editTextPassword.setText(password);
            switchRecordar.setChecked(true);

        }
    }*/

    private void goToMain(){
        Intent intent=new Intent(LoginActivity.this, MenuPrincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}