package com.example.myapplication9231;

import android.os.Bundle;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);  // 使用 MainActivity1 的布局

        // 绑定按钮，触发对话框
        Button showDialogButton = findViewById(R.id.showDialogButton);
        showDialogButton.setOnClickListener(v -> showCustomDialog());
    }

    // 显示自定义 AlertDialog
    private void showCustomDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);

        EditText usernameInput = dialogView.findViewById(R.id.username);
        EditText passwordInput = dialogView.findViewById(R.id.password);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)  // 禁止点击外部关闭对话框
                .create();

        // 绑定自定义按钮的事件
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button signInButton = dialogView.findViewById(R.id.signInButton);

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        signInButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Welcome, " + username, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
