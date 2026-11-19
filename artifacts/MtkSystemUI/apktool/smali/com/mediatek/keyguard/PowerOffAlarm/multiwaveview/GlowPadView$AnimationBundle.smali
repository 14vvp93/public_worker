.class Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;
.super Ljava/util/ArrayList;
.source "GlowPadView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "AnimationBundle"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/util/ArrayList<",
        "Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;",
        ">;"
    }
.end annotation


# static fields
.field private static final serialVersionUID:J = -0x57b2878d90ed8b98L


# instance fields
.field private mSuspended:Z

.field final synthetic this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;


# direct methods
.method private constructor <init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V
    .locals 0

    .line 132
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$1;)V
    .locals 0
    .param p1, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
    .param p2, "x1"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$1;

    .line 132
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V

    return-void
.end method


# virtual methods
.method public cancel()V
    .locals 4

    .line 146
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->size()I

    move-result v0

    .line 147
    .local v0, "count":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    if-ge v1, v0, :cond_0

    .line 148
    invoke-virtual {p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    .line 149
    .local v2, "anim":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    iget-object v3, v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v3}, Landroid/animation/ObjectAnimator;->cancel()V

    .line 147
    .end local v2    # "anim":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 151
    .end local v1    # "i":I
    :cond_0
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->clear()V

    .line 152
    return-void
.end method

.method public start()V
    .locals 4

    .line 137
    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->mSuspended:Z

    if-eqz v0, :cond_0

    return-void

    .line 138
    :cond_0
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->size()I

    move-result v0

    .line 139
    .local v0, "count":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    if-ge v1, v0, :cond_1

    .line 140
    invoke-virtual {p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    .line 141
    .local v2, "anim":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    iget-object v3, v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v3}, Landroid/animation/ObjectAnimator;->start()V

    .line 139
    .end local v2    # "anim":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 143
    .end local v1    # "i":I
    :cond_1
    return-void
.end method

.method public stop()V
    .locals 4

    .line 155
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->size()I

    move-result v0

    .line 156
    .local v0, "count":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    if-ge v1, v0, :cond_0

    .line 157
    invoke-virtual {p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    .line 158
    .local v2, "anim":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    iget-object v3, v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v3}, Landroid/animation/ObjectAnimator;->end()V

    .line 156
    .end local v2    # "anim":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 160
    .end local v1    # "i":I
    :cond_0
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->clear()V

    .line 161
    return-void
.end method
