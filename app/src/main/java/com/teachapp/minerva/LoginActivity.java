package com.teachapp.minerva;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends ActionBarActivity {
    EditText email, password;
    Button login;
    TextView register;
    String user_email;
    String user_password;
    private CallbackManager callbackManager;//Used to route calls back to the Facebook SDK and your registered callbacks.
    private LoginButton fbLoginButton;
    private AccessTokenTracker accessTokenTracker;//It follows the current access token and can log people in and out.
    private ProfileTracker profileTracker;//It shows the user id, and the accepted and denied permissions.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.textView3);
        login = (Button) findViewById(R.id.login);
        user_email = SharedData.readString(getApplicationContext(),
                SharedData.EMAIL, "");
        user_password = SharedData.readString(getApplicationContext(),
                SharedData.PASSWORD, "");
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //Once the email and password are validated,the user is displayed the general profile screen
                if ((email.getText().toString().equals(user_email)) && (password.getText().toString().equals(user_password))) {
                    Intent i = new Intent(getApplicationContext(), Profile.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),
                            "Login Successful", Toast.LENGTH_SHORT).show();
                } else if ((email.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                } else if ((password.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //On clicking the register button,the new user is directed to the registration screen(RegisterActivity.class)
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);

            }
        });
        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //asks the user for accessing profile info
                fbLoginButton.setReadPermissions(Arrays.asList("public_profile, email"));

            }});

            fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    //if the login is successful
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                    /*Place causing error..response is generated using the users current access token and then written into
                        the shared data file.
                     */
                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                       SharedData.writeString(getApplicationContext(),
                                                SharedData.USER_NAME, object.optString("name"));
                                        SharedData.writeString(getApplicationContext(),
                                                SharedData.EMAIL, object.optString("email"));
                                        SharedData.writeString(getApplicationContext(),
                                                SharedData.USER_ID, object.optString("id"));
                                        Intent i1=new Intent(LoginActivity.this, Profile.class);
                                        startActivity(i1);
                                       Log.v("LoginActivity", response.toString());
                                   }
                               });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                        System.out.println("Facebook Login failed!!");

                    }

                    @Override
                    public void onError(FacebookException e) {
                        Toast.makeText(LoginActivity.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
                        System.out.println("Facebook Login failed!!");
                    }

        }   );


    }


    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent i) {
        callbackManager.onActivityResult(reqCode, resCode, i);
        System.out.println("Here");


    }


}



