package com.vymosoftware.git_pull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vymosoftware.git_pull.API.API;
import com.vymosoftware.git_pull.API.PullModel;
import com.vymosoftware.git_pull.API.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PullsActivity extends AppCompatActivity {

    private String owner_name ,repo_name;
    private RecyclerView recyclerView;
    private ProgressBar loader;
    private List<PullModel> pullModels;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulls);

        owner_name = getIntent().getStringExtra("owner_name");
        repo_name = getIntent().getStringExtra("repo_name");



        loader = findViewById(R.id.loader);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        recyclerView.setVisibility(View.INVISIBLE);
        pullModels =new ArrayList<PullModel>();


        Retrofit retrofit = RetrofitClient.getRetrofit(owner_name+"/"+repo_name);
        api = retrofit.create(API.class);
        getPullListDetails();


    }

    private void getPullListDetails() {
        loader.setVisibility(View.VISIBLE);

        Call<List<PullModel>> listCall =api.getPulls();
        listCall.enqueue(new Callback<List<PullModel>>() {
            @Override
            public void onResponse(Call<List<PullModel>> call, Response<List<PullModel>> response) {
                if (response.isSuccessful()){
                    List<PullModel> modelList =response.body();
                    if (modelList.size()>0){
                        loader.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        Toast.makeText(PullsActivity.this, "Sucess"+modelList.get(0).getTitle()+modelList.get(0).getState(), Toast.LENGTH_SHORT).show();
                        PullAdapter adapter =new PullAdapter(modelList ,getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else {

                    }

                }
                else {
                    loader.setVisibility(View.GONE);
                    Toast.makeText(PullsActivity.this, "Server Error Code :"+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PullModel>> call, Throwable t) {
                loader.setVisibility(View.GONE);

                Log.d("error", t.toString());
                Toast.makeText(PullsActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}