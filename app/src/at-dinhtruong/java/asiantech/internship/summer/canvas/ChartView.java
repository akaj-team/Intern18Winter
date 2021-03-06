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
import asiantech.internship.summer.models.Money;

public class ChartView extends View {
    //zoom
    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 5.0f;

    private int mMode;
    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private float mPreTranslateX = 0f;
    private float mPreTranslateY = 0f;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private static final int DRAG = 1;
    private static final int NONE = 0;
    private static final int ZOOM = 2;
    private boolean mDragged = false;

    private Paint mPaintTextItem;
    private Paint mPaintTextCaption;
    private Paint mPaintColumnMoney;
    private Paint mPaintLine;
    private Paint mPaintColumnSales;
    private Paint mPaintColumnExpenses;
    private Paint mPaintNoteSales;
    private Paint mPaintNoteExpenses;
    private Paint mPaintRect;
    private List<Money> mListMoney = new ArrayList<>();

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        //mock Data
        createListMoney();
        //zoom
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        getStyleableAttributes(context, attrs);
    }

    private void getStyleableAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
        try {
            int colorOfColumnSales = typedArray.getColor(R.styleable.ChartView_colorOfColumnSales, 0);
            int colorOfColumnExpenses = typedArray.getColor(R.styleable.ChartView_colorOfColumnExpenses, 0);
            int colorOfText = typedArray.getColor(R.styleable.ChartView_colorOfText, 0);
            int colorOfLine = typedArray.getColor(R.styleable.ChartView_colorOfLine, 0);
            int colorOfColumnRect = typedArray.getColor(R.styleable.ChartView_colorOfColumnRect, 0);
            float widthOfColumn = typedArray.getDimension(R.styleable.ChartView_widthOfColumn, 0);
            float sizeOfTextCaption = typedArray.getDimension(R.styleable.ChartView_sizeOfTextCaption, 0);
            float sizeOfTextItem = typedArray.getDimension(R.styleable.ChartView_sizeOfTextItem, 0);
            mPaintColumnSales.setColor(colorOfColumnSales);
            mPaintColumnSales.setStrokeWidth(widthOfColumn);
            mPaintColumnExpenses.setColor(colorOfColumnExpenses);
            mPaintColumnExpenses.setStrokeWidth(widthOfColumn);
            mPaintNoteSales.setColor(colorOfColumnSales);
            mPaintNoteExpenses.setColor(colorOfColumnExpenses);
            mPaintTextItem.setColor(colorOfText);
            mPaintTextCaption.setColor(colorOfText);
            mPaintColumnMoney.setColor(colorOfText);
            mPaintLine.setColor(colorOfLine);
            mPaintTextCaption.setTextSize(sizeOfTextCaption);
            mPaintTextItem.setTextSize(sizeOfTextItem);
            mPaintColumnMoney.setTextSize(sizeOfTextItem);
            mPaintRect.setColor(colorOfColumnRect);
        } finally {
            typedArray.recycle();
        }
    }

    private void initPaint() {
        mPaintNoteSales = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteExpenses = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextItem = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextCaption = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumnSales = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumnExpenses = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumnMoney = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumnMoney.setTextAlign(Paint.Align.RIGHT);
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int xDistance = width / 12;
        int yDistance = 4 * height / 5;
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mScaleDetector.getFocusX(), mScaleDetector.getFocusY());
        //drawCaption
        drawCaption(width, height, canvas);
        //draw note
        drawNote(width, height, canvas);
        // drawline
        drawLineMoney(width, xDistance, yDistance, canvas);
        //zoom
        canvas.save();
        canvas.translate(mTranslateX / mScaleFactor, 0);
        //drawColumn
        drawColumnChart(height, xDistance, yDistance, canvas);
        canvas.restore();
        //drawLine
        drawRectangoMoney(height, xDistance, canvas);
        drawColumnMoney(xDistance, yDistance, canvas);
    }

    private void drawRectangoMoney(int height, int xDistance, Canvas canvas) {
        canvas.drawRect(0, height, xDistance + xDistance / 3, 0, mPaintRect);
    }

    private float getMaxChartWidth() {
        return (float) (getWidth() * 1.75);
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

    private void drawCaption(int width, int height, Canvas canvas) {
        canvas.drawText(getContext().getString(R.string.salesAndExpenses), (float) ((width) / (2.5)), height / 15, mPaintTextCaption);
    }

    private void drawNote(int width, int height, Canvas canvas) {
        canvas.drawRect((float) (width / 2.5), height - 130, (float) (width / 2.5 + 30), height - 100, mPaintNoteSales);
        canvas.drawRect((float) (width / 2.5 + 180), height - 130, (float) (width / 2.5 + 210), height - 100, mPaintNoteExpenses);
        canvas.drawText(getContext().getString(R.string.sales), (float) (width / 2.5 + 60), height - 100, mPaintTextItem);
        canvas.drawText(getContext().getString(R.string.expenses), (float) (width / 2.5 + 240), height - 100, mPaintTextItem);
    }

    private void drawColumnMoney(int xDistance, int yDistance, Canvas canvas) {
        canvas.drawText(String.valueOf(0), xDistance, yDistance, mPaintColumnMoney);
        for (int i = 1; i < 7; i++) {
            canvas.drawText(getContext().getString(R.string.usd) + getMaxMoney() * i / 5, xDistance, yDistance - yDistance / 7 * i, mPaintColumnMoney);
        }
    }

    private void drawLineMoney(int width, int xDistance, int yDistance, Canvas canvas) {
        canvas.drawLine(xDistance + xDistance / 3, yDistance, width, yDistance, mPaintColumnMoney);
        for (int i = 1; i < 7; i++) {
            canvas.drawLine(xDistance + xDistance / 3, yDistance - yDistance / 7 * i, width, yDistance - yDistance / 7 * i, mPaintLine);
        }
    }

    private void drawColumnChart(int height, int xDistance, int yDistance, Canvas canvas) {
        int size = mListMoney.size();
        for (int i = 0; i < size; i++) {
            canvas.drawLine((float) (1.8 * xDistance + 260 * i), yDistance, (float) (1.8 * xDistance + 260 * i), yDistance - (mListMoney.get(i).getSale() * height) / (getMaxMoney() * 7 / 4), mPaintColumnSales);
            canvas.drawLine((float) (2.1 * xDistance + 260 * i), yDistance, (float) (2.1 * xDistance + 260 * i), (yDistance - mListMoney.get(i).getExpense() * height / (getMaxMoney() * 7 / 4)), mPaintColumnExpenses);
            canvas.drawText(mListMoney.get(i).getMonth(), (float) (1.8 * xDistance + 260 * i), yDistance + yDistance / 20, mPaintTextItem);
        }
    }

    private void createListMoney() {
        mListMoney.add(new Money(getContext().getString(R.string.jan), 750000, 100000));
        mListMoney.add(new Money(getContext().getString(R.string.feb), 600000, 180000));
        mListMoney.add(new Money(getContext().getString(R.string.mar), 350000, 200000));
        mListMoney.add(new Money(getContext().getString(R.string.apr), 300000, 300000));
        mListMoney.add(new Money(getContext().getString(R.string.may), 105000, 42000));
        mListMoney.add(new Money(getContext().getString(R.string.jun), 130000, 80000));
        mListMoney.add(new Money(getContext().getString(R.string.jul), 150000, 100000));
        mListMoney.add(new Money(getContext().getString(R.string.aug), 182000, 180000));
        mListMoney.add(new Money(getContext().getString(R.string.sep), 177000, 200000));
        mListMoney.add(new Money(getContext().getString(R.string.oct), 193000, 300000));
        mListMoney.add(new Money(getContext().getString(R.string.nov), 115000, 420000));
        mListMoney.add(new Money(getContext().getString(R.string.dec), 125000, 180000));
    }

    private int getMaxMoney() {
        List<Integer> listMoney = new ArrayList<>();
        if (mListMoney != null) {
            for (Money objMoney : mListMoney) {
                listMoney.add(objMoney.getSale());
                listMoney.add(objMoney.getExpense());
            }
        }
        return Collections.max(listMoney);
    }

    //zoom in zoom out
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

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
            invalidate();
            return true;
        }
    }
}
