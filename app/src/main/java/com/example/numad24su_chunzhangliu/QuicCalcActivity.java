package com.example.numad24su_chunzhangliu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class QuicCalcActivity extends AppCompatActivity {

    private TextView tvCalcDisplay;
    private String expression = ""; // 用于保存当前输入的表达式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quic_calc);

        tvCalcDisplay = findViewById(R.id.tvCalcDisplay);

        // 示例：为按钮 7 设置点击事件
        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToExpression("7");
            }
        });

        // 同理，为其他数字和运算符按钮设置监听
        Button btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(v -> appendToExpression("8"));

        // … 为 btn9, btnPlus, btn4, btn5, ... 等添加监听

        // “x” 按钮作为删除键
        Button btnX = findViewById(R.id.btnX);
        btnX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expression.length() > 0) {
                    expression = expression.substring(0, expression.length() - 1);
                    tvCalcDisplay.setText(expression.isEmpty() ? "CALC" : expression);
                }
            }
        });

        // “=” 按钮计算表达式
        Button btnEqual = findViewById(R.id.btnEqual);
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int result = evaluateExpression(expression);
                    expression = String.valueOf(result);
                    tvCalcDisplay.setText(expression);
                } catch (Exception e) {
                    tvCalcDisplay.setText("Error");
                    expression = "";
                }
            }
        });
    }

    // 辅助方法：追加字符并更新显示
    private void appendToExpression(String str) {
        expression += str;
        tvCalcDisplay.setText(expression);
    }

    // 简单的表达式求值方法：仅支持正整数的加减运算
    private int evaluateExpression(String expr) {
        // 这里的实现仅作为示例，不处理运算优先级和错误情况
        int result = 0;
        // 用正则表达式分割数字（注意：如果表达式以负号开头可能需要特殊处理）
        String[] numbers = expr.split("[+-]");
        // 获取运算符
        List<Character> operators = new ArrayList<>();
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '+' || c == '-') {
                operators.add(c);
            }
        }
        result = Integer.parseInt(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {
            int num = Integer.parseInt(numbers[i]);
            char op = operators.get(i - 1);
            if (op == '+') {
                result += num;
            } else if (op == '-') {
                result -= num;
            }
        }
        return result;
    }
}