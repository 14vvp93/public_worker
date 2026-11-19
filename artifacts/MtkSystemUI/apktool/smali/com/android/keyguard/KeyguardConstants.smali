.class public Lcom/android/keyguard/KeyguardConstants;
.super Ljava/lang/Object;
.source "KeyguardConstants.java"


# static fields
.field public static final DEBUG:Z

.field public static final DEBUG_SIM_STATES:Z

.field private static final IS_ENG_BUILD:Z


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 26
    const-string v0, "ro.build.type"

    invoke-static {v0}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "eng"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/android/keyguard/KeyguardConstants;->IS_ENG_BUILD:Z

    .line 32
    sget-boolean v0, Lcom/android/keyguard/KeyguardConstants;->IS_ENG_BUILD:Z

    sput-boolean v0, Lcom/android/keyguard/KeyguardConstants;->DEBUG:Z

    .line 33
    sget-boolean v0, Lcom/android/keyguard/KeyguardConstants;->IS_ENG_BUILD:Z

    sput-boolean v0, Lcom/android/keyguard/KeyguardConstants;->DEBUG_SIM_STATES:Z

    return-void
.end method
