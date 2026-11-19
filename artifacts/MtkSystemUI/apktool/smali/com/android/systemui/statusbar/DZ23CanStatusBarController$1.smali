.class Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;
.super Landroid/database/ContentObserver;
.source "DZ23CanStatusBarController.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->buildNormalObserver(Landroid/widget/ImageView;Ljava/lang/String;)Landroid/database/ContentObserver;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

.field final synthetic val$imageView:Landroid/widget/ImageView;

.field final synthetic val$target:Ljava/lang/String;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;Landroid/os/Handler;Landroid/widget/ImageView;Ljava/lang/String;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/DZ23CanStatusBarController;
    .param p2, "x0"    # Landroid/os/Handler;

    .line 118
    iput-object p1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    iput-object p3, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;->val$imageView:Landroid/widget/ImageView;

    iput-object p4, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;->val$target:Ljava/lang/String;

    invoke-direct {p0, p2}, Landroid/database/ContentObserver;-><init>(Landroid/os/Handler;)V

    return-void
.end method


# virtual methods
.method public onChange(Z)V
    .locals 3
    .param p1, "selfChange"    # Z

    .line 120
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->access$000(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)Ljava/lang/ref/WeakReference;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;->val$imageView:Landroid/widget/ImageView;

    if-nez v0, :cond_0

    goto :goto_2

    .line 121
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;->this$0:Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    invoke-static {v0}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->access$000(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)Ljava/lang/ref/WeakReference;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    iget-object v1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;->val$target:Ljava/lang/String;

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    goto :goto_0

    :cond_1
    move v1, v2

    :goto_0
    move v0, v1

    .line 122
    .local v0, "isShow":Z
    iget-object v1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;->val$imageView:Landroid/widget/ImageView;

    if-eqz v0, :cond_2

    goto :goto_1

    :cond_2
    const/4 v2, 0x4

    :goto_1
    invoke-virtual {v1, v2}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 123
    return-void

    .line 120
    .end local v0    # "isShow":Z
    :cond_3
    :goto_2
    return-void
.end method
