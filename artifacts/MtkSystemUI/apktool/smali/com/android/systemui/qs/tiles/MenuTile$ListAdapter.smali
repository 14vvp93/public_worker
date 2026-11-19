.class Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;
.super Landroid/widget/BaseAdapter;
.source "MenuTile.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/qs/tiles/MenuTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ListAdapter"
.end annotation


# instance fields
.field private mContext:Landroid/content/Context;

.field private menuNameDatas:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mlayoutInflater:Landroid/view/LayoutInflater;

.field private packageNameDatas:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field final synthetic this$0:Lcom/android/systemui/qs/tiles/MenuTile;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/tiles/MenuTile;Landroid/content/Context;Ljava/util/ArrayList;Ljava/util/ArrayList;)V
    .locals 1
    .param p1, "this$0"    # Lcom/android/systemui/qs/tiles/MenuTile;
    .param p2, "context"    # Landroid/content/Context;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;",
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;)V"
        }
    .end annotation

    .line 177
    .local p3, "mDatas":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/String;>;"
    .local p4, "pDatas":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/String;>;"
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->this$0:Lcom/android/systemui/qs/tiles/MenuTile;

    invoke-direct {p0}, Landroid/widget/BaseAdapter;-><init>()V

    .line 179
    iput-object p2, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->mContext:Landroid/content/Context;

    .line 180
    iput-object p4, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->packageNameDatas:Ljava/util/ArrayList;

    .line 181
    iput-object p3, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->menuNameDatas:Ljava/util/ArrayList;

    .line 182
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->mContext:Landroid/content/Context;

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->mlayoutInflater:Landroid/view/LayoutInflater;

    .line 183
    return-void
.end method


# virtual methods
.method public getCount()I
    .locals 1

    .line 187
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->packageNameDatas:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    return v0
.end method

.method public getItem(I)Ljava/lang/Object;
    .locals 1
    .param p1, "position"    # I

    .line 192
    iget-object v0, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->packageNameDatas:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    return-object v0
.end method

.method public getItemId(I)J
    .locals 2
    .param p1, "arg0"    # I

    .line 197
    const-wide/16 v0, 0x0

    return-wide v0
.end method

.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 4
    .param p1, "position"    # I
    .param p2, "convertView"    # Landroid/view/View;
    .param p3, "parent"    # Landroid/view/ViewGroup;

    .line 203
    const/4 v0, 0x0

    .line 204
    .local v0, "mViewHolder":Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;
    if-nez p2, :cond_0

    .line 205
    iget-object v1, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->mlayoutInflater:Landroid/view/LayoutInflater;

    const v2, 0x7f0d011b

    const/4 v3, 0x0

    invoke-virtual {v1, v2, p3, v3}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p2

    .line 206
    const v1, 0x7f0a026b

    invoke-virtual {p2, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 207
    .local v1, "menuName":Landroid/widget/TextView;
    new-instance v2, Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;

    iget-object v3, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->this$0:Lcom/android/systemui/qs/tiles/MenuTile;

    invoke-direct {v2, v3, v1}, Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;-><init>(Lcom/android/systemui/qs/tiles/MenuTile;Landroid/widget/TextView;)V

    move-object v0, v2

    .line 208
    invoke-virtual {p2, v0}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    .line 209
    .end local v1    # "menuName":Landroid/widget/TextView;
    goto :goto_0

    .line 210
    :cond_0
    invoke-virtual {p2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v1

    move-object v0, v1

    check-cast v0, Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;

    .line 213
    :goto_0
    iget-object v1, v0, Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;->mMenuName:Landroid/widget/TextView;

    iget-object v2, p0, Lcom/android/systemui/qs/tiles/MenuTile$ListAdapter;->menuNameDatas:Ljava/util/ArrayList;

    invoke-virtual {v2, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/CharSequence;

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 214
    return-object p2
.end method
