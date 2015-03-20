package com.home.giftcardmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.helper.DataBaseHelper;

import java.util.Calendar;


public class CardDetailsActivity extends ActionBarActivity {

    //declare a public string to store email id passed from previous screen
    public String emailID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        Intent intent = getIntent();
        String email = intent.getStringExtra("EmailID").toString();
        this.emailID = email;

        Spinner dropmon = (Spinner)findViewById(R.id.month);
        String[] months = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        dropmon.setAdapter(adapter);

        Spinner dropyear = (Spinner)findViewById(R.id.year);
        String[] years = new String[]{"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        dropyear.setAdapter(adapter2);
    }

    //on clicking Back button
    public void goback (View view) {
        Intent i1 = new Intent(getApplicationContext(), HomePageActivity.class);
        i1.putExtra("EmailID",emailID);
        startActivity(i1);
        finish();
    }

    //on clicking signout button
    public void signout(View view) {
        Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i1);
        finish();
    }

    //on clicking Save button
    public void saveCard(View view){

        //Intent intent = getIntent();
        //String email = intent.getStringExtra("EmailID").toString();
        String cardnumber    = ((EditText)findViewById(R.id.cardnumber)).getText().toString() ;
        String amount = ((EditText)findViewById(R.id.amount)).getText().toString() ;
        String cvv     = ((EditText)findViewById(R.id.cvv)).getText().toString() ;

        Spinner spinner1 = (Spinner)findViewById(R.id.month);
        String month = spinner1.getSelectedItem().toString();

        Spinner spinner2 = (Spinner)findViewById(R.id.year);
        String year = spinner2.getSelectedItem().toString();

        //check entered expiry date if it is in the past
        /**Calendar c = Calendar.getInstance();
        int curmonth = c.get(Calendar.MONTH);
        int curyear = c.get(Calendar.YEAR)

        if ((Integer.valueOf(month)) < curmonth && (Integer.valueOf(year)) < curyear ){

        } */

        //verify if cardnumber is blank
        if (cardnumber.length() == 0){
            ((EditText) findViewById(R.id.cardnumber)).setError("Card Number is required");
            return;
        }

        //verify if cardnumber is valid
        if (cardnumber.length() != 16){
            ((EditText) findViewById(R.id.cardnumber)).setError("Incorrect Card Number");
            return;
        }

        //verify if amount is blank
        if (amount.length() == 0){
            ((EditText)findViewById(R.id.amount)).setError("Amount is required");
            return;
        }

        //verify if cvv is blank
        if (cvv.length() == 0){
            ((EditText)findViewById(R.id.cvv)).setError("CVV is required");
            return;
        }

        String cardType = null;
        String expdate = month + "/" + year;
        RadioButton button1 = (RadioButton) findViewById(R.id.Gift);
        boolean cardTypeFlag = button1.isChecked();
        if(cardTypeFlag){
            cardType = "G";
        }else{
            cardType = "U";
        }

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        ContentValues values = new ContentValues();

        values.put("CardNumber",cardnumber);
        values.put("CardExpiryDate",expdate);
        values.put("cvv",cvv);
        values.put("balance",Integer.parseInt(amount));
        values.put("status","A");
        values.put("cardType",cardType);
        values.put("Email", emailID);


        long isValid = helper.addCard(values);

        if(isValid>0){
            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
            intent.putExtra("EmailID",emailID);
            startActivity(intent);
            finish();
        }else{
            TextView msg = (TextView) this.findViewById(R.id.link_to_login);
            msg.setText("Email is already Registered. Please use a different Email ID...");
            msg.setTextColor(Color.RED);
        }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
