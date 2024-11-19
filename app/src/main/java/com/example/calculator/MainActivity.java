package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Double a, b;
    private Boolean isOperationClick;
    private String currentOperation;
    private StringBuilder currentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.text);
        isOperationClick = false;
        currentOperation = "";
        currentInput = new StringBuilder("0");
        textView.setText(currentInput.toString());
    }

    public void oneNumberClick(View view) {
        String buttonText = ((MaterialButton) view).getText().toString();

        if (buttonText.equals("AC")) {
            currentInput.setLength(0);
            currentInput.append("0");
            textView.setText(currentInput.toString());
            a = 0.0;
            b = 0.0;
            currentOperation = "";
        } else if (buttonText.equals(".")) {
            if (!currentInput.toString().contains(".")) {
                currentInput.append(".");
                textView.setText(currentInput.toString());
            }
        } else if (textView.getText().toString().equals("0") || isOperationClick) {
            currentInput.setLength(0);
            currentInput.append(buttonText);
            textView.setText(currentInput.toString());
        } else {
            currentInput.append(buttonText);
            textView.setText(currentInput.toString());
        }

        isOperationClick = false;
    }

    public void oneOperarionClick(View view) {
        String buttonText = ((MaterialButton) view).getText().toString();

        if (buttonText.equals("+")) {
            currentOperation = "+";
        } else if (buttonText.equals("-")) {
            currentOperation = "-";
        } else if (buttonText.equals("*")) {
            currentOperation = "*";
        } else if (buttonText.equals("/")) {
            currentOperation = "/";
        }

        a = Double.valueOf(currentInput.toString());
        isOperationClick = true;
    }

    public void equalClick(View view) {
        b = Double.valueOf(currentInput.toString());
        Double result = 0.0;

        switch (currentOperation) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b != 0) {
                    result = a / b;
                } else {
                    Snackbar.make(view, "Нельзя делить на ноль", Snackbar.LENGTH_SHORT).show();
                    isOperationClick = true;
                    return;
                }
                break;
        }

        textView.setText(String.valueOf(result));
        currentInput.setLength(0);
        currentInput.append(result);
        isOperationClick = true;
    }
}
