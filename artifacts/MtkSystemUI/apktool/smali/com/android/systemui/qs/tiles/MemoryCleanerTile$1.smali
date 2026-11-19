.class Lcom/android/systemui/qs/tiles/MemoryCleanerTile$1;
.super Landroid/os/Handler;
.source "MemoryCleanerTile.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/qs/tiles/MemoryCleanerTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/qs/tiles/MemoryCleanerTile;


# direct methods
.method constructor <init>(Lcom/android/systemui/qs/tiles/MemoryCleanerTile;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    .line 98
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile$1;->this$0:Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 4
    .param p1, "msg"    # Landroid/os/Message;

    .line 100
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-eq v0, v1, :cond_0

    goto :goto_0

    .line 102
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile$1;->this$0:Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->access$000(Lcom/android/systemui/qs/tiles/MemoryCleanerTile;)V

    .line 103
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile$1;->this$0:Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    invoke-static {v0}, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->access$100(Lcom/android/systemui/qs/tiles/MemoryCleanerTile;)Landroid/content/Context;

    move-result-object v0

    iget-object v2, p0, Lcom/android/systemui/qs/tiles/MemoryCleanerTile$1;->this$0:Lcom/android/systemui/qs/tiles/MemoryCleanerTile;

    invoke-static {v2}, Lcom/android/systemui/qs/tiles/MemoryCleanerTile;->access$200(Lcom/android/systemui/qs/tiles/MemoryCleanerTile;)Landroid/content/Context;

    move-result-object v2

    const v3, 0x7f1104a1

    invoke-virtual {v2, v3}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 106
    :goto_0
    return-void
.end method
