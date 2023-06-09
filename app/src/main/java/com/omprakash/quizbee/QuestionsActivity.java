package com.omprakash.quizbee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
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
    private int currentQuestionPosition = 0;
    private Integer[] answerOptionsIndexes;

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
        handleRadioGroup();
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
                    answerOptionsIndexes = new Integer[quizzes.get(0).getQuestions().size()];
                    showData(questions.get(0));
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
                showData(question);
            }
        });
    }

    private void setupQuestionNumbersRv() {
        binding.questionNumbersRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.questionNumbersRv.setAdapter(questionNumbersAdapter);
    }

    private void showData(Question question) {
        showQuestion(question);
        setQuestionNumberColor();
        setPreviousBtnVisibility();
        setNextAndSubmitBtnsVisibility();
    }

    private void showQuestion(Question question) {
        currentQuestionPosition = question.getNumber() - 1;
        binding.textView.setText("Q). " + question.getQuestion());
        binding.radioGroup.clearCheck();
        binding.option1Rb.setText(question.getAnswers().get(0));
        binding.option2Rb.setText(question.getAnswers().get(1));
        binding.option3Rb.setText(question.getAnswers().get(2));
        binding.option4Rb.setText(question.getAnswers().get(3));
        if (answerOptionsIndexes[currentQuestionPosition] != null) {
            if (answerOptionsIndexes[currentQuestionPosition] == 0) {
                binding.option1Rb.setChecked(true);
            } else if (answerOptionsIndexes[currentQuestionPosition] == 1) {
                binding.option2Rb.setChecked(true);
            } else if (answerOptionsIndexes[currentQuestionPosition] == 2) {
                binding.option3Rb.setChecked(true);
            } else if (answerOptionsIndexes[currentQuestionPosition] == 3) {
                binding.option4Rb.setChecked(true);
            }
        }
    }

    private void setQuestionNumberColor() {
        questionNumbersAdapter.currentQuestionPosition = currentQuestionPosition;
        questionNumbersAdapter.notifyDataSetChanged();
    }

    private void setPreviousBtnVisibility() {
        if (currentQuestionPosition == 0) {
            binding.previousBtn.setVisibility(View.GONE);
        } else {
            binding.previousBtn.setVisibility(View.VISIBLE);
        }
    }

    private void setNextAndSubmitBtnsVisibility() {
        if (currentQuestionPosition == questions.size() - 1) {
            binding.nextBtn.setVisibility(View.GONE);
            binding.submitBtn.setVisibility(View.VISIBLE);
        } else {
            binding.nextBtn.setVisibility(View.VISIBLE);
            binding.submitBtn.setVisibility(View.GONE);
        }
    }

    private void handleNext() {
        binding.nextBtn.setOnClickListener(v -> {
            currentQuestionPosition++;
            Question question = questions.get(currentQuestionPosition);
            showData(question);

        });
    }

    private void handlePrevious() {
        binding.previousBtn.setOnClickListener(v -> {
            currentQuestionPosition--;
            Question question = questions.get(currentQuestionPosition);
            showData(question);
        });
    }

    private void handleRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.option1Rb.isChecked()) {
                    answerOptionsIndexes[currentQuestionPosition] = 0;
                } else if (binding.option2Rb.isChecked()) {
                    answerOptionsIndexes[currentQuestionPosition] = 1;
                } else if (binding.option3Rb.isChecked()) {
                    answerOptionsIndexes[currentQuestionPosition] = 2;
                } else if (binding.option4Rb.isChecked()) {
                    answerOptionsIndexes[currentQuestionPosition] = 3;
                }
            }
        });
    }
}