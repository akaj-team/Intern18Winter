package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.canvas.model.Money;

public class VerticalChartView extends View {
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final int COLUMN_WIDTH = 50;


    private int mMode;
    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private float mPreTranslateX = 0f;
    private float mPreTranslateY = 0f;
    private float mScaleFactor = 1.f;
    private boolean mDragged = false;

    private List<Money> mMoneyLists = new ArrayList<>();
    private Paint mPaint;
    private Paint mPointPaint;
    private Paint mColumnPaint;
    private Rect mBounds;
    private ScaleGestureDetector mScaleDetector;


    public VerticalChartView(Context context) {
        super(context);
        addData();
        mBounds = new Rect();
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        initPaintObjects();
    }

    public VerticalChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addData();
        mBounds = new Rect();
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        initPaintObjects();
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

    private void initPaintObjects() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setStrokeWidth(20);

        mColumnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColumnPaint.setStyle(Paint.Style.FILL);
        mColumnPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mScaleDetector.getFocusX(), mScaleDetector.getFocusY());

        canvas.save();
        canvas.translate(mTranslateX / mScaleFactor, 0);

        drawChartLine(canvas, width, height);
        drawChartColumn(canvas, width, height);
        canvas.restore();

        setChartTitle(canvas, width, height);
        setSalesNote(canvas, width, height);
        setExpensesNote(canvas, width, height);
        setMoneyValue(canvas, width, height);
        canvas.restore();
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
        int count = mMoneyLists.size();
        return (count * (COLUMN_WIDTH * 2 + 130));
    }

    private void drawChartLine(Canvas canvas, int width, int height) {
        mPaint.setStyle(Paint.Style.STROKE);
        int firstLineHeight = height * 3 / 4;
        int startLinePosition = width / 8;

        for (int i = 0; i <= 7; i++) {
            canvas.drawLine(startLinePosition, firstLineHeight - i * height / 14, startLinePosition + getMaxChartWidth(), firstLineHeight - i * height / 14, mPaint);
        }
    }

    private void drawChartColumn(Canvas canvas, int width, int height) {
        int smallColumnDistance = 25;
        int largeColumnDistance;
        int startColumnPosition = width / 8;
        int startColumnHeight = height * 3 / 4;
        int columnUnit = height / 14;
        int moneyUnit = 20000;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(getResources().getDimension(R.dimen.smallTextSize));

        for (int i = 0; i < mMoneyLists.size(); i++) {
            largeColumnDistance = (i == 0) ? 50 : 150;
            mColumnPaint.setColor(getResources().getColor(R.color.colorBlue));
            canvas.drawRect(startColumnPosition + largeColumnDistance, startColumnHeight - ((mMoneyLists.get(i).getSales() * columnUnit) / moneyUnit), startColumnPosition + largeColumnDistance + COLUMN_WIDTH, startColumnHeight, mColumnPaint);
            mColumnPaint.setColor(getResources().getColor(R.color.colorOrange));
            canvas.drawRect(startColumnPosition + largeColumnDistance + COLUMN_WIDTH + smallColumnDistance, startColumnHeight - ((mMoneyLists.get(i).getExpenses() * columnUnit) / moneyUnit), startColumnPosition + largeColumnDistance + smallColumnDistance + COLUMN_WIDTH * 2, startColumnHeight, mColumnPaint);

            canvas.drawText(mMoneyLists.get(i).getYear(), startColumnPosition + largeColumnDistance, startColumnHeight + 50, mPaint);
            startColumnPosition += largeColumnDistance + smallColumnDistance + 50;
        }
    }

    private void setChartTitle(Canvas canvas, int width, int height) {
        mPaint.setTextSize(getResources().getDimension(R.dimen.largeTextSize));
        mPaint.getTextBounds(getResources().getString(R.string.chartName), 0, getResources().getString(R.string.chartName).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.chartName), width / 3, height / 7, mPaint);
    }

    private void setSalesNote(Canvas canvas, int width, int height) {
        int salesWidthPosition = width / 3;
        int salesHeightPosition = height * 9 / 10;
        mPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(getResources().getColor(R.color.colorBlue));
        canvas.drawPoint(salesWidthPosition, salesHeightPosition, mPointPaint);

        mPaint.setTextSize(getResources().getDimension(R.dimen.mediumTexSize));
        mPaint.getTextBounds(getResources().getString(R.string.noteSales), 0, getResources().getString(R.string.noteSales).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.noteSales), salesWidthPosition + 20, salesHeightPosition + 10, mPaint);
    }

    private void setExpensesNote(Canvas canvas, int width, int height) {
        int expensesWidthPosition = width / 2;
        int expensesHeightPosition = height * 9 / 10;
        mPointPaint.setColor(getResources().getColor(R.color.colorOrange));
        canvas.drawPoint(expensesWidthPosition, expensesHeightPosition, mPointPaint);

        mPaint.setTextSize(getResources().getDimension(R.dimen.mediumTexSize));
        mPaint.getTextBounds(getResources().getString(R.string.noteExpenses), 0, getResources().getString(R.string.noteExpenses).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.noteExpenses), expensesWidthPosition + 20, expensesHeightPosition + 10, mPaint);
    }

    private void setMoneyValue(Canvas canvas, int width, int height) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, height / 20 + 10, width / 8, height * 19 / 20 - 10, mPaint);

        mPaint.setTextSize(getResources().getDimension(R.dimen.smallTextSize));
        mPaint.setColor(Color.BLACK);
        int moneyUnit = 20000;
        int startWidthPosition = 50;
        int firstNumberHeight = height * 3 / 4;

        for (int i = 0; i <= 7; i++) {
            canvas.drawText("$" + i * moneyUnit, startWidthPosition, firstNumberHeight - i * height / 14 + 15, mPaint);
        }
    }

    private void addData() {
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrJanuary), 70000, 10000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrFebruary), 80000, 15000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrMarch), 75000, 20000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrApril), 90000, 30000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrMay), 105000, 45000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrJune), 130000, 80000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrJuly), 140000, 70000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrAugust), 120000, 60000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrSeptember), 135000, 70000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrOctober), 100000, 45000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrNovember), 115000, 50000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrDecember), 120000, 85000));
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
            invalidate();
            return true;
        }
    }
}
