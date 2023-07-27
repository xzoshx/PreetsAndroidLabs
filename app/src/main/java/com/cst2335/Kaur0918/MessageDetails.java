package com.cst2335.Kaur0918;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.cst2335.Kaur0918.data.ChatMessage;
import com.cst2335.Kaur0918.databinding.LayoutDetailsBinding;

public class MessageDetails extends Fragment{
    ChatMessage chatMessage;


    public MessageDetails(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LayoutDetailsBinding binding = LayoutDetailsBinding.inflate(inflater);

        binding.fragmentMessageTextView.setText("Your Message: " + chatMessage.message);
        binding.fragmentTimeTextView.setText("Date of message: " + chatMessage.timeSent);
        binding.fragmentDBIdTextView.setText("DatabaseId: " + chatMessage.id);

        return binding.getRoot();
    }
}