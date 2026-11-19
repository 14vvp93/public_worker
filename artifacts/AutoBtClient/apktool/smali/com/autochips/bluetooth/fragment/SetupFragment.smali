.class public Lcom/autochips/bluetooth/fragment/SetupFragment;
.super Lcom/hcn/skin/support/app/SkinCompatFragment;
.source "SetupFragment.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# static fields
.field private static final MAX_PIN_CODE_LENGTH:I = 0x10

.field private static final MIN_PIN_CODE_LENGTH:I = 0x4

.field public static final TAG:Ljava/lang/String; = "SetupFragment"


# instance fields
.field private layout_pin:Landroid/widget/LinearLayout;

.field private mA2dpSourceSwitch:Landroid/view/View;

.field private mAutoAnswerButton:Landroid/widget/Button;

.field private mAutoConnectButton:Landroid/widget/Button;

.field mConnectionListener:Lcom/hcn/bluetooth/api/ConnectionListener;

.field public mDevName:Ljava/lang/String;

.field private mEditBTNameDialog:Landroid/app/Dialog;

.field private mEditBTPinDialog:Landroid/app/Dialog;

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

    .line 45
    invoke-direct {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;-><init>()V

    const/4 v0, 0x0

    .line 51
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    .line 52
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    .line 53
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    .line 54
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditPin:Landroid/widget/EditText;

    .line 55
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->layout_pin:Landroid/widget/LinearLayout;

    .line 57
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoConnectButton:Landroid/widget/Button;

    .line 58
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoAnswerButton:Landroid/widget/Button;

    .line 59
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPowerSwitchButton:Landroid/widget/Button;

    .line 60
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mTipsButton:Landroid/widget/Button;

    .line 61
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mNetWorkButton:Landroid/widget/Button;

    .line 62
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mTipsCloseButton:Landroid/widget/Button;

    .line 66
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mA2dpSourceSwitch:Landroid/view/View;

    const-string v1, "0000"

    .line 68
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPinCode:Ljava/lang/String;

    const-string v1, "CarBT"

    .line 69
    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mDevName:Ljava/lang/String;

    const/4 v1, 0x0

    .line 70
    iput-boolean v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mIsAutoAnswer:Z

    .line 71
    iput-boolean v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mIsAutoConnect:Z

    .line 73
    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 584
    new-instance v0, Lcom/autochips/bluetooth/fragment/SetupFragment$6;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$6;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    .line 602
    new-instance v0, Lcom/autochips/bluetooth/fragment/SetupFragment$7;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$7;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mConnectionListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    return-void
.end method

.method static synthetic access$000(Lcom/autochips/bluetooth/fragment/SetupFragment;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 0

    .line 45
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p0
.end method

.method static synthetic access$100(Lcom/autochips/bluetooth/fragment/SetupFragment;)Landroid/widget/EditText;
    .locals 0

    .line 45
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    return-object p0
.end method

.method static synthetic access$200(Lcom/autochips/bluetooth/fragment/SetupFragment;)Landroid/view/View;
    .locals 0

    .line 45
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    return-object p0
.end method

.method static synthetic access$300(Lcom/autochips/bluetooth/fragment/SetupFragment;)Landroid/widget/EditText;
    .locals 0

    .line 45
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditPin:Landroid/widget/EditText;

    return-object p0
.end method

.method static synthetic access$400(Lcom/autochips/bluetooth/fragment/SetupFragment;)Landroid/app/Dialog;
    .locals 0

    .line 45
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    return-object p0
.end method

.method static synthetic access$500(Lcom/autochips/bluetooth/fragment/SetupFragment;)Landroid/widget/Button;
    .locals 0

    .line 45
    iget-object p0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPowerSwitchButton:Landroid/widget/Button;

    return-object p0
.end method

.method static synthetic access$600(Lcom/autochips/bluetooth/fragment/SetupFragment;)V
    .locals 0

    .line 45
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->checkBluetoothState()V

    return-void
.end method

.method private buildEditNameDialog()V
    .locals 9

    .line 407
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    .line 410
    :cond_0
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    .line 411
    iget v1, v0, Landroid/util/DisplayMetrics;->widthPixels:I

    .line 412
    iget v0, v0, Landroid/util/DisplayMetrics;->heightPixels:I

    .line 413
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "buildEditNameDialog: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v3, "SetupFragment"

    invoke-static {v3, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 415
    invoke-static {}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getEThemeGod()Ljava/lang/String;

    move-result-object v2

    const-string v3, "400"

    invoke-virtual {v3, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    const v4, 0x7f0d008b

    const/4 v5, 0x0

    const v6, 0x7f0d008a

    if-eqz v2, :cond_1

    invoke-static {}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getEThemeSub()Ljava/lang/String;

    move-result-object v2

    const-string v7, "034"

    invoke-virtual {v7, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_2

    :cond_1
    invoke-static {}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getEThemeGod()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_5

    const/16 v2, 0x500

    if-ne v1, v2, :cond_5

    const/16 v1, 0x1e0

    if-ne v0, v1, :cond_5

    .line 416
    :cond_2
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    const v1, 0x7f0b0023

    invoke-virtual {v0, v1, v5}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    const v1, 0x7f0800b3

    .line 417
    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/EditText;

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    const v1, 0x7f080052

    .line 418
    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/Button;

    const v2, 0x7f080051

    .line 419
    invoke-virtual {v0, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    if-eqz v2, :cond_3

    .line 421
    invoke-static {v6}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    :cond_3
    if-eqz v1, :cond_4

    .line 425
    invoke-static {v4}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Landroid/widget/Button;->setText(Ljava/lang/CharSequence;)V

    .line 427
    :cond_4
    new-instance v3, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v4

    invoke-direct {v3, v4}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 428
    invoke-virtual {v3, v0}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    .line 429
    invoke-virtual {v3}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    .line 430
    invoke-virtual {v0}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    .line 431
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    new-instance v4, Lcom/autochips/bluetooth/fragment/SetupFragment$$ExternalSyntheticLambda2;

    invoke-direct {v4, v0}, Lcom/autochips/bluetooth/fragment/SetupFragment$$ExternalSyntheticLambda2;-><init>(Landroid/view/Window;)V

    invoke-virtual {v3, v4}, Landroid/widget/EditText;->setOnFocusChangeListener(Landroid/view/View$OnFocusChangeListener;)V

    .line 444
    new-instance v0, Lcom/autochips/bluetooth/fragment/SetupFragment$$ExternalSyntheticLambda0;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$$ExternalSyntheticLambda0;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    invoke-virtual {v1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 463
    new-instance v0, Lcom/autochips/bluetooth/fragment/SetupFragment$$ExternalSyntheticLambda1;

    invoke-direct {v0, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$$ExternalSyntheticLambda1;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    invoke-virtual {v2, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto/16 :goto_0

    .line 465
    :cond_5
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x10302d2

    invoke-direct {v0, v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    const v1, 0x7f0d003b

    .line 467
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    const v1, 0x7f0d003d

    .line 468
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 469
    new-instance v1, Landroid/widget/EditText;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    const/4 v2, 0x1

    .line 470
    invoke-virtual {v1, v2}, Landroid/widget/EditText;->setSingleLine(Z)V

    .line 471
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    new-array v3, v2, [Landroid/text/InputFilter;

    new-instance v7, Landroid/text/InputFilter$LengthFilter;

    const/16 v8, 0xf8

    invoke-direct {v7, v8}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    const/4 v8, 0x0

    aput-object v7, v3, v8

    invoke-virtual {v1, v3}, Landroid/widget/EditText;->setFilters([Landroid/text/InputFilter;)V

    .line 472
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    const/high16 v3, -0x1000000

    invoke-virtual {v1, v3}, Landroid/widget/EditText;->setTextColor(I)V

    .line 474
    new-instance v1, Landroid/widget/LinearLayout;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v1, v3}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 475
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 476
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v3, -0x1

    const/4 v7, -0x2

    invoke-direct {v2, v3, v7}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    const/16 v3, 0x18

    .line 478
    invoke-virtual {v2, v3, v8, v3, v8}, Landroid/widget/LinearLayout$LayoutParams;->setMargins(IIII)V

    .line 479
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    invoke-virtual {v1, v3, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 481
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    .line 482
    invoke-static {v6}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1, v5}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 483
    invoke-static {v4}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    new-instance v2, Lcom/autochips/bluetooth/fragment/SetupFragment$2;

    invoke-direct {v2, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$2;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 506
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    :goto_0
    return-void
.end method

.method private buildEditPinDialog()V
    .locals 6

    .line 511
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    .line 514
    :cond_0
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    const v2, 0x10302d2

    invoke-direct {v0, v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;I)V

    const v1, 0x7f0d003b

    .line 516
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    const v1, 0x7f0d003f

    .line 517
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 518
    new-instance v1, Landroid/widget/EditText;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditPin:Landroid/widget/EditText;

    const/4 v2, 0x1

    .line 519
    invoke-virtual {v1, v2}, Landroid/widget/EditText;->setSingleLine(Z)V

    .line 520
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditPin:Landroid/widget/EditText;

    new-array v3, v2, [Landroid/text/InputFilter;

    new-instance v4, Landroid/text/InputFilter$LengthFilter;

    const/16 v5, 0x10

    invoke-direct {v4, v5}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v1, v3}, Landroid/widget/EditText;->setFilters([Landroid/text/InputFilter;)V

    .line 522
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditPin:Landroid/widget/EditText;

    const/4 v3, 0x2

    invoke-virtual {v1, v3}, Landroid/widget/EditText;->setInputType(I)V

    .line 525
    new-instance v1, Landroid/widget/LinearLayout;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v1, v3}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 526
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 527
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v3, -0x1

    const/4 v4, -0x2

    invoke-direct {v2, v3, v4}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    const/16 v3, 0x18

    .line 529
    invoke-virtual {v2, v3, v5, v3, v5}, Landroid/widget/LinearLayout$LayoutParams;->setMargins(IIII)V

    .line 530
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditPin:Landroid/widget/EditText;

    invoke-virtual {v1, v3, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 532
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    const/high16 v1, 0x1040000

    const/4 v2, 0x0

    .line 533
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    const v1, 0x104000a

    .line 534
    new-instance v2, Lcom/autochips/bluetooth/fragment/SetupFragment$3;

    invoke-direct {v2, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$3;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 555
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    return-void
.end method

.method private checkBluetoothState()V
    .locals 4

    .line 362
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothEnable()Z

    move-result v0

    const v1, 0x7f07011e

    const v2, 0x7f07011d

    if-eqz v0, :cond_0

    .line 363
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPowerSwitchButton:Landroid/widget/Button;

    const/4 v3, 0x1

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setEnabled(Z)V

    .line 364
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPowerSwitchButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 366
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPowerSwitchButton:Landroid/widget/Button;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 369
    :goto_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoAnswer()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 370
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoAnswerButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    .line 372
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoAnswerButton:Landroid/widget/Button;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 375
    :goto_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoConnect()Z

    move-result v0

    if-eqz v0, :cond_2

    .line 376
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoConnectButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    .line 378
    :cond_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoConnectButton:Landroid/widget/Button;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 381
    :goto_2
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isAutoConnectedNetwork()Z

    move-result v0

    if-eqz v0, :cond_3

    .line 382
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mNetWorkButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_3

    .line 384
    :cond_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mNetWorkButton:Landroid/widget/Button;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 387
    :goto_3
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object v0

    invoke-virtual {v0}, Lcom/autochips/bluetooth/MyApplication;->isA2dpSourceRole()Z

    move-result v0

    if-eqz v0, :cond_4

    .line 388
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mA2dpSourceSwitch:Landroid/view/View;

    if-eqz v0, :cond_5

    .line 389
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_4

    .line 392
    :cond_4
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mA2dpSourceSwitch:Landroid/view/View;

    if-eqz v0, :cond_5

    .line 393
    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    :cond_5
    :goto_4
    return-void
.end method

.method public static getEThemeGod()Ljava/lang/String;
    .locals 2

    const-string v0, "persist.sys.etheme_god"

    const-string v1, ""

    .line 399
    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getEThemeSub()Ljava/lang/String;
    .locals 2

    const-string v0, "persist.sys.etheme_sub"

    const-string v1, ""

    .line 403
    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method private initView()V
    .locals 6

    .line 122
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08016b

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 123
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v2, 0x7f08016c

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 124
    iget-object v2, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v3, 0x7f08007c

    invoke-static {v3}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/Button;

    .line 125
    iget-object v3, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v4, 0x7f08007d

    invoke-static {v4}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v4

    invoke-virtual {v3, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/Button;

    .line 126
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v5, 0x7f080139

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoConnectButton:Landroid/widget/Button;

    .line 127
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v5, 0x7f080138

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoAnswerButton:Landroid/widget/Button;

    .line 128
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v5, 0x7f08013a

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPowerSwitchButton:Landroid/widget/Button;

    .line 129
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v5, 0x7f0800f5

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mNetWorkButton:Landroid/widget/Button;

    .line 130
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v5, 0x7f0800f7

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mTipsButton:Landroid/widget/Button;

    .line 131
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v5, 0x7f08015d

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mTipsCloseButton:Landroid/widget/Button;

    .line 133
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v5, 0x7f0800f6

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/view/ViewGroup;

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLayoutNetwork:Landroid/view/ViewGroup;

    .line 134
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v5, 0x7f08012a

    invoke-static {v5}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v5

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/view/ViewGroup;

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLayoutSetting:Landroid/view/ViewGroup;

    if-eqz v0, :cond_7

    if-eqz v1, :cond_7

    if-eqz v2, :cond_7

    if-eqz v3, :cond_7

    .line 136
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoConnectButton:Landroid/widget/Button;

    if-eqz v4, :cond_7

    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoAnswerButton:Landroid/widget/Button;

    if-eqz v4, :cond_7

    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPowerSwitchButton:Landroid/widget/Button;

    if-nez v4, :cond_0

    goto/16 :goto_0

    .line 144
    :cond_0
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mNetWorkButton:Landroid/widget/Button;

    invoke-virtual {v4, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 145
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mTipsButton:Landroid/widget/Button;

    invoke-virtual {v4, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 146
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mTipsCloseButton:Landroid/widget/Button;

    invoke-virtual {v4, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 148
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPowerSwitchButton:Landroid/widget/Button;

    new-instance v5, Lcom/autochips/bluetooth/fragment/SetupFragment$1;

    invoke-direct {v5, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$1;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    invoke-virtual {v4, v5}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 161
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isReady()Z

    move-result v4

    if-nez v4, :cond_1

    .line 162
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v4, :cond_1

    .line 163
    iget-object v5, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mConnectionListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {v4, v5}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    .line 166
    :cond_1
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v4, :cond_2

    .line 167
    invoke-virtual {v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTName()Ljava/lang/String;

    move-result-object v4

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mDevName:Ljava/lang/String;

    .line 170
    :cond_2
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v4, :cond_3

    .line 171
    invoke-virtual {v4}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTPincode()Ljava/lang/String;

    move-result-object v4

    iput-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPinCode:Ljava/lang/String;

    .line 174
    :cond_3
    iget-object v4, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mDevName:Ljava/lang/String;

    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 175
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPinCode:Ljava/lang/String;

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 176
    invoke-virtual {v2, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 177
    invoke-virtual {v3, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 178
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoConnectButton:Landroid/widget/Button;

    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 179
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoAnswerButton:Landroid/widget/Button;

    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 181
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f0800e6

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->layout_pin:Landroid/widget/LinearLayout;

    const-string v0, "persist.sys.bt.disablessp"

    const-string v1, "false"

    .line 183
    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 184
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->layout_pin:Landroid/widget/LinearLayout;

    const/4 v2, 0x0

    if-eqz v1, :cond_4

    const-string v1, "true"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    .line 185
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->layout_pin:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 188
    :cond_4
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->isHeadrest()Z

    move-result v0

    if-eqz v0, :cond_6

    .line 189
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f0800e4

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    if-eqz v0, :cond_5

    .line 191
    invoke-virtual {v0, v2}, Landroid/view/View;->setVisibility(I)V

    .line 193
    :cond_5
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f080137

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mA2dpSourceSwitch:Landroid/view/View;

    if-eqz v0, :cond_6

    .line 195
    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 199
    :cond_6
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->buildEditNameDialog()V

    .line 200
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->buildEditPinDialog()V

    return-void

    :cond_7
    :goto_0
    const-string v0, "SetupFragment"

    const-string v1, "Widget missing!"

    .line 140
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic lambda$buildEditNameDialog$0(Landroid/view/Window;Landroid/view/View;Z)V
    .locals 0

    if-eqz p2, :cond_0

    .line 433
    invoke-virtual {p0}, Landroid/view/Window;->getAttributes()Landroid/view/WindowManager$LayoutParams;

    move-result-object p1

    const/16 p2, 0x30

    .line 434
    iput p2, p1, Landroid/view/WindowManager$LayoutParams;->gravity:I

    const/16 p2, 0x14

    .line 435
    iput p2, p1, Landroid/view/WindowManager$LayoutParams;->y:I

    const/4 p2, 0x0

    .line 436
    iput p2, p1, Landroid/view/WindowManager$LayoutParams;->verticalMargin:F

    .line 437
    invoke-virtual {p0, p1}, Landroid/view/Window;->setAttributes(Landroid/view/WindowManager$LayoutParams;)V

    const/16 p1, 0x35

    .line 438
    invoke-virtual {p0, p1}, Landroid/view/Window;->setSoftInputMode(I)V

    :cond_0
    return-void
.end method

.method private onActionTipsFinished()V
    .locals 2

    const-string v0, "SetupFragment"

    const-string v1, "onActionTipsFinished"

    .line 350
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 352
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLayoutNetwork:Landroid/view/ViewGroup;

    if-eqz v0, :cond_0

    const/16 v1, 0x8

    .line 353
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 355
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLayoutSetting:Landroid/view/ViewGroup;

    if-eqz v0, :cond_1

    const/4 v1, 0x0

    .line 356
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    :cond_1
    return-void
.end method

.method private onActionTipsStarted()V
    .locals 2

    const-string v0, "SetupFragment"

    const-string v1, "onActionTipsStarted"

    .line 339
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 341
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLayoutNetwork:Landroid/view/ViewGroup;

    if-eqz v0, :cond_0

    const/4 v1, 0x0

    .line 342
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    .line 344
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLayoutSetting:Landroid/view/ViewGroup;

    if-eqz v0, :cond_1

    const/4 v1, 0x4

    .line 345
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->setVisibility(I)V

    :cond_1
    return-void
.end method

.method private showRebootDialog()V
    .locals 3

    .line 559
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    if-nez v0, :cond_0

    return-void

    .line 562
    :cond_0
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const v1, 0x1080027

    .line 563
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setIcon(I)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const v1, 0x7f0d0055

    .line 564
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const v1, 0x7f0d0057

    .line 565
    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const/4 v1, 0x0

    .line 566
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const/high16 v1, 0x1040000

    new-instance v2, Lcom/autochips/bluetooth/fragment/SetupFragment$5;

    invoke-direct {v2, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$5;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    .line 567
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const v1, 0x104000a

    new-instance v2, Lcom/autochips/bluetooth/fragment/SetupFragment$4;

    invoke-direct {v2, p0}, Lcom/autochips/bluetooth/fragment/SetupFragment$4;-><init>(Lcom/autochips/bluetooth/fragment/SetupFragment;)V

    .line 573
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setPositiveButton(ILandroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 581
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    return-void
.end method


# virtual methods
.method public getLayoutRes()I
    .locals 1

    const v0, 0x7f0b002c

    return v0
.end method

.method public synthetic lambda$buildEditNameDialog$1$com-autochips-bluetooth-fragment-SetupFragment(Landroid/view/View;)V
    .locals 2

    .line 445
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    invoke-virtual {p1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    .line 446
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    const/16 v1, 0x14

    if-le v0, v1, :cond_0

    const/4 v0, 0x0

    const/16 v1, 0x13

    .line 447
    invoke-virtual {p1, v0, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object p1

    .line 450
    :cond_0
    invoke-virtual {p1}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_3

    .line 451
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_1

    .line 452
    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBTName(Ljava/lang/String;)V

    .line 454
    :cond_1
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    const v1, 0x7f08016b

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getId(I)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    if-eqz v0, :cond_2

    .line 456
    invoke-virtual {v0, p1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 458
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    invoke-virtual {p1}, Landroid/app/Dialog;->dismiss()V

    goto :goto_0

    .line 460
    :cond_3
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object p1

    const v0, 0x7f0d002e

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    :goto_0
    return-void
.end method

.method public synthetic lambda$buildEditNameDialog$2$com-autochips-bluetooth-fragment-SetupFragment(Landroid/view/View;)V
    .locals 0

    .line 463
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    invoke-virtual {p1}, Landroid/app/Dialog;->dismiss()V

    return-void
.end method

.method public onAttach(Landroid/content/Context;)V
    .locals 0

    .line 78
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onAttach(Landroid/content/Context;)V

    return-void
.end method

.method public onBindViewData()V
    .locals 0

    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 3

    .line 255
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothEnable()Z

    move-result v0

    if-nez v0, :cond_0

    .line 256
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object p1

    const v0, 0x7f0d0034

    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getString(I)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/hcn/bluetooth/api/Utils;->showToast(Landroid/content/Context;Ljava/lang/String;)V

    return-void

    .line 262
    :cond_0
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getViewId(Landroid/view/View;)I

    move-result p1

    const-string v0, "SetupFragment"

    const v1, 0x7f07011e

    const v2, 0x7f07011d

    sparse-switch p1, :sswitch_data_0

    goto/16 :goto_3

    .line 321
    :sswitch_0
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->onActionTipsFinished()V

    goto/16 :goto_3

    .line 287
    :sswitch_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoConnect()Z

    move-result p1

    xor-int/lit8 p1, p1, 0x1

    iput-boolean p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mIsAutoConnect:Z

    if-eqz p1, :cond_1

    .line 289
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoConnectButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 291
    :cond_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoConnectButton:Landroid/widget/Button;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 293
    :goto_0
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-boolean v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mIsAutoConnect:Z

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBluetoothAutoConnect(Z)V

    goto/16 :goto_3

    .line 297
    :sswitch_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBluetoothAutoAnswer()Z

    move-result p1

    xor-int/lit8 p1, p1, 0x1

    iput-boolean p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mIsAutoAnswer:Z

    if-eqz p1, :cond_2

    .line 299
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoAnswerButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    .line 301
    :cond_2
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mAutoAnswerButton:Landroid/widget/Button;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 303
    :goto_1
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    iget-boolean v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mIsAutoAnswer:Z

    invoke-virtual {p1, v0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setBluetoothAutoAnswer(Z)V

    goto/16 :goto_3

    .line 324
    :sswitch_3
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->isA2dpSourceRole()Z

    move-result p1

    xor-int/lit8 p1, p1, 0x1

    if-eqz p1, :cond_3

    .line 326
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mA2dpSourceSwitch:Landroid/view/View;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    .line 328
    :cond_3
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mA2dpSourceSwitch:Landroid/view/View;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 330
    :goto_2
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object v0

    invoke-virtual {v0, p1}, Lcom/autochips/bluetooth/MyApplication;->setA2dpSourceRole(Z)V

    .line 331
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->showRebootDialog()V

    goto/16 :goto_3

    .line 307
    :sswitch_4
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->onActionTipsStarted()V

    goto/16 :goto_3

    :sswitch_5
    const-string p1, "network click"

    .line 310
    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 311
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isAutoConnectedNetwork()Z

    move-result p1

    xor-int/lit8 p1, p1, 0x1

    .line 312
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->setAutoConnectedNetwork(Z)V

    if-eqz p1, :cond_4

    .line 315
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mNetWorkButton:Landroid/widget/Button;

    invoke-static {v1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_3

    .line 317
    :cond_4
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mNetWorkButton:Landroid/widget/Button;

    invoke-static {v2}, Lcom/autochips/bluetooth/skin/SkinUtils;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_3

    .line 276
    :sswitch_6
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    if-eqz p1, :cond_7

    invoke-virtual {p1}, Landroid/app/Dialog;->isShowing()Z

    move-result p1

    if-nez p1, :cond_7

    .line 277
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_5

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    if-eqz v0, :cond_5

    .line 278
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTPincode()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mPinCode:Ljava/lang/String;

    .line 279
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditPin:Landroid/widget/EditText;

    if-eqz v0, :cond_5

    .line 280
    invoke-virtual {v0, p1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 283
    :cond_5
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTPinDialog:Landroid/app/Dialog;

    invoke-virtual {p1}, Landroid/app/Dialog;->show()V

    goto :goto_3

    :sswitch_7
    const-string p1, "onClick: 2222222"

    .line 265
    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 266
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    if-eqz p1, :cond_7

    invoke-virtual {p1}, Landroid/app/Dialog;->isShowing()Z

    move-result p1

    if-nez p1, :cond_7

    .line 267
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz p1, :cond_6

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    if-eqz v0, :cond_6

    .line 268
    invoke-virtual {p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getBTName()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mDevName:Ljava/lang/String;

    .line 269
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditDeviceName:Landroid/widget/EditText;

    invoke-virtual {v0, p1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 271
    :cond_6
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mEditBTNameDialog:Landroid/app/Dialog;

    invoke-virtual {p1}, Landroid/app/Dialog;->show()V

    :cond_7
    :goto_3
    return-void

    :sswitch_data_0
    .sparse-switch
        0x7f08007c -> :sswitch_7
        0x7f08007d -> :sswitch_6
        0x7f0800f5 -> :sswitch_5
        0x7f0800f7 -> :sswitch_4
        0x7f080137 -> :sswitch_3
        0x7f080138 -> :sswitch_2
        0x7f080139 -> :sswitch_1
        0x7f08015d -> :sswitch_0
    .end sparse-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 0

    .line 83
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onCreate(Landroid/os/Bundle;)V

    .line 84
    invoke-static {}, Lcom/autochips/bluetooth/MyApplication;->getInstance()Lcom/autochips/bluetooth/MyApplication;

    move-result-object p1

    invoke-virtual {p1}, Lcom/autochips/bluetooth/MyApplication;->getAdapterManager()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 1

    .line 91
    invoke-static {}, Lcom/autochips/bluetooth/skin/SkinUtils;->useSkinPackage()Z

    move-result p3

    const v0, 0x7f0b002c

    if-eqz p3, :cond_1

    .line 92
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->isResourcesExist(I)I

    move-result p1

    if-lez p1, :cond_0

    .line 94
    invoke-static {v0}, Lcom/autochips/bluetooth/skin/SkinUtils;->getLayout(I)Landroid/view/View;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    goto :goto_0

    :cond_0
    const-string p1, "SetupFragment"

    const-string p2, "onCreateView: no layout_bt_setting in skin apk!"

    .line 96
    invoke-static {p1, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :goto_0
    const-string p1, "layout_zx_bt_setting"

    .line 99
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/SkinUtils;->getLayout(Ljava/lang/String;)Landroid/view/View;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    goto :goto_1

    :cond_1
    const/4 p3, 0x0

    .line 101
    invoke-virtual {p1, v0, p2, p3}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object p1

    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    .line 104
    :goto_1
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->initView()V

    .line 105
    new-instance p1, Landroid/content/IntentFilter;

    invoke-direct {p1}, Landroid/content/IntentFilter;-><init>()V

    const-string p2, "android.bluetooth.adapter.action.STATE_CHANGED"

    .line 106
    invoke-virtual {p1, p2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 107
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-static {p2}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p2

    check-cast p2, Landroid/content/Context;

    iget-object p3, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p2, p3, p1}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 108
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mViewRoot:Landroid/view/View;

    return-object p1
.end method

.method public onDestroy()V
    .locals 0

    .line 245
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroy()V

    return-void
.end method

.method public onDestroyView()V
    .locals 2

    .line 236
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDestroyView()V

    .line 237
    invoke-virtual {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->getContext()Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 238
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mLocalAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-eqz v0, :cond_0

    .line 239
    iget-object v1, p0, Lcom/autochips/bluetooth/fragment/SetupFragment;->mConnectionListener:Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-virtual {v0, v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V

    :cond_0
    return-void
.end method

.method public onDetach()V
    .locals 0

    .line 250
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onDetach()V

    return-void
.end method

.method public onHiddenChanged(Z)V
    .locals 0

    .line 231
    invoke-super {p0, p1}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onHiddenChanged(Z)V

    return-void
.end method

.method public onPause()V
    .locals 0

    .line 221
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onPause()V

    return-void
.end method

.method public onResume()V
    .locals 0

    .line 215
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onResume()V

    .line 216
    invoke-direct {p0}, Lcom/autochips/bluetooth/fragment/SetupFragment;->checkBluetoothState()V

    return-void
.end method

.method public onStart()V
    .locals 0

    .line 210
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStart()V

    return-void
.end method

.method public onStop()V
    .locals 0

    .line 226
    invoke-super {p0}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onStop()V

    return-void
.end method

.method public onViewCreated(Landroid/view/View;Landroid/os/Bundle;)V
    .locals 0

    .line 205
    invoke-super {p0, p1, p2}, Lcom/hcn/skin/support/app/SkinCompatFragment;->onViewCreated(Landroid/view/View;Landroid/os/Bundle;)V

    return-void
.end method
