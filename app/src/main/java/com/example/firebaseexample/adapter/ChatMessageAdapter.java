package com.example.firebaseexample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.firebaseexample.ChatMessage;
import com.example.firebaseexample.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private Context mContext;
    private final List<ChatMessage> chatMessages;

    public ChatMessageAdapter(Context context, ArrayList<ChatMessage> list) {
        super(context, 0, list);
        mContext = context;
        chatMessages = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Collections.sort(chatMessages);
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.chat_message, parent, false);

        ChatMessage message = chatMessages.get(position);

        TextView messageText = listItem.findViewById(R.id.message);
        messageText.setText(message.getMessageUser() + ": " + message.getMessageText());

        return listItem;
    }
}
