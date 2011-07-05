package com.matji.sandwich.util;

import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

public class TextHelper {
	/**
	 * Given either a Spannable String or a regular String and a token, apply
	 * the given CharacterStyle to the span between the tokens, and also remove
	 * tokens.
	 * <p>
	 * For example, {@code setSpanBetweenTokens("Hello ##world##!", "##",
	 * new ForegroundColorSpan(0xFFFF0000));} will return a CharSequence {@code
	 * "Hello world!"} with {@code world} in red.
	 *
	 * @param text The text, with the tokens, to adjust.
	 * @param token The token string; there should be at least two instances of
	 *            token in text.
	 * @param cs The style to apply to the CharSequence. WARNING: You cannot
	 *            send the same two instances of this parameter, otherwise the
	 *            second call will remove the original span.
	 * @return A Spannable CharSequence with the new style applied.
	 *
	 * @see http://developer.android.com/reference/android/text/style/CharacterStyle.html
	 */
	public static CharSequence setSpanBetweenTokens(CharSequence text,
			String token, CharacterStyle... cs)
	{
		// Start and end refer to the points where the span will apply
		int tokenLen = token.length();
		int start = text.toString().indexOf(token) + tokenLen;
		int end = text.toString().indexOf(token, start);

		if (start > -1 && end > -1)
		{
			// Copy the spannable string to a mutable spannable string
			SpannableStringBuilder ssb = new SpannableStringBuilder(text);
			for (CharacterStyle c : cs)
				ssb.setSpan(c, start, end, 0);

			// Delete the tokens before and after the span
			ssb.delete(end, end + tokenLen);
			ssb.delete(start - tokenLen, start);

			text = ssb;
			// return setSpanBetweenTokens(text, token, cs);
		}
		return text;
	}

	public static CharSequence convertColorSpan(CharSequence text, String token, int color) {
		return TextHelper.setSpanBetweenTokens(text, token, new ForegroundColorSpan(color));
	}	
}
