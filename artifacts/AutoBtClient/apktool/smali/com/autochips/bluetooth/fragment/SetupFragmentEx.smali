.class public Lcom/autochips/bluetooth/fragment/SetupFragmentEx;
.super Lcom/hcn/skin/support/app/SkinCompatFragment;
.source "SetupFragmentEx.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# static fields
.field private static final MAX_PIN_CODE_LENGTH:I = 0x10

.field private static final MIN_PIN_CODE_LENGTH:I = 0x4

.field public static final TAG:Ljava/lang/String; = "SetupFragmentEx"


# instance fields
.field private fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

.field private layoutPinCode:Landroid/widget/LinearLayout;

.field private mAutoAnswerButton:Landroid/widget/Button;

.field private mAutoConnectButton:Landroid/widget/Button;

.field private mContext:Landroid/content/Context;

.field public mDevName:Ljava/lang/String;

.field private mEditBTNameDialog:Landroid/widget/PopupWindow;

.field private mEditBTPinDialog:Landroid/widget/PopupWindow;

.field private mEditDeviceName:Landroid/widget/EditText;

.field private mEditPin:Landroid/widget/EditText;

.field private mIsAutoAnswer:Z

.field private mIsAutoConnect:Z

.field private mLayoutNetwork:Landroid/view/ViewGroup;

.field private mLayoutSetting:Landroid/view/ViewGroup;

.field private mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

.field private mNetWorkButton:Landroid/widget/Button;

.field public mPinCode:Ljava/lang/String;

.field private mPowerSwitchButton:Landroid/widget/Button;

.field private mReceiver:Landroid/content/BroadcastReceiver;

.field private mTipsButton:Landroid/widget/Button;

.field private mTipsCloseButton:Landroid/widget/Button;

.field private mViewRoot:Landroid/view/View;


# direct methods
.method public constructor <init>()V
    .locals 2

    .line 42
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;-><init>()V

    const/4 v0, 0x0

    .line 44
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 50
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoConnectButton:Landroid/widget/Button;

    .line 51
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoAnswerButton:Landroid/widget/Button;

    .line 52
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPowerSwitchButton:Landroid/widget/Button;

    .line 53
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mTipsButton:Landroid/widget/Button;

    .line 54
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mNetWorkButton:Landroid/widget/Button;

    .line 55
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mTipsCloseButton:Landroid/widget/Button;

    .line 58
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->layoutPinCode:Landroid/widget/LinearLayout;

    .line 60
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    .line 61
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditDeviceName:Landroid/widget/EditText;

    .line 63
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    .line 64
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditPin:Landroid/widget/EditText;

    const-string v1, "0000"

    .line 68
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPinCode:Ljava/lang/String;

    const-string v1, "CarBT"

    .line 69
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mDevName:Ljava/lang/String;

    const/4 v1, 0x0

    .line 75
    iput-boolean v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mIsAutoAnswer:Z

    .line 80
    iput-boolean v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mIsAutoConnect:Z

    .line 85
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    .line 564
    new-instance v0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$4;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$4;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mReceiver:Landroid/content/BroadcastReceiver;

    return-void
.end method

.method static synthetic access$000(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 0

    .line 42
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p0
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Lcom/autochips/bluetooth/IFragmentCallback;
    .locals 0

    .line 42
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    return-object p0
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)Landroid/widget/Button;
    .locals 0

    .line 42
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPowerSwitchButton:Landroid/widget/Button;

    return-object p0
.end method

.method private checkBluetoothState()V
    .locals 4

    .line 327
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    const-string v0, "SetupFragmentEx"

    const-string v1, "checkBluetoothState: mLocalAdapterManager is null"

    .line 328
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 332
    :cond_0
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothEnable()Z

    move-result v0

    const v1, 0x7f07011e

    const v2, 0x7f07011d

    if-eqz v0, :cond_1

    .line 333
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPowerSwitchButton:Landroid/widget/Button;

    if-eqz v0, :cond_2

    const/4 v3, 0x1

    .line 334
    invoke-virtual {v0, v3}, Landroid/widget/Button;->setEnabled(Z)V

    .line 335
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPowerSwitchButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 338
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPowerSwitchButton:Landroid/widget/Button;

    if-eqz v0, :cond_2

    .line 339
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 343
    :cond_2
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoAnswer()Z

    move-result v0

    if-eqz v0, :cond_3

    .line 344
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoAnswerButton:Landroid/widget/Button;

    if-eqz v0, :cond_4

    .line 345
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    .line 348
    :cond_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoAnswerButton:Landroid/widget/Button;

    if-eqz v0, :cond_4

    .line 349
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 353
    :cond_4
    :goto_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoConnect()Z

    move-result v0

    if-eqz v0, :cond_5

    .line 354
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoConnectButton:Landroid/widget/Button;

    if-eqz v0, :cond_6

    .line 355
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    .line 358
    :cond_5
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoConnectButton:Landroid/widget/Button;

    if-eqz v0, :cond_6

    .line 359
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 363
    :cond_6
    :goto_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isAutoConnectedNetwork()Z

    move-result v0

    if-eqz v0, :cond_7

    .line 364
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mNetWorkButton:Landroid/widget/Button;

    if-eqz v0, :cond_8

    .line 365
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_3

    .line 368
    :cond_7
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mNetWorkButton:Landroid/widget/Button;

    if-eqz v0, :cond_8

    .line 369
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :cond_8
    :goto_3
    return-void
