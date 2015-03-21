package com.home.giftcardmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.helper.DataBaseHelper;

public class LoginActivity extends Activity {
    //variables to capture emailId and password from screen
    //private EditText emailEditText;
    //private EditText pwdEditText;

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
                finish();

            }
        });

    }

    // validating email id
    private boolean isValidEmail(String e_mail) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(e_mail);
        return matcher.matches();
    }

    // on Login Button click
    public void Login(View view)
    {
        EditText  emailEditText = (EditText) findViewById(R.id.login_emailId);
        EditText pwdEditText = (EditText) findViewById(R.id.login_pwd);
        String emailId = emailEditText.getText().toString() ;
        String pwd     = pwdEditText.getText().toString() ;

        // verify blank email ID field
        if (emailId.length() == 0) {
            emailEditText.setError("EmailID is required");
            return;
        }

        //verify if email format is correct
        if (!isValidEmail(emailId)) {
            emailEditText.setError("Invalid email format");
            return;
        }

        //verify blank password field
        if (pwd.length() == 0){
            pwdEditText.setError("Password is required");
            return;
        }


        //once all validations pass, make a DB call to verify credentials
            DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
            boolean isValid = helper.isValidLogin(emailId,pwd);

            if(isValid){
                Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                i.putExtra("EmailID",emailId);
                startActivity(i);
                finish();
            }else{
                /**TextView msg = (TextView) this.findViewById(R.id.link_to_register);
                msg.setText("Incorrect Email ID/Password. Please try again...");
                msg.setTextColor(Color.RED); */
                emailEditText.setError("EmailID/Password combination is incorrect");
                pwdEditText.setError("EmailID/Password combination is incorrect");
                emailEditText.requestFocus();
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

            }
    }
}