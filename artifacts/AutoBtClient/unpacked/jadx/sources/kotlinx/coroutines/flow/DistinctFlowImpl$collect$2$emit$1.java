package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: compiled from: Distinct.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m63k = 3, m64mv = {1, 6, 0}, m66xi = 48)
@DebugMetadata(m70c = "kotlinx.coroutines.flow.DistinctFlowImpl$collect$2", m71f = "Distinct.kt", m72i = {}, m73l = {81}, m74m = "emit", m75n = {}, m76s = {})
final class DistinctFlowImpl$collect$2$emit$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DistinctFlowImpl.C07332<T> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    DistinctFlowImpl$collect$2$emit$1(DistinctFlowImpl.C07332<? super T> c07332, Continuation<? super DistinctFlowImpl$collect$2$emit$1> continuation) {
        super(continuation);
        this.this$0 = c07332;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
