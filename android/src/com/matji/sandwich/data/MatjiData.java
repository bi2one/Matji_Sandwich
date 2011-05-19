package com.matji.sandwich.data;

import android.os.Parcelable;

public abstract class MatjiData implements Parcelable {
	/* Intent로 객체를 전달하기 위해서 Parcelable 인터페이스를 구현 ..
	 * 각 MatjiData마다 protected Parcelable.Creator<T> CREATOR를
	 * 정의해야만 익셉션이 발생하지 않음
	 */
}
