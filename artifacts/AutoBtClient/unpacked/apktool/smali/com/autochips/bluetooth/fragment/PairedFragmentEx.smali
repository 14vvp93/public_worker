.class public Lcom/autochips/bluetooth/fragment/PairedFragmentEx;
.super Lcom/hcn/skin/support/app/SkinCompatFragment;
.source "PairedFragmentEx.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;,
        Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;
    }
.end annotation


# static fields
.field private static final MSG_BLUETOOTH_STATE_CHANGE:I = 0x0

.field private static final MSG_BOND_STATE_CHANGE:I = 0x4

.field private static final MSG_CONNECT_STATE_CHANGE:I = 0x5

.field private static final MSG_DISCOVERY_DEVICE_FOUND:I = 0x2

.field private static final MSG_DISCOVERY_DEVICE_NAME_CHANGE:I = 0x3

.field private static final MSG_DISCOVERY_STATE_CHANGE:I = 0x1

.field private static final MSG_UPDATE_FRAGMENT:I = 0x6

.field private static final REMOTE_CONNECT_STATE:Ljava/lang/String; = "remote_connect_status"

.field private static final REMOTE_DEVICE_MACADDR:Ljava/lang/String; = "remote_device_macaddr"

.field private static final REMOTE_DEVICE_NAME:Ljava/lang/String; = "remote_device_name"

.field private static final TAG:Ljava/lang/String; = "PairedFragmentEx"


# instance fields
.field private a2dpSwitchBtn:Landroid/widget/CheckBox;

.field private fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

.field private hfpSwitchBtn:Landroid/widget/CheckBox;

.field private mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

.field private mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

.field private mBluetoothPairedDevices:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Landroid/util/ArrayMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation
.end field

.field private mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

.field private mBluetoothUnpairedDevices:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Landroid/util/ArrayMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation
.end field

.field private mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

.field private mConfigDeviceMap:Landroid/util/ArrayMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/ArrayMap<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation
.end field

.field private mConnectState:Landroid/widget/TextView;

.field private mContext:Landroid/content/Context;

.field private mDeviceName:Landroid/widget/TextView;

.field private mDeviceProfileConfigDialog:Landroid/app/Dialog;

.field private mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

.field private mPairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field private mPairedHandler:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

.field private mPairedPopupView:Landroid/view/View;

.field private mUnpairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field private pairedRadioGroup:Landroid/widget/RadioGroup;

.field private popupWindow:Landroid/widget/PopupWindow;

.field private root:Landroid/view/View;

.field scanLayout:Landroid/widget/LinearLayout;

.field private strMacAdder:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 61
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;-><init>()V

    .line 87
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevices:Ljava/util/ArrayList;

    .line 91
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevices:Ljava/util/ArrayList;

    const/4 v0, 0x0

    .line 104
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    .line 113
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedPopupView:Landroid/view/View;

    .line 114
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->pairedRadioGroup:Landroid/widget/RadioGroup;

    .line 119
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    .line 415
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$2;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mUnpairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 439
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$3;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 568
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$5;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$5;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    .line 906
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$6;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

    return-void
.end method

