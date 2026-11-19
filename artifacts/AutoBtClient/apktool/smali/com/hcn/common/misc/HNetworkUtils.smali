.class public Lcom/hcn/common/misc/HNetworkUtils;
.super Ljava/lang/Object;
.source "HNetworkUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/misc/HNetworkUtils$NetworkType;,
        Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;,
        Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;,
        Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;
    }
.end annotation


# static fields
.field private static final SCAN_PERIOD_MILLIS:J = 0xbb8L

.field private static final SCAN_RESULT_CONSUMERS:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;",
            ">;>;"
        }
    .end annotation
.end field

.field private static sPreWifiScanResults:Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

.field private static sScanWifiTimer:Ljava/util/Timer;


# direct methods
.method static bridge synthetic -$$Nest$sfgetSCAN_RESULT_CONSUMERS()Ljava/util/Set;
    .locals 1

    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils;->SCAN_RESULT_CONSUMERS:Ljava/util/Set;

    return-object v0
.end method

.method static bridge synthetic -$$Nest$sfgetsPreWifiScanResults()Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;
    .locals 1

    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils;->sPreWifiScanResults:Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    return-object v0
.end method

.method static bridge synthetic -$$Nest$sfputsPreWifiScanResults(Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;)V
    .locals 0

    sput-object p0, Lcom/hcn/common/misc/HNetworkUtils;->sPreWifiScanResults:Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    return-void
.end method

.method static bridge synthetic -$$Nest$smisSameScanResults(Ljava/util/List;Ljava/util/List;)Z
    .locals 0

    invoke-static {p0, p1}, Lcom/hcn/common/misc/HNetworkUtils;->isSameScanResults(Ljava/util/List;Ljava/util/List;)Z

    move-result p0

    return p0
.end method

.method static bridge synthetic -$$Nest$smstartScanWifi()V
    .locals 0

    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->startScanWifi()V

    return-void
.end method

.method static bridge synthetic -$$Nest$smstartScanWifiIfEnabled()V
    .locals 0

    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->startScanWifiIfEnabled()V

    return-void
.end method

.method static bridge synthetic -$$Nest$smstopScanWifi()V
    .locals 0

    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->stopScanWifi()V

    return-void
.end method

.method static constructor <clinit>()V
    .locals 1

    .line 826
    new-instance v0, Ljava/util/concurrent/CopyOnWriteArraySet;

    invoke-direct {v0}, Ljava/util/concurrent/CopyOnWriteArraySet;-><init>()V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils;->SCAN_RESULT_CONSUMERS:Ljava/util/Set;

    return-void
.end method

