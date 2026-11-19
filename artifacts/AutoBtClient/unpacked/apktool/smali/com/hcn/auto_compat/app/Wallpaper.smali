.class public Lcom/hcn/auto_compat/app/Wallpaper;
.super Ljava/lang/Object;
.source "Wallpaper.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/auto_compat/app/Wallpaper$Info;,
        Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;,
        Lcom/hcn/auto_compat/app/Wallpaper$WallpaperHolder;
    }
.end annotation


# static fields
.field public static final APP_WALLPAPER_PATH:Ljava/lang/String; = "/apd/appWallpaper"

.field public static final ET_SAVE_PATH:Ljava/lang/String; = "Save-Path"

.field public static final ST_COMPLETED:Ljava/lang/String; = "Completed"

.field private static final TAG:Ljava/lang/String; = "Wallpaper"


# instance fields
.field private mInitialized:Z

.field private final mList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/hcn/auto_compat/app/Wallpaper$Info;",
            ">;"
        }
    .end annotation
.end field

.field private final mListeners:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/common/lang/Listenable<",
            "Ljava/lang/String;",
            ">;>;"
        }
    .end annotation
.end field


# direct methods
.method private constructor <init>()V
    .locals 1

    .line 64
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 55
    iput-boolean v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mInitialized:Z

    .line 58
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mList:Ljava/util/ArrayList;

    .line 65
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mListeners:Ljava/util/List;

    return-void
.end method

.method synthetic constructor <init>(Lcom/hcn/auto_compat/app/Wallpaper$1;)V
    .locals 0

    .line 27
    invoke-direct {p0}, Lcom/hcn/auto_compat/app/Wallpaper;-><init>()V

    return-void
.end method

.method static synthetic access$202(Lcom/hcn/auto_compat/app/Wallpaper;Z)Z
    .locals 0

    .line 27
    iput-boolean p1, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mInitialized:Z

    return p1
.end method

.method static synthetic access$300(Lcom/hcn/auto_compat/app/Wallpaper;)Ljava/util/ArrayList;
    .locals 0

    .line 27
    iget-object p0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mList:Ljava/util/ArrayList;

    return-object p0
.end method

.method static synthetic access$400(Lcom/hcn/auto_compat/app/Wallpaper;Ljava/lang/String;Ljava/lang/Object;)V
    .locals 0

    .line 27
    invoke-direct {p0, p1, p2}, Lcom/hcn/auto_compat/app/Wallpaper;->notifyListener(Ljava/lang/String;Ljava/lang/Object;)V

    return-void
.end method

.method static synthetic access$500(Lcom/hcn/auto_compat/app/Wallpaper;Ljava/lang/String;Ljava/lang/String;)Z
    .locals 0

    .line 27
    invoke-direct {p0, p1, p2}, Lcom/hcn/auto_compat/app/Wallpaper;->isFileSuffix(Ljava/lang/String;Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method public static instance()Lcom/hcn/auto_compat/app/Wallpaper;
    .locals 1

    .line 46
    sget-object v0, Lcom/hcn/auto_compat/app/Wallpaper$WallpaperHolder;->sInstance:Lcom/hcn/auto_compat/app/Wallpaper;

    return-object v0
.end method

.method private isFileSuffix(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 1

    .line 76
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_1

    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    .line 80
    :cond_0
    invoke-virtual {p1, p2}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result p1

    return p1

    :cond_1
    :goto_0
    const/4 p1, 0x0

    return p1
.end method

.method private notifyListener(Ljava/lang/String;Ljava/lang/Object;)V
    .locals 2

    .line 119
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mListeners:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/common/lang/Listenable;

    if-nez v1, :cond_0

    goto :goto_0

    .line 124
    :cond_0
    invoke-interface {v1, p1, p2}, Lcom/hcn/common/lang/Listenable;->listen(Ljava/lang/Object;Ljava/lang/Object;)V

    goto :goto_0

    :cond_1
    return-void
.end method


# virtual methods
.method public destroy()V
    .locals 1

    .line 270
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    return-void
.end method

.method public getInfo()Ljava/util/List;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/hcn/auto_compat/app/Wallpaper$Info;",
            ">;"
        }
    .end annotation

    .line 244
    iget-boolean v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mInitialized:Z

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 248
    :cond_0
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mList:Ljava/util/ArrayList;

    return-object v0
.end method

.method public initialize()V
    .locals 6

    .line 133
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    const-string v0, "/apd/appWallpaper"

    .line 136
    invoke-static {v0}, Lcom/hcn/common/utils/HFileUtils;->isFileExists(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    const v0, 0x133504d

    .line 141
    invoke-static {}, Lcom/hcn/common/lang/HPublicExecutor;->instance()Lcom/hcn/common/lang/HPublicExecutor;

    move-result-object v1

    int-to-long v2, v0

    new-instance v4, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;

    const/4 v5, 0x0

    invoke-direct {v4, p0, v5}, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;-><init>(Lcom/hcn/auto_compat/app/Wallpaper;Lcom/hcn/auto_compat/app/Wallpaper$1;)V

    new-instance v5, Lcom/hcn/auto_compat/app/Wallpaper$1;

    invoke-direct {v5, p0, v0}, Lcom/hcn/auto_compat/app/Wallpaper$1;-><init>(Lcom/hcn/auto_compat/app/Wallpaper;I)V

    invoke-virtual {v1, v2, v3, v4, v5}, Lcom/hcn/common/lang/HPublicExecutor;->submitTask(JLcom/hcn/common/lang/RunnableEx;Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;)Ljava/util/concurrent/Future;

    return-void
.end method

.method public isInitialized()Z
    .locals 1

    .line 235
    iget-boolean v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mInitialized:Z

    return v0
.end method

.method public register(Lcom/hcn/common/lang/Listenable;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/lang/Listenable<",
            "Ljava/lang/String;",
            ">;)V"
        }
    .end annotation

    .line 89
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 94
    :cond_0
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 97
    iget-boolean v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mInitialized:Z

    if-eqz v0, :cond_1

    const/4 v0, 0x0

    const-string v1, "Completed"

    .line 98
    invoke-interface {p1, v1, v0}, Lcom/hcn/common/lang/Listenable;->listen(Ljava/lang/Object;Ljava/lang/Object;)V

    :cond_1
    return-void
.end method

.method public saveWallpaperPath(Ljava/lang/String;)V
    .locals 1

    .line 258
    iget-boolean v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mInitialized:Z

    if-nez v0, :cond_0

    return-void

    :cond_0
    const-string v0, "Save-Path"

    .line 262
    invoke-direct {p0, v0, p1}, Lcom/hcn/auto_compat/app/Wallpaper;->notifyListener(Ljava/lang/String;Ljava/lang/Object;)V

    return-void
.end method

.method public unregister(Lcom/hcn/common/lang/Listenable;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/lang/Listenable<",
            "Ljava/lang/String;",
            ">;)V"
        }
    .end annotation

    .line 107
    invoke-static {p1}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 111
    :cond_0
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    return-void
.end method
