package com.vymosoftware.git_pull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText owner_name, repo_name;
    private Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        owner_name = findViewById(R.id.owner_name);
        repo_name = findViewById(R.id.repository_name);
        Submit = findViewById(R.id.submitbtn);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(owner_name.getText().toString().trim())){
                    Toast.makeText(MainActivity.this, "Enter owner name !", Toast.LENGTH_SHORT).show();
                }
                else  if (TextUtils.isEmpty(repo_name.getText().toString().trim())){
                    Toast.makeText(MainActivity.this, "Enter repository name !", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(MainActivity.this, PullsActivity.class)
                            .putExtra("owner_name", owner_name.getText().toString().trim())
                            .putExtra("repo_name", repo_name.getText().toString().trim()));
                }

            }
        });
    }
}