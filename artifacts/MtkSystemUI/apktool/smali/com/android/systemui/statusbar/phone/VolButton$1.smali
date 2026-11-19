.class Lcom/android/systemui/statusbar/phone/VolButton$1;
.super Ljava/lang/Object;
.source "VolButton.java"

# interfaces
.implements Landroid/view/View$OnLongClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/phone/VolButton;->init()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/phone/VolButton;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/phone/VolButton;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/phone/VolButton;

    .line 32
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/VolButton$1;->this$0:Lcom/android/systemui/statusbar/phone/VolButton;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onLongClick(Landroid/view/View;)Z
    .locals 4
    .param p1, "v"    # Landroid/view/View;

    .line 35
    new-instance v0, Ljava/lang/Thread;

    new-instance v1, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;

    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/VolButton$1;->this$0:Lcom/android/systemui/statusbar/phone/VolButton;

    const/4 v3, 0x0

    invoke-direct {v1, v2, v3}, Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;-><init>(Lcom/android/systemui/statusbar/phone/VolButton;Lcom/android/systemui/statusbar/phone/VolButton$1;)V

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    .line 36
    const/4 v0, 0x1

    return v0
.end method
