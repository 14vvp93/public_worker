.class final Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;
.super Ljava/lang/Object;
.source "MobileDataTile.java"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/NetworkController$SignalCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x12
    name = "MobileDataSignalCallback"
.end annotation


# instance fields
.field final mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

.field final synthetic this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;


# direct methods
.method private constructor <init>(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;)V
    .locals 1

    .line 237
    iput-object p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 238
    new-instance p1, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    const/4 v0, 0x0

    invoke-direct {p1, v0}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$1;)V

    iput-object p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    return-void
.end method

.method synthetic constructor <init>(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$1;)V
    .locals 0
    .param p1, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;
    .param p2, "x1"    # Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$1;

    .line 237
    invoke-direct {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;)V

    return-void
.end method


# virtual methods
.method public setIsAirplaneMode(Lcom/android/systemui/statusbar/policy/NetworkController$IconState;)V
    .locals 2
    .param p1, "icon"    # Lcom/android/systemui/statusbar/policy/NetworkController$IconState;

    .line 288
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iget-boolean v1, p1, Lcom/android/systemui/statusbar/policy/NetworkController$IconState;->visible:Z

    iput-boolean v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->airplaneModeEnabled:Z

    .line 289
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iget-boolean v0, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->airplaneModeEnabled:Z

    if-eqz v0, :cond_0

    .line 290
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    const/4 v1, 0x0

    iput v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->mobileSignalIconId:I

    .line 291
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iput v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->dataTypeIconId:I

    .line 292
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iput-boolean v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->enabled:Z

    .line 294
    :cond_0
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    invoke-static {v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->access$700(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V

    .line 295
    return-void
.end method

.method public setMobileDataEnabled(Z)V
    .locals 2
    .param p1, "enabled"    # Z

    .line 299
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    invoke-static {v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->access$800(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V

    .line 300
    return-void
.end method

.method public setMobileDataIndicators(Lcom/android/systemui/statusbar/policy/NetworkController$IconState;Lcom/android/systemui/statusbar/policy/NetworkController$IconState;IIIIZZLjava/lang/String;Ljava/lang/String;ZIZZ)V
    .locals 9
    .param p1, "statusIcon"    # Lcom/android/systemui/statusbar/policy/NetworkController$IconState;
    .param p2, "qsIcon"    # Lcom/android/systemui/statusbar/policy/NetworkController$IconState;
    .param p3, "statusType"    # I
    .param p4, "networkIcon"    # I
    .param p5, "volteIcon"    # I
    .param p6, "qsType"    # I
    .param p7, "activityIn"    # Z
    .param p8, "activityOut"    # Z
    .param p9, "typeContentDescription"    # Ljava/lang/String;
    .param p10, "description"    # Ljava/lang/String;
    .param p11, "isWide"    # Z
    .param p12, "subId"    # I
    .param p13, "roaming"    # Z
    .param p14, "isDefaultData"    # Z

    move-object v0, p0

    move-object v1, p2

    .line 254
    if-nez v1, :cond_0

    .line 256
    return-void

    .line 258
    :cond_0
    iget-object v2, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iget-boolean v3, v1, Lcom/android/systemui/statusbar/policy/NetworkController$IconState;->visible:Z

    iput-boolean v3, v2, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->enabled:Z

    .line 259
    iget-object v2, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iget v3, v1, Lcom/android/systemui/statusbar/policy/NetworkController$IconState;->icon:I

    iput v3, v2, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->mobileSignalIconId:I

    .line 260
    iget-object v2, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    move v3, p6

    iput v3, v2, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->dataTypeIconId:I

    .line 261
    iget-object v2, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    move/from16 v4, p7

    iput-boolean v4, v2, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->activityIn:Z

    .line 262
    iget-object v2, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    move/from16 v5, p8

    iput-boolean v5, v2, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->activityOut:Z

    .line 263
    iget-object v2, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    move-object/from16 v6, p10

    iput-object v6, v2, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->enabledDesc:Ljava/lang/String;

    .line 265
    iget-object v2, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    invoke-static {v2}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->access$300(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;)Ljava/lang/String;

    move-result-object v2

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "setMobileDataIndicators mInfo="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v8, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v2, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 267
    iget-object v2, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    iget-object v7, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    invoke-static {v2, v7}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->access$400(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V

    .line 268
    return-void
.end method

.method public setNoSims(ZZ)V
    .locals 3
    .param p1, "show"    # Z
    .param p2, "simDetected"    # Z

    .line 272
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iput-boolean p1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->noSim:Z

    .line 273
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iget-boolean v0, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->noSim:Z

    if-eqz v0, :cond_0

    .line 275
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    const/4 v1, 0x0

    iput v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->mobileSignalIconId:I

    .line 276
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iput v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->dataTypeIconId:I

    .line 277
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iput-boolean v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->enabled:Z

    .line 280
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->access$500(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;)Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setNoSims noSim="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 283
    :cond_0
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    invoke-static {v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->access$600(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V

    .line 284
    return-void
.end method

.method public setWifiIndicators(ZLcom/android/systemui/statusbar/policy/NetworkController$IconState;Lcom/android/systemui/statusbar/policy/NetworkController$IconState;ZZLjava/lang/String;ZLjava/lang/String;)V
    .locals 2
    .param p1, "enabled"    # Z
    .param p2, "statusIcon"    # Lcom/android/systemui/statusbar/policy/NetworkController$IconState;
    .param p3, "qsIcon"    # Lcom/android/systemui/statusbar/policy/NetworkController$IconState;
    .param p4, "activityIn"    # Z
    .param p5, "activityOut"    # Z
    .param p6, "description"    # Ljava/lang/String;
    .param p7, "isTransient"    # Z
    .param p8, "statusLabel"    # Ljava/lang/String;

    .line 244
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iput-boolean p1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->wifiEnabled:Z

    .line 245
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    iget-boolean v1, p3, Lcom/android/systemui/statusbar/policy/NetworkController$IconState;->visible:Z

    iput-boolean v1, v0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;->wifiConnected:Z

    .line 246
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$MobileDataSignalCallback;->mInfo:Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile$CallbackInfo;

    invoke-static {v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;->access$200(Lcom/mediatek/systemui/qs/tiles/ext/MobileDataTile;Ljava/lang/Object;)V

    .line 247
    return-void
.end method
