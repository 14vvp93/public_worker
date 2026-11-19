.class public final Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;
.super Landroid/content/BroadcastReceiver;
.source "HNetworkUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/HNetworkUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "NetworkChangedReceiver"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$LazyHolder;
    }
.end annotation


# instance fields
.field private final mListeners:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;",
            ">;"
        }
    .end annotation
.end field

.field private mType:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;


# direct methods
.method static bridge synthetic -$$Nest$fgetmListeners(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)Ljava/util/Set;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->mListeners:Ljava/util/Set;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fgetmType(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)Lcom/hcn/common/misc/HNetworkUtils$NetworkType;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->mType:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fputmType(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;Lcom/hcn/common/misc/HNetworkUtils$NetworkType;)V
    .locals 0

    iput-object p1, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->mType:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-void
.end method

.method static bridge synthetic -$$Nest$smgetInstance()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;
    .locals 1

    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->getInstance()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    move-result-object v0

    return-object v0
.end method

.method public constructor <init>()V
    .locals 1

    .line 937
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    .line 943
    new-instance v0, Ljava/util/HashSet;

    invoke-direct {v0}, Ljava/util/HashSet;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->mListeners:Ljava/util/Set;

    return-void
.end method

.method private static getInstance()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;
    .locals 1

    .line 939
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$LazyHolder;->-$$Nest$sfgetINSTANCE()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    move-result-object v0

    return-object v0
.end method


# virtual methods
.method isRegistered(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)Z
    .locals 1

    if-nez p1, :cond_0

    const/4 p1, 0x0

    return p1

    .line 971
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->mListeners:Ljava/util/Set;

    invoke-interface {v0, p1}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result p1

    return p1
.end method

.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 2

    .line 993
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string p2, "android.net.conn.CONNECTIVITY_CHANGE"

    invoke-virtual {p2, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    .line 996
    new-instance p1, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$3;

    invoke-direct {p1, p0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$3;-><init>(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;)V

    const-wide/16 v0, 0x3e8

    invoke-static {p1, v0, v1}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThreadDelayed(Ljava/lang/Runnable;J)V

    :cond_0
    return-void
.end method

.method registerListener(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V
    .locals 1

    if-nez p1, :cond_0

    return-void

    .line 951
    :cond_0
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$1;

    invoke-direct {v0, p0, p1}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$1;-><init>(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method unregisterListener(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V
    .locals 1

    if-nez p1, :cond_0

    return-void

    .line 979
    :cond_0
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;

    invoke-direct {v0, p0, p1}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver$2;-><init>(Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method
