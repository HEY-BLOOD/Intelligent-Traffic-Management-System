package io.b4a.itms.auth;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import io.b4a.itms.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment {

    public AuthFragment() {
        // Required empty public constructor
    }

    protected void finishActivity() {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            fragmentActivity.finish();
        }
    }

    protected void loginToSignUp() {
        NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_signUpFragment);
    }

    protected void signUpToLogin() {
        NavHostFragment.findNavController(this).navigate(R.id.action_signUpFragment_to_loginFragment);
    }
}
