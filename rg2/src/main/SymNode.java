package main;

public class SymNode extends Node{
    String sym;

    SymNode(Node ri, Node le, String sym) {
        super(null,ri,le,0);
        this.sym = sym;
    }

    @Override
    public String toString() {
        return " "+sym+" ";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymNode that = (SymNode) o;
        if (this.h != that.h) {
            return false;
        }
        boolean flag = (sym != null? sym.equals(that.sym) : that.sym == null);
        if (!flag) {
            return false;
        }
        boolean left = this.le != null? this.le.equals(that.le) : that.le == null;
        boolean right = this.ri != null? this.ri.equals(that.ri) : that.ri == null;

        if (left && right) {
            return true;
        }
        if (left ^ right) {
            return false;
        }
        if (that.sym == null) {
            return false;
        }
        if (this.sym.equals("+") || this.sym.equals("×")) {
            left = (this.le != null) && (this.le.equals(that.ri));
            right = (this.ri != null) && (this.ri.equals(that.le));
        }
        return left&&right;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sym != null ? sym.hashCode() : 0);
        return result;
    }
}

