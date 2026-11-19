.class public Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;
.super Landroidx/fragment/app/FragmentPagerAdapter;
.source "FragmentViewPaperAdapter.java"


# instance fields
.field currentposition:I

.field private fragments:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Landroidx/fragment/app/Fragment;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Landroidx/fragment/app/FragmentManager;Ljava/util/List;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroidx/fragment/app/FragmentManager;",
            "Ljava/util/List<",
            "Landroidx/fragment/app/Fragment;",
            ">;)V"
        }
    .end annotation

    .line 16
    invoke-direct {p0, p1}, Landroidx/fragment/app/FragmentPagerAdapter;-><init>(Landroidx/fragment/app/FragmentManager;)V

    const/4 p1, -0x1

    .line 12
    iput p1, p0, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;->currentposition:I

    .line 17
    iput-object p2, p0, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;->fragments:Ljava/util/List;

    return-void
.end method


# virtual methods
.method public destroyItem(Landroid/view/ViewGroup;ILjava/lang/Object;)V
    .locals 0

    return-void
.end method

.method public getCount()I
    .locals 1

    .line 40
    iget-object v0, p0, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;->fragments:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    return v0
.end method

.method public getCurrentPosition()I
    .locals 1

    .line 48
    iget v0, p0, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;->currentposition:I

    return v0
.end method

.method public getItem(I)Landroidx/fragment/app/Fragment;
    .locals 1

    .line 35
    iget-object v0, p0, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;->fragments:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroidx/fragment/app/Fragment;

    return-object p1
.end method

.method public getItemId(I)J
    .locals 2

    .line 23
    invoke-super {p0, p1}, Landroidx/fragment/app/FragmentPagerAdapter;->getItemId(I)J

    move-result-wide v0

    return-wide v0
.end method

.method public setCurrentPosition(I)V
    .locals 0

    .line 44
    iput p1, p0, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;->currentposition:I

    return-void
.end method

.method public setPrimaryItem(Landroid/view/ViewGroup;ILjava/lang/Object;)V
    .locals 0

    .line 29
    invoke-virtual {p0, p2}, Lcom/autochips/bluetooth/viewpager/FragmentViewPaperAdapter;->setCurrentPosition(I)V

    .line 30
    invoke-super {p0, p1, p2, p3}, Landroidx/fragment/app/FragmentPagerAdapter;->setPrimaryItem(Landroid/view/ViewGroup;ILjava/lang/Object;)V

    return-void
.end method
