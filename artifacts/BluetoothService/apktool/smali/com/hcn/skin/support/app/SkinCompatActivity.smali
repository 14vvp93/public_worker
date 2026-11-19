.class public abstract Lcom/hcn/skin/support/app/SkinCompatActivity;
.super Landroidx/fragment/app/FragmentActivity;
.source "SkinCompatActivity.java"

# interfaces
.implements Lcom/hcn/skin/support/observe/SkinObserver;


# static fields
.field private static final TAG:Ljava/lang/String; = "SkinCompatActivity"


# instance fields
.field private mLayoutId:I

.field private mSkinContentView:Landroid/view/View;

.field private mSkinInflaterFactory:Lcom/hcn/skin/support/app/SkinInflaterFactory;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 32
    invoke-direct {p0}, Landroidx/fragment/app/FragmentActivity;-><init>()V

    const/4 v0, 0x0

    .line 38
    iput-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinContentView:Landroid/view/View;

    const/4 v0, -0x1

    .line 39
    iput v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mLayoutId:I

    return-void
.end method

.method private getSkinInflaterFactory()Lcom/hcn/skin/support/app/SkinInflaterFactory;
    .locals 1

    .line 59
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinInflaterFactory:Lcom/hcn/skin/support/app/SkinInflaterFactory;

    if-nez v0, :cond_0

    .line 60
    invoke-static {p0}, Lcom/hcn/skin/support/app/SkinInflaterFactory;->create(Landroid/content/Context;)Lcom/hcn/skin/support/app/SkinInflaterFactory;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinInflaterFactory:Lcom/hcn/skin/support/app/SkinInflaterFactory;

    .line 62
    :cond_0
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinInflaterFactory:Lcom/hcn/skin/support/app/SkinInflaterFactory;

    return-object v0
.end method

.method private isSameContentView(Landroid/view/View;)Z
    .locals 2

    if-eqz p1, :cond_0

    .line 141
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinContentView:Landroid/view/View;

    if-eqz v0, :cond_0

    .line 142
    invoke-virtual {p1, v0}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result p1

    return p1

    :cond_0
    const/4 v0, 0x0

    if-nez p1, :cond_1

    .line 143
    iget-object v1, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinContentView:Landroid/view/View;

    if-eqz v1, :cond_1

    return v0

    :cond_1
    if-nez p1, :cond_2

    const/4 v0, 0x1

    :cond_2
    return v0
.end method


# virtual methods
.method public applySkin()V
    .locals 1

    .line 137
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->getSkinInflaterFactory()Lcom/hcn/skin/support/app/SkinInflaterFactory;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/app/SkinInflaterFactory;->applySkin()V

    return-void
.end method

.method public findViewByName(Ljava/lang/String;)Landroid/view/View;
    .locals 5

    .line 107
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinContentView:Landroid/view/View;

    const-string v1, "id"

    const-string v2, "SkinCompatActivity"

    if-eqz v0, :cond_0

    .line 108
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

    .line 109
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinContentView:Landroid/view/View;

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v2

    invoke-virtual {v2, p1, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    invoke-virtual {v0, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    return-object p1

    .line 111
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "findViewByName "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    .line 112
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    .line 113
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, p1, v1, v4}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-eqz p1, :cond_1

    .line 115
    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v0

    .line 116
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

    .line 117
    invoke-virtual {p0, v0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    return-object p1

    :cond_1
    const/4 p1, 0x0

    return-object p1
.end method

.method public getSkinContentView()Landroid/view/View;
    .locals 1

    .line 97
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinContentView:Landroid/view/View;

    return-object v0
.end method

.method public onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 2

    .line 54
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onConfigurationChanged "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatActivity"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 55
    invoke-super {p0, p1}, Landroidx/fragment/app/FragmentActivity;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    return-void
.end method

.method protected onContentViewCreated(Landroid/view/View;)V
    .locals 0

    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 2

    .line 43
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/skin/support/SkinCompatManager;->create(Landroid/content/Context;)Lcom/hcn/skin/support/SkinCompatManager;

    .line 44
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/skin/support/SkinCompatManager;->addObserver(Lcom/hcn/skin/support/observe/SkinObserver;)V

    .line 45
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    .line 46
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1, v0}, Lcom/hcn/skin/support/SkinCompatManager;->updateConfig(Landroid/content/res/Configuration;)V

    .line 49
    invoke-super {p0, p1}, Landroidx/fragment/app/FragmentActivity;->onCreate(Landroid/os/Bundle;)V

    return-void
.end method

.method protected onDestroy()V
    .locals 1

    .line 102
    invoke-super {p0}, Landroidx/fragment/app/FragmentActivity;->onDestroy()V

    .line 103
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/skin/support/SkinCompatManager;->deleteObserver(Lcom/hcn/skin/support/observe/SkinObserver;)V

    return-void
.end method

.method public onSkinChanged(Ljava/lang/Object;)V
    .locals 2

    .line 124
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object p1

    iget v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mLayoutId:I

    .line 125
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->skinLayoutFactory2()Landroid/view/LayoutInflater$Factory2;

    move-result-object v1

    .line 124
    invoke-virtual {p1, p0, v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getLayout(Landroid/content/Context;ILandroid/view/LayoutInflater$Factory2;)Landroid/view/View;

    move-result-object p1

    .line 126
    invoke-direct {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatActivity;->isSameContentView(Landroid/view/View;)Z

    move-result p1

    .line 127
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onSkinChanged isSameContentView:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatActivity"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    if-eqz p1, :cond_0

    .line 129
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->getSkinInflaterFactory()Lcom/hcn/skin/support/app/SkinInflaterFactory;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/skin/support/app/SkinInflaterFactory;->applySkin()V

    goto :goto_0

    .line 132
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->recreate()V

    :goto_0
    return-void
.end method

.method public setContentView(I)V
    .locals 2

    .line 72
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setContentView "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {p1}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "SkinCompatActivity"

    invoke-static {v1, v0}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 73
    iput p1, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mLayoutId:I

    .line 74
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    .line 75
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->skinLayoutFactory2()Landroid/view/LayoutInflater$Factory2;

    move-result-object v1

    .line 74
    invoke-virtual {v0, p0, p1, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getLayout(Landroid/content/Context;ILandroid/view/LayoutInflater$Factory2;)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinContentView:Landroid/view/View;

    if-eqz v0, :cond_0

    .line 77
    invoke-virtual {p0, v0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->setContentView(Landroid/view/View;)V

    goto :goto_0

    .line 79
    :cond_0
    invoke-super {p0, p1}, Landroidx/fragment/app/FragmentActivity;->setContentView(I)V

    .line 83
    :goto_0
    iget-object p1, p0, Lcom/hcn/skin/support/app/SkinCompatActivity;->mSkinContentView:Landroid/view/View;

    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onContentViewCreated(Landroid/view/View;)V

    return-void
.end method
