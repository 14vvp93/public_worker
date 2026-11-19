.class final Lcom/hcn/common/app/HToastUtils$ActivityToast;
.super Lcom/hcn/common/app/HToastUtils$AbsToast;
.source "HToastUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/app/HToastUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "ActivityToast"
.end annotation


# static fields
.field private static sShowingIndex:I


# instance fields
.field private iToast:Lcom/hcn/common/app/HToastUtils$IToast;

.field private mActivityLifecycleCallbacks:Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;


# direct methods
.method static bridge synthetic -$$Nest$misShowing(Lcom/hcn/common/app/HToastUtils$ActivityToast;)Z
    .locals 0

    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->isShowing()Z

    move-result p0

    return p0
.end method

.method static bridge synthetic -$$Nest$mshowWithActivityView(Lcom/hcn/common/app/HToastUtils$ActivityToast;Landroid/app/Activity;IZ)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->showWithActivityView(Landroid/app/Activity;IZ)V

    return-void
.end method

.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method constructor <init>(Lcom/hcn/common/app/HToastUtils;)V
    .locals 0

    .line 674
    invoke-direct {p0, p1}, Lcom/hcn/common/app/HToastUtils$AbsToast;-><init>(Lcom/hcn/common/app/HToastUtils;)V

    return-void
.end method

.method private isShowing()Z
    .locals 1

    .line 798
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mActivityLifecycleCallbacks:Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private registerLifecycleCallback()V
    .locals 2

    .line 780
    sget v0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->sShowingIndex:I

    .line 781
    new-instance v1, Lcom/hcn/common/app/HToastUtils$ActivityToast$2;

    invoke-direct {v1, p0, v0}, Lcom/hcn/common/app/HToastUtils$ActivityToast$2;-><init>(Lcom/hcn/common/app/HToastUtils$ActivityToast;I)V

    iput-object v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mActivityLifecycleCallbacks:Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;

    .line 789
    invoke-static {v1}, Lcom/hcn/common/app/HActivityUtils;->addActivityLifecycleCallbacks(Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;)V

    return-void
.end method

.method private showSystemToast(I)Lcom/hcn/common/app/HToastUtils$IToast;
    .locals 2

    .line 745
    new-instance v0, Lcom/hcn/common/app/HToastUtils$SystemToast;

    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-direct {v0, v1}, Lcom/hcn/common/app/HToastUtils$SystemToast;-><init>(Lcom/hcn/common/app/HToastUtils;)V

    .line 746
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToast:Landroid/widget/Toast;

    iput-object v1, v0, Lcom/hcn/common/app/HToastUtils$SystemToast;->mToast:Landroid/widget/Toast;

    .line 747
    invoke-virtual {v0, p1}, Lcom/hcn/common/app/HToastUtils$SystemToast;->show(I)V

    return-object v0
.end method

.method private showWithActivityView(Landroid/app/Activity;IZ)V
    .locals 3

    .line 760
    invoke-virtual {p1}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 762
    invoke-virtual {p1}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/view/ViewGroup;

    .line 763
    new-instance v0, Landroid/widget/FrameLayout$LayoutParams;

    const/4 v1, -0x2

    invoke-direct {v0, v1, v1}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 766
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {v1}, Landroid/widget/Toast;->getGravity()I

    move-result v1

    iput v1, v0, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    .line 767
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {v1}, Landroid/widget/Toast;->getYOffset()I

    move-result v1

    invoke-static {}, Lcom/hcn/common/widget/HBarUtils;->getNavBarHeight()I

    move-result v2

    add-int/2addr v1, v2

    iput v1, v0, Landroid/widget/FrameLayout$LayoutParams;->bottomMargin:I

    .line 768
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {v1}, Landroid/widget/Toast;->getYOffset()I

    move-result v1

    invoke-static {}, Lcom/hcn/common/widget/HBarUtils;->getStatusBarHeight()I

    move-result v2

    add-int/2addr v1, v2

    iput v1, v0, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 769
    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {v1}, Landroid/widget/Toast;->getXOffset()I

    move-result v1

    iput v1, v0, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    .line 770
    invoke-virtual {p0, p2}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->getToastViewSnapshot(I)Landroid/view/View;

    move-result-object p2

    if-eqz p3, :cond_0

    const/4 p3, 0x0

    .line 772
    invoke-virtual {p2, p3}, Landroid/view/View;->setAlpha(F)V

    .line 773
    invoke-virtual {p2}, Landroid/view/View;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object p3

    const/high16 v1, 0x3f800000    # 1.0f

    invoke-virtual {p3, v1}, Landroid/view/ViewPropertyAnimator;->alpha(F)Landroid/view/ViewPropertyAnimator;

    move-result-object p3

    const-wide/16 v1, 0xc8

    invoke-virtual {p3, v1, v2}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    move-result-object p3

    invoke-virtual {p3}, Landroid/view/ViewPropertyAnimator;->start()V

    .line 775
    :cond_0
    invoke-virtual {p1, p2, v0}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    :cond_1
    return-void
.end method

.method private showWithActivityWindow(Landroid/app/Activity;I)Lcom/hcn/common/app/HToastUtils$IToast;
    .locals 3

    .line 752
    new-instance v0, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;

    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-virtual {p1}, Landroid/app/Activity;->getWindowManager()Landroid/view/WindowManager;

    move-result-object p1

    const/16 v2, 0x63

    invoke-direct {v0, v1, p1, v2}, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;-><init>(Lcom/hcn/common/app/HToastUtils;Landroid/view/WindowManager;I)V

    const/4 p1, -0x1

    .line 753
    invoke-virtual {p0, p1}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->getToastViewSnapshot(I)Landroid/view/View;

    move-result-object p1

    iput-object p1, v0, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;->mToastView:Landroid/view/View;

    .line 754
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToast:Landroid/widget/Toast;

    iput-object p1, v0, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;->mToast:Landroid/widget/Toast;

    .line 755
    invoke-virtual {v0, p2}, Lcom/hcn/common/app/HToastUtils$WindowManagerToast;->show(I)V

    return-object v0
