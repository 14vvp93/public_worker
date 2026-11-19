.class public Lcom/mediatek/keyguard/PowerOffAlarm/Alarms;
.super Ljava/lang/Object;
.source "Alarms.java"


# direct methods
.method public static get24HourMode(Landroid/content/Context;)Z
    .locals 1
    .param p0, "context"    # Landroid/content/Context;

    .line 660
    invoke-static {p0}, Landroid/text/format/DateFormat;->is24HourFormat(Landroid/content/Context;)Z

    move-result v0

    return v0
.end method
