.class public Lcom/android/launcher3/icons/GraphicsUtils;
.super Ljava/lang/Object;
.source "GraphicsUtils.java"


# direct methods
.method public static getArea(Landroid/graphics/Region;)I
    .locals 5
    .param p0, "r"    # Landroid/graphics/Region;

    .line 68
    new-instance v0, Landroid/graphics/RegionIterator;

    invoke-direct {v0, p0}, Landroid/graphics/RegionIterator;-><init>(Landroid/graphics/Region;)V

    .line 69
    .local v0, "itr":Landroid/graphics/RegionIterator;
    const/4 v1, 0x0

    .line 70
    .local v1, "area":I
    new-instance v2, Landroid/graphics/Rect;

    invoke-direct {v2}, Landroid/graphics/Rect;-><init>()V

    .line 71
    .local v2, "tempRect":Landroid/graphics/Rect;
    :goto_0
    invoke-virtual {v0, v2}, Landroid/graphics/RegionIterator;->next(Landroid/graphics/Rect;)Z

    move-result v3

    if-eqz v3, :cond_0

    .line 72
    invoke-virtual {v2}, Landroid/graphics/Rect;->width()I

    move-result v3

    invoke-virtual {v2}, Landroid/graphics/Rect;->height()I

    move-result v4

    mul-int/2addr v3, v4

    add-int/2addr v1, v3

    goto :goto_0

    .line 74
    :cond_0
    return v1
.end method
