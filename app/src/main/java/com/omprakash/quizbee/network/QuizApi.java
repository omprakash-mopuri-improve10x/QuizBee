package com.omprakash.quizbee.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizApi {

    public QuizApiService createQuizApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://crudcrud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        QuizApiService quizApiService = retrofit.create(QuizApiService.class);
        return quizApiService;
    }
}
