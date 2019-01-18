package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Money;

public class GraphicViewSale extends View {
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private final ScaleGestureDetector mDetector;
    private Paint mPaint;
    private List<Money> mMoney = new ArrayList<>();
    private float mScaleFactor = 1.f;
    private int mMode;
    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private float mPreviousTranslateX = 0f;
    private float mPreviousTranslateY = 0f;
    private boolean mDragged = false;
    private float mTotalWidth = 0;
    private Paint mPaintColumnSale = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintColumnExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintColorText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintColorWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float widthOfColumn;

    public GraphicViewSale(Context context) {
        super(context);
        initPaint();
        mDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public GraphicViewSale(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        mDetector = new ScaleGestureDetector(context, new ScaleListener());
        init(attrs);
    }

    public GraphicViewSale(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        mDetector = new ScaleGestureDetector(context, new ScaleListener());

    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.GraphicViewSale, 0, 0);
        try {
            int colorBlue = typedArray.getColor(R.styleable.GraphicViewSale_colorBlue, 0);
            int colorOrange = typedArray.getColor(R.styleable.GraphicViewSale_colorOrange, 0);
            int colorGrey = typedArray.getColor(R.styleable.GraphicViewSale_colorGray, 0);
            int colorWhite = typedArray.getColor(R.styleable.GraphicViewSale_colorWhite, 0);
            widthOfColumn = typedArray.getDimension(R.styleable.GraphicViewSale_widthOfColumn, 0);

            mPaintColumnSale.setColor(colorBlue);
            mPaintColumnExpense.setColor(colorOrange);
            mPaintColorText.setColor(colorGrey);
            mPaintColorWhite.setColor(colorWhite);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mDetector.getFocusX(), mDetector.getFocusY());
        if ((mTranslateX * -1) < 0) {
            mTranslateX = 0;
        }
        if (-mTranslateX >= mTotalWidth - getWidth() + 80) {
            mTranslateX = -(mTotalWidth - getWidth() + 80);
        }
        float top = getHeight() * 3 / 4.0f;
        float unit = (getHeight() - 2 * (getHeight() - top) - 40) / maxChartValue(mMoney);
        int exponent = (int) Math.log10(maxChartValue(mMoney) / 13);
        int distance = (int) ((int) ((maxChartValue(mMoney) / 13) % Math.pow(10, exponent)) * Math.pow(10, exponent));
        float space = distance * unit;
        drawSetTitle(canvas, top, unit);
        drawRectSaleandExpense(canvas, top, space);
        canvas.translate(mTranslateX, 0);
        drawLine(canvas, top);
        drawChart(canvas, top, space, distance);
        canvas.restore();
        canvas.scale(mScaleFactor, mScaleFactor, mDetector.getFocusX(), mDetector.getFocusY());
        drawRectBackground(canvas, top, unit);
        drawTextSaleandExpense(canvas, top, space, distance);
    }

