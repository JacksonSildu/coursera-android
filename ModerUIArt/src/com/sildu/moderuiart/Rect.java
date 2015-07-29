package com.sildu.moderuiart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Rect extends View {

	Paint	paint	= new Paint();
	float[]	bounds;
	int		color;

	public Rect(Context context, float[] bounds, int color) {
		super(context);
		this.color = color;
		this.bounds = bounds;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		paint.setColor(color);
		canvas.drawRect(bounds[0], bounds[1], bounds[2], bounds[3], paint);

	}

}
