.class public Lcom/android/systemui/ImageWallpaper;
.super Landroid/service/wallpaper/WallpaperService;
.source "ImageWallpaper.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/ImageWallpaper$DrawableEngine;
    }
.end annotation


# instance fields
.field private mContext:Landroid/content/Context;

.field private mEngine:Lcom/android/systemui/ImageWallpaper$DrawableEngine;

.field private mIsLowMemoryDevice:Z

.field private mIstablet:Z

.field private mWallpaperManager:Landroid/app/WallpaperManager;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 48
    invoke-direct {p0}, Landroid/service/wallpaper/WallpaperService;-><init>()V

    .line 64
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/systemui/ImageWallpaper;->mIsLowMemoryDevice:Z

    return-void
.end method

.method static synthetic access$000(Lcom/android/systemui/ImageWallpaper;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/ImageWallpaper;

    .line 48
    iget-boolean v0, p0, Lcom/android/systemui/ImageWallpaper;->mIstablet:Z

    return v0
.end method

.method static synthetic access$100(Lcom/android/systemui/ImageWallpaper;)Landroid/app/WallpaperManager;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/ImageWallpaper;

    .line 48
    iget-object v0, p0, Lcom/android/systemui/ImageWallpaper;->mWallpaperManager:Landroid/app/WallpaperManager;

    return-object v0
.end method


# virtual methods
.method public onCreate()V
    .locals 4

    .line 68
    invoke-super {p0}, Landroid/service/wallpaper/WallpaperService;->onCreate()V

    .line 69
    invoke-virtual {p0}, Lcom/android/systemui/ImageWallpaper;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/ImageWallpaper;->mContext:Landroid/content/Context;

    .line 70
    const-class v0, Landroid/app/WallpaperManager;

    invoke-virtual {p0, v0}, Lcom/android/systemui/ImageWallpaper;->getSystemService(Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/WallpaperManager;

    iput-object v0, p0, Lcom/android/systemui/ImageWallpaper;->mWallpaperManager:Landroid/app/WallpaperManager;

    .line 71
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/systemui/ImageWallpaper;->mIstablet:Z

    .line 74
    invoke-static {}, Landroid/os/Process;->getTotalMemory()J

    move-result-wide v0

    const-wide/32 v2, 0x100000

    div-long/2addr v0, v2

    .line 75
    .local v0, "totalMemory":J
    const-wide/16 v2, 0x400

    cmp-long v2, v0, v2

    if-gtz v2, :cond_0

    .line 76
    const/4 v2, 0x1

    iput-boolean v2, p0, Lcom/android/systemui/ImageWallpaper;->mIsLowMemoryDevice:Z

    .line 78
    :cond_0
    return-void
.end method

.method public onCreateEngine()Landroid/service/wallpaper/WallpaperService$Engine;
    .locals 1

    .line 94
    new-instance v0, Lcom/android/systemui/ImageWallpaper$DrawableEngine;

    invoke-direct {v0, p0}, Lcom/android/systemui/ImageWallpaper$DrawableEngine;-><init>(Lcom/android/systemui/ImageWallpaper;)V

    iput-object v0, p0, Lcom/android/systemui/ImageWallpaper;->mEngine:Lcom/android/systemui/ImageWallpaper$DrawableEngine;

    .line 95
    iget-object v0, p0, Lcom/android/systemui/ImageWallpaper;->mEngine:Lcom/android/systemui/ImageWallpaper$DrawableEngine;

    return-object v0
.end method

.method public onTrimMemory(I)V
    .locals 1
    .param p1, "level"    # I

    .line 83
    iget-boolean v0, p0, Lcom/android/systemui/ImageWallpaper;->mIsLowMemoryDevice:Z

    if-nez v0, :cond_0

    .line 84
    return-void

    .line 87
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/ImageWallpaper;->mEngine:Lcom/android/systemui/ImageWallpaper$DrawableEngine;

    if-eqz v0, :cond_1

    .line 88
    iget-object v0, p0, Lcom/android/systemui/ImageWallpaper;->mEngine:Lcom/android/systemui/ImageWallpaper$DrawableEngine;

    invoke-virtual {v0, p1}, Lcom/android/systemui/ImageWallpaper$DrawableEngine;->trimMemory(I)V

    .line 90
    :cond_1
    return-void
.end method
