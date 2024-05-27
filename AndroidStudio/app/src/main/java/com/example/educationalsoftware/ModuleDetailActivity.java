package com.example.educationalsoftware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ModuleDetailActivity extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "MyPreferences";
    SharedPreferences sharedPreferences;
    private Long id;
    private String textContent, extendedTextContent, title, videoUrl;
    private TextView moduleDetailTitleTextView, moduleContentTextView, redirectToQuizTextView;
    private Button button;
    private VideoView moduleVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_detail);
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        initViews();
        initializeExtras();
        setUpListeners();
    }

    private void initViews() {
        moduleDetailTitleTextView = findViewById(R.id.moduleDetailTitleTextView);
        moduleContentTextView = findViewById(R.id.moduleContentTextView);
        moduleVideoView = findViewById(R.id.moduleVideoView);
        redirectToQuizTextView = findViewById(R.id.redirectToQuizTextView);
        button = findViewById(R.id.redirectToQuizButton);
    }

    private void setUpListeners(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModuleDetailActivity.this, QuizActivity.class);
                intent.putExtra("quizId", id);
                startActivity(intent);
            }
        });
    }

    private void initializeExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id");
            title = extras.getString("title");
            textContent = extras.getString("textContent");
            extendedTextContent = extras.getString("extendedTextContent");
            videoUrl = extras.getString("videoUrl");
            ArrayList<String> imageUrls = extras.getStringArrayList("imageUrls");

            moduleDetailTitleTextView.setText(title);
            moduleContentTextView.setText(Html.fromHtml(
                    sharedPreferences.getString("level", "LOW").equals("HIGH") ? extendedTextContent : textContent,
                    new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    int drawableId;
                    switch (source) {
                        case "enotita1_eikona1":
                            drawableId = R.drawable.outputonlinepngtools2; // Replace with your actual drawable resource ID
                            break;
                        case "enotita1_eikona2":
                            drawableId = R.drawable.outputonlinepngtools; // Replace with your actual drawable resource ID
                            break;
                        case "enotita2_eikona1":
                            drawableId = R.drawable.outputonlinepngtools1; // Replace with your actual drawable resource ID
                            break;
                        case "enotita2_eikona2":
                            drawableId = R.drawable.outputonlinepngtools5; // Replace with your actual drawable resource ID
                            break;
                        case "enotita3_eikona1":
                            drawableId = R.drawable.outputonlinepngtools3; // Replace with your actual drawable resource ID
                            break;
                        case "enotita3_eikona2":
                            drawableId = R.drawable.outputonlinepngtools4; // Replace with your actual drawable resource ID
                            break;
                        default:
                            return null; // No matching image found
                    }

                    // Get the Drawable from resources
                    Drawable drawable = ContextCompat.getDrawable(ModuleDetailActivity.this, drawableId);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    }
                    return drawable;
                }
            }, null));

            if(id == 4){
                moduleVideoView.setVisibility(View.GONE);
                redirectToQuizTextView.setVisibility(View.GONE);
            }

            if (videoUrl != null && !videoUrl.isEmpty()) {
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(moduleVideoView);
                moduleVideoView.setMediaController(mediaController);
                moduleVideoView.setVideoURI(Uri.parse(videoUrl));
                moduleVideoView.requestFocus();
                moduleVideoView.start();
            }
        }
    }
}
