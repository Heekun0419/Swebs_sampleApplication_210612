package com.example.swebs_sampleapplication_210612.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.ImagePickerDialog;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog2;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel2;
import com.example.swebs_sampleapplication_210612.R;
import com.example.swebs_sampleapplication_210612.databinding.ActivityModifyUserInfoBinding;
import com.example.swebs_sampleapplication_210612.util.FilePathFinder;
import com.example.swebs_sampleapplication_210612.util.Listener.onSingleClickListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyUserInfoActivity extends AppCompatActivity {

    private ActivityModifyUserInfoBinding binding;
    private ImagePickerDialog dialog;
    private File photoFile;
    private String realPath= null, photoPath =null;
    private Uri imageUri = null;
    FilePathFinder filePathFinder;
    private SPmanager sPmanager;
    private SwebsAPI retroAPI;

    private MyInfoRepository myInfoRepository;
    private final String DIALOG_TITLE = "회원정보 수정 안내";

    private String selectGender = null;
    private String country = null;
    private String region = null;
    private boolean checkEmailOverlap;
    private boolean checkPasswordForm;
    private boolean checkPasswordConfirm;
    private boolean checkBirthdayForm;

    List<String> regionList;

    private ImageView profileImage;
    public static final int TAKE_PHOTO_REQUEST = 11;
    public static final int PICK_IMAGE_REQUEST = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifyUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        sPmanager = new SPmanager(this);

        myInfoRepository = new MyInfoRepository(getApplication());

        // value init;
        checkEmailOverlap = false;
        checkPasswordForm = false;
        checkPasswordConfirm = false;
        checkBirthdayForm = false;
        regionList = new ArrayList<>();

        // 퍼미션 허용 한번 더 검사
        requestStoragePermission();

        filePathFinder = new FilePathFinder(getApplicationContext());
        //뒤로가기 버튼
        binding.btnInformationActivityBack.setOnClickListener(v -> onBackPressed());
        profileImage = binding.imageViewProfileModify;
        //디폴트 프로필 이미지 생성
        GlideImage("default",profileImage);

        binding.editTextUserInfoPassword.setFilters(new InputFilter[] {filterPassword});
        binding.editTextUserInfoPasswordConfirm.setFilters(new InputFilter[] {filterPassword});
        binding.editTextUserInfoNickname.setFilters(new InputFilter[] {filterNickname});
        binding.editTextUserInfoUsername.setFilters(new InputFilter[] {filtername});

        // 성별 설정 버튼 이벤트
        binding.btnGenderFemale.setOnClickListener(v -> {
            selectGender = "female";
            renderGenderButton();
        });

        binding.btnGenderMale.setOnClickListener(v -> {
            selectGender = "male";
            renderGenderButton();
        });

        // 국가선택
        binding.textViewCountrySelect.setOnClickListener(new onSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dialogCountry();
            }
        });

        // 지역 선택
        binding.textViewRegionSelect.setOnClickListener(new onSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dialogRegion();
            }
        });

        binding.imageViewProfileModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ImagePickerDialog(ModifyUserInfoActivity.this, new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            photoFile = filePathFinder.createMakefile();
                            photoPath = photoFile.getAbsolutePath();
                            Uri uri = FileProvider.getUriForFile(ModifyUserInfoActivity.this,
                                    "com.example.swebs_sampleapplication_210612.provider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                            if (takePictureIntent.resolveActivity(ModifyUserInfoActivity.this.getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNegativeClick() {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
            }
        });

    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ModifyUserInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 457);
        }
        else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ModifyUserInfoActivity.this, new String[]{Manifest.permission.CAMERA}, 456);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    GlideImage(photoPath, profileImage);
                }
                break;

            case PICK_IMAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    assert data != null;
                    imageUri = data.getData();
                    // You can update this bitmap to your server
                    realPath = filePathFinder.getPath(imageUri);
                    //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri)
                    GlideImage(realPath,profileImage);
                }
                break;
        }
    }


    private void GlideImage(String uri, ImageView view) {
        Glide.with(getApplicationContext()).load(uri)
                .placeholder(R.drawable.ic_profile_basic)
                .override(600, 600)
                .circleCrop()
                .into(view);
    }

    @Override
    public void onResume() {
        super.onResume();

        // START - data Observe
        // nickname
        myInfoRepository.getValueToLiveData("nickName").observe(this, s -> {
            if (s != null)
                binding.editTextUserInfoNickname.setText(s);
        });

        // birthday
        myInfoRepository.getValueToLiveData("birthday").observe(this, s -> {
            if (s != null)
                binding.editTextUserInfoBirthday.setText(s);
        });

        // gender
        myInfoRepository.getValueToLiveData("gender").observe(this, s -> {
            if (s != null) {
                selectGender = s;
                renderGenderButton();
            }
        });

        //Email
        myInfoRepository.getValueToLiveData("email").observe(this,s -> {
            if(s!=null)
                binding.editTextEmail.setText(s);
        });

        //Name
        myInfoRepository.getValueToLiveData("name").observe(this,s -> {
            if(s!=null)
                binding.editTextUserInfoUsername.setText(s);
        });

        // Country
        myInfoRepository.getValueToLiveData("country").observe(this, s -> {
            String viewText = "국가 선택";
            if (s != null) {
                country = s;
                viewText = countryCodeToName(s);
            }

            binding.textViewCountrySelect.setText(viewText);
            renderRegionSelect(Objects.requireNonNull(s));
        });
        // END - data Observe

        binding.editTextUserInfoPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // 이메일 형식 체크 후 중복 확인.
        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    if (binding.editTextEmail.getVisibility() == View.VISIBLE)
                        binding.emailOverlapCheck.setVisibility(View.VISIBLE);
                } else if (binding.emailOverlapCheck.getVisibility() == View.VISIBLE){
                    binding.emailOverlapCheck.setVisibility(View.GONE);
                }

                checkEmailOverlap = false;
                if (!binding.emailOverlapCheck.getText().toString().equals("중복확인"))
                    binding.emailOverlapCheck.setText("중복확인");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // EditText Watcher - 비밀번호 체크
        binding.editTextUserInfoPassword.addTextChangedListener(watcherPassword);

        // EditText Watcher - 비밀번호 확인 체크
        binding.editTextUserInfoPasswordConfirm.addTextChangedListener(watcherPassword);

        // EditText Watcher - 생년월일 체크
        binding.editTextUserInfoBirthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                renderBirthdayForm();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void dialogCountry() {
        int defaultValue = 1;
        List<String> inputData = new ArrayList<>();

        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (!locale.getCountry().equals("")) {
                if (!inputData.contains(locale.getDisplayCountry())) {
                    inputData.add(locale.getDisplayCountry());
                }
            }
        }
        Collections.sort(inputData);

        // 기본 선택 구하기.
        int countryCount = 0;
        for (String data : inputData) {
            if (binding.textViewCountrySelect.getText().toString().equals(data))
                defaultValue = countryCount;
            else if (binding.textViewCountrySelect.getText().toString().equals("국가선택")
                    && country.equals(data)) {
                defaultValue = countryCount;
            }

            countryCount++;
        }

        NumberPickerDialog2 dialog = new NumberPickerDialog2(
                this,
                new NumberPickerModel2(
                        "국가 선택",
                        inputData,
                        defaultValue,
                        "확인",
                        "취소"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        binding.textViewCountrySelect.setText(inputData.get(position));
                        if (!country.equals(countryNameToCode(inputData.get(position)))) {
                            binding.textViewRegionSelect.setText("지역 선택");
                            region = null;
                        }
                        country = countryNameToCode(inputData.get(position));
                        renderRegionSelect(Objects.requireNonNull(countryNameToCode(inputData.get(position))));
                    }

                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onCloseClick() {

                    }
                }
        );
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private void renderGenderButton() {
        if (selectGender.equals("female")) {
            binding.btnGenderFemale.setSelected(true);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderMale.setSelected(false);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#C2C3C7"));
        }

        if (selectGender.equals("male")) {
            binding.btnGenderMale.setSelected(true);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderFemale.setSelected(false);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#C2C3C7"));
        }
    }

    private void dialogRegion() {
        NumberPickerDialog2 dialog = new NumberPickerDialog2(
                this,
                new NumberPickerModel2(
                        "지역 선택"
                        , regionList
                        , 0
                        , "확인"
                        , "취소"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                        binding.textViewRegionSelect.setText(regionList.get(position));
                        region = regionList.get(position);
                    }

                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onCloseClick() {

                    }
                }
        );
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private String countryCodeToName(String countryCode) {
        return new Locale("", countryCode).getDisplayCountry();
    }

    private String countryNameToCode(String countryName) {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            if (!locale.getCountry().equals("")
                    && locale.getDisplayCountry().equals(countryName)) {
                return locale.getCountry();
            }
        }

        return null;
    }
    // 지역 리스트...
    private void renderRegionSelect(String country) {
        if (country == null) return;

        if (country.equals("KR")
                || country.equals("US")
                || country.equals("CN"))
            binding.layoutRegionSelect.setVisibility(View.VISIBLE);
        else {
            region = null;
            binding.layoutRegionSelect.setVisibility(View.GONE);
        }

        // 리스트 셋업
        regionList.clear();
        switch (country) {
            case "KR":
                regionList.add("서울특별시");
                regionList.add("부산광역시");
                regionList.add("대구광역시");
                regionList.add("인천광역시");
                regionList.add("광주광역시");
                regionList.add("대전광역시");
                regionList.add("울산광역시");
                regionList.add("경기도");
                regionList.add("강원도");
                regionList.add("충청북도");
                regionList.add("충청남도");
                regionList.add("전라북도");
                regionList.add("전라남도");
                regionList.add("경상북도");
                regionList.add("경상남도");
                regionList.add("제주도");
                regionList.add("세종특별시");
                break;
            case "US":
                regionList.add("Alaska");
                regionList.add("Alabama");
                regionList.add("Arkansas");
                regionList.add("Arizona");
                regionList.add("California");
                regionList.add("Colorado");
                regionList.add("Connecticut");
                regionList.add("District of Columbia");
                regionList.add("Delaware");
                regionList.add("Florida");
                regionList.add("Georgia");
                regionList.add("Hawaii");
                regionList.add("Iowa");
                regionList.add("Idaho");
                regionList.add("Illinois");
                regionList.add("Indiana");
                regionList.add("Kansas");
                regionList.add("Kentucky");
                regionList.add("Louisiana");
                regionList.add("Massachusetts");
                regionList.add("Maryland");
                regionList.add("Maine");
                regionList.add("Michigan");
                regionList.add("Minnesota");
                regionList.add("Missouri");
                regionList.add("Mississippi");
                regionList.add("Montana");
                regionList.add("North Carolina");
                regionList.add("North Dakota");
                regionList.add("Nebraska");
                regionList.add("New Hampshire");
                regionList.add("New Jersey");
                regionList.add("New Mexico");
                regionList.add("Nevada");
                regionList.add("New York");
                regionList.add("Ohio");
                regionList.add("Oklahoma");
                regionList.add("Oregon");
                regionList.add("Pennsylvania");
                regionList.add("Rhode Island");
                regionList.add("South Carolina");
                regionList.add("South Dakota");
                regionList.add("Tennessee");
                regionList.add("Texas");
                regionList.add("Utah");
                regionList.add("Virginia");
                regionList.add("Vermont");
                regionList.add("Washington");
                regionList.add("Wisconsin");
                regionList.add("West Virginia");
                regionList.add("Wyoming");
                break;
            case "CN":
                regionList.add("Anhui");
                regionList.add("Beijing");
                regionList.add("Chongqing");
                regionList.add("Fujian");
                regionList.add("Guangdong");
                regionList.add("Gansu");
                regionList.add("Guangxi");
                regionList.add("Guizhou");
                regionList.add("Henan");
                regionList.add("Hubei");
                regionList.add("Hebei");
                regionList.add("Hainan");
                regionList.add("Heilongjiang");
                regionList.add("Hunan");
                regionList.add("Jilin");
                regionList.add("Jiangsu");
                regionList.add("Jiangxi");
                regionList.add("Liaoning");
                regionList.add("Inner Mongolia Autonomous Regi");
                regionList.add("Ningxia Hui Autonomous Region");
                regionList.add("Qinghai");
                regionList.add("Sichuan");
                regionList.add("Shandong");
                regionList.add("Shanghai");
                regionList.add("Shaanxi");
                regionList.add("Shanxi");
                regionList.add("Tianjin");
                regionList.add("Xinjiang");
                regionList.add("Tibet");
                regionList.add("Yunnan");
                regionList.add("Zhejiang");
                break;
        }
    }


    private void renderPasswordForm() {
        if (binding.editTextUserInfoPassword.getText().toString().length() > 0) {
            Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{6,}$");
            Matcher matcher = pattern.matcher(binding.editTextUserInfoPassword.getText().toString());
            if (matcher.find()) {
                binding.passwordFormText.setVisibility(View.GONE);
                checkPasswordForm = true;
            } else {
                binding.passwordFormText.setVisibility(View.VISIBLE);
                checkPasswordForm = false;
            }
        } else {
            binding.passwordFormText.setVisibility(View.GONE);
            checkPasswordForm = false;
        }
    }

    private void renderPasswordConfirm() {
        if (binding.editTextUserInfoPasswordConfirm.getText().length() > 0) {
            if (binding.editTextUserInfoPassword.getText().toString().equals(binding.editTextUserInfoPasswordConfirm.getText().toString())) {
                binding.passwordConfirmText.setVisibility(View.GONE);
                checkPasswordConfirm = true;
            } else {
                binding.passwordConfirmText.setVisibility(View.VISIBLE);
                checkPasswordConfirm = false;
            }
        }
    }

    private void renderBirthdayForm() {
        if (binding.editTextUserInfoBirthday.getText().toString().length() > 0) {
            Pattern pattern = Pattern.compile("([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))");
            Matcher matcher = pattern.matcher(binding.editTextUserInfoBirthday.getText().toString());
            if (matcher.find()
                    && binding.editTextUserInfoBirthday.getText().toString().length() == 8) {
                binding.birthdayFormText.setVisibility(View.GONE);
                checkBirthdayForm = true;
            } else {
                binding.birthdayFormText.setVisibility(View.VISIBLE);
                checkBirthdayForm = false;
            }
        } else {
            binding.birthdayFormText.setVisibility(View.GONE);
            checkBirthdayForm = false;
        }
    }

    private final TextWatcher watcherPassword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            renderPasswordConfirm();
            renderPasswordForm();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    protected  InputFilter filterPassword = (source, start, end, dest, dstart, dend) -> {
        Pattern ps = Pattern.compile("^[가-힣ㄱ-ㅎㅏ-ㅣ]+$");
        if (ps.matcher(source).matches()) {
            Toast.makeText(getApplicationContext(), "비밀번호에 입력할 수 없는 형식입니다.", Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    };

    protected  InputFilter filterNickname = (source, start, end, dest, dstart, dend) -> {
        Pattern ps = Pattern.compile("[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ._-]*$");
        if (!ps.matcher(source).matches()) {
            Toast.makeText(getApplicationContext(), "닉네임에 입력할 수 없는 형식입니다.", Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    };

    protected  InputFilter filtername = (source, start, end, dest, dstart, dend) -> {
        Pattern ps = Pattern.compile("[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ._-]*$");
        if (!ps.matcher(source).matches()) {
            Toast.makeText(getApplicationContext(), "이름에 입력할 수 없는 형식입니다.", Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    };

}