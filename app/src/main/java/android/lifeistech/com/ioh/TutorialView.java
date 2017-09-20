package android.lifeistech.com.ioh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by fumiyauchiyama on 2017/06/24.
 */

public class TutorialView extends FrameLayout {

    public TutorialView(Context context) {
        this(context, null);
    }

    public TutorialView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);

    }

    public TutorialView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        inflate(context, R.layout.view_tutorial, this);

    }
}
