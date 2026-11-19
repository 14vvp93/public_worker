.class public Lcom/hcn/auto/systemui/IconAdapter;
.super Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;
.source "IconAdapter.java"


# direct methods
.method public constructor <init>(I)V
    .locals 0
    .param p1, "layoutId"    # I

    .line 15
    invoke-direct {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/AppIconAdapter;-><init>(I)V

    .line 16
    return-void
.end method


# virtual methods
.method public bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;I)V
    .locals 3
    .param p2, "appIconData"    # Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
    .param p3, "i"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            ">;",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;",
            "I)V"
        }
    .end annotation

    .line 20
    .local p1, "baseViewHolder":Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;, "Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder<Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;>;"
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    const-string v1, "skin_app_icon"

    const-string v2, "id"

    invoke-virtual {v0, v1, v2}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    .line 21
    .local v0, "appIcon":I
    if-eqz v0, :cond_0

    .line 22
    invoke-virtual {p2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getAppIcon()Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {p1, v0, v1}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->setImageDrawable(ILandroid/graphics/drawable/Drawable;)Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    goto :goto_0

    .line 25
    :cond_0
    const v1, 0x7f0a0069

    invoke-virtual {p2}, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->getAppIcon()Landroid/graphics/drawable/Drawable;

    move-result-object v2

    invoke-virtual {p1, v1, v2}, Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;->setImageDrawable(ILandroid/graphics/drawable/Drawable;)Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;

    .line 27
    :goto_0
    return-void
.end method

.method public bridge synthetic bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Ljava/lang/Object;I)V
    .locals 0

    .line 11
    check-cast p2, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;

    invoke-virtual {p0, p1, p2, p3}, Lcom/hcn/auto/systemui/IconAdapter;->bindView(Lcom/hcn/widgets/dynamicrecyclerview/adapter/BaseViewHolder;Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;I)V

    return-void
.end method
