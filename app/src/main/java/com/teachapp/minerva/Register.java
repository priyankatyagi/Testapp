package com.teachapp.minerva;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends ActionBarActivity {
    EditText email,password,fname,lname;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        register=(Button) findViewById(R.id.register);
        /*The data entered by the user is stored in a shared preferences file
        so that it is available to all activities within the app
         */


        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedData.writeString(getApplicationContext(),
                        SharedData.FNAME,fname.getText().toString());
                SharedData.writeString(getApplicationContext(),
                        SharedData.LNAME,lname.getText().toString());
                SharedData.writeString(getApplicationContext(),
                        SharedData.EMAIL,email.getText().toString());
                SharedData.writeString(getApplicationContext(),
                        SharedData.PASSWORD,password.getText().toString());
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

            }});
    }



}
