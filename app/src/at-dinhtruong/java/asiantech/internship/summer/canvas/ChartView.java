package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Money;

public class ChartView extends View {
    //zoom
    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 5f;

    private float scaleFactor = 1.f;
    private ScaleGestureDetector mDetector;

    //These constants specify the mode that we're in
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int mode;
    private boolean dragged = false;

    //These two variables keep track of the X and Y coordinate of the finger when it first
    //touches the screen
    private float startX = 0f;
    private float startY = 0f;

    //These two variables keep track of the amount we need to translate the canvas along the X
    //and the Y coordinate
    private float translateX = 0f;
    private float translateY = 0f;

    //These two variables keep track of the amount we translated the X and Y coordinates, the last time we
    //panned.
    private float previousTranslateX = 0f;
    private float previousTranslateY = 0f;

    /////////////

    private Paint mPaintTextItem;
    private Paint mPaintTextCaption;
    private Paint mPaintLine;
    private Paint mPaintColumnSales;
    private Paint mPaintColumnExpenses;
    private Paint mPaintNote;
    private List<Money> mListMoney;

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        createListMoney();
        //zoom
        mDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    @SuppressLint("ResourceType")
    private void initPaint() {
//        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
//                R.styleable.ChartView, defStyleAttr, 0);
//
//        int colorColumnSales = getContext().getColor(getContext(), R.color.colorBlue);
//        int colorColumnexpenses = ContextCompat.getColor(context, R.color.colorOrange);
//        int widthColumn = getWidthScreen() / 30;
//
//            colorColumnSales = typedArray.getColor(R.styleable.colorOfColumnSales, colorColumnSales);
//            colorColumnexpenses = typedArray.getColor(R.attr.colorOfColumnSales, colorColumnexpenses);
//            widthColumn = (int) typedArray.getDimension(R.attr.widthOfColumn, widthColumn);
//            typedArray.recycle();
        mPaintNote = new Paint();
        mPaintNote.setColor(getResources().getColor(R.color.colorBlue));

        mPaintTextItem = new Paint();
        mPaintTextItem.setColor(getResources().getColor(R.color.colorBlack));
        mPaintTextItem.setTextSize(35);

        mPaintTextCaption = new Paint();
        mPaintTextCaption.setColor(getResources().getColor(R.color.colorBlack));
        mPaintTextCaption.setTextSize(50);

        mPaintColumnSales = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumnSales.setColor(getResources().getColor(R.color.colorBlue));
        mPaintColumnSales.setStrokeWidth(getHeightScreen() / 30);

        mPaintColumnExpenses = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintColumnExpenses.setColor(getResources().getColor(R.color.colorOrange));
        mPaintColumnExpenses.setStrokeWidth(getHeightScreen() / 30);

        mPaintLine = new Paint();
        mPaintLine.setColor(getResources().getColor(R.color.colorBlack));
        mPaintLine.setTextAlign(Paint.Align.RIGHT);
        mPaintLine.setTextSize(35);
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
        int yDistance = 4 * height / 5;
        //drawLine
        drawLineMoney(width, height, xDistance, yDistance, canvas);
        //drawColumn
        drawColumnChart(height, xDistance, yDistance, canvas);
        //drawCaption
        drawCaption(width, height, canvas);
        //draw note
        drawNote(width, height, canvas);
        //zoom
        canvas.save();

        //We're going to scale the X and Y coordinates by the same amount
        canvas.scale(scaleFactor, scaleFactor);

        //If translateX times -1 is lesser than zero, let's set it to zero. This takes care of the left bound
        if ((translateX * -1) < 0) {
            translateX = 0;
        }

        //This is where we take care of the right bound. We compare translateX times -1 to (scaleFactor - 1) * displayWidth.
        //If translateX is greater than that value, then we know that we've gone over the bound. So we set the value of
        //translateX to (1 - scaleFactor) times the display width. Notice that the terms are interchanged; it's the same
        //as doing -1 * (scaleFactor - 1) * displayWidth
        else if ((translateX * -1) > (scaleFactor - 1) * width) {
            translateX = (1 - scaleFactor) * width;
        }

        if (translateY * -1 < 0) {
            translateY = 0;
        }

        //We do the exact same thing for the bottom bound, except in this case we use the height of the display
        else if ((translateY * -1) > (scaleFactor - 1) * height) {
            translateY = (1 - scaleFactor) * height;
        }

        //We need to divide by the scale factor here, otherwise we end up with excessive panning based on our zoom level
        //because the translation amount also gets scaled according to how much we've zoomed into the canvas.
        canvas.translate(translateX / scaleFactor, translateY / scaleFactor);

        /* The rest of your canvas-drawing code */
        canvas.restore();
    }

