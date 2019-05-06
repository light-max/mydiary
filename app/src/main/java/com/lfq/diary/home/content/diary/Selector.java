package com.lfq.diary.home.content.diary;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.lfq.diary.R;
import com.lfq.diary.base.BaseAdapter;
import com.lfq.diary.callback.NumberCallback;
import com.lfq.diary.util.ColorTools;

/**
 * 这是一个选择器，点击后会弹出一组图片用于选择，用于在编辑日记的时候选择“心情”“天气”
 */
public abstract class Selector extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String TAG = "Select";
    public Selector(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnClickListener(this);
        resIdArray = createResIdArray();
        colorTools = ColorTools.getInstance();
        super.setColorFilter(colorTools.getProspectColor());
    }

    private ColorTools colorTools;
    private int position;

    public void setPosition(int position) {
        this.position = position;
        super.setImageResource(resIdArray[position]);
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void onClick(View view) {
        if (popupWindow==null){
            initPopWindow();
        }
        popupWindow.showAsDropDown(view,-25,0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        popupWindow.dismiss();
        setPosition(i);
     //   callback.onCallback(i);
    }

    protected abstract int[] createResIdArray();

    /**
     * 图片列表展示所需
     */
    private ViewGroup.LayoutParams itemParams;
    private PopupWindow popupWindow;
    private View popView;
    private ListView listView;
    private BaseAdapter<Integer> adapter = new com.lfq.diary.base.BaseAdapter<Integer>(getContext()){
        @Override
        protected int attrView() {
            return R.layout.imageview;
        }

        @Override
        protected void onGetView(View v, int position, Integer obj) {
            ImageView imageView = VH.get(v,R.id.image);
            imageView.setLayoutParams(itemParams);
            imageView.setPadding(getPaddingLeft(),getPaddingTop(),getPaddingRight(),getPaddingBottom());
            imageView.setImageResource(obj);
            imageView.setColorFilter(colorTools.getProspectColor());
        }
    };
    private void initPopWindow(){
        itemParams = getLayoutParams();
        for (int resId:resIdArray){
            adapter.addData(resId);
        }
        popView = LayoutInflater.from(getContext()).inflate(R.layout.popwndlist,null);
        listView = popView.findViewById(R.id.popwndlist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
    }

    /**
     * 点击的图片列表的回调
     */
//    private NumberCallback callback;
//
//    public void setCallback(NumberCallback callback) {
//        this.callback = callback;
//    }

    /**
     * 保存图片resId的数组
     */
    private int[] resIdArray = null;

    public int[] getResIdArray() {
        return resIdArray;
    }
}
