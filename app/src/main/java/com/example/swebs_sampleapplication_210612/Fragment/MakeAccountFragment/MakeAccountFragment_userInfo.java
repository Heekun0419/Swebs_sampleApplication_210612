package com.example.swebs_sampleapplication_210612.Fragment.MakeAccountFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.swebs_sampleapplication_210612.Activity.MakeAccountActivity;
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

    private String selectGender = null, country = null;
    private boolean checkEmailOverlap;
    private boolean checkPasswordForm;
    private boolean checkPasswordConfirm;
    private boolean checkBirthdayForm;

    private final String DIALOG_TITLE = "회원가입 안내";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
        sPmanager = new SPmanager(requireContext());

        myInfoRepository = new MyInfoRepository(requireActivity().getApplication());
        //국가코드 가져오기
        Locale locale;
        locale = requireContext().getResources().getConfiguration().getLocales().get(0);
        country = locale.getCountry();

        // value init;
        checkEmailOverlap = false;
        checkPasswordForm = false;
        checkPasswordConfirm = false;
        checkBirthdayForm = false;
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

        //초기 지역선택 GONE
        binding.constraintLayoutLocationSelect.setVisibility(View.GONE);
        binding.textViewCountrySelect.setText(country);

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
            binding.btnGenderFemale.setSelected(true);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderMale.setSelected(false);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#C2C3C7"));
            selectGender = "female";
        });

        binding.btnGenderMale.setOnClickListener(v -> {
            binding.btnGenderMale.setSelected(true);
            binding.textViewMakeAccountGenderMale.setTextColor(Color.parseColor("#21CCB2"));
            binding.btnGenderFemale.setSelected(false);
            binding.textViewMakeAccountGenderFemale.setTextColor(Color.parseColor("#C2C3C7"));
            selectGender = "male";
        });

        binding.textViewCountrySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCountry();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

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
                requireContext(),
                new NumberPickerModel2(
                        "국가지역",
                        inputData,
                        defaultValue,
                        "확인",
                        "취소"
                ),
                new DialogClickListener() {
                    @Override
                    public void onPositiveClick(int position) {
                       // myInfoRepository.insertMyInfo("country", countryNameToCode(inputData.get(position)));
                        binding.textViewCountrySelect.setText(inputData.get(position));
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

        // 추후 얻어와ㅏ서 입력 하기.
        body.put("inputReferralCode", RequestBody.create("WSdcR", MediaType.parse("text/plane")));

        // 추후 변경하기...
        body.put("inputCountry", RequestBody.create("KR", MediaType.parse("text/plane")));
        body.put("inputCountryRegion", RequestBody.create("none", MediaType.parse("text/plane")));

        body.put("inputReceiveReferralCode", RequestBody.create("WSdcR", MediaType.parse("text/plane")));

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