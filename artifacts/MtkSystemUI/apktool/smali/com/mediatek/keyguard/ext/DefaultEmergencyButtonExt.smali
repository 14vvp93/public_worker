.class public Lcom/mediatek/keyguard/ext/DefaultEmergencyButtonExt;
.super Ljava/lang/Object;
.source "DefaultEmergencyButtonExt.java"

# interfaces
.implements Lcom/mediatek/keyguard/ext/IEmergencyButtonExt;


# static fields
.field private static final TAG:Ljava/lang/String; = "DefaultEmergencyButtonExt"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 47
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public customizeEmergencyIntent(Landroid/content/Intent;I)V
    .locals 0
    .param p1, "intent"    # Landroid/content/Intent;
    .param p2, "slotId"    # I

    .line 63
    return-void
.end method

.method public setEmergencyButtonVisibility(Landroid/view/View;F)V
    .locals 0
    .param p1, "eccButtonView"    # Landroid/view/View;
    .param p2, "alpha"    # F

    .line 72
    return-void
.end method

.method public showEccByServiceState([ZI)Z
    .locals 4
    .param p1, "isServiceSupportEcc"    # [Z
    .param p2, "slotId"    # I

    .line 52
    array-length v0, p1

    .line 53
    .local v0, "simSlotCount":I
    const/4 v1, 0x0

    move v2, v1

    .local v2, "i":I
    :goto_0
    if-ge v2, v0, :cond_1

    .line 54
    aget-boolean v3, p1, v2

    if-eqz v3, :cond_0

    .line 55
    const/4 v1, 0x1

    return v1

    .line 53
    :cond_0
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 58
    .end local v2    # "i":I
    :cond_1
    return v1
.end method

.method public showEccInNonSecureUnlock()Z
    .locals 1

    .line 67
    const/4 v0, 0x0

    return v0
.end method
