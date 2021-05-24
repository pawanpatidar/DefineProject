package com.patidar.pawan.definelabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patidar.pawan.definelabs.ui.AllMatches.MatchesModel;

import java.util.List;

public class MatchesAdapter  extends RecyclerView.Adapter<MatchesAdapter.ViewHolder>{

    List<MatchesModel> matchesModels;
    Context context ;
    DBHelper dbHelper;
    boolean isdfromSavedMatch =false;

    public MatchesAdapter(Context context, List<MatchesModel> matchesModels, boolean isfromSavedMatch){
        this.context = context;
        this.matchesModels = matchesModels;
        dbHelper = DBHelper.getmInstance(context);
        this.isdfromSavedMatch = isfromSavedMatch;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vanue_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchesModel matchesModel = matchesModels.get(position);
        holder.vanueName.setText(matchesModel.getVanue());
        holder.address.setText(matchesModel.getAddress());
        if(matchesModel.isMatched()){
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_star_24));
        }else{
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_star_outline_24));
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(matchesModel.isMatched()){
                    matchesModels.get(position).setMatched(false);
                    dbHelper.deleteMatch(matchesModel.getId());
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_star_outline_24));
                    if(isdfromSavedMatch){
                        matchesModels.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, matchesModels.size());
                    }
                }else{
                    matchesModels.get(position).setMatched(true);
                    dbHelper.AddMatch(matchesModel);
                    holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_star_24));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchesModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView vanueName;
        private TextView address;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            vanueName = (TextView) view.findViewById(R.id.vanueName);
            address = view.findViewById(R.id.vanueAddress);
            imageView = view.findViewById(R.id.starMatched);
        }

    }
}
