.class public Lcom/autochips/bluetooth/fragment/DialFragment;
.super Lcom/hcn/skin/support/app/SkinCompatFragment;
.source "DialFragment.java"

# interfaces
.implements Landroid/view/View$OnClickListener;
.implements Landroid/view/View$OnLongClickListener;
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;,
        Lcom/autochips/bluetooth/fragment/DialFragment$ViewHolder;,
        Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;,
        Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;
    }
.end annotation


# static fields
.field public static final ACTION_CALL_CHANGED:Ljava/lang/String; = "android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED"

.field public static final EXTRA_CALL:Ljava/lang/String; = "android.bluetooth.headsetclient.extra.CALL"

.field private static final ITEM_PHONEBOOK_NAME:Ljava/lang/String; = "item_phonebook_name"

.field private static final ITEM_PHONEBOOK_NUMBER:Ljava/lang/String; = "item_phonebook_number"

.field private static final ITEM_PHONEBOOK_PATH:Ljava/lang/String; = "item_phonebook_path"

.field private static final LOAD_CONTACT_ON_CONNECTED_DELAY:I = 0xc8

.field public static final MSG_CLEAR_PBLIST:I = 0x1

.field public static final MSG_LOAD_CONTACT:I = 0x3

.field public static final MSG_PB_SEARCH:I = 0x4

.field public static final MSG_UI_PB_SEARCH_ADD:I = 0x2

.field private static SEARCH_SELECTION:Ljava/lang/String; = null

.field private static final TAG:Ljava/lang/String; = "DialFragment"


# instance fields
.field callNumEditText:Landroid/widget/EditText;

.field private mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

.field private mContactLoaderHandle:Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;

.field private mHandler:Landroid/os/Handler;

.field private mListViews:Landroid/widget/ListView;

.field private mPBList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;>;"
        }
    .end annotation
.end field

.field private mPBListAdapter:Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;

.field private mParentContext:Landroid/content/Context;

.field private mReceiver:Landroid/content/BroadcastReceiver;

.field private mSearchCancellationSignal:Landroid/os/CancellationSignal;

.field private mSearchTextWatcher:Landroid/text/TextWatcher;

.field private mToast:Landroid/widget/Toast;

.field private mViewRoot:Landroid/view/View;

.field private mcontactLoaderThread:Landroid/os/HandlerThread;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    .line 79
    sget-object v0, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const/4 v1, 0x3

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "data1"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    const-string v3, "account_name"

    aput-object v3, v1, v2

    const/4 v2, 0x2

    const-string v3, "account_type"

    aput-object v3, v1, v2

    const-string v2, "%s LIKE ? AND %s=? AND %s =?"

    invoke-static {v0, v2, v1}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/autochips/bluetooth/fragment/DialFragment;->SEARCH_SELECTION:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 58
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;-><init>()V

    const/4 v0, 0x0

    .line 73
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mToast:Landroid/widget/Toast;

    .line 86
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mSearchCancellationSignal:Landroid/os/CancellationSignal;

    .line 106
    new-instance v0, Lcom/autochips/bluetooth/fragment/DialFragment$1;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/DialFragment$1;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    .line 462
    new-instance v0, Lcom/autochips/bluetooth/fragment/DialFragment$2;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/DialFragment$2;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mSearchTextWatcher:Landroid/text/TextWatcher;

    .line 496
    new-instance v0, Lcom/autochips/bluetooth/fragment/DialFragment$3;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/DialFragment$3;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mHandler:Landroid/os/Handler;

    return-void
.end method

.method static synthetic access$000(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;
    .locals 0

    .line 58
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mContactLoaderHandle:Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;

    return-object p0
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/fragment/DialFragment;)V
    .locals 0

    .line 58
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->clearPBList()V

    return-void
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/fragment/DialFragment;)V
    .locals 0

    .line 58
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->deleteAllDialPadString()V

    return-void
.end method

