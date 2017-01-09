package mahmoud.footballcalendar.Classes;

/**
 * Created by Mahmoud on 0003 1/3/2016.
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class LockedHorizontalScrollView extends HorizontalScrollView {

    public LockedHorizontalScrollView(Context context) {
        super(context);
    }

    public LockedHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockedHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
        return false;
    }

}