.method static synthetic access$000(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/IFragmentCallback;
    .locals 0

    .line 61
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    return-object p0
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;
    .locals 0

    .line 61
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    return-object p0
.end method

.method static synthetic access$1000(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V
    .locals 0

    .line 61
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->updateBondedDevicesList()V

    return-void
.end method

.method static synthetic access$1100(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Ljava/lang/String;
    .locals 0

    .line 61
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->strMacAdder:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$1102(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 61
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->strMacAdder:Ljava/lang/String;

    return-object p1
.end method

.method static synthetic access$1200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;ILjava/lang/String;)V
    .locals 0

    .line 61
    invoke-direct {p0, p1, p2}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->updateConnectState(ILjava/lang/String;)V

    return-void
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;
    .locals 0

    .line 61
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    return-object p0
.end method

.method static synthetic access$300(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 0

    .line 61
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p0
.end method

.method static synthetic access$400(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V
    .locals 0

    .line 61
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->onConfigDeviceProfile()V

    return-void
.end method

.method static synthetic access$500(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/hcn/bluetooth/service/IAdapterCallback;
    .locals 0

    .line 61
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

    return-object p0
.end method

.method static synthetic access$600(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;
    .locals 0

    .line 61
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedHandler:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    return-object p0
.end method

.method static synthetic access$700(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V
    .locals 0

    .line 61
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->onActionDiscoveryStarted()V

    return-void
.end method

.method static synthetic access$800(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V
    .locals 0

    .line 61
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->updateUnpairedDevicesList()V

    return-void
.end method

.method static synthetic access$900(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V
    .locals 0

    .line 61
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->onActionDiscoveryFinished()V

    return-void
.end method

.method private buildDeviceProfileConfigDialog()V
    .locals 7

    .line 459
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x10302d2

    invoke-direct {v0, v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    const v1, 0x7f0d003b

    .line 461
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 463
    new-instance v1, Landroid/widget/CheckBox;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/CheckBox;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hfpSwitchBtn:Landroid/widget/CheckBox;

    const v2, 0x7f0d008e

    .line 464
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/CheckBox;->setText(Ljava/lang/CharSequence;)V

    .line 465
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hfpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const v3, 0x106000c

    const/4 v4, 0x0

    invoke-virtual {v2, v3, v4}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/widget/CheckBox;->setTextColor(I)V

    .line 466
    new-instance v1, Landroid/widget/CheckBox;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/CheckBox;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    const v2, 0x7f0d0072

    .line 467
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/CheckBox;->setText(Ljava/lang/CharSequence;)V

    .line 468
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2, v3, v4}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/widget/CheckBox;->setTextColor(I)V

    .line 470
    new-instance v1, Landroid/widget/LinearLayout;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    const/4 v2, 0x1

    .line 471
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 472
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v3, -0x1

    const/4 v5, -0x2

    invoke-direct {v2, v3, v5}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    const/16 v3, 0x18

    const/16 v5, 0xc

    const/4 v6, 0x0

    .line 474
    invoke-virtual {v2, v3, v5, v3, v6}, Landroid/widget/LinearLayout$LayoutParams;->setMargins(IIII)V

    .line 475
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hfpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v1, v3, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 476
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v1, v3, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 478
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    const/high16 v1, 0x1040000

    .line 479
    invoke-virtual {v0, v1, v4}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 480
    new-instance v1, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$4;

    invoke-direct {v1, p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$4;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    const v2, 0x104000a

    invoke-virtual {v0, v2, v1}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 487
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    return-void
.end method

.method private initPairedDevicesAdapter()V
    .locals 9

    .line 396
    new-instance v7, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mContext:Landroid/content/Context;

    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevices:Ljava/util/ArrayList;

    const v0, 0x7f0b0029

    .line 397
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v4

    const-string v0, "remote_device_name"

    const-string v1, "remote_connect_status"

    const-string v5, "remote_device_macaddr"

    filled-new-array {v0, v1, v5}, [Ljava/lang/String;

    move-result-object v5

    const/4 v0, 0x3

    new-array v6, v0, [I

    const v0, 0x7f0800df

    .line 400
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x0

    aput v0, v6, v1

    const v0, 0x7f0800dd

    .line 401
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v8, 0x1

    aput v0, v6, v8

    const v0, 0x7f0800de

    .line 402
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x2

    aput v0, v6, v1

    move-object v0, v7

    move-object v1, p0

    invoke-direct/range {v0 .. v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    iput-object v7, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    .line 404
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    const v1, 0x7f080041

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ListView;

    if-eqz v0, :cond_0

    .line 406
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 407
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 408
    invoke-virtual {v0, v8}, Landroid/widget/ListView;->setEnabled(Z)V

    :cond_0
    return-void
.end method

.method private initUnpairedDevicesAdapter()V
    .locals 9

    .line 376
    new-instance v7, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mContext:Landroid/content/Context;

    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevices:Ljava/util/ArrayList;

    const v0, 0x7f0b0029

    .line 377
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v4

    const-string v0, "remote_device_name"

    const-string v1, "remote_connect_status"

    const-string v5, "remote_device_macaddr"

    filled-new-array {v0, v1, v5}, [Ljava/lang/String;

    move-result-object v5

    const/4 v0, 0x3

    new-array v6, v0, [I

    const v0, 0x7f0800df

    .line 380
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x0

    aput v0, v6, v1

    const v0, 0x7f0800dd

    .line 381
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v8, 0x1

    aput v0, v6, v8

    const v0, 0x7f0800de

    .line 382
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x2

    aput v0, v6, v1

    move-object v0, v7

    move-object v1, p0

    invoke-direct/range {v0 .. v6}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    iput-object v7, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    .line 384
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    const v1, 0x7f080042

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ListView;

    if-eqz v0, :cond_0

    .line 386
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 387
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mUnpairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 388
    invoke-virtual {v0, v8}, Landroid/widget/ListView;->setEnabled(Z)V

    :cond_0
    return-void
.end method

.method private initView()V
    .locals 2

    .line 361
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    const v1, 0x7f080118

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->scanLayout:Landroid/widget/LinearLayout;

    .line 363
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    const v1, 0x7f080050

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mDeviceName:Landroid/widget/TextView;

    .line 364
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    const v1, 0x7f08004f

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mConnectState:Landroid/widget/TextView;

    .line 366
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    const v1, 0x7f080093

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 368
    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_0
    return-void
.end method

.method private onActionDiscoveryFinished()V
    .locals 2

    .line 760
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->scanLayout:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_0

    const/16 v1, 0x8

    .line 761
    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_0
    return-void
.end method

.method private onActionDiscoveryStarted()V
    .locals 2

    .line 751
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->scanLayout:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 752
    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_0
    return-void
.end method

.method private onClickConnect()V
    .locals 2

    .line 314
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    return-void

    .line 318
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->getSelect()Ljava/lang/String;

    move-result-object v0

    .line 319
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_1

    .line 320
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->getSelect()Ljava/lang/String;

    move-result-object v0

    .line 322
    :cond_1
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_2

    return-void

    .line 325
    :cond_2
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->connectDevice(Ljava/lang/String;)V

    return-void
.end method

.method private onClickDisconnect()V
    .locals 2

    .line 332
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    return-void

    .line 336
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->getSelect()Ljava/lang/String;

    move-result-object v0

    .line 337
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_1

    return-void

    .line 340
    :cond_1
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectDevice(Ljava/lang/String;)V

    return-void
.end method

.method private onClickUnpair()V
    .locals 2

    .line 347
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    return-void

    .line 351
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->getSelect()Ljava/lang/String;

    move-result-object v0

    .line 352
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_1

    .line 353
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->unpairDevice(Ljava/lang/String;)V

    :cond_1
    return-void
.end method

.method private onConfigDeviceProfile()V
    .locals 6

    .line 529
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mConfigDeviceMap:Landroid/util/ArrayMap;

    if-nez v0, :cond_0

    const-string v0, "PairedFragmentEx"

    const-string v1, "onConfigDeviceProfile mConfigDeviceMap is null !"

    .line 530
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 537
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hfpSwitchBtn:Landroid/widget/CheckBox;

    const/4 v1, 0x1

    const/4 v2, 0x0

    if-eqz v0, :cond_1

    invoke-virtual {v0}, Landroid/widget/CheckBox;->isChecked()Z

    move-result v0

    if-eqz v0, :cond_1

    move v0, v1

    goto :goto_0

    :cond_1
    move v0, v2

    .line 541
    :goto_0
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    if-eqz v3, :cond_2

    invoke-virtual {v3}, Landroid/widget/CheckBox;->isChecked()Z

    move-result v3

    if-eqz v3, :cond_2

    move v3, v1

    goto :goto_1

    :cond_2
    move v3, v2

    .line 545
    :goto_1
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mConfigDeviceMap:Landroid/util/ArrayMap;

    const-string v5, "remote_device_macaddr"

    invoke-virtual {v4, v5}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 546
    iget-object v5, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v5}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v5

    if-eqz v5, :cond_3

    .line 548
    invoke-virtual {v5}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_3

    goto :goto_2

    :cond_3
    move v1, v2

    :goto_2
    if-eqz v0, :cond_4

    if-nez v1, :cond_4

    .line 555
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->connectDevice(Ljava/lang/String;)V

    goto :goto_3

    :cond_4
    if-nez v0, :cond_5

    if-eqz v1, :cond_5

    .line 557
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectDevice(Ljava/lang/String;)V

    .line 560
    :cond_5
    :goto_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isA2dpConnected(Ljava/lang/String;)Z

    move-result v0

    if-eqz v3, :cond_6

    if-eqz v1, :cond_6

    if-nez v0, :cond_6

    .line 562
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->connectA2dp(Ljava/lang/String;)V

    goto :goto_4

    :cond_6
    if-nez v3, :cond_7

    if-eqz v0, :cond_7

    .line 564
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectA2dp(Ljava/lang/String;)V

    :cond_7
    :goto_4
    return-void
.end method

.method private showDeviceProfileConfigDialog()V
    .locals 5

    .line 494
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    if-nez v0, :cond_0

    return-void

    .line 498
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mConfigDeviceMap:Landroid/util/ArrayMap;

    if-nez v0, :cond_1

    const-string v0, "PairedFragmentEx"

    const-string v1, "showDeviceProfile mConfigDeviceMap is null !"

    .line 499
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    const-string v1, "remote_device_name"

    .line 503
    invoke-virtual {v0, v1}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    .line 504
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mConfigDeviceMap:Landroid/util/ArrayMap;

    const-string v2, "remote_device_macaddr"

    invoke-virtual {v1, v2}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 505
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v2

    .line 506
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    invoke-virtual {v3}, Landroid/app/Dialog;->isShowing()Z

    move-result v3

    if-nez v3, :cond_6

    .line 507
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    invoke-virtual {v3, v0}, Landroid/app/Dialog;->setTitle(Ljava/lang/CharSequence;)V

    .line 509
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hfpSwitchBtn:Landroid/widget/CheckBox;

    const/4 v3, 0x1

    const/4 v4, 0x0

    if-eqz v0, :cond_3

    if-eqz v2, :cond_2

    .line 510
    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 511
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hfpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v0, v3}, Landroid/widget/CheckBox;->setChecked(Z)V

    goto :goto_0

    .line 513
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hfpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v0, v4}, Landroid/widget/CheckBox;->setChecked(Z)V

    .line 517
    :cond_3
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    if-eqz v0, :cond_5

    .line 518
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isA2dpConnected(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_4

    .line 519
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v0, v3}, Landroid/widget/CheckBox;->setChecked(Z)V

    goto :goto_1

    .line 521
    :cond_4
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v0, v4}, Landroid/widget/CheckBox;->setChecked(Z)V

    .line 524
    :cond_5
    :goto_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    invoke-virtual {v0}, Landroid/app/Dialog;->show()V

    :cond_6
    return-void
.end method

.method private updateBondedDevicesList()V
    .locals 5

    .line 729
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_1

    .line 730
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevices:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 731
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBondedDevices()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 733
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "updateBondedDevicesList: getBondedDevices size="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 734
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "PairedFragmentEx"

    .line 733
    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 735
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 736
    new-instance v2, Landroid/util/ArrayMap;

    invoke-direct {v2}, Landroid/util/ArrayMap;-><init>()V

    .line 737
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object v3

    const-string v4, "remote_device_name"

    invoke-virtual {v2, v4, v3}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 738
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v3

    const-string v4, "remote_device_macaddr"

    invoke-virtual {v2, v4, v3}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 739
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v3, "remote_connect_status"

    invoke-virtual {v2, v3, v1}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 740
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevices:Ljava/util/ArrayList;

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 743
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->notifyDataSetChanged()V

    :cond_1
    return-void
.end method

.method private updateConnectState(ILjava/lang/String;)V
    .locals 2

    .line 695
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mConnectState:Landroid/widget/TextView;

    if-eqz v0, :cond_0

    .line 696
    invoke-static {}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-static {p1}, Lcom/hcn/skin2/Skin2;->xAppId(I)I

    move-result p1

    invoke-virtual {v1, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 698
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mDeviceName:Landroid/widget/TextView;

    if-eqz p1, :cond_1

    .line 699
    invoke-virtual {p1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_1
    return-void
.end method

.method private updateUnpairedDevicesList()V
    .locals 5

    .line 707
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_1

    .line 708
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevices:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 709
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getDeviceList()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 711
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "updateUnpairedDevicesList: UnpairedDevices size="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 712
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "PairedFragmentEx"

    .line 711
    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 713
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 714
    new-instance v2, Landroid/util/ArrayMap;

    invoke-direct {v2}, Landroid/util/ArrayMap;-><init>()V

    .line 715
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object v3

    const-string v4, "remote_device_name"

    invoke-virtual {v2, v4, v3}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 716
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v3

    const-string v4, "remote_device_macaddr"

    invoke-virtual {v2, v4, v3}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 717
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v3, "remote_connect_status"

    invoke-virtual {v2, v3, v1}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 718
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevices:Ljava/util/ArrayList;

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 721
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedListAdapter;->notifyDataSetChanged()V

    :cond_1
    return-void
.end method


# virtual methods
.method public getLayoutRes()I
    .locals 1

    const v0, 0x7f0b0024

    return v0
.end method

.method public hidePairedOperationDialog()V
    .locals 1

    .line 305
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->popupWindow:Landroid/widget/PopupWindow;

    if-eqz v0, :cond_0

    .line 306
    invoke-virtual {v0}, Landroid/widget/PopupWindow;->dismiss()V

    :cond_0
    return-void
.end method

.method public onAttach(Landroid/content/Context;)V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onAttach"

    .line 159
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 160
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onAttach(Landroid/content/Context;)V

    .line 161
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->getAdapterManager()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 162
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    return-void
.end method

.method public onBindViewData()V
    .locals 0

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 2

    .line 214
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getThirdPartAPPMode()I

    move-result v0

    const/4 v1, 0x1

    if-ne v1, v0, :cond_0

    .line 215
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mContext:Landroid/content/Context;

    const v0, 0x7f0d00a0

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    .line 218
    :cond_0
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result v0

    const v1, 0x7f080076

    if-eq v0, v1, :cond_3

    const p1, 0x7f080093

    if-eq v0, p1, :cond_1

    packed-switch v0, :pswitch_data_0

    goto :goto_0

    .line 232
    :pswitch_0
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->onClickDisconnect()V

    .line 233
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hidePairedOperationDialog()V

    goto :goto_0

    .line 240
    :pswitch_1
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->onClickUnpair()V

    .line 241
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hidePairedOperationDialog()V

    goto :goto_0

    .line 236
    :pswitch_2
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->onClickConnect()V

    .line 237
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hidePairedOperationDialog()V

    goto :goto_0

    .line 221
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isDiscovering()Z

    move-result p1

    if-eqz p1, :cond_2

    .line 222
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->stopDiscovery()V

    goto :goto_0

    .line 224
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->startDiscovery()V

    goto :goto_0

    .line 228
    :cond_3
    invoke-virtual {p1}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/util/ArrayMap;

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mConfigDeviceMap:Landroid/util/ArrayMap;

    .line 229
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->showDeviceProfileConfigDialog()V

    :goto_0
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x7f080054
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1

    .line 128
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreate(Landroid/os/Bundle;)V

    .line 129
    new-instance p1, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {p1, p0, v0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;Landroid/os/Looper;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedHandler:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    .line 130
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->registerCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onCreateView"

    .line 136
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 137
    invoke-super {p0, p1, p2, p3}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    .line 138
    invoke-static {}, Lcom/autochips/bluetooth/skin/SkinUtils;->getContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mContext:Landroid/content/Context;

    .line 140
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->initPairedDevicesAdapter()V

    .line 141
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->initUnpairedDevicesAdapter()V

    .line 142
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->initView()V

    .line 143
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->buildDeviceProfileConfigDialog()V

    .line 144
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    return-object p1
.end method

.method public onDestroy()V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onDestroy"

    .line 198
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 199
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroy()V

    .line 200
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->unregisterCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V

    .line 201
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedHandler:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    return-void
.end method

.method public onDestroyView()V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onDestroyView"

    .line 192
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 193
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroyView()V

    return-void
.end method

.method public onDetach()V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onDetach"

    .line 206
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 207
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDetach()V

    .line 208
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    return-void
.end method

.method public onHiddenChanged(Z)V
    .locals 0

    .line 167
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onHiddenChanged(Z)V

    return-void
.end method

.method public onPause()V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onPause"

    .line 180
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 181
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onPause()V

    return-void
.end method

.method public onResume()V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onResume"

    .line 172
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 173
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onResume()V

    .line 174
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedHandler:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->removeMessages(I)V

    .line 175
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedHandler:Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$PairedHandler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onStop()V
    .locals 2

    const-string v0, "PairedFragmentEx"

    const-string v1, "onStop"

    .line 186
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 187
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStop()V

    return-void
.end method

.method public setFragmentCallback(Lcom/autochips/bluetooth/IFragmentCallback;)V
    .locals 0

    .line 249
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    return-void
.end method

.method public showPairedOperationDialog()V
    .locals 5

    .line 256
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->popupWindow:Landroid/widget/PopupWindow;

    if-nez v0, :cond_2

    .line 257
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mContext:Landroid/content/Context;

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    const v1, 0x7f0b0037

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedPopupView:Landroid/view/View;

    .line 258
    new-instance v0, Landroid/widget/PopupWindow;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedPopupView:Landroid/view/View;

    const/4 v2, -0x2

    invoke-direct {v0, v1, v2, v2}, Landroid/widget/PopupWindow;-><init>(Landroid/view/View;II)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->popupWindow:Landroid/widget/PopupWindow;

    .line 261
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedPopupView:Landroid/view/View;

    const v1, 0x7f080057

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RadioGroup;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->pairedRadioGroup:Landroid/widget/RadioGroup;

    .line 262
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedPopupView:Landroid/view/View;

    const v1, 0x7f080056

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 263
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedPopupView:Landroid/view/View;

    const v2, 0x7f080054

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 264
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->mPairedPopupView:Landroid/view/View;

    const v3, 0x7f080055

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    if-eqz v2, :cond_0

    .line 267
    invoke-virtual {v2, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_0
    if-eqz v1, :cond_1

    .line 270
    invoke-virtual {v1, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_1
    if-eqz v0, :cond_2

    .line 273
    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 276
    :cond_2
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "PairedFragment2="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->hashCode()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "PairedFragmentEx"

    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 277
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    const/4 v2, 0x1

    if-eqz v0, :cond_3

    .line 278
    invoke-interface {v0, v2}, Lcom/autochips/bluetooth/IFragmentCallback;->updateBackground(Z)Z

    goto :goto_0

    :cond_3
    const-string v0, "fragmentCallback is null"

    .line 280
    invoke-static {v1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 283
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->pairedRadioGroup:Landroid/widget/RadioGroup;

    if-eqz v0, :cond_4

    .line 284
    invoke-virtual {v0}, Landroid/widget/RadioGroup;->clearCheck()V

    .line 287
    :cond_4
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->popupWindow:Landroid/widget/PopupWindow;

    invoke-virtual {v0, v2}, Landroid/widget/PopupWindow;->setOutsideTouchable(Z)V

    .line 288
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->popupWindow:Landroid/widget/PopupWindow;

    invoke-virtual {v0, v2}, Landroid/widget/PopupWindow;->setFocusable(Z)V

    .line 289
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->popupWindow:Landroid/widget/PopupWindow;

    const v1, 0x7f0e00ac

    invoke-virtual {v0, v1}, Landroid/widget/PopupWindow;->setAnimationStyle(I)V

    .line 290
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->popupWindow:Landroid/widget/PopupWindow;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->root:Landroid/view/View;

    const/16 v2, 0x11

    const/4 v3, 0x0

    const/16 v4, 0x1e

    invoke-virtual {v0, v1, v2, v3, v4}, Landroid/widget/PopupWindow;->showAtLocation(Landroid/view/View;III)V

    .line 291
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragmentEx;->popupWindow:Landroid/widget/PopupWindow;

    new-instance v1, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$1;

    invoke-direct {v1, p0}, Lcom/autochips/bluetooth/fragment/PairedFragmentEx$1;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragmentEx;)V

    invoke-virtual {v0, v1}, Landroid/widget/PopupWindow;->setOnDismissListener(Landroid/widget/PopupWindow$OnDismissListener;)V

    return-void
.end method
