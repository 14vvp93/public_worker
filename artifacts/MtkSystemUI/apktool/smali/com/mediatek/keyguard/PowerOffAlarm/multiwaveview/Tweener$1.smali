.class Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener$1;
.super Landroid/animation/AnimatorListenerAdapter;
.source "Tweener.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .line 141
    invoke-direct {p0}, Landroid/animation/AnimatorListenerAdapter;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationCancel(Landroid/animation/Animator;)V
    .locals 0
    .param p1, "animation"    # Landroid/animation/Animator;

    .line 150
    invoke-static {p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->access$000(Landroid/animation/Animator;)V

    .line 151
    return-void
.end method

.method public onAnimationEnd(Landroid/animation/Animator;)V
    .locals 0
    .param p1, "animation"    # Landroid/animation/Animator;

    .line 145
    invoke-static {p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->access$000(Landroid/animation/Animator;)V

    .line 146
    return-void
.end method
