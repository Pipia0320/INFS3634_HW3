package com.example.lmao2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lmao2.CatAboutActivity;
import com.example.lmao2.MainActivity;
import com.example.lmao2.R;
import com.example.lmao2.models.Cat;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    private ArrayList<Cat> catsToAdapt;

    public void setData(ArrayList<Cat> catsToAdapt) {
        this.catsToAdapt = catsToAdapt;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        CatViewHolder catViewHolder = new CatViewHolder(view);

        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        final Cat catAtPosition = catsToAdapt.get(position);

        holder.bind(catAtPosition);
    }

    @Override
    public int getItemCount() {
        return catsToAdapt.size();
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder{

        public TextView title;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_cat);
        }

        public void bind(final Cat catAtPosition) {
            title.setText(catAtPosition.getCatName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Intent intent = new Intent(context, CatAboutActivity.class);
                    intent.putExtra("CatID", catAtPosition.getCatId());
                    intent.setType("text/plain");
                    context.startActivity(intent);
                }
            });
        }
    }
}
