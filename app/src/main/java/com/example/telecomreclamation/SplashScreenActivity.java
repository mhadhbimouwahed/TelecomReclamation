package com.example.telecomreclamation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    ProgressBar wait;
    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        wait=findViewById(R.id.wait);


    }

    public boolean isServicesOkay(){
        Log.d(TAG,"checking if services okay");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SplashScreenActivity.this);
        if (available == ConnectionResult.SUCCESS){
            Toast.makeText(getApplicationContext(), "Google play services working", Toast.LENGTH_SHORT).show();
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.create();
            builder.setTitle("Error occurred with google maps");
            builder.setMessage((CharSequence) GoogleApiAvailability.getInstance().getErrorDialog(SplashScreenActivity.this,available,ERROR_DIALOG_REQUEST));
            builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
            builder.show();
        }else{
            Toast.makeText(getApplicationContext(), "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        wait.setVisibility(View.VISIBLE);
        CountDownTimer countDownTimer=new CountDownTimer(300,50) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        };

        countDownTimer.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        wait.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wait.setVisibility(View.INVISIBLE);
    }
}