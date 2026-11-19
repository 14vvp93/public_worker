package kotlinx.coroutines.internal;

import com.autochips.bluetooth.skin.SkinID;
import java.util.List;
import kotlin.Metadata;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* JADX INFO: compiled from: MainDispatcherFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\tH&J\n\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\f"}, m62d2 = {"Lkotlinx/coroutines/internal/MainDispatcherFactory;", SkinID.SKIN_NONE, "loadPriority", SkinID.SKIN_NONE, "getLoadPriority", "()I", "createDispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "allFactories", SkinID.SKIN_NONE, "hintOnError", SkinID.SKIN_NONE, "kotlinx-coroutines-core"}, m63k = 1, m64mv = {1, 6, 0}, m66xi = 48)
public interface MainDispatcherFactory {

    /* JADX INFO: compiled from: MainDispatcherFactory.kt */
    @Metadata(m63k = 3, m64mv = {1, 6, 0}, m66xi = 48)
    public static final class DefaultImpls {
        public static String hintOnError(MainDispatcherFactory mainDispatcherFactory) {
            return null;
        }
    }

    MainCoroutineDispatcher createDispatcher(List<? extends MainDispatcherFactory> allFactories);

    int getLoadPriority();

    String hintOnError();
}
