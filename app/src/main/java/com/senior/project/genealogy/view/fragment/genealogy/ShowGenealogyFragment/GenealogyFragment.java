package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.CreateGenealogyFragment;
import com.senior.project.genealogy.view.fragment.genealogy.adapter.RecyclerItemTouchHelper;
import com.senior.project.genealogy.view.fragment.genealogy.adapter.RecyclerViewItemGenealogyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.senior.project.genealogy.util.Constants.EMPTY_STRING;

/**
 * CreateBranchFragment => click button [ADD]
 * DetailGenealogyFragment => click each row
 */
public class GenealogyFragment extends Fragment implements GenealogyFragmentView {

    @BindView(R.id.btnCreateGenealogy)
    FloatingActionButton btnCreateGenealogy;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.txtNotice)
    TextView txtNotice;

    @BindView(R.id.swifeRefresh)
    SwipeRefreshLayout swifeRefresh;

    RecyclerViewItemGenealogyAdapter mRcvAdapter;
    List<Genealogy> genealogies;

    private GenealogyFragmentPresenterImpl genealogyFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private List<Genealogy> genealogyList;
    private Context mContext;

    public GenealogyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, EMPTY_STRING);
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_my_genealogy));
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            genealogyList = (List<Genealogy>) getArguments().getSerializable("genealogyList");
            showGenealogy(genealogyList);
        } else {
            genealogyFragmentPresenterImpl = new GenealogyFragmentPresenterImpl(this);
            genealogyFragmentPresenterImpl.getGenealogiesByUsername(token, true);
        }
        swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                genealogyFragmentPresenterImpl.getGenealogiesByUsername(token, false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btnCreateGenealogy)
    public void onClick() {
        CreateGenealogyFragment mFragment = new CreateGenealogyFragment();
        mFragment.attackInterface(new CreateGenealogyFragment.CreateGenealogyInterface() {
            @Override
            public void sendDataToListGenealogy(Genealogy genealogy) {
                if (txtNotice.getVisibility() == View.VISIBLE){
                    txtNotice.setVisibility(View.GONE);
                }
                mRcvAdapter.updateGenealogy(genealogy);
            }
        });
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).updateTitleBar("Create new genealogy");
        }
        pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.genealogy_frame);
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
        if (mContext instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) mContext;
            if (!homeActivity.isFinishing()) {
                if (progressDialog != null && !progressDialog.isShowing())
                    progressDialog.show();
            }
        }
    }

    @Override
    public void closeProgressDialog() {
        if (isAdded() && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void showGenealogy(List<Genealogy> genealogyList) {
        swifeRefresh.setRefreshing(false);
        genealogies = new ArrayList<>();

        if(genealogyList == null){
            txtNotice.setVisibility(View.VISIBLE);
        }
        else {
            genealogies.addAll(genealogyList);
        }
        if (mContext instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) mContext;
            if (!homeActivity.isFinishing()) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                mRcvAdapter = new RecyclerViewItemGenealogyAdapter(getActivity(), fragmentManager, genealogies, this);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mRcvAdapter);
                ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        if (viewHolder instanceof RecyclerViewItemGenealogyAdapter.RecyclerViewHolder) {
                            if (genealogies.get(viewHolder.getAdapterPosition()).getRole() == Constants.ROLE.OWNER_ROLE) {
                                showAlertDialog("Delete", "Are you sure?", "Delete", "Cancel", viewHolder, position);
                            } else {
                                showToast("You don't have permission to delete it!");
                                mRcvAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        }
                    }
                });
                new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
            }
        }
    }


    public void showAlertDialog(String title, String message, String positive, String negative, final RecyclerView.ViewHolder viewHolder, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int genealogyId = genealogies.get(viewHolder.getAdapterPosition()).getId();
                genealogyFragmentPresenterImpl.deleteGenealogy(genealogyId, token, viewHolder);
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mRcvAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void deleteItemGenealogy(RecyclerView.ViewHolder viewHolder){
        mRcvAdapter.removeItem(viewHolder.getAdapterPosition());
        if (mRcvAdapter.getItemCount() == 0){
            txtNotice.setVisibility(View.VISIBLE);
        }
    }

    public void refreshListGenealogies() {
        genealogyFragmentPresenterImpl = new GenealogyFragmentPresenterImpl(this);
        genealogyFragmentPresenterImpl.getGenealogiesByUsername(token, true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
