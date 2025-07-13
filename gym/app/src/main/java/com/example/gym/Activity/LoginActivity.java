package com.example.gym.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.Api.ApiService;
import com.example.gym.Model.LoginRequest;
import com.example.gym.Model.LoginResponse;
import com.example.gym.Model.User;
import com.example.gym.databinding.LoginActivityBinding;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;

    private EditText userName;
    private EditText password;

    LoginActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        BindingView();
        BindingAction();
    }

    private void BindingView() {

        loginBtn = binding.loginBtn;
        userName = binding.emailPhone;
        password = binding.password;
    }

    private void BindingAction() {
        loginBtn.setOnClickListener(v -> {
            String _userName = userName.getText().toString();
            String _passWord = password.getText().toString();
            if (_userName.isEmpty() || _passWord.isEmpty()) {
                return;
            }


            LoginRequest request = new LoginRequest(_userName, _passWord);
            myRetrofitAPI.login(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.d("Error", "Loi xay ra " + response);

                    if(response.isSuccessful()){
                        LoginResponse loginData = response.body();
                        String fullName = loginData.getFullName();
                        Log.d("FULL_NAME", "Tên người dùng: " + fullName);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Log.e("LOGIN_FAIL", "Code: " + response.code());
                        try {
                            Log.e("LOGIN_FAIL", "Error: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    String text = t.getMessage();
                    Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
                }


           });
        });
    }




    public static final String BASE_URL = "http://10.0.2.2:5000/";


    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    ApiService myRetrofitAPI = retrofit.create(ApiService.class);
}
