.class Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;
.super Landroid/database/ContentObserver;
.source "DZ23CanStatusBarController.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->buildGearObserver(Landroid/widget/TextView;)Landroid/database/ContentObserver;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

.field final synthetic val$tvGear:Landroid/widget/TextView;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;Landroid/os/Handler;Landroid/widget/TextView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/DZ23CanStatusBarController;
    .param p2, "x0"    # Landroid/os/Handler;

    .line 129
    iput-object p1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    iput-object p3, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;->val$tvGear:Landroid/widget/TextView;

    invoke-direct {p0, p2}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    return-void
.end method


# virtual methods
.method public onChange(Z)V
    .locals 3
    .param p1, "selfChange"    # Z

    .line 131
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->access$000(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)Ljava/lang/ref/WeakReference;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;->val$tvGear:Landroid/widget/TextView;

    if-nez v0, :cond_0

    goto :goto_1

    .line 132
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->access$000(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)Ljava/lang/ref/WeakReference;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "car_manual"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    .line 133
    .local v0, "gearValue":I
    const/4 v1, 0x2

    if-gt v0, v1, :cond_2

    if-gez v0, :cond_1

    goto :goto_0

    .line 134
    :cond_1
    iget-object v1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;->val$tvGear:Landroid/widget/TextView;

    iget-object v2, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-static {v2}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->access$100(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)[Ljava/lang/String;

    move-result-object v2

    aget-object v2, v2, v0

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 135
    return-void

    .line 133
    :cond_2
    :goto_0
    return-void

    .line 131
    .end local v0    # "gearValue":I
    :cond_3
    :goto_1
    return-void
.end method
