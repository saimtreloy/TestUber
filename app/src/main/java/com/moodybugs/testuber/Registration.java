package com.moodybugs.testuber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    RelativeLayout layoutSignUp, layoutSignIn;
    //Registration
    EditText inputUsername, inputEmail, inputPassword, inputMobile;
    Button btnRegister, btnBackToLogin;
    //Login
    EditText inputMobileNumberSignIn, inputPasswordSignIn;
    Button btnSignIn;
    Button btnCreateAccount, btnForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
    }


    public void init(){

        layoutSignUp = (RelativeLayout) findViewById(R.id.layoutSignUp);
        layoutSignIn = (RelativeLayout) findViewById(R.id.layoutSignIn);

        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputMobile = (EditText) findViewById(R.id.inputMobile);

        inputMobileNumberSignIn = (EditText) findViewById(R.id.inputMobileNumberSignIn);
        inputPasswordSignIn = (EditText) findViewById(R.id.inputPasswordSignIn);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnForgetPassword = (Button) findViewById(R.id.btnForgetPassword);
        btnBackToLogin = (Button) findViewById(R.id.btnBackToLogin);

        ButtonClicked();
    }


    public void ButtonClicked(){

        inputMobile.setText(new SharedPrefDatabase(getApplicationContext()).RetriveNumber());
        inputMobileNumberSignIn.setText(new SharedPrefDatabase(getApplicationContext()).RetriveNumber());

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSignIn.setVisibility(View.GONE);
                layoutSignUp.setVisibility(View.VISIBLE);
            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSignIn.setVisibility(View.VISIBLE);
                layoutSignUp.setVisibility(View.GONE);
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, MapsActivity2.class));
            }
        });
    }
}
