package com.laurennguyen.drinkwater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.laurennguyen.drinkwater.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public static final int FROM_GOAL_ACTIVITY = 2;
    public static final int FROM_ADD_ACTIVITY = 1;

    private static double goal = 2;
    private static double remaining = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Handles clicks on menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                //Navigate to compose screen
                Intent addIntent = new Intent(this, AddActivity.class);
                startActivityForResult(addIntent, FROM_ADD_ACTIVITY);
                break;
            case R.id.goal:
                Intent goalIntent = new Intent(this, AddActivity.class);
                startActivityForResult(goalIntent, FROM_GOAL_ACTIVITY);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case FROM_ADD_ACTIVITY:
                double add = data.getDoubleExtra(AddActivity.EXTRA_ADD, 0);
                Toast.makeText(this, String.valueOf(add), Toast.LENGTH_LONG).show();
                binding.pbProgress.setProgress(binding.pbProgress.getProgress()+(int) (add/goal*100),true);
                if (add > remaining) {
                    binding.tvAmountRemaining.setText("0 L");
                }
                else {
                    binding.tvAmountRemaining.setText( (remaining - add) + " L");
                }
                break;
            case FROM_GOAL_ACTIVITY:
                double newGoal = data.getDoubleExtra(GoalActivity.EXTRA_GOAL, 2);
                binding.tvGoal.setText("/ " + newGoal + " L");
                binding.pbProgress.setProgress((int)((goal-remaining)/newGoal*100));
                goal = newGoal;
                break;
        }
    }
}