package name.murfel.hw01;

public class Main {

    public static void main(String[] args) {
        myElementDemo();
        myListDemo();
        myHashMapDemo();
    }

    public static void myElementDemo() {
        MyElement element = new MyElement("first", "second");
        System.out.println(element.key);  // first
        System.out.println(element.value);  // second
    }

    public static void myListDemo() {
        MyList l = new MyList();
        l.add(new MyElement("kek", "lol"));
        System.out.println(l.size()); // 1
        System.out.println(l.at(0).key);  // kek
        System.out.println(l.at(0).value);  // lol
        System.out.println(l.size());  // 1
        l.remove("kek");
        System.out.println(l.size());  // 0
    }

    public static void myHashMapDemo() {
        MyHashMap hm = new MyHashMap();
        hm.put("hi", "kek");
        System.out.println(hm.contains("hi"));  // true
        System.out.println(hm.contains("kek"));  // false
        System.out.println(hm.get("hi"));  // kek
        System.out.println(hm.size());  // 1
        System.out.println(hm.remove("hi"));  // kek
        System.out.println(hm.size());  // 0
        System.out.println(hm.contains("hi"));  // false
        System.out.println(hm.contains("kek"));  // false
        System.out.println(hm.contains("meow"));  // false
    }
}