.class public final synthetic Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$VKp9cJzYklngzLI-lCJ9AVCmB6U;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/util/function/Consumer;


# static fields
.field public static final synthetic INSTANCE:Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$VKp9cJzYklngzLI-lCJ9AVCmB6U;


# direct methods
.method static synthetic constructor <clinit>()V
    .locals 1

    new-instance v0, Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$VKp9cJzYklngzLI-lCJ9AVCmB6U;

    invoke-direct {v0}, Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$VKp9cJzYklngzLI-lCJ9AVCmB6U;-><init>()V

    sput-object v0, Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$VKp9cJzYklngzLI-lCJ9AVCmB6U;->INSTANCE:Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$VKp9cJzYklngzLI-lCJ9AVCmB6U;

    return-void
.end method

.method private synthetic constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    check-cast p1, Lcom/android/systemui/statusbar/NotificationData$Entry;

    invoke-static {p1}, Lcom/android/systemui/statusbar/phone/StatusBar;->lambda$maybeEscalateHeadsUp$22(Lcom/android/systemui/statusbar/NotificationData$Entry;)V

    return-void
.end method
