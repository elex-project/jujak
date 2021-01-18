/*
 * Project Jujak
 *
 * Copyright (c) 2021. Elex.
 * All Rights Reserved.
 */

package com.elex_project.jujak;

import org.json.JSONObject;

/**
 * 제이슨 객체를 다시 현재 객체로 바꾸는 생성자 또는 정적 메서드도 함께 구현하시오.
 */
public interface JSONable {
	/**
	 * 제이슨 객체로 변환
	 *
	 * @return 제이슨
	 */
	public JSONObject toJSON();
}
