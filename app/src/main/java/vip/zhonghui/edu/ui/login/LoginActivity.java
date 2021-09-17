package vip.zhonghui.edu.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vip.zhonghui.edu.MainActivity;
import vip.zhonghui.edu.R;
import vip.zhonghui.edu.databinding.ActivityLoginBinding;
import vip.zhonghui.edu.ui.setting.SettingActivity;
import vip.zhonghui.edu.url.Api;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            loadingProgressBar.setVisibility(View.GONE);
            loginButton.setEnabled(true);
            switch (msg.what) {
                case 0:
                    Toast.makeText(LoginActivity.this,
                            R.string.login_error,
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(LoginActivity.this,
                            R.string.login_success,
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    private ProgressBar loadingProgressBar;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBarNo.toolbar.setTitle(R.string.login);
        setSupportActionBar(binding.appBarNo.toolbar);
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        loginButton = binding.login;
        loadingProgressBar = binding.loading;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String untext = usernameEditText.getText().toString();
                String pwtext = passwordEditText.getText().toString();
                if (untext.isEmpty() || pwtext.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            R.string.login_tips,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginButton.setEnabled(false);
                login(untext, pwtext);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void login(String untext, String pwtext) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Api.getRootURL(LoginActivity.this) + Api.URL_LOGIN);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestProperty("Content-type", "application/json");
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                    JSONObject object = new JSONObject();
                    object.put("UserName", untext);
                    object.put("UserPwd", pwtext);
                    out.append(object.toString());
                    out.flush();
                    out.close();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                        String line;
                        String res = "";
                        while ((line = reader.readLine()) != null) {
                            res += line;
                        }
                        reader.close();
                        JSONObject dataJson = new JSONObject(res);
                        String result = dataJson.getString("RESULT");
                        if ("S".equals(result.toUpperCase())) {
                            handler.sendEmptyMessage(1);
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    } else {
                        handler.sendEmptyMessage(0);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }
}