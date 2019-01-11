package com.senior.project.genealogy.view.fragment.search.MemberSearchFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment.DetailBranchFragment;
import com.senior.project.genealogy.view.fragment.search.Adapter.RecyclerViewItemPeopleAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PeopleSearchFragment extends Fragment implements PeopleSearchFragmentView{

    @BindView(R.id.fullname)
    TextInputEditText fullname;

    @BindView(R.id.nickname)
    TextInputEditText nickname;

    @BindView(R.id.yearOfBirth)
    TextInputEditText yearOfBirth;

    @BindView(R.id.yearOfDeath)
    TextInputEditText yearOfDeath;

    @BindView(R.id.address)
    TextInputEditText address;

    @BindView(R.id.radioGender)
    RadioGroup radioGender;

    @BindView(R.id.radioMale)
    RadioButton radioMale;

    @BindView(R.id.radioFemale)
    RadioButton radioFemale;

    @BindView(R.id.btnSearchPeople)
    Button btnSearchPeople;

    @BindView(R.id.rcvPeople)
    RecyclerView rcvPeople;

    @BindView(R.id.txtNotFoundPeople)
    TextView txtNotFoundPeople;

    @BindView(R.id.searchForm)
    LinearLayout searchForm;

    @BindView(R.id.btnSearchAgain)
    Button btnSearchAgain;

    private ProgressDialog mProgressDialog;
    private List<People> peopleList;
    private RecyclerViewItemPeopleAdapter mRcvAdapter;
    private String token;
    private PeopleSearchFragmentPresenter peopleSearchFragmentPresenter;
    public PeopleSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_people, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, Constants.EMPTY_STRING);
        peopleSearchFragmentPresenter = new PeopleSearchFragmentPresenterImpl(this);
        initRcvPeople();
        return view;
    }

    public void initRcvPeople(){
        peopleList = new ArrayList<>();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mRcvAdapter = new RecyclerViewItemPeopleAdapter(getActivity(), fragmentManager, peopleList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvPeople.setLayoutManager(layoutManager);
        rcvPeople.setAdapter(mRcvAdapter);
    }

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @OnClick({R.id.btnSearchPeople, R.id.yearOfBirth, R.id.yearOfDeath, R.id.btnSearchAgain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearchPeople:
                Utils.hiddenKeyBoard(getActivity());
                peopleSearchFragmentPresenter.searchBranchByPeople(getPeopleSearch(), token);
                break;
            case R.id.yearOfBirth:
                onCreateDialog("Choose year of birth:", yearOfBirth);
                break;
            case R.id.yearOfDeath:
                onCreateDialog("Choose year of birth:", yearOfDeath);
                break;
            case R.id.btnSearchAgain:
                searchForm.setVisibility(View.VISIBLE);
                btnSearchAgain.setVisibility(View.GONE);
                break;
        }
    }

    public People getPeopleSearch(){
        People people = new People();
        if (!"".equals(fullname.getText().toString().trim())){
            people.setName(fullname.getText().toString());
        }
        if (!"".equals(nickname.getText().toString().trim())){
            people.setNickname(nickname.getText().toString());
        }
        if (!"".equals(yearOfBirth.getText().toString())){
            String birthday =  "01/01/" + yearOfBirth.getText().toString();
            people.setBirthday(birthday);
        }
        if (!"".equals(yearOfDeath.getText().toString())){
            String deathday = "01/01/" + yearOfDeath.getText().toString();
            people.setDeathDay(deathday);
        }
        if (radioMale.isChecked()){
            people.setGender(1);
        }
        if (radioFemale.isChecked()){
            people.setGender(0);
        }
        return people;
    }

    @Override
    public void showPeople(List<People> peopleList) {
        searchForm.setVisibility(View.GONE);
        btnSearchAgain.setVisibility(View.VISIBLE);
        if(peopleList.size() == 0){
            txtNotFoundPeople.setVisibility(View.VISIBLE);
            mRcvAdapter.updateRcvPeople(peopleList);
        }
        else {
            txtNotFoundPeople.setVisibility(View.GONE);
            mRcvAdapter.updateRcvPeople(peopleList);
        }
    }

    @Override
    public void getBranchById(Integer branchId) {
        peopleSearchFragmentPresenter.getBranchByBranchId(branchId, token);
    }

    @Override
    public void showBranch(Branch branch) {
        DetailBranchFragment mFragment = new DetailBranchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("branch", branch);
        mFragment.setArguments(bundle);
        mFragment.attachInterface(new DetailBranchFragment.UpdateBranchInterface() {
            @Override
            public void refreshBranches() {
                //
            }
        });
        pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.search_frame);
    }

    public Dialog onCreateDialog(String title, final TextInputEditText editText)
    {
        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMaxValue(2018);
        numberPicker.setMinValue(1700);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(numberPicker);
        builder.setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                editText.setText(String.valueOf(numberPicker.getValue()));
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.create();
        return builder.show();
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
}
