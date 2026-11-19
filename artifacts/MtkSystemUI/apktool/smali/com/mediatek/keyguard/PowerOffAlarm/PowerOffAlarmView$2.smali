.class Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$2;
.super Landroid/content/BroadcastReceiver;
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

    .line 290
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$2;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 5
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 293
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 294
    .local v0, "action":Ljava/lang/String;
    const-string v1, "PowerOffAlarmView"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "receive action : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 295
    const-string v1, "update.power.off.alarm.label"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 296
    new-instance v1, Landroid/os/Message;

    invoke-direct {v1}, Landroid/os/Message;-><init>()V

    .line 297
    .local v1, "msg":Landroid/os/Message;
    const/16 v2, 0x63

    iput v2, v1, Landroid/os/Message;->what:I

    .line 298
    new-instance v2, Landroid/os/Bundle;

    invoke-direct {v2}, Landroid/os/Bundle;-><init>()V

    .line 299
    .local v2, "data":Landroid/os/Bundle;
    const-string v3, "label"

    const-string v4, "label"

    invoke-virtual {p2, v4}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v2, v3, v4}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 300
    invoke-virtual {v1, v2}, Landroid/os/Message;->setData(Landroid/os/Bundle;)V

    .line 301
    iget-object v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$2;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    invoke-static {v3}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->access$200(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)Landroid/os/Handler;

    move-result-object v3

    invoke-virtual {v3, v1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    .line 302
    .end local v1    # "msg":Landroid/os/Message;
    .end local v2    # "data":Landroid/os/Bundle;
    goto :goto_0

    :cond_0
    invoke-static {}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->isAlarmBoot()Z

    move-result v1

    if-eqz v1, :cond_1

    .line 303
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView$2;->this$0:Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;

    invoke-static {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;->access$300(Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmView;)V

    .line 305
    :cond_1
    :goto_0
    return-void
.end method