.end method

.method private unregisterLifecycleCallback()V
    .locals 1

    .line 793
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mActivityLifecycleCallbacks:Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;

    invoke-static {v0}, Lcom/hcn/common/app/HActivityUtils;->removeActivityLifecycleCallbacks(Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;)V

    const/4 v0, 0x0

    .line 794
    iput-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mActivityLifecycleCallbacks:Lcom/hcn/common/app/HActivityUtils$ActivityLifecycleCallbacks;

    return-void
.end method


# virtual methods
.method public cancel()V
    .locals 4

    .line 717
    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 718
    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->unregisterLifecycleCallback()V

    .line 719
    sget-object v0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->INSTANCE:Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;

    invoke-virtual {v0}, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->getActivityList()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :catch_0
    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/app/Activity;

    .line 720
    invoke-static {v1}, Lcom/hcn/common/app/HActivityUtils;->isActivityAlive(Landroid/app/Activity;)Z

    move-result v2

    if-nez v2, :cond_1

    goto :goto_0

    .line 723
    :cond_1
    invoke-virtual {v1}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v1

    if-eqz v1, :cond_0

    .line 725
    invoke-virtual {v1}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/view/ViewGroup;

    .line 726
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "TAG_TOAST"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget v3, Lcom/hcn/common/app/HToastUtils$ActivityToast;->sShowingIndex:I

    add-int/lit8 v3, v3, -0x1

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/view/ViewGroup;->findViewWithTag(Ljava/lang/Object;)Landroid/view/View;

    move-result-object v2

    if-eqz v2, :cond_0

    .line 729
    :try_start_0
    invoke-virtual {v1, v2}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 736
    :cond_2
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->iToast:Lcom/hcn/common/app/HToastUtils$IToast;

    if-eqz v0, :cond_3

    .line 737
    invoke-interface {v0}, Lcom/hcn/common/app/HToastUtils$IToast;->cancel()V

    const/4 v0, 0x0

    .line 738
    iput-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->iToast:Lcom/hcn/common/app/HToastUtils$IToast;

    .line 741
    :cond_3
    invoke-super {p0}, Lcom/hcn/common/app/HToastUtils$AbsToast;->cancel()V

    return-void
.end method

.method public show(I)V
    .locals 5

    .line 679
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->mToast:Landroid/widget/Toast;

    if-nez v0, :cond_0

    return-void

    .line 682
    :cond_0
    invoke-static {}, Lcom/hcn/common/app/HAppUtils;->isAppForeground()Z

    move-result v0

    if-nez v0, :cond_1

    .line 684
    invoke-direct {p0, p1}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->showSystemToast(I)Lcom/hcn/common/app/HToastUtils$IToast;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->iToast:Lcom/hcn/common/app/HToastUtils$IToast;

    return-void

    :cond_1
    const/4 v0, 0x0

    .line 688
    sget-object v1, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->INSTANCE:Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;

    invoke-virtual {v1}, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->getActivityList()Ljava/util/List;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    const/4 v3, 0x1

    if-eqz v2, :cond_4

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/app/Activity;

    .line 689
    invoke-static {v2}, Lcom/hcn/common/app/HActivityUtils;->isActivityAlive(Landroid/app/Activity;)Z

    move-result v4

    if-nez v4, :cond_2

    goto :goto_0

    :cond_2
    if-nez v0, :cond_3

    .line 694
    invoke-direct {p0, v2, p1}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->showWithActivityWindow(Landroid/app/Activity;I)Lcom/hcn/common/app/HToastUtils$IToast;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->iToast:Lcom/hcn/common/app/HToastUtils$IToast;

    move v0, v3

    goto :goto_0

    .line 696
    :cond_3
    sget v4, Lcom/hcn/common/app/HToastUtils$ActivityToast;->sShowingIndex:I

    invoke-direct {p0, v2, v4, v3}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->showWithActivityView(Landroid/app/Activity;IZ)V

    goto :goto_0

    :cond_4
    if-eqz v0, :cond_6

    .line 700
    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->registerLifecycleCallback()V

    .line 701
    new-instance v0, Lcom/hcn/common/app/HToastUtils$ActivityToast$1;

    invoke-direct {v0, p0}, Lcom/hcn/common/app/HToastUtils$ActivityToast$1;-><init>(Lcom/hcn/common/app/HToastUtils$ActivityToast;)V

    if-nez p1, :cond_5

    const-wide/16 v1, 0x7d0

    goto :goto_1

    :cond_5
    const-wide/16 v1, 0xdac

    :goto_1
    invoke-static {v0, v1, v2}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThreadDelayed(Ljava/lang/Runnable;J)V

    .line 708
    sget p1, Lcom/hcn/common/app/HToastUtils$ActivityToast;->sShowingIndex:I

    add-int/2addr p1, v3

    sput p1, Lcom/hcn/common/app/HToastUtils$ActivityToast;->sShowingIndex:I

    goto :goto_2

    .line 711
    :cond_6
    invoke-direct {p0, p1}, Lcom/hcn/common/app/HToastUtils$ActivityToast;->showSystemToast(I)Lcom/hcn/common/app/HToastUtils$IToast;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils$ActivityToast;->iToast:Lcom/hcn/common/app/HToastUtils$IToast;

    :goto_2
    return-void
.end method
