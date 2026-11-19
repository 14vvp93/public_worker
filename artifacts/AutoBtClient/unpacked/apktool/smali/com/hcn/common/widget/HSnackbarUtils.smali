.class public Lcom/hcn/common/widget/HSnackbarUtils;
.super Ljava/lang/Object;
.source "HSnackbarUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/widget/HSnackbarUtils$Duration;
    }
.end annotation


# static fields
.field private static final COLOR_DEFAULT:I = -0x1000001

.field private static final COLOR_ERROR:I = -0x10000

.field private static final COLOR_MESSAGE:I = -0x1

.field private static final COLOR_SUCCESS:I = -0xd44a00

.field private static final COLOR_WARNING:I = -0x3f00

.field public static final LENGTH_INDEFINITE:I = -0x2

.field public static final LENGTH_LONG:I = 0x0

.field public static final LENGTH_SHORT:I = -0x1

.field private static sWeakSnackbar:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/google/android/material/snackbar/Snackbar;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field private actionListener:Landroid/view/View$OnClickListener;

.field private actionText:Ljava/lang/CharSequence;

.field private actionTextColor:I

.field private alpha:F

.field private bgColor:I

.field private bgResource:I

.field private bottomMargin:I

.field private duration:I

.field private leftDrawable:Landroid/graphics/drawable/Drawable;

.field private message:Ljava/lang/CharSequence;

.field private messageColor:I

.field private messageSize:I

.field private radius:F

.field private view:Landroid/view/View;


# direct methods
.method private constructor <init>(Landroid/view/View;)V
    .locals 0

    .line 74
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 75
    invoke-direct {p0}, Lcom/hcn/common/widget/HSnackbarUtils;->setDefault()V

    .line 76
    iput-object p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->view:Landroid/view/View;

    return-void
.end method

