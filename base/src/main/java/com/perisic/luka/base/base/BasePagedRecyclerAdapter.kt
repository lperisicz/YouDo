package com.perisic.luka.base.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

open class BasePagedRecyclerAdapter<T, D : ViewDataBinding>(
    private val inflater: ((inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> D)? = null,
    protected val binder: D.(data: T) -> Unit,
    diffCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, BaseViewHolder<T, D>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, D> {
        inflater?.let {
            return BaseViewHolder(
                binding = parent.inflate(inflater),
                binder = binder
            )
        } ?: let {
            throw RuntimeException("Inflater passed is null, but BaseAdapter.onCreateViewHolder not overridden")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, D>, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    private fun <T : ViewDataBinding> ViewGroup.inflate(binder: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T): T {
        val inflater = LayoutInflater.from(context)
        return binder(inflater, this, false)
    }

}