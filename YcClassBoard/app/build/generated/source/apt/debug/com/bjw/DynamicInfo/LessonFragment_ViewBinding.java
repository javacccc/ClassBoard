// Generated code from Butter Knife. Do not modify!
package com.bjw.DynamicInfo;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bjw.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LessonFragment_ViewBinding implements Unbinder {
  private LessonFragment target;

  @UiThread
  public LessonFragment_ViewBinding(LessonFragment target, View source) {
    this.target = target;

    target.courseName = Utils.findRequiredViewAsType(source, R.id.course_name, "field 'courseName'", TextView.class);
    target.courseNum = Utils.findRequiredViewAsType(source, R.id.course_num, "field 'courseNum'", TextView.class);
    target.coursePeriod = Utils.findRequiredViewAsType(source, R.id.course_period, "field 'coursePeriod'", TextView.class);
    target.courseTeacher = Utils.findRequiredViewAsType(source, R.id.course_teacher, "field 'courseTeacher'", TextView.class);
    target.chooseNum = Utils.findRequiredViewAsType(source, R.id.choose_num, "field 'chooseNum'", TextView.class);
    target.presentNum = Utils.findRequiredViewAsType(source, R.id.present_num, "field 'presentNum'", TextView.class);
    target.idLlPresent = Utils.findRequiredViewAsType(source, R.id.id_ll_present, "field 'idLlPresent'", LinearLayout.class);
    target.obsentNum = Utils.findRequiredViewAsType(source, R.id.obsent_num, "field 'obsentNum'", TextView.class);
    target.idCardNumber = Utils.findRequiredViewAsType(source, R.id.id_card_number, "field 'idCardNumber'", TextView.class);
    target.idStudentName = Utils.findRequiredViewAsType(source, R.id.id_student_name, "field 'idStudentName'", TextView.class);
    target.idLlCardandname = Utils.findRequiredViewAsType(source, R.id.id_ll_cardandname, "field 'idLlCardandname'", LinearLayout.class);
    target.idAfterClassTv = Utils.findRequiredViewAsType(source, R.id.id_afterClass_Tv, "field 'idAfterClassTv'", TextView.class);
    target.idLlAfterschool = Utils.findRequiredViewAsType(source, R.id.id_ll_afterschool, "field 'idLlAfterschool'", LinearLayout.class);
    target.listView = Utils.findRequiredViewAsType(source, R.id.listviewforlesson, "field 'listView'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LessonFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.courseName = null;
    target.courseNum = null;
    target.coursePeriod = null;
    target.courseTeacher = null;
    target.chooseNum = null;
    target.presentNum = null;
    target.idLlPresent = null;
    target.obsentNum = null;
    target.idCardNumber = null;
    target.idStudentName = null;
    target.idLlCardandname = null;
    target.idAfterClassTv = null;
    target.idLlAfterschool = null;
    target.listView = null;
  }
}
