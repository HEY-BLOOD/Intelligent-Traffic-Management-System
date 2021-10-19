package vip.zhonghui.edu.ui.fragment03.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vip.zhonghui.edu.R;

public class ShowImageActivity extends AppCompatActivity {

    public static final String SHOW_IMAGE_RES_ID = "show_image_res_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Intent intent = getIntent();
        int resId = intent.getIntExtra(SHOW_IMAGE_RES_ID, 0);
        if (resId == 0) {
            return;
        }

        ImageView imageView = findViewById(R.id.pec_image_view);

        imageView.setImageResource(resId);
        imageView.setOnTouchListener(new ImageZoom());
    }


    class ImageZoom implements View.OnTouchListener {

        private int model;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    model = 1;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    model = 2;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (model == 2) {
                        float x = event.getX(0);
                        float x1 = event.getX(1);
                        float y = event.getY(0);
                        float y1 = event.getY(1);
                        double sqrt = Math.sqrt((x - x1) * (x - x1) * (y - y1) * (y - y1));
                        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                        layoutParams.width = (int) sqrt;
                        layoutParams.height = (int) sqrt;
                        v.setLayoutParams(layoutParams);
                    }
                    break;
            }
            return true;
        }
    }
}