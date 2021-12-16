package io.b4a.itms.ui.learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import io.b4a.itms.R;

public class ShowVideoActivity extends AppCompatActivity {

    public static final String SHOW_VIDEO_RES_ID = "show_video_res_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);


        Intent intent = getIntent();
        int resId = intent.getIntExtra(SHOW_VIDEO_RES_ID, 0);
        if (resId == 0) {
            return;
        }

        VideoView videoView = findViewById(R.id.pec_video_view);

//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//
//        videoView.setZOrderMediaOverlay(true);
//        videoView.setZOrderOnTop(true);

        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + resId));
        videoView.requestFocus();
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
                Toast.makeText(ShowVideoActivity.this, "视频播放结束", Toast.LENGTH_SHORT).show();
            }
        });
    }
}