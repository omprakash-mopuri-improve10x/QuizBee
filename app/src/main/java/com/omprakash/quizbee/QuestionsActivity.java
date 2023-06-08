package com.omprakash.quizbee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.omprakash.quizbee.databinding.ActivityQuestionsBinding;
import com.omprakash.quizbee.network.QuizApi;
import com.omprakash.quizbee.network.QuizApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity {

    private ActivityQuestionsBinding binding;
    private QuestionNumbersAdapter questionNumbersAdapter;
    private ArrayList<Question> questions = new ArrayList<>();
    private int currentQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Questions");
        getQuizzes();
        setupQuestionNumbersAdapter();
        setupQuestionNumbersRv();
        handleNext();
        handlePrevious();
    }

    private void getQuizzes() {
        QuizApi quizApi = new QuizApi();
        QuizApiService quizApiService = quizApi.createQuizApiService();
        Call<List<Quiz>> call = quizApiService.fetchQuiz();
        call.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                if (response.isSuccessful()) {
                    List<Quiz> quizzes = response.body();
                    questionNumbersAdapter.setQuestions(quizzes.get(0).getQuestions());
                    questions = quizzes.get(0).getQuestions();
                    onBindData(questions.get(0));
                }
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(QuestionsActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupQuestionNumbersAdapter() {
        questionNumbersAdapter = new QuestionNumbersAdapter();
        questionNumbersAdapter.setQuestions(questions);
        questionNumbersAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void OnItemClicked(Question question) {
                onBindData(question);
            }
        });
    }

    private void setupQuestionNumbersRv() {
        binding.questionNumbersRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.questionNumbersRv.setAdapter(questionNumbersAdapter);
    }

    private void onBindData(Question question) {
        currentQuestionNumber =  question.getNumber() - 1;
        binding.textView.setText("Q). " + question.getQuestion());
        binding.radioButton.setText(question.getAnswers().get(0));
        binding.radioButton2.setText(question.getAnswers().get(1));
        binding.radioButton3.setText(question.getAnswers().get(2));
        binding.radioButton4.setText(question.getAnswers().get(3));
    }

    public void handleNext() {
        binding.nextBtn.setOnClickListener(v -> {
            try {
                currentQuestionNumber++;
                Question question = questions.get(currentQuestionNumber);
                onBindData(question);
                questionNumbersAdapter.selectedQuestionNumber = currentQuestionNumber;
                questionNumbersAdapter.notifyDataSetChanged();
            } catch (IndexOutOfBoundsException ex) {
                Toast.makeText(this, "Questions completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handlePrevious() {
        binding.previousBtn.setOnClickListener(v -> {
            try {
                currentQuestionNumber--;
                Question question = questions.get(currentQuestionNumber);
                onBindData(question);
                questionNumbersAdapter.selectedQuestionNumber = currentQuestionNumber;
                questionNumbersAdapter.notifyDataSetChanged();
            } catch (IndexOutOfBoundsException ex) {
                Toast.makeText(this, "There is no previous question", Toast.LENGTH_SHORT).show();
            }
        });
    }
}