    private void drawCaption(int width, int height, Canvas canvas) {
        canvas.drawText(getContext().getString(R.string.salesAndExpenses), (float) ((width) / (2.5)), height / 20, mPaintTextCaption);
    }

    private void drawNote(int width, int height, Canvas canvas) {
        canvas.drawRect((float) (width / 2.5), height - 130, (float) (width / 2.5 + 30), height - 100, mPaintNote);
        canvas.save();
        mPaintNote.setColor(getResources().getColor(R.color.colorOrange));
        canvas.drawRect((float) (width / 2.5 + 180), height - 130, (float) (width / 2.5 + 210), height - 100, mPaintNote);
        canvas.drawText(getContext().getString(R.string.sales), (float) (width / 2.5 + 60), height - 100, mPaintTextItem);
        canvas.drawText(getContext().getString(R.string.expenses), (float) (width / 2.5 + 240), height - 100, mPaintTextItem);
        canvas.restore();
    }

    private void drawLineMoney(int width, int height, int xDistance, int yDistance, Canvas canvas) {
        canvas.drawText("0", xDistance, yDistance, mPaintLine);
        canvas.drawLine(xDistance + xDistance / 3, yDistance, width, yDistance, mPaintLine);
        for (int i = 1; i < 8; i++) {
            canvas.drawText("$ " + 20000 * i, xDistance, yDistance - yDistance / 8 * i, mPaintLine);
            canvas.drawLine(xDistance + xDistance / 3, yDistance - height / 10 * i, width, yDistance - height / 10 * i, mPaintLine);
        }
    }

    private void drawColumnChart(int height, int xDistance, int yDistance, Canvas canvas) {
        for (int i = 0; i < mListMoney.size(); i++) {
            canvas.drawLine((float) (1.8 * xDistance + 250 * i), yDistance, (float) (1.8 * xDistance + 250 * i), yDistance - (mListMoney.get(i).getSale() * height) / 200000, mPaintColumnSales);
            canvas.drawLine((float) (2.1 * xDistance + 250 * i), yDistance, (float) (2.1 * xDistance + 250 * i), (yDistance - mListMoney.get(i).getExpense() * height / 200000), mPaintColumnExpenses);
            canvas.drawText(mListMoney.get(i).getMonth(), (float) (1.8 * xDistance + 250 * i), yDistance + yDistance / 20, mPaintTextItem);
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

    //zoom in zoom out
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                mode = DRAG;
                startX = event.getX() - previousTranslateX;
                startY = event.getY() - previousTranslateY;
                break;

            case MotionEvent.ACTION_MOVE:
                translateX = event.getX() - startX;
                translateY = event.getY() - startY;
                double distance = Math.sqrt(Math.pow(event.getX() - (startX + previousTranslateX), 2) +
                        Math.pow(event.getY() - (startY + previousTranslateY), 2)
                );

                if (distance > 0) {
                    dragged = true;
                }

                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                break;

            case MotionEvent.ACTION_UP:
                mode = NONE;
                dragged = false;
                previousTranslateX = translateX;
                previousTranslateY = translateY;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                mode = DRAG;
                previousTranslateX = translateX;
                previousTranslateY = translateY;
                break;
        }

        mDetector.onTouchEvent(event);
        if ((mode == DRAG && scaleFactor != 1f && dragged) || mode == ZOOM) {
            invalidate();
        }
        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
            return true;
        }
    }
}

