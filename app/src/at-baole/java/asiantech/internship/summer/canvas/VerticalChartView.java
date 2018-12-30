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
        setChartNumber(canvas, width, height);
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
        return (count * 60 + (count - 1) * 180);
    }

    private void drawChartLine(Canvas canvas, int width, int height) {
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, height / 4, width / 8 + getMaxChartWidth(), 3 * height / 4, mPaint);
        int maxLineHeight = 2 * height / 3;
        int i = 0;

        while (maxLineHeight - i * height / 24 >= height / 3) {
            mPaint.setTextSize(getResources().getDimension(R.dimen.smallTextSize));
            canvas.drawLine(width / 5, maxLineHeight - i * height / 24, width / 8 + getMaxChartWidth(), maxLineHeight - i * height / 24, mPaint);
            i++;
        }
    }

    private void drawChartColumn(Canvas canvas, int width, int height) {
        int smallColumnDistance = 20;
        int largeColumnDistance;
        int startPosition = width / 5;
        int size = mMoneyLists.size();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(getResources().getDimension(R.dimen.smallTextSize));

        for (int i = 0; i < size; i++) {
            largeColumnDistance = (i == 0) ? 25 : 150;
            mColumnPaint.setColor(getResources().getColor(R.color.colorBlue));
            canvas.drawRect(startPosition + largeColumnDistance, height - (height / 4 + height / 12) - ((mMoneyLists.get(i).getDolphins() * height / 24) / 20000), startPosition + largeColumnDistance + COLUMN_WIDTH, height - (height / 4 + height / 12), mColumnPaint);
            mColumnPaint.setColor(getResources().getColor(R.color.colorOrange));
            canvas.drawRect(startPosition + largeColumnDistance + COLUMN_WIDTH + smallColumnDistance, height - (height / 4 + height / 12) - ((mMoneyLists.get(i).getWhales() * height / 24) / 20000), startPosition + largeColumnDistance + COLUMN_WIDTH + smallColumnDistance + COLUMN_WIDTH, height - (height / 4 + height / 12), mColumnPaint);

            canvas.drawText(mMoneyLists.get(i).getYear(), startPosition + largeColumnDistance, height - (height / 4 + height / 12) + 40, mPaint);
            startPosition += largeColumnDistance + smallColumnDistance + 50;
        }
    }

    private void setChartTitle(Canvas canvas, int width, int height) {
        mPaint.setTextSize(getResources().getDimension(R.dimen.largeTextSize));
        mPaint.getTextBounds(getResources().getString(R.string.chartName), 0, getResources().getString(R.string.chartName).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.chartName), width / 4, height / 4 + height / 16, mPaint);
    }

    private void setSalesNote(Canvas canvas, int width, int height) {
        mPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(getResources().getColor(R.color.colorBlue));
        canvas.drawPoint(width / 3, height - (height / 4 + height / 36), mPointPaint);

        mPaint.setTextSize(getResources().getDimension(R.dimen.mediumTexSize));
        mPaint.getTextBounds(getResources().getString(R.string.noteSales), 0, getResources().getString(R.string.noteSales).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.noteSales), width / 3 + 20, height - (height / 4 + height / 36 - 10), mPaint);
    }

    private void setExpensesNote(Canvas canvas, int width, int height) {
        mPointPaint.setColor(getResources().getColor(R.color.colorOrange));
        canvas.drawPoint(width / 2, height - (height / 4 + height / 36), mPointPaint);

        mPaint.setTextSize(getResources().getDimension(R.dimen.mediumTexSize));
        mPaint.getTextBounds(getResources().getString(R.string.noteExpenses), 0, getResources().getString(R.string.noteExpenses).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.noteExpenses), width / 2 + 20, height - (height / 4 + height / 36 - 10), mPaint);
    }

    private void setChartNumber(Canvas canvas, int width, int height) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, height / 4 + 100, width / 5, 3 * height / 4 - 1, mPaint);

        mPaint.setTextSize(getResources().getDimension(R.dimen.smallTextSize));
        mPaint.setColor(Color.BLACK);
        int maxLineHeight = 2 * height / 3 + 10;
        int i = 0;

        while (maxLineHeight - i * height / 24 >= height / 3) {
            canvas.drawText("$" + String.valueOf(i * 20000), width / 24, maxLineHeight - i * height / 24, mPaint);
            i++;
        }
    }

    private void addData() {
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrJanuary), 70000, 10000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrFebruary), 80000, 15000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrMarch), 75000, 20000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrApril), 90000, 30000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrMay), 105000, 45000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrJune), 130000, 80000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrJuly), 135000, 85000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrAugust), 140000, 90000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrSeptember), 145000, 95000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrOctober), 150000, 100000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrNovember), 155000, 105000));
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrDecember), 160000, 110000));
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
