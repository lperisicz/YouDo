package com.perisic.luka.base.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T, D : ViewDataBinding>(
    val binding: D,
    internal val binder: D.(data: T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(data: T) = binding.binder(data)

}