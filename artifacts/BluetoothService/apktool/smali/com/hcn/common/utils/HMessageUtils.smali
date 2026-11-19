.class public Lcom/hcn/common/utils/HMessageUtils;
.super Ljava/lang/Object;
.source "HMessageUtils.java"

# interfaces
.implements Landroid/os/Handler$Callback;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/utils/HMessageUtils$LazyHolder;,
        Lcom/hcn/common/utils/HMessageUtils$UiMessage;,
        Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;,
        Lcom/hcn/common/utils/HMessageUtils$Builder;
    }
.end annotation


# static fields
.field private static final DEBUG:Z

.field private static final TAG:Ljava/lang/String; = "UiMessageUtils"

.field private static final sPool:Ljava/util/concurrent/ConcurrentHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/ConcurrentHashMap<",
            "Ljava/lang/String;",
            "Lcom/hcn/common/utils/HMessageUtils;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field private final mDefensiveCopyList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;",
            ">;"
        }
    .end annotation
.end field

.field private final mHandler:Landroid/os/Handler;

.field private final mListenersSpecific:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Ljava/util/List<",
            "Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;",
            ">;>;"
        }
    .end annotation
.end field

.field private final mListenersUniversal:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;",
            ">;"
        }
    .end annotation
.end field

.field private final mMessage:Lcom/hcn/common/utils/HMessageUtils$UiMessage;

.field private final mName:Ljava/lang/String;


