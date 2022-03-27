package com.laurennguyen.drinkwater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.laurennguyen.drinkwater.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;

    public static final String EXTRA_ADD = "com.laurennguyen.drinkwater.EXTRA_ADD";


    private View.OnClickListener btn_add_clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            double sum = 0;
            int num250 = Integer.parseInt(nullCheck(binding.et250.getText()));
            int num500 = Integer.parseInt(nullCheck(binding.et500.getText()));
            int num750 = Integer.parseInt(nullCheck(binding.et750.getText()));
            double numEst = Double.parseDouble(nullCheck(binding.etEstimate.getText()));
            sum += num250*0.25 + num500*0.5 + num750*0.75 + numEst;

            Intent intent = new Intent();
            intent.putExtra(EXTRA_ADD, sum);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnAdd.setOnClickListener(btn_add_clickListener);
    }

    private String nullCheck(Editable editable) {
        String s = editable.toString();
        if(s.length() == 0) return "0";
        return s;
    }
}