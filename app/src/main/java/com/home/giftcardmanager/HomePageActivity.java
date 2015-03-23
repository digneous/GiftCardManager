package com.home.giftcardmanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.helper.Card;
import com.helper.DataBaseHelper;
import com.helper.TransactionHistory;

import java.util.List;


public class HomePageActivity extends ActionBarActivity {

    public String emailID = null;

    //on clicking Transfer button
    public void Transfer(View view){
        Intent intent = new Intent(getApplicationContext(),TransferActivity.class);
        intent.putExtra("EmailID", emailID);
        startActivity(intent);
        finish();
    }

    //on clicking History button
    public void history(View view){
        Intent intent = new Intent(getApplicationContext(), TransactionHistoryActivity.class);
        intent.putExtra("EmailID", emailID);
        startActivity(intent);
        finish();
    }

    //on clicking Add Card button
    public void saveCard(View view){
        Intent intent = getIntent();
        Intent i1 = new Intent(getApplicationContext(), CardDetailsActivity.class);
        i1.putExtra("EmailID", emailID);
        startActivity(i1);
        finish();
    }

    //on clicking Sign Out button
    public void signout(View view){
        Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i1);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();
        String email = intent.getStringExtra("EmailID").toString();
        this.emailID = email;

        TableLayout cardtable = (TableLayout) findViewById(R.id.cardtable);

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        List list = helper.FetchCards(email,"G");
        Card card = null;
        TableRow row = null;
        TextView tv1 = null;
        TextView tv2 = null;

        if(list.size()>0){
            tv1 = new TextView(this);
            tv2 = new TextView(this);

            tv1.setWidth(450);
            tv2.setWidth(200);

            tv1.setText("Card Number");
            tv2.setText("Balance");


            tv1.setBackgroundColor(Color.DKGRAY);
            tv1.setTextColor(Color.WHITE);
            tv2.setBackgroundColor(Color.DKGRAY);
            tv2.setTextColor(Color.WHITE);
            tv1.setLayoutParams(new TableRow.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv2.setGravity(View.FOCUS_RIGHT);

            tv1.setBackgroundColor(Color.LTGRAY);
            tv1.setTextColor(Color.BLACK);
            tv2.setBackgroundColor(Color.LTGRAY);
            tv2.setTextColor(Color.BLACK);


            row = new TableRow(this);

            row.addView(tv1);
            row.addView(tv2);
            cardtable.addView(row);
            for (int i=0; i < list.size(); i++){

                card = (Card)list.get(i);
                row = new TableRow(this);
                tv1 = new TextView(this);
                tv1.setTextSize(20);
                tv1.setText(card.getCardNumber());
                tv1.setTextColor(Color.BLACK);

                tv2 = new TextView(this);
                tv2.setTextSize(20);
                tv2.setText(String.valueOf(card.getBalance()));
                tv2.setTextColor(Color.BLACK);

                tv1.setLayoutParams(new TableRow.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv2.setGravity(View.FOCUS_RIGHT);


                if(i%2==1) {
                    tv1.setBackgroundColor(Color.LTGRAY);
                    tv2.setBackgroundColor(Color.LTGRAY);
                }else{
                    tv1.setBackgroundColor(Color.WHITE);
                    tv2.setBackgroundColor(Color.WHITE);
                }
                row.addView(tv1);
                row.addView(tv2);
                cardtable.addView(row);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
