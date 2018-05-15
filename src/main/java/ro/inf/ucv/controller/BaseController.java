package ro.inf.ucv.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;

import ro.inf.ucv.service.DocumentService;
import ro.inf.ucv.service.RecordService;
import ro.inf.ucv.service.utils.ConfigurationUtils;

@Controller
public class BaseController {

	@Autowired
	protected RecordService recordService;

	@Autowired
	protected DocumentService documentService;

	@Autowired
	protected ConfigurationUtils configurationUtils;

	@Autowired
	private MessageSource messageSource;

	private Locale locale = LocaleContextHolder.getLocale();

	public String getMessage(String key, Object[] args, Locale locale) {
		return messageSource.getMessage(key, args, locale);
	}

	public String getMessage(String key, Object args, Locale locale) {
		Object[] objects = { args };
		return messageSource.getMessage(key, objects, locale);
	}

	public Locale getLocale() {
		return locale;
	}

}
