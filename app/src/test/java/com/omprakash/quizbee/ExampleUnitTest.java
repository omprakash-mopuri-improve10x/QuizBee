package com.omprakash.quizbee;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.omprakash.quizbee.network.QuizApi;
import com.omprakash.quizbee.network.QuizApiService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getQuiz() throws IOException {
        QuizApi quizApi = new QuizApi();
        QuizApiService quizApiService = quizApi.createQuizApiService();
        Call<List<Quiz>> call = quizApiService.fetchQuiz();
        List<Quiz> quizzes = call.execute().body();
        assertNotNull(quizzes);
        assertFalse(quizzes.isEmpty());
        System.out.println(new Gson().toJson(quizzes));
    }
}