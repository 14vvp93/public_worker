.class public Lcom/autochips/bluetooth/fragment/PairedFragment;
.super Lcom/hcn/skin/support/app/SkinCompatFragment;
.source "PairedFragment.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;,
        Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;
    }
.end annotation


# static fields
.field private static final MAX_PIN_CODE_LENGTH:I = 0x10

.field private static final MIN_PIN_CODE_LENGTH:I = 0x4

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

.field private static final TAG:Ljava/lang/String; = "ParedFragment"


# instance fields
.field private a2dpSwitchBtn:Landroid/widget/CheckBox;

.field private hfpSwitchBtn:Landroid/widget/CheckBox;

.field private isMarqueeOn:Z

.field private mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

.field private mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

.field private mAutoAnswerButton:Landroid/widget/Button;

.field private mAutoConnectButton:Landroid/widget/Button;

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

.field private mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

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

.field private mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

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

.field public mDevName:Ljava/lang/String;

.field private mDeviceName:Landroid/widget/Button;

.field private mDeviceProfileConfigDialog:Landroid/app/Dialog;

.field private mEditBTNameDialog:Landroid/app/Dialog;

.field private mEditBTPinDialog:Landroid/app/Dialog;

.field private mEditDeviceName:Landroid/widget/EditText;

.field private mEditPin:Landroid/widget/EditText;

.field private mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

.field private mIsAutoAnswer:Z

.field private mIsAutoConnect:Z

.field private mLayoutNetwork:Landroid/view/ViewGroup;

.field private mLayoutPaired:Landroid/view/ViewGroup;

.field private mMainHandler:Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

.field private mNetWorkButton:Landroid/widget/Button;

.field private mPairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field public mPinCode:Ljava/lang/String;

.field private mPowerSwitchButton:Landroid/widget/Button;

.field private mReceiver:Landroid/content/BroadcastReceiver;

.field private mTipsButton:Landroid/widget/Button;

.field private mTipsCloseButton:Landroid/widget/Button;

.field private mTvBTName:Landroid/widget/TextView;

.field private mTvBTPinCode:Landroid/widget/TextView;

.field private mUnpairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

.field private root:Landroid/view/View;

.field scanLayout:Landroid/widget/LinearLayout;


# direct methods
.method public constructor <init>()V
    .locals 2

    .line 60
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;-><init>()V

    .line 68
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevices:Ljava/util/ArrayList;

    .line 71
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevices:Ljava/util/ArrayList;

    const/4 v0, 0x0

    .line 77
    iput-boolean v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->isMarqueeOn:Z

    const/4 v1, 0x0

    .line 82
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    .line 92
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    .line 93
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditDeviceName:Landroid/widget/EditText;

    .line 94
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    .line 95
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditPin:Landroid/widget/EditText;

    .line 97
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoConnectButton:Landroid/widget/Button;

    .line 98
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoAnswerButton:Landroid/widget/Button;

    .line 99
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPowerSwitchButton:Landroid/widget/Button;

    .line 100
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTipsButton:Landroid/widget/Button;

    .line 101
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mNetWorkButton:Landroid/widget/Button;

    .line 102
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTipsCloseButton:Landroid/widget/Button;

    const-string v1, "0000"

    .line 108
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPinCode:Ljava/lang/String;

    const-string v1, "CarBT"

    .line 109
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDevName:Ljava/lang/String;

    .line 110
    iput-boolean v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIsAutoAnswer:Z

    .line 111
    iput-boolean v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIsAutoConnect:Z

    .line 518
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragment$1;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$1;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mUnpairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 534
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragment$2;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$2;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    .line 889
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragment$5;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$5;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    .line 904
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragment$6;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$6;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

    .line 1123
    new-instance v0, Lcom/autochips/bluetooth/fragment/PairedFragment$9;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$9;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    return-void
.end method

