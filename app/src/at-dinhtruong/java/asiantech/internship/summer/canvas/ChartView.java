package asiantech.internship.summer.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Money;

public class ChartView extends View {
    private Paint mPaintSale;
    private Paint mPaintExpense;
    private Paint mPaintText;
    private Paint mPaintTextMoney;
    private Paint mPaintTextContent;
    private Paint mPaintLine;
    private Paint mPaintNoteSale;
    private Paint mPaintNoteExpense;
    private Paint mPaint;
    private List<Money> mListMoney;

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        initPaint();
        initListMoney();
    }

    private void initListMoney() {
        mListMoney = new ArrayList<>();
        mListMoney.add(new Money("Jan", 70000, 10000));
        mListMoney.add(new Money("Feb", 80000, 18000));
        mListMoney.add(new Money("Mar", 75000, 20000));
        mListMoney.add(new Money("Apr", 90000, 30000));
        mListMoney.add(new Money("May", 105000, 42000));
        mListMoney.add(new Money("Jun", 130000, 80000));
    }

    private int getWidthScreen() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        return size.x;
    }

    private int getHeightScreen() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        return size.y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidthScreen();
        int height = getHeightScreen();
        int x = getWidthScreen() / 13;
        int y = 4 * getHeightScreen() / 5;
        drawColumnChart(width, height, x, y, canvas);
        drawLineMoney(width, height, x, y, canvas);
        canvas.drawText(getContext().getString(R.string.salesAndExpenses), getWidthScreen() / 2 - mPaintTextContent.measureText(getContext().getString(R.string.salesAndExpenses)) / 2, getHeightScreen() / 20, mPaintTextContent);
        canvas.drawLine(getWidthScreen() / 2 - mPaintTextContent.measureText(getContext().getString(R.string.expenses)) - getHeightScreen() / 40, 9 * getHeightScreen() / 10, getWidthScreen() / 2 - mPaintTextContent.measureText(getContext().getString(R.string.expenses)) - getHeightScreen() / 40, 9 * getHeightScreen() / 10 - 30, mPaintNoteSale);
        canvas.drawText(getContext().getString(R.string.sales), getWidthScreen() / 2 - mPaintTextContent.measureText(getContext().getString(R.string.expenses)), 9 * getHeightScreen() / 10, mPaintText);
        canvas.drawLine(getWidthScreen() / 2, 9 * getHeightScreen() / 10, getWidthScreen() / 2, 9 * getHeightScreen() / 10 - 30, mPaintNoteExpense);
        canvas.drawText(getContext().getString(R.string.expenses), getWidthScreen() / 2 + getHeightScreen() / 40, 9 * getHeightScreen() / 10, mPaintText);
    }

    private void drawLineMoney(int width, int height, int x, int y, Canvas canvas) {
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                //test
                mPaint.setColor(getResources().getColor(R.color.colorBlack));
                mPaint.setTextAlign(Paint.Align.RIGHT);
                mPaint.setTextSize(30);
                canvas.drawText("0", 2 * width / 30, y - height / 10 * i, mPaint);
                mPaint.reset();
                mPaint.setColor(getResources().getColor(R.color.colorBlack));
                canvas.drawLine(x, y, width, y, mPaint);
            } else {
                //test
                mPaint.setColor(getResources().getColor(R.color.colorBlack));
                mPaint.setTextAlign(Paint.Align.RIGHT);
                mPaint.setTextSize(30);
                canvas.drawText("$" + 20 * i + ",000", 2 * width / 30, y - height / 10 * i, mPaint);
                mPaint.reset();
                mPaint.setColor(getResources().getColor(R.color.colorBlack));
                canvas.drawLine(x, y - height / 10 * i, width, y - height / 10 * i, mPaint);
            }
        }
    }

    private void drawColumnChart(int width, int height, int x, int y, Canvas canvas) {
        for (int i = 0; i < mListMoney.size(); i++) {
            mPaint.setColor(getResources().getColor(R.color.colorBlue));
            mPaint.setStrokeWidth(getHeightScreen() / 30);
            canvas.drawLine(x + 250 * i + width / 20, y, x + 250 * i + width / 20, y - (mListMoney.get(i).getSale() * height) / 200000, mPaint);
            mPaint.setColor(getResources().getColor(R.color.colorGray));
            mPaint.setStrokeWidth(getHeightScreen() / 30);
            canvas.drawLine(x + 250 * i + width / 20 + width / 30, y, x + 250 * i + width / 30 + width / 20, (y - mListMoney.get(i).getExpense() * height / 200000), mPaint);
            mPaint.reset();
            mPaint.setColor(getResources().getColor(R.color.colorBlack));
            mPaint.setTextSize(30);
            canvas.drawText(mListMoney.get(i).getMonth(), x + 250 * i + width / 20 + width / 190, y + height / 30, mPaint);
        }
    }

    private void initPaint() {
        mPaintSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSale.setColor(getResources().getColor(R.color.colorBlue));
        mPaintSale.setStrokeWidth(getHeightScreen() / 30);

        mPaintExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintExpense.setColor(getResources().getColor(R.color.colorGray));
        mPaintExpense.setStrokeWidth(getHeightScreen() / 30);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(getResources().getColor(R.color.colorBlack));
        mPaintText.setTextSize(30);

        mPaintTextMoney = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextMoney.setColor(getResources().getColor(R.color.colorBlack));
        mPaintTextMoney.setTextAlign(Paint.Align.RIGHT);
        mPaintTextMoney.setTextSize(30);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setColor(getResources().getColor(R.color.colorBlack));

        mPaintTextContent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextContent.setColor(getResources().getColor(R.color.colorBlack));
        mPaintTextContent.setTextSize(50);

        mPaintNoteSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteSale.setColor(getResources().getColor(R.color.colorBlue));
        mPaintNoteSale.setStrokeWidth(getHeightScreen() / 40);

        mPaintNoteExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteExpense.setColor(getResources().getColor(R.color.colorGray));
        mPaintNoteExpense.setStrokeWidth(getHeightScreen() / 40);
    }
}

