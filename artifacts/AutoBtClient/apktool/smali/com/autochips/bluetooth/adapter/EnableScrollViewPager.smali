.class public Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;
.super Landroidx/viewpager/widget/ViewPager;
.source "EnableScrollViewPager.java"


# instance fields
.field private isCanScroll:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0

    .line 14
    invoke-direct {p0, p1}, Landroidx/viewpager/widget/ViewPager;-><init>(Landroid/content/Context;)V

    const/4 p1, 0x0

    .line 11
    iput-boolean p1, p0, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->isCanScroll:Z

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    .line 18
    invoke-direct {p0, p1, p2}, Landroidx/viewpager/widget/ViewPager;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    const/4 p1, 0x0

    .line 11
    iput-boolean p1, p0, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->isCanScroll:Z

    return-void
.end method


# virtual methods
.method public onInterceptTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 1

    .line 57
    iget-boolean v0, p0, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->isCanScroll:Z

    if-eqz v0, :cond_0

    .line 58
    invoke-super {p0, p1}, Landroidx/viewpager/widget/ViewPager;->onInterceptTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p1

    return p1

    :cond_0
    const/4 p1, 0x0

    return p1
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 1

    .line 34
    iget-boolean v0, p0, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->isCanScroll:Z

    if-eqz v0, :cond_0

    .line 35
    invoke-super {p0, p1}, Landroidx/viewpager/widget/ViewPager;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p1

    return p1

    :cond_0
    const/4 p1, 0x0

    return p1
.end method

.method public scrollTo(II)V
    .locals 0

    .line 28
    invoke-super {p0, p1, p2}, Landroidx/viewpager/widget/ViewPager;->scrollTo(II)V

    return-void
.end method

.method public setCurrentItem(I)V
    .locals 0

    .line 51
    invoke-super {p0, p1}, Landroidx/viewpager/widget/ViewPager;->setCurrentItem(I)V

    return-void
.end method

.method public setCurrentItem(IZ)V
    .locals 0

    .line 45
    invoke-super {p0, p1, p2}, Landroidx/viewpager/widget/ViewPager;->setCurrentItem(IZ)V

    return-void
.end method

.method public setScanScroll(Z)Z
    .locals 0

    .line 22
    iput-boolean p1, p0, Lcom/autochips/bluetooth/adapter/EnableScrollViewPager;->isCanScroll:Z

    return p1
.end method
