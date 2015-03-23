package com.home.giftcardmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;


import com.helper.Card;
import com.helper.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


public class TransferActivity extends ActionBarActivity {
    public static Button TransferBtn;
    public static Button PreviewBtn;

    //declare a public string to store email id passed from previous screen
    public String emailID = null;
    List list1 = new ArrayList();

    public List algorithm(List list, int transferAmount){
        int sum = 0;
        TreeSet<Card> set = new TreeSet<Card>();

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


    public void performTransfer(View view){

        Spinner spinner1 = (Spinner)findViewById(R.id.cardList);
        String toCard = spinner1.getSelectedItem().toString();
        int amount = Integer.parseInt(((EditText) findViewById(R.id.TransferAmount)).getText().toString());
        String comment = ((EditText)findViewById(R.id.Comments)).getText().toString() ;

        Card card = null;
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        ContentValues values = new ContentValues();

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String txnDate = new SimpleDateFormat("MM/dd/yyyy").format(date);

        values.put("TxnDate",txnDate);
        values.put("cardNumber", toCard);
        values.put("Amount", amount);
        values.put("TnxType","CR");
        values.put("comment",comment);
        values.put("Email",this.emailID);
        helper.saveTxn(values);
        helper.updateCardBalanceAndStatus(toCard,amount,"U");

        for(Object obj : list1){
            card = (Card)obj;
            values.put("TxnDate",txnDate);
            values.put("cardNumber",card.getCardNumber());
            values.put("Amount",card.getDebitAmount());
            values.put("TnxType","DR");
            values.put("comment",comment);
            values.put("Email",this.emailID);
            helper.saveTxn(values);
            helper.updateCardBalanceAndStatus(card.getCardNumber(),card.getBalanceAfterDebit(),card.getBalanceAfterDebit()==0?"C":"A");
        }

        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        intent.putExtra("EmailID",emailID);
        startActivity(intent);
        finish();
    }

    public void displayTable(View view){

        EditText xframt = ((EditText)findViewById(R.id.TransferAmount));
        if(xframt.getText().toString().length() == 0){
            xframt.setError("Amount is required");
            return;
        }
        int amount = Integer.parseInt(((EditText) findViewById(R.id.TransferAmount)).getText().toString());
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        boolean flag = helper.isAmountValid(amount,emailID);
        Log.v("TransferActivity","Is Amount Valid = "+flag);


        if(flag){

            TableLayout cardtable = (TableLayout) findViewById(R.id.FromCardTable);
            cardtable.removeAllViews();
            List list = helper.FetchCards(emailID,"G");
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

                tv1.setWidth(380);
                tv2.setWidth(100);
                tv3.setWidth(80);
                tv4.setWidth(120);

                tv1.setText("Card#");
                tv2.setText("Cur Bal");
                tv3.setText("Debit");
                tv4.setText("Post bal");

                tv1.setBackgroundColor(Color.DKGRAY);
                tv1.setTextColor(Color.WHITE);
                tv2.setBackgroundColor(Color.DKGRAY);
                tv2.setTextColor(Color.WHITE);
                tv3.setBackgroundColor(Color.DKGRAY);
                tv3.setTextColor(Color.WHITE);
                tv4.setBackgroundColor(Color.DKGRAY);
                tv4.setTextColor(Color.WHITE);

                tv1.setLayoutParams(new TableRow.LayoutParams(125, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv2.setLayoutParams(new TableRow.LayoutParams(60, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv3.setLayoutParams(new TableRow.LayoutParams(60, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv4.setLayoutParams(new TableRow.LayoutParams(60, ViewGroup.LayoutParams.WRAP_CONTENT));

                tv2.setGravity(View.FOCUS_RIGHT);
                tv3.setGravity(View.FOCUS_RIGHT);
                tv4.setGravity(View.FOCUS_RIGHT);


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
                    tv1.setTextColor(Color.BLACK);

                    tv2 = new TextView(this);
                    tv2.setTextSize(20);
                    tv2.setText(String.valueOf(card.getBalance()));
                    tv2.setTextColor(Color.BLACK);

                    tv3 = new TextView(this);
                    tv3.setTextSize(20);
                    tv3.setText(String.valueOf(card.getDebitAmount()));
                    tv3.setTextColor(Color.BLACK);

                    tv4 = new TextView(this);
                    tv4.setTextSize(20);
                    tv4.setText(String.valueOf(card.getBalanceAfterDebit()));
                    tv4.setTextColor(Color.BLACK);


                    tv1.setLayoutParams(new TableRow.LayoutParams(125, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tv2.setLayoutParams(new TableRow.LayoutParams(60, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tv3.setLayoutParams(new TableRow.LayoutParams(60, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tv4.setLayoutParams(new TableRow.LayoutParams(60, ViewGroup.LayoutParams.WRAP_CONTENT));

                    tv2.setGravity(View.FOCUS_RIGHT);
                    tv3.setGravity(View.FOCUS_RIGHT);
                    tv4.setGravity(View.FOCUS_RIGHT);


                    if(i%2==1) {
                        tv1.setBackgroundColor(Color.LTGRAY);
                        tv2.setBackgroundColor(Color.LTGRAY);
                        tv3.setBackgroundColor(Color.LTGRAY);
                        tv4.setBackgroundColor(Color.LTGRAY);
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

            //hide virtual keyboard once the preview button is clicked

            Button TransferBtn = (Button) findViewById(R.id.btnAmtTransfer) ;
            TransferBtn.setEnabled(true);
            Button PreviewBtn = (Button) findViewById(R.id.btnAmtPreview) ;
            PreviewBtn.setEnabled(false);

        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }

        }else{
            TextView msg = (TextView) this.findViewById(R.id.msg);
            msg.setText("InSufficient Balance!!!");
            msg.setTextColor(Color.RED);
            Button TransferBtn = (Button) findViewById(R.id.btnAmtTransfer) ;
            TransferBtn.setEnabled(false);
            Button PreviewBtn = (Button) findViewById(R.id.btnAmtPreview) ;
            PreviewBtn.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Button TransferBtn = (Button) findViewById(R.id.btnAmtTransfer) ;
        TransferBtn.setEnabled(false);

        Intent intent = getIntent();
        String email = intent.getStringExtra("EmailID").toString();
        this.emailID = email;

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        List list = helper.FetchCards(emailID,"U");
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

    public void signout(View view) {
        Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i1);
        finish();
    }

    public void goback (View view) {
        Intent i1 = new Intent(getApplicationContext(), HomePageActivity.class);
        i1.putExtra("EmailID",emailID);
        startActivity(i1);
        finish();
    }

    public void reset (View view) {
        /**Intent i1 = new Intent(getApplicationContext(), HomePageActivity.class);
        i1.putExtra("EmailID",emailID);
        startActivity(i1);
        finish(); */
        EditText xfrAmt = (EditText) findViewById(R.id.TransferAmount);
        TableLayout cardtable = (TableLayout) findViewById(R.id.FromCardTable);

        xfrAmt.setText(null);
        xfrAmt.setHint("Enter transfer amount");

        EditText comments = (EditText) findViewById(R.id.Comments);
        comments.setText(null);
        comments.setHint("Enter Comments");

        Button TransferBtn = (Button) findViewById(R.id.btnAmtTransfer) ;
        TransferBtn.setEnabled(false);
        Button PreviewBtn = (Button) findViewById(R.id.btnAmtPreview) ;
        PreviewBtn.setEnabled(true);

        cardtable.removeAllViews();

        /**
        int count = cardtable.getChildCount();
        for (int i=0;i<count;i++)
            cardtable.removeView(cardtable.getChildAt(i)); */

        xfrAmt.requestFocus();
    }
}
