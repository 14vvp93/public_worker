.class public Lcom/autochips/bluetooth/fragment/PBFragment;
.super Lcom/hcn/skin/support/app/SkinCompatFragment;
.source "PBFragment.java"

# interfaces
.implements Landroid/view/View$OnClickListener;
.implements Lcom/autochips/bluetooth/view/SideBar$OnTouchingLetterChangedListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;,
        Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;,
        Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;
    }
.end annotation


# static fields
.field private static final ITEM_PHONEBOOK_LABEL:Ljava/lang/String; = "item_phonebook_label"

.field private static final ITEM_PHONEBOOK_NAME:Ljava/lang/String; = "item_phonebook_name"

.field private static final ITEM_PHONEBOOK_NUMBER:Ljava/lang/String; = "item_phonebook_number"

.field private static final ITEM_PHONEBOOK_PATH:Ljava/lang/String; = "item_phonebook_path"

.field private static final LOADER_CONTRACT:I = 0x1

.field private static final MSG_DOWNLOAD_FAILED:I = 0x2

.field private static final MSG_DOWNLOAD_FINISH:I = 0x1

.field private static final MSG_DOWNLOAD_START:I = 0x0

.field private static final MSG_UI_HIDE_DOWNLOAD_GROUP:I = 0x6

.field private static final MSG_UI_LOAD_FINISH:I = 0x4

.field private static final MSG_UI_LOAD_START:I = 0x3

.field private static final MSG_UI_PB_ADD:I = 0x5

.field private static final PHONEBOOK_LABEL:Ljava/lang/String; = "phonebook_label"

.field private static final PHONEBOOK_LABEL_ALT:Ljava/lang/String; = "phonebook_label_alt"

.field public static final TAG:Ljava/lang/String; = "PBFragment"


# instance fields
.field private mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

.field private mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

.field private mCancellationSignal:Landroid/os/CancellationSignal;

.field private mClearContacts:Landroid/view/View;

.field private mContactCountTextView:Landroid/widget/TextView;

.field private mContext:Landroid/content/Context;

.field private mDownloadLayout:Landroid/widget/LinearLayout;

.field private mDownloadTextView:Landroid/widget/TextView;

.field private mEditSearchText:Landroid/widget/EditText;

.field private mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

.field mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

.field private mLoaderHandler:Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;

.field private mLoaderThread:Landroid/os/HandlerThread;

.field private mPBGridView:Landroid/widget/AbsListView;

.field public mPBList:Ljava/util/ArrayList;
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

.field private mPBListAdapter:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

.field private mPhoneBookListClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field private mSearchEditWatcher:Landroid/text/TextWatcher;

.field private mSearchKey:Ljava/lang/String;

.field private mSearchText:Landroid/widget/EditText;

.field private mSideBar:Lcom/autochips/bluetooth/view/SideBar;

.field private mUpdatePBCtrl:Landroid/widget/Button;

.field private pbReceiver:Landroid/content/BroadcastReceiver;


# direct methods
.method public constructor <init>()V
    .locals 2

    .line 60
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;-><init>()V

    const/4 v0, 0x0

    .line 71
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mUpdatePBCtrl:Landroid/widget/Button;

    .line 73
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBGridView:Landroid/widget/AbsListView;

    .line 75
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    const-string v1, ""

    .line 91
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchKey:Ljava/lang/String;

    .line 92
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 472
    new-instance v0, Lcom/autochips/bluetooth/fragment/PBFragment$2;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PBFragment$2;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->pbReceiver:Landroid/content/BroadcastReceiver;

    .line 662
    new-instance v0, Lcom/autochips/bluetooth/fragment/PBFragment$3;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PBFragment$3;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchEditWatcher:Landroid/text/TextWatcher;

    .line 810
    new-instance v0, Lcom/autochips/bluetooth/fragment/PBFragment$4;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PBFragment$4;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPhoneBookListClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 825
    new-instance v0, Lcom/autochips/bluetooth/fragment/PBFragment$5;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PBFragment$5;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    .line 841
    new-instance v0, Lcom/autochips/bluetooth/fragment/PBFragment$6;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PBFragment$6;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

    return-void
