package com.home.giftcardmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.helper.Card;
import com.helper.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class TransferActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        Intent intent = getIntent();
        String email = intent.getStringExtra("EmailID").toString();
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
