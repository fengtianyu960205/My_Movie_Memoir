package com.example.my_movie_memoir.Signin.Signup;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.Signin.Signin;
import com.example.my_movie_memoir.Signin.entity.Credential;
import com.example.my_movie_memoir.Signin.entity.Person;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


// the UI for sign up
public class SignupFragment extends Fragment {


    private SignupViewModel signUpViewModel;
    private GetAllCredentials getAllCredentials;
    private EditText et_fistName;
    private EditText et_surName;
    private RadioGroup rb_gender;
    private DatePicker dp_dob;
    private EditText et_address;
    private Spinner spinner_state;
    private EditText et_postcode;
    private EditText et_email;
    private EditText et_passwrod;
    private Button bt_confirm;


    private String gender = "M";
    private String dob;
    private Integer postcode;


    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);
        et_fistName = view.findViewById(R.id.et_first_name);
        et_surName = view.findViewById(R.id.et_sur_name);
        rb_gender = view.findViewById(R.id.radio_button_gender);
        dp_dob = view.findViewById(R.id.date_picker_dob);
        et_address = view.findViewById(R.id.et_address);
        spinner_state = view.findViewById(R.id.spinner_state);
        et_postcode = view.findViewById(R.id.et_postcode);
        et_email = view.findViewById(R.id.et_email);
        et_passwrod = view.findViewById(R.id.et_password);
        bt_confirm = view.findViewById(R.id.bt_confirm);


        signUpViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        getAllCredentials = new ViewModelProvider(this).get(GetAllCredentials.class);


        // get person gender
        rb_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        gender = "M";
                        break;
                    case R.id.radio_female:
                        gender = "F";
                        break;
                }
            }
        });

        // get person dob
        dp_dob.init(1996, 8, 8, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year,monthOfYear,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
                dob = sdf.format(c);
                Toast.makeText(getContext(),
                        "Date: " + dob,
                        Toast.LENGTH_SHORT).show();
            }
        });

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if postcode is integer then add person and credential if not tell user to enter again
                try {

                    postcode = Integer.parseInt(et_postcode.getText().toString().trim());
                    Credential credential = initCredential();
                    Person person = initPerson(credential.getcredId());
                    signUpViewModel.singUpProcessing(person, credential);
                }

                catch(Exception e)
                {

                    Toast.makeText(getContext(),
                            "Please enter postcode again (must number)",
                            Toast.LENGTH_SHORT).show();
                }


            }
       });

        // get the adding result, if result is 0 it adds successfully if not add fail.
        signUpViewModel.getAddPersonResult().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (integer == 0) {
                    Toast.makeText(getContext(),
                            "Sign up succeed",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), Signin.class));
                } else if (integer == 1) {
                    Toast.makeText(getContext(),
                            "Sign up failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }



    private Credential initCredential(){
        int id = getuId();
        String password = et_passwrod.getText().toString().trim();

        String newPassword = getSHA256StrJava(password);
        Credential credential = new Credential(id,
                et_email.getText().toString().trim(),
                newPassword,
                new Date());
        return credential;
    }

    private Person initPerson(int credid) {
        int id = getuId();
        Person person = new Person(id,et_fistName.getText().toString().trim(),et_surName.getText().toString().trim(),gender,dob,et_address.getText().toString().trim(),spinner_state.getSelectedItem().toString().trim(),postcode);
        person.setcredId(credid);
        return person;
    }

    // hash the password
    private String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }


    private String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public int getuId(){
        int min = 10000;
        int max = 100000;
        int id = (int)(Math.random() * (max - min + 1) + min);
        return id;

    }

}
