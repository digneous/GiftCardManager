package com.home.giftcardmanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.helper.Card;
import com.helper.DataBaseHelper;

import java.util.List;


public class TransactionHistoryActivity extends ActionBarActivity {
    public String emailID = null;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        Intent intent = getIntent();
        String email = intent.getStringExtra("EmailID").toString();
        this.emailID = email;
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        List TranHisList = helper.FetchTransactionHistory(email);

        ArrayAdapter<String> TransHist = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TranHisList);
        listView = (ListView)findViewById(R.id.TransactionHistorylistView);
        listView.setAdapter(TransHist);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction_history, menu);
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


    public void signout(View view){
        Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i1);
        finish();
    }

    public void goback (View view) {
        Intent intent = getIntent();
        String email = intent.getStringExtra("EmailID").toString();
        Intent i1 = new Intent(getApplicationContext(), HomePageActivity.class);
        i1.putExtra("EmailID",email);
        startActivity(i1);
        finish();
    }
}
