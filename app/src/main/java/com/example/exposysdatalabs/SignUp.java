package com.example.exposysdatalabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //Firebase database
    FirebaseAuth auth;
    //
    EditText userName;
    EditText email;
    EditText password;
    EditText repassword;
    EditText number;
    String domain;

    public void signIn(View view){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    public void signUp(View view){

        String username = userName.getText().toString();
        String mail = email.getText().toString();
        String pwd = password.getText().toString();
        String cnfpwd = repassword.getText().toString();
        String mobile = number.getText().toString();

        boolean allGood = true;

        //Username
        if (TextUtils.isEmpty(username)){
            userName.setError("Username is required");
        }

        //Email
        else if (TextUtils.isEmpty(mail)){
            email.setError("Email is required");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Enter a valid email address");
        }

        //Password
        else if (TextUtils.isEmpty(pwd)){
            password.setError("Please set a password");
        }else if(pwd.length()>0&&pwd.length()<=5){
            password.setError("Password too weak");
        }
        else if (TextUtils.isEmpty(cnfpwd)){
            repassword.setError("Re-enter your password");
        }else if(pwd.length()>5 &&!cnfpwd.equals(pwd)){
            repassword.setError("Passwords don't match");
        }

        //Phone number
        else if (TextUtils.isEmpty(mobile)|| mobile.length()!=10){
            number.setError("Enter a valid mobile number");
        }

        //Register user
        else{

            registerUser(username,mail,pwd,mobile);

        }

    }

    //Register user
    private void registerUser(String username, String mail, String pwd, String mobile) {

        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(mail,pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "User registered", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    //Update Display Name of user
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //Save user data into the Firebase Realtime Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(username,mail,mobile,domain,pwd);

                    //Extracting User Reference from Database for "Registered Users"
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");


                    databaseReference.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {

                            if (task1.isSuccessful()){
                                //send verification mail
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(SignUp.this, "User details added successfully. Please verify your mail", Toast.LENGTH_LONG).show();
                                //Open user profile
                                Intent intent = new Intent(SignUp.this, SignIn.class);
                                //To not allow user to go back to Register activity on pressing back button
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); //Register activity closed
                            }else{
                                Toast.makeText(SignUp.this, "User registration failed ", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                } else if (!task.isSuccessful()) {
                    Log.i("Details",mail+" "+pwd+" "+username+" "+mobile);

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(SignUp.this, "Password too weak", Toast.LENGTH_SHORT).show();
                        password.setError("Your password is too weak. Use Alphanumeric and Special characters");
                    } catch(FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(SignUp.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                        email.setError("Your email is invalid");
                    } catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(SignUp.this, "Email is already registered. Sign in or use another email.", Toast.LENGTH_SHORT).show();
                        email.setError("Email is already registered.");
                    } catch(Exception e){
                        Log.e("Data Error",e.getMessage());
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.getUsername);
        email = findViewById(R.id.getEmail);
        password = findViewById(R.id.getPassword);
        number = findViewById(R.id.getNumber);
        repassword = findViewById(R.id.confirmPassword);


    }
}