package com.example.telecomreclamation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView login;
    TextView signup;
    TextView forgotPassword;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        forgotPassword=findViewById(R.id.forgotPassword);
        progressBar=findViewById(R.id.progress_bar);

        login.setOnClickListener(x->{
            if(email.getText().toString().equals("")){
                email.setError("This field cannot be empty");
            }
            else if(password.getText().toString().equals("")){
                password.setError("This field cannot be empty");
            }else{
                Login();
            }
        });

        signup.setOnClickListener(x->{
            startActivity(new Intent(getApplicationContext(),SignupActivity.class));
        });
    }

    private void Login() {

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                        email.setText("");
                        password.setText("");
                        startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.create();
                        builder.setTitle("Error logging in");
                        builder.setMessage("It's either a connection problem or the account doesn't exist");
                        builder.setPositiveButton("Okay",((dialog, which) -> dialog.dismiss()));
                        builder.show();
                    }
                }).addOnFailureListener(failure->{
            Log.d("Error loggin in",failure.getMessage());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar.setVisibility(View.INVISIBLE);
    }
}