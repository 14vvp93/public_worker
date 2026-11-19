.class public Lcom/android/systemui/volume/JellyProgressView;
.super Landroid/view/View;
.source "JellyProgressView.java"


# instance fields
.field private audioManager:Landroid/media/AudioManager;

.field private bitmapShader:Landroid/graphics/BitmapShader;

.field private currentProgress:F

.field private currentSkinName:Ljava/lang/String;

.field private maxVolume:I

.field private progress:F

.field private progressAnimator:Landroid/animation/ValueAnimator;

.field private progressBitmap:Landroid/graphics/Bitmap;

.field private progressPaint:Landroid/graphics/Paint;

.field private progressRect:Landroid/graphics/RectF;

.field private streamType:I

.field private useClockwiseDirection:Z

.field protected volumeReceiver:Landroid/content/BroadcastReceiver;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 68
    invoke-direct {p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    .line 45
    const/high16 v0, 0x42480000    # 50.0f

    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progress:F

    .line 47
    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    .line 53
    const/4 v0, 0x3

    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->streamType:I

    .line 59
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/systemui/volume/JellyProgressView;->useClockwiseDirection:Z

    .line 69
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->init()V

    .line 70
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 73
    invoke-direct {p0, p1, p2}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 45
    const/high16 v0, 0x42480000    # 50.0f

    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progress:F

    .line 47
    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    .line 53
    const/4 v0, 0x3

    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->streamType:I

    .line 59
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/systemui/volume/JellyProgressView;->useClockwiseDirection:Z

    .line 74
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->init()V

    .line 75
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyleAttr"    # I

    .line 78
    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 45
    const/high16 v0, 0x42480000    # 50.0f

    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progress:F

    .line 47
    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    .line 53
    const/4 v0, 0x3

    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->streamType:I

    .line 59
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/systemui/volume/JellyProgressView;->useClockwiseDirection:Z

    .line 79
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->init()V

    .line 80
    return-void
.end method

.method static synthetic access$000(Lcom/android/systemui/volume/JellyProgressView;)I
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/volume/JellyProgressView;

    .line 33
    iget v0, p0, Lcom/android/systemui/volume/JellyProgressView;->streamType:I

    return v0
.end method

.method static synthetic access$100(Lcom/android/systemui/volume/JellyProgressView;)I
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/volume/JellyProgressView;

    .line 33
    iget v0, p0, Lcom/android/systemui/volume/JellyProgressView;->maxVolume:I

    return v0
.end method

.method static synthetic access$200(Lcom/android/systemui/volume/JellyProgressView;)F
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/volume/JellyProgressView;

    .line 33
    iget v0, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    return v0
.end method

.method static synthetic access$202(Lcom/android/systemui/volume/JellyProgressView;F)F
    .locals 0
    .param p0, "x0"    # Lcom/android/systemui/volume/JellyProgressView;
    .param p1, "x1"    # F

    .line 33
    iput p1, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    return p1
.end method

.method static synthetic access$300(Lcom/android/systemui/volume/JellyProgressView;)F
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/volume/JellyProgressView;

    .line 33
    iget v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progress:F

    return v0
.end method

.method public static getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 6
    .param p0, "property"    # Ljava/lang/String;
    .param p1, "defaultValue"    # Ljava/lang/String;

    .line 341
    :try_start_0
    const-string v0, "android.os.SystemProperties"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 342
    .local v0, "clazz":Ljava/lang/Class;
    const-string v1, "get"

    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Class;

    const-class v4, Ljava/lang/String;

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v0, v1, v3}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    .line 343
    .local v1, "getter":Ljava/lang/reflect/Method;
    const/4 v3, 0x0

    new-array v2, v2, [Ljava/lang/Object;

    aput-object p0, v2, v5

    invoke-virtual {v1, v3, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 344
    .local v2, "value":Ljava/lang/String;
    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v3
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    if-nez v3, :cond_0

    .line 345
    return-object v2

    .line 348
    .end local v0    # "clazz":Ljava/lang/Class;
    .end local v1    # "getter":Ljava/lang/reflect/Method;
    .end local v2    # "value":Ljava/lang/String;
    :cond_0
    goto :goto_0

    .line 347
    :catch_0
    move-exception v0

    .line 349
    :goto_0
    return-object p1
.end method

.method private init()V
    .locals 2

    .line 87
    new-instance v0, Landroid/graphics/Paint;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Landroid/graphics/Paint;-><init>(I)V

    iput-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressPaint:Landroid/graphics/Paint;

    .line 88
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressPaint:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 91
    new-instance v0, Landroid/graphics/RectF;

    invoke-direct {v0}, Landroid/graphics/RectF;-><init>()V

    iput-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressRect:Landroid/graphics/RectF;

    .line 94
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->setupProgressAnimation()V

    .line 97
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->loadProgressBitmap()V

    .line 100
    invoke-virtual {p0}, Lcom/android/systemui/volume/JellyProgressView;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "audio"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/AudioManager;

    iput-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->audioManager:Landroid/media/AudioManager;

    .line 101
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->audioManager:Landroid/media/AudioManager;

    iget v1, p0, Lcom/android/systemui/volume/JellyProgressView;->streamType:I

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamMaxVolume(I)I

    move-result v0

    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->maxVolume:I

    .line 102
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->updateProgressFromVolume()V

    .line 104
    return-void
.end method

.method private loadProgressBitmap()V
    .locals 5

    .line 137
    const/4 v0, 0x0

    .line 138
    .local v0, "resourceId":I
    const-string v1, "persist.sys.skin"

    const-string v2, "com.hcn.auto.theme.p1"

    invoke-static {v1, v2}, Lcom/android/systemui/volume/JellyProgressView;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->currentSkinName:Ljava/lang/String;

    .line 140
    const-string v1, "p38"

    iget-object v2, p0, Lcom/android/systemui/volume/JellyProgressView;->currentSkinName:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    xor-int/lit8 v1, v1, 0x1

    iput-boolean v1, p0, Lcom/android/systemui/volume/JellyProgressView;->useClockwiseDirection:Z

    .line 141
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->currentSkinName:Ljava/lang/String;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->currentSkinName:Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_0

    .line 142
    const v0, 0x7f080714

    .line 145
    :cond_0
    if-nez v0, :cond_1

    .line 146
    const v0, 0x7f0804ff

    .line 147
    const/4 v1, 0x0

    iput-boolean v1, p0, Lcom/android/systemui/volume/JellyProgressView;->useClockwiseDirection:Z

    .line 150
    :cond_1
    invoke-virtual {p0}, Lcom/android/systemui/volume/JellyProgressView;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-static {v1, v0}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v1

    iput-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressBitmap:Landroid/graphics/Bitmap;

    .line 151
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressBitmap:Landroid/graphics/Bitmap;

    if-eqz v1, :cond_2

    .line 152
    new-instance v1, Landroid/graphics/BitmapShader;

    iget-object v2, p0, Lcom/android/systemui/volume/JellyProgressView;->progressBitmap:Landroid/graphics/Bitmap;

    sget-object v3, Landroid/graphics/Shader$TileMode;->REPEAT:Landroid/graphics/Shader$TileMode;

    sget-object v4, Landroid/graphics/Shader$TileMode;->CLAMP:Landroid/graphics/Shader$TileMode;

    invoke-direct {v1, v2, v3, v4}, Landroid/graphics/BitmapShader;-><init>(Landroid/graphics/Bitmap;Landroid/graphics/Shader$TileMode;Landroid/graphics/Shader$TileMode;)V

    iput-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->bitmapShader:Landroid/graphics/BitmapShader;

    .line 153
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressPaint:Landroid/graphics/Paint;

    iget-object v2, p0, Lcom/android/systemui/volume/JellyProgressView;->bitmapShader:Landroid/graphics/BitmapShader;

    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setShader(Landroid/graphics/Shader;)Landroid/graphics/Shader;

    goto :goto_0

    .line 155
    :cond_2
    const-string v1, "ProgressBar"

    const-string v2, "Failed to load progress bitmap"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 157
    :goto_0
    return-void
.end method

.method private registerVolumeChangedReceiver()V
    .locals 3

    .line 110
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->volumeReceiver:Landroid/content/BroadcastReceiver;

    if-nez v0, :cond_0

    .line 111
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 112
    .local v0, "intentFilter":Landroid/content/IntentFilter;
    const-string v1, "android.media.VOLUME_CHANGED_ACTION"

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 113
    new-instance v1, Lcom/android/systemui/volume/JellyProgressView$1;

    invoke-direct {v1, p0}, Lcom/android/systemui/volume/JellyProgressView$1;-><init>(Lcom/android/systemui/volume/JellyProgressView;)V

    iput-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->volumeReceiver:Landroid/content/BroadcastReceiver;

    .line 128
    const-string v1, "JellyProgressView"

    const-string v2, "registerVolumeChangedReceiver"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 129
    invoke-virtual {p0}, Lcom/android/systemui/volume/JellyProgressView;->getContext()Landroid/content/Context;

    move-result-object v1

    iget-object v2, p0, Lcom/android/systemui/volume/JellyProgressView;->volumeReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 131
    .end local v0    # "intentFilter":Landroid/content/IntentFilter;
    :cond_0
    return-void
.end method

.method private setupProgressAnimation()V
    .locals 3

    .line 163
    const/4 v0, 0x2

    new-array v0, v0, [F

    fill-array-data v0, :array_0

    invoke-static {v0}, Landroid/animation/ValueAnimator;->ofFloat([F)Landroid/animation/ValueAnimator;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    .line 164
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    new-instance v1, Landroid/view/animation/DecelerateInterpolator;

    invoke-direct {v1}, Landroid/view/animation/DecelerateInterpolator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 165
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    const-wide/16 v1, 0x12c

    invoke-virtual {v0, v1, v2}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    .line 166
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    new-instance v1, Lcom/android/systemui/volume/JellyProgressView$2;

    invoke-direct {v1, p0}, Lcom/android/systemui/volume/JellyProgressView$2;-><init>(Lcom/android/systemui/volume/JellyProgressView;)V

    invoke-virtual {v0, v1}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    .line 175
    return-void

    :array_0
    .array-data 4
        0x0
        0x3f800000    # 1.0f
    .end array-data
.end method

.method private updateBitmapShader()V
    .locals 4

    .line 210
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressBitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_0

    .line 211
    new-instance v0, Landroid/graphics/BitmapShader;

    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressBitmap:Landroid/graphics/Bitmap;

    sget-object v2, Landroid/graphics/Shader$TileMode;->REPEAT:Landroid/graphics/Shader$TileMode;

    sget-object v3, Landroid/graphics/Shader$TileMode;->CLAMP:Landroid/graphics/Shader$TileMode;

    invoke-direct {v0, v1, v2, v3}, Landroid/graphics/BitmapShader;-><init>(Landroid/graphics/Bitmap;Landroid/graphics/Shader$TileMode;Landroid/graphics/Shader$TileMode;)V

    iput-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->bitmapShader:Landroid/graphics/BitmapShader;

    .line 212
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressPaint:Landroid/graphics/Paint;

    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->bitmapShader:Landroid/graphics/BitmapShader;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setShader(Landroid/graphics/Shader;)Landroid/graphics/Shader;

    .line 214
    :cond_0
    return-void
.end method

.method private updateProgressFromVolume()V
    .locals 3

    .line 267
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->audioManager:Landroid/media/AudioManager;

    iget v1, p0, Lcom/android/systemui/volume/JellyProgressView;->streamType:I

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v0

    .line 268
    .local v0, "currentVolume":I
    int-to-float v1, v0

    const/high16 v2, 0x42c80000    # 100.0f

    mul-float/2addr v1, v2

    iget v2, p0, Lcom/android/systemui/volume/JellyProgressView;->maxVolume:I

    int-to-float v2, v2

    div-float/2addr v1, v2

    iput v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progress:F

    .line 269
    iget v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progress:F

    iput v1, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    .line 270
    invoke-virtual {p0}, Lcom/android/systemui/volume/JellyProgressView;->invalidate()V

    .line 271
    return-void
.end method


# virtual methods
.method protected onAttachedToWindow()V
    .locals 0

    .line 285
    invoke-super {p0}, Landroid/view/View;->onAttachedToWindow()V

    .line 286
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->registerVolumeChangedReceiver()V

    .line 287
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->updateProgressFromVolume()V

    .line 288
    return-void
.end method

.method protected onDetachedFromWindow()V
    .locals 4

    .line 292
    invoke-super {p0}, Landroid/view/View;->onDetachedFromWindow()V

    .line 293
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    if-eqz v0, :cond_0

    .line 294
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->cancel()V

    .line 297
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->volumeReceiver:Landroid/content/BroadcastReceiver;

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    .line 299
    :try_start_0
    invoke-virtual {p0}, Lcom/android/systemui/volume/JellyProgressView;->getContext()Landroid/content/Context;

    move-result-object v0

    iget-object v2, p0, Lcom/android/systemui/volume/JellyProgressView;->volumeReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v2}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 300
    iput-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->volumeReceiver:Landroid/content/BroadcastReceiver;

    .line 301
    const-string v0, "JellyProgressView"

    const-string v2, "unregisterVolumeChangedReceiver"

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 304
    goto :goto_0

    .line 302
    :catch_0
    move-exception v0

    .line 303
    .local v0, "e":Ljava/lang/Exception;
    const-string v2, "JellyProgressView"

    const-string v3, "Error unregistering volume receiver"

    invoke-static {v2, v3, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 307
    .end local v0    # "e":Ljava/lang/Exception;
    :cond_1
    :goto_0
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressBitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_2

    .line 308
    iget-object v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progressBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V

    .line 309
    iput-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressBitmap:Landroid/graphics/Bitmap;

    .line 311
    :cond_2
    return-void
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 8
    .param p1, "canvas"    # Landroid/graphics/Canvas;

    .line 218
    invoke-super {p0, p1}, Landroid/view/View;->onDraw(Landroid/graphics/Canvas;)V

    .line 221
    iget v0, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    const v1, 0x3dcccccd    # 0.1f

    cmpg-float v0, v0, v1

    if-gtz v0, :cond_0

    .line 222
    return-void

    .line 225
    :cond_0
    const/high16 v0, 0x43340000    # 180.0f

    iget v1, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    const/high16 v2, 0x42c80000    # 100.0f

    div-float/2addr v1, v2

    mul-float/2addr v0, v1

    .line 228
    .local v0, "sweepAngle":F
    iget-boolean v1, p0, Lcom/android/systemui/volume/JellyProgressView;->useClockwiseDirection:Z

    if-eqz v1, :cond_1

    .line 229
    const/high16 v1, 0x43340000    # 180.0f

    .line 230
    .local v1, "startAngle":F
    iget-object v3, p0, Lcom/android/systemui/volume/JellyProgressView;->progressRect:Landroid/graphics/RectF;

    const/4 v6, 0x0

    iget-object v7, p0, Lcom/android/systemui/volume/JellyProgressView;->progressPaint:Landroid/graphics/Paint;

    move-object v2, p1

    move v4, v1

    move v5, v0

    invoke-virtual/range {v2 .. v7}, Landroid/graphics/Canvas;->drawArc(Landroid/graphics/RectF;FFZLandroid/graphics/Paint;)V

    goto :goto_0

    .line 233
    .end local v1    # "startAngle":F
    :cond_1
    const/high16 v1, 0x42b40000    # 90.0f

    .line 234
    .restart local v1    # "startAngle":F
    neg-float v0, v0

    .line 238
    :goto_0
    iget-object v3, p0, Lcom/android/systemui/volume/JellyProgressView;->progressRect:Landroid/graphics/RectF;

    const/4 v6, 0x1

    iget-object v7, p0, Lcom/android/systemui/volume/JellyProgressView;->progressPaint:Landroid/graphics/Paint;

    move-object v2, p1

    move v4, v1

    move v5, v0

    invoke-virtual/range {v2 .. v7}, Landroid/graphics/Canvas;->drawArc(Landroid/graphics/RectF;FFZLandroid/graphics/Paint;)V

    .line 239
    return-void
.end method

.method protected onSizeChanged(IIII)V
    .locals 9
    .param p1, "w"    # I
    .param p2, "h"    # I
    .param p3, "oldw"    # I
    .param p4, "oldh"    # I

    .line 179
    invoke-super {p0, p1, p2, p3, p4}, Landroid/view/View;->onSizeChanged(IIII)V

    .line 182
    move v0, p2

    .line 184
    .local v0, "diameter":I
    iget-boolean v1, p0, Lcom/android/systemui/volume/JellyProgressView;->useClockwiseDirection:Z

    if-eqz v1, :cond_0

    .line 185
    int-to-float v1, p1

    const/high16 v2, 0x40000000    # 2.0f

    div-float/2addr v1, v2

    .line 186
    .local v1, "centerX":F
    int-to-float v2, p2

    .line 189
    .local v2, "centerY":F
    int-to-float v3, p2

    .line 191
    .local v3, "radius":F
    iget-object v4, p0, Lcom/android/systemui/volume/JellyProgressView;->progressRect:Landroid/graphics/RectF;

    sub-float v5, v1, v3

    sub-float v6, v2, v3

    add-float v7, v1, v3

    add-float v8, v2, v3

    invoke-virtual {v4, v5, v6, v7, v8}, Landroid/graphics/RectF;->set(FFFF)V

    .line 197
    .end local v1    # "centerX":F
    .end local v2    # "centerY":F
    .end local v3    # "radius":F
    goto :goto_0

    .line 200
    :cond_0
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressRect:Landroid/graphics/RectF;

    neg-int v2, v0

    div-int/lit8 v2, v2, 0x2

    int-to-float v2, v2

    const/4 v3, 0x0

    div-int/lit8 v4, v0, 0x2

    int-to-float v4, v4

    int-to-float v5, v0

    invoke-virtual {v1, v2, v3, v4, v5}, Landroid/graphics/RectF;->set(FFFF)V

    .line 203
    :goto_0
    invoke-direct {p0}, Lcom/android/systemui/volume/JellyProgressView;->updateBitmapShader()V

    .line 204
    return-void
.end method

.method public setProgress(F)V
    .locals 2
    .param p1, "progress"    # F

    .line 246
    const/high16 v0, 0x42c80000    # 100.0f

    invoke-static {v0, p1}, Ljava/lang/Math;->min(FF)F

    move-result v0

    const/4 v1, 0x0

    invoke-static {v1, v0}, Ljava/lang/Math;->max(FF)F

    move-result v0

    .line 247
    .local v0, "newProgress":F
    iget v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progress:F

    cmpl-float v1, v1, v0

    if-eqz v1, :cond_2

    .line 248
    iput v0, p0, Lcom/android/systemui/volume/JellyProgressView;->progress:F

    .line 251
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    if-eqz v1, :cond_1

    .line 252
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v1}, Landroid/animation/ValueAnimator;->isRunning()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 253
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v1}, Landroid/animation/ValueAnimator;->cancel()V

    .line 255
    :cond_0
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView;->progressAnimator:Landroid/animation/ValueAnimator;

    invoke-virtual {v1}, Landroid/animation/ValueAnimator;->start()V

    goto :goto_0

    .line 257
    :cond_1
    iput p1, p0, Lcom/android/systemui/volume/JellyProgressView;->currentProgress:F

    .line 258
    invoke-virtual {p0}, Lcom/android/systemui/volume/JellyProgressView;->invalidate()V

    .line 261
    :cond_2
    :goto_0
    return-void
.end method
