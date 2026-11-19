.class Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;
.super Ljava/lang/Object;
.source "IconThemeUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/auto/theme/utils/IconThemeUtils;
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

    .line 171
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 171
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->packageName:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 171
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->className:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$200(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Z
    .locals 1
    .param p0, "x0"    # Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 171
    iget-boolean v0, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->hidden:Z

    return v0
.end method

.method static synthetic access$300(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 171
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->iconName:Ljava/lang/String;

    return-object v0
.end method


# virtual methods
.method public getPackageName()Ljava/lang/String;
    .locals 1

    .line 178
    iget-object v0, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->packageName:Ljava/lang/String;

    return-object v0
.end method

.method public setClassName(Ljava/lang/String;)V
    .locals 0
    .param p1, "className"    # Ljava/lang/String;

    .line 191
    iput-object p1, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->className:Ljava/lang/String;

    .line 192
    return-void
.end method

.method public setHidden(Z)V
    .locals 0
    .param p1, "hidden"    # Z

    .line 207
    iput-boolean p1, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->hidden:Z

    .line 208
    return-void
.end method

.method public setIconName(Ljava/lang/String;)V
    .locals 0
    .param p1, "iconName"    # Ljava/lang/String;

    .line 199
    iput-object p1, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->iconName:Ljava/lang/String;

    .line 200
    return-void
.end method

.method public setPackageName(Ljava/lang/String;)V
    .locals 0
    .param p1, "packageName"    # Ljava/lang/String;

    .line 182
    iput-object p1, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->packageName:Ljava/lang/String;

    .line 183
    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 3

    .line 212
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "AppInfo{packageName=\'"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->packageName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const/16 v1, 0x27

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const-string v2, ", className=\'"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->className:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const-string v2, ", iconName=\'"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->iconName:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const-string v2, ", hidden=\'"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->hidden:Z

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const/16 v1, 0x7d

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
