package main;

public class Fract {
    //分子分母
    private int a;
    private int b;

    public Fract(String string) {
        string = string.trim();

        int a, b;
        int cc = string.indexOf("'");
        int bb = string.indexOf("/");
        if (cc != -1) {

            int c = Integer.valueOf(string.substring(0, cc));
            b = Integer.valueOf(string.substring(bb + 1));
            a = c * b + Integer.valueOf(string.substring(cc + 1, bb));
        } else if (bb != -1) {
            b = Integer.valueOf(string.substring(bb + 1));
            a = Integer.valueOf(string.substring(0, bb));
        } else {
            a = Integer.valueOf(string);
            b = 1;
        }
        setNumeratorAndDenominato(a, b);
    }

    public Fract(int a, int b) {
        setNumeratorAndDenominato(a, b);
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public String getFract(){  //获得分数的表达，根据分子分母的大小关系是否化为带分数
        String str;
        if(b == 1) {  //分母为1，只看分子的大小
            str = String.valueOf(this.a);
        }else if(a == b){  //分子分母相等，输出1
            str = "1";
        }else if(a > b){  //分子大于分母，转化为带分数
            int roundNumber = a / b;
            int newNumerator = a - b * roundNumber;
            str = roundNumber + "'" + newNumerator + "/" + this.b;
        }else{  //否则按正常的分数输出
            str = this.a + "/" + this.b;
        }
        return str;
    }
    
    private void setNumeratorAndDenominato(int a, int b) {
        if (b == 0) {
            throw new RuntimeException("分母不为0");
        }
        int isNegative = (a ^ b) >>> 31 == 1 ? -1 : 1;
        a = Math.abs(a);
        b = Math.abs(b);
        int c = gcd(a, b);
        this.a = a / c * isNegative;
        this.b = b / c;
    }

    public Fract add(Fract fraction) {
        return new Fract(this.a * fraction.b + fraction.a * this.b,
                this.b * fraction.b);
    }

    public Fract sub(Fract fraction) {
        return new Fract(this.a * fraction.b - fraction.a * this.b,
                this.b * fraction.b);
    }

    public Fract multi(Fract fraction) {
        return new Fract(this.a * fraction.a,
                this.b * fraction.b);
    }

    public Fract div(Fract fraction) {
        return new Fract(this.a * fraction.b, b * fraction.a);
    }

    public void abs() {
        this.a = Math.abs(this.a);
        this.b = Math.abs(this.b);
    }

    public boolean isNega() {
        return a < 0;
    }

    private int gcd(int a, int b) {
        int mod = a % b;
        return mod == 0 ? b : gcd(b, mod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fract fraction = (Fract) o;

        if (a != fraction.a) return false;
        return b == fraction.b;
    }

    @Override
    public int hashCode() {
        int result = a;
        return 31 * result + b;
    }

    @Override
    public String toString() {
        if (b == 1) {
            return String.valueOf(a);
        } else {
            int i = a / b;
            if (i != 0) {
                return String.format("%d'%d/%d", i, Math.abs(a) - b * Math.abs(i), b);
            } else {
                return String.format("%d/%d", a, b);
            }
        }
    }
}

