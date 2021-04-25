package markus.wieland.dvbfahrplan.ui.timepicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDateTime;

import markus.wieland.dvbfahrplan.R;

public class TimePickerBottomSheetDialog extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener {

    private TimePickerEventListener eventListener;
    private RadioGroup arrivalRadioGroup;
    private DatePickerDialog datePickerDialog;

    private PickedTime pickedTime;

    private NumberPicker numberPickerHour;
    private NumberPicker numberPickerMinute;

    private TextView timePickerDate;

    private int year;
    private int month;
    private int day;

    public TimePickerBottomSheetDialog(PickedTime pickedTime) {
        this.pickedTime = pickedTime;
        this.year = pickedTime.getYear();
        this.month = pickedTime.getMonth();
        this.day = pickedTime.getDayOfMonth();
    }

    public TimePickerBottomSheetDialog() {
        this(new PickedTime());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_time_picker, container, false);

        numberPickerHour = view.findViewById(R.id.time_picker_hour);
        numberPickerMinute = view.findViewById(R.id.time_picker_minute);
        arrivalRadioGroup = view.findViewById(R.id.time_picker_radio_group);
        timePickerDate = view.findViewById(R.id.time_picker_date_value);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        view.findViewById(R.id.time_picker_date).setOnClickListener(v -> showDatePickerDialog());
        view.findViewById(R.id.time_picker_set).setOnClickListener(v -> setDate());
        view.findViewById(R.id.time_picker_cancel).setOnClickListener(v -> dismiss());

        timePickerDate.setText(pickedTime.getDateAsString(getActivity()));

        numberPickerMinute.setMinValue(0);
        numberPickerMinute.setMaxValue(59);
        numberPickerMinute.setValue(pickedTime.getMinute());

        numberPickerHour.setMinValue(0);
        numberPickerHour.setMaxValue(23);
        numberPickerHour.setValue(pickedTime.getHour());
        execute();
    }

    private void showDatePickerDialog() {
        datePickerDialog.show();
    }

    private void setDate() {
        pickedTime = getPickedTime();
        eventListener.onSetDate(pickedTime);
        dismiss();
    }

    private PickedTime getPickedTime(){
        return new PickedTime(LocalDateTime.of(year, month, day, numberPickerHour.getValue(), numberPickerMinute.getValue()), arrivalRadioGroup.getCheckedRadioButtonId() == R.id.time_picker_arrival);

    }

    private void execute() {
        datePickerDialog = new DatePickerDialog(
                getActivity(), this, pickedTime.getYear(), pickedTime.getMonth() - 1, pickedTime.getDayOfMonth());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            eventListener = (TimePickerEventListener) context;
        } catch (Exception e) {
            throw new ClassCastException((context.toString()) + "must implement TimePickerEventListener");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month + 1; // Convert to local date time month
        this.day = dayOfMonth;
        timePickerDate.setText(getPickedTime().getDateAsString(getActivity()));
    }
}
