package com.example.androidplay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class Fragment1 extends android.app.DialogFragment {

    static Fragment1 newInstance(String title,String message, String from) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("from", from);
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String from = getArguments().getString("from");
        String message = getArguments().getString("message");

        if (from.equals("student_info")) {
            return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("OK", null).create();

        }
        else if (from.equals("list_view")) {
            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(title)
                    .setMessage(message)
                    .setNeutralButton("OK", null).create();
        }
        else {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_list, null);

            return new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText valueView = (EditText) dialogView.findViewById(R.id.list_view_item);
                        String valueText=valueView.getText().toString();
                        ((ListViewActivity)getActivity()).doPositiveClick(valueText);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ((ListViewActivity)getActivity()).doNegativeClick();
                    }
                }).create();
        }
    }
}
