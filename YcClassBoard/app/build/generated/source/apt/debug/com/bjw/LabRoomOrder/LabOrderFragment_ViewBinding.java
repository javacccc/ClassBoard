// Generated code from Butter Knife. Do not modify!
package com.bjw.LabRoomOrder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.etOrdercontent = Utils.findRequiredViewAsType(source, R.id.et_ordercontent, "field 'etOrdercontent'", EditText.class);
    target.btSubmit = Utils.findRequiredViewAsType(source, R.id.bt_submit, "field 'btSubmit'", Button.class);
    target.spStartclass = Utils.findRequiredViewAsType(source, R.id.sp_startclass, "field 'spStartclass'", Spinner.class);
    target.spEndclass = Utils.findRequiredViewAsType(source, R.id.sp_endclass, "field 'spEndclass'", Spinner.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LabOrderFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTime = null;
    target.etOrdercontent = null;
    target.btSubmit = null;
    target.spStartclass = null;
    target.spEndclass = null;
  }
}
