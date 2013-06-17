package net.chrisistuff.rubyforandroid;

import android.content.Context;

import com.googlecode.android_scripting.interpreter.InterpreterDescriptor;
import com.googlecode.android_scripting.interpreter.InterpreterConstants;
import com.googlecode.android_scripting.interpreter.InterpreterUtils;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class RubyDescriptor implements InterpreterDescriptor {

  private static final String RUBY_INSTALL_URL = "http://files.chrisistuff.net/ruby/";
  private static final String RUBY_BIN = "bin/ruby";
  private static final String ENV_PATH = "RUBYLIB";
  private static final String ENV_TEMP = "TEMP";
  private static final String ENV_RUBY = "RUBY";

  @Override
  public String getName() {
    return "ruby";
  }

  @Override
  public String getNiceName() {
    return "Ruby 2.0.0";
  }

  @Override
  public String getExtension() {
    return ".rb";
  }

  @Override
  public File getBinary(Context context) {
    return new File(InterpreterUtils.getInterpreterRoot(context, getName()), "bin/ruby");
  }

  @Override
  public List<String> getArguments(Context context) {
    return new ArrayList<String>();
  }

  @Override
  public String getInteractiveCommand(Context context) {
    return (new File(InterpreterUtils.getInterpreterRoot(context, getName()), "bin/irb")).getAbsolutePath();
  }

  @Override
  public String getScriptCommand(Context context) {
    return "";
  }

  @Override
  public int getVersion() {
    return 3;
  }

  @Override
  public boolean hasInterpreterArchive() {
    return true;
  }

  @Override
  public String getInterpreterArchiveName() {
    return String.format("ruby_r%d.zip", getVersion());
  }

  @Override
  public String getInterpreterArchiveUrl() {
    return RUBY_INSTALL_URL + getInterpreterArchiveName();
  }

  @Override
  public boolean hasExtrasArchive() {
    return true;
  }

  @Override
  public String getExtrasArchiveName() {
    return String.format("ruby_extras_r%d.zip", getVersion());
  }

  @Override
  public String getExtrasArchiveUrl() {
    return RUBY_INSTALL_URL + getExtrasArchiveName();
  }

  @Override
  public boolean hasScriptsArchive() {
    return false;
  }

  @Override
  public String getScriptsArchiveName() {
    return "";
  }

  @Override
  public String getScriptsArchiveUrl() {
    return "";
  }

  @Override
  public Map<String, String> getEnvironmentVariables(Context context) {
    Map<String, String> values = new HashMap<String, String>(3);
    values.put(ENV_PATH, getHome(context) + "/lib/ruby/2.0.0/arm-linux-androideabi" + ":" + getExtras() + "/lib/ruby/2.0.0");
    values.put(ENV_TEMP, getTemp());
    values.put("LD_LIBRARY_PATH", getHome(context) + "/lib:/system/lib");
    values.put(ENV_RUBY, getBinary(context).getAbsolutePath());
    return values;
  }

  @Override
  public boolean hasInteractiveMode() {
    return false;
  }

  private String getHome(Context context) {
    File file = InterpreterUtils.getInterpreterRoot(context, getName());
    return file.getAbsolutePath();
  }

  private String getExtras() {
    File file = new File(getExtrasRoot(), getName());
    return file.getAbsolutePath();
  }

  private String getTemp() {
    File tmp = new File(getExtrasRoot(), getName() + "/tmp");
    if (!tmp.isDirectory()) {
      tmp.mkdir();
    }
    return tmp.getAbsolutePath();
  }

  private String getExtrasRoot() {
    return InterpreterConstants.SDCARD_ROOT + getClass().getPackage().getName()
        + InterpreterConstants.INTERPRETER_EXTRAS_ROOT;
  }
}
