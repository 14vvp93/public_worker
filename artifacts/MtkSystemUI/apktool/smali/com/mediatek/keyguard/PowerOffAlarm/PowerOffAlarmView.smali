.class public Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;
.super Landroid/widget/RelativeLayout;
.source "PowerOffAlarmView.java"

# interfaces
.implements Lcom/android/keyguard/KeyguardSecurityView;
.implements Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;


# instance fields
.field private final DELAY_TIME_SECONDS:I

.field private mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

.field private mContext:Landroid/content/Context;

.field private mFailedPatternAttemptsSinceLastTimeout:I

.field private mGlowPadView:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

.field private final mHandler:Landroid/os/Handler;

.field private mIsDocked:Z

.field private mIsRegistered:Z

.field private mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

.field private mPingEnabled:Z

.field private final mReceiver:Landroid/content/BroadcastReceiver;

.field private mTitleView:Landroid/widget/TextView;

.field private mTotalFailedPatternAttempts:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 109
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 110
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 118
    invoke-direct {p0, p1, p2}, Landroid/widget/RelativeLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 53
    const/4 v0, 0x7

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->DELAY_TIME_SECONDS:I

    .line 54
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mFailedPatternAttemptsSinceLastTimeout:I

    .line 55
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mTotalFailedPatternAttempts:I

    .line 59
    const/4 v1, 0x0

    iput-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mTitleView:Landroid/widget/TextView;

    .line 62
    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mIsRegistered:Z

    .line 75
    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mIsDocked:Z

    .line 82
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mPingEnabled:Z

    .line 84
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$1;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$1;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mHandler:Landroid/os/Handler;

    .line 290
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$2;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$2;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mReceiver:Landroid/content/BroadcastReceiver;

    .line 119
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mContext:Landroid/content/Context;

    .line 121
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    .line 49
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->triggerPing()V

    return-void
.end method

.method static synthetic access$100(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)Landroid/widget/TextView;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    .line 49
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mTitleView:Landroid/widget/TextView;

    return-object v0
.end method

