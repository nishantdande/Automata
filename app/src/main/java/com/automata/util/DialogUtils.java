package com.automata.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.automata.Automata;

/**
 * Created by nishant on 11/04/16.
 */
public class DialogUtils {

    public static final void showMessage(String message){
        Toast.makeText(Automata.getInstance().getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * This method returns AlertDialog with a neutral button
     * */
    public static AlertDialog createAlertDialogWithNeutralButton(Context context, String titleText, String messageText,
                                                                 String neutralButtonText, DialogInterface.OnClickListener neutralButtonClickListner){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set dialog message
        alertDialogBuilder
                .setTitle(titleText)
                .setMessage(messageText)
                .setCancelable(true)
                .setNeutralButton(neutralButtonText, neutralButtonClickListner);

        return  alertDialogBuilder.create();
    }

    /**
     * This method returns AlertDialog with a Positive and Negative buttons
     *
     */
    public static AlertDialog createAlertDialogWithButtons(Context context, String titleText, String messageText,
                                                           String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener positiveButtonClickListner,
                                                           DialogInterface.OnClickListener negativeButtonClickListner){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set dialog message
        alertDialogBuilder
                .setTitle(titleText)
                .setMessage(messageText)
                .setCancelable(true)
                .setPositiveButton(positiveButtonText, positiveButtonClickListner)
                .setNegativeButton(negativeButtonText, negativeButtonClickListner);

        return  alertDialogBuilder.create();
    }
}
