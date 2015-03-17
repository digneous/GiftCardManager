package com.home.giftcardmanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.helper.Card;
import com.helper.DataBaseHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


public class TransferActivity extends ActionBarActivity {

    String email = null;


    public List algorithm(List list, int transferAmount){
        int sum = 0;
        TreeSet<Card> set = new TreeSet<Card>();
        List list1 = new ArrayList();
        Card cardObj = null;
        for(Object card : list){
            cardObj= (Card)card;
            set.add(cardObj);
        }

        Iterator iterator = set.iterator();
        while(iterator.hasNext())
        {
            cardObj = (Card)iterator.next();
            sum = sum+cardObj.getBalance();
            if(sum <transferAmount){
                cardObj.setDebitAmount(cardObj.getBalance());
                cardObj.setBalanceAfterDebit(0);
                list1.add(cardObj);

            }else if(sum ==transferAmount){
                cardObj.setDebitAmount(cardObj.getBalance());
                cardObj.setBalanceAfterDebit(0);
                list1.add(cardObj);
                return list1;
            }
            else if(sum>transferAmount){
                cardObj.setDebitAmount(cardObj.getBalance()-sum+transferAmount);
                cardObj.setBalanceAfterDebit(sum-transferAmount);
                list1.add(cardObj);
                return list1;
            }
        }
        return list1;
    }

    public void displayTable(View view){

        int amount = Integer.parseInt(((EditText) findViewById(R.id.TransferAmount)).getText().toString());
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        boolean flag = helper.isAmountValid(amount,email);
        Log.v("TransferActivity","Is Amount Valid = "+flag);
        if(flag){

            TableLayout cardtable = (TableLayout) findViewById(R.id.FromCardTable);
            cardtable.removeAllViews();
            List list = helper.FetchCards(email,"G");
            list = algorithm(list,amount);
            Card card = null;
            TableRow row = null;
            TextView tv1 = null;
            TextView tv2 = null;
            TextView tv3 = null;
            TextView tv4 = null;

            if(list.size()>0)
            {
                tv1 = new TextView(this);
                tv2 = new TextView(this);
                tv3 = new TextView(this);
                tv4 = new TextView(this);


                tv1.setText("Card#");
                tv2.setText("Bal");
                tv3.setText("Debit");
                tv4.setText("BalAftDebit");

                tv1.setBackgroundColor(Color.LTGRAY);
                tv1.setTextColor(Color.BLACK);
                tv2.setBackgroundColor(Color.LTGRAY);
                tv2.setTextColor(Color.BLACK);
                tv3.setBackgroundColor(Color.LTGRAY);
                tv3.setTextColor(Color.BLACK);
                tv4.setBackgroundColor(Color.LTGRAY);
                tv4.setTextColor(Color.BLACK);

                row = new TableRow(this);

                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);
                row.addView(tv4);

                cardtable.addView(row);
                for (int i=0; i < list.size(); i++){

                    card = (Card)list.get(i);
                    row = new TableRow(this);
                    tv1 = new TextView(this);
                    tv1.setTextSize(20);
                    tv1.setText(card.getCardNumber());
                    tv1.setTextColor(Color.RED);

                    tv2 = new TextView(this);
                    tv2.setTextSize(20);
                    tv2.setText(String.valueOf(card.getBalance()));
                    tv2.setTextColor(Color.RED);

                    tv3 = new TextView(this);
                    tv3.setTextSize(20);
                    tv3.setText(String.valueOf(card.getDebitAmount()));
                    tv3.setTextColor(Color.RED);

                    tv4 = new TextView(this);
                    tv4.setTextSize(20);
                    tv4.setText(String.valueOf(card.getBalanceAfterDebit()));
                    tv4.setTextColor(Color.RED);


                    if(i%2==1) {
                        tv1.setBackgroundColor(Color.YELLOW);
                        tv2.setBackgroundColor(Color.YELLOW);
                        tv3.setBackgroundColor(Color.YELLOW);
                        tv4.setBackgroundColor(Color.YELLOW);
                    }else{
                        tv1.setBackgroundColor(Color.WHITE);
                        tv2.setBackgroundColor(Color.WHITE);
                        tv3.setBackgroundColor(Color.WHITE);
                        tv4.setBackgroundColor(Color.WHITE);
                    }
                    row.addView(tv1);
                    row.addView(tv2);
                    row.addView(tv3);
                    row.addView(tv4);
                    cardtable.addView(row);
                }
            }//end of for


        }else{
            TextView msg = (TextView) this.findViewById(R.id.msg);
            msg.setText("InSufficient Balance!!!");
            msg.setTextColor(Color.RED);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        Intent intent = getIntent();
        this.email = intent.getStringExtra("EmailID").toString();
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        List list = helper.FetchCards(email,"U");
        List<String> cardList = new ArrayList<String>();
        Card card = null;
        Spinner spinner = (Spinner) findViewById(R.id.cardList);

        for(Object car:list){
            card = (Card)car;
            cardList.add(card.getCardNumber());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cardList);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

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
