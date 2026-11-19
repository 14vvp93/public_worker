.class public Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;
.super Ljava/lang/Object;
.source "IconIdWrapper.java"

# interfaces
.implements Ljava/lang/Cloneable;


# instance fields
.field private mIconId:I

.field private mResources:Landroid/content/res/Resources;


# direct methods
.method public constructor <init>()V
    .locals 2

    .line 18
    const/4 v0, 0x0

    const/4 v1, 0x0

    invoke-direct {p0, v0, v1}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;-><init>(Landroid/content/res/Resources;I)V

    .line 19
    return-void
.end method

.method public constructor <init>(Landroid/content/res/Resources;I)V
    .locals 1
    .param p1, "resources"    # Landroid/content/res/Resources;
    .param p2, "iconId"    # I

    .line 34
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 11
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    .line 12
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    .line 35
    iput-object p1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    .line 36
    iput p2, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    .line 37
    return-void
.end method


# virtual methods
.method public clone()Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;
    .locals 2

    .line 71
    const/4 v0, 0x0

    .line 73
    .local v0, "clone":Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;
    :try_start_0
    invoke-super {p0}, Ljava/lang/Object;->clone()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    move-object v0, v1

    .line 74
    iget-object v1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    iput-object v1, v0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    .line 75
    iget v1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    iput v1, v0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I
    :try_end_0
    .catch Ljava/lang/CloneNotSupportedException; {:try_start_0 .. :try_end_0} :catch_0

    .line 78
    goto :goto_0

    .line 76
    :catch_0
    move-exception v1

    .line 77
    .local v1, "e":Ljava/lang/CloneNotSupportedException;
    const/4 v0, 0x0

    .line 79
    .end local v1    # "e":Ljava/lang/CloneNotSupportedException;
    :goto_0
    return-object v0
.end method

.method public bridge synthetic clone()Ljava/lang/Object;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/CloneNotSupportedException;
        }
    .end annotation

    .line 9
    invoke-virtual {p0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->clone()Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    move-result-object v0

    return-object v0
.end method

.method public equals(Ljava/lang/Object;)Z
    .locals 5
    .param p1, "obj"    # Ljava/lang/Object;

    .line 102
    const/4 v0, 0x1

    if-ne p0, p1, :cond_0

    .line 103
    return v0

    .line 105
    :cond_0
    const/4 v1, 0x0

    if-nez p1, :cond_1

    .line 106
    return v1

    .line 108
    :cond_1
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v2

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v3

    if-eq v2, v3, :cond_2

    .line 109
    return v1

    .line 111
    :cond_2
    move-object v2, p1

    check-cast v2, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 112
    .local v2, "other":Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;
    iget v3, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    iget v4, v2, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    if-eq v3, v4, :cond_3

    .line 113
    return v1

    .line 115
    :cond_3
    iget-object v3, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    if-nez v3, :cond_4

    .line 116
    iget-object v3, v2, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    if-eqz v3, :cond_5

    .line 117
    return v1

    .line 119
    :cond_4
    iget-object v3, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    iget-object v4, v2, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    invoke-virtual {v3, v4}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_5

    .line 120
    return v1

    .line 122
    :cond_5
    return v0
.end method

.method public getDrawable()Landroid/graphics/drawable/Drawable;
    .locals 2

    .line 60
    iget-object v0, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    if-eqz v0, :cond_0

    iget v0, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    if-eqz v0, :cond_0

    .line 61
    iget-object v0, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    iget v1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0

    .line 63
    :cond_0
    const/4 v0, 0x0

    return-object v0
.end method

.method public getIconId()I
    .locals 1

    .line 48
    iget v0, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    return v0
.end method

.method public getResources()Landroid/content/res/Resources;
    .locals 1

    .line 40
    iget-object v0, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    return-object v0
.end method

.method public hashCode()I
    .locals 5

    .line 93
    const/16 v0, 0x1f

    .line 94
    .local v0, "prime":I
    const/4 v1, 0x1

    .line 95
    .local v1, "result":I
    const/16 v2, 0x1f

    mul-int v3, v2, v1

    iget v4, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    add-int/2addr v3, v4

    .line 96
    .end local v1    # "result":I
    .local v3, "result":I
    mul-int/2addr v2, v3

    iget-object v1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    if-nez v1, :cond_0

    const/4 v1, 0x0

    goto :goto_0

    :cond_0
    iget-object v1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    invoke-virtual {v1}, Ljava/lang/Object;->hashCode()I

    move-result v1

    :goto_0
    add-int/2addr v2, v1

    .line 97
    .end local v3    # "result":I
    .local v2, "result":I
    return v2
.end method

.method public setIconId(I)V
    .locals 0
    .param p1, "iconId"    # I

    .line 52
    iput p1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    .line 53
    return-void
.end method

.method public setResources(Landroid/content/res/Resources;)V
    .locals 0
    .param p1, "resources"    # Landroid/content/res/Resources;

    .line 44
    iput-object p1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mResources:Landroid/content/res/Resources;

    .line 45
    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    .line 84
    invoke-virtual {p0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    if-nez v0, :cond_0

    .line 85
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "IconIdWrapper [mResources == null, mIconId="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0

    .line 87
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "IconIdWrapper [mResources != null, mIconId="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->mIconId:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, "]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
