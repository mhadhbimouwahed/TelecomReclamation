package com.example.telecomreclamation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.telecomreclamation.databinding.ActivityNavigationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    FirebaseUser user;
    private TextView userEmail;
    private NavigationView navigationView;
    private Menu menu;
    private MenuItem nav_home,nav_gallery,nav_slideshow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("Users");


        setSupportActionBar(binding.appBarNavigation.toolbarrr);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        navigationView=findViewById(R.id.nav_view);
        menu=navigationView.getMenu();
        nav_home=menu.findItem(R.id.nav_home);
        nav_gallery=menu.findItem(R.id.nav_gallery);
        nav_slideshow=menu.findItem(R.id.nav_slideshow);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View header=navigationView.getHeaderView(0);
        userEmail=header.findViewById(R.id.userEmail);


    }

    @Override
    protected void onStart() {
        super.onStart();
        /*user=firebaseAuth.getCurrentUser();
        if (user!=null){

                    collectionReference.whereEqualTo("UserID",firebaseAuth.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                Map<String, Object> hashMap=documentSnapshot.getData();
                                userEmail.setText(hashMap.get("Email").toString());
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "failed to display user email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(failure->{
                        Toast.makeText(getApplicationContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
                    });
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.navigation,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*if (item.getItemId()==R.id.action_logout){
            logout();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        user=firebaseAuth.getCurrentUser();
        if (user!=null){
            firebaseAuth.signOut();
            
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            Toast.makeText(getApplicationContext(), "logged out successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}