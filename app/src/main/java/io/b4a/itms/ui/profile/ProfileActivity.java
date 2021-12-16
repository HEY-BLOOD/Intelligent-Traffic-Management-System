package io.b4a.itms.ui.profile;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import io.b4a.itms.R;
import io.b4a.itms.data.parse_model.ParseUserExtensions;
import io.b4a.itms.databinding.ActivityProfileBinding;
import io.b4a.itms.utils.DateFormatUtil;
import io.b4a.itms.utils.ToastUtil;

public class ProfileActivity extends AppCompatActivity {

    public static final String ARG_OBJECT_ID = "objectId";

    private ParseUser mParseUser;

    private ActivityProfileBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Intent intent = getIntent();
        String userId = intent.getStringExtra(ARG_OBJECT_ID);

        setSupportActionBar(mBinding.toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (null != ab) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo(ARG_OBJECT_ID, userId);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.getObjectId().equals(userId)) {
            mParseUser = currentUser;
            setupView();
        } else {
            userQuery.getFirstInBackground((object, e) -> {
                if (e == null) {
                    mParseUser = object;
                    setupView();
                } else {
                    // Something went wrong.
                    ToastUtil.showShortToast(getBaseContext(), e.getMessage());
                }
            });
        }

    }

    private void setupView() {

        final String username = mParseUser.getUsername();
        mBinding.backdropUsername.setText(getString(R.string.at_username, username));

        String emailAddress = mParseUser.getEmail();
        mBinding.backdropEmail.setText(emailAddress);

        // loading user avatar
        ParseUserExtensions.loadAvatar(this, mParseUser, mBinding.profileAvatar);

        final String nickname = ParseUserExtensions.getNickname(mParseUser);
        mBinding.contentProfile.detailNickname.setText(nickname);

        final String phone = ParseUserExtensions.getPhone(mParseUser);
        mBinding.contentProfile.detailPhone.setText(phone);

        final String email = mParseUser.getEmail();
        mBinding.contentProfile.detailEmail.setText(email);

        final String address = ParseUserExtensions.getAddress(mParseUser);
        mBinding.contentProfile.detailAddress.setText(address);

        final String registerTime = DateFormatUtil.formatMedium(mParseUser.getCreatedAt());
        mBinding.contentProfile.detailBirthday.setText(registerTime);

        mBinding.editProfile.setOnClickListener(v ->
                ToastUtil.showShortToast(getBaseContext(), "TODO 编辑资料"));
    }

}
