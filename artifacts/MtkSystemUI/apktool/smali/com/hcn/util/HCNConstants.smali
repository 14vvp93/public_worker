.class public Lcom/hcn/util/HCNConstants;
.super Ljava/lang/Object;
.source "HCNConstants.java"


# static fields
.field public static ACTION_HCN_OUTDOOR_TEMP:Ljava/lang/String;

.field public static ACTION_INIT_CAN:Ljava/lang/String;

.field public static ACTION_UNBIND_CAN:Ljava/lang/String;

.field public static AVM_LB_RADAR:Ljava/lang/String;

.field public static AVM_LF_RADAR:Ljava/lang/String;

.field public static AVM_LMB_RADAR:Ljava/lang/String;

.field public static AVM_LMF_RADAR:Ljava/lang/String;

.field public static AVM_RB_RADAR:Ljava/lang/String;

.field public static AVM_RF_RADAR:Ljava/lang/String;

.field public static AVM_RMB_RADAR:Ljava/lang/String;

.field public static AVM_RMF_RADAR:Ljava/lang/String;

.field public static AVM_SPEED:Ljava/lang/String;

.field public static AVM_TURN_LEFT:Ljava/lang/String;

.field public static AVM_TURN_RIGHT:Ljava/lang/String;

.field public static AVM_TURN_WARN:Ljava/lang/String;

.field public static AVM_TYPE:Ljava/lang/String;

.field public static DISSMISS_AIR_CTRL_TIME:I

.field public static DOOR_LEFT_FRONT:Ljava/lang/String;

.field public static DOOR_LEFT_REAR:Ljava/lang/String;

.field public static DOOR_REAR:Ljava/lang/String;

.field public static DOOR_RIGHT_FRONT:Ljava/lang/String;

.field public static DOOR_RIGHT_REAR:Ljava/lang/String;

.field public static DYNAMIC_LINE_ANGLE:Ljava/lang/String;

.field public static KEY_EXTRA_OUTDOOR_TEMP:Ljava/lang/String;

.field public static SETTINGS_CAN_MSG:Ljava/lang/String;

.field public static SPEED_FROM_CAN:Ljava/lang/String;

.field public static TAG:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 8
    const-string v0, "AutoCan_"

    sput-object v0, Lcom/hcn/util/HCNConstants;->TAG:Ljava/lang/String;

    .line 109
    const-string v0, "hcnhw/can_type_msg"

    sput-object v0, Lcom/hcn/util/HCNConstants;->SETTINGS_CAN_MSG:Ljava/lang/String;

    .line 115
    const-string v0, "persist.vendor.cem.speed"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_SPEED:Ljava/lang/String;

    .line 119
    const-string v0, "persist.vendor.cem.light0"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_TURN_LEFT:Ljava/lang/String;

    .line 123
    const-string v0, "persist.vendor.cem.light1"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_TURN_RIGHT:Ljava/lang/String;

    .line 127
    const-string v0, "persist.vendor.cem.light2"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_TURN_WARN:Ljava/lang/String;

    .line 131
    const-string v0, "persist.vendor.cem.radar0"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_LF_RADAR:Ljava/lang/String;

    .line 136
    const-string v0, "persist.vendor.cem.radar1"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_LMF_RADAR:Ljava/lang/String;

    .line 141
    const-string v0, "persist.vendor.cem.radar2"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_RMF_RADAR:Ljava/lang/String;

    .line 145
    const-string v0, "persist.vendor.cem.radar3"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_RF_RADAR:Ljava/lang/String;

    .line 150
    const-string v0, "persist.vendor.cem.radar4"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_LB_RADAR:Ljava/lang/String;

    .line 155
    const-string v0, "persist.vendor.cem.radar5"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_LMB_RADAR:Ljava/lang/String;

    .line 160
    const-string v0, "persist.vendor.cem.radar6"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_RMB_RADAR:Ljava/lang/String;

    .line 165
    const-string v0, "persist.vendor.cem.radar7"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_RB_RADAR:Ljava/lang/String;

    .line 171
    const-string v0, "persist.vendor.cem.cardoor1"

    sput-object v0, Lcom/hcn/util/HCNConstants;->DOOR_LEFT_FRONT:Ljava/lang/String;

    .line 176
    const-string v0, "persist.vendor.cem.cardoor2"

    sput-object v0, Lcom/hcn/util/HCNConstants;->DOOR_RIGHT_FRONT:Ljava/lang/String;

    .line 181
    const-string v0, "persist.vendor.cem.cardoor3"

    sput-object v0, Lcom/hcn/util/HCNConstants;->DOOR_LEFT_REAR:Ljava/lang/String;

    .line 186
    const-string v0, "persist.vendor.cem.cardoor4"

    sput-object v0, Lcom/hcn/util/HCNConstants;->DOOR_RIGHT_REAR:Ljava/lang/String;

    .line 191
    const-string v0, "persist.vendor.cem.cardoor5"

    sput-object v0, Lcom/hcn/util/HCNConstants;->DOOR_REAR:Ljava/lang/String;

    .line 195
    const-string v0, "persist.vendor.cem.angle"

    sput-object v0, Lcom/hcn/util/HCNConstants;->DYNAMIC_LINE_ANGLE:Ljava/lang/String;

    .line 203
    const-string v0, "ro.vendor.sys.avm.type"

    sput-object v0, Lcom/hcn/util/HCNConstants;->AVM_TYPE:Ljava/lang/String;

    .line 209
    const-string v0, "persist.sys.cem.speed.from.can"

    sput-object v0, Lcom/hcn/util/HCNConstants;->SPEED_FROM_CAN:Ljava/lang/String;

    .line 214
    const/16 v0, 0x2710

    sput v0, Lcom/hcn/util/HCNConstants;->DISSMISS_AIR_CTRL_TIME:I

    .line 216
    const-string v0, "hcn.air.outdoor_temp"

    sput-object v0, Lcom/hcn/util/HCNConstants;->ACTION_HCN_OUTDOOR_TEMP:Ljava/lang/String;

    .line 217
    const-string v0, "hcn.air.extra.outdoor_temp"

    sput-object v0, Lcom/hcn/util/HCNConstants;->KEY_EXTRA_OUTDOOR_TEMP:Ljava/lang/String;

    .line 218
    const-string v0, "action.hcn.initcan"

    sput-object v0, Lcom/hcn/util/HCNConstants;->ACTION_INIT_CAN:Ljava/lang/String;

    .line 219
    const-string v0, "action.hcn.ubindcan"

    sput-object v0, Lcom/hcn/util/HCNConstants;->ACTION_UNBIND_CAN:Ljava/lang/String;

    return-void
.end method
