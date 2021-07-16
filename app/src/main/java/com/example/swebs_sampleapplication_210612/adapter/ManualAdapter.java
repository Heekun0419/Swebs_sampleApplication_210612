package com.example.swebs_sampleapplication_210612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ItemManualListBinding;

import org.jetbrains.annotations.NotNull;

public class ManualAdapter extends RecyclerView.Adapter<ManualAdapter.ManualViewHolder>{

    Context context;
    private ItemManualListBinding binding;
    String[] title ={"스웹스는 어떤 어플인가요?","어플은 어떻게 사용하나요?","앱이 실행이 안되요", "쿠폰발급은 어떻게 하나요"};
    String[] date = {"2021.07.12","2021.07.10", "2021.06.23", "2021.06.11"};

    public ManualAdapter(Context context){
        this.context = context;
    }

    @NotNull
    @Override
    public ManualViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       binding = ItemManualListBinding.inflate(LayoutInflater.from(context),parent,false);

        return new ManualViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ManualViewHolder holder, int position) {
        holder.textView_title.setText(title[position]);
        holder.textView_date.setText(date[position]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ManualViewHolder extends RecyclerView.ViewHolder {
        TextView textView_title, textView_date;

        public ManualViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView_date = itemView.findViewById(R.id.textView_manual_date);
            textView_title = itemView.findViewById(R.id.textView_manual_title);
        }
    }
}
