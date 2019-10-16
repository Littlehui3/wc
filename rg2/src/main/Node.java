package main;

public class Node implements Cloneable{
    Fract r;
    Node ri;
    Node le;
    int h;

    Node(Fract r, Node ri, Node le, int h) {
        this.r = r;
        this.ri = ri;
        this.le = le;
        this.h = h;
    }

    @Override
    public String toString() {
        return r.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        Node node = (Node)super.clone();
        Node right = node.ri;
        Node left = node.le;
        if (right != null) {
            node.ri = (Node)right.clone();
        }
        if (left != null) {
            node.le = (Node)left.clone();
        }
        return node;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if((o==null)||(getClass() != o.getClass())) return false;

        Node node = (Node) o;

        if(r != null){
            if(!r.equals(node.r)){
                return false;
            }
        }else{
            if(node.r!=null){
                return false;
            }
        }
        if(r != null){
            if(!ri.equals(node.ri)){
                return false;
            }
        }else{
            if(node.ri != null){
                return false;
            }
        }
        if(le != null){
            return le.equals(node.le);
        }else{
            return node.le == null;
        }
    }
}
