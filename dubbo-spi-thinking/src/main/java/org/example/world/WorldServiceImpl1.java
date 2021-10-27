package org.example.world;

import org.apache.dubbo.common.URL;

public class WorldServiceImpl1 implements WorldService {

	@Override
	public void worldCN() {
		System.out.println("this is world service 1 world cn");
	}

	@Override
	public void worldEN(URL url) {
		System.out.println("@Adaptive this is world service 1 world en");
	}

}
