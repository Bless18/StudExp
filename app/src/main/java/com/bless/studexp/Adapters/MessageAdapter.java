package com.bless.studexp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bless.studexp.R;
import com.bless.studexp.models.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private  final Context context;
    private  final List<Message> messages;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.sample_message,parent,false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView messageSender;
        private ImageView messageImage;
        private  TextView messageImageCaption;
        private  TextView messageBody;
        private TextView messageTime;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageSender=itemView.findViewById(R.id.message_sender);
            messageBody=itemView.findViewById(R.id.message_body);
            messageImage=itemView.findViewById(R.id.message_image);
            messageImageCaption=itemView.findViewById(R.id.message_image_caption);
            messageTime=itemView.findViewById(R.id.message_time);
        }
    }
}
