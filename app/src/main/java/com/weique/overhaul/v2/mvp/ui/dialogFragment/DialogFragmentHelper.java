package com.weique.overhaul.v2.mvp.ui.dialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.weique.overhaul.v2.R;

import java.util.Calendar;

public class DialogFragmentHelper {


    private static final String DIALOG_POSITIVE = "确定";
    private static final String DIALOG_NEGATIVE = "取消";

    private static final String TAG_HEAD = DialogFragmentHelper.class.getSimpleName();

    /**
     * 加载中的弹出窗
     */
    private static final int PROGRESS_THEME = R.style.Base_AlertDialog;
    private static final String PROGRESS_TAG = TAG_HEAD + ":progress";

    public static CommonDialogFragment showProgress(FragmentManager fragmentManager, String message) {
        return showProgress(fragmentManager, message, true, null);
    }

    public static CommonDialogFragment showProgress(FragmentManager fragmentManager, String message, boolean cancelable) {
        return showProgress(fragmentManager, message, cancelable, null);
    }

    private static CommonDialogFragment showProgress(FragmentManager fragmentManager, final String message, boolean cancelable
            , CommonDialogFragment.OnDialogCancelListener cancelListener) {

        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            ProgressDialog progressDialog = new ProgressDialog(context, PROGRESS_THEME);
            progressDialog.setMessage(message);
            return progressDialog;
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, PROGRESS_TAG);
        return dialogFragment;
    }

    /**
     * 简单提示弹出窗
     */
    private static final int TIPS_THEME = R.style.Base_AlertDialog;
    private static final String TIPS_TAG = TAG_HEAD + ":tips";

    public static void showTips(FragmentManager fragmentManager, String message) {
        showTips(fragmentManager, message, true, null);
    }

    public static void showTips(FragmentManager fragmentManager, String message, boolean cancelable) {
        showTips(fragmentManager, message, cancelable, null);
    }

    private static void showTips(FragmentManager fragmentManager, final String message, boolean cancelable
            , CommonDialogFragment.OnDialogCancelListener cancelListener) {

        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, TIPS_THEME);
            builder.setMessage(message);
            return builder.create();
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, TIPS_TAG);
    }


    /**
     * 确定取消框
     */
    private static final int CONFIRM_THEME = R.style.Base_AlertDialog;
    private static final String CONfIRM_TAG = TAG_HEAD + ":confirm";

    public static void showConfirmDialog(FragmentManager fragmentManager, final String message, final IDialogResultListener<Integer> listener
            , boolean cancelable, CommonDialogFragment.OnDialogCancelListener cancelListener) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, CONFIRM_THEME);
            builder.setMessage(message);
            builder.setPositiveButton(DIALOG_POSITIVE, (dialog, which) -> {
                if (listener != null) {
                    listener.onDataResult(which);
                }
            });
            builder.setNegativeButton(DIALOG_NEGATIVE, (dialog, which) -> {
                if (listener != null) {
                    listener.onDataResult(which);
                }
            });
            return builder.create();
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, CONfIRM_TAG);

    }


    /**
     * 评价 dialog
     */
    private static final int EVALUTATE_THEME = R.style.Base_AlertDialog;
    public static final String EVALUTATE_TAG = TAG_HEAD + ":evaluate";


    public static CommonDialogFragment showEvalutateDialog(View view
            , boolean cancelable, CommonDialogFragment.OnDialogCancelListener cancelListener) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(FragmentActivity context) {
                    Dialog dialog = new Dialog(context, EVALUTATE_THEME);
                try {
                    dialog.setContentView(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return dialog;
            }
        }, cancelable, cancelListener);

        return dialogFragment;
    }


    /**
     * 二维码框
     */
    private static final int IMG_THEME = R.style.Base_AlertDialog;
    private static final String IMG_TAG = TAG_HEAD + ":code_img";


    public static void showImGDialog(FragmentManager fragmentManager, View view
            , boolean cancelable, CommonDialogFragment.OnDialogCancelListener cancelListener) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            Dialog dialog = new Dialog(context, IMG_THEME);
//            ButterKnife.bind(context, view);
            dialog.setContentView(view);

            return dialog;
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, IMG_TAG);

    }

    /**
     * 带列表的弹出窗
     */
    private static final int LIST_THEME = R.style.Base_AlertDialog;
    private static final String LIST_TAG = TAG_HEAD + ":list";

    public static DialogFragment showListDialog(FragmentManager fragmentManager, final String title, final String[] items
            , final IDialogResultListener<String> resultListener, boolean cancelable) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, LIST_THEME);
            builder.setTitle(title);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (resultListener != null) {
                        resultListener.onDataResult(items[which]);
                    }
                }
            });
            return builder.create();
        }, cancelable, null);
        dialogFragment.show(fragmentManager, LIST_TAG);
        return null;
    }

    /**
     * 选择日期
     */
    private static final int DATE_THEME = R.style.Base_AlertDialog;
    private static final String DATE_TAG = TAG_HEAD + ":date";

    public static DialogFragment showDateDialog(FragmentManager fragmentManager, final String title, final Calendar calendar
            , final IDialogResultListener<Calendar> resultListener, final boolean cancelable) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            final DatePickerDialog datePickerDialog = new DatePickerDialog(context, DATE_THEME, (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                resultListener.onDataResult(calendar);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.setTitle(title);
            datePickerDialog.setOnShowListener(dialog -> {
                datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(DIALOG_POSITIVE);
                datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(DIALOG_NEGATIVE);
            });
            return datePickerDialog;

        }, cancelable, null);
        dialogFragment.show(fragmentManager, DATE_TAG);
        return null;
    }


    /**
     * 选择时间
     */
    private static final int TIME_THEME = R.style.Base_AlertDialog;
    private static final String TIME_TAG = TAG_HEAD + ":time";

    public static void showTimeDialog(FragmentManager manager, final String title, final Calendar calendar, final IDialogResultListener<Calendar> resultListener, final boolean cancelable) {
        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            final TimePickerDialog dateDialog = new TimePickerDialog(context, TIME_THEME, (view, hourOfDay, minute) -> {
                if (resultListener != null) {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    resultListener.onDataResult(calendar);
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

            dateDialog.setTitle(title);
            dateDialog.setOnShowListener(dialog -> {
                dateDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(DIALOG_POSITIVE);
                dateDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(DIALOG_NEGATIVE);
            });

            return dateDialog;
        }, cancelable, null);
        dialogFragment.show(manager, DATE_TAG);
    }

    /**
     * 带输入框的弹出窗
     */
    private static final int INSERT_THEME = R.style.Base_AlertDialog;
    private static final String INSERT_TAG = TAG_HEAD + ":insert";

    public static void showInsertDialog(FragmentManager manager, final String title, final IDialogResultListener<String> resultListener, final boolean cancelable) {

        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            final EditText editText = new EditText(context);
            editText.setBackground(null);
            editText.setPadding(60, 40, 0, 0);
            AlertDialog.Builder builder = new AlertDialog.Builder(context, INSERT_THEME);
            builder.setTitle(title);
            builder.setView(editText);
            builder.setPositiveButton(DIALOG_POSITIVE, (dialog, which) -> {
                if (resultListener != null) {
                    resultListener.onDataResult(editText.getText().toString());
                }
            });
            builder.setNegativeButton(DIALOG_NEGATIVE, null);
            return builder.create();

        }, cancelable, null);
        dialogFragment.show(manager, INSERT_TAG);

    }


}
