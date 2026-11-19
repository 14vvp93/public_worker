.class public Lcom/mediatek/keyguard/ext/DefaultKeyguardUtilExt;
.super Ljava/lang/Object;
.source "DefaultKeyguardUtilExt.java"

# interfaces
.implements Lcom/mediatek/keyguard/ext/IKeyguardUtilExt;


# static fields
.field private static final TAG:Ljava/lang/String; = "DefaultKeyguardUtilExt"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 51
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public customizeCarrierTextGravity(Landroid/widget/TextView;)V
    .locals 0
    .param p1, "view"    # Landroid/widget/TextView;

    .line 77
    return-void
.end method

.method public customizePinPukLockView(ILandroid/widget/ImageView;Landroid/widget/TextView;)V
    .locals 0
    .param p1, "phoneId"    # I
    .param p2, "imageView"    # Landroid/widget/ImageView;
    .param p3, "textView"    # Landroid/widget/TextView;

    .line 68
    return-void
.end method

.method public lockImmediatelyWhenScreenTimeout()Z
    .locals 1

    .line 81
    const/4 v0, 0x0

    return v0
.end method

.method public showToastWhenUnlockPinPuk(Landroid/content/Context;I)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "simLockType"    # I

    .line 56
    return-void
.end method
