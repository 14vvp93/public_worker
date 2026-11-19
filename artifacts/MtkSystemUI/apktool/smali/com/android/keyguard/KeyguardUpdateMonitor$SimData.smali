.class Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;
.super Ljava/lang/Object;
.source "KeyguardUpdateMonitor.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/keyguard/KeyguardUpdateMonitor;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "SimData"
.end annotation


# instance fields
.field public phoneId:I

.field public simMECategory:I

.field public final simState:Lcom/android/internal/telephony/IccCardConstants$State;

.field public subId:I


# direct methods
.method constructor <init>(Lcom/android/internal/telephony/IccCardConstants$State;II)V
    .locals 1
    .param p1, "state"    # Lcom/android/internal/telephony/IccCardConstants$State;
    .param p2, "phoneId"    # I
    .param p3, "subId"    # I

    .line 1005
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 994
    const/4 v0, 0x0

    iput v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->phoneId:I

    .line 996
    iput v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simMECategory:I

    .line 1006
    iput-object p1, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simState:Lcom/android/internal/telephony/IccCardConstants$State;

    .line 1007
    iput p2, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->phoneId:I

    .line 1008
    iput p3, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->subId:I

    .line 1009
    return-void
.end method

.method constructor <init>(Lcom/android/internal/telephony/IccCardConstants$State;III)V
    .locals 1
    .param p1, "state"    # Lcom/android/internal/telephony/IccCardConstants$State;
    .param p2, "phoneId"    # I
    .param p3, "subId"    # I
    .param p4, "meCategory"    # I
    .annotation build Lcom/android/internal/annotations/VisibleForTesting;
    .end annotation

    .line 1012
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 994
    const/4 v0, 0x0

    iput v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->phoneId:I

    .line 996
    iput v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simMECategory:I

    .line 1013
    iput-object p1, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simState:Lcom/android/internal/telephony/IccCardConstants$State;

    .line 1014
    iput p2, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->phoneId:I

    .line 1015
    iput p3, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->subId:I

    .line 1016
    iput p4, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simMECategory:I

    .line 1017
    return-void
.end method

