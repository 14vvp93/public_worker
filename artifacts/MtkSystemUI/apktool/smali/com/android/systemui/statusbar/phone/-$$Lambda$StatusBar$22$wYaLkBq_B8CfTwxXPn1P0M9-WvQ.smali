.class public final synthetic Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$22$wYaLkBq_B8CfTwxXPn1P0M9-WvQ;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Lcom/android/systemui/statusbar/phone/StatusBar$22;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/phone/StatusBar$22;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$22$wYaLkBq_B8CfTwxXPn1P0M9-WvQ;->f$0:Lcom/android/systemui/statusbar/phone/StatusBar$22;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/-$$Lambda$StatusBar$22$wYaLkBq_B8CfTwxXPn1P0M9-WvQ;->f$0:Lcom/android/systemui/statusbar/phone/StatusBar$22;

    invoke-static {v0}, Lcom/android/systemui/statusbar/phone/StatusBar$22;->lambda$onFinishedGoingToSleep$0(Lcom/android/systemui/statusbar/phone/StatusBar$22;)V

    return-void
.end method
