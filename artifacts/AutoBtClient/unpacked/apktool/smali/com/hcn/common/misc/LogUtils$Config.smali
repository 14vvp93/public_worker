.class public final Lcom/hcn/common/misc/LogUtils$Config;
.super Ljava/lang/Object;
.source "LogUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/LogUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "Config"
.end annotation


# static fields
.field static final synthetic $assertionsDisabled:Z


# instance fields
.field private mConsoleFilter:I

.field private mDefaultDir:Ljava/lang/String;

.field private mDir:Ljava/lang/String;

.field private mFileExtension:Ljava/lang/String;

.field private mFileFilter:I

.field private final mFileHead:Lcom/hcn/common/utils/HUtilsEx$FileHead;

.field private mFilePrefix:Ljava/lang/String;

.field private mFileWriter:Lcom/hcn/common/misc/LogUtils$IFileWriter;

.field private mGlobalTag:Ljava/lang/String;

.field private mLog2ConsoleSwitch:Z

.field private mLog2FileSwitch:Z

.field private mLogBorderSwitch:Z

.field private mLogHeadSwitch:Z

.field private mLogSwitch:Z

.field private mOnConsoleOutputListener:Lcom/hcn/common/misc/LogUtils$OnConsoleOutputListener;

.field private mOnFileOutputListener:Lcom/hcn/common/misc/LogUtils$OnFileOutputListener;

.field private final mProcessName:Ljava/lang/String;

.field private mSaveDays:I

.field private mSingleTagSwitch:Z

.field private mStackDeep:I

.field private mStackOffset:I

.field private mTagIsSpace:Z


