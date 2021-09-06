package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.Login_Signup.MakeAccountActivity;
import com.example.swebs_sampleapplication_210612.Data.Repository.MyInfoRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.NormalSignUpModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.Dialog.NumberPickerDialog2;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.BasicDialogTextModel;
import com.example.swebs_sampleapplication_210612.Dialog.DialogClickListener;
import com.example.swebs_sampleapplication_210612.Dialog.OneButtonBasicDialog;
import com.example.swebs_sampleapplication_210612.Dialog.dialogModel.NumberPickerModel2;
import com.example.swebs_sampleapplication_210612.databinding.FragmentMakeAccountUserInfoBinding;
import com.example.swebs_sampleapplication_210612.util.Listener.onSingleClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeAccountFragment_userInfo extends Fragment {

    private FragmentMakeAccountUserInfoBinding binding;
    SwebsAPI retroAPI;

    private SPmanager sPmanager;

    private MyInfoRepository myInfoRepository;

    private String selectGender = null;
    private String country = null;
    private String region = null;
    private String referralCode = null;
    private boolean checkEmailOverlap;
    private boolean checkPasswordForm;
    private boolean checkPasswordConfirm;
    private boolean checkBirthdayForm;

    private final String DIALOG_TITLE = "회원가입 안내";

    List<String> regionList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        sPmanager = new SPmanager(requireContext());

        myInfoRepository = new MyInfoRepository(requireActivity().getApplication());

        // value init;
        checkEmailOverlap = false;
        checkPasswordForm = false;
        checkPasswordConfirm = false;
        checkBirthdayForm = false;
        referralCode = ((MakeAccountActivity)requireActivity()).getReferralCode();
        regionList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMakeAccountUserInfoBinding.inflate(inflater,container,false);

        binding.editTextUserInfoPassword.setFilters(new InputFilter[] {filterPassword});
        binding.editTextUserInfoPasswordConfirm.setFilters(new InputFilter[] {filterPassword});
        binding.editTextUserInfoNickname.setFilters(new InputFilter[] {filterNickname});
        binding.editTextUserInfoUsername.setFilters(new InputFilter[] {filtername});

        //첫글자 빨간색
        renderFirstTextRed();

        // 버튼 - 회원가입.
        binding.btnMakeAccount.setOnClickListener(v -> {
            userSingUpUpload();
        });

        // 버튼 - 이메일 중복 확인.
        binding.emailOverlapCheck.setOnClickListener(v -> {
            if (!checkEmailOverlap)
                overlapEmailFromServer();
        });

        // 성별 설정 버튼 이벤트
        binding.btnGenderFemale.setOnClickListener(v -> {
            selectGender = "female";
            renderGenderButton();
        });

        binding.btnGenderMale.setOnClickListener(v -> {
            selectGender = "male";
            renderGenderButton();
        });

        binding.textViewCountrySelect.setOnClickListener(new onSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dialogCountry();
            }
        });

        binding.textViewRegionSelect.setOnClickListener(new onSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dialogRegion();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        // START - data Observe
        // nickname
        myInfoRepository.getValueToLiveData("nickName").observe(getViewLifecycleOwner(), s -> {
            if (s != null)
                binding.editTextUserInfoNickname.setText(s);
        });

        // birthday
        myInfoRepository.getValueToLiveData("birthday").observe(getViewLifecycleOwner(), s -> {
            if (s != null)
                binding.editTextUserInfoBirthday.setText(s);
        });

        // gender
        myInfoRepository.getValueToLiveData("gender").observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                selectGender = s;
                renderGenderButton();
            }
        });


        // Country
        myInfoRepository.getValueToLiveData("country").observe(getViewLifecycleOwner(), s -> {
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
                requireContext(),
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

    private void dialogRegion() {
        NumberPickerDialog2 dialog = new NumberPickerDialog2(
                requireContext(),
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

    private void renderFirstTextRed() {
        binding.textViewName.setText(spannable(binding.textViewName.getText().toString()));
        binding.textViewBirthday.setText(spannable(binding.textViewBirthday.getText().toString()));
        binding.textViewEmail.setText(spannable(binding.textViewEmail.getText().toString()));
        binding.textViewNickname.setText(spannable(binding.textViewNickname.getText().toString()));
        binding.textViewGender.setText(spannable(binding.textViewGender.getText().toString()));
        binding.textViewCountry.setText(spannable(binding.textViewCountry.getText().toString()));
        binding.textViewPassword.setText(spannable(binding.textViewPassword.getText().toString()));
        binding.textViewPasswordConfirm.setText(spannable(binding.textViewPasswordConfirm.getText().toString()));
        binding.textViewPassword.setText(spannable(binding.textViewPassword.getText().toString()));
    }

    private SpannableString spannable(String string) {
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ED6D6D")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
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


    private void overlapEmailFromServer() {
        if (binding.editTextEmail.getText().toString().equals("")) {
            showOneButtonDialog(DIALOG_TITLE, "이메일을 입력 해주세요.");
            return;
        }

        // 서버 전송할 데이터 만들기.
        HashMap <String, RequestBody> formData = new HashMap<>();
        formData.put("inputEmail", RequestBody.create(binding.editTextEmail.getText().toString(), MediaType.parse("text/plane")));
        formData.put("inputUserType", RequestBody.create("normal", MediaType.parse("text/plane")));
        Call<Boolean> call = retroAPI.overlapEmail(formData);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                    && response.body()) {
                        showOneButtonDialog(DIALOG_TITLE, "사용 가능한 이메일입니다.");
                        binding.emailOverlapCheck.setText("완료");
                        checkEmailOverlap = true;
                    } else {
                        showOneButtonDialog(DIALOG_TITLE, "이미 사용중인 이메일 입니다.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    // 로그인 입력 사항을 모두 했나용?
    private boolean checkSignupDataForm() {
        String content = null;
        if (!checkEmailOverlap) {
            content = "이메일 중복확인을 해주세요.";
        } else  if (!checkPasswordForm) {
            content = "비밀번호 형식을 확인 해주세요.";
        } else if (!checkPasswordConfirm) {
            content = "비밀번호 확인이 일치하지 않습니다.";
        } else if (binding.editTextUserInfoNickname.getText().toString().length() <= 0) {
            content = "닉네임을 입력 해주세요.";
        } else if (binding.editTextUserInfoUsername.getText().toString().length() <= 0) {
            content = "이름을 입력 해주세요.";
        } else if (!checkBirthdayForm) {
            content = "생년월일 형식을 확인 해주세요.";
        } else if (binding.editTextUserInfoPhoneNumber.getText().toString().length() <= 0) {
            content = "전화번호를 입력 해주세요.";
        } else if (selectGender == null) {
            content = "성별을 선택 해주세요.";
        } else if (country == null) {
            content = "국가를 선택 해주세요.";
        } else if (binding.layoutRegionSelect.getVisibility() == View.VISIBLE && region == null) {
            content = "지역을 선택 해주세요.";
        }

        if (content != null) {
            showOneButtonDialog(DIALOG_TITLE, content);
            return false;
        }

        return true;
    }

    private void userSingUpUpload() {
        if (!checkSignupDataForm())
            return;

        ((MakeAccountActivity)requireActivity()).renderLoading(true);

        // 서버로 전송할 데이터 만들기.
        String inputEmail = binding.editTextEmail.getText().toString().toLowerCase();
        String inputPhoneNumber = binding.editTextUserInfoPhoneNumber.getText().toString().replace("-", "");

        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("inputUserSrl", RequestBody.create(sPmanager.getUserSrl(), MediaType.parse("text/plane")));
        body.put("inputEmail", RequestBody.create(inputEmail, MediaType.parse("text/plane")));
        body.put("inputPassword", RequestBody.create(binding.editTextUserInfoPassword.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputName", RequestBody.create(binding.editTextUserInfoUsername.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputBirthday", RequestBody.create(binding.editTextUserInfoBirthday.getText().toString(), MediaType.parse("text/plane")));
        body.put("inputPhoneNumber", RequestBody.create(inputPhoneNumber, MediaType.parse("text/plane")));
        body.put("inputPhoneCountry", RequestBody.create("none", MediaType.parse("text/plane")));
        body.put("inputGender", RequestBody.create(selectGender, MediaType.parse("text/plane")));
        body.put("inputNickname", RequestBody.create(binding.editTextUserInfoNickname.getText().toString(), MediaType.parse("text/plane")));

        // 추천인 코드 적용.
        if (referralCode != null)
            body.put("inputReceiveReferralCode", RequestBody.create(referralCode, MediaType.parse("text/plane")));

        // 국가 넣기.
        body.put("inputCountry", RequestBody.create(country, MediaType.parse("text/plane")));
        
        // 지역 넣기
        if (region != null)
            body.put("inputCountryRegion", RequestBody.create(region, MediaType.parse("text/plane")));
        else
            body.put("inputCountryRegion", RequestBody.create("none", MediaType.parse("text/plane")));

        Call<NormalSignUpModel> call = retroAPI.userSingUp(body);
        call.enqueue(new Callback<NormalSignUpModel>() {
            @Override
            public void onResponse(Call<NormalSignUpModel> call, Response<NormalSignUpModel> response) {
                if (!response.isSuccessful())
                    return;

                if (response.body() != null) {
                    NormalSignUpModel responseData = response.body();
                    if (responseData.isSuccess()) {
                        myInfoRepository.insertMyInfo("userSrl", responseData.getUserSrl());
                        myInfoRepository.insertMyInfo("token", responseData.getToken());
                        myInfoRepository.insertMyInfo("nickName", responseData.getNickname());
                        myInfoRepository.insertMyInfo("name", responseData.getName());
                        myInfoRepository.insertMyInfo("country", responseData.getCountry());
                        myInfoRepository.insertMyInfo("point", responseData.getPoint());
                        myInfoRepository.insertMyInfo("birthday", responseData.getBirthday());
                        myInfoRepository.insertMyInfo("userType", responseData.getUserType());
                        myInfoRepository.insertMyInfo("gender", responseData.getGender());
                        myInfoRepository.insertMyInfo("referralCode", responseData.getReferralCode());
                        myInfoRepository.insertMyInfo("phone", responseData.getPhone_number());
                        myInfoRepository.insertMyInfo("email", inputEmail);

                        sPmanager.setUserSrl(responseData.getUserSrl());
                        sPmanager.setUserType(responseData.getUserType());
                        sPmanager.setUserToken(responseData.getToken());
                        sPmanager.setUserReferralCode(responseData.getReferralCode());

                        ((MakeAccountActivity)requireActivity()).renderLoading(false);
                        ((MakeAccountActivity)requireActivity()).moveFragment(new MakeAccountFragment_success(),"success");
                    } else {
                        ((MakeAccountActivity)requireActivity()).renderLoading(false);

                        String dialogText;
                        switch (Objects.requireNonNull(responseData.getReason())) {
                            case "emailForm":
                                dialogText = "이메일 형식이 맞지 않습니다.\n이메일을 확인 해주세요.";
                                break;
                            case "overlapEmail":
                                dialogText = "이미 가입된 이메일 입니다.\n이메일을 확인 해주세요.";
                                break;
                            case "overlapNickname":
                                dialogText = "이미 가입된 닉네임 입니다.\n닉네임을 확인 해주세요.";
                                break;
                            case "alreadySignUp":
                                dialogText = "이미 회원가입을 진행된 게스트 계정입니다.\n어플을 종료 후 다시 실행 해주세요.";
                                break;
                            default:
                                dialogText = "회원가입을 실패 하였습니다.\n잠시 후 다시 시도 해주세요.";
                                break;
                        }

                        showOneButtonDialog(DIALOG_TITLE, dialogText);

                    }
                }
            }

            @Override
            public void onFailure(Call<NormalSignUpModel> call, Throwable t) {
                ((MakeAccountActivity)requireActivity()).renderLoading(false);
                showOneButtonDialog(DIALOG_TITLE, "회원가입을 실패 하였습니다.\n잠시 후 다시 시도 해주세요.");
            }
        });

    }

    private void showOneButtonDialog(String title, String content) {
        OneButtonBasicDialog oneButtonBasicDialog = new OneButtonBasicDialog(requireContext()
                , new BasicDialogTextModel(title, content, "확인", "")
                , new DialogClickListener() {
            @Override
            public void onPositiveClick(int position) {

            }

            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onCloseClick() {

            }
        });

        oneButtonBasicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        oneButtonBasicDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        oneButtonBasicDialog.show();
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
            Toast.makeText(requireContext(), "비밀번호에 입력할 수 없는 형식입니다.", Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    };

    protected  InputFilter filterNickname = (source, start, end, dest, dstart, dend) -> {
        Pattern ps = Pattern.compile("[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ._-]*$");
        if (!ps.matcher(source).matches()) {
            Toast.makeText(requireContext(), "닉네임에 입력할 수 없는 형식입니다.", Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    };

    protected  InputFilter filtername = (source, start, end, dest, dstart, dend) -> {
        Pattern ps = Pattern.compile("[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ._-]*$");
        if (!ps.matcher(source).matches()) {
            Toast.makeText(requireContext(), "이름에 입력할 수 없는 형식입니다.", Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    };
}