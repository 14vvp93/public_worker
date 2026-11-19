.class Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;
.super Landroid/database/ContentObserver;
.source "DZ23CanStatusBarController.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->buildSpeedObserver(Landroid/widget/TextView;)Landroid/database/ContentObserver;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

.field final synthetic val$tvSpeed:Landroid/widget/TextView;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;Landroid/os/Handler;Landroid/widget/TextView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/DZ23CanStatusBarController;
    .param p2, "x0"    # Landroid/os/Handler;

    .line 141
    iput-object p1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    iput-object p3, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;->val$tvSpeed:Landroid/widget/TextView;

    invoke-direct {p0, p2}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    return-void
.end method


# virtual methods
.method public onChange(Z)V
    .locals 3
    .param p1, "selfChange"    # Z

    .line 143
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->access$000(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)Ljava/lang/ref/WeakReference;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;->val$tvSpeed:Landroid/widget/TextView;

    if-nez v0, :cond_0

    goto :goto_1

    .line 144
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->access$000(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)Ljava/lang/ref/WeakReference;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "car_speed_value"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    .line 145
    .local v0, "speed":I
    const/16 v1, 0xff

    if-gt v0, v1, :cond_2

    if-gez v0, :cond_1

    goto :goto_0

    .line 146
    :cond_1
    iget-object v1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;->val$tvSpeed:Landroid/widget/TextView;

    invoke-static {v0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 147
    return-void

    .line 145
    :cond_2
    :goto_0
    return-void

    .line 143
    .end local v0    # "speed":I
    :cond_3
    :goto_1
    return-void
.end method
