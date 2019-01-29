package com.app.stock.config.Alipay;

/**
 * Created by lmx
 * Date 2019/1/15
 */
public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     *合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
     */
    public static final String SELLER_ID = "2088102177123794";

    /**
     * 支付宝的URL地址
     */
    public static final String APP_URL = "https://openapi.alipaydev.com/gateway.do";

    /**
     *商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
     */
    public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnIjUsVdBx/s43\n" +
            "FJdwp/d7iirAo2BDLhp81n8CLi7OxzvHhbxyNW94jCMVcK8y3r94pF+tR6afyYK2\n" +
            "pyc3zGwgbSf8Thz0QnJDiYBhKcaCuopPWRAaX/hQeoLoARg5nmKV4nr8IfZcugaf\n" +
            "InvLuKvzod/wAUD4W/VkwCdSi4Ms29EvreRY4av30j50w2ry290bdXSVQxbMqOnO\n" +
            "k6VVkUj7LVbynjM0jQYw1kCW7GqXa1+8ufw5ozx4ZCPTjetVXS1Oyui8b9btMzY0\n" +
            "28PfDmeOR0wIpzxXaNNUVLNIgewUrfPxAGps4jz2vu3eXf5BsrzgbpsBSX5RkWbj\n" +
            "X1XoHGcVAgMBAAECggEBAIIbZR1waGZ1n37Nbt7qQwRPGN0B0gw6NbB9Ij5GSXX7\n" +
            "LxQxjfOymWZUYXNtV1XYhWGbREgWgSXerv0Vvrf3US0lDH2jKXMUzvK4ddItvTVD\n" +
            "liwS/zApCZtM499MCtm/7KsVtfvVTH3+6uyy4YBD8MgmebfSogs1OyEFXRcrOCCQ\n" +
            "dIN5q+Sm30QaCAMjOjhJqlNj1HTRxVKziiEoxPxmLbyeauTc03qtl9S6RkhPeYVO\n" +
            "YFC68IpQWSailVtIgenlLihL6InXRKn8DNbh9K3Qn3BaHZUmZ2/jN6UoweBMsSyX\n" +
            "xtwIHf0+WIfQ90CzdVk6q9NRA37v6qoH1n7eeZtsR/kCgYEA0Trw01Q0A/NJRomg\n" +
            "maQOwXAIB345C3oawe/1LLV52PUbu7J9/Yp5BqA/2zSYJFf5yYsHdSuHx86WCDn4\n" +
            "4XmDgZs6WeKhlbRNIqGuyzAaDkIPP1/Mnga/BOSxpQoNW40iOwF88Aud7bhbd4Ks\n" +
            "TY5FbzpoTJ8Kp0pGWKBFwD0zvDMCgYEAzH5RENNzFBjXOd35F64uSlw+xWQAbekb\n" +
            "LEBupoXNYjh8usvVdlOirtYS7wU6vpd6JiZ7Mcu3gMqfgyDsiJZWdDoSjlH2ueKJ\n" +
            "xjSJB+YNyHAb1umGIAjlGXiOf1SmwW/Yx3An7roKbMcjG7UmBgVO1GhZ6MhpXPRv\n" +
            "AsmsZ3bBB5cCgYAJlyYKycVImRI2xYAzCq4Sd8wma8a5m/M2CIW4cukO5M1MoTXd\n" +
            "NUeu4hGLPrHqmI2+izzgRgqHQhTmc6HQ0/0PJ8pY7GZzsjwG5lpYve+zz5tbUEzU\n" +
            "wYJn+wOZiQhrteBny/2qEx4+E0E+DSNQusC8BHVbbb7TRDhNP6ty0FtauQKBgFll\n" +
            "VzcN2R8OBPjNKxnJEX98eAx+vRlPcIuV10mA8Npha1WVykmiIlpgR4KqPJYZTlLj\n" +
            "O7P3JM9uxPDkMcM8Raslv/rrpfp5KFW3nbgQQDJBoEoBaJsUiGLsKw9LCGND6/az\n" +
            "9yELv4byP0zbkjXLmDyPKc7z3eu/B9CmLXJFnfkdAoGAIQATqPF7MHeL3rZ/O3YE\n" +
            "0qxweJ4eWrcGoAJD1115hcZwrBEyA5k4krZ8i0dw3q3oYx9kpT3VvkiZ2GMifdv3\n" +
            "hBK23eBxJn8XJjKFZ7rM0H3XGDB4+zN1nRj80rLvLJT3Fy+ojR0odVSKIxMd1Ki6\n" +
            "8zFxsv4rheN4OLlst2fOaX8=";

    /**
     *支付宝的应用公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
     */
    public static final String ALIPAY_PUBLIC_KEY = "\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApyI1LFXQcf7ONxSXcKf3\n" +
            "e4oqwKNgQy4afNZ/Ai4uzsc7x4W8cjVveIwjFXCvMt6/eKRfrUemn8mCtqcnN8xs\n" +
            "IG0n/E4c9EJyQ4mAYSnGgrqKT1kQGl/4UHqC6AEYOZ5ileJ6/CH2XLoGnyJ7y7ir\n" +
            "86Hf8AFA+Fv1ZMAnUouDLNvRL63kWOGr99I+dMNq8tvdG3V0lUMWzKjpzpOlVZFI\n" +
            "+y1W8p4zNI0GMNZAluxql2tfvLn8OaM8eGQj043rVV0tTsrovG/W7TM2NNvD3w5n\n" +
            "jkdMCKc8V2jTVFSzSIHsFK3z8QBqbOI89r7t3l3+QbK84G6bAUl+UZFm419V6Bxn\n" +
            "FQIDAQAB";

    /**
     * 支付宝公钥
     */
    public static final String ALIPAY_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx60ZDvCm3fIbWKuluWumwQgzIAmCH31zeMxOTg7Jp2RYJ63+zbq1oxeK/HpjLakLOOxrK7L9SVfNMnH9QUgKLVL8MCpK3fR1lYkqXZPcmf20bf9oJaN0iycNbHFPo4tn5gfmL4D1QeXtf5+GAIGN0d5y31A/5cufi9eKTKKWUQFwletVm6a1YPcsdIYTvBsF28YKAGnajprzin73iYsRwah7FTfSd/3I4HKrOkN3S7j7BI0A1uDvnRT+MvDIDcUSSlaA+EGdmEu2By5nFBin0dN+OQ3PJ8Pr6TAPMQdh4EOM1l0xztuknhWyJNBL2CV7vWIV/Rb3h9tuHdTUyUtEpwIDAQAB";

    /**
     * 签名方式
     */
    public static final String SIGN_TYPE = "RSA2";
    /**
     * 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法
     */
    public static final String LOG_PATH = "C://";
    /**
     * 字符编码格式 目前支持 gbk 或 utf-8
     */
    public static final String CHARSET = "utf-8";
    /**
     * 接收通知的接口名(回调地址)
     */
    public static final String NOTIFY_URL = "/api/alipay/alipayNotify";
    /**
     * APPID
     */
    public static final String APP_ID = "2016092400588821";

    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}
