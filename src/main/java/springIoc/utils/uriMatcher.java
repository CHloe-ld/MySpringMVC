package springIoc.utils;

import java.util.ArrayList;

public class uriMatcher {

    public static ArrayList<String> matchUrl(String url, String pattern){
        String[] splitUrl = url.split("/");
        String[] splitPattern = pattern.split("/");
        if(splitUrl.length != splitPattern.length) // 长度不相等 显然不是匹配的url
            return null;

        ArrayList<String> restParameters = new ArrayList<String>();
        for(int i=0;i<splitUrl.length;i++){
            if(isRestPattern(splitPattern[i])){
                restParameters.add(getRestAttr(splitUrl[i]));
            } else {
                if(!splitUrl[i].equals(splitPattern[i])) { // 中间有普通url不匹配 直接返回null
                    return null;
                }
            }
        }
        return restParameters; // 匹配成功 返回一个包含了rest attr的列表 用户必须按顺序定义rest参数
    }


    private static boolean isRestPattern(String p){
        return p.startsWith("{") && p.endsWith("}");
    }

    private static String getRestAttr(String pattern){
        return pattern.replace("{","").replace("}","");
    }
}
