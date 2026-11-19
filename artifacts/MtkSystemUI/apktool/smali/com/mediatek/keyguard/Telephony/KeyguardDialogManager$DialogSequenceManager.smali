.class Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;
.super Ljava/lang/Object;
.source "KeyguardDialogManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "DialogSequenceManager"
.end annotation


# instance fields
.field private mDialogSequenceObserver:Landroid/database/ContentObserver;

.field private mDialogShowCallbackQueue:Ljava/util/Queue;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Queue<",
            "Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;",
            ">;"
        }
    .end annotation
.end field

.field private mInnerDialogShowing:Z

.field private mLocked:Z

.field final synthetic this$0:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;


# direct methods
.method public constructor <init>(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)V
    .locals 3

    .line 131
    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 127
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mInnerDialogShowing:Z

    .line 129
    iput-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mLocked:Z

    .line 265
    new-instance v1, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;

    new-instance v2, Landroid/os/Handler;

    invoke-direct {v2}, Landroid/os/Handler;-><init>()V

    invoke-direct {v1, p0, v2}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager$1;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;Landroid/os/Handler;)V

    iput-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogSequenceObserver:Landroid/database/ContentObserver;

    .line 133
    const-string v1, "KeyguardDialogManager"

    const-string v2, "DialogSequenceManager DialogSequenceManager()"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 135
    new-instance v1, Ljava/util/LinkedList;

    invoke-direct {v1}, Ljava/util/LinkedList;-><init>()V

    iput-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogShowCallbackQueue:Ljava/util/Queue;

    .line 137
    invoke-static {p1}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->access$000(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object p1

    const-string v1, "dialog_sequence_settings"

    .line 138
    invoke-static {v1}, Landroid/provider/Settings$System;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogSequenceObserver:Landroid/database/ContentObserver;

    invoke-virtual {p1, v1, v0, v2}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    .line 140
    return-void
.end method

.method static synthetic access$200(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;)I
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;

    .line 119
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->queryDialogSequenceSeetings()I

    move-result v0

    return v0
.end method

.method static synthetic access$300(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;Z)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;
    .param p1, "x1"    # Z

    .line 119
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->setLocked(Z)V

    return-void
.end method

.method private enableShow()Z
    .locals 4

    .line 205
    const-string v0, "KeyguardDialogManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "DialogSequenceManager --enableShow()-- !mDialogShowCallbackQueue.isEmpty() = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogShowCallbackQueue:Ljava/util/Queue;

    .line 206
    invoke-interface {v2}, Ljava/util/Queue;->isEmpty()Z

    move-result v2

    const/4 v3, 0x1

    xor-int/2addr v2, v3

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " !getInnerDialogShowing() = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 207
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->getInnerDialogShowing()Z

    move-result v2

    xor-int/2addr v2, v3

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " !isOtherModuleShowing() = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 208
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->isOtherModuleShowing()Z

    move-result v2

    xor-int/2addr v2, v3

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, "!isAlarmBoot() = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 209
    invoke-static {}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->isAlarmBoot()Z

    move-result v2

    xor-int/2addr v2, v3

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " isDeviceProvisioned() = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    .line 210
    invoke-static {v2}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->access$100(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v2

    invoke-virtual {v2}, Lcom/android/keyguard/KeyguardUpdateMonitor;->isDeviceProvisioned()Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 205
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 214
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogShowCallbackQueue:Ljava/util/Queue;

    invoke-interface {v0}, Ljava/util/Queue;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_0

    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->getInnerDialogShowing()Z

    move-result v0

    if-nez v0, :cond_0

    .line 215
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->isOtherModuleShowing()Z

    move-result v0

    if-nez v0, :cond_0

    .line 216
    invoke-static {}, Lcom/mediatek/keyguard/PowerOffAlarm/PowerOffAlarmManager;->isAlarmBoot()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    .line 217
    invoke-static {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->access$100(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)Lcom/android/keyguard/KeyguardUpdateMonitor;

    move-result-object v0

    invoke-virtual {v0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->isDeviceProvisioned()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 218
    invoke-static {}, Lcom/android/keyguard/KeyguardUtils;->isSystemEncrypted()Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v3, 0x0

    .line 214
    :goto_0
    return v3
.end method

.method private getInnerDialogShowing()Z
    .locals 1

    .line 243
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mInnerDialogShowing:Z

    return v0
.end method

.method private getLocked()Z
    .locals 1

    .line 251
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mLocked:Z

    return v0
.end method

.method private isOtherModuleShowing()Z
    .locals 4

    .line 226
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->queryDialogSequenceSeetings()I

    move-result v0

    .line 228
    .local v0, "value":I
    const-string v1, "KeyguardDialogManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "DialogSequenceManager --isOtherModuleShowing()--dialog_sequence_settings = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 231
    if-eqz v0, :cond_1

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    goto :goto_0

    .line 235
    :cond_0
    return v1

    .line 233
    :cond_1
    :goto_0
    const/4 v1, 0x0

    return v1
.end method

.method private queryDialogSequenceSeetings()I
    .locals 3

    .line 258
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    invoke-static {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->access$000(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "dialog_sequence_settings"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    .line 261
    .local v0, "value":I
    return v0
.end method

.method private setInnerDialogShowing(Z)V
    .locals 0
    .param p1, "show"    # Z

    .line 239
    iput-boolean p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mInnerDialogShowing:Z

    .line 240
    return-void
.end method

.method private setLocked(Z)V
    .locals 0
    .param p1, "locked"    # Z

    .line 247
    iput-boolean p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mLocked:Z

    .line 248
    return-void
.end method


# virtual methods
.method public handleShowDialog()V
    .locals 5

    .line 152
    const-string v0, "KeyguardDialogManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "DialogSequenceManager --handleShowDialog()--enableShow() = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->enableShow()Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 154
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->enableShow()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 155
    invoke-direct {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->getLocked()Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_1

    .line 156
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogShowCallbackQueue:Ljava/util/Queue;

    invoke-interface {v0}, Ljava/util/Queue;->poll()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;

    .line 158
    .local v0, "dialogCallBack":Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;
    const-string v2, "KeyguardDialogManager"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "DialogSequenceManager --handleShowDialog()--dialogCallBack = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 161
    if-eqz v0, :cond_0

    .line 162
    invoke-interface {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;->show()V

    .line 163
    invoke-direct {p0, v1}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->setInnerDialogShowing(Z)V

    .line 165
    .end local v0    # "dialogCallBack":Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;
    :cond_0
    goto :goto_0

    .line 167
    :cond_1
    const-string v0, "KeyguardDialogManager"

    const-string v2, "DialogSequenceManager --handleShowDialog()--System.putInt( dialog_sequence_settings value = 1"

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 171
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    invoke-static {v0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->access$000(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v2, "dialog_sequence_settings"

    invoke-static {v0, v2, v1}, Landroid/provider/Settings$System;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 175
    :cond_2
    :goto_0
    return-void
.end method

.method public reportDialogClose()V
    .locals 3

    .line 179
    const-string v0, "KeyguardDialogManager"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "DialogSequenceManager --reportDialogClose()--mDialogShowCallbackQueue.isEmpty() = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogShowCallbackQueue:Ljava/util/Queue;

    .line 181
    invoke-interface {v2}, Ljava/util/Queue;->isEmpty()Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 179
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 183
    const/4 v0, 0x0

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->setInnerDialogShowing(Z)V

    .line 185
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogShowCallbackQueue:Ljava/util/Queue;

    invoke-interface {v1}, Ljava/util/Queue;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 187
    const-string v1, "KeyguardDialogManager"

    const-string v2, "DialogSequenceManager --reportDialogClose()--System.putInt( dialog_sequence_settings value = 0 --setLocked(false)--"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 192
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;

    invoke-static {v1}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;->access$000(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager;)Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "dialog_sequence_settings"

    invoke-static {v1, v2, v0}, Landroid/provider/Settings$System;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 194
    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->setLocked(Z)V

    goto :goto_0

    .line 196
    :cond_0
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->handleShowDialog()V

    .line 198
    :goto_0
    return-void
.end method

.method public requestShowDialog(Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;)V
    .locals 2
    .param p1, "callback"    # Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogShowCallBack;

    .line 144
    const-string v0, "KeyguardDialogManager"

    const-string v1, "DialogSequenceManager --requestShowDialog()"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 146
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->mDialogShowCallbackQueue:Ljava/util/Queue;

    invoke-interface {v0, p1}, Ljava/util/Queue;->add(Ljava/lang/Object;)Z

    .line 147
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Telephony/KeyguardDialogManager$DialogSequenceManager;->handleShowDialog()V

    .line 148
    return-void
.end method
