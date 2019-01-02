package asiantech.internship.summer.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Money;

public class GraphicViewSale extends View {
    private static final String CONTENT = "Sales and Expenses";
    private static final String SALE = "Sales";
    private static final String EXPENSE = "Expenses";
    private static final int DRAG = 1;
    private static final int NONE = 0;
    private static final int ZOOM = 2;
    private boolean mDragged = false;
    private Paint mPaintSale;
    private Paint mPaintExpense;
    private Paint mPaintText;
    private Paint mPaintTextMoney;
    private Paint mPaintTextContent;
    private Paint mPaintLine;
    private Paint mPaintNoteSale;
    private Paint mPaintNoteExpense;
    private int mMode;
    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private float mPreTranslateX = 0f;
    private float mPreTranslateY = 0f;
    private List<Money> mListMoney;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    public GraphicViewSale(Context context) {
        this(context, null);
    }

    public GraphicViewSale(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraphicViewSale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaintSale();
        initPaintExpense();
        initPaintText();
        initPaintTextMoney();
        initPaintTextContent();
        initPaintLine();
        initPaintNoteSale();
        initPaintNoteExpense();
        initListMoney();
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    private void initListMoney() {
        mListMoney = new ArrayList<>();
        //10 000$ = 1/20 height
        mListMoney.add(new Money("Jan", 70, 10));
        mListMoney.add(new Money("Feb", 80, 15));
        mListMoney.add(new Money("Mar", 75, 20));
        mListMoney.add(new Money("Apr", 90, 30));
        mListMoney.add(new Money("May", 105, 45));
        mListMoney.add(new Money("June", 130, 80));
        mListMoney.add(new Money("Jul", 115, 50));
        mListMoney.add(new Money("Aug", 100, 40));
        mListMoney.add(new Money("Sep", 90, 20));
        mListMoney.add(new Money("Oct", 80, 20));
        mListMoney.add(new Money("Nov", 120, 40));
        mListMoney.add(new Money("Dec", 140, 80));
    }

    private int getWithScreen() {
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
    public boolean onTouchEvent(MotionEvent event) {
        // Let the ScaleGestureDetector inspect all events.
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
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mScaleDetector.getFocusX(), mScaleDetector.getFocusY());
        canvas.save();
        canvas.translate(mTranslateX / mScaleFactor, 0);
        //Draw component in here
        int x = getWithScreen() / 10;
        int y = 4 * getHeightScreen() / 5;
        for (int i = 0; i < mListMoney.size(); i++) {
            canvas.drawLine(x + 250 * i + getWithScreen() / 50, y, x + 250 * i + getWithScreen() / 50, (y - mListMoney.get(i).getSale() * getHeightScreen() / 200), mPaintSale);
            canvas.drawLine(x + 250 * i + getWithScreen() / 50 + getWithScreen() / 30, y, x + 250 * i + getWithScreen() / 30 + getWithScreen() / 50, (y - mListMoney.get(i).getExpense() * getHeightScreen() / 200), mPaintExpense);
            canvas.drawText(mListMoney.get(i).getMonth(), x + 250 * i + getWithScreen() / 50 + getHeightScreen() / 70, y + getHeightScreen() / 40, mPaintText);
        }
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                canvas.drawText(getContext().getString(R.string.zero), 2 * getWithScreen() / 30, y - getHeightScreen() / 10 * i, mPaintTextMoney);
                canvas.drawLine(x - getWithScreen() / 50, y, 2 * getWithScreen(), y, mPaintLine);
            } else {
                canvas.drawText(getContext().getString(R.string.dola) + 20 * i + getContext().getString(R.string.thousan), 2 * getWithScreen() / 30, y - getHeightScreen() / 10 * i, mPaintTextMoney);
                canvas.drawLine(x - getWithScreen() / 50, y - getHeightScreen() / 10 * i, 2 * getWithScreen(), y - getHeightScreen() / 10 * i, mPaintLine);
            }
        }
        canvas.drawText(CONTENT, getWithScreen() / 2 - mPaintTextContent.measureText(CONTENT) / 2, getHeightScreen() / 20, mPaintTextContent);
        canvas.drawLine(getWithScreen() / 2 - mPaintTextContent.measureText(EXPENSE) - getHeightScreen() / 40, 9 * getHeightScreen() / 10, getWithScreen() / 2 - mPaintTextContent.measureText(EXPENSE) - getHeightScreen() / 40, 9 * getHeightScreen() / 10 - 30, mPaintNoteSale);
        canvas.drawText(SALE, getWithScreen() / 2 - mPaintTextContent.measureText(EXPENSE), 9 * getHeightScreen() / 10, mPaintText);
        canvas.drawLine(getWithScreen() / 2, 9 * getHeightScreen() / 10, getWithScreen() / 2, 9 * getHeightScreen() / 10 - 30, mPaintNoteExpense);
        canvas.drawText(EXPENSE, getWithScreen() / 2 + getHeightScreen() / 40, 9 * getHeightScreen() / 10, mPaintText);
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
        int count = mListMoney.size();
        return (count * 60 + (count - 1) * 180);
    }

    private void initPaintSale() {
        mPaintSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSale.setColor(getResources().getColor(R.color.colorColumnBlue));
        mPaintSale.setStrokeWidth(getHeightScreen() / 20);
    }

    private void initPaintExpense() {
        mPaintExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintExpense.setColor(getResources().getColor(R.color.colorColumnOrange));
        mPaintExpense.setStrokeWidth(getHeightScreen() / 20);
    }

    private void initPaintText() {
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(getResources().getColor(R.color.colorTextBlack));
        mPaintText.setTextSize(30);
    }

    private void initPaintTextMoney() {
        mPaintTextMoney = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextMoney.setColor(getResources().getColor(R.color.colorTextBlack));
        mPaintTextMoney.setTextAlign(Paint.Align.RIGHT);
        mPaintTextMoney.setTextSize(30);
    }

    private void initPaintLine() {
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setColor(getResources().getColor(R.color.colorLineGray));
    }

    private void initPaintTextContent() {
        mPaintTextContent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextContent.setColor(getResources().getColor(R.color.colorTextBlack));
        mPaintTextContent.setTextSize(60);
    }

    private void initPaintNoteSale() {
        mPaintNoteSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteSale.setColor(getResources().getColor(R.color.colorColumnBlue));
        mPaintNoteSale.setStrokeWidth(getHeightScreen() / 40);
    }

    private void initPaintNoteExpense() {
        mPaintNoteExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteExpense.setColor(getResources().getColor(R.color.colorColumnOrange));
        mPaintNoteExpense.setStrokeWidth(getHeightScreen() / 40);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 5.0f));
            invalidate();
            return true;
        }
    }
}
