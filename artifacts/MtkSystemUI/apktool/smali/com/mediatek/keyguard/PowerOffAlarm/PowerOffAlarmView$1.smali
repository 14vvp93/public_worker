.class Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$1;
.super Landroid/os/Handler;
.source "PowerOffAlarmView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;


# direct methods
.method constructor <init>(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    .line 84
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$1;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2
    .param p1, "msg"    # Landroid/os/Message;

    .line 87
    iget v0, p1, Landroid/os/Message;->what:I

    const/16 v1, 0x63

    if-eq v0, v1, :cond_1

    const/16 v1, 0x65

    if-eq v0, v1, :cond_0

    goto :goto_0

    .line 89
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$1;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    invoke-static {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->access$000(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)V

    .line 90
    goto :goto_0

    .line 92
    :cond_1
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$1;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    invoke-static {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->access$100(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)Landroid/widget/TextView;

    move-result-object v0

    if-eqz v0, :cond_2

    .line 93
    invoke-virtual {p1}, Landroid/os/Message;->getData()Landroid/os/Bundle;

    move-result-object v0

    const-string v1, "label"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 94
    .local v0, "titleStr":Ljava/lang/String;
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$1;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    invoke-static {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->access$100(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)Landroid/widget/TextView;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 95
    invoke-static {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->setAlarmTitle(Ljava/lang/String;)V

    .line 96
    .end local v0    # "titleStr":Ljava/lang/String;
    nop

    .line 101
    :cond_2
    :goto_0
    return-void
.end method
