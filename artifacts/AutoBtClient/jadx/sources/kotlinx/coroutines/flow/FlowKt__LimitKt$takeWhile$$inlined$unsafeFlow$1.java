package kotlinx.coroutines.flow;

import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.skin.SkinID;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import kotlinx.coroutines.flow.internal.FlowExceptions_commonKt;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: SafeCollector.common.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, m62d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", SkinID.SKIN_NONE, "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, m63k = 1, m64mv = {1, 6, 0}, m66xi = 48)
public final class FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1<T> implements Flow<T> {
    final /* synthetic */ Function2 $predicate$inlined;
    final /* synthetic */ Flow $this_takeWhile$inlined;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1$1 */
    /* JADX INFO: compiled from: SafeCollector.common.kt */
    @Metadata(m63k = 3, m64mv = {1, 6, 0}, m66xi = 48)
    @DebugMetadata(m70c = "kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1", m71f = "Limit.kt", m72i = {0}, m73l = {C0414R.styleable.AppCompatTheme_windowMinWidthMajor}, m74m = "collect", m75n = {"collector$iv"}, m76s = {"L$0"})
    public static final class C07721 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C07721(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) throws Throwable {
        C07721 c07721;
        FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1 flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1;
        if (continuation instanceof C07721) {
            c07721 = (C07721) continuation;
            if ((c07721.label & Integer.MIN_VALUE) != 0) {
                c07721.label -= Integer.MIN_VALUE;
            } else {
                c07721 = new C07721(continuation);
            }
        } else {
            c07721 = new C07721(continuation);
        }
        Object obj = c07721.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c07721.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = this.$this_takeWhile$inlined;
            FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1 flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$2 = new FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1(this.$predicate$inlined, flowCollector);
            try {
                c07721.L$0 = flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$2;
                c07721.label = 1;
                if (flow.collect(flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$2, c07721) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } catch (AbortFlowException e) {
                e = e;
                flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1 = flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$2;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1);
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1 = (FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1) c07721.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e2) {
                e = e2;
                FlowExceptions_commonKt.checkOwnership(e, flowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1);
            }
        }
        return Unit.INSTANCE;
    }

    public FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1(Flow flow, Function2 function2) {
        this.$this_takeWhile$inlined = flow;
        this.$predicate$inlined = function2;
    }
}
