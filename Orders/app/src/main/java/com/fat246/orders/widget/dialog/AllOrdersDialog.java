package com.fat246.orders.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2016/3/19.
 */
public class AllOrdersDialog extends Dialog {

    public AllOrdersDialog(Context context) {
        super(context);
    }

    public AllOrdersDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AllOrdersDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
