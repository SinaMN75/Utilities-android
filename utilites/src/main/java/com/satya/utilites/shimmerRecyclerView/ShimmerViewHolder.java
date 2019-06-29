package com.satya.utilites.shimmerRecyclerView;

import android.graphics.drawable.*;
import android.view.*;
import androidx.recyclerview.widget.*;
import com.satya.utilites.R;
import io.supercharge.shimmerlayout.*;

public class ShimmerViewHolder extends RecyclerView.ViewHolder {
	private ShimmerLayout mShimmerLayout;
	
	public ShimmerViewHolder(LayoutInflater inflater, ViewGroup parent, int innerViewResId) {
		super(inflater.inflate(R.layout.viewholder_shimmer, parent, false));
		mShimmerLayout = (ShimmerLayout) itemView;
		inflater.inflate(innerViewResId, mShimmerLayout, true);
	}
	
	public void setShimmerAngle(int angle) {
		mShimmerLayout.setShimmerAngle(angle);
	}
	
	public void setShimmerColor(int color) {
		mShimmerLayout.setShimmerColor(color);
	}
	
	public void setShimmerMaskWidth(float maskWidth) {
		mShimmerLayout.setMaskWidth(maskWidth);
	}
	
	public void setShimmerViewHolderBackground(Drawable viewHolderBackground) {
		if (viewHolderBackground != null) {
			setBackground(viewHolderBackground);
		}
	}
	
	public void setShimmerAnimationDuration(int duration) {
		mShimmerLayout.setShimmerAnimationDuration(duration);
	}
	
	public void setAnimationReversed(boolean animationReversed) {
		mShimmerLayout.setAnimationReversed(animationReversed);
	}
	
	public void bind() {
		mShimmerLayout.startShimmerAnimation();
	}
	
	private void setBackground(Drawable background) {
		mShimmerLayout.setBackground(background);
	}
}
