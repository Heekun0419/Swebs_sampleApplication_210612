package com.example.swebs_sampleapplication_210612.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swebs_sampleapplication_210612.Activity.ScanHistoryActivity;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.adapter.BottomSheetAdapter;
import com.example.swebs_sampleapplication_210612.adapter.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class bottomSheetFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.Theme_MaterialComponents_BottomSheetDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet,container,false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_bottomSheet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BottomSheetAdapter(getContext(),this));
    }


    private void IntentActivity(String string){
        Intent intent = new Intent(requireContext(), ScanHistoryActivity.class);
        intent.putExtra("resultCode",string);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(View view, int position, String code) {
        if (position == 0){ // 스캔 히스토리
            IntentActivity("scanHistory");

        }else if(position == 1) { // 구매등록 리스트
            IntentActivity("purchaseList");

        }else if(position ==2){ // 복제품 신고
            IntentActivity("copy");

        }
        dismiss();
    }
}
