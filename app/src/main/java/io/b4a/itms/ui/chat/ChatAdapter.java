package io.b4a.itms.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseUser;

import java.util.List;

import io.b4a.itms.R;
import io.b4a.itms.data.parse_model.Message;
import io.b4a.itms.data.parse_model.ParseUserExtensions;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private static final int MESSAGE_OUTGOING = 123;
    private static final int MESSAGE_INCOMING = 321;

    private OnAvatarClickListener mOnAvatarClickListener;
    private List<Message> mMessages;
    private Context mContext;
    private String mMyUserId;

    public ChatAdapter(Context context, String userId, List<Message> messages, OnAvatarClickListener onAvatarClickListener) {
        mMessages = messages;
        mMyUserId = userId;
        mContext = context;
        mOnAvatarClickListener = onAvatarClickListener;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isMe(position)) {
            return MESSAGE_OUTGOING;
        } else {
            return MESSAGE_INCOMING;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == MESSAGE_INCOMING) {
            View contactView = inflater.inflate(R.layout.message_incoming, parent, false);
            return new IncomingMessageViewHolder(contactView);
        } else if (viewType == MESSAGE_OUTGOING) {
            View contactView = inflater.inflate(R.layout.message_outgoing, parent, false);
            return new OutgoingMessageViewHolder(contactView);
        } else {
            throw new IllegalArgumentException("Unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.bindMessage(message);
    }

    /**
     * Check if the message of passed position is sent by logged user
     *
     * @param position Current message position
     */
    private boolean isMe(int position) {
        Message message = mMessages.get(position);
        final String messageUserId = message.getSender().getObjectId();
        return messageUserId != null && messageUserId.equals(mMyUserId);
    }


    protected class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageProfile;
        TextView body;

        public MessageViewHolder(View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.ivProfilePicture);
            body = itemView.findViewById(R.id.tvBody);
        }

        public void bindMessage(Message message) {
            // loading user avatar
            ParseUserExtensions.loadAvatar(mContext, message.getSender(), imageProfile);

            imageProfile.setOnClickListener(this);

            body.setText(message.getBody());
        }

        @Override
        public void onClick(View view) {
            ParseUser sender = mMessages.get(getAbsoluteAdapterPosition()).getSender();

            mOnAvatarClickListener.onAvatarClick(sender);
        }
    }

    protected class OutgoingMessageViewHolder extends MessageViewHolder {

        public OutgoingMessageViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected class IncomingMessageViewHolder extends MessageViewHolder {
        TextView name;

        public IncomingMessageViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
        }

        @Override
        public void bindMessage(Message message) {
            super.bindMessage(message);

            // in addition to message show user ID
            name.setText(message.getSender().getUsername());
        }
    }


    public interface OnAvatarClickListener {
        void onAvatarClick(ParseUser sender);
    }

}