    private void initPaint() {
        createListMoney();
        WindowManager w = ((Activity) getContext()).getWindowManager();
        float element = (w.getDefaultDisplay().getWidth() - 110) / (13.0f * 6);
        mTotalWidth = 80 + 7 * element * mMoney.size() + 6 * element * (mMoney.size() - 1);
        setFadingEdgeLength(0);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorGray));
        mPaint.setStrokeWidth(1);
    }

    private void createListMoney() {
        mMoney.add(new Money("Jan", 700000, 10000));
        mMoney.add(new Money("Feb", 350000, 15000));
        mMoney.add(new Money("Mar", 56000, 20000));
        mMoney.add(new Money("Apr", 90000, 25000));
        mMoney.add(new Money("May", 100000, 30000));
        mMoney.add(new Money("June", 123000, 34000));
        mMoney.add(new Money("July", 50000, 23000));
        mMoney.add(new Money("Aug", 76000, 53000));
        mMoney.add(new Money("Sep", 80000, 45000));
        mMoney.add(new Money("Oct", 120000, 50000));
        mMoney.add(new Money("Nov", 94000, 53000));
        mMoney.add(new Money("Dec", 134000, 64000));
    }

    private int maxChartValue(List<Money> moneyList) {
        List<Integer> listChartValue = new ArrayList<>();
        for (Money money : moneyList) {
            listChartValue.add(money.getSale());
            listChartValue.add(money.getExpense());
        }
        return Collections.max(listChartValue);
    }

    private void drawTextSaleandExpense(Canvas canvas, float top, float space, float distance) {
        int n = 0;
        mPaintColorText.setStyle(Paint.Style.FILL);
        while (n <= 7) {
            canvas.drawText(getResources().getString(R.string.usd) + n * maxChartValue(mMoney) / 7, 10, top - n * top / 12 + 10, mPaintColorText);
            n++;
        }
    }

    private void drawRectBackground(Canvas canvas, float top, float unit) {
        mPaintColorWhite.setStyle(Paint.Style.FILL);
        canvas.drawRect(getWidth() - 5, top - 150 - unit * maxChartValue(mMoney), getWidth(), top + 50, mPaintColorWhite);
        canvas.drawRect(0, top - 150 - unit * maxChartValue(mMoney), 120, top + 50, mPaintColorWhite);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(5, top - 150 - unit * maxChartValue(mMoney), getWidth() - 5, top + 100, mPaint);
    }

    private void drawSetTitle(Canvas canvas, float top, float unit) {
        mPaintColorText.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_title));
        canvas.drawText(getResources().getString(R.string.title_chart), (getWidth() - 500) / 2.0f, top - 80 - unit * maxChartValue(mMoney), mPaintColorText);
    }

    private void drawRectSaleandExpense(Canvas canvas, float top, float unit) {
        mPaintColorText.setStyle(Paint.Style.FILL);
        mPaintColorText.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_name));
        canvas.drawText(getResources().getString(R.string.sale), getHeight() * 3 / 4 + 40, top + 90, mPaintColorText);
        canvas.drawText(getResources().getString(R.string.expense), getHeight() - 20, top + 90, mPaintColorText);
        canvas.drawRect(getHeight() * 3 / 4, top + 65, getHeight() * 3 / 4 + widthOfColumn, top + 65 + widthOfColumn, mPaintColumnSale);
        canvas.drawRect(getHeight() * 3 / 4 + 200, top + 65, getHeight() * 3 / 4 + 200 + widthOfColumn, top + 65 + widthOfColumn, mPaintColumnExpense);
    }

    private void drawLine(Canvas canvas, float top) {
        int n = 0;
        mPaint.setStyle(Paint.Style.FILL);
        while (n <= 7) {
            canvas.drawLine(120, top - n * top / 12, getWidth() * 2, top - n * top / 12, mPaintColorText);
            n++;
        }
    }

    private void drawChart(Canvas canvas, float top, float space, int distance) {
        float element = (getWidth() - 110) / (13.0f * 6);
        float left = 80 + element * 3;
        int n = 0;
        mPaint.setStyle(Paint.Style.FILL);
        float totalWidth = left + 7 * element * mMoney.size() + 6 * element * (mMoney.size() - 1);
        float line = top;
        if (totalWidth + 30 > getWidth()) {
            while (n <= maxChartValue(mMoney) + distance) {
                canvas.drawLine(180, line, totalWidth, line, mPaintColorText);
                line -= space;
                n += distance;
            }
        } else {
            while (n <= maxChartValue(mMoney) + distance) {
                canvas.drawLine(180, line, getWidth() - 30 - 3 * element, line, mPaintColorText);
                line -= space;
                n += distance;
            }
        }
        mPaint.setStyle(Paint.Style.FILL);
        for (Money money : mMoney) {
            mPaintColorText.setTextSize(getContext().getResources().getDimension(R.dimen.text_size_month));
            canvas.drawText(String.valueOf(money.getMonth()), left + 60, top + 35, mPaintColorText);
            canvas.drawRect(left, top, left += element * 3, top - money.getSale() * top / 12 / maxChartValue(mMoney) * 7, mPaintColumnSale);
            canvas.drawRect(left += element, top, left += element * 3, top - money.getExpense() * top / 12 / maxChartValue(mMoney) * 7, mPaintColumnExpense);
            left += element * 6;
        }
        mPaint.setStyle(Paint.Style.FILL);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mMode = DRAG;
                mStartX = event.getX() - mPreviousTranslateX;
                mStartY = event.getY() - mPreviousTranslateY;
                break;
            case MotionEvent.ACTION_MOVE:
                mTranslateX = event.getX() - mStartX;
                mTranslateY = event.getY() - mStartY;
                double distance = Math.abs(event.getX() - (mStartX + mPreviousTranslateX));
                if (distance > 0) {
                    mDragged = true;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = ZOOM;
                break;
            case MotionEvent.ACTION_UP:
                mMode = NONE;
                mDragged = false;
                mPreviousTranslateX = mTranslateX;
                mPreviousTranslateY = mTranslateY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mMode = DRAG;
                mPreviousTranslateX = mTranslateX;
                mPreviousTranslateY = mTranslateY;
                break;
        }
        if (mMode == ZOOM) {
            mDetector.onTouchEvent(event);
        }
        if ((mMode == DRAG && mScaleFactor != 1f && mDragged) || mMode == ZOOM || mDragged) {
            invalidate();
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
            if (mScaleFactor > 1f) {
                invalidate();
            }
            return true;
        }
    }
}
