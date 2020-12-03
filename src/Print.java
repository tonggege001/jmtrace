public class Print {
    public static final String clname =
            Print.class.getCanonicalName().replace(".", "/");

    public static void PrintGetStatic(String className, String fieldName){
        className = className.replace("/", ".");
        Object clsobj = null;
        try {
            clsobj = Class.forName(className);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.printf("%s %d %x %s\n", 'R', Thread.currentThread().getId(),
                System.identityHashCode(clsobj), className+"."+fieldName);
    }

    public static void PrintPutStatic(String className, String fieldName){
        className = className.replace("/", ".");
        Object clsobj = null;
        try {
            clsobj = Class.forName(className);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.printf("%s %d %x %s\n", 'W', Thread.currentThread().getId(),
                System.identityHashCode(clsobj), className+"."+fieldName);
    }

    public static void PrintGetField(Object obj, String fieldName){
        String className = obj.getClass().getCanonicalName();
        System.out.printf("%s %d %x %s\n", "R", Thread.currentThread().getId(), System.identityHashCode(obj),
                className+"."+fieldName);
    }

    public static void PrintPutField(Object obj, String fieldName){
        String className = obj.getClass().getCanonicalName();
        System.out.printf("%s %d %x %s\n", "W", Thread.currentThread().getId(), System.identityHashCode(obj),
                className+"."+fieldName);
    }

    public static void PrintXALoad(Object obj, int index){
        String arrayType = obj.getClass().getComponentType().getCanonicalName();
        String descriptor = String.format("%s[%d]", arrayType, index);
        System.out.printf("%s %d %x %s\n", "R", Thread.currentThread().getId(), System.identityHashCode(obj),
                descriptor);
    }

    public static void PrintXAStore(Object obj, int index){
        String arrayType = obj.getClass().getComponentType().getCanonicalName();
        String descriptor = String.format("%s[%d]", arrayType, index);
        System.out.printf("%s %d %x %s\n", "W", Thread.currentThread().getId(), System.identityHashCode(obj),
                descriptor);
    }

    public static void main(String[] args){
        Object obj = new Object();
        String str = "ssss";
        PrintGetField(obj,str);
    }
}
