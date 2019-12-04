package com.kubatov.client.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    private String mNumber;
    private List<Chat> mChat = new ArrayList<>();
    private Map<String, Object> chatMap  = new HashMap<>();

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
        ButterKnife.bind(this);
        initRecycler();
        getCurrentUserNumber();
        getMessage();
    }

    private void getCurrentUserNumber() {
        mNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
    }

    private void initRecycler() {
        ChatAdapter adapter = new ChatAdapter();
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatRecyclerView.setAdapter(adapter);
        adapter.setChatList(mChat, mNumber);
    }

    private void getMessage() {
        String message = mEditMessage.getText().toString().trim();
        if (mEditMessage.getText().toString().isEmpty()) {
            mMessageSend.setVisibility(View.GONE);
        } else {
            mMessageSend.setVisibility(View.VISIBLE);
        }
        mEditMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    mMessageSend.setVisibility(View.GONE);
                } else {
                    mMessageSend.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        SharedPreferences prefs = getSharedPreferences("olo", MODE_PRIVATE);
        String numberTo = prefs.getString("numbers", null);

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM,dd, yyyy HH:mm");
        Date resultDate = new Date(yourmilliseconds);

        chatMap.put("message", message);
        chatMap.put("myNumber", mNumber);
        chatMap.put("driversNumber", numberTo);
        chatMap.put("chatTime", sdf.format(resultDate));
    }

    @OnClick(R.id.send_message)
    void sentMessage(View view) {
        getMessage();
        App.clientRepository.sendChatMessage(chatMap);
        if (mEditMessage.getText() != null){
            mEditMessage.getText().clear();
        }
    }
}
