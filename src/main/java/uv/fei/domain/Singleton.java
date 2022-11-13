package uv.fei.domain;

public class Singleton {
    private static Singleton login;
    private Usuario user;

    private Singleton(Usuario user) {
        this.user = user;
    }

    public static void setLogin(Usuario user) {

            login  = new Singleton(user);

    }

    public static String getUserName(){
        return login.user.getNombre();
    }

    public static int getId(){
        return login.user.getId();
    }

    public static  String rol(){ return  login.user.getRol();}

    public  static String acreditation(){ return  login.user.getAcreditacion();}

}