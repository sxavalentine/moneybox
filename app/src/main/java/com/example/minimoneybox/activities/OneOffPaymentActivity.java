package com.example.minimoneybox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.minimoneybox.R;
import com.example.minimoneybox.entities.Plan;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class OneOffPaymentActivity extends AppCompatActivity {

    private TextView planName;
    private TextView planValue;
    private TextView planMoneybox;
    private Button addButton;
    private Button backButton;
    private Gson gson;
    private Plan plan;
    private String token;
    private String name;// This is just to pass it back to UserAccountActivity after completing a payment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_account);
        this.gson = new Gson();
        String json = getIntent().getStringExtra("json");
        this.plan = gson.fromJson(json, Plan.class);
        this.token = getIntent().getStringExtra("token");
        this.name = getIntent().getStringExtra("name");
        setupViews();
    }

    private void setupViews() {
        this.planName = findViewById(R.id.planName);
        this.planValue = findViewById(R.id.planValue);
        this.planMoneybox = findViewById(R.id.planMoneybox);
        this.addButton = findViewById(R.id.addButton);
        this.backButton = findViewById(R.id.backButton);

        setButtonListeners();

        planName.setText(plan.getProduct().getFriendlyName());
        planValue.setText("Plan Value: £" + plan.getPlanValue());
        planMoneybox.setText("Moneybox: £" + plan.getMoneybox());
    }

    private void setButtonListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                String url = "https://api-test01.moneyboxapp.com/oneoffpayments";

                FormBody.Builder formBuilder = new FormBody.Builder()
                        .add("Amount", "10")
                        .add("InvestorProductId", plan.getId().toString());

                RequestBody formBody = formBuilder.build();

                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", token)
                        .addHeader("AppId","3a97b932a9d449c981b595")
                        .addHeader("Content-Type","application/json")
                        .addHeader("appVersion",	"5.10.0")
                        .addHeader("apiVersion",	"3.0.0")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response){
                        if (response.isSuccessful()) {
                            returnToUserAccountActivity();
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToUserAccountActivity();
            }
        });
    }

    private void returnToUserAccountActivity() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api-test01.moneyboxapp.com/investorproducts";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", token)
                .addHeader("AppId","3a97b932a9d449c981b595")
                .addHeader("Content-Type","application/json")
                .addHeader("appVersion",	"5.10.0")
                .addHeader("apiVersion",	"3.0.0")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    String myResponse = response.body().string();

                    Intent intent = new Intent(OneOffPaymentActivity.this, UserAccountActivity.class);
                    if (name != null) {
                        intent.putExtra("name", name);
                    }
                    intent.putExtra("json", myResponse);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
}
