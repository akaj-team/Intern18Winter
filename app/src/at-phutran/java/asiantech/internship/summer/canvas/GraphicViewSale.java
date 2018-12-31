package asiantech.internship.summer.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
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
    private Paint mPaintSale;
    private Paint mPaintExpense;
    private Paint mPaintText;
    private Paint mPaintTextMoney;
    private Paint mPaintTextContent;
    private Paint mPaintLine;
    private Paint mPaintNoteSale;
    private Paint mPaintNoteExpense;
    private List<Money> mListMoney;

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
    }

    private void initListMoney() {
        mListMoney = new ArrayList<>();
        //10 000$ = 1/20 height
        mListMoney.add(new Money("Jan", 7 * getHeightScreen() / 20, getHeightScreen() / 20));
        mListMoney.add(new Money("Feb", 2 * getHeightScreen() / 5, getHeightScreen() / 20 + getHeightScreen() / 40));
        mListMoney.add(new Money("Mar", 7 * getHeightScreen() / 20 + getHeightScreen() / 40, getHeightScreen() / 10));
        mListMoney.add(new Money("Apr", 9 * getHeightScreen() / 20, 3 * getHeightScreen() / 20));
        mListMoney.add(new Money("May", 3 * getHeightScreen() / 5 + getHeightScreen() / 40, getHeightScreen() / 5 + getHeightScreen() / 40));
        mListMoney.add(new Money("June", 13 * getHeightScreen() / 20, 2 * getHeightScreen() / 5));
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw component in here
        int x = getWithScreen() / 10;
        int y = 4 * getHeightScreen() / 5;
        for (int i = 0; i < mListMoney.size(); i++) {
            canvas.drawLine(x + 250 * i + getWithScreen() / 50, y, x + 250 * i + getWithScreen() / 50, (y - mListMoney.get(i).getSale()), mPaintSale);
            canvas.drawLine(x + 250 * i + getWithScreen() / 50 + getWithScreen() / 30, y, x + 250 * i + getWithScreen() / 30 + getWithScreen() / 50, (y - mListMoney.get(i).getExpense()), mPaintExpense);
            canvas.drawText(mListMoney.get(i).getMonth(), x + 250 * i + getWithScreen() / 50 + getHeightScreen() / 70, y + getHeightScreen() / 40, mPaintText);
        }
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                canvas.drawText(getContext().getString(R.string.zero), 2 * getWithScreen() / 30, y - getHeightScreen() / 10 * i, mPaintTextMoney);
                canvas.drawLine(x - getWithScreen() / 50, y, getWithScreen(), y, mPaintLine);
            } else {
                canvas.drawText(getContext().getString(R.string.dola) + 20 * i + getContext().getString(R.string.thousan), 2 * getWithScreen() / 30, y - getHeightScreen() / 10 * i, mPaintTextMoney);
                canvas.drawLine(x - getWithScreen() / 50, y - getHeightScreen() / 10 * i, getWithScreen(), y - getHeightScreen() / 10 * i, mPaintLine);
            }
        }
        canvas.drawText(CONTENT, getWithScreen() / 2  - mPaintTextContent.measureText(CONTENT) / 2, getHeightScreen() / 20, mPaintTextContent);
        canvas.drawLine(getWithScreen() / 2  - mPaintTextContent.measureText(EXPENSE) - getHeightScreen() / 40, 9 * getHeightScreen() / 10, getWithScreen() / 2  - mPaintTextContent.measureText(EXPENSE) - getHeightScreen() / 40, 9 * getHeightScreen() / 10 - 30, mPaintNoteSale);
        canvas.drawText(SALE, getWithScreen() / 2  - mPaintTextContent.measureText(EXPENSE), 9 * getHeightScreen() / 10, mPaintText);
        canvas.drawLine(getWithScreen() / 2 , 9 * getHeightScreen() / 10, getWithScreen() / 2, 9 * getHeightScreen() / 10 - 30, mPaintNoteExpense);
        canvas.drawText(EXPENSE, getWithScreen() / 2 + getHeightScreen() / 40, 9 * getHeightScreen() / 10, mPaintText);
    }
    private void initPaintSale () {
        mPaintSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSale.setColor(getResources().getColor(R.color.colorColumnBlue));
        mPaintSale.setStrokeWidth(getHeightScreen() / 20);
    }
    private void initPaintExpense () {
        mPaintExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintExpense.setColor(getResources().getColor(R.color.colorColumnOrange));
        mPaintExpense.setStrokeWidth(getHeightScreen() / 20);
    }
    private void initPaintText () {
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(getResources().getColor(R.color.colorTextBlack));
        mPaintText.setTextSize(30);
    }
    private void initPaintTextMoney () {
        mPaintTextMoney = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextMoney.setColor(getResources().getColor(R.color.colorTextBlack));
        mPaintTextMoney.setTextAlign(Paint.Align.RIGHT);
        mPaintTextMoney.setTextSize(30);
    }
    private void initPaintLine () {
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setColor(getResources().getColor(R.color.colorLineGray));
    }
    private void initPaintTextContent () {
        mPaintTextContent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextContent.setColor(getResources().getColor(R.color.colorTextBlack));
        mPaintTextContent.setTextSize(50);
    }
    private void initPaintNoteSale () {
        mPaintNoteSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteSale.setColor(getResources().getColor(R.color.colorColumnBlue));
        mPaintNoteSale.setStrokeWidth(getHeightScreen() / 40);
    }
    private void initPaintNoteExpense () {
        mPaintNoteExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteExpense.setColor(getResources().getColor(R.color.colorColumnOrange));
        mPaintNoteExpense.setStrokeWidth(getHeightScreen() / 40);
    }
}
