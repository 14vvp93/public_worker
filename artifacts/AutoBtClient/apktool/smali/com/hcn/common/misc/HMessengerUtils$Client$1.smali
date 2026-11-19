.class Lcom/hcn/common/misc/HMessengerUtils$Client$1;
.super Landroid/os/Handler;
.source "HMessengerUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/HMessengerUtils$Client;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/misc/HMessengerUtils$Client;


# direct methods
.method constructor <init>(Lcom/hcn/common/misc/HMessengerUtils$Client;)V
    .locals 0

    .line 152
    iput-object p1, p0, Lcom/hcn/common/misc/HMessengerUtils$Client$1;->this$0:Lcom/hcn/common/misc/HMessengerUtils$Client;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2

    .line 156
    invoke-virtual {p1}, Landroid/os/Message;->getData()Landroid/os/Bundle;

    move-result-object p1

    .line 157
    const-class v0, Lcom/hcn/common/misc/HMessengerUtils;

    invoke-virtual {v0}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/os/Bundle;->setClassLoader(Ljava/lang/ClassLoader;)V

    const-string v0, "MESSENGER_UTILS"

    .line 158
    invoke-virtual {p1, v0}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 160
    invoke-static {}, Lcom/hcn/common/misc/HMessengerUtils;->-$$Nest$sfgetsubscribers()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/misc/HMessengerUtils$MessageCallback;

    if-eqz v0, :cond_0

    .line 162
    invoke-interface {v0, p1}, Lcom/hcn/common/misc/HMessengerUtils$MessageCallback;->messageCall(Landroid/os/Bundle;)V

    :cond_0
    return-void
.end method
