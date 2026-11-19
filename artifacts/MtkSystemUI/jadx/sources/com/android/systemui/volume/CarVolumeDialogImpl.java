package com.android.systemui.volume;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.media.CarAudioManager;
import android.car.media.ICarVolumeCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import androidx.car.widget.ListItem;
import androidx.car.widget.ListItemAdapter;
import androidx.car.widget.ListItemProvider;
import androidx.car.widget.PagedListView;
import androidx.car.widget.SeekbarListItem;
import com.android.systemui.R;
import com.android.systemui.plugins.VolumeDialog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class CarVolumeDialogImpl implements VolumeDialog {
    private static final String TAG = Util.logTag(CarVolumeDialogImpl.class);
    private Car mCar;
    private CarAudioManager mCarAudioManager;
    private final Context mContext;
    private CustomDialog mDialog;
    private boolean mExpanded;
    private boolean mHovering;
    private final KeyguardManager mKeyguard;
    private PagedListView mListView;
    private ListItemAdapter mPagedListAdapter;
    private boolean mShowing;
    private Window mWindow;
    private final H mHandler = new H();
    private final SparseArray<VolumeItem> mVolumeItems = new SparseArray<>();
    private final List<VolumeItem> mAvailableVolumeItems = new ArrayList();
    private final List<ListItem> mVolumeLineItems = new ArrayList();
    private final ICarVolumeCallback mVolumeChangeCallback = new ICarVolumeCallback.Stub() { // from class: com.android.systemui.volume.CarVolumeDialogImpl.1
        public void onGroupVolumeChanged(int groupId, int flags) {
            VolumeItem volumeItem = (VolumeItem) CarVolumeDialogImpl.this.mAvailableVolumeItems.get(groupId);
            int value = CarVolumeDialogImpl.getSeekbarValue(CarVolumeDialogImpl.this.mCarAudioManager, groupId);
            if (value == volumeItem.progress) {
                return;
            }
            if (volumeItem.listItem != null) {
                volumeItem.listItem.setProgress(value);
            }
            volumeItem.progress = value;
            if ((flags & 1) != 0) {
                CarVolumeDialogImpl.this.show(1);
            }
        }

        public void onMasterMuteChanged(int flags) {
        }
    };
    private final ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.android.systemui.volume.CarVolumeDialogImpl.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                CarVolumeDialogImpl.this.mExpanded = false;
                CarVolumeDialogImpl.this.mCarAudioManager = (CarAudioManager) CarVolumeDialogImpl.this.mCar.getCarManager("audio");
                int volumeGroupCount = CarVolumeDialogImpl.this.mCarAudioManager.getVolumeGroupCount();
                for (int groupId = 0; groupId < volumeGroupCount; groupId++) {
                    VolumeItem volumeItem = CarVolumeDialogImpl.this.getVolumeItemForUsages(CarVolumeDialogImpl.this.mCarAudioManager.getUsagesForVolumeGroupId(groupId));
                    CarVolumeDialogImpl.this.mAvailableVolumeItems.add(volumeItem);
                    if (groupId == 0) {
                        volumeItem.defaultItem = true;
                        CarVolumeDialogImpl.this.addSeekbarListItem(volumeItem, groupId, R.drawable.car_ic_keyboard_arrow_down, new ExpandIconListener());
                    }
                }
                if (CarVolumeDialogImpl.this.mPagedListAdapter != null) {
                    CarVolumeDialogImpl.this.mPagedListAdapter.notifyDataSetChanged();
                }
                CarVolumeDialogImpl.this.mCarAudioManager.registerVolumeCallback(CarVolumeDialogImpl.this.mVolumeChangeCallback.asBinder());
            } catch (CarNotConnectedException e) {
                Log.e(CarVolumeDialogImpl.TAG, "Car is not connected!", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            CarVolumeDialogImpl.this.cleanupAudioManager();
        }
    };

    public CarVolumeDialogImpl(Context context) {
        this.mContext = new ContextThemeWrapper(context, R.style.qs_theme);
        this.mKeyguard = (KeyguardManager) this.mContext.getSystemService("keyguard");
        this.mCar = Car.createCar(this.mContext, this.mServiceConnection);
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public void init(int windowType, VolumeDialog.Callback callback) {
        initDialog();
        this.mCar.connect();
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public void destroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        cleanupAudioManager();
        this.mCar.disconnect();
    }

    private void initDialog() {
        loadAudioUsageItems();
        this.mVolumeLineItems.clear();
        this.mDialog = new CustomDialog(this.mContext);
        this.mHovering = false;
        this.mShowing = false;
        this.mExpanded = false;
        this.mWindow = this.mDialog.getWindow();
        this.mWindow.requestFeature(1);
        this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mWindow.clearFlags(65538);
        this.mWindow.addFlags(17563944);
        this.mWindow.setType(2020);
        this.mWindow.setWindowAnimations(android.R.style.Animation.Toast);
        WindowManager.LayoutParams lp = this.mWindow.getAttributes();
        lp.format = -3;
        lp.setTitle(VolumeDialogImpl.class.getSimpleName());
        lp.gravity = 49;
        lp.windowAnimations = -1;
        this.mWindow.setAttributes(lp);
        this.mWindow.setLayout(-2, -2);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.setContentView(R.layout.car_volume_dialog);
        this.mDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.-$$Lambda$CarVolumeDialogImpl$vlKmnqSHXeP1A03lvbFsgzjPtc0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                CarVolumeDialogImpl.lambda$initDialog$0(this.f$0, dialogInterface);
            }
        });
        this.mListView = (PagedListView) this.mWindow.findViewById(R.id.volume_list);
        this.mListView.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.volume.-$$Lambda$CarVolumeDialogImpl$ttx6YehS3HDGJCSyF1Z5F3v3yDI
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                return CarVolumeDialogImpl.lambda$initDialog$1(this.f$0, view, motionEvent);
            }
        });
        this.mPagedListAdapter = new ListItemAdapter(this.mContext, new ListItemProvider.ListProvider(this.mVolumeLineItems), 3);
        this.mListView.setAdapter(this.mPagedListAdapter);
        this.mListView.setMaxPages(-1);
    }

    public static /* synthetic */ void lambda$initDialog$0(CarVolumeDialogImpl carVolumeDialogImpl, DialogInterface dialog) {
        carVolumeDialogImpl.mListView.setTranslationY(-carVolumeDialogImpl.mListView.getHeight());
        carVolumeDialogImpl.mListView.setAlpha(0.0f);
        carVolumeDialogImpl.mListView.animate().alpha(1.0f).translationY(0.0f).setDuration(250L).setInterpolator(new SystemUIInterpolators.LogDecelerateInterpolator()).start();
    }

    public static /* synthetic */ boolean lambda$initDialog$1(CarVolumeDialogImpl carVolumeDialogImpl, View v, MotionEvent event) {
        int action = event.getActionMasked();
        carVolumeDialogImpl.mHovering = action == 9 || action == 7;
        carVolumeDialogImpl.rescheduleTimeoutH();
        return true;
    }

    public void show(int reason) {
        this.mHandler.obtainMessage(1, reason, 0).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showH(int reason) {
        if (D.BUG) {
            Log.d(TAG, "showH r=" + Events.DISMISS_REASONS[reason]);
        }
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        rescheduleTimeoutH();
        this.mPagedListAdapter.notifyDataSetChanged();
        if (this.mShowing) {
            return;
        }
        this.mShowing = true;
        this.mDialog.show();
        Events.writeEvent(this.mContext, 0, Integer.valueOf(reason), Boolean.valueOf(this.mKeyguard.isKeyguardLocked()));
    }

    protected void rescheduleTimeoutH() {
        this.mHandler.removeMessages(2);
        int timeout = computeTimeoutH();
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, 3, 0), timeout);
        if (D.BUG) {
            Log.d(TAG, "rescheduleTimeout " + timeout + " " + Debug.getCaller());
        }
    }

    private int computeTimeoutH() {
        return this.mHovering ? 16000 : 3000;
    }

    protected void dismissH(int reason) {
        if (D.BUG) {
            Log.d(TAG, "dismissH r=" + Events.DISMISS_REASONS[reason]);
        }
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(1);
        if (!this.mShowing) {
            return;
        }
        this.mListView.animate().cancel();
        this.mShowing = false;
        this.mListView.setTranslationY(0.0f);
        this.mListView.setAlpha(1.0f);
        this.mListView.animate().alpha(0.0f).translationY(-this.mListView.getHeight()).setDuration(250L).setInterpolator(new SystemUIInterpolators.LogAccelerateInterpolator()).withEndAction(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$CarVolumeDialogImpl$rCIGYbfmmruUBwL8pG_nmk6yEXo
            @Override // java.lang.Runnable
            public final void run() {
                CarVolumeDialogImpl carVolumeDialogImpl = this.f$0;
                carVolumeDialogImpl.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.volume.-$$Lambda$CarVolumeDialogImpl$Ta8M0dGuIMjlpBaP0lNmvgYiiqA
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarVolumeDialogImpl.lambda$dismissH$2(this.f$0);
                    }
                }, 50L);
            }
        }).start();
        Events.writeEvent(this.mContext, 1, Integer.valueOf(reason));
    }

    public static /* synthetic */ void lambda$dismissH$2(CarVolumeDialogImpl carVolumeDialogImpl) {
        if (D.BUG) {
            Log.d(TAG, "mDialog.dismiss()");
        }
        carVolumeDialogImpl.mDialog.dismiss();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void loadAudioUsageItems() {
        int next;
        try {
            XmlResourceParser xml = this.mContext.getResources().getXml(R.xml.car_volume_items);
            Throwable th = null;
            Object[] objArr = 0;
            try {
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
                do {
                    next = xml.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"carVolumeItems".equals(xml.getName())) {
                    throw new RuntimeException("Meta-data does not start with carVolumeItems tag");
                }
                int depth = xml.getDepth();
                int i = 0;
                while (true) {
                    int next2 = xml.next();
                    if (next2 == 1 || (next2 == 3 && xml.getDepth() <= depth)) {
                        break;
                    }
                    if (next2 != 3 && "item".equals(xml.getName())) {
                        TypedArray typedArrayObtainAttributes = this.mContext.getResources().obtainAttributes(attributeSetAsAttributeSet, R.styleable.carVolumeItems_item);
                        int i2 = typedArrayObtainAttributes.getInt(1, -1);
                        if (i2 >= 0) {
                            VolumeItem volumeItem = new VolumeItem();
                            volumeItem.usage = i2;
                            volumeItem.rank = i;
                            volumeItem.icon = typedArrayObtainAttributes.getResourceId(0, 0);
                            this.mVolumeItems.put(i2, volumeItem);
                            i++;
                        }
                        typedArrayObtainAttributes.recycle();
                    }
                }
                if (xml != null) {
                    xml.close();
                }
            } catch (Throwable th2) {
                if (xml != null) {
                    if (0 != 0) {
                        try {
                            xml.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    } else {
                        xml.close();
                    }
                }
                throw th2;
            }
        } catch (IOException | XmlPullParserException e) {
            Log.e(TAG, "Error parsing volume groups configuration", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VolumeItem getVolumeItemForUsages(int[] usages) {
        int rank = Integer.MAX_VALUE;
        VolumeItem result = null;
        for (int usage : usages) {
            VolumeItem volumeItem = this.mVolumeItems.get(usage);
            if (volumeItem != null && volumeItem.rank < rank) {
                rank = volumeItem.rank;
                result = volumeItem;
            }
        }
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getSeekbarValue(CarAudioManager carAudioManager, int volumeGroupId) {
        try {
            return carAudioManager.getGroupVolume(volumeGroupId);
        } catch (CarNotConnectedException e) {
            Log.e(TAG, "Car is not connected!", e);
            return 0;
        }
    }

    private static int getMaxSeekbarValue(CarAudioManager carAudioManager, int volumeGroupId) {
        try {
            return carAudioManager.getGroupMaxVolume(volumeGroupId);
        } catch (CarNotConnectedException e) {
            Log.e(TAG, "Car is not connected!", e);
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SeekbarListItem addSeekbarListItem(VolumeItem volumeItem, int volumeGroupId, int supplementalIconId, View.OnClickListener supplementalIconOnClickListener) {
        SeekbarListItem listItem = new SeekbarListItem(this.mContext);
        listItem.setMax(getMaxSeekbarValue(this.mCarAudioManager, volumeGroupId));
        int color = this.mContext.getResources().getColor(R.color.car_volume_dialog_tint);
        int progress = getSeekbarValue(this.mCarAudioManager, volumeGroupId);
        listItem.setProgress(progress);
        listItem.setOnSeekBarChangeListener(new VolumeSeekBarChangeListener(volumeGroupId, this.mCarAudioManager));
        Drawable primaryIcon = this.mContext.getResources().getDrawable(volumeItem.icon);
        primaryIcon.setTint(color);
        listItem.setPrimaryActionIcon(primaryIcon);
        if (supplementalIconId != 0) {
            Drawable supplementalIcon = this.mContext.getResources().getDrawable(supplementalIconId);
            supplementalIcon.setTint(color);
            listItem.setSupplementalIcon(supplementalIcon, true, supplementalIconOnClickListener);
        } else {
            listItem.setSupplementalEmptyIcon(true);
        }
        this.mVolumeLineItems.add(listItem);
        volumeItem.listItem = listItem;
        volumeItem.progress = progress;
        return listItem;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VolumeItem findVolumeItem(SeekbarListItem targetItem) {
        for (int i = 0; i < this.mVolumeItems.size(); i++) {
            VolumeItem volumeItem = this.mVolumeItems.valueAt(i);
            if (volumeItem.listItem == targetItem) {
                return volumeItem;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupAudioManager() {
        try {
            this.mCarAudioManager.unregisterVolumeCallback(this.mVolumeChangeCallback.asBinder());
        } catch (CarNotConnectedException e) {
            Log.e(TAG, "Car is not connected!", e);
        }
        this.mVolumeLineItems.clear();
        this.mCarAudioManager = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CarVolumeDialogImpl.this.showH(msg.arg1);
                    break;
                case 2:
                    CarVolumeDialogImpl.this.dismissH(msg.arg1);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class CustomDialog extends Dialog implements DialogInterface {
        public CustomDialog(Context context) {
            super(context, R.style.qs_theme);
        }

        @Override // android.app.Dialog, android.view.Window.Callback
        public boolean dispatchTouchEvent(MotionEvent ev) {
            CarVolumeDialogImpl.this.rescheduleTimeoutH();
            return super.dispatchTouchEvent(ev);
        }

        @Override // android.app.Dialog
        protected void onStart() {
            super.setCanceledOnTouchOutside(true);
            super.onStart();
        }

        @Override // android.app.Dialog
        protected void onStop() {
            super.onStop();
        }

        @Override // android.app.Dialog
        public boolean onTouchEvent(MotionEvent event) {
            if (isShowing() && event.getAction() == 4) {
                CarVolumeDialogImpl.this.dismissH(1);
                return true;
            }
            return false;
        }
    }

    private final class ExpandIconListener implements View.OnClickListener {
        private ExpandIconListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Animator inAnimator;
            CarVolumeDialogImpl.this.mExpanded = !CarVolumeDialogImpl.this.mExpanded;
            if (!CarVolumeDialogImpl.this.mExpanded) {
                Iterator itr = CarVolumeDialogImpl.this.mVolumeLineItems.iterator();
                while (itr.hasNext()) {
                    SeekbarListItem seekbarListItem = (SeekbarListItem) itr.next();
                    VolumeItem volumeItem = CarVolumeDialogImpl.this.findVolumeItem(seekbarListItem);
                    if (!volumeItem.defaultItem) {
                        itr.remove();
                    } else {
                        seekbarListItem.setProgress(volumeItem.progress);
                    }
                }
                inAnimator = AnimatorInflater.loadAnimator(CarVolumeDialogImpl.this.mContext, R.anim.car_arrow_fade_in_rotate_down);
            } else {
                for (int groupId = 0; groupId < CarVolumeDialogImpl.this.mAvailableVolumeItems.size(); groupId++) {
                    VolumeItem volumeItem2 = (VolumeItem) CarVolumeDialogImpl.this.mAvailableVolumeItems.get(groupId);
                    if (volumeItem2 != null) {
                        if (!volumeItem2.defaultItem) {
                            CarVolumeDialogImpl.this.addSeekbarListItem(volumeItem2, groupId, 0, null);
                        } else {
                            volumeItem2.listItem.setProgress(volumeItem2.progress);
                        }
                    }
                }
                inAnimator = AnimatorInflater.loadAnimator(CarVolumeDialogImpl.this.mContext, R.anim.car_arrow_fade_in_rotate_up);
            }
            Animator outAnimator = AnimatorInflater.loadAnimator(CarVolumeDialogImpl.this.mContext, R.anim.car_arrow_fade_out);
            inAnimator.setStartDelay(100L);
            AnimatorSet animators = new AnimatorSet();
            animators.playTogether(outAnimator, inAnimator);
            animators.setTarget(v);
            animators.start();
            CarVolumeDialogImpl.this.mPagedListAdapter.notifyDataSetChanged();
        }
    }

    private final class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private final CarAudioManager mCarAudioManager;
        private final int mVolumeGroupId;

        private VolumeSeekBarChangeListener(int volumeGroupId, CarAudioManager carAudioManager) {
            this.mVolumeGroupId = volumeGroupId;
            this.mCarAudioManager = carAudioManager;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) {
                return;
            }
            try {
                if (this.mCarAudioManager == null) {
                    Log.w(CarVolumeDialogImpl.TAG, "Ignoring volume change event because the car isn't connected");
                } else {
                    ((VolumeItem) CarVolumeDialogImpl.this.mAvailableVolumeItems.get(this.mVolumeGroupId)).progress = progress;
                    this.mCarAudioManager.setGroupVolume(this.mVolumeGroupId, progress, 0);
                }
            } catch (CarNotConnectedException e) {
                Log.e(CarVolumeDialogImpl.TAG, "Car is not connected!", e);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    private static class VolumeItem {
        private boolean defaultItem;
        private int icon;
        private SeekbarListItem listItem;
        private int progress;
        private int rank;
        private int usage;

        private VolumeItem() {
            this.defaultItem = false;
        }
    }
}
