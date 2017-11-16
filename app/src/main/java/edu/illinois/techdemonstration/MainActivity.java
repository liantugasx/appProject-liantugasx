package edu.illinois.techdemonstration;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import edu.illinois.techdemonstration.App.Authentication;

public class MainActivity extends AppCompatActivity {

    MaterialEditText newUserName;
    MaterialEditText newPassword;
    MaterialEditText emailAddress;
    MaterialEditText userName;
    MaterialEditText password;
    Button signInButton;
    Button signUpButton;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (MaterialEditText) findViewById(R.id.userName);
        password = (MaterialEditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.sign_in);
        signUpButton = (Button) findViewById(R.id.sign_up);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignIn(userName.getText().toString(),
                        password.getText().toString());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySignUp();
            }
        });
    }

    private void userSignIn(final String registeredUserName, final String registeredPassword) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(registeredUserName).exists()) {
                    if(!registeredUserName.isEmpty()) {
                        Authentication signIn = dataSnapshot.child(registeredUserName).getValue(Authentication.class);
                        if(signIn.getPassword().equals(registeredPassword))
                            Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Wrong password. Try again", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Enter user name and password", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displaySignUp() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Create New Account");

        LayoutInflater inflater = this.getLayoutInflater();
        View signUpView = inflater.inflate(R.layout.authentication_page_layout, null);

        newUserName = (MaterialEditText) signUpView.findViewById(R.id.newUserName);
        newPassword = (MaterialEditText) signUpView.findViewById(R.id.newPassword);
        emailAddress = (MaterialEditText) signUpView.findViewById(R.id.emailAddress);

        alertDialog.setView(signUpView);
        alertDialog.setIcon(R.drawable.ic_account_box_black_24dp);

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Authentication user = new Authentication(newUserName.getText().toString(),
                        newPassword.getText().toString(),
                        emailAddress.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUserName()).exists())
                            Toast.makeText(MainActivity.this, "UserName already exists. Please try again", Toast.LENGTH_SHORT).show();
                        else {
                            users.child(user.getUserName()).setValue(user);
                            Toast.makeText(MainActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }
}
