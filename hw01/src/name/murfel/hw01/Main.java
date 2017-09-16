package name.murfel.hw01;

public class Main {

    public static void main(String[] args) {
        myListDemo();
        myHashMapDemo();
    }

    public static void myListDemo() {
        MyList l = new MyList();
        l.add("kek", "lol");
        System.out.println(l.size()); // 1
        System.out.println(l.get(0)[0]);  // kek
        System.out.println(l.get(0)[1]);  // lol
        System.out.println(l.size());  // 1
        l.remove(0);
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
        System.out.println(hm.contains("hi"));  // false
        System.out.println(hm.contains("kek"));  // false
    }
}