.class Lcom/android/systemui/statusbar/phone/StatusBarIconList$Slot$1;
.super Ljava/lang/Object;
.source "StatusBarIconList.java"

# interfaces
.implements Ljava/util/Comparator;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/phone/StatusBarIconList$Slot;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Ljava/util/Comparator<",
        "Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/statusbar/phone/StatusBarIconList$Slot;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/phone/StatusBarIconList$Slot;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/phone/StatusBarIconList$Slot;

    .line 148
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/StatusBarIconList$Slot$1;->this$0:Lcom/android/systemui/statusbar/phone/StatusBarIconList$Slot;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public compare(Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;)I
    .locals 2
    .param p1, "h1"    # Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;
    .param p2, "h2"    # Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;

    .line 151
    invoke-virtual {p1}, Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;->getType()I

    move-result v0

    const/4 v1, 0x2

    if-ne v0, v1, :cond_2

    .line 152
    invoke-virtual {p2}, Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;->getType()I

    move-result v0

    if-eq v0, v1, :cond_0

    goto :goto_0

    .line 154
    :cond_0
    invoke-virtual {p1}, Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;->getMobileState()Lcom/android/systemui/statusbar/phone/StatusBarSignalPolicy$MobileIconState;

    move-result-object v0

    iget v0, v0, Lcom/android/systemui/statusbar/phone/StatusBarSignalPolicy$MobileIconState;->phoneId:I

    invoke-virtual {p2}, Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;->getMobileState()Lcom/android/systemui/statusbar/phone/StatusBarSignalPolicy$MobileIconState;

    move-result-object v1

    iget v1, v1, Lcom/android/systemui/statusbar/phone/StatusBarSignalPolicy$MobileIconState;->phoneId:I

    if-ge v0, v1, :cond_1

    .line 155
    const/4 v0, 0x1

    return v0

    .line 157
    :cond_1
    const/4 v0, -0x1

    return v0

    .line 153
    :cond_2
    :goto_0
    const/4 v0, 0x0

    return v0
.end method

.method public bridge synthetic compare(Ljava/lang/Object;Ljava/lang/Object;)I
    .locals 0

    .line 148
    check-cast p1, Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;

    check-cast p2, Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;

    invoke-virtual {p0, p1, p2}, Lcom/android/systemui/statusbar/phone/StatusBarIconList$Slot$1;->compare(Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;Lcom/android/systemui/statusbar/phone/StatusBarIconHolder;)I

    move-result p1

    return p1
.end method
