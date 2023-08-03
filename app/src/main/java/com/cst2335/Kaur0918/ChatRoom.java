package com.cst2335.Kaur0918;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.cst2335.Kaur0918.data.ChatMessageDAO;
import com.cst2335.Kaur0918.data.ChatRoomViewModel;
import com.cst2335.Kaur0918.data.MessageDatabase;
import com.cst2335.Kaur0918.data.MessageDetailsFragment;
import com.cst2335.Kaur0918.databinding.ActivityChatRoomBinding;
import com.cst2335.Kaur0918.databinding.ReceiveMessageBinding;
import com.cst2335.Kaur0918.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;

    ChatRoomViewModel chatModel;

    ArrayList<ChatMessage> messages = new ArrayList<>();
    ////ArrayList<String> message = new ArrayList<>();
    private RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        ChatMessageDAO mDAO = db.cmDAO();
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        ////message = chatModel.messages.getValue();
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        ChatRoomViewModel viewModel = null;

        chatModel.selectedMessage.observe(this, (newMessageValue) -> {

            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.replace(R.id.fragmentLocation, chatFragment);
            tx.addToBackStack(null);
            tx.commit();
        });


        if (messages == null) {
            ArrayList<String> message;
            ////////chatModel.messages.setValue(message = new ArrayList<>());
            chatModel.messages.setValue(messages);

        }


        binding.button.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            String message = binding.textInput.getText().toString();
            ChatMessage chatMessage = new ChatMessage(message, currentDateandTime, true);

            // Insert the message into the database
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(chatMessage);
            });

            // Add the message to the list and notify the adapter
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size() - 1);

            // Clear the previous text
            binding.textInput.setText("");
        });

        binding.button4.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            String message = binding.textInput.getText().toString();
            ChatMessage chatMessage = new ChatMessage(message, currentDateandTime, false);

            // Insert the message into the database
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(chatMessage);
            });

            // Add the message to the list and notify the adapter
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size() - 1);

            // Clear the previous text
            binding.textInput.setText("");


        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {


            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    View root = binding.getRoot();
                    return new MyRowHolder(binding.getRoot(),messages, mDAO, myAdapter);
                } else {
                    // Inflate the receive_message layout for viewType 1
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot(), messages, mDAO, myAdapter);
                }

                ////SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                ////    return new MyRowHolder(binding.getRoot());
            }


            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatMessage = messages.get(position);
                holder.messageText.setText(chatMessage.getMessage());
                holder.timeText.setText(chatMessage.getTimeSent());
                ////holder.messageText.setText("");
                //////holder.timeText.setText("");
                /////  String obj=messages.get(position);
                //////  holder.messageText.setText(obj);

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage chatMessage = messages.get(position);
                if (chatMessage.getIsSendButton()) {
                    return 0; // Send message type
                } else {
                    return 1; // Receive message type
                }
            }

        });
        //  return 0;
        //  }

    }
    @Override
    public void onBackPressed() {
        // Check if there are any Fragments in the back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Pop the Fragment from the back stack
            getSupportFragmentManager().popBackStack();
        } else {
            // No Fragments in the back stack, let the system handle the back button
            super.onBackPressed();
        }
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        ArrayList<ChatMessage> messages;
        ChatMessageDAO mDAO;
        RecyclerView.Adapter adt;

        public MyRowHolder(@NonNull View itemView,ArrayList<ChatMessage> messages, ChatMessageDAO mDAO, RecyclerView.Adapter adt) {
            super(itemView);
            this.messages = messages;
            this.mDAO = mDAO;
            this.adt = adt;

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);
                /*int position = getAbsoluteAdapterPosition();
                ChatMessage message = messages.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setMessage("Do you want to delete the message:" + messageText.getText());
                builder.setNegativeButton("No", null);

                builder.setPositiveButton("Yes", (dialog, cl) -> {



                        builder.setNegativeButton("No", null);

                        Executor thread = Executors.newSingleThreadExecutor();

                        // Delete the message from the database
                        thread.execute(() -> {
                            mDAO.deleteMessage(message);
                        });

                        // Remove the message from the list and update the adapter
                        messages.remove(position);
                        adt.notifyItemRemoved(position);

                        // Show a snackbar with the option to undo the delete
                        Snackbar.make(messageText, "You deleted the message #" + position, Snackbar.LENGTH_LONG)
                                .setAction("Undo", ck -> {
                                    // Add the removed message back to the list and update the adapter
                                    messages.add(position, message);
                                    adt.notifyItemInserted(position);
                                })
                                .show();

                        });






                        builder.create().show();

                 */


            });
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);


        }
    }
}