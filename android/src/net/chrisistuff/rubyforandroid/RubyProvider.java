package net.chrisistuff.rubyforandroid;

import com.googlecode.android_scripting.interpreter.InterpreterDescriptor;
import com.googlecode.android_scripting.interpreter.InterpreterProvider;

public class RubyProvider extends InterpreterProvider {
  @Override
  protected InterpreterDescriptor getDescriptor() {
    return new RubyDescriptor();
  }
}
