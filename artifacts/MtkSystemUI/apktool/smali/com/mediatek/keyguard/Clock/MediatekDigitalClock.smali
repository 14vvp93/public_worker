.class public Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;
.super Landroid/widget/LinearLayout;
.source "MediatekDigitalClock.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$FormatChangeObserver;,
        Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;
    }
.end annotation


# instance fields
.field private mAmPm:Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;

.field private mAttached:Z

.field private mCalendar:Ljava/util/Calendar;

.field private mFormatChangeObserver:Landroid/database/ContentObserver;

.field private final mHandler:Landroid/os/Handler;

.field private mHoursFormat:Ljava/lang/String;

.field private final mIntentReceiver:Landroid/content/BroadcastReceiver;

.field private mLive:Z

.field private mTimeDisplayHours:Landroid/widget/TextView;

.field private mTimeDisplayMinutes:Landroid/widget/TextView;

.field private mTimeZoneId:Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 113
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 114
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 117
    invoke-direct {p0, p1, p2}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 54
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mLive:Z

    .line 59
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mHandler:Landroid/os/Handler;

    .line 60
    new-instance v0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$1;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$1;-><init>(Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mIntentReceiver:Landroid/content/BroadcastReceiver;

    .line 118
    return-void
.end method

.method static synthetic access$000(Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;)Z
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;

    .line 43
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mLive:Z

    return v0
.end method

.method static synthetic access$102(Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;Ljava/util/Calendar;)Ljava/util/Calendar;
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;
    .param p1, "x1"    # Ljava/util/Calendar;

    .line 43
    iput-object p1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mCalendar:Ljava/util/Calendar;

    return-object p1
.end method

.method static synthetic access$200(Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;

    .line 43
    invoke-direct {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->updateTime()V

    return-void
.end method

.method static synthetic access$300(Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;

    .line 43
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$400(Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;

    .line 43
    invoke-direct {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->setDateFormat()V

    return-void
.end method

.method private setDateFormat()V
    .locals 2

    .line 214
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-static {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/Alarms;->get24HourMode(Landroid/content/Context;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string v0, "kk"

    goto :goto_0

    :cond_0
    const-string v0, "h"

    :goto_0
    iput-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mHoursFormat:Ljava/lang/String;

    .line 215
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mAmPm:Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;

    invoke-virtual {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-static {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/Alarms;->get24HourMode(Landroid/content/Context;)Z

    move-result v1

    xor-int/lit8 v1, v1, 0x1

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->setShowAmPm(Z)V

    .line 216
    return-void
.end method

.method private updateTime()V
    .locals 4

    .line 188
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mLive:Z

    if-eqz v0, :cond_0

    .line 189
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mCalendar:Ljava/util/Calendar;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    invoke-virtual {v0, v1, v2}, Ljava/util/Calendar;->setTimeInMillis(J)V

    .line 191
    :cond_0
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mTimeZoneId:Ljava/lang/String;

    if-eqz v0, :cond_1

    .line 192
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mCalendar:Ljava/util/Calendar;

    iget-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mTimeZoneId:Ljava/lang/String;

    invoke-static {v1}, Ljava/util/TimeZone;->getTimeZone(Ljava/lang/String;)Ljava/util/TimeZone;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/Calendar;->setTimeZone(Ljava/util/TimeZone;)V

    .line 195
    :cond_1
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 196
    .local v0, "fullTimeStr":Ljava/lang/StringBuilder;
    iget-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mHoursFormat:Ljava/lang/String;

    iget-object v2, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mCalendar:Ljava/util/Calendar;

    invoke-static {v1, v2}, Landroid/text/format/DateFormat;->format(Ljava/lang/CharSequence;Ljava/util/Calendar;)Ljava/lang/CharSequence;

    move-result-object v1

    .line 197
    .local v1, "newTime":Ljava/lang/CharSequence;
    iget-object v2, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mTimeDisplayHours:Landroid/widget/TextView;

    invoke-virtual {v2, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 198
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    .line 199
    const-string v2, ":mm"

    iget-object v3, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mCalendar:Ljava/util/Calendar;

    invoke-static {v2, v3}, Landroid/text/format/DateFormat;->format(Ljava/lang/CharSequence;Ljava/util/Calendar;)Ljava/lang/CharSequence;

    move-result-object v1

    .line 200
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    .line 201
    iget-object v2, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mTimeDisplayMinutes:Landroid/widget/TextView;

    invoke-virtual {v2, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 203
    iget-object v2, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mCalendar:Ljava/util/Calendar;

    const/16 v3, 0x9

    invoke-virtual {v2, v3}, Ljava/util/Calendar;->get(I)I

    move-result v2

    if-nez v2, :cond_2

    const/4 v2, 0x1

    goto :goto_0

    :cond_2
    const/4 v2, 0x0

    .line 204
    .local v2, "isMorning":Z
    :goto_0
    iget-object v3, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mAmPm:Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;

    invoke-virtual {v3, v2}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->setIsMorning(Z)V

    .line 205
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-static {v3}, Lcom/mediatek/keyguard/PowerOffAlarm/Alarms;->get24HourMode(Landroid/content/Context;)Z

    move-result v3

    if-nez v3, :cond_3

    .line 206
    iget-object v3, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mAmPm:Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;

    invoke-virtual {v3}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->getAmPmText()Ljava/lang/CharSequence;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;

    .line 210
    :cond_3
    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 211
    return-void
.end method


# virtual methods
.method protected onAttachedToWindow()V
    .locals 4

    .line 134
    invoke-super {p0}, Landroid/widget/LinearLayout;->onAttachedToWindow()V

    .line 136
    const-string v0, "PowerOffAlarm"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onAttachedToWindow "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 138
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mAttached:Z

    if-eqz v0, :cond_0

    return-void

    .line 139
    :cond_0
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mAttached:Z

    .line 141
    iget-boolean v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mLive:Z

    if-eqz v1, :cond_1

    .line 143
    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    .line 145
    .local v1, "filter":Landroid/content/IntentFilter;
    const-string v2, "android.intent.action.TIME_SET"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 146
    const-string v2, "android.intent.action.TIMEZONE_CHANGED"

    invoke-virtual {v1, v2}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 147
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->getContext()Landroid/content/Context;

    move-result-object v2

    iget-object v3, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mIntentReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v2, v3, v1}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 151
    .end local v1    # "filter":Landroid/content/IntentFilter;
    :cond_1
    new-instance v1, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$FormatChangeObserver;

    invoke-direct {v1, p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$FormatChangeObserver;-><init>(Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;)V

    iput-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mFormatChangeObserver:Landroid/database/ContentObserver;

    .line 152
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    sget-object v2, Landroid/provider/Settings$System;->CONTENT_URI:Landroid/net/Uri;

    iget-object v3, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mFormatChangeObserver:Landroid/database/ContentObserver;

    invoke-virtual {v1, v2, v0, v3}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;)V

    .line 155
    invoke-direct {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->updateTime()V

    .line 156
    return-void
.end method

.method protected onDetachedFromWindow()V
    .locals 2

    .line 160
    invoke-super {p0}, Landroid/widget/LinearLayout;->onDetachedFromWindow()V

    .line 162
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mAttached:Z

    if-nez v0, :cond_0

    return-void

    .line 163
    :cond_0
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mAttached:Z

    .line 165
    iget-boolean v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mLive:Z

    if-eqz v0, :cond_1

    .line 166
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->getContext()Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mIntentReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    .line 168
    :cond_1
    invoke-virtual {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    iget-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mFormatChangeObserver:Landroid/database/ContentObserver;

    invoke-virtual {v0, v1}, Landroid/content/ContentResolver;->unregisterContentObserver(Landroid/database/ContentObserver;)V

    .line 170
    return-void
.end method

.method protected onFinishInflate()V
    .locals 1

    .line 122
    invoke-super {p0}, Landroid/widget/LinearLayout;->onFinishInflate()V

    .line 124
    const v0, 0x7f0a03ff

    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mTimeDisplayHours:Landroid/widget/TextView;

    .line 125
    const v0, 0x7f0a0400

    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mTimeDisplayMinutes:Landroid/widget/TextView;

    .line 126
    new-instance v0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;

    invoke-direct {v0, p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;-><init>(Landroid/view/View;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mAmPm:Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;

    .line 127
    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->mCalendar:Ljava/util/Calendar;

    .line 129
    invoke-direct {p0}, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;->setDateFormat()V

    .line 130
    return-void
.end method
