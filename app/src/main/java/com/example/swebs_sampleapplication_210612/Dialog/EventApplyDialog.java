package com.example.swebs_sampleapplication_210612.Dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;

public class EventApplyDialog extends Dialog {

    private DialogClickListener listener;
    private Context context;
    private BasicDialogTextModel model;

    public EventApplyDialog(@NonNull Context context, DialogClickListener listener, BasicDialogTextModel model) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.model = model;
    }



}
