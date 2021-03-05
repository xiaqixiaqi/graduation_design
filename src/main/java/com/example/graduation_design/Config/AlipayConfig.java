package com.example.graduation_design.Config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2021000117610841";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDrbbJZXOFCDIhXj3JQaN0/h4IVB5bW9Z4uxBH2zcTTCGXs3v5Owm98Yxj0ly0ZbCd6IsDuMtKDMCpsmPKdNJRPN4nNE2sswd+f5/SD2P1807SWK+VZQQ127NMUdapCucpkYxYJBJpHfdC2MUlqiVOdU4I1c5frCggC8RjXaeTNSzggpUSsZOVWazA9tQQfxTvTKBbPRzIXastwOiJxkON/njQqt4+CYd7emim7IV5kVVUmJ/GhXxia9i30PMjgX71s26AtvvTAbhNox7SUslDYRaZTClGcxvptwMuLO2blWjofj1EyFq8ip9SvyHpyKNJO8g4ATr060hIxzAL3+yiDAgMBAAECggEBAMzpc7xYHS/x3JHppuPaAgiTl2Naca2zpvSpb8FTJCdSPfTHuq0uiv7G+ieZlYV1H38s1KP+1D+fIq3XCgROiW/RtT0r07LnJA74Q1im+ys00Q+MMVlYR7HlO2upZoIhV8m8fKdovCfRmGGb0HzL/l0HHGQNsoK+/m4hDzAfWShczn03AFtJNI6scdVO3NTXfzsK22bYo3uLmGgORt55+yfoqc7TWGP6uoT7GFiiFqZ9/IBOlckGUPvuAwcrz/Hxf76s/Zj26fxnSIGsD7q0FOYNFDNKuHc4tbV6PllNHehZwIBBuEb2EiVp4z4IKVAxZfOKYgfo4jZ1ycNwk8xdmMECgYEA/Ga45p4FDqafqEcwFu+Et5JWrhp4CbyMc/47ipufzo5hdkrVuKu2u4yc1akrF5htJopQ+g9Wti9lnciruHkq0sdQqFZ+ihkQZT4Hrae/41C4OnGmfv+RMspNKunVbl7FGeIYcBq76pDwQT9XW8vxvHPeN+zmNE5efl4Fs37j2DECgYEA7skF4PDKvdng1mUdz7jc5LbSsMicF7Q1j2AVqnULL2mfQLoGoDALgBiLwEi9WJM2cmt/+1VEDknbHwY9x/y1+P+MnlMwET1Ph8OY4Iqbxt/bW4ZYBUPZrK7lZaStSZR4POQU+jPcOv1kIjD+i8jy0IETQTXMtrQQstVRJKm5kvMCgYBWjbtWilXdolIZ2N+q3dQ7R1mmNrl3TUs087fjXl687KKLeDo2K9xsEFowAuUeIE097Cy8s8nei/5iz0/j4NlIksUcFR0rqlCvB0iGyoHJKAMlN5gK8QUPz8QKtUlVofYhXCFZQDG0zKM3HMuOVS++mlVYBEslIMLSvtdhqF/l4QKBgFRKBZc3qHmH1YQYwYM3wFHD72+UAPWlGVr5EezP0Bj2cc7JW9lj3EtlXr+Lbbs8i/Wr2MuBd7qLe5vh+iEEJcA1hQ/GUatkNxS3iNmKVB7JmoceTyfcoZQBUNymWr0keaUU5grED7OLCrO/E7uIPZ44UfGs6gx8V4CpKDGG8AjdAoGBAMbTFcAAThrFHLq/lEgZAD58M15ZeN4lMGv5fEvGE2QWlBu2O4rGnQxlVUUswpjogNgn/PEPji4pn001B1mG0bauvQJBaL1l8zmAvqM8euWvpv9a599GOX/6zFg2v6emywZQazxT60wkftocjRrQeRRG2m4gEUIM1e0LyryOXJzO";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4DhvoQonx/mD3kC8w8+Sh7JMyyuxkuwaj6DoSaCvo9xFFJZopL44vZInWsB52aR4eBIItckf91nQ4cHKjqVlBlkhjojiMMSfEd5ascMJhuLr+I1WsniO5gL06i5jOyd1hGgiOpnBv1Z0tuzRoSR/gvTgHVy9ANI4fkYCDH0PBmKAOvUHKgVmPBTAkeTYDgtUhw7iNt0p22/q+0r1uZlpxSkwghPsh6kKXz2zDwWsHVYGFLREmZ9LAUpAalFlSmaacqoFHMOVzUkD/RzdHWL9j3No7z/3sjv9veTicwuSj3txxGtSzgnPDsr8qv1EXAmZgVfypLiWviUipDwBrwn/oQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/user/addOrdering";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8080/user/addOrdering";

    // 签名方式
    public static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关，这是沙箱的网关
    public static String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}