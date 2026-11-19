.class Lcom/hcn/common/utils/HMessage$1;
.super Ljava/lang/Object;
.source "HMessage.java"

# interfaces
.implements Landroid/os/Parcelable$Creator;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HMessage;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Landroid/os/Parcelable$Creator<",
        "Lcom/hcn/common/utils/HMessage;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .line 197
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public createFromParcel(Landroid/os/Parcel;)Lcom/hcn/common/utils/HMessage;
    .locals 1

    .line 199
    invoke-static {}, Lcom/hcn/common/utils/HMessage;->obtain()Lcom/hcn/common/utils/HMessage;

    move-result-object v0

    .line 200
    invoke-static {v0, p1}, Lcom/hcn/common/utils/HMessage;->-$$Nest$mreadFromParcel(Lcom/hcn/common/utils/HMessage;Landroid/os/Parcel;)V

    return-object v0
.end method

.method public bridge synthetic createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;
    .locals 0

    .line 197
    invoke-virtual {p0, p1}, Lcom/hcn/common/utils/HMessage$1;->createFromParcel(Landroid/os/Parcel;)Lcom/hcn/common/utils/HMessage;

    move-result-object p1

    return-object p1
.end method

.method public newArray(I)[Lcom/hcn/common/utils/HMessage;
    .locals 0

    .line 205
    new-array p1, p1, [Lcom/hcn/common/utils/HMessage;

    return-object p1
.end method

.method public bridge synthetic newArray(I)[Ljava/lang/Object;
    .locals 0

    .line 197
    invoke-virtual {p0, p1}, Lcom/hcn/common/utils/HMessage$1;->newArray(I)[Lcom/hcn/common/utils/HMessage;

    move-result-object p1

    return-object p1
.end method
