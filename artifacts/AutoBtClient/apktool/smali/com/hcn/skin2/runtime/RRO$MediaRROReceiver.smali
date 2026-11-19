.class final Lcom/hcn/skin2/runtime/RRO$MediaRROReceiver;
.super Landroid/content/BroadcastReceiver;
.source "RRO.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/skin2/runtime/RRO;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "MediaRROReceiver"
.end annotation


# instance fields
.field private final mOwnerRef:Ljava/lang/ref/Reference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/Reference<",
            "Lcom/hcn/skin2/runtime/RRO;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/hcn/skin2/runtime/RRO;)V
    .locals 1

    .line 240
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    .line 241
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/hcn/skin2/runtime/RRO$MediaRROReceiver;->mOwnerRef:Ljava/lang/ref/Reference;

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    .line 246
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    .line 247
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p2

    if-eqz p2, :cond_0

    return-void

    .line 252
    :cond_0
    iget-object p2, p0, Lcom/hcn/skin2/runtime/RRO$MediaRROReceiver;->mOwnerRef:Ljava/lang/ref/Reference;

    invoke-virtual {p2}, Ljava/lang/ref/Reference;->get()Ljava/lang/Object;

    move-result-object p2

    check-cast p2, Lcom/hcn/skin2/runtime/RRO;

    .line 253
    invoke-static {p2}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    return-void

    :cond_1
    const-string v0, "android.intent.action.OVERLAY_CHANGED"

    .line 257
    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    .line 258
    invoke-static {p2}, Lcom/hcn/skin2/runtime/RRO;->access$000(Lcom/hcn/skin2/runtime/RRO;)Ljava/lang/ref/Reference;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/ref/Reference;->get()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/content/Context;

    .line 259
    invoke-static {p1}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    return-void

    .line 263
    :cond_2
    invoke-static {p2, p1}, Lcom/hcn/skin2/runtime/RRO;->access$100(Lcom/hcn/skin2/runtime/RRO;Landroid/content/Context;)V

    :cond_3
    return-void
.end method
