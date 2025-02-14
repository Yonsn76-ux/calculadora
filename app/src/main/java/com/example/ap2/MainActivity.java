package com.example.ap2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mostResultado;
    private StringBuilder currentInput;
    private double firstOperand;
    private String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostResultado = findViewById(R.id.most_resultado);
        currentInput = new StringBuilder();

        // Configurar listeners para botones numéricos
        setNumberButtonListeners();

        // Configurar listeners para botones de operación
        setOperationButtonListeners();

        // Configurar listener para el botón igual
        Button btnIgual = findViewById(R.id.igu);
        btnIgual.setOnClickListener(v -> calculateResult());

        // Configurar listener para el botón DEL
        Button btnDel = findViewById(R.id.DEL);
        btnDel.setOnClickListener(v -> deleteLastChar());
    }

    private void setNumberButtonListeners() {
        int[] numberIds = {R.id.n0, R.id.n1, R.id.n2, R.id.n3, R.id.n4, R.id.n5, R.id.n6, R.id.n7, R.id.n8, R.id.n9};
        for (int id : numberIds) {
            findViewById(id).setOnClickListener(this::onNumberClick);
        }
    }

    private void setOperationButtonListeners() {
        int[] opIds = {R.id.sum, R.id.rest, R.id.mult, R.id.div};
        for (int id : opIds) {
            findViewById(id).setOnClickListener(this::onOperationClick);
        }
    }

    private void onNumberClick(View view) {
        Button button = (Button) view;
        currentInput.append(button.getText());
        updateDisplay();
    }

    private void onOperationClick(View view) {
        Button button = (Button) view;
        if (currentInput.length() > 0) {
            firstOperand = Double.parseDouble(currentInput.toString());
            operator = button.getText().toString();
            currentInput.setLength(0);
            updateDisplay();
        }
    }

    private void calculateResult() {
        if (currentInput.length() > 0 && operator != null) {
            double secondOperand = Double.parseDouble(currentInput.toString());
            double result = 0;
            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        mostResultado.setText("Error");
                        return;
                    }
                    break;
            }
            currentInput.setLength(0);
            currentInput.append(result);
            updateDisplay();
            operator = null;
        }
    }

    private void deleteLastChar() {
        if (currentInput.length() > 0) {
            currentInput.deleteCharAt(currentInput.length() - 1);
            updateDisplay();
        }
    }

    private void updateDisplay() {
        mostResultado.setText(currentInput.toString());
    }
}