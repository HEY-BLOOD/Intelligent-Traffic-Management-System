package vip.zhonghui.edu.ui.fragment02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import vip.zhonghui.edu.R;
import vip.zhonghui.edu.databinding.Fragment02Binding;

public class Fragment02 extends Fragment {

    private Fragment02Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = Fragment02Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        textView.setText(R.string.menu_fragment_02);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}