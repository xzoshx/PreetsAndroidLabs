package com.cst2335.Kaur0918;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cst2335.Kaur0918.data.ChatMessage;
import com.cst2335.Kaur0918.data.ChatRoomViewModel;
import com.cst2335.Kaur0918.databinding.ActivityChatRoomBinding;
import com.cst2335.Kaur0918.databinding.ReceiveMessageBinding;
import com.cst2335.Kaur0918.databinding.SentMessageBinding;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private ChatMessage chatMessageObj;

    private ArrayList<ChatMessage> messages;
    //    private ArrayList<String> messages = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ChatRoomViewModel cvm = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = cvm.messages.getValue();
        if (messages == null) {
            cvm.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setAdapter(adapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding sentMessageBinding = null;
                ReceiveMessageBinding receiveMessageBinding = null;
                if (viewType == 0) {
                    sentMessageBinding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    System.err.println("The viewType function is 0 now!!!!!");
                    return new MyRowHolder(sentMessageBinding.getRoot());
                } else { //  if (viewType == 1)
                    receiveMessageBinding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    System.err.println("The viewType function is 1 now!!!!!");
                    return new MyRowHolder(receiveMessageBinding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage messagePosition = messages.get(position);
                holder.messageTextView.setText(messagePosition.getMessage());
                holder.messageTextView.setText(chatMessageObj.getMessage());
                holder.timeTextView.setText(chatMessageObj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                if (messages.get(position).getIsSendButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendBtn.setOnClickListener(click -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");

            String currentDetectedTime = simpleDateFormat.format(new Date());
            String txt = binding.messageEditText.getText().toString();
            chatMessageObj = new ChatMessage(txt, currentDetectedTime, true);
            messages.add(chatMessageObj);
            System.out.println(chatMessageObj.getMessage());
            adapter.notifyItemInserted(messages.size() - 1);
            binding.messageEditText.setText("");
        });

        binding.receiveBtn.setOnClickListener(click -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");

            String currentDetectedTime = simpleDateFormat.format(new Date());
            String txt = binding.messageEditText.getText().toString();

            chatMessageObj = new ChatMessage(txt, currentDetectedTime, false);

            messages.add(chatMessageObj);
            adapter.notifyItemInserted(messages.size() - 1);
            // clear the previous text
            binding.messageEditText.setText("");
        });

    }
}

class MyRowHolder extends RecyclerView.ViewHolder {

    public TextView messageTextView;
    public TextView timeTextView;

    public MyRowHolder(@NonNull View itemView) {
        super(itemView);

        messageTextView = itemView.findViewById(R.id.messageTextView);
        timeTextView = itemView.findViewById(R.id.timeTextView);
    }
}