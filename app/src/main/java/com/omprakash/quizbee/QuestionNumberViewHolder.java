package com.omprakash.quizbee;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omprakash.quizbee.databinding.QuestionNumberItemBinding;

public class QuestionNumberViewHolder extends RecyclerView.ViewHolder {

    QuestionNumberItemBinding binding;

    public QuestionNumberViewHolder(QuestionNumberItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
