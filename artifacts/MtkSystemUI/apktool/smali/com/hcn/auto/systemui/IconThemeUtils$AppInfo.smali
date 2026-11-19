.class Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
.super Ljava/lang/Object;
.source "IconThemeUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/auto/systemui/IconThemeUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "AppInfo"
.end annotation


# instance fields
.field private className:Ljava/lang/String;

.field private hidden:Z

.field private iconName:Ljava/lang/String;

.field private packageName:Ljava/lang/String;


# direct methods
.method constructor <init>()V
    .locals 0

    .line 389
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;

    .line 389
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->packageName:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$100(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;

    .line 389
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->className:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$300(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;

    .line 389
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->iconName:Ljava/lang/String;

    return-object v0
.end method


# virtual methods
.method public getPackageName()Ljava/lang/String;
    .locals 1

    .line 396
    iget-object v0, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->packageName:Ljava/lang/String;

    return-object v0
.end method

.method public setClassName(Ljava/lang/String;)V
    .locals 0
    .param p1, "className"    # Ljava/lang/String;

    .line 409
    iput-object p1, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->className:Ljava/lang/String;

    .line 410
    return-void
.end method

.method public setHidden(Z)V
    .locals 0
    .param p1, "hidden"    # Z

    .line 425
    iput-boolean p1, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->hidden:Z

    .line 426
    return-void
.end method

.method public setIconName(Ljava/lang/String;)V
    .locals 0
    .param p1, "iconName"    # Ljava/lang/String;

    .line 417
    iput-object p1, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->iconName:Ljava/lang/String;

    .line 418
    return-void
.end method

.method public setPackageName(Ljava/lang/String;)V
    .locals 0
    .param p1, "packageName"    # Ljava/lang/String;

    .line 400
    iput-object p1, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->packageName:Ljava/lang/String;

    .line 401
    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 3

    .line 430
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "AppInfo{packageName=\'"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const/16 v1, 0x27

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const-string v2, ", className=\'"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->className:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const-string v2, ", iconName=\'"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->iconName:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const-string v2, ", hidden=\'"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->hidden:Z

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const/16 v1, 0x7d

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
