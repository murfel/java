package name.murfel.ftp;

public class ClientOrder {
    public ClientOrder() {

    }
    public ClientOrder(int query_type, String path) {
        this.query_type = query_type;
        this.path = path;
    }
    public int query_type;
    public String path;
}