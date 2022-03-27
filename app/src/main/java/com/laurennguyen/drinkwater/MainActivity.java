package com.laurennguyen.drinkwater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.laurennguyen.drinkwater.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public static final int FROM_GOAL_ACTIVITY = 2;
    public static final int FROM_ADD_ACTIVITY = 1;
    public static final double SIZE_250 = 0.25;
    public static final double SIZE_500 = 0.5;
    public static final double SIZE_750 = 0.75;

    private static double goal = 2;
    private static double remaining = 2;

    private View.OnClickListener ib_reset_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            remaining = goal;
            populateRecommends();
            binding.tvAmountRemaining.setText(padDouble(remaining) + " L");
            binding.pbProgress.setProgress(0);
            saveItems();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadItems();
        populateRecommends();
        binding.ibReset.setOnClickListener(ib_reset_clickListener);
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
                Intent goalIntent = new Intent(this, GoalActivity.class);
                startActivityForResult(goalIntent, FROM_GOAL_ACTIVITY);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case FROM_ADD_ACTIVITY:
                double add = data.getDoubleExtra(AddActivity.EXTRA_ADD, 0);

                binding.pbProgress.setProgress(binding.pbProgress.getProgress()+(int) (add/goal*100),true);

                remaining -= add;
                if(remaining < 0) remaining = 0;

                populateRecommends();

                binding.tvAmountRemaining.setText(padDouble(remaining) + " L");
                saveItems();
                break;
            case FROM_GOAL_ACTIVITY:
                double newGoal = data.getDoubleExtra(GoalActivity.EXTRA_GOAL, 2);
                binding.tvGoal.setText("/ " + padDouble(newGoal) + " L");

                remaining = newGoal - (goal-remaining);
                if(remaining < 0) remaining = 0;

                populateRecommends();

                binding.tvAmountRemaining.setText(padDouble(remaining) + " L");
                binding.pbProgress.setProgress((int)((newGoal-remaining)/newGoal*100));
                goal = newGoal;
                saveItems();
                break;
        }
    }
    public static String padDouble(double num) {
        return String.format("%,.1f", num);
    }
    private void populateRecommends() {
        binding.tvRcm250.setText(padDouble(remaining/SIZE_250));
        binding.tvRcm500.setText(padDouble(remaining/SIZE_500));
        binding.tvRcm750.setText(padDouble(remaining/SIZE_750));

    }
    //save the data
    //create a method to get the data file
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }
    //Load the item by reading the file
    private void loadItems() {
        try {
            File file = getDataFile();
            if(file.isFile()) {
                Scanner myReader = new Scanner(file);
                String[] line = myReader.nextLine().split(" ");
                goal = Double.parseDouble(line[0]);
                remaining = Double.parseDouble(line[1]);
                binding.tvAmountRemaining.setText(padDouble(remaining) + " L");
                binding.tvGoal.setText("/ " + padDouble(goal) + " L");
                binding.pbProgress.setProgress((int)((goal -remaining)/goal*100));
                myReader.close();
            }
        }
        catch(IOException ioException ) {
            ioException.printStackTrace();
        }
    }

    //Save items by writing them into a file
    private void saveItems() {
        try {
            FileWriter myWriter = new FileWriter(getDataFile());
            myWriter.write(goal + " " + remaining);
            myWriter.close();
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }
}