package com.example.minimoneybox.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import com.example.minimoneybox.R;
import com.example.minimoneybox.entities.Plan;
import com.google.gson.Gson;

public class OneOffPaymentActivity extends AppCompatActivity {

    private TextView planName;
    private TextView planValue;
    private TextView planMoneybox;
    private Button addButton;
    private String json;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_account);
        this.json = getIntent().getStringExtra("json");
        this.gson = new Gson();
        setupViews();
    }

    private void setupViews() {
        this.planName = findViewById(R.id.planName);
        this.planValue = findViewById(R.id.planValue);
        this.planMoneybox = findViewById(R.id.planMoneybox);
        this.addButton = findViewById(R.id.addButton);

        Plan plan = gson.fromJson(json, Plan.class);
        planName.setText(plan.getProduct().getFriendlyName());
        planValue.setText("Plan Value: £" + plan.getPlanValue());
        planMoneybox.setText("Moneybox: £" + plan.getMoneybox());
    }
}
