package com.laurennguyen.drinkwater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.laurennguyen.drinkwater.databinding.ActivityGoalBinding;

public class GoalActivity extends AppCompatActivity {

    private ActivityGoalBinding binding;
    public static final String EXTRA_GOAL = "com.laurennguyen.drinkwater.EXTRA_GOAL";

    private View.OnClickListener btn_submit_goal_clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            double goal = Integer.valueOf(binding.etGoal.getText().toString());
            Intent intent = new Intent();
            intent.putExtra(EXTRA_GOAL, goal);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSubmitGoal.setOnClickListener(btn_submit_goal_clickListener);
    }
}