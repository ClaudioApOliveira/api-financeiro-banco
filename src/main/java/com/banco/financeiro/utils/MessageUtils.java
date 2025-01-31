package com.banco.financeiro.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MessageUtils {

	public static String getMensagemValidacao(final String chaveMensagem, final Object... params) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
		String pattern = bundle.getString(chaveMensagem);
		return MessageFormat.format(pattern, params);
	}
}
