package io.b4a.itms.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseUser;

import io.b4a.itms.R;
import io.b4a.itms.utils.AuthUtil;
import io.b4a.itms.utils.DialogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends AuthFragment {

    TextInputLayout usernameInputLayout;
    TextInputLayout emailInputLayout;
    TextInputLayout passwordInputLayout;
    TextInputLayout passwordConfirmInputLayout;

    TextInputEditText usernameEditText;
    TextInputEditText emailEditText;
    TextInputEditText passwordEditText;
    TextInputEditText passwordConfirmEditText;
    MaterialButton cancelButton;
    MaterialButton signUpButton;

    public SignUpFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        usernameInputLayout = rootView.findViewById(R.id.username_input_layout);
        emailInputLayout = rootView.findViewById(R.id.email_input_layout);
        passwordInputLayout = rootView.findViewById(R.id.password_input_layout);
//        passwordConfirmInputLayout = rootView.findViewById(R.id.password_confirm_input_layout);

        usernameEditText = rootView.findViewById(R.id.username_edit_text);
        emailEditText = rootView.findViewById(R.id.email_edit_text);
        passwordEditText = rootView.findViewById(R.id.password_edit_text);
        passwordConfirmEditText = rootView.findViewById(R.id.password_confirm_edit_text);
        signUpButton = rootView.findViewById(R.id.sign_up_button);
        cancelButton = rootView.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(view -> {
            backToLogin();
        });

        signUpButton.setOnClickListener(view -> {
            final String username = usernameEditText.getText().toString();
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();
            final String passwordConfirm = passwordConfirmEditText.getText().toString();

            if (!AuthUtil.isUsernameValid(username)) {
                emailInputLayout.setError(null);
                passwordInputLayout.setError(null);
                passwordConfirmInputLayout.setError(null);
                usernameInputLayout.setError(getString(R.string.username_is_not_valid_text));
            } else if (!AuthUtil.isEmailValid(email)) {
                usernameInputLayout.setError(null);
                passwordInputLayout.setError(null);
                passwordConfirmInputLayout.setError(null);
                emailInputLayout.setError(getString(R.string.password_is_not_valid_text));
            } else if (!AuthUtil.isPasswordValid(password)) {
                usernameInputLayout.setError(null);
                emailInputLayout.setError(null);
                passwordConfirmInputLayout.setError(null);
                passwordInputLayout.setError(getString(R.string.password_is_not_valid_text));
            }
            // FIXME it not work
            else if (!AuthUtil.isPasswordConfirmValid(password, passwordConfirm)) {
                usernameInputLayout.setError(null);
                emailInputLayout.setError(null);
                passwordInputLayout.setError(null);
                passwordConfirmEditText.setError(getString(R.string.password_is_not_valid_text));
            } else {
                signUpUser(username, email, password);
            }
        });

        return rootView;
    }

    private void signUpUser(String username, String email, String password) {
        DialogUtil.showLoadingDialog(getContext());
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Other fields can be set just like any other ParseObject,
        // using the "put" method, like this: user.put("attribute", "its value");
        // If this field does not exists, it will be automatically created
        user.signUpInBackground(e -> {
            DialogUtil.dismissDialog();
            if (e == null) {
                // Hooray! Let them use the app now.
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("用户注册");
                builder.setMessage("用户注册成功，是否立即返回登录？");
                // add the buttons
                builder.setPositiveButton("确定", (dialog, which) -> signUpToLogin());

                builder.setNegativeButton("取消", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void backToLogin() {
        NavHostFragment.findNavController(this).navigateUp();
    }
}