package com.lfq.diary.home.content.phone;

import android.content.Context;
import android.util.AttributeSet;

import com.lfq.diary.R;
import com.lfq.diary.util.ColorTools;

/**
 * 联系人头像，这是固定的图片，没法改，也不想改
 */
public class ContactHeadImage extends android.support.v7.widget.AppCompatImageView {
    public ContactHeadImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setImageResource(R.mipmap.ic_personal);
        super.setColorFilter(ColorTools.getInstance().getProspectColor());
    }
}
