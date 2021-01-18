/*
 * Project Jujak
 *
 * Copyright (c) 2021. Elex.
 * All Rights Reserved.
 */

package com.elex_project.jujak;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
public class JsonPreferences {
	protected JSONObject jsonObject;

	public JsonPreferences() {
		jsonObject = new JSONObject();
	}

	public JsonPreferences(final InputStream inputStream) {
		try {
			jsonObject = JsonUtils.readJSONFrom(inputStream);
		} catch (Exception e) {
			log.error("", e);
			jsonObject = new JSONObject();
		}
	}

	public JsonPreferences(final Reader reader) {
		try {
			jsonObject = JsonUtils.readJSONFrom(reader);
		} catch (Exception e) {
			log.error("", e);
			jsonObject = new JSONObject();
		}
	}

	public JsonPreferences(final File file) {
		try {
			jsonObject = JsonUtils.readJSONFrom(file);
		} catch (Exception e) {
			log.error("", e);
			jsonObject = new JSONObject();
		}
	}

	public void patch(@Nullable final JSONObject json) {
		if (null == json) return;

		for (String key : json.keySet()) {
			try {
				jsonObject.put(key, json.get(key));
			} catch (JSONException ignore) {
			}
		}
	}

	public void save(final @NotNull OutputStream outputStream, final int indent) throws Exception {
		JsonUtils.writeJSONTo(jsonObject, indent, outputStream);
	}

	public void save(final @NotNull OutputStream outputStream) throws Exception {
		save(outputStream, 0);
	}

	public void save(final @NotNull File file, final int indent) throws Exception {
		JsonUtils.writeJSONTo(jsonObject, indent, file);
	}

	public void save(final @NotNull File file) throws Exception {
		save(file, 0);
	}

	public boolean getBoolean(final @NotNull String key) throws JSONException {
		return jsonObject.getBoolean(key);
	}

	public boolean getBoolean(final @NotNull String key, final boolean def) {
		return jsonObject.optBoolean(key, def);
	}

	public void put(final @NotNull String key, final boolean value) {
		jsonObject.put(key, value);
	}


	public byte getByte(final @NotNull String key) throws JSONException {
		return (byte) jsonObject.getInt(key);
	}

	public byte getByte(final @NotNull String key, byte def) {
		return (byte) jsonObject.optInt(key, def);
	}

	public void put(final @NotNull String key, final byte value) {
		jsonObject.put(key, value);
	}

	public char getChar(final @NotNull String key) throws JSONException {
		return (char) jsonObject.getInt(key);
	}

	public char getChar(final @NotNull String key, final char def) {
		return (char) jsonObject.optInt(key, def);
	}

	public void put(final @NotNull String key, final char value) {
		jsonObject.put(key, value);
	}

	public short getShort(final @NotNull String key) throws JSONException {
		return (short) jsonObject.getInt(key);
	}

	public short getShort(final @NotNull String key, final short def) {
		return (short) jsonObject.optInt(key, def);
	}

	public void put(final @NotNull String key, final short value) {
		jsonObject.put(key, value);
	}

	public int getInt(final @NotNull String key) throws JSONException {
		return jsonObject.getInt(key);
	}

	public int getInt(final @NotNull String key, final int def) {
		return jsonObject.optInt(key, def);
	}

	public void put(final @NotNull String key, final int value) {
		jsonObject.put(key, value);
	}

	public long getLong(final @NotNull String key) throws JSONException {
		return jsonObject.getLong(key);
	}

	public long getLong(final @NotNull String key, final long def) {
		return jsonObject.optLong(key, def);
	}

	public void put(final @NotNull String key, final long value) {
		jsonObject.put(key, value);
	}

	public BigInteger getBigInt(final @NotNull String key) throws JSONException {
		return jsonObject.getBigInteger(key);
	}

	public BigInteger getBigInt(final @NotNull String key, final BigInteger def) {
		return jsonObject.optBigInteger(key, def);
	}

	public void put(final @NotNull String key, final BigInteger value) {
		jsonObject.put(key, value);
	}

	public float getFloat(final @NotNull String key) throws JSONException {
		return jsonObject.getFloat(key);
	}

	public float getFloat(final @NotNull String key, final float def) {
		return jsonObject.optFloat(key, def);
	}

	public void put(final @NotNull String key, final float value) {
		jsonObject.put(key, value);
	}

	public double getDouble(final @NotNull String key) throws JSONException {
		return jsonObject.getDouble(key);
	}

	public double getDouble(final @NotNull String key, final double def) {
		return jsonObject.optDouble(key, def);
	}

