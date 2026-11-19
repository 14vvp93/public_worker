.class Lcom/hcn/common/misc/LogUtils$2;
.super Ljava/lang/Object;
.source "LogUtils.java"

# interfaces
.implements Ljava/io/FilenameFilter;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/LogUtils;->getLogFiles()Ljava/util/List;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .line 257
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public accept(Ljava/io/File;Ljava/lang/String;)Z
    .locals 0

    .line 260
    invoke-static {p2}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$smisMatchLogFileName(Ljava/lang/String;)Z

    move-result p1

    return p1
.end method
