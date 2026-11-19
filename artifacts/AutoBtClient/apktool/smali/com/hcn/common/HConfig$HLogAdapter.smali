.class Lcom/hcn/common/HConfig$HLogAdapter;
.super Lcom/orhanobut/logger/AndroidLogAdapter;
.source "HConfig.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/HConfig;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "HLogAdapter"
.end annotation


# direct methods
.method public constructor <init>(Lcom/orhanobut/logger/FormatStrategy;)V
    .locals 0

    .line 151
    invoke-direct {p0, p1}, Lcom/orhanobut/logger/AndroidLogAdapter;-><init>(Lcom/orhanobut/logger/FormatStrategy;)V

    return-void
.end method


# virtual methods
.method public isLoggable(ILjava/lang/String;)Z
    .locals 1

    const/4 v0, 0x2

    if-eq p1, v0, :cond_4

    const/4 v0, 0x3

    if-eq p1, v0, :cond_3

    const/4 v0, 0x4

    if-eq p1, v0, :cond_2

    const/4 v0, 0x5

    if-eq p1, v0, :cond_1

    const/4 v0, 0x6

    if-eq p1, v0, :cond_0

    .line 171
    invoke-super {p0, p1, p2}, Lcom/orhanobut/logger/AndroidLogAdapter;->isLoggable(ILjava/lang/String;)Z

    move-result p1

    return p1

    .line 166
    :cond_0
    invoke-static {}, Lcom/hcn/common/HConfig;->-$$Nest$sfgetE_LOGGABLE()Z

    move-result p1

    return p1

    .line 164
    :cond_1
    invoke-static {}, Lcom/hcn/common/HConfig;->-$$Nest$sfgetW_LOGGABLE()Z

    move-result p1

    return p1

    .line 162
    :cond_2
    invoke-static {}, Lcom/hcn/common/HConfig;->-$$Nest$sfgetI_LOGGABLE()Z

    move-result p1

    return p1

    .line 160
    :cond_3
    invoke-static {}, Lcom/hcn/common/HConfig;->-$$Nest$sfgetD_LOGGABLE()Z

    move-result p1

    return p1

    .line 158
    :cond_4
    invoke-static {}, Lcom/hcn/common/HConfig;->-$$Nest$sfgetV_LOGGABLE()Z

    move-result p1

    return p1
.end method