# direct methods
.method static bridge synthetic -$$Nest$fgetmConsoleFilter(Lcom/hcn/common/misc/LogUtils$Config;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mConsoleFilter:I

    return p0
.end method

.method static bridge synthetic -$$Nest$fgetmFileFilter(Lcom/hcn/common/misc/LogUtils$Config;)I
    .locals 0

    iget p0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileFilter:I

    return p0
.end method

.method static bridge synthetic -$$Nest$fgetmFileHead(Lcom/hcn/common/misc/LogUtils$Config;)Lcom/hcn/common/utils/HUtilsEx$FileHead;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileHead:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fgetmFileWriter(Lcom/hcn/common/misc/LogUtils$Config;)Lcom/hcn/common/misc/LogUtils$IFileWriter;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileWriter:Lcom/hcn/common/misc/LogUtils$IFileWriter;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fgetmOnConsoleOutputListener(Lcom/hcn/common/misc/LogUtils$Config;)Lcom/hcn/common/misc/LogUtils$OnConsoleOutputListener;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mOnConsoleOutputListener:Lcom/hcn/common/misc/LogUtils$OnConsoleOutputListener;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fgetmOnFileOutputListener(Lcom/hcn/common/misc/LogUtils$Config;)Lcom/hcn/common/misc/LogUtils$OnFileOutputListener;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mOnFileOutputListener:Lcom/hcn/common/misc/LogUtils$OnFileOutputListener;

    return-object p0
.end method

.method static bridge synthetic -$$Nest$fgetmTagIsSpace(Lcom/hcn/common/misc/LogUtils$Config;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mTagIsSpace:Z

    return p0
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 666
    const-class v0, Lcom/hcn/common/misc/LogUtils;

    return-void
.end method

.method private constructor <init>()V
    .locals 4

    .line 690
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, "util"

    .line 669
    iput-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFilePrefix:Ljava/lang/String;

    const-string v0, ".txt"

    .line 670
    iput-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileExtension:Ljava/lang/String;

    const/4 v0, 0x1

    .line 671
    iput-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogSwitch:Z

    .line 672
    iput-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLog2ConsoleSwitch:Z

    const-string v1, ""

    .line 673
    iput-object v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mGlobalTag:Ljava/lang/String;

    .line 674
    iput-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mTagIsSpace:Z

    .line 675
    iput-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogHeadSwitch:Z

    const/4 v1, 0x0

    .line 676
    iput-boolean v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLog2FileSwitch:Z

    .line 677
    iput-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogBorderSwitch:Z

    .line 678
    iput-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mSingleTagSwitch:Z

    const/4 v2, 0x2

    .line 679
    iput v2, p0, Lcom/hcn/common/misc/LogUtils$Config;->mConsoleFilter:I

    .line 680
    iput v2, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileFilter:I

    .line 681
    iput v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mStackDeep:I

    .line 682
    iput v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mStackOffset:I

    const/4 v0, -0x1

    .line 683
    iput v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mSaveDays:I

    .line 684
    invoke-static {}, Lcom/hcn/common/app/HProcessUtils;->getCurrentProcessName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mProcessName:Ljava/lang/String;

    .line 688
    new-instance v0, Lcom/hcn/common/utils/HUtilsEx$FileHead;

    const-string v1, "Log"

    invoke-direct {v0, v1}, Lcom/hcn/common/utils/HUtilsEx$FileHead;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileHead:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    .line 691
    invoke-static {}, Lcom/hcn/common/utils/HSDCardUtils;->isSDCardEnableByEnvironment()Z

    move-result v0

    const-string v1, "log"

    if-eqz v0, :cond_0

    .line 692
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const/4 v2, 0x0

    invoke-virtual {v0, v2}, Landroid/app/Application;->getExternalFilesDir(Ljava/lang/String;)Ljava/io/File;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 693
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/app/Application;->getExternalFilesDir(Ljava/lang/String;)Ljava/io/File;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetFILE_SEP()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetFILE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mDefaultDir:Ljava/lang/String;

    goto :goto_0

    .line 695
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v2

    invoke-virtual {v2}, Landroid/app/Application;->getFilesDir()Ljava/io/File;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetFILE_SEP()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetFILE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mDefaultDir:Ljava/lang/String;

    :goto_0
    return-void
.end method

.method synthetic constructor <init>(Lcom/hcn/common/misc/LogUtils$Config-IA;)V
    .locals 0

    invoke-direct {p0}, Lcom/hcn/common/misc/LogUtils$Config;-><init>()V

    return-void
.end method


# virtual methods
.method public final addFileExtraHead(Ljava/lang/String;Ljava/lang/String;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 1

    .line 830
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileHead:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    invoke-virtual {v0, p1, p2}, Lcom/hcn/common/utils/HUtilsEx$FileHead;->append(Ljava/lang/String;Ljava/lang/String;)V

    return-object p0
.end method

.method public final addFileExtraHead(Ljava/util/Map;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;)",
            "Lcom/hcn/common/misc/LogUtils$Config;"
        }
    .end annotation

    .line 825
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileHead:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    invoke-virtual {v0, p1}, Lcom/hcn/common/utils/HUtilsEx$FileHead;->append(Ljava/util/Map;)V

    return-object p0
.end method

.method public final addFormatter(Lcom/hcn/common/misc/LogUtils$IFormatter;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "<T:",
            "Ljava/lang/Object;",
            ">(",
            "Lcom/hcn/common/misc/LogUtils$IFormatter<",
            "TT;>;)",
            "Lcom/hcn/common/misc/LogUtils$Config;"
        }
    .end annotation

    if-eqz p1, :cond_0

    .line 804
    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetI_FORMATTER_MAP()Landroidx/collection/SimpleArrayMap;

    move-result-object v0

    invoke-static {p1}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$smgetTypeClassFromParadigm(Lcom/hcn/common/misc/LogUtils$IFormatter;)Ljava/lang/Class;

    move-result-object v1

    invoke-virtual {v0, v1, p1}, Landroidx/collection/SimpleArrayMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_0
    return-object p0
.end method

.method public final getConsoleFilter()C
    .locals 2

    .line 891
    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetT()[C

    move-result-object v0

    iget v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mConsoleFilter:I

    add-int/lit8 v1, v1, -0x2

    aget-char v0, v0, v1

    return v0
.end method

.method public final getDefaultDir()Ljava/lang/String;
    .locals 1

    .line 843
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mDefaultDir:Ljava/lang/String;

    return-object v0
.end method

.method public final getDir()Ljava/lang/String;
    .locals 1

    .line 847
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mDir:Ljava/lang/String;

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mDefaultDir:Ljava/lang/String;

    :cond_0
    return-object v0
.end method

.method public final getFileExtension()Ljava/lang/String;
    .locals 1

    .line 855
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileExtension:Ljava/lang/String;

    return-object v0
.end method

.method public final getFileFilter()C
    .locals 2

    .line 895
    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetT()[C

    move-result-object v0

    iget v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileFilter:I

    add-int/lit8 v1, v1, -0x2

    aget-char v0, v0, v1

    return v0
.end method

.method public final getFilePrefix()Ljava/lang/String;
    .locals 1

    .line 851
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFilePrefix:Ljava/lang/String;

    return-object v0
.end method

.method public final getGlobalTag()Ljava/lang/String;
    .locals 1

    .line 867
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mGlobalTag:Ljava/lang/String;

    invoke-static {v0}, Lcom/hcn/common/utils/HStringUtils;->isSpace(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, ""

    return-object v0

    .line 871
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mGlobalTag:Ljava/lang/String;

    return-object v0
.end method

.method public final getProcessName()Ljava/lang/String;
    .locals 3

    .line 835
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mProcessName:Ljava/lang/String;

    if-nez v0, :cond_0

    const-string v0, ""

    return-object v0

    :cond_0
    const-string v1, ":"

    const-string v2, "_"

    .line 839
    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public final getSaveDays()I
    .locals 1

    .line 907
    iget v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mSaveDays:I

    return v0
.end method

.method public final getStackDeep()I
    .locals 1

    .line 899
    iget v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mStackDeep:I

    return v0
.end method

.method public final getStackOffset()I
    .locals 1

    .line 903
    iget v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mStackOffset:I

    return v0
.end method

.method public final haveSetOnConsoleOutputListener()Z
    .locals 1

    .line 911
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mOnConsoleOutputListener:Lcom/hcn/common/misc/LogUtils$OnConsoleOutputListener;

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public final haveSetOnFileOutputListener()Z
    .locals 1

    .line 915
    iget-object v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mOnFileOutputListener:Lcom/hcn/common/misc/LogUtils$OnFileOutputListener;

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public final isLog2ConsoleSwitch()Z
    .locals 1

    .line 863
    iget-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLog2ConsoleSwitch:Z

    return v0
.end method

.method public final isLog2FileSwitch()Z
    .locals 1

    .line 879
    iget-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLog2FileSwitch:Z

    return v0
.end method

.method public final isLogBorderSwitch()Z
    .locals 1

    .line 883
    iget-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogBorderSwitch:Z

    return v0
.end method

.method public final isLogHeadSwitch()Z
    .locals 1

    .line 875
    iget-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogHeadSwitch:Z

    return v0
.end method

.method public final isLogSwitch()Z
    .locals 1

    .line 859
    iget-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogSwitch:Z

    return v0
.end method

.method public final isSingleTagSwitch()Z
    .locals 1

    .line 887
    iget-boolean v0, p0, Lcom/hcn/common/misc/LogUtils$Config;->mSingleTagSwitch:Z

    return v0
.end method

.method public final setBorderSwitch(Z)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 768
    iput-boolean p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogBorderSwitch:Z

    return-object p0
.end method

.method public final setConsoleFilter(I)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 778
    iput p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mConsoleFilter:I

    return-object p0
.end method

.method public final setConsoleSwitch(Z)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 705
    iput-boolean p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLog2ConsoleSwitch:Z

    return-object p0
.end method

.method public final setDir(Ljava/io/File;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 1

    if-nez p1, :cond_0

    const/4 p1, 0x0

    goto :goto_0

    .line 741
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetFILE_SEP()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    :goto_0
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mDir:Ljava/lang/String;

    return-object p0
.end method

.method public final setDir(Ljava/lang/String;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 1

    .line 731
    invoke-static {p1}, Lcom/hcn/common/utils/HStringUtils;->isSpace(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p1, 0x0

    .line 732
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mDir:Ljava/lang/String;

    goto :goto_1

    .line 735
    :cond_0
    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetFILE_SEP()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetFILE_SEP()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    :goto_0
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mDir:Ljava/lang/String;

    :goto_1
    return-object p0
.end method

.method public final setFileExtension(Ljava/lang/String;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 2

    .line 755
    invoke-static {p1}, Lcom/hcn/common/utils/HStringUtils;->isSpace(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string p1, ".txt"

    .line 756
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileExtension:Ljava/lang/String;

    goto :goto_0

    :cond_0
    const-string v0, "."

    .line 758
    invoke-virtual {p1, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 759
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileExtension:Ljava/lang/String;

    goto :goto_0

    .line 761
    :cond_1
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileExtension:Ljava/lang/String;

    :goto_0
    return-object p0
.end method

.method public final setFileFilter(I)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 783
    iput p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileFilter:I

    return-object p0
.end method

.method public final setFilePrefix(Ljava/lang/String;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 1

    .line 746
    invoke-static {p1}, Lcom/hcn/common/utils/HStringUtils;->isSpace(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string p1, "util"

    .line 747
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFilePrefix:Ljava/lang/String;

    goto :goto_0

    .line 749
    :cond_0
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFilePrefix:Ljava/lang/String;

    :goto_0
    return-object p0
.end method

.method public final setFileWriter(Lcom/hcn/common/misc/LogUtils$IFileWriter;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 810
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileWriter:Lcom/hcn/common/misc/LogUtils$IFileWriter;

    return-object p0
.end method

.method public final setGlobalTag(Ljava/lang/String;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 1

    .line 710
    invoke-static {p1}, Lcom/hcn/common/utils/HStringUtils;->isSpace(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string p1, ""

    .line 711
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mGlobalTag:Ljava/lang/String;

    const/4 p1, 0x1

    .line 712
    iput-boolean p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mTagIsSpace:Z

    goto :goto_0

    .line 714
    :cond_0
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mGlobalTag:Ljava/lang/String;

    const/4 p1, 0x0

    .line 715
    iput-boolean p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mTagIsSpace:Z

    :goto_0
    return-object p0
.end method

.method public final setLog2FileSwitch(Z)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 726
    iput-boolean p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLog2FileSwitch:Z

    return-object p0
.end method

.method public final setLogHeadSwitch(Z)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 721
    iput-boolean p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogHeadSwitch:Z

    return-object p0
.end method

.method public final setLogSwitch(Z)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 700
    iput-boolean p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mLogSwitch:Z

    return-object p0
.end method

.method public final setOnConsoleOutputListener(Lcom/hcn/common/misc/LogUtils$OnConsoleOutputListener;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 815
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mOnConsoleOutputListener:Lcom/hcn/common/misc/LogUtils$OnConsoleOutputListener;

    return-object p0
.end method

.method public final setOnFileOutputListener(Lcom/hcn/common/misc/LogUtils$OnFileOutputListener;)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 820
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mOnFileOutputListener:Lcom/hcn/common/misc/LogUtils$OnFileOutputListener;

    return-object p0
.end method

.method public final setSaveDays(I)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 798
    iput p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mSaveDays:I

    return-object p0
.end method

.method public final setSingleTagSwitch(Z)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 773
    iput-boolean p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mSingleTagSwitch:Z

    return-object p0
.end method

.method public final setStackDeep(I)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 788
    iput p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mStackDeep:I

    return-object p0
.end method

.method public final setStackOffset(I)Lcom/hcn/common/misc/LogUtils$Config;
    .locals 0

    .line 793
    iput p1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mStackOffset:I

    return-object p0
.end method

.method public toString()Ljava/lang/String;
    .locals 3

    .line 920
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "process: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getProcessName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "logSwitch: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 921
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->isLogSwitch()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "consoleSwitch: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 922
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->isLog2ConsoleSwitch()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "tag: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 923
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getGlobalTag()Ljava/lang/String;

    move-result-object v1

    const-string v2, ""

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    const-string v1, "null"

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getGlobalTag()Ljava/lang/String;

    move-result-object v1

    :goto_0
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "headSwitch: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 924
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->isLogHeadSwitch()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "fileSwitch: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 925
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->isLog2FileSwitch()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "dir: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 926
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getDir()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "filePrefix: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 927
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getFilePrefix()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "borderSwitch: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 928
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->isLogBorderSwitch()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "singleTagSwitch: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 929
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->isSingleTagSwitch()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "consoleFilter: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 930
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getConsoleFilter()C

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "fileFilter: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 931
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getFileFilter()C

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "stackDeep: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 932
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getStackDeep()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "stackOffset: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 933
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getStackOffset()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "saveDays: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 934
    invoke-virtual {p0}, Lcom/hcn/common/misc/LogUtils$Config;->getSaveDays()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "formatter: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetI_FORMATTER_MAP()Landroidx/collection/SimpleArrayMap;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "fileWriter: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileWriter:Lcom/hcn/common/misc/LogUtils$IFileWriter;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "onConsoleOutputListener: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mOnConsoleOutputListener:Lcom/hcn/common/misc/LogUtils$OnConsoleOutputListener;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "onFileOutputListener: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mOnFileOutputListener:Lcom/hcn/common/misc/LogUtils$OnFileOutputListener;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$sfgetLINE_SEP()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "fileExtraHeader: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/common/misc/LogUtils$Config;->mFileHead:Lcom/hcn/common/utils/HUtilsEx$FileHead;

    .line 939
    invoke-virtual {v1}, Lcom/hcn/common/utils/HUtilsEx$FileHead;->getAppended()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
