package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.canvas.model.Money;

public class VerticalChartView extends View {
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

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
    private Paint mPaintText;
    private Paint mPaintLine;
    private Paint mPaintColumnSales;
    private Paint mPaintColumnExpenses;
    private Paint mPaintSalesNote;
    private Paint mPaintExpensesNote;
    private Rect mBounds;
    private ScaleGestureDetector mScaleDetector;


    public VerticalChartView(Context context) {
        super(context);
    }

    public VerticalChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addData();
        mBounds = new Rect();
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        initPaintObjects();
        getStyleableAttributes(context, attrs);
    }

    private void getStyleableAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerticalChartView);
        try {
            int salesColor = array.getColor(R.styleable.VerticalChartView_columnSalesColor, 0);
            int expensesColor = array.getColor(R.styleable.VerticalChartView_columnExpensesColor, 0);
            float columnWidth = array.getDimension(R.styleable.VerticalChartView_columnWidth, 0);
            float noteWidth = array.getDimension(R.styleable.VerticalChartView_noteWidth, 0);
            float lineWidth = array.getDimension(R.styleable.VerticalChartView_lineWidth, 0);
            mPaintColumnSales.setColor(salesColor);
            mPaintColumnSales.setStrokeWidth(columnWidth);
            mPaintColumnExpenses.setColor(expensesColor);
            mPaintColumnExpenses.setStrokeWidth(columnWidth);
            mPaintSalesNote.setColor(salesColor);
            mPaintExpensesNote.setColor(expensesColor);
            mPaintSalesNote.setStrokeWidth(noteWidth);
            mPaintExpensesNote.setStrokeWidth(noteWidth);
            mPaintLine.setStrokeWidth(lineWidth);
        } finally {
            array.recycle();
        }
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
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(Color.BLACK);
        mPaintText.setStyle(Paint.Style.FILL);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setColor(Color.BLACK);
        mPaintLine.setStyle(Paint.Style.STROKE);

        mPaintSalesNote = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSalesNote.setStyle(Paint.Style.FILL);

        mPaintExpensesNote = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintExpensesNote.setStyle(Paint.Style.FILL);

        mPaintColumnSales = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumnSales.setStyle(Paint.Style.FILL);

        mPaintColumnExpenses = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumnExpenses.setStyle(Paint.Style.FILL);
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
        return (count * (50 * 2 + 130));
    }

    private void drawChartLine(Canvas canvas, int width, int height) {
        int firstLineHeight = height * 3 / 4;
        int startLinePosition = width / 8;

        for (int i = 0; i <= 7; i++) {
            canvas.drawLine(startLinePosition, firstLineHeight - i * height / 14, startLinePosition + getMaxChartWidth(), firstLineHeight - i * height / 14, mPaintLine);
        }
    }

    private void drawChartColumn(Canvas canvas, int width, int height) {
        int smallColumnDistance = 25;
        int largeColumnDistance;
        int startColumnPosition = width / 8;
        int startColumnHeight = height * 3 / 4;
        int columnUnit = height / 14;
        mPaintText.setTextSize(getResources().getDimension(R.dimen.smallTextSize));
        for (int i = 0; i < mMoneyLists.size(); i++) {
            largeColumnDistance = (i == 0) ? 50 : 150;
            canvas.drawRect(startColumnPosition + largeColumnDistance, (float) (startColumnHeight - ((mMoneyLists.get(i).getSales() * columnUnit) / moneyUnit())), startColumnPosition + largeColumnDistance + 50, startColumnHeight, mPaintColumnSales);
            canvas.drawRect(startColumnPosition + largeColumnDistance + 50 + smallColumnDistance, (float) (startColumnHeight - ((mMoneyLists.get(i).getExpenses() * columnUnit) / moneyUnit())), startColumnPosition + largeColumnDistance + smallColumnDistance + 50 * 2, startColumnHeight, mPaintColumnExpenses);

            canvas.drawText(mMoneyLists.get(i).getMonth(), startColumnPosition + largeColumnDistance, startColumnHeight + 50, mPaintText);
            startColumnPosition += largeColumnDistance + smallColumnDistance + 50;
        }
    }

    private void setChartTitle(Canvas canvas, int width, int height) {
        mPaintText.setTextSize(getResources().getDimension(R.dimen.largeTextSize));
        mPaintText.getTextBounds(getResources().getString(R.string.chartName), 0, getResources().getString(R.string.chartName).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.chartName), width / 3, height / 7, mPaintText);
    }

    private void setSalesNote(Canvas canvas, int width, int height) {
        int salesWidthPosition = width / 3;
        int salesHeightPosition = height * 9 / 10;
        canvas.drawPoint(salesWidthPosition, salesHeightPosition, mPaintSalesNote);

        mPaintText.setTextSize(getResources().getDimension(R.dimen.mediumTexSize));
        mPaintText.getTextBounds(getResources().getString(R.string.noteSales), 0, getResources().getString(R.string.noteSales).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.noteSales), salesWidthPosition + 30, salesHeightPosition + 20, mPaintText);
    }

    private void setExpensesNote(Canvas canvas, int width, int height) {
        int expensesWidthPosition = width / 2;
        int expensesHeightPosition = height * 9 / 10;
        canvas.drawPoint(expensesWidthPosition, expensesHeightPosition, mPaintExpensesNote);

        mPaintText.setTextSize(getResources().getDimension(R.dimen.mediumTexSize));
        mPaintText.getTextBounds(getResources().getString(R.string.noteExpenses), 0, getResources().getString(R.string.noteExpenses).length(), mBounds);
        canvas.drawText(getResources().getString(R.string.noteExpenses), expensesWidthPosition + 30, expensesHeightPosition + 20, mPaintText);
    }

    private void setMoneyValue(Canvas canvas, int width, int height) {
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(Color.WHITE);
        canvas.drawRect(0, height / 20 + 10, width / 8, height * 19 / 20 - 10, mPaintText);

        mPaintText.setTextSize(getResources().getDimension(R.dimen.smallTextSize));
        mPaintText.setColor(Color.BLACK);

        int startWidthPosition = 50;
        int firstNumberHeight = height * 3 / 4;
        DecimalFormat df = new DecimalFormat("0");

        for (int i = 0; i <= 7; i++) {
            canvas.drawText("$" + df.format(moneyUnit() * i), startWidthPosition, firstNumberHeight - i * height / 14 + 15, mPaintText);
        }
    }

    private double maxMoneyInput(List<Money> moneyList) {
        List<Double> listChartValue = new ArrayList<>();

        for (Money money : moneyList) {
            listChartValue.add((double) money.getSales());
            listChartValue.add((double) money.getExpenses());
        }
        return Collections.max(listChartValue);
    }

    private double moneyUnit() {
        int countDigits = 0;
        double twoFirstDigits = maxMoneyInput(mMoneyLists);
        while (twoFirstDigits >= 100) {
            twoFirstDigits = twoFirstDigits / 10;
            countDigits++;
        }

        int quotient = (int) twoFirstDigits / 7;
        int surplus = (int) twoFirstDigits % 7;
        if (surplus == 0) {
            return quotient * Math.pow(10, countDigits);
        } else {
            return (quotient + 1) * Math.pow(10, countDigits);
        }
    }

    private void addData() {
        mMoneyLists.add(new Money(getResources().getString(R.string.abbrJanuary), 700000, 10000));
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
