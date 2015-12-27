package com.weather.passport.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.dao.Limit;
import com.juzhai.core.util.StringUtil;
import com.weather.alipay.util.Rsa;
import com.weather.core.InitData;
import com.weather.core.bean.DeviceName;
import com.weather.passport.controller.form.DonateForm;
import com.weather.passport.exception.DonateException;
import com.weather.passport.mapper.DonateMapper;
import com.weather.passport.mapper.OrderMapper;
import com.weather.passport.model.Donate;
import com.weather.passport.model.DonateExample;
import com.weather.passport.model.Order;
import com.weather.passport.model.OrderExample;
import com.weather.passport.service.IDonateService;

@Service
public class DonateService implements IDonateService {

	@Autowired
	private DonateMapper donateMapper;
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public void addUpDonate(DonateForm donateForm, DeviceName deviceName,
			boolean needValidate) throws DonateException {
		// 字段更新
		if (null == deviceName) {
			throw new DonateException(DonateException.ILLEGAL_OPERATION);
		}
		if (null == donateForm) {
			throw new DonateException(DonateException.ILLEGAL_OPERATION);
		}
		if (StringUtils.isEmpty(donateForm.getUdid())
				|| StringUtils.length(donateForm.getUdid()) > 100) {
			throw new DonateException(DonateException.ILLEGAL_OPERATION);
		}

		// TODO name过滤词
		if (StringUtils.isEmpty(donateForm.getName())
				|| StringUtil.chineseLength(donateForm.getName()) > 16) {
			throw new DonateException(DonateException.DONATE_NAME_ERROR);
		}

		if (donateForm.getPrice() <= 0) {
			throw new DonateException(DonateException.ILLEGAL_OPERATION);
		}

		if (StringUtils.isEmpty(donateForm.getOrderId())
				|| StringUtils.length(donateForm.getOrderId()) > 100) {
			throw new DonateException(DonateException.ILLEGAL_OPERATION);
		}
		if (donateForm.getColorIndex() < 0) {
			throw new DonateException(DonateException.ILLEGAL_OPERATION);
		}

		// city验证，过滤词
		if (StringUtils.isEmpty(donateForm.getCity())
				|| InitData.CITY_MAP.get(donateForm.getCity()) == null) {
			throw new DonateException(DonateException.DONATE_CITY_ERROR);
		}
		// 本地验证订单有效性
		OrderExample orderExample = new OrderExample();
		orderExample.createCriteria()
				.andOrderIdEqualTo(donateForm.getOrderId());
		if (orderMapper.countByExample(orderExample) > 0) {
			throw new DonateException(DonateException.ILLEGAL_OPERATION);
		}

		if (needValidate) {
			// TODO 远程验证订单有效性
			if (DeviceName.ANDROID.getName().equals(deviceName.getName())) {
				if (StringUtils.isEmpty(donateForm.getAlipayParams())) {
					throw new DonateException(DonateException.ILLEGAL_OPERATION);
				}
				if (!Rsa.checkSign(donateForm.getAlipayParams())) {
					throw new DonateException(DonateException.ILLEGAL_OPERATION);
				}
			}
		}
		// 创建订单流水
		Order order = new Order();
		order.setOrderId(donateForm.getOrderId());
		order.setUdid(donateForm.getUdid());
		order.setPrice(donateForm.getPrice());
		order.setOrigin(deviceName.getName());
		order.setCreateTime(new Date());
		if (orderMapper.insertSelective(order) != 1) {
			throw new DonateException(DonateException.SYSTEM_ERROR);
		}

		Donate donate = donateMapper.selectByPrimaryKey(donateForm.getUdid());
		if (null == donate) {
			// 创建
			donate = new Donate();
			donate.setUdid(donateForm.getUdid());
			donate.setSum(donateForm.getPrice());
			donate.setName(donateForm.getName());
			donate.setLastOrderId(donateForm.getOrderId());
			donate.setLastDonateTime(new Date());
			donate.setColorIndex(donateForm.getColorIndex());
			donate.setCity(donateForm.getCity());
			donate.setOrigin(deviceName.getName());
			if (donateMapper.insertSelective(donate) != 1) {
				throw new DonateException(DonateException.SYSTEM_ERROR);
			}
		} else {
			// 更新
			donate.setCity(donateForm.getCity());
			donate.setColorIndex(donateForm.getColorIndex());
			donate.setLastDonateTime(new Date());
			donate.setLastOrderId(donateForm.getOrderId());
			donate.setName(donateForm.getName());
			donate.setSum(donate.getSum() + donateForm.getPrice());
			if (donateMapper.updateByPrimaryKey(donate) != 1) {
				throw new DonateException(DonateException.SYSTEM_ERROR);
			}
		}
	}

	@Override
	public List<Donate> listDonateOrderByTime(int firstResult, int maxResults) {
		DonateExample example = new DonateExample();
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_donate_time desc");
		return donateMapper.selectByExample(example);
	}

	@Override
	public List<Donate> listDonateOrderBySum(int firstResult, int maxResults) {
		DonateExample example = new DonateExample();
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("sum desc");
		return donateMapper.selectByExample(example);
	}

	@Override
	public int countDonate() {
		return donateMapper.countByExample(new DonateExample());
	}

}
