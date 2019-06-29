package com.satya.utilites.shimmerRecyclerView;

import android.graphics.drawable.*;
import android.view.*;
import androidx.recyclerview.widget.*;
import org.jetbrains.annotations.*;

public class ShimmerAdapter extends RecyclerView.Adapter<ShimmerViewHolder> {
	private int mItemCount;
	private int mLayoutReference;
	private int mShimmerAngle;
	private int mShimmerColor;
	private int mShimmerDuration;
	private float mShimmerMaskWidth;
	private boolean isAnimationReversed;
	private Drawable mShimmerItemBackground;
	
	@NotNull
	@Override
	public ShimmerViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		ShimmerViewHolder shimmerViewHolder = new ShimmerViewHolder(inflater, parent, mLayoutReference);
		shimmerViewHolder.setShimmerColor(mShimmerColor);
		shimmerViewHolder.setShimmerAngle(mShimmerAngle);
		shimmerViewHolder.setShimmerMaskWidth(mShimmerMaskWidth);
		shimmerViewHolder.setShimmerViewHolderBackground(mShimmerItemBackground);
		shimmerViewHolder.setShimmerAnimationDuration(mShimmerDuration);
		shimmerViewHolder.setAnimationReversed(isAnimationReversed);
		return shimmerViewHolder;
	}
	
	@Override
	public void onBindViewHolder(@NotNull ShimmerViewHolder holder, int position) {
		holder.bind();
	}
	
	@Override
	public int getItemCount() {
		return mItemCount;
	}
	
	public void setMinItemCount(int itemCount) {
		mItemCount = itemCount;
	}
	
	public void setShimmerAngle(int shimmerAngle) {
		this.mShimmerAngle = shimmerAngle;
	}
	
	public void setShimmerColor(int shimmerColor) {
		this.mShimmerColor = shimmerColor;
	}
	
	public void setShimmerMaskWidth(float maskWidth) {
		this.mShimmerMaskWidth = maskWidth;
	}
	
	public void setShimmerItemBackground(Drawable shimmerItemBackground) {
		this.mShimmerItemBackground = shimmerItemBackground;
	}
	
	public void setShimmerDuration(int mShimmerDuration) {
		this.mShimmerDuration = mShimmerDuration;
	}
	
	public void setLayoutReference(int layoutReference) {
		this.mLayoutReference = layoutReference;
	}
	
	public void setAnimationReversed(boolean animationReversed) {
		this.isAnimationReversed = animationReversed;
	}
}