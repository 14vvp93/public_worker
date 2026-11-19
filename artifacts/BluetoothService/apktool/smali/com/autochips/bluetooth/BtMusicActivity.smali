.class public Lcom/autochips/bluetooth/BtMusicActivity;
.super Lcom/hcn/skin/support/app/SkinCompatActivity;
.source "BtMusicActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;
    }
.end annotation


# static fields
.field public static final MSG_A2DP_CONNECT_STATE_CHANGE:I = 0x2

.field public static final MSG_AVRCP_CONNECT_STATE_CHANGE:I = 0x3

.field public static final MSG_UPDATE_METADATA:I = 0x1

.field public static final MSG_UPDATE_PLAY_STATE:I = 0x5

.field public static final MSG_UPDATE_SEEKBAR:I = 0x4

.field private static final TAG:Ljava/lang/String; = "BTMusicActivity"

.field public static final VOICE_EVENT_EXIT:Ljava/lang/String; = "com.btmusic.extra.exit"


# instance fields
.field private a2dpsinkStateInfo:Landroid/widget/TextView;

.field private avrcpctStateInfo:Landroid/widget/TextView;

.field private btnPause:Landroid/widget/Button;

.field private btnPlay:Landroid/widget/Button;

.field private btnStartBt:Landroid/widget/ImageButton;

.field private btnVolDown:Landroid/widget/ImageButton;

.field private btnVolUp:Landroid/widget/ImageButton;

.field private btn_music_eq:Landroid/widget/Button;

.field private connectState:Landroid/widget/ImageView;

.field public mA2dpsinkstate:I

.field private mAudioManager:Landroid/media/AudioManager;

.field public mAvrcpctstate:I

.field private final mBTMusicCallback:Lcom/hcn/bluetooth/service/IMusicCallback;

.field mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

.field mConnectionListener:Lcom/hcn/bluetooth/api/ConnectionListener;

.field private mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

.field private mMainHandler:Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

.field private mMusicPlayingProgressBar:Landroid/widget/ProgressBar;

.field private final mReceiver:Landroid/content/BroadcastReceiver;

.field private mRotateAnim:Landroid/animation/ObjectAnimator;

.field private mediaAlbumInfo:Landroid/widget/TextView;

.field private mediaArtistInfo:Landroid/widget/TextView;

.field private mediaBtName:Landroid/widget/TextView;

.field private mediaLengthInfo:Landroid/widget/TextView;

.field private mediaPlayingPositionInfo:Landroid/widget/TextView;

.field private mediaTitleInfo:Landroid/widget/TextView;

.field private musicAlbum:Ljava/lang/String;

.field private musicArtist:Ljava/lang/String;

.field private musicDuration:I

.field private musicPosition:I

.field private musicTitle:Ljava/lang/String;

.field private playingAnimation:Landroid/widget/ImageView;


