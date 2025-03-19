package com.example.numad24su_chunzhangliu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class PrimeActivity extends AppCompatActivity {

    private Button btnFindPrimes;
    private Button btnTerminateSearch;
    private Switch pacifierSwitch;
    private TextView tvCurrentNumber;
    private TextView tvLatestPrime;

    // 标识是否正在搜索
    private volatile boolean isSearching = false;

    // 搜索线程
    private Thread primeThread;

    // 当前正在检查的数字和最新找到的质数
    private int currentNumber = 3;
    private int latestPrime = 2;  // 先给个初始值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);

        // 1. 绑定布局中的控件
        btnFindPrimes = findViewById(R.id.btn_find_primes);
        btnTerminateSearch = findViewById(R.id.btn_terminate_search);
        pacifierSwitch = findViewById(R.id.switch_pacifier);
        tvCurrentNumber = findViewById(R.id.tv_current_number);
        tvLatestPrime = findViewById(R.id.tv_latest_prime);

        // 2. 如果有保存的状态，恢复一下
        if (savedInstanceState != null) {
            isSearching = savedInstanceState.getBoolean("IS_SEARCHING", false);
            currentNumber = savedInstanceState.getInt("CURRENT_NUMBER", 3);
            latestPrime = savedInstanceState.getInt("LATEST_PRIME", 2);
            boolean pacifierChecked = savedInstanceState.getBoolean("PACIFIER_CHECKED", false);
            pacifierSwitch.setChecked(pacifierChecked);
        }

        // 恢复 UI 显示
        tvCurrentNumber.setText("Current Number: " + currentNumber);
        tvLatestPrime.setText("Latest Prime: " + latestPrime);

        // 3. 设置按钮点击事件
        btnFindPrimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 若尚未在搜索，点击后开始搜索，并从 3 开始
                if (!isSearching) {
                    currentNumber = 3;  // 作业要求：每次新搜索都从3开始
                    startFindingPrimes();
                }
            }
        });

        btnTerminateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 若在搜索，则终止
                if (isSearching) {
                    stopFindingPrimes();
                }
            }
        });

        // 如果之前就在搜索（比如屏幕旋转导致 Activity 重建），继续搜索
        if (isSearching) {
            startFindingPrimes();
        }
    }

    /**
     * 启动后台线程，开始找质数
     */
    private void startFindingPrimes() {
        isSearching = true;
        primeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 在后台线程中不停找质数
                while (isSearching) {
                    if (isPrime(currentNumber)) {
                        latestPrime = currentNumber;
                        // 更新最新质数到 UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvLatestPrime.setText("Latest Prime: " + latestPrime);
                            }
                        });
                    }
                    // 更新“当前数字”到 UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCurrentNumber.setText("Current Number: " + currentNumber);
                        }
                    });

                    // 下一步，从当前数字+2 继续
                    currentNumber += 2;
                }
            }
        });
        primeThread.start();
    }

    /**
     * 终止搜索线程
     */
    private void stopFindingPrimes() {
        isSearching = false;
        primeThread = null;
        // 最新质数和当前数字留在界面上，不要清空
    }

    /**
     * 简单判断是否质数
     */
    private boolean isPrime(int n) {
        if (n < 2) return false;
        // 按作业要求，不做算法优化，最简单的循环
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理屏幕旋转等场景，保存数据
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("IS_SEARCHING", isSearching);
        outState.putInt("CURRENT_NUMBER", currentNumber);
        outState.putInt("LATEST_PRIME", latestPrime);
        outState.putBoolean("PACIFIER_CHECKED", pacifierSwitch.isChecked());
    }

    /**
     * 如果正在搜索，拦截系统Back按钮，先询问用户
     * 如果不在搜索，直接退出
     */
    @Override
    public void onBackPressed() {
        if (isSearching) {
            new AlertDialog.Builder(this)
                    .setTitle("Search in progress")
                    .setMessage("Are you sure you want to terminate search and exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            stopFindingPrimes();
                            PrimeActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}
