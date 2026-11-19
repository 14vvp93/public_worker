.class public abstract Lcom/hcn/common/lang/RunnableEx;
.super Ljava/lang/Object;
.source "RunnableEx.java"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private mObject:Ljava/lang/Object;


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public callback(Ljava/lang/Object;)V
    .locals 0

    return-void
.end method

.method public getObject()Ljava/lang/Object;
    .locals 1

    .line 11
    iget-object v0, p0, Lcom/hcn/common/lang/RunnableEx;->mObject:Ljava/lang/Object;

    return-object v0
.end method

.method public run()V
    .locals 0

    return-void
.end method

.method public setObject(Ljava/lang/Object;)V
    .locals 0

    .line 15
    iput-object p1, p0, Lcom/hcn/common/lang/RunnableEx;->mObject:Ljava/lang/Object;

    return-void
.end method
