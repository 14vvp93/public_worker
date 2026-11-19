package kotlin;

import com.autochips.bluetooth.skin.SkinID;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AssertionsJVM.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, m62d2 = {"assert", SkinID.SKIN_NONE, "value", SkinID.SKIN_NONE, "lazyMessage", "Lkotlin/Function0;", SkinID.SKIN_NONE, "kotlin-stdlib"}, m63k = 5, m64mv = {1, 7, 1}, m66xi = 49, m67xs = "kotlin/PreconditionsKt")
class PreconditionsKt__AssertionsJVMKt {
    /* JADX INFO: renamed from: assert, reason: not valid java name */
    private static final void m204assert(boolean z) {
    }

    /* JADX INFO: renamed from: assert, reason: not valid java name */
    private static final void m205assert(boolean z, Function0<? extends Object> lazyMessage) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
    }
}
