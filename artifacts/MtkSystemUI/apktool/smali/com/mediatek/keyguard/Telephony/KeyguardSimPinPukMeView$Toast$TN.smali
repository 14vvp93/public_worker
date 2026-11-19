.class Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;
.super Landroid/app/ITransientNotification$Stub;
.source "KeyguardSimPinPukMeView.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "TN"
.end annotation


# instance fields
.field final mHide:Ljava/lang/Runnable;

.field private final mParams:Landroid/view/WindowManager$LayoutParams;

.field final mShow:Ljava/lang/Runnable;

.field mWM:Landroid/view/WindowManagerImpl;

.field final synthetic this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;


# direct methods
.method constructor <init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;)V
    .locals 1

    .line 1146
    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    invoke-direct {p0}, Landroid/app/ITransientNotification$Stub;-><init>()V

    .line 1130
    new-instance p1, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN$1;

    invoke-direct {p1, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN$1;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;)V

    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mShow:Ljava/lang/Runnable;

    .line 1136
    new-instance p1, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN$2;

    invoke-direct {p1, p0}, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN$2;-><init>(Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;)V

    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mHide:Ljava/lang/Runnable;

    .line 1142
    new-instance p1, Landroid/view/WindowManager$LayoutParams;

    invoke-direct {p1}, Landroid/view/WindowManager$LayoutParams;-><init>()V

    iput-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mParams:Landroid/view/WindowManager$LayoutParams;

    .line 1147
    iget-object p1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mParams:Landroid/view/WindowManager$LayoutParams;

    .line 1148
    .local p1, "params":Landroid/view/WindowManager$LayoutParams;
    const/4 v0, -0x2

    iput v0, p1, Landroid/view/WindowManager$LayoutParams;->height:I

    .line 1149
    iput v0, p1, Landroid/view/WindowManager$LayoutParams;->width:I

    .line 1150
    const/16 v0, 0x98

    iput v0, p1, Landroid/view/WindowManager$LayoutParams;->flags:I

    .line 1153
    const/4 v0, -0x3

    iput v0, p1, Landroid/view/WindowManager$LayoutParams;->format:I

    .line 1154
    const v0, 0x1030004

    iput v0, p1, Landroid/view/WindowManager$LayoutParams;->windowAnimations:I

    .line 1155
    const/16 v0, 0x7d9

    iput v0, p1, Landroid/view/WindowManager$LayoutParams;->type:I

    .line 1156
    const-string v0, "Toast"

    invoke-virtual {p1, v0}, Landroid/view/WindowManager$LayoutParams;->setTitle(Ljava/lang/CharSequence;)V

    .line 1157
    return-void
.end method


# virtual methods
.method public handleHide()V
    .locals 2

    .line 1212
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v0, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    if-eqz v0, :cond_1

    .line 1216
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v0, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 1220
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mWM:Landroid/view/WindowManagerImpl;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v1, v1, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    invoke-virtual {v0, v1}, Landroid/view/WindowManagerImpl;->removeView(Landroid/view/View;)V

    .line 1223
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    const/4 v1, 0x0

    iput-object v1, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    .line 1225
    :cond_1
    return-void
.end method

.method public handleShow()V
    .locals 4

    .line 1184
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v0, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mContext:Landroid/content/Context;

    const-string/jumbo v1, "window"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManagerImpl;

    iput-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mWM:Landroid/view/WindowManagerImpl;

    .line 1185
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget v0, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mGravity:I

    .line 1186
    .local v0, "gravity":I
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mParams:Landroid/view/WindowManager$LayoutParams;

    iput v0, v1, Landroid/view/WindowManager$LayoutParams;->gravity:I

    .line 1187
    and-int/lit8 v1, v0, 0x7

    const/high16 v2, 0x3f800000    # 1.0f

    const/4 v3, 0x7

    if-ne v1, v3, :cond_0

    .line 1188
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mParams:Landroid/view/WindowManager$LayoutParams;

    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->horizontalWeight:F

    .line 1190
    :cond_0
    and-int/lit8 v1, v0, 0x70

    const/16 v3, 0x70

    if-ne v1, v3, :cond_1

    .line 1191
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mParams:Landroid/view/WindowManager$LayoutParams;

    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->verticalWeight:F

    .line 1193
    :cond_1
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mParams:Landroid/view/WindowManager$LayoutParams;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget v2, v2, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mY:I

    iput v2, v1, Landroid/view/WindowManager$LayoutParams;->y:I

    .line 1194
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v1, v1, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    if-eqz v1, :cond_3

    .line 1195
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v1, v1, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    invoke-virtual {v1}, Landroid/view/View;->getParent()Landroid/view/ViewParent;

    move-result-object v1

    if-eqz v1, :cond_2

    .line 1199
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mWM:Landroid/view/WindowManagerImpl;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v2, v2, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    invoke-virtual {v1, v2}, Landroid/view/WindowManagerImpl;->removeView(Landroid/view/View;)V

    .line 1204
    :cond_2
    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mWM:Landroid/view/WindowManagerImpl;

    iget-object v2, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v2, v2, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mView:Landroid/view/View;

    iget-object v3, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mParams:Landroid/view/WindowManager$LayoutParams;

    invoke-virtual {v1, v2, v3}, Landroid/view/WindowManagerImpl;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 1206
    :cond_3
    return-void
.end method

.method public hide()V
    .locals 2

    .line 1176
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v0, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mHide:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 1177
    return-void
.end method

.method public show(Landroid/os/IBinder;)V
    .locals 2
    .param p1, "windowToken"    # Landroid/os/IBinder;

    .line 1166
    iget-object v0, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->this$0:Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;

    iget-object v0, v0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast;->mHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/mediatek/keyguard/Telephony/KeyguardSimPinPukMeView$Toast$TN;->mShow:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 1167
    return-void
.end method
