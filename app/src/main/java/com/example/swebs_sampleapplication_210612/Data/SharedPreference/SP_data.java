package com.example.swebs_sampleapplication_210612.Data.SharedPreference;

public class SP_data {
    // 쉐어드 프레퍼런스 이름만 저장하는 클래스
    // SP manager 통해서 사용 가능.

    public static final String PERMISSION = "PERMISSION";
    public static final String SCAN_TUTORIAL ="SCAN_TUTORIAL";
    public static final String MY_TUTORIAL ="MY_TUTORIAL";

    // 스캔 세팅값 저장
    public static class SETTING_INFO {
        public static final String LANGUAGE ="LANGUAGE";
        public static final String SCAN_ANIMATION = "SCAN_ANIMATION";
        public static final String SCAN_SOUND ="SCAN_SOUND";
        public static final String SCAN_HISTORY ="SCAN_HISTORY";
        public static final String AUTOFOCUS ="AUTOFOCUS";
    }

    public static class USER_INFO {
        public static final String USERTYPE ="USER_TYPE";
        public static final String USER_NAME = "USER_NAME";
        public static final String USER_EMAIL = "USER_EMAIL";
        public static final String USER_GENDER = "USER_GENDER";
        public static final String USER_BIRTHDAY = "USER_BIRTHDAY";
        public static final String USER_REGION = "USER_REGION";
        public static final String USER_POINT ="USER_POINT";
    }


}
