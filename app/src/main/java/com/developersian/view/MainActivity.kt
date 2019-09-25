package com.developersian.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.developersian.R

class MainActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		throw Exception("ERRor")
	}
}


//package com.example.protech.view.user
//
//import android.annotation.SuppressLint
//import android.graphics.*
//import android.graphics.drawable.*
//import android.os.*
//import android.view.*
//import android.widget.*
//import androidx.recyclerview.widget.*
//import com.example.protech.R
//import com.example.protech.base.*
//import com.example.protech.model.Address
//import com.example.protech.view.order.CheckoutFragment
//import com.example.protech.webService.*
//import com.satya.utilites.extentions.*
//import kotlinx.android.synthetic.main.fragment_address.*
//import java.io.Serializable
//
//@SuppressLint("SetTextI18n") class AddressFragment : BaseFragment() {
//	/**
//	 * The status of the [AddressFragment] that defines that, whether user is here to choose an address for their order,
//	 * or looking at the address to edit them, or add a new one.
//	 */
//	private var status = AddressFragmentStatus.SEE_ADDRESSES
//	var orderId = 0
//
//	companion object {
//		fun newInstance(addressPage: Serializable, orderId: Int = 0): AddressFragment {
//			val fragment = AddressFragment()
//			val bundle = Bundle()
//			bundle.putSerializable("status", addressPage)
//			bundle.putInt("orderId", orderId)
//			fragment.arguments = bundle
//			return fragment
//		}
//	}
//
//	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_address, container, false)
//	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//		super.onViewCreated(view, savedInstanceState)
//
//		orderId = argumentInt("orderId")!!
//		status = arguments!!.getSerializable("status") as AddressFragmentStatus
//		when (status) {
//			AddressFragmentStatus.SEE_ADDRESSES -> {
//			}
//			AddressFragmentStatus.CHOOSE_ADDRESS -> {
//			}
//		}
//
//		buttonAddressFragment.setOnClickListener { transactionAddToBackStack(FRAME_LAYOUT_MAIN_ACTIVITY, CreateAddressFragment.newInstance(CreateAddressFragmentStatus.CREATE_ADDRESS)) }
//
//		addressR {
//			val adapter = AddressAdapter(it.address)
//			ItemTouchHelper(SwipeToDeleteCallback(adapter)).attachToRecyclerView(recyclerViewAddressFragment)
//			recyclerViewAddressFragment.adapter = adapter
//		}
//	}
//
//	private inner class SwipeToDeleteCallback(private val adapter: AddressAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//		private var icon: Drawable = drawable(R.drawable.ic_android_black_24dp)
//		private var background: ColorDrawable = ColorDrawable(Color.RED)
//		override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true
//		override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = adapter.deleteItem(viewHolder.adapterPosition)
//		override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//			super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//			val itemView = viewHolder.itemView
//			val backgroundCornerOffset = 20
//			when {
//				dX < 0 -> background.setBounds(itemView.right + dX.toInt() - backgroundCornerOffset, itemView.top, itemView.right, itemView.bottom)
//				else -> background.setBounds(0, 0, 0, 0)
//			}
//			background.draw(c)
//			val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
//			val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
//			val iconBottom = iconTop + icon.intrinsicHeight
//			when {
//				dX < 0 -> {
//					val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
//					val iconRight = itemView.right - iconMargin
//					icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//					background.setBounds(itemView.right + dX.toInt() - backgroundCornerOffset, itemView.top, itemView.right, itemView.bottom)
//				}
//				else -> background.setBounds(0, 0, 0, 0)
//			}
//			background.draw(c)
//			icon.draw(c)
//		}
//	}
//
//	private inner class AddressAdapter(private val list: ArrayList<Address.Addres>) : RecyclerView.Adapter<AddressAdapter.Holder>() {
//		inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(LayoutInflater.from(parent.context).inflate(R.layout.row_adress, parent, false))
//		override fun getItemCount(): Int = list.size
//		override fun onBindViewHolder(h: Holder, p: Int) {
//			val i = list[p]
//
//			h.itemView.findViewById<TextView>(R.id.textViewTitleRowAddress).text = i.title
//			h.itemView.findViewById<TextView>(R.id.textViewDescriptionRowAddress).text = i.addressDetail
//			h.itemView.findViewById<TextView>(R.id.textViewCityRowAddress).text = "${i.provinceName} - ${i.cityName}"
//
//			/**
//			 * If the status of the [AddressFragment] is [AddressFragmentStatus.SEE_ADDRESSES], after clicking in any
//			 * address, user directs to the [CreateAddressFragment] to edit the address.
//			 *
//			 * If the status of the [AddressFragment] is [AddressFragmentStatus.CHOOSE_ADDRESS], it means that user is
//			 * choosing the Address for the order. so after clicking on any address, user will directs to payment page.
//			 */
//			h.itemView.setOnClickListener {
//				when (status) {
//					AddressFragmentStatus.SEE_ADDRESSES -> {
//						transactionAddToBackStack(FRAME_LAYOUT_MAIN_ACTIVITY, CreateAddressFragment.newInstance(CreateAddressFragmentStatus.UPDATE_ADDRESS, i.addressId))
//					}
//					AddressFragmentStatus.CHOOSE_ADDRESS -> {
//						updateOrderR(orderId, jsonObject(Pair("AddressId", i.addressId))) {
//							transactionAddToBackStack(FRAME_LAYOUT_MAIN_ACTIVITY, CheckoutFragment.newInstance(orderId))
//						}
//					}
//				}
//			}
//		}
//
//		fun deleteItem(position: Int) {
//			list.removeAt(position)
//			notifyItemRemoved(position)
//			val handler = Handler()
//			handler.postDelayed({ deleteAddressR(list[position].addressId) }, 3000)
//			//TODO: Will Crash if user has only one address.
//			activity?.dialogStandard(title = "میخواهید آدرس مورد نظر را حذف کنید؟", positiveButtonText = "بله", negativeButtonText = "خیر", onPositiveClick = View.OnClickListener {
//
//			})
//			activity?.findViewById<FrameLayout>(R.id.frameLayoutAddressFragment)?.snackBarAction("آدرس حذف شد.", "Undo", View.OnClickListener {
//				if (position != list.size) list.add(position, list[position])
//				else list.add(list[0])
//				notifyItemInserted(position)
//				handler.removeCallbacksAndMessages(null)
//			})
//		}
//	}
//}