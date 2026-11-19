.class public Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;
.super Lcom/android/systemui/plugins/qs/QSTile$Icon;
.source "QsIconWrapper.java"


# static fields
.field private static sQsIconWrapperMap:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field private final mIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 14
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    sput-object v0, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->sQsIconWrapperMap:Landroid/util/SparseArray;

    return-void
.end method

.method public constructor <init>(Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)V
    .locals 0
    .param p1, "iconWrapper"    # Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 22
    invoke-direct {p0}, Lcom/android/systemui/plugins/qs/QSTile$Icon;-><init>()V

    .line 23
    iput-object p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->mIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 24
    return-void
.end method

.method public static get(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;
    .locals 2
    .param p0, "resId"    # I
    .param p1, "iconId"    # Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 27
    sget-object v0, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->sQsIconWrapperMap:Landroid/util/SparseArray;

    invoke-virtual {v0, p0}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    .line 28
    .local v0, "icon":Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;
    if-nez v0, :cond_0

    .line 29
    new-instance v1, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;

    invoke-direct {v1, p1}, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;-><init>(Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)V

    move-object v0, v1

    .line 30
    sget-object v1, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->sQsIconWrapperMap:Landroid/util/SparseArray;

    invoke-virtual {v1, p0, v0}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 32
    :cond_0
    return-object v0
.end method


# virtual methods
.method public equals(Ljava/lang/Object;)Z
    .locals 1
    .param p1, "o"    # Ljava/lang/Object;

    .line 47
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->mIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-virtual {v0, p1}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public getDrawable(Landroid/content/Context;)Landroid/graphics/drawable/Drawable;
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 37
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->mIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-virtual {v0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->getDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0
.end method

.method public hashCode()I
    .locals 1

    .line 42
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/QsIconWrapper;->mIconWrapper:Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    invoke-virtual {v0}, Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;->hashCode()I

    move-result v0

    return v0
.end method
