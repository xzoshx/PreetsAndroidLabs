package com.cst2335.Kaur0918.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cst2335.Kaur0918.ChatMessage;
import com.cst2335.Kaur0918.databinding.ActivityChatRoomBinding;
import com.cst2335.Kaur0918.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {
    ChatMessage selected;
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutBinding binding=DetailsLayoutBinding.inflate(inflater);
        ActivityChatRoomBinding binding1=ActivityChatRoomBinding.inflate(inflater);
        binding.message.setText(selected.message);
        binding.time.setText(selected.timeSent);
        binding.textView3.setText(selected.message);

        binding.textView4.setText("Id ="+selected.id);
        return binding.getRoot();
    }
    public MessageDetailsFragment(ChatMessage m){
        selected=m;
    }


}