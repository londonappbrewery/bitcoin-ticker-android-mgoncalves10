package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import static android.provider.UserDictionary.Words.APP_ID;
import static cz.msebera.android.httpclient.util.CharsetUtils.get;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
    private String tmpUrl;
    //    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/short?crypto=BTC&fiat=AUD,BRL,CAD,CNY,EUE,GBP,HKD,JPY,PLN,RUB,SEK,USD,ZAR";
    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                CurrencyDataModel currencyData = new CurrencyDataModel();
                currencyData.setSymbol(adapterView.getItemAtPosition(position).toString());
                Log.d("Bitcoin", "onItemSelected callback received");
                currencyData.setURL(BASE_URL);
                Log.d("Bitcoin", " URL is: " + currencyData.getURL());
               // mPriceTextView.setText(" "+Math.random());
                // Copy the value of Spinner to tmpUrl
                tmpUrl = currencyData.getURL();
                Log.d("Bitcoin", " spSymbol " + currencyData.getSymbol());
                RequestParams params = new RequestParams();
                params.put("url", currencyData.getURL());

                letsDoSomeNetworking(params);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(tmpUrl,null, new JsonHttpResponseHandler() {

            @Override
            public void onStart(){

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"

                Log.d("Bitcoin", "Response " + response.toString());
               // CurrencyDataModel currency = CurrencyDataModel.fromJson(response);
                //Log.d("Bitcoin", "currency " + currency.toString());
                CurrencyDataModel.fromJson(response);
                try{
                    JSONObject sys  = response.getJSONObject("changes");
                    //JSONArray changes = response.getJSONArray("changes");
                    JSONObject prices = sys.getJSONObject("price");
                    String aPrice = prices.getString("hour");

                   // String price = response.getString("price");
                    mPriceTextView.setText(aPrice);
                    Log.d("Bitcoin", "Price:" + aPrice);
                }
                catch (Exception ex)
                {
                    Log.d("", ex.getMessage());
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers,  Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin ", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + response + headers.toString());


            }

            @Override
            public void onRetry(int retryNo){

            }
        });

    }
}
