.class public abstract Lcom/hcn/skin/support/app/SkinCompatFragment;
.super Landroidx/fragment/app/Fragment;
.source "SkinCompatFragment.java"

# interfaces
.implements Lcom/hcn/skin/support/observe/SkinObserver;
.implements Lcom/hcn/skin/support/observe/SkinLayoutObserver;


# static fields
.field private static final TAG:Ljava/lang/String; = "SkinCompatFragment"


# instance fields
.field private mLayoutId:I

.field private mSkinContentView:Landroid/view/View;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 33
    invoke-direct {p0}, Landroidx/fragment/app/Fragment;-><init>()V

    const/4 v0, 0x0

    .line 38
    iput-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mSkinContentView:Landroid/view/View;

    const/4 v0, -0x1

    .line 39
    iput v0, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mLayoutId:I

    return-void
.end method

.method private isSameContentView(Landroid/view/View;)Z
    .locals 2

    if-eqz p1, :cond_0

    .line 122
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mSkinContentView:Landroid/view/View;

    if-eqz v0, :cond_0

    .line 123
    invoke-virtual {p1, v0}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result p1

    return p1

    :cond_0
    const/4 v0, 0x0

    if-nez p1, :cond_1

    .line 124
    iget-object v1, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mSkinContentView:Landroid/view/View;

    if-eqz v1, :cond_1

    return v0

    :cond_1
    if-nez p1, :cond_2

    const/4 v0, 0x1

    :cond_2
    return v0
.end method


# virtual methods
.method public findViewByName(Ljava/lang/String;)Landroid/view/View;
    .locals 5

    .line 80
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mSkinContentView:Landroid/view/View;

    const-string v1, "id"

    const-string v2, "SkinCompatFragment"

    if-eqz v0, :cond_0

    .line 81
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "skin ContentView findViewByName "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 82
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mSkinContentView:Landroid/view/View;

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    invoke-virtual {v2, p1, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    invoke-virtual {v0, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    return-object p1

    .line 85
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->getView()Landroid/view/View;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 86
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->getView()Landroid/view/View;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, " findViewByName "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 87
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->getView()Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    .line 88
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, p1, v1, v4}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_1

    .line 90
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v0

    .line 91
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, " targetId "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p1}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, " Integer "

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-static {v0}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v2, p1}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 92
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->getView()Landroid/view/View;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    return-object p1

    :cond_1
    const/4 p1, 0x0

    return-object p1
.end method

.method public getSkinContentView()Landroid/view/View;
    .locals 1

    .line 118
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mSkinContentView:Landroid/view/View;

    return-object v0
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1

    .line 43
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->requireActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object v0

    invoke-virtual {v0}, Landroidx/fragment/app/FragmentActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/skin/support/SkinCompatManager;->create(Landroid/content/Context;)Lcom/hcn/skin/support/SkinCompatManager;

    .line 44
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/skin/support/SkinCompatManager;->addObserver(Lcom/hcn/skin/support/observe/SkinObserver;)V

    .line 46
    invoke-super {p0, p1}, Landroidx/fragment/app/Fragment;->onCreate(Landroid/os/Bundle;)V

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 3

    .line 54
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->getLayoutRes()I

    move-result p3

    iput p3, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mLayoutId:I

    .line 55
    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onCreateView layout: "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    iget v0, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mLayoutId:I

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p3

    const-string v0, " id: "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->getId()I

    move-result v0

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p3

    const-string v0, " "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    .line 56
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->getActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object v0

    instance-of v0, v0, Lcom/hcn/skin/support/observe/SkinObserver;

    if-eqz v0, :cond_0

    const-string v0, "SkinCompatActivity"

    goto :goto_0

    :cond_0
    const-string v0, "NormalActivity"

    :goto_0
    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    const-string v0, "SkinCompatFragment"

    .line 55
    invoke-static {v0, p3}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 58
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object p3

    .line 59
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->requireActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object v0

    invoke-virtual {v0}, Landroidx/fragment/app/FragmentActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mLayoutId:I

    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->skinLayoutFactory2()Landroid/view/LayoutInflater$Factory2;

    move-result-object v2

    .line 58
    invoke-virtual {p3, v0, v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getLayout(Landroid/content/Context;ILandroid/view/LayoutInflater$Factory2;)Landroid/view/View;

    move-result-object p3

    iput-object p3, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mSkinContentView:Landroid/view/View;

    if-eqz p3, :cond_1

    return-object p3

    .line 63
    :cond_1
    iget p3, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mLayoutId:I

    const/4 v0, 0x0

    invoke-virtual {p1, p3, p2, v0}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    return-object p1
.end method

.method public onDestroy()V
    .locals 1

    .line 100
    invoke-super {p0}, Landroidx/fragment/app/Fragment;->onDestroy()V

    .line 101
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/skin/support/SkinCompatManager;->deleteObserver(Lcom/hcn/skin/support/observe/SkinObserver;)V

    return-void
.end method

.method public onResume()V
    .locals 2

    const-string v0, "SkinCompatFragment"

    const-string v1, "onResume"

    .line 74
    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 75
    invoke-super {p0}, Landroidx/fragment/app/Fragment;->onResume()V

    .line 76
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onBindViewData()V

    return-void
.end method

.method public onSkinChanged(Ljava/lang/Object;)V
    .locals 3

    .line 106
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object p1

    .line 107
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->requireActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object v0

    invoke-virtual {v0}, Landroidx/fragment/app/FragmentActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/app/SkinCompatFragment;->mLayoutId:I

    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->skinLayoutFactory2()Landroid/view/LayoutInflater$Factory2;

    move-result-object v2

    .line 106
    invoke-virtual {p1, v0, v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getLayout(Landroid/content/Context;ILandroid/view/LayoutInflater$Factory2;)Landroid/view/View;

    move-result-object p1

    .line 108
    invoke-direct {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->isSameContentView(Landroid/view/View;)Z

    move-result p1

    .line 109
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onSkinChanged isSameContentView: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 110
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->getActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object v1

    instance-of v1, v1, Lcom/hcn/skin/support/observe/SkinObserver;

    if-eqz v1, :cond_0

    const-string v1, "SkinCompatActivity"

    goto :goto_0

    :cond_0
    const-string v1, "NormalActivity"

    :goto_0
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatFragment"

    .line 109
    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    if-nez p1, :cond_1

    .line 113
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->requireActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object p1

    invoke-virtual {p1}, Landroidx/fragment/app/FragmentActivity;->recreate()V

    :cond_1
    return-void
.end method

.method public onViewCreated(Landroid/view/View;Landroid/os/Bundle;)V
    .locals 1

    .line 69
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onCreateView "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string p2, "SkinCompatFragment"

    invoke-static {p2, p1}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method
