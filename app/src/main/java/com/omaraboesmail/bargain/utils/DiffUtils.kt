package com.omaraboesmail.bargain.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil


class DiffUtils(
    newList: List<Any>,
    oldList: List<Any>
) :
    DiffUtil.Callback() {
    var oldList: List<Any>
    var newList: List<Any>
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        this.newList = newList
        this.oldList = oldList
    }
}