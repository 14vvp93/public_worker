.class public final synthetic Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# instance fields
.field private final synthetic f$0:Lcom/android/systemui/qs/tiles/MenuTile;

.field private final synthetic f$1:Ljava/util/ArrayList;

.field private final synthetic f$2:Lcom/android/systemui/statusbar/phone/SystemUIDialog;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/qs/tiles/MenuTile;Ljava/util/ArrayList;Lcom/android/systemui/statusbar/phone/SystemUIDialog;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;->f$0:Lcom/android/systemui/qs/tiles/MenuTile;

    iput-object p2, p0, Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;->f$1:Ljava/util/ArrayList;

    iput-object p3, p0, Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;->f$2:Lcom/android/systemui/statusbar/phone/SystemUIDialog;

    return-void
.end method


# virtual methods
.method public final onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 8

    iget-object v0, p0, Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;->f$0:Lcom/android/systemui/qs/tiles/MenuTile;

    iget-object v1, p0, Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;->f$1:Ljava/util/ArrayList;

    iget-object v2, p0, Lcom/android/systemui/qs/tiles/-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo;->f$2:Lcom/android/systemui/statusbar/phone/SystemUIDialog;

    move-object v3, p1

    move-object v4, p2

    move v5, p3

    move-wide v6, p4

    invoke-static/range {v0 .. v7}, Lcom/android/systemui/qs/tiles/MenuTile;->lambda$launchMenuTask$0(Lcom/android/systemui/qs/tiles/MenuTile;Ljava/util/ArrayList;Lcom/android/systemui/statusbar/phone/SystemUIDialog;Landroid/widget/AdapterView;Landroid/view/View;IJ)V

    return-void
.end method
