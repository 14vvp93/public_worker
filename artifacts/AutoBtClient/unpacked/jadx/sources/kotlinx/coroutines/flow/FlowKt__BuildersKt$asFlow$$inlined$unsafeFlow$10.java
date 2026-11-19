package kotlinx.coroutines.flow;

import com.autochips.bluetooth.skin.SkinID;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.LongIterator;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.ranges.LongRange;

/* JADX INFO: compiled from: SafeCollector.common.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, m62d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", SkinID.SKIN_NONE, "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, m63k = 1, m64mv = {1, 6, 0}, m66xi = 48)
public final class FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10 implements Flow<Long> {
    final /* synthetic */ LongRange $this_asFlow$inlined;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10$1 */
    /* JADX INFO: compiled from: SafeCollector.common.kt */
    @Metadata(m63k = 3, m64mv = {1, 6, 0}, m66xi = 48)
    @DebugMetadata(m70c = "kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10", m71f = "Builders.kt", m72i = {0}, m73l = {115}, m74m = "collect", m75n = {"$this$asFlow_u24lambda_u2d19"}, m76s = {"L$0"})
    public static final class C07341 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C07341(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10.this.collect(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector<? super Long> flowCollector, Continuation<? super Unit> continuation) throws Throwable {
        C07341 c07341;
        FlowCollector flowCollector2;
        Iterator<Long> it;
        if (continuation instanceof C07341) {
            c07341 = (C07341) continuation;
            if ((c07341.label & Integer.MIN_VALUE) != 0) {
                c07341.label -= Integer.MIN_VALUE;
            } else {
                c07341 = new C07341(continuation);
            }
        } else {
            c07341 = new C07341(continuation);
        }
        Object obj = c07341.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c07341.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector2 = flowCollector;
            it = this.$this_asFlow$inlined.iterator();
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            it = (Iterator) c07341.L$1;
            FlowCollector flowCollector3 = (FlowCollector) c07341.L$0;
            ResultKt.throwOnFailure(obj);
            flowCollector2 = flowCollector3;
        }
        while (it.hasNext()) {
            Long lBoxLong = Boxing.boxLong(((LongIterator) it).nextLong());
            c07341.L$0 = flowCollector2;
            c07341.L$1 = it;
            c07341.label = 1;
            if (flowCollector2.emit(lBoxLong, c07341) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }

    public FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$10(LongRange longRange) {
        this.$this_asFlow$inlined = longRange;
    }
}