.end method

.method static synthetic access$000(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->showPBDownloadGroup()V

    return-void
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->cancelLoadContact()V

    return-void
.end method

.method static synthetic access$1000(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/view/SideBar;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSideBar:Lcom/autochips/bluetooth/view/SideBar;

    return-object p0
.end method

.method static synthetic access$1200(Lcom/autochips/bluetooth/fragment/PBFragment;)Ljava/lang/String;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchKey:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$1202(Lcom/autochips/bluetooth/fragment/PBFragment;Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 60
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchKey:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$1400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p0
.end method

.method static synthetic access$1500(Lcom/autochips/bluetooth/fragment/PBFragment;I)V
    .locals 0

    .line 60
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->pbapConnectStateChange(I)V

    return-void
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/fragment/PBFragment;Ljava/lang/String;)V
    .locals 0

    .line 60
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->startLoadContact(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$300(Lcom/autochips/bluetooth/fragment/PBFragment;I)V
    .locals 0

    .line 60
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->changeDownloadHint(I)V

    return-void
.end method

.method static synthetic access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    return-object p0
.end method

.method static synthetic access$500(Lcom/autochips/bluetooth/fragment/PBFragment;)Landroid/widget/TextView;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mContactCountTextView:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$600(Lcom/autochips/bluetooth/fragment/PBFragment;)Landroid/widget/EditText;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchText:Landroid/widget/EditText;

    return-object p0
.end method

.method static synthetic access$700(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->hidePBDownloadGroup()V

    return-void
.end method

.method static synthetic access$800(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    return-object p0
.end method

.method static synthetic access$900(Lcom/autochips/bluetooth/fragment/PBFragment;)Landroid/widget/AbsListView;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBGridView:Landroid/widget/AbsListView;

    return-object p0
.end method

.method private cancelLoadContact()V
    .locals 3

    const-string v0, "PBFragment"

    const-string v1, "cancelLoadContact!!!"

    .line 533
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 534
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;->removeMessages(I)V

    .line 535
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    if-eqz v1, :cond_0

    const-string v1, "mCancellationSignal cancel!!!"

    .line 536
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 537
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    invoke-virtual {v0}, Landroid/os/CancellationSignal;->cancel()V

    :cond_0
    return-void
.end method

.method private changeDownloadHint(I)V
    .locals 4

    .line 462
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->isAdded()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    .line 465
    :cond_0
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v0

    const v1, 0x7f0d0086

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    aput-object p1, v2, v3

    invoke-static {v0, v1, v2}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p1

    .line 466
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mDownloadTextView:Landroid/widget/TextView;

    if-eqz v0, :cond_1

    .line 467
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "changeDownloadHint:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "PBFragment"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 468
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mDownloadTextView:Landroid/widget/TextView;

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_1
    return-void
.end method

.method private hidePBDownloadGroup()V
    .locals 2

    .line 456
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mDownloadLayout:Landroid/widget/LinearLayout;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 457
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mUpdatePBCtrl:Landroid/widget/Button;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setEnabled(Z)V

    return-void
.end method

.method private hideSoftInput()V
    .locals 3

    .line 648
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    .line 651
    :cond_0
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "input_method"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/inputmethod/InputMethodManager;

    .line 653
    invoke-virtual {v0}, Landroid/view/inputmethod/InputMethodManager;->isActive()Z

    move-result v1

    if-eqz v1, :cond_2

    .line 654
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchText:Landroid/widget/EditText;

    const/4 v2, 0x0

    if-eqz v1, :cond_1

    .line 655
    invoke-virtual {v1}, Landroid/widget/EditText;->getWindowToken()Landroid/os/IBinder;

    move-result-object v1

    invoke-virtual {v0, v1, v2}, Landroid/view/inputmethod/InputMethodManager;->hideSoftInputFromWindow(Landroid/os/IBinder;I)Z

    goto :goto_0

    .line 656
    :cond_1
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mEditSearchText:Landroid/widget/EditText;

    if-eqz v1, :cond_2

    .line 657
    invoke-virtual {v1}, Landroid/widget/EditText;->getWindowToken()Landroid/os/IBinder;

    move-result-object v1

    invoke-virtual {v0, v1, v2}, Landroid/view/inputmethod/InputMethodManager;->hideSoftInputFromWindow(Landroid/os/IBinder;I)Z

    :cond_2
    :goto_0
    return-void
.end method

.method private initAdapterView()V
    .locals 8

    .line 250
    new-instance v7, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mContext:Landroid/content/Context;

    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    const v0, 0x7f0b0038

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v4

    const-string v0, "item_phonebook_name"

    const-string v1, "item_phonebook_number"

    const-string v5, "item_phonebook_path"

    filled-new-array {v0, v1, v5}, [Ljava/lang/String;

    move-result-object v5

    const/4 v0, 0x3

    new-array v6, v0, [I

    const v0, 0x7f0800db

    .line 255
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x0

    aput v0, v6, v1

    const v0, 0x7f0800dc

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x1

    aput v0, v6, v1

    const v0, 0x7f080090

    .line 256
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x2

    aput v0, v6, v1

    move-object v0, v7

    move-object v1, p0

    invoke-direct/range {v0 .. v6}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;Landroid/content/Context;Ljava/util/ArrayList;I[Ljava/lang/String;[I)V

    iput-object v7, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    .line 259
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBGridView:Landroid/widget/AbsListView;

    invoke-virtual {v0, v7}, Landroid/widget/AbsListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 260
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBGridView:Landroid/widget/AbsListView;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPhoneBookListClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/AbsListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 261
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBGridView:Landroid/widget/AbsListView;

    new-instance v1, Lcom/autochips/bluetooth/fragment/PBFragment$1;

    invoke-direct {v1, p0}, Lcom/autochips/bluetooth/fragment/PBFragment$1;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    invoke-virtual {v0, v1}, Landroid/widget/AbsListView;->setOnScrollListener(Landroid/widget/AbsListView$OnScrollListener;)V

    return-void
.end method

.method private initView(Landroid/view/View;)V
    .locals 6

    if-nez p1, :cond_0

    const-string p1, "PBFragment"

    const-string v0, "initView roo is null"

    .line 301
    invoke-static {p1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_0
    const v0, 0x7f080058

    .line 304
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mUpdatePBCtrl:Landroid/widget/Button;

    .line 305
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const v0, 0x7f080168

    .line 308
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mContactCountTextView:Landroid/widget/TextView;

    const v0, 0x7f080078

    .line 309
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mClearContacts:Landroid/view/View;

    if-eqz v0, :cond_2

    .line 311
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1d

    if-lt v0, v1, :cond_1

    .line 312
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mClearContacts:Landroid/view/View;

    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto :goto_0

    .line 314
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mClearContacts:Landroid/view/View;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    :cond_2
    :goto_0
    const v0, 0x7f0800b6

    .line 317
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mEditSearchText:Landroid/widget/EditText;

    if-eqz v0, :cond_3

    .line 319
    invoke-virtual {v0}, Landroid/widget/EditText;->clearFocus()V

    .line 320
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mEditSearchText:Landroid/widget/EditText;

    const v1, 0x7f0d0043

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setHint(Ljava/lang/CharSequence;)V

    .line 321
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mEditSearchText:Landroid/widget/EditText;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchEditWatcher:Landroid/text/TextWatcher;

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->addTextChangedListener(Landroid/text/TextWatcher;)V

    :cond_3
    const v0, 0x7f080122

    .line 325
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchText:Landroid/widget/EditText;

    const/4 v1, 0x0

    if-eqz v0, :cond_4

    .line 327
    invoke-virtual {v0}, Landroid/widget/EditText;->clearFocus()V

    .line 328
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchText:Landroid/widget/EditText;

    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v2

    const v3, 0x7f0d0041

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v3

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/Object;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v4, v1

    invoke-static {v2, v3, v4}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/widget/EditText;->setHint(Ljava/lang/CharSequence;)V

    .line 329
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchText:Landroid/widget/EditText;

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchEditWatcher:Landroid/text/TextWatcher;

    invoke-virtual {v0, v2}, Landroid/widget/EditText;->addTextChangedListener(Landroid/text/TextWatcher;)V

    :cond_4
    const v0, 0x7f08010a

    .line 332
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/autochips/bluetooth/view/SideBar;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSideBar:Lcom/autochips/bluetooth/view/SideBar;

    if-eqz v0, :cond_5

    .line 334
    invoke-virtual {v0, p0}, Lcom/autochips/bluetooth/view/SideBar;->setOnTouchingLetterChangedListener(Lcom/autochips/bluetooth/view/SideBar$OnTouchingLetterChangedListener;)V

    :cond_5
    const v0, 0x7f0800b2

    .line 337
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mDownloadLayout:Landroid/widget/LinearLayout;

    const v0, 0x7f080111

    .line 339
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ProgressBar;

    if-eqz v0, :cond_6

    .line 341
    invoke-virtual {v0, v1}, Landroid/widget/ProgressBar;->setIndeterminate(Z)V

    :cond_6
    const v0, 0x7f08016a

    .line 343
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mDownloadTextView:Landroid/widget/TextView;

    const v0, 0x7f080109

    .line 344
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/AbsListView;

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBGridView:Landroid/widget/AbsListView;

    .line 345
    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    return-void
.end method

.method private pbapConnectStateChange(I)V
    .locals 2

    .line 490
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "pbapConnectStateChange: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "PBFragment"

    invoke-static {v0, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method private showPBDownloadGroup()V
    .locals 2

    .line 449
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->removeMessages(I)V

    .line 450
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mUpdatePBCtrl:Landroid/widget/Button;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setEnabled(Z)V

    .line 451
    invoke-direct {p0, v1}, Lcom/autochips/bluetooth/fragment/PBFragment;->changeDownloadHint(I)V

    .line 452
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mDownloadLayout:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    return-void
.end method

.method private startLoadContact(Ljava/lang/String;)V
    .locals 2

    const-string v0, "PBFragment"

    const-string v1, "startLoadContact"

    .line 524
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 525
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->cancelLoadContact()V

    .line 526
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->clearContactList()V

    .line 527
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;

    const/4 v1, 0x1

    invoke-static {v0, v1}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 528
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    const-string p1, ""

    :cond_0
    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 529
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;

    invoke-virtual {p1, v0}, Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method


# virtual methods
.method public clearContactList()V
    .locals 8

    .line 502
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->access$1100(Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 503
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    const-string v1, ""

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->setSearchLetter(Ljava/lang/String;)V

    .line 506
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-lez v0, :cond_1

    .line 507
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 508
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->notifyDataSetChanged()V

    .line 510
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSideBar:Lcom/autochips/bluetooth/view/SideBar;

    if-eqz v0, :cond_2

    const/4 v1, -0x1

    .line 511
    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/view/SideBar;->setChoose(I)V

    .line 514
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mContactCountTextView:Landroid/widget/TextView;

    const/4 v1, 0x1

    const v2, 0x7f0d0041

    const/4 v3, 0x0

    if-eqz v0, :cond_3

    .line 515
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v4

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v5

    new-array v6, v1, [Ljava/lang/Object;

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v3

    invoke-static {v4, v5, v6}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 518
    :cond_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mSearchText:Landroid/widget/EditText;

    if-eqz v0, :cond_4

    .line 519
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v4

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v2

    new-array v1, v1, [Ljava/lang/Object;

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v1, v3

    invoke-static {v4, v2, v1}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setHint(Ljava/lang/CharSequence;)V

    :cond_4
    return-void
.end method

.method public getLayoutRes()I
    .locals 1

    const v0, 0x7f0b0025

    return v0
.end method

.method public loadContact(Ljava/lang/String;)V
    .locals 16

    move-object/from16 v1, p0

    move-object/from16 v0, p1

    .line 542
    invoke-virtual/range {p0 .. p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getContext()Landroid/content/Context;

    move-result-object v2

    const/4 v3, 0x4

    if-nez v2, :cond_0

    const-string v0, "PBFragment"

    const-string v2, "getContext()==null!"

    .line 543
    invoke-static {v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 544
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-static {v0, v3}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 545
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-virtual {v2, v0}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void

    .line 548
    :cond_0
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v2

    if-nez v2, :cond_1

    const-string v0, "PBFragment"

    const-string v2, "loadContact deviceInfo==null!"

    .line 550
    invoke-static {v0, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 551
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-static {v0, v3}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 552
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-virtual {v2, v0}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    return-void

    .line 556
    :cond_1
    invoke-virtual/range {p0 .. p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getContext()Landroid/content/Context;

    move-result-object v4

    invoke-virtual {v4}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v5

    const-string v4, "display_name"

    const-string v6, "phonebook_label"

    .line 561
    invoke-static {}, Lcom/hcn/bluetooth/api/Utils;->isReverseName()Z

    move-result v7

    if-eqz v7, :cond_2

    const-string v4, "display_name_alt"

    const-string v6, "phonebook_label_alt"

    :cond_2
    move-object v12, v6

    const/4 v13, 0x0

    .line 566
    :try_start_0
    monitor-enter p0
    :try_end_0
    .catch Landroid/os/OperationCanceledException; {:try_start_0 .. :try_end_0} :catch_0

    .line 567
    :try_start_1
    iget-object v6, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    if-eqz v6, :cond_3

    .line 568
    invoke-virtual {v6}, Landroid/os/CancellationSignal;->cancel()V

    .line 569
    iput-object v13, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 571
    :cond_3
    new-instance v6, Landroid/os/CancellationSignal;

    invoke-direct {v6}, Landroid/os/CancellationSignal;-><init>()V

    iput-object v6, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 572
    monitor-exit p0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 573
    :try_start_2
    invoke-static/range {p1 .. p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v6

    const/4 v7, 0x2

    const/4 v8, 0x0

    const/4 v14, 0x3

    const/4 v15, 0x1

    if-eqz v6, :cond_4

    .line 574
    sget-object v6, Landroid/provider/ContactsContract$CommonDataKinds$Phone;->CONTENT_URI:Landroid/net/Uri;

    new-array v0, v14, [Ljava/lang/String;

    aput-object v4, v0, v8

    const-string v9, "data1"

    aput-object v9, v0, v15

    aput-object v12, v0, v7

    const-string v9, "account_name=? AND account_type=? "

    new-array v10, v7, [Ljava/lang/String;

    .line 580
    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v10, v8

    const-string v2, "com.android.bluetooth.pbapsink"

    aput-object v2, v10, v15

    const-string v2, "sort_key COLLATE LOCALIZED asc"

    iget-object v11, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    move-object v7, v0

    move-object v8, v9

    move-object v9, v10

    move-object v10, v2

    .line 574
    invoke-virtual/range {v5 .. v11}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/database/Cursor;

    move-result-object v0

    goto/16 :goto_0

    .line 583
    :cond_4
    sget-object v6, Ljava/util/Locale;->ENGLISH:Ljava/util/Locale;

    const-string v9, "(%s LIKE ? or %s LIKE ?) AND %s=? AND %s=?"

    new-array v10, v3, [Ljava/lang/Object;

    const-string v11, "data1"

    aput-object v11, v10, v8

    aput-object v4, v10, v15

    const-string v11, "account_name"

    aput-object v11, v10, v7

    const-string v11, "account_type"

    aput-object v11, v10, v14

    invoke-static {v6, v9, v10}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    .line 587
    sget-object v6, Landroid/provider/ContactsContract$CommonDataKinds$Phone;->CONTENT_URI:Landroid/net/Uri;

    new-array v10, v14, [Ljava/lang/String;

    aput-object v4, v10, v8

    const-string v11, "data1"

    aput-object v11, v10, v15

    aput-object v12, v10, v7

    new-array v11, v3, [Ljava/lang/String;

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "%"

    invoke-virtual {v13, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v13, "%"

    invoke-virtual {v3, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    aput-object v3, v11, v8

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "%"

    invoke-virtual {v3, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, "%"

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v11, v15

    .line 593
    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v11, v7

    const-string v0, "com.android.bluetooth.pbapsink"

    aput-object v0, v11, v14

    const-string v0, "sort_key COLLATE LOCALIZED asc"

    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    move-object v7, v10

    move-object v8, v9

    move-object v9, v11

    move-object v10, v0

    move-object v11, v2

    .line 587
    invoke-virtual/range {v5 .. v11}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/database/Cursor;

    move-result-object v0
    :try_end_2
    .catch Landroid/os/OperationCanceledException; {:try_start_2 .. :try_end_2} :catch_0

    .line 605
    :goto_0
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    .line 606
    iget-object v3, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-virtual {v3, v14}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendEmptyMessage(I)Z

    .line 607
    :cond_5
    :goto_1
    invoke-interface {v0}, Landroid/database/Cursor;->moveToNext()Z

    move-result v3

    const/4 v5, 0x5

    if-eqz v3, :cond_6

    iget-object v3, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;

    invoke-virtual {v3, v15}, Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;->hasMessages(I)Z

    move-result v3

    if-nez v3, :cond_6

    iget-object v3, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 608
    invoke-virtual {v3}, Landroid/os/CancellationSignal;->isCanceled()Z

    move-result v3

    if-nez v3, :cond_6

    .line 609
    new-instance v3, Ljava/util/HashMap;

    invoke-direct {v3}, Ljava/util/HashMap;-><init>()V

    .line 610
    invoke-interface {v0, v4}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v6

    invoke-interface {v0, v6}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v6

    const-string v7, "data1"

    .line 611
    invoke-interface {v0, v7}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v7

    invoke-interface {v0, v7}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v7

    .line 612
    invoke-interface {v0, v12}, Landroid/database/Cursor;->getColumnIndex(Ljava/lang/String;)I

    move-result v8

    invoke-interface {v0, v8}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    move-result-object v8

    const-string v9, "item_phonebook_name"

    .line 614
    invoke-virtual {v3, v9, v6}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v6, "item_phonebook_number"

    .line 615
    invoke-virtual {v3, v6, v7}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v6, "item_phonebook_path"

    const-string v7, "1"

    .line 616
    invoke-virtual {v3, v6, v7}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    const-string v6, "item_phonebook_label"

    .line 617
    invoke-virtual {v3, v6, v8}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 619
    invoke-virtual {v2, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 621
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v3

    const/16 v6, 0xa

    if-lt v3, v6, :cond_5

    .line 623
    iget-object v3, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-static {v3, v5}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v3

    .line 624
    iput-object v2, v3, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 625
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-virtual {v2, v3}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    .line 626
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    goto :goto_1

    .line 629
    :cond_6
    invoke-interface {v0}, Landroid/database/Cursor;->close()V

    .line 630
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    invoke-virtual {v0}, Landroid/os/CancellationSignal;->isCanceled()Z

    move-result v0

    if-eqz v0, :cond_7

    const-string v0, "PBFragment"

    const-string v2, "loadContact: CancelLoader!!"

    .line 631
    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 632
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-virtual {v0, v5}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->removeMessages(I)V

    goto :goto_2

    :cond_7
    const-string v0, "PBFragment"

    .line 634
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "loadContact finish [list.size ]= "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 635
    iget-object v0, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    const/4 v3, 0x4

    invoke-static {v0, v3}, Landroid/os/Message;->obtain(Landroid/os/Handler;I)Landroid/os/Message;

    move-result-object v0

    .line 636
    iput-object v2, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 637
    iget-object v2, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-virtual {v2, v0}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->sendMessage(Landroid/os/Message;)Z

    .line 639
    :goto_2
    monitor-enter p0

    const/4 v2, 0x0

    .line 640
    :try_start_3
    iput-object v2, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 641
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

    .line 572
    :try_start_4
    monitor-exit p0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    :try_start_5
    throw v0
    :try_end_5
    .catch Landroid/os/OperationCanceledException; {:try_start_5 .. :try_end_5} :catch_0

    :catch_0
    const-string v0, "PBFragment"

    const-string v2, "loadContact: OperationCanceledException!!!!!!"

    .line 597
    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v2, 0x0

    .line 598
    invoke-interface {v2}, Landroid/database/Cursor;->close()V

    .line 599
    monitor-enter p0

    .line 600
    :try_start_6
    iput-object v2, v1, Lcom/autochips/bluetooth/fragment/PBFragment;->mCancellationSignal:Landroid/os/CancellationSignal;

    .line 601
    monitor-exit p0

    return-void

    :catchall_2
    move-exception v0

    monitor-exit p0
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_2

    throw v0
.end method

.method public onActivityCreated(Landroid/os/Bundle;)V
    .locals 0

    .line 350
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onActivityCreated(Landroid/os/Bundle;)V

    return-void
.end method

.method public onAttach(Landroid/content/Context;)V
    .locals 1

    .line 206
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onAttach(Landroid/content/Context;)V

    const-string p1, "PBFragment"

    const-string v0, "PBFragment onAttach"

    .line 207
    invoke-static {p1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 208
    new-instance p1, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    invoke-direct {p1, p0}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    return-void
.end method

.method public onBindViewData()V
    .locals 0

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 1

    .line 431
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result p1

    const v0, 0x7f080058

    if-eq p1, v0, :cond_1

    const v0, 0x7f080078

    if-eq p1, v0, :cond_0

    goto :goto_0

    .line 441
    :cond_0
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->clearContactList()V

    goto :goto_0

    .line 434
    :cond_1
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->hideSoftInput()V

    .line 435
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    const/4 v0, 0x2

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->pbapStartDownLoad(I)Z

    move-result p1

    if-nez p1, :cond_2

    .line 437
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getContext()Landroid/content/Context;

    move-result-object p1

    const v0, 0x7f0d0088

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    :cond_2
    :goto_0
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 2

    const-string v0, "PBFragment"

    const-string v1, "onCreate"

    .line 213
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 214
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreate(Landroid/os/Bundle;)V

    .line 215
    new-instance p1, Landroid/content/IntentFilter;

    invoke-direct {p1}, Landroid/content/IntentFilter;-><init>()V

    const-string v0, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    .line 216
    invoke-virtual {p1, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 217
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getActivity()Landroidx/fragment/app/FragmentActivity;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->pbReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1, p1}, Landroidx/fragment/app/FragmentActivity;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 219
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->getAdapterManager()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 220
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    .line 221
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->registerPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V

    .line 222
    new-instance p1, Landroid/os/HandlerThread;

    const-string v0, "loader_thread"

    invoke-direct {p1, v0}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mLoaderThread:Landroid/os/HandlerThread;

    .line 223
    invoke-virtual {p1}, Landroid/os/HandlerThread;->start()V

    .line 224
    new-instance p1, Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mLoaderThread:Landroid/os/HandlerThread;

    invoke-virtual {v0}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {p1, p0, v0}, Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;-><init>(Lcom/autochips/bluetooth/fragment/PBFragment;Landroid/os/Looper;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mLoaderHandler:Lcom/autochips/bluetooth/fragment/PBFragment$LoaderHandler;

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 2

    const-string v0, "PBFragment"

    const-string v1, "onCreateView"

    .line 230
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 232
    invoke-super {p0, p1, p2, p3}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;

    move-result-object p1

    .line 233
    invoke-static {}, Lcom/autochips/bluetooth/skin/SkinUtils;->getContext()Landroid/content/Context;

    move-result-object p2

    iput-object p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mContext:Landroid/content/Context;

    .line 234
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->initView(Landroid/view/View;)V

    .line 235
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->initAdapterView()V

    return-object p1
.end method

.method public onDestroy()V
    .locals 2

    const-string v0, "PBFragment"

    const-string v1, "onDestroy"

    .line 406
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 407
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 408
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->pbReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 410
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_1

    .line 411
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mIPbapCallback:Lcom/hcn/bluetooth/service/IPbapCallback;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->unregisterPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V

    .line 412
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    .line 414
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mLoaderThread:Landroid/os/HandlerThread;

    if-eqz v0, :cond_2

    .line 415
    invoke-virtual {v0}, Landroid/os/HandlerThread;->quitSafely()Z

    .line 417
    :cond_2
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroy()V

    return-void
.end method

.method public onDestroyView()V
    .locals 2

    .line 400
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroyView()V

    const-string v0, "PBFragment"

    const-string v1, "onDestroyView"

    .line 401
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onDetach()V
    .locals 2

    .line 422
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDetach()V

    const-string v0, "PBFragment"

    const-string v1, "onDetach"

    .line 423
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 424
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mHandler:Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 425
    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PBFragment$MainHandler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    :cond_0
    return-void
.end method

.method public onHiddenChanged(Z)V
    .locals 2

    .line 394
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onHiddenChanged(Z)V

    .line 395
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onHiddenChanged hidden="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "PBFragment"

    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onPause()V
    .locals 2

    const-string v0, "PBFragment"

    const-string v1, "onPause"

    .line 388
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 389
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onPause()V

    return-void
.end method

.method public onResume()V
    .locals 2

    .line 382
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onResume()V

    const-string v0, "PBFragment"

    const-string v1, "onResume"

    .line 383
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onTouchingLetterChanged(Ljava/lang/String;)V
    .locals 2

    .line 110
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onTouchingLetterChanged:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "PBFragment"

    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 111
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBListAdapter:Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    if-eqz v0, :cond_0

    .line 112
    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->setSearchLetter(Ljava/lang/String;)V

    .line 113
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBGridView:Landroid/widget/AbsListView;

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/widget/AbsListView;->setSelection(I)V

    :cond_0
    return-void
.end method

.method public setUserVisibleHint(Z)V
    .locals 2

    .line 355
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setUserVisibleHint:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "PBFragment"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 356
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->setUserVisibleHint(Z)V

    if-eqz p1, :cond_2

    .line 359
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_3

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isReady()Z

    move-result p1

    if-eqz p1, :cond_3

    .line 360
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 362
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    const/4 v0, 0x2

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getPbapDownLoadState(I)Z

    move-result p1

    if-eqz p1, :cond_0

    .line 364
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->showPBDownloadGroup()V

    goto :goto_0

    .line 366
    :cond_0
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->hidePBDownloadGroup()V

    .line 367
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment;->mPBList:Ljava/util/ArrayList;

    invoke-virtual {p1}, Ljava/util/ArrayList;->isEmpty()Z

    move-result p1

    if-eqz p1, :cond_3

    const-string p1, ""

    .line 368
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->startLoadContact(Ljava/lang/String;)V

    goto :goto_0

    .line 372
    :cond_1
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->clearContactList()V

    goto :goto_0

    .line 376
    :cond_2
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PBFragment;->hideSoftInput()V

    :cond_3
    :goto_0
    return-void
.end method
