package com.drp.gankdrp.ui.view;

import android.content.Context;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * @author KG on 17/8/1.
 */
public class DownloadDialog {

    private Context mContext;
    private MaterialDialog materialDialog;

    public DownloadDialog(Context context) {
        this.mContext = context;
    }

    public MaterialDialog getMaterialDialog() {
        return materialDialog;
    }

    public void show() {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog.Builder(mContext)
                    .content("正在下载...")
                    .progress(false, 100, false)
                    .cancelListener(dialog -> Toast.makeText(mContext, "已进入后台下载", Toast.LENGTH_SHORT).show())
                    .build();
        }
        if (!materialDialog.isShowing()) {
            materialDialog.show();
        }
    }

    public void setProgress(int progress) {
        materialDialog.setProgress(progress);
    }

    public boolean isShowing() {
        if (materialDialog != null) {
            return materialDialog.isShowing();
        }
        return false;
    }

    public void dismiss() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
            materialDialog = null;
        }
    }

}
