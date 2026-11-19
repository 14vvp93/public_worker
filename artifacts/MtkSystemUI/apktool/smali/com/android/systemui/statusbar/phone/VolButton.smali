.class public Lcom/android/systemui/statusbar/phone/VolButton;
.super Landroid/widget/ImageView;
.source "VolButton.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;,
        Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;,
        Lcom/android/systemui/statusbar/phone/VolButton$LongClickThread;
    }
.end annotation


# instance fields
.field private handler:Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;

.field private intervalTime:J

.field private repeatListener:Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 25
    invoke-direct {p0, p1, p2}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 26
    invoke-direct {p0}, Lcom/android/systemui/statusbar/phone/VolButton;->init()V

    .line 27
    return-void
.end method

.method static synthetic access$100(Lcom/android/systemui/statusbar/phone/VolButton;)Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/VolButton;

    .line 13
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/VolButton;->handler:Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;

    return-object v0
.end method

.method static synthetic access$200(Lcom/android/systemui/statusbar/phone/VolButton;)J
    .locals 2
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/VolButton;

    .line 13
    iget-wide v0, p0, Lcom/android/systemui/statusbar/phone/VolButton;->intervalTime:J

    return-wide v0
.end method

.method static synthetic access$300(Lcom/android/systemui/statusbar/phone/VolButton;)Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/phone/VolButton;

    .line 13
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/VolButton;->repeatListener:Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;

    return-object v0
.end method

.method private init()V
    .locals 1

    .line 31
    new-instance v0, Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;-><init>(Lcom/android/systemui/statusbar/phone/VolButton;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/VolButton;->handler:Lcom/android/systemui/statusbar/phone/VolButton$MyHandler;

    .line 32
    new-instance v0, Lcom/android/systemui/statusbar/phone/VolButton$1;

    invoke-direct {v0, p0}, Lcom/android/systemui/statusbar/phone/VolButton$1;-><init>(Lcom/android/systemui/statusbar/phone/VolButton;)V

    invoke-virtual {p0, v0}, Lcom/android/systemui/statusbar/phone/VolButton;->setOnLongClickListener(Landroid/view/View$OnLongClickListener;)V

    .line 39
    return-void
.end method


# virtual methods
.method public setLongClickRepeatListener(Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;)V
    .locals 2
    .param p1, "listener"    # Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;

    .line 89
    const-wide/16 v0, 0x64

    invoke-virtual {p0, p1, v0, v1}, Lcom/android/systemui/statusbar/phone/VolButton;->setLongClickRepeatListener(Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;J)V

    .line 90
    return-void
.end method

.method public setLongClickRepeatListener(Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;J)V
    .locals 0
    .param p1, "listener"    # Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;
    .param p2, "intervalTime"    # J

    .line 80
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/VolButton;->repeatListener:Lcom/android/systemui/statusbar/phone/VolButton$LongClickRepeatListener;

    .line 81
    iput-wide p2, p0, Lcom/android/systemui/statusbar/phone/VolButton;->intervalTime:J

    .line 82
    return-void
.end method
