package io.github.zauther.android.hive.qjs.value;

public class QJSTag {
    public static final int JS_TAG_FIRST       = -11, /* first negative tag */
            JS_TAG_BIG_DECIMAL = -11,
            JS_TAG_BIG_INT     = -10,
            JS_TAG_BIG_FLOAT   = -9,
            JS_TAG_SYMBOL      = -8,
            JS_TAG_STRING      = -7,
            JS_TAG_MODULE      = -3, /* used internally */
            JS_TAG_FUNCTION_BYTECODE = -2, /* used internally */
            JS_TAG_OBJECT      = -1,
            JS_TAG_INT         = 0,
            JS_TAG_BOOL        = 1,
            JS_TAG_NULL        = 2,
            JS_TAG_UNDEFINED   = 3,
            JS_TAG_UNINITIALIZED = 4,
            JS_TAG_CATCH_OFFSET = 5,
            JS_TAG_EXCEPTION   = 6,
            JS_TAG_FLOAT64     = 7;
}
