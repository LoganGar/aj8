package org.apollo.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;

/**
 * A class which contains text-related utility methods.
 *
 * @author Graham
 */
public final class TextUtil {

	/**
	 * An array of characters ordered by frequency - the elements with lower
	 * indices (generally) appear more often in chat messages.
	 */
	public static final char[] FREQUENCY_ORDERED_CHARS = { ' ', 'e', 't', 'a',
			'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y',
			'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', '.', ',', ':',
			';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=',
			'\243', '$', '%', '"', '[', ']' };

	/**
	 * Uncompresses the compressed data ({@code in}) with the length ({@code len}
	 * ) and returns the uncompressed {@link String}.
	 *
	 * @param in The compressed input data.
	 * @param len The length.
	 * @return The uncompressed {@link String}.
	 */
	public static String uncompress(byte[] in, int len) {
		byte[] out = new byte[4096];
		int outPos = 0;
		int carry = -1;

		for (int i = 0; i < len * 2; i++) {
			int tblPos = in[i / 2] >> 4 - 4 * (i % 2) & 0xF;
			if (carry == -1) {
				if (tblPos < 13) {
					out[outPos++] = (byte) FREQUENCY_ORDERED_CHARS[tblPos];
				} else {
					carry = tblPos;
				}
			} else {
				out[outPos++] = (byte) FREQUENCY_ORDERED_CHARS[(carry << 4) + tblPos - 195];
				carry = -1;
			}
		}
		return new String(out, 0, outPos);
	}

	/**
	 * Compresses the input text ({@code in}) and places the result in the
	 * {@code out} array.
	 *
	 * @param in The input text.
	 * @param out The output array.
	 * @return The number of bytes written to the output array.
	 */
	public static int compress(String in, byte[] out) {
		if (in.length() > 80) {
			in = in.substring(0, 80);
		}
		in = in.toLowerCase();

		int carry = -1;
		int outPos = 0;
		for (int inPos = 0; inPos < in.length(); inPos++) {
			char c = in.charAt(inPos);
			int tblPos = 0;
			for (int i = 0; i < FREQUENCY_ORDERED_CHARS.length; i++) {
				if (c == FREQUENCY_ORDERED_CHARS[i]) {
					tblPos = i;
					break;
				}
			}
			if (tblPos > 12) {
				tblPos += 195;
			}
			if (carry == -1) {
				if (tblPos < 13) {
					carry = tblPos;
				} else {
					out[outPos++] = (byte) tblPos;
				}
			} else if (tblPos < 13) {
				out[outPos++] = (byte) ((carry << 4) + tblPos);
				carry = -1;
			} else {
				out[outPos++] = (byte) ((carry << 4) + (tblPos >> 4));
				carry = tblPos & 0xF;
			}
		}
		if (carry != -1) {
			out[outPos++] = (byte) (carry << 4);
		}
		return outPos;
	}

	/**
	 * Filters invalid characters from the specified string.
	 *
	 * @param str The input string.
	 * @return The filtered string.
	 */
	public static String filterInvalidCharacters(String str) {
		StringBuilder bldr = new StringBuilder();
		for (char c : str.toLowerCase().toCharArray()) {
			for (char validChar : FREQUENCY_ORDERED_CHARS) {
				if (c == validChar) {
					bldr.append(c);
					break;
				}
			}
		}
		return bldr.toString();
	}

	/**
	 * Capitalizes the string correctly.
	 *
	 * @param str The input string.
	 * @return The string with correct capitalization.
	 */
	public static String capitalize(String str) {
		char[] chars = str.toCharArray();
		boolean sentenceStart = true;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (sentenceStart) {
				if (c >= 'a' && c <= 'z') {
					chars[i] -= 0x20;
					sentenceStart = false;
				} else if (c >= 'A' && c <= 'Z') {
					sentenceStart = false;
				}
			} else {
				if (c >= 'A' && c <= 'Z') {
					chars[i] += 0x20;
				}
			}
			if (c == '.' || c == '!' || c == '?') {
				sentenceStart = true;
			}
		}
		return new String(chars, 0, chars.length);
	}

	/**
	 * Parses a single {@code String} by splitting them into arguments delimited
	 * by a quote character <tt>"</tt>.
	 *
	 * @param source The source string.
	 * @return The parsed arguments.
	 */
	public static String[] parse(String source) {
		return parse(source, '"');
	}

	/**
	 * Parses a single {@code String} by splitting them into arguments delimited
	 * by the specified character.
	 *
	 * @param source The source string.
	 * @param delim The delimiter of the string.
	 * @return The parsed arguments.
	 */
	public static String[] parse(String source, char delim) {
		List<String> arguments = new ArrayList<>();
		StringBuilder bldr = new StringBuilder();
		boolean quoted = false, escaped = false;

		for (int i = 0, len = source.length(); i < len; i++) {
			char c = source.charAt(i);
			if (c == ' ' && !quoted) {
				arguments.add(bldr.toString());
				bldr.setLength(0);
			} else if (c == delim && !escaped) {
				quoted = !quoted;
			} else if (c == '\\') {
				escaped = true;
			} else {
				escaped = false;
				bldr.append(c);
			}
		}

		if (bldr.length() > 0) {
			arguments.add(bldr.toString());
		}

		return Iterables.toArray(arguments, String.class);
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated within itself.
	 */
	private TextUtil() {
		throw new UnsupportedOperationException("static-utility classes may not be instantiated.");
	}

}