.method static synthetic access$200(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    .line 49
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$300(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    .line 49
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->snooze()V

    return-void
.end method

.method private enableEventDispatching(Z)V
    .locals 3
    .param p1, "flag"    # Z

    .line 270
    :try_start_0
    const-string/jumbo v0, "window"

    .line 272
    invoke-static {v0}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v0

    .line 271
    invoke-static {v0}, Landroid/view/IWindowManager$Stub;->asInterface(Landroid/os/IBinder;)Landroid/view/IWindowManager;

    move-result-object v0

    .line 273
    .local v0, "wm":Landroid/view/IWindowManager;
    if-eqz v0, :cond_0

    .line 274
    invoke-interface {v0, p1}, Landroid/view/IWindowManager;->setEventDispatching(Z)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 278
    .end local v0    # "wm":Landroid/view/IWindowManager;
    :cond_0
    goto :goto_0

    .line 276
    :catch_0
    move-exception v0

    .line 277
    .local v0, "e":Landroid/os/RemoteException;
    const-string v1, "PowerOffAlarmView"

    invoke-virtual {v0}, Landroid/os/RemoteException;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 279
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_0
    return-void
.end method

.method private powerOff()V
    .locals 2

    .line 211
    const-string v0, "PowerOffAlarmView"

    const-string v1, "powerOff selected"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 212
    const-string v0, "com.android.deskclock.DISMISS_ALARM"

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->sendBR(Ljava/lang/String;)V

    .line 213
    return-void
.end method

.method private powerOn()V
    .locals 2

    .line 203
    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->enableEventDispatching(Z)V

    .line 204
    const-string v0, "PowerOffAlarmView"

    const-string v1, "powerOn selected"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 205
    const-string v0, "com.android.deskclock.POWER_ON_ALARM"

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->sendBR(Ljava/lang/String;)V

    .line 206
    const-string v0, "android.intent.action.normal.boot"

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->sendBR(Ljava/lang/String;)V

    .line 207
    return-void
.end method

.method private sendBR(Ljava/lang/String;)V
    .locals 3
    .param p1, "action"    # Ljava/lang/String;

    .line 282
    const-string v0, "PowerOffAlarmView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "send BR: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 283
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mContext:Landroid/content/Context;

    new-instance v1, Landroid/content/Intent;

    invoke-direct {v1, p1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    .line 284
    sget-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sDEFAULT_TITLE:Ljava/lang/String;

    invoke-static {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->setAlarmTitle(Ljava/lang/String;)V

    .line 285
    return-void
.end method

.method private snooze()V
    .locals 2

    .line 197
    const-string v0, "PowerOffAlarmView"

    const-string v1, "snooze selected"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 198
    const-string v0, "com.android.deskclock.SNOOZE_ALARM"

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->sendBR(Ljava/lang/String;)V

    .line 199
    return-void
.end method

.method private triggerPing()V
    .locals 4

    .line 186
    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mPingEnabled:Z

    if-eqz v0, :cond_0

    .line 187
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mGlowPadView:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->ping()V

    .line 190
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mHandler:Landroid/os/Handler;

    const/16 v1, 0x65

    const-wide/16 v2, 0x4b0

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    .line 193
    :cond_0
    return-void
.end method


# virtual methods
.method public getTitle()Ljava/lang/CharSequence;
    .locals 1

    .line 345
    const-string v0, ""

    return-object v0
.end method

.method public needsInput()Z
    .locals 1

    .line 236
    const/4 v0, 0x0

    return v0
.end method

.method public onDetachedFromWindow()V
    .locals 2

    .line 256
    const-string v0, "PowerOffAlarmView"

    const-string v1, "onDetachedFromWindow ...."

    invoke-static {v0, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 257
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 258
    return-void
.end method

.method public onFinishFinalAnimation()V
    .locals 0

    .line 326
    return-void
.end method

.method protected onFinishInflate()V
    .locals 6

    .line 137
    invoke-super {p0}, Landroid/widget/RelativeLayout;->onFinishInflate()V

    .line 138
    const-string v0, "PowerOffAlarmView"

    const-string v1, "onFinishInflate ... "

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 139
    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->setKeepScreenOn(Z)V

    .line 140
    const v1, 0x7f0a005a

    invoke-virtual {p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    iput-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mTitleView:Landroid/widget/TextView;

    .line 141
    const v1, 0x7f0a0180

    invoke-virtual {p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    iput-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mGlowPadView:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    .line 142
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mGlowPadView:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-virtual {v1, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->setOnTriggerListener(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;)V

    .line 143
    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->setFocusableInTouchMode(Z)V

    .line 144
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->triggerPing()V

    .line 147
    new-instance v1, Landroid/content/IntentFilter;

    const-string v2, "android.intent.action.DOCK_EVENT"

    invoke-direct {v1, v2}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 148
    .local v1, "ifilter":Landroid/content/IntentFilter;
    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mContext:Landroid/content/Context;

    const/4 v3, 0x0

    invoke-virtual {v2, v3, v1}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    move-result-object v2

    .line 149
    .local v2, "dockStatus":Landroid/content/Intent;
    if-eqz v2, :cond_1

    .line 150
    const-string v3, "android.intent.extra.DOCK_STATE"

    const/4 v4, -0x1

    invoke-virtual {v2, v3, v4}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v3

    if-eqz v3, :cond_0

    move v3, v0

    goto :goto_0

    :cond_0
    const/4 v3, 0x0

    :goto_0
    iput-boolean v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mIsDocked:Z

    .line 155
    :cond_1
    new-instance v3, Landroid/content/IntentFilter;

    const-string v4, "alarm_killed"

    invoke-direct {v3, v4}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 156
    .local v3, "filter":Landroid/content/IntentFilter;
    const-string v4, "com.android.deskclock.ALARM_SNOOZE"

    invoke-virtual {v3, v4}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 157
    const-string v4, "com.android.deskclock.ALARM_DISMISS"

    invoke-virtual {v3, v4}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 158
    const-string v4, "update.power.off.alarm.label"

    invoke-virtual {v3, v4}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 159
    iget-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mContext:Landroid/content/Context;

    iget-object v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v4, v5, v3}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 161
    iget-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    if-nez v4, :cond_2

    new-instance v4, Lcom/android/internal/widget/LockPatternUtils;

    iget-object v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mContext:Landroid/content/Context;

    invoke-direct {v4, v5}, Lcom/android/internal/widget/LockPatternUtils;-><init>(Landroid/content/Context;)V

    goto :goto_1

    .line 162
    :cond_2
    iget-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    :goto_1
    iput-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    .line 163
    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->enableEventDispatching(Z)V

    .line 164
    invoke-static {}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->getAlarmTitle()Ljava/lang/String;

    move-result-object v0

    .line 165
    .local v0, "titleStr":Ljava/lang/String;
    sget-object v4, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->sDEFAULT_TITLE:Ljava/lang/String;

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_3

    .line 166
    iget-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mTitleView:Landroid/widget/TextView;

    invoke-virtual {v4, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 168
    :cond_3
    return-void
.end method

.method public onGrabbed(Landroid/view/View;I)V
    .locals 0
    .param p1, "v"    # Landroid/view/View;
    .param p2, "handle"    # I

    .line 311
    return-void
.end method

.method public onGrabbedStateChange(Landroid/view/View;I)V
    .locals 0
    .param p1, "v"    # Landroid/view/View;
    .param p2, "handle"    # I

    .line 321
    return-void
.end method

.method public onKeyDown(ILandroid/view/KeyEvent;)Z
    .locals 3
    .param p1, "keyCode"    # I
    .param p2, "event"    # Landroid/view/KeyEvent;

    .line 360
    invoke-static {}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->isAlarmBoot()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 361
    const/4 v0, 0x1

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    .line 366
    :pswitch_0
    const-string v1, "PowerOffAlarmView"

    const-string v2, "onKeyDown() - KeyEvent.KEYCODE_VOLUME_DOWN, do nothing."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 367
    return v0

    .line 363
    :pswitch_1
    const-string v1, "PowerOffAlarmView"

    const-string v2, "onKeyDown() - KeyEvent.KEYCODE_VOLUME_UP, do nothing."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 364
    return v0

    .line 372
    :cond_0
    :goto_0
    invoke-super {p0, p1, p2}, Landroid/widget/RelativeLayout;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result v0

    return v0

    :pswitch_data_0
    .packed-switch 0x18
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public onKeyUp(ILandroid/view/KeyEvent;)Z
    .locals 3
    .param p1, "keyCode"    # I
    .param p2, "event"    # Landroid/view/KeyEvent;

    .line 378
    invoke-static {}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->isAlarmBoot()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 379
    const/4 v0, 0x1

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    .line 384
    :pswitch_0
    const-string v1, "PowerOffAlarmView"

    const-string v2, "onKeyUp() - KeyEvent.KEYCODE_VOLUME_DOWN, do nothing."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 385
    return v0

    .line 381
    :pswitch_1
    const-string v1, "PowerOffAlarmView"

    const-string v2, "onKeyUp() - KeyEvent.KEYCODE_VOLUME_UP, do nothing."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 382
    return v0

    .line 390
    :cond_0
    :goto_0
    invoke-super {p0, p1, p2}, Landroid/widget/RelativeLayout;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result v0

    return v0

    :pswitch_data_0
    .packed-switch 0x18
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public onPause()V
    .locals 0

    .line 241
    return-void
.end method

.method public onReleased(Landroid/view/View;I)V
    .locals 0
    .param p1, "v"    # Landroid/view/View;
    .param p2, "handle"    # I

    .line 316
    return-void
.end method

.method public onResume(I)V
    .locals 2
    .param p1, "reason"    # I

    .line 245
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->reset()V

    .line 246
    const-string v0, "PowerOffAlarmView"

    const-string v1, "onResume"

    invoke-static {v0, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 247
    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 1
    .param p1, "ev"    # Landroid/view/MotionEvent;

    .line 217
    invoke-super {p0, p1}, Landroid/widget/RelativeLayout;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    .line 219
    .local v0, "result":Z
    return v0
.end method

.method public onTrigger(Landroid/view/View;I)V
    .locals 3
    .param p1, "v"    # Landroid/view/View;
    .param p2, "target"    # I

    .line 172
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mGlowPadView:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-virtual {v0, p2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getResourceIdForTarget(I)I

    move-result v0

    .line 173
    .local v0, "resId":I
    const v1, 0x7f0804c5

    if-ne v0, v1, :cond_0

    .line 174
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->snooze()V

    goto :goto_0

    .line 175
    :cond_0
    const v1, 0x7f0804c2

    if-ne v0, v1, :cond_1

    .line 176
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->powerOff()V

    goto :goto_0

    .line 177
    :cond_1
    const v1, 0x7f0804c3

    if-ne v0, v1, :cond_2

    .line 178
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->powerOn()V

    goto :goto_0

    .line 181
    :cond_2
    const-string v1, "PowerOffAlarmView"

    const-string v2, "Trigger detected on unhandled resource. Skipping."

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 183
    :goto_0
    return-void
.end method

.method public reset()V
    .locals 0

    .line 331
    return-void
.end method

.method public setKeyguardCallback(Lcom/android/keyguard/KeyguardSecurityCallback;)V
    .locals 0
    .param p1, "callback"    # Lcom/android/keyguard/KeyguardSecurityCallback;

    .line 124
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    .line 125
    return-void
.end method

.method public setLockPatternUtils(Lcom/android/internal/widget/LockPatternUtils;)V
    .locals 0
    .param p1, "utils"    # Lcom/android/internal/widget/LockPatternUtils;

    .line 132
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->mLockPatternUtils:Lcom/android/internal/widget/LockPatternUtils;

    .line 133
    return-void
.end method

.method public showMessage(Ljava/lang/CharSequence;I)V
    .locals 0
    .param p1, "message"    # Ljava/lang/CharSequence;
    .param p2, "color"    # I

    .line 266
    return-void
.end method

.method public showPromptReason(I)V
    .locals 0
    .param p1, "reason"    # I

    .line 262
    return-void
.end method

.method public startAppearAnimation()V
    .locals 0

    .line 336
    return-void
.end method

.method public startDisappearAnimation(Ljava/lang/Runnable;)Z
    .locals 1
    .param p1, "finishRunnable"    # Ljava/lang/Runnable;

    .line 340
    const/4 v0, 0x0

    return v0
.end method
