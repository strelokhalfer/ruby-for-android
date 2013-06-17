package net.chrisistuff.rubyforandroid;

import java.io.File;

import android.content.Context;

import com.googlecode.android_scripting.AsyncTaskListener;
import com.googlecode.android_scripting.InterpreterInstaller;
import com.googlecode.android_scripting.exception.Sl4aException;
import com.googlecode.android_scripting.interpreter.InterpreterDescriptor;
import com.googlecode.android_scripting.interpreter.InterpreterConstants;
import com.googlecode.android_scripting.Log;

public class RubyInstaller extends InterpreterInstaller {

  public RubyInstaller(InterpreterDescriptor descriptor, Context context,
      AsyncTaskListener<Boolean> listener) throws Sl4aException {
    super(descriptor, context, listener);
  }

  @Override
  protected boolean setup() {
    File tmp =
        new File(InterpreterConstants.SDCARD_ROOT + getClass().getPackage().getName()
            + InterpreterConstants.INTERPRETER_EXTRAS_ROOT, mDescriptor.getName() + "/tmp");
    if (!tmp.isDirectory()) {
      try {
        tmp.mkdir();
      } catch (SecurityException e) {
        Log.e(mContext, "Setup failed.", e);
        return false;
      }
    }
    return true;
  }

}
