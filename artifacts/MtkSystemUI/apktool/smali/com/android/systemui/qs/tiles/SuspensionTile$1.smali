.class Lcom/android/systemui/qs/tiles/SuspensionTile$1;
.super Landroid/os/Handler;
.source "SuspensionTile.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/qs/tiles/SuspensionTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/qs/tiles/SuspensionTile;


# direct methods
.method constructor <init>(Lcom/android/systemui/qs/tiles/SuspensionTile;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/qs/tiles/SuspensionTile;

    .line 90
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/SuspensionTile$1;->this$0:Lcom/android/systemui/qs/tiles/SuspensionTile;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2
    .param p1, "msg"    # Landroid/os/Message;

    .line 92
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-eq v0, v1, :cond_0

    goto :goto_0

    .line 94
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/SuspensionTile$1;->this$0:Lcom/android/systemui/qs/tiles/SuspensionTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/SuspensionTile;->access$000(Lcom/android/systemui/qs/tiles/SuspensionTile;)V

    .line 97
    :goto_0
    return-void
.end method
