package com.kubatov.client.ui.chat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.ServerTimestamp;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.ui.chat.model.Chat;
import com.kubatov.client.util.Constants;
import com.kubatov.client.util.DateHelper;
import com.kubatov.client.util.SharedHelper;
import com.kubatov.client.util.ShowToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kubatov.client.util.Constants.CHANNEL_ID;
import static com.kubatov.client.util.Constants.DRIVER_NUMBERS;
import static com.kubatov.client.util.Constants.SHARED_KEY;

public class ChatActivity extends AppCompatActivity {
    private static final String MESSAGE = "message";
    private static final String MY_NUMBER = "messageFrom";
    private static final String DRIVER_NUMBER = "messageTo";
    private static final String CHAT_TIME = "chatTime";
    private IClientRepository repository = App.clientRepository;
    private ChatAdapter mAdapter;
    private Map<String, Object> chatMap = new HashMap<>();
    private NotificationManagerCompat notificationManager;
    private String driverNumber;
    private String mNumber;
    private String chatTitle;

    @BindView(R.id.chat_recycler_view)
    RecyclerView mChatRecyclerView;
    @BindView(R.id.edit_text_chat)
    EditText mEditMessage;
    @BindView(R.id.send_message)
    ImageView mMessageSend;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ChatActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        mAdapter = new ChatAdapter();
        ButterKnife.bind(this);
        initRecycler();
        getChatMessage();
        setTitle(chatTitle);
        driverNumber = SharedHelper.getShared(ChatActivity.this, SHARED_KEY, DRIVER_NUMBERS);

    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mChatRecyclerView.setLayoutManager(linearLayoutManager);
        mChatRecyclerView.setAdapter(mAdapter);

        getMessage();
    }

    private void getChatMessage() {
        repository.getChatMessage(new IClientRepository.chatCallback() {
            @Override
            public void onSuccess(List<Chat> chatList) {
                List<Chat> nChat = new ArrayList<>();
                for (Chat chat : chatList) {
                    if (chat.getMessageFrom().equals(mNumber) || chat.getMessageTo().equals(mNumber)) {
                        if (chat.getMessageFrom().equals(driverNumber) || chat.getMessageTo().equals(driverNumber)) {
                            nChat.add(chat);
                            chatTitle = chat.getMessageFrom();
                            getChatNotification(chat.getMessage(), chat.getMessageFrom());
                        }
                        mChatRecyclerView.scrollToPosition(nChat.size() - 1);
                    }
                }
                mAdapter.setChatList(nChat, mNumber);
            }

            @Override
            public void onFailure(Exception e) {
                ShowToast.me(e.getMessage());
            }
        });
    }

    private void getChatNotification(String message, String messageFrom) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(messageFrom)
                    .setContentText(message)
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(pendingIntent)
                    .build();
            notificationManager.notify(1, notification);
        }
    }

    private void getMessage() {
        String message = mEditMessage.getText().toString().trim();
        if (mEditMessage.getText().toString().isEmpty()) {
            mMessageSend.setVisibility(View.GONE);
        } else {
            mMessageSend.setVisibility(View.VISIBLE);
        }

        mEditMessage.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.toString().equals("")) {
                    mMessageSend.setVisibility(View.GONE);
                } else {
                    mMessageSend.setVisibility(View.VISIBLE);
                }
            }
        });

        /*GET DRIVERS NUMBERS FROM TRIP DETAILS ACTIVITY*/

        setMessageToChat(message, driverNumber);

    }

    private void setMessageToChat(String message, String number) {
        long chatTime = System.currentTimeMillis();
        chatMap.put(MESSAGE, message);
        chatMap.put(MY_NUMBER, mNumber);
        chatMap.put(DRIVER_NUMBER, number);
        chatMap.put(CHAT_TIME, chatTime);
    }

    @OnClick(R.id.send_message)
    void sentMessage(View view) {
        getMessage();
        repository.sendChatMessage(chatMap);
        if (mEditMessage.getText() != null) {
            mEditMessage.getText().clear();
        }
        getChatMessage();
    }
}
