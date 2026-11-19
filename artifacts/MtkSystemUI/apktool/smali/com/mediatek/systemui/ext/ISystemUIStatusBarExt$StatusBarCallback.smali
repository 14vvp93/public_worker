.class public interface abstract Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt$StatusBarCallback;
.super Ljava/lang/Object;
.source "ISystemUIStatusBarExt.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x609
    name = "StatusBarCallback"
.end annotation


# virtual methods
.method public setSystemIcon(Ljava/lang/String;ILjava/lang/CharSequence;Z)V
    .locals 0
    .param p1, "slot"    # Ljava/lang/String;
    .param p2, "resourceId"    # I
    .param p3, "contentDescription"    # Ljava/lang/CharSequence;
    .param p4, "isVisible"    # Z

    .line 354
    return-void
.end method
