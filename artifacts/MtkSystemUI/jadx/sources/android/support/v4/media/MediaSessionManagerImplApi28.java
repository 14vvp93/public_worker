package android.support.v4.media;

/* JADX INFO: loaded from: classes.dex */
class MediaSessionManagerImplApi28 extends MediaSessionManagerImplApi21 {

    static final class RemoteUserInfo implements MediaSessionManager.RemoteUserInfoImpl {
        android.media.session.MediaSessionManager.RemoteUserInfo mObject;

        RemoteUserInfo(String packageName, int pid, int uid) {
            this.mObject = new android.media.session.MediaSessionManager.RemoteUserInfo(packageName, pid, uid);
        }
    }
}
