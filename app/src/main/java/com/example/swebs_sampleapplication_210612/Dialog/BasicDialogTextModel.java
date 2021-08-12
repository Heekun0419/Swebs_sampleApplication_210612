package com.example.swebs_sampleapplication_210612.Dialog;

public class BasicDialogTextModel {
    private String title;
    private String content;
    private String positiveButton;
    private String negativeButton;

    public BasicDialogTextModel(String title, String content, String positiveButton, String negativeButton){
        this.title =title;
        this.content = content;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPositiveButton() {
        return positiveButton;
    }

    public String getNegativeButton() {
        return negativeButton;
    }
}
