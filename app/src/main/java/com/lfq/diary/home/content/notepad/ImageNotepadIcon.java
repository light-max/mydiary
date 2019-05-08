package com.lfq.diary.home.content.notepad;

import android.content.Context;
import android.util.AttributeSet;

import com.lfq.diary.R;
import com.lfq.diary.util.ColorTools;

/**
 * 一个静态icon，不过它的颜色会跟随主题
 */
public class ImageNotepadIcon extends android.support.v7.widget.AppCompatImageView {
    public ImageNotepadIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        ColorTools color = ColorTools.getInstance();
        super.setImageResource(R.drawable.ic_book);
        super.setColorFilter(color.getProspectColor());
    }
}
