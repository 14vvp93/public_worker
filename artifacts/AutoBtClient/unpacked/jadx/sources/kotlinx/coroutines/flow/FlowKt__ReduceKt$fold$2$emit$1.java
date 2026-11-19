package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: compiled from: Reduce.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m63k = 3, m64mv = {1, 6, 0}, m66xi = 176)
@DebugMetadata(m70c = "kotlinx.coroutines.flow.FlowKt__ReduceKt$fold$2", m71f = "Reduce.kt", m72i = {}, m73l = {45}, m74m = "emit", m75n = {}, m76s = {})
public final class FlowKt__ReduceKt$fold$2$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt__ReduceKt.C07912<T> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FlowKt__ReduceKt$fold$2$emit$1(FlowKt__ReduceKt.C07912<? super T> c07912, Continuation<? super FlowKt__ReduceKt$fold$2$emit$1> continuation) {
        super(continuation);
        this.this$0 = c07912;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
