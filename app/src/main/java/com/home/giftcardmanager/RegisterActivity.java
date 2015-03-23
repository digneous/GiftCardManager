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

    // validating email id
    private boolean isValidEmail(String e_mail) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(e_mail);
        return matcher.matches();
    }

    public void registerNewAccount(View view)
    {
        //Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
        EditText emailEditText = (EditText) findViewById(R.id.reg_email);
        EditText pwdEditText = (EditText) findViewById(R.id.reg_password);
        EditText nameEditText = (EditText) findViewById(R.id.reg_fullname);
        String name    = nameEditText.getText().toString() ;
        String emailId = emailEditText.getText().toString() ;
        String pwd     = pwdEditText.getText().toString() ;


        // verify blank name field
        if (name.length() == 0) {
            nameEditText.setError("Name is required");
            return;
        }

        // verify blank Email ID field
        if (emailId.length() == 0) {
            emailEditText.setError("Email ID is required");
            return;
        }

        //verify if email format is correct
        if (!isValidEmail(emailId)) {
            emailEditText.setError("Invalid Email");
            return;
        }

        //verify blank password field
        if (pwd.length() == 0){
            pwdEditText.setError("Password is required");
            return;
        }


        //once all validations pass, make a DB call to register new user
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
                /**TextView msg = (TextView) this.findViewById(R.id.link_to_login);
                msg.setText("Email is already Registered. Please use a different Email ID...");
                msg.setTextColor(Color.RED); */
                emailEditText.setError("Email ID already exists");
                emailEditText.requestFocus();
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
            }




    }
}
