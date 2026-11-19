.class final Lcom/hcn/auto_compat/app/Wallpaper$WallpaperHolder;
.super Ljava/lang/Object;
.source "Wallpaper.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/auto_compat/app/Wallpaper;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "WallpaperHolder"
.end annotation


# static fields
.field static final sInstance:Lcom/hcn/auto_compat/app/Wallpaper;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 37
    new-instance v0, Lcom/hcn/auto_compat/app/Wallpaper;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Lcom/hcn/auto_compat/app/Wallpaper;-><init>(Lcom/hcn/auto_compat/app/Wallpaper$1;)V

    sput-object v0, Lcom/hcn/auto_compat/app/Wallpaper$WallpaperHolder;->sInstance:Lcom/hcn/auto_compat/app/Wallpaper;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 36
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
