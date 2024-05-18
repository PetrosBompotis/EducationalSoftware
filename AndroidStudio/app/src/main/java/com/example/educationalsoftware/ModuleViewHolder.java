package com.example.educationalsoftware;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ModuleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView moduleTitleTextView, moduleDescriptionTextView;
    List<ModuleResponse> modules;
    public ModuleViewHolder(@NonNull View itemView, List<ModuleResponse> modules) {
        super(itemView);
        this.modules = modules;

        moduleTitleTextView = itemView.findViewById(R.id.moduleTitleTextView);
        moduleDescriptionTextView = itemView.findViewById(R.id.moduleDescriptionTextView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ModuleResponse module = modules.get(getAdapterPosition());
        Context context = itemView.getContext();
        Intent intent = new Intent(context, ModuleDetailActivity.class);
        intent.putExtra("id", module.getId());
        intent.putExtra("title", module.getTitle());
        intent.putExtra("textContent", module.getTextContent());
        intent.putExtra("extendedTextContent", module.getExtendedTextContent());
        ArrayList<String> imageUrlsArrayList = new ArrayList<>(module.getImageUrls());
        intent.putStringArrayListExtra("imageUrls", imageUrlsArrayList);
        intent.putExtra("videoUrl", module.getVideoUrl());
        context.startActivity(intent);
    }
}
