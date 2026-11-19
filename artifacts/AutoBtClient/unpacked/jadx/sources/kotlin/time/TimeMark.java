package kotlin.time;

import com.autochips.bluetooth.skin.SkinID;
import kotlin.Metadata;

/* JADX INFO: compiled from: TimeSource.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000ø\u0001\u0001J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0005H\u0016J\u0014\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0003H\u0096\u0002ø\u0001\u0001J\u0014\u0010\t\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0003H\u0096\u0002ø\u0001\u0001\u0082\u0002\b\n\u0002\b!\n\u0002\b\u0019¨\u0006\n"}, m62d2 = {"Lkotlin/time/TimeMark;", SkinID.SKIN_NONE, "elapsedNow", "Lkotlin/time/Duration;", "hasNotPassedNow", SkinID.SKIN_NONE, "hasPassedNow", "minus", "duration", "plus", "kotlin-stdlib"}, m63k = 1, m64mv = {1, 7, 1}, m66xi = 48)
public interface TimeMark {
    /* JADX INFO: renamed from: elapsedNow-UwyO8pc */
    long mo1514elapsedNowUwyO8pc();

    boolean hasNotPassedNow();

    boolean hasPassedNow();

    /* JADX INFO: renamed from: minus-LRDsOJo */
    TimeMark mo1515minusLRDsOJo(long j);

    /* JADX INFO: renamed from: plus-LRDsOJo */
    TimeMark mo1516plusLRDsOJo(long j);

    /* JADX INFO: compiled from: TimeSource.kt */
    @Metadata(m63k = 3, m64mv = {1, 7, 1}, m66xi = 48)
    public static final class DefaultImpls {
        /* JADX INFO: renamed from: plus-LRDsOJo, reason: not valid java name */
        public static TimeMark m1658plusLRDsOJo(TimeMark timeMark, long j) {
            return new AdjustedTimeMark(timeMark, j, null);
        }

        /* JADX INFO: renamed from: minus-LRDsOJo, reason: not valid java name */
        public static TimeMark m1657minusLRDsOJo(TimeMark timeMark, long j) {
            return timeMark.mo1516plusLRDsOJo(Duration.m1574unaryMinusUwyO8pc(j));
        }

        public static boolean hasPassedNow(TimeMark timeMark) {
            return !Duration.m1555isNegativeimpl(timeMark.mo1514elapsedNowUwyO8pc());
        }

        public static boolean hasNotPassedNow(TimeMark timeMark) {
            return Duration.m1555isNegativeimpl(timeMark.mo1514elapsedNowUwyO8pc());
        }
    }
}
