package com.example.elifb.bitcointracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    final String PRICE_USD_URL = "https://blockchain.info/tobtc?currency=USD&value=500";
    final String PRICE_URL = "https://blockchain.info/ticker";
    final String PRICE_TRY_URL = "https://api.coindesk.com/v1/bpi/currentprice/try.json";
    final String LOGCAT_TAG = "BitcoinTracker";
    private TextView price ;
    private Button getPriceButton;
    private BitcoinPriceManager bitcoinPriceManager ;
    protected static String rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        price = (TextView)findViewById(R.id.price);
        getPriceButton = (Button) findViewById(R.id.getPriceButton) ;

        bitcoinPriceManager = new BitcoinPriceManager();
        doNetworking();

        getPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(LOGCAT_TAG,"In click listener");
                doNetworking();
            }
        });
    }

    public void doNetworking(){
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(PRICE_TRY_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                String rate = bitcoinPriceManager.parseJson(response);
                price.setText(rate);
                Log.d(LOGCAT_TAG,rate);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Toast.makeText(getApplicationContext(), "İnternete bağlanıp tekrar deneyiniz.", Toast.LENGTH_LONG).show();
                Log.e(LOGCAT_TAG, "Fail " + e.toString());
                Log.d(LOGCAT_TAG, "Status code " + statusCode);
                Log.d(LOGCAT_TAG, "Here's what we got instead " + response.toString());
            }

        });
    }
}
