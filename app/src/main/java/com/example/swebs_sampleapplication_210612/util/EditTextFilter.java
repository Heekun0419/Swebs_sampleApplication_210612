package com.example.swebs_sampleapplication_210612.util;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import java.util.regex.Pattern;

public class EditTextFilter {
    private  InputFilter inputFilter;
    private Context context;
    public EditTextFilter(Context context){
        this.context = context;
    }

    public InputFilter[] SetFilter(){
        inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern pattern = Pattern.compile("[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]*$");
                if(pattern.matcher(source).matches()||source.equals("")){
                    return source;
                }else{
                    Toast.makeText(context, "특수문자를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return "";
                }
            }
        };
        return new InputFilter[]{inputFilter};
    }

}
