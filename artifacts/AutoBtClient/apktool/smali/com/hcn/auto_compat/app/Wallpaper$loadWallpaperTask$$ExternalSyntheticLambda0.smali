.class public final synthetic Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Ljava/util/Comparator;


# static fields
.field public static final synthetic INSTANCE:Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0;

    invoke-direct {v0}, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0;-><init>()V

    sput-object v0, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0;->INSTANCE:Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final compare(Ljava/lang/Object;Ljava/lang/Object;)I
    .locals 0

    check-cast p1, Lcom/hcn/auto_compat/app/Wallpaper$Info;

    check-cast p2, Lcom/hcn/auto_compat/app/Wallpaper$Info;

    invoke-static {p1, p2}, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;->lambda$run$0(Lcom/hcn/auto_compat/app/Wallpaper$Info;Lcom/hcn/auto_compat/app/Wallpaper$Info;)I

    move-result p1

    return p1
.end method
