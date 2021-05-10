import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author zhishun.cai
 * @date 2021/1/9 10:02
 */

public class A {
    public static void main(String[] args) {
        String gensalt = BCrypt.gensalt();
        String damoncai = BCrypt.hashpw("damoncai", gensalt);
        System.out.println(gensalt);
        System.out.println(damoncai);
    }
}