.method static fromIntent(Landroid/content/Intent;)Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;
    .locals 8
    .param p0, "intent"    # Landroid/content/Intent;

    .line 1023
    const-string v0, "android.intent.action.SIM_STATE_CHANGED"

    invoke-virtual {p0}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "com.mediatek.phone.ACTION_UNLOCK_SIM_LOCK"

    .line 1024
    invoke-virtual {p0}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    .line 1025
    :cond_0
    new-instance v0, Ljava/lang/IllegalArgumentException;

    const-string v1, "only handles intent ACTION_SIM_STATE_CHANGED"

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 1027
    :cond_1
    :goto_0
    const/4 v0, 0x0

    .line 1028
    .local v0, "meCategory":I
    const-string v1, "ss"

    invoke-virtual {p0, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 1030
    .local v1, "stateExtra":Ljava/lang/String;
    const-string v2, "slot"

    const/4 v3, 0x0

    invoke-virtual {p0, v2, v3}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v2

    .line 1031
    .local v2, "phoneId":I
    const-string v3, "subscription"

    const/4 v4, -0x1

    invoke-virtual {p0, v3, v4}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v3

    .line 1033
    .local v3, "subId":I
    const-string v4, "ABSENT"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_3

    .line 1034
    const-string v4, "reason"

    .line 1035
    invoke-virtual {p0, v4}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 1037
    .local v4, "absentReason":Ljava/lang/String;
    const-string v5, "PERM_DISABLED"

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_2

    .line 1039
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->PERM_DISABLED:Lcom/android/internal/telephony/IccCardConstants$State;

    .local v5, "state":Lcom/android/internal/telephony/IccCardConstants$State;
    goto :goto_1

    .line 1041
    .end local v5    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :cond_2
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->ABSENT:Lcom/android/internal/telephony/IccCardConstants$State;

    .end local v4    # "absentReason":Ljava/lang/String;
    .restart local v5    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :goto_1
    move-object v4, v5

    .line 1043
    .end local v5    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    .local v4, "state":Lcom/android/internal/telephony/IccCardConstants$State;
    goto/16 :goto_6

    .end local v4    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :cond_3
    const-string v4, "READY"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    .line 1044
    sget-object v4, Lcom/android/internal/telephony/IccCardConstants$State;->READY:Lcom/android/internal/telephony/IccCardConstants$State;

    .restart local v4    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :goto_2
    goto/16 :goto_6

    .line 1045
    .end local v4    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :cond_4
    const-string v4, "LOCKED"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_c

    .line 1047
    const-string v4, "reason"

    .line 1048
    invoke-virtual {p0, v4}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 1049
    .local v4, "lockedReason":Ljava/lang/String;
    const-string v5, "KeyguardUpdateMonitor"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "INTENT_VALUE_ICC_LOCKED, lockedReason="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1051
    const-string v5, "PIN"

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_5

    .line 1052
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->PIN_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    .end local v4    # "lockedReason":Ljava/lang/String;
    .restart local v5    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :goto_3
    goto :goto_4

    .line 1053
    .end local v5    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    .restart local v4    # "lockedReason":Ljava/lang/String;
    :cond_5
    const-string v5, "PUK"

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_6

    .line 1054
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->PUK_REQUIRED:Lcom/android/internal/telephony/IccCardConstants$State;

    goto :goto_3

    .line 1055
    :cond_6
    const-string v5, "NETWORK"

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_7

    .line 1056
    const/4 v0, 0x0

    .line 1057
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    goto :goto_3

    .line 1058
    :cond_7
    const-string v5, "NETWORK_SUBSET"

    .line 1059
    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_8

    .line 1060
    const/4 v0, 0x1

    .line 1061
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    goto :goto_3

    .line 1062
    :cond_8
    const-string v5, "SERVICE_PROVIDER"

    .line 1063
    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_9

    .line 1064
    const/4 v0, 0x2

    .line 1065
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    goto :goto_3

    .line 1066
    :cond_9
    const-string v5, "CORPORATE"

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_a

    .line 1067
    const/4 v0, 0x3

    .line 1068
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    goto :goto_3

    .line 1069
    :cond_a
    const-string v5, "SIM"

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_b

    .line 1070
    const/4 v0, 0x4

    .line 1071
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    goto :goto_3

    .line 1073
    :cond_b
    sget-object v5, Lcom/android/internal/telephony/IccCardConstants$State;->UNKNOWN:Lcom/android/internal/telephony/IccCardConstants$State;

    .end local v4    # "lockedReason":Ljava/lang/String;
    .restart local v5    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :goto_4
    move-object v4, v5

    .line 1075
    .end local v5    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    .local v4, "state":Lcom/android/internal/telephony/IccCardConstants$State;
    goto :goto_6

    .end local v4    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :cond_c
    const-string v4, "NETWORK"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_d

    .line 1076
    sget-object v4, Lcom/android/internal/telephony/IccCardConstants$State;->NETWORK_LOCKED:Lcom/android/internal/telephony/IccCardConstants$State;

    goto/16 :goto_2

    .line 1077
    :cond_d
    const-string v4, "CARD_IO_ERROR"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_e

    .line 1078
    sget-object v4, Lcom/android/internal/telephony/IccCardConstants$State;->CARD_IO_ERROR:Lcom/android/internal/telephony/IccCardConstants$State;

    goto/16 :goto_2

    .line 1079
    :cond_e
    const-string v4, "LOADED"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_11

    const-string v4, "IMSI"

    .line 1080
    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_f

    goto :goto_5

    .line 1084
    :cond_f
    const-string v4, "NOT_READY"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_10

    .line 1086
    sget-object v4, Lcom/android/internal/telephony/IccCardConstants$State;->NOT_READY:Lcom/android/internal/telephony/IccCardConstants$State;

    goto/16 :goto_2

    .line 1088
    :cond_10
    sget-object v4, Lcom/android/internal/telephony/IccCardConstants$State;->UNKNOWN:Lcom/android/internal/telephony/IccCardConstants$State;

    goto :goto_6

    .line 1083
    :cond_11
    :goto_5
    sget-object v4, Lcom/android/internal/telephony/IccCardConstants$State;->READY:Lcom/android/internal/telephony/IccCardConstants$State;

    goto/16 :goto_2

    .line 1088
    .restart local v4    # "state":Lcom/android/internal/telephony/IccCardConstants$State;
    :goto_6
    nop

    .line 1091
    new-instance v5, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;

    invoke-direct {v5, v4, v2, v3, v0}, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;-><init>(Lcom/android/internal/telephony/IccCardConstants$State;III)V

    return-object v5
.end method


# virtual methods
.method public toString()Ljava/lang/String;
    .locals 1

    .line 1097
    iget-object v0, p0, Lcom/android/keyguard/KeyguardUpdateMonitor$SimData;->simState:Lcom/android/internal/telephony/IccCardConstants$State;

    invoke-virtual {v0}, Lcom/android/internal/telephony/IccCardConstants$State;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
