package com.ldedusoft.ldbm.util;

public interface Callback {
	void onBefore();

	boolean onRun();

	void onAfter(boolean b);
}
