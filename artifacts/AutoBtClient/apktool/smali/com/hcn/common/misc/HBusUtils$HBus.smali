.class public interface abstract annotation Lcom/hcn/common/misc/HBusUtils$HBus;
.super Ljava/lang/Object;
.source "HBusUtils.java"

# interfaces
.implements Ljava/lang/annotation/Annotation;


# annotations
.annotation system Ldalvik/annotation/AnnotationDefault;
    value = .subannotation Lcom/hcn/common/misc/HBusUtils$HBus;
        priority = 0x0
        sticky = false
        threadMode = .enum Lcom/hcn/common/misc/HBusUtils$ThreadMode;->POSTING:Lcom/hcn/common/misc/HBusUtils$ThreadMode;
    .end subannotation
.end annotation

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/HBusUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2609
    name = "HBus"
.end annotation

.annotation runtime Ljava/lang/annotation/Retention;
    value = .enum Ljava/lang/annotation/RetentionPolicy;->CLASS:Ljava/lang/annotation/RetentionPolicy;
.end annotation

.annotation runtime Ljava/lang/annotation/Target;
    value = {
        .enum Ljava/lang/annotation/ElementType;->METHOD:Ljava/lang/annotation/ElementType;
    }
.end annotation


# virtual methods
.method public abstract priority()I
.end method

.method public abstract sticky()Z
.end method

.method public abstract tag()Ljava/lang/String;
.end method

.method public abstract threadMode()Lcom/hcn/common/misc/HBusUtils$ThreadMode;
.end method