.method static synthetic access$300(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/CancellationSignal;
    .locals 0

    .line 58
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mSearchCancellationSignal:Landroid/os/CancellationSignal;

    return-object p0
.end method

.method static synthetic access$302(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/os/CancellationSignal;)Landroid/os/CancellationSignal;
    .locals 0

    .line 58
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mSearchCancellationSignal:Landroid/os/CancellationSignal;

    return-object p1
.end method

.method static synthetic access$400(Lcom/autochips/bluetooth/fragment/DialFragment;)Ljava/util/ArrayList;
    .locals 0

    .line 58
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mPBList:Ljava/util/ArrayList;

    return-object p0
.end method

.method static synthetic access$500(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;
    .locals 0

    .line 58
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;

    return-object p0
.end method

.method static synthetic access$600(Lcom/autochips/bluetooth/fragment/DialFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 0

    .line 58
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p0
.end method

.method static synthetic access$700(Lcom/autochips/bluetooth/fragment/DialFragment;)Landroid/os/Handler;
    .locals 0

    .line 58
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mHandler:Landroid/os/Handler;

    return-object p0
.end method

.method static synthetic access$800()Ljava/lang/String;
    .locals 1

    .line 58
    sget-object v0, Lcom/autochips/bluetooth/fragment/DialFragment;->SEARCH_SELECTION:Ljava/lang/String;

    return-object v0
.end method

.method private addDialPadInputString(Ljava/lang/CharSequence;)V
    .locals 4

    if-nez p1, :cond_0

    return-void

    .line 404
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    if-nez v0, :cond_1

    return-void

    .line 407
    :cond_1
    invoke-virtual {v0}, Landroid/widget/EditText;->getSelectionStart()I

    move-result v0

    .line 408
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    .line 410
    invoke-interface {v1}, Landroid/text/Editable;->length()I

    move-result v2

    const/16 v3, 0xe

    if-le v2, v3, :cond_2

    const p1, 0x7f0d007e

    .line 411
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->showToast(Ljava/lang/String;)V

    return-void

    :cond_2
    if-ltz v0, :cond_4

    .line 414
    invoke-interface {v1}, Landroid/text/Editable;->length()I

    move-result v2

    if-le v0, v2, :cond_3

    goto :goto_0

    .line 417
    :cond_3
    invoke-interface {v1, v0, p1}, Landroid/text/Editable;->insert(ILjava/lang/CharSequence;)Landroid/text/Editable;

    goto :goto_1

    .line 415
    :cond_4
    :goto_0
    invoke-interface {v1, p1}, Landroid/text/Editable;->append(Ljava/lang/CharSequence;)Landroid/text/Editable;

    .line 419
    :goto_1
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    invoke-virtual {v2, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 420
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    invoke-interface {p1}, Ljava/lang/CharSequence;->length()I

    move-result p1

    add-int/2addr v0, p1

    invoke-virtual {v1, v0}, Landroid/widget/EditText;->setSelection(I)V

    return-void
.end method

.method private call(Ljava/lang/String;)V
    .locals 1

    .line 364
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothConnected()Z

    move-result v0

    if-nez v0, :cond_0

    const-string p1, "DialFragment"

    const-string v0, "device is not connected!"

    .line 365
    invoke-static {p1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 369
    :cond_0
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->checkCallNumberVaild()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 370
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object v0

    invoke-virtual {v0}, Lcom/autochips/bluetooth/MyApplication;->getHfpclientManager()Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->dial(Ljava/lang/String;)V

    goto :goto_0

    :cond_1
    const p1, 0x7f0d007d

    .line 372
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->showToast(Ljava/lang/String;)V

    :goto_0
    return-void
.end method

.method private checkCallNumberVaild()Z
    .locals 1

    .line 388
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    if-eqz v0, :cond_0

    .line 389
    invoke-virtual {v0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v0

    .line 391
    invoke-interface {v0}, Landroid/text/Editable;->length()I

    move-result v0

    if-lez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private clearPBList()V
    .locals 1

    .line 449
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mPBList:Ljava/util/ArrayList;

    if-eqz v0, :cond_0

    .line 450
    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 452
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;

    if-eqz v0, :cond_1

    .line 453
    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;->notifyDataSetChanged()V

    :cond_1
    return-void
.end method

.method private deleteAllDialPadString()V
    .locals 2

    .line 441
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    if-nez v0, :cond_0

    return-void

    :cond_0
    const-string v1, ""

    .line 445
    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    return-void
.end method

.method private deleteOneDialPadString()V
    .locals 3

    .line 424
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    if-nez v0, :cond_0

    return-void

    .line 428
    :cond_0
    invoke-virtual {v0}, Landroid/widget/EditText;->getSelectionStart()I

    move-result v0

    .line 429
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    const/4 v2, 0x1

    if-lt v0, v2, :cond_1

    add-int/lit8 v2, v0, -0x1

    .line 432
    invoke-interface {v1, v2, v0}, Landroid/text/Editable;->delete(II)Landroid/text/Editable;

    .line 436
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 437
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    invoke-virtual {v0, v2}, Landroid/widget/EditText;->setSelection(I)V

    :cond_1
    return-void
.end method

.method private getCallNumber()Ljava/lang/String;
    .locals 1

    .line 378
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    if-eqz v0, :cond_0

    .line 379
    invoke-virtual {v0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :cond_0
    const-string v0, ""

    :goto_0
    return-object v0
.end method

.method private getLastDialNumber()Ljava/lang/String;
    .locals 1

    .line 701
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object v0

    invoke-virtual {v0}, Lcom/autochips/bluetooth/MyApplication;->getHfpclientManager()Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 703
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->getLastCall()Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :cond_0
    const-string v0, ""

    :goto_0
    return-object v0
.end method

.method private initView()V
    .locals 3

    .line 316
    :try_start_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f0800ed

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ListView;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mListViews:Landroid/widget/ListView;

    .line 317
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mPBList:Ljava/util/ArrayList;

    .line 318
    new-instance v0, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mParentContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mPBList:Ljava/util/ArrayList;

    invoke-direct {v0, p0, v1, v2}, Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/content/Context;Ljava/util/ArrayList;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/DialFragment$ListAdapter;

    .line 319
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mListViews:Landroid/widget/ListView;

    invoke-virtual {v1, v0}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 320
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mListViews:Landroid/widget/ListView;

    invoke-virtual {v0, p0}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 322
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08005c

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 324
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080069

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 325
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnLongClickListener(Landroid/view/View$OnLongClickListener;)V

    .line 326
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080063

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 327
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080068

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 328
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080067

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 329
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080060

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 330
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08005f

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 331
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080066

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 332
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080065

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 333
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08005e

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 334
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080062

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 335
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08005d

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 336
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080064

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 337
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080079

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 338
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnLongClickListener(Landroid/view/View$OnLongClickListener;)V
    :try_end_0
    .catch Ljava/lang/NullPointerException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 341
    invoke-virtual {v0}, Ljava/lang/NullPointerException;->printStackTrace()V

    .line 344
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08015b

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    .line 345
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mSearchTextWatcher:Landroid/text/TextWatcher;

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->addTextChangedListener(Landroid/text/TextWatcher;)V

    .line 346
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 347
    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setInputType(I)V

    .line 348
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    const-string v2, ""

    invoke-virtual {v0, v2}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 349
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->callNumEditText:Landroid/widget/EditText;

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setFocusable(Z)V

    :cond_0
    return-void
.end method

.method private onCallClick()V
    .locals 1

    .line 355
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->getCallNumber()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    .line 356
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->getLastDialNumber()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 358
    :cond_0
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->getCallNumber()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/autochips/bluetooth/fragment/DialFragment;->call(Ljava/lang/String;)V

    :goto_0
    return-void
.end method

.method private showToast(Ljava/lang/String;)V
    .locals 2

    .line 709
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mToast:Landroid/widget/Toast;

    if-eqz v0, :cond_0

    .line 710
    invoke-virtual {v0}, Landroid/widget/Toast;->cancel()V

    .line 712
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mParentContext:Landroid/content/Context;

    const/4 v1, 0x0

    invoke-static {v0, p1, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mToast:Landroid/widget/Toast;

    .line 713
    invoke-virtual {p1}, Landroid/widget/Toast;->show()V

    return-void
.end method


# virtual methods
.method public getLayoutRes()I
    .locals 1

    const v0, 0x7f0b001e

    return v0
.end method

.method public onAttach(Landroid/content/Context;)V
    .locals 0

    .line 97
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onAttach(Landroid/content/Context;)V

    return-void
.end method

.method public onBindViewData()V
    .locals 0

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 1

    .line 251
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result p1

    const v0, 0x7f080079

    if-eq p1, v0, :cond_0

    packed-switch p1, :pswitch_data_0

    packed-switch p1, :pswitch_data_1

    goto :goto_0

    :pswitch_0
    const-string p1, "0"

    .line 258
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_1
    const-string p1, "2"

    .line 266
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_2
    const-string p1, "3"

    .line 270
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_3
    const-string p1, "6"

    .line 282
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_4
    const-string p1, "7"

    .line 286
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_5
    const-string p1, "#"

    .line 302
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_6
    const-string p1, "1"

    .line 262
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_7
    const-string p1, "9"

    .line 294
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_8
    const-string p1, "4"

    .line 274
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_9
    const-string p1, "5"

    .line 278
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_a
    const-string p1, "8"

    .line 290
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_b
    const-string p1, "*"

    .line 298
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 254
    :pswitch_c
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->onCallClick()V

    goto :goto_0

    .line 306
    :cond_0
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->deleteOneDialPadString()V

    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x7f08005c
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x7f080062
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 0

    .line 102
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreate(Landroid/os/Bundle;)V

    .line 103
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->getAdapterManager()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 0

    .line 139
    invoke-super {p0, p1, p2, p3}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    .line 140
    new-instance p1, Landroid/os/HandlerThread;

    const-string p2, "contactloader_thread"

    invoke-direct {p1, p2}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mcontactLoaderThread:Landroid/os/HandlerThread;

    .line 141
    invoke-virtual {p1}, Landroid/os/HandlerThread;->start()V

    .line 142
    new-instance p1, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;

    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mcontactLoaderThread:Landroid/os/HandlerThread;

    invoke-virtual {p2}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object p2

    invoke-direct {p1, p0, p2}, Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;Landroid/os/Looper;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mContactLoaderHandle:Lcom/autochips/bluetooth/fragment/DialFragment$contactLoaderHandle;

    .line 144
    invoke-static {}, Lcom/autochips/bluetooth/skin/SkinUtils;->getContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mParentContext:Landroid/content/Context;

    .line 146
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->initView()V

    .line 147
    new-instance p1, Landroid/content/IntentFilter;

    invoke-direct {p1}, Landroid/content/IntentFilter;-><init>()V

    const-string p2, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    .line 148
    invoke-virtual {p1, p2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string p2, "android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED"

    .line 149
    invoke-virtual {p1, p2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 150
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->getActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object p2

    iget-object p3, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p2, p3, p1}, Landroidx/fragment/app/FragmentActivity;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 151
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mViewRoot:Landroid/view/View;

    return-object p1
.end method

.method public onDestroy()V
    .locals 2

    .line 232
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroy()V

    const-string v0, "DialFragment"

    const-string v1, "onDestroy"

    .line 233
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onDestroyView()V
    .locals 2

    .line 176
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroyView()V

    .line 177
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->getActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroidx/fragment/app/FragmentActivity;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    return-void
.end method

.method public onDetach()V
    .locals 0

    .line 182
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDetach()V

    return-void
.end method

.method public onHiddenChanged(Z)V
    .locals 0

    .line 92
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onHiddenChanged(Z)V

    return-void
.end method

.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView<",
            "*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    .line 695
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {p1, p3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/util/HashMap;

    const-string p2, "item_phonebook_number"

    invoke-virtual {p1, p2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->call(Ljava/lang/String;)V

    return-void
.end method

.method public onKeyDown(ILandroid/view/KeyEvent;)Z
    .locals 1

    .line 186
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onKeyDown:keyCode="

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    const-string v0, "DialFragment"

    invoke-static {v0, p2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    :pswitch_0
    const-string p1, "9"

    .line 216
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_1
    const-string p1, "8"

    .line 213
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_2
    const-string p1, "7"

    .line 210
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_3
    const-string p1, "6"

    .line 207
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_4
    const-string p1, "5"

    .line 204
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_5
    const-string p1, "4"

    .line 201
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_6
    const-string p1, "3"

    .line 198
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_7
    const-string p1, "2"

    .line 195
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_8
    const-string p1, "1"

    .line 192
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    goto :goto_0

    :pswitch_9
    const-string p1, "0"

    .line 189
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    :goto_0
    const/4 p1, 0x1

    return p1

    nop

    :pswitch_data_0
    .packed-switch 0x7
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public onKeyUp(ILandroid/view/KeyEvent;)Z
    .locals 1

    .line 226
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onKeyUp:keyCode="

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string p2, "DialFragment"

    invoke-static {p2, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p1, 0x1

    return p1
.end method

.method public onLongClick(Landroid/view/View;)Z
    .locals 2

    .line 238
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result p1

    const/4 v0, 0x1

    const v1, 0x7f080079

    if-ne p1, v1, :cond_0

    .line 240
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/DialFragment;->deleteAllDialPadString()V

    return v0

    :cond_0
    const v1, 0x7f080069

    if-ne p1, v1, :cond_1

    const-string p1, "+"

    .line 243
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment;->addDialPadInputString(Ljava/lang/CharSequence;)V

    return v0

    :cond_1
    const/4 p1, 0x0

    return p1
.end method

.method public onStart()V
    .locals 0

    .line 171
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStart()V

    return-void
.end method

.method public onViewCreated(Landroid/view/View;Landroid/os/Bundle;)V
    .locals 0

    .line 166
    invoke-super {p0, p1, p2}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onViewCreated(Landroid/view/View;Landroid/os/Bundle;)V

    return-void
.end method
