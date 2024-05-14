package com.example.educationalsoftware;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ModuleDetailActivity extends AppCompatActivity {
    private Long id;
    private String textContent, title, videoUrl;
    private TextView moduleDetailTitleTextView, moduleContentTextView;
    private ImageView moduleImageView1, moduleImageView2;
    private VideoView moduleVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_detail);
        initViews();
        initializeExtras();
    }

    private void initViews() {
        moduleDetailTitleTextView = findViewById(R.id.moduleDetailTitleTextView);
        moduleContentTextView = findViewById(R.id.moduleContentTextView);
        //moduleImageView1 = findViewById(R.id.moduleImageView1);
        //moduleImageView2 = findViewById(R.id.moduleImageView2);
        moduleVideoView = findViewById(R.id.moduleVideoView);
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

                    // Map the image source (URL) to a drawable resource ID
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
                    return getResources().getDrawable(drawableId);
                }
            }, null));
            // Load images into ImageViews

            if (imageUrls != null && !imageUrls.isEmpty()) {
                //Glide.with(this).load(imageUrls.get(0)).into(moduleImageView1);
                if (imageUrls.size() > 1) {
                    //Glide.with(this).load(imageUrls.get(1)).into(moduleImageView2);
                }
            }

            // Set video URI to VideoView
            if (videoUrl != null && !videoUrl.isEmpty()) {
                moduleVideoView.setVideoURI(Uri.parse(videoUrl));
                moduleVideoView.start(); // Start playing the video
            }
        }
    }
}