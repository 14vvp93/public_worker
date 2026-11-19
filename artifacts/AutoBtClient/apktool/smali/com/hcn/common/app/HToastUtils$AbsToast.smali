.class abstract Lcom/hcn/common/app/HToastUtils$AbsToast;
.super Ljava/lang/Object;
.source "HToastUtils.java"

# interfaces
.implements Lcom/hcn/common/app/HToastUtils$IToast;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/app/HToastUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x408
    name = "AbsToast"
.end annotation


# instance fields
.field protected mToast:Landroid/widget/Toast;

.field protected mToastUtils:Lcom/hcn/common/app/HToastUtils;

.field protected mToastView:Landroid/view/View;


# direct methods
.method constructor <init>(Lcom/hcn/common/app/HToastUtils;)V
    .locals 3

    .line 808
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 809
    new-instance v0, Landroid/widget/Toast;

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/widget/Toast;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToast:Landroid/widget/Toast;

    .line 810
    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    .line 812
    invoke-static {p1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmGravity(Lcom/hcn/common/app/HToastUtils;)I

    move-result p1

    const/4 v0, -0x1

    if-ne p1, v0, :cond_0

    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {p1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmXOffset(Lcom/hcn/common/app/HToastUtils;)I

    move-result p1

    if-ne p1, v0, :cond_0

    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {p1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmYOffset(Lcom/hcn/common/app/HToastUtils;)I

    move-result p1

    if-eq p1, v0, :cond_1

    .line 813
    :cond_0
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToast:Landroid/widget/Toast;

    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v0}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmGravity(Lcom/hcn/common/app/HToastUtils;)I

    move-result v0

    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmXOffset(Lcom/hcn/common/app/HToastUtils;)I

    move-result v1

    iget-object v2, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v2}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmYOffset(Lcom/hcn/common/app/HToastUtils;)I

    move-result v2

    invoke-virtual {p1, v0, v1, v2}, Landroid/widget/Toast;->setGravity(III)V

    :cond_1
    return-void
.end method

.method private processRtlIfNeed()V
    .locals 1

    .line 850
    invoke-static {}, Lcom/hcn/common/widget/HViewUtils;->isLayoutRtl()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, -0x1

    .line 851
    invoke-virtual {p0, v0}, Lcom/hcn/common/app/HToastUtils$AbsToast;->getToastViewSnapshot(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/hcn/common/app/HToastUtils$AbsToast;->setToastView(Landroid/view/View;)V

    :cond_0
    return-void
.end method

.method private setBg(Landroid/widget/TextView;)V
    .locals 5

    .line 856
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v0}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmBgResource(Lcom/hcn/common/app/HToastUtils;)I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, -0x1

    if-eq v0, v2, :cond_0

    .line 857
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastView:Landroid/view/View;

    iget-object v2, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v2}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmBgResource(Lcom/hcn/common/app/HToastUtils;)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->setBackgroundResource(I)V

    .line 858
    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setBackgroundColor(I)V

    goto :goto_0

    .line 859
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v0}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmBgColor(Lcom/hcn/common/app/HToastUtils;)I

    move-result v0

    const v2, -0x1000001

    if-eq v0, v2, :cond_4

    .line 860
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 861
    invoke-virtual {p1}, Landroid/widget/TextView;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v2

    if-eqz v0, :cond_1

    if-eqz v2, :cond_1

    .line 863
    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    new-instance v2, Landroid/graphics/PorterDuffColorFilter;

    iget-object v3, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v3}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmBgColor(Lcom/hcn/common/app/HToastUtils;)I

    move-result v3

    sget-object v4, Landroid/graphics/PorterDuff$Mode;->SRC_IN:Landroid/graphics/PorterDuff$Mode;

    invoke-direct {v2, v3, v4}, Landroid/graphics/PorterDuffColorFilter;-><init>(ILandroid/graphics/PorterDuff$Mode;)V

    invoke-virtual {v0, v2}, Landroid/graphics/drawable/Drawable;->setColorFilter(Landroid/graphics/ColorFilter;)V

    .line 864
    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setBackgroundColor(I)V

    goto :goto_0

    :cond_1
    if-eqz v0, :cond_2

    .line 866
    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object p1

    new-instance v0, Landroid/graphics/PorterDuffColorFilter;

    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmBgColor(Lcom/hcn/common/app/HToastUtils;)I

    move-result v1

    sget-object v2, Landroid/graphics/PorterDuff$Mode;->SRC_IN:Landroid/graphics/PorterDuff$Mode;

    invoke-direct {v0, v1, v2}, Landroid/graphics/PorterDuffColorFilter;-><init>(ILandroid/graphics/PorterDuff$Mode;)V

    invoke-virtual {p1, v0}, Landroid/graphics/drawable/Drawable;->setColorFilter(Landroid/graphics/ColorFilter;)V

    goto :goto_0

    :cond_2
    if-eqz v2, :cond_3

    .line 868
    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object p1

    new-instance v0, Landroid/graphics/PorterDuffColorFilter;

    iget-object v1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmBgColor(Lcom/hcn/common/app/HToastUtils;)I

    move-result v1

    sget-object v2, Landroid/graphics/PorterDuff$Mode;->SRC_IN:Landroid/graphics/PorterDuff$Mode;

    invoke-direct {v0, v1, v2}, Landroid/graphics/PorterDuffColorFilter;-><init>(ILandroid/graphics/PorterDuff$Mode;)V

    invoke-virtual {p1, v0}, Landroid/graphics/drawable/Drawable;->setColorFilter(Landroid/graphics/ColorFilter;)V

    goto :goto_0

    .line 870
    :cond_3
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastView:Landroid/view/View;

    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v0}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmBgColor(Lcom/hcn/common/app/HToastUtils;)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->setBackgroundColor(I)V

    :cond_4
    :goto_0
    return-void
