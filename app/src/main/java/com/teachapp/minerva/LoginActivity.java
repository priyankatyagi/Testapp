package com.teachapp.minerva;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {
    EditText email,password;
    Button login;
    TextView register;
    String user_email;
    String user_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.textView3);
        login = (Button) findViewById(R.id.login);
        user_email=SharedData.readString(getApplicationContext(),
                SharedData.EMAIL,"");
        user_password=SharedData.readString(getApplicationContext(),
                SharedData.PASSWORD,"");

        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //Once the email and password are validated,the user is displayed the general profile screen
                if (  ( email.getText().toString().equals(user_email)) && ( password.getText().toString().equals(user_password)) )
                {
                   Intent i=new Intent(getApplicationContext(),Profile.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),
                            "Login Successful", Toast.LENGTH_SHORT).show();
                }
                else if ( ( email.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                }
                else if ( ( password.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //On clicking the register button,the new user is directed to the registration screen(RegisterActivity.class)
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);

            }});
    }



}
