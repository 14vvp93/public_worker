.class public Lcom/android/systemui/settings/VolumeController;
.super Ljava/lang/Object;
.source "VolumeController.java"

# interfaces
.implements Lcom/android/systemui/settings/ToggleSlider$Listener;


# instance fields
.field private final mAudioManager:Landroid/media/AudioManager;

.field private final mBackgroundHandler:Landroid/os/Handler;

.field private final mContext:Landroid/content/Context;

.field private final mControl:Lcom/android/systemui/settings/ToggleSlider;

.field private mExternalChange:Z

.field private final mHandler:Landroid/os/Handler;
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "HandlerLeak"
        }
    .end annotation
.end field

.field private final mHasVolumeText:Z

.field private final mIcon:Landroid/widget/ImageView;

.field private mListening:Z

.field private mReceiver:Landroid/content/BroadcastReceiver;

.field private final mStartListeningRunnable:Ljava/lang/Runnable;

.field private final mStopListeningRunnable:Ljava/lang/Runnable;

.field private final mUpdateSliderRunnable:Ljava/lang/Runnable;

.field private final mUserTracker:Lcom/android/systemui/settings/CurrentUserTracker;

.field private final mVolumeText:Landroid/widget/TextView;

.field private tracking:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/widget/ImageView;Lcom/android/systemui/settings/ToggleSlider;)V
    .locals 6
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "icon"    # Landroid/widget/ImageView;
    .param p3, "control"    # Lcom/android/systemui/settings/ToggleSlider;

    .line 123
    const/4 v4, 0x0

    const/4 v5, 0x0

    move-object v0, p0

    move-object v1, p1

    move-object v2, p2

    move-object v3, p3

    invoke-direct/range {v0 .. v5}, Lcom/android/systemui/settings/VolumeController;-><init>(Landroid/content/Context;Landroid/widget/ImageView;Lcom/android/systemui/settings/ToggleSlider;Landroid/widget/TextView;Z)V

    .line 124
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/widget/ImageView;Lcom/android/systemui/settings/ToggleSlider;Landroid/widget/TextView;Z)V
    .locals 3
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "icon"    # Landroid/widget/ImageView;
    .param p3, "control"    # Lcom/android/systemui/settings/ToggleSlider;
    .param p4, "volumeText"    # Landroid/widget/TextView;
    .param p5, "hasVolumeText"    # Z

    .line 127
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 59
    new-instance v0, Lcom/android/systemui/settings/VolumeController$1;

    invoke-direct {v0, p0}, Lcom/android/systemui/settings/VolumeController$1;-><init>(Lcom/android/systemui/settings/VolumeController;)V

    iput-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mStartListeningRunnable:Ljava/lang/Runnable;

    .line 70
    new-instance v0, Lcom/android/systemui/settings/VolumeController$2;

    invoke-direct {v0, p0}, Lcom/android/systemui/settings/VolumeController$2;-><init>(Lcom/android/systemui/settings/VolumeController;)V

    iput-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mStopListeningRunnable:Ljava/lang/Runnable;

    .line 83
    new-instance v0, Lcom/android/systemui/settings/VolumeController$3;

    invoke-direct {v0, p0}, Lcom/android/systemui/settings/VolumeController$3;-><init>(Lcom/android/systemui/settings/VolumeController;)V

    iput-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mUpdateSliderRunnable:Ljava/lang/Runnable;

    .line 91
    new-instance v0, Lcom/android/systemui/settings/VolumeController$4;

    invoke-direct {v0, p0}, Lcom/android/systemui/settings/VolumeController$4;-><init>(Lcom/android/systemui/settings/VolumeController;)V

    iput-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mHandler:Landroid/os/Handler;

    .line 244
    new-instance v0, Lcom/android/systemui/settings/VolumeController$6;

    invoke-direct {v0, p0}, Lcom/android/systemui/settings/VolumeController$6;-><init>(Lcom/android/systemui/settings/VolumeController;)V

    iput-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mReceiver:Landroid/content/BroadcastReceiver;

    .line 128
    iput-object p1, p0, Lcom/android/systemui/settings/VolumeController;->mContext:Landroid/content/Context;

    .line 129
    iput-object p2, p0, Lcom/android/systemui/settings/VolumeController;->mIcon:Landroid/widget/ImageView;

    .line 130
    iput-object p3, p0, Lcom/android/systemui/settings/VolumeController;->mControl:Lcom/android/systemui/settings/ToggleSlider;

    .line 131
    iput-object p4, p0, Lcom/android/systemui/settings/VolumeController;->mVolumeText:Landroid/widget/TextView;

    .line 132
    iput-boolean p5, p0, Lcom/android/systemui/settings/VolumeController;->mHasVolumeText:Z

    .line 134
    new-instance v0, Landroid/os/Handler;

    sget-object v1, Lcom/android/systemui/Dependency;->BG_LOOPER:Lcom/android/systemui/Dependency$DependencyKey;

    invoke-static {v1}, Lcom/android/systemui/Dependency;->get(Lcom/android/systemui/Dependency$DependencyKey;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/os/Looper;

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    iput-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mBackgroundHandler:Landroid/os/Handler;

    .line 135
    new-instance v0, Lcom/android/systemui/settings/VolumeController$5;

    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController;->mContext:Landroid/content/Context;

    invoke-direct {v0, p0, v1}, Lcom/android/systemui/settings/VolumeController$5;-><init>(Lcom/android/systemui/settings/VolumeController;Landroid/content/Context;)V

    iput-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mUserTracker:Lcom/android/systemui/settings/CurrentUserTracker;

    .line 142
    const-string v0, "audio"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/AudioManager;

    iput-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    .line 143
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    if-eqz v0, :cond_1

    .line 144
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mControl:Lcom/android/systemui/settings/ToggleSlider;

    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    const/4 v2, 0x3

    invoke-virtual {v1, v2}, Landroid/media/AudioManager;->getStreamMaxVolume(I)I

    move-result v1

    invoke-interface {v0, v1}, Lcom/android/systemui/settings/ToggleSlider;->setMax(I)V

    .line 145
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mControl:Lcom/android/systemui/settings/ToggleSlider;

    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    invoke-virtual {v1, v2}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v1

    invoke-interface {v0, v1}, Lcom/android/systemui/settings/ToggleSlider;->setValue(I)V

    .line 147
    iget-boolean v0, p0, Lcom/android/systemui/settings/VolumeController;->mHasVolumeText:Z

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mVolumeText:Landroid/widget/TextView;

    if-eqz v0, :cond_0

    .line 148
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    invoke-virtual {v0, v2}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v0

    invoke-direct {p0, v0}, Lcom/android/systemui/settings/VolumeController;->updateVolumeText(I)V

    .line 151
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mIcon:Landroid/widget/ImageView;

    new-instance v1, Lcom/android/systemui/settings/-$$Lambda$VolumeController$Po80t8e7LXwUas_bbNfxcc5pFFQ;

    invoke-direct {v1, p0}, Lcom/android/systemui/settings/-$$Lambda$VolumeController$Po80t8e7LXwUas_bbNfxcc5pFFQ;-><init>(Lcom/android/systemui/settings/VolumeController;)V

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 157
    :cond_1
    return-void
.end method

.method static synthetic access$000(Lcom/android/systemui/settings/VolumeController;)Lcom/android/systemui/settings/CurrentUserTracker;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;

    .line 35
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mUserTracker:Lcom/android/systemui/settings/CurrentUserTracker;

    return-object v0
.end method

.method static synthetic access$100(Lcom/android/systemui/settings/VolumeController;)Ljava/lang/Runnable;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;

    .line 35
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mUpdateSliderRunnable:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$200(Lcom/android/systemui/settings/VolumeController;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;

    .line 35
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$300(Lcom/android/systemui/settings/VolumeController;)Landroid/media/AudioManager;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;

    .line 35
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    return-object v0
.end method

.method static synthetic access$402(Lcom/android/systemui/settings/VolumeController;Z)Z
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;
    .param p1, "x1"    # Z

    .line 35
    iput-boolean p1, p0, Lcom/android/systemui/settings/VolumeController;->mExternalChange:Z

    return p1
.end method

.method static synthetic access$500(Lcom/android/systemui/settings/VolumeController;)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;

    .line 35
    invoke-direct {p0}, Lcom/android/systemui/settings/VolumeController;->updateIcon()V

    return-void
.end method

.method static synthetic access$600(Lcom/android/systemui/settings/VolumeController;I)V
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;
    .param p1, "x1"    # I

    .line 35
    invoke-direct {p0, p1}, Lcom/android/systemui/settings/VolumeController;->updateSlider(I)V

    return-void
.end method

.method static synthetic access$700(Lcom/android/systemui/settings/VolumeController;)Lcom/android/systemui/settings/ToggleSlider;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;

    .line 35
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mControl:Lcom/android/systemui/settings/ToggleSlider;

    return-object v0
.end method

.method static synthetic access$800(Lcom/android/systemui/settings/VolumeController;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;

    .line 35
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mBackgroundHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$900(Lcom/android/systemui/settings/VolumeController;)Z
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/settings/VolumeController;

    .line 35
    iget-boolean v0, p0, Lcom/android/systemui/settings/VolumeController;->tracking:Z

    return v0
.end method

.method public static synthetic lambda$new$0(Lcom/android/systemui/settings/VolumeController;Landroid/view/View;)V
    .locals 4
    .param p1, "v"    # Landroid/view/View;

    .line 152
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    if-eqz v0, :cond_0

    .line 153
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    const/16 v2, 0x65

    const/4 v3, 0x1

    invoke-virtual {v0, v1, v2, v3}, Landroid/media/AudioManager;->adjustStreamVolume(III)V

    .line 155
    :cond_0
    return-void
.end method

.method private registerReceiver()V
    .locals 3

    .line 233
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 234
    .local v0, "intentFilter":Landroid/content/IntentFilter;
    const-string v1, "android.media.VOLUME_CHANGED_ACTION"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 235
    const-string v1, "android.media.STREAM_MUTE_CHANGED_ACTION"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 236
    const-string v1, "android.media.STREAM_DEVICES_CHANGED_ACTION"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 237
    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 238
    return-void
.end method

.method private updateIcon()V
    .locals 4

    .line 204
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mContext:Landroid/content/Context;

    const-string v1, "audio"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/AudioManager;

    .line 205
    .local v0, "audioManager":Landroid/media/AudioManager;
    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->isStreamMute(I)Z

    move-result v1

    .line 206
    .local v1, "muted":Z
    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController;->mIcon:Landroid/widget/ImageView;

    if-eqz v2, :cond_1

    .line 207
    iget-object v2, p0, Lcom/android/systemui/settings/VolumeController;->mIcon:Landroid/widget/ImageView;

    if-eqz v1, :cond_0

    const v3, 0x7f0803b9

    goto :goto_0

    :cond_0
    const v3, 0x7f0803ba

    :goto_0
    invoke-virtual {v2, v3}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 209
    :cond_1
    return-void
.end method

.method private updateSlider(I)V
    .locals 1
    .param p1, "val"    # I

    .line 218
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mControl:Lcom/android/systemui/settings/ToggleSlider;

    invoke-interface {v0}, Lcom/android/systemui/settings/ToggleSlider;->getValue()I

    move-result v0

    if-ne p1, v0, :cond_0

    .line 224
    return-void

    .line 226
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mControl:Lcom/android/systemui/settings/ToggleSlider;

    invoke-interface {v0, p1}, Lcom/android/systemui/settings/ToggleSlider;->setValue(I)V

    .line 227
    iget-boolean v0, p0, Lcom/android/systemui/settings/VolumeController;->mHasVolumeText:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mVolumeText:Landroid/widget/TextView;

    if-eqz v0, :cond_1

    .line 228
    invoke-direct {p0, p1}, Lcom/android/systemui/settings/VolumeController;->updateVolumeText(I)V

    .line 230
    :cond_1
    return-void
.end method

.method private updateVolumeText(I)V
    .locals 2
    .param p1, "volumeValue"    # I

    .line 212
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mVolumeText:Landroid/widget/TextView;

    if-eqz v0, :cond_0

    .line 213
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mVolumeText:Landroid/widget/TextView;

    invoke-static {p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 215
    :cond_0
    return-void
.end method


# virtual methods
.method public onChanged(Lcom/android/systemui/settings/ToggleSlider;ZZIZ)V
    .locals 3
    .param p1, "toggleSlider"    # Lcom/android/systemui/settings/ToggleSlider;
    .param p2, "tracking"    # Z
    .param p3, "automatic"    # Z
    .param p4, "value"    # I
    .param p5, "stopTracking"    # Z

    .line 190
    iput-boolean p2, p0, Lcom/android/systemui/settings/VolumeController;->tracking:Z

    .line 191
    invoke-direct {p0}, Lcom/android/systemui/settings/VolumeController;->updateIcon()V

    .line 192
    iget-boolean v0, p0, Lcom/android/systemui/settings/VolumeController;->mExternalChange:Z

    if-eqz v0, :cond_0

    return-void

    .line 193
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    const/4 v2, 0x0

    invoke-virtual {v0, v1, p4, v2}, Landroid/media/AudioManager;->setStreamVolume(III)V

    .line 195
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x4

    invoke-virtual {v0, v1, p4, v2}, Landroid/media/AudioManager;->setStreamVolume(III)V

    .line 197
    iget-boolean v0, p0, Lcom/android/systemui/settings/VolumeController;->mHasVolumeText:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mVolumeText:Landroid/widget/TextView;

    if-eqz v0, :cond_1

    .line 198
    invoke-direct {p0, p4}, Lcom/android/systemui/settings/VolumeController;->updateVolumeText(I)V

    .line 200
    :cond_1
    return-void
.end method

.method public onInit(Lcom/android/systemui/settings/ToggleSlider;)V
    .locals 0
    .param p1, "control"    # Lcom/android/systemui/settings/ToggleSlider;

    .line 162
    return-void
.end method

.method public registerCallbacks()V
    .locals 2

    .line 165
    iget-boolean v0, p0, Lcom/android/systemui/settings/VolumeController;->mListening:Z

    if-eqz v0, :cond_0

    .line 166
    return-void

    .line 169
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/settings/VolumeController;->mBackgroundHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/android/systemui/settings/VolumeController;->mStartListeningRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 170
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/android/systemui/settings/VolumeController;->mListening:Z

    .line 171
    invoke-direct {p0}, Lcom/android/systemui/settings/VolumeController;->registerReceiver()V

    .line 172
    return-void
.end method
