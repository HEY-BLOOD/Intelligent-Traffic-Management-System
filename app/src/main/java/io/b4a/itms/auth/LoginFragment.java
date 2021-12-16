package io.b4a.itms.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseUser;

import io.b4a.itms.R;
import io.b4a.itms.utils.AuthHelper;
import io.b4a.itms.utils.AuthUtil;
import io.b4a.itms.utils.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends AuthFragment {
    MaterialButton loginButton;
    MaterialButton cancelButton;
    TextInputEditText usernameEditText;
    TextInputEditText passwordEditText;
    TextInputLayout usernameInputLayout;
    TextInputLayout passwordInputLayout;
    TextView signUpView;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = rootView.findViewById(R.id.login_button);
        cancelButton = rootView.findViewById(R.id.cancel_button);
        usernameEditText = rootView.findViewById(R.id.username_edit_text);
        passwordEditText = rootView.findViewById(R.id.password_edit_text);
        usernameInputLayout = rootView.findViewById(R.id.username_input_layout);
        passwordInputLayout = rootView.findViewById(R.id.password_input_layout);
        signUpView = rootView.findViewById(R.id.sign_up_text);

        loginButton.setOnClickListener(view -> {
            // TODO check username and password
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (!AuthUtil.isUsernameValid(username)) {
                passwordInputLayout.setError(null);
                usernameInputLayout.setError(getString(R.string.username_is_not_valid_text));
            } else if (!AuthUtil.isPasswordValid(password)) {
                usernameInputLayout.setError(null);
                passwordInputLayout.setError(getString(R.string.password_is_not_valid_text));
            } else {
                usernameInputLayout.setError(null);
                passwordInputLayout.setError(null);
                loginUser(username, password);
            }
        });
        cancelButton.setOnClickListener(view -> finishActivity());
        signUpView.setOnClickListener(view -> loginToSignUp());

        return rootView;
    }

    private void loginUser(String username, String password) {
//        DialogUtil.showLoadingDialog(getContext());
        ParseUser.logInInBackground(username, password, (user, e) -> {
            if (user != null) {
                // Hooray! The user is logged in.
                ToastUtil.showShortToast(getContext(), getString(R.string.user_logged_in_successfully));
//                DialogUtil.dismissDialog();
                AuthHelper.launchDashboard(getContext());
                finishActivity();
            } else {
//                DialogUtil.dismissDialog();
                // Login failed. Look at the ParseException to see what happened.
                ToastUtil.showShortToast(getContext(), e.getMessage());
            }
        });
    }

}