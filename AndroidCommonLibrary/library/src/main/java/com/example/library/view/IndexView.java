package com.example.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.library.util.DelayedTrigger;

/**
 * 首字母索引
 */
public class IndexView extends View {
    static final String TAG = "IndexView";

    private static final String[] WORDS = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private boolean showBackground = false;
    private Paint textPaint;
    private Paint selectionTextPaint;
    private int lastPosition = -1;
    private OnWordChangedListener mOnWordChangedListener;
    private int textSize;
    private int margin;

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IndexView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        textSize = (int) (14 * displayMetrics.scaledDensity);//14sp
        margin = (int) (3 * displayMetrics.density);//3dp
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(0xFFC0C0C0);

        selectionTextPaint = new Paint(textPaint);
        selectionTextPaint.setColor(0xFF3399FF);
        selectionTextPaint.setFakeBoldText(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int suggestionWidth = textSize + margin * 2;
        switch(widthMode) {
        case MeasureSpec.AT_MOST:
            if(widthSize > suggestionWidth) {
                widthSize = suggestionWidth;
            }
            break;
        case MeasureSpec.UNSPECIFIED:
            widthSize = suggestionWidth;
            break;
        }
        int suggestionHeight = (textSize + margin) * WORDS.length + margin;
        switch(heightMode) {
        case MeasureSpec.AT_MOST:
            if(heightSize > suggestionHeight) {
                heightSize = suggestionHeight;
            }
            break;
        case MeasureSpec.UNSPECIFIED:
            widthSize = suggestionHeight;
            break;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(showBackground) {
            canvas.drawColor(0x50D0D0D0);
        }
        String[] words = WORDS;
        Paint textPaint = this.textPaint;
        Paint selectionTextPaint = this.selectionTextPaint;
        float dh = (float) getHeight() / words.length;
        float x = getWidth() / 2;
        float y = dh / 2 + textSize / 2;
        for(int i = 0; i < words.length; ++i) {
            if(i == lastPosition) {
                canvas.drawText(words[i], x, y, selectionTextPaint);
            } else {
                canvas.drawText(words[i], x, y, textPaint);
            }
            y += dh;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int position = (int) (event.getY() / getHeight() * WORDS.length);
        if(position < 0) {
            position = 0;
        } else if(position >= WORDS.length) {
            position = WORDS.length - 1;
        }
        switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            showBackground = true;
            if(mOnWordChangedListener != null) {
                mOnWordChangedListener.onWordChanged(WORDS[position]);
            }
            lastPosition = position;
            invalidate();
            break;
        case MotionEvent.ACTION_MOVE:
            if(position != lastPosition) {
                if(mOnWordChangedListener != null) {
                    mOnWordChangedListener.onWordChanged(WORDS[position]);
                }
                lastPosition = position;
                invalidate();
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            showBackground = false;
            lastPosition = -1;
            invalidate();
            break;
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mWordPopup != null) {
            mWordPopup.dismiss();
            mWordPopup = null;
        }
    }

    public void setOnWordChangedListener(final OnWordChangedListener onWordChangedListener) {
        mOnWordChangedListener = new OnWordChangedListener() {

            @Override
            public void onWordChanged(String s) {
                if(mWordPopup != null) {
                    mWordPopup.show(s);
                }
                onWordChangedListener.onWordChanged(s);
            }
        };
    }

    public interface OnWordChangedListener {
        public void onWordChanged(String s);
    }

    private WordPopup mWordPopup;

    public void setWordPopup(WordPopup wordPopup) {
        mWordPopup = wordPopup;
    }

    private static class WordPopup {

        private View parentView;
        private PopupWindow wordPopup;
        private TextView contentView;
        private DelayedTrigger delayedTrigger = new DelayedTrigger(666) {

            @Override
            public void run() {
                wordPopup.dismiss();
            }
        };

        WordPopup(View parentView, TextView contentView) {
            this.parentView = parentView;
            this.contentView = contentView;
            int size = (int) (parentView.getResources().getDisplayMetrics().density * 80);//80dp
            wordPopup = new PopupWindow(contentView, size, size);
        }

        public void show(String str) {
            contentView.setText(str);
            wordPopup.showAtLocation(parentView, Gravity.CENTER, 0, 0);
            delayedTrigger.postDelayed();
        }

        public void dismiss() {
            wordPopup.dismiss();
            delayedTrigger.removeCallbacks();
        }
    }
}
