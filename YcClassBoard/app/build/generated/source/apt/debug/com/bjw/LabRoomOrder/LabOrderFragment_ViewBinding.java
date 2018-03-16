// Generated code from Butter Knife. Do not modify!
package com.bjw.LabRoomOrder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bjw.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LabOrderFragment_ViewBinding implements Unbinder {
  private LabOrderFragment target;

  @UiThread
  public LabOrderFragment_ViewBinding(LabOrderFragment target, View source) {
    this.target = target;

    target.btTest = Utils.findRequiredViewAsType(source, R.id.bt_test, "field 'btTest'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LabOrderFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btTest = null;
  }
}
