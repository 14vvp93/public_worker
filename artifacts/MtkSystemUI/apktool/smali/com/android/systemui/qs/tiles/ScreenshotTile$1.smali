.class Lcom/android/systemui/qs/tiles/ScreenshotTile$1;
.super Landroid/os/Handler;
.source "ScreenshotTile.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/qs/tiles/ScreenshotTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/qs/tiles/ScreenshotTile;


# direct methods
.method constructor <init>(Lcom/android/systemui/qs/tiles/ScreenshotTile;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/qs/tiles/ScreenshotTile;

    .line 83
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/ScreenshotTile$1;->this$0:Lcom/android/systemui/qs/tiles/ScreenshotTile;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 13
    .param p1, "msg"    # Landroid/os/Message;

    .line 85
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-eq v0, v1, :cond_0

    goto :goto_0

    .line 87
    :cond_0
    invoke-static {}, Landroid/os/SystemClock;->uptimeMillis()J

    move-result-wide v0

    .line 88
    .local v0, "now":J
    new-instance v10, Landroid/view/KeyEvent;

    const/4 v7, 0x0

    const/16 v8, 0x78

    const/4 v9, 0x0

    move-object v2, v10

    move-wide v3, v0

    move-wide v5, v0

    invoke-direct/range {v2 .. v9}, Landroid/view/KeyEvent;-><init>(JJIII)V

    .line 89
    .local v10, "down":Landroid/view/KeyEvent;
    invoke-static {}, Landroid/hardware/input/InputManager;->getInstance()Landroid/hardware/input/InputManager;

    move-result-object v2

    const/4 v11, 0x0

    invoke-virtual {v2, v10, v11}, Landroid/hardware/input/InputManager;->injectInputEvent(Landroid/view/InputEvent;I)Z

    .line 90
    new-instance v12, Landroid/view/KeyEvent;

    const/4 v7, 0x1

    move-object v2, v12

    invoke-direct/range {v2 .. v9}, Landroid/view/KeyEvent;-><init>(JJIII)V

    .line 91
    .local v2, "up":Landroid/view/KeyEvent;
    invoke-static {}, Landroid/hardware/input/InputManager;->getInstance()Landroid/hardware/input/InputManager;

    move-result-object v3

    invoke-virtual {v3, v2, v11}, Landroid/hardware/input/InputManager;->injectInputEvent(Landroid/view/InputEvent;I)Z

    .line 94
    .end local v0    # "now":J
    .end local v2    # "up":Landroid/view/KeyEvent;
    .end local v10    # "down":Landroid/view/KeyEvent;
    :goto_0
    return-void
.end method
