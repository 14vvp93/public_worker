package kotlinx.coroutines.flow;

import com.autochips.bluetooth.skin.SkinID;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: compiled from: SafeCollector.common.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, m62d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", SkinID.SKIN_NONE, "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, m63k = 1, m64mv = {1, 6, 0}, m66xi = 48)
public final class FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7 implements Flow<Integer> {
    final /* synthetic */ int[] $this_asFlow$inlined;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7$1 */
    /* JADX INFO: compiled from: SafeCollector.common.kt */
    @Metadata(m63k = 3, m64mv = {1, 6, 0}, m66xi = 48)
    @DebugMetadata(m70c = "kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7", m71f = "Builders.kt", m72i = {0}, m73l = {115}, m74m = "collect", m75n = {"$this$asFlow_u24lambda_u2d13"}, m76s = {"L$0"})
    public static final class C07401 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C07401(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7.this.collect(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector<? super Integer> flowCollector, Continuation<? super Unit> continuation) throws Throwable {
        C07401 c07401;
        int i;
        FlowCollector flowCollector2;
        int i2;
        int[] iArr;
        if (continuation instanceof C07401) {
            c07401 = (C07401) continuation;
            if ((c07401.label & Integer.MIN_VALUE) != 0) {
                c07401.label -= Integer.MIN_VALUE;
            } else {
                c07401 = new C07401(continuation);
            }
        } else {
            c07401 = new C07401(continuation);
        }
        Object obj = c07401.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i3 = c07401.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            int[] iArr2 = this.$this_asFlow$inlined;
            i = 0;
            int length = iArr2.length;
            flowCollector2 = flowCollector;
            i2 = length;
            iArr = iArr2;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i2 = c07401.I$1;
            i = c07401.I$0;
            iArr = (int[]) c07401.L$1;
            FlowCollector flowCollector3 = (FlowCollector) c07401.L$0;
            ResultKt.throwOnFailure(obj);
            flowCollector2 = flowCollector3;
        }
        while (i < i2) {
            int i4 = iArr[i];
            i++;
            Integer numBoxInt = Boxing.boxInt(i4);
            c07401.L$0 = flowCollector2;
            c07401.L$1 = iArr;
            c07401.I$0 = i;
            c07401.I$1 = i2;
            c07401.label = 1;
            if (flowCollector2.emit(numBoxInt, c07401) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }

    public FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$7(int[] iArr) {
        this.$this_asFlow$inlined = iArr;
    }
}
