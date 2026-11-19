.class public final Lcom/hcn/common/utils/HCrashUtils$CrashInfo;
.super Ljava/lang/Object;
.source "HCrashUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HCrashUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "CrashInfo"
.end annotation


# instance fields
.field private final mFileHeadProvider:Lcom/hcn/common/utils/HUtilsEx$FileHead;

.field private final mThrowable:Ljava/lang/Throwable;


# direct methods
.method private constructor <init>(Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 126
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 127
    iput-object p2, p0, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;->mThrowable:Ljava/lang/Throwable;

    .line 128
    new-instance p2, Lcom/hcn/common/utils/HUtilsEx$FileHead;

    const-string v0, "Crash"

    invoke-direct {p2, v0}, Lcom/hcn/common/utils/HUtilsEx$FileHead;-><init>(Ljava/lang/String;)V

    iput-object p2, p0, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;->mFileHeadProvider:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    const-string v0, "Time Of Crash"

    .line 129
    invoke-virtual {p2, v0, p1}, Lcom/hcn/common/utils/HUtilsEx$FileHead;->addFirst(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method synthetic constructor <init>(Ljava/lang/String;Ljava/lang/Throwable;Lcom/hcn/common/utils/HCrashUtils$CrashInfo-IA;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method


# virtual methods
.method public final addExtraHead(Ljava/lang/String;Ljava/lang/String;)V
    .locals 1

    .line 137
    iget-object v0, p0, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;->mFileHeadProvider:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    invoke-virtual {v0, p1, p2}, Lcom/hcn/common/utils/HUtilsEx$FileHead;->append(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method public final addExtraHead(Ljava/util/Map;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;)V"
        }
    .end annotation

    .line 133
    iget-object v0, p0, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;->mFileHeadProvider:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    invoke-virtual {v0, p1}, Lcom/hcn/common/utils/HUtilsEx$FileHead;->append(Ljava/util/Map;)V

    return-void
.end method

.method public final getThrowable()Ljava/lang/Throwable;
    .locals 1

    .line 141
    iget-object v0, p0, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;->mThrowable:Ljava/lang/Throwable;

    return-object v0
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    .line 147
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;->mFileHeadProvider:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    invoke-virtual {v1}, Lcom/hcn/common/utils/HUtilsEx$FileHead;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/utils/HCrashUtils$CrashInfo;->mThrowable:Ljava/lang/Throwable;

    .line 148
    invoke-static {v1}, Lcom/hcn/common/utils/HThrowableUtils;->getFullStackTrace(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
