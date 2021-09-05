package com.example.telecomreclamation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telecomreclamation.databinding.ActivityNavBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.telecomreclamation.databinding.ActivityNavBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavBinding binding;
    TextView name;
    TextView email;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("CreatedUsers");



        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNav.toolbarr);
        
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View header=navigationView.getHeaderView(0);
        name=header.findViewById(R.id.connectedClientName);
        email=header.findViewById(R.id.connectedClientEmail);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_logout){
            firebaseAuth.signOut();
            Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        user=firebaseAuth.getCurrentUser();
        if (user!=null){
            collectionReference
                    .document(firebaseAuth.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            name.setText(documentSnapshot.getString("ClientName"));
                            email.setText(documentSnapshot.getString("ClientEmail"));
                        }else{
                            Toast.makeText(getApplicationContext(), "couldn't display user name and email", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.d("ERROR_READING_FROM_DATABASE",e.getMessage());
                    });
        }else{
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            Toast.makeText(getApplicationContext(), "This incident will be reported", Toast.LENGTH_SHORT).show();
        }
    }
}