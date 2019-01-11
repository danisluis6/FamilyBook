package com.senior.project.genealogy.view.fragment.profile.ShowProfile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.app.GenealogyApplication;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment.UpdateGenealogyFragment;
import com.senior.project.genealogy.view.fragment.profile.UpdateProfile.UpdateProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.senior.project.genealogy.util.Constants.EMPTY_STRING;

public class ProfileFragment extends Fragment implements ProfileFragmentView {

    @BindView(R.id.circle_profile)
    CircleImageView imgProfile;

    @BindView(R.id.imgGenger)
    ImageView imgGenger;

    @BindView(R.id.txtFullname)
    TextView txtFullname;

    @BindView(R.id.txtBirthday)
    TextView txtBirthday;

    @BindView(R.id.txtMail)
    TextView txtMail;

    @BindView(R.id.txtAddress)
    TextView txtAddress;

    @BindView(R.id.btnUpdateProfile)
    FloatingActionButton btnUpdateProfile;

    private User myAccount;
    private ProfileFragmentPresenterImpl profileFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, EMPTY_STRING);
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_profile));
        }
        profileFragmentPresenterImpl = new ProfileFragmentPresenterImpl(this);
        profileFragmentPresenterImpl.getProfile(token);
        return view;
    }

    @OnClick(R.id.btnUpdateProfile)
    public void onClick() {
        UpdateProfileFragment mFragment = new UpdateProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", myAccount);
        mFragment.setArguments(bundle);
        mFragment.attachInterface(new UpdateProfileFragment.UpdateProfileInterface() {
            @Override
            public void sendDataUpdateToProfile(User user) {
                showProfile(user);
            }
        });
        pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.profile_frame);

    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            if (getActivity() instanceof HomeActivity) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                if (type == HomeActivity.PushFrgType.REPLACE) {
                    ft.replace(mContainerId, fragment, tag);
                    ft.addToBackStack(fragment.getTag());
                    ft.commitAllowingStateLoss();
                } else if (type == HomeActivity.PushFrgType.ADD) {
                    ft.add(mContainerId, fragment, tag);
                    ft.addToBackStack(fragment.getTag());
                    ft.commit();
                }
                manager.executePendingTransactions();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
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
    public void showProfile(User user) {
        myAccount = user;
        if(user.isGender()){
            imgGenger.setImageResource(R.drawable.ic_male);
        } else {
            imgGenger.setImageResource(R.drawable.ic_female);
        }

        txtFullname.setText(user.getFullname());
        txtBirthday.setText(user.getBirthday());
        txtMail.setText(user.getMail());
        txtAddress.setText(user.getAddress());
        txtBirthday.setText(user.getBirthday());
        Glide.with(GenealogyApplication.getInstance())
                .load(user.getAvatar())
                .into(imgProfile);
    }

}
