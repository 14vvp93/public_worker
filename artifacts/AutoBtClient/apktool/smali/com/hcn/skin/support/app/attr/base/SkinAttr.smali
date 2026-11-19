.class public abstract Lcom/hcn/skin/support/app/attr/base/SkinAttr;
.super Ljava/lang/Object;
.source "SkinAttr.java"

# interfaces
.implements Ljava/lang/Cloneable;


# static fields
.field protected static final RES_TYPE_NAME_ANIM:Ljava/lang/String; = "anim"

.field protected static final RES_TYPE_NAME_COLOR:Ljava/lang/String; = "color"

.field protected static final RES_TYPE_NAME_DRAWABLE:Ljava/lang/String; = "drawable"

.field protected static final RES_TYPE_NAME_MIPMAP:Ljava/lang/String; = "mipmap"


# instance fields
.field protected attrName:Ljava/lang/String;

.field protected attrValueRefId:I

.field protected attrValueRefName:Ljava/lang/String;

.field protected attrValueTypeName:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 12
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public apply(Landroid/view/View;)V
    .locals 0

    .line 43
    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->applySkin(Landroid/view/View;)V

    return-void
.end method

.method protected abstract applySkin(Landroid/view/View;)V
.end method

.method public clone()Lcom/hcn/skin/support/app/attr/base/SkinAttr;
    .locals 1

    .line 80
    :try_start_0
    invoke-super {p0}, Ljava/lang/Object;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;
    :try_end_0
    .catch Ljava/lang/CloneNotSupportedException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 82
    invoke-virtual {v0}, Ljava/lang/CloneNotSupportedException;->printStackTrace()V

    const/4 v0, 0x0

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

    .line 11
    invoke-virtual {p0}, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->clone()Lcom/hcn/skin/support/app/attr/base/SkinAttr;

    move-result-object v0

    return-object v0
.end method

.method protected isAnim()Z
    .locals 2

    .line 63
    iget-object v0, p0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueTypeName:Ljava/lang/String;

    const-string v1, "anim"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method protected isColor()Z
    .locals 2

    .line 59
    iget-object v0, p0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueTypeName:Ljava/lang/String;

    const-string v1, "color"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method protected isDrawable()Z
    .locals 2

    .line 54
    iget-object v0, p0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueTypeName:Ljava/lang/String;

    const-string v1, "drawable"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    iget-object v0, p0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueTypeName:Ljava/lang/String;

    const-string v1, "mipmap"

    .line 55
    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public toString()Ljava/lang/String;
    .locals 3

    .line 68
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "SkinAttr{attrName=\'"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const/16 v1, 0x27

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ", attrValueRefId="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget v2, p0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueRefId:I

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ", attrValueRefName=\'"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, p0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueRefName:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ", attrValueTypeName=\'"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, p0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueTypeName:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v0

    const/16 v1, 0x7d

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
