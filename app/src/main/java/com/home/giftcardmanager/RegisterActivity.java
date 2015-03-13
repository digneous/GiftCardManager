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

public class RegisterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i1);
                finish();
            }
        });
    }

    public void registerNewAccount(View view)
    {
        //Intent i = new Intent(getApplicationContext(), HomePageActivity.class);

        String name    = ((EditText)findViewById(R.id.reg_fullname)).getText().toString() ;
        String emailId = ((EditText)findViewById(R.id.reg_email)).getText().toString() ;
        String pwd     = ((EditText)findViewById(R.id.reg_password)).getText().toString() ;

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        ContentValues values = new ContentValues();

        values.put("Name",name);
        values.put("Email",emailId);
        values.put("Password",pwd);

        long isValid = helper.addUser(values);


        if(isValid>0){
            Intent i1 = new Intent(getApplicationContext(), HomePageActivity.class);
            i1.putExtra("UserName",name);
            i1.putExtra("EmailID",emailId);
            startActivity(i1);
            finish();
        }else{
            TextView msg = (TextView) this.findViewById(R.id.link_to_login);
            msg.setText("Email is already Registered. Please use a different Email ID...");
            msg.setTextColor(Color.RED);
        }
    }
}
