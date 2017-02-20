package com.moodybugs.testuber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.verification.CodeInterceptionException;
import com.sinch.verification.Config;
import com.sinch.verification.InitiationResult;
import com.sinch.verification.PhoneNumberUtils;
import com.sinch.verification.SinchVerification;
import com.sinch.verification.Verification;
import com.sinch.verification.VerificationListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText inputMobileNumber;
    Button btnVarify;
    ImageButton imageButtonVarified;
    private static final String APPLICATION_KEY = "0a8531ed-a061-4b1e-89a4-8c26f771975d";
    private Verification mVerification;
    LinearLayout layoutVarified;

    ProgressBar progressBar;
    TextView txtWait;

    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isNetworkAvailable()){
            if (new SharedPrefDatabase(getApplicationContext()).RetriveNumber() != null){
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
                finish();
            }else {
                init();
            }
        }else {
            Intent intent = new Intent(MainActivity.this, InternetConnection.class);
            startActivity(intent);
        }
    }

    public void init(){
        inputMobileNumber = (EditText) findViewById(R.id.inputMobileNumber);
        btnVarify = (Button) findViewById(R.id.btnVarify);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtWait = (TextView) findViewById(R.id.txtWait);
        layoutVarified = (LinearLayout) findViewById(R.id.layoutVarified);
        imageButtonVarified = (ImageButton) findViewById(R.id.imageButtonVarified);

        ButtonClicked();
    }

    public void ButtonClicked(){
        btnVarify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileNumber = inputMobileNumber.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                txtWait.setVisibility(View.VISIBLE);
                inputMobileNumber.setEnabled(false);
                btnVarify.setEnabled(false);
                MobileVarify();
            }
        });

        imageButtonVarified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void MobileVarify(){
        Config config = SinchVerification.config().applicationKey(APPLICATION_KEY).context(getApplicationContext()).build();
        VerificationListener listener = new MyVerificationListener();
        mVerification = SinchVerification.createFlashCallVerification(config, inputMobileNumber.getText().toString(), listener);
        mVerification.initiate();
    }


    private class MyVerificationListener implements VerificationListener {

        @Override
        public void onInitiated(InitiationResult initiationResult) {
            progressBar.setVisibility(View.VISIBLE);
            txtWait.setVisibility(View.VISIBLE);
        }

        @Override
        public void onInitiationFailed(Exception e) {
            progressBar.setVisibility(View.GONE);
            txtWait.setVisibility(View.GONE);
            inputMobileNumber.setEnabled(true);
            btnVarify.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Varifying Failed.\nInitialization Error", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVerified() {
            progressBar.setVisibility(View.GONE);
            txtWait.setVisibility(View.GONE);
            layoutVarified.setVisibility(View.VISIBLE);
            imageButtonVarified.setVisibility(View.VISIBLE);
            new SharedPrefDatabase(getApplicationContext()).StoreNumber(inputMobileNumber.getText().toString());
            //Toast.makeText(getApplicationContext(), "Varifying Completed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVerificationFailed(Exception e) {
            progressBar.setVisibility(View.GONE);
            txtWait.setVisibility(View.GONE);
            if (e instanceof CodeInterceptionException) {
                inputMobileNumber.setEnabled(true);
                btnVarify.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Varifying Failed " + e.toString(), Toast.LENGTH_LONG).show();
            } else {
                inputMobileNumber.setEnabled(true);
                btnVarify.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Varifying Failed " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
