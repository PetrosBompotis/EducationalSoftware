package com.example.educationalsoftware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatisticsFragment extends Fragment {
    private RequestQueue requestQueue;
    private UserActivity userActivity;
    private TextView quiz1TotalAttempts, quiz1BestScore, quiz2TotalAttempts, quiz2BestScore,
            quiz3TotalAttempts, quiz3BestScore, revisionQuizTotalAttempts, revisionQuizBestScore;
    private Float bestScore;
    private Integer totalAttempts;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(requireContext());
        userActivity = (UserActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        initViews(view);
        fetchUserStats(1L);

        return view;
    }

    private void initViews(View view) {
        quiz1TotalAttempts = view.findViewById(R.id.quiz1_total_attempts);
        quiz1BestScore = view.findViewById(R.id.quiz1_best_score);
        quiz2TotalAttempts = view.findViewById(R.id.quiz2_total_attempts);
        quiz2BestScore = view.findViewById(R.id.quiz2_best_score);
        quiz3TotalAttempts = view.findViewById(R.id.quiz3_total_attempts);
        quiz3BestScore = view.findViewById(R.id.quiz3_best_score);
        revisionQuizTotalAttempts = view.findViewById(R.id.revision_quiz_total_attempts);
        revisionQuizBestScore = view.findViewById((R.id.revision_quiz_best_score));
    }

    private void fetchUserStats(Long quizId) {
        bestScore = 0F;
        totalAttempts = 0;
        Long customerId = userActivity.sharedPreferences.getLong("id", 1);
        String url = "http://10.0.2.2:8080/api/v1/customers/"+customerId+"/quizzes/"+quizId+"/attempts";

        String accessToken = userActivity.sharedPreferences.getString("accessToken", "");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject attemptJson = response.getJSONObject(i);
                                Float score = (float) attemptJson.getDouble("score");
                                if (score > bestScore){
                                    bestScore = score;
                                }
                                totalAttempts += 1;
                            }
                        } catch (JSONException e) {
                            Log.e("fetchUserStats", "JSON parsing error: " + e.getMessage());
                            Toast.makeText(requireContext(), "Error parsing data", Toast.LENGTH_SHORT).show();
                        }

                        if (quizId == 1) {
                            quiz1TotalAttempts.setText(String.valueOf(totalAttempts));
                            quiz1BestScore.setText(String.valueOf(bestScore));
                            fetchUserStats(2L);

                        } else if (quizId == 2) {
                            quiz2TotalAttempts.setText(String.valueOf(totalAttempts));
                            quiz2BestScore.setText(String.valueOf(bestScore));
                            fetchUserStats(3L);

                        } else if (quizId == 3) {
                            quiz3TotalAttempts.setText(String.valueOf(totalAttempts));
                            quiz3BestScore.setText(String.valueOf(bestScore));
                            fetchUserStats(4L);

                        } else {
                            revisionQuizTotalAttempts.setText(String.valueOf(totalAttempts));
                            revisionQuizBestScore.setText(String.valueOf(bestScore));

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("fetchUserStats", "Volley error: " + error.getMessage());
                        Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }
}