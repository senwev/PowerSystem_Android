package com.eecso.powersystem;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;

public class DialogHelper extends Dialog {
    public DialogHelper(@NonNull Context context) {
        super(context);
        init();
    }

    public DialogHelper(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected DialogHelper(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }
    void init()
    {
        addContentView(new Button(getContext()),null);
    }
}
