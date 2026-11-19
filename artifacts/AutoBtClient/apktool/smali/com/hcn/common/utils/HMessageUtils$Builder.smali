.class public final Lcom/hcn/common/utils/HMessageUtils$Builder;
.super Ljava/lang/Object;
.source "HMessageUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HMessageUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "Builder"
.end annotation


# instance fields
.field name:Ljava/lang/String;

.field supportGlobalUsage:Z


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 72
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 73
    iput-object v0, p0, Lcom/hcn/common/utils/HMessageUtils$Builder;->name:Ljava/lang/String;

    const/4 v0, 0x0

    .line 74
    iput-boolean v0, p0, Lcom/hcn/common/utils/HMessageUtils$Builder;->supportGlobalUsage:Z

    return-void
.end method


# virtual methods
.method public build()Lcom/hcn/common/utils/HMessageUtils;
    .locals 3

    .line 87
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils$Builder;->name:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 91
    invoke-static {}, Lcom/hcn/common/utils/HMessageUtils;->-$$Nest$sfgetsPool()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils$Builder;->name:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/utils/HMessageUtils;

    if-nez v0, :cond_0

    .line 93
    new-instance v0, Lcom/hcn/common/utils/HMessageUtils;

    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils$Builder;->name:Ljava/lang/String;

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/hcn/common/utils/HMessageUtils;-><init>(Ljava/lang/String;Lcom/hcn/common/utils/HMessageUtils-IA;)V

    .line 95
    iget-boolean v1, p0, Lcom/hcn/common/utils/HMessageUtils$Builder;->supportGlobalUsage:Z

    if-eqz v1, :cond_0

    .line 96
    invoke-static {}, Lcom/hcn/common/utils/HMessageUtils;->-$$Nest$sfgetsPool()Ljava/util/concurrent/ConcurrentHashMap;

    move-result-object v1

    invoke-virtual {v0}, Lcom/hcn/common/utils/HMessageUtils;->name()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2, v0}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_0
    return-object v0

    .line 88
    :cond_1
    new-instance v0, Ljava/security/InvalidParameterException;

    const-string v1, "HMessageUtils must have a name!"

    invoke-direct {v0, v1}, Ljava/security/InvalidParameterException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public setName(Ljava/lang/String;)Lcom/hcn/common/utils/HMessageUtils$Builder;
    .locals 0

    .line 77
    iput-object p1, p0, Lcom/hcn/common/utils/HMessageUtils$Builder;->name:Ljava/lang/String;

    return-object p0
.end method

.method public setSupportGlobalUsage(Z)Lcom/hcn/common/utils/HMessageUtils$Builder;
    .locals 0

    .line 82
    iput-boolean p1, p0, Lcom/hcn/common/utils/HMessageUtils$Builder;->supportGlobalUsage:Z

    return-object p0
.end method
