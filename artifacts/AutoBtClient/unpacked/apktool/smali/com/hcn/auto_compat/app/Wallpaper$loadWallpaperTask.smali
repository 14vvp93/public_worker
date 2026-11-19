.class Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;
.super Lcom/hcn/common/lang/RunnableEx;
.source "Wallpaper.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/auto_compat/app/Wallpaper;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "loadWallpaperTask"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/auto_compat/app/Wallpaper;


# direct methods
.method private constructor <init>(Lcom/hcn/auto_compat/app/Wallpaper;)V
    .locals 0

    .line 170
    iput-object p1, p0, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    invoke-direct {p0}, Lcom/hcn/common/lang/RunnableEx;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/hcn/auto_compat/app/Wallpaper;Lcom/hcn/auto_compat/app/Wallpaper$1;)V
    .locals 0

    .line 170
    invoke-direct {p0, p1}, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;-><init>(Lcom/hcn/auto_compat/app/Wallpaper;)V

    return-void
.end method

.method static synthetic lambda$run$0(Lcom/hcn/auto_compat/app/Wallpaper$Info;Lcom/hcn/auto_compat/app/Wallpaper$Info;)I
    .locals 2

    const/4 v0, 0x0

    if-eqz p0, :cond_1

    if-nez p1, :cond_0

    goto :goto_0

    .line 220
    :cond_0
    iget-object v1, p0, Lcom/hcn/auto_compat/app/Wallpaper$Info;->thumbnailPath:Ljava/lang/String;

    if-eqz v1, :cond_1

    iget-object v1, p1, Lcom/hcn/auto_compat/app/Wallpaper$Info;->thumbnailPath:Ljava/lang/String;

    if-eqz v1, :cond_1

    .line 221
    iget-object p0, p0, Lcom/hcn/auto_compat/app/Wallpaper$Info;->thumbnailPath:Ljava/lang/String;

    iget-object p1, p1, Lcom/hcn/auto_compat/app/Wallpaper$Info;->thumbnailPath:Ljava/lang/String;

    invoke-virtual {p0, p1}, Ljava/lang/String;->compareTo(Ljava/lang/String;)I

    move-result p0

    return p0

    :cond_1
    :goto_0
    return v0
.end method


# virtual methods
.method public run()V
    .locals 9

    const-string v0, "/apd/appWallpaper"

    .line 174
    invoke-static {v0}, Lcom/hcn/common/utils/HFileUtils;->isFileExists(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_0

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    const/4 v1, 0x0

    const-string v2, "loadWallpaperTask: /apd/appWallpaper not exists"

    aput-object v2, v0, v1

    const-string v1, "Wallpaper"

    .line 175
    invoke-static {v1, v0}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-void

    .line 180
    :cond_0
    invoke-static {v0}, Lcom/hcn/common/utils/HFileUtils;->listFilesInDir(Ljava/lang/String;)Ljava/util/List;

    move-result-object v1

    .line 182
    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_1
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_5

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/io/File;

    .line 183
    invoke-virtual {v2}, Ljava/io/File;->isDirectory()Z

    move-result v3

    if-eqz v3, :cond_2

    goto :goto_0

    .line 187
    :cond_2
    invoke-virtual {v2}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v3

    const-string v4, "thumbnail_"

    .line 188
    invoke-virtual {v3, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_1

    iget-object v4, p0, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    const-string v5, ".jpg"

    .line 189
    invoke-static {v4, v3, v5}, Lcom/hcn/auto_compat/app/Wallpaper;->access$500(Lcom/hcn/auto_compat/app/Wallpaper;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v4

    const-string v6, ".png"

    if-nez v4, :cond_3

    iget-object v4, p0, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    invoke-static {v4, v3, v6}, Lcom/hcn/auto_compat/app/Wallpaper;->access$500(Lcom/hcn/auto_compat/app/Wallpaper;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_1

    .line 190
    :cond_3
    new-instance v4, Lcom/hcn/auto_compat/app/Wallpaper$Info;

    invoke-direct {v4}, Lcom/hcn/auto_compat/app/Wallpaper$Info;-><init>()V

    .line 191
    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    iput-object v2, v4, Lcom/hcn/auto_compat/app/Wallpaper$Info;->thumbnailPath:Ljava/lang/String;

    const-string v2, "."

    .line 192
    invoke-virtual {v3, v2}, Ljava/lang/String;->lastIndexOf(Ljava/lang/String;)I

    move-result v2

    .line 193
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    sget-object v8, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "wallpaper_"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const/16 v8, 0xa

    .line 194
    invoke-virtual {v3, v8, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v7, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 196
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 197
    invoke-static {v3}, Lcom/hcn/common/utils/HFileUtils;->isFileExists(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_4

    .line 198
    iput-object v3, v4, Lcom/hcn/auto_compat/app/Wallpaper$Info;->wallpaperPath:Ljava/lang/String;

    goto :goto_1

    .line 200
    :cond_4
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 201
    invoke-static {v2}, Lcom/hcn/common/utils/HFileUtils;->isFileExists(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_1

    .line 202
    iput-object v2, v4, Lcom/hcn/auto_compat/app/Wallpaper$Info;->wallpaperPath:Ljava/lang/String;

    .line 208
    :goto_1
    iget-object v2, p0, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    invoke-static {v2}, Lcom/hcn/auto_compat/app/Wallpaper;->access$300(Lcom/hcn/auto_compat/app/Wallpaper;)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto/16 :goto_0

    .line 213
    :cond_5
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    invoke-static {v0}, Lcom/hcn/auto_compat/app/Wallpaper;->access$300(Lcom/hcn/auto_compat/app/Wallpaper;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_6

    .line 214
    iget-object v0, p0, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    invoke-static {v0}, Lcom/hcn/auto_compat/app/Wallpaper;->access$300(Lcom/hcn/auto_compat/app/Wallpaper;)Ljava/util/ArrayList;

    move-result-object v0

    sget-object v1, Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0;->INSTANCE:Lcom/hcn/auto_compat/app/Wallpaper$loadWallpaperTask$$ExternalSyntheticLambda0;

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->sort(Ljava/util/Comparator;)V

    :cond_6
    return-void
.end method
