package com.senior.project.genealogy.view.fragment.profile.UpdateProfile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.senior.project.genealogy.BuildConfig;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.app.GenealogyApplication;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.SnackBarLayout;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.profile.ShowProfile.ProfileFragmentPresenterImpl;
import com.senior.project.genealogy.view.fragment.profile.ShowProfile.ProfileFragmentView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.senior.project.genealogy.util.Constants.EMPTY_STRING;

public class UpdateProfileFragment extends Fragment implements UpdateProfileFragmentView, SnackBarLayout.DialogInterface {

    @BindView(R.id.circle_profile)
    CircleImageView imgProfile;

    @BindView(R.id.edtFullname)
    EditText edtFullname;

    @BindView(R.id.edtBirthday)
    EditText edtBirthday;

    @BindView(R.id.edtMail)
    EditText edtMail;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.radioGender)
    RadioGroup radioGender;

    @BindView(R.id.radioMale)
    RadioButton radioMale;

    @BindView(R.id.radioFemale)
    RadioButton radioFemale;

    @BindView(R.id.btnDoneProfile)
    FloatingActionButton btnDoneProfile;

    Snackbar mSnackbar;

    SnackBarLayout mSnackBarLayout;

    private User user;
    private UpdateProfileFragmentPresenterImpl updateProfileFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private String mCurrentPhotoPath;

    public UpdateProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_update, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, EMPTY_STRING);
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_update_profile));
        }

        user = (User) getArguments().getSerializable("user");
        showProfile(user);

        mSnackBarLayout = new SnackBarLayout(GenealogyApplication.getInstance());
        mSnackbar = initSnackBar(mSnackBarLayout);
        mSnackBarLayout.attachDialogInterface(this);
        return view;
    }

    private Snackbar initSnackBar(SnackBarLayout snackBarLayout) {
        View parentLayout = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, "File Choosers", Snackbar.LENGTH_LONG);
        final Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        layout.setLayoutParams(params);
        layout.setBackgroundColor(ContextCompat.getColor(GenealogyApplication.getInstance(), R.color.white));
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);
        if(snackBarLayout.getParent() != null)
            ((ViewGroup)snackBarLayout.getParent()).removeView(snackBarLayout);
        layout.addView(snackBarLayout);
        snackbar.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE);
        return snackbar;
    }

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @OnClick({R.id.btnDoneProfile, R.id.edtBirthday, R.id.circle_profile})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDoneProfile:
                updateProfileFragmentPresenterImpl = new UpdateProfileFragmentPresenterImpl(this);
                user.setFullname(edtFullname.getText().toString());
                user.setAddress(edtAddress.getText().toString());
                user.setBirthday(edtBirthday.getText().toString());
                if(radioMale.isChecked()){
                    user.setGender(true);
                } else {
                    user.setGender(false);
                }
                user.setMail(edtMail.getText().toString());
                updateProfileFragmentPresenterImpl.updateProfile(token, user);
                break;
            case R.id.edtBirthday:
                selectDate(edtBirthday);
                break;
            case R.id.circle_profile:
                if (mSnackbar.isShown())
                    mSnackbar.dismiss();
                else
                    mSnackbar.show();
                break;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CAMERA:
                    Bitmap bitmap = Utils.fixOrientationBugOfProcessedBitmap(GenealogyApplication.getInstance(),
                            BitmapFactory.decodeFile(mCurrentPhotoPath), mCurrentPhotoPath);
                    Uri tempUri = Utils.getImageUri(GenealogyApplication.getInstance(), bitmap);
                    if (tempUri != null) {
                        File newFile = new File(Utils.getRealPathFromURI(getActivity(), tempUri));
                        uploadFileToFirebase(newFile);
                    } else {
                        Toast.makeText(GenealogyApplication.getInstance(), getString(R.string.message_wrong_image), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFileToFirebase(final File newFile) {
        final Uri file = Uri.fromFile(newFile);
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("images/"+file.getLastPathSegment());
        riversRef.putFile(file).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/"+newFile.getName());

                ref.putFile(file).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            user.setAvatar(String.valueOf(downloadUri));
                            Glide.with(GenealogyApplication.getInstance())
                                    .load(user.getAvatar())
                                    .into(imgProfile);
                        }
                    }
                });
            }
        });
    }

    private Calendar mCalendar;

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    private void selectDate(final EditText edt){
        mCalendar = Calendar.getInstance();
        int day = mCalendar.get(Calendar.DATE);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public ProgressDialog initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void closeFragment(User user) {
        mUpdateProfileInterface.sendDataUpdateToProfile(user);
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_profile));
        }
        getActivity().onBackPressed();
    }

    @Override
    public void showProfile(User user) {
        if(user.isGender()){
            radioMale.setChecked(true);
        } else {
            radioFemale.setChecked(true);
        }

        edtFullname.setText(user.getFullname());
        edtBirthday.setText(user.getBirthday());
        edtMail.setText(user.getMail());
        edtAddress.setText(user.getAddress());
        edtBirthday.setText(user.getBirthday());
        Glide.with(GenealogyApplication.getInstance())
                .load(user.getAvatar())
                .into(imgProfile);
    }

    @Override
    public void openCamera() {
        if (Utils.isDoubleClick()) {
            return;
        }
        if (Utils.checkPermissionCamera(getActivity())) {
            takePhotoByCamera();
        } else {
            Utils.settingPermissionCameraOnFragment(this);
        }
    }

    private void takePhotoByCamera() {
        if (mSnackbar.isShown()) mSnackbar.dismiss();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = Utils.createImageFile();
            mCurrentPhotoPath = photoFile.getAbsolutePath();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(GenealogyApplication.getInstance(),
                    BuildConfig.APPLICATION_ID + getString(R.string.fileprovider),
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, Constants.REQUEST_CAMERA);
        }
    }

    @Override
    public void openGallery() {

    }

    public interface UpdateProfileInterface{
        void sendDataUpdateToProfile(User user);
    }

    public UpdateProfileInterface mUpdateProfileInterface;

    public void attachInterface(UpdateProfileInterface updateProfileInterface){
        mUpdateProfileInterface = updateProfileInterface;
    }
}
