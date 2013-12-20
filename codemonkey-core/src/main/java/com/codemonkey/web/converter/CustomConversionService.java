package com.codemonkey.web.converter;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

import com.codemonkey.utils.SysUtils;

@Service
public class CustomConversionService extends FormattingConversionServiceFactoryBean {

	private Logger logger = SysUtils.getLog(CustomConversionService.class);

	@Autowired private List<Converter<?, ?>> converters;
	
	protected void installFormatters(FormatterRegistry registry) {
        super.installFormatters(registry);
        installCustomConverters(registry);
    }

	private void installCustomConverters(FormatterRegistry registry) {
		for (Converter<?, ?> converter : converters) {
            if (logger.isInfoEnabled()) {
                logger.info("Registering converter " + converter.toString());
            }
            registry.addConverter(converter);
        }
	}
}
