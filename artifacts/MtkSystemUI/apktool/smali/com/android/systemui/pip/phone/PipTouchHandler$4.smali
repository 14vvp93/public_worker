.class Lcom/android/systemui/pip/phone/PipTouchHandler$4;
.super Landroid/content/BroadcastReceiver;
.source "PipTouchHandler.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/pip/phone/PipTouchHandler;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;


# direct methods
.method constructor <init>(Lcom/android/systemui/pip/phone/PipTouchHandler;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/pip/phone/PipTouchHandler;

    .line 1007
    iput-object p1, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 5
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 1011
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 1012
    .local v0, "action":Ljava/lang/String;
    const-string v1, "com.android.systemui.action.PIP_CONTROL"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_8

    .line 1013
    const-string v1, "command"

    invoke-virtual {p2, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 1014
    .local v1, "key":Ljava/lang/String;
    const-string v2, "PipTouchHandler"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "onReceive command : "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1015
    const-string v2, "expand"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 1016
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    invoke-static {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler;->access$3000(Lcom/android/systemui/pip/phone/PipTouchHandler;)Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;->onPipExpand()V

    goto/16 :goto_0

    .line 1017
    :cond_0
    const-string v2, "dismiss"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 1018
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    invoke-static {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler;->access$3000(Lcom/android/systemui/pip/phone/PipTouchHandler;)Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;->onPipDismiss()V

    goto/16 :goto_0

    .line 1019
    :cond_1
    const-string v2, "bigger"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 1020
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    invoke-static {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler;->access$3000(Lcom/android/systemui/pip/phone/PipTouchHandler;)Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;->onPipBigger()V

    goto :goto_0

    .line 1021
    :cond_2
    const-string v2, "smaller"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 1022
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    invoke-static {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler;->access$3000(Lcom/android/systemui/pip/phone/PipTouchHandler;)Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;->onPipSmaller()V

    goto :goto_0

    .line 1023
    :cond_3
    const-string v2, "minimize"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_4

    .line 1024
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    invoke-static {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler;->access$3000(Lcom/android/systemui/pip/phone/PipTouchHandler;)Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;->onPipMinimize()V

    goto :goto_0

    .line 1025
    :cond_4
    const-string v2, "hidden"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_5

    .line 1026
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    invoke-static {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler;->access$3000(Lcom/android/systemui/pip/phone/PipTouchHandler;)Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;->onPipHidden()V

    goto :goto_0

    .line 1027
    :cond_5
    const-string v2, "show"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_6

    .line 1028
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    invoke-static {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler;->access$3000(Lcom/android/systemui/pip/phone/PipTouchHandler;)Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/systemui/pip/phone/PipTouchHandler$PipMenuListener;->onPipShow()V

    goto :goto_0

    .line 1029
    :cond_6
    const-string v2, "touchEnabled"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_7

    .line 1030
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Lcom/android/systemui/pip/phone/PipTouchHandler;->setTouchEnabled(Z)V

    goto :goto_0

    .line 1031
    :cond_7
    const-string v2, "touchDisabled"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_8

    .line 1032
    iget-object v2, p0, Lcom/android/systemui/pip/phone/PipTouchHandler$4;->this$0:Lcom/android/systemui/pip/phone/PipTouchHandler;

    const/4 v3, 0x0

    invoke-virtual {v2, v3}, Lcom/android/systemui/pip/phone/PipTouchHandler;->setTouchEnabled(Z)V

    .line 1035
    .end local v1    # "key":Ljava/lang/String;
    :cond_8
    :goto_0
    return-void
.end method
