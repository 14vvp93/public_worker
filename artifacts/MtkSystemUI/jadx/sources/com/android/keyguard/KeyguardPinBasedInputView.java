package com.android.keyguard;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.annotations.VisibleForTesting;

/* JADX INFO: loaded from: classes.dex */
public abstract class KeyguardPinBasedInputView extends KeyguardAbsKeyInputView implements View.OnKeyListener, View.OnTouchListener {
    private static final boolean DEBUG = KeyguardConstants.DEBUG;
    private View mButton0;
    private View mButton1;
    private View mButton2;
    private View mButton3;
    private View mButton4;
    private View mButton5;
    private View mButton6;
    private View mButton7;
    private View mButton8;
    private View mButton9;
    private View mDeleteButton;
    private View mOkButton;

    @VisibleForTesting
    public PasswordTextView mPasswordEntry;

    public KeyguardPinBasedInputView(Context context) {
        this(context, null);
    }

    public KeyguardPinBasedInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return this.mPasswordEntry.requestFocus(direction, previouslyFocusedRect);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected void resetState() {
        setPasswordEntryEnabled(true);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected void setPasswordEntryEnabled(boolean enabled) {
        this.mPasswordEntry.setEnabled(enabled);
        this.mOkButton.setEnabled(enabled);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected void setPasswordEntryInputEnabled(boolean enabled) {
        this.mPasswordEntry.setEnabled(enabled);
        this.mOkButton.setEnabled(enabled);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.isConfirmKey(keyCode)) {
            performClick(this.mOkButton);
            return true;
        }
        if (keyCode == 67) {
            performClick(this.mDeleteButton);
            return true;
        }
        if (keyCode >= 7 && keyCode <= 16) {
            int number = keyCode - 7;
            performNumberClick(number);
            return true;
        }
        if (keyCode >= 144 && keyCode <= 153) {
            int number2 = keyCode - 144;
            performNumberClick(number2);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected int getPromptReasonStringRes(int reason) {
        switch (reason) {
            case 0:
                return 0;
            case 1:
                return com.android.systemui.R.string.kg_prompt_reason_restart_pin;
            case 2:
                return com.android.systemui.R.string.kg_prompt_reason_timeout_pin;
            case 3:
                return com.android.systemui.R.string.kg_prompt_reason_device_admin;
            case 4:
                return com.android.systemui.R.string.kg_prompt_reason_user_request;
            default:
                return com.android.systemui.R.string.kg_prompt_reason_timeout_pin;
        }
    }

    private void performClick(View view) {
        view.performClick();
    }

    private void performNumberClick(int number) {
        switch (number) {
            case 0:
                performClick(this.mButton0);
                break;
            case 1:
                performClick(this.mButton1);
                break;
            case 2:
                performClick(this.mButton2);
                break;
            case 3:
                performClick(this.mButton3);
                break;
            case 4:
                performClick(this.mButton4);
                break;
            case 5:
                performClick(this.mButton5);
                break;
            case 6:
                performClick(this.mButton6);
                break;
            case 7:
                performClick(this.mButton7);
                break;
            case 8:
                performClick(this.mButton8);
                break;
            case 9:
                performClick(this.mButton9);
                break;
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected void resetPasswordText(boolean animate, boolean announce) {
        this.mPasswordEntry.reset(animate, announce);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected String getPasswordText() {
        return this.mPasswordEntry.getText();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    protected void onFinishInflate() {
        this.mPasswordEntry = (PasswordTextView) findViewById(getPasswordTextViewId());
        this.mPasswordEntry.setOnKeyListener(this);
        this.mPasswordEntry.setSelected(true);
        this.mPasswordEntry.setUserActivityListener(new PasswordTextView.UserActivityListener() { // from class: com.android.keyguard.KeyguardPinBasedInputView.1
            @Override // com.android.keyguard.PasswordTextView.UserActivityListener
            public void onUserActivity() {
                KeyguardPinBasedInputView.this.onUserInput();
            }
        });
        this.mOkButton = findViewById(com.android.systemui.R.id.key_enter);
        if (this.mOkButton != null) {
            this.mOkButton.setOnTouchListener(this);
            this.mOkButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPinBasedInputView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    if (KeyguardPinBasedInputView.this.mPasswordEntry.isEnabled()) {
                        KeyguardPinBasedInputView.this.verifyPasswordAndUnlock();
                    }
                }
            });
            this.mOkButton.setOnHoverListener(new LiftToActivateListener(getContext()));
        }
        this.mDeleteButton = findViewById(com.android.systemui.R.id.delete_button);
        this.mDeleteButton.setVisibility(0);
        this.mDeleteButton.setOnTouchListener(this);
        this.mDeleteButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPinBasedInputView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (KeyguardPinBasedInputView.this.mPasswordEntry.isEnabled()) {
                    KeyguardPinBasedInputView.this.mPasswordEntry.deleteLastChar();
                }
            }
        });
        this.mDeleteButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.keyguard.KeyguardPinBasedInputView.4
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View v) {
                if (KeyguardPinBasedInputView.this.mPasswordEntry.isEnabled()) {
                    KeyguardPinBasedInputView.this.resetPasswordText(true, true);
                }
                KeyguardPinBasedInputView.this.doHapticKeyClick();
                return true;
            }
        });
        this.mButton0 = findViewById(com.android.systemui.R.id.key0);
        this.mButton1 = findViewById(com.android.systemui.R.id.key1);
        this.mButton2 = findViewById(com.android.systemui.R.id.key2);
        this.mButton3 = findViewById(com.android.systemui.R.id.key3);
        this.mButton4 = findViewById(com.android.systemui.R.id.key4);
        this.mButton5 = findViewById(com.android.systemui.R.id.key5);
        this.mButton6 = findViewById(com.android.systemui.R.id.key6);
        this.mButton7 = findViewById(com.android.systemui.R.id.key7);
        this.mButton8 = findViewById(com.android.systemui.R.id.key8);
        this.mButton9 = findViewById(com.android.systemui.R.id.key9);
        this.mPasswordEntry.requestFocus();
        super.onFinishInflate();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public void onResume(int reason) {
        super.onResume(reason);
        this.mPasswordEntry.requestFocus();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == 0) {
            doHapticKeyClick();
            return false;
        }
        return false;
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == 0) {
            if (DEBUG) {
                Log.d("KeyguardPinBasedInputView", "keyCode: " + keyCode + " event: " + event);
            }
            return onKeyDown(keyCode, event);
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public CharSequence getTitle() {
        return getContext().getString(android.R.string.config_rawContactsLocalAccountType);
    }
}
