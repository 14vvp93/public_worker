.class public Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;
.super Ljava/lang/Object;
.source "KeyguardDialogManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;,
        Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;
    }
.end annotation


# static fields
.field private static sInstance:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;


# instance fields
.field private final mContext:Landroid/content/Context;

.field private mDialogSequenceManager:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

.field private mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;


# direct methods
.method private constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 58
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 59
    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->mContext:Landroid/content/Context;

    .line 61
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->mDialogSequenceManager:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    .line 63
    invoke-static {p1}, Lcom/android/keyguard/KeyguardUpdateMonitor;->getInstance(Landroid/content/Context;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 64
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    .line 27
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$100(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)Lcom/android/keyguard/KeyguardUpdateMonitor;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    .line 27
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->mUpdateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    return-object v0
.end method

.method public static getInstance(Landroid/content/Context;)Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;
    .locals 1
    .param p0, "context"    # Landroid/content/Context;

    .line 67
    sget-object v0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->sInstance:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    if-nez v0, :cond_0

    .line 68
    new-instance v0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;-><init>(Landroid/content/Context;)V

    sput-object v0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->sInstance:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    .line 70
    :cond_0
    sget-object v0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->sInstance:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    return-object v0
.end method


# virtual methods
.method public reportDialogClose()V
    .locals 1

    .line 96
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->mDialogSequenceManager:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->reportDialogClose()V

    .line 97
    return-void
.end method

.method public requestShowDialog(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;)V
    .locals 1
    .param p1, "callback"    # Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;

    .line 86
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->mDialogSequenceManager:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    invoke-virtual {v0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->requestShowDialog(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;)V

    .line 90
    return-void
.end method
