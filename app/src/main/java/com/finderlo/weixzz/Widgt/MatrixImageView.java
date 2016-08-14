package com.finderlo.weixzz.Widgt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Finderlo on 2016/8/10.
 */
public class MatrixImageView extends BaseImageView {
    private static final String TAG = "MatrixImageView";

    private GestureDetector mGestureDetector;
    /**
     * 模板matrix
     */
    private Matrix mMatrix = new Matrix();
    /**
     * 原图片的高和宽
     */
    private float mImageWidth;
    private float mImageHeight;

    public MatrixImageView(Context context) {
        super(context);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        MatrixToucherListener mListener = new MatrixToucherListener();
        setOnTouchListener(mListener);
        mGestureDetector = new GestureDetector(context, new GestureListener(mListener));

        setBackgroundColor(Color.BLACK);
        setScaleType(ScaleType.FIT_CENTER);


        mMatrix.set(getImageMatrix());//对变换矩阵的初始化
        float[] values = new float[9];
        mMatrix.getValues(values);
        //图片的宽度=屏幕宽度除以缩放倍数
        mImageWidth = getWidth() / values[Matrix.MSCALE_X];
        mImageHeight = (getHeight() - values[Matrix.MSKEW_Y] * 2) / Matrix.MSCALE_Y;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    public class MatrixToucherListener implements OnTouchListener {

        //拖拉照片模式
        private static final int MODE_DRAG = 1;
        /**放大缩小模式*/
        private static final int MODE_ZOOM = 2;
        private static final int MODE_UNABLE = 3;

        /**最大缩放级别*/
        float mMaxScale = 6;
        /**双击时的缩放级别*/
        float mDobleClickScale = 2;
        private int mMode = 0;
        /**缩放开始时的手指间距*/
        private float mStartDis;
        /*** 当前Matrix*/
        private Matrix mCurrentMatrix = new Matrix();

        private PointF startPoint = new PointF();

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mMode = MODE_DRAG;
                    startPoint.set(motionEvent.getX(), motionEvent.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    reSetMatrix();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mMode == MODE_ZOOM) {
                        setZoomMatrix(motionEvent);
                    } else if (mMode == MODE_DRAG) {
                        setDragMatrix(motionEvent);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (mMode == MODE_UNABLE) return true;
                    mMode = MODE_ZOOM;
                    mStartDis = distance(motionEvent);
                default:
                    break;

            }
            return mGestureDetector.onTouchEvent(motionEvent);
        }

        /**
         * 计算两个手指间的距离
         *
         * @param event
         * @return
         */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /** 使用勾股定理返回两点之间的距离 */
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 设置缩放matrix
         *
         * @param event
         **/
        private void setZoomMatrix(MotionEvent event) {
            /**只用触屏两个点的时候才会执行*/
            if (event.getPointerCount() < 2) return;
            float endDis = distance(event);
            if (endDis > 10f) {
                float scale = endDis / mStartDis; //缩放倍数
                mStartDis = endDis; //重置距离
                mCurrentMatrix.set(getImageMatrix());
                float[] value = new float[9];
                mCurrentMatrix.getValues(value);
                scale = checkMaxScale(scale, value);
                mCurrentMatrix.postScale(scale, scale,getWidth()/2,getHeight()/2);
                setImageMatrix(mCurrentMatrix);
            }
        }

        /**
         *  检验scale，使图像缩放后不会超出最大倍数
         *  @param scale
         *  @param values
         *  @return
         */
        private float checkMaxScale(float scale, float[] values) {
            if (scale * values[Matrix.MSCALE_X] > mMaxScale)
                scale = mMaxScale / values[Matrix.MSCALE_X];
//            mCurrentMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            return scale;
        }

        /**
         *   重置Matrix
         */
        private void reSetMatrix(){
            if (checkRest()){
                mCurrentMatrix.set(mMatrix);
                setImageMatrix(mCurrentMatrix);
            }
        }

        /**
         *  判断是否需要重置
         *  @return  当前缩放级别小于模板缩放级别时，重置
         */
        private boolean checkRest(){
            float[] values = new float[9];
            getImageMatrix().getValues(values);
            float scale = values[Matrix.MSCALE_X];
            mMatrix.getValues(values);
            return scale<values[Matrix.MSCALE_X];
        }

        /**
         *   双击时触发
         */
        public void onDoubleClick(){
            float scale=isZoomChanged()?1:mDobleClickScale;
            mCurrentMatrix.set(mMatrix);//初始化Matrix
            mCurrentMatrix.postScale(scale, scale,getWidth()/2,getHeight()/2);
            setImageMatrix(mCurrentMatrix);
        }
        /**
         *  判断缩放级别是否是改变过
         *  @return   true表示非初始值,false表示初始值
         */
        private boolean isZoomChanged() {
            float[] values=new float[9];
            getImageMatrix().getValues(values);
            //获取当前X轴缩放级别
            float scale=values[Matrix.MSCALE_X];
            //获取模板的X轴缩放级别，两者做比较
            mMatrix.getValues(values);
            return scale!=values[Matrix.MSCALE_X];
        }

        public void setDragMatrix(MotionEvent event) {
            if(isZoomChanged()){
                float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                //避免和双击冲突,大于10f才算是拖动
                if(Math.sqrt(dx*dx+dy*dy)>10f){
                    startPoint.set(event.getX(), event.getY());
                    //在当前基础上移动
                    mCurrentMatrix.set(getImageMatrix());
                    float[] values=new float[9];
                    mCurrentMatrix.getValues(values);
                    dx=checkDxBound(values,dx);
                    dy=checkDyBound(values,dy);
                    mCurrentMatrix.postTranslate(dx, dy);
                    setImageMatrix(mCurrentMatrix);
                }
            }
        }

        /**
         *和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
         *  @param values
         *  @param dx
         *  @return
         */
        private float checkDxBound(float[] values,float dx) {
            float width=getWidth();
            if(mImageWidth*values[Matrix.MSCALE_X]<width)
                return 0;
            if(values[Matrix.MTRANS_X]+dx>0)
                dx=-values[Matrix.MTRANS_X];
            else if(values[Matrix.MTRANS_X]+dx<-(mImageWidth*values[Matrix.MSCALE_X]-width))
                dx=-(mImageWidth*values[Matrix.MSCALE_X]-width)-values[Matrix.MTRANS_X];
            return dx;
        }

        /**
         *  和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
         *  @param values
         *  @param dy
         *  @return
         */
        private float checkDyBound(float[] values, float dy) {
            float height=getHeight();
            if(mImageHeight*values[Matrix.MSCALE_Y]<height)
                return 0;
            if(values[Matrix.MTRANS_Y]+dy>0)
                dy=-values[Matrix.MTRANS_Y];
            else if(values[Matrix.MTRANS_Y]+dy<-(mImageHeight*values[Matrix.MSCALE_Y]-height))
                dy=-(mImageHeight*values[Matrix.MSCALE_Y]-height)-values[Matrix.MTRANS_Y];
            return dy;
        }


    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private  final  MatrixToucherListener mListener;
        public GestureListener(MatrixToucherListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mListener.onDoubleClick();
            return true;
        }
    }

}
