package com.home.giftcardmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.helper.DataBaseHelper;


public class CardDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
    }

    public void signout(View view) {
        Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i1);
        finish();
    }

    public void saveCard(View view){

        Intent intent = getIntent();
        String email = intent.getStringExtra("EmailID").toString();

        String cardnumber    = ((EditText)findViewById(R.id.cardnumber)).getText().toString() ;
        String amount = ((EditText)findViewById(R.id.amount)).getText().toString() ;
        String cvv     = ((EditText)findViewById(R.id.cvv)).getText().toString() ;
        String expdate = ((EditText)findViewById(R.id.expiry)).getText().toString() ;
        String cardType = null;

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
        values.put("cardType","1");
        values.put("Email", email);


        long isValid = helper.addCard(values);

        if(isValid>0){
            Intent i1 = new Intent(getApplicationContext(), HomePageActivity.class);
            startActivity(i1);
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
