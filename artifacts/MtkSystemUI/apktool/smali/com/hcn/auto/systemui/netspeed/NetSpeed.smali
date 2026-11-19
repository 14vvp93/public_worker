.class public Lcom/hcn/auto/systemui/netspeed/NetSpeed;
.super Ljava/lang/Object;
.source "NetSpeed.java"


# static fields
.field private static final TAG:Ljava/lang/String;

.field private static lastTimeStamp:J

.field private static lastTotalRxBytes:J


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 12
    const-class v0, Lcom/hcn/auto/systemui/netspeed/NetSpeed;

    invoke-virtual {v0}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->TAG:Ljava/lang/String;

    .line 13
    const-wide/16 v0, 0x0

    sput-wide v0, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->lastTotalRxBytes:J

    .line 14
    sput-wide v0, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->lastTimeStamp:J

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getNetFileSizeDescription(J)Ljava/lang/String;
    .locals 7
    .param p0, "size"    # J

    .line 88
    new-instance v0, Ljava/lang/StringBuffer;

    invoke-direct {v0}, Ljava/lang/StringBuffer;-><init>()V

    .line 89
    .local v0, "bytes":Ljava/lang/StringBuffer;
    new-instance v1, Ljava/text/DecimalFormat;

    const-string v2, "###"

    invoke-direct {v1, v2}, Ljava/text/DecimalFormat;-><init>(Ljava/lang/String;)V

    .line 90
    .local v1, "format":Ljava/text/DecimalFormat;
    const-wide/32 v2, 0x3b9aca00

    cmp-long v2, p0, v2

    if-ltz v2, :cond_0

    .line 91
    long-to-double v2, p0

    const-wide v4, 0x41cdcd6500000000L    # 1.0E9

    div-double/2addr v2, v4

    .line 92
    .local v2, "i":D
    invoke-virtual {v1, v2, v3}, Ljava/text/DecimalFormat;->format(D)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v4}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v4, "GB/s"

    invoke-virtual {v0, v4}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 93
    .end local v2    # "i":D
    goto :goto_0

    :cond_0
    const-wide/32 v2, 0xf4240

    cmp-long v2, p0, v2

    if-ltz v2, :cond_1

    .line 94
    long-to-double v2, p0

    const-wide v4, 0x412e848000000000L    # 1000000.0

    div-double/2addr v2, v4

    .line 95
    .restart local v2    # "i":D
    invoke-virtual {v1, v2, v3}, Ljava/text/DecimalFormat;->format(D)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v4}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v4, "MB/s"

    invoke-virtual {v0, v4}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 96
    .end local v2    # "i":D
    goto :goto_0

    :cond_1
    const-wide/16 v2, 0x3e8

    cmp-long v2, p0, v2

    const-wide v3, 0x408f400000000000L    # 1000.0

    if-ltz v2, :cond_2

    .line 97
    long-to-double v5, p0

    div-double/2addr v5, v3

    .line 98
    .local v5, "i":D
    invoke-virtual {v1, v5, v6}, Ljava/text/DecimalFormat;->format(D)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    const-string v2, "KB/s"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 99
    .end local v5    # "i":D
    goto :goto_0

    :cond_2
    long-to-double v5, p0

    cmpg-double v2, v5, v3

    if-gez v2, :cond_4

    .line 100
    const-wide/16 v2, 0x0

    cmp-long v2, p0, v2

    if-gtz v2, :cond_3

    .line 101
    const-string v2, "0B/s"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    goto :goto_0

    .line 103
    :cond_3
    long-to-int v2, p0

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(I)Ljava/lang/StringBuffer;

    const-string v2, "B/s"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    .line 106
    :cond_4
    :goto_0
    invoke-virtual {v0}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v2

    return-object v2
.end method

