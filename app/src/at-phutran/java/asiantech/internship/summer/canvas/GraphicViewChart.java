package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Money;

public class GraphicViewChart extends View {
    private static final int DRAG = 1;
    private static final int NONE = 0;
    private static final int ZOOM = 2;
    private int mBeginWidth;
    private int mBeginHeight;
    private boolean mDragged = false;
    private Paint mPaintSale;
    private Paint mPaintExpense;
    private Paint mPaintText;
    private Paint mPaintTextMoney;
    private Paint mPaintTextContent;
    private Paint mPaintLine;
    private Paint mPaintNoteSale;
    private Paint mPaintNoteExpense;
    private Paint mPaintBackground;
    private int mMode;
    private int mSize;
    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private float mPreTranslateX = 0f;
    private float mPreTranslateY = 0f;
    private List<Money> mListMoney = new ArrayList<>();
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    public GraphicViewChart(Context context) {
        this(context, null);
    }

    public GraphicViewChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraphicViewChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initListMoney();
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        getStyleableAttributes(context, attrs);
    }

    private int getMaxOfList() {
        List<Integer> listSale = new ArrayList<>();
        List<Integer> listExpense = new ArrayList<>();
        for (int i = 0; i < mSize; i++) {
            listSale.add(mListMoney.get(i).getSale());
            listExpense.add(mListMoney.get(i).getExpense());
        }
        int max = Collections.max(listSale);
        if (max <= Collections.max(listExpense)) {
            return Collections.max(listExpense);
        } else {
            return max;
        }
    }

    private void getStyleableAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GraphicViewChart);
        try {
            int colorOfLine = typedArray.getColor(R.styleable.GraphicViewChart_colorOfLine, 0);
            int colorOfSale = typedArray.getColor(R.styleable.GraphicViewChart_colorOfSale, 0);
            int colorOfExpense = typedArray.getColor(R.styleable.GraphicViewChart_colorOfExpense, 0);
            int colorOfText = typedArray.getColor(R.styleable.GraphicViewChart_colorOfText, 0);
            int colorOfBackground = typedArray.getColor(R.styleable.GraphicViewChart_colorOfBackground, 0);
            float widthOfColumn = typedArray.getDimension(R.styleable.GraphicViewChart_widthOfColumn, 0);
            float widthOfNoteColumn = typedArray.getDimension(R.styleable.GraphicViewChart_widthOfNoteColumn, 0);
            float textNote = typedArray.getDimension(R.styleable.GraphicViewChart_textNote, 0);
            float textContent = typedArray.getDimension(R.styleable.GraphicViewChart_textContent, 0);
            mPaintLine.setColor(colorOfLine);
            mPaintSale.setColor(colorOfSale);
            mPaintSale.setStrokeWidth(widthOfColumn);
            mPaintNoteSale.setColor(colorOfSale);
            mPaintNoteSale.setStrokeWidth(widthOfNoteColumn);
            mPaintExpense.setColor(colorOfExpense);
            mPaintExpense.setStrokeWidth(widthOfColumn);
            mPaintNoteExpense.setColor(colorOfExpense);
            mPaintNoteExpense.setStrokeWidth(widthOfNoteColumn);
            mPaintText.setColor(colorOfText);
            mPaintTextMoney.setColor(colorOfText);
            mPaintTextContent.setColor(colorOfText);
            mPaintBackground.setColor(colorOfBackground);
            mPaintTextMoney.setTextSize(textNote);
            mPaintTextMoney.setTextAlign(Paint.Align.RIGHT);
            mPaintText.setTextSize(textNote);
            mPaintTextContent.setTextSize(textContent);
        } finally {
            typedArray.recycle();
        }
    }

    private void initListMoney() {
        mListMoney.add(new Money(getContext().getString(R.string.Jan), 200, 10));
        mListMoney.add(new Money(getContext().getString(R.string.Feb), 80, 15));
        mListMoney.add(new Money(getContext().getString(R.string.Mar), 75, 20));
        mListMoney.add(new Money(getContext().getString(R.string.Apr), 90, 30));
        mListMoney.add(new Money(getContext().getString(R.string.May), 105, 45));
        mListMoney.add(new Money(getContext().getString(R.string.June), 130, 80));
        mListMoney.add(new Money(getContext().getString(R.string.Jul), 115, 200));
        mListMoney.add(new Money(getContext().getString(R.string.Aug), 100, 40));
        mListMoney.add(new Money(getContext().getString(R.string.Sep), 90, 20));
        mListMoney.add(new Money(getContext().getString(R.string.Oct), 80, 20));
        mListMoney.add(new Money(getContext().getString(R.string.Nov), 120, 40));
        mListMoney.add(new Money(getContext().getString(R.string.Dec), 140, 80));
        mSize = mListMoney.size();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mMode = DRAG;
                mStartX = event.getX() - mPreTranslateX;
                mStartY = event.getY() - mPreTranslateY;
                break;
            case MotionEvent.ACTION_MOVE:
                mTranslateX = event.getX() - mStartX;
                mTranslateY = event.getY() - mStartY;
                double distance = Math.sqrt(Math.pow(event.getX() - (mStartX + mPreTranslateX), 2) +
                        Math.pow(event.getY() - (mStartY + mPreTranslateY), 2));
                if (distance > 0 && mMode == DRAG) {
                    mDragged = true;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = ZOOM;
                break;
            case MotionEvent.ACTION_UP:
                mMode = NONE;
                mDragged = false;
                mPreTranslateX = mTranslateX;
                mPreTranslateY = mTranslateY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mMode = DRAG;
                mPreTranslateX = mTranslateX;
                mPreTranslateY = mTranslateY;
                break;
        }
        setChartSize(getWidth(), getHeight());
        if (mMode == ZOOM) {
            mScaleDetector.onTouchEvent(event);
        }
        if ((mMode == DRAG && mScaleFactor != 1f) || mDragged) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBeginWidth = getWidth() / 10;
        mBeginHeight = 5 * getHeight() / 6;
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mScaleDetector.getFocusX(), mScaleDetector.getFocusY());
        drawNoteChart(canvas);
        drawLine(canvas);
        canvas.save();
        canvas.translate(mTranslateX / mScaleFactor, 0);
        drawChart(canvas);
        canvas.restore();
        drawTextSale(canvas);
    }

    private void drawNoteChart(Canvas canvas) {
        canvas.drawText(getContext().getString(R.string.saleAndExpense), getWidth() / 2 - mPaintTextContent.measureText(getContext().getString(R.string.saleAndExpense)) / 2, getHeight() / 20, mPaintTextContent);
        canvas.drawLine(getWidth() / 2 - mPaintTextContent.measureText(getContext().getString(R.string.expense)) / 2 - getHeight() / 40, 11 * getHeight() / 12, getWidth() / 2 - mPaintTextContent.measureText(getContext().getString(R.string.expense)) / 2 - getHeight() / 40, 11 * getHeight() / 12 - 30, mPaintNoteSale);
        canvas.drawText(getContext().getString(R.string.sale), getWidth() / 2 - mPaintTextContent.measureText(getContext().getString(R.string.expense)) / 2, 11 * getHeight() / 12, mPaintText);
        canvas.drawLine(getWidth() / 2, 11 * getHeight() / 12, getWidth() / 2, 11 * getHeight() / 12 - 30, mPaintNoteExpense);
        canvas.drawText(getContext().getString(R.string.expense), getWidth() / 2 + getHeight() / 40, 11 * getHeight() / 12, mPaintText);
    }

    private void drawLine(Canvas canvas) {
        for (int i = 0; i < 12; i++) {
            canvas.drawLine(mBeginWidth - getWidth() / 50, mBeginHeight - 2 * getHeight() / 30 * i, getWidth(), mBeginHeight - 2 * getHeight() / 30 * i, mPaintLine);
        }
    }

    private void drawTextSale(Canvas canvas) {
        canvas.drawRect(0, 0, mBeginWidth - getWidth() / 50, getHeight(), mPaintBackground);
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                canvas.drawText(getContext().getString(R.string.zero), mPaintTextMoney.measureText(getContext().getString(R.string.defaulString)), mBeginHeight - 2 * getHeight() / 30 * i, mPaintTextMoney);
            } else {
                canvas.drawText(getContext().getString(R.string.dola) + getMaxOfList() / 10 * i + getContext().getString(R.string.thousan), mPaintTextMoney.measureText(getContext().getString(R.string.defaulString)), mBeginHeight - 2 * getHeight() / 30 * i, mPaintTextMoney);
            }
        }
    }

    private void drawChart(Canvas canvas) {
        // max = 2/3 height
        for (int i = 0; i < mSize; i++) {
            canvas.drawLine(mBeginWidth + 250 * i + getWidth() / 50, mBeginHeight, mBeginWidth + 250 * i + getWidth() / 50, (mBeginHeight - mListMoney.get(i).getSale() * 2 * getHeight() / (3 * getMaxOfList())), mPaintSale);
            canvas.drawLine(mBeginWidth + 250 * i + getWidth() / 50 + getWidth() / 30, mBeginHeight, mBeginWidth + 250 * i + getWidth() / 30 + getWidth() / 50, (mBeginHeight - mListMoney.get(i).getExpense() * 2 * getHeight() / (3 * getMaxOfList())), mPaintExpense);
            canvas.drawText(mListMoney.get(i).getMonth(), mBeginWidth + 250 * i + getWidth() / 50 + getHeight() / 70, mBeginHeight + getHeight() / 40, mPaintText);
        }
    }

    private void setChartSize(int width, int height) {
        if ((mTranslateX * -1) < 0) {
            mTranslateX = 0;
        }
        if ((mTranslateX * -1) >= getMaxChartWidth() - width + width / 8) {
            mTranslateX = -(getMaxChartWidth() - width + width / 8);
        }
        if (mTranslateY * -1 < 0) {
            mTranslateY = 0;
        } else if ((mTranslateY * -1) > (mScaleFactor - 1) * height) {
            mTranslateY = (1 - mScaleFactor) * height;
        }
    }

    private int getMaxChartWidth() {
        return mBeginWidth + 250 * 11 + getWidth() / 50;
    }

    private void initPaint() {
        mPaintSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextContent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextMoney = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 5.0f));
            invalidate();
            return true;
        }
    }
}
