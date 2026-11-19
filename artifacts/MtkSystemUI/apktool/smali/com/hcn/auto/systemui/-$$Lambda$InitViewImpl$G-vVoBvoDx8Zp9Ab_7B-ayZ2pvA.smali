.class public final synthetic Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$G-vVoBvoDx8Zp9Ab_7B-ayZ2pvA;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Lcom/hcn/widgets/dynamicrecyclerview/DynamicAppRecyclerView$AppIconLoader;


# instance fields
.field private final synthetic f$0:Lcom/hcn/auto/systemui/InitViewImpl;


# direct methods
.method public synthetic constructor <init>(Lcom/hcn/auto/systemui/InitViewImpl;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$G-vVoBvoDx8Zp9Ab_7B-ayZ2pvA;->f$0:Lcom/hcn/auto/systemui/InitViewImpl;

    return-void
.end method


# virtual methods
.method public final loadIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;
    .locals 1

    iget-object v0, p0, Lcom/hcn/auto/systemui/-$$Lambda$InitViewImpl$G-vVoBvoDx8Zp9Ab_7B-ayZ2pvA;->f$0:Lcom/hcn/auto/systemui/InitViewImpl;

    invoke-static {v0, p1}, Lcom/hcn/auto/systemui/InitViewImpl;->lambda$initView$17(Lcom/hcn/auto/systemui/InitViewImpl;Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    return-object p1
.end method
