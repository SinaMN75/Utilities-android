package com.satya.utilites.shimmerRecyclerView;

import android.content.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import androidx.annotation.*;
import androidx.recyclerview.widget.*;
import com.satya.utilites.*;

public class ShimmerRecyclerView extends RecyclerView {
	private Adapter mActualAdapter;
	private ShimmerAdapter mShimmerAdapter;
	private RecyclerView.LayoutManager mShimmerLayoutManager;
	private LayoutManager mActualLayoutManager;
	private LayoutMangerType mLayoutMangerType;
	private boolean mCanScroll;
	private int mLayoutReference;
	private int mGridCount;
	
	public ShimmerRecyclerView(Context context) {
		super(context);
		init(context, null);
	}
	
	public ShimmerRecyclerView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	public ShimmerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		mShimmerAdapter = new ShimmerAdapter();
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShimmerRecyclerView, 0, 0);
		int mShimmerAngle;
		int mShimmerColor;
		int mShimmerDuration;
		float mShimmerMaskWidth;
		boolean isAnimationReversed;
		Drawable mShimmerItemBackground;
		try {
			setDemoLayoutReference(a.getResourceId(R.styleable.ShimmerRecyclerView_shimmer_demo_layout, R.layout.layout_sample_view));
			setDemoChildCount(a.getInteger(R.styleable.ShimmerRecyclerView_shimmer_demo_child_count, 10));
			setGridChildCount(a.getInteger(R.styleable.ShimmerRecyclerView_shimmer_demo_grid_child_count, 2));
			final int value = a.getInteger(R.styleable.ShimmerRecyclerView_shimmer_demo_layout_manager_type, 0);
			switch (value) {
				case 0:
					setDemoLayoutManager(LayoutMangerType.LINEAR_VERTICAL);
					break;
				case 1:
					setDemoLayoutManager(LayoutMangerType.LINEAR_HORIZONTAL);
					break;
				case 2:
					setDemoLayoutManager(LayoutMangerType.GRID);
					break;
				default:
					throw new IllegalArgumentException("This value for layout manager is not valid!");
			}
			mShimmerAngle = a.getInteger(R.styleable.ShimmerRecyclerView_shimmer_demo_angle, 0);
			mShimmerColor = a.getColor(R.styleable.ShimmerRecyclerView_shimmer_demo_shimmer_color, getColor(R.color.md_blue_grey_100));
			mShimmerItemBackground = a.getDrawable(R.styleable.ShimmerRecyclerView_shimmer_demo_view_holder_item_background);
			mShimmerDuration = a.getInteger(R.styleable.ShimmerRecyclerView_shimmer_demo_duration, 1500);
			mShimmerMaskWidth = a.getFloat(R.styleable.ShimmerRecyclerView_shimmer_demo_mask_width, 0.5f);
			isAnimationReversed = a.getBoolean(R.styleable.ShimmerRecyclerView_shimmer_demo_reverse_animation, false);
		} finally {
			a.recycle();
		}
		mShimmerAdapter.setShimmerAngle(mShimmerAngle);
		mShimmerAdapter.setShimmerColor(mShimmerColor);
		mShimmerAdapter.setShimmerMaskWidth(mShimmerMaskWidth);
		mShimmerAdapter.setShimmerItemBackground(mShimmerItemBackground);
		mShimmerAdapter.setShimmerDuration(mShimmerDuration);
		mShimmerAdapter.setAnimationReversed(isAnimationReversed);
		showShimmerAdapter();
	}
	
	public void setGridChildCount(int count) {
		mGridCount = count;
	}
	
	public void setDemoLayoutManager(LayoutMangerType type) {
		mLayoutMangerType = type;
	}
	
	public void setDemoChildCount(int count) {
		mShimmerAdapter.setMinItemCount(count);
	}
	
	public void setDemoShimmerDuration(int duration) {
		mShimmerAdapter.setShimmerDuration(duration);
	}
	
	public void setDemoShimmerMaskWidth(float maskWidth) {
		mShimmerAdapter.setShimmerMaskWidth(maskWidth);
	}
	
	public void showShimmerAdapter() {
		mCanScroll = false;
		if (mShimmerLayoutManager == null) initShimmerManager();
		setLayoutManager(mShimmerLayoutManager);
		setAdapter(mShimmerAdapter);
	}
	
	public void hideShimmerAdapter() {
		mCanScroll = true;
		setLayoutManager(mActualLayoutManager);
		setAdapter(mActualAdapter);
	}
	
	public void setLayoutManager(LayoutManager manager) {
		if (manager == null) {
			mActualLayoutManager = null;
		} else if (manager != mShimmerLayoutManager) {
			mActualLayoutManager = manager;
		}
		super.setLayoutManager(manager);
	}
	
	public void setAdapter(Adapter adapter) {
		if (adapter == null) {
			mActualAdapter = null;
		} else if (adapter != mShimmerAdapter) {
			mActualAdapter = adapter;
		}
		super.setAdapter(adapter);
	}
	
	public Adapter getActualAdapter() {
		return mActualAdapter;
	}
	
	public Adapter getShimmerAdapter() {
		return mShimmerAdapter;
	}
	
	public int getLayoutReference() {
		return mLayoutReference;
	}
	
	public void setDemoLayoutReference(int mLayoutReference) {
		this.mLayoutReference = mLayoutReference;
		mShimmerAdapter.setLayoutReference(getLayoutReference());
	}
	
	private void initShimmerManager() {
		switch (mLayoutMangerType) {
			case LINEAR_VERTICAL:
				mShimmerLayoutManager = new LinearLayoutManager(getContext()) {
					public boolean canScrollVertically() {
						return mCanScroll;
					}
				};
				break;
			case LINEAR_HORIZONTAL:
				mShimmerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
					public boolean canScrollHorizontally() {
						return mCanScroll;
					}
				};
				break;
			case GRID:
				mShimmerLayoutManager = new GridLayoutManager(getContext(), mGridCount) {
					public boolean canScrollVertically() {
						return mCanScroll;
					}
				};
				break;
		}
	}
	
	private int getColor(int id) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return getContext().getColor(id);
		} else {
			return getResources().getColor(id);
		}
	}
	
	public enum LayoutMangerType {
		LINEAR_VERTICAL, LINEAR_HORIZONTAL, GRID
	}
}