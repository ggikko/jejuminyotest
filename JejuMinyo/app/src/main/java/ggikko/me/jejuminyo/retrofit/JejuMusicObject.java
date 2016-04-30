package ggikko.me.jejuminyo.retrofit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by ggikko on 16. 2. 29..
 */

@Root(name = "jejunetApi")
public class JejuMusicObject {

    @Element(name = "items")
    private String items;

    public JejuMusicObject(String items) {
        this.items = items;
    }

    public String getSeq() {
        return items;
    }

    public void setSeq(String items) {
        this.items = items;
    }
}
