package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemListBinding
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.utils.Constants

class SimpleListAdapter(private val selectedListener: ItemSelectedListener? = null) : RecyclerView.Adapter<SimpleListAdapter.SimpleListViewHolder>() {

    private var mLists: List<JHList>? = null

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SimpleListViewHolder {
        val binding =
            DataBindingUtil.inflate<ListItemListBinding>(LayoutInflater.from(parent.context),
                                                         R.layout.list_item_list,
                                                         parent,
                                                         false)
        return SimpleListViewHolder(binding)
    }

    override fun getItemCount() = mLists?.size ?: 0

    override fun onBindViewHolder(holder: SimpleListViewHolder,
                                  position: Int) {
        val list = mLists?.get(position)
        holder.apply {
            if (list != null) {
                val args = Bundle()
                args.putLong(Constants.ARGUMENT_LIST_ID,
                             list.listId)
                args.putString(Constants.ARGUMENT_APP_TITLE,
                               list.listName)

                if (selectedListener == null) {
                    bind(Navigation.createNavigateOnClickListener(R.id.taskListFragment,
                                                                  args),
                         list)
                } else {
                    // you can't pass in an ItemSelectedListener without rewriting a bunch of code,
                    // so wrap it in an OnClickListener
                    bind(View.OnClickListener { selectedListener.onItemSelected(list) },
                         list)
                }
                itemView.tag = list
            }
        }
    }

    fun setListsList(lists: List<JHList>) {
        if (mLists == null) {
            mLists = lists
            notifyItemRangeInserted(0,
                                    lists.size)
        } else {
            val result = DiffUtil.calculateDiff(ListDiffCallback(lists))
            mLists = lists
            result.dispatchUpdatesTo(this)
        }
    }

    inner class ListDiffCallback(private val lists: List<JHList>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int,
                                     newItemPosition: Int) = mLists?.get(oldItemPosition) == lists[newItemPosition]

        override fun getOldListSize() = mLists?.size ?: 0

        override fun getNewListSize() = lists.size
        override fun areContentsTheSame(oldItemPosition: Int,
                                        newItemPosition: Int): Boolean {
            val newList = lists[newItemPosition]
            val oldList = mLists?.get(oldItemPosition)

            return newList.listId == oldList?.listId && newList.listName == oldList.listName
        }
    }

    class SimpleListViewHolder(private val binding: ListItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener,
                 item: JHList) {
            binding.apply {
                clickListener = listener
                list = item
                executePendingBindings()
            }
        }
    }
}