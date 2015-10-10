package com.hadican.bildirim.health;

import com.codahale.metrics.health.HealthCheck;

public class BildirimHealthCheck extends HealthCheck {

	/**
	 * Simple health check.
	 */
	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}
