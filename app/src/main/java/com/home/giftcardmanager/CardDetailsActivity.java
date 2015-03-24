package com.home.giftcardmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        //get email ID from calling screen
        Intent intent = getIntent();
        String email = intent.getStringExtra("EmailID").toString();
        this.emailID = email;

        //populate month drop down
        Spinner dropmon = (Spinner)findViewById(R.id.month);
        String[] months = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        dropmon.setAdapter(adapter);

        //populate year dropdown
        Spinner dropyear = (Spinner)findViewById(R.id.year);
        String[] years = new String[]{"2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
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

    //on clicking reset button
    public void reset (View view) {

        EditText c_no = (EditText) findViewById(R.id.cardnumber);
        c_no.setText(null);
        c_no.setHint("enter card number");

        EditText amt = (EditText) findViewById(R.id.amount);
        amt.setText(null);
        amt.setHint("enter amount");

        EditText cvv_no = (EditText) findViewById(R.id.cvv);
        cvv_no.setText(null);
        cvv_no.setHint("enter 4 digit security code");

        c_no.requestFocus();

    }

    //on clicking Save button
    public void saveCard(View view) {
        String cardnumber = ((EditText) findViewById(R.id.cardnumber)).getText().toString();
        String amount = ((EditText) findViewById(R.id.amount)).getText().toString();
        String cvv = ((EditText) findViewById(R.id.cvv)).getText().toString();

        Spinner spinner1 = (Spinner) findViewById(R.id.month);
        String month = spinner1.getSelectedItem().toString();

        Spinner spinner2 = (Spinner) findViewById(R.id.year);
        String year = spinner2.getSelectedItem().toString();

        //reset error msg
        ((EditText) findViewById(R.id.cardnumber)).setError(null);
        ((EditText) findViewById(R.id.amount)).setError(null);
        ((EditText) findViewById(R.id.cvv)).setError(null);

        //verify if cardnumber is blank
        if (cardnumber.length() == 0) {
            ((EditText) findViewById(R.id.cardnumber)).setError("Card Number is required");
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e3) {

            }
            return;
        }

        //verify if cardnumber is valid
        if (cardnumber.length() < 15) {
            ((EditText) findViewById(R.id.cardnumber)).setError("Incorrect Card Number");
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e4) {

            }
            return;
        }

        //verify if amount is blank
        if (amount.length() == 0) {
            ((EditText) findViewById(R.id.amount)).setError("Amount is required");
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e5) {

            }
            return;
        }

        //verify if cvv is blank
        if (cvv.length() == 0) {
            ((EditText) findViewById(R.id.cvv)).setError("CVV is required");
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e6) {

            }
            return;
        }

        //verify if cvv length is incorrect
        if (cvv.length() < 3) {
            ((EditText) findViewById(R.id.cvv)).setError("CVV is invalid");
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e7) {

            }
            return;
        }

        //check if entered expiry date is in the past
        Calendar c = Calendar.getInstance();
        int curmonth = c.get(Calendar.MONTH);
        int curyear = c.get(Calendar.YEAR);
        View selectedView1 = spinner1.getSelectedView();
        TextView selectedTextView1 = (TextView) selectedView1;

        View selectedView2 = spinner2.getSelectedView();
        TextView selectedTextView2 = (TextView) selectedView2;

        selectedTextView1.setError(null);
        selectedTextView2.setError(null);

        if ((Integer.valueOf(year)) == curyear) {
            if ((Integer.valueOf(month)) < curmonth) {
                if (selectedView1 != null && selectedView1 instanceof TextView) {
                    selectedTextView1.setError("Expiry date cannot be in the past");
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e1) {

                    }
                    return;
                }
            }
        }
        if ((Integer.valueOf(year)) < curyear) {

            if (selectedView2 != null && selectedView2 instanceof TextView) {
                selectedTextView2.setError("Expiry date cannot be in the past");
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e2) {

                }
                return;
            }
        }

        String cardType = null;
        String expdate = month + "/" + year;
        RadioButton button1 = (RadioButton) findViewById(R.id.Gift);
        RadioButton button2 = (RadioButton) findViewById(R.id.Use);
        boolean cardTypeFlag = button1.isChecked();

        button1.setError(null);
        button2.setError(null);

        if (cardTypeFlag) {
            cardType = "G";
            if (amount.equals("0")){
                button1.setError("For 'Gift' Card, amount cannot be 0");

                ((EditText) findViewById(R.id.amount)).setError("For 'Gift' card, amount cannot be 0");
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e8) {

                }
                return;
            }

        } else {
            cardType = "U";
            if (!(amount.equals("0"))){
                //amount = "0";
                button2.setError("For 'Use' Card, amount should be 0");
                ((EditText) findViewById(R.id.amount)).requestFocus();
                //((EditText) findViewById(R.id.amount)).setText("0");
                ((EditText) findViewById(R.id.amount)).setError("For 'Use' card, amount should be 0");
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e9) {

                }
                return;
            }

        }

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        ContentValues values = new ContentValues();

        values.put("CardNumber", cardnumber);
        values.put("CardExpiryDate", expdate);
        values.put("cvv", cvv);
        values.put("balance", Integer.parseInt(amount));
        values.put("status", "A");
        values.put("cardType", cardType);
        values.put("Email", emailID);

        try {
            long isValid = helper.addCard(values);

            if (isValid > 0) {
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.putExtra("EmailID", emailID);
                startActivity(intent);
                finish();
            } else {
                ((EditText) findViewById(R.id.cardnumber)).setError("Card Number already exists");
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e10) {

                }
                return;
            }
        }
        catch (Exception e11) {
            ((EditText) findViewById(R.id.cardnumber)).setError("Card Number already exists");
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e12) {

            }
            return;
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
