package com.kubatov.client.ui.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.client.R;
import com.kubatov.client.ui.chat.model.Chat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Chat> mChat = new ArrayList<>();
    private String mNumber;

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_viewholder, parent, false);
        return new ChatViewHolder(view);
    }

    public void setChatList(List<Chat> chatList) {

        mChat.clear();
        mChat.addAll(chatList);
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.onBind(mChat.get(position));

    }

    @Override
    public int getItemCount() { return mChat.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_message_1)
        TextView textViewClient;
        @BindView(R.id.text_message_2)
        TextView textViewDriver;
        @BindView(R.id.container_1)
        RelativeLayout relativeLayoutClient;
        @BindView(R.id.container_2)
        RelativeLayout relativeLayoutDriver;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Chat chat) {

            if (chat.getMessage() == chat.getMessageFrom()) {
                relativeLayoutClient.setVisibility(View.VISIBLE);
                relativeLayoutDriver.setVisibility(View.VISIBLE);
                setData(chat);
            } else if (chat.getMessage() == chat.getMessageTo()) {
                setData(chat);
                relativeLayoutClient.setVisibility(View.VISIBLE);
                relativeLayoutDriver.setVisibility(View.VISIBLE);
            }
        }

        private void setData(Chat chat){
            textViewClient.setText(chat.getMessage());
            textViewDriver.setText(chat.getMessage());
        }
    }
}
