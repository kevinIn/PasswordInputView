package com.mw.passwordinput;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.EditText;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @author mw
 * @desc mw
 * @time 2016/12/16 0016 10:27
 */
public class PasswordInputView extends EditText {

    private static final int defaultContMargin = 5;
    private static final int defaultSplitLineWidth = 10;

    private int borderColor = 0xFFCCCCCC;

    private int passwordLength = 6;
    private int passwordColor = 0xFFCCCCCC;
    private float passwordWidth = 8;

    private Paint passwordPaint = new Paint(ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(ANTI_ALIAS_FLAG);
    private int textLength;

    private float boxMarge; //字符方块的marge
    private float boxRadius; //字符方块的边角圆弧

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        borderColor = a.getColor(R.styleable.PasswordInputView_pivBorderColor, borderColor);
        passwordLength = a.getInt(R.styleable.PasswordInputView_pivPasswordLength, passwordLength);
        passwordColor = a.getColor(R.styleable.PasswordInputView_pivPasswordColor, passwordColor);
        passwordWidth = a.getDimension(R.styleable.PasswordInputView_pivPasswordWidth, passwordWidth);
        a.recycle();

        borderPaint.setColor(borderColor);
        passwordPaint.setStrokeWidth(passwordWidth);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);

        boxRadius = DisplayUtil.dp2px(context, 3);
        boxMarge = DisplayUtil.dp2px(context, 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // 分割线
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(defaultSplitLineWidth);
        for (int i = 0; i < passwordLength; i++) {
            RectF rect2 = generationSquareBoxRectF(height, width, i);
            canvas.drawRoundRect(rect2, boxRadius, boxRadius, borderPaint);
        }

        // 密码
        float cx = height / 2;
        float cy = height / 2 - passwordWidth;
        float half = width / passwordLength / 2;
        for (int i = 0; i < textLength; i++) {
            cx = width * i / passwordLength + half;
            Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.xing);
            canvas.drawBitmap(bitmap, cx - passwordWidth , cy, passwordPaint);
        }
    }

    @NonNull
    private RectF generationSquareBoxRectF(int height, int width, int i) {
        float boxWidth = (width / passwordLength);
        float boxHeight = height;
        float left = boxMarge + boxWidth * i;
        float right = boxWidth * (i + 1) - boxMarge;
        float top = boxMarge;
        float bottom = boxHeight - boxMarge;

        float min = Math.min(boxWidth, boxHeight);

        float dw = (boxWidth - min) / 2F;
        float dh = (boxHeight - min) / 2F;
        left += dw;
        right -= dw;
        top += dh;
        bottom -= dh;

        return new RectF(left, top, right, bottom);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        invalidate();
    }

    public int getPasswordColor() {
        return passwordColor;
    }

    public void setPasswordColor(int passwordColor) {
        this.passwordColor = passwordColor;
        passwordPaint.setColor(passwordColor);
        invalidate();
    }

    public float getPasswordWidth() {
        return passwordWidth;
    }

    public void setPasswordWidth(float passwordWidth) {
        this.passwordWidth = passwordWidth;
        passwordPaint.setStrokeWidth(passwordWidth);
        invalidate();
    }

}
