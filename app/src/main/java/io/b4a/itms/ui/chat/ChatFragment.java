package io.b4a.itms.ui.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import io.b4a.itms.R;
import io.b4a.itms.base.BaseFragment;
import io.b4a.itms.data.parse_model.Message;
import io.b4a.itms.databinding.FragmentChatBinding;
import io.b4a.itms.utils.DialogUtil;
import io.b4a.itms.utils.InputMethodUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment implements ChatAdapter.OnAvatarClickListener {

    static final String TAG = ChatFragment.class.getSimpleName();

    private LinearLayoutManager mLayoutManager;
    private ArrayList<Message> mMessages = new ArrayList<>();
    private ChatAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the RecyclerView

    private FragmentChatBinding mBinding;

    public ChatFragment() {
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
        mBinding = FragmentChatBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();


        setupView();

        // Load existing messages to begin with
        loadMessages();

        setupLiveQuery();

        return root;
    }


    @Override
    public void onStop() {
        super.onStop();
        mBinding.etMessage.clearFocus();
    }


    /**
     * Real time get message
     */
    private void setupLiveQuery() {
        // Make sure the Parse server is setup to configured for live queries
        // URL for server is determined by Parse.initialize() call.
        ParseLiveQueryClient parseLiveQueryClient = null;
        try {
            parseLiveQueryClient = ParseLiveQueryClient.Factory
                    .getClient(new URI(getString(R.string.parse_server_url)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Message - Live Query
        if (parseLiveQueryClient != null) {
            ParseQuery<Message> parseQuery = ParseQuery.getQuery(Message.class);
            // This query can even be more granular (i.e. only refresh if the entry was added by some other user)
            // parseQuery.whereNotEqualTo(USER_ID_KEY, ParseUser.getCurrentUser().getObjectId());

            // Connect to Parse server
            SubscriptionHandling<Message> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

            // Listen for CREATE events
            subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, (query, object) -> {
                mMessages.add(object);

                // RecyclerView updates need run on UI thread
                getActivity().runOnUiThread(() -> {
                    mAdapter.notifyDataSetChanged();
                    // Always scroll to bottom when receive message
                    scrollToBottom();
                });
            });
        } else {
            Log.d(TAG, "ParseLiveQueryClient is null.");
            Toast.makeText(getContext(), "ParseLiveQueryClient is null.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Setup button event handler which posts the entered message to Parse
     */
    private void setupView() {
        // Find the text field and button
        final String userId = ParseUser.getCurrentUser().getObjectId();
        mAdapter = new ChatAdapter(getContext(), userId, mMessages, this);
        mBinding.rvChat.setAdapter(mAdapter);
        scrollToBottom();

        // Associate the LayoutManager with the RecyclerView
        mLayoutManager = new LinearLayoutManager(getContext());
        // Keep last item always show in recyclerview
        mLayoutManager.setStackFromEnd(true);
        mBinding.rvChat.setLayoutManager(mLayoutManager);

        // Hide keyboard when RecyclerView scrolled
        mBinding.rvChat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                InputMethodUtil.hideKeyBoard(getContext(), recyclerView);
            }
        });

        // When send button is clicked, create message object on Parse
        mBinding.btSend.setOnClickListener(v -> {
            String data = mBinding.etMessage.getText().toString();
            if (TextUtils.isEmpty(data)) return;

            sendTextMessage(data);
        });

    }

    private void sendTextMessage(String messageText) {
        Message message = new Message();
        message.setBody(messageText);
        message.setSender(ParseUser.getCurrentUser());
        message.saveInBackground();

        // clear edit input
        mBinding.etMessage.setText(null);
    }

    /**
     * Query messages from Parse so we can load them into the chat adapter
     */
    private void loadMessages() {
        DialogUtil.showLoadingDialog(getContext());

        // Construct query for message
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground((messages, e) -> {
            DialogUtil.dismissDialog();
            if (e == null) {
                mMessages.clear();
                mMessages.addAll(messages);
                mAdapter.notifyDataSetChanged(); // update adapter
                // Scroll to last item to get the view of last item
                scrollToBottom();
            } else {
                Log.e(TAG, "Error Loading Messages" + e);
            }
        });
    }

    /**
     * Scroll to last item to get the view of last item
     */
    private void scrollToBottom() {
        final int bottomPosition = mAdapter.getItemCount() - 1;
        getActivity().runOnUiThread(() -> mBinding.rvChat.scrollToPosition(bottomPosition));
    }

    /**
     * Go to user single page when click at user's avatar
     */
    @Override
    public void onAvatarClick(ParseUser sender) {
        navigateToProfile(sender);
    }

}