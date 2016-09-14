package com.bitspilani.socialcops;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by asifsheikh on 3/9/16.
 */
public class MainDialogFragment extends DialogFragment {


    private  MainDialogListener mainDialogListener;

    public interface MainDialogListener{
        void takePhoto();
        void takeVideo();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(
                R.array.dialogoptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0)
                    mainDialogListener.takePhoto();
                else if(which == 1)
                    mainDialogListener.takeVideo();
            }
        });

        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainDialogListener = (MainDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "must implement the MainDialogListener interface");
        }
    }

}
