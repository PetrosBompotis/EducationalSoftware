package com.example.educationalsoftware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MyPreferences";
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        tokenValidation();
    }

    public void tokenValidation(){
        String savedAccessToken = sharedPreferences.getString("accessToken", null);
        String savedUsername = sharedPreferences.getString("name", null);

        String url = "http://10.0.2.2:8080/api/v1/auth/validate-token";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("accessToken", savedAccessToken);
            requestBody.put("username", savedUsername);
        } catch (JSONException e) {
            Log.e("RefreshToken", "Error creating JSON request body: " + e.getMessage());
            return;
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, url, requestBody,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MainActivity", "Token validation successful");
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "Error refreshing token: " + error.getMessage());
                        redirectToSignUp();
                    }
                }){
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        Log.d("Signup", "Success block");
                        return Response.success(new JSONObject(), HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

        requestQueue.add(jsonObjectRequest);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
        fragmentTransaction.commit();
    }

    public void redirectToSignUp() {
        replaceFragment(new SignUpFragment());
    }

    public void redirectToSignIn(){
        replaceFragment(new SignInFragment());
    }

    public void redirectToUserActivity(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void saveLoginDataToSharedPreferences(String accessToken, Long id, String name, String email, String level) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.putLong("id", id);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("level", level);
        editor.apply();
    }
}