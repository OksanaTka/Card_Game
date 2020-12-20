package com.example.cardgame.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cardgame.R;

public class MyAlertDialog extends AppCompatDialogFragment {
    private EditText dialog_LBL_playerName1;
    private EditText dialog_LBL_playerName2;
    private DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(view)
                .setTitle("Enter Players name:")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String player_name1 = dialog_LBL_playerName1.getText().toString();
                        String player_name2 = dialog_LBL_playerName2.getText().toString();
                        listener.applyTexts(player_name1, player_name2);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.applyTexts("player 1", "player 2");
                    }
                });
        dialog_LBL_playerName1 = view.findViewById(R.id.dialog_LBL_playerName1);
        dialog_LBL_playerName2 = view.findViewById(R.id.dialog_LBL_playerName2);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogListener");
        }
    }

    public interface DialogListener {
        void applyTexts(String player_name1, String player_name2);
    }
}