.class public Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
.super Lcom/android/keyguard/KeyguardPinBasedInputView;
.source "KeyguardSimPinPukMeView.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;,
        Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$CheckSimMe;,
        Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$CheckSimPinPuk;
    }
.end annotation


# instance fields
.field private mDismissButton:Landroid/widget/Button;

.field private mDismissSimPinPukRunnable:Ljava/lang/Runnable;

.field private mHandler:Landroid/os/Handler;

.field private mIOperatorSIMString:Lcom/mediatek/keyguard/ext/IOperatorSIMString;

.field private mKeyguardUtilExt:Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;

.field private mKeyguardUtils:Lcom/android/keyguard/KeyguardUtils;

.field private mLastSimState:Lcom/android/internal/telephony/IccCardConstants$State;

.field private mNewPinText:Ljava/lang/String;

.field private mNextRepollStatePhoneId:I

.field private mPhoneId:I

.field private mPukText:Ljava/lang/String;

.field private mRemainingAttemptsDialog:Landroid/app/AlertDialog;

.field private mSb:Ljava/lang/StringBuffer;

.field private mSecurityModel:Lcom/android/keyguard/KeyguardSecurityModel;

.field private mSimCardDialog:Landroid/app/AlertDialog;

.field private volatile mSimCheckInProgress:Z

.field private mSimImageView:Landroid/widget/ImageView;

.field private mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

.field private mUnlockEnterState:I

.field mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

.field mUpdateMonitorCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

