.class Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;
.super Landroid/animation/AnimatorListenerAdapter;
.source "GlowPadView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;


# direct methods
.method constructor <init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    .line 190
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-direct {p0}, Landroid/animation/AnimatorListenerAdapter;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationEnd(Landroid/animation/Animator;)V
    .locals 3
    .param p1, "animator"    # Landroid/animation/Animator;

    .line 192
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-static {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->access$500(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)I

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 193
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-static {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->access$500(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)I

    move-result v2

    invoke-static {v0, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->access$600(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;I)V

    .line 194
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-static {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->access$502(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;I)I

    .line 195
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-static {v0, v1, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->access$700(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;ZZ)V

    .line 197
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    invoke-static {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->access$802(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;Z)Z

    .line 198
    return-void
.end method
