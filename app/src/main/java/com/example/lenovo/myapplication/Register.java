package com.example.lenovo.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.support.v7.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.lenovo.myapplication.api.BmobService;
import com.example.lenovo.myapplication.api.Client;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private Button login,register;
    private TextInputLayout phone,password,passwordConfiguration;
    private TextInputEditText phone_context,password_context,passwordConfiguration_context;
    private String phone_str,password_str,passwordConfiguration_str;
    private void checkUser() {
        phone.setError("");
        password.setError("");
        passwordConfiguration.setError("");
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(phone_str);
        if (TextUtils.isEmpty(phone_str)) {
            phone.setError("Input your phone number!");
            return;
        }
        if (!m.matches()){
            phone.setError("Invalid phone number (invalid symbol)!");
            return;
        }
        if(phone_str.length()!=11){
            phone.setError("Invalid phone number (must be 11 bits)!");
            return;
        }
        if (TextUtils.isEmpty(password_str)) {
            password.setError("Input your password!");
            return;
        }
        if (TextUtils.isEmpty(passwordConfiguration_str)) {
            passwordConfiguration.setError("Input your password configuration!");
            return;
        }
        if (!password_str.equals(passwordConfiguration_str)) {
            passwordConfiguration.setError("Password mismatch!");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",phone_str);
            jsonObject.put("password",password_str);
//            jsonObject.put("mobilePhoneNumber",tel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //将json转为请求体
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),jsonObject.toString());

        //使用retrofit发送请求
        BmobService service = Client.retrofit.create(BmobService.class);
        Call<ResponseBody> call = service.postUser(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(Register.this, "Register Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                } else if (response.code() == 400) {
                    phone.setError("This number is already taken!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        login = findViewById(R.id.register_login);
        phone = findViewById(R.id.register_phone);
        password = findViewById(R.id.register_password);
        passwordConfiguration = findViewById(R.id.register_passwordConfiguration);
        phone_context = findViewById(R.id.register_phone_context);
        password_context = findViewById(R.id.register_password_context);
        passwordConfiguration_context = findViewById(R.id.register_passwordConfiguration_context);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
        register = findViewById(R.id.register_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_str = phone_context.getText().toString();
                password_str = password_context.getText().toString();
                passwordConfiguration_str = passwordConfiguration_context.getText().toString();
                checkUser();
            }
        });
    }
}
