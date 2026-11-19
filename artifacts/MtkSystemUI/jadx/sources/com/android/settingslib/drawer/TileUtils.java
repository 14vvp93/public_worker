package com.android.settingslib.drawer;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TileUtils {
    private static final Comparator<DashboardCategory> CATEGORY_COMPARATOR = new Comparator<DashboardCategory>() { // from class: com.android.settingslib.drawer.TileUtils.1
        @Override // java.util.Comparator
        public int compare(DashboardCategory lhs, DashboardCategory rhs) {
            return rhs.priority - lhs.priority;
        }
    };

    public static List<DashboardCategory> getCategories(Context context, Map<Pair<String, String>, Tile> cache, boolean categoryDefinedInManifest, String extraAction, String settingPkg) {
        UserHandle user;
        System.currentTimeMillis();
        boolean setup = Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
        ArrayList<Tile> tiles = new ArrayList<>();
        UserManager userManager = (UserManager) context.getSystemService("user");
        for (Iterator<UserHandle> it = userManager.getUserProfiles().iterator(); it.hasNext(); it = it) {
            UserHandle user2 = it.next();
            if (user2.getIdentifier() == ActivityManager.getCurrentUser()) {
                user = user2;
                getTilesForAction(context, user2, "com.android.settings.action.SETTINGS", cache, null, tiles, true, settingPkg);
                getTilesForAction(context, user, "com.android.settings.OPERATOR_APPLICATION_SETTING", cache, "com.android.settings.category.wireless", tiles, false, true, settingPkg);
                getTilesForAction(context, user, "com.android.settings.MANUFACTURER_APPLICATION_SETTING", cache, "com.android.settings.category.device", tiles, false, true, settingPkg);
            } else {
                user = user2;
            }
            if (setup) {
                getTilesForAction(context, user, "com.android.settings.action.EXTRA_SETTINGS", cache, null, tiles, false, settingPkg);
                if (!categoryDefinedInManifest) {
                    getTilesForAction(context, user, "com.android.settings.action.IA_SETTINGS", cache, null, tiles, false, settingPkg);
                    if (extraAction != null) {
                        getTilesForAction(context, user, extraAction, cache, null, tiles, false, settingPkg);
                    }
                }
            }
        }
        HashMap<String, DashboardCategory> categoryMap = new HashMap<>();
        for (Tile tile : tiles) {
            DashboardCategory category = categoryMap.get(tile.category);
            if (category == null) {
                category = createCategory(context, tile.category, categoryDefinedInManifest);
                if (category == null) {
                    Log.w("TileUtils", "Couldn't find category " + tile.category);
                } else {
                    categoryMap.put(category.key, category);
                }
            }
            category.addTile(tile);
        }
        ArrayList<DashboardCategory> categories = new ArrayList<>(categoryMap.values());
        Iterator<DashboardCategory> it2 = categories.iterator();
        while (it2.hasNext()) {
            it2.next().sortTiles();
        }
        Collections.sort(categories, CATEGORY_COMPARATOR);
        return categories;
    }

    private static DashboardCategory createCategory(Context context, String categoryKey, boolean categoryDefinedInManifest) {
        DashboardCategory category = new DashboardCategory();
        category.key = categoryKey;
        if (!categoryDefinedInManifest) {
            return category;
        }
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> results = pm.queryIntentActivities(new Intent(categoryKey), 0);
        if (results.size() == 0) {
            return null;
        }
        for (ResolveInfo resolved : results) {
            if (resolved.system) {
                category.title = resolved.activityInfo.loadLabel(pm);
                category.priority = "com.android.settings".equals(resolved.activityInfo.applicationInfo.packageName) ? resolved.priority : 0;
            }
        }
        return category;
    }

    private static void getTilesForAction(Context context, UserHandle user, String action, Map<Pair<String, String>, Tile> addedCache, String defaultCategory, ArrayList<Tile> outTiles, boolean requireSettings, String settingPkg) {
        getTilesForAction(context, user, action, addedCache, defaultCategory, outTiles, requireSettings, requireSettings, settingPkg);
    }

    private static void getTilesForAction(Context context, UserHandle user, String action, Map<Pair<String, String>, Tile> addedCache, String defaultCategory, ArrayList<Tile> outTiles, boolean requireSettings, boolean usePriority, String settingPkg) {
        Intent intent = new Intent(action);
        if (requireSettings) {
            intent.setPackage(settingPkg);
        }
        getTilesForIntent(context, user, intent, addedCache, defaultCategory, outTiles, usePriority, true, true);
    }

    public static void getTilesForIntent(Context context, UserHandle user, Intent intent, Map<Pair<String, String>, Tile> addedCache, String defaultCategory, List<Tile> outTiles, boolean usePriority, boolean checkCategory, boolean forceTintExternalIcon) {
        getTilesForIntent(context, user, intent, addedCache, defaultCategory, outTiles, usePriority, checkCategory, forceTintExternalIcon, false);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x011d  */
    /* JADX WARN: Code duplicated, block: B:41:0x0128  */
    public static void getTilesForIntent(Context context, UserHandle user, Intent intent, Map<Pair<String, String>, Tile> addedCache, String defaultCategory, List<Tile> outTiles, boolean usePriority, boolean checkCategory, boolean forceTintExternalIcon, boolean shouldUpdateTiles) {
        Intent intent2 = intent;
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> results = pm.queryIntentActivitiesAsUser(intent2, 128, user.getIdentifier());
        Map<String, IContentProvider> providerMap = new HashMap<>();
        String isHiddenStorage = SystemProperties.get("ro.sys.settings.storage", "0");
        for (ResolveInfo resolved : results) {
            if (resolved.system) {
                ActivityInfo activityInfo = resolved.activityInfo;
                Bundle metaData = activityInfo.metaData;
                if ("1".equals(isHiddenStorage) && "com.android.settings".equals(activityInfo.packageName) && activityInfo.name.endsWith("StorageDashboardActivity")) {
                    Log.v("TileUtils", "not allow to scan storage dashboard activity.");
                } else if (checkCategory && ((metaData == null || !metaData.containsKey("com.android.settings.category")) && defaultCategory == null)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Found ");
                    sb.append(resolved.activityInfo.name);
                    sb.append(" for intent ");
                    sb.append(intent2);
                    sb.append(" missing metadata ");
                    sb.append(metaData == null ? "" : "com.android.settings.category");
                    Log.w("TileUtils", sb.toString());
                } else {
                    String categoryKey = metaData.getString("com.android.settings.category");
                    Pair<String, String> key = new Pair<>(activityInfo.packageName, activityInfo.name);
                    Tile tile = addedCache.get(key);
                    if (tile == null) {
                        Tile tile2 = new Tile();
                        tile2.intent = new Intent().setClassName(activityInfo.packageName, activityInfo.name);
                        tile2.category = categoryKey;
                        tile2.priority = usePriority ? resolved.priority : 0;
                        tile2.metaData = activityInfo.metaData;
                        updateTileData(context, tile2, activityInfo, activityInfo.applicationInfo, pm, providerMap, forceTintExternalIcon);
                        tile = tile2;
                        addedCache.put(key, tile);
                    } else {
                        if (shouldUpdateTiles) {
                            updateSummaryAndTitle(context, providerMap, tile);
                        }
                        if (!tile.userHandle.contains(user)) {
                            tile.userHandle.add(user);
                        }
                        if (!outTiles.contains(tile)) {
                            outTiles.add(tile);
                        }
                        intent2 = intent;
                    }
                    if (!tile.userHandle.contains(user)) {
                        tile.userHandle.add(user);
                    }
                    if (!outTiles.contains(tile)) {
                        outTiles.add(tile);
                    }
                    intent2 = intent;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0088 A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TRY_ENTER, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x0092 A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x009e A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TRY_LEAVE, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x00ad A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TRY_ENTER, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00b7 A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x00c3 A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TRY_LEAVE, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00d2 A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TRY_ENTER, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00dc A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x00e8 A[Catch: NameNotFoundException | NotFoundException -> 0x002f, NameNotFoundException | NotFoundException -> 0x002f, TRY_LEAVE, TryCatch #6 {NameNotFoundException | NotFoundException -> 0x002f, blocks: (B:7:0x0020, B:17:0x0043, B:17:0x0043, B:35:0x0088, B:35:0x0088, B:37:0x0092, B:37:0x0092, B:38:0x009e, B:38:0x009e, B:42:0x00ad, B:42:0x00ad, B:44:0x00b7, B:44:0x00b7, B:45:0x00c3, B:45:0x00c3, B:49:0x00d2, B:49:0x00d2, B:51:0x00dc, B:51:0x00dc, B:52:0x00e8, B:52:0x00e8), top: B:97:0x0020 }] */
    /* JADX WARN: Code duplicated, block: B:56:0x00f7 A[Catch: NameNotFoundException | NotFoundException -> 0x011f, NameNotFoundException | NotFoundException -> 0x011f, TRY_LEAVE, TryCatch #5 {NameNotFoundException | NotFoundException -> 0x011f, blocks: (B:33:0x0080, B:40:0x00a5, B:40:0x00a5, B:47:0x00ca, B:47:0x00ca, B:54:0x00ef, B:54:0x00ef, B:56:0x00f7, B:56:0x00f7), top: B:95:0x0080 }] */
    /* JADX WARN: Code duplicated, block: B:62:0x0116  */
    /* JADX WARN: Code duplicated, block: B:69:0x0130  */
    /* JADX WARN: Code duplicated, block: B:75:0x0144  */
    /* JADX WARN: Code duplicated, block: B:81:0x015c  */
    private static boolean updateTileData(Context context, Tile tile, ActivityInfo activityInfo, ApplicationInfo applicationInfo, PackageManager pm, Map<String, IContentProvider> providerMap, boolean forceTintExternalIcon) {
        boolean isIconTintable;
        boolean isIconTintable2;
        boolean isIconTintable3 = false;
        if (!applicationInfo.isSystemApp()) {
            return false;
        }
        boolean forceTintIcon = false;
        int icon = 0;
        CharSequence title = null;
        String summary = null;
        String keyHint = null;
        try {
            Resources res = pm.getResourcesForApplication(applicationInfo.packageName);
            Bundle metaData = activityInfo.metaData;
            if (forceTintExternalIcon) {
                try {
                    if (!context.getPackageName().equals(applicationInfo.packageName)) {
                        isIconTintable3 = true;
                        forceTintIcon = true;
                    }
                    if (res != null || metaData == null) {
                        isIconTintable3 = isIconTintable3;
                    } else {
                        try {
                            icon = metaData.containsKey("com.android.settings.icon") ? metaData.getInt("com.android.settings.icon") : 0;
                            try {
                                if (metaData.containsKey("com.android.settings.icon_tintable")) {
                                    try {
                                        if (forceTintIcon) {
                                            try {
                                                StringBuilder sb = new StringBuilder();
                                                sb.append("Ignoring icon tintable for ");
                                                sb.append(activityInfo);
                                                Log.w("TileUtils", sb.toString());
                                            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
                                            }
                                        } else {
                                            isIconTintable = metaData.getBoolean("com.android.settings.icon_tintable");
                                        }
                                        if (metaData.containsKey("com.android.settings.title")) {
                                            if (metaData.get("com.android.settings.title") instanceof Integer) {
                                                title = res.getString(metaData.getInt("com.android.settings.title"));
                                            } else {
                                                title = metaData.getString("com.android.settings.title");
                                            }
                                        }
                                        if (metaData.containsKey("com.android.settings.summary")) {
                                            if (metaData.get("com.android.settings.summary") instanceof Integer) {
                                                summary = res.getString(metaData.getInt("com.android.settings.summary"));
                                            } else {
                                                summary = metaData.getString("com.android.settings.summary");
                                            }
                                        }
                                        if (metaData.containsKey("com.android.settings.keyhint")) {
                                            if (metaData.get("com.android.settings.keyhint") instanceof Integer) {
                                                keyHint = res.getString(metaData.getInt("com.android.settings.keyhint"));
                                            } else {
                                                keyHint = metaData.getString("com.android.settings.keyhint");
                                            }
                                        }
                                        if (metaData.containsKey("com.android.settings.custom_view")) {
                                            int layoutId = metaData.getInt("com.android.settings.custom_view");
                                            tile.remoteViews = new RemoteViews(applicationInfo.packageName, layoutId);
                                            isIconTintable2 = isIconTintable;
                                            try {
                                                updateSummaryAndTitle(context, providerMap, tile);
                                            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e2) {
                                                isIconTintable3 = isIconTintable2;
                                            }
                                        } else {
                                            isIconTintable2 = isIconTintable;
                                        }
                                        isIconTintable3 = isIconTintable2;
                                    } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e3) {
                                    }
                                }
                                if (metaData.containsKey("com.android.settings.title")) {
                                    if (metaData.get("com.android.settings.title") instanceof Integer) {
                                        title = res.getString(metaData.getInt("com.android.settings.title"));
                                    } else {
                                        title = metaData.getString("com.android.settings.title");
                                    }
                                }
                                if (metaData.containsKey("com.android.settings.summary")) {
                                    if (metaData.get("com.android.settings.summary") instanceof Integer) {
                                        summary = res.getString(metaData.getInt("com.android.settings.summary"));
                                    } else {
                                        summary = metaData.getString("com.android.settings.summary");
                                    }
                                }
                                if (metaData.containsKey("com.android.settings.keyhint")) {
                                    if (metaData.get("com.android.settings.keyhint") instanceof Integer) {
                                        keyHint = res.getString(metaData.getInt("com.android.settings.keyhint"));
                                    } else {
                                        keyHint = metaData.getString("com.android.settings.keyhint");
                                    }
                                }
                                if (metaData.containsKey("com.android.settings.custom_view")) {
                                    int layoutId2 = metaData.getInt("com.android.settings.custom_view");
                                    tile.remoteViews = new RemoteViews(applicationInfo.packageName, layoutId2);
                                    isIconTintable2 = isIconTintable;
                                    updateSummaryAndTitle(context, providerMap, tile);
                                } else {
                                    isIconTintable2 = isIconTintable;
                                }
                                isIconTintable3 = isIconTintable2;
                            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e4) {
                                isIconTintable3 = isIconTintable;
                            }
                            isIconTintable = isIconTintable3;
                        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e5) {
                        }
                    }
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e6) {
                    isIconTintable3 = false;
                    boolean isIconTintable4 = isIconTintable3;
                    if (TextUtils.isEmpty(title)) {
                        title = activityInfo.loadLabel(pm).toString();
                    }
                    if (icon == 0) {
                        icon = activityInfo.icon;
                    }
                    if (icon != 0) {
                        tile.icon = Icon.createWithResource(activityInfo.packageName, icon);
                    }
                    tile.title = title;
                    tile.summary = summary;
                    tile.intent = new Intent().setClassName(activityInfo.packageName, activityInfo.name);
                    tile.key = keyHint;
                    tile.isIconTintable = isIconTintable4;
                    return true;
                }
            } else if (res != null) {
                isIconTintable3 = isIconTintable3;
            } else {
                isIconTintable3 = isIconTintable3;
            }
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e7) {
            isIconTintable3 = false;
        }
        boolean isIconTintable5 = isIconTintable3;
        if (TextUtils.isEmpty(title)) {
            title = activityInfo.loadLabel(pm).toString();
        }
        if (icon == 0 && !tile.metaData.containsKey("com.android.settings.icon_uri")) {
            icon = activityInfo.icon;
        }
        if (icon != 0) {
            tile.icon = Icon.createWithResource(activityInfo.packageName, icon);
        }
        tile.title = title;
        tile.summary = summary;
        tile.intent = new Intent().setClassName(activityInfo.packageName, activityInfo.name);
        tile.key = keyHint;
        tile.isIconTintable = isIconTintable5;
        return true;
    }

    private static void updateSummaryAndTitle(Context context, Map<String, IContentProvider> providerMap, Tile tile) {
        if (tile == null || tile.metaData == null || !tile.metaData.containsKey("com.android.settings.summary_uri")) {
            return;
        }
        String uriString = tile.metaData.getString("com.android.settings.summary_uri");
        Bundle bundle = getBundleFromUri(context, uriString, providerMap);
        String overrideSummary = getString(bundle, "com.android.settings.summary");
        String overrideTitle = getString(bundle, "com.android.settings.title");
        if (overrideSummary != null) {
            tile.remoteViews.setTextViewText(R.id.summary, overrideSummary);
        }
        if (overrideTitle != null) {
            tile.remoteViews.setTextViewText(R.id.title, overrideTitle);
        }
    }

    private static Bundle getBundleFromUri(Context context, String uriString, Map<String, IContentProvider> providerMap) {
        IContentProvider provider;
        if (TextUtils.isEmpty(uriString)) {
            return null;
        }
        Uri uri = Uri.parse(uriString);
        String method = getMethodFromUri(uri);
        if (TextUtils.isEmpty(method) || (provider = getProviderFromUri(context, uri, providerMap)) == null) {
            return null;
        }
        try {
            return provider.call(context.getPackageName(), method, uriString, (Bundle) null);
        } catch (RemoteException e) {
            return null;
        }
    }

    private static String getString(Bundle bundle, String key) {
        if (bundle == null) {
            return null;
        }
        return bundle.getString(key);
    }

    private static IContentProvider getProviderFromUri(Context context, Uri uri, Map<String, IContentProvider> providerMap) {
        if (uri == null) {
            return null;
        }
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            return null;
        }
        if (!providerMap.containsKey(authority)) {
            providerMap.put(authority, context.getContentResolver().acquireUnstableProvider(uri));
        }
        return providerMap.get(authority);
    }

    static String getMethodFromUri(Uri uri) {
        List<String> pathSegments;
        if (uri == null || (pathSegments = uri.getPathSegments()) == null || pathSegments.isEmpty()) {
            return null;
        }
        return pathSegments.get(0);
    }
}
