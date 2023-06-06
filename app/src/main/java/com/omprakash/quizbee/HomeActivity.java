package com.omprakash.quizbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.omprakash.quizbee.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleStart();
    }

    private void handleStart() {
        binding.startBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuestionsActivity.class);
            startActivity(intent);
        });
    }
}