package com.example.educationalsoftware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private RequestQueue requestQueue;
    private UserActivity userActivity;
    private List<ModuleResponse> modulesList;
    private List<String> imageUrlList;
    private ModuleAdapter moduleAdapter;
    private RecyclerView moduleRecyclerView;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        moduleRecyclerView = view.findViewById(R.id.modulesRecyclerView);
        moduleRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadModules();
        return view;
    }

    private void loadModules(){
        modulesList = new ArrayList<>();
        String url = "http://10.0.2.2:8080/api/v1/modules";
        String accessToken = userActivity.sharedPreferences.getString("accessToken", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                imageUrlList = new ArrayList<>();
                                JSONObject moduleJson = response.getJSONObject(i);
                                Long moduleId = moduleJson.getLong("id");
                                String title = moduleJson.getString("title");
                                String textContent = moduleJson.getString("textContent");
                                String description = moduleJson.getString("description");
                                JSONArray imagesArray = moduleJson.getJSONArray("imageUrls");
                                for (int j = 0; j < imagesArray.length(); j++) {
                                    try {
                                        String imageUrl = imagesArray.getString(j);
                                        imageUrlList.add(imageUrl);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                String videoUrl = moduleJson.getString("videoUrl");

                                modulesList.add(new ModuleResponse(moduleId,title,textContent,description,imageUrlList,videoUrl));
                            }
                            moduleAdapter = new ModuleAdapter(modulesList);
                            moduleRecyclerView.setAdapter(moduleAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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