.method static synthetic access$000(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->onActionDiscoveryStarted()V

    return-void
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->updateUnpairedDevicesList()V

    return-void
.end method

.method static synthetic access$1000(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceName:Landroid/widget/Button;

    return-object p0
.end method

.method static synthetic access$1100(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->onConfigDeviceProfile()V

    return-void
.end method

.method static synthetic access$1200(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/service/IAdapterCallback;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

    return-object p0
.end method

.method static synthetic access$1300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    return-object p0
.end method

.method static synthetic access$1400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/EditText;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditDeviceName:Landroid/widget/EditText;

    return-object p0
.end method

.method static synthetic access$1500(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/view/View;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    return-object p0
.end method

.method static synthetic access$1600(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/EditText;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditPin:Landroid/widget/EditText;

    return-object p0
.end method

.method static synthetic access$1700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/app/Dialog;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    return-object p0
.end method

.method static synthetic access$1800(Lcom/autochips/bluetooth/fragment/PairedFragment;)Landroid/widget/Button;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPowerSwitchButton:Landroid/widget/Button;

    return-object p0
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->onActionDiscoveryFinished()V

    return-void
.end method

.method static synthetic access$300(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    return-object p0
.end method

.method static synthetic access$400(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    return-object p0
.end method

.method static synthetic access$500(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->updateBondedDevicesList()V

    return-void
.end method

.method static synthetic access$600(Lcom/autochips/bluetooth/fragment/PairedFragment;ILjava/lang/String;)V
    .locals 0

    .line 60
    invoke-direct {p0, p1, p2}, Lcom/autochips/bluetooth/fragment/PairedFragment;->updateConnectState(ILjava/lang/String;)V

    return-void
.end method

.method static synthetic access$700(Lcom/autochips/bluetooth/fragment/PairedFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 0

    .line 60
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p0
.end method

.method static synthetic access$800(Lcom/autochips/bluetooth/fragment/PairedFragment;)V
    .locals 0

    .line 60
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->updateSettingView()V

    return-void
.end method

.method static synthetic access$900(Lcom/autochips/bluetooth/fragment/PairedFragment;)Z
    .locals 0

    .line 60
    iget-boolean p0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->isMarqueeOn:Z

    return p0
.end method

.method static synthetic access$902(Lcom/autochips/bluetooth/fragment/PairedFragment;Z)Z
    .locals 0

    .line 60
    iput-boolean p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->isMarqueeOn:Z

    return p1
.end method

.method private buildDeviceProfileConfigDialog()V
    .locals 6

    .line 817
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x10302d2

    invoke-direct {v0, v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    const v1, 0x7f0d003b

    .line 819
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 821
    new-instance v1, Landroid/widget/CheckBox;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/CheckBox;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->hfpSwitchBtn:Landroid/widget/CheckBox;

    const v2, 0x7f0d008e

    .line 822
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/widget/CheckBox;->setText(Ljava/lang/CharSequence;)V

    .line 823
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->hfpSwitchBtn:Landroid/widget/CheckBox;

    const v2, 0x106000c

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result v3

    invoke-virtual {v1, v3}, Landroid/widget/CheckBox;->setTextColor(I)V

    .line 824
    new-instance v1, Landroid/widget/CheckBox;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v1, v3}, Landroid/widget/CheckBox;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    const v3, 0x7f0d0072

    .line 825
    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Landroid/widget/CheckBox;->setText(Ljava/lang/CharSequence;)V

    .line 826
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getColor(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/widget/CheckBox;->setTextColor(I)V

    .line 829
    new-instance v1, Landroid/widget/LinearLayout;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    const/4 v2, 0x1

    .line 830
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 831
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v3, -0x1

    const/4 v4, -0x2

    invoke-direct {v2, v3, v4}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    const/16 v3, 0x18

    const/16 v4, 0xc

    const/4 v5, 0x0

    .line 833
    invoke-virtual {v2, v3, v4, v3, v5}, Landroid/widget/LinearLayout$LayoutParams;->setMargins(IIII)V

    .line 834
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->hfpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v1, v3, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 835
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v1, v3, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 837
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    const/high16 v1, 0x1040000

    const/4 v2, 0x0

    .line 838
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 839
    new-instance v1, Lcom/autochips/bluetooth/fragment/PairedFragment$4;

    invoke-direct {v1, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$4;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    const v2, 0x104000a

    invoke-virtual {v0, v2, v1}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 846
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    return-void
.end method

.method private buildEditNameDialog()V
    .locals 6

    .line 1028
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    .line 1031
    :cond_0
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x10302d2

    invoke-direct {v0, v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    const v1, 0x7f0d003b

    .line 1033
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    const v1, 0x7f0d003d

    .line 1034
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 1035
    new-instance v1, Landroid/widget/EditText;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditDeviceName:Landroid/widget/EditText;

    const/4 v2, 0x1

    .line 1036
    invoke-virtual {v1, v2}, Landroid/widget/EditText;->setSingleLine(Z)V

    .line 1037
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditDeviceName:Landroid/widget/EditText;

    new-array v3, v2, [Landroid/text/InputFilter;

    new-instance v4, Landroid/text/InputFilter$LengthFilter;

    const/16 v5, 0xf8

    invoke-direct {v4, v5}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v1, v3}, Landroid/widget/EditText;->setFilters([Landroid/text/InputFilter;)V

    .line 1039
    new-instance v1, Landroid/widget/LinearLayout;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v1, v3}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 1040
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 1041
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v3, -0x1

    const/4 v4, -0x2

    invoke-direct {v2, v3, v4}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    const/16 v3, 0x18

    .line 1043
    invoke-virtual {v2, v3, v5, v3, v5}, Landroid/widget/LinearLayout$LayoutParams;->setMargins(IIII)V

    .line 1044
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditDeviceName:Landroid/widget/EditText;

    invoke-virtual {v1, v3, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 1046
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    const/high16 v1, 0x1040000

    const/4 v2, 0x0

    .line 1047
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    const v1, 0x104000a

    .line 1048
    new-instance v2, Lcom/autochips/bluetooth/fragment/PairedFragment$7;

    invoke-direct {v2, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$7;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 1071
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    return-void
.end method

.method private buildEditPinDialog()V
    .locals 6

    .line 1075
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    .line 1078
    :cond_0
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x10302d2

    invoke-direct {v0, v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    const v1, 0x7f0d003b

    .line 1080
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    const v1, 0x7f0d003f

    .line 1081
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 1082
    new-instance v1, Landroid/widget/EditText;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditPin:Landroid/widget/EditText;

    const/4 v2, 0x1

    .line 1083
    invoke-virtual {v1, v2}, Landroid/widget/EditText;->setSingleLine(Z)V

    .line 1084
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditPin:Landroid/widget/EditText;

    new-array v3, v2, [Landroid/text/InputFilter;

    new-instance v4, Landroid/text/InputFilter$LengthFilter;

    const/16 v5, 0x10

    invoke-direct {v4, v5}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v1, v3}, Landroid/widget/EditText;->setFilters([Landroid/text/InputFilter;)V

    .line 1086
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditPin:Landroid/widget/EditText;

    const/4 v3, 0x2

    invoke-virtual {v1, v3}, Landroid/widget/EditText;->setInputType(I)V

    .line 1089
    new-instance v1, Landroid/widget/LinearLayout;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v1, v3}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 1090
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 1091
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v3, -0x1

    const/4 v4, -0x2

    invoke-direct {v2, v3, v4}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    const/16 v3, 0x18

    .line 1093
    invoke-virtual {v2, v3, v5, v3, v5}, Landroid/widget/LinearLayout$LayoutParams;->setMargins(IIII)V

    .line 1094
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditPin:Landroid/widget/EditText;

    invoke-virtual {v1, v3, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 1096
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    const/high16 v1, 0x1040000

    const/4 v2, 0x0

    .line 1097
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    const v1, 0x104000a

    .line 1098
    new-instance v2, Lcom/autochips/bluetooth/fragment/PairedFragment$8;

    invoke-direct {v2, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$8;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 1120
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    return-void
.end method

.method private initSettingView()V
    .locals 4

    .line 306
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f08016b

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTvBTName:Landroid/widget/TextView;

    .line 307
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f08016c

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTvBTPinCode:Landroid/widget/TextView;

    .line 308
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f08007c

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    .line 309
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v2, 0x7f08007d

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/Button;

    .line 310
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f080139

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoConnectButton:Landroid/widget/Button;

    .line 311
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f080138

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoAnswerButton:Landroid/widget/Button;

    .line 312
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f08013a

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPowerSwitchButton:Landroid/widget/Button;

    .line 313
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f0800f5

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mNetWorkButton:Landroid/widget/Button;

    .line 314
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f0800f7

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTipsButton:Landroid/widget/Button;

    .line 315
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f08015d

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTipsCloseButton:Landroid/widget/Button;

    .line 317
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f0800f6

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/view/ViewGroup;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mLayoutNetwork:Landroid/view/ViewGroup;

    .line 318
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f080106

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/view/ViewGroup;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mLayoutPaired:Landroid/view/ViewGroup;

    .line 320
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mNetWorkButton:Landroid/widget/Button;

    if-eqz v2, :cond_0

    .line 321
    invoke-virtual {v2, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 323
    :cond_0
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTipsButton:Landroid/widget/Button;

    if-eqz v2, :cond_1

    .line 324
    invoke-virtual {v2, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 326
    :cond_1
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTipsCloseButton:Landroid/widget/Button;

    if-eqz v2, :cond_2

    .line 327
    invoke-virtual {v2, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 329
    :cond_2
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPowerSwitchButton:Landroid/widget/Button;

    if-eqz v2, :cond_3

    .line 330
    invoke-virtual {v2, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_3
    if-eqz v0, :cond_4

    .line 333
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_4
    if-eqz v1, :cond_5

    .line 336
    invoke-virtual {v1, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 338
    :cond_5
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoConnectButton:Landroid/widget/Button;

    if-eqz v0, :cond_6

    .line 339
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 341
    :cond_6
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoAnswerButton:Landroid/widget/Button;

    if-eqz v0, :cond_7

    .line 342
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 345
    :cond_7
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f0800e6

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    const-string v1, "persist.sys.bt.disablessp"

    const-string v2, "false"

    .line 347
    invoke-static {v1, v2}, Lcom/hcn/bluetooth/api/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    if-eqz v0, :cond_8

    const-string v2, "true"

    .line 348
    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_8

    const/4 v1, 0x0

    .line 349
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    :cond_8
    return-void
.end method

.method private onActionDiscoveryFinished()V
    .locals 2

    .line 568
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->scanLayout:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_0

    const/16 v1, 0x8

    .line 569
    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_0
    return-void
.end method

.method private onActionDiscoveryStarted()V
    .locals 2

    .line 562
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->scanLayout:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 563
    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_0
    return-void
.end method

.method private onClickConnect()V
    .locals 2

    .line 481
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    return-void

    .line 485
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->getSelect()Ljava/lang/String;

    move-result-object v0

    .line 486
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_1

    .line 487
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->getSelect()Ljava/lang/String;

    move-result-object v0

    .line 489
    :cond_1
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_2

    return-void

    .line 492
    :cond_2
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->connectDevice(Ljava/lang/String;)V

    return-void
.end method

.method private onClickDisconnect()V
    .locals 2

    .line 496
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    return-void

    .line 500
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->getSelect()Ljava/lang/String;

    move-result-object v0

    .line 501
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_1

    return-void

    .line 504
    :cond_1
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectDevice(Ljava/lang/String;)V

    return-void
.end method

.method private onClickUnpair()V
    .locals 2

    .line 508
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    return-void

    .line 512
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->getSelect()Ljava/lang/String;

    move-result-object v0

    .line 513
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_1

    .line 514
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->unpairDevice(Ljava/lang/String;)V

    :cond_1
    return-void
.end method

.method private onConfigDeviceProfile()V
    .locals 6

    .line 850
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mConfigDeviceMap:Landroid/util/ArrayMap;

    if-nez v0, :cond_0

    const-string v0, "ParedFragment"

    const-string v1, "onConfigDeviceProfile mConfigDeviceMap is null !"

    .line 851
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 858
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->hfpSwitchBtn:Landroid/widget/CheckBox;

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

    .line 862
    :goto_0
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    if-eqz v3, :cond_2

    invoke-virtual {v3}, Landroid/widget/CheckBox;->isChecked()Z

    move-result v3

    if-eqz v3, :cond_2

    move v3, v1

    goto :goto_1

    :cond_2
    move v3, v2

    .line 866
    :goto_1
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mConfigDeviceMap:Landroid/util/ArrayMap;

    const-string v5, "remote_device_macaddr"

    invoke-virtual {v4, v5}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 867
    iget-object v5, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v5}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v5

    if-eqz v5, :cond_3

    .line 869
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

    .line 876
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->connectDevice(Ljava/lang/String;)V

    goto :goto_3

    :cond_4
    if-nez v0, :cond_5

    if-eqz v1, :cond_5

    .line 878
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectDevice(Ljava/lang/String;)V

    .line 881
    :cond_5
    :goto_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isA2dpConnected(Ljava/lang/String;)Z

    move-result v0

    if-eqz v3, :cond_6

    if-eqz v1, :cond_6

    if-nez v0, :cond_6

    .line 883
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->connectA2dp(Ljava/lang/String;)V

    goto :goto_4

    :cond_6
    if-nez v3, :cond_7

    if-eqz v0, :cond_7

    .line 885
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectA2dp(Ljava/lang/String;)V

    :cond_7
    :goto_4
    return-void
.end method

.method private showDeviceProfileConfigDialog()V
    .locals 5

    .line 782
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    if-nez v0, :cond_0

    return-void

    .line 786
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mConfigDeviceMap:Landroid/util/ArrayMap;

    if-nez v0, :cond_1

    const-string v0, "ParedFragment"

    const-string v1, "showDeviceProfile mConfigDeviceMap is null !"

    .line 787
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    :cond_1
    const-string v1, "remote_device_name"

    .line 791
    invoke-virtual {v0, v1}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    .line 792
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mConfigDeviceMap:Landroid/util/ArrayMap;

    const-string v2, "remote_device_macaddr"

    invoke-virtual {v1, v2}, Landroid/util/ArrayMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 793
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v2

    .line 794
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    invoke-virtual {v3}, Landroid/app/Dialog;->isShowing()Z

    move-result v3

    if-nez v3, :cond_6

    .line 795
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    invoke-virtual {v3, v0}, Landroid/app/Dialog;->setTitle(Ljava/lang/CharSequence;)V

    .line 797
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->hfpSwitchBtn:Landroid/widget/CheckBox;

    const/4 v3, 0x1

    const/4 v4, 0x0

    if-eqz v0, :cond_3

    if-eqz v2, :cond_2

    .line 798
    invoke-virtual {v2}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 799
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->hfpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v0, v3}, Landroid/widget/CheckBox;->setChecked(Z)V

    goto :goto_0

    .line 801
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->hfpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v0, v4}, Landroid/widget/CheckBox;->setChecked(Z)V

    .line 805
    :cond_3
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    if-eqz v0, :cond_5

    .line 806
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isA2dpConnected(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_4

    .line 807
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v0, v3}, Landroid/widget/CheckBox;->setChecked(Z)V

    goto :goto_1

    .line 809
    :cond_4
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->a2dpSwitchBtn:Landroid/widget/CheckBox;

    invoke-virtual {v0, v4}, Landroid/widget/CheckBox;->setChecked(Z)V

    .line 812
    :cond_5
    :goto_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceProfileConfigDialog:Landroid/app/Dialog;

    invoke-virtual {v0}, Landroid/app/Dialog;->show()V

    :cond_6
    return-void
.end method

.method private showNetworkTips(Z)V
    .locals 2

    .line 954
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "showNetworkTips show="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "ParedFragment"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    const/4 v0, 0x0

    if-eqz p1, :cond_1

    .line 956
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mLayoutNetwork:Landroid/view/ViewGroup;

    if-eqz p1, :cond_0

    .line 957
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 959
    :cond_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mLayoutPaired:Landroid/view/ViewGroup;

    if-eqz p1, :cond_3

    const/4 v0, 0x4

    .line 960
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->setVisibility(I)V

    goto :goto_0

    .line 963
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mLayoutNetwork:Landroid/view/ViewGroup;

    if-eqz p1, :cond_2

    const/16 v1, 0x8

    .line 964
    invoke-virtual {p1, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 966
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mLayoutPaired:Landroid/view/ViewGroup;

    if-eqz p1, :cond_3

    .line 967
    invoke-virtual {p1, v0}, Landroid/view/ViewGroup;->setVisibility(I)V

    :cond_3
    :goto_0
    return-void
.end method

.method private updateBondedDevicesList()V
    .locals 5

    .line 621
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_1

    .line 622
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevices:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 623
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBondedDevices()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 625
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "updateBondedDevicesList: getBondedDevices size="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 626
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "ParedFragment"

    .line 625
    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 627
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 628
    new-instance v2, Landroid/util/ArrayMap;

    invoke-direct {v2}, Landroid/util/ArrayMap;-><init>()V

    .line 629
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object v3

    const-string v4, "remote_device_name"

    invoke-virtual {v2, v4, v3}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 630
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v3

    const-string v4, "remote_device_macaddr"

    invoke-virtual {v2, v4, v3}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 631
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v3, "remote_connect_status"

    invoke-virtual {v2, v3, v1}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 632
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevices:Ljava/util/ArrayList;

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 635
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->notifyDataSetChanged()V

    :cond_1
    return-void
.end method

.method private updateConnectState(ILjava/lang/String;)V
    .locals 2

    .line 580
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mConnectState:Landroid/widget/TextView;

    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-static {p1}, Lcom/hcn/skin2/Skin2;->xAppId(I)I

    move-result p1

    invoke-virtual {v1, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getString(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 581
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceName:Landroid/widget/Button;

    invoke-virtual {p1, p2}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 582
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceName:Landroid/widget/Button;

    new-instance p2, Lcom/autochips/bluetooth/fragment/PairedFragment$3;

    invoke-direct {p2, p0}, Lcom/autochips/bluetooth/fragment/PairedFragment$3;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;)V

    invoke-virtual {p1, p2}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    return-void
.end method

.method private updateSettingView()V
    .locals 4

    .line 973
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    const-string v0, "ParedFragment"

    const-string v1, "updateSettingView: mAdapterManager is null!"

    .line 974
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 977
    :cond_0
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDevName:Ljava/lang/String;

    .line 978
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTPincode()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPinCode:Ljava/lang/String;

    .line 979
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTvBTName:Landroid/widget/TextView;

    if-eqz v0, :cond_1

    .line 980
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDevName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 982
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mTvBTPinCode:Landroid/widget/TextView;

    if-eqz v0, :cond_2

    .line 983
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPinCode:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 985
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothEnable()Z

    move-result v0

    const v1, 0x7f07011e

    const v2, 0x7f07011d

    if-eqz v0, :cond_3

    .line 986
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPowerSwitchButton:Landroid/widget/Button;

    if-eqz v0, :cond_4

    const/4 v3, 0x1

    .line 987
    invoke-virtual {v0, v3}, Landroid/widget/Button;->setEnabled(Z)V

    .line 988
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPowerSwitchButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 991
    :cond_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPowerSwitchButton:Landroid/widget/Button;

    if-eqz v0, :cond_4

    .line 992
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 996
    :cond_4
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoAnswer()Z

    move-result v0

    if-eqz v0, :cond_5

    .line 997
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoAnswerButton:Landroid/widget/Button;

    if-eqz v0, :cond_6

    .line 998
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    .line 1001
    :cond_5
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoAnswerButton:Landroid/widget/Button;

    if-eqz v0, :cond_6

    .line 1002
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 1006
    :cond_6
    :goto_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoConnect()Z

    move-result v0

    if-eqz v0, :cond_7

    .line 1007
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoConnectButton:Landroid/widget/Button;

    if-eqz v0, :cond_8

    .line 1008
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    .line 1011
    :cond_7
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoConnectButton:Landroid/widget/Button;

    if-eqz v0, :cond_8

    .line 1012
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 1016
    :cond_8
    :goto_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isAutoConnectedNetwork()Z

    move-result v0

    if-eqz v0, :cond_9

    .line 1017
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mNetWorkButton:Landroid/widget/Button;

    if-eqz v0, :cond_a

    .line 1018
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_3

    .line 1021
    :cond_9
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mNetWorkButton:Landroid/widget/Button;

    if-eqz v0, :cond_a

    .line 1022
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :cond_a
    :goto_3
    return-void
.end method

.method private updateUnpairedDevicesList()V
    .locals 5

    .line 604
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_1

    .line 605
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevices:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 606
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getDeviceList()Ljava/util/List;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 608
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 609
    new-instance v2, Landroid/util/ArrayMap;

    invoke-direct {v2}, Landroid/util/ArrayMap;-><init>()V

    .line 610
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceName()Ljava/lang/String;

    move-result-object v3

    const-string v4, "remote_device_name"

    invoke-virtual {v2, v4, v3}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 611
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object v3

    const-string v4, "remote_device_macaddr"

    invoke-virtual {v2, v4, v3}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 612
    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceStatus()I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v3, "remote_connect_status"

    invoke-virtual {v2, v3, v1}, Landroid/util/ArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 613
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevices:Ljava/util/ArrayList;

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 616
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-virtual {v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;->notifyDataSetChanged()V

    :cond_1
    return-void
.end method


# virtual methods
.method public getLayoutRes()I
    .locals 1

    const v0, 0x7f0b0024

    return v0
.end method

.method public onAttach(Landroid/content/Context;)V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onAttach: "

    .line 222
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 223
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onAttach(Landroid/content/Context;)V

    .line 224
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->getAdapterManager()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 225
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    return-void
.end method

.method public onBindViewData()V
    .locals 0

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 4

    .line 365
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getThirdPartAPPMode()I

    move-result v0

    const/4 v1, 0x1

    if-ne v1, v0, :cond_0

    .line 366
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mContext:Landroid/content/Context;

    const v0, 0x7f0d00a0

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    .line 369
    :cond_0
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result v0

    const v2, 0x7f07011e

    const v3, 0x7f07011d

    sparse-switch v0, :sswitch_data_0

    goto/16 :goto_2

    :sswitch_0
    const/4 p1, 0x0

    .line 463
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->showNetworkTips(Z)V

    goto/16 :goto_2

    .line 466
    :sswitch_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_a

    .line 468
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 470
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->getDeviceAddr()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->disconnectDevice(Ljava/lang/String;)V

    .line 472
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->resetBT()V

    goto/16 :goto_2

    .line 419
    :sswitch_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_a

    .line 420
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoConnect()Z

    move-result p1

    xor-int/2addr p1, v1

    iput-boolean p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIsAutoConnect:Z

    if-eqz p1, :cond_2

    .line 422
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoConnectButton:Landroid/widget/Button;

    .line 423
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 422
    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 425
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoConnectButton:Landroid/widget/Button;

    .line 426
    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 425
    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 428
    :goto_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-boolean v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIsAutoConnect:Z

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBluetoothAutoConnect(Z)V

    goto/16 :goto_2

    .line 433
    :sswitch_3
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_a

    .line 434
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoAnswer()Z

    move-result p1

    xor-int/2addr p1, v1

    iput-boolean p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIsAutoAnswer:Z

    if-eqz p1, :cond_3

    .line 436
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoAnswerButton:Landroid/widget/Button;

    .line 437
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 436
    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    .line 439
    :cond_3
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAutoAnswerButton:Landroid/widget/Button;

    .line 440
    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 439
    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 442
    :goto_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-boolean v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIsAutoAnswer:Z

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBluetoothAutoAnswer(Z)V

    goto/16 :goto_2

    .line 447
    :sswitch_4
    invoke-direct {p0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragment;->showNetworkTips(Z)V

    goto/16 :goto_2

    .line 450
    :sswitch_5
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_a

    .line 451
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isAutoConnectedNetwork()Z

    move-result p1

    xor-int/2addr p1, v1

    .line 452
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setAutoConnectedNetwork(Z)V

    if-eqz p1, :cond_4

    .line 454
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mNetWorkButton:Landroid/widget/Button;

    .line 455
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 454
    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto/16 :goto_2

    .line 457
    :cond_4
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mNetWorkButton:Landroid/widget/Button;

    .line 458
    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 457
    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto/16 :goto_2

    .line 379
    :sswitch_6
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->onClickUnpair()V

    goto/16 :goto_2

    .line 372
    :sswitch_7
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isDiscovering()Z

    move-result p1

    if-eqz p1, :cond_5

    .line 373
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->stopDiscovery()V

    goto/16 :goto_2

    .line 375
    :cond_5
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->startDiscovery()V

    goto :goto_2

    .line 405
    :sswitch_8
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    if-nez p1, :cond_6

    .line 406
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->buildEditPinDialog()V

    .line 408
    :cond_6
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    if-eqz p1, :cond_a

    invoke-virtual {p1}, Landroid/app/Dialog;->isShowing()Z

    move-result p1

    if-nez p1, :cond_a

    .line 409
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_7

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditDeviceName:Landroid/widget/EditText;

    if-eqz v0, :cond_7

    .line 410
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTPincode()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPinCode:Ljava/lang/String;

    .line 411
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditPin:Landroid/widget/EditText;

    if-eqz v0, :cond_7

    .line 412
    invoke-virtual {v0, p1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 415
    :cond_7
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    invoke-virtual {p1}, Landroid/app/Dialog;->show()V

    goto :goto_2

    .line 392
    :sswitch_9
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    if-nez p1, :cond_8

    .line 393
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->buildEditNameDialog()V

    .line 395
    :cond_8
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    if-eqz p1, :cond_a

    invoke-virtual {p1}, Landroid/app/Dialog;->isShowing()Z

    move-result p1

    if-nez p1, :cond_a

    .line 396
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_9

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditDeviceName:Landroid/widget/EditText;

    if-eqz v0, :cond_9

    .line 397
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTName()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDevName:Ljava/lang/String;

    .line 398
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditDeviceName:Landroid/widget/EditText;

    invoke-virtual {v0, p1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 400
    :cond_9
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    invoke-virtual {p1}, Landroid/app/Dialog;->show()V

    goto :goto_2

    .line 385
    :sswitch_a
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->onClickDisconnect()V

    goto :goto_2

    .line 382
    :sswitch_b
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->onClickConnect()V

    goto :goto_2

    .line 388
    :sswitch_c
    invoke-virtual {p1}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Landroid/util/ArrayMap;

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mConfigDeviceMap:Landroid/util/ArrayMap;

    .line 389
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->showDeviceProfileConfigDialog()V

    :cond_a
    :goto_2
    return-void

    :sswitch_data_0
    .sparse-switch
        0x7f080076 -> :sswitch_c
        0x7f080077 -> :sswitch_b
        0x7f08007b -> :sswitch_a
        0x7f08007c -> :sswitch_9
        0x7f08007d -> :sswitch_8
        0x7f080093 -> :sswitch_7
        0x7f080098 -> :sswitch_6
        0x7f0800f5 -> :sswitch_5
        0x7f0800f7 -> :sswitch_4
        0x7f080138 -> :sswitch_3
        0x7f080139 -> :sswitch_2
        0x7f08013a -> :sswitch_1
        0x7f08015d -> :sswitch_0
    .end sparse-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onCreate: "

    .line 230
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 231
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreate(Landroid/os/Bundle;)V

    .line 232
    new-instance p1, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {p1, p0, v0}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;Landroid/os/Looper;)V

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    .line 233
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->registerCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 19

    move-object/from16 v7, p0

    const-string v0, "ParedFragment"

    const-string v1, "onCreateView"

    .line 240
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 242
    invoke-super/range {p0 .. p3}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;

    move-result-object v0

    iput-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    .line 243
    invoke-static {}, Lcom/autochips/bluetooth/skin/SkinUtils;->getContext()Landroid/content/Context;

    move-result-object v0

    iput-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mContext:Landroid/content/Context;

    .line 245
    new-instance v8, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    iget-object v2, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mContext:Landroid/content/Context;

    iget-object v3, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevices:Ljava/util/ArrayList;

    const v9, 0x7f0b0029

    .line 246
    invoke-static {v9}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v4

    const-string v10, "remote_device_name"

    const-string v11, "remote_connect_status"

    const-string v12, "remote_device_macaddr"

    filled-new-array {v10, v11, v12}, [Ljava/lang/String;

    move-result-object v5

    const/4 v13, 0x3

    new-array v6, v13, [I

    const v14, 0x7f0800df

    .line 249
    invoke-static {v14}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v15, 0x0

    aput v0, v6, v15

    const v16, 0x7f0800dd

    .line 250
    invoke-static/range {v16 .. v16}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x1

    aput v0, v6, v1

    const v17, 0x7f0800de

    .line 251
    invoke-static/range {v17 .. v17}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/16 v18, 0x2

    aput v0, v6, v18

    move-object v0, v8

    move v15, v1

    move-object/from16 v1, p0

    invoke-direct/range {v0 .. v6}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    iput-object v8, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    .line 253
    iget-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f080042

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ListView;

    if-eqz v0, :cond_0

    .line 255
    iget-object v1, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothUnpairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 256
    iget-object v1, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mUnpairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 257
    invoke-virtual {v0, v15}, Landroid/widget/ListView;->setEnabled(Z)V

    .line 260
    :cond_0
    new-instance v8, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    iget-object v2, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mContext:Landroid/content/Context;

    iget-object v3, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevices:Ljava/util/ArrayList;

    .line 261
    invoke-static {v9}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v4

    filled-new-array {v10, v11, v12}, [Ljava/lang/String;

    move-result-object v5

    new-array v6, v13, [I

    .line 264
    invoke-static {v14}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    const/4 v1, 0x0

    aput v0, v6, v1

    .line 265
    invoke-static/range {v16 .. v16}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    aput v0, v6, v15

    .line 266
    invoke-static/range {v17 .. v17}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v0

    aput v0, v6, v18

    move-object v0, v8

    move-object/from16 v1, p0

    invoke-direct/range {v0 .. v6}, Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;-><init>(Lcom/autochips/bluetooth/fragment/PairedFragment;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    iput-object v8, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    .line 268
    iget-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f080041

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ListView;

    if-eqz v0, :cond_1

    .line 270
    iget-object v1, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mBluetoothPairedDevicesAdapter:Lcom/autochips/bluetooth/fragment/PairedFragment$ListAdapter;

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 271
    iget-object v1, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mPairedClickListener:Landroid/widget/AdapterView$OnItemClickListener;

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 272
    invoke-virtual {v0, v15}, Landroid/widget/ListView;->setEnabled(Z)V

    .line 274
    :cond_1
    iget-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f080118

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->scanLayout:Landroid/widget/LinearLayout;

    .line 276
    iget-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f080050

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mDeviceName:Landroid/widget/Button;

    .line 277
    iget-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f08004f

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mConnectState:Landroid/widget/TextView;

    .line 279
    iget-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v1, 0x7f080093

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 280
    iget-object v1, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v2, 0x7f080098

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 281
    iget-object v2, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v3, 0x7f080077

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .line 282
    iget-object v3, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    const v4, 0x7f08007b

    invoke-static {v4}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    if-eqz v0, :cond_2

    .line 284
    invoke-virtual {v0, v7}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_2
    if-eqz v1, :cond_3

    .line 287
    invoke-virtual {v1, v7}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_3
    if-eqz v2, :cond_4

    .line 290
    invoke-virtual {v2, v7}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_4
    if-eqz v3, :cond_5

    .line 293
    invoke-virtual {v3, v7}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 295
    :cond_5
    invoke-direct/range {p0 .. p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->buildDeviceProfileConfigDialog()V

    .line 296
    invoke-direct/range {p0 .. p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->initSettingView()V

    .line 298
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    const-string v1, "android.bluetooth.adapter.action.STATE_CHANGED"

    .line 299
    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 300
    invoke-virtual/range {p0 .. p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-static {v1}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/Context;

    iget-object v2, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 301
    iget-object v0, v7, Lcom/autochips/bluetooth/fragment/PairedFragment;->root:Landroid/view/View;

    return-object v0
.end method

.method public onDestroy()V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onDestroy: "

    .line 668
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 669
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroy()V

    .line 670
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mIAdapterCallback:Lcom/hcn/bluetooth/service/IAdapterCallback;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->unregisterCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V

    .line 671
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->removeCallbacksAndMessages(Ljava/lang/Object;)V

    return-void
.end method

.method public onDestroyView()V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onDestroyView: "

    .line 661
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 662
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroyView()V

    .line 663
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/PairedFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    return-void
.end method

.method public onDetach()V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onDetach: "

    .line 676
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 677
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDetach()V

    .line 678
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mAdapterListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    return-void
.end method

.method public onHiddenChanged(Z)V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onHiddenChanged: "

    .line 216
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 217
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onHiddenChanged(Z)V

    return-void
.end method

.method public onPause()V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onPause: "

    .line 649
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 650
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onPause()V

    return-void
.end method

.method public onResume()V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onResume: "

    .line 641
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 642
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onResume()V

    .line 643
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    const/4 v1, 0x6

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->removeMessages(I)V

    .line 644
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PairedFragment;->mMainHandler:Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;

    invoke-virtual {v0, v1}, Lcom/autochips/bluetooth/fragment/PairedFragment$MainHandler;->sendEmptyMessage(I)Z

    return-void
.end method

.method public onStart()V
    .locals 2

    .line 557
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStart()V

    const-string v0, "ParedFragment"

    const-string v1, "onStart: "

    .line 558
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method public onStop()V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onStop: "

    .line 655
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 656
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStop()V

    return-void
.end method

.method public onViewCreated(Landroid/view/View;Landroid/os/Bundle;)V
    .locals 2

    const-string v0, "ParedFragment"

    const-string v1, "onViewCreated: "

    .line 551
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 552
    invoke-super {p0, p1, p2}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onViewCreated(Landroid/view/View;Landroid/os/Bundle;)V

    return-void
.end method
