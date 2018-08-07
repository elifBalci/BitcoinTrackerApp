package com.example.elifb.bitcointracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;


public class BitcoinPriceManager extends AppCompatActivity {
    final String LOGCAT_TAG = "BitcoinTracker";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BitcoinTracker","on create");
        setContentView(R.layout.activity_main);

    }
    public String parseJson(JSONObject response){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response.toString());
        JsonObject bpi = jsonObject.get("bpi").getAsJsonObject();
        JsonObject tryObject = bpi.get("TRY").getAsJsonObject();
        return tryObject.get("rate").getAsString();
    }

}