# direct methods
.method public constructor <init>()V
    .locals 3

    .line 48
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;-><init>()V

    const/4 v0, 0x0

    .line 57
    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMusicPlayingProgressBar:Landroid/widget/ProgressBar;

    .line 71
    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->playingAnimation:Landroid/widget/ImageView;

    .line 72
    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mRotateAnim:Landroid/animation/ObjectAnimator;

    const/4 v1, 0x0

    .line 75
    iput v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    .line 76
    iput v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    const-string v2, ""

    .line 81
    iput-object v2, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicTitle:Ljava/lang/String;

    .line 82
    iput-object v2, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicArtist:Ljava/lang/String;

    .line 83
    iput-object v2, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicAlbum:Ljava/lang/String;

    .line 85
    iput v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicDuration:I

    .line 86
    iput v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    .line 89
    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mAudioManager:Landroid/media/AudioManager;

    .line 91
    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMainHandler:Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    .line 452
    new-instance v0, Lcom/autochips/bluetooth/BtMusicActivity$2;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/BtMusicActivity$2;-><init>(Lcom/autochips/bluetooth/BtMusicActivity;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mReceiver:Landroid/content/BroadcastReceiver;

    .line 481
    new-instance v0, Lcom/autochips/bluetooth/BtMusicActivity$3;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/BtMusicActivity$3;-><init>(Lcom/autochips/bluetooth/BtMusicActivity;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBTMusicCallback:Lcom/hcn/bluetooth/service/IMusicCallback;

    .line 533
    new-instance v0, Lcom/autochips/bluetooth/BtMusicActivity$4;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/BtMusicActivity$4;-><init>(Lcom/autochips/bluetooth/BtMusicActivity;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mConnectionListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    return-void
.end method

.method static synthetic access$000(Lcom/autochips/bluetooth/BtMusicActivity;I)V
    .locals 0

    .line 48
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->updateA2dpConnectStatus(I)V

    return-void
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/BtMusicActivity;I)V
    .locals 0

    .line 48
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->updateAvrcpConnectStatus(I)V

    return-void
.end method

.method static synthetic access$1000(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;
    .locals 0

    .line 48
    iget-object p0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMainHandler:Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    return-object p0
.end method

.method static synthetic access$1100(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/hcn/bluetooth/service/IMusicCallback;
    .locals 0

    .line 48
    iget-object p0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBTMusicCallback:Lcom/hcn/bluetooth/service/IMusicCallback;

    return-object p0
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/BtMusicActivity;)Ljava/lang/String;
    .locals 0

    .line 48
    iget-object p0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicTitle:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$202(Lcom/autochips/bluetooth/BtMusicActivity;Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 48
    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicTitle:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$300(Lcom/autochips/bluetooth/BtMusicActivity;)Ljava/lang/String;
    .locals 0

    .line 48
    iget-object p0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicArtist:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$302(Lcom/autochips/bluetooth/BtMusicActivity;Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 48
    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicArtist:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$400(Lcom/autochips/bluetooth/BtMusicActivity;)Ljava/lang/String;
    .locals 0

    .line 48
    iget-object p0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicAlbum:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$402(Lcom/autochips/bluetooth/BtMusicActivity;Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 48
    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicAlbum:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$500(Lcom/autochips/bluetooth/BtMusicActivity;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    .line 48
    invoke-direct {p0, p1, p2, p3}, Lcom/autochips/bluetooth/BtMusicActivity;->updateMetadata(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$600(Lcom/autochips/bluetooth/BtMusicActivity;)V
    .locals 0

    .line 48
    invoke-direct {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->updateMusicPlayingProgress()V

    return-void
.end method

.method static synthetic access$700(Lcom/autochips/bluetooth/BtMusicActivity;)Lcom/hcn/bluetooth/api/MusicPlayState;
    .locals 0

    .line 48
    iget-object p0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    return-object p0
.end method

.method static synthetic access$702(Lcom/autochips/bluetooth/BtMusicActivity;Lcom/hcn/bluetooth/api/MusicPlayState;)Lcom/hcn/bluetooth/api/MusicPlayState;
    .locals 0

    .line 48
    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    return-object p1
.end method

.method static synthetic access$800(Lcom/autochips/bluetooth/BtMusicActivity;I)V
    .locals 0

    .line 48
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->updatePlayPauseButton(I)V

    return-void
.end method

.method static synthetic access$900(Lcom/autochips/bluetooth/BtMusicActivity;)V
    .locals 0

    .line 48
    invoke-direct {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->exit()V

    return-void
.end method

.method private exit()V
    .locals 2

    const-string v0, "persist.sys.media_exit"

    const-string v1, "1"

    .line 464
    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 465
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 467
    :try_start_0
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->unRegMusicClientBinder()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 472
    :catch_0
    :cond_0
    invoke-virtual {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->finish()V

    return-void
.end method

.method private initAnimation()V
    .locals 3

    .line 335
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->playingAnimation:Landroid/widget/ImageView;

    if-eqz v0, :cond_0

    const/4 v1, 0x2

    new-array v1, v1, [F

    .line 336
    fill-array-data v1, :array_0

    const-string v2, "rotation"

    invoke-static {v0, v2, v1}, Landroid/animation/ObjectAnimator;->ofFloat(Ljava/lang/Object;Ljava/lang/String;[F)Landroid/animation/ObjectAnimator;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mRotateAnim:Landroid/animation/ObjectAnimator;

    const-wide/16 v1, 0x1388

    .line 337
    invoke-virtual {v0, v1, v2}, Landroid/animation/ObjectAnimator;->setDuration(J)Landroid/animation/ObjectAnimator;

    .line 338
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mRotateAnim:Landroid/animation/ObjectAnimator;

    new-instance v1, Landroid/view/animation/LinearInterpolator;

    invoke-direct {v1}, Landroid/view/animation/LinearInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/animation/ObjectAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 339
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mRotateAnim:Landroid/animation/ObjectAnimator;

    const/4 v1, -0x1

    invoke-virtual {v0, v1}, Landroid/animation/ObjectAnimator;->setRepeatCount(I)V

    .line 340
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mRotateAnim:Landroid/animation/ObjectAnimator;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/animation/ObjectAnimator;->setRepeatMode(I)V

    .line 341
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mRotateAnim:Landroid/animation/ObjectAnimator;

    invoke-virtual {v0}, Landroid/animation/ObjectAnimator;->start()V

    :cond_0
    return-void

    nop

    :array_0
    .array-data 4
        0x0
        0x43b40000    # 360.0f
    .end array-data
.end method

.method private initView()V
    .locals 3

    const v0, 0x7f0800ee

    .line 263
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    .line 264
    invoke-static {}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getInstance()Lcom/hcn/auto/theme/utils/ThemeUtils;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getAppWallpaper()Landroid/graphics/drawable/Drawable;

    move-result-object v1

    if-eqz v0, :cond_0

    if-eqz v1, :cond_0

    .line 266
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :cond_0
    const v0, 0x7f080163

    .line 269
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->a2dpsinkStateInfo:Landroid/widget/TextView;

    const v0, 0x7f080164

    .line 271
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->avrcpctStateInfo:Landroid/widget/TextView;

    const v0, 0x7f08004e

    .line 273
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->connectState:Landroid/widget/ImageView;

    const v0, 0x7f0800e1

    .line 275
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->playingAnimation:Landroid/widget/ImageView;

    const v0, 0x7f080167

    .line 277
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaBtName:Landroid/widget/TextView;

    .line 282
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnVolDown:Landroid/widget/ImageButton;

    if-eqz v0, :cond_1

    .line 283
    invoke-virtual {v0, p0}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 285
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnVolUp:Landroid/widget/ImageButton;

    if-eqz v0, :cond_2

    .line 286
    invoke-virtual {v0, p0}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 288
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnStartBt:Landroid/widget/ImageButton;

    if-eqz v0, :cond_3

    .line 289
    invoke-virtual {v0, p0}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_3
    const v0, 0x7f080084

    .line 293
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnPause:Landroid/widget/Button;

    const v0, 0x7f080085

    .line 294
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnPlay:Landroid/widget/Button;

    const v0, 0x7f080082

    .line 295
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btn_music_eq:Landroid/widget/Button;

    .line 296
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnPause:Landroid/widget/Button;

    if-eqz v0, :cond_4

    .line 297
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 299
    :cond_4
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnPlay:Landroid/widget/Button;

    if-eqz v0, :cond_5

    .line 300
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 302
    :cond_5
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btn_music_eq:Landroid/widget/Button;

    if-eqz v0, :cond_6

    .line 303
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_6
    const v0, 0x7f080086

    .line 306
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f080083

    .line 307
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f080171

    .line 310
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaTitleInfo:Landroid/widget/TextView;

    const v0, 0x7f08016e

    .line 311
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaArtistInfo:Landroid/widget/TextView;

    const v0, 0x7f08016d

    .line 312
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaAlbumInfo:Landroid/widget/TextView;

    const v0, 0x7f08016f

    .line 313
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaPlayingPositionInfo:Landroid/widget/TextView;

    const v0, 0x7f080172

    .line 314
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaLengthInfo:Landroid/widget/TextView;

    const v0, 0x7f080170

    .line 315
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMusicPlayingProgressBar:Landroid/widget/ProgressBar;

    .line 319
    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_7

    .line 320
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMusicPlayingProgressBar:Landroid/widget/ProgressBar;

    new-instance v1, Lcom/autochips/bluetooth/BtMusicActivity$1;

    invoke-direct {v1, p0}, Lcom/autochips/bluetooth/BtMusicActivity$1;-><init>(Lcom/autochips/bluetooth/BtMusicActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/ProgressBar;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 327
    :cond_7
    invoke-direct {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->updateMusicPlayingProgress()V

    .line 328
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicTitle:Ljava/lang/String;

    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicArtist:Ljava/lang/String;

    iget-object v2, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicAlbum:Ljava/lang/String;

    invoke-direct {p0, v0, v1, v2}, Lcom/autochips/bluetooth/BtMusicActivity;->updateMetadata(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    const/4 v0, 0x2

    .line 329
    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->updatePlayPauseButton(I)V

    .line 331
    invoke-direct {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->initAnimation()V

    return-void
.end method

.method private onVolDown()V
    .locals 4

    .line 448
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    const/4 v2, -0x1

    const/4 v3, 0x1

    invoke-virtual {v0, v1, v2, v3}, Landroid/media/AudioManager;->adjustStreamVolume(III)V

    return-void
.end method

.method private onVolUp()V
    .locals 3

    .line 443
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mAudioManager:Landroid/media/AudioManager;

    const/4 v1, 0x3

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2, v2}, Landroid/media/AudioManager;->adjustStreamVolume(III)V

    return-void
.end method

.method private updateA2dpConnectStatus(I)V
    .locals 2

    const/4 v0, 0x2

    if-ne p1, v0, :cond_0

    .line 205
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->a2dpsinkStateInfo:Landroid/widget/TextView;

    const v0, 0x7f0d0002

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 206
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->connectState:Landroid/widget/ImageView;

    const v0, 0x7f070073

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 207
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaBtName:Landroid/widget/TextView;

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    if-eqz p1, :cond_1

    .line 208
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object p1

    .line 209
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "updateA2dpConnectStatus: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "BTMusicActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 210
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaBtName:Landroid/widget/TextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 211
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->a2dpsinkStateInfo:Landroid/widget/TextView;

    const v0, 0x7f0d0071

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 214
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->a2dpsinkStateInfo:Landroid/widget/TextView;

    const v0, 0x7f0d0003

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 215
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->connectState:Landroid/widget/ImageView;

    const v1, 0x7f07007d

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {p1, v1}, Landroid/widget/ImageView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 216
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaBtName:Landroid/widget/TextView;

    if-eqz p1, :cond_1

    .line 217
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_1
    :goto_0
    return-void
.end method

.method private updateAvrcpConnectStatus(I)V
    .locals 1

    const/4 v0, 0x2

    if-ne p1, v0, :cond_0

    .line 224
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->avrcpctStateInfo:Landroid/widget/TextView;

    const v0, 0x7f0d002a

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 226
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->avrcpctStateInfo:Landroid/widget/TextView;

    const v0, 0x7f0d002b

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :goto_0
    return-void
.end method

.method private updateMetadata(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .locals 1

    .line 165
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 166
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaTitleInfo:Landroid/widget/TextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 168
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaTitleInfo:Landroid/widget/TextView;

    const v0, 0x7f0d0096

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 171
    :goto_0
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-nez p1, :cond_1

    .line 172
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaArtistInfo:Landroid/widget/TextView;

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_1

    .line 174
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaArtistInfo:Landroid/widget/TextView;

    const p2, 0x7f0d0095

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 177
    :goto_1
    invoke-static {p3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-nez p1, :cond_2

    .line 178
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaAlbumInfo:Landroid/widget/TextView;

    invoke-virtual {p1, p3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_2

    .line 180
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaAlbumInfo:Landroid/widget/TextView;

    const p2, 0x7f0d0094

    invoke-static {p2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :goto_2
    return-void
.end method

.method private updateMusicPlayingProgress()V
    .locals 5

    .line 135
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    if-eqz v0, :cond_1

    .line 136
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/MusicPlayState;->getState()I

    move-result v0

    const/4 v1, 0x3

    const v2, 0x3a83126f    # 0.001f

    if-eq v0, v1, :cond_0

    .line 149
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/MusicPlayState;->getDuration()J

    move-result-wide v0

    long-to-float v0, v0

    mul-float/2addr v0, v2

    float-to-int v0, v0

    iput v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicDuration:I

    .line 150
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/MusicPlayState;->getPosition()J

    move-result-wide v0

    long-to-float v0, v0

    mul-float/2addr v0, v2

    float-to-int v0, v0

    iput v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    goto :goto_0

    .line 139
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/MusicPlayState;->getPosition()J

    move-result-wide v0

    long-to-float v0, v0

    mul-float/2addr v0, v2

    float-to-int v0, v0

    iput v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    .line 140
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v0

    iget-object v3, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    .line 141
    invoke-virtual {v3}, Lcom/hcn/bluetooth/api/MusicPlayState;->getLastPositionUpdateTime()J

    move-result-wide v3

    sub-long/2addr v0, v3

    long-to-float v0, v0

    mul-float/2addr v0, v2

    float-to-int v0, v0

    .line 142
    iget v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    int-to-float v1, v1

    int-to-float v0, v0

    iget-object v3, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    invoke-virtual {v3}, Lcom/hcn/bluetooth/api/MusicPlayState;->getPlaybackSpeed()F

    move-result v3

    mul-float/2addr v0, v3

    add-float/2addr v1, v0

    float-to-int v0, v1

    iput v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    .line 143
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/MusicPlayState;->getDuration()J

    move-result-wide v0

    long-to-float v0, v0

    mul-float/2addr v0, v2

    float-to-int v0, v0

    iput v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicDuration:I

    .line 144
    iget v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    if-le v1, v0, :cond_1

    .line 145
    iput v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    .line 155
    :cond_1
    :goto_0
    iget v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    int-to-long v0, v0

    invoke-static {v0, v1}, Landroid/text/format/DateUtils;->formatElapsedTime(J)Ljava/lang/String;

    move-result-object v0

    .line 156
    iget v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicDuration:I

    int-to-long v1, v1

    invoke-static {v1, v2}, Landroid/text/format/DateUtils;->formatElapsedTime(J)Ljava/lang/String;

    move-result-object v1

    .line 157
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "updateProgress: pos="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " duration="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v3, "BTMusicActivity"

    invoke-static {v3, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 158
    iget-object v2, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaLengthInfo:Landroid/widget/TextView;

    invoke-virtual {v2, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 159
    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mediaPlayingPositionInfo:Landroid/widget/TextView;

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 160
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMusicPlayingProgressBar:Landroid/widget/ProgressBar;

    iget v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicDuration:I

    invoke-virtual {v0, v1}, Landroid/widget/ProgressBar;->setMax(I)V

    .line 161
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMusicPlayingProgressBar:Landroid/widget/ProgressBar;

    iget v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->musicPosition:I

    invoke-virtual {v0, v1}, Landroid/widget/ProgressBar;->setProgress(I)V

    return-void
.end method

.method private updatePlayPauseButton(I)V
    .locals 3

    const/16 v0, 0x8

    const/4 v1, 0x0

    const/4 v2, 0x3

    if-eq p1, v2, :cond_0

    .line 194
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnPause:Landroid/widget/Button;

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setVisibility(I)V

    .line 195
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnPlay:Landroid/widget/Button;

    invoke-virtual {p1, v1}, Landroid/widget/Button;->setVisibility(I)V

    .line 196
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mRotateAnim:Landroid/animation/ObjectAnimator;

    if-eqz p1, :cond_1

    .line 197
    invoke-virtual {p1}, Landroid/animation/ObjectAnimator;->pause()V

    goto :goto_0

    .line 187
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnPause:Landroid/widget/Button;

    invoke-virtual {p1, v1}, Landroid/widget/Button;->setVisibility(I)V

    .line 188
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->btnPlay:Landroid/widget/Button;

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setVisibility(I)V

    .line 189
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mRotateAnim:Landroid/animation/ObjectAnimator;

    if-eqz p1, :cond_1

    .line 190
    invoke-virtual {p1}, Landroid/animation/ObjectAnimator;->resume()V

    :cond_1
    :goto_0
    return-void
.end method


# virtual methods
.method public onBackPressed()V
    .locals 0

    .line 477
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onBackPressed()V

    .line 478
    invoke-direct {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->exit()V

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 1

    .line 414
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result p1

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    .line 423
    :pswitch_0
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    const/4 v0, 0x5

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->send_Avrcp_Cmd(I)V

    goto :goto_0

    .line 417
    :pswitch_1
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->send_Avrcp_Cmd(I)V

    goto :goto_0

    .line 420
    :pswitch_2
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    const/4 v0, 0x1

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->send_Avrcp_Cmd(I)V

    goto :goto_0

    .line 426
    :pswitch_3
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    const/4 v0, 0x4

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->send_Avrcp_Cmd(I)V

    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x7f080083
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 3

    const-string v0, "BTMusicActivity"

    const-string v1, "onCreate"

    .line 232
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 233
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x15

    if-lt v0, v1, :cond_0

    .line 234
    invoke-virtual {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->getWindow()Landroid/view/Window;

    move-result-object v0

    const/high16 v1, 0x4000000

    .line 235
    invoke-virtual {v0, v1}, Landroid/view/Window;->clearFlags(I)V

    .line 236
    invoke-virtual {v0}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object v1

    const/16 v2, 0x500

    invoke-virtual {v1, v2}, Landroid/view/View;->setSystemUiVisibility(I)V

    const/high16 v1, -0x80000000

    .line 238
    invoke-virtual {v0, v1}, Landroid/view/Window;->addFlags(I)V

    const/4 v1, 0x0

    .line 239
    invoke-virtual {v0, v1}, Landroid/view/Window;->setStatusBarColor(I)V

    .line 241
    :cond_0
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onCreate(Landroid/os/Bundle;)V

    const p1, 0x7f0b0022

    .line 243
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->setContentView(I)V

    const-string p1, "audio"

    .line 244
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/media/AudioManager;

    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mAudioManager:Landroid/media/AudioManager;

    .line 245
    new-instance p1, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {p1, p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;-><init>(Lcom/autochips/bluetooth/BtMusicActivity;Landroid/os/Looper;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMainHandler:Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    .line 246
    invoke-static {}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->getInstance()Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    move-result-object p1

    invoke-virtual {p1, p0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->init(Landroid/content/Context;)Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    .line 247
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mConnectionListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    .line 248
    iget-object p1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object v0

    invoke-virtual {v0}, Lcom/autochips/bluetooth/MyApplication;->getBinder()Landroid/os/IBinder;

    move-result-object v0

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->regMusicClientBinder(Landroid/os/IBinder;)V

    .line 250
    invoke-direct {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->initView()V

    .line 252
    new-instance p1, Landroid/content/IntentFilter;

    invoke-direct {p1}, Landroid/content/IntentFilter;-><init>()V

    const-string v0, "com.btmusic.extra.exit"

    .line 253
    invoke-virtual {p1, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 254
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v0, p1}, Lcom/autochips/bluetooth/BtMusicActivity;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 256
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->isHeadrest()Z

    move-result p1

    if-eqz p1, :cond_1

    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->isA2dpSourceRole()Z

    move-result p1

    if-eqz p1, :cond_1

    .line 257
    invoke-virtual {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    const v0, 0x7f0d0056

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    .line 258
    invoke-virtual {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->finish()V

    :cond_1
    return-void
.end method

.method protected onDestroy()V
    .locals 2

    const-string v0, "BTMusicActivity"

    const-string v1, "onDestroy"

    .line 397
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 398
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onDestroy()V

    .line 399
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mConnectionListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    if-eqz v0, :cond_0

    .line 400
    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v1, v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    .line 402
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMainHandler:Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    if-eqz v0, :cond_1

    const/4 v1, 0x0

    .line 403
    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    .line 405
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mReceiver:Landroid/content/BroadcastReceiver;

    if-eqz v0, :cond_2

    .line 406
    invoke-virtual {p0, v0}, Lcom/autochips/bluetooth/BtMusicActivity;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    :cond_2
    return-void
.end method

.method protected onPause()V
    .locals 2

    const-string v0, "BTMusicActivity"

    const-string v1, "onPause"

    .line 383
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 384
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onPause()V

    return-void
.end method

.method protected onResume()V
    .locals 7

    const-string v0, "BTMusicActivity"

    const-string v1, "onResume"

    .line 357
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 358
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onResume()V

    .line 359
    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->requestA2dp()V

    .line 360
    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isAvrcpConnected()Z

    move-result v1

    .line 361
    iget-object v2, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isA2dpConnected()Z

    move-result v2

    const/4 v3, 0x2

    const/4 v4, 0x0

    if-eqz v2, :cond_0

    .line 363
    iput v3, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    goto :goto_0

    .line 365
    :cond_0
    iput v4, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    .line 366
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->isHeadrest()Z

    move-result v5

    if-eqz v5, :cond_1

    .line 367
    invoke-virtual {p0}, Lcom/autochips/bluetooth/BtMusicActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v5

    const v6, 0x7f0d0081

    invoke-static {v6}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    :cond_1
    :goto_0
    if-eqz v1, :cond_2

    .line 371
    iput v3, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    goto :goto_1

    .line 373
    :cond_2
    iput v4, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    .line 375
    :goto_1
    iget v3, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mA2dpsinkstate:I

    invoke-direct {p0, v3}, Lcom/autochips/bluetooth/BtMusicActivity;->updateA2dpConnectStatus(I)V

    .line 376
    iget v3, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mAvrcpctstate:I

    invoke-direct {p0, v3}, Lcom/autochips/bluetooth/BtMusicActivity;->updateAvrcpConnectStatus(I)V

    .line 378
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "isAvrcpConnected="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, " isA2dpConnected="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method protected onStart()V
    .locals 2

    .line 347
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onStart()V

    const-string v0, "BTMusicActivity"

    const-string v1, "onStart"

    .line 348
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 349
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isReady()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    .line 350
    iput-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mLastMusicPlayState:Lcom/hcn/bluetooth/api/MusicPlayState;

    .line 351
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBTMusicCallback:Lcom/hcn/bluetooth/service/IMusicCallback;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->registerBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V

    :cond_0
    return-void
.end method

.method protected onStop()V
    .locals 2

    .line 389
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatActivity;->onStop()V

    .line 390
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "unRegisterBTMusicCallback: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBTMusicCallback:Lcom/hcn/bluetooth/service/IMusicCallback;

    invoke-virtual {v1}, Ljava/lang/Object;->hashCode()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "BTMusicActivity"

    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 391
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBluetoothMusicManager:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mBTMusicCallback:Lcom/hcn/bluetooth/service/IMusicCallback;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->unRegisterBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V

    .line 392
    iget-object v0, p0, Lcom/autochips/bluetooth/BtMusicActivity;->mMainHandler:Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/BtMusicActivity$MainHandler;->removeMessages(I)V

    return-void
.end method
