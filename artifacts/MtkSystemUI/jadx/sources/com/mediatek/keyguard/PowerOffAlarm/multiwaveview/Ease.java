package com.mediatek.keyguard.PowerOffAlarm.multiwaveview;

import android.animation.TimeInterpolator;

/* JADX INFO: loaded from: classes.dex */
class Ease {

    static class Cubic {
        public static final TimeInterpolator easeIn = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Cubic.1
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = input / 1.0f;
                return (1.0f * input2 * input2 * input2) + 0.0f;
            }
        };
        public static final TimeInterpolator easeOut = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Cubic.2
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = (input / 1.0f) - 1.0f;
                return (1.0f * ((input2 * input2 * input2) + 1.0f)) + 0.0f;
            }
        };
        public static final TimeInterpolator easeInOut = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Cubic.3
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = input / 0.5f;
                if (input2 < 1.0f) {
                    return (0.5f * input2 * input2 * input2) + 0.0f;
                }
                float input3 = input2 - 2.0f;
                return (0.5f * ((input3 * input3 * input3) + 2.0f)) + 0.0f;
            }
        };
    }

    static class Quad {
        public static final TimeInterpolator easeIn = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Quad.1
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = input / 1.0f;
                return (1.0f * input2 * input2) + 0.0f;
            }
        };
        public static final TimeInterpolator easeOut = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Quad.2
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = input / 1.0f;
                return ((-1.0f) * input2 * (input2 - 2.0f)) + 0.0f;
            }
        };
        public static final TimeInterpolator easeInOut = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Quad.3
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = input / 0.5f;
                if (input2 < 1.0f) {
                    return (0.5f * input2 * input2) + 0.0f;
                }
                float input3 = input2 - 1.0f;
                return ((-0.5f) * ((input3 * (input3 - 2.0f)) - 1.0f)) + 0.0f;
            }
        };
    }

    static class Quart {
        public static final TimeInterpolator easeIn = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Quart.1
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = input / 1.0f;
                return (1.0f * input2 * input2 * input2 * input2) + 0.0f;
            }
        };
        public static final TimeInterpolator easeOut = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Quart.2
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = (input / 1.0f) - 1.0f;
                return ((-1.0f) * ((((input2 * input2) * input2) * input2) - 1.0f)) + 0.0f;
            }
        };
        public static final TimeInterpolator easeInOut = new TimeInterpolator() { // from class: com.mediatek.keyguard.PowerOffAlarm.multiwaveview.Ease.Quart.3
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float input) {
                float input2 = input / 0.5f;
                if (input2 < 1.0f) {
                    return (0.5f * input2 * input2 * input2 * input2) + 0.0f;
                }
                float input3 = input2 - 2.0f;
                return ((-0.5f) * ((((input3 * input3) * input3) * input3) - 2.0f)) + 0.0f;
            }
        };
    }
}
