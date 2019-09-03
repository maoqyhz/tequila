package xyz.furur.load;

/**
 * @author Fururur
 * @create 2019-09-03-16:17
 */
public class RestRemoteHystrix implements IRestRemote{
    @Override
    public String home() {
        return "default";
    }
}
