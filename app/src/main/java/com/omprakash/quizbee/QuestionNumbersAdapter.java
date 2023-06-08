package com.omprakash.quizbee;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omprakash.quizbee.databinding.QuestionNumberItemBinding;

import java.util.List;

public class QuestionNumbersAdapter extends RecyclerView.Adapter<QuestionNumberViewHolder> {

    private List<Question> questions;
    private OnItemActionListener onItemActionListener;
    public int selectedQuestionNumber = 0;

    void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    @NonNull
    @Override
    public QuestionNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionNumberItemBinding binding = QuestionNumberItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        QuestionNumberViewHolder questionNumberViewHolder = new QuestionNumberViewHolder(binding);
        return questionNumberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionNumberViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Question question = questions.get(position);
        holder.binding.questionNumberTxt.setText(String.valueOf(question.getNumber()));
        holder.binding.getRoot().setOnClickListener(v -> {
            selectedQuestionNumber = position;
            notifyDataSetChanged();
            onItemActionListener.OnItemClicked(question);
        });
        if (selectedQuestionNumber == position) {
            holder.binding.questionNumberTxt.setTextColor(Color.parseColor("#AC1D1D"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#AC1D1D"));
        } else {
            holder.binding.questionNumberTxt.setTextColor(Color.parseColor("#000000"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
