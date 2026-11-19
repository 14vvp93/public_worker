.class Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;
.super Ljava/lang/Object;
.source "SimDataConnectionTile.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "SimDataSwitchStateMachine"
.end annotation


# instance fields
.field private mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

.field private mDataTimerHandler:Landroid/os/Handler;

.field private mIsAirlineMode:Z

.field protected mIsUserSwitching:Z

.field mMmsOngoing:Z

.field private mPhoneStateListener:[Landroid/telephony/PhoneStateListener;

.field mSimConnStateTrackerReady:Z

.field private mSimStateIntentReceiver:Landroid/content/BroadcastReceiver;

.field private mSlotCount:I

.field mTelephonyManager:Landroid/telephony/TelephonyManager;

.field final synthetic this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;


# direct methods
.method public constructor <init>(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)V
    .locals 1

    .line 168
    iput-object p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 154
    sget-object v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->NO_SIM:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    .line 162
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mSlotCount:I

    .line 415
    new-instance v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine$1;

    invoke-direct {v0, p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine$1;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mDataTimerHandler:Landroid/os/Handler;

    .line 438
    new-instance v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine$2;

    invoke-direct {v0, p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine$2;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mSimStateIntentReceiver:Landroid/content/BroadcastReceiver;

    .line 169
    invoke-static {p1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$200(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;

    move-result-object p1

    const-string v0, "phone"

    .line 170
    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/telephony/TelephonyManager;

    iput-object p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mTelephonyManager:Landroid/telephony/TelephonyManager;

    .line 172
    invoke-static {}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->getSlotCount()I

    move-result p1

    iput p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mSlotCount:I

    .line 173
    iget p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mSlotCount:I

    new-array p1, p1, [Landroid/telephony/PhoneStateListener;

    iput-object p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mPhoneStateListener:[Landroid/telephony/PhoneStateListener;

    .line 174
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    .line 148
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->registerReceiver()V

    return-void
.end method

.method static synthetic access$100(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    .line 148
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->unregisterReceiver()V

    return-void
.end method

.method static synthetic access$1100(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    .line 148
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->updateSimConnTile()V

    return-void
.end method

.method static synthetic access$1200(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;Z)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;
    .param p1, "x1"    # Z

    .line 148
    invoke-direct {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->setAirplaneMode(Z)V

    return-void
.end method

.method static synthetic access$1300(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;Landroid/content/Intent;)Lcom/android/internal/telephony/PhoneConstants$DataState;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;
    .param p1, "x1"    # Landroid/content/Intent;

    .line 148
    invoke-direct {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->getMobileDataState(Landroid/content/Intent;)Lcom/android/internal/telephony/PhoneConstants$DataState;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$1400(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)Z
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    .line 148
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isMmsOngoing()Z

    move-result v0

    return v0
.end method

.method static synthetic access$1500(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    .line 148
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->unRegisterPhoneStateListener()V

    return-void
.end method

.method static synthetic access$1600(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;

    .line 148
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->registerPhoneStateListener()V

    return-void
.end method

.method static synthetic access$1700(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;Z)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;
    .param p1, "x1"    # Z

    .line 148
    invoke-direct {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->setIsMmsOnging(Z)V

    return-void
.end method

.method private enterNextState(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;)V
    .locals 4
    .param p1, "state"    # Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    .line 325
    const-string v0, "SimDataConnectionTile"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "enterNextState state is "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 326
    sget-object v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$1;->$SwitchMap$com$mediatek$systemui$qs$tiles$ext$SimDataConnectionTile$SIMConnState:[I

    invoke-virtual {p1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->ordinal()I

    move-result v1

    aget v0, v0, v1

    const/4 v1, 0x1

    const/4 v2, 0x0

    packed-switch v0, :pswitch_data_0

    goto/16 :goto_0

    .line 366
    :pswitch_0
    const-string v0, "SimDataConnectionTile"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Try to switch from Sim2 to Sim1! mSimCurrentCurrentState="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 368
    invoke-direct {p0, v2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->switchDataDefaultSIM(I)V

    .line 369
    goto/16 :goto_0

    .line 361
    :pswitch_1
    const-string v0, "SimDataConnectionTile"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Try to switch from Sim1 to Sim2! mSimCurrentCurrentState="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 363
    invoke-direct {p0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->switchDataDefaultSIM(I)V

    .line 364
    goto/16 :goto_0

    .line 355
    :pswitch_2
    const-string v0, "SimDataConnectionTile"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Try to switch from Sim2 to Sim1! mSimCurrentCurrentState="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 357
    sget-object v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_E_E:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    .line 358
    invoke-direct {p0, v2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->switchDataDefaultSIM(I)V

    .line 359
    goto :goto_0

    .line 349
    :pswitch_3
    const-string v0, "SimDataConnectionTile"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Try to switch from Sim2 to Sim1! mSimCurrentCurrentState="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 351
    sget-object v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_E_D:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    .line 352
    invoke-direct {p0, v2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->switchDataDefaultSIM(I)V

    .line 353
    goto :goto_0

    .line 343
    :pswitch_4
    const-string v0, "SimDataConnectionTile"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Try to switch from Sim1 to Sim2! mSimCurrentCurrentState="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 345
    sget-object v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_E_E:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    .line 346
    invoke-direct {p0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->switchDataDefaultSIM(I)V

    .line 347
    goto :goto_0

    .line 337
    :pswitch_5
    const-string v0, "SimDataConnectionTile"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Try to switch from Sim1 to Sim2! mSimCurrentCurrentState="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 339
    sget-object v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_E_D:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    .line 340
    invoke-direct {p0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->switchDataDefaultSIM(I)V

    .line 341
    goto :goto_0

    .line 334
    :pswitch_6
    const-string v0, "SimDataConnectionTile"

    const-string v1, "No Sim or one Sim do nothing!"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 335
    nop

    .line 373
    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_6
        :pswitch_6
        :pswitch_6
        :pswitch_6
        :pswitch_6
        :pswitch_6
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private getMobileDataState(Landroid/content/Intent;)Lcom/android/internal/telephony/PhoneConstants$DataState;
    .locals 2
    .param p1, "intent"    # Landroid/content/Intent;

    .line 534
    const-string v0, "state"

    invoke-virtual {p1, v0}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 535
    .local v0, "str":Ljava/lang/String;
    if-eqz v0, :cond_0

    .line 536
    const-class v1, Lcom/android/internal/telephony/PhoneConstants$DataState;

    invoke-static {v1, v0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v1

    check-cast v1, Lcom/android/internal/telephony/PhoneConstants$DataState;

    return-object v1

    .line 538
    :cond_0
    sget-object v1, Lcom/android/internal/telephony/PhoneConstants$DataState;->DISCONNECTED:Lcom/android/internal/telephony/PhoneConstants$DataState;

    return-object v1
.end method

.method private getPhoneStateListener(II)Landroid/telephony/PhoneStateListener;
    .locals 2
    .param p1, "subId"    # I
    .param p2, "slotId"    # I

    .line 562
    new-instance v0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine$3;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-direct {v0, p0, v1, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine$3;-><init>(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;Ljava/lang/Integer;I)V

    return-object v0
.end method

.method private handleDataConnectionChange(I)V
    .locals 9
    .param p1, "newSlot"    # I

    .line 384
    const-string v0, "SimDataConnectionTile"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "handleDataConnectionChange, newSlot="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 385
    invoke-static {}, Landroid/telephony/SubscriptionManager;->getDefaultDataSubscriptionId()I

    move-result v0

    invoke-static {v0}, Landroid/telephony/SubscriptionManager;->getSlotIndex(I)I

    move-result v0

    if-eq v0, p1, :cond_2

    .line 387
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mDataTimerHandler:Landroid/os/Handler;

    const/16 v1, 0x7d0

    const-wide/16 v2, 0x7530

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    .line 389
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$900(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Landroid/telephony/SubscriptionManager;->from(Landroid/content/Context;)Landroid/telephony/SubscriptionManager;

    move-result-object v0

    .line 390
    invoke-virtual {v0}, Landroid/telephony/SubscriptionManager;->getActiveSubscriptionInfoList()Ljava/util/List;

    move-result-object v0

    .line 391
    .local v0, "si":Ljava/util/List;, "Ljava/util/List<Landroid/telephony/SubscriptionInfo;>;"
    if-eqz v0, :cond_2

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v1

    if-lez v1, :cond_2

    .line 393
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mTelephonyManager:Landroid/telephony/TelephonyManager;

    invoke-virtual {v1}, Landroid/telephony/TelephonyManager;->getDataEnabled()Z

    move-result v1

    .line 394
    .local v1, "dataEnabled":Z
    const/4 v2, 0x0

    move v3, v2

    .local v3, "i":I
    :goto_0
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v4

    if-ge v3, v4, :cond_2

    .line 395
    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/telephony/SubscriptionInfo;

    .line 396
    .local v4, "subInfo":Landroid/telephony/SubscriptionInfo;
    invoke-virtual {v4}, Landroid/telephony/SubscriptionInfo;->getSubscriptionId()I

    move-result v5

    .line 397
    .local v5, "subId":I
    invoke-virtual {v4}, Landroid/telephony/SubscriptionInfo;->getSimSlotIndex()I

    move-result v6

    if-ne p1, v6, :cond_0

    .line 398
    const-string v6, "SimDataConnectionTile"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "handleDataConnectionChange. newSlot = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " subId = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 400
    iget-object v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v6}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$1000(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;

    move-result-object v6

    invoke-static {v6}, Landroid/telephony/SubscriptionManager;->from(Landroid/content/Context;)Landroid/telephony/SubscriptionManager;

    move-result-object v6

    invoke-virtual {v6, v5}, Landroid/telephony/SubscriptionManager;->setDefaultDataSubId(I)V

    .line 401
    if-eqz v1, :cond_1

    .line 402
    iget-object v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mTelephonyManager:Landroid/telephony/TelephonyManager;

    const/4 v7, 0x1

    invoke-virtual {v6, v5, v7}, Landroid/telephony/TelephonyManager;->setDataEnabled(IZ)V

    goto :goto_1

    .line 405
    :cond_0
    if-eqz v1, :cond_1

    .line 406
    iget-object v6, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mTelephonyManager:Landroid/telephony/TelephonyManager;

    invoke-virtual {v6, v5, v2}, Landroid/telephony/TelephonyManager;->setDataEnabled(IZ)V

    .line 394
    .end local v4    # "subInfo":Landroid/telephony/SubscriptionInfo;
    :cond_1
    :goto_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 412
    .end local v0    # "si":Ljava/util/List;, "Ljava/util/List<Landroid/telephony/SubscriptionInfo;>;"
    .end local v1    # "dataEnabled":Z
    .end local v3    # "i":I
    .end local v5    # "subId":I
    :cond_2
    return-void
.end method

.method private isAirplaneMode()Z
    .locals 1

    .line 522
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mIsAirlineMode:Z

    return v0
.end method

.method private isDataConnected()Z
    .locals 2

    .line 507
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getDataState()I

    move-result v0

    const/4 v1, 0x2

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private isMmsOngoing()Z
    .locals 1

    .line 514
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mMmsOngoing:Z

    return v0
.end method

.method private isRadioOn(I)Z
    .locals 2
    .param p1, "slotId"    # I

    .line 306
    invoke-static {p1}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->getFirstSubInSlot(I)I

    move-result v0

    .line 307
    .local v0, "subId1":I
    invoke-static {v0}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->isRadioOn(I)Z

    move-result v1

    return v1
.end method

.method private isSimEnable(Ljava/util/List;I)Z
    .locals 1
    .param p2, "slotId"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/telephony/SubscriptionInfo;",
            ">;I)Z"
        }
    .end annotation

    .line 294
    .local p1, "infos":Ljava/util/List;, "Ljava/util/List<Landroid/telephony/SubscriptionInfo;>;"
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v0, p1, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$700(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;Ljava/util/List;I)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 295
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isAirplaneMode()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-direct {p0, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isRadioOn(I)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 296
    invoke-direct {p0, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimLocked(I)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 294
    :goto_0
    return v0
.end method

.method private isSimInsertedWithUnAvaliable(Ljava/util/List;I)Z
    .locals 1
    .param p2, "slotId"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/telephony/SubscriptionInfo;",
            ">;I)Z"
        }
    .end annotation

    .line 301
    .local p1, "infos":Ljava/util/List;, "Ljava/util/List<Landroid/telephony/SubscriptionInfo;>;"
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v0, p1, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$700(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;Ljava/util/List;I)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 302
    invoke-direct {p0, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isRadioOn(I)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isAirplaneMode()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-direct {p0, p2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimLocked(I)Z

    move-result v0

    if-eqz v0, :cond_1

    :cond_0
    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    .line 301
    :goto_0
    return v0
.end method

.method private isSimLocked(I)Z
    .locals 5
    .param p1, "slotId"    # I

    .line 311
    invoke-static {}, Landroid/telephony/TelephonyManager;->getDefault()Landroid/telephony/TelephonyManager;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/telephony/TelephonyManager;->getSimState(I)I

    move-result v0

    .line 312
    .local v0, "simState":I
    const/4 v1, 0x2

    if-eq v0, v1, :cond_1

    const/4 v1, 0x3

    if-eq v0, v1, :cond_1

    const/4 v1, 0x4

    if-ne v0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v1, 0x1

    .line 315
    .local v1, "bSimLocked":Z
    :goto_1
    const-string v2, "SimDataConnectionTile"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "isSimLocked, slotId="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, " simState="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, " bSimLocked= "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 317
    return v1
.end method

.method private isUserSwitching()Z
    .locals 1

    .line 530
    iget-boolean v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mIsUserSwitching:Z

    return v0
.end method

.method private registerPhoneStateListener()V
    .locals 5

    .line 549
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    iget v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mSlotCount:I

    if-ge v0, v1, :cond_1

    .line 550
    invoke-static {v0}, Lcom/mediatek/systemui/statusbar/util/SIMHelper;->getFirstSubInSlot(I)I

    move-result v1

    .line 551
    .local v1, "subId":I
    if-ltz v1, :cond_0

    .line 552
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mPhoneStateListener:[Landroid/telephony/PhoneStateListener;

    invoke-direct {p0, v1, v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->getPhoneStateListener(II)Landroid/telephony/PhoneStateListener;

    move-result-object v3

    aput-object v3, v2, v0

    .line 553
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mTelephonyManager:Landroid/telephony/TelephonyManager;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mPhoneStateListener:[Landroid/telephony/PhoneStateListener;

    aget-object v3, v3, v0

    const/4 v4, 0x1

    invoke-virtual {v2, v3, v4}, Landroid/telephony/TelephonyManager;->listen(Landroid/telephony/PhoneStateListener;I)V

    goto :goto_1

    .line 556
    :cond_0
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mPhoneStateListener:[Landroid/telephony/PhoneStateListener;

    const/4 v3, 0x0

    aput-object v3, v2, v0

    .line 549
    .end local v1    # "subId":I
    :goto_1
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 559
    .end local v0    # "i":I
    :cond_1
    return-void
.end method

.method private registerReceiver()V
    .locals 3

    .line 177
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 178
    .local v0, "simIntentFilter":Landroid/content/IntentFilter;
    const-string v1, "android.intent.action.AIRPLANE_MODE"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 179
    const-string v1, "com.android.mms.transaction.START"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 180
    const-string v1, "com.android.mms.transaction.STOP"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 181
    const-string v1, "android.intent.action.ACTION_SUBINFO_CONTENT_CHANGE"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 182
    const-string v1, "android.intent.action.ACTION_SUBINFO_RECORD_UPDATED"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 183
    const-string v1, "android.intent.action.SIM_STATE_CHANGED"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 184
    const-string v1, "android.intent.action.ANY_DATA_STATE"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 185
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$300(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mSimStateIntentReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 186
    return-void
.end method

.method private setAirplaneMode(Z)V
    .locals 0
    .param p1, "airplaneMode"    # Z

    .line 518
    iput-boolean p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mIsAirlineMode:Z

    .line 519
    return-void
.end method

.method private setIsMmsOnging(Z)V
    .locals 0
    .param p1, "ongoing"    # Z

    .line 510
    iput-boolean p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mMmsOngoing:Z

    .line 511
    return-void
.end method

.method private setUserSwitching(Z)V
    .locals 0
    .param p1, "userSwitching"    # Z

    .line 526
    iput-boolean p1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mIsUserSwitching:Z

    .line 527
    return-void
.end method

.method private switchDataDefaultSIM(I)V
    .locals 1
    .param p1, "slotId"    # I

    .line 376
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$800(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 377
    const/4 v0, 0x1

    invoke-direct {p0, v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->setUserSwitching(Z)V

    .line 379
    invoke-direct {p0, p1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->handleDataConnectionChange(I)V

    .line 381
    :cond_0
    return-void
.end method

.method private unRegisterPhoneStateListener()V
    .locals 4

    .line 573
    const/4 v0, 0x0

    move v1, v0

    .local v1, "i":I
    :goto_0
    iget v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mSlotCount:I

    if-ge v1, v2, :cond_1

    .line 574
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mPhoneStateListener:[Landroid/telephony/PhoneStateListener;

    aget-object v2, v2, v1

    if-eqz v2, :cond_0

    .line 575
    iget-object v2, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mTelephonyManager:Landroid/telephony/TelephonyManager;

    iget-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mPhoneStateListener:[Landroid/telephony/PhoneStateListener;

    aget-object v3, v3, v1

    invoke-virtual {v2, v3, v0}, Landroid/telephony/TelephonyManager;->listen(Landroid/telephony/PhoneStateListener;I)V

    .line 573
    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 579
    .end local v1    # "i":I
    :cond_1
    return-void
.end method

.method private unregisterReceiver()V
    .locals 2

    .line 189
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$400(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mSimStateIntentReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 190
    return-void
.end method

.method private updateSimConnTile()V
    .locals 2

    .line 197
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$500(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->onActualStateChange(Landroid/content/Context;Landroid/content/Intent;)V

    .line 198
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-virtual {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->refreshState()V

    .line 199
    return-void
.end method


# virtual methods
.method public getCurrentSimConnState()Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;
    .locals 1

    .line 165
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    return-object v0
.end method

.method public isClickable()Z
    .locals 4

    .line 496
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 497
    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$1800(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Landroid/telephony/SubscriptionManager;->from(Landroid/content/Context;)Landroid/telephony/SubscriptionManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/telephony/SubscriptionManager;->getActiveSubscriptionInfoList()Ljava/util/List;

    move-result-object v0

    .line 498
    .local v0, "infos":Ljava/util/List;, "Ljava/util/List<Landroid/telephony/SubscriptionInfo;>;"
    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    const/4 v2, 0x0

    invoke-static {v1, v0, v2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$700(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;Ljava/util/List;I)Z

    move-result v1

    const/4 v3, 0x1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    .line 499
    invoke-static {v1, v0, v3}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$700(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;Ljava/util/List;I)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 500
    :cond_0
    invoke-direct {p0, v2}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isRadioOn(I)Z

    move-result v1

    if-nez v1, :cond_1

    invoke-direct {p0, v3}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isRadioOn(I)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 501
    :cond_1
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isAirplaneMode()Z

    move-result v1

    if-nez v1, :cond_2

    .line 502
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isMmsOngoing()Z

    move-result v1

    if-nez v1, :cond_2

    .line 503
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isUserSwitching()Z

    move-result v1

    if-nez v1, :cond_2

    .line 498
    move v2, v3

    goto :goto_0

    .line 503
    :cond_2
    nop

    .line 498
    :goto_0
    return v2
.end method

.method public onActualStateChange(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 12
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 207
    nop

    .line 208
    invoke-static {p1}, Landroid/telephony/SubscriptionManager;->from(Landroid/content/Context;)Landroid/telephony/SubscriptionManager;

    move-result-object v0

    invoke-virtual {v0}, Landroid/telephony/SubscriptionManager;->getActiveSubscriptionInfoList()Ljava/util/List;

    move-result-object v0

    .line 210
    .local v0, "infos":Ljava/util/List;, "Ljava/util/List<Landroid/telephony/SubscriptionInfo;>;"
    const/4 v1, 0x0

    invoke-direct {p0, v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimEnable(Ljava/util/List;I)Z

    move-result v2

    .line 211
    .local v2, "sim1Enable":Z
    const/4 v3, 0x1

    invoke-direct {p0, v0, v3}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimEnable(Ljava/util/List;I)Z

    move-result v4

    .line 213
    .local v4, "sim2Enable":Z
    const/4 v5, 0x0

    .line 214
    .local v5, "sim1Conn":Z
    const/4 v6, 0x0

    .line 216
    .local v6, "sim2Conn":Z
    invoke-static {}, Landroid/telephony/SubscriptionManager;->getDefaultDataSubscriptionId()I

    move-result v7

    .line 217
    .local v7, "dataConnectionId":I
    invoke-static {v7}, Landroid/telephony/SubscriptionManager;->getSlotIndex(I)I

    move-result v8

    if-nez v8, :cond_0

    .line 218
    const/4 v5, 0x1

    .line 219
    const/4 v6, 0x0

    goto :goto_0

    .line 220
    :cond_0
    invoke-static {v7}, Landroid/telephony/SubscriptionManager;->getSlotIndex(I)I

    move-result v8

    if-ne v8, v3, :cond_1

    .line 222
    const/4 v5, 0x0

    .line 223
    const/4 v6, 0x1

    .line 226
    :cond_1
    :goto_0
    const-string v8, "SimDataConnectionTile"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "SimConnStateTracker onActualStateChange sim1Enable = "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v10, ", sim2Enable = "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 228
    if-nez v2, :cond_5

    if-eqz v4, :cond_2

    goto :goto_1

    .line 280
    :cond_2
    invoke-direct {p0, v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimInsertedWithUnAvaliable(Ljava/util/List;I)Z

    move-result v8

    if-eqz v8, :cond_3

    if-eqz v5, :cond_3

    .line 282
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_D_F:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto/16 :goto_3

    .line 283
    :cond_3
    invoke-direct {p0, v0, v3}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimInsertedWithUnAvaliable(Ljava/util/List;I)Z

    move-result v3

    if-eqz v3, :cond_4

    if-eqz v6, :cond_4

    .line 285
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_D_F:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto/16 :goto_3

    .line 287
    :cond_4
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->NO_SIM:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto/16 :goto_3

    .line 229
    :cond_5
    :goto_1
    invoke-direct {p0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isDataConnected()Z

    move-result v8

    .line 231
    .local v8, "dataConnected":Z
    const-string v9, "SimDataConnectionTile"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "onActualStateChange, dataConnected = "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10, v8}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v11, ", sim1Enable = "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v11, ", sim2Enable = "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10, v4}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v11, ", sim1Conn = "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v11, ", sim2Conn = "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v10, v6}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 234
    if-eqz v8, :cond_b

    .line 235
    if-eqz v2, :cond_7

    if-eqz v4, :cond_7

    .line 236
    if-eqz v5, :cond_6

    .line 237
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_E_E:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto/16 :goto_2

    .line 239
    :cond_6
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_E_E:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto/16 :goto_2

    .line 241
    :cond_7
    if-nez v2, :cond_9

    if-eqz v4, :cond_9

    .line 242
    invoke-direct {p0, v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimInsertedWithUnAvaliable(Ljava/util/List;I)Z

    move-result v3

    if-eqz v3, :cond_8

    if-eqz v5, :cond_8

    .line 244
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_E_F:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 246
    :cond_8
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_D_E:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 248
    :cond_9
    if-eqz v2, :cond_11

    if-nez v4, :cond_11

    .line 249
    invoke-direct {p0, v0, v3}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimInsertedWithUnAvaliable(Ljava/util/List;I)Z

    move-result v3

    if-eqz v3, :cond_a

    if-eqz v6, :cond_a

    .line 251
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_E_F:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 253
    :cond_a
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_D_E:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 257
    :cond_b
    if-eqz v2, :cond_d

    if-eqz v4, :cond_d

    .line 258
    if-eqz v5, :cond_c

    .line 259
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_E_D:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 261
    :cond_c
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_E_D:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 263
    :cond_d
    if-nez v2, :cond_f

    if-eqz v4, :cond_f

    .line 264
    invoke-direct {p0, v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimInsertedWithUnAvaliable(Ljava/util/List;I)Z

    move-result v3

    if-eqz v3, :cond_e

    if-eqz v5, :cond_e

    .line 266
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_E_F:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 268
    :cond_e
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_D_D:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 270
    :cond_f
    if-eqz v2, :cond_11

    if-nez v4, :cond_11

    .line 271
    invoke-direct {p0, v0, v3}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->isSimInsertedWithUnAvaliable(Ljava/util/List;I)Z

    move-result v3

    if-eqz v3, :cond_10

    if-eqz v6, :cond_10

    .line 273
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM2_E_F:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    goto :goto_2

    .line 275
    :cond_10
    sget-object v3, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;->SIM1_D_D:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    iput-object v3, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    .line 279
    .end local v8    # "dataConnected":Z
    :cond_11
    :goto_2
    nop

    .line 290
    :goto_3
    invoke-direct {p0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->setUserSwitching(Z)V

    .line 291
    return-void
.end method

.method public refresh()V
    .locals 2

    .line 202
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->this$0:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;

    invoke-static {v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;->access$600(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile;)Landroid/content/Context;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->onActualStateChange(Landroid/content/Context;Landroid/content/Intent;)V

    .line 203
    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->setUserSwitching(Z)V

    .line 204
    return-void
.end method

.method public toggleState(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 321
    iget-object v0, p0, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->mCurrentSimConnState:Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;

    invoke-direct {p0, v0}, Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SimDataSwitchStateMachine;->enterNextState(Lcom/mediatek/systemui/qs/tiles/ext/SimDataConnectionTile$SIMConnState;)V

    .line 322
    return-void
.end method
