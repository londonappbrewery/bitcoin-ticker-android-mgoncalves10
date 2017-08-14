package com.londonappbrewery.bitcointicker;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by mgonc on 8/10/2017.
 */

public class CurrencyDataModel {


    private String symbol;
    private String url;

    public String getURL() {
        return url + symbol;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }




    public static CurrencyDataModel fromJson(JSONObject jsonObject) {
        try {
            CurrencyDataModel currencyData = new CurrencyDataModel();
            //currencyData.setCurrencyData(jsonObject.getString("price"));
            //currencyData.mCurrency = jsonObject.getInt("price");
            String price = jsonObject.getString("price");
            Log.d("price ", price);
            return currencyData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}



