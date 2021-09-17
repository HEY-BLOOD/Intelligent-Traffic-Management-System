package vip.zhonghui.edu.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vip.zhonghui.edu.R;
import vip.zhonghui.edu.databinding.ActivitySettingBinding;
import vip.zhonghui.edu.utils.SharedPreferencesUtil;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBarNo.toolbar.setTitle(R.string.network_setting);
        setSupportActionBar(binding.appBarNo.toolbar);
        binding.appBarNo.toolbar.setNavigationIcon(R.drawable.back);
        binding.appBarNo.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.ip.setText(SharedPreferencesUtil.getString(this, "ip", ""));
        binding.port.setText(SharedPreferencesUtil.getString(this, "port", ""));

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.putString(SettingActivity.this,
                        "ip", binding.ip.getText().toString() + "");
                SharedPreferencesUtil.putString(SettingActivity.this,
                        "port", binding.port.getText().toString() + "");
                Toast.makeText(SettingActivity.this,
                        R.string.save_commit,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}