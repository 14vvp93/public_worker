package kotlinx.coroutines;

import com.autochips.bluetooth.skin.SkinID;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: compiled from: Await.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\u001a=\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004\"\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a%\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0004\"\u00020\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\r\u001a\u001b\u0010\u0007\u001a\u00020\b*\b\u0012\u0004\u0012\u00020\n0\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, m62d2 = {"awaitAll", SkinID.SKIN_NONE, "T", "deferreds", SkinID.SKIN_NONE, "Lkotlinx/coroutines/Deferred;", "([Lkotlinx/coroutines/Deferred;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinAll", SkinID.SKIN_NONE, "jobs", "Lkotlinx/coroutines/Job;", "([Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", SkinID.SKIN_NONE, "(Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, m63k = 2, m64mv = {1, 6, 0}, m66xi = 48)
public final class AwaitKt {

    /* JADX INFO: renamed from: kotlinx.coroutines.AwaitKt$joinAll$1 */
    /* JADX INFO: compiled from: Await.kt */
    @Metadata(m63k = 3, m64mv = {1, 6, 0}, m66xi = 48)
    @DebugMetadata(m70c = "kotlinx.coroutines.AwaitKt", m71f = "Await.kt", m72i = {}, m73l = {54}, m74m = "joinAll", m75n = {}, m76s = {})
    static final class C06581 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C06581(Continuation<? super C06581> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AwaitKt.joinAll((Job[]) null, this);
        }
    }

    /* JADX INFO: renamed from: kotlinx.coroutines.AwaitKt$joinAll$3 */
    /* JADX INFO: compiled from: Await.kt */
    @Metadata(m63k = 3, m64mv = {1, 6, 0}, m66xi = 48)
    @DebugMetadata(m70c = "kotlinx.coroutines.AwaitKt", m71f = "Await.kt", m72i = {}, m73l = {66}, m74m = "joinAll", m75n = {}, m76s = {})
    static final class C06593 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C06593(Continuation<? super C06593> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AwaitKt.joinAll((Collection<? extends Job>) null, this);
        }
    }

    public static final <T> Object awaitAll(Deferred<? extends T>[] deferredArr, Continuation<? super List<? extends T>> continuation) {
        return deferredArr.length == 0 ? CollectionsKt.emptyList() : new AwaitAll(deferredArr).await(continuation);
    }

    public static final <T> Object awaitAll(Collection<? extends Deferred<? extends T>> collection, Continuation<? super List<? extends T>> continuation) {
        if (collection.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        Object[] array = collection.toArray(new Deferred[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        return new AwaitAll((Deferred[]) array).await(continuation);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final Object joinAll(Job[] jobArr, Continuation<? super Unit> continuation) throws Throwable {
        C06581 c06581;
        Job[] jobArr2;
        int length;
        Object obj;
        int i;
        if (continuation instanceof C06581) {
            c06581 = (C06581) continuation;
            if ((c06581.label & Integer.MIN_VALUE) != 0) {
                c06581.label -= Integer.MIN_VALUE;
            } else {
                c06581 = new C06581(continuation);
            }
        } else {
            c06581 = new C06581(continuation);
        }
        Object obj2 = c06581.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c06581.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            jobArr2 = jobArr;
            length = jobArr.length;
            obj = coroutine_suspended;
            i = 0;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            length = c06581.I$1;
            int i3 = c06581.I$0;
            Job[] jobArr3 = (Job[]) c06581.L$0;
            ResultKt.throwOnFailure(obj2);
            jobArr2 = jobArr3;
            i = i3;
            obj = coroutine_suspended;
        }
        while (i < length) {
            Job job = jobArr2[i];
            i++;
            c06581.L$0 = jobArr2;
            c06581.I$0 = i;
            c06581.I$1 = length;
            c06581.label = 1;
            if (job.join(c06581) == obj) {
                return obj;
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    public static final Object joinAll(Collection<? extends Job> collection, Continuation<? super Unit> continuation) throws Throwable {
        C06593 c06593;
        Iterator it;
        if (continuation instanceof C06593) {
            c06593 = (C06593) continuation;
            if ((c06593.label & Integer.MIN_VALUE) != 0) {
                c06593.label -= Integer.MIN_VALUE;
            } else {
                c06593 = new C06593(continuation);
            }
        } else {
            c06593 = new C06593(continuation);
        }
        Object obj = c06593.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c06593.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            it = collection.iterator();
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            it = (Iterator) c06593.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (it.hasNext()) {
            Job job = (Job) it.next();
            c06593.L$0 = it;
            c06593.label = 1;
            if (job.join(c06593) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