	public void put(final @NotNull String key, final double value) {
		jsonObject.put(key, value);
	}

	public BigDecimal getBigDecimal(final @NotNull String key) throws JSONException {
		return jsonObject.getBigDecimal(key);
	}

	public BigDecimal getBigDecimal(final @NotNull String key, final BigDecimal def) {
		return jsonObject.optBigDecimal(key, def);
	}

	public void put(final @NotNull String key, final BigDecimal value) {
		jsonObject.put(key, value);
	}

	public String getString(final @NotNull String key) throws JSONException {
		return jsonObject.getString(key);
	}

	public String getString(final @NotNull String key, final String def) {
		return jsonObject.optString(key, def);
	}

	public void put(final @NotNull String key, final String value) {
		jsonObject.put(key, value);
	}

	public JSONArray getArray(final @NotNull String key) throws JSONException {
		return jsonObject.getJSONArray(key);
	}

	public void put(final @NotNull String key, final JSONArray value) {
		jsonObject.put(key, value);
	}

	/**
	 * 저장된 열거형 값을 불러온다. 내부적으로는 열거형의 순서값이 정수로 저장되어 있고,
	 * 복원할 때에는 리플렉션을 사용하고 있다.
	 *
	 * @param key 키
	 * @param def 기본값
	 * @param <T> 열거형 상수
	 * @return 이넘
	 */
	@SuppressWarnings("unchecked")
	public <T extends Enum<?>> T getEnum(final @NotNull String key, final T def) {
		try {
			//Field field = def.getClass().getField(def.name());
			return (T) def.getClass().getField(def.name()).getType()
					.getEnumConstants()[getInt(key, def.ordinal())];
		} catch (Exception e) {
			//L.d(TAG, e);
			return def;
		}
	}

	/**
	 * 열거형을 저장한다. 내부적으로는 이넘의 순서값을 정수로 저장한다.
	 *
	 * @param key   키
	 * @param value 이넘
	 * @param <T>   이넘 타입
	 */
	public <T extends Enum<?>> void put(final @NotNull String key, final @NotNull T value) {
		put(key, value.ordinal());
	}

	public int[] getIntArray(final @NotNull String key, final int[] def) {
		try {
			return getIntArray(key);
		} catch (Exception e) {
			return def;
		}
	}

	public int[] getIntArray(final @NotNull String key) throws JSONException {
		JSONArray array = jsonObject.getJSONArray(key);
		int[] intArray = new int[array.length()];
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = array.getInt(i);
		}
		return intArray;
	}

	/**
	 * 정수형 배열을 저장한다.
	 *
	 * @param key      키
	 * @param intArray 정수 배열
	 */
	public void put(final @NotNull String key, final int[] intArray) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < intArray.length; i++) {
			array.put(i, intArray[i]);
		}
		jsonObject.put(key, array);
	}

	public byte[] getByteArray(final @NotNull String key, final byte[] def) {
		try {
			return getByteArray(key);
		} catch (Exception e) {
			return def;
		}
	}

	public byte[] getByteArray(final @NotNull String key) throws JSONException {
		JSONArray array = jsonObject.getJSONArray(key);
		byte[] byteArray = new byte[array.length()];
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = (byte) array.getInt(i);
		}
		return byteArray;
	}

	public void put(final @NotNull String key, final byte[] byteArray) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < byteArray.length; i++) {
			array.put(i, byteArray[i]);
		}
		jsonObject.put(key, array);
	}

	public String[] getStringArray(final @NotNull String key, final String[] def) {
		try {
			return getStringArray(key);
		} catch (Exception e) {
			return def;
		}
	}

	public String[] getStringArray(final @NotNull String key) throws JSONException {
		JSONArray array = jsonObject.getJSONArray(key);
		String[] stringArray = new String[array.length()];
		for (int i = 0; i < stringArray.length; i++) {
			stringArray[i] = array.getString(i);
		}
		return stringArray;
	}

	public void put(final @NotNull String key, final @NotNull String[] stringArray) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < stringArray.length; i++) {
			array.put(i, stringArray[i]);
		}
		jsonObject.put(key, array);
	}

	public JSONObject getJSONObject(final @NotNull String key) throws JSONException {
		return jsonObject.getJSONObject(key);
	}

	public void put(final @NotNull String key, final JSONObject json) {
		jsonObject.put(key, json);
	}

	public void put(final @NotNull String key, final @NotNull JSONable jsonable) {
		jsonObject.put(key, jsonable.toJSON());
	}
}
