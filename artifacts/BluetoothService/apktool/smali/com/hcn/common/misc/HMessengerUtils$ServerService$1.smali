.class Lcom/hcn/common/misc/HMessengerUtils$ServerService$1;
.super Landroid/os/Handler;
.source "HMessengerUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/HMessengerUtils$ServerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/misc/HMessengerUtils$ServerService;


# direct methods
.method constructor <init>(Lcom/hcn/common/misc/HMessengerUtils$ServerService;)V
    .locals 0

    .line 278
    iput-object p1, p0, Lcom/hcn/common/misc/HMessengerUtils$ServerService$1;->this$0:Lcom/hcn/common/misc/HMessengerUtils$ServerService;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2

    .line 281
    iget v0, p1, Landroid/os/Message;->what:I

    if-eqz v0, :cond_2

    const/4 v1, 0x1

    if-eq v0, v1, :cond_1

    const/4 v1, 0x2

    if-eq v0, v1, :cond_0

    .line 293
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    goto :goto_0

    .line 286
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/misc/HMessengerUtils$ServerService$1;->this$0:Lcom/hcn/common/misc/HMessengerUtils$ServerService;

    invoke-static {v0, p1}, Lcom/hcn/common/misc/HMessengerUtils$ServerService;->-$$Nest$msendMsg2Client(Lcom/hcn/common/misc/HMessengerUtils$ServerService;Landroid/os/Message;)V

    .line 287
    iget-object v0, p0, Lcom/hcn/common/misc/HMessengerUtils$ServerService$1;->this$0:Lcom/hcn/common/misc/HMessengerUtils$ServerService;

    invoke-static {v0, p1}, Lcom/hcn/common/misc/HMessengerUtils$ServerService;->-$$Nest$mconsumeServerProcessCallback(Lcom/hcn/common/misc/HMessengerUtils$ServerService;Landroid/os/Message;)V

    goto :goto_0

    .line 290
    :cond_1
    iget-object v0, p0, Lcom/hcn/common/misc/HMessengerUtils$ServerService$1;->this$0:Lcom/hcn/common/misc/HMessengerUtils$ServerService;

    invoke-static {v0}, Lcom/hcn/common/misc/HMessengerUtils$ServerService;->-$$Nest$fgetmClientMap(Lcom/hcn/common/misc/HMessengerUtils$ServerService;)Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v0

    iget p1, p1, Landroid/os/Message;->arg1:I

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    .line 283
    :cond_2
    iget-object v0, p0, Lcom/hcn/common/misc/HMessengerUtils$ServerService$1;->this$0:Lcom/hcn/common/misc/HMessengerUtils$ServerService;

    invoke-static {v0}, Lcom/hcn/common/misc/HMessengerUtils$ServerService;->-$$Nest$fgetmClientMap(Lcom/hcn/common/misc/HMessengerUtils$ServerService;)Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v0

    iget v1, p1, Landroid/os/Message;->arg1:I

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    iget-object p1, p1, Landroid/os/Message;->replyTo:Landroid/os/Messenger;

    invoke-virtual {v0, v1, p1}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :goto_0
    return-void
.end method
