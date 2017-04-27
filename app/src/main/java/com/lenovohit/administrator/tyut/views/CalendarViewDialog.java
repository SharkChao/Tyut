package com.lenovohit.administrator.tyut.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.utils.StringUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * 弹出日历控件
 * Created by yuzhijun on 2016/12/28.
 */
public class CalendarViewDialog extends AppCompatDialogFragment implements OnDateSelectedListener,DialogInterface.OnClickListener {
    private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private TextView textView;
    private MaterialCalendarView widget;

    public static CalendarViewDialog newInstance(String startDate,String endDate){
        CalendarViewDialog calendarViewDialog  = new CalendarViewDialog();
        Bundle bundle = new Bundle();
        bundle.putString("startDate",startDate);
        bundle.putString("endDate",endDate);
        calendarViewDialog.setArguments(bundle);
        return calendarViewDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_basic, null);

        textView = (TextView) view.findViewById(R.id.textView);
        widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);

        String startDate = (String) getArguments().get("startDate");
        String endDate = (String) getArguments().get("endDate");
        setMinMaxDate(startDate,endDate);

        return new AlertDialog.Builder(getActivity())
                .setTitle("请选择要查看的日期")
                .setView(view)
                .setPositiveButton(android.R.string.ok, this)
                .create();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        textView.setText(FORMATTER.format(date.getDate()));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (StringUtil.isNotEmpty(mIDateCallBack)){
            String dateStr = textView.getText().toString();
            mIDateCallBack.getDateSelected(StringUtil.isStrEmpty(dateStr)?"":dateStr);
        }
    }

    public void setMinMaxDate(String minDate,String maxDate){
        if(!StringUtil.isStrEmpty(minDate) && minDate.length() >= 10 ){
            int minYear = Integer.parseInt(minDate.substring(0,4));
            int minMonth = Integer.parseInt(minDate.substring(5,7));
            int minDay = Integer.parseInt(minDate.substring(8,10));
            widget.state().edit().setMinimumDate(CalendarDay.from(minYear, minMonth-1, minDay)).commit();
        }
        if ( !StringUtil.isStrEmpty(maxDate) && maxDate.length() >= 10){
            int maxYear = Integer.parseInt(maxDate.substring(0,4));
            int maxMonth = Integer.parseInt(maxDate.substring(5,7));
            int maxDay = Integer.parseInt(maxDate.substring(8,10));
            widget.state().edit().setMaximumDate(CalendarDay.from(maxYear, maxMonth-1, maxDay)).commit();
        }
    }

    private IDateCallBack mIDateCallBack;
    public void registerDateCallBack(IDateCallBack dateCallBack){
        mIDateCallBack = dateCallBack;
    }
    public interface IDateCallBack{
        void getDateSelected(String date);
    }
}
