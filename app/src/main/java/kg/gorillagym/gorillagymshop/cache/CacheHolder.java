package kg.gorillagym.gorillagymshop.cache;

import android.content.Context;

import kg.gorillagym.gorillagymshop.cache.impl.CacheImpl;

//TODO get rid of this some time
public class CacheHolder {
    public static Cache cache;

    public static void init(Context context){
        cache = new CacheImpl(context);
    }

    public static Cache getCache() {
        return cache;
    }
}
