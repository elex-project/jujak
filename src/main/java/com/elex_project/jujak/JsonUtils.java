/*
 * Project Jujak
 *
 * Copyright (c) 2021. Elex.
 * All Rights Reserved.
 */

package com.elex_project.jujak;

import com.elex_project.abraxas.IOz;
import com.elex_project.abraxas.Reflectz;
import com.elex_project.abraxas.Stringz;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class JsonUtils {
	private JsonUtils(){}

	/**
	 *
	 * @param csv
	 * @return
	 */
	@NotNull
	public static JSONArray fromCommaSeparatedString(@NotNull String csv){
		return CDL.toJSONArray(csv);
	}

	/**
	 *
	 * @param header
	 * @return
	 * @see HTTP#toJSONObject(String)
	 */
	public static JSONObject fromHttpHeader(@NotNull String header){
		return HTTP.toJSONObject(header);
	}

	/**
	 *
	 * @param cookie
	 * @return
	 * @see Cookie#toJSONObject(String)
	 */
	public static JSONObject fromCookie(@NotNull String cookie){
		return Cookie.toJSONObject(cookie);
	}

	/**
	 * 제이슨 파일을 읽어온다.
	 *
	 * @param file 제이슨 파일. UTF-8인코딩
	 * @return 제이슨 객체
	 * @throws IOException ..
	 */
	@NotNull
	public static JSONObject readJSONFrom(@NotNull File file) throws IOException {
		return readJSONFrom(new FileInputStream(file));
	}

	/**
	 * 입력스트림에서 제이슨을 읽어온다.
	 *
	 * @param inputStream 입력 스트림
	 * @return 제이슨
	 * @throws IOException ..
	 */
	@NotNull
	public static JSONObject readJSONFrom(@NotNull InputStream inputStream) throws IOException {
		return new JSONObject(IOz.readStringFrom(inputStream));
	}

	@NotNull
	public static JSONObject readJSONFrom(@NotNull Reader reader) throws IOException {
		return new JSONObject(IOz.readStringFrom(reader));
	}

	/**
	 * 제이슨 파일을 작성한다.
	 *
	 * @param json 제이슨
	 * @param file 파일
	 * @throws IOException ..
	 */
	public static void writeJSONTo(@NotNull JSONObject json, @NotNull File file) throws IOException {
		writeJSONTo(json, new FileOutputStream(file));
	}

	/**
	 * 제이슨 파일을 작성한다.
	 *
	 * @param json   제이슨
	 * @param indent 들여쓰기
	 * @param file   파일
	 * @throws IOException ..
	 */
	public static void writeJSONTo(@NotNull JSONObject json, int indent, @NotNull File file) throws IOException {
		writeJSONTo(json, indent, new FileOutputStream(file));
	}

	/**
	 * 제이슨 파일을 작성한다.
	 *
	 * @param json         제이슨
	 * @param outputStream 출력 스트림
	 * @throws IOException ..
	 */
	public static void writeJSONTo(@NotNull JSONObject json, @NotNull OutputStream outputStream) throws IOException {
		writeJSONTo(json, 0, outputStream);
	}

	/**
	 * 제이슨 파일을 작성한다.
	 *
	 * @param json         제이슨
	 * @param indent       들여쓰기
	 * @param outputStream 출력 스트림
	 * @throws IOException ..
	 */
	public static void writeJSONTo(@NotNull JSONObject json, int indent, @NotNull OutputStream outputStream) throws IOException {
		BufferedWriter writer = IOz.getWriter(outputStream);

		if (indent == 0) {
			writer.write(json.toString());
		} else {
			writer.write(json.toString(indent));
		}
		writer.close();
	}


	/**
	 * 인스턴스를 제이슨 객체로 만들기.
	 * 제이슨에 포함하고자하는 인스턴스의 멤버 필드(혹은 메서드)는 @JSONPropertyName 로 어노테이션을 달아야 한다.
	 * @param object 대상 객체
	 * @return 제이슨
	 */
	@NotNull
	public static JSONObject reflectAsJSONObject(@NotNull Object object) {
		JSONObject json = new JSONObject();
		//Field[] fields = getFields(object.getClass());
		for (Field field : Reflectz.getFields(object.getClass())) {
			JSONPropertyName annotation = field.getAnnotation(JSONPropertyName.class);
			if (null != annotation) {
				try {
					String name = annotation.value();
					if (Stringz.isEmpty(name)) {
						name = field.getName();
					}
					Object value;
					if (!field.isAccessible()) {
						field.setAccessible(true);
						value = field.get(object);
						field.setAccessible(false);
					} else {
						value = field.get(object);
					}

					if (null == value){
						json.put(name, JSONObject.NULL);
					} else if (value instanceof Boolean || value instanceof Character ||
							value instanceof Number || value instanceof String ||
							value instanceof JSONObject || value instanceof JSONArray) {
						json.put(name, value);
					} else {
						json.put(name, reflectAsJSONObject(value));
					}

				} catch (IllegalAccessException e) {
					log.error("IllegalAccessException", e);
				}
			}

		}

		for (Method method : Reflectz.getMethods(object.getClass())){
			JSONPropertyName annotation = method.getAnnotation(JSONPropertyName.class);
			if (null != annotation) {
				try {
					String name = annotation.value();
					if (Stringz.isEmpty(name)) {
						name = method.getName();
					}
					Object value;
					if (!method.isAccessible()) {
						method.setAccessible(true);
						value = method.invoke(object);
						method.setAccessible(false);
					} else {
						value = method.invoke(object);
					}

					if (null == value){
						json.put(name, JSONObject.NULL);
					} else if (value instanceof Boolean || value instanceof Character ||
							value instanceof Number || value instanceof String ||
							value instanceof JSONObject || value instanceof JSONArray) {
						json.put(name, value);
					} else {
						json.put(name, reflectAsJSONObject(value));
					}

				} catch (IllegalAccessException | InvocationTargetException e) {
					log.error("IllegalAccessException", e);
				}
			}
		}

		return json;
	}

	public static String toXMLString(@Nullable JSONObject json, @Nullable String tagName){
		return XML.toString(json, tagName);
	}
}