.field private strLockName:[Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 215
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 216
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 6
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 219
    invoke-direct {p0, p1, p2}, Lcom/android/keyguard/KeyguardPinBasedInputView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 81
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    .line 83
    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 92
    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    .line 100
    const/4 v1, -0x1

    iput v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNextRepollStatePhoneId:I

    .line 101
    sget-object v1, Lcom/android/internal/telephony/IccCardConstants$State;->UNKNOWN:Lcom/android/internal/telephony/IccCardConstants$State;

    iput-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mLastSimState:Lcom/android/internal/telephony/IccCardConstants$State;

    .line 119
    const-string v1, " [NP]"

    const-string v2, " [NSP]"

    const-string v3, " [SP]"

    const-string v4, " [CP]"

    const-string v5, " [SIMP]"

    filled-new-array {v1, v2, v3, v4, v5}, [Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->strLockName:[Ljava/lang/String;

    .line 128
    new-instance v1, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object v2

    const/4 v3, 0x1

    invoke-direct {v1, v2, v0, v3}, Landroid/os/Handler;-><init>(Landroid/os/Looper;Landroid/os/Handler$Callback;Z)V

    iput-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mHandler:Landroid/os/Handler;

    .line 137
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$1;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$1;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mDismissSimPinPukRunnable:Ljava/lang/Runnable;

    .line 143
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$2;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$2;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitorCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    .line 221
    new-instance v0, Lcom/android/keyguard/KeyguardUtils;

    invoke-direct {v0, p1}, Lcom/android/keyguard/KeyguardUtils;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mKeyguardUtils:Lcom/android/keyguard/KeyguardUtils;

    .line 222
    new-instance v0, Ljava/lang/StringBuffer;

    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    .line 223
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 224
    new-instance v0, Lcom/android/keyguard/KeyguardSecurityModel;

    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Lcom/android/keyguard/KeyguardSecurityModel;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityModel:Lcom/android/keyguard/KeyguardSecurityModel;

    .line 228
    :try_start_0
    invoke-static {p1}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    move-result-object v0

    .line 229
    invoke-virtual {v0}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->makeKeyguardUtil()Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mKeyguardUtilExt:Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;

    .line 230
    invoke-static {p1}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->getOpFactory(Landroid/content/Context;)Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;

    move-result-object v0

    .line 231
    invoke-virtual {v0}, Lcom/mediatek/keyguard/ext/OpKeyguardCustomizationFactoryBase;->makeOperatorSIMString()Lcom/mediatek/keyguard/ext/IOperatorSIMString;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mIOperatorSIMString:Lcom/mediatek/keyguard/ext/IOperatorSIMString;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 234
    goto :goto_0

    .line 232
    :catch_0
    move-exception v0

    .line 233
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 235
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)I
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    return v0
.end method

.method static synthetic access$100(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$1000(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$1100(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/internal/telephony/IccCardConstants$State;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mLastSimState:Lcom/android/internal/telephony/IccCardConstants$State;

    return-object v0
.end method

.method static synthetic access$1102(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;Lcom/android/internal/telephony/IccCardConstants$State;)Lcom/android/internal/telephony/IccCardConstants$State;
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # Lcom/android/internal/telephony/IccCardConstants$State;

    .line 77
    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mLastSimState:Lcom/android/internal/telephony/IccCardConstants$State;

    return-object p1
.end method

.method static synthetic access$1200(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)I
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNextRepollStatePhoneId:I

    return v0
.end method

.method static synthetic access$1300(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$1400(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$1500(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$1800(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;ZZ)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # Z
    .param p2, "x2"    # Z

    .line 77
    invoke-virtual {p0, p1, p2}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->resetPasswordText(ZZ)V

    return-void
.end method

.method static synthetic access$1900(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$200(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Landroid/app/ProgressDialog;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    return-object v0
.end method

.method static synthetic access$2000(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mKeyguardUtilExt:Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;

    return-object v0
.end method

.method static synthetic access$2100(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$2200(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;I)Landroid/app/Dialog;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # I

    .line 77
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getSimRemainingAttemptsDialog(I)Landroid/app/Dialog;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$2300(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;I)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # I

    .line 77
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getPinPasswordErrorMessage(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$2400(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/SecurityMessageDisplay;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    return-object v0
.end method

.method static synthetic access$2500(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/SecurityMessageDisplay;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    return-object v0
.end method

.method static synthetic access$2600(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/SecurityMessageDisplay;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    return-object v0
.end method

.method static synthetic access$2700(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;ZZ)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # Z
    .param p2, "x2"    # Z

    .line 77
    invoke-virtual {p0, p1, p2}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->resetPasswordText(ZZ)V

    return-void
.end method

.method static synthetic access$2800(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$2900(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;ZZ)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # Z
    .param p2, "x2"    # Z

    .line 77
    invoke-virtual {p0, p1, p2}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->resetPasswordText(ZZ)V

    return-void
.end method

.method static synthetic access$300(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Ljava/lang/Runnable;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mDismissSimPinPukRunnable:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$3000(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$3100(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$3200(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)I
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    return v0
.end method

.method static synthetic access$3202(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;I)I
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # I

    .line 77
    iput p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    return p1
.end method

.method static synthetic access$3300(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;I)Landroid/app/Dialog;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # I

    .line 77
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getPukRemainingAttemptsDialog(I)Landroid/app/Dialog;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$3400(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;I)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # I

    .line 77
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getPukPasswordErrorMessage(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$3500(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/SecurityMessageDisplay;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    return-object v0
.end method

.method static synthetic access$3600(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/SecurityMessageDisplay;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    return-object v0
.end method

.method static synthetic access$3700(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/SecurityMessageDisplay;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    return-object v0
.end method

.method static synthetic access$3800(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$3900(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$400(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$4000(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Ljava/lang/StringBuffer;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    return-object v0
.end method

.method static synthetic access$4100(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;I)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # I

    .line 77
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->minusRetryMeCount(I)V

    return-void
.end method

.method static synthetic access$4200(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$4300(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;Ljava/lang/CharSequence;Z)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # Ljava/lang/CharSequence;
    .param p2, "x2"    # Z

    .line 77
    invoke-direct {p0, p1, p2}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->setInputInvalidAlertDialog(Ljava/lang/CharSequence;Z)V

    return-void
.end method

.method static synthetic access$4400(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$4500(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$4600(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$4700(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)[Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->strLockName:[Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$4800(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;I)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # I

    .line 77
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getRetryMeString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$4900(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/SecurityMessageDisplay;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    return-object v0
.end method

.method static synthetic access$500(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->simStateReadyProcess()V

    return-void
.end method

.method static synthetic access$5000(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$5100(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$5200(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;ZZ)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # Z
    .param p2, "x2"    # Z

    .line 77
    invoke-virtual {p0, p1, p2}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->resetPasswordText(ZZ)V

    return-void
.end method

.method static synthetic access$5300(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$600(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$702(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;Z)Z
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # Z

    .line 77
    iput-boolean p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    return p1
.end method

.method static synthetic access$800(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)Lcom/android/keyguard/KeyguardSecurityCallback;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;

    .line 77
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    return-object v0
.end method

.method static synthetic access$900(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;I)I
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
    .param p1, "x1"    # I

    .line 77
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getRetryMeCount(I)I

    move-result v0

    return v0
.end method

.method private checkMe(I)V
    .locals 2
    .param p1, "phoneId"    # I

    .line 909
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getSimUnlockProgressDialog()Landroid/app/Dialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Dialog;->show()V

    .line 910
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    if-nez v0, :cond_0

    .line 911
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    .line 912
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$5;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v1}, Lcom/android/keyguard/PasswordTextView;->getText()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, p0, v1, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$5;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;Ljava/lang/String;I)V

    .line 952
    invoke-virtual {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$5;->start()V

    .line 954
    :cond_0
    return-void
.end method

.method private checkPin(I)V
    .locals 3
    .param p1, "phoneId"    # I

    .line 797
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getSimUnlockProgressDialog()Landroid/app/Dialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Dialog;->show()V

    .line 798
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mSimCheckInProgress: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 799
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    if-nez v0, :cond_0

    .line 800
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    .line 801
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$3;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v1}, Lcom/android/keyguard/PasswordTextView;->getText()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, p0, v1, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$3;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;Ljava/lang/String;I)V

    .line 843
    invoke-virtual {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$3;->start()V

    .line 845
    :cond_0
    return-void
.end method

.method private checkPuk(I)V
    .locals 3
    .param p1, "phoneId"    # I

    .line 852
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->updatePinEnterScreen()V

    .line 853
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    const/4 v1, 0x4

    if-eq v0, v1, :cond_0

    .line 854
    return-void

    .line 856
    :cond_0
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getSimUnlockProgressDialog()Landroid/app/Dialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Dialog;->show()V

    .line 857
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mSimCheckInProgress: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 858
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    if-nez v0, :cond_1

    .line 859
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCheckInProgress:Z

    .line 860
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$4;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPukText:Ljava/lang/String;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNewPinText:Ljava/lang/String;

    invoke-direct {v0, p0, v1, v2, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$4;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;Ljava/lang/String;Ljava/lang/String;I)V

    .line 900
    invoke-virtual {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$4;->start()V

    .line 902
    :cond_1
    return-void
.end method

.method private dealWithPinOrPukUnlock()V
    .locals 3

    .line 775
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v0

    sget-object v1, Lcom/android/internal/telephony/IccCardConstants$State;->PIN_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v0, v1, :cond_0

    .line 776
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onClick, checkPin, mPhoneId="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 777
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->checkPin(I)V

    goto :goto_0

    .line 778
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v0

    sget-object v1, Lcom/android/internal/telephony/IccCardConstants$State;->PUK_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v0, v1, :cond_1

    .line 780
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onClick, checkPuk, mPhoneId="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 781
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->checkPuk(I)V

    goto :goto_0

    .line 782
    :cond_1
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v0

    sget-object v1, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v0, v1, :cond_2

    .line 784
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isMediatekSimMeLockSupport()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 785
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onClick, checkMe, mPhoneId="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 786
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->checkMe(I)V

    goto :goto_0

    .line 788
    :cond_2
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "wrong status, mPhoneId="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 790
    :goto_0
    return-void
.end method

.method private getNextRepollStatePhoneId()I
    .locals 3

    .line 1042
    sget-object v0, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mLastSimState:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v0, v1, :cond_1

    .line 1043
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isMediatekSimMeLockSupport()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 1044
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->getNumOfPhone()I

    move-result v1

    if-ge v0, v1, :cond_1

    .line 1045
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityModel:Lcom/android/keyguard/KeyguardSecurityModel;

    invoke-virtual {v1, v0}, Lcom/android/keyguard/KeyguardSecurityModel;->isPinPukOrMeRequiredOfPhoneId(I)Z

    move-result v1

    if-nez v1, :cond_0

    .line 1046
    nop

    .line 1044
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 1049
    :cond_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-virtual {v1, v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v1

    .line 1050
    .local v1, "simState":Lcom/android/internal/telephony/IccCardConstants$State;
    sget-object v2, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v1, v2, :cond_1

    .line 1051
    return v0

    .line 1057
    .end local v0    # "i":I
    .end local v1    # "simState":Lcom/android/internal/telephony/IccCardConstants$State;
    :cond_1
    const/4 v0, -0x1

    return v0
.end method

.method private getPinPasswordErrorMessage(I)Ljava/lang/String;
    .locals 5
    .param p1, "attemptsRemaining"    # I

    .line 344
    if-nez p1, :cond_0

    .line 345
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f110327

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    .local v0, "displayMessage":Ljava/lang/String;
    :goto_0
    goto :goto_1

    .line 346
    .end local v0    # "displayMessage":Ljava/lang/String;
    :cond_0
    if-lez p1, :cond_1

    .line 347
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f0f0005

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    .line 349
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    aput-object v4, v2, v3

    .line 348
    invoke-virtual {v0, v1, p1, v2}, Landroid/content/res/Resources;->getQuantityString(II[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    .line 351
    :cond_1
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f110325

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    .line 354
    .restart local v0    # "displayMessage":Ljava/lang/String;
    :goto_1
    const-string v1, "KeyguardSimPinPukMeView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getPinPasswordErrorMessage: attemptsRemaining="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " displayMessage="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 357
    return-object v0
.end method

.method private getPukPasswordErrorMessage(I)Ljava/lang/String;
    .locals 5
    .param p1, "attemptsRemaining"    # I

    .line 363
    if-nez p1, :cond_0

    .line 364
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f110328

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    .local v0, "displayMessage":Ljava/lang/String;
    :goto_0
    goto :goto_1

    .line 365
    .end local v0    # "displayMessage":Ljava/lang/String;
    :cond_0
    if-lez p1, :cond_1

    .line 366
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f0f0006

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    .line 368
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    aput-object v4, v2, v3

    .line 367
    invoke-virtual {v0, v1, p1, v2}, Landroid/content/res/Resources;->getQuantityString(II[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    .line 370
    :cond_1
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f110326

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    .line 373
    .restart local v0    # "displayMessage":Ljava/lang/String;
    :goto_1
    const-string v1, "KeyguardSimPinPukMeView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getPukPasswordErrorMessage: attemptsRemaining="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " displayMessage="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 376
    return-object v0
.end method

.method private getPukRemainingAttemptsDialog(I)Landroid/app/Dialog;
    .locals 4
    .param p1, "remaining"    # I

    .line 973
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getPukPasswordErrorMessage(I)Ljava/lang/String;

    move-result-object v0

    .line 974
    .local v0, "msg":Ljava/lang/String;
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    if-nez v1, :cond_0

    .line 975
    new-instance v1, Landroid/app/AlertDialog$Builder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-direct {v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 976
    .local v1, "builder":Landroid/app/AlertDialog$Builder;
    invoke-virtual {v1, v0}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 977
    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    .line 978
    const v2, 0x7f110402

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setNeutralButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 979
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    iput-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    .line 980
    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    invoke-virtual {v2}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v2

    const/16 v3, 0x7d9

    invoke-virtual {v2, v3}, Landroid/view/Window;->setType(I)V

    .line 982
    .end local v1    # "builder":Landroid/app/AlertDialog$Builder;
    goto :goto_0

    .line 983
    :cond_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    invoke-virtual {v1, v0}, Landroid/app/AlertDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 985
    :goto_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    return-object v1
.end method

.method private getRetryMeCount(I)I
    .locals 1
    .param p1, "phoneId"    # I

    .line 479
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-virtual {v0, p1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimMeLeftRetryCountOfPhoneId(I)I

    move-result v0

    return v0
.end method

.method private getRetryMeString(I)Ljava/lang/String;
    .locals 6
    .param p1, "phoneId"    # I

    .line 474
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getRetryMeCount(I)I

    move-result v0

    .line 475
    .local v0, "meRetryCount":I
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "("

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x0

    aput-object v4, v3, v5

    const v4, 0x7f1104f9

    invoke-virtual {v2, v4, v3}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ")"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method private getSimRemainingAttemptsDialog(I)Landroid/app/Dialog;
    .locals 4
    .param p1, "remaining"    # I

    .line 957
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getPinPasswordErrorMessage(I)Ljava/lang/String;

    move-result-object v0

    .line 958
    .local v0, "msg":Ljava/lang/String;
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    if-nez v1, :cond_0

    .line 959
    new-instance v1, Landroid/app/AlertDialog$Builder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-direct {v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 960
    .local v1, "builder":Landroid/app/AlertDialog$Builder;
    invoke-virtual {v1, v0}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 961
    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    .line 962
    const v2, 0x7f110402

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setNeutralButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 963
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    iput-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    .line 964
    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    invoke-virtual {v2}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v2

    const/16 v3, 0x7d9

    invoke-virtual {v2, v3}, Landroid/view/Window;->setType(I)V

    .line 966
    .end local v1    # "builder":Landroid/app/AlertDialog$Builder;
    goto :goto_0

    .line 967
    :cond_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    invoke-virtual {v1, v0}, Landroid/app/AlertDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 969
    :goto_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mRemainingAttemptsDialog:Landroid/app/AlertDialog;

    return-object v1
.end method

.method private getSimUnlockProgressDialog()Landroid/app/Dialog;
    .locals 5

    .line 725
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    if-nez v0, :cond_0

    .line 726
    new-instance v0, Landroid/app/ProgressDialog;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    .line 728
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    const v1, 0x7f11033d

    invoke-virtual {v0, v1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    .line 729
    .local v0, "msg":Ljava/lang/String;
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mIOperatorSIMString:Lcom/mediatek/keyguard/ext/IOperatorSIMString;

    iget v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    sget-object v3, Lcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;->DELSIM:Lcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;

    iget-object v4, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-interface {v1, v0, v2, v3, v4}, Lcom/mediatek/keyguard/ext/IOperatorSIMString;->getOperatorSIMString(Ljava/lang/String;ILcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;Landroid/content/Context;)Ljava/lang/String;

    move-result-object v0

    .line 731
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v1, v0}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 733
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Landroid/app/ProgressDialog;->setIndeterminate(Z)V

    .line 734
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/app/ProgressDialog;->setCancelable(Z)V

    .line 735
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    instance-of v1, v1, Landroid/app/Activity;

    if-nez v1, :cond_0

    .line 736
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v1}, Landroid/app/ProgressDialog;->getWindow()Landroid/view/Window;

    move-result-object v1

    const/16 v2, 0x7d9

    invoke-virtual {v1, v2}, Landroid/view/Window;->setType(I)V

    .line 740
    .end local v0    # "msg":Ljava/lang/String;
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    return-object v0
.end method

.method private minusRetryMeCount(I)V
    .locals 1
    .param p1, "phoneId"    # I

    .line 483
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    invoke-virtual {v0, p1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->minusSimMeLeftRetryCountOfPhoneId(I)V

    .line 484
    return-void
.end method

.method private setInputInvalidAlertDialog(Ljava/lang/CharSequence;Z)V
    .locals 4
    .param p1, "message"    # Ljava/lang/CharSequence;
    .param p2, "shouldDisplay"    # Z

    .line 454
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0, p1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/CharSequence;)V

    .line 456
    .local v0, "sb":Ljava/lang/StringBuilder;
    if-eqz p2, :cond_0

    .line 457
    new-instance v1, Landroid/app/AlertDialog$Builder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-direct {v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 458
    invoke-virtual {v1, v0}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    const v2, 0x104000a

    const/4 v3, 0x0

    .line 459
    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    const/4 v2, 0x1

    .line 460
    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 461
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v1

    .line 463
    .local v1, "newDialog":Landroid/app/AlertDialog;
    invoke-virtual {v1}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v2

    const/16 v3, 0x7d9

    invoke-virtual {v2, v3}, Landroid/view/Window;->setType(I)V

    .line 465
    invoke-virtual {v1}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v2

    const/4 v3, 0x2

    invoke-virtual {v2, v3}, Landroid/view/Window;->addFlags(I)V

    .line 467
    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 468
    .end local v1    # "newDialog":Landroid/app/AlertDialog;
    goto :goto_0

    .line 469
    :cond_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-static {v1, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;)Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    move-result-object v1

    invoke-virtual {v1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->show()V

    .line 471
    :goto_0
    return-void
.end method

.method private showDismissIfNeed(Lcom/android/internal/telephony/IccCardConstants$State;)V
    .locals 2
    .param p1, "simState"    # Lcom/android/internal/telephony/IccCardConstants$State;

    .line 1254
    sget-object v0, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne p1, v0, :cond_0

    .line 1255
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isMediatekSimMeLockSupport()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 1256
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isDismissSimMeLockSupport()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 1257
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mDismissButton:Landroid/widget/Button;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setVisibility(I)V

    .line 1258
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mDismissButton:Landroid/widget/Button;

    new-instance v1, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$6;

    invoke-direct {v1, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$6;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;)V

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto :goto_0

    .line 1267
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mDismissButton:Landroid/widget/Button;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setVisibility(I)V

    .line 1268
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mDismissButton:Landroid/widget/Button;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1270
    :goto_0
    return-void
.end method

.method private simStateReadyProcess()V
    .locals 4

    .line 1018
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getNextRepollStatePhoneId()I

    move-result v0

    iput v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNextRepollStatePhoneId:I

    .line 1019
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "simStateReadyProcess mNextRepollStatePhoneId ="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNextRepollStatePhoneId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1020
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNextRepollStatePhoneId:I

    const/4 v1, 0x1

    const/4 v2, -0x1

    if-eq v0, v2, :cond_0

    .line 1022
    :try_start_0
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getSimUnlockProgressDialog()Landroid/app/Dialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Dialog;->show()V

    .line 1023
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "repollIccStateForNetworkLock phoneId ="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNextRepollStatePhoneId:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1027
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNextRepollStatePhoneId:I

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUtils;->getSubIdUsingPhoneId(I)I

    move-result v0

    .line 1028
    .local v0, "subId":I
    const-string v2, "phoneEx"

    invoke-static {v2}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v2

    invoke-static {v2}, Lcom/mediatek/internal/telephony/IMtkTelephonyEx$Stub;->asInterface(Landroid/os/IBinder;)Lcom/mediatek/internal/telephony/IMtkTelephonyEx;

    move-result-object v2

    .line 1029
    invoke-interface {v2, v0, v1}, Lcom/mediatek/internal/telephony/IMtkTelephonyEx;->repollIccStateForNetworkLock(IZ)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .end local v0    # "subId":I
    goto :goto_0

    .line 1030
    :catch_0
    move-exception v0

    .line 1031
    .local v0, "e":Landroid/os/RemoteException;
    const-string v1, "KeyguardSimPinPukMeView"

    const-string v2, "repollIccStateForNetworkLock exception caught"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1032
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_0
    goto :goto_1

    .line 1035
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    invoke-static {}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getCurrentUser()I

    move-result v2

    invoke-interface {v0, v1, v2}, Lcom/android/keyguard/KeyguardSecurityCallback;->dismiss(ZI)V

    .line 1037
    :goto_1
    return-void
.end method

.method private updatePinEnterScreen()V
    .locals 6

    .line 533
    iget v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    const v1, 0x7f1102f7

    const/4 v2, 0x2

    const/4 v3, 0x1

    const/4 v4, 0x0

    packed-switch v0, :pswitch_data_0

    goto/16 :goto_0

    .line 567
    :pswitch_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNewPinText:Ljava/lang/String;

    iget-object v5, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v5}, Lcom/android/keyguard/PasswordTextView;->getText()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v0, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 568
    iput v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    .line 569
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    invoke-virtual {v2}, Ljava/lang/StringBuffer;->length()I

    move-result v2

    invoke-virtual {v0, v4, v2}, Ljava/lang/StringBuffer;->delete(II)Ljava/lang/StringBuffer;

    .line 570
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    const v4, 0x7f1102dd

    invoke-virtual {v2, v4}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuffer;

    .line 571
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-virtual {v2, v1}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuffer;

    .line 572
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "updatePinEnterScreen() - STATE_REENTER_NEW, true ,mSecurityMessageDisplay.setMessage = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    .line 573
    invoke-virtual {v2}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 572
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 574
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    invoke-virtual {v1}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    .line 576
    :cond_0
    const-string v0, "KeyguardSimPinPukMeView"

    const-string v1, "updatePinEnterScreen() - STATE_REENTER_NEW, false ,mSecurityMessageDisplay.setMessage = empty string."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 578
    const/4 v0, 0x4

    iput v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    .line 579
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    const-string v1, ""

    invoke-interface {v0, v1}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(Ljava/lang/CharSequence;)V

    .line 581
    goto/16 :goto_0

    .line 551
    :pswitch_1
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v0}, Lcom/android/keyguard/PasswordTextView;->getText()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNewPinText:Ljava/lang/String;

    .line 552
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mNewPinText:Ljava/lang/String;

    invoke-direct {p0, v0, v4}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->validatePin(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 553
    const/4 v0, 0x3

    iput v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    .line 554
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    invoke-virtual {v1}, Ljava/lang/StringBuffer;->length()I

    move-result v1

    invoke-virtual {v0, v4, v1}, Ljava/lang/StringBuffer;->delete(II)Ljava/lang/StringBuffer;

    .line 555
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    const v2, 0x7f1102f6

    invoke-virtual {v1, v2}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuffer;

    .line 556
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "updatePinEnterScreen() - STATE_ENTER_NEW, validatePin = true ,mSecurityMessageDisplay.setMessage = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    .line 557
    invoke-virtual {v2}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 556
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 558
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    invoke-virtual {v1}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 560
    :cond_1
    const-string v0, "KeyguardSimPinPukMeView"

    const-string v1, "updatePinEnterScreen() - STATE_ENTER_NEW, validatePin = false ,mSecurityMessageDisplay.setMessage = R.string.keyguard_code_length_prompt"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 562
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    const v1, 0x7f1102de

    invoke-interface {v0, v1}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(I)V

    .line 564
    goto :goto_0

    .line 535
    :pswitch_2
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v0}, Lcom/android/keyguard/PasswordTextView;->getText()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPukText:Ljava/lang/String;

    .line 536
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPukText:Ljava/lang/String;

    invoke-direct {p0, v0, v3}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->validatePin(Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 537
    iput v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    .line 538
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    invoke-virtual {v2}, Ljava/lang/StringBuffer;->length()I

    move-result v2

    invoke-virtual {v0, v4, v2}, Ljava/lang/StringBuffer;->delete(II)Ljava/lang/StringBuffer;

    .line 539
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-virtual {v2, v1}, Landroid/content/Context;->getText(I)Ljava/lang/CharSequence;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuffer;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuffer;

    .line 540
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "updatePinEnterScreen() - STATE_ENTER_UK, validatePin = true ,mSecurityMessageDisplay.setMessage = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    .line 541
    invoke-virtual {v2}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 540
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 542
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSb:Ljava/lang/StringBuffer;

    invoke-virtual {v1}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 544
    :cond_2
    const-string v0, "KeyguardSimPinPukMeView"

    const-string v1, "updatePinEnterScreen() - STATE_ENTER_UK, validatePin = false ,mSecurityMessageDisplay.setMessage = R.string.invalidPuk"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 546
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    const v1, 0x7f11029f

    invoke-interface {v0, v1}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(I)V

    .line 548
    nop

    .line 586
    :goto_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v0, v3, v3}, Lcom/android/keyguard/PasswordTextView;->reset(ZZ)V

    .line 587
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    invoke-interface {v0}, Lcom/android/keyguard/KeyguardSecurityCallback;->userActivity()V

    .line 588
    return-void

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private validatePin(Ljava/lang/String;Z)Z
    .locals 3
    .param p1, "pin"    # Ljava/lang/String;
    .param p2, "isPUK"    # Z

    .line 518
    const/16 v0, 0x8

    if-eqz p2, :cond_0

    move v1, v0

    goto :goto_0

    :cond_0
    const/4 v1, 0x4

    .line 520
    .local v1, "pinMinimum":I
    :goto_0
    if-eqz p1, :cond_2

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v2

    if-lt v2, v1, :cond_2

    .line 521
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v2

    if-le v2, v0, :cond_1

    goto :goto_1

    .line 524
    :cond_1
    const/4 v0, 0x1

    return v0

    .line 522
    :cond_2
    :goto_1
    const/4 v0, 0x0

    return v0
.end method


# virtual methods
.method protected getPasswordTextViewId()I
    .locals 1

    .line 387
    const v0, 0x7f0a03aa

    return v0
.end method

.method public onAttachedToWindow()V
    .locals 2

    .line 411
    invoke-super {p0}, Lcom/android/keyguard/KeyguardPinBasedInputView;->onAttachedToWindow()V

    .line 412
    const-string v0, "KeyguardSimPinPukMeView"

    const-string v1, "onAttachedToWindow"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 413
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitorCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->registerCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    .line 414
    return-void
.end method

.method public onDetachedFromWindow()V
    .locals 2

    .line 418
    invoke-super {p0}, Lcom/android/keyguard/KeyguardPinBasedInputView;->onDetachedFromWindow()V

    .line 419
    const-string v0, "KeyguardSimPinPukMeView"

    const-string v1, "onDetachedFromWindow"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 420
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mDismissSimPinPukRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 421
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitorCallback:Lcom/android/keyguard/KeyguardUpdateMonitorCallback;

    invoke-virtual {v0, v1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->removeCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    .line 422
    return-void
.end method

.method protected onFinishInflate()V
    .locals 3

    .line 392
    invoke-super {p0}, Lcom/android/keyguard/KeyguardPinBasedInputView;->onFinishInflate()V

    .line 394
    const/4 v0, -0x1

    iput v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    .line 396
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mEcaView:Landroid/view/View;

    instance-of v0, v0, Lcom/android/keyguard/EmergencyCarrierArea;

    if-eqz v0, :cond_0

    .line 397
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mEcaView:Landroid/view/View;

    check-cast v0, Lcom/android/keyguard/EmergencyCarrierArea;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/android/keyguard/EmergencyCarrierArea;->setCarrierTextVisible(Z)V

    .line 399
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mEcaView:Landroid/view/View;

    const v1, 0x7f0a00d7

    .line 400
    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 401
    .local v0, "ctView":Landroid/view/View;
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mKeyguardUtilExt:Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;

    move-object v2, v0

    check-cast v2, Landroid/widget/TextView;

    invoke-interface {v1, v2}, Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;->customizeCarrierTextGravity(Landroid/widget/TextView;)V

    .line 404
    .end local v0    # "ctView":Landroid/view/View;
    :cond_0
    const v0, 0x7f0a0209

    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimImageView:Landroid/widget/ImageView;

    .line 406
    const v0, 0x7f0a01f0

    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mDismissButton:Landroid/widget/Button;

    .line 407
    return-void
.end method

.method public onPause()V
    .locals 1

    .line 447
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    if-eqz v0, :cond_0

    .line 448
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->dismiss()V

    .line 449
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    .line 451
    :cond_0
    return-void
.end method

.method public onResume(I)V
    .locals 3
    .param p1, "reason"    # I

    .line 430
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    if-eqz v0, :cond_0

    .line 431
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->dismiss()V

    .line 432
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimUnlockProgressDialog:Landroid/app/ProgressDialog;

    .line 436
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    const-string v1, "input_method"

    .line 437
    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/inputmethod/InputMethodManager;

    .line 438
    .local v0, "imm":Landroid/view/inputmethod/InputMethodManager;
    invoke-virtual {v0}, Landroid/view/inputmethod/InputMethodManager;->isActive()Z

    move-result v1

    if-eqz v1, :cond_1

    .line 439
    const-string v1, "KeyguardSimPinPukMeView"

    const-string v2, "IME is showing, we should hide it"

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 440
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getWindowToken()Landroid/os/IBinder;

    move-result-object v1

    const/4 v2, 0x2

    invoke-virtual {v0, v1, v2}, Landroid/view/inputmethod/InputMethodManager;->hideSoftInputFromWindow(Landroid/os/IBinder;I)Z

    .line 443
    :cond_1
    return-void
.end method

.method public onWindowFocusChanged(Z)V
    .locals 3
    .param p1, "hasWindowFocus"    # Z

    .line 1234
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onWindowFocusChanged(hasWindowFocus = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, ")"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1235
    if-eqz p1, :cond_0

    .line 1237
    const/4 v0, 0x1

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->resetPasswordText(ZZ)V

    .line 1238
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-static {v0}, Lcom/android/keyguard/KeyguardUtils;->requestImeStatusRefresh(Landroid/content/Context;)V

    .line 1240
    :cond_0
    return-void
.end method

.method public resetState()V
    .locals 1

    .line 259
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->resetState(Z)V

    .line 260
    return-void
.end method

.method public resetState(Z)V
    .locals 17
    .param p1, "forceReload"    # Z

    move-object/from16 v0, p0

    .line 267
    const-string v1, "KeyguardSimPinPukMeView"

    const-string v2, "Resetting state"

    invoke-static {v1, v2}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 268
    invoke-super/range {p0 .. p0}, Lcom/android/keyguard/KeyguardPinBasedInputView;->resetState()V

    .line 270
    const v1, 0x7f0a03af

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 271
    .local v1, "forText":Landroid/widget/TextView;
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    const/4 v4, 0x1

    new-array v5, v4, [Ljava/lang/Object;

    iget v6, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    add-int/2addr v6, v4

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    const/4 v7, 0x0

    aput-object v6, v5, v7

    const v6, 0x7f11033e

    invoke-virtual {v3, v6, v5}, Landroid/content/Context;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 274
    invoke-virtual/range {p0 .. p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 275
    .local v2, "rez":Landroid/content/res/Resources;
    const-string v3, ""

    .line 276
    .local v3, "msg":Ljava/lang/String;
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->getNumOfPhone()I

    move-result v5

    .line 277
    .local v5, "count":I
    const/4 v6, -0x1

    .line 279
    .local v6, "color":I
    iget-object v8, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v9, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v8, v9}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v8

    .line 280
    .local v8, "simState":Lcom/android/internal/telephony/IccCardConstants$State;
    invoke-direct {v0, v8}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->showDismissIfNeed(Lcom/android/internal/telephony/IccCardConstants$State;)V

    .line 281
    const/4 v9, 0x5

    const v10, 0x7f110546

    const/4 v11, 0x2

    if-ge v5, v11, :cond_3

    .line 282
    sget-object v11, Lcom/android/internal/telephony/IccCardConstants$State;->PIN_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v8, v11, :cond_1

    .line 283
    const v4, 0x7f11033b

    invoke-virtual {v2, v4}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v3

    .line 284
    iput v7, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    .line 329
    :cond_0
    :goto_0
    move/from16 v13, p1

    goto/16 :goto_3

    .line 285
    :cond_1
    sget-object v7, Lcom/android/internal/telephony/IccCardConstants$State;->PUK_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v8, v7, :cond_2

    .line 286
    const v7, 0x7f110338

    invoke-virtual {v2, v7}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v3

    .line 287
    iput v4, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    goto :goto_0

    .line 288
    :cond_2
    sget-object v4, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v4, v8, :cond_0

    .line 289
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isMediatekSimMeLockSupport()Z

    move-result v4

    if-eqz v4, :cond_0

    .line 290
    iget-object v4, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v7, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v4, v7}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimMeCategoryOfPhoneId(I)I

    move-result v4

    .line 291
    .local v4, "category":I
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v10}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v7, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v10, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->strLockName:[Ljava/lang/String;

    aget-object v10, v10, v4

    invoke-virtual {v7, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v10, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    .line 293
    invoke-direct {v0, v10}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getRetryMeString(I)Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v7, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 294
    iput v9, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    .line 295
    .end local v4    # "category":I
    goto :goto_0

    .line 297
    :cond_3
    iget v11, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-static {v11}, Lcom/android/keyguard/KeyguardUtils;->getSubIdUsingPhoneId(I)I

    move-result v11

    .line 301
    .local v11, "subId":I
    iget-object v12, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    move/from16 v13, p1

    invoke-virtual {v12, v11, v13}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSubscriptionInfoForSubId(IZ)Landroid/telephony/SubscriptionInfo;

    move-result-object v12

    .line 302
    .local v12, "info":Landroid/telephony/SubscriptionInfo;
    if-eqz v12, :cond_4

    invoke-virtual {v12}, Landroid/telephony/SubscriptionInfo;->getDisplayName()Ljava/lang/CharSequence;

    move-result-object v14

    goto :goto_1

    :cond_4
    const-string v14, ""

    .line 304
    .local v14, "displayName":Ljava/lang/CharSequence;
    :goto_1
    if-nez v12, :cond_5

    .line 305
    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "CARD "

    invoke-virtual {v15, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v9, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    add-int/2addr v9, v4

    invoke-static {v9}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v15, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    .line 306
    const-string v9, "KeyguardSimPinPukMeView"

    const-string/jumbo v15, "we set a displayname"

    invoke-static {v9, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 308
    :cond_5
    const-string v9, "KeyguardSimPinPukMeView"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "resetState() - subId = "

    invoke-virtual {v15, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v10, ", displayName = "

    invoke-virtual {v15, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 310
    sget-object v9, Lcom/android/internal/telephony/IccCardConstants$State;->PIN_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v8, v9, :cond_6

    .line 311
    const v9, 0x7f11033c

    new-array v4, v4, [Ljava/lang/Object;

    aput-object v14, v4, v7

    invoke-virtual {v2, v9, v4}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    .line 312
    iput v7, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    goto :goto_2

    .line 313
    :cond_6
    sget-object v9, Lcom/android/internal/telephony/IccCardConstants$State;->PUK_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v8, v9, :cond_7

    .line 314
    const v9, 0x7f110339

    new-array v10, v4, [Ljava/lang/Object;

    aput-object v14, v10, v7

    invoke-virtual {v2, v9, v10}, Landroid/content/res/Resources;->getString(I[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    .line 315
    iput v4, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    goto :goto_2

    .line 316
    :cond_7
    sget-object v4, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v4, v8, :cond_8

    .line 317
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isMediatekSimMeLockSupport()Z

    move-result v4

    if-eqz v4, :cond_8

    .line 318
    iget-object v4, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v7, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v4, v7}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimMeCategoryOfPhoneId(I)I

    move-result v4

    .line 319
    .restart local v4    # "category":I
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const v9, 0x7f110546

    invoke-virtual {v2, v9}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v9, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->strLockName:[Ljava/lang/String;

    aget-object v9, v9, v4

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v9, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    .line 321
    invoke-direct {v0, v9}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->getRetryMeString(I)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 322
    const/4 v7, 0x5

    iput v7, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUnlockEnterState:I

    .line 324
    .end local v4    # "category":I
    :cond_8
    :goto_2
    if-eqz v12, :cond_9

    .line 325
    invoke-virtual {v12}, Landroid/telephony/SubscriptionInfo;->getIconTint()I

    move-result v6

    .line 329
    .end local v11    # "subId":I
    .end local v12    # "info":Landroid/telephony/SubscriptionInfo;
    .end local v14    # "displayName":Ljava/lang/CharSequence;
    :cond_9
    :goto_3
    iget-object v4, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mKeyguardUtilExt:Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;

    iget v7, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    iget-object v9, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimImageView:Landroid/widget/ImageView;

    invoke-interface {v4, v7, v9, v1}, Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;->customizePinPukLockView(ILandroid/widget/ImageView;Landroid/widget/TextView;)V

    .line 331
    iget-object v4, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimImageView:Landroid/widget/ImageView;

    invoke-static {v6}, Landroid/content/res/ColorStateList;->valueOf(I)Landroid/content/res/ColorStateList;

    move-result-object v7

    invoke-virtual {v4, v7}, Landroid/widget/ImageView;->setImageTintList(Landroid/content/res/ColorStateList;)V

    .line 333
    iget-object v4, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mIOperatorSIMString:Lcom/mediatek/keyguard/ext/IOperatorSIMString;

    iget v7, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    sget-object v9, Lcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;->DELSIM:Lcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;

    iget-object v10, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mContext:Landroid/content/Context;

    invoke-interface {v4, v3, v7, v9, v10}, Lcom/mediatek/keyguard/ext/IOperatorSIMString;->getOperatorSIMString(Ljava/lang/String;ILcom/mediatek/keyguard/ext/IOperatorSIMString$SIMChangedTag;Landroid/content/Context;)Ljava/lang/String;

    move-result-object v3

    .line 335
    const-string v4, "KeyguardSimPinPukMeView"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "resetState() - mSecurityMessageDisplay.setMessage = "

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v4, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 336
    iget-object v4, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    invoke-interface {v4, v3}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(Ljava/lang/CharSequence;)V

    .line 339
    return-void
.end method

.method public setPhoneId(I)V
    .locals 3
    .param p1, "phoneId"    # I

    .line 243
    iput p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    .line 244
    const-string v0, "KeyguardSimPinPukMeView"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setPhoneId="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 246
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->resetState()V

    .line 250
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCardDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_1

    .line 251
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCardDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 252
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCardDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V

    .line 254
    :cond_0
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSimCardDialog:Landroid/app/AlertDialog;

    .line 256
    :cond_1
    return-void
.end method

.method protected shouldLockout(J)Z
    .locals 1
    .param p1, "deadline"    # J

    .line 382
    const/4 v0, 0x0

    return v0
.end method

.method public startAppearAnimation()V
    .locals 0

    .line 1246
    return-void
.end method

.method public startDisappearAnimation(Ljava/lang/Runnable;)Z
    .locals 1
    .param p1, "finishRunnable"    # Ljava/lang/Runnable;

    .line 1250
    const/4 v0, 0x0

    return v0
.end method

.method protected verifyPasswordAndUnlock()V
    .locals 4

    .line 747
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v0}, Lcom/android/keyguard/PasswordTextView;->getText()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v0

    .line 750
    .local v0, "entry":Ljava/lang/String;
    const/4 v1, 0x0

    invoke-direct {p0, v0, v1}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->validatePin(Ljava/lang/String;Z)Z

    move-result v1

    const/4 v2, 0x1

    if-nez v1, :cond_2

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v3, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    .line 751
    invoke-virtual {v1, v3}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v1

    sget-object v3, Lcom/android/internal/telephony/IccCardConstants$State;->PIN_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-eq v1, v3, :cond_0

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v3, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    .line 752
    invoke-virtual {v1, v3}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v1

    sget-object v3, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v1, v3, :cond_2

    .line 753
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isMediatekSimMeLockSupport()Z

    move-result v1

    if-eqz v1, :cond_2

    .line 756
    :cond_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    iget v3, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPhoneId:I

    invoke-virtual {v1, v3}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getSimStateOfPhoneId(I)Lcom/android/internal/telephony/IccCardConstants$State;

    move-result-object v1

    sget-object v3, Lcom/android/internal/telephony/IccCardConstants$State;->PIN_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    if-ne v1, v3, :cond_1

    .line 758
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    const v3, 0x7f110320

    invoke-interface {v1, v3}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(I)V

    goto :goto_0

    .line 761
    :cond_1
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mSecurityMessageDisplay:Lcom/android/keyguard/SecurityMessageDisplay;

    const v3, 0x7f1102de

    invoke-interface {v1, v3}, Lcom/android/keyguard/SecurityMessageDisplay;->setMessage(I)V

    .line 764
    :goto_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v1, v2, v2}, Lcom/android/keyguard/PasswordTextView;->reset(ZZ)V

    .line 765
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v1, v2}, Lcom/android/keyguard/PasswordTextView;->setEnabled(Z)V

    .line 766
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mCallback:Lcom/android/keyguard/KeyguardSecurityCallback;

    invoke-interface {v1}, Lcom/android/keyguard/KeyguardSecurityCallback;->userActivity()V

    .line 768
    return-void

    .line 770
    :cond_2
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->mPasswordEntry:Lcom/android/keyguard/PasswordTextView;

    invoke-virtual {v1, v2}, Lcom/android/keyguard/PasswordTextView;->setEnabled(Z)V

    .line 771
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;->dealWithPinOrPukUnlock()V

    .line 772
    return-void
.end method
