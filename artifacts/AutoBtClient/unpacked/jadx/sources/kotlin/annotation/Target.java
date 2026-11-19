package kotlin.annotation;

import com.autochips.bluetooth.skin.SkinID;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes.dex */
@java.lang.annotation.Target({ElementType.ANNOTATION_TYPE})
@MustBeDocumented
@Metadata(m61d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0014\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004R\u0017\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u0006"}, m62d2 = {"Lkotlin/annotation/Target;", SkinID.SKIN_NONE, "allowedTargets", SkinID.SKIN_NONE, "Lkotlin/annotation/AnnotationTarget;", "()[Lkotlin/annotation/AnnotationTarget;", "kotlin-stdlib"}, m63k = 1, m64mv = {1, 7, 1}, m66xi = 48)
@Target(allowedTargets = {AnnotationTarget.ANNOTATION_CLASS})
@Documented
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
public @interface Target {
    AnnotationTarget[] allowedTargets();
}
