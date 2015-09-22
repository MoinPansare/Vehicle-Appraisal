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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.victor.loading.newton.NewtonCradleLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import vehicleappraisal.com.vehicleappraisal.network.VolleySingelton;

public class MainActivity extends AppCompatActivity {

    private NewtonCradleLoading newtonCradleLoading;
    private View loadingView;
    private TextView loadingTextView;

    private View userNameView, passwordView;
    private EditText userName, password;
    private Button loginButton;
    private int backPressed = 0;
    private Runnable task,task1,task2;
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    private final String loginUrl = "http://testwip.northside.co.uk/Broker/Broker.svc/Authenticate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading1);
        loadingView = (View)findViewById(R.id.loadingView1);
        loadingTextView = (TextView)findViewById(R.id.loadingTextView1);

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

        if (usNAME.length() == 0) {
            Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
            userNameView.setBackgroundColor(Color.RED);
            return;
        }
        else{
            userNameView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (paWord.length() == 0) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            passwordView.setBackgroundColor(Color.RED);
            return;
        }
        else{
            passwordView.setBackgroundColor(Color.TRANSPARENT);
        }

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", usNAME);
//        params.put("password", paWord);

        JSONObject params = new JSONObject();
        params.put("username", usNAME);
        params.put("password", paWord);

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, loginUrl, params,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        stopLoading();
                        NavigateToHomePage(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stopLoading();
                        showError(1,"");
                    }
                }

        );

        startLoading();
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

            String code = response.getString("ErrorCode");
            if( !code.equalsIgnoreCase("2") &&  response.get("ErrorCode") != null){
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

//                task1 = new Runnable() {
//                    public void run() {
//                        userName.setText("");
//
//                    }
//                };
//                worker.schedule(task1, 2, TimeUnit.SECONDS);
//
//                task2 = new Runnable() {
//                    public void run() {
//                        password.setText("");
//
//                    }
//                };
//                worker.schedule(task2, 2, TimeUnit.SECONDS);
//
//                userName.requestFocus();



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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(backPressed == 0){
                backPressed = 1;
                Toast.makeText(this,"Press Back Again To Exit",Toast.LENGTH_SHORT).show();
                task = new Runnable() {
                    public void run() {
                        backPressed = 0;
                    }
                };
                worker.schedule(task, 2, TimeUnit.SECONDS);
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);

    }

    public void startLoading(){
        newtonCradleLoading.setAlpha((float) 1.0);
        newtonCradleLoading.start();
        loadingView.setAlpha((float) 0.7);
        loadingTextView.setAlpha((float) 1.0);
    }

    public void stopLoading(){
        newtonCradleLoading.setAlpha((float) 0.0);
        newtonCradleLoading.stop();
        loadingView.setAlpha((float) 0.0);
        loadingTextView.setAlpha((float) 0.0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        password.setText("");
        userName.setText("");
        userName.requestFocus();
    }
}
