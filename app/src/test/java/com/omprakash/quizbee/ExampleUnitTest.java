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
        String s= "[{\"_id\":\"647ec79f456f2b03e80bb62e\",\"module\":{\"number\":1,\"name\":\"Java & Android\",\"questions\":5,\"revision\":\"2023-06-06\"},\"questions\":[{\"number\":1,\"question\":\"1 byte contains\",\"answers\":[\"4 bits\",\"8 bits\",\"2 bits\",\"16 bits\"],\"correct_answer\":2},{\"number\":2,\"question\":\"Android apk stands for \",\"answers\":[\"Application Package\",\"App Package Kit\",\"App Product Kit\",\"Application Protect\"],\"correct_answer\":1},{\"number\":3,\"question\":\"Which is not a java OOPs Concept\",\"answers\":[\"Class\",\"Polymorphism\",\"interface\",\"Inheritance\"],\"correct_answer\":3},{\"number\":4,\"question\":\"Which is not a java keyword\",\"answers\":[\"public\",\"default\",\"static\",\"void\"],\"correct_answer\":2},{\"number\":5,\"question\":\"Which language is not used for Android app development\",\"answers\":[\"Java\",\"Kotlin\",\"Both\",\"C++\"],\"correct_answer\":4}]}]";
    }
}