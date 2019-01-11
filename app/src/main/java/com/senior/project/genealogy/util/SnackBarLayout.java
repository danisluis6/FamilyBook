package com.senior.project.genealogy.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.senior.project.genealogy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SnackBarLayout extends LinearLayout {

    @BindView(R.id.lnCamera)
    LinearLayout lnCamera;

    @BindView(R.id.lnGallery)
    LinearLayout lnGallery;

    public interface DialogInterface {
        void openCamera();
        void openGallery();
    }

    private static DialogInterface listener;

    public SnackBarLayout(Context context) {
        super(context);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (mInflater == null)
            return;
        View view = mInflater.inflate(R.layout.dialog_pick, this, true);
        ButterKnife.bind(this, view);
    }

    public void attachDialogInterface(DialogInterface _interface) {
        listener = _interface;
    }

    @OnClick(R.id.lnCamera)
    public void OpenCamera() {
        listener.openCamera();
    }

    @OnClick(R.id.lnGallery)
    public void OpenGallery() {
        listener.openGallery();
    }

}