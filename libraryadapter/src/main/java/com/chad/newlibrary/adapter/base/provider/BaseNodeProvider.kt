package com.chad.newlibrary.adapter.base.provider

import com.chad.newlibrary.adapter.base.BaseNodeAdapter
import com.chad.newlibrary.adapter.base.entity.node.BaseNode

abstract class BaseNodeProvider : BaseItemProvider<BaseNode>() {

    override fun getAdapter(): BaseNodeAdapter? {
        return super.getAdapter() as? BaseNodeAdapter
    }

}