.end method


# virtual methods
.method public cancel()V
    .locals 1

    .line 878
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToast:Landroid/widget/Toast;

    if-eqz v0, :cond_0

    .line 879
    invoke-virtual {v0}, Landroid/widget/Toast;->cancel()V

    :cond_0
    const/4 v0, 0x0

    .line 881
    iput-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToast:Landroid/widget/Toast;

    .line 882
    iput-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastView:Landroid/view/View;

    return-void
.end method

.method getToastViewSnapshot(I)Landroid/view/View;
    .locals 4

    .line 886
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastView:Landroid/view/View;

    invoke-static {v0}, Lcom/hcn/common/utils/HImageUtils;->view2Bitmap(Landroid/view/View;)Landroid/graphics/Bitmap;

    move-result-object v0

    .line 887
    new-instance v1, Landroid/widget/ImageView;

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    .line 888
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "TAG_TOAST"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v1, p1}, Landroid/widget/ImageView;->setTag(Ljava/lang/Object;)V

    .line 889
    invoke-virtual {v1, v0}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    return-object v1
.end method

.method public setToastView(Landroid/view/View;)V
    .locals 1

    .line 819
    iput-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastView:Landroid/view/View;

    .line 820
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {v0, p1}, Landroid/widget/Toast;->setView(Landroid/view/View;)V

    return-void
.end method

.method public setToastView(Ljava/lang/CharSequence;)V
    .locals 2

    .line 825
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {v0, p1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$mtryApplyUtilsToastView(Lcom/hcn/common/app/HToastUtils;Ljava/lang/CharSequence;)Landroid/view/View;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 827
    invoke-virtual {p0, v0}, Lcom/hcn/common/app/HToastUtils$AbsToast;->setToastView(Landroid/view/View;)V

    .line 828
    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils$AbsToast;->processRtlIfNeed()V

    return-void

    .line 832
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToast:Landroid/widget/Toast;

    invoke-virtual {v0}, Landroid/widget/Toast;->getView()Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastView:Landroid/view/View;

    const v1, 0x102000b

    if-eqz v0, :cond_1

    .line 833
    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    if-nez v0, :cond_2

    .line 834
    :cond_1
    sget v0, Lcom/hcn/common/R$layout;->utils_toast_view:I

    invoke-static {v0}, Lcom/hcn/common/widget/HViewUtils;->layoutId2View(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/hcn/common/app/HToastUtils$AbsToast;->setToastView(Landroid/view/View;)V

    .line 837
    :cond_2
    iget-object v0, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastView:Landroid/view/View;

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 838
    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 839
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {p1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmTextColor(Lcom/hcn/common/app/HToastUtils;)I

    move-result p1

    const v1, -0x1000001

    if-eq p1, v1, :cond_3

    .line 840
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {p1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmTextColor(Lcom/hcn/common/app/HToastUtils;)I

    move-result p1

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setTextColor(I)V

    .line 842
    :cond_3
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {p1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmTextSize(Lcom/hcn/common/app/HToastUtils;)I

    move-result p1

    const/4 v1, -0x1

    if-eq p1, v1, :cond_4

    .line 843
    iget-object p1, p0, Lcom/hcn/common/app/HToastUtils$AbsToast;->mToastUtils:Lcom/hcn/common/app/HToastUtils;

    invoke-static {p1}, Lcom/hcn/common/app/HToastUtils;->-$$Nest$fgetmTextSize(Lcom/hcn/common/app/HToastUtils;)I

    move-result p1

    int-to-float p1, p1

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setTextSize(F)V

    .line 845
    :cond_4
    invoke-direct {p0, v0}, Lcom/hcn/common/app/HToastUtils$AbsToast;->setBg(Landroid/widget/TextView;)V

    .line 846
    invoke-direct {p0}, Lcom/hcn/common/app/HToastUtils$AbsToast;->processRtlIfNeed()V

    return-void
.end method
