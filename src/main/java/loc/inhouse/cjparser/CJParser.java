package loc.inhouse.cjparser;

import java.util.HashMap;
import java.util.Map;

public class CJParser {
    private static Map<String, CJPOption> options = new HashMap<>();

    private static CJPOption curArgReceiver = null;
    private static int requiredArgs = 0;

    public static void parse(String... args){
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if(arg.charAt(0) == '-'){
                parseOpt(arg);
            }else{
                parseArg(arg);
            }
        }
    }

    private static void parseOpt(String opt){
        if(opt.indexOf("--") == 0 && opt.length() > 2){
            String longOpt = opt.substring(2);
            CJPOption option = options.get(longOpt);
            if(option == null) return; // TODO - ERROR REPORTING
            option.enable();
            if(!option.flag()){
                if(requiredArgs > 0) return;// TODO - MISSING ARGUMENTS FOR PREVIOUS OPT
                else {
                    curArgReceiver = option;
                    requiredArgs = option.argCount();
                }
            }
            return;
        }
        if(opt.length() > 1){
            for(char c : opt.substring(1).toCharArray()){
                try {
                    CJPOption option = options.values().parallelStream()
                            .filter(cjpOption -> cjpOption.getShortOpt() == c)
                            .findFirst().get();
                    option.enable();
                    if(!option.flag()){
                        if(requiredArgs > 0) return;// TODO - MISSING ARGUMENTS FOR PREVIOUS OPT
                        else {
                            curArgReceiver = option;
                            requiredArgs = option.argCount();
                        }
                    }
                } catch (NullPointerException ne){
                    // TODO - ERROR REPORTING
                }
            }
        }
    }

    private static void parseArg(String arg){
        if(requiredArgs == 0) return; //TODO - TOO MANY ARGUMENTS
        requiredArgs--;
        curArgReceiver.addArg(arg);
    }

    public static void addOpts(CJPOption... opts){
        for (CJPOption opt: opts) {
            options.put(opt.getLongOpt(), opt);
        }
    }

    public static String getUsage(){
        StringBuilder usage = new StringBuilder("Usage:\n\n");
        options.forEach((s, cjpOption) -> {
            String opt = String.format("\t-%s, --%-20s\t%s\n", cjpOption.getShortOpt(), s, cjpOption.getDescription());
            usage.append(opt);
        });
        return usage.toString();
    }

    public static CJPOption getOpt(String longName){ return options.get(longName); }
}
