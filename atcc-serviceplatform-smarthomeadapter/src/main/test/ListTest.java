import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
    @Test
    public void test() {
        List<Node> list = new ArrayList<Node>();
        Node node = new Node();
        node.setKey("1");
        node.setValue("value");
        list.add(node);
        Node node2 = node;
        node2.setKey("2");
        node2.setValue("value2");
        list.add(node2);
        System.out.println(list);
    }


}

class Node {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}