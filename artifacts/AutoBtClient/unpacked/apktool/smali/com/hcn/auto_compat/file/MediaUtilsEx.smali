.class public Lcom/hcn/auto_compat/file/MediaUtilsEx;
.super Ljava/lang/Object;
.source "MediaUtilsEx.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "MediaUtilsEx"

.field public static final UNSUPPORTED:Ljava/lang/String; = "unsupported"


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 20
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static parseAudioUriFilePath(Landroid/content/Context;Landroid/net/Uri;)Ljava/lang/String;
    .locals 1

    .line 42
    sget p0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v0, 0x1b

    if-le p0, v0, :cond_1

    .line 43
    invoke-static {p1}, Lcom/hcn/common/utils/HUriUtils;->uri2File(Landroid/net/Uri;)Ljava/io/File;

    move-result-object p0

    .line 44
    invoke-static {p0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    const/4 p0, 0x0

    goto :goto_0

    .line 45
    :cond_0
    invoke-virtual {p0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object p0

    :goto_0
    return-object p0

    :cond_1
    const-string p0, "unsupported"

    return-object p0
.end method

.method public static parseImageUriFilePath(Landroid/content/Context;Landroid/net/Uri;)Ljava/lang/String;
    .locals 1

    .line 80
    sget p0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v0, 0x1b

    if-le p0, v0, :cond_1

    .line 81
    invoke-static {p1}, Lcom/hcn/common/utils/HUriUtils;->uri2File(Landroid/net/Uri;)Ljava/io/File;

    move-result-object p0

    .line 82
    invoke-static {p0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    const/4 p0, 0x0

    goto :goto_0

    .line 83
    :cond_0
    invoke-virtual {p0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object p0

    :goto_0
    return-object p0

    :cond_1
    const-string p0, "unsupported"

    return-object p0
.end method

.method public static parseVideoUriFilePath(Landroid/content/Context;Landroid/net/Uri;)Ljava/lang/String;
    .locals 1

    .line 61
    sget p0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v0, 0x1b

    if-le p0, v0, :cond_1

    .line 62
    invoke-static {p1}, Lcom/hcn/common/utils/HUriUtils;->uri2File(Landroid/net/Uri;)Ljava/io/File;

    move-result-object p0

    .line 63
    invoke-static {p0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    const/4 p0, 0x0

    goto :goto_0

    .line 64
    :cond_0
    invoke-virtual {p0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object p0

    :goto_0
    return-object p0

    :cond_1
    const-string p0, "unsupported"

    return-object p0
.end method
