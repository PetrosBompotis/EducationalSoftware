package com.example.educationalsoftware;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ModuleDetailActivity extends AppCompatActivity {
    private Long id;
    private String textContent, title, videoUrl;
    private TextView moduleDetailTitleTextView, moduleContentTextView;
    private ImageView moduleImageView1, moduleImageView2;
    private Button button;
    private VideoView moduleVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_detail);
        initViews();
        initializeExtras();
        setUpListeners();
    }

    private void initViews() {
        moduleDetailTitleTextView = findViewById(R.id.moduleDetailTitleTextView);
        moduleContentTextView = findViewById(R.id.moduleContentTextView);
        //moduleImageView1 = findViewById(R.id.moduleImageView1);
        //moduleImageView2 = findViewById(R.id.moduleImageView2);
        moduleVideoView = findViewById(R.id.moduleVideoView);
        button = findViewById(R.id.submit_button);
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
            videoUrl = extras.getString("videoUrl");
            ArrayList<String> imageUrls = extras.getStringArrayList("imageUrls");

            moduleDetailTitleTextView.setText(title);
            moduleContentTextView.setText(Html.fromHtml(textContent, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    int drawableId;
                    switch (source) {
                        case "https://picsum.photos/300":
                            drawableId = R.drawable.exercise; // Replace with your actual drawable resource ID
                            break;
                        case "https://picsum.photos/200":
                            drawableId = R.drawable.exercise; // Replace with your actual drawable resource ID
                            break;
                        case "https://picsum.photos/id/237/200/300":
                            drawableId = R.drawable.exercise; // Replace with your actual drawable resource ID
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

            if (imageUrls != null && !imageUrls.isEmpty()) {
                //Glide.with(this).load(imageUrls.get(0)).into(moduleImageView1);
                if (imageUrls.size() > 1) {
                    //Glide.with(this).load(imageUrls.get(1)).into(moduleImageView2);
                }
            }

            // Set video URI to VideoView
            if (videoUrl != null && !videoUrl.isEmpty()) {
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(moduleVideoView);
                moduleVideoView.setMediaController(mediaController);
                moduleVideoView.setVideoURI(Uri.parse(videoUrl));
                moduleVideoView.requestFocus();
                moduleVideoView.start(); // Start playing the video
            }
        }
    }
}
