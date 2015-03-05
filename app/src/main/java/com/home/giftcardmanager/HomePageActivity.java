package com.home.giftcardmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.helper.Card;
import com.helper.DataBaseHelper;

import java.util.List;


public class HomePageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TableLayout cardtable = (TableLayout) findViewById(R.id.cardtable);

        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        List list = helper.FetchCards();
        Card card = null;
        TableRow row = null;
        TextView tv1 = null;
        TextView tv2 = null;
        for (int i=0; i < list.size(); i++){

                card = (Card)list.get(i);
                row = new TableRow(this);
                tv1 = new TextView(this);
                tv1.setTextSize(20);
                tv1.setText(card.getCardNumber());

                tv2 = new TextView(this);
                tv2.setTextSize(20);
                tv2.setText("12.00"/*card.getBalance()*/);

                row.addView(tv1);
                row.addView(tv2);
                cardtable.addView(row);
        }


       /* Intent intent = getIntent();
        String name ="Welcome "+ intent.getStringExtra("UserName").toString()+"!!!";
        TextView tv = (TextView) this.findViewById(R.id.Name);
        tv.setTextSize(60);
        tv.setText(name);*/
    }

    public void saveCard(View view){
        Intent i1 = new Intent(getApplicationContext(), CardDetails.class);
        startActivity(i1);
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
