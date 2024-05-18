package com.example.educationalsoftware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MyPreferences";
    public SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private TextView questionTextView;
    private RadioGroup choicesRadioGroup;
    private RadioButton choice1RadioButton, choice2RadioButton, choice3RadioButton;
    private String correctAnswer;
    private Long quizId, attemptId, questionId;
    private Integer questionCounter;
    private Float score, correctAnswersCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        questionTextView = findViewById(R.id.question_text);
        choicesRadioGroup = findViewById(R.id.choices_radio_group);
        choice1RadioButton = findViewById(R.id.choice_1);
        choice2RadioButton = findViewById(R.id.choice_2);
        choice3RadioButton = findViewById(R.id.choice_3);
        correctAnswer = "";
        questionCounter = 0;
        correctAnswersCounter = 0.0F;

        initializeExtras();
        createAttempt();
        loadRandomQuestionBasedOnDifficultyLevel();
    }

    private void initializeExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            quizId = extras.getLong("quizId");
        }
    }

    private void loadRandomQuestionBasedOnDifficultyLevel(){
        String difficultyLevel = sharedPreferences.getString("level", "LOW");
        String url = "http://10.0.2.2:8080/api/v1/quizzes/" + quizId + "/questions/" + difficultyLevel;
        String accessToken = sharedPreferences.getString("accessToken", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        // Request a JSON response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        try {
                            String questionText = response.getString("questionText");
                            String choice1 = response.getString("choice1");
                            String choice2 = response.getString("choice2");
                            String choice3 = response.getString("choice3");
                            String questionType = response.getString("questionType");
                            correctAnswer = response.getString("correctAnswer");
                            questionId = response.getLong("id");

                            // Update UI with the fetched data
                            updateUI(questionText, choice1, choice2, choice3, questionType);
                        } catch (JSONException e) {
                            Log.e("QuizActivity", "JSON parsing error: " + e.getMessage());
                            Toast.makeText(QuizActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("QuizActivity", "Volley error: " + error.getMessage());
                        Toast.makeText(QuizActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void updateUI(String questionText, String choice1, String choice2, String choice3, String questionType) {
        questionTextView.setText(questionText);
        choice1RadioButton.setText(choice1);
        choice2RadioButton.setText(choice2);
        if (questionType.equals("TRUE_FALSE")){
            choice3RadioButton.setVisibility(View.INVISIBLE);
        }else {
            choice3RadioButton.setVisibility(View.VISIBLE);
            choice3RadioButton.setText(choice3);
        }

    }

    public void submit(View view){
        int selectedId = choicesRadioGroup.getCheckedRadioButtonId();

        if (selectedId != -1) {
            // Get the selected radio button
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedChoice = selectedRadioButton.getText().toString();

            // Check if the selected choice is correct
            if (selectedChoice.equals(correctAnswer)) {
                saveAnswer(selectedChoice, true);
                questionCounter += 1;
                correctAnswersCounter += 1;
                questionCounterCheck();
                increaseDifficulty();
                loadRandomQuestionBasedOnDifficultyLevel();
                Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                saveAnswer(selectedChoice, false);
                questionCounter += 1;
                questionCounterCheck();
                decreaseDifficulty();
                loadRandomQuestionBasedOnDifficultyLevel();
                Toast.makeText(QuizActivity.this, "Incorrect. Try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(QuizActivity.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
        }
    }

    private void questionCounterCheck() {
        if (questionCounter == 3){
            score = (correctAnswersCounter/3);
            updateAttempt();
            Intent intent = new Intent(this, QuizResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("quizId", quizId);
            startActivity(intent);
        }
    }

    private void updateAttempt() {
        String url = "http://10.0.2.2:8080/api/v1/attempts/"+attemptId;

        String accessToken = sharedPreferences.getString("accessToken", "");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("score", score);
        } catch (JSONException e) {
            Log.e("updateAttempt", "Error creating JSON request body: " + e.getMessage());
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url,
                requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("updateAttempt", "Answer created!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("updateAttempt", "Error creating answer: " + error.getMessage());
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.d("updateAttempt", "Success block");
                return Response.success(new JSONObject(), HttpHeaderParser.parseCacheHeaders(response));
            }
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void saveAnswer(String selectedChoice, Boolean isCorrect) {
        String url = "http://10.0.2.2:8080/api/v1/attempts/"+attemptId+"/questions/"+questionId+"/answers";

        String accessToken = sharedPreferences.getString("accessToken", "");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userAnswer", selectedChoice);
            requestBody.put("isCorrect", isCorrect);
        } catch (JSONException e) {
            Log.e("createAnswer", "Error creating JSON request body: " + e.getMessage());
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("createAnswer", "Answer created!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("createAnswer", "Error creating answer: " + error.getMessage());
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.d("createAnswer", "Success block");
                return Response.success(new JSONObject(), HttpHeaderParser.parseCacheHeaders(response));
            }
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void increaseDifficulty() {
        String currentDifficulty = sharedPreferences.getString("level", "LOW");
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (currentDifficulty) {
            case "LOW":
                editor.putString("level", "MEDIUM");
                editor.apply();
                break;
            case "MEDIUM":
                editor.putString("level", "HIGH");
                editor.apply();
                break;
            default:
                editor.putString("level", "HIGH");
                editor.apply();  // Keep at 'hard'
        }
    }

    public void decreaseDifficulty() {
        String currentDifficulty = sharedPreferences.getString("level", "LOW");
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (currentDifficulty) {
            case "HIGH":
                editor.putString("level", "MEDIUM");
                editor.apply();
                break;
            case "MEDIUM":
                editor.putString("level", "LOW");
                editor.apply();
                break;
            default:
                editor.putString("level", "LOW");
                editor.apply();  // Keep at 'easy'
        }
    }

    private void createAttempt(){
        Long customerId = sharedPreferences.getLong("id", 1);
        String url = "http://10.0.2.2:8080/api/v1/customers/" + customerId + "/quizzes/" + quizId + "/attempts";

        String accessToken = sharedPreferences.getString("accessToken", "");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("score", 120.0);
        } catch (JSONException e) {
            Log.e("createAttempt", "Error creating JSON request body: " + e.getMessage());
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    attemptId = response.getLong("id");
                } catch (JSONException e) {
                    Log.e("createAttempt", "JSON parsing error: " + e.getMessage());
                    Toast.makeText(QuizActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                }
                Log.d("createAttempt", "Attempt created!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("createAttempt", "Error creating attempt: " + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}