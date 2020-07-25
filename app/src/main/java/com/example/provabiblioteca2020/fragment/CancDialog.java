package com.example.provabiblioteca2020.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CancDialog extends DialogFragment {

    String mTitle, mMessage;
    int mId;

    public CancDialog(String aTitle, String aMessage, int aId)
    {
        mTitle = aTitle;
        mMessage = aMessage;
        mId = aId;
    }

    public interface ICancDialog
    {
        void onResponse(boolean aResponse, int aId);
    }

    ICancDialog mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
        vBuilder.setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onResponse(true, mId);
                        dismiss();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onResponse(false, mId);
                        dismiss();
                    }
                });
        return vBuilder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ICancDialog)
        {
            mListener = (ICancDialog) activity;
        }
    }
}