.method public static getNetSpeed(I)Ljava/lang/String;
    .locals 18
    .param p0, "uid"    # I

    .line 37
    const-string v1, "0B/s"

    .line 38
    .local v1, "mSpeed":Ljava/lang/String;
    const-wide/16 v2, 0x0

    .line 39
    .local v2, "nowTotalRxBytes":J
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    .line 40
    .local v4, "nowTimeStamp":J
    sget-wide v6, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->lastTimeStamp:J

    sub-long v6, v4, v6

    .line 41
    .local v6, "difTime":J
    const-wide/16 v8, 0x0

    cmp-long v0, v6, v8

    if-gtz v0, :cond_0

    .line 42
    const-string v0, "0B/s"

    .line 42
    .end local v1    # "mSpeed":Ljava/lang/String;
    .local v0, "mSpeed":Ljava/lang/String;
    goto/16 :goto_5

    .line 44
    .end local v0    # "mSpeed":Ljava/lang/String;
    .restart local v1    # "mSpeed":Ljava/lang/String;
    :cond_0
    const/4 v8, 0x0

    .line 45
    .local v8, "stream":Ljava/io/FileReader;
    const/4 v0, 0x0

    move-object v9, v0

    .line 48
    .local v9, "in":Ljava/io/BufferedReader;
    :try_start_0
    new-instance v0, Ljava/io/FileReader;

    const-string v10, "/proc/net/xt_qtaguid/stats"

    invoke-direct {v0, v10}, Ljava/io/FileReader;-><init>(Ljava/lang/String;)V

    move-object v8, v0

    .line 49
    new-instance v0, Ljava/io/BufferedReader;

    const/16 v10, 0x1f4

    invoke-direct {v0, v8, v10}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;I)V

    move-object v9, v0

    .line 52
    :goto_0
    invoke-virtual {v9}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v0

    move-object v10, v0

    .line 52
    .local v10, "line":Ljava/lang/String;
    if-eqz v0, :cond_3

    .line 53
    const-string v0, " "

    invoke-virtual {v10, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    .line 54
    .local v0, "dataStrings":[Ljava/lang/String;
    const/4 v11, 0x1

    aget-object v11, v0, v11

    invoke-virtual {v11}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v11

    .line 55
    .local v11, "iface":Ljava/lang/String;
    const-string/jumbo v12, "wlan"

    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v12

    if-nez v12, :cond_1

    const-string v12, "bt-pan"

    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v12

    if-nez v12, :cond_1

    const-string v12, "usb"

    .line 56
    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v12

    if-nez v12, :cond_1

    const-string v12, "ccmni"

    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v12

    if-nez v12, :cond_1

    const-string v12, "iph"

    .line 57
    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v12

    if-nez v12, :cond_1

    const-string v12, "and0"

    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v12

    if-eqz v12, :cond_2

    .line 59
    :cond_1
    const/4 v12, 0x5

    aget-object v12, v0, v12

    invoke-virtual {v12}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v12

    .line 60
    .local v12, "rx_bytes":Ljava/lang/String;
    const/16 v13, 0x9

    aget-object v13, v0, v13

    invoke-virtual {v13}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v13

    .line 61
    .local v13, "rx_tcp_bytes":Ljava/lang/String;
    invoke-static {v12}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v14

    invoke-static {v13}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v16
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_2
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    add-long v14, v14, v16

    add-long/2addr v2, v14

    .line 63
    .end local v11    # "iface":Ljava/lang/String;
    .end local v12    # "rx_bytes":Ljava/lang/String;
    .end local v13    # "rx_tcp_bytes":Ljava/lang/String;
    :cond_2
    goto :goto_0

    .line 68
    .end local v0    # "dataStrings":[Ljava/lang/String;
    .end local v10    # "line":Ljava/lang/String;
    :cond_3
    :try_start_1
    invoke-virtual {v9}, Ljava/io/BufferedReader;->close()V
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_0

    .line 71
    goto :goto_1

    .line 69
    :catch_0
    move-exception v0

    move-object v10, v0

    .line 70
    .local v0, "e":Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    .line 73
    .end local v0    # "e":Ljava/io/IOException;
    :goto_1
    :try_start_2
    invoke-virtual {v8}, Ljava/io/FileReader;->close()V
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_1

    .line 76
    :goto_2
    goto :goto_4

    .line 74
    :catch_1
    move-exception v0

    move-object v10, v0

    .line 75
    .restart local v0    # "e":Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    .line 77
    .end local v0    # "e":Ljava/io/IOException;
    goto :goto_4

    .line 67
    :catchall_0
    move-exception v0

    move-wide v10, v2

    move-object v2, v0

    goto :goto_6

    .line 64
    :catch_2
    move-exception v0

    .line 65
    .local v0, "e":Ljava/lang/Exception;
    :try_start_3
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    .line 68
    .end local v0    # "e":Ljava/lang/Exception;
    :try_start_4
    invoke-virtual {v9}, Ljava/io/BufferedReader;->close()V
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_3

    .line 71
    goto :goto_3

    .line 69
    :catch_3
    move-exception v0

    move-object v10, v0

    .line 70
    .local v0, "e":Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    .line 73
    .end local v0    # "e":Ljava/io/IOException;
    :goto_3
    :try_start_5
    invoke-virtual {v8}, Ljava/io/FileReader;->close()V
    :try_end_5
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_5} :catch_1

    goto :goto_2

    .line 79
    :goto_4
    sget-wide v10, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->lastTotalRxBytes:J

    sub-long v10, v2, v10

    const-wide/16 v12, 0x3e8

    mul-long/2addr v10, v12

    div-long/2addr v10, v6

    .line 80
    .local v10, "speedTotal":J
    invoke-static {v10, v11}, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->getNetFileSizeDescription(J)Ljava/lang/String;

    move-result-object v0

    .line 82
    .end local v1    # "mSpeed":Ljava/lang/String;
    .end local v8    # "stream":Ljava/io/FileReader;
    .end local v9    # "in":Ljava/io/BufferedReader;
    .end local v10    # "speedTotal":J
    .local v0, "mSpeed":Ljava/lang/String;
    :goto_5
    sput-wide v4, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->lastTimeStamp:J

    .line 83
    sput-wide v2, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->lastTotalRxBytes:J

    .line 84
    return-object v0

    .line 67
    .end local v0    # "mSpeed":Ljava/lang/String;
    .end local v2    # "nowTotalRxBytes":J
    .restart local v1    # "mSpeed":Ljava/lang/String;
    .restart local v8    # "stream":Ljava/io/FileReader;
    .restart local v9    # "in":Ljava/io/BufferedReader;
    .local v10, "nowTotalRxBytes":J
    :goto_6
    nop

    .line 68
    :try_start_6
    invoke-virtual {v9}, Ljava/io/BufferedReader;->close()V
    :try_end_6
    .catch Ljava/io/IOException; {:try_start_6 .. :try_end_6} :catch_4

    .line 71
    goto :goto_7

    .line 69
    :catch_4
    move-exception v0

    move-object v3, v0

    .line 70
    .local v0, "e":Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    .line 73
    .end local v0    # "e":Ljava/io/IOException;
    :goto_7
    :try_start_7
    invoke-virtual {v8}, Ljava/io/FileReader;->close()V
    :try_end_7
    .catch Ljava/io/IOException; {:try_start_7 .. :try_end_7} :catch_5

    .line 76
    goto :goto_8

    .line 74
    :catch_5
    move-exception v0

    move-object v3, v0

    .line 75
    .restart local v0    # "e":Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    .line 76
    .end local v0    # "e":Ljava/io/IOException;
    :goto_8
    throw v2
.end method

.method public static reset()V
    .locals 2

    .line 32
    const-wide/16 v0, 0x0

    sput-wide v0, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->lastTotalRxBytes:J

    .line 33
    sput-wide v0, Lcom/hcn/auto/systemui/netspeed/NetSpeed;->lastTimeStamp:J

    .line 34
    return-void
.end method

.method public static showNetSpeed(Landroid/content/Context;)Z
    .locals 3
    .param p0, "context"    # Landroid/content/Context;

    .line 28
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "real_time_network"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    goto :goto_0

    :cond_0
    move v1, v2

    :goto_0
    return v1
.end method