.end method

.method private confirmModifyBTName()V
    .locals 3

    .line 460
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditDeviceName:Landroid/widget/EditText;

    if-eqz v0, :cond_4

    .line 461
    invoke-virtual {v0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 462
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    const/16 v2, 0x14

    if-le v1, v2, :cond_0

    const/4 v1, 0x0

    const/16 v2, 0x13

    .line 463
    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v0

    .line 466
    :cond_0
    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_3

    .line 468
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v1, :cond_1

    .line 469
    invoke-virtual {v1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBTName(Ljava/lang/String;)V

    .line 471
    :cond_1
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v2, 0x7f08016b

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    if-eqz v1, :cond_2

    .line 473
    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 475
    :cond_2
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->hideEditNameDialog()V

    goto :goto_0

    .line 477
    :cond_3
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f0d002e

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    :cond_4
    :goto_0
    return-void
.end method

.method private confirmModifyPinCode()V
    .locals 3

    .line 544
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditPin:Landroid/widget/EditText;

    if-eqz v0, :cond_2

    .line 545
    invoke-virtual {v0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 547
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    const/4 v2, 0x4

    if-lt v1, v2, :cond_1

    .line 548
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    const/16 v2, 0x10

    if-gt v1, v2, :cond_1

    .line 549
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v2, 0x7f08016c

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    if-eqz v1, :cond_0

    .line 551
    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 553
    :cond_0
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPinCode:Ljava/lang/String;

    .line 554
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->hideEditPinDialog()V

    .line 555
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_2

    .line 556
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPinCode:Ljava/lang/String;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBTPincode(Ljava/lang/String;)V

    goto :goto_0

    .line 559
    :cond_1
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->getContext()Landroid/content/Context;

    move-result-object v0

    const v1, 0x7f0d002e

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    :cond_2
    :goto_0
    return-void
.end method

.method private hideEditNameDialog()V
    .locals 2

    .line 448
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    if-eqz v0, :cond_0

    .line 449
    invoke-virtual {v0}, Landroid/widget/PopupWindow;->dismiss()V

    .line 451
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    if-eqz v0, :cond_1

    const/4 v1, 0x0

    .line 452
    invoke-interface {v0, v1}, Lcom/autochips/bluetooth/IFragmentCallback;->updateBackground(Z)Z

    :cond_1
    return-void
.end method

.method private hideEditPinDialog()V
    .locals 2

    .line 532
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    if-eqz v0, :cond_0

    .line 533
    invoke-virtual {v0}, Landroid/widget/PopupWindow;->dismiss()V

    .line 535
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    if-eqz v0, :cond_1

    const/4 v1, 0x0

    .line 536
    invoke-interface {v0, v1}, Lcom/autochips/bluetooth/IFragmentCallback;->updateBackground(Z)Z

    :cond_1
    return-void
.end method

.method private initView()V
    .locals 3

    .line 113
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_0

    .line 114
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mDevName:Ljava/lang/String;

    .line 117
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_1

    .line 118
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTPincode()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPinCode:Ljava/lang/String;

    .line 121
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08016b

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    if-eqz v0, :cond_2

    .line 123
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mDevName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 125
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08016c

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    if-eqz v0, :cond_3

    .line 127
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPinCode:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 129
    :cond_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08007c

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    if-eqz v0, :cond_4

    .line 131
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 133
    :cond_4
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08007d

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    if-eqz v0, :cond_5

    .line 135
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 138
    :cond_5
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080139

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoConnectButton:Landroid/widget/Button;

    if-eqz v0, :cond_6

    .line 140
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 143
    :cond_6
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080138

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoAnswerButton:Landroid/widget/Button;

    if-eqz v0, :cond_7

    .line 145
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 148
    :cond_7
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f0800f5

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mNetWorkButton:Landroid/widget/Button;

    if-eqz v0, :cond_8

    .line 150
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 153
    :cond_8
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f0800f7

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mTipsButton:Landroid/widget/Button;

    if-eqz v0, :cond_9

    .line 155
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 158
    :cond_9
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08015d

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mTipsCloseButton:Landroid/widget/Button;

    if-eqz v0, :cond_a

    .line 160
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 163
    :cond_a
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f0800f6

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLayoutNetwork:Landroid/view/ViewGroup;

    .line 164
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08012a

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLayoutSetting:Landroid/view/ViewGroup;

    .line 166
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08013a

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPowerSwitchButton:Landroid/widget/Button;

    .line 167
    new-instance v1, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$1;

    invoke-direct {v1, p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$1;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)V

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const-string v0, "persist.sys.bt.disablessp"

    const-string v1, "false"

    .line 182
    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 183
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const v2, 0x7f0800e6

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/LinearLayout;

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->layoutPinCode:Landroid/widget/LinearLayout;

    if-eqz v1, :cond_b

    const-string v1, "true"

    .line 184
    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_b

    .line 185
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->layoutPinCode:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    :cond_b
    return-void
.end method

.method private onActionTipsFinished()V
    .locals 2

    const-string v0, "SetupFragmentEx"

    const-string v1, "onActionTipsFinished"

    .line 386
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 388
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLayoutNetwork:Landroid/view/ViewGroup;

    if-eqz v0, :cond_0

    const/16 v1, 0x8

    .line 389
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 391
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLayoutSetting:Landroid/view/ViewGroup;

    if-eqz v0, :cond_1

    const/4 v1, 0x0

    .line 392
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    :cond_1
    return-void
.end method

.method private onActionTipsStarted()V
    .locals 2

    const-string v0, "SetupFragmentEx"

    const-string v1, "onActionTipsStarted"

    .line 375
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 377
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLayoutNetwork:Landroid/view/ViewGroup;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 378
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 380
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLayoutSetting:Landroid/view/ViewGroup;

    if-eqz v0, :cond_1

    const/4 v1, 0x4

    .line 381
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    :cond_1
    return-void
.end method

.method private registerReceiver()V
    .locals 3

    .line 104
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    const-string v1, "android.bluetooth.adapter.action.STATE_CHANGED"

    .line 105
    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 106
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-static {v1}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/Context;

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    return-void
.end method

.method private showEditNameDialog()V
    .locals 4

    .line 400
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mContext:Landroid/content/Context;

    if-nez v0, :cond_0

    const-string v0, "SetupFragmentEx"

    const-string v1, "showEditNameDialog: mContext is null"

    .line 401
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 404
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    const/4 v1, 0x1

    if-nez v0, :cond_2

    const v0, 0x7f0b0023

    .line 405
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getLayout(I)Landroid/view/View;

    move-result-object v0

    .line 406
    new-instance v2, Landroid/widget/PopupWindow;

    const/4 v3, -0x2

    invoke-direct {v2, v0, v3, v3}, Landroid/widget/PopupWindow;-><init>(Landroid/view/View;II)V

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    .line 408
    invoke-virtual {v2, v1}, Landroid/widget/PopupWindow;->setSoftInputMode(I)V

    .line 409
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    const/16 v3, 0x10

    invoke-virtual {v2, v3}, Landroid/widget/PopupWindow;->setSoftInputMode(I)V

    .line 410
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    invoke-virtual {v2, v1}, Landroid/widget/PopupWindow;->setOutsideTouchable(Z)V

    .line 411
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    invoke-virtual {v2, v1}, Landroid/widget/PopupWindow;->setFocusable(Z)V

    .line 412
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    const v3, 0x7f0e00ac

    invoke-virtual {v2, v3}, Landroid/widget/PopupWindow;->setAnimationStyle(I)V

    .line 413
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    new-instance v3, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$2;

    invoke-direct {v3, p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$2;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)V

    invoke-virtual {v2, v3}, Landroid/widget/PopupWindow;->setOnDismissListener(Landroid/widget/PopupWindow$OnDismissListener;)V

    const v2, 0x7f0800b3

    .line 422
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/EditText;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditDeviceName:Landroid/widget/EditText;

    const v2, 0x7f080052

    .line 423
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    if-eqz v2, :cond_1

    .line 425
    invoke-virtual {v2, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_1
    const v2, 0x7f080051

    .line 427
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    if-eqz v0, :cond_2

    .line 429
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 433
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_3

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditDeviceName:Landroid/widget/EditText;

    if-eqz v2, :cond_3

    .line 434
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mDevName:Ljava/lang/String;

    .line 435
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditDeviceName:Landroid/widget/EditText;

    invoke-virtual {v2, v0}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 438
    :cond_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    if-eqz v0, :cond_4

    .line 439
    invoke-interface {v0, v1}, Lcom/autochips/bluetooth/IFragmentCallback;->updateBackground(Z)Z

    .line 441
    :cond_4
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTNameDialog:Landroid/widget/PopupWindow;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const/16 v2, 0x11

    const/4 v3, 0x0

    invoke-virtual {v0, v1, v2, v3, v3}, Landroid/widget/PopupWindow;->showAtLocation(Landroid/view/View;III)V

    return-void
.end method

.method private showEditPinDialog()V
    .locals 4

    .line 483
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mContext:Landroid/content/Context;

    if-nez v0, :cond_0

    const-string v0, "SetupFragmentEx"

    const-string v1, "buildEditPinDialog: mContext is null"

    .line 484
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void

    .line 487
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    const/4 v1, 0x1

    if-nez v0, :cond_2

    const v0, 0x7f0b0039

    .line 488
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getLayout(I)Landroid/view/View;

    move-result-object v0

    .line 489
    new-instance v2, Landroid/widget/PopupWindow;

    const/4 v3, -0x2

    invoke-direct {v2, v0, v3, v3}, Landroid/widget/PopupWindow;-><init>(Landroid/view/View;II)V

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    .line 491
    invoke-virtual {v2, v1}, Landroid/widget/PopupWindow;->setSoftInputMode(I)V

    .line 492
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    const/16 v3, 0x10

    invoke-virtual {v2, v3}, Landroid/widget/PopupWindow;->setSoftInputMode(I)V

    .line 493
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    invoke-virtual {v2, v1}, Landroid/widget/PopupWindow;->setOutsideTouchable(Z)V

    .line 494
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    invoke-virtual {v2, v1}, Landroid/widget/PopupWindow;->setFocusable(Z)V

    .line 495
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    const v3, 0x7f0e00ac

    invoke-virtual {v2, v3}, Landroid/widget/PopupWindow;->setAnimationStyle(I)V

    .line 496
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    new-instance v3, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$3;

    invoke-direct {v3, p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx$3;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragmentEx;)V

    invoke-virtual {v2, v3}, Landroid/widget/PopupWindow;->setOnDismissListener(Landroid/widget/PopupWindow$OnDismissListener;)V

    const v2, 0x7f0800b4

    .line 505
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/EditText;

    iput-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditPin:Landroid/widget/EditText;

    const v2, 0x7f08010c

    .line 506
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    if-eqz v2, :cond_1

    .line 508
    invoke-virtual {v2, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    :cond_1
    const v2, 0x7f08010b

    .line 510
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    if-eqz v0, :cond_2

    .line 512
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 516
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_3

    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditPin:Landroid/widget/EditText;

    if-eqz v2, :cond_3

    .line 517
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTPincode()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mPinCode:Ljava/lang/String;

    .line 518
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditPin:Landroid/widget/EditText;

    invoke-virtual {v2, v0}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 521
    :cond_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    if-eqz v0, :cond_4

    .line 522
    invoke-interface {v0, v1}, Lcom/autochips/bluetooth/IFragmentCallback;->updateBackground(Z)Z

    .line 525
    :cond_4
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mEditBTPinDialog:Landroid/widget/PopupWindow;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    const/16 v2, 0x11

    const/4 v3, 0x0

    invoke-virtual {v0, v1, v2, v3, v3}, Landroid/widget/PopupWindow;->showAtLocation(Landroid/view/View;III)V

    return-void
.end method


# virtual methods
.method public getLayoutRes()I
    .locals 1

    const v0, 0x7f0b002c

    return v0
.end method

.method public onAttach(Landroid/content/Context;)V
    .locals 0

    .line 192
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onAttach(Landroid/content/Context;)V

    return-void
.end method

.method public onBindViewData()V
    .locals 0

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 3

    .line 240
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothEnable()Z

    move-result v0

    if-nez v0, :cond_0

    .line 241
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->getContext()Landroid/content/Context;

    move-result-object p1

    const v0, 0x7f0d0034

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    .line 247
    :cond_0
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result p1

    const v0, 0x7f07011e

    const v1, 0x7f07011d

    sparse-switch p1, :sswitch_data_0

    goto/16 :goto_2

    .line 290
    :sswitch_0
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->onActionTipsFinished()V

    goto/16 :goto_2

    .line 256
    :sswitch_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoConnect()Z

    move-result p1

    xor-int/lit8 p1, p1, 0x1

    iput-boolean p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mIsAutoConnect:Z

    if-eqz p1, :cond_1

    .line 258
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoConnectButton:Landroid/widget/Button;

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 260
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoConnectButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 262
    :goto_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-boolean v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mIsAutoConnect:Z

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBluetoothAutoConnect(Z)V

    goto/16 :goto_2

    .line 266
    :sswitch_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoAnswer()Z

    move-result p1

    xor-int/lit8 p1, p1, 0x1

    iput-boolean p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mIsAutoAnswer:Z

    if-eqz p1, :cond_2

    .line 268
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoAnswerButton:Landroid/widget/Button;

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    .line 270
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mAutoAnswerButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 272
    :goto_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-boolean v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mIsAutoAnswer:Z

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBluetoothAutoAnswer(Z)V

    goto :goto_2

    .line 299
    :sswitch_3
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->confirmModifyPinCode()V

    goto :goto_2

    .line 302
    :sswitch_4
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->hideEditPinDialog()V

    goto :goto_2

    .line 276
    :sswitch_5
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->onActionTipsStarted()V

    goto :goto_2

    :sswitch_6
    const-string p1, "SetupFragmentEx"

    const-string v2, "network click"

    .line 279
    invoke-static {p1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 280
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isAutoConnectedNetwork()Z

    move-result p1

    xor-int/lit8 p1, p1, 0x1

    .line 281
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v2, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setAutoConnectedNetwork(Z)V

    if-eqz p1, :cond_3

    .line 284
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mNetWorkButton:Landroid/widget/Button;

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    .line 286
    :cond_3
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mNetWorkButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    .line 253
    :sswitch_7
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->showEditPinDialog()V

    goto :goto_2

    .line 250
    :sswitch_8
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->showEditNameDialog()V

    goto :goto_2

    .line 293
    :sswitch_9
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->confirmModifyBTName()V

    goto :goto_2

    .line 296
    :sswitch_a
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->hideEditNameDialog()V

    :goto_2
    return-void

    :sswitch_data_0
    .sparse-switch
        0x7f080051 -> :sswitch_a
        0x7f080052 -> :sswitch_9
        0x7f08007c -> :sswitch_8
        0x7f08007d -> :sswitch_7
        0x7f0800f5 -> :sswitch_6
        0x7f0800f7 -> :sswitch_5
        0x7f08010b -> :sswitch_4
        0x7f08010c -> :sswitch_3
        0x7f080138 -> :sswitch_2
        0x7f080139 -> :sswitch_1
        0x7f08015d -> :sswitch_0
    .end sparse-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 0

    .line 89
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreate(Landroid/os/Bundle;)V

    .line 90
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->getAdapterManager()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 0

    .line 96
    invoke-super {p0, p1, p2, p3}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    .line 97
    invoke-static {}, Lcom/autochips/bluetooth/skin/SkinUtils;->getContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mContext:Landroid/content/Context;

    .line 98
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->initView()V

    .line 99
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->registerReceiver()V

    .line 100
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mViewRoot:Landroid/view/View;

    return-object p1
.end method

.method public onDestroy()V
    .locals 0

    .line 229
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroy()V

    return-void
.end method

.method public onDestroyView()V
    .locals 2

    .line 223
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroyView()V

    .line 224
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    return-void
.end method

.method public onDetach()V
    .locals 0

    .line 234
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDetach()V

    return-void
.end method

.method public onHiddenChanged(Z)V
    .locals 0

    .line 218
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onHiddenChanged(Z)V

    return-void
.end method

.method public onPause()V
    .locals 0

    .line 208
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onPause()V

    return-void
.end method

.method public onResume()V
    .locals 0

    .line 202
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onResume()V

    .line 203
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->checkBluetoothState()V

    return-void
.end method

.method public onStart()V
    .locals 0

    .line 197
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStart()V

    return-void
.end method

.method public onStop()V
    .locals 0

    .line 213
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStop()V

    return-void
.end method

.method public setFragmentCallback(Lcom/autochips/bluetooth/IFragmentCallback;)V
    .locals 0

    .line 320
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragmentEx;->fragmentCallback:Lcom/autochips/bluetooth/IFragmentCallback;

    return-void
.end method
