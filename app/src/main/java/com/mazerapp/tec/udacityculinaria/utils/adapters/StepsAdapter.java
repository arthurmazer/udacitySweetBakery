package com.mazerapp.tec.udacityculinaria.utils.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.utils.interfaces.OnStepClicked;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private ArrayList<Steps> stepsList;
    Context mContext;
    OnStepClicked onStepClicked;
    Steps previousStep;
    Steps nextStep;


    public StepsAdapter(Context context, OnStepClicked onStepClicked) {
        this.stepsList = new ArrayList<>();
        this.mContext = context;
        this.onStepClicked = onStepClicked;
    }

    public void setList(ArrayList<Steps> stepsList) {
        this.stepsList = stepsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_steps, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Steps steps = stepsList.get(position);

        if (position == 0){
            holder.tvStepNumber.setText(mContext.getString(R.string.step_intro));
        }else{
            holder.tvStepNumber.setText(String.format(mContext.getString(R.string.step_field), steps.getId()));
        }

        holder.tvStepTitle.setText(steps.getShortDescription());

        if (!steps.getVideoURL().trim().isEmpty()){
            holder.ivPlay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvStepNumber;
        TextView tvStepTitle;
        ImageView ivPlay;

        public ViewHolder(View itemView) {
            super(itemView);

            tvStepNumber = itemView.findViewById(R.id.tv_step_number);
            tvStepTitle = itemView.findViewById(R.id.tv_step_title);
            ivPlay = itemView.findViewById(R.id.iv_icon_play);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() > 0) {
                previousStep = stepsList.get(getAdapterPosition() - 1);
            }else{
                previousStep = null;
            }

            if (getAdapterPosition() < stepsList.size()-1){
                nextStep = stepsList.get(getAdapterPosition()+1);
            }else{
                nextStep = null;
            }

            onStepClicked.onStepClicked(stepsList, getAdapterPosition());
        }
    }
}
