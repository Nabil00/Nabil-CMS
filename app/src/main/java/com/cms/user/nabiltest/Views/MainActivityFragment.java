package com.cms.user.nabiltest.Views;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;


import com.cms.registrationResponseData.nabiltest.R;
import com.cms.user.nabiltest.model.RegistrationResponseData;
import com.cms.user.nabiltest.rest.ApiClient;
import com.cms.user.nabiltest.rest.ApiInterface;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ImageView userImage;
    private EditText inputName, inputEmail, inputPassword, inputPasswordConfirm;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputLayoutPasswordConfirm;
    private Switch genderSwitch;
    private Button btnSignUp;

    private Uri selectedImage;
    private String picturePath;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        userImage = (ImageView) v.findViewById(R.id.user_img);
        inputLayoutName = (TextInputLayout) v.findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) v.findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) v.findViewById(R.id.input_layout_password);
        inputLayoutPasswordConfirm = (TextInputLayout) v.findViewById(R.id.input_layout_password_confirm);
        inputName = (EditText) v.findViewById(R.id.input_name);
        inputEmail = (EditText) v.findViewById(R.id.input_email);
        inputPassword = (EditText) v.findViewById(R.id.input_password);
        inputPasswordConfirm = (EditText) v.findViewById(R.id.input_password_confirm);
        genderSwitch = (Switch) v.findViewById(R.id.gender_switch_input);
        btnSignUp = (Button) v.findViewById(R.id.btn_signup);


        userImage.setOnClickListener(new MyClickListener());

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnSignUp.setOnClickListener(new MyClickListener());
    }

//    validate name not equal null
    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

//    check email form
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


//    not null check for password
    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }


    //    passwordConfirmation with 1st password
    private boolean validatePasswordConfirm() {
        if (! inputPassword.getText().toString().trim().equals(inputPasswordConfirm.getText().toString().trim()) || inputPasswordConfirm.getText().toString().trim() == null) {
            inputLayoutPasswordConfirm.setError(getString(R.string.err_msg_password_confirm));
            requestFocus(inputPasswordConfirm);
            return false;
        } else {
            inputLayoutPasswordConfirm.setErrorEnabled(false);
        }
        return true;
    }
//    Keep focus on the error field
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

//    text watcher to validate text after finish
    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }


        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_password_confirm:
                    validatePasswordConfirm();
                    break;
            }
        }
    }


//    method to set the data coming back from server into views
    private void setUserData(RegistrationResponseData registrationResponseData){

        inputName.setText(registrationResponseData.getUser_name());
        inputName.setEnabled(false);

        inputEmail.setText(registrationResponseData.getAvatar().getUrl());
        inputEmail.setEnabled(false);

        inputPassword.setText("000");
        inputPassword.setEnabled(false);

        inputPasswordConfirm.setText("000");
        inputPasswordConfirm.setEnabled(false);


    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse("multipart/form-data"), descriptionString);
    }

//    click listener implementation
    private class MyClickListener implements View.OnClickListener {
        static final int SELECT_SINGLE_PICTURE = 101;
        @Override
        public void onClick(View v) {

//            check which button
//            image clicked to be updated from gallery
            if(v.getId()==R.id.user_img) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "select_picture"), SELECT_SINGLE_PICTURE);
            }


//            sign up button click to register new user
            else if(v.getId()==R.id.btn_signup){
//                first check Validation
                if (!validateName() || !validateEmail() || !validatePassword() || !validatePasswordConfirm()) {
                    Toast.makeText(getContext(), "Check Invalid Fields!", Toast.LENGTH_SHORT).show();
                }else {
//                    request data

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);

                    MultipartBody.Part body=null;

                    try {
                        File file = new File(picturePath);
                        // create RequestBody instance from file
                        RequestBody requestFile =
                                RequestBody.create(MediaType.parse("multipart/form-data"), file);

                        // MultipartBody.Part is used to send also the actual file name
                        body=
                                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

                    }catch (Exception e){

                    }


                    Call<RegistrationResponseData> call =apiService.createNewUser(
                            inputEmail.getText().toString(),
                            inputName.getText().toString(),
                            inputPassword.getText().toString(),
                            body
                            );

                    call.enqueue(new Callback<RegistrationResponseData>() {
                        @Override
                        public void onResponse(Call<RegistrationResponseData> call, Response<RegistrationResponseData> response) {
                            if(response.isSuccessful()) {
                                setUserData(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<RegistrationResponseData> call, Throwable t) {
                            Toast.makeText(getContext(),"Submition Failed",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }
    }


//    intent handling when coming back from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == MyClickListener.SELECT_SINGLE_PICTURE){
                try{

//                    get picture path using content provider
                    selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContext().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

//                    set image into image view
                    userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    Toast.makeText(getContext(),"Image Loaded",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getContext(),"Image Loaded Exception",Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(getContext(),"Image Not Laded",Toast.LENGTH_LONG).show();
        }
    }
}
