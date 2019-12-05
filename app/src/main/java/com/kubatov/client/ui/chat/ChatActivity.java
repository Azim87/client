package com.kubatov.client.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.kubatov.client.App;
import com.kubatov.client.R;
import com.kubatov.client.data.repository.IClientRepository;
import com.kubatov.client.ui.chat.model.Chat;
import com.kubatov.client.util.DateHelper;
import com.kubatov.client.util.SharedHelper;
import com.kubatov.client.util.ShowToast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kubatov.client.util.Constants.DRIVER_NUMBERS;
import static com.kubatov.client.util.Constants.SHARED_KEY;

public class ChatActivity extends AppCompatActivity {
    private static final String MESSAGE = "message";
    private static final String MY_NUMBER = "myNumber";
    private static final String DRIVER_NUMBER = "driverNumber";
    private static final String CHAT_TIME = "chatTime";
    private IClientRepository repository = App.clientRepository;
    private String mNumber;
    private ChatAdapter mAdapter;
    private Map<String, Object> chatMap = new HashMap<>();

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
    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new  LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mChatRecyclerView.setLayoutManager(linearLayoutManager);
        mChatRecyclerView.setAdapter(mAdapter);
        getMessage();
    }

    private void getChatMessage() {
        repository.getChatMessage(new IClientRepository.chatCallback() {
            @Override
            public void onSuccess(List<Chat> chatList) {
                mAdapter.setChatList(chatList, mNumber);
            }

            @Override
            public void onFailure(Exception e) {
                ShowToast.me(e.getMessage());
            }
        });
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
        String number = SharedHelper.getShared(ChatActivity.this, SHARED_KEY, DRIVER_NUMBERS);
        setMessageToChat(message, number);
    }

    private void setMessageToChat(String message, String number){
        long milliseconds = System.currentTimeMillis();
        chatMap.put(MESSAGE, message);
        chatMap.put(MY_NUMBER, mNumber);
        chatMap.put(DRIVER_NUMBER, number);
        chatMap.put(CHAT_TIME, DateHelper.convertToHour(String.valueOf(milliseconds)));
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
