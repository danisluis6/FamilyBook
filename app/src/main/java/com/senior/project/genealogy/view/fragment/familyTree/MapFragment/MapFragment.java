package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Couple;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.familyTree.DialogNode.DialogNodeFragment;
import com.senior.project.genealogy.view.fragment.familyTree.DialogProfile.DialogProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;
import de.hdodenhof.circleimageview.CircleImageView;


public class MapFragment extends Fragment implements MapFragmentView{

    @BindView(R.id.graph)
    GraphView graphView;

    @BindView(R.id.addNode)
    FloatingActionButton addNode;

    @BindView(R.id.btnFollow)
    ImageButton btnFollow;

    @BindView(R.id.btnFollowed)
    ImageButton btnFollowed;

    @BindView(R.id.btnWaiting)
    ImageButton btnWaiting;

    @BindView(R.id.txtNotice)
    TextView txtNotice;

    @BindView(R.id.txtNoticeMemberAndGuest)
    TextView txtNoticeMemberAndGuest;

    private MapFragmentPresenterImpl mapFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private Graph graph;
    private Branch branch;
    protected BaseGraphAdapter<ViewHolder> adapter;

    public MapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_family_tree));
        branch = (Branch) getArguments().getSerializable("branch");
        if (branch.getRole() == Constants.ROLE.OWNER_ROLE || branch.getRole() == Constants.ROLE.EDITOR_ROLE
                || branch.getRole() == Constants.ROLE.MEMBER_ROLE){
            btnFollowed.setVisibility(View.VISIBLE);
        }else {
            if (branch.getRole() == Constants.ROLE.WAITING){
                btnWaiting.setVisibility(View.VISIBLE);
            } else {
                btnFollow.setVisibility(View.VISIBLE);
            }
        }
        mapFragmentPresenterImpl = new MapFragmentPresenterImpl(this);
        mapFragmentPresenterImpl.getFamilyTreeByBranchId(branch.getId(), token);
        return view;
    }

    @Override
    public void showMap(List<People> peopleList){

        if(peopleList.size() == 0){
            if (branch.getRole() == Constants.ROLE.OWNER_ROLE || branch.getRole() == Constants.ROLE.EDITOR_ROLE){
                addNode.setVisibility(View.VISIBLE);
                txtNotice.setVisibility(View.VISIBLE);
            }else {
                addNode.setVisibility(View.GONE);
                txtNoticeMemberAndGuest.setVisibility(View.VISIBLE);
            }
        }else {
            txtNotice.setVisibility(View.GONE);
            graph = new Graph();
            if (peopleList.size() == 1){
                //lấy mảng couple từ mảng people
                //truyền couple vào 1 node
                List<Couple> coupleList = getCoupleListFromPeopleList(peopleList);
                Node node = new Node(coupleList.get(0));
                graph.addNode(node);
            }
            else {
                List<Couple> coupleList = getCoupleListFromPeopleList(peopleList);
                for (Couple couple : coupleList) {
                    graph.addNode(new Node(couple));
                    if (couple.getMainPeople().getParentId() != null) {
                        graph.addEdge(findNode(graph, couple.getMainPeople().getParentId()), findNode(graph, couple.getMainPeople().getId()));
                    }
                }
            }
            setupAdapter(graph);
        }
    }

    private List<Couple> getCoupleListFromPeopleList(List<People> peopleList){
        List<Couple> coupleList = new ArrayList<>();
        for(People people:peopleList){
            if(people.getPartnerId()==null){
                coupleList.add(new Couple(people));
            }
        }

        for(Couple couple:coupleList){
            for(People people:peopleList){
                if (people.getPartnerId() != null){
                    if(people.getPartnerId() == couple.getMainPeople().getId()){
                        couple.setPartnerPeople(people);
                    }
                }
            }
        }

        return coupleList;
    }

    @Override
    public void deletePeople(int peopleId) {
        if(findNode(graph, peopleId) != null){
            graph.removeNode(findNode(graph, peopleId));
        }
        if(findNodeByPartner(graph, peopleId) != null){
            Node updatedNode = findNodeByPartner(graph, peopleId);
            Couple updatedCouple= ((Couple)updatedNode.getData());
            updatedCouple.setPartnerPeople(null);
            updatedNode.setData(updatedCouple);
            adapter.notifyDataChanged(updatedNode);
        }
        if (graph.getNodes().size() == 0){
            txtNotice.setVisibility(View.VISIBLE);
            addNode.setVisibility(View.VISIBLE);
        }
    }

    public Node findNode(Graph graph, int id){
        List<Node> nodeList = graph.getNodes();
        Node nodeReturn = null;
        for (Node node : nodeList){
            if(((Couple) node.getData()).getMainPeople().getId() == id){
                nodeReturn = node;
            }
        }
        return nodeReturn;
    }

    public Node findNodeByPartner(Graph graph, int id){
        List<Node> nodeList = graph.getNodes();
        Node nodeReturn = null;
        for (Node node : nodeList){
            if(((Couple) node.getData()).getPartnerPeople() != null){
                if(((Couple) node.getData()).getPartnerPeople().getId() == id){
                    nodeReturn = node;
                }
            }
        }
        return nodeReturn;
    }

    public List<Node> findNodesByParentId(Graph graph, int id){
        List<Node> nodeList = graph.getNodes();
        showToast(String.valueOf(nodeList.size()));
        List<Node> nodesReturn = new ArrayList<>();
        for (Node node : nodeList){
            if(((People) node.getData()).getParentId() != null){
                if(((People) node.getData()).getParentId() == id){
                    nodesReturn.add(node);
                }
            }
        }
        return nodesReturn;
    }

    private void setupAdapter(final Graph graph) {
        adapter = new BaseGraphAdapter<ViewHolder>(this.getContext(), R.layout.node, graph) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, final Object data, int position) {
                viewHolder.nodeBg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                     FIRST_MAN_SINGLE = 1;
//                     FIRST_MAN_MARRIED = 2;
//                     MAN_SINGLE = 3;
//                     MAN_MARRIED = 4;
//                     WOMAN_SINGLE = 5;
//                     WOMAN_MARRIED = 6;
//                     PARTNER = 7;
                        int nodeType;
                        if (((Couple)data).getPartnerPeople() != null){
                            if (((Couple)data).getMainPeople().getGender() == 0){
                                nodeType = Constants.NODE_TYPE.WOMAN_MARRIED;
                            } else {
                                if (((Couple)data).getMainPeople().getParentId() == null){
                                    nodeType = Constants.NODE_TYPE.FIRST_MAN_MARRIED;
                                } else {
                                    nodeType = Constants.NODE_TYPE.MAN_MARRIED;
                                }
                            }
                        } else {
                            if (((Couple)data).getMainPeople().getGender() == 0){
                                nodeType = Constants.NODE_TYPE.WOMAN_SINGLE;
                            } else {
                                if (((Couple)data).getMainPeople().getParentId() == null){
                                    nodeType = Constants.NODE_TYPE.FIRST_MAN_SINGLE;
                                } else {
                                    nodeType = Constants.NODE_TYPE.MAN_SINGLE;
                                }
                            }
                        }
                        DialogProfileFragment dialogProfileFragment = DialogProfileFragment.newInstance(((Couple)data).getMainPeople(), getArguments().getInt("branchId"), nodeType);
                        dialogProfileFragment.show(getActivity().getSupportFragmentManager(), null);

                        dialogProfileFragment.attackInterface(new DialogProfileFragment.DialogProfileInterface() {
                            @Override
                            public void sendDataToMap(People people) {
                                if (people.getParentId() != null){
                                    Couple couple = new Couple(people);
                                    graph.addNode(new Node(couple));
                                    graph.addEdge(findNode(graph, people.getParentId()), findNode(graph, people.getId()));
                                } else {
                                    Node updatedNode = findNode(graph, people.getPartnerId());
                                    Couple updatedCouple= ((Couple)updatedNode.getData());
                                    updatedCouple.setPartnerPeople(people);
                                    updatedNode.setData(updatedCouple);
                                    adapter.notifyDataChanged(updatedNode);
                                }
                            }

                            @Override
                            public void sendUpdateDataToMap(People people) {
                                Node updatedNodeMain = findNode(graph, people.getId());
                                if (updatedNodeMain != null){
                                    Couple updatedCouple = ((Couple)updatedNodeMain.getData());
                                    updatedCouple.setMainPeople(people);
                                    updatedNodeMain.setData(updatedCouple);
                                    adapter.notifyDataChanged(updatedNodeMain);
                                } else {
                                    Node updatedNodePartner = findNodeByPartner(graph, people.getId());
                                    Couple updatedCouple = ((Couple)updatedNodePartner.getData());
                                    updatedCouple.setPartnerPeople(people);
                                    updatedNodePartner.setData(updatedCouple);
                                    adapter.notifyDataChanged(updatedNodePartner);
                                }
                            }
                        });
                        dialogProfileFragment.attackInterface(new DialogProfileFragment.GetRelationInterface() {
                            @Override
                            public void sendDataToMap(List<People> peopleList) {
                                deleteNode();

                                List<Couple> coupleList = getCoupleListFromPeopleList(peopleList);
                                for (Couple couple : coupleList) {
                                    graph.addNode(new Node(couple));
                                    if (couple.getMainPeople().getParentId() != null) {
                                        graph.addEdge(findNode(graph, couple.getMainPeople().getParentId()), findNode(graph, couple.getMainPeople().getId()));
                                    }
                                }
                            }
                        });
                    }
                });

                viewHolder.nodeBg.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (branch.getRole() == Constants.ROLE.OWNER_ROLE || branch.getRole() == Constants.ROLE.EDITOR_ROLE){
                            showDeleteAlertDialog(((Couple)data).getMainPeople());
                        }
                        return false;
                    }
                });

                viewHolder.txtName.setText(((Couple)data).getMainPeople().getName());
                if(((Couple)data).getMainPeople().getBirthday() != null)
                {
                    String date = ((Couple)data).getMainPeople().getBirthday();
                    if(((Couple)data).getMainPeople().getDeathDay() != null){
                        date = date + " - " + ((Couple)data).getMainPeople().getDeathDay();
                    }
                    viewHolder.txtDate.setText(date);
                } else {
                    viewHolder.txtDate.setText("");
                }

                if(((Couple)data).getMainPeople().getGender() != null){
                    if(((Couple)data).getMainPeople().getGender() == 1){
                        viewHolder.civProfile.setImageResource(R.drawable.man);
                    } else {
                        viewHolder.civProfile.setImageResource(R.drawable.woman);
                    }
                }

                if(((Couple)data).getMainPeople().getAppellation() != null){
                    viewHolder.txtRelation.setVisibility(View.VISIBLE);
                    viewHolder.txtRelation.setText(((Couple)data).getMainPeople().getAppellation());

                    if("Center".equals(((Couple)data).getMainPeople().getAppellation())) {
                        viewHolder.txtRelation.setText("Tôi");
                        viewHolder.txtRelation.setTypeface(null, Typeface.ITALIC);
                        viewHolder.txtRelation.setTypeface(null, Typeface.BOLD);
                        @ColorInt int nodeBgColor = 0xFFE9C2B3;
                        viewHolder.nodeBg.setBackgroundColor(nodeBgColor);
                    }
                }

                //partner
                if(((Couple)data).getPartnerPeople() != null){
                    viewHolder.nodePartnerBg.setVisibility(View.VISIBLE);
                    viewHolder.relationLine.setVisibility(View.VISIBLE);
                    viewHolder.marginLeft.setVisibility(View.VISIBLE);

                    viewHolder.nodePartnerBg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogProfileFragment dialogProfileFragment = DialogProfileFragment.newInstance(((Couple)data).getPartnerPeople(), getArguments().getInt("branchId"), Constants.NODE_TYPE.PARTNER);
                            dialogProfileFragment.show(getActivity().getSupportFragmentManager(), null);

                            dialogProfileFragment.attackInterface(new DialogProfileFragment.DialogProfileInterface() {
                                @Override
                                public void sendDataToMap(People people) {
                                    if (people.getParentId() == null){
                                        Couple couple = new Couple(people);
                                        graph.addNode(new Node(couple));
                                        graph.addEdge(findNode(graph, people.getParentId()), findNode(graph, people.getId()));
                                    } else {
                                        Node updatedNode = findNode(graph, people.getPartnerId());
                                        Couple updatedCouple= ((Couple)updatedNode.getData());
                                        updatedCouple.setPartnerPeople(people);
                                        updatedNode.setData(updatedCouple);
                                        adapter.notifyDataChanged(updatedNode);
                                    }
                                }

                                @Override
                                public void sendUpdateDataToMap(People people) {
                                    Node updatedNodeMain = findNode(graph, people.getId());
                                    if (updatedNodeMain != null){
                                        Couple updatedCouple = ((Couple)updatedNodeMain.getData());
                                        updatedCouple.setMainPeople(people);
                                        updatedNodeMain.setData(updatedCouple);
                                        adapter.notifyDataChanged(updatedNodeMain);
                                    } else {
                                        Node updatedNodePartner = findNodeByPartner(graph, people.getId());
                                        Couple updatedCouple = ((Couple)updatedNodePartner.getData());
                                        updatedCouple.setPartnerPeople(people);
                                        updatedNodePartner.setData(updatedCouple);
                                        adapter.notifyDataChanged(updatedNodePartner);
                                    }
                                }
                            });
                            dialogProfileFragment.attackInterface(new DialogProfileFragment.GetRelationInterface() {
                                @Override
                                public void sendDataToMap(List<People> peopleList) {
                                    deleteNode();

                                    List<Couple> coupleList = getCoupleListFromPeopleList(peopleList);
                                    for (Couple couple : coupleList) {
                                        graph.addNode(new Node(couple));
                                        if (couple.getMainPeople().getParentId() != null) {
                                            graph.addEdge(findNode(graph, couple.getMainPeople().getParentId()), findNode(graph, couple.getMainPeople().getId()));
                                        }
                                    }
                                }
                            });
                        }
                    });

                    viewHolder.nodePartnerBg.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (branch.getRole() == Constants.ROLE.OWNER_ROLE || branch.getRole() == Constants.ROLE.EDITOR_ROLE){
                                showDeleteAlertDialog(((Couple)data).getPartnerPeople());
                            }
                            return false;
                        }
                    });

                    viewHolder.txtPartnerName .setText(((Couple)data).getPartnerPeople().getName());
                    if(((Couple)data).getPartnerPeople().getBirthday() != null)
                    {
                        String date = ((Couple)data).getPartnerPeople().getBirthday();
                        if(((Couple)data).getPartnerPeople().getDeathDay() != null){
                            date = date + " - " + ((Couple)data).getPartnerPeople().getDeathDay();
                        }
                        viewHolder.txtPartnerDate .setText(date);
                    } else {
                        viewHolder.txtPartnerDate .setText("");
                    }

                    if(((Couple)data).getPartnerPeople().getGender() != null){
                        if(((Couple)data).getPartnerPeople().getGender() == 1){
                            viewHolder.civPartnerProfile .setImageResource(R.drawable.man);
                        } else {
                            viewHolder.civPartnerProfile .setImageResource(R.drawable.woman);
                        }
                    }

                    if(((Couple)data).getPartnerPeople().getAppellation() != null){
                        viewHolder.txtPartnerRelation .setVisibility(View.VISIBLE);
                        viewHolder.txtPartnerRelation .setText(((Couple)data).getPartnerPeople().getAppellation());

                        if("Center".equals(((Couple)data).getPartnerPeople().getAppellation())) {
                            viewHolder.txtPartnerRelation .setText("Tôi");
                            viewHolder.txtPartnerRelation .setTypeface(null, Typeface.ITALIC);
                            viewHolder.txtPartnerRelation .setTypeface(null, Typeface.BOLD);
                            @ColorInt int nodeBgColor = 0xFFE9C2B3;
                            viewHolder.nodePartnerBg .setBackgroundColor(nodeBgColor);
                        }
                    } else {
                        if(((Couple)data).getMainPeople().getAppellation() != null){
                            String appellation = ((Couple)data).getMainPeople().getAppellation();
                            switch (appellation){
                                case "Tôi":
                                    if(((Couple)data).getMainPeople().getGender() == 0){
                                        viewHolder.txtPartnerRelation.setText("Chồng");
                                    } else {
                                        viewHolder.txtPartnerRelation.setText("Vợ");
                                    }
                                    break;
                                case "Cha":
                                    viewHolder.txtPartnerRelation.setText("Mẹ");
                                    break;
                                case "O":
                                    viewHolder.txtPartnerRelation.setText("Dượng");
                                    break;
                                case "Bác":
                                    viewHolder.txtPartnerRelation.setText("Bác");
                                    break;
                                case "Chú":
                                    viewHolder.txtPartnerRelation.setText("Thím");
                                    break;
                            }
                            viewHolder.txtPartnerRelation .setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        };

        setAlgorithm(adapter);
        graphView.setAdapter(adapter);
    }

    public void showDeleteAlertDialog(final People people){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete this person");
        builder.setMessage("Are you sure you want to delete " + people.getName() + " from the family tree?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mapFragmentPresenterImpl.deletePeople(people.getId(), token);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteNode(){
        List<Node> nodes = graph.getNodes();
        if(nodes.size() != 0){
            for (Node node : nodes){
                graph.removeNode(node);
                break;
            }
            deleteNode();
        }
    }

    public void setAlgorithm(GraphAdapter adapter) {
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(400)
                .setLevelSeparation(200)
                .setSubtreeSeparation(500)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
    }

    @OnClick({R.id.addNode, R.id.btnFollow, R.id.btnFollowed, R.id.btnWaiting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNode:
                DialogNodeFragment dialogNodeFragment = DialogNodeFragment.newInstance(null, String.valueOf(branch.getId()), 0);
                dialogNodeFragment.show(getActivity().getSupportFragmentManager(), null);
                dialogNodeFragment.attackInterface(new DialogNodeFragment.CreateNodeInterface() {
                    @Override
                    public void sendDataToMap(People people) {
                        graph = new Graph();
                        graph.addNode(new Node(new Couple(people)));
                        setupAdapter(graph);
                        addNode.setVisibility(View.GONE);
                        txtNotice.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.btnFollow:
                UserBranchPermission userBranchPermission = new UserBranchPermission(branch.getId(), Constants.ROLE.MEMBER_ROLE);
                mapFragmentPresenterImpl.joinBranch(userBranchPermission, token);
                break;
            case R.id.btnFollowed:
                if(branch.getRole() == Constants.ROLE.OWNER_ROLE) {
                    showAlertDialog();
                } else {
                    showOutBranchDialog();
                }
                break;
            case R.id.btnWaiting:
                showToast("You have sent request! \nPlease wait for reply from owner of branch!");
                break;
        }
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("You can't out of your branch!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showOutBranchDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Out this branch");
        builder.setMessage("Are you sure you want to out " + branch.getName() + " ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserBranchPermission outBranch = new UserBranchPermission(branch.getId());
                mapFragmentPresenterImpl.outBranch(outBranch, token);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void joinBranchSuccess() {
        btnFollowed.setVisibility(View.GONE);
        btnWaiting.setVisibility(View.VISIBLE);
    }

    @Override
    public void joinBranchFalse() {
        showToast("False");
    }

    @Override
    public void outBranchSuccess() {
        btnFollowed.setVisibility(View.GONE);
        btnWaiting.setVisibility(View.GONE);
        btnFollow.setVisibility(View.VISIBLE);
    }

    @Override
    public void outBranchFalse() {
        showToast("False");
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

//    @Override
//    public void onDestroyView() {
//        mUpdateFamilyTreeListInterface.refreshFamilyTree();
//        super.onDestroyView();
//    }

    public interface UpdateFamilyTreeListInterface{
        void refreshFamilyTree();
    }

    public UpdateFamilyTreeListInterface mUpdateFamilyTreeListInterface;

    public void attachInterface(UpdateFamilyTreeListInterface updateFamilyTreeListInterface){
        mUpdateFamilyTreeListInterface = updateFamilyTreeListInterface;
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtDate;
        TextView txtRelation;
        CircleImageView civProfile;
        LinearLayout nodeBg;
        LinearLayout relationLine;
        LinearLayout nodePartnerBg;
        CircleImageView civPartnerProfile;
        TextView txtPartnerName;
        TextView txtPartnerDate;
        TextView txtPartnerRelation;
        LinearLayout marginLeft;
        ViewHolder(View view) {
            txtName = view.findViewById(R.id.txtName);
            txtDate = view.findViewById(R.id.txtDate);
            txtRelation = view.findViewById(R.id.txtRelation);
            civProfile = view.findViewById(R.id.civProfile);
            nodeBg = view.findViewById(R.id.nodeBg);
            relationLine = view.findViewById(R.id.relationLine);
            nodePartnerBg = view.findViewById(R.id.nodePartnerBg);
            civPartnerProfile = view.findViewById(R.id.civPartnerProfile);
            txtPartnerName = view.findViewById(R.id.txtPartnerName);
            txtPartnerDate = view.findViewById(R.id.txtPartnerDate);
            txtPartnerRelation = view.findViewById(R.id.txtPartnerRelation);
            marginLeft = view.findViewById(R.id.marginLeft);
        }
    }
}