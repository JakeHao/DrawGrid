package com.jake.drawgrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;

/**
 * @author yinhao
 * @date 2019/4/10
 */
public class MyGridView extends View {

  public static final int DEFAULT_COLUMN = 2;
  private int mColumn = DEFAULT_COLUMN;
  private int columnSpace;
  private int rowSpace;
  private int itemCount;
  private Paint mPaint;
  private Rect drawRect;
  private int itemWidth;
  private Random random = new Random();

  public MyGridView(Context context) {
    super(context);
    init(context, null);
  }

  public MyGridView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    if (attrs != null) {
      TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyGridView);
      mColumn = typedArray.getInt(R.styleable.MyGridView_column, DEFAULT_COLUMN);
      columnSpace = (int) typedArray.getDimension(R.styleable.MyGridView_column_space, 0F);
      rowSpace = (int) typedArray.getDimension(R.styleable.MyGridView_row_space, 0F);
      itemCount = typedArray.getInt(R.styleable.MyGridView_item_count, 0);
    }
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStyle(Style.FILL);
    drawRect = new Rect();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    itemWidth =
        (w - ((mColumn - 1) * columnSpace) - getPaddingLeft() - getPaddingRight()) / mColumn;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    for (int i = 0; i < itemCount; i++) {
      drawRect.setEmpty();
      //获取列
      int column = i % mColumn;
      //获取行
      int row = i / mColumn;
      if (column == 0) {
        //第一列
        drawRect.left = getPaddingLeft();
        drawRect.right = drawRect.left + itemWidth;
      } else {
        drawRect.left = getPaddingLeft() + columnSpace * column + itemWidth * column;
        drawRect.right = drawRect.left + itemWidth;
      }
      if (row == 0) {
        //第一行
        drawRect.top = getPaddingTop();
        drawRect.bottom = drawRect.top + itemWidth;
      } else {
        drawRect.top = getPaddingTop() + rowSpace * row + itemWidth * row;
        drawRect.bottom = drawRect.top + itemWidth;
      }
      mPaint.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
      canvas.drawRect(drawRect, mPaint);
    }
  }
}
