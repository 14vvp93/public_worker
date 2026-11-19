.class public final Lcom/hcn/common/utils/HMessage;
.super Ljava/lang/Object;
.source "HMessage.java"

# interfaces
.implements Landroid/os/Parcelable;


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lcom/hcn/common/utils/HMessage;",
            ">;"
        }
    .end annotation
.end field

.field static final FLAGS_TO_CLEAR_ON_COPY_FROM:I = 0x1

.field static final FLAG_ASYNCHRONOUS:I = 0x2

.field static final FLAG_IN_USE:I = 0x1

.field private static final MAX_POOL_SIZE:I = 0x14

.field private static gCheckRecycle:Z = true

.field private static sPool:Lcom/hcn/common/utils/HMessage;

.field private static sPoolSize:I

.field public static final sPoolSync:Ljava/lang/Object;


# instance fields
.field public arg0:I

.field public arg1:I

.field flags:I

.field next:Lcom/hcn/common/utils/HMessage;

.field public obj0:Ljava/lang/Object;

.field public what:I


# direct methods
.method static bridge synthetic -$$Nest$mreadFromParcel(Lcom/hcn/common/utils/HMessage;Landroid/os/Parcel;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/hcn/common/utils/HMessage;->readFromParcel(Landroid/os/Parcel;)V

    return-void
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 81
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    sput-object v0, Lcom/hcn/common/utils/HMessage;->sPoolSync:Ljava/lang/Object;

    .line 196
    new-instance v0, Lcom/hcn/common/utils/HMessage$1;

    invoke-direct {v0}, Lcom/hcn/common/utils/HMessage$1;-><init>()V

    sput-object v0, Lcom/hcn/common/utils/HMessage;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    const/4 v0, -0x1

    .line 21
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HMessage;-><init>(I)V

    return-void
.end method

.method public constructor <init>(I)V
    .locals 1

    const/4 v0, -0x1

    .line 25
    invoke-direct {p0, p1, v0, v0}, Lcom/hcn/common/utils/HMessage;-><init>(III)V

    return-void
.end method

.method public constructor <init>(III)V
    .locals 1

    const/4 v0, 0x0

    .line 29
    invoke-direct {p0, p1, p2, p3, v0}, Lcom/hcn/common/utils/HMessage;-><init>(IIILjava/lang/Object;)V

    return-void
.end method

.method public constructor <init>(IIILjava/lang/Object;)V
    .locals 0

    .line 32
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 33
    iput p1, p0, Lcom/hcn/common/utils/HMessage;->what:I

    .line 34
    iput p2, p0, Lcom/hcn/common/utils/HMessage;->arg0:I

    .line 35
    iput p3, p0, Lcom/hcn/common/utils/HMessage;->arg1:I

    .line 36
    iput-object p4, p0, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    return-void
.end method

.method public static obtain()Lcom/hcn/common/utils/HMessage;
    .locals 3

    .line 88
    sget-object v0, Lcom/hcn/common/utils/HMessage;->sPoolSync:Ljava/lang/Object;

    monitor-enter v0

    .line 89
    :try_start_0
    sget-object v1, Lcom/hcn/common/utils/HMessage;->sPool:Lcom/hcn/common/utils/HMessage;

    if-eqz v1, :cond_0

    .line 91
    iget-object v2, v1, Lcom/hcn/common/utils/HMessage;->next:Lcom/hcn/common/utils/HMessage;

    sput-object v2, Lcom/hcn/common/utils/HMessage;->sPool:Lcom/hcn/common/utils/HMessage;

    const/4 v2, 0x0

    .line 92
    iput-object v2, v1, Lcom/hcn/common/utils/HMessage;->next:Lcom/hcn/common/utils/HMessage;

    const/4 v2, 0x0

    .line 93
    iput v2, v1, Lcom/hcn/common/utils/HMessage;->flags:I

    .line 94
    sget v2, Lcom/hcn/common/utils/HMessage;->sPoolSize:I

    add-int/lit8 v2, v2, -0x1

    sput v2, Lcom/hcn/common/utils/HMessage;->sPoolSize:I

    .line 95
    monitor-exit v0

    return-object v1

    .line 97
    :cond_0
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 99
    new-instance v0, Lcom/hcn/common/utils/HMessage;

    invoke-direct {v0}, Lcom/hcn/common/utils/HMessage;-><init>()V

    return-object v0

    :catchall_0
    move-exception v1

    .line 97
    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v1
.end method

.method public static obtain(I)Lcom/hcn/common/utils/HMessage;
    .locals 2

    const/4 v0, -0x1

    const/4 v1, 0x0

    .line 103
    invoke-static {p0, v0, v0, v1}, Lcom/hcn/common/utils/HMessage;->obtain(IIILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;

    move-result-object p0

    return-object p0
.end method

.method public static obtain(II)Lcom/hcn/common/utils/HMessage;
    .locals 2

    const/4 v0, -0x1

    const/4 v1, 0x0

    .line 111
    invoke-static {p0, p1, v0, v1}, Lcom/hcn/common/utils/HMessage;->obtain(IIILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;

    move-result-object p0

    return-object p0
.end method

.method public static obtain(III)Lcom/hcn/common/utils/HMessage;
    .locals 1

    const/4 v0, 0x0

    .line 119
    invoke-static {p0, p1, p2, v0}, Lcom/hcn/common/utils/HMessage;->obtain(IIILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;

    move-result-object p0

    return-object p0
.end method

.method public static obtain(IIILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;
    .locals 1

    .line 127
    invoke-static {}, Lcom/hcn/common/utils/HMessage;->obtain()Lcom/hcn/common/utils/HMessage;

    move-result-object v0

    .line 128
    iput p0, v0, Lcom/hcn/common/utils/HMessage;->what:I

    .line 129
    iput p1, v0, Lcom/hcn/common/utils/HMessage;->arg0:I

    .line 130
    iput p2, v0, Lcom/hcn/common/utils/HMessage;->arg1:I

    .line 131
    iput-object p3, v0, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    return-object v0
.end method

.method public static obtain(IILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;
    .locals 1

    const/4 v0, -0x1

    .line 123
    invoke-static {p0, p1, v0, p2}, Lcom/hcn/common/utils/HMessage;->obtain(IIILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;

    move-result-object p0

    return-object p0
.end method

.method public static obtain(ILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;
    .locals 1

    const/4 v0, -0x1

    .line 115
    invoke-static {p0, v0, v0, p1}, Lcom/hcn/common/utils/HMessage;->obtain(IIILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;

    move-result-object p0

    return-object p0
.end method

.method public static obtain(Lcom/hcn/common/utils/HMessage;)Lcom/hcn/common/utils/HMessage;
    .locals 2

    .line 137
    invoke-static {}, Lcom/hcn/common/utils/HMessage;->obtain()Lcom/hcn/common/utils/HMessage;

    move-result-object v0

    .line 138
    iget v1, p0, Lcom/hcn/common/utils/HMessage;->what:I

    iput v1, v0, Lcom/hcn/common/utils/HMessage;->what:I

    .line 139
    iget v1, p0, Lcom/hcn/common/utils/HMessage;->arg0:I

    iput v1, v0, Lcom/hcn/common/utils/HMessage;->arg0:I

    .line 140
    iget v1, p0, Lcom/hcn/common/utils/HMessage;->arg1:I

    iput v1, v0, Lcom/hcn/common/utils/HMessage;->arg1:I

    .line 141
    iget-object p0, p0, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    iput-object p0, v0, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    return-object v0
.end method

.method public static obtain(Ljava/lang/Object;)Lcom/hcn/common/utils/HMessage;
    .locals 1

    const/4 v0, -0x1

    .line 107
    invoke-static {v0, v0, v0, p0}, Lcom/hcn/common/utils/HMessage;->obtain(IIILjava/lang/Object;)Lcom/hcn/common/utils/HMessage;

    move-result-object p0

    return-object p0
.end method

.method private readFromParcel(Landroid/os/Parcel;)V
    .locals 1

    .line 235
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/common/utils/HMessage;->what:I

    .line 236
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/common/utils/HMessage;->arg0:I

    .line 237
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/common/utils/HMessage;->arg1:I

    .line 239
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    if-eqz v0, :cond_0

    .line 240
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->readParcelable(Ljava/lang/ClassLoader;)Landroid/os/Parcelable;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    :cond_0
    return-void
.end method

.method public static updateCheckRecycle(I)V
    .locals 1

    const/16 v0, 0x15

    if-ge p0, v0, :cond_0

    const/4 p0, 0x0

    .line 163
    sput-boolean p0, Lcom/hcn/common/utils/HMessage;->gCheckRecycle:Z

    :cond_0
    return-void
.end method


# virtual methods
.method public copyFrom(Lcom/hcn/common/utils/HMessage;)V
    .locals 1

    .line 152
    iget v0, p1, Lcom/hcn/common/utils/HMessage;->what:I

    iput v0, p0, Lcom/hcn/common/utils/HMessage;->what:I

    .line 153
    iget v0, p1, Lcom/hcn/common/utils/HMessage;->arg0:I

    iput v0, p0, Lcom/hcn/common/utils/HMessage;->arg0:I

    .line 154
    iget v0, p1, Lcom/hcn/common/utils/HMessage;->arg1:I

    iput v0, p0, Lcom/hcn/common/utils/HMessage;->arg1:I

    .line 155
    iget-object v0, p1, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    iput-object v0, p0, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    .line 157
    iget p1, p1, Lcom/hcn/common/utils/HMessage;->flags:I

    and-int/lit8 p1, p1, -0x2

    iput p1, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    return-void
.end method

.method public describeContents()I
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public isAsynchronous()Z
    .locals 1

    .line 57
    iget v0, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    and-int/lit8 v0, v0, 0x2

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method isInUse()Z
    .locals 2

    .line 69
    iget v0, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    const/4 v1, 0x1

    and-int/2addr v0, v1

    if-ne v0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x0

    :goto_0
    return v1
.end method

.method markInUse()V
    .locals 1

    .line 73
    iget v0, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    or-int/lit8 v0, v0, 0x1

    iput v0, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    return-void
.end method

.method public recycle()V
    .locals 2

    .line 169
    invoke-virtual {p0}, Lcom/hcn/common/utils/HMessage;->isInUse()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 170
    sget-boolean v0, Lcom/hcn/common/utils/HMessage;->gCheckRecycle:Z

    if-nez v0, :cond_0

    return-void

    .line 171
    :cond_0
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string v1, "This message cannot be recycled because it is still in use."

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 177
    :cond_1
    invoke-virtual {p0}, Lcom/hcn/common/utils/HMessage;->recycleUnchecked()V

    return-void
.end method

.method recycleUnchecked()V
    .locals 4

    const/4 v0, 0x1

    .line 181
    iput v0, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    const/4 v1, 0x0

    .line 182
    iput v1, p0, Lcom/hcn/common/utils/HMessage;->what:I

    .line 183
    iput v1, p0, Lcom/hcn/common/utils/HMessage;->arg0:I

    .line 184
    iput v1, p0, Lcom/hcn/common/utils/HMessage;->arg1:I

    const/4 v1, 0x0

    .line 185
    iput-object v1, p0, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    .line 187
    sget-object v1, Lcom/hcn/common/utils/HMessage;->sPoolSync:Ljava/lang/Object;

    monitor-enter v1

    .line 188
    :try_start_0
    sget v2, Lcom/hcn/common/utils/HMessage;->sPoolSize:I

    const/16 v3, 0x14

    if-ge v2, v3, :cond_0

    .line 189
    sget-object v3, Lcom/hcn/common/utils/HMessage;->sPool:Lcom/hcn/common/utils/HMessage;

    iput-object v3, p0, Lcom/hcn/common/utils/HMessage;->next:Lcom/hcn/common/utils/HMessage;

    .line 190
    sput-object p0, Lcom/hcn/common/utils/HMessage;->sPool:Lcom/hcn/common/utils/HMessage;

    add-int/2addr v2, v0

    .line 191
    sput v2, Lcom/hcn/common/utils/HMessage;->sPoolSize:I

    .line 193
    :cond_0
    monitor-exit v1

    return-void

    :catchall_0
    move-exception v0

    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v0
.end method

.method public setAsynchronous(Z)V
    .locals 0

    if-eqz p1, :cond_0

    .line 62
    iget p1, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    or-int/lit8 p1, p1, 0x2

    iput p1, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    goto :goto_0

    .line 64
    :cond_0
    iget p1, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    and-int/lit8 p1, p1, -0x3

    iput p1, p0, Lcom/hcn/common/utils/HMessage;->flags:I

    :goto_0
    return-void
.end method

.method public setWhat(I)Lcom/hcn/common/utils/HMessage;
    .locals 0

    .line 147
    iput p1, p0, Lcom/hcn/common/utils/HMessage;->what:I

    return-object p0
.end method

.method public writeToParcel(Landroid/os/Parcel;I)V
    .locals 2

    .line 216
    iget v0, p0, Lcom/hcn/common/utils/HMessage;->what:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 217
    iget v0, p0, Lcom/hcn/common/utils/HMessage;->arg0:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 218
    iget v0, p0, Lcom/hcn/common/utils/HMessage;->arg1:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 220
    iget-object v0, p0, Lcom/hcn/common/utils/HMessage;->obj0:Ljava/lang/Object;

    if-eqz v0, :cond_0

    .line 222
    :try_start_0
    check-cast v0, Landroid/os/Parcelable;

    const/4 v1, 0x1

    .line 223
    invoke-virtual {p1, v1}, Landroid/os/Parcel;->writeInt(I)V

    .line 224
    invoke-virtual {p1, v0, p2}, Landroid/os/Parcel;->writeParcelable(Landroid/os/Parcelable;I)V
    :try_end_0
    .catch Ljava/lang/ClassCastException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 226
    :catch_0
    new-instance p1, Ljava/lang/RuntimeException;

    const-string p2, "Can\'t marshal non-Parcelable objects across processes."

    invoke-direct {p1, p2}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw p1

    :cond_0
    const/4 p2, 0x0

    .line 230
    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeInt(I)V

    :goto_0
    return-void
.end method
