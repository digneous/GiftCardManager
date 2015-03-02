package com.home.giftcardmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.helper.DataBaseHelper;

public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);

        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void Login(View view)
    {

        String emailId = ((EditText)findViewById(R.id.login_emailId)).getText().toString() ;
        String pwd     = ((EditText)findViewById(R.id.login_pwd)).getText().toString() ;

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        boolean isValid = helper.isValidLogin(emailId,pwd);

        if(isValid){
            Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
            i.putExtra("UserName",emailId);
            startActivity(i);
        }else{
            TextView msg = (TextView) this.findViewById(R.id.link_to_register);
            msg.setText("Incorrect Email ID/Password. Please try again...");
            msg.setTextColor(Color.RED);
        }



    }
}