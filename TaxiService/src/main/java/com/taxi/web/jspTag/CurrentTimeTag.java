package com.taxi.web.jspTag;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CurrentTimeTag extends SimpleTagSupport {
	private String value;
	
	public CurrentTimeTag() {
		
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	
	@Override
	public void doTag() throws SkipPageException {
		try {
			getJspContext().getOut().write(" || "+  
				LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
						.withLocale(Locale.forLanguageTag(value))).toString());
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new SkipPageException();
		}
	}
}