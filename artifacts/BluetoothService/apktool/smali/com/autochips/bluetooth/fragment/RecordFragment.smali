.class public Lcom/autochips/bluetooth/fragment/RecordFragment;
.super Lcom/hcn/skin/support/app/SkinCompatFragment;
.source "RecordFragment.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;,
        Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;,
        Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;
    }
.end annotation


# static fields
.field private static final CALLLOG_PROJECTION:[Ljava/lang/String;

.field private static final ITEM_HISTORY_NAME:Ljava/lang/String; = "item_history_name"

.field private static final ITEM_HISTORY_NUMBER:Ljava/lang/String; = "item_history_number"

.field private static final ITEM_HISTORY_TIME:Ljava/lang/String; = "item_history_time"

.field private static final ITEM_HISTORY_TYPE:Ljava/lang/String; = "item_history_type"

.field private static final LOADER_HISTORY:I = 0x1

.field private static final MSG_DOWNLOAD_FAILED:I = 0x2

.field private static final MSG_DOWNLOAD_FINISH:I = 0x1

.field private static final MSG_DOWNLOAD_START:I = 0x0

.field private static final MSG_UI_HISTORY_ADD:I = 0x5

.field private static final MSG_UI_LOAD_FINISH:I = 0x4

.field private static final MSG_UI_LOAD_START:I = 0x3

.field public static final TAG:Ljava/lang/String; = "RecordFragment"


# instance fields
.field private historyListView:Landroid/widget/AbsListView;

.field private mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

.field private mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

.field private mCancellationSignal:Landroid/os/CancellationSignal;

.field private mContext:Landroid/content/Context;

.field private mDownloadLayout:Landroid/widget/LinearLayout;

.field private mDownloadTextView:Landroid/widget/TextView;

.field private mFocusId:I

.field private mHistoryList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation
.end field

.field private mHistoryListAdapter:Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

.field private mHistoryListClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

.field private mLoaderHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;

.field private mLoaderThread:Landroid/os/HandlerThread;

.field private mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

.field private mMissedCall:Landroid/widget/RadioButton;

.field private mReceiver:Landroid/content/BroadcastReceiver;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    const-string v0, "number"

    const-string v1, "date"

    const-string v2, "type"

    const-string v3, "name"

    .line 87
    filled-new-array {v0, v1, v2, v3}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/autochips/bluetooth/fragment/RecordFragment;->CALLLOG_PROJECTION:[Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    .line 59
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;-><init>()V

    const/4 v0, 0x0

    .line 71
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->historyListView:Landroid/widget/AbsListView;

    const v1, 0x7f080081

    .line 72
    iput v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mFocusId:I

    .line 83
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 355
    new-instance v0, Lcom/autochips/bluetooth/fragment/RecordFragment$1;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/RecordFragment$1;-><init>(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    .line 659
    new-instance v0, Lcom/autochips/bluetooth/fragment/RecordFragment$2;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/RecordFragment$2;-><init>(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryListClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 672
    new-instance v0, Lcom/autochips/bluetooth/fragment/RecordFragment$3;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/RecordFragment$3;-><init>(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    .line 688
    new-instance v0, Lcom/autochips/bluetooth/fragment/RecordFragment$4;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/RecordFragment$4;-><init>(Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

    return-void
.end method

.method static synthetic access$000(Lcom/autochips/bluetooth/fragment/RecordFragment;)V
    .locals 0

    .line 59
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->showDownloadGroup()V

    return-void
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/fragment/RecordFragment;)V
    .locals 0

    .line 59
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->hideDownloadGroup()V

    return-void
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/fragment/RecordFragment;)Ljava/util/ArrayList;
    .locals 0

    .line 59
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryList:Ljava/util/ArrayList;

    return-object p0
.end method

.method static synthetic access$300(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;
    .locals 0

    .line 59
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryListAdapter:Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    return-object p0
.end method

.method static synthetic access$400(Lcom/autochips/bluetooth/fragment/RecordFragment;I)V
    .locals 0

    .line 59
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->changeDownloadHint(I)V

    return-void
.end method

.method static synthetic access$502(Lcom/autochips/bluetooth/fragment/RecordFragment;I)I
    .locals 0

    .line 59
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mFocusId:I

    return p1
.end method

.method static synthetic access$600(Lcom/autochips/bluetooth/fragment/RecordFragment;)Landroid/widget/RadioButton;
    .locals 0

    .line 59
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMissedCall:Landroid/widget/RadioButton;

    return-object p0
.end method

.method static synthetic access$700(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 0

    .line 59
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p0
.end method

.method static synthetic access$800(Lcom/autochips/bluetooth/fragment/RecordFragment;)Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;
    .locals 0

    .line 59
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    return-object p0
.end method

.method private changeDownloadHint(I)V
    .locals 3

    .line 345
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->isAdded()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    const v0, 0x7f0d0085

    .line 348
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    aput-object p1, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    .line 349
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mDownloadTextView:Landroid/widget/TextView;

    if-eqz v0, :cond_1

    .line 350
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "changeDownloadHint:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "RecordFragment"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 351
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mDownloadTextView:Landroid/widget/TextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_1
    return-void
.end method

.method private hideDownloadGroup()V
    .locals 2

    .line 340
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mDownloadLayout:Landroid/widget/LinearLayout;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    return-void
.end method

.method private initView(Landroid/view/View;)V
    .locals 9

    if-nez p1, :cond_0

    const-string p1, "RecordFragment"

    const-string v0, "initView: layout is null"

    .line 220
    invoke-static {p1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    const v0, 0x7f080047

    .line 224
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f08007a

    .line 225
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f080091

    .line 226
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f080081

    .line 227
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RadioButton;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMissedCall:Landroid/widget/RadioButton;

    .line 228
    invoke-virtual {v0, p0}, Landroid/widget/RadioButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f0800b1

    .line 229
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mDownloadLayout:Landroid/widget/LinearLayout;

    const v0, 0x7f080169

    .line 230
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mDownloadTextView:Landroid/widget/TextView;

    const v0, 0x7f080110

    .line 232
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    const/4 v1, 0x0

    .line 233
    invoke-virtual {v0, v1}, Landroid/widget/ProgressBar;->setIndeterminate(Z)V

    const v0, 0x7f0800be

    .line 235
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/AbsListView;

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->historyListView:Landroid/widget/AbsListView;

    .line 236
    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryList:Ljava/util/ArrayList;

    .line 237
    new-instance p1, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mContext:Landroid/content/Context;

    iget-object v5, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryList:Ljava/util/ArrayList;

    const v0, 0x7f0b002b

    .line 238
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v6

    const-string v0, "item_history_name"

    const-string v2, "item_history_number"

    const-string v3, "item_history_time"

    const-string v7, "item_history_type"

    filled-new-array {v0, v2, v3, v7}, [Ljava/lang/String;

    move-result-object v7

    const/4 v0, 0x4

    new-array v8, v0, [I

    const v0, 0x7f0800d7

    .line 240
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    aput v0, v8, v1

    const/4 v0, 0x1

    const v1, 0x7f0800d8

    .line 241
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    aput v1, v8, v0

    const/4 v0, 0x2

    const v1, 0x7f0800d9

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    aput v1, v8, v0

    const/4 v0, 0x3

    const v1, 0x7f080061

    .line 242
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    aput v1, v8, v0

    move-object v2, p1

    move-object v3, p0

    invoke-direct/range {v2 .. v8}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;-><init>(Lcom/autochips/bluetooth/fragment/RecordFragment;Landroid/content/Context;Ljava/util/ArrayList;I[Ljava/lang/String;[I)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryListAdapter:Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    .line 243
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->historyListView:Landroid/widget/AbsListView;

    invoke-virtual {v0, p1}, Landroid/widget/AbsListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 244
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->historyListView:Landroid/widget/AbsListView;

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryListClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    invoke-virtual {p1, v0}, Landroid/widget/AbsListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    return-void
.end method

.method private showDownloadGroup()V
    .locals 2

    const/4 v0, 0x0

    .line 335
    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->changeDownloadHint(I)V

    .line 336
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mDownloadLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v1, v0}, Landroid/widget/LinearLayout;->setVisibility(I)V

    return-void
.end method


# virtual methods
.method public clearHistoryList()V
    .locals 1

    .line 421
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryList:Ljava/util/ArrayList;

    if-nez v0, :cond_0

    return-void

    .line 424
    :cond_0
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-lez v0, :cond_1

    .line 425
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 426
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryListAdapter:Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;->notifyDataSetChanged()V

    :cond_1
    return-void
.end method

.method public getLayoutRes()I
    .locals 1

    const v0, 0x7f0b001f

    return v0
.end method

.method public loadHistoryCallLog(I)V
    .locals 16

    move-object/from16 v1, p0

    move/from16 v0, p1

    .line 447
    invoke-virtual/range {p0 .. p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object v2

    const/4 v3, 0x4

    if-nez v2, :cond_0

    .line 448
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-static {v0, v3}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 449
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-virtual {v2, v0}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void

    .line 452
    :cond_0
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v2

    if-nez v2, :cond_1

    const-string v0, "RecordFragment"

    const-string v2, "loadHistoryCallLog: deviceInfo==null!"

    .line 454
    invoke-static {v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 455
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-static {v0, v3}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 456
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-virtual {v2, v0}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void

    .line 460
    :cond_1
    invoke-virtual/range {p0 .. p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object v4

    invoke-virtual {v4}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v5

    const-string v4, "RecordFragment"

    .line 462
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "loadHistoryCallLog: callType="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v4, 0x0

    .line 464
    :try_start_0
    monitor-enter p0
    :try_end_0
    .catch Landroid/os/OperationCanceledException; {:try_start_0 .. :try_end_0} :catch_0

    .line 465
    :try_start_1
    iget-object v6, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    if-eqz v6, :cond_2

    .line 466
    invoke-virtual {v6}, Landroid/os/CancellationSignal;->cancel()V

    .line 467
    iput-object v4, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 469
    :cond_2
    new-instance v6, Landroid/os/CancellationSignal;

    invoke-direct {v6}, Landroid/os/CancellationSignal;-><init>()V

    iput-object v6, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 470
    monitor-exit p0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 471
    :try_start_2
    new-instance v6, Landroid/accounts/Account;

    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v7

    const-string v8, "com.android.bluetooth.pbapsink"

    invoke-direct {v6, v7, v8}, Landroid/accounts/Account;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    const-string v7, "RecordFragment"

    .line 473
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "loadHistoryCallLog: name = "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    iget-object v9, v6, Landroid/accounts/Account;->name:Ljava/lang/String;

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, " hashCode = "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v6}, Landroid/accounts/Account;->hashCode()I

    move-result v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v7, 0x2

    const/4 v12, 0x3

    const/4 v13, 0x1

    const/4 v14, 0x0

    if-nez v0, :cond_3

    .line 475
    sget-object v0, Landroid/provider/CallLog$Calls;->CONTENT_URI:Landroid/net/Uri;

    sget-object v8, Lcom/autochips/bluetooth/fragment/RecordFragment;->CALLLOG_PROJECTION:[Ljava/lang/String;

    const-string v9, "subscription_id=? or subscription_id=?"

    new-array v10, v7, [Ljava/lang/String;

    .line 477
    invoke-virtual {v6}, Landroid/accounts/Account;->hashCode()I

    move-result v7

    invoke-static {v7}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v7

    aput-object v7, v10, v14

    iget-object v6, v6, Landroid/accounts/Account;->name:Ljava/lang/String;

    aput-object v6, v10, v13

    const-string v11, "date desc"

    iget-object v15, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    move-object v6, v0

    move-object v7, v8

    move-object v8, v9

    move-object v9, v10

    move-object v10, v11

    move-object v11, v15

    .line 475
    invoke-virtual/range {v5 .. v11}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/database/Cursor;

    move-result-object v0

    goto :goto_0

    .line 480
    :cond_3
    sget-object v8, Landroid/provider/CallLog$Calls;->CONTENT_URI:Landroid/net/Uri;

    sget-object v9, Lcom/autochips/bluetooth/fragment/RecordFragment;->CALLLOG_PROJECTION:[Ljava/lang/String;

    const-string v10, "type=? AND (subscription_id=? or subscription_id=?)"

    new-array v11, v12, [Ljava/lang/String;

    .line 483
    invoke-static/range {p1 .. p1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v0

    aput-object v0, v11, v14

    invoke-virtual {v6}, Landroid/accounts/Account;->hashCode()I

    move-result v0

    invoke-static {v0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v0

    aput-object v0, v11, v13

    iget-object v0, v6, Landroid/accounts/Account;->name:Ljava/lang/String;

    aput-object v0, v11, v7

    const-string v0, "date desc"

    iget-object v15, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    move-object v6, v8

    move-object v7, v9

    move-object v8, v10

    move-object v9, v11

    move-object v10, v0

    move-object v11, v15

    .line 480
    invoke-virtual/range {v5 .. v11}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/database/Cursor;

    move-result-object v0
    :try_end_2
    .catch Landroid/os/OperationCanceledException; {:try_start_2 .. :try_end_2} :catch_0

    .line 497
    :goto_0
    new-instance v5, Ljava/util/ArrayList;

    invoke-direct {v5}, Ljava/util/ArrayList;-><init>()V

    .line 498
    iget-object v6, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    const/4 v7, 0x5

    invoke-virtual {v6, v7}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->removeMessages(I)V

    .line 499
    iget-object v6, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-virtual {v6, v12}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->sendEmptyMessage(I)Z

    const-string v6, "RecordFragment"

    const-string v8, "loadHistoryCallLog: send MSG_UI_LOAD_START"

    .line 500
    invoke-static {v6, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 501
    :goto_1
    invoke-interface {v0}, Landroid/database/Cursor;->moveToNext()Z

    move-result v6

    if-eqz v6, :cond_9

    iget-object v6, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;

    invoke-virtual {v6, v13}, Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;->hasMessages(I)Z

    move-result v6

    if-nez v6, :cond_9

    iget-object v6, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 502
    invoke-virtual {v6}, Landroid/os/CancellationSignal;->isCanceled()Z

    move-result v6

    if-nez v6, :cond_9

    const-string v6, "date"

    .line 504
    invoke-interface {v0, v6}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v6

    .line 505
    invoke-interface {v0, v6}, Landroid/database/Cursor;->getLong(I)J

    move-result-wide v8

    const-string v6, "number"

    .line 506
    invoke-interface {v0, v6}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v6

    .line 507
    invoke-interface {v0, v6}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v6

    const-string v10, "type"

    .line 508
    invoke-interface {v0, v10}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v10

    .line 509
    invoke-interface {v0, v10}, Landroid/database/Cursor;->getInt(I)I

    move-result v10

    const-string v11, ""

    .line 512
    invoke-virtual {v6}, Ljava/lang/String;->length()I

    move-result v12

    const/16 v15, 0xf

    const/16 v13, 0xa

    if-le v12, v15, :cond_4

    const/16 v11, 0xc

    .line 513
    invoke-virtual {v6, v11}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v12

    .line 514
    invoke-virtual {v6, v14, v11}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v6

    move-object v11, v12

    goto :goto_2

    .line 515
    :cond_4
    invoke-virtual {v6}, Ljava/lang/String;->length()I

    move-result v12

    const/16 v15, 0xe

    if-le v12, v15, :cond_5

    .line 516
    invoke-virtual {v6, v13}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v11

    .line 517
    invoke-virtual {v6, v14, v13}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v6

    :cond_5
    :goto_2
    const-string v12, ""

    .line 520
    invoke-virtual {v12, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-nez v12, :cond_6

    .line 521
    new-instance v12, Ljava/lang/StringBuffer;

    invoke-direct {v12}, Ljava/lang/StringBuffer;-><init>()V

    .line 522
    invoke-virtual {v12, v6}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object v6

    const-string v12, ","

    .line 523
    invoke-virtual {v6, v12}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object v6

    .line 524
    invoke-virtual {v6, v11}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;

    move-result-object v6

    .line 525
    invoke-virtual {v6}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v6

    .line 528
    :cond_6
    invoke-virtual/range {p0 .. p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object v11

    .line 529
    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v12

    .line 528
    invoke-static {v11, v6, v12}, Lcom/hcn/bluetooth/api/Utils;->getContactNameByNumber(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v11

    .line 530
    invoke-static {v11}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v12

    if-eqz v12, :cond_7

    const-string v11, ""

    .line 534
    :cond_7
    new-instance v12, Ljava/util/HashMap;

    invoke-direct {v12}, Ljava/util/HashMap;-><init>()V

    const-string v15, "item_history_name"

    .line 535
    invoke-virtual {v12, v15, v11}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v11, "item_history_number"

    .line 536
    invoke-virtual {v12, v11, v6}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v6, "item_history_time"

    .line 537
    invoke-static {v8, v9}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v8

    invoke-virtual {v12, v6, v8}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v6, "item_history_type"

    .line 538
    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    invoke-virtual {v12, v6, v8}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 540
    invoke-virtual {v5, v12}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 542
    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v6

    if-lt v6, v13, :cond_8

    .line 544
    iget-object v6, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-static {v6, v7}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v6

    .line 545
    iput-object v5, v6, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 546
    iget-object v5, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-virtual {v5, v6}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    .line 547
    new-instance v5, Ljava/util/ArrayList;

    invoke-direct {v5}, Ljava/util/ArrayList;-><init>()V

    :cond_8
    const/4 v13, 0x1

    goto/16 :goto_1

    .line 550
    :cond_9
    invoke-interface {v0}, Landroid/database/Cursor;->close()V

    .line 551
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    invoke-virtual {v0}, Landroid/os/CancellationSignal;->isCanceled()Z

    move-result v0

    if-eqz v0, :cond_a

    .line 552
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-virtual {v0, v7}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->removeMessages(I)V

    const-string v0, "RecordFragment"

    const-string v2, "loadHistoryCallLog: finish cancel!!"

    .line 553
    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_3

    .line 555
    :cond_a
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-static {v0, v3}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 556
    iput-object v5, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 557
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-virtual {v2, v0}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    const-string v0, "RecordFragment"

    const-string v2, "loadHistoryCallLog: send MSG_UI_LOAD_FINISH"

    .line 558
    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 560
    :goto_3
    monitor-enter p0

    .line 561
    :try_start_3
    iput-object v4, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 562
    monitor-exit p0

    return-void

    :catchall_0
    move-exception v0

    monitor-exit p0
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    throw v0

    :catchall_1
    move-exception v0

    .line 470
    :try_start_4
    monitor-exit p0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    :try_start_5
    throw v0
    :try_end_5
    .catch Landroid/os/OperationCanceledException; {:try_start_5 .. :try_end_5} :catch_0

    .line 491
    :catch_0
    monitor-enter p0

    .line 492
    :try_start_6
    iput-object v4, v1, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 493
    monitor-exit p0
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_2

    const-string v0, "RecordFragment"

    const-string v2, "loadHistoryCallLog: OperationCanceledException!"

    .line 494
    invoke-static {v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :catchall_2
    move-exception v0

    .line 493
    :try_start_7
    monitor-exit p0
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_2

    throw v0
.end method

.method public onAttach(Landroid/content/Context;)V
    .locals 0

    .line 178
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onAttach(Landroid/content/Context;)V

    .line 179
    new-instance p1, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    invoke-direct {p1, p0, p0}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;-><init>(Lcom/autochips/bluetooth/fragment/RecordFragment;Lcom/autochips/bluetooth/fragment/RecordFragment;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    return-void
.end method

.method public onBindViewData()V
    .locals 0

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 1

    .line 386
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result p1

    const-string v0, "RecordFragment"

    sparse-switch p1, :sswitch_data_0

    goto :goto_0

    :sswitch_0
    const p1, 0x7f080091

    .line 395
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mFocusId:I

    .line 396
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->clearHistoryList()V

    const/4 p1, 0x1

    .line 397
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->startLoadHistoryCallLog(I)V

    const-string p1, "show download btn_received"

    .line 398
    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :sswitch_1
    const p1, 0x7f080081

    .line 401
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mFocusId:I

    .line 402
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->clearHistoryList()V

    const/4 p1, 0x3

    .line 403
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->startLoadHistoryCallLog(I)V

    const-string p1, "show download btn_missed"

    .line 404
    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    :sswitch_2
    const p1, 0x7f08007a

    .line 389
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mFocusId:I

    .line 390
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->clearHistoryList()V

    const/4 p1, 0x2

    .line 391
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->startLoadHistoryCallLog(I)V

    const-string p1, "show download btn_dialed"

    .line 392
    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 407
    :sswitch_3
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->stopLoadHistoryCallLog()V

    .line 408
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    const/4 v0, 0x6

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->pbapStartDownLoad(I)Z

    move-result p1

    if-nez p1, :cond_0

    .line 410
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object p1

    const v0, 0x7f0d0089

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    goto :goto_0

    :cond_0
    const p1, 0x7f080047

    .line 412
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mFocusId:I

    :goto_0
    return-void

    nop

    :sswitch_data_0
    .sparse-switch
        0x7f080047 -> :sswitch_3
        0x7f08007a -> :sswitch_2
        0x7f080081 -> :sswitch_1
        0x7f080091 -> :sswitch_0
    .end sparse-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1

    .line 184
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreate(Landroid/os/Bundle;)V

    const-string p1, "RecordFragment"

    const-string v0, "RecordFragment onCreate"

    .line 185
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 186
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->getAdapterManager()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 187
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    .line 188
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->registerPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V

    .line 190
    new-instance p1, Landroid/os/HandlerThread;

    const-string v0, "work_thread"

    invoke-direct {p1, v0}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mLoaderThread:Landroid/os/HandlerThread;

    .line 191
    invoke-virtual {p1}, Landroid/os/HandlerThread;->start()V

    .line 192
    new-instance p1, Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mLoaderThread:Landroid/os/HandlerThread;

    invoke-virtual {v0}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {p1, p0, v0}, Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;-><init>(Lcom/autochips/bluetooth/fragment/RecordFragment;Landroid/os/Looper;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 0

    .line 199
    invoke-super {p0, p1, p2, p3}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;

    move-result-object p1

    .line 201
    invoke-static {}, Lcom/autochips/bluetooth/skin/SkinUtils;->getContext()Landroid/content/Context;

    move-result-object p2

    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mContext:Landroid/content/Context;

    .line 203
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->initView(Landroid/view/View;)V

    const-string p2, "RecordFragment"

    const-string p3, "onCreateView"

    .line 204
    invoke-static {p2, p3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-object p1
.end method

.method public onDestroy()V
    .locals 2

    .line 316
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroy()V

    .line 317
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_0

    .line 318
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->unregisterPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V

    .line 319
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    .line 321
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mLoaderThread:Landroid/os/HandlerThread;

    if-eqz v0, :cond_1

    .line 322
    invoke-virtual {v0}, Landroid/os/HandlerThread;->quitSafely()Z

    :cond_1
    return-void
.end method

.method public onDestroyView()V
    .locals 0

    .line 311
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroyView()V

    return-void
.end method

.method public onDetach()V
    .locals 2

    .line 328
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDetach()V

    .line 329
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 330
    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/RecordFragment$MainHandler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    :cond_0
    return-void
.end method

.method public onHiddenChanged(Z)V
    .locals 2

    .line 380
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onHiddenChanged(Z)V

    .line 381
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onHiddenChanged hidden="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "RecordFragment"

    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onPause()V
    .locals 2

    const-string v0, "RecordFragment"

    const-string v1, "RecordFragment onPause"

    .line 296
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 297
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onPause()V

    return-void
.end method

.method public onResume()V
    .locals 2

    .line 289
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onResume()V

    const-string v0, "RecordFragment"

    const-string v1, "RecordFragment onResume"

    .line 290
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onStart()V
    .locals 3

    .line 277
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStart()V

    const-string v0, "RecordFragment"

    const-string v1, "RecordFragment onStart"

    .line 278
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 279
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    const-string v1, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    .line 280
    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 281
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    if-eqz v1, :cond_0

    .line 282
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    :cond_0
    return-void
.end method

.method public onStop()V
    .locals 2

    const-string v0, "RecordFragment"

    const-string v1, "RecordFragment onStop"

    .line 302
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 303
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStop()V

    .line 304
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 305
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    :cond_0
    return-void
.end method

.method public setUserVisibleHint(Z)V
    .locals 2

    .line 249
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setUserVisibleHint:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "RecordFragment"

    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 250
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->setUserVisibleHint(Z)V

    if-eqz p1, :cond_3

    .line 252
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isReady()Z

    move-result p1

    if-eqz p1, :cond_3

    .line 253
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    if-eqz p1, :cond_2

    .line 255
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    const/4 v0, 0x6

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getPbapDownLoadState(I)Z

    move-result p1

    if-eqz p1, :cond_0

    .line 257
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->showDownloadGroup()V

    goto :goto_0

    .line 259
    :cond_0
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->hideDownloadGroup()V

    .line 260
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mHistoryList:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/util/ArrayList;->isEmpty()Z

    move-result p1

    if-eqz p1, :cond_3

    const p1, 0x7f080081

    .line 261
    iput p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mFocusId:I

    .line 262
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mMissedCall:Landroid/widget/RadioButton;

    if-eqz p1, :cond_1

    const/4 v0, 0x1

    .line 263
    invoke-virtual {p1, v0}, Landroid/widget/RadioButton;->setChecked(Z)V

    :cond_1
    const/4 p1, 0x3

    .line 265
    invoke-virtual {p0, p1}, Lcom/autochips/bluetooth/fragment/RecordFragment;->startLoadHistoryCallLog(I)V

    goto :goto_0

    .line 269
    :cond_2
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->clearHistoryList()V

    :cond_3
    :goto_0
    return-void
.end method

.method public startLoadHistoryCallLog(I)V
    .locals 2

    .line 440
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 441
    iput p1, v0, Landroid/os/Message;->arg1:I

    .line 442
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/RecordFragment;->stopLoadHistoryCallLog()V

    .line 443
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method public stopLoadHistoryCallLog()V
    .locals 3

    const-string v0, "RecordFragment"

    const-string v1, "stopLoadHistoryCallLog"

    .line 431
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 432
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Lcom/autochips/bluetooth/fragment/RecordFragment$LoaderHandler;->removeMessages(I)V

    .line 433
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    if-eqz v1, :cond_0

    const-string v1, "stopLoadHistoryCallLog mCancellationSignal"

    .line 434
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 435
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/RecordFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    invoke-virtual {v0}, Landroid/os/CancellationSignal;->cancel()V

    :cond_0
    return-void
.end method
