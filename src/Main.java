import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String [] args){
        int a = test();
        System.out.println("Main return value "+a);

        aaa A = new aaa();
        System.out.println(A.ccc());
    }

    public static int test(){
        System.out.println("Main class");
        return 0;
    }
}

class aaa{
    public int[] b;
    public List<Integer> list = null;

    public aaa(){
        b = new int[20];
        list = new LinkedList<>();
    }

    public int ccc(){
        this.b[2] = 5;
        this.b[1] = 7;

        int d = b[2];

        this.list.add(d+3);

        return d;
    }
}
