/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.weather.alipay.util;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	public static final String DEFAULT_PARTNER = "2088111784669441";

	public static final String DEFAULT_SELLER = "easylife222@qq.com ";

	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANcAPUCjtbKhlduYhxLhbQaBmXTh4OIS8s0qkWwkO1zudyjLQt/F8gqrG7NznJHPKAAzks4MGRF5i8snPnLQkzy3Jv98SpQVA/VEe0PB47ytwZVgBjlZI3uVhbxeftfcr/OrCHt/4MHBm6FznhszPcSWfWOQ7hY9Vrkm30ihBWHfAgMBAAECgYAHC/b1+2elJy6mN+WVdNYB9JV3A0A/qKdUmiSSCs33ScmWCLN0aiR4vQr3Yr6q+MMy3n3dSwA3VmiCjBK76KIqt93j4vwUacYjeDWOoqIBi8v7PvEzvFOYmqE49fFX1MNOl2jLBpIDQb+kTmhZmUUjhSyCFe6QbTeKvEBpfmspQQJBAPkxEsqq8A4KhlcCV5FugmapQ8MZo28rh439KYDQOWHBiDPXg/1NvPYgHTbXKxMvtex/Sz3KlXsKT54oopKXTDkCQQDc4AZVJLfUIzyXijjrQbhk8kuLO2cDUINB8+KT/gUzhpI7rJ/edT6BuMTT0ebf2QwBOroenytzpJyRZWXWk07XAkATu+UnE55rHj5BSbgxljTReHobSz58IVBc2D2fK94/dZoZsOmM98kNHMHzg9qvbEnV7sbAn/hyhACGH+WwAjdRAkB7liY08eyiGG1I5aBfklBYu3Iln8FOC3jGKG55HENQx1V/amGxmay17OrFEse6LLoY3gFSLQGlHVA10qSe4aHVAkEAtABd0FeArm9rPRh0MADTDgPGLbnJctkPPDytjfjp+MuNwrI0/93DJpqDVycqRD6zBsBSyhsyZNs2YG7MGrp3nA==";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
