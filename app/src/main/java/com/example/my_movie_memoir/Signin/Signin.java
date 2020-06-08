package com.example.my_movie_memoir.Signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.my_movie_memoir.MainActivity;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.Signin.Signup.SignupFragment;


// the UI for sign in
public class Signin extends AppCompatActivity {

    private SignInViewModel signinViewModel;
    private EditText add_username;
    private EditText add_password;
    private Button btn_signIn;
    private Button btn_signUp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        add_username = findViewById(R.id.add_username);
        add_password = findViewById(R.id.add_password);
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_signUp = findViewById(R.id.btn_signUp);
        progressBar = findViewById(R.id.progressbar);
        signinViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        signinViewModel.initContext(this);


        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(SignupFragment.newInstance());
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                signinViewModel.singInProcess(add_username.getText().toString().trim(), add_password.getText().toString().trim());
            }
        });

        signinViewModel.getSignInResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0) {
                    // if sign in successfully then go to home page
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else if (integer == 1) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),
                            "Sign in failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);

    }

// if user click sign up than go to sign up  fragment
    private void navigateTo (Fragment fragment){
        String NAVIGATION_TAG = "nav";
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerSignin, fragment)
                .addToBackStack(NAVIGATION_TAG)
                .commit();
    }

}
