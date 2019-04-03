package com.example.base.icon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.example.base.R;

/**
 * IconFontTextView
 */
public class IconFontTextView extends AppCompatTextView {

    private static final String DEFAULT_ICON_FONT_PATH = "font/iconfont.ttf";

    public IconFontTextView(Context context) {
        this(context, null, 0);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconFont);
        if (typedArray != null) {
            String iconFontPath = typedArray.getString(R.styleable.IconFont_iconFontPath);
            if (TextUtils.isEmpty(iconFontPath)) {
                iconFontPath = DEFAULT_ICON_FONT_PATH;
            }

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), iconFontPath);
            if (typeface != null) {
                this.setTypeface(typeface);
            }
            typedArray.recycle();
        }
    }
}
