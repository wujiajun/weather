package com.weather.cms.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.core.web.session.UserContext;
import com.weather.cms.service.ICmsLoginService;

@Controller
@RequestMapping("/cms")
public class CmsLoginController {
	private final Log log = LogFactory.getLog(getClass());

	private static final String ERROR = "cms/error";

	@Autowired
	private ICmsLoginService cmsLoginService;
	@Value("${cms.secret}")
	private String cmsSecret;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request,
			HttpServletResponse response, Model model, String token) {
		long uid = 0;
		try {
			uid = Long.parseLong(DESUtils.decryptToString(cmsSecret.getBytes(),
					token));
		} catch (Exception e) {
			log.error("decrypt cms login error.", e);
			return ERROR;
		}
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin() && context.isAdmin() && context.getUid() == uid) {
			return "cms/index";
		}
		if (uid == 3) {
			cmsLoginService.login(request, response, uid);
			return "cms/index";
		}
		return ERROR;
	}

	public static void main(String[] args) throws NumberFormatException,
			DecoderException {
		try {
			String token = DESUtils.encryptToHexString(
					"51juzhai.wujiajun.max.kooks".getBytes(), "3".getBytes());
			System.out.println(token);

			long uid = Long.parseLong(DESUtils.decryptToString(
					"51juzhai.wujiajun.max.kooks".getBytes(), token));
			System.out.println(uid);

			// System.out
			// .println(DESUtils.encryptToHexString(
			// "51juzhai.wujiajun.max.kooks".getBytes(),
			// "812".getBytes()));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
}
