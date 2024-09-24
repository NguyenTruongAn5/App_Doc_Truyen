package com.truongan.btl_app_doc_truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.truongan.btl_app_doc_truyen.objects.MD5Utils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class dang_ki extends AppCompatActivity {
    private Button btnHome;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        btnHome = findViewById(R.id.btnHome);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(dang_ki.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                if (username.trim().isEmpty() || password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
                    Toast.makeText(dang_ki.this, "Username, password, and confirm password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (isValidRegistration(username, password, confirmPassword)) {
                        registerUser(username, password);
                    } else {
                        Toast.makeText(dang_ki.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidRegistration(String username, String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private void registerUser(final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", username)
                            .add("password", MD5Utils.md5(password))
                            .build();

                    Request request = new Request.Builder()
                            .url("https://mitdthu.000webhostapp.com/dang_ki_user.php")
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        handleRegistrationResponse(responseData);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(dang_ki.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleRegistrationResponse(final String responseData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonResponse = new JSONObject(responseData);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");

                    if (success) {
                        Toast.makeText(dang_ki.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(dang_ki.this, "Đăng ký không thành công: " + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
