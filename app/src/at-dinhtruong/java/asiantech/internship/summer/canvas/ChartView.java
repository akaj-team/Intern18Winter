package asiantech.internship.summer.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Money;

public class ChartView extends View {
    private Paint mPaintTextItem;
    private Paint mPaintTextCaption;
    private Paint mPaintLine;
    private Paint mPaintColumn;
    private List<Money> mListMoney;

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        createListMoney();
    }

    private void initPaint() {
        mPaintTextItem = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextItem = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintColumn = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumn.setColor(getResources().getColor(R.color.colorBlue));
        mPaintColumn.setStrokeWidth(getHeightScreen() / 30);
    }

    private int getWidthScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getHeightScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidthScreen();
        int height = getHeightScreen();
        int xDistance = width / 12;
        int yDistance = 4 * getHeightScreen() / 5;
        drawColumnChart(width, height, xDistance, yDistance, canvas);
        drawLineMoney(width, height, xDistance, yDistance, canvas);
        mPaint.setTextSize(50);
        canvas.drawText(getContext().getString(R.string.salesAndExpenses), (float) ((width) / (2.5)), height / 20, mPaint);
        //draw note
        mPaint.reset();
        mPaint.setColor(getResources().getColor(R.color.colorBlue));
        canvas.drawRect((float) (width / 2.5), height - 130, (float) (width / 2.5 + 30), height - 100, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorBlue));
        canvas.drawRect((float) (width / 2.5 + 180), height - 130, (float) (width / 2.5 + 210), height - 100, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorBlack));
        mPaint.setTextSize(35);
        canvas.drawText(getContext().getString(R.string.sales), (float) (width / 2.5 + 60), height - 100, mPaint);
        canvas.drawText(getContext().getString(R.string.expenses), (float) (width / 2.5 + 240), height - 100, mPaint);

    }

    private void drawLineMoney(int width, int height, int xDistance, int yDistance, Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.colorBlack));
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setTextSize(35);
        canvas.drawText("0", xDistance, yDistance, mPaint);
        canvas.drawLine(xDistance + xDistance / 3, yDistance, width, yDistance, mPaint);
        for (int i = 1; i < 8; i++) {
            canvas.drawText("$ " + 20000 * i, xDistance, yDistance - yDistance / 8 * i, mPaint);
            canvas.drawLine(xDistance + xDistance / 3, yDistance - height / 10 * i, width, yDistance - height / 10 * i, mPaint);
        }
        mPaint.reset();
    }

    private void drawColumnChart(int width, int height, int xDistance, int yDistance, Canvas canvas) {
        for (int i = 0; i < mListMoney.size(); i++) {
            mPaint.setColor(getResources().getColor(R.color.colorBlue));
            mPaint.setStrokeWidth(height / 30);
            canvas.drawLine((float) (1.8 * xDistance + 250 * i), yDistance, (float) (1.8 * xDistance + 250 * i), yDistance - (mListMoney.get(i).getSale() * height) / 200000, mPaint);
            mPaint.setColor(getResources().getColor(R.color.colorGray));
            mPaint.setStrokeWidth(height / 30);
            canvas.drawLine((float) (2.1 * xDistance + 250 * i), yDistance, (float) (2.1 * xDistance + 250 * i), (yDistance - mListMoney.get(i).getExpense() * height / 200000), mPaint);
            mPaint.reset();
            mPaint.setColor(getResources().getColor(R.color.colorBlack));
            mPaint.setTextSize(30);
            canvas.drawText(mListMoney.get(i).getMonth(), (float) (1.8 * xDistance + 250 * i), yDistance + yDistance / 20, mPaint);
        }
    }

    private void createListMoney() {
        mListMoney = new ArrayList<>();
        mListMoney.add(new Money("Jan", 70000, 10000));
        mListMoney.add(new Money("Feb", 80000, 18000));
        mListMoney.add(new Money("Mar", 75000, 20000));
        mListMoney.add(new Money("Apr", 90000, 30000));
        mListMoney.add(new Money("May", 105000, 42000));
        mListMoney.add(new Money("Jun", 130000, 80000));
    }
}