.method private constructor <init>()V
    .locals 2

    .line 58
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 59
    new-instance v0, Ljava/lang/UnsupportedOperationException;

    const-string v1, "u can\'t instantiate me..."

    invoke-direct {v0, v1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public static addOnWifiChangedConsumer(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;",
            ">;)V"
        }
    .end annotation

    if-nez p0, :cond_0

    return-void

    .line 836
    :cond_0
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$7;

    invoke-direct {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils$7;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method private static getActiveNetworkInfo()Landroid/net/NetworkInfo;
    .locals 2

    .line 542
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "connectivity"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 547
    :cond_0
    invoke-virtual {v0}, Landroid/net/ConnectivityManager;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    return-object v0
.end method

.method public static getBroadcastIpAddress()Ljava/lang/String;
    .locals 5

    .line 625
    :try_start_0
    invoke-static {}, Ljava/net/NetworkInterface;->getNetworkInterfaces()Ljava/util/Enumeration;

    move-result-object v0

    .line 626
    new-instance v1, Ljava/util/LinkedList;

    invoke-direct {v1}, Ljava/util/LinkedList;-><init>()V

    .line 627
    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v1

    if-eqz v1, :cond_3

    .line 628
    invoke-interface {v0}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/net/NetworkInterface;

    .line 629
    invoke-virtual {v1}, Ljava/net/NetworkInterface;->isUp()Z

    move-result v2

    if-eqz v2, :cond_0

    invoke-virtual {v1}, Ljava/net/NetworkInterface;->isLoopback()Z

    move-result v2

    if-eqz v2, :cond_1

    goto :goto_0

    .line 633
    :cond_1
    invoke-virtual {v1}, Ljava/net/NetworkInterface;->getInterfaceAddresses()Ljava/util/List;

    move-result-object v1

    const/4 v2, 0x0

    .line 634
    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v3

    :goto_1
    if-ge v2, v3, :cond_0

    .line 635
    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/net/InterfaceAddress;

    .line 636
    invoke-virtual {v4}, Ljava/net/InterfaceAddress;->getBroadcast()Ljava/net/InetAddress;

    move-result-object v4

    if-eqz v4, :cond_2

    .line 638
    invoke-virtual {v4}, Ljava/net/InetAddress;->getHostAddress()Ljava/lang/String;

    move-result-object v0
    :try_end_0
    .catch Ljava/net/SocketException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v0

    :cond_2
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    :catch_0
    move-exception v0

    .line 643
    invoke-virtual {v0}, Ljava/net/SocketException;->printStackTrace()V

    :cond_3
    const-string v0, ""

    return-object v0
.end method

.method public static getDomainAddress(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 679
    :try_start_0
    invoke-static {p0}, Ljava/net/InetAddress;->getByName(Ljava/lang/String;)Ljava/net/InetAddress;

    move-result-object p0

    .line 680
    invoke-virtual {p0}, Ljava/net/InetAddress;->getHostAddress()Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catch Ljava/net/UnknownHostException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p0

    :catch_0
    move-exception p0

    .line 682
    invoke-virtual {p0}, Ljava/net/UnknownHostException;->printStackTrace()V

    const-string p0, ""

    return-object p0
.end method

.method public static getDomainAddressAsync(Ljava/lang/String;Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/String;",
            ">;)",
            "Lcom/hcn/common/utils/HUtilsEx$Task<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 659
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$6;

    invoke-direct {v0, p1, p0}, Lcom/hcn/common/misc/HNetworkUtils$6;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;Ljava/lang/String;)V

    invoke-static {v0}, Lcom/hcn/common/utils/HUtilsEx;->doAsync(Lcom/hcn/common/utils/HUtilsEx$Task;)Lcom/hcn/common/utils/HUtilsEx$Task;

    move-result-object p0

    return-object p0
.end method

.method public static getGatewayByWifi()Ljava/lang/String;
    .locals 2

    .line 711
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "wifi"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    if-nez v0, :cond_0

    const-string v0, ""

    return-object v0

    .line 716
    :cond_0
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->getDhcpInfo()Landroid/net/DhcpInfo;

    move-result-object v0

    iget v0, v0, Landroid/net/DhcpInfo;->gateway:I

    invoke-static {v0}, Landroid/text/format/Formatter;->formatIpAddress(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getIPAddress(Z)Ljava/lang/String;
    .locals 4

    .line 579
    :try_start_0
    invoke-static {}, Ljava/net/NetworkInterface;->getNetworkInterfaces()Ljava/util/Enumeration;

    move-result-object v0

    .line 580
    new-instance v1, Ljava/util/LinkedList;

    invoke-direct {v1}, Ljava/util/LinkedList;-><init>()V

    .line 581
    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v2

    if-eqz v2, :cond_2

    .line 582
    invoke-interface {v0}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/net/NetworkInterface;

    .line 584
    invoke-virtual {v2}, Ljava/net/NetworkInterface;->isUp()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-virtual {v2}, Ljava/net/NetworkInterface;->isLoopback()Z

    move-result v3

    if-eqz v3, :cond_1

    goto :goto_0

    .line 588
    :cond_1
    invoke-virtual {v2}, Ljava/net/NetworkInterface;->getInetAddresses()Ljava/util/Enumeration;

    move-result-object v2

    .line 589
    :goto_1
    invoke-interface {v2}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v3

    if-eqz v3, :cond_0

    .line 590
    invoke-interface {v2}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/net/InetAddress;

    invoke-virtual {v1, v3}, Ljava/util/LinkedList;->addFirst(Ljava/lang/Object;)V

    goto :goto_1

    .line 594
    :cond_2
    invoke-virtual {v1}, Ljava/util/LinkedList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_3
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_7

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/net/InetAddress;

    .line 595
    invoke-virtual {v1}, Ljava/net/InetAddress;->isLoopbackAddress()Z

    move-result v2

    if-nez v2, :cond_3

    .line 596
    invoke-virtual {v1}, Ljava/net/InetAddress;->getHostAddress()Ljava/lang/String;

    move-result-object v1

    const/16 v2, 0x3a

    .line 597
    invoke-virtual {v1, v2}, Ljava/lang/String;->indexOf(I)I

    move-result v2

    const/4 v3, 0x0

    if-gez v2, :cond_4

    const/4 v2, 0x1

    goto :goto_2

    :cond_4
    move v2, v3

    :goto_2
    if-eqz p0, :cond_5

    if-eqz v2, :cond_3

    return-object v1

    :cond_5
    if-nez v2, :cond_3

    const/16 p0, 0x25

    .line 604
    invoke-virtual {v1, p0}, Ljava/lang/String;->indexOf(I)I

    move-result p0

    if-gez p0, :cond_6

    .line 606
    invoke-virtual {v1}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object p0

    goto :goto_3

    .line 607
    :cond_6
    invoke-virtual {v1, v3, p0}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catch Ljava/net/SocketException; {:try_start_0 .. :try_end_0} :catch_0

    :goto_3
    return-object p0

    :catch_0
    move-exception p0

    .line 613
    invoke-virtual {p0}, Ljava/net/SocketException;->printStackTrace()V

    :cond_7
    const-string p0, ""

    return-object p0
.end method

.method public static getIPAddressAsync(ZLcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(Z",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/String;",
            ">;)",
            "Lcom/hcn/common/utils/HUtilsEx$Task<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 560
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$5;

    invoke-direct {v0, p1, p0}, Lcom/hcn/common/misc/HNetworkUtils$5;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;Z)V

    invoke-static {v0}, Lcom/hcn/common/utils/HUtilsEx;->doAsync(Lcom/hcn/common/utils/HUtilsEx$Task;)Lcom/hcn/common/utils/HUtilsEx$Task;

    move-result-object p0

    return-object p0
.end method

.method public static getIpAddressByWifi()Ljava/lang/String;
    .locals 2

    .line 695
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "wifi"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    if-nez v0, :cond_0

    const-string v0, ""

    return-object v0

    .line 700
    :cond_0
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->getDhcpInfo()Landroid/net/DhcpInfo;

    move-result-object v0

    iget v0, v0, Landroid/net/DhcpInfo;->ipAddress:I

    invoke-static {v0}, Landroid/text/format/Formatter;->formatIpAddress(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getMobileDataEnabled()Z
    .locals 5

    const/4 v0, 0x0

    .line 256
    :try_start_0
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v1

    const-string v2, "phone"

    invoke-virtual {v1, v2}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/telephony/TelephonyManager;

    if-nez v1, :cond_0

    return v0

    .line 261
    :cond_0
    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x1a

    if-lt v2, v3, :cond_1

    .line 262
    invoke-virtual {v1}, Landroid/telephony/TelephonyManager;->isDataEnabled()Z

    move-result v0

    return v0

    .line 267
    :cond_1
    invoke-virtual {v1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v2

    const-string v3, "getDataEnabled"

    new-array v4, v0, [Ljava/lang/Class;

    invoke-virtual {v2, v3, v4}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v2

    new-array v3, v0, [Ljava/lang/Object;

    .line 268
    invoke-virtual {v2, v1, v3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Boolean;

    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v1

    .line 270
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    return v0
.end method

.method public static getNetMaskByWifi()Ljava/lang/String;
    .locals 2

    .line 727
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "wifi"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    if-nez v0, :cond_0

    const-string v0, ""

    return-object v0

    .line 732
    :cond_0
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->getDhcpInfo()Landroid/net/DhcpInfo;

    move-result-object v0

    iget v0, v0, Landroid/net/DhcpInfo;->netmask:I

    invoke-static {v0}, Landroid/text/format/Formatter;->formatIpAddress(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getNetworkOperatorName()Ljava/lang/String;
    .locals 2

    .line 434
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "phone"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/TelephonyManager;

    if-nez v0, :cond_0

    const-string v0, ""

    return-object v0

    .line 439
    :cond_0
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getNetworkOperatorName()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getNetworkType()Lcom/hcn/common/misc/HNetworkUtils$NetworkType;
    .locals 3

    .line 459
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->isEthernet()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 460
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_ETHERNET:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 462
    :cond_0
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    if-eqz v0, :cond_5

    .line 463
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isAvailable()Z

    move-result v1

    if-eqz v1, :cond_5

    .line 464
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getType()I

    move-result v1

    const/4 v2, 0x1

    if-ne v1, v2, :cond_1

    .line 465
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_WIFI:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 466
    :cond_1
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getType()I

    move-result v1

    if-nez v1, :cond_4

    .line 467
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getSubtype()I

    move-result v1

    packed-switch v1, :pswitch_data_0

    .line 495
    :pswitch_0
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getSubtypeName()Ljava/lang/String;

    move-result-object v0

    const-string v1, "TD-SCDMA"

    .line 496
    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_3

    const-string v1, "WCDMA"

    .line 497
    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_3

    const-string v1, "CDMA2000"

    .line 498
    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_2

    goto :goto_0

    .line 493
    :pswitch_1
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_5G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 490
    :pswitch_2
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_4G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 486
    :pswitch_3
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_3G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 474
    :pswitch_4
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_2G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 501
    :cond_2
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_UNKNOWN:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 499
    :cond_3
    :goto_0
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_3G:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 505
    :cond_4
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_UNKNOWN:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    .line 508
    :cond_5
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils$NetworkType;->NETWORK_NO:Lcom/hcn/common/misc/HNetworkUtils$NetworkType;

    return-object v0

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_4
        :pswitch_4
        :pswitch_3
        :pswitch_4
        :pswitch_3
        :pswitch_3
        :pswitch_4
        :pswitch_3
        :pswitch_3
        :pswitch_3
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_3
        :pswitch_3
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method public static getSSID()Ljava/lang/String;
    .locals 4

    .line 758
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Application;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "wifi"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    const-string v1, ""

    if-nez v0, :cond_0

    return-object v1

    .line 763
    :cond_0
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->getConnectionInfo()Landroid/net/wifi/WifiInfo;

    move-result-object v0

    if-nez v0, :cond_1

    return-object v1

    .line 768
    :cond_1
    invoke-virtual {v0}, Landroid/net/wifi/WifiInfo;->getSSID()Ljava/lang/String;

    move-result-object v0

    .line 769
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_2

    return-object v1

    .line 772
    :cond_2
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    const/4 v2, 0x2

    if-le v1, v2, :cond_3

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/lang/String;->charAt(I)C

    move-result v1

    const/16 v2, 0x22

    if-ne v1, v2, :cond_3

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    const/4 v3, 0x1

    sub-int/2addr v1, v3

    invoke-virtual {v0, v1}, Ljava/lang/String;->charAt(I)C

    move-result v1

    if-ne v1, v2, :cond_3

    .line 773
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    sub-int/2addr v1, v3

    invoke-virtual {v0, v3, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v0

    :cond_3
    return-object v0
.end method

.method public static getServerAddressByWifi()Ljava/lang/String;
    .locals 2

    .line 743
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "wifi"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    if-nez v0, :cond_0

    const-string v0, ""

    return-object v0

    .line 748
    :cond_0
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->getDhcpInfo()Landroid/net/DhcpInfo;

    move-result-object v0

    iget v0, v0, Landroid/net/DhcpInfo;->serverAddress:I

    invoke-static {v0}, Landroid/text/format/Formatter;->formatIpAddress(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static getWifiEnabled()Z
    .locals 2

    .line 349
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "wifi"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return v0

    .line 354
    :cond_0
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->isWifiEnabled()Z

    move-result v0

    return v0
.end method

.method public static getWifiScanResult()Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;
    .locals 3

    .line 809
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    invoke-direct {v0}, Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;-><init>()V

    .line 810
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getWifiEnabled()Z

    move-result v1

    if-nez v1, :cond_0

    return-object v0

    .line 815
    :cond_0
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v1

    const-string v2, "wifi"

    invoke-virtual {v1, v2}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/net/wifi/WifiManager;

    .line 817
    invoke-virtual {v1}, Landroid/net/wifi/WifiManager;->getScanResults()Ljava/util/List;

    move-result-object v1

    if-eqz v1, :cond_1

    .line 819
    invoke-virtual {v0, v1}, Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;->setAllResults(Ljava/util/List;)V

    :cond_1
    return-object v0
.end method

.method public static is4G()Z
    .locals 2

    .line 320
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 322
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isAvailable()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 323
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getSubtype()I

    move-result v0

    const/16 v1, 0xd

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static is5G()Z
    .locals 2

    .line 334
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 336
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isAvailable()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 337
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getSubtype()I

    move-result v0

    const/16 v1, 0x14

    if-ne v0, v1, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static isAvailable()Z
    .locals 1

    .line 121
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailableByDns()Z

    move-result v0

    if-nez v0, :cond_1

    const/4 v0, 0x0

    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailableByPing(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method public static isAvailableAsync(Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/Boolean;",
            ">;)",
            "Lcom/hcn/common/utils/HUtilsEx$Task<",
            "Ljava/lang/Boolean;",
            ">;"
        }
    .end annotation

    .line 104
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$1;

    invoke-direct {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils$1;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V

    invoke-static {v0}, Lcom/hcn/common/utils/HUtilsEx;->doAsync(Lcom/hcn/common/utils/HUtilsEx$Task;)Lcom/hcn/common/utils/HUtilsEx$Task;

    move-result-object p0

    return-object p0
.end method

.method public static isAvailableByDns()Z
    .locals 1

    const-string v0, ""

    .line 222
    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailableByDns(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public static isAvailableByDns(Ljava/lang/String;)Z
    .locals 1

    .line 234
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string p0, "www.baidu.com"

    :cond_0
    const/4 v0, 0x0

    .line 237
    :try_start_0
    invoke-static {p0}, Ljava/net/InetAddress;->getByName(Ljava/lang/String;)Ljava/net/InetAddress;

    move-result-object p0
    :try_end_0
    .catch Ljava/net/UnknownHostException; {:try_start_0 .. :try_end_0} :catch_0

    if-eqz p0, :cond_1

    const/4 v0, 0x1

    :cond_1
    return v0

    :catch_0
    move-exception p0

    .line 240
    invoke-virtual {p0}, Ljava/net/UnknownHostException;->printStackTrace()V

    return v0
.end method

.method public static isAvailableByDnsAsync(Ljava/lang/String;Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/Boolean;",
            ">;)",
            "Lcom/hcn/common/utils/HUtilsEx$Task;"
        }
    .end annotation

    .line 205
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$3;

    invoke-direct {v0, p1, p0}, Lcom/hcn/common/misc/HNetworkUtils$3;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;Ljava/lang/String;)V

    invoke-static {v0}, Lcom/hcn/common/utils/HUtilsEx;->doAsync(Lcom/hcn/common/utils/HUtilsEx$Task;)Lcom/hcn/common/utils/HUtilsEx$Task;

    move-result-object p0

    return-object p0
.end method

.method public static isAvailableByDnsAsync(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/Boolean;",
            ">;)V"
        }
    .end annotation

    const-string v0, ""

    .line 191
    invoke-static {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailableByDnsAsync(Ljava/lang/String;Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;

    return-void
.end method

.method public static isAvailableByPing()Z
    .locals 1

    const-string v0, ""

    .line 165
    invoke-static {v0}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailableByPing(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public static isAvailableByPing(Ljava/lang/String;)Z
    .locals 3

    .line 177
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    const-string p0, "223.5.5.5"

    :cond_0
    const/4 v0, 0x1

    new-array v1, v0, [Ljava/lang/Object;

    const/4 v2, 0x0

    aput-object p0, v1, v2

    const-string p0, "ping -c 1 %s"

    .line 179
    invoke-static {p0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0, v2}, Lcom/hcn/common/app/HShellUtils;->execCmd(Ljava/lang/String;Z)Lcom/hcn/common/app/HShellUtils$CommandResult;

    move-result-object p0

    .line 180
    iget p0, p0, Lcom/hcn/common/app/HShellUtils$CommandResult;->result:I

    if-nez p0, :cond_1

    goto :goto_0

    :cond_1
    move v0, v2

    :goto_0
    return v0
.end method

.method public static isAvailableByPingAsync(Ljava/lang/String;Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/Boolean;",
            ">;)",
            "Lcom/hcn/common/utils/HUtilsEx$Task<",
            "Ljava/lang/Boolean;",
            ">;"
        }
    .end annotation

    .line 147
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$2;

    invoke-direct {v0, p1, p0}, Lcom/hcn/common/misc/HNetworkUtils$2;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;Ljava/lang/String;)V

    invoke-static {v0}, Lcom/hcn/common/utils/HUtilsEx;->doAsync(Lcom/hcn/common/utils/HUtilsEx$Task;)Lcom/hcn/common/utils/HUtilsEx$Task;

    move-result-object p0

    return-object p0
.end method

.method public static isAvailableByPingAsync(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/Boolean;",
            ">;)V"
        }
    .end annotation

    const-string v0, ""

    .line 133
    invoke-static {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailableByPingAsync(Ljava/lang/String;Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;

    return-void
.end method

.method public static isBehindProxy()Z
    .locals 1

    const-string v0, "http.proxyHost"

    .line 281
    invoke-static {v0}, Ljava/lang/System;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_0

    const-string v0, "http.proxyPort"

    invoke-static {v0}, Ljava/lang/System;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static isConnected()Z
    .locals 1

    .line 91
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 92
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isConnected()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method private static isEthernet()Z
    .locals 3

    .line 521
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "connectivity"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    const/16 v2, 0x9

    .line 526
    invoke-virtual {v0, v2}, Landroid/net/ConnectivityManager;->getNetworkInfo(I)Landroid/net/NetworkInfo;

    move-result-object v0

    if-nez v0, :cond_1

    return v1

    .line 531
    :cond_1
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getState()Landroid/net/NetworkInfo$State;

    move-result-object v0

    if-nez v0, :cond_2

    return v1

    .line 536
    :cond_2
    sget-object v2, Landroid/net/NetworkInfo$State;->CONNECTED:Landroid/net/NetworkInfo$State;

    if-eq v0, v2, :cond_3

    sget-object v2, Landroid/net/NetworkInfo$State;->CONNECTING:Landroid/net/NetworkInfo$State;

    if-ne v0, v2, :cond_4

    :cond_3
    const/4 v1, 0x1

    :cond_4
    return v1
.end method

.method public static isMobileData()Z
    .locals 2

    .line 306
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 308
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isAvailable()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 309
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getType()I

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static isRegisteredNetworkStatusChangedListener(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)Z
    .locals 1

    .line 795
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$smgetInstance()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->isRegistered(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)Z

    move-result p0

    return p0
.end method

.method private static isSameScanResultContent(Landroid/net/wifi/ScanResult;Landroid/net/wifi/ScanResult;)Z
    .locals 2

    if-eqz p0, :cond_0

    if-eqz p1, :cond_0

    .line 931
    iget-object v0, p0, Landroid/net/wifi/ScanResult;->BSSID:Ljava/lang/String;

    iget-object v1, p1, Landroid/net/wifi/ScanResult;->BSSID:Ljava/lang/String;

    invoke-static {v0, v1}, Lcom/hcn/common/utils/HStringUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Landroid/net/wifi/ScanResult;->SSID:Ljava/lang/String;

    iget-object v1, p1, Landroid/net/wifi/ScanResult;->SSID:Ljava/lang/String;

    .line 932
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HStringUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Landroid/net/wifi/ScanResult;->capabilities:Ljava/lang/String;

    iget-object v1, p1, Landroid/net/wifi/ScanResult;->capabilities:Ljava/lang/String;

    .line 933
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HStringUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget p0, p0, Landroid/net/wifi/ScanResult;->level:I

    iget p1, p1, Landroid/net/wifi/ScanResult;->level:I

    if-ne p0, p1, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private static isSameScanResults(Ljava/util/List;Ljava/util/List;)Z
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Landroid/net/wifi/ScanResult;",
            ">;",
            "Ljava/util/List<",
            "Landroid/net/wifi/ScanResult;",
            ">;)Z"
        }
    .end annotation

    const/4 v0, 0x1

    if-nez p0, :cond_0

    if-nez p1, :cond_0

    return v0

    :cond_0
    const/4 v1, 0x0

    if-eqz p0, :cond_5

    if-nez p1, :cond_1

    goto :goto_1

    .line 917
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result v2

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result v3

    if-eq v2, v3, :cond_2

    return v1

    :cond_2
    move v2, v1

    .line 920
    :goto_0
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result v3

    if-ge v2, v3, :cond_4

    .line 921
    invoke-interface {p0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/net/wifi/ScanResult;

    .line 922
    invoke-interface {p1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/net/wifi/ScanResult;

    .line 923
    invoke-static {v3, v4}, Lcom/hcn/common/misc/HNetworkUtils;->isSameScanResultContent(Landroid/net/wifi/ScanResult;Landroid/net/wifi/ScanResult;)Z

    move-result v3

    if-nez v3, :cond_3

    return v1

    :cond_3
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_4
    return v0

    :cond_5
    :goto_1
    return v1
.end method

.method public static isUsingVPN()Z
    .locals 3

    .line 290
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "connectivity"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    .line 291
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1c

    if-lt v1, v2, :cond_0

    const/16 v1, 0x11

    .line 292
    invoke-virtual {v0, v1}, Landroid/net/ConnectivityManager;->getNetworkInfo(I)Landroid/net/NetworkInfo;

    move-result-object v0

    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isConnectedOrConnecting()Z

    move-result v0

    return v0

    :cond_0
    const/4 v1, 0x4

    .line 294
    invoke-virtual {v0, v1}, Landroid/net/ConnectivityManager;->getNetworkInfo(I)Landroid/net/NetworkInfo;

    move-result-object v0

    invoke-virtual {v0}, Landroid/net/NetworkInfo;->isConnectedOrConnecting()Z

    move-result v0

    return v0
.end method

.method public static isWifiAvailable()Z
    .locals 1

    .line 405
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getWifiEnabled()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->isAvailable()Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static isWifiAvailableAsync(Lcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/Boolean;",
            ">;)",
            "Lcom/hcn/common/utils/HUtilsEx$Task<",
            "Ljava/lang/Boolean;",
            ">;"
        }
    .end annotation

    .line 418
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$4;

    invoke-direct {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils$4;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V

    invoke-static {v0}, Lcom/hcn/common/utils/HUtilsEx;->doAsync(Lcom/hcn/common/utils/HUtilsEx$Task;)Lcom/hcn/common/utils/HUtilsEx$Task;

    move-result-object p0

    return-object p0
.end method

.method public static isWifiConnected()Z
    .locals 3

    .line 387
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "connectivity"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/ConnectivityManager;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    .line 392
    :cond_0
    invoke-virtual {v0}, Landroid/net/ConnectivityManager;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    const/4 v2, 0x1

    if-eqz v0, :cond_1

    .line 393
    invoke-virtual {v0}, Landroid/net/NetworkInfo;->getType()I

    move-result v0

    if-ne v0, v2, :cond_1

    move v1, v2

    :cond_1
    return v1
.end method

.method public static openWirelessSettings()V
    .locals 3

    .line 77
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    new-instance v1, Landroid/content/Intent;

    const-string v2, "android.settings.WIRELESS_SETTINGS"

    invoke-direct {v1, v2}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const/high16 v2, 0x10000000

    .line 79
    invoke-virtual {v1, v2}, Landroid/content/Intent;->setFlags(I)Landroid/content/Intent;

    move-result-object v1

    .line 77
    invoke-virtual {v0, v1}, Landroid/app/Application;->startActivity(Landroid/content/Intent;)V

    return-void
.end method

.method public static registerNetworkStatusChangedListener(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V
    .locals 1

    .line 785
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$smgetInstance()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->registerListener(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V

    return-void
.end method

.method public static removeOnWifiChangedConsumer(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;",
            ">;)V"
        }
    .end annotation

    if-nez p0, :cond_0

    return-void

    .line 892
    :cond_0
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$9;

    invoke-direct {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils$9;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V

    invoke-static {v0}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method

.method public static setWifiEnabled(Z)V
    .locals 2

    .line 366
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "wifi"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    if-nez v0, :cond_0

    return-void

    .line 371
    :cond_0
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->isWifiEnabled()Z

    move-result v1

    if-ne p0, v1, :cond_1

    return-void

    .line 375
    :cond_1
    invoke-virtual {v0, p0}, Landroid/net/wifi/WifiManager;->setWifiEnabled(Z)Z

    return-void
.end method

.method private static startScanWifi()V
    .locals 7

    .line 851
    new-instance v0, Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    invoke-direct {v0}, Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;-><init>()V

    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils;->sPreWifiScanResults:Lcom/hcn/common/misc/HNetworkUtils$WifiScanResults;

    .line 852
    new-instance v1, Ljava/util/Timer;

    invoke-direct {v1}, Ljava/util/Timer;-><init>()V

    sput-object v1, Lcom/hcn/common/misc/HNetworkUtils;->sScanWifiTimer:Ljava/util/Timer;

    .line 853
    new-instance v2, Lcom/hcn/common/misc/HNetworkUtils$8;

    invoke-direct {v2}, Lcom/hcn/common/misc/HNetworkUtils$8;-><init>()V

    const-wide/16 v3, 0x0

    const-wide/16 v5, 0xbb8

    invoke-virtual/range {v1 .. v6}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;JJ)V

    return-void
.end method

.method private static startScanWifiIfEnabled()V
    .locals 2

    .line 877
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils;->getWifiEnabled()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    .line 882
    :cond_0
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    const-string v1, "wifi"

    invoke-virtual {v0, v1}, Landroid/app/Application;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    .line 884
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->startScan()Z

    return-void
.end method

.method private static stopScanWifi()V
    .locals 1

    .line 904
    sget-object v0, Lcom/hcn/common/misc/HNetworkUtils;->sScanWifiTimer:Ljava/util/Timer;

    if-eqz v0, :cond_0

    .line 905
    invoke-virtual {v0}, Ljava/util/Timer;->cancel()V

    const/4 v0, 0x0

    .line 906
    sput-object v0, Lcom/hcn/common/misc/HNetworkUtils;->sScanWifiTimer:Ljava/util/Timer;

    :cond_0
    return-void
.end method

.method public static unregisterNetworkStatusChangedListener(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V
    .locals 1

    .line 804
    invoke-static {}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->-$$Nest$smgetInstance()Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;

    move-result-object v0

    invoke-virtual {v0, p0}, Lcom/hcn/common/misc/HNetworkUtils$NetworkChangedReceiver;->unregisterListener(Lcom/hcn/common/misc/HNetworkUtils$OnNetworkStatusChangedListener;)V

    return-void
.end method
