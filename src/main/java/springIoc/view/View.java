package springIoc.view;

import java.util.HashMap;
import java.util.Map;

public class View {
    private String path;
    private Map<String, Object> attrMap;

    public View(String path){
        this.path = path;
        attrMap = new HashMap<>();
    }

    public String getPath(){
        return path;
    }

    public void addAttr(String s, Object o){
        this.attrMap.put(s, o);
    }

    public Map<String, Object> getAttr(){
        return this.attrMap;
    }
}
