package com.example.minimoneybox.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.minimoneybox.R;
import com.example.minimoneybox.entities.Plan;
import com.example.minimoneybox.entities.UserAccountJSON;
import com.google.gson.Gson;

import java.util.List;

import static android.view.View.GONE;

public class UserAccountActivity extends AppCompatActivity {

    private TextView greeting;
    private TextView totalPlanValue;
    private LinearLayout plansLayout;
    private String json;
    private String token;// This is just to pass it to the OneOffPaymentActivity
    private String name;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_accounts);
        this.json = getIntent().getStringExtra("json");
        this.token = getIntent().getStringExtra("token");
        this.name = getIntent().getStringExtra("name");
        this.gson = new Gson();
        setupViews();
    }

    private void setupViews() {
        greeting = findViewById(R.id.greeting);
        if (name != null) {
            greeting.setText("Hello " + name + " !");
        } else {
            greeting.setVisibility(GONE);
        }
        totalPlanValue = findViewById(R.id.totalPlanValue);
        plansLayout = findViewById(R.id.plansLayout);

        UserAccountJSON jsonResponse = gson.fromJson(json, UserAccountJSON.class);
        List<Plan> plans = jsonResponse.getProductResponses();

        String totPlanValue = getString(R.string.total_plan_value) + jsonResponse.getTotalPlanValue();

        totalPlanValue.setText(totPlanValue);

        int count = 0;
        for (Plan plan : plans) {
            boolean even = count % 2 == 0;

            // inflating layout, generating view
            LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View planView = inflater.inflate(R.layout.plan_view, null);

            // coloring the view background
            ConstraintLayout background = planView.findViewById(R.id.planLayoutBackground);
            background.setBackgroundColor(
                    even ? ContextCompat.getColor(this, R.color.lightGreen) : ContextCompat.getColor(this, R.color.lightOrange));

            // identifying the view elements
            TextView planName = planView.findViewById(R.id.planName);
            TextView planValue = planView.findViewById(R.id.planValue);
            TextView planMoneybox = planView.findViewById(R.id.planMoneybox);
            Button planDetailButton = planView.findViewById(R.id.planDetailButton);

            // setting the values of the view elements
            planName.setText(plan.getProduct().getFriendlyName());
            planValue.setText(getString(R.string.plan_value) + plan.getPlanValue());
            planMoneybox.setText(getString(R.string.moneybox_value) + plan.getMoneybox());
            String planJson = gson.toJson(plan);
            planDetailButton.setTag(planJson);

            // setting listener on the button
            planDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserAccountActivity.this, OneOffPaymentActivity.class);
                    intent.putExtra("json", v.getTag().toString());
                    intent.putExtra("token", token);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    finish();
                }
            });

            // adding the view
            plansLayout.addView(planView);
            count++;
        }
    }
}