.method public static addView(ILandroid/view/ViewGroup$LayoutParams;)V
    .locals 3

    .line 470
    invoke-static {}, Lcom/hcn/common/widget/HSnackbarUtils;->getView()Landroid/view/View;

    move-result-object v0

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 472
    invoke-virtual {v0, v1, v1, v1, v1}, Landroid/view/View;->setPadding(IIII)V

    .line 473
    move-object v1, v0

    check-cast v1, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;

    .line 474
    invoke-virtual {v0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    const/4 v2, 0x0

    invoke-virtual {v0, p0, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p0

    const/4 v0, -0x1

    .line 475
    invoke-virtual {v1, p0, v0, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->addView(Landroid/view/View;ILandroid/view/ViewGroup$LayoutParams;)V

    :cond_0
    return-void
.end method

.method public static addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    .locals 2

    .line 488
    invoke-static {}, Lcom/hcn/common/widget/HSnackbarUtils;->getView()Landroid/view/View;

    move-result-object v0

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 490
    invoke-virtual {v0, v1, v1, v1, v1}, Landroid/view/View;->setPadding(IIII)V

    .line 491
    check-cast v0, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;

    .line 492
    invoke-virtual {v0, p0, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    :cond_0
    return-void
.end method

.method public static dismiss()V
    .locals 1

    .line 441
    sget-object v0, Lcom/hcn/common/widget/HSnackbarUtils;->sWeakSnackbar:Ljava/lang/ref/WeakReference;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 442
    sget-object v0, Lcom/hcn/common/widget/HSnackbarUtils;->sWeakSnackbar:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/google/android/material/snackbar/Snackbar;

    invoke-virtual {v0}, Lcom/google/android/material/snackbar/Snackbar;->dismiss()V

    const/4 v0, 0x0

    .line 443
    sput-object v0, Lcom/hcn/common/widget/HSnackbarUtils;->sWeakSnackbar:Ljava/lang/ref/WeakReference;

    :cond_0
    return-void
.end method

.method private static findSuitableParentCopyFromSnackbar(Landroid/view/View;)Landroid/view/ViewGroup;
    .locals 3

    const/4 v0, 0x0

    move-object v1, v0

    .line 500
    :cond_0
    instance-of v2, p0, Landroidx/coordinatorlayout/widget/CoordinatorLayout;

    if-eqz v2, :cond_1

    .line 501
    check-cast p0, Landroid/view/ViewGroup;

    return-object p0

    .line 504
    :cond_1
    instance-of v2, p0, Landroid/widget/FrameLayout;

    if-eqz v2, :cond_3

    .line 505
    invoke-virtual {p0}, Landroid/view/View;->getId()I

    move-result v1

    const v2, 0x1020002

    if-ne v1, v2, :cond_2

    .line 506
    check-cast p0, Landroid/view/ViewGroup;

    return-object p0

    .line 509
    :cond_2
    move-object v1, p0

    check-cast v1, Landroid/view/ViewGroup;

    :cond_3
    if-eqz p0, :cond_5

    .line 513
    invoke-virtual {p0}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    move-result-object p0

    .line 514
    instance-of v2, p0, Landroid/view/View;

    if-eqz v2, :cond_4

    check-cast p0, Landroid/view/View;

    goto :goto_0

    :cond_4
    move-object p0, v0

    :cond_5
    :goto_0
    if-nez p0, :cond_0

    return-object v1
.end method

.method private getRadiusDrawable(Landroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/GradientDrawable;
    .locals 1

    .line 161
    instance-of v0, p1, Landroid/graphics/drawable/GradientDrawable;

    if-eqz v0, :cond_0

    .line 162
    check-cast p1, Landroid/graphics/drawable/GradientDrawable;

    goto :goto_0

    .line 163
    :cond_0
    instance-of v0, p1, Landroid/graphics/drawable/ColorDrawable;

    if-eqz v0, :cond_1

    .line 164
    check-cast p1, Landroid/graphics/drawable/ColorDrawable;

    invoke-virtual {p1}, Landroid/graphics/drawable/ColorDrawable;->getColor()I

    move-result p1

    .line 165
    new-instance v0, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v0}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 166
    invoke-virtual {v0, p1}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    move-object p1, v0

    goto :goto_0

    :cond_1
    const/4 p1, 0x0

    :goto_0
    return-object p1
.end method

.method public static getView()Landroid/view/View;
    .locals 1

    .line 453
    sget-object v0, Lcom/hcn/common/widget/HSnackbarUtils;->sWeakSnackbar:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/google/android/material/snackbar/Snackbar;

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 458
    :cond_0
    invoke-virtual {v0}, Lcom/google/android/material/snackbar/Snackbar;->getView()Landroid/view/View;

    move-result-object v0

    return-object v0
.end method

.method private setDefault()V
    .locals 4

    const-string v0, ""

    .line 80
    iput-object v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->message:Ljava/lang/CharSequence;

    const v1, -0x1000001

    .line 81
    iput v1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageColor:I

    const/4 v2, 0x0

    .line 82
    iput v2, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageSize:I

    .line 83
    iput v1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgColor:I

    const/4 v3, -0x1

    .line 84
    iput v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgResource:I

    .line 85
    iput v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->duration:I

    .line 86
    iput-object v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionText:Ljava/lang/CharSequence;

    .line 87
    iput v1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionTextColor:I

    .line 88
    iput v2, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bottomMargin:I

    const/4 v0, 0x0

    .line 89
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->radius:F

    const/high16 v0, 0x3f800000    # 1.0f

    .line 90
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->alpha:F

    const/4 v0, 0x0

    .line 91
    iput-object v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->leftDrawable:Landroid/graphics/drawable/Drawable;

    return-void
.end method

.method public static with(Landroid/view/View;)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 1

    .line 101
    new-instance v0, Lcom/hcn/common/widget/HSnackbarUtils;

    invoke-direct {v0, p0}, Lcom/hcn/common/widget/HSnackbarUtils;-><init>(Landroid/view/View;)V

    return-object v0
.end method


# virtual methods
.method public setAction(Ljava/lang/CharSequence;ILandroid/view/View$OnClickListener;)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 234
    iput-object p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionText:Ljava/lang/CharSequence;

    .line 235
    iput p2, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionTextColor:I

    .line 236
    iput-object p3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionListener:Landroid/view/View$OnClickListener;

    return-object p0
.end method

.method public setAction(Ljava/lang/CharSequence;Landroid/view/View$OnClickListener;)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 1

    const v0, -0x1000001

    .line 219
    invoke-virtual {p0, p1, v0, p2}, Lcom/hcn/common/widget/HSnackbarUtils;->setAction(Ljava/lang/CharSequence;ILandroid/view/View$OnClickListener;)Lcom/hcn/common/widget/HSnackbarUtils;

    move-result-object p1

    return-object p1
.end method

.method public setAlpha(F)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 2

    const/high16 v0, 0x3f800000    # 1.0f

    cmpl-float v1, p1, v0

    if-ltz v1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 190
    invoke-static {p1, v0}, Ljava/lang/Math;->max(FF)F

    move-result v0

    :goto_0
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->alpha:F

    return-object p0
.end method

.method public setBgColor(I)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 144
    iput p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgColor:I

    return-object p0
.end method

.method public setBgResource(I)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 155
    iput p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgResource:I

    return-object p0
.end method

.method public setBottomMargin(I)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 257
    iput p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bottomMargin:I

    return-object p0
.end method

.method public setDuration(I)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 206
    iput p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->duration:I

    return-object p0
.end method

.method public setLeftDrawable(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 247
    iput-object p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->leftDrawable:Landroid/graphics/drawable/Drawable;

    return-object p0
.end method

.method public setMessage(Ljava/lang/CharSequence;)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 111
    iput-object p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->message:Ljava/lang/CharSequence;

    return-object p0
.end method

.method public setMessageColor(I)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 122
    iput p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageColor:I

    return-object p0
.end method

.method public setMessageSize(I)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 133
    iput p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageSize:I

    return-object p0
.end method

.method public setRadius(F)Lcom/hcn/common/widget/HSnackbarUtils;
    .locals 0

    .line 179
    iput p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->radius:F

    return-object p0
.end method

.method public show()Lcom/google/android/material/snackbar/Snackbar;
    .locals 1

    const/4 v0, 0x0

    .line 265
    invoke-virtual {p0, v0}, Lcom/hcn/common/widget/HSnackbarUtils;->show(Z)Lcom/google/android/material/snackbar/Snackbar;

    move-result-object v0

    return-object v0
.end method

.method public show(Z)Lcom/google/android/material/snackbar/Snackbar;
    .locals 10

    .line 275
    iget-object v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->view:Landroid/view/View;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return-object v1

    :cond_0
    const/high16 v2, 0x43340000    # 180.0f

    const/4 v3, -0x1

    if-eqz p1, :cond_3

    .line 281
    invoke-static {v0}, Lcom/hcn/common/widget/HSnackbarUtils;->findSuitableParentCopyFromSnackbar(Landroid/view/View;)Landroid/view/ViewGroup;

    move-result-object v4

    const-string v5, "topSnackBarCoordinatorLayout"

    .line 282
    invoke-virtual {v4, v5}, Landroid/view/ViewGroup;->findViewWithTag(Ljava/lang/Object;)Landroid/view/View;

    move-result-object v6

    if-nez v6, :cond_2

    .line 284
    new-instance v6, Landroidx/coordinatorlayout/widget/CoordinatorLayout;

    invoke-virtual {v0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-direct {v6, v0}, Landroidx/coordinatorlayout/widget/CoordinatorLayout;-><init>(Landroid/content/Context;)V

    .line 285
    invoke-virtual {v6, v5}, Landroidx/coordinatorlayout/widget/CoordinatorLayout;->setTag(Ljava/lang/Object;)V

    .line 286
    invoke-virtual {v6, v2}, Landroidx/coordinatorlayout/widget/CoordinatorLayout;->setRotation(F)V

    .line 287
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v5, 0x15

    if-lt v0, v5, :cond_1

    const/high16 v0, 0x42c80000    # 100.0f

    .line 289
    invoke-virtual {v6, v0}, Landroidx/coordinatorlayout/widget/CoordinatorLayout;->setElevation(F)V

    .line 291
    :cond_1
    invoke-virtual {v4, v6, v3, v3}, Landroid/view/ViewGroup;->addView(Landroid/view/View;II)V

    :cond_2
    move-object v0, v6

    .line 298
    :cond_3
    iget v4, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageColor:I

    const v5, -0x1000001

    const/4 v6, 0x0

    if-eq v4, v5, :cond_4

    .line 299
    new-instance v4, Landroid/text/SpannableString;

    iget-object v7, p0, Lcom/hcn/common/widget/HSnackbarUtils;->message:Ljava/lang/CharSequence;

    invoke-direct {v4, v7}, Landroid/text/SpannableString;-><init>(Ljava/lang/CharSequence;)V

    .line 300
    new-instance v7, Landroid/text/style/ForegroundColorSpan;

    iget v8, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageColor:I

    invoke-direct {v7, v8}, Landroid/text/style/ForegroundColorSpan;-><init>(I)V

    .line 302
    invoke-virtual {v4}, Landroid/text/SpannableString;->length()I

    move-result v8

    const/16 v9, 0x21

    .line 301
    invoke-virtual {v4, v7, v6, v8, v9}, Landroid/text/SpannableString;->setSpan(Ljava/lang/Object;III)V

    .line 304
    new-instance v7, Ljava/lang/ref/WeakReference;

    iget v8, p0, Lcom/hcn/common/widget/HSnackbarUtils;->duration:I

    invoke-static {v0, v4, v8}, Lcom/google/android/material/snackbar/Snackbar;->make(Landroid/view/View;Ljava/lang/CharSequence;I)Lcom/google/android/material/snackbar/Snackbar;

    move-result-object v0

    invoke-direct {v7, v0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    sput-object v7, Lcom/hcn/common/widget/HSnackbarUtils;->sWeakSnackbar:Ljava/lang/ref/WeakReference;

    goto :goto_0

    .line 306
    :cond_4
    new-instance v4, Ljava/lang/ref/WeakReference;

    iget-object v7, p0, Lcom/hcn/common/widget/HSnackbarUtils;->message:Ljava/lang/CharSequence;

    iget v8, p0, Lcom/hcn/common/widget/HSnackbarUtils;->duration:I

    invoke-static {v0, v7, v8}, Lcom/google/android/material/snackbar/Snackbar;->make(Landroid/view/View;Ljava/lang/CharSequence;I)Lcom/google/android/material/snackbar/Snackbar;

    move-result-object v0

    invoke-direct {v4, v0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    sput-object v4, Lcom/hcn/common/widget/HSnackbarUtils;->sWeakSnackbar:Ljava/lang/ref/WeakReference;

    .line 309
    :goto_0
    sget-object v0, Lcom/hcn/common/widget/HSnackbarUtils;->sWeakSnackbar:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/google/android/material/snackbar/Snackbar;

    .line 310
    invoke-virtual {v0}, Lcom/google/android/material/snackbar/Snackbar;->getView()Landroid/view/View;

    move-result-object v4

    check-cast v4, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;

    if-eqz p1, :cond_5

    move p1, v6

    .line 312
    :goto_1
    invoke-virtual {v4}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->getChildCount()I

    move-result v7

    if-ge p1, v7, :cond_5

    .line 313
    invoke-virtual {v4, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->getChildAt(I)Landroid/view/View;

    move-result-object v7

    .line 314
    invoke-virtual {v7, v2}, Landroid/view/View;->setRotation(F)V

    add-int/lit8 p1, p1, 0x1

    goto :goto_1

    .line 318
    :cond_5
    iget p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgResource:I

    if-eq p1, v3, :cond_6

    .line 319
    invoke-virtual {v4, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->setBackgroundResource(I)V

    goto :goto_2

    .line 320
    :cond_6
    iget p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgColor:I

    if-eq p1, v5, :cond_7

    .line 321
    invoke-virtual {v4, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->setBackgroundColor(I)V

    .line 324
    :cond_7
    :goto_2
    invoke-virtual {v4}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/hcn/common/widget/HSnackbarUtils;->getRadiusDrawable(Landroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/GradientDrawable;

    move-result-object p1

    const/4 v2, 0x0

    if-eqz p1, :cond_9

    .line 326
    iget v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->radius:F

    cmpg-float v7, v3, v2

    if-gez v7, :cond_8

    const/high16 v3, 0x41400000    # 12.0f

    :cond_8
    iput v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->radius:F

    .line 327
    invoke-virtual {p1, v3}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 328
    invoke-virtual {v4, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 331
    :cond_9
    iget p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->alpha:F

    invoke-virtual {v4, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->setAlpha(F)V

    .line 333
    iget p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bottomMargin:I

    if-eqz p1, :cond_a

    .line 335
    invoke-virtual {v4}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    check-cast p1, Landroid/view/ViewGroup$MarginLayoutParams;

    .line 336
    iget v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bottomMargin:I

    iput v3, p1, Landroid/view/ViewGroup$MarginLayoutParams;->bottomMargin:I

    .line 337
    invoke-virtual {v4, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 340
    :cond_a
    iget-object p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionText:Ljava/lang/CharSequence;

    invoke-interface {p1}, Ljava/lang/CharSequence;->length()I

    move-result p1

    if-lez p1, :cond_c

    iget-object p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionListener:Landroid/view/View$OnClickListener;

    if-eqz p1, :cond_c

    .line 341
    iget p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionTextColor:I

    if-eq p1, v5, :cond_b

    .line 342
    invoke-virtual {v0, p1}, Lcom/google/android/material/snackbar/Snackbar;->setActionTextColor(I)Lcom/google/android/material/snackbar/Snackbar;

    .line 344
    :cond_b
    iget-object p1, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionText:Ljava/lang/CharSequence;

    iget-object v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionListener:Landroid/view/View$OnClickListener;

    invoke-virtual {v0, p1, v3}, Lcom/google/android/material/snackbar/Snackbar;->setAction(Ljava/lang/CharSequence;Landroid/view/View$OnClickListener;)Lcom/google/android/material/snackbar/Snackbar;

    .line 348
    :cond_c
    invoke-virtual {v4, v6}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->getChildAt(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/google/android/material/snackbar/SnackbarContentLayout;

    .line 349
    invoke-virtual {p1}, Lcom/google/android/material/snackbar/SnackbarContentLayout;->getMessageView()Landroid/widget/TextView;

    move-result-object p1

    if-eqz p1, :cond_e

    .line 351
    iget v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageSize:I

    if-eqz v3, :cond_d

    int-to-float v3, v3

    .line 352
    invoke-virtual {p1, v3}, Landroid/widget/TextView;->setTextSize(F)V

    .line 355
    :cond_d
    iget-object v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->leftDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v3, :cond_e

    .line 357
    invoke-virtual {p1}, Landroid/widget/TextView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v3

    check-cast v3, Landroid/widget/LinearLayout$LayoutParams;

    .line 358
    new-instance v5, Landroid/widget/LinearLayout$LayoutParams;

    iget v7, v3, Landroid/widget/LinearLayout$LayoutParams;->width:I

    iget v3, v3, Landroid/widget/LinearLayout$LayoutParams;->height:I

    invoke-direct {v5, v7, v3, v2}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    .line 360
    invoke-virtual {p1, v5}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 361
    invoke-virtual {p1}, Landroid/widget/TextView;->getPaddingLeft()I

    move-result v2

    invoke-virtual {p1, v2}, Landroid/widget/TextView;->setCompoundDrawablePadding(I)V

    .line 362
    invoke-virtual {p1}, Landroid/widget/TextView;->getTextSize()F

    move-result v2

    float-to-int v2, v2

    .line 363
    iget-object v3, p0, Lcom/hcn/common/widget/HSnackbarUtils;->leftDrawable:Landroid/graphics/drawable/Drawable;

    add-int/lit8 v2, v2, 0x3

    invoke-virtual {v3, v6, v6, v2, v2}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 365
    iget-object v2, p0, Lcom/hcn/common/widget/HSnackbarUtils;->leftDrawable:Landroid/graphics/drawable/Drawable;

    invoke-virtual {p1, v2, v1, v1, v1}, Landroid/widget/TextView;->setCompoundDrawables(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;)V

    .line 366
    new-instance p1, Landroid/widget/LinearLayout$LayoutParams;

    const/high16 v1, 0x3f800000    # 1.0f

    const/4 v2, -0x2

    invoke-direct {p1, v2, v2, v1}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    .line 372
    new-instance v1, Landroid/widget/Space;

    invoke-virtual {v4}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/Space;-><init>(Landroid/content/Context;)V

    const/4 v2, 0x1

    invoke-virtual {v4, v1, v2, p1}, Lcom/google/android/material/snackbar/Snackbar$SnackbarLayout;->addView(Landroid/view/View;ILandroid/view/ViewGroup$LayoutParams;)V

    .line 376
    :cond_e
    invoke-virtual {v0}, Lcom/google/android/material/snackbar/Snackbar;->show()V

    return-object v0
.end method

.method public showError()V
    .locals 1

    const/4 v0, 0x0

    .line 422
    invoke-virtual {p0, v0}, Lcom/hcn/common/widget/HSnackbarUtils;->showError(Z)V

    return-void
.end method

.method public showError(Z)V
    .locals 1

    const/high16 v0, -0x10000

    .line 431
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgColor:I

    const/4 v0, -0x1

    .line 432
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageColor:I

    .line 433
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionTextColor:I

    .line 434
    invoke-virtual {p0, p1}, Lcom/hcn/common/widget/HSnackbarUtils;->show(Z)Lcom/google/android/material/snackbar/Snackbar;

    return-void
.end method

.method public showSuccess()V
    .locals 1

    const/4 v0, 0x0

    .line 384
    invoke-virtual {p0, v0}, Lcom/hcn/common/widget/HSnackbarUtils;->showSuccess(Z)V

    return-void
.end method

.method public showSuccess(Z)V
    .locals 1

    const v0, -0xd44a00

    .line 393
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgColor:I

    const/4 v0, -0x1

    .line 394
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageColor:I

    .line 395
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionTextColor:I

    .line 396
    invoke-virtual {p0, p1}, Lcom/hcn/common/widget/HSnackbarUtils;->show(Z)Lcom/google/android/material/snackbar/Snackbar;

    return-void
.end method

.method public showWarning()V
    .locals 1

    const/4 v0, 0x0

    .line 403
    invoke-virtual {p0, v0}, Lcom/hcn/common/widget/HSnackbarUtils;->showWarning(Z)V

    return-void
.end method

.method public showWarning(Z)V
    .locals 1

    const/16 v0, -0x3f00

    .line 412
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->bgColor:I

    const/4 v0, -0x1

    .line 413
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->messageColor:I

    .line 414
    iput v0, p0, Lcom/hcn/common/widget/HSnackbarUtils;->actionTextColor:I

    .line 415
    invoke-virtual {p0, p1}, Lcom/hcn/common/widget/HSnackbarUtils;->show(Z)Lcom/google/android/material/snackbar/Snackbar;

    return-void
.end method
