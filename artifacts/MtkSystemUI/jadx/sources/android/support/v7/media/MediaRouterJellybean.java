package android.support.v7.media;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class MediaRouterJellybean {

    public interface Callback {
        void onRouteAdded(Object obj);

        void onRouteChanged(Object obj);

        void onRouteGrouped(Object obj, Object obj2, int i);

        void onRouteRemoved(Object obj);

        void onRouteSelected(int i, Object obj);

        void onRouteUngrouped(Object obj, Object obj2);

        void onRouteUnselected(int i, Object obj);

        void onRouteVolumeChanged(Object obj);
    }

    public interface VolumeCallback {
        void onVolumeSetRequest(Object obj, int i);

        void onVolumeUpdateRequest(Object obj, int i);
    }

    public static Object getMediaRouter(Context context) {
        return context.getSystemService("media_router");
    }

    public static List getRoutes(Object routerObj) {
        android.media.MediaRouter router = (android.media.MediaRouter) routerObj;
        int count = router.getRouteCount();
        List out = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            out.add(router.getRouteAt(i));
        }
        return out;
    }

    public static Object getSelectedRoute(Object routerObj, int type) {
        return ((android.media.MediaRouter) routerObj).getSelectedRoute(type);
    }

    public static void selectRoute(Object routerObj, int types, Object routeObj) {
        ((android.media.MediaRouter) routerObj).selectRoute(types, (android.media.MediaRouter.RouteInfo) routeObj);
    }

    public static void addCallback(Object routerObj, int types, Object callbackObj) {
        ((android.media.MediaRouter) routerObj).addCallback(types, (android.media.MediaRouter.Callback) callbackObj);
    }

    public static void removeCallback(Object routerObj, Object callbackObj) {
        ((android.media.MediaRouter) routerObj).removeCallback((android.media.MediaRouter.Callback) callbackObj);
    }

    public static Object createRouteCategory(Object routerObj, String name, boolean isGroupable) {
        return ((android.media.MediaRouter) routerObj).createRouteCategory(name, isGroupable);
    }

    public static Object createUserRoute(Object routerObj, Object categoryObj) {
        return ((android.media.MediaRouter) routerObj).createUserRoute((android.media.MediaRouter.RouteCategory) categoryObj);
    }

    public static void addUserRoute(Object routerObj, Object routeObj) {
        ((android.media.MediaRouter) routerObj).addUserRoute((android.media.MediaRouter.UserRouteInfo) routeObj);
    }

    public static void removeUserRoute(Object routerObj, Object routeObj) {
        ((android.media.MediaRouter) routerObj).removeUserRoute((android.media.MediaRouter.UserRouteInfo) routeObj);
    }

    public static Object createCallback(Callback callback) {
        return new CallbackProxy(callback);
    }

    public static Object createVolumeCallback(VolumeCallback callback) {
        return new VolumeCallbackProxy(callback);
    }

    public static final class RouteInfo {
        public static CharSequence getName(Object routeObj, Context context) {
            return ((android.media.MediaRouter.RouteInfo) routeObj).getName(context);
        }

        public static int getSupportedTypes(Object routeObj) {
            return ((android.media.MediaRouter.RouteInfo) routeObj).getSupportedTypes();
        }

        public static int getPlaybackType(Object routeObj) {
            return ((android.media.MediaRouter.RouteInfo) routeObj).getPlaybackType();
        }

        public static int getPlaybackStream(Object routeObj) {
            return ((android.media.MediaRouter.RouteInfo) routeObj).getPlaybackStream();
        }

        public static int getVolume(Object routeObj) {
            return ((android.media.MediaRouter.RouteInfo) routeObj).getVolume();
        }

        public static int getVolumeMax(Object routeObj) {
            return ((android.media.MediaRouter.RouteInfo) routeObj).getVolumeMax();
        }

        public static int getVolumeHandling(Object routeObj) {
            return ((android.media.MediaRouter.RouteInfo) routeObj).getVolumeHandling();
        }

        public static Object getTag(Object routeObj) {
            return ((android.media.MediaRouter.RouteInfo) routeObj).getTag();
        }

        public static void setTag(Object routeObj, Object tag) {
            ((android.media.MediaRouter.RouteInfo) routeObj).setTag(tag);
        }

        public static void requestSetVolume(Object routeObj, int volume) {
            ((android.media.MediaRouter.RouteInfo) routeObj).requestSetVolume(volume);
        }

        public static void requestUpdateVolume(Object routeObj, int direction) {
            ((android.media.MediaRouter.RouteInfo) routeObj).requestUpdateVolume(direction);
        }
    }

    public static final class UserRouteInfo {
        public static void setName(Object routeObj, CharSequence name) {
            ((android.media.MediaRouter.UserRouteInfo) routeObj).setName(name);
        }

        public static void setPlaybackType(Object routeObj, int type) {
            ((android.media.MediaRouter.UserRouteInfo) routeObj).setPlaybackType(type);
        }

        public static void setPlaybackStream(Object routeObj, int stream) {
            ((android.media.MediaRouter.UserRouteInfo) routeObj).setPlaybackStream(stream);
        }

        public static void setVolume(Object routeObj, int volume) {
            ((android.media.MediaRouter.UserRouteInfo) routeObj).setVolume(volume);
        }

        public static void setVolumeMax(Object routeObj, int volumeMax) {
            ((android.media.MediaRouter.UserRouteInfo) routeObj).setVolumeMax(volumeMax);
        }

        public static void setVolumeHandling(Object routeObj, int volumeHandling) {
            ((android.media.MediaRouter.UserRouteInfo) routeObj).setVolumeHandling(volumeHandling);
        }

        public static void setVolumeCallback(Object routeObj, Object volumeCallbackObj) {
            ((android.media.MediaRouter.UserRouteInfo) routeObj).setVolumeCallback((android.media.MediaRouter.VolumeCallback) volumeCallbackObj);
        }
    }

    public static final class SelectRouteWorkaround {
        private Method mSelectRouteIntMethod;

        public SelectRouteWorkaround() {
            if (Build.VERSION.SDK_INT < 16 || Build.VERSION.SDK_INT > 17) {
                throw new UnsupportedOperationException();
            }
            try {
                this.mSelectRouteIntMethod = android.media.MediaRouter.class.getMethod("selectRouteInt", Integer.TYPE, android.media.MediaRouter.RouteInfo.class);
            } catch (NoSuchMethodException e) {
            }
        }

        public void selectRoute(Object routerObj, int types, Object routeObj) {
            android.media.MediaRouter router = (android.media.MediaRouter) routerObj;
            android.media.MediaRouter.RouteInfo route = (android.media.MediaRouter.RouteInfo) routeObj;
            int routeTypes = route.getSupportedTypes();
            if ((8388608 & routeTypes) == 0) {
                if (this.mSelectRouteIntMethod != null) {
                    try {
                        this.mSelectRouteIntMethod.invoke(router, Integer.valueOf(types), route);
                        return;
                    } catch (IllegalAccessException ex) {
                        Log.w("MediaRouterJellybean", "Cannot programmatically select non-user route.  Media routing may not work.", ex);
                    } catch (InvocationTargetException ex2) {
                        Log.w("MediaRouterJellybean", "Cannot programmatically select non-user route.  Media routing may not work.", ex2);
                    }
                } else {
                    Log.w("MediaRouterJellybean", "Cannot programmatically select non-user route because the platform is missing the selectRouteInt() method.  Media routing may not work.");
                }
            }
            router.selectRoute(types, route);
        }
    }

    public static final class GetDefaultRouteWorkaround {
        private Method mGetSystemAudioRouteMethod;

        public GetDefaultRouteWorkaround() {
            if (Build.VERSION.SDK_INT < 16 || Build.VERSION.SDK_INT > 17) {
                throw new UnsupportedOperationException();
            }
            try {
                this.mGetSystemAudioRouteMethod = android.media.MediaRouter.class.getMethod("getSystemAudioRoute", new Class[0]);
            } catch (NoSuchMethodException e) {
            }
        }

        public Object getDefaultRoute(Object routerObj) {
            android.media.MediaRouter router = (android.media.MediaRouter) routerObj;
            if (this.mGetSystemAudioRouteMethod != null) {
                try {
                    return this.mGetSystemAudioRouteMethod.invoke(router, new Object[0]);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e2) {
                }
            }
            return router.getRouteAt(0);
        }
    }

    static class CallbackProxy<T extends Callback> extends android.media.MediaRouter.Callback {
        protected final T mCallback;

        public CallbackProxy(T callback) {
            this.mCallback = callback;
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteSelected(android.media.MediaRouter router, int type, android.media.MediaRouter.RouteInfo route) {
            this.mCallback.onRouteSelected(type, route);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteUnselected(android.media.MediaRouter router, int type, android.media.MediaRouter.RouteInfo route) {
            this.mCallback.onRouteUnselected(type, route);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteAdded(android.media.MediaRouter router, android.media.MediaRouter.RouteInfo route) {
            this.mCallback.onRouteAdded(route);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteRemoved(android.media.MediaRouter router, android.media.MediaRouter.RouteInfo route) {
            this.mCallback.onRouteRemoved(route);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteChanged(android.media.MediaRouter router, android.media.MediaRouter.RouteInfo route) {
            this.mCallback.onRouteChanged(route);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteGrouped(android.media.MediaRouter router, android.media.MediaRouter.RouteInfo route, android.media.MediaRouter.RouteGroup group, int index) {
            this.mCallback.onRouteGrouped(route, group, index);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteUngrouped(android.media.MediaRouter router, android.media.MediaRouter.RouteInfo route, android.media.MediaRouter.RouteGroup group) {
            this.mCallback.onRouteUngrouped(route, group);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteVolumeChanged(android.media.MediaRouter router, android.media.MediaRouter.RouteInfo route) {
            this.mCallback.onRouteVolumeChanged(route);
        }
    }

    static class VolumeCallbackProxy<T extends VolumeCallback> extends android.media.MediaRouter.VolumeCallback {
        protected final T mCallback;

        public VolumeCallbackProxy(T callback) {
            this.mCallback = callback;
        }

        @Override // android.media.MediaRouter.VolumeCallback
        public void onVolumeSetRequest(android.media.MediaRouter.RouteInfo route, int volume) {
            this.mCallback.onVolumeSetRequest(route, volume);
        }

        @Override // android.media.MediaRouter.VolumeCallback
        public void onVolumeUpdateRequest(android.media.MediaRouter.RouteInfo route, int direction) {
            this.mCallback.onVolumeUpdateRequest(route, direction);
        }
    }
}
