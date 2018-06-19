package fun.skai.smartcar.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyInformation {

    /**
     * 验证手机号是否有效
     * @param phoneNumber 手机号
     * @return 手机号是否有效
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        String regExp = "^((13[0-9])|(18[0-9])|(17[135-8])|(14[579])|(15[0-35-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * 验证密码是否有效
     * @param password 密码
     * @return 密码是否有效
     */
    public static boolean isPasswordValid(String password) {
        String regExp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 判断密码是否和确认密码相同
     * @param password 密码
     * @param rePassword 确认密码
     * @return 两次密码是否相同
     */
    public static boolean isPasswordConfirm(String password, String rePassword) {
        return password.equals(rePassword);
    }
}
