package com.example.educationalsoftware;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleViewHolder>{
    private List<ModuleResponse> modules;

    public ModuleAdapter(List<ModuleResponse> modules) {
        this.modules = modules;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item, parent, false);
        return new ModuleViewHolder(view, modules);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        ModuleResponse module = modules.get(position);
        holder.moduleTitleTextView.setText(module.getTitle());
        holder.moduleDescriptionTextView.setText(module.getDescription());
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }
}