# direct methods
.method static bridge synthetic -$$Nest$sfgetsPool()Ljava/util/concurrent/ConcurrentHashMap;
    .locals 1

    sget-object v0, Lcom/hcn/common/utils/HMessageUtils;->sPool:Ljava/util/concurrent/ConcurrentHashMap;

    return-object v0
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 28
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->isAppDebug()Z

    move-result v0

    sput-boolean v0, Lcom/hcn/common/utils/HMessageUtils;->DEBUG:Z

    .line 45
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lcom/hcn/common/utils/HMessageUtils;->sPool:Ljava/util/concurrent/ConcurrentHashMap;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;)V
    .locals 2

    .line 56
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 31
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1, p0}, Landroid/os/Handler;-><init>(Landroid/os/Looper;Landroid/os/Handler$Callback;)V

    iput-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    .line 32
    new-instance v0, Lcom/hcn/common/utils/HMessageUtils$UiMessage;

    const/4 v1, 0x0

    invoke-direct {v0, v1, v1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;-><init>(Landroid/os/Message;Lcom/hcn/common/utils/HMessageUtils$UiMessage-IA;)V

    iput-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mMessage:Lcom/hcn/common/utils/HMessageUtils$UiMessage;

    .line 34
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    .line 35
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    .line 36
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mDefensiveCopyList:Ljava/util/List;

    .line 57
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 61
    iput-object p1, p0, Lcom/hcn/common/utils/HMessageUtils;->mName:Ljava/lang/String;

    return-void

    .line 58
    :cond_0
    new-instance p1, Ljava/security/InvalidParameterException;

    const-string v0, "HMessageUtils must have a name!"

    invoke-direct {p1, v0}, Ljava/security/InvalidParameterException;-><init>(Ljava/lang/String;)V

    throw p1
.end method

.method synthetic constructor <init>(Ljava/lang/String;Lcom/hcn/common/utils/HMessageUtils-IA;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/hcn/common/utils/HMessageUtils;-><init>(Ljava/lang/String;)V

    return-void
.end method

.method public static getInstance()Lcom/hcn/common/utils/HMessageUtils;
    .locals 1

    .line 48
    invoke-static {}, Lcom/hcn/common/utils/HMessageUtils$LazyHolder;->-$$Nest$sfgetINSTANCE()Lcom/hcn/common/utils/HMessageUtils;

    move-result-object v0

    return-object v0
.end method

.method private logMessageHandling(Lcom/hcn/common/utils/HMessageUtils$UiMessage;)V
    .locals 6

    .line 364
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    invoke-virtual {p1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->getId()I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/List;

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eqz v0, :cond_1

    .line 365
    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v3

    if-eqz v3, :cond_0

    goto :goto_0

    :cond_0
    move v3, v1

    goto :goto_1

    :cond_1
    :goto_0
    move v3, v2

    :goto_1
    if-eqz v3, :cond_2

    .line 367
    iget-object v3, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    if-nez v3, :cond_2

    const-string v0, "UiMessageUtils"

    .line 368
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Delivering FAILED for message ID "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {p1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->getId()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ". No listeners. "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {p1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_7

    .line 370
    :cond_2
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Delivering message ID "

    .line 371
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 372
    invoke-virtual {p1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->getId()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, ", Specific listeners: "

    .line 373
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    if-eqz v0, :cond_6

    .line 374
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v4

    if-nez v4, :cond_3

    goto :goto_3

    .line 377
    :cond_3
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, " ["

    .line 378
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move v4, v1

    .line 379
    :goto_2
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v5

    if-ge v4, v5, :cond_5

    .line 380
    invoke-interface {v0, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;

    invoke-virtual {v5}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 381
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v5

    sub-int/2addr v5, v2

    if-ge v4, v5, :cond_4

    const-string v5, ","

    .line 382
    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_4
    add-int/lit8 v4, v4, 0x1

    goto :goto_2

    :cond_5
    const-string v0, "]"

    .line 385
    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_4

    .line 375
    :cond_6
    :goto_3
    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    :goto_4
    const-string v0, ", Universal listeners: "

    .line 388
    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 389
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    monitor-enter v0

    .line 390
    :try_start_0
    iget-object v4, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v4

    if-nez v4, :cond_7

    .line 391
    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    goto :goto_6

    .line 393
    :cond_7
    iget-object v4, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, " ["

    .line 394
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 395
    :goto_5
    iget-object v4, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v4

    if-ge v1, v4, :cond_9

    .line 396
    iget-object v4, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v4, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;

    invoke-virtual {v4}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 397
    iget-object v4, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v4

    sub-int/2addr v4, v2

    if-ge v1, v4, :cond_8

    const-string v4, ","

    .line 398
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_8
    add-int/lit8 v1, v1, 0x1

    goto :goto_5

    :cond_9
    const-string v1, "], Message: "

    .line 401
    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 403
    :goto_6
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 404
    invoke-virtual {p1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string p1, "UiMessageUtils"

    .line 406
    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    :goto_7
    return-void

    :catchall_0
    move-exception p1

    .line 403
    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p1
.end method


# virtual methods
.method public addListener(ILcom/hcn/common/utils/HMessageUtils$HMessageCallback;)V
    .locals 3

    .line 239
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    monitor-enter v0

    .line 240
    :try_start_0
    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    invoke-virtual {v1, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/List;

    if-nez v1, :cond_0

    .line 242
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 243
    iget-object v2, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    invoke-virtual {v2, p1, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 245
    :cond_0
    invoke-interface {v1, p2}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_1

    .line 246
    invoke-interface {v1, p2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 248
    :cond_1
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1
.end method

.method public addListener(Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;)V
    .locals 4

    .line 257
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    monitor-enter v0

    .line 258
    :try_start_0
    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v1, p1}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 259
    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v1, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 261
    :cond_0
    sget-boolean v1, Lcom/hcn/common/utils/HMessageUtils;->DEBUG:Z

    if-eqz v1, :cond_1

    const-string v1, "UiMessageUtils"

    .line 262
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Listener is already added. "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 265
    :cond_1
    :goto_0
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1
.end method

.method public final clearMessageQueue()V
    .locals 2

    .line 227
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    return-void
.end method

.method public handleMessage(Landroid/os/Message;)Z
    .locals 3

    .line 326
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mMessage:Lcom/hcn/common/utils/HMessageUtils$UiMessage;

    invoke-static {v0, p1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->-$$Nest$msetMessage(Lcom/hcn/common/utils/HMessageUtils$UiMessage;Landroid/os/Message;)V

    .line 327
    sget-boolean v0, Lcom/hcn/common/utils/HMessageUtils;->DEBUG:Z

    if-eqz v0, :cond_0

    .line 328
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mMessage:Lcom/hcn/common/utils/HMessageUtils$UiMessage;

    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HMessageUtils;->logMessageHandling(Lcom/hcn/common/utils/HMessageUtils$UiMessage;)V

    .line 332
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    monitor-enter v0

    .line 333
    :try_start_0
    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    iget v2, p1, Landroid/os/Message;->what:I

    invoke-virtual {v1, v2}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/List;

    if-eqz v1, :cond_3

    .line 335
    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v2

    if-nez v2, :cond_1

    .line 336
    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    iget p1, p1, Landroid/os/Message;->what:I

    invoke-virtual {v1, p1}, Landroid/util/SparseArray;->remove(I)V

    goto :goto_1

    .line 338
    :cond_1
    iget-object p1, p0, Lcom/hcn/common/utils/HMessageUtils;->mDefensiveCopyList:Ljava/util/List;

    invoke-interface {p1, v1}, Ljava/util/List;->addAll(Ljava/util/Collection;)Z

    .line 339
    iget-object p1, p0, Lcom/hcn/common/utils/HMessageUtils;->mDefensiveCopyList:Ljava/util/List;

    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_0
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;

    .line 340
    iget-object v2, p0, Lcom/hcn/common/utils/HMessageUtils;->mMessage:Lcom/hcn/common/utils/HMessageUtils$UiMessage;

    invoke-interface {v1, v2}, Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;->handleMessage(Lcom/hcn/common/utils/HMessageUtils$UiMessage;)V

    goto :goto_0

    .line 342
    :cond_2
    iget-object p1, p0, Lcom/hcn/common/utils/HMessageUtils;->mDefensiveCopyList:Ljava/util/List;

    invoke-interface {p1}, Ljava/util/List;->clear()V

    .line 345
    :cond_3
    :goto_1
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 348
    iget-object p1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    monitor-enter p1

    .line 349
    :try_start_1
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-lez v0, :cond_5

    .line 350
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mDefensiveCopyList:Ljava/util/List;

    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v0, v1}, Ljava/util/List;->addAll(Ljava/util/Collection;)Z

    .line 351
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mDefensiveCopyList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_2
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_4

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;

    .line 352
    iget-object v2, p0, Lcom/hcn/common/utils/HMessageUtils;->mMessage:Lcom/hcn/common/utils/HMessageUtils$UiMessage;

    invoke-interface {v1, v2}, Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;->handleMessage(Lcom/hcn/common/utils/HMessageUtils$UiMessage;)V

    goto :goto_2

    .line 354
    :cond_4
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mDefensiveCopyList:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->clear()V

    .line 356
    :cond_5
    monitor-exit p1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 358
    iget-object p1, p0, Lcom/hcn/common/utils/HMessageUtils;->mMessage:Lcom/hcn/common/utils/HMessageUtils$UiMessage;

    const/4 v0, 0x0

    invoke-static {p1, v0}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->-$$Nest$msetMessage(Lcom/hcn/common/utils/HMessageUtils$UiMessage;Landroid/os/Message;)V

    const/4 p1, 0x1

    return p1

    :catchall_0
    move-exception v0

    .line 356
    :try_start_2
    monitor-exit p1
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    throw v0

    :catchall_1
    move-exception p1

    .line 345
    :try_start_3
    monitor-exit v0
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    throw p1
.end method

.method public name()Ljava/lang/String;
    .locals 1

    .line 65
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mName:Ljava/lang/String;

    return-object v0
.end method

.method public release()V
    .locals 2

    .line 109
    sget-object v0, Lcom/hcn/common/utils/HMessageUtils;->sPool:Ljava/util/concurrent/ConcurrentHashMap;

    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public final remove(I)V
    .locals 1

    .line 219
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->removeMessages(I)V

    return-void
.end method

.method public removeListener(ILcom/hcn/common/utils/HMessageUtils$HMessageCallback;)V
    .locals 4

    .line 306
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    monitor-enter v0

    .line 307
    :try_start_0
    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    invoke-virtual {v1, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/List;

    if-eqz v1, :cond_1

    .line 308
    invoke-interface {v1}, Ljava/util/List;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_1

    .line 309
    sget-boolean v2, Lcom/hcn/common/utils/HMessageUtils;->DEBUG:Z

    if-eqz v2, :cond_0

    .line 310
    invoke-interface {v1, p2}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    const-string v1, "UiMessageUtils"

    .line 311
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Trying to remove specific listener that is not registered. ID "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v2, ", "

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 312
    monitor-exit v0

    return-void

    .line 315
    :cond_0
    invoke-interface {v1, p2}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    goto :goto_0

    .line 317
    :cond_1
    sget-boolean v1, Lcom/hcn/common/utils/HMessageUtils;->DEBUG:Z

    if-eqz v1, :cond_2

    const-string v1, "UiMessageUtils"

    .line 318
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Trying to remove specific listener that is not registered. ID "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v2, ", "

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 321
    :cond_2
    :goto_0
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1
.end method

.method public removeListener(Lcom/hcn/common/utils/HMessageUtils$HMessageCallback;)V
    .locals 4

    .line 274
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    monitor-enter v0

    .line 275
    :try_start_0
    sget-boolean v1, Lcom/hcn/common/utils/HMessageUtils;->DEBUG:Z

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v1, p1}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    const-string v1, "UiMessageUtils"

    .line 276
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Trying to remove a listener that is not registered. "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 278
    :cond_0
    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersUniversal:Ljava/util/List;

    invoke-interface {v1, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    .line 279
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1
.end method

.method public removeListeners(I)V
    .locals 3

    .line 288
    sget-boolean v0, Lcom/hcn/common/utils/HMessageUtils;->DEBUG:Z

    if-eqz v0, :cond_1

    .line 289
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    invoke-virtual {v0, p1}, Landroid/util/SparseArray;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/List;

    if-eqz v0, :cond_0

    .line 290
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-nez v0, :cond_1

    :cond_0
    const-string v0, "UiMessageUtils"

    .line 291
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Trying to remove specific listeners that are not registered. ID "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 294
    :cond_1
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    monitor-enter v0

    .line 295
    :try_start_0
    iget-object v1, p0, Lcom/hcn/common/utils/HMessageUtils;->mListenersSpecific:Landroid/util/SparseArray;

    invoke-virtual {v1, p1}, Landroid/util/SparseArray;->delete(I)V

    .line 296
    monitor-exit v0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p1
.end method

.method public final send(I)V
    .locals 1

    .line 118
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public final send(IJ)V
    .locals 1

    .line 142
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1, p2, p3}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    return-void
.end method

.method public final send(ILjava/lang/Object;)V
    .locals 1

    .line 167
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1, p2}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public final send(ILjava/lang/Object;J)V
    .locals 1

    .line 193
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1, p2}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p1

    invoke-virtual {v0, p1, p3, p4}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    return-void
.end method

.method public final sendUnique(I)V
    .locals 1

    .line 131
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->removeMessages(I)V

    .line 132
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public final sendUnique(IJ)V
    .locals 1

    .line 156
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->removeMessages(I)V

    .line 157
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1, p2, p3}, Landroid/os/Handler;->sendEmptyMessageDelayed(IJ)Z

    return-void
.end method

.method public final sendUnique(ILjava/lang/Object;)V
    .locals 1

    .line 181
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->removeMessages(I)V

    .line 182
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1, p2}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public final sendUnique(ILjava/lang/Object;J)V
    .locals 1

    .line 208
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1}, Landroid/os/Handler;->removeMessages(I)V

    .line 209
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils;->mHandler:Landroid/os/Handler;

    invoke-virtual {v0, p1, p2}, Landroid/os/Handler;->obtainMessage(ILjava/lang/Object;)Landroid/os/Message;

    move-result-object p1

    invoke-virtual {v0, p1, p3, p4}, Landroid/os/Handler;->sendMessageDelayed(Landroid/os/Message;J)Z

    return-void
.end method
