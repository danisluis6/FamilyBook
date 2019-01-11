package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogEventFragment extends DialogFragment implements DialogEventFragmentView {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.edtSetTime)
    EditText edtSetTime;

    @BindView(R.id.edtSetDate)
    EditText edtSetDate;

    @BindView(R.id.edtContent)
    EditText edtContent;

    @BindView(R.id.btnCreateEvent)
    Button btnCreateEvent;

    private Calendar mCalendar;

    public DialogEventFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_event, container, false);
        setCancelable(false);
        ButterKnife.bind(this, view);
        initComponents();
        checkInputValue();
        return view;
    }

    private void initComponents() {
        tvTitle.setText(getResources().getString(R.string.frg_title_event));
        resetFrom();
    }

    private void resetFrom() {
        edtContent.setError(null);
        edtSetDate.setError(null);
        edtSetTime.setError(null);
    }

    public static DialogEventFragment newInstance() {
        DialogEventFragment dialog = new DialogEventFragment();
        return dialog;
    }

    @OnClick({ R.id.btnClose, R.id.btnCreateEvent, R.id.edtSetDate, R.id.edtSetTime })
    void onClick(View view) {
        if (Utils.isDoubleClick()) return;
        switch (view.getId()) {
            case R.id.btnClose:
                this.dismiss();
                break;
            case R.id.btnCreateEvent:
                if(isDataValid()) {
                    mDialogEventInterface.sendDataToFrg(getDataFromForm());
                }
                break;
            case R.id.edtSetDate:
                selectDate(edtSetDate);
                break;
            case R.id.edtSetTime:
                selectTime(edtSetTime);
                break;
        }
    }


    private void checkInputValue(){
        if (edtSetDate != null && edtSetTime != null && edtContent != null){
            btnCreateEvent.setBackgroundResource(R.drawable.radius_button);
            btnCreateEvent.setEnabled(true);
        } else {
            btnCreateEvent.setBackgroundResource(R.drawable.radius_button_disable);
            btnCreateEvent.setEnabled(false);
        }
    }

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
        datePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void selectTime(final EditText edt){
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                edt.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public Event getDataFromForm() {
        Event event = new Event();
        event.setContent(edtContent.getText().toString());
        event.setDate(edtSetDate.getText().toString() + " " + edtSetTime.getText().toString() + ":00");
        return event;
    }

    public boolean isDataValid() {
        if (edtSetDate.getText().toString().isEmpty()) {
            edtSetDate.setError(getString(R.string.error_set_date));
            edtSetDate.requestFocus();
            return false;
        } else if (edtSetTime.getText().toString().isEmpty()) {
            edtSetTime.setError(getString(R.string.error_set_time));
            edtSetTime.requestFocus();
            return false;
        } else if (edtContent.getText().toString().isEmpty()) {
            edtContent.setError(getString(R.string.error_event_content));
            edtContent.requestFocus();
            return false;
        }
        return true;
    }

    public interface DialogEventInterface {
        void sendDataToFrg(Event event);
    }

    public DialogEventInterface mDialogEventInterface;

    public void attackInterface(DialogEventInterface dialogEventInterface){
        mDialogEventInterface = dialogEventInterface;
    }
}
