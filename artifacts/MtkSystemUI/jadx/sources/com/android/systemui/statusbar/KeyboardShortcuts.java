package com.android.systemui.statusbar;

import android.R;
import android.app.AlertDialog;
import android.app.AppGlobals;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.Utils;
import com.android.systemui.recents.misc.SystemServicesProxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class KeyboardShortcuts {
    private static KeyboardShortcuts sInstance;
    private KeyCharacterMap mBackupKeyCharacterMap;
    private final Context mContext;
    private KeyCharacterMap mKeyCharacterMap;
    private Dialog mKeyboardShortcutsDialog;
    private static final String TAG = KeyboardShortcuts.class.getSimpleName();
    private static final Object sLock = new Object();
    private final SparseArray<String> mSpecialCharacterNames = new SparseArray<>();
    private final SparseArray<String> mModifierNames = new SparseArray<>();
    private final SparseArray<Drawable> mSpecialCharacterDrawables = new SparseArray<>();
    private final SparseArray<Drawable> mModifierDrawables = new SparseArray<>();
    private final int[] mModifierList = {65536, 4096, 2, 1, 4, 8};
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final DialogInterface.OnClickListener mDialogCloseListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.statusbar.KeyboardShortcuts.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int id) {
            KeyboardShortcuts.this.dismissKeyboardShortcuts();
        }
    };
    private final Comparator<KeyboardShortcutInfo> mApplicationItemsComparator = new Comparator<KeyboardShortcutInfo>() { // from class: com.android.systemui.statusbar.KeyboardShortcuts.2
        @Override // java.util.Comparator
        public int compare(KeyboardShortcutInfo ksh1, KeyboardShortcutInfo ksh2) {
            boolean ksh1ShouldBeLast = ksh1.getLabel() == null || ksh1.getLabel().toString().isEmpty();
            boolean ksh2ShouldBeLast = ksh2.getLabel() == null || ksh2.getLabel().toString().isEmpty();
            if (ksh1ShouldBeLast && ksh2ShouldBeLast) {
                return 0;
            }
            if (ksh1ShouldBeLast) {
                return 1;
            }
            if (ksh2ShouldBeLast) {
                return -1;
            }
            return ksh1.getLabel().toString().compareToIgnoreCase(ksh2.getLabel().toString());
        }
    };
    private final IPackageManager mPackageManager = AppGlobals.getPackageManager();

    private KeyboardShortcuts(Context context) {
        this.mContext = new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.Light);
        loadResources(context);
    }

    private static KeyboardShortcuts getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KeyboardShortcuts(context);
        }
        return sInstance;
    }

    public static void show(Context context, int deviceId) {
        MetricsLogger.visible(context, 500);
        synchronized (sLock) {
            if (sInstance != null && !sInstance.mContext.equals(context)) {
                dismiss();
            }
            getInstance(context).showKeyboardShortcuts(deviceId);
        }
    }

    public static void toggle(Context context, int deviceId) {
        synchronized (sLock) {
            if (isShowing()) {
                dismiss();
            } else {
                show(context, deviceId);
            }
        }
    }

    public static void dismiss() {
        synchronized (sLock) {
            if (sInstance != null) {
                MetricsLogger.hidden(sInstance.mContext, 500);
                sInstance.dismissKeyboardShortcuts();
                sInstance = null;
            }
        }
    }

    private static boolean isShowing() {
        return (sInstance == null || sInstance.mKeyboardShortcutsDialog == null || !sInstance.mKeyboardShortcutsDialog.isShowing()) ? false : true;
    }

    private void loadResources(Context context) {
        this.mSpecialCharacterNames.put(3, context.getString(com.android.systemui.R.string.keyboard_key_home));
        this.mSpecialCharacterNames.put(4, context.getString(com.android.systemui.R.string.keyboard_key_back));
        this.mSpecialCharacterNames.put(19, context.getString(com.android.systemui.R.string.keyboard_key_dpad_up));
        this.mSpecialCharacterNames.put(20, context.getString(com.android.systemui.R.string.keyboard_key_dpad_down));
        this.mSpecialCharacterNames.put(21, context.getString(com.android.systemui.R.string.keyboard_key_dpad_left));
        this.mSpecialCharacterNames.put(22, context.getString(com.android.systemui.R.string.keyboard_key_dpad_right));
        this.mSpecialCharacterNames.put(23, context.getString(com.android.systemui.R.string.keyboard_key_dpad_center));
        this.mSpecialCharacterNames.put(56, ".");
        this.mSpecialCharacterNames.put(61, context.getString(com.android.systemui.R.string.keyboard_key_tab));
        this.mSpecialCharacterNames.put(62, context.getString(com.android.systemui.R.string.keyboard_key_space));
        this.mSpecialCharacterNames.put(66, context.getString(com.android.systemui.R.string.keyboard_key_enter));
        this.mSpecialCharacterNames.put(67, context.getString(com.android.systemui.R.string.keyboard_key_backspace));
        this.mSpecialCharacterNames.put(85, context.getString(com.android.systemui.R.string.keyboard_key_media_play_pause));
        this.mSpecialCharacterNames.put(86, context.getString(com.android.systemui.R.string.keyboard_key_media_stop));
        this.mSpecialCharacterNames.put(87, context.getString(com.android.systemui.R.string.keyboard_key_media_next));
        this.mSpecialCharacterNames.put(88, context.getString(com.android.systemui.R.string.keyboard_key_media_previous));
        this.mSpecialCharacterNames.put(89, context.getString(com.android.systemui.R.string.keyboard_key_media_rewind));
        this.mSpecialCharacterNames.put(90, context.getString(com.android.systemui.R.string.keyboard_key_media_fast_forward));
        this.mSpecialCharacterNames.put(92, context.getString(com.android.systemui.R.string.keyboard_key_page_up));
        this.mSpecialCharacterNames.put(93, context.getString(com.android.systemui.R.string.keyboard_key_page_down));
        this.mSpecialCharacterNames.put(96, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "A"));
        this.mSpecialCharacterNames.put(97, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "B"));
        this.mSpecialCharacterNames.put(98, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "C"));
        this.mSpecialCharacterNames.put(99, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "X"));
        this.mSpecialCharacterNames.put(100, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Y"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Z"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "L1"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "R1"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_textColorAlertDialogListItem, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "L2"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_textColorSearchUrl, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "R2"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_tooltipForegroundColor, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Start"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_tooltipFrameBackground, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Select"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_viewInflaterClass, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Mode"));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay, context.getString(com.android.systemui.R.string.keyboard_key_forward_del));
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBar, "Esc");
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_windowNoTitle, "SysRq");
        this.mSpecialCharacterNames.put(121, "Break");
        this.mSpecialCharacterNames.put(com.android.systemui.plugins.R.styleable.AppCompatTheme_windowFixedWidthMajor, "Scroll Lock");
        this.mSpecialCharacterNames.put(122, context.getString(com.android.systemui.R.string.keyboard_key_move_home));
        this.mSpecialCharacterNames.put(123, context.getString(com.android.systemui.R.string.keyboard_key_move_end));
        this.mSpecialCharacterNames.put(124, context.getString(com.android.systemui.R.string.keyboard_key_insert));
        this.mSpecialCharacterNames.put(131, "F1");
        this.mSpecialCharacterNames.put(132, "F2");
        this.mSpecialCharacterNames.put(133, "F3");
        this.mSpecialCharacterNames.put(134, "F4");
        this.mSpecialCharacterNames.put(135, "F5");
        this.mSpecialCharacterNames.put(136, "F6");
        this.mSpecialCharacterNames.put(137, "F7");
        this.mSpecialCharacterNames.put(138, "F8");
        this.mSpecialCharacterNames.put(139, "F9");
        this.mSpecialCharacterNames.put(140, "F10");
        this.mSpecialCharacterNames.put(141, "F11");
        this.mSpecialCharacterNames.put(142, "F12");
        this.mSpecialCharacterNames.put(143, context.getString(com.android.systemui.R.string.keyboard_key_num_lock));
        this.mSpecialCharacterNames.put(144, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "0"));
        this.mSpecialCharacterNames.put(145, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "1"));
        this.mSpecialCharacterNames.put(146, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "2"));
        this.mSpecialCharacterNames.put(147, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "3"));
        this.mSpecialCharacterNames.put(148, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "4"));
        this.mSpecialCharacterNames.put(149, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "5"));
        this.mSpecialCharacterNames.put(150, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "6"));
        this.mSpecialCharacterNames.put(151, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "7"));
        this.mSpecialCharacterNames.put(152, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "8"));
        this.mSpecialCharacterNames.put(153, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "9"));
        this.mSpecialCharacterNames.put(154, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "/"));
        this.mSpecialCharacterNames.put(155, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "*"));
        this.mSpecialCharacterNames.put(156, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "-"));
        this.mSpecialCharacterNames.put(157, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "+"));
        this.mSpecialCharacterNames.put(158, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "."));
        this.mSpecialCharacterNames.put(159, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, ","));
        this.mSpecialCharacterNames.put(160, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, context.getString(com.android.systemui.R.string.keyboard_key_enter)));
        this.mSpecialCharacterNames.put(161, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "="));
        this.mSpecialCharacterNames.put(162, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "("));
        this.mSpecialCharacterNames.put(163, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, ")"));
        this.mSpecialCharacterNames.put(211, "半角/全角");
        this.mSpecialCharacterNames.put(212, "英数");
        this.mSpecialCharacterNames.put(213, "無変換");
        this.mSpecialCharacterNames.put(214, "変換");
        this.mSpecialCharacterNames.put(215, "かな");
        this.mModifierNames.put(65536, "Meta");
        this.mModifierNames.put(4096, "Ctrl");
        this.mModifierNames.put(2, "Alt");
        this.mModifierNames.put(1, "Shift");
        this.mModifierNames.put(4, "Sym");
        this.mModifierNames.put(8, "Fn");
        this.mSpecialCharacterDrawables.put(67, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_backspace));
        this.mSpecialCharacterDrawables.put(66, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_enter));
        this.mSpecialCharacterDrawables.put(19, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_up));
        this.mSpecialCharacterDrawables.put(22, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_right));
        this.mSpecialCharacterDrawables.put(20, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_down));
        this.mSpecialCharacterDrawables.put(21, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_left));
        this.mModifierDrawables.put(65536, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_meta));
    }

    private void retrieveKeyCharacterMap(int deviceId) {
        InputDevice inputDevice;
        InputManager inputManager = InputManager.getInstance();
        this.mBackupKeyCharacterMap = inputManager.getInputDevice(-1).getKeyCharacterMap();
        if (deviceId != -1 && (inputDevice = inputManager.getInputDevice(deviceId)) != null) {
            this.mKeyCharacterMap = inputDevice.getKeyCharacterMap();
            return;
        }
        int[] deviceIds = inputManager.getInputDeviceIds();
        for (int i : deviceIds) {
            InputDevice inputDevice2 = inputManager.getInputDevice(i);
            if (inputDevice2.getId() != -1 && inputDevice2.isFullKeyboard()) {
                this.mKeyCharacterMap = inputDevice2.getKeyCharacterMap();
                return;
            }
        }
        this.mKeyCharacterMap = this.mBackupKeyCharacterMap;
    }

    private void showKeyboardShortcuts(int deviceId) {
        retrieveKeyCharacterMap(deviceId);
        SystemServicesProxy.getInstance(this.mContext).requestKeyboardShortcuts(this.mContext, new WindowManager.KeyboardShortcutsReceiver() { // from class: com.android.systemui.statusbar.KeyboardShortcuts.3
            public void onKeyboardShortcutsReceived(List<KeyboardShortcutGroup> result) {
                result.add(KeyboardShortcuts.this.getSystemShortcuts());
                KeyboardShortcutGroup appShortcuts = KeyboardShortcuts.this.getDefaultApplicationShortcuts();
                if (appShortcuts != null) {
                    result.add(appShortcuts);
                }
                KeyboardShortcuts.this.showKeyboardShortcutsDialog(result);
            }
        }, deviceId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissKeyboardShortcuts() {
        if (this.mKeyboardShortcutsDialog != null) {
            this.mKeyboardShortcutsDialog.dismiss();
            this.mKeyboardShortcutsDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public KeyboardShortcutGroup getSystemShortcuts() {
        KeyboardShortcutGroup systemGroup = new KeyboardShortcutGroup((CharSequence) this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_system), true);
        systemGroup.addItem(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_system_home), 66, 65536));
        systemGroup.addItem(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_system_back), 67, 65536));
        systemGroup.addItem(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_system_recents), 61, 2));
        systemGroup.addItem(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_system_notifications), 42, 65536));
        systemGroup.addItem(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_system_shortcuts_helper), 76, 65536));
        systemGroup.addItem(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_system_switch_input), 62, 65536));
        return systemGroup;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public KeyboardShortcutGroup getDefaultApplicationShortcuts() {
        int userId = this.mContext.getUserId();
        List<KeyboardShortcutInfo> keyboardShortcutInfoAppItems = new ArrayList<>();
        AssistUtils assistUtils = new AssistUtils(this.mContext);
        ComponentName assistComponent = assistUtils.getAssistComponentForUser(userId);
        if (assistComponent != null) {
            PackageInfo assistPackageInfo = null;
            try {
                assistPackageInfo = this.mPackageManager.getPackageInfo(assistComponent.getPackageName(), 0, userId);
            } catch (RemoteException e) {
                Log.e(TAG, "PackageManagerService is dead");
            }
            if (assistPackageInfo != null) {
                Icon assistIcon = Icon.createWithResource(assistPackageInfo.applicationInfo.packageName, assistPackageInfo.applicationInfo.icon);
                keyboardShortcutInfoAppItems.add(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_applications_assist), assistIcon, 0, 65536));
            }
        }
        Icon browserIcon = getIconForIntentCategory("android.intent.category.APP_BROWSER", userId);
        if (browserIcon != null) {
            keyboardShortcutInfoAppItems.add(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_applications_browser), browserIcon, 30, 65536));
        }
        Icon contactsIcon = getIconForIntentCategory("android.intent.category.APP_CONTACTS", userId);
        if (contactsIcon != null) {
            keyboardShortcutInfoAppItems.add(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_applications_contacts), contactsIcon, 31, 65536));
        }
        Icon emailIcon = getIconForIntentCategory("android.intent.category.APP_EMAIL", userId);
        if (emailIcon != null) {
            keyboardShortcutInfoAppItems.add(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_applications_email), emailIcon, 33, 65536));
        }
        Icon messagingIcon = getIconForIntentCategory("android.intent.category.APP_MESSAGING", userId);
        if (messagingIcon != null) {
            keyboardShortcutInfoAppItems.add(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_applications_sms), messagingIcon, 47, 65536));
        }
        Icon musicIcon = getIconForIntentCategory("android.intent.category.APP_MUSIC", userId);
        if (musicIcon != null) {
            keyboardShortcutInfoAppItems.add(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_applications_music), musicIcon, 44, 65536));
        }
        Icon calendarIcon = getIconForIntentCategory("android.intent.category.APP_CALENDAR", userId);
        if (calendarIcon != null) {
            keyboardShortcutInfoAppItems.add(new KeyboardShortcutInfo(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_applications_calendar), calendarIcon, 40, 65536));
        }
        int itemsSize = keyboardShortcutInfoAppItems.size();
        if (itemsSize == 0) {
            return null;
        }
        Collections.sort(keyboardShortcutInfoAppItems, this.mApplicationItemsComparator);
        return new KeyboardShortcutGroup(this.mContext.getString(com.android.systemui.R.string.keyboard_shortcut_group_applications), keyboardShortcutInfoAppItems, true);
    }

    private Icon getIconForIntentCategory(String intentCategory, int userId) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory(intentCategory);
        PackageInfo packageInfo = getPackageInfoForIntent(intent, userId);
        if (packageInfo != null && packageInfo.applicationInfo.icon != 0) {
            return Icon.createWithResource(packageInfo.applicationInfo.packageName, packageInfo.applicationInfo.icon);
        }
        return null;
    }

    private PackageInfo getPackageInfoForIntent(Intent intent, int userId) {
        try {
            ResolveInfo handler = this.mPackageManager.resolveIntent(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 0, userId);
            if (handler != null && handler.activityInfo != null) {
                return this.mPackageManager.getPackageInfo(handler.activityInfo.packageName, 0, userId);
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "PackageManagerService is dead", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKeyboardShortcutsDialog(final List<KeyboardShortcutGroup> keyboardShortcutGroups) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyboardShortcuts.4
            @Override // java.lang.Runnable
            public void run() {
                KeyboardShortcuts.this.handleShowKeyboardShortcuts(keyboardShortcutGroups);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShowKeyboardShortcuts(List<KeyboardShortcutGroup> keyboardShortcutGroups) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.mContext);
        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        View keyboardShortcutsView = inflater.inflate(com.android.systemui.R.layout.keyboard_shortcuts_view, (ViewGroup) null);
        populateKeyboardShortcuts((LinearLayout) keyboardShortcutsView.findViewById(com.android.systemui.R.id.keyboard_shortcuts_container), keyboardShortcutGroups);
        dialogBuilder.setView(keyboardShortcutsView);
        dialogBuilder.setPositiveButton(com.android.systemui.R.string.quick_settings_done, this.mDialogCloseListener);
        this.mKeyboardShortcutsDialog = dialogBuilder.create();
        this.mKeyboardShortcutsDialog.setCanceledOnTouchOutside(true);
        Window keyboardShortcutsWindow = this.mKeyboardShortcutsDialog.getWindow();
        keyboardShortcutsWindow.setType(2008);
        synchronized (sLock) {
            if (sInstance != null) {
                this.mKeyboardShortcutsDialog.show();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1, types: [int] */
    /* JADX WARN: Type inference failed for: r15v3 */
    private void populateKeyboardShortcuts(LinearLayout linearLayout, List<KeyboardShortcutGroup> list) {
        int color;
        boolean z;
        int i;
        int i2;
        int i3;
        TextView textView;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        TextView textView2;
        int i9;
        LinearLayout linearLayout2 = linearLayout;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        int size = list.size();
        int i10 = com.android.systemui.R.layout.keyboard_shortcuts_key_view;
        boolean z2 = false;
        TextView textView3 = (TextView) layoutInflaterFrom.inflate(com.android.systemui.R.layout.keyboard_shortcuts_key_view, (ViewGroup) null, false);
        textView3.measure(0, 0);
        int measuredHeight = textView3.getMeasuredHeight();
        int measuredHeight2 = (textView3.getMeasuredHeight() - textView3.getPaddingTop()) - textView3.getPaddingBottom();
        int i11 = 0;
        while (i11 < size) {
            KeyboardShortcutGroup keyboardShortcutGroup = list.get(i11);
            TextView textView4 = (TextView) layoutInflaterFrom.inflate(com.android.systemui.R.layout.keyboard_shortcuts_category_title, linearLayout2, z2);
            textView4.setText(keyboardShortcutGroup.getLabel());
            if (keyboardShortcutGroup.isSystemGroup()) {
                color = Utils.getColorAccent(this.mContext);
            } else {
                color = this.mContext.getColor(com.android.systemui.R.color.ksh_application_group_color);
            }
            textView4.setTextColor(color);
            linearLayout2.addView(textView4);
            LinearLayout linearLayout3 = (LinearLayout) layoutInflaterFrom.inflate(com.android.systemui.R.layout.keyboard_shortcuts_container, linearLayout2, z2);
            int size2 = keyboardShortcutGroup.getItems().size();
            for (?? r15 = z2; r15 < size2; r15++) {
                KeyboardShortcutInfo keyboardShortcutInfo = keyboardShortcutGroup.getItems().get(r15);
                List<StringDrawableContainer> humanReadableShortcutKeys = getHumanReadableShortcutKeys(keyboardShortcutInfo);
                if (humanReadableShortcutKeys == null) {
                    Log.w(TAG, "Keyboard Shortcut contains unsupported keys, skipping.");
                    i = size;
                    i2 = measuredHeight2;
                    i3 = i11;
                    keyboardShortcutGroup = keyboardShortcutGroup;
                    textView = textView4;
                    i4 = size2;
                    i5 = com.android.systemui.R.layout.keyboard_shortcuts_key_view;
                } else {
                    View viewInflate = layoutInflaterFrom.inflate(com.android.systemui.R.layout.keyboard_shortcut_app_item, (ViewGroup) linearLayout3, false);
                    if (keyboardShortcutInfo.getIcon() != null) {
                        ImageView imageView = (ImageView) viewInflate.findViewById(com.android.systemui.R.id.keyboard_shortcuts_icon);
                        imageView.setImageIcon(keyboardShortcutInfo.getIcon());
                        imageView.setVisibility(0);
                    }
                    TextView textView5 = (TextView) viewInflate.findViewById(com.android.systemui.R.id.keyboard_shortcuts_keyword);
                    textView5.setText(keyboardShortcutInfo.getLabel());
                    if (keyboardShortcutInfo.getIcon() != null) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView5.getLayoutParams();
                        layoutParams.removeRule(20);
                        textView5.setLayoutParams(layoutParams);
                    }
                    ViewGroup viewGroup = (ViewGroup) viewInflate.findViewById(com.android.systemui.R.id.keyboard_shortcuts_item_container);
                    int size3 = humanReadableShortcutKeys.size();
                    int i12 = 0;
                    while (true) {
                        int i13 = i12;
                        TextView textView6 = textView5;
                        if (i13 >= size3) {
                            break;
                        }
                        List<StringDrawableContainer> list2 = humanReadableShortcutKeys;
                        StringDrawableContainer stringDrawableContainer = humanReadableShortcutKeys.get(i13);
                        int i14 = size3;
                        if (stringDrawableContainer.mDrawable != null) {
                            textView2 = textView4;
                            ImageView imageView2 = (ImageView) layoutInflaterFrom.inflate(com.android.systemui.R.layout.keyboard_shortcuts_key_icon_view, viewGroup, false);
                            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(measuredHeight2, measuredHeight2, Bitmap.Config.ARGB_8888);
                            i7 = measuredHeight2;
                            Canvas canvas = new Canvas(bitmapCreateBitmap);
                            i9 = size2;
                            i8 = i11;
                            i6 = size;
                            stringDrawableContainer.mDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            stringDrawableContainer.mDrawable.draw(canvas);
                            imageView2.setImageBitmap(bitmapCreateBitmap);
                            imageView2.setImportantForAccessibility(1);
                            imageView2.setAccessibilityDelegate(new ShortcutKeyAccessibilityDelegate(stringDrawableContainer.mString));
                            viewGroup.addView(imageView2);
                        } else {
                            i6 = size;
                            i7 = measuredHeight2;
                            i8 = i11;
                            textView2 = textView4;
                            i9 = size2;
                            if (stringDrawableContainer.mString != null) {
                                TextView textView7 = (TextView) layoutInflaterFrom.inflate(com.android.systemui.R.layout.keyboard_shortcuts_key_view, viewGroup, false);
                                textView7.setMinimumWidth(measuredHeight);
                                textView7.setText(stringDrawableContainer.mString);
                                textView7.setAccessibilityDelegate(new ShortcutKeyAccessibilityDelegate(stringDrawableContainer.mString));
                                viewGroup.addView(textView7);
                            }
                            i12 = i13 + 1;
                            textView5 = textView6;
                            humanReadableShortcutKeys = list2;
                            size3 = i14;
                            textView4 = textView2;
                            measuredHeight2 = i7;
                            size2 = i9;
                            i11 = i8;
                            size = i6;
                        }
                        i12 = i13 + 1;
                        textView5 = textView6;
                        humanReadableShortcutKeys = list2;
                        size3 = i14;
                        textView4 = textView2;
                        measuredHeight2 = i7;
                        size2 = i9;
                        i11 = i8;
                        size = i6;
                    }
                    i = size;
                    i2 = measuredHeight2;
                    i3 = i11;
                    textView = textView4;
                    i4 = size2;
                    i5 = com.android.systemui.R.layout.keyboard_shortcuts_key_view;
                    linearLayout3.addView(viewInflate);
                }
                i10 = i5;
                textView3 = textView3;
                keyboardShortcutGroup = keyboardShortcutGroup;
                textView4 = textView;
                measuredHeight2 = i2;
                size2 = i4;
                i11 = i3;
                size = i;
            }
            int i15 = size;
            int i16 = i10;
            TextView textView8 = textView3;
            int i17 = measuredHeight2;
            int i18 = i11;
            linearLayout2 = linearLayout;
            linearLayout2.addView(linearLayout3);
            if (i18 >= i15 - 1) {
                z = false;
            } else {
                z = false;
                linearLayout2.addView(layoutInflaterFrom.inflate(com.android.systemui.R.layout.keyboard_shortcuts_category_separator, (ViewGroup) linearLayout2, false));
            }
            i11 = i18 + 1;
            i10 = i16;
            z2 = z;
            textView3 = textView8;
            measuredHeight2 = i17;
            size = i15;
        }
    }

    private List<StringDrawableContainer> getHumanReadableShortcutKeys(KeyboardShortcutInfo info) {
        String shortcutKeyString;
        List<StringDrawableContainer> shortcutKeys = getHumanReadableModifiers(info);
        if (shortcutKeys == null) {
            return null;
        }
        Drawable shortcutKeyDrawable = null;
        if (info.getBaseCharacter() > 0) {
            shortcutKeyString = String.valueOf(info.getBaseCharacter());
        } else if (this.mSpecialCharacterDrawables.get(info.getKeycode()) != null) {
            shortcutKeyDrawable = this.mSpecialCharacterDrawables.get(info.getKeycode());
            shortcutKeyString = this.mSpecialCharacterNames.get(info.getKeycode());
        } else if (this.mSpecialCharacterNames.get(info.getKeycode()) != null) {
            shortcutKeyString = this.mSpecialCharacterNames.get(info.getKeycode());
        } else {
            if (info.getKeycode() == 0) {
                return shortcutKeys;
            }
            char displayLabel = this.mKeyCharacterMap.getDisplayLabel(info.getKeycode());
            if (displayLabel != 0) {
                shortcutKeyString = String.valueOf(displayLabel);
            } else {
                char displayLabel2 = this.mBackupKeyCharacterMap.getDisplayLabel(info.getKeycode());
                if (displayLabel2 == 0) {
                    return null;
                }
                shortcutKeyString = String.valueOf(displayLabel2);
            }
        }
        if (shortcutKeyString != null) {
            shortcutKeys.add(new StringDrawableContainer(shortcutKeyString, shortcutKeyDrawable));
        } else {
            Log.w(TAG, "Keyboard Shortcut does not have a text representation, skipping.");
        }
        return shortcutKeys;
    }

    private List<StringDrawableContainer> getHumanReadableModifiers(KeyboardShortcutInfo info) {
        List<StringDrawableContainer> shortcutKeys = new ArrayList<>();
        int modifiers = info.getModifiers();
        if (modifiers == 0) {
            return shortcutKeys;
        }
        for (int i = 0; i < this.mModifierList.length; i++) {
            int supportedModifier = this.mModifierList[i];
            if ((modifiers & supportedModifier) != 0) {
                shortcutKeys.add(new StringDrawableContainer(this.mModifierNames.get(supportedModifier), this.mModifierDrawables.get(supportedModifier)));
                modifiers &= ~supportedModifier;
            }
        }
        if (modifiers != 0) {
            return null;
        }
        return shortcutKeys;
    }

    private final class ShortcutKeyAccessibilityDelegate extends View.AccessibilityDelegate {
        private String mContentDescription;

        ShortcutKeyAccessibilityDelegate(String contentDescription) {
            this.mContentDescription = contentDescription;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (this.mContentDescription != null) {
                info.setContentDescription(this.mContentDescription.toLowerCase());
            }
        }
    }

    private static final class StringDrawableContainer {
        public Drawable mDrawable;
        public String mString;

        StringDrawableContainer(String string, Drawable drawable) {
            this.mString = string;
            this.mDrawable = drawable;
        }
    }
}
