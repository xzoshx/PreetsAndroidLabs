package com.cst2335.Kaur0918;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.cst2335.Kaur0918.data.ChatMessage;
import com.cst2335.Kaur0918.data.ChatRoomViewModel;
import com.cst2335.Kaur0918.databinding.ActivityChatRoomBinding;
import com.cst2335.Kaur0918.databinding.ReceiveMessageBinding;
import com.cst2335.Kaur0918.databinding.SentMessageBinding;
import com.google.android.material.snackbar.Snackbar;

public class ChatRoom extends AppCompatActivity {

    private int globalPosition;
    private ActivityChatRoomBinding binding;
    private ChatMessage chatMessageObj;
    private ArrayList<ChatMessage> messages;
    private RecyclerView.Adapter adapter;
    private ChatMessageDAO chatMessageDAO;
    ChatRoomViewModel cvm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cvm=new ViewModelProvider(this).get(ChatRoomViewModel.class);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        chatMessageDAO = db.chatMessageDAO();
        setSupportActionBar(binding.myToolbar);


        if (messages == null) {
            messages = cvm.messages.getValue();
            cvm.messages.postValue(messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll(chatMessageDAO.getAllMessages());
                runOnUiThread(() -> binding.recycleView.setAdapter(adapter));
            });
        }

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        cvm.selectedMessage.observe(this, newMessageValue -> {
            MessageDetails messageDetailsFragment = new MessageDetails(newMessageValue);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sw600Fragment, messageDetailsFragment)
                    .addToBackStack("")
                    .commit();
        });
        binding.sendBtn.setOnClickListener(click -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");

            String currentDetectedTime = simpleDateFormat.format(new Date());
            String txt = binding.messageEditText.getText().toString();
            chatMessageObj = new ChatMessage(txt, currentDetectedTime, true);
            messages.add(chatMessageObj);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                long id = chatMessageDAO.insertMessage(chatMessageObj);
                chatMessageObj.id = id;
            });
            adapter.notifyItemInserted(messages.size() - 1);
            binding.messageEditText.setText("");
        });

        binding.receiveBtn.setOnClickListener(click -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDetectedTime = simpleDateFormat.format(new Date());
            String txt = binding.messageEditText.getText().toString();
            chatMessageObj = new ChatMessage(txt, currentDetectedTime, false);
            messages.add(chatMessageObj);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                long id = chatMessageDAO.insertMessage((chatMessageObj));
                chatMessageObj.id = id;
            });
            adapter.notifyItemInserted(messages.size() - 1);
            binding.messageEditText.setText("");
        });

        adapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding sentMessageBinding = null;
                ReceiveMessageBinding receiveMessageBinding = null;
                if (viewType == 0) {
                    sentMessageBinding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(sentMessageBinding.getRoot());
                } else {
                    receiveMessageBinding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(receiveMessageBinding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage messagePosition = messages.get(position);
                holder.messageTextView.setText(messagePosition.getMessage());
                holder.timeTextView.setText(messagePosition.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                if (messages.get(position).getIsSendButton()) return 0;
                else return 1;
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String words="Version 1.0, created by Sukhmanpreet kaur";
        if(item.getItemId()==R.id.item_2){
            words="Garbage";
            //Handle the delete message action here
            int position = adapter.getItemCount() - 1; // Get the position of the last item in the list

            if (position >= 0) {
                ChatMessage message = messages.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to delete the message: " + message.getMessage());
                builder.setNegativeButton("No", null);

                builder.setPositiveButton("Yes", (dialog, cl) -> {
                    // Delete the message from the database
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() -> {
                        chatMessageDAO.deleteMessage(message);
                    });

                    // Remove the message from the list and update the adapter
                    messages.remove(position);
                    adapter.notifyItemRemoved(position);

                    // Show a snackbar with the option to undo the delete
                    Snackbar.make(binding.getRoot(), "You deleted the message #" + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", ck -> {
                                // Add the removed message back to the list and update the adapter
                                messages.add(position, message);
                                adapter.notifyItemInserted(position);
                            })
                            .show();
                });

                builder.create().show();
            }



        }else if(item.getItemId()==R.id.item_1){
            words="Version 1.0, created by Sukhmanpreet kaur";

        }
        Toast.makeText(this,"You clicked on the "+words, Toast.LENGTH_LONG).show();
        return true;

    }

    //the inner class
    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView messageTextView;
        public TextView timeTextView;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            messageTextView = itemView.findViewById(R.id.messageTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            itemView.setOnClickListener(click->{
                int position = getAdapterPosition();
                globalPosition=position;
                ChatMessage selectedMessage = messages.get(position);
                cvm.selectedMessage.postValue(selectedMessage);
            });

        }
    }
}