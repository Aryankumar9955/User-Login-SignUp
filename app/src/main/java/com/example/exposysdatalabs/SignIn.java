package com.example.exposysdatalabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    FirebaseAuth authProfile;
    EditText emailCheck;
    EditText passwordCheck;
    String textEmail;
    String pwd;
    public void signIn(View view){
        textEmail = emailCheck.getText().toString();
        pwd = passwordCheck.getText().toString();

        if(TextUtils.isEmpty(textEmail)){
            Toast.makeText(this, "Enter email id", Toast.LENGTH_SHORT).show();
            emailCheck.setError("Empty field");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
            Toast.makeText(this, "Invalid email id", Toast.LENGTH_SHORT).show();
            emailCheck.setError("Enter a valid email address");
        }
        else if(TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            passwordCheck.setError("Empty field");
        }
        else{
            loginUser(textEmail,pwd);
        }

    }

    private void loginUser(String textEmail, String pwd) {
        authProfile.signInWithEmailAndPassword(textEmail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //Get instance of Current User
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //Check if email is verified or not
                    if(firebaseUser.isEmailVerified()){

                        String username = firebaseUser.getDisplayName();
                        //Open user profile
                        Intent intent = new Intent(SignIn.this, LogInMainPage.class);
                        intent.putExtra("name",username);
                        //To not allow user to go back to Register activity on pressing back button
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); //Register activity closed

                    }else{
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut(); //sign out user
                        showAlertDialog();
                    }

                }
                else{
//                    Toast.makeText(SignIn.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                    try{
                        throw task.getException();
                    }catch(FirebaseAuthInvalidUserException e){
                        Toast.makeText(SignIn.this, "Invalid User", Toast.LENGTH_SHORT).show();
                        emailCheck.setError("User does not exist or is no longer valid. Please register again.");
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(SignIn.this, "Invalid credentials. Check credentials and try again.", Toast.LENGTH_SHORT).show();
                        emailCheck.setError("Invalid credentials.");
                    }catch(Exception e){
                        Log.e("Error",e.getMessage());
                        Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You cannot login without email verification");

        //Open email app if user taps positive button
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //open email app in new window
                startActivity(intent);
            }
        });

        //Create the Alert dialog box
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void signUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
    public void adminSignIn(View view){
        Intent intent = new Intent(getApplicationContext(), AdminSignIn.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        emailCheck = findViewById(R.id.emailAdmin);
        passwordCheck = findViewById(R.id.passwordAdmin);


        authProfile = FirebaseAuth.getInstance();


    }

    //Check if user is already logged in. If true, then take user straight to loginMainPage
    @Override
    protected void onStart() {

        super.onStart();

        if(authProfile.getCurrentUser() != null){
            Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();

            //Start the User profile activity
            startActivity(new Intent(this, LogInMainPage.class));
            finish(); //End SignIn activity
        }

    }
}