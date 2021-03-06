package com.hiepdt.annavoochackathon.community.inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.hiepdt.annavoochackathon.R;
import com.hiepdt.annavoochackathon.models.Messenger;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private static String uID;
    Context mContext;
    ArrayList<Messenger> mListMes;
    private FirebaseAuth mAuth;

    public InboxAdapter(Context mContext, ArrayList<Messenger> mListMes) {
        this.mContext = mContext;
        this.mListMes = mListMes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_inbox, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();
        uID = mAuth.getCurrentUser().getUid();

        Messenger messenger = mListMes.get(position);

        String createBy = messenger.getCreateBy();

        String url = messenger.getUrl();
        String content = messenger.getContent();
        if (!createBy.equals(uID)) {
            //--------Ẩn------------//
            holder.tvMesR.setVisibility(View.INVISIBLE);
            //--------Hiện-----------//
            holder.tvMesL.setVisibility(View.VISIBLE);
            holder.imageL.setVisibility(View.VISIBLE);
            if (position > 1 && position < mListMes.size() - 1) {
                if (!createBy.equals(mListMes.get(position + 1).getCreateBy())) {
                    Glide.with(holder.imageL).load(url).into(holder.imageL);
                } else {
                    holder.imageL.setImageResource(0);
                }
            } else {
                Glide.with(holder.imageL).load(url).into(holder.imageL);
            }
            holder.tvMesL.setText(content);
        } else {
            holder.tvMesL.setVisibility(View.INVISIBLE);
            holder.imageL.setVisibility(View.INVISIBLE);

            holder.tvMesR.setVisibility(View.VISIBLE);

            holder.tvMesR.setText(content);
        }


    }

    @Override
    public int getItemCount() {
        return mListMes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageL;
        TextView tvMesL, tvMesR;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageL = itemView.findViewById(R.id.imageL);
            tvMesL = itemView.findViewById(R.id.tvMesL);

            tvMesR = itemView.findViewById(R.id.tvMesR);
        }
    }
}
