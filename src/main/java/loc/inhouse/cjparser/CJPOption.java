package loc.inhouse.cjparser;

import java.util.ArrayList;

public class CJPOption {
    private String longOpt;
    private char shortOpt;
    private String description;
    private boolean isFlag;

    private boolean present = false;
    private ArrayList<String> args;
    private int argc;

    public CJPOption(String longOpt, char shortOpt, String description, int argc){
        this(longOpt, shortOpt, description, false, argc);
    }
    public CJPOption(String longOpt, char shortOpt, String description){
        this(longOpt, shortOpt, description, true, 0);
    }
    private CJPOption(String longOpt, char shortOpt, String description, boolean isFlag, int argc){
        this.longOpt = longOpt;
        this.shortOpt = shortOpt;
        this.description = description;
        this.isFlag = isFlag;
        this.args = new ArrayList<>(argc);
        this.argc = argc;
    }

    public String getLongOpt(){ return longOpt; }
    public char getShortOpt(){ return shortOpt; }
    public void enable(){ present = true; }
    public boolean enabled(){ return present; }
    public boolean flag(){ return isFlag; }
    public int argCount(){ return argc; }
    public void addArg(String arg){ args.add(arg); }
    public String[] getArgs(){ return args.toArray(new String[args.size()]); }
    public String getDescription(){ return description; }
}
