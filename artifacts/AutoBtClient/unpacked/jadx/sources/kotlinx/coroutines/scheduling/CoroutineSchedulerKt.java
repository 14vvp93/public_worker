package kotlinx.coroutines.scheduling;

import com.autochips.bluetooth.skin.SkinID;
import kotlin.Metadata;

/* JADX INFO: compiled from: CoroutineScheduler.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001¨\u0006\u0005"}, m62d2 = {"isSchedulerWorker", SkinID.SKIN_NONE, "thread", "Ljava/lang/Thread;", "mayNotBlock", "kotlinx-coroutines-core"}, m63k = 2, m64mv = {1, 6, 0}, m66xi = 48)
public final class CoroutineSchedulerKt {
    public static final boolean isSchedulerWorker(Thread thread) {
        return thread instanceof CoroutineScheduler.Worker;
    }

    public static final boolean mayNotBlock(Thread thread) {
        return (thread instanceof CoroutineScheduler.Worker) && ((CoroutineScheduler.Worker) thread).state == CoroutineScheduler.WorkerState.CPU_ACQUIRED;
    }
}
