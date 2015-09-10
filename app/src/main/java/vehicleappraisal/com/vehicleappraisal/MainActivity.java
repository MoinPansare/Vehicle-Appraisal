package vehicleappraisal.com.vehicleappraisal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import vehicleappraisal.com.vehicleappraisal.network.VolleySingelton;

public class MainActivity extends AppCompatActivity {

    private View userNameView, passwordView;
    private EditText userName, password;
    private Button loginButton;

    private final String loginUrl = "http://testwip.northside.co.uk/Broker/Broker.svc/Authenticate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameView = findViewById(R.id.userNameLinearLayout);
        passwordView = findViewById(R.id.passwordlinearLayout);

        userName = (EditText) findViewById(R.id.userNameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validateAndCheck();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void validateAndCheck() throws JSONException {
        final String usNAME = userName.getText().toString();
        final String paWord = password.getText().toString();

//        if (usNAME.length() == 0) {
//            Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
//            userNameView.setBackgroundColor(Color.RED);
//        }
//        if (paWord.length() == 0) {
//            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
//            passwordView.setBackgroundColor(Color.RED);
//        }
        userNameView.setBackgroundColor(Color.TRANSPARENT);
        passwordView.setBackgroundColor(Color.TRANSPARENT);

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", usNAME);
//        params.put("password", paWord);

        JSONObject params = new JSONObject();
        params.put("username", "rg-115");
        params.put("password", "Ny88le15");

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, loginUrl, params,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        NavigateToHomePage(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError(1,"");
                    }
                }

        );

        VolleySingelton.getMy_Volley_Singelton_Reference().getRequestQueue().add(loginRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void NavigateToHomePage(JSONObject response) {

        try{
            if(response.get("ErrorCode") != null ){
                String accessToken = response.getString("TokenValue");
//                String userNameResponse = response.getString("UserName");

//                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPref.edit();
//
//
//                editor.putInt(getString(R.string.saved_high_score), newHighScore);
//                editor.commit();

//                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString(getString(R.string.userNameSharedPreference), userNameResponse);
//                editor.putString(getString(R.string.accessTokenSharedPreference), accessToken);
//                editor.apply();

                userName.setText("");
                password.setText("");

                Intent homeIntent = new Intent(this,Home.class);
                homeIntent.putExtra(getString(R.string.userName),"");
                homeIntent.putExtra(getString(R.string.accessToken),accessToken);
                startActivity(homeIntent);

                //reading
//                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//                int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
//                long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);

            }
            else{
                showError(2,response.getString("Message"));
            }
        }catch(Exception e){
            showError(1,"");
        }
    }

    public void showError(int code,String someMessage) {
        switch (code) {
            case 1:
                Toast.makeText(this, "Something went wrong \n Please try again after some time", Toast.LENGTH_LONG).show();
                break;
            case 2 :
                Toast.makeText(this, someMessage, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Something went wrong \n Please try again after some time", Toast.LENGTH_LONG).show();

        }
    }
}
