package com.wordpress.trusted827.mira.components;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.wordpress.trusted827.mira.R;

public class CustomDialog
        extends AlertDialog
{
    private CallBack mCallBack;
    private View mEmptyView;
    private EditText mEtPhone;
    private Button mNegativeButton;
    private Button mPositiveButton;

    private CustomDialog(@NonNull Context paramContext)
    {
        super(paramContext, R.style.CustomDialog);
        setCanceledOnTouchOutside(false);
    }

    private void initComponent()
    {
        this.mEmptyView = findViewById(R.id.vEmpty);
        this.mPositiveButton = ((Button)findViewById(R.id.btnPositive));
        this.mNegativeButton = ((Button)findViewById(R.id.btnNegative));
        this.mEtPhone = ((EditText)findViewById(R.id.etDialogPhone));
    }

    private void setAllListener()
    {
        this.mPositiveButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                String phone = mEtPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    mEtPhone.setError("Invalid phone number");
                } else {
                    dismiss();
                    if (mCallBack != null) {
                        mCallBack.onPositiveClick(phone);
                    }
                }
            }
        });
        this.mNegativeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                dismiss();
                if (mCallBack != null) {
                    mCallBack.onNegativeClick();
                }
            }
        });
        this.mEmptyView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                dismiss();
            }
        });
    }

    private void setCallBack(CallBack paramCallBack)
    {
        this.mCallBack = paramCallBack;
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.custom_dialog);
        initComponent();
        setAllListener();
    }

    public static class Builder
    {
        private final CustomDialog mCustomDialog;

        public Builder(@NonNull Context paramContext)
        {
            this.mCustomDialog = new CustomDialog(paramContext);
        }

        public Builder setCallBack(CustomDialog.CallBack paramCallBack)
        {
            this.mCustomDialog.setCallBack(paramCallBack);
            return this;
        }

        public CustomDialog show()
        {
            this.mCustomDialog.show();
            this.mCustomDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            this.mCustomDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            return this.mCustomDialog;
        }
    }

    public static abstract interface CallBack
    {
        public abstract void onNegativeClick();

        public abstract void onPositiveClick(String paramString);
    }
}
