package com.kubatov.client.ui.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.client.R;
import com.kubatov.client.ui.chat.model.Chat;
import com.kubatov.client.util.DateHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Chat> mChat = new ArrayList<>();
    private String myNumber;

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_viewholder, parent, false);
        return new ChatViewHolder(view);
    }

    public void setChatList(List<Chat> chatList, String number) {
        myNumber = number;
        mChat.clear();
        mChat.addAll(chatList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.onBind(mChat.get(position));
    }

    @Override
    public int getItemCount() {
        Log.e("ololo", "getItemCount: " + mChat.size()  );
        return mChat.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_message_1) TextView textViewClient;
        @BindView(R.id.text_message_2) TextView textViewDriver;
        @BindView(R.id.chat_time_1) TextView textViewClientTime;
        @BindView(R.id.chat_time_2) TextView textViewDriverTime;
        @BindView(R.id.container_1) ConstraintLayout constraintLayoutClient;
        @BindView(R.id.container_2) ConstraintLayout constraintLayoutDriver;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Chat chat) {
            if (myNumber.equals(chat.getMessageTo())) {
                constraintLayoutClient.setVisibility(View.INVISIBLE);
                constraintLayoutDriver.setVisibility(View.VISIBLE);
                setData(chat);
            } else {
                constraintLayoutClient.setVisibility(View.VISIBLE);
                constraintLayoutDriver.setVisibility(View.INVISIBLE);
                setData(chat);
            }
        }

        private void setData(Chat chat) {
            textViewClient.setText(chat.getMessage());
            textViewDriver.setText(chat.getMessage());
            textViewClientTime.setText(DateHelper.convertUnixToDate(chat.getChatTime()));
            textViewDriverTime.setText(DateHelper.convertUnixToDate(chat.getChatTime()));
        }
    }
}
