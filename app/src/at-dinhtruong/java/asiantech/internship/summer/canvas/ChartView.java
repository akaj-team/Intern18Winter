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
    private List<Money> mListMoney;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        createListMoney();
        //zoom
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        getStyleableAttributes(context, attrs);
    }

    private void getStyleableAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
        try {
            int colorOfColumnSales = a.getColor(R.styleable.ChartView_colorOfColumnSales, 0);
            int colorOfColumnExpenses = a.getColor(R.styleable.ChartView_colorOfColumnExpenses, 0);
            float widthOfColumn = a.getDimension(R.styleable.ChartView_widthOfColumn, 0);
            mPaintColumnSales.setColor(colorOfColumnSales);
            mPaintColumnSales.setStrokeWidth(widthOfColumn);
            mPaintColumnExpenses.setColor(colorOfColumnExpenses);
            mPaintColumnExpenses.setStrokeWidth(widthOfColumn);
        } finally {
            a.recycle();
        }
    }

    @SuppressLint("ResourceType")
    private void initPaint() {
        mPaintNoteSales = new Paint();
        mPaintNoteSales.setColor(getResources().getColor(R.color.colorBlue));

        mPaintNoteExpenses = new Paint();
        mPaintNoteExpenses.setColor(getResources().getColor(R.color.colorOrange));

        mPaintTextItem = new Paint();
        mPaintTextItem.setColor(getResources().getColor(R.color.colorBlack));
        mPaintTextItem.setTextSize(35);

        mPaintTextCaption = new Paint();
        mPaintTextCaption.setColor(getResources().getColor(R.color.colorBlack));
        mPaintTextCaption.setTextSize(60);

        mPaintColumnSales = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintColumnExpenses = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintColumnMoney = new Paint();
        mPaintColumnMoney.setColor(getResources().getColor(R.color.colorBlack));
        mPaintColumnMoney.setTextAlign(Paint.Align.RIGHT);
        mPaintColumnMoney.setTextSize(35);

        mPaintLine = new Paint();
        mPaintLine.setColor(getResources().getColor(R.color.colorBlack));

        mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRect.setColor(getResources().getColor(R.color.colorColumnMoney));
        mPaintRect.setStrokeWidth(50);
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
        drawLineMoney(width, height, xDistance, yDistance, canvas);
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
        canvas.drawText(getContext().getString(R.string.salesAndExpenses), (float) ((width) / (2.5)), height / 20, mPaintTextCaption);
    }

    private void drawNote(int width, int height, Canvas canvas) {
        canvas.drawRect((float) (width / 2.5), height - 130, (float) (width / 2.5 + 30), height - 100, mPaintNoteSales);
        canvas.drawRect((float) (width / 2.5 + 180), height - 130, (float) (width / 2.5 + 210), height - 100, mPaintNoteExpenses);
        canvas.drawText(getContext().getString(R.string.sales), (float) (width / 2.5 + 60), height - 100, mPaintTextItem);
        canvas.drawText(getContext().getString(R.string.expenses), (float) (width / 2.5 + 240), height - 100, mPaintTextItem);
    }

    private void drawColumnMoney(int xDistance, int yDistance, Canvas canvas) {
        canvas.drawText(String.valueOf(0), xDistance, yDistance, mPaintColumnMoney);
        for (int i = 1; i < 8; i++) {
            canvas.drawText(getContext().getString(R.string.usd) + 20000 * i, xDistance, yDistance - yDistance / 8 * i, mPaintColumnMoney);
        }
    }

    private void drawLineMoney(int width, int height, int xDistance, int yDistance, Canvas canvas) {
        canvas.drawLine(xDistance + xDistance / 3, yDistance, width, yDistance, mPaintColumnMoney);
        for (int i = 1; i < 8; i++) {
            canvas.drawLine(xDistance + xDistance / 3, yDistance - height / 10 * i, width, yDistance - height / 10 * i, mPaintLine);
        }
    }

    private void drawColumnChart(int height, int xDistance, int yDistance, Canvas canvas) {
        for (int i = 0; i < mListMoney.size(); i++) {
            canvas.drawLine((float) (1.8 * xDistance + 260 * i), yDistance, (float) (1.8 * xDistance + 260 * i), yDistance - (mListMoney.get(i).getSale() * height) / 200000, mPaintColumnSales);
            canvas.drawLine((float) (2.1 * xDistance + 260 * i), yDistance, (float) (2.1 * xDistance + 260 * i), (yDistance - mListMoney.get(i).getExpense() * height / 200000), mPaintColumnExpenses);
            canvas.drawText(mListMoney.get(i).getMonth(), (float) (1.8 * xDistance + 260 * i), yDistance + yDistance / 20, mPaintTextItem);
        }
    }

    private void createListMoney() {
        mListMoney = new ArrayList<>();
        mListMoney.add(new Money(getContext().getString(R.string.jan), 70000, 10000));
        mListMoney.add(new Money(getContext().getString(R.string.feb), 80000, 18000));
        mListMoney.add(new Money(getContext().getString(R.string.mar), 75000, 20000));
        mListMoney.add(new Money(getContext().getString(R.string.apr), 90000, 30000));
        mListMoney.add(new Money(getContext().getString(R.string.may), 105000, 42000));
        mListMoney.add(new Money(getContext().getString(R.string.jun), 130000, 80000));
        mListMoney.add(new Money(getContext().getString(R.string.jul), 78000, 10000));
        mListMoney.add(new Money(getContext().getString(R.string.aug), 82000, 18000));
        mListMoney.add(new Money(getContext().getString(R.string.sep), 77000, 20000));
        mListMoney.add(new Money(getContext().getString(R.string.oct), 93000, 30000));
        mListMoney.add(new Money(getContext().getString(R.string.nov), 115000, 42000));
        mListMoney.add(new Money(getContext().getString(R.string.dec), 125000, 80000));
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

