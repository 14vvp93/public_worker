.class public abstract Lcom/hcn/skin2/compat/Skin2CompatFragment;
.super Lcom/hcn/skin/support/app/SkinCompatFragment;
.source "Skin2CompatFragment.java"

# interfaces
.implements Lcom/hcn/skinx_night/INightDecoration;


# static fields
.field private static final TAG:Ljava/lang/String; = "Skin2CompatFragment"


# instance fields
.field protected mUiMode:I


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 19
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;-><init>()V

    const/4 v0, 0x0

    .line 31
    iput v0, p0, Lcom/hcn/skin2/compat/Skin2CompatFragment;->mUiMode:I

    return-void
.end method


# virtual methods
.method protected TAG()Ljava/lang/String;
    .locals 1

    .line 24
    sget-object v0, Lcom/hcn/skin2/compat/Skin2CompatFragment;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method public initNightDecoration()V
    .locals 4

    .line 43
    invoke-virtual {p0}, Lcom/hcn/skin2/compat/Skin2CompatFragment;->TAG()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "initNightDecoration: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget v3, p0, Lcom/hcn/skin2/compat/Skin2CompatFragment;->mUiMode:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x0

    aput-object v2, v1, v3

    invoke-static {v0, v1}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-void
.end method

.method public onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 0

    .line 48
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    .line 51
    iget p1, p1, Landroid/content/res/Configuration;->uiMode:I

    invoke-virtual {p0, p1}, Lcom/hcn/skin2/compat/Skin2CompatFragment;->tryOnUiModeChanged(I)V

    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 0

    .line 35
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreate(Landroid/os/Bundle;)V

    .line 38
    invoke-virtual {p0}, Lcom/hcn/skin2/compat/Skin2CompatFragment;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object p1

    iget p1, p1, Landroid/content/res/Configuration;->uiMode:I

    iput p1, p0, Lcom/hcn/skin2/compat/Skin2CompatFragment;->mUiMode:I

    return-void
.end method

.method public tryOnUiModeChanged(I)V
    .locals 5

    .line 56
    iget v0, p0, Lcom/hcn/skin2/compat/Skin2CompatFragment;->mUiMode:I

    if-ne v0, p1, :cond_0

    return-void

    .line 60
    :cond_0
    iput p1, p0, Lcom/hcn/skin2/compat/Skin2CompatFragment;->mUiMode:I

    .line 61
    invoke-virtual {p0}, Lcom/hcn/skin2/compat/Skin2CompatFragment;->TAG()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "tryOnUiModeChanged: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    aput-object p1, v1, v2

    invoke-static {v0, v1}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-void
.end method
