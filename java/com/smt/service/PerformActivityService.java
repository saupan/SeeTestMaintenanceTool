package com.smt.service;

import com.smt.utils.DevicePOJO;

public interface PerformActivityService {

	public String callPerformActivityAndroid(DevicePOJO seeTestDevice, String lowerCase);

	public String callPerformActivityIOS(DevicePOJO seeTestDevice, String lowerCase);
}
