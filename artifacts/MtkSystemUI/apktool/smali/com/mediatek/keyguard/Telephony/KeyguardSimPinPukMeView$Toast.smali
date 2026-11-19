.class public Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;
.super Ljava/lang/Object;
.source "KeyguardSimPinPukMeView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "Toast"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;
    }
.end annotation


# instance fields
.field final mContext:Landroid/content/Context;

.field mGravity:I

.field final mHandler:Landroid/os/Handler;

.field private mService:Landroid/app/INotificationManager;

.field final mTN:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;

.field mView:Landroid/view/View;

.field mY:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;

    .line 1071
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 1064
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mHandler:Landroid/os/Handler;

    .line 1067
    const/16 v0, 0x51

    iput v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mGravity:I

    .line 1072
    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mContext:Landroid/content/Context;

    .line 1073
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mTN:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;

    .line 1074
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 1075
    const v1, 0x10501d5

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    move-result v0

    iput v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mY:I

    .line 1076
    return-void
.end method

.method private getService()Landroid/app/INotificationManager;
    .locals 1

    .line 1121
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mService:Landroid/app/INotificationManager;

    if-eqz v0, :cond_0

    .line 1122
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mService:Landroid/app/INotificationManager;

    return-object v0

    .line 1124
    :cond_0
    const-string v0, "notification"

    .line 1125
    invoke-static {v0}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v0

    invoke-static {v0}, Landroid/app/INotificationManager$Stub;->asInterface(Landroid/os/IBinder;)Landroid/app/INotificationManager;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mService:Landroid/app/INotificationManager;

    .line 1126
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mService:Landroid/app/INotificationManager;

    return-object v0
.end method

.method public static makeText(Landroid/content/Context;Ljava/lang/CharSequence;)Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;
    .locals 4
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "text"    # Ljava/lang/CharSequence;

    .line 1079
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;-><init>(Landroid/content/Context;)V

    .line 1081
    .local v0, "result":Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;
    const-string v1, "layout_inflater"

    .line 1082
    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/view/LayoutInflater;

    .line 1083
    .local v1, "inflate":Landroid/view/LayoutInflater;
    const v2, 0x1090116

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v2

    .line 1084
    .local v2, "v":Landroid/view/View;
    const v3, 0x102000b

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/TextView;

    .line 1085
    .local v3, "tv":Landroid/widget/TextView;
    invoke-virtual {v3, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1087
    iput-object v2, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    .line 1089
    return-object v0
.end method


# virtual methods
.method public show()V
    .locals 4

    .line 1096
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    if-eqz v0, :cond_0

    .line 1099
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->getService()Landroid/app/INotificationManager;

    move-result-object v0

    .line 1100
    .local v0, "service":Landroid/app/INotificationManager;
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    .line 1101
    .local v1, "pkg":Ljava/lang/String;
    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mTN:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;

    .line 1103
    .local v2, "tn":Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;
    const/4 v3, 0x0

    :try_start_0
    invoke-interface {v0, v1, v2, v3}, Landroid/app/INotificationManager;->enqueueToast(Ljava/lang/String;Landroid/app/ITransientNotification;I)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1106
    goto :goto_0

    .line 1104
    :catch_0
    move-exception v3

    .line 1107
    :goto_0
    return-void

    .line 1097
    .end local v0    # "service":Landroid/app/INotificationManager;
    .end local v1    # "pkg":Ljava/lang/String;
    .end local v2    # "tn":Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;
    :cond_0
    new-instance v0, Ljava/lang/RuntimeException;

    const-string v1, "setView must have been called"

    invoke-direct {v0, v1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw v0
.end method
