package com.chad.newlibrary.adapter.base.diff;

import androidx.annotation.NonNull;

/**
 * 使用java接口定义方法
 * @param <T>
 */
public interface DifferImp<T> {
    void addListListener(@NonNull ListChangeListener<T> listChangeListener);
}
