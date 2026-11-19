.class Lcom/autochips/bluetooth/CrashHandler$1;
.super Ljava/lang/Thread;
.source "CrashHandler.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/CrashHandler;->handleException(Ljava/lang/Throwable;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/CrashHandler;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/CrashHandler;)V
    .locals 0

    .line 100
    iput-object p1, p0, Lcom/autochips/bluetooth/CrashHandler$1;->this$0:Lcom/autochips/bluetooth/CrashHandler;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 0

    .line 103
    invoke-static {}, Landroid/os/Looper;->prepare()V

    .line 104
    invoke-static {}, Landroid/os/Looper;->loop()V

    return-void
.end method
