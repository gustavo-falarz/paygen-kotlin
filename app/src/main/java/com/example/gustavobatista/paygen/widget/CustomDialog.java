package com.example.gustavobatista.paygen.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.gustavobatista.paygen.R;


public class CustomDialog extends DialogFragment {

    private String message;
    private String title;
    private String negative;
    private String positive;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        if (positive == null) {
            builder.setPositiveButton(R.string.ok, onclickDismiss());
        } else {
            builder.setPositiveButton(R.string.ok, onclickPositive());
        }

        return builder.create();
    }

    private DialogInterface.OnClickListener onclickDismiss() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        };
    }

    private DialogInterface.OnClickListener onclickPositive() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        };
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

}
