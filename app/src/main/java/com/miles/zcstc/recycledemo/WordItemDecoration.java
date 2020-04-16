package com.miles.zcstc.recycledemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordItemDecoration extends RecyclerView.ItemDecoration {
    private ArrayList<Word> mWords;//设置数据
    private Paint mPaint;//设置画悬浮栏的画笔
    private Rect mRectBounds;//设置一个矩形，用于画文字

    private int mTitleHeight;//设置悬浮栏的高度
    private int mTextSize;//设置文字大小
    private Context mContext;//设置上下文对象

    public WordItemDecoration(Context context,ArrayList<Word> words) {
        mWords = words;
        mContext = context;

        //设置悬浮栏高度以及文字大小,为了统一尺寸规格，转换为像素
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, mContext.getResources().getDisplayMetrics());
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, mContext.getResources().getDisplayMetrics());

        mRectBounds = new Rect();//初始化矩形
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setTextSize(mTextSize);
    }

    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn before the item views are drawn,
     * and will thus appear underneath the views.
     *
     * 提供给RecyclerView的画布中绘制任何适当的装饰。
     * 使用此方法绘制的任何内容都将在绘制项目视图之前绘制，因此将显示在视图下面。
     *
     * @param c      Canvas to draw into    画布
     * @param parent RecyclerView this ItemDecoration is drawing into   正在使用装饰的recycle view
     * @param state  The current state of RecyclerView      即RecyclerView的当前状态
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        /**
         * 这个方法负责绘制每一个标题，可以实现随着视图移动而移动
         * */

        super.onDraw(c, parent, state);

        //先画出带有背景颜色的矩形条悬浮栏，从哪个位置开始绘制到哪个位置结束，则需要先确定位置，再画文字（即：title）
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        //父view（RecyclerView）有padding值，子view有margin值
        int childCount = parent.getChildCount();//得到的数据其实是一屏可见的item数量并非总item数，再复用
        for(int i = 0; i < childCount; i++){
            View child = parent.getChildAt(i);
            //子view（即：item）有可能设置有margin值，所以需要parms来设置margin值
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            //以及 获取 position 位置
            int position = params.getViewLayoutPosition();
            if(position > -1){
                if(position == 0){//肯定是要绘制一个悬浮栏 以及 悬浮栏内的文字
                    //画矩形悬浮条以及文字
                    drawRectAndText(c, left, right, child, params, position);
                }else{
                    if(mWords.get(position).getInitial() != null && !mWords.get(position).getInitial().equals(mWords.get(position - 1).getInitial())){
                        //和上一个Tag不一样，说明是另一个新的分组
                        //画矩形悬浮条以及文字
                        drawRectAndText(c, left, right, child, params, position);
                    }else{
                        //说明是一组的，什么也不画，共用同一个首字母
                    }
                }
            }

        }

    }

    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn after the item views are drawn
     * and will thus appear over the views.
     *
     * 在提供给RecyclerView的画布中绘制任何适当的装饰。
     * 使用此方法绘制的任何内容都将在绘制项目视图之后绘制，因此将显示在视图上。
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView.
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        /**
         * 这个方法可以显示在视图上面，所以可以实现悬浮标题的效果
         * */
        super.onDrawOver(c, parent, state);


        //其实就是获取到每一个可见的位置的item时，执行画顶层悬浮栏
        int firstPosition = ((LinearLayoutManager)parent.getLayoutManager()).findFirstVisibleItemPosition();
        View child = parent.findViewHolderForLayoutPosition(firstPosition).itemView;
        //绘制悬浮栏，其实这里和上面onDraw()绘制方法差不多，只不过，这里面的绘制是在最上层，会悬浮
        mPaint.setColor(Color.parseColor("#C5E4FD"));
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        //绘制文字
        mPaint.setColor(Color.parseColor("#555555"));
        mPaint.getTextBounds(mWords.get(firstPosition).getInitial(), 0, mWords.get(firstPosition).getInitial().length(), mRectBounds);
        c.drawText(mWords.get(firstPosition).getInitial(), child.getPaddingLeft()+40, parent.getPaddingTop() + mTitleHeight - (mTitleHeight/2 - mRectBounds.height()/2), mPaint);

    }

    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     *
     * 检索给定项的任何偏移量。outRect<code>outRect</code>的每个字段指定项目视图应插入的像素数，
     * 类似于填充或边距。默认实现将outRect的边界设置为0并返回
     *
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     * 如果此ItemDecoration不影响项视图的位置，则在返回之前，
     * 它应将<code>outRect</code>的所有四个字段（左、上、右、下）设置为零。
     *
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     * 如果需要访问适配器以获取其他数据，
     * 可以调用{@link RecyclerView#getChildAdapterPosition（View）}获取查看。
     *
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        /**
         * 这个方法设置预留空间
         * */
        super.getItemOffsets(outRect, view, parent, state);

        //获取position,由本方法的第三段注释可得
        int position = parent.getChildAdapterPosition(view);
        if(position > -1){//界面中的所有子view
            if(position == 0){//第一个位置，设置悬浮栏
                //在top留出一段距离
                outRect.set(0, mTitleHeight, 0, 0);//里面参数表示：左上右下的内边距padding距离
            }else{
                //当滑动到某一个item时(position位置)得到首字母，与上一个item对应的首字母不一致( position-1 位置)，说明这是下一分组了
                if(mWords.get(position).getInitial() != null && !mWords.get(position).getInitial().equals(mWords.get(position-1).getInitial())){
                    //在top留出一段距离
                    outRect.set(0, mTitleHeight, 0, 0);
                }else{
                    //首字母相同说明是同一组的数据，比如都是 A 组下面的数据，那么就不需要再留出空间绘制悬浮栏了，共用同一个 A 组即可
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

    /**
     * 绘制文字和图形
     * */
    private void drawRectAndText(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        //1、画矩形悬浮栏
        //item可以有margin值不设置就默认为0，其中child.getTop()表示item距离父recycler view的距离，params.topMargin表示item的外边距，悬浮栏在item上方，那么悬浮栏的bottom就是child.getTop() - params.topMargin
        mPaint.setColor(Color.parseColor("#C5E4FD"));
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        //2、画文字
        mPaint.setColor(Color.parseColor("#555555"));
        mPaint.getTextBounds(mWords.get(position).getInitial(), 0, mWords.get(position).getInitial().length(), mRectBounds);//将文字放到矩形中，得到Rect的宽高
        c.drawText(mWords.get(position).getInitial(), child.getPaddingLeft()+40, child.getTop() - params.topMargin - (mTitleHeight / 2 - mRectBounds.height() / 2), mPaint);
    }


}

