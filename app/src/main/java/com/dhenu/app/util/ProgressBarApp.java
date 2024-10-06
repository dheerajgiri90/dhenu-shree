package com.dhenu.app.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.dhenu.app.R;


public class ProgressBarApp extends Dialog {

    public ProgressBarApp(Context context) {
        super(context);
    }

    private ProgressBarApp(Context context, int theme) {
        super(context, theme);
    }

    public static ProgressDialog init(Context context, boolean cancelable, boolean isTransparent) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (Exception e) {

        }
        //dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_custom_progress);
        //ContentLoadingProgressBar loadingImageView = dialog.findViewById(R.id.loader_real_peg);
        return dialog